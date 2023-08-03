package com.biblioteca.bibliotecauteq.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TipoAutor")
@Data
public class TipoAutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAutorTipo")
    private Integer idAutor;
    @Column(name = "tipoAutor", nullable = false, length = 30)
    private String tipoAutor;
}
