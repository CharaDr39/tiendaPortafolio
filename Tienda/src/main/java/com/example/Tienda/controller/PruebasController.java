package com.example.Tienda.controller;

import com.example.Tienda.domain.Categoria;
import com.example.Tienda.service.CategoriaService;
import com.example.Tienda.service.ProductoService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/pruebas")
public class PruebasController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    @GetMapping("/listado/{idCategoria}")
    public String listado(Model model, Categoria categoria) {
        var productos = categoriaService.getCategoria(categoria).getProductos();
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }


//-*-*-*-*-*--*-*-*-*-*- EA-08-Practicas-4 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    

    @GetMapping("/consultaAmpliada")
    public String consultaAmpliada(Model model) {
        List<Object[]> consulta = productoService.getTotalInventoryValueByCategory();
        model.addAttribute("consulta", consulta);
        return "pruebas/consultaAmpliada";
    }

}
