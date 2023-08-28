package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.AutorLibro;
import com.biblioteca.bibliotecauteq.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorLibroRepository extends JpaRepository<AutorLibro, Integer> {
    List<AutorLibro> findByLibro(Libro libro);
}
