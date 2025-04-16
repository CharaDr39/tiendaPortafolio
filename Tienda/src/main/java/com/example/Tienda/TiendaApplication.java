package com.example.Tienda; // Define el paquete donde reside la aplicación

import org.springframework.boot.SpringApplication; // Proporciona método para lanzar la aplicación
import org.springframework.boot.autoconfigure.SpringBootApplication; // Activa la configuración automática de Spring Boot

@SpringBootApplication // Marca esta clase como la aplicación Spring Boot principal
public class TiendaApplication {

    public static void main(String[] args) {
        // Método de entrada de la aplicación
        SpringApplication.run(TiendaApplication.class, args); 
        // Inicia el contexto de Spring y arranca el servidor embebido
    }

}
