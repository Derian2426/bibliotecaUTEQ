package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.ICapitulo;
import com.biblioteca.bibliotecauteq.model.Capitulo;
import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.repository.CapituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CapituloService implements ICapitulo {
    @Autowired
    private CapituloRepository capituloRepository;

    @Override
    public Capitulo create(Capitulo capitulo) {
        return null;
    }

    public List<Capitulo> createList(List<Capitulo> capitulos) {
        try {
            return capituloRepository.saveAll(capitulos);
        } catch (Exception e) {
            return new ArrayList<>();
        }
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
    public void delete(List<Capitulo> capitulos) {
        capituloRepository.deleteAll(capitulos);
    }

    public void deleteCapitulo(Capitulo capitulos) {
        capituloRepository.delete(capitulos);
    }

    public List<Capitulo> findByLibro(Libro libro) {
        try {
            return capituloRepository.findByLibro(libro).map(capitulos -> {
                for (Capitulo capitulo : capitulos) {
                    capitulo.getUsuario().setPassword("");
                }
                return capitulos;
            }).orElse(new ArrayList<>());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Capitulo> findByLibroAll(Libro libro) {
        try {
            return capituloRepository.findByLibro(libro).orElse(new ArrayList<>());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}