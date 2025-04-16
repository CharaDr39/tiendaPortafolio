package com.example.Tienda.controller; // Paquete del controlador

import com.example.Tienda.domain.Categoria; // Entidad Categoria
import com.example.Tienda.service.CategoriaService; // Servicio de negocio para categorías
import com.example.Tienda.service.ProductoService; // Servicio de negocio para productos

import java.util.List; // Para listas genéricas
import org.springframework.beans.factory.annotation.Autowired; // Inyección de dependencias
import org.springframework.stereotype.Controller; // Marca la clase como controlador MVC
import org.springframework.ui.Model; // Para pasar datos a la vista
import org.springframework.web.bind.annotation.GetMapping; // Mapea peticiones GET
import org.springframework.web.bind.annotation.RequestMapping; // Prefijo común de ruta

@Controller
@RequestMapping("/pruebas") // Prefijo base: /pruebas
public class PruebasController {

    @Autowired
    private ProductoService productoService; // Inyecta el servicio de productos
    @Autowired
    private CategoriaService categoriaService; // Inyecta el servicio de categorías

    @GetMapping("/listado") // GET /pruebas/listado
    public String listado(Model model) {
        var productos = productoService.getProductos(false); // Obtiene todos productos activos
        var categorias = categoriaService.getCategorias(false); // Obtiene todas categorías activas
        model.addAttribute("productos", productos); // Añade lista de productos al modelo
        model.addAttribute("totalProductos", productos.size()); // Añade conteo de productos
        model.addAttribute("categorias", categorias); // Añade lista de categorías
        return "/pruebas/listado"; // Retorna la vista pruebas/listado.html
    }

    @GetMapping("/listado/{idCategoria}") // GET /pruebas/listado/{idCategoria}
    public String listado(Model model, Categoria categoria) {
        // Recupera la categoría por su ID y obtiene sus productos
        var productos = categoriaService.getCategoria(categoria).getProductos();
        var categorias = categoriaService.getCategorias(false); // Para menú de categorías
        model.addAttribute("productos", productos); // Productos filtrados por categoría
        model.addAttribute("totalProductos", productos.size()); // Conteo de filtrados
        model.addAttribute("categorias", categorias); // Todas las categorías
        return "/pruebas/listado"; // Muestra la misma vista con datos filtrados
    }

    // -*-*-*-*-*- EA-08-Practicas-4 -*-*-*-*-*-*-*-*-*-*-*-*-*-
    @GetMapping("/consultaAmpliada") // GET /pruebas/consultaAmpliada
    public String consultaAmpliada(Model model) {
        // Llama al servicio para obtener valor total de inventario por categoría
        List<Object[]> consulta = productoService.getTotalInventoryValueByCategory();
        model.addAttribute("consulta", consulta); // Pasa la consulta a la vista
        return "pruebas/consultaAmpliada"; // Retorna la vista pruebas/consultaAmpliada.html
    }

}
