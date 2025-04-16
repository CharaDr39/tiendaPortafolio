package com.example.Tienda.dao; // Paquete del DAO

import com.example.Tienda.domain.Producto;            // Entidad Producto
import org.springframework.data.jpa.repository.JpaRepository; // CRUD y paginación
import org.springframework.data.jpa.repository.Query;       // Para consultas JPQL
import java.util.List;                                     // Para listas genéricas

/**
 * DAO para entidad Producto.
 * Extiende JpaRepository<Producto, Long> para heredar:
 *   - save, findById, findAll, delete, etc.
 */
public interface ProductoDao extends JpaRepository<Producto, Long> {

    // Consulta JPQL que agrupa por categoría y suma (precio * existencias)
    @Query("SELECT p.categoria.descripcion, SUM(p.precio * p.existencias) " +
           "FROM Producto p GROUP BY p.categoria.descripcion")
    List<Object[]> findTotalInventoryValueByCategory();
}
