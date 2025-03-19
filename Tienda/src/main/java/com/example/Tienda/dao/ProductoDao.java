package com.example.Tienda.dao;

import org.springframework.data.jpa.repository.Query;
import java.util.List;

import com.example.Tienda.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDao extends JpaRepository <Producto, Long> {


//-*-*-*-*-*--*-*-*-*-*- EA-08-Practicas-4 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*

    @Query("SELECT p.categoria.descripcion, SUM(p.precio * p.existencias) " +
           "FROM Producto p GROUP BY p.categoria.descripcion")
    List<Object[]> findTotalInventoryValueByCategory();
}
