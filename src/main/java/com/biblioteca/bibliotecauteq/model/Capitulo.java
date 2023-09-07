package com.biblioteca.bibliotecauteq.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Capitulo")
@Data
public class Capitulo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCapitulo")
    private Integer idCapitulo;
    @Column(name = "titulo", nullable = false,length = 300)
    private String titulo;
    @Column(name = "nombreArchivo",length = 300)
    private String nombreArchivo;
    @Column(name = "ruta_archivo",length = 250)
    private String rutaArchivo;
    @Column(name = "ordenArchivo")
    private Integer ordenArchivo;
    @Column(name = "numeroDescarga")
    private Integer numeroDescarga;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fechaCreacion")
    private Date fechaCreacion;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    //@JsonIgnore
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "idLibro")
    @JsonIgnore
    //@JsonBackReference
    private Libro libro;

}
