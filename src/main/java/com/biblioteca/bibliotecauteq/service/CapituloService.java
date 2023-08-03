package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.ICapitulo;
import com.biblioteca.bibliotecauteq.model.Capitulo;
import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.repository.CapituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapituloService implements ICapitulo {
    @Autowired
    private CapituloRepository capituloRepository;
    @Override
    public Capitulo create(Capitulo capitulo) {
        return null;
    }
    public List<Capitulo> createList(List<Capitulo> capitulos) {
        return capituloRepository.saveAll(capitulos);
    }

    @Override
    public Capitulo update(Capitulo capitulo) {
        return null;
    }

    @Override
    public Capitulo findById(Capitulo capitulo) {
        return null;
    }

    @Override
    public List<Capitulo> findAll() {
        return capituloRepository.findAll();
    }

    @Override
    public void delete(Integer idCapitulo) {

    }
    public List<Capitulo> findByLibro(Libro libro) {
        Optional<List<Capitulo>> capitulo=capituloRepository.findByLibro(libro);
        return capitulo.orElse(null);
    }
}
