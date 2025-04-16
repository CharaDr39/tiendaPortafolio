package com.example.Tienda.domain; // Paquete de la entidad

import jakarta.persistence.*;    // Anotaciones JPA
import java.io.Serializable;      // Para permitir serialización
import lombok.Data;               // Genera getters, setters, toString, etc.

@Data
@Entity                      // Marca la clase como entidad JPA
@Table(name = "producto")    // Mapea esta entidad a la tabla "producto"
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L; // Versión de serialización

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK auto-incremental
    @Column(name = "id_producto")                       // Nombre de la columna
    private Long idProducto;                             // Identificador del producto

    @Column(nullable = false)   // No permite valor nulo en BD
    private String descripcion;  // Descripción breve del producto

    private String detalle;      // Detalle o especificaciones del producto
    private double precio;       // Precio unitario
    private int existencias;     // Cantidad disponible en inventario

    @Column(name = "ruta_imagen") // Mapea a columna ruta_imagen
    private String rutaImagen;     // URL o ruta de la imagen del producto

    private boolean activo;       // Indica si el producto está activo

    @ManyToOne                  // Relación muchos-a-uno con Categoria
    @JoinColumn(name = "id_categoria") // Clave foránea en tabla producto
    private Categoria categoria; // Categoría a la que pertenece el producto

    // Constructor sin argumentos (requerido por JPA)
    public Producto() {
    }

    // Constructor conveniente para crear instancias mínimas
    public Producto(String descripcion, boolean activo) {
        this.descripcion = descripcion;
        this.activo = activo;
    }
}
