package com.biblioteca.bibliotecauteq.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Libro")
@Data
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLibro")
    private Long idLibro;
    @Column(name = "nombreLibro", nullable = false, length = 300)
    private String nombreLibro;
    @Column(name = "carpetaLibro", length = 300)
    private String carpetaLibro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fechaPublicacion")
    private Date fechaPublicacion;
    @Column(name = "isbn", length = 30)
    private String isbn;
    @Column(name = "lenguaje", length = 30)
    private String lenguaje;
    @Column(name = "coverImage", length = 300)
    private String coverImage;
    @Column(name = "pdfLibro", length = 300)
    private String pdfLibro;
    @Column(name = "pdfDescarga")
    private Long pdfDescarga;
    @ManyToOne
    @JoinColumn(name = "idSubAreaEspecifica")
    private SubAreasEspecificas subAreasEspecificas;
    public Libro libro(LibroRequest libroRequest){
        try {
            Libro libro= new Libro();
            libro.setSubAreasEspecificas(libro.getSubAreasEspecificas());
            libro.setNombreLibro(libroRequest.getLibro().getNombreLibro());
            libro.setFechaPublicacion(libroRequest.getLibro().getFechaPublicacion());
            libro.setIsbn(libroRequest.getLibro().getIsbn());
            libro.setLenguaje(libroRequest.getLibro().getLenguaje());
            libro.setCoverImage(libroRequest.getLibro().getCoverImage());
            libro.setPdfLibro(libroRequest.getLibro().getPdfLibro());
            libro.setPdfDescarga(libroRequest.getLibro().getPdfDescarga());
            return libro;
        }catch (Exception e){
            return new Libro();
        }
    }

    //@OneToMany(mappedBy = "libro")
    //@JsonManagedReference
    //private List<Capitulo>capitulos;
}
