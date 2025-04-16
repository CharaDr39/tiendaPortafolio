package com.example.Tienda.service;

import com.example.Tienda.domain.Categoria;
import java.util.List;

/**
 * Interfaz de servicio para operaciones con la entidad Categoria.
 */
public interface CategoriaService {

    /**
     * Obtiene la lista de categorías.
     * @param activo si true filtra solo categorías activas; si false, todas.
     * @return lista de categorías según el filtro.
     */
    List<Categoria> getCategorias(boolean activo);

    /**
     * Recupera una categoría a partir de su identificador.
     * @param categoria instancia con el idCategoria a buscar.
     * @return la categoría completa obtenida de la base de datos.
     */
    Categoria getCategoria(Categoria categoria);
    
    /**
     * Inserta una nueva categoría o actualiza una existente.
     * @param categoria la categoría a guardar; si idCategoria es null inserta, sino actualiza.
     */
    void save(Categoria categoria);
    
    /**
     * Elimina la categoría especificada.
     * @param categoria la categoría a eliminar (debe contener el idCategoria).
     */
    void delete(Categoria categoria);

}
