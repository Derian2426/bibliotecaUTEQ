package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.IAutor;
import com.biblioteca.bibliotecauteq.model.Autor;
import com.biblioteca.bibliotecauteq.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorServices implements IAutor {
    @Autowired
    private AutorRepository autorRepository;

    @Override
    public Autor create(Autor autor) {
        try {
            if (buscarAutor(autor) && buscarApellido(autor))
                return new Autor();
            return autorRepository.save(autor);
        } catch (Exception e) {
            return new Autor();
        }
    }

    private boolean buscarAutor(Autor autor) {
        List<Autor> lista = autorRepository.findByNombre(autor.getNombre());
        return lista.size() > 0;
    }

    public List<Autor> buscarListaAutor(Autor autor) {
        try {
            if (autor != null)
                return autorRepository.findByNombre(autor.getNombre());
            return new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private boolean buscarApellido(Autor autor) {
        List<Autor> lista = autorRepository.findByApellido(autor.getApellido());
        return lista.size() > 0;
    }

    @Override
    public Autor update(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    public Autor findById(Integer idAutor) {
        try {
            Optional<Autor> autor = autorRepository.findById(idAutor);
            return autor.orElse(null);
        } catch (Exception e) {
            return new Autor();
        }
    }

    @Override
    public List<Autor> findAll() {
        try {
            return autorRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(Integer idAutor) {
        autorRepository.deleteById(idAutor);
    }
}
