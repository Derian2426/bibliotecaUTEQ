package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Integer> {
    Autor findByNombre(String nombre);
}
