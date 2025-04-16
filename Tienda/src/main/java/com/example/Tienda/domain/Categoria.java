package com.example.Tienda.domain;

import jakarta.persistence.*;        // Anotaciones JPA
import java.io.Serializable;        // Permite serializar la entidad
import java.util.List;              // Para colecciones
import lombok.Data;                 // Genera getters, setters, toString, etc.

/**
 * Entidad JPA que representa la tabla "categoria"
 */
@Data
@Entity                        // Marca la clase como entidad JPA
@Table(name = "categoria")    // Mapea a la tabla "categoria"
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L; // Versión de serialización

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK auto-incremental
    @Column(name = "id_categoria")                     // Nombre de la columna
    private Long idCategoria;

    @Column(nullable = false)   // Columna NOT NULL
    private String descripcion;

    @Column(name = "ruta_imagen") // Mapea a columna ruta_imagen
    private String rutaImagen;

    private boolean activo;       // Indica si la categoría está activa

    @OneToMany                  // Relación 1:N con Producto
    @JoinColumn(name = "id_categoria", updatable = false)
    private List<Producto> productos; // Lista de productos asociados

    // Constructor sin argumentos (requerido por JPA)
    public Categoria() {
    }

    // Constructor para crear instancias rápidas
    public Categoria(String descripcion, boolean activo) {
        this.descripcion = descripcion;
        this.activo = activo;
    }

}
