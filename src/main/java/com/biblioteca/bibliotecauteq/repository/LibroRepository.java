package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.AreaConocimiento;
import com.biblioteca.bibliotecauteq.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    Libro findByNombreLibro(String nombreLibro);
}
