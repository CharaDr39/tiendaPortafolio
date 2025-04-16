package com.example.Tienda.dao; // Paquete donde se ubica el DAO

import com.example.Tienda.domain.Categoria; // Entidad Categoria
import org.springframework.data.jpa.repository.JpaRepository; // Proporciona métodos CRUD

/**
 * Interfaz DAO para la entidad Categoria.
 * Al extender JpaRepository<Categoria, Long>, hereda:
 *  - save(Categoria): guardar o actualizar
 *  - findById(Long): buscar por ID
 *  - findAll(): listar todas
 *  - delete(Categoria): eliminar
 *  - y otros métodos estándar de JPA
 */
public interface CategoriaDao extends JpaRepository<Categoria, Long> {
    // No se requieren métodos adicionales; JpaRepository ya ofrece las operaciones básicas
}
