package com.example.Tienda.controller; // Define el paquete del controlador

import com.example.Tienda.domain.Categoria; // Entidad de dominio Categoria
import com.example.Tienda.service.CategoriaService; // Servicio para operaciones de Categoria
import com.example.Tienda.service.impl.FirebaseStorageServiceImpl; // Servicio para subir imágenes a Firebase

import org.springframework.beans.factory.annotation.Autowired; // Inyección de dependencias
import org.springframework.stereotype.Controller; // Marca la clase como controlador MVC
import org.springframework.ui.Model; // Para pasar datos a la vista
import org.springframework.web.bind.annotation.GetMapping; // Mapea peticiones GET
import org.springframework.web.bind.annotation.PostMapping; // Mapea peticiones POST
import org.springframework.web.bind.annotation.RequestMapping; // Prefijo común para rutas
import org.springframework.web.bind.annotation.RequestParam; // Para leer parámetros de formulario
import org.springframework.web.multipart.MultipartFile; // Representa un archivo subido

@Controller
@RequestMapping("/categoria") // Ruta base para todas las acciones de categorías
public class CategoriaController {
  
    @Autowired
    private CategoriaService categoriaService; // Servicio principal de categorías
    
    @GetMapping("/listado") // Atiende GET /categoria/listado
    private String listado(Model model) {
        var categorias = categoriaService.getCategorias(false); // Obtiene lista de categorías activas
        model.addAttribute("categorias", categorias); // Agrega la lista al modelo
        model.addAttribute("totalCategorias", categorias.size()); // Agrega el total al modelo
        return "/categoria/listado"; // Devuelve la vista listado.html
    }
    
    @GetMapping("/nuevo") // Atiende GET /categoria/nuevo
    public String categoriaNuevo(Categoria categoria) {
        return "/categoria/modifica"; // Carga la vista de formulario en blanco
    }

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService; // Servicio para almacenar imágenes en Firebase
    
    @PostMapping("/guardar") // Atiende POST /categoria/guardar
    public String categoriaGuardar(Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile) {
        // Si se sube una imagen, primero guarda la categoría para obtener su ID
        if (!imagenFile.isEmpty()) {
            categoriaService.save(categoria); 
            // Sube la imagen a Firebase y obtiene la URL
            String url = firebaseStorageService.cargaImagen(
                    imagenFile, 
                    "categoria", 
                    categoria.getIdCategoria()
            );
            categoria.setRutaImagen(url); // Guarda la ruta en la entidad
        }
        categoriaService.save(categoria); // Guarda o actualiza la categoría con la ruta de imagen
        return "redirect:/categoria/listado"; // Redirige al listado
    }

    @GetMapping("/eliminar/{idCategoria}") // Atiende GET /categoria/eliminar/{id}
    public String categoriaEliminar(Categoria categoria) {
        categoriaService.delete(categoria); // Elimina la categoría indicada
        return "redirect:/categoria/listado"; // Redirige al listado
    }

    @GetMapping("/modificar/{idCategoria}") // Atiende GET /categoria/modificar/{id}
    public String categoriaModificar(Categoria categoria, Model model) {
        categoria = categoriaService.getCategoria(categoria); // Recupera la categoría por ID
        model.addAttribute("categoria", categoria); // La pasa al modelo para precargar el formulario
        return "/categoria/modifica"; // Carga la vista de edición
    }   
}
