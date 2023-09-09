package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.model.SubAreasEspecificas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    Libro findByNombreLibro(String nombreLibro);
    List<Libro> findByNombreLibroContainingIgnoreCase(String nombreLibro);
    List<Libro> findBySubAreasEspecificas(SubAreasEspecificas subAreasEspecificas);
    List<Libro> findBySubAreasEspecificasAndNombreLibro(SubAreasEspecificas subAreasEspecificas, String nombreLibro);

}
