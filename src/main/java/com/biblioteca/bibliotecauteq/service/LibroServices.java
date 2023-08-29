package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.ILibro;
import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        return libroRepository.findById(idLibro);
    }

    @Override
    public List<Libro> findAll() {
        try {
            return libroRepository.findAll();
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
