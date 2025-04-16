package com.example.Tienda.service.impl;

import com.example.Tienda.service.FirebaseStorageService;            // Interface del servicio de almacenamiento
import com.google.auth.Credentials;                                 // Credenciales de autenticación
import com.google.auth.ServiceAccountSigner;                        // Para firmar URLs con cuenta de servicio
import com.google.auth.oauth2.GoogleCredentials;                    // Leer credenciales desde JSON
import com.google.cloud.storage.BlobId;                             // Identificador de objeto en Cloud Storage
import com.google.cloud.storage.BlobInfo;                           // Metadatos del objeto
import com.google.cloud.storage.Storage;                            // Cliente de Cloud Storage
import com.google.cloud.storage.Storage.SignUrlOption;              // Opciones para firmar URLs
import com.google.cloud.storage.StorageOptions;                     // Configuración del cliente
import java.io.File;                                               // Representa archivo temporal
import java.io.FileOutputStream;                                   // Para escribir bytes en archivo
import java.io.IOException;                                        // Excepciones de I/O
import java.nio.file.Files;                                        // Utilidades de archivos
import java.util.concurrent.TimeUnit;                              // Para especificar duración de la URL
import org.springframework.core.io.ClassPathResource;               // Para cargar recursos del classpath
import org.springframework.stereotype.Service;                     // Marca la clase como servicio Spring
import org.springframework.web.multipart.MultipartFile;            // Representa archivo subido por cliente

@Service // Declara este bean como servicio gestionado por Spring
public class FirebaseStorageServiceImpl implements FirebaseStorageService {
    // Método principal que recibe un MultipartFile, nombre de carpeta e ID para generar URL pública
    @Override
    public String cargaImagen(MultipartFile archivoLocalCliente, String carpeta, Long id) {
        try {
            // Obtiene el nombre original del archivo subido
            String extension = archivoLocalCliente.getOriginalFilename();
            // Genera nombre único: "img" + ID formateado + nombre original
            String fileName = "img" + sacaNumero(id) + extension;
            // Convierte el MultipartFile a un File temporal en el servidor
            File file = this.convertToFile(archivoLocalCliente);
            // Sube el archivo a Cloud Storage y obtiene la URL firmada (válida 10 años)
            String URL = this.uploadFile(file, carpeta, fileName);
            // Elimina el archivo temporal local
            file.delete();
            return URL;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Retorna null si ocurre un error
        }
    }

    // Sube el archivo al bucket y genera URL firmada
    private String uploadFile(File file, String carpeta, String fileName) throws IOException {
        // Carga el JSON de credenciales desde el classpath
        ClassPathResource json = new ClassPathResource(rutaJsonFile + File.separator + archivoJsonFile);
        // Construye el identificador del blob: bucket + ruta/carpeta/archivo
        BlobId blobId = BlobId.of(BucketName, rutaSuperiorStorage + "/" + carpeta + "/" + fileName);
        // Crea metadata del blob indicando tipo de contenido
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        // Lee credenciales de la cuenta de servicio
        Credentials credentials = GoogleCredentials.fromStream(json.getInputStream());
        // Construye el cliente de Storage con dichas credenciales
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        // Sube los bytes del archivo al blob
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        // Genera URL firmada válida por 3650 días (10 años)
        return storage
            .signUrl(blobInfo, 3650, TimeUnit.DAYS,
                     SignUrlOption.signWith((ServiceAccountSigner) credentials))
            .toString();
    }

    // Convierte MultipartFile a File temporal
    private File convertToFile(MultipartFile archivoLocalCliente) throws IOException {
        // Crea un archivo temporal con prefijo "img"
        File tempFile = File.createTempFile("img", null);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(archivoLocalCliente.getBytes()); // Escribe los bytes del multipart
        }
        return tempFile;
    }

    // Formatea el ID con ceros a la izquierda hasta longitud 19
    private String sacaNumero(long id) {
        return String.format("%019d", id);
    }
}
