package com.example.Tienda.controller; // Define el paquete del controlador

import com.example.Tienda.domain.Producto; // Entidad de dominio Producto
import com.example.Tienda.service.CategoriaService; // Servicio para categorías
import com.example.Tienda.service.ProductoService; // Servicio para productos
import com.example.Tienda.service.impl.FirebaseStorageServiceImpl; // Servicio para almacenamiento de imágenes en Firebase

import org.springframework.beans.factory.annotation.Autowired; // Inyección de dependencias
import org.springframework.stereotype.Controller; // Marca la clase como controlador MVC
import org.springframework.ui.Model; // Para pasar datos a la vista
import org.springframework.web.bind.annotation.GetMapping; // Mapea peticiones GET
import org.springframework.web.bind.annotation.PostMapping; // Mapea peticiones POST
import org.springframework.web.bind.annotation.RequestMapping; // Prefijo común de ruta
import org.springframework.web.bind.annotation.RequestParam; // Para leer parámetros de formulario
import org.springframework.web.multipart.MultipartFile; // Representa archivo subido

@Controller
@RequestMapping("/producto") // Ruta base para operaciones de productos
public class ProductoController {
  
    @Autowired
    private ProductoService productoService; // Servicio principal de productos
    
    @Autowired
    private CategoriaService categoriaService; // Servicio para obtener categorías
    
    @GetMapping("/listado") // Atiende GET /producto/listado
    private String listado(Model model) {
        var productos = productoService.getProductos(false); // Lista productos activos
        model.addAttribute("productos", productos); // Agrega lista al modelo
        
        var categorias = categoriaService.getCategorias(false); // Lista categorías activas
        model.addAttribute("categorias", categorias); // Agrega categorías al modelo
        
        model.addAttribute("totalProductos", productos.size()); // Agrega total de productos
        return "/producto/listado"; // Devuelve la vista listado.html
    }
    
    @GetMapping("/nuevo") // Atiende GET /producto/nuevo
    public String productoNuevo(Producto producto) {
        return "/producto/modifica"; // Carga formulario en blanco
    }

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService; // Servicio para subir imágenes
    
    @PostMapping("/guardar") // Atiende POST /producto/guardar
    public String productoGuardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()) { // Si hay archivo de imagen
            productoService.save(producto); // Guarda primero para generar ID
            // Sube imagen a Firebase y obtiene URL
            String url = firebaseStorageService.cargaImagen(
                    imagenFile,
                    "producto",
                    producto.getIdProducto()
            );
            producto.setRutaImagen(url); // Asigna la ruta de la imagen
        }
        productoService.save(producto); // Guarda o actualiza producto
        return "redirect:/producto/listado"; // Redirige al listado
    }

    @GetMapping("/eliminar/{idProducto}") // Atiende GET /producto/eliminar/{id}
    public String productoEliminar(Producto producto) {
        productoService.delete(producto); // Elimina el producto indicado
        return "redirect:/producto/listado"; // Redirige al listado
    }

    @GetMapping("/modificar/{idProducto}") // Atiende GET /producto/modificar/{id}
    public String productoModificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto); // Recupera producto por ID
        model.addAttribute("producto", producto); // Precarga producto en formulario
        
        var categorias = categoriaService.getCategorias(false); // Lista categorías
        model.addAttribute("categorias", categorias); // Agrega categorías al modelo
        
        return "/producto/modifica"; // Carga vista de edición
    }   
}
