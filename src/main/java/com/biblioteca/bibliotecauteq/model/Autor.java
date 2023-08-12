package com.biblioteca.bibliotecauteq.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Autor")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
