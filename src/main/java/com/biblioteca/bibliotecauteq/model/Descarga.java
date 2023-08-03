package com.biblioteca.bibliotecauteq.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Descarga")
@Data
public class Descarga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDescarga")
    private Long idDescarga;
    @Column(name = "ipAddress", nullable = false, length = 50)
    private String ipAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fechaDescarga")
    private Date fechaDescarga;
    @Column(name = "pdfCantidadDescarga")
    private Long pdfCantidadDescarga;
    @ManyToOne
    @JoinColumn(name = "idLibro")
    private Libro libro;
}
