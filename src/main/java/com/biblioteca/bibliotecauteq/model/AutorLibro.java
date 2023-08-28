package com.biblioteca.bibliotecauteq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "AutorLibro")
@Data
public class AutorLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAutorLibro")
    private Integer idAutorLibro;
    @ManyToOne
    @JoinColumn(name = "idAutor")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "idLibro")
    @JsonIgnore
    private Libro libro;
    @ManyToOne
    @JoinColumn(name = "idAutorTipo")
    private TipoAutor tipoAutor;
}
