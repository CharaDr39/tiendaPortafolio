package com.example.Tienda.service.impl; // Paquete de implementación del servicio

import com.example.Tienda.dao.CategoriaDao;            // DAO para acceso a datos de Categoria
import com.example.Tienda.domain.Categoria;            // Entidad Categoria
import com.example.Tienda.service.CategoriaService;    // Interfaz de servicio
import java.util.List;                                 // Para listas genéricas
import org.springframework.beans.factory.annotation.Autowired; // Inyección de dependencias
import org.springframework.stereotype.Service;          // Marca la clase como servicio
import org.springframework.transaction.annotation.Transactional; // Control de transacciones

@Service // Declara esta clase como componente de servicio para Spring
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaDao categoriaDao; // Inyecta el DAO de Categoria

    @Override
    @Transactional(readOnly = true) // Transacción de solo lectura
    public List<Categoria> getCategorias(boolean activos) {
        var lista = categoriaDao.findAll();         // Obtiene todas las categorías
        if (activos) {
            lista.removeIf(e -> !e.isActivo());     // Filtra solo las activas si corresponde
        }
        return lista;                               // Devuelve la lista resultante
    }

    @Override
    @Transactional(readOnly = true) // Transacción de solo lectura
    public Categoria getCategoria(Categoria categoria) {
        // Busca por ID y devuelve la categoría o null si no existe
        return categoriaDao.findById(categoria.getIdCategoria()).orElse(null);
    }

    @Override
    @Transactional // Transacción de lectura/escritura
    public void save(Categoria categoria) {
        categoriaDao.save(categoria); // Inserta o actualiza la categoría
    }

    @Override
    @Transactional // Transacción de lectura/escritura
    public void delete(Categoria categoria) {
        categoriaDao.delete(categoria); // Elimina la categoría indicada
    }
    
}
