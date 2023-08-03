package com.biblioteca.bibliotecauteq.model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "Autor")
@Data
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAutor")
    private Integer idAutor;
    @Column(name = "nombre", nullable = false,length = 40)
    private String nombre;
    @Column(name = "apellido", nullable = false,length = 40)
    private String apellido;
}
