package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.ILibro;
import com.biblioteca.bibliotecauteq.model.Busqueda;
import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LibroServices implements ILibro {
    @Autowired
    private LibroRepository libroRepository;

    @Override
    public Libro create(Libro libro) {
        try {
            Libro busquedaLibro = libroRepository.findByNombreLibro(libro.getNombreLibro());
            if (busquedaLibro != null)
                return new Libro();
            return libroRepository.save(libro);
        } catch (Exception e) {
            return new Libro();
        }
    }

    @Override
    public Libro update(Libro libro) {
        return null;
    }

    @Override
    public Optional<Libro> findById(Integer idLibro) {
        try {
            return libroRepository.findById(idLibro);
        } catch (Exception e) {
            return Optional.of(new Libro());
        }
    }

    @Override
    public List<Libro> findAll() {
        try {
            return libroRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Libro> findLibro(Busqueda libroBusqueda) {
        try {
            if (libroBusqueda.getSubAreasEspecificas() != null && libroBusqueda.getSubAreasEspecificas().getSubAreasConocimiento() != null
                    && libroBusqueda.getSubAreasEspecificas().getSubAreasConocimiento().getAreaConocimiento() != null
            ) {
                if (!Objects.equals(libroBusqueda.getCadenaBusqueda(), null)) {
                    return libroRepository.findBySubAreasEspecificasAndNombreLibroContainingIgnoreCase(libroBusqueda.getSubAreasEspecificas(), libroBusqueda.getCadenaBusqueda());
                } else {
                    return libroRepository.findBySubAreasEspecificas(libroBusqueda.getSubAreasEspecificas());
                }
            } else {
                return libroRepository.findByNombreLibroContainingIgnoreCase(libroBusqueda.getCadenaBusqueda());
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(Integer idLibro) {

    }

    public Boolean busquedaLibro(String nombreLibro) {
        Libro busquedaLibro = libroRepository.findByNombreLibro(nombreLibro);
        return busquedaLibro != null;
    }
}
