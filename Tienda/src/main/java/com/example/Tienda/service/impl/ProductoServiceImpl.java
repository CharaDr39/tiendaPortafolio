package com.example.Tienda.service.impl; // Paquete de implementación del servicio

import com.example.Tienda.dao.ProductoDao;          // DAO para acceso a datos de Producto
import com.example.Tienda.domain.Producto;          // Entidad Producto
import com.example.Tienda.service.ProductoService;  // Interfaz del servicio
import java.util.List;                              // Para manejo de listas
import org.springframework.beans.factory.annotation.Autowired;       // Inyección de dependencias
import org.springframework.stereotype.Service;       // Marca la clase como servicio Spring
import org.springframework.transaction.annotation.Transactional; // Control de transacciones

@Service // Declara este bean como servicio gestionado por Spring
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao; // Inyecta el DAO de Producto

    @Override
    @Transactional(readOnly = true) // Transacción de solo lectura
    public List<Producto> getProductos(boolean activos) {
        var lista = productoDao.findAll();          // Obtiene todos los productos
        if (activos) {
            lista.removeIf(e -> !e.isActivo());    // Filtra solo los activos si es true
        }
        return lista;                              // Devuelve la lista resultante
    }

    @Override
    @Transactional(readOnly = true) // Transacción de solo lectura
    public Producto getProducto(Producto producto) {
        // Busca un producto por su ID; retorna null si no existe
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    @Override
    @Transactional // Transacción de lectura/escritura
    public void save(Producto producto) {
        productoDao.save(producto); // Inserta o actualiza el producto
    }

    @Override
    @Transactional // Transacción de lectura/escritura
    public void delete(Producto producto) {
        productoDao.delete(producto); // Elimina el producto indicado
    }

    // -*-*-*-*-*--*-*-*-*-*- EA-08-Practicas-4 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*

    @Override
    public List<Object[]> getTotalInventoryValueByCategory() {
        // Llama al DAO para obtener el valor total de inventario por categoría
        return productoDao.findTotalInventoryValueByCategory();
    }
}
