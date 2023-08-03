package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.Capitulo;
import com.biblioteca.bibliotecauteq.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CapituloRepository extends JpaRepository<Capitulo, Integer> {
    Optional<List<Capitulo>> findByLibro(Libro libro);
}
