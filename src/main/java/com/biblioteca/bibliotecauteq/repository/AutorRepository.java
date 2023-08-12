package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Integer> {
    List<Autor> findByNombre(String nombre);
    List<Autor> findByApellido(String apellido);
}
