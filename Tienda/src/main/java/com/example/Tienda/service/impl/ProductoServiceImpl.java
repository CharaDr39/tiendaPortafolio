package com.example.Tienda.service.impl;

import com.example.Tienda.dao.ProductoDao;
import com.example.Tienda.domain.Producto;
import com.example.Tienda.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

     @Autowired
    private ProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activos) {
        var lista = productoDao.findAll();
        if (activos) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    @Transactional
    public void delete(Producto producto) {
        productoDao.delete(producto);
    }
    



//-*-*-*-*-*--*-*-*-*-*- EA-08-Practicas-4 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*



    @Override
    public List<Object[]> getTotalInventoryValueByCategory() {
        return productoDao.findTotalInventoryValueByCategory();
    }
}
