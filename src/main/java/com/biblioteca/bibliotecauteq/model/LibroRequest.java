package com.biblioteca.bibliotecauteq.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LibroRequest implements Serializable {
    private Libro libro;
    private List<Capitulo> capituloFileList;
}
