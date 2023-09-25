package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.IAutorLibro;
import com.biblioteca.bibliotecauteq.model.AutorLibro;
import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.repository.AutorLibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AutorLibroServices implements IAutorLibro {
    @Autowired
    private AutorLibroRepository autorLibroRepository;
    @Override
    public AutorLibro create(AutorLibro autorLibro) {
        return autorLibroRepository.save(autorLibro);
    }

    @Override
    public AutorLibro update(AutorLibro autorLibro) {
        return null;
    }

    @Override
    public AutorLibro findById(Integer idAutorLibro) {
        return null;
    }

    @Override
    public List<AutorLibro> findAll() {
        return null;
    }

    @Override
    public void delete(List<AutorLibro> autorLibros) {
        autorLibroRepository.deleteAll(autorLibros);
    }
    public List<AutorLibro> createList(List<AutorLibro> autoesLibro) {
        try {
            return autorLibroRepository.saveAll(autoesLibro);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
    public List<AutorLibro> listaAutores(Libro libro) {
        try {
            return autorLibroRepository.findByLibro(libro);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
}
