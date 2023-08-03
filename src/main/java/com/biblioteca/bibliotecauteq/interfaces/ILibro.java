package com.biblioteca.bibliotecauteq.interfaces;

import com.biblioteca.bibliotecauteq.model.Libro;

import java.util.List;
import java.util.Optional;

public interface ILibro {
    Libro create(Libro libro);
    Libro update(Libro libro);
    Optional<Libro> findById(Integer idLibro);
    List<Libro> findAll();
    void delete(Integer idLibro);
}
