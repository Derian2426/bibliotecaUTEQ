package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.IAutor;
import com.biblioteca.bibliotecauteq.model.Autor;
import com.biblioteca.bibliotecauteq.model.TipoAutor;
import com.biblioteca.bibliotecauteq.repository.AutorRepository;
import com.biblioteca.bibliotecauteq.repository.TipoAutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServices implements IAutor {
    @Autowired
    private AutorRepository autorRepository;
    @Override
    public Autor create(Autor autor) {
        if(buscarAutor(autor)&&buscarApellido(autor))
            return new Autor();
        return autorRepository.save(autor);
    }
    public boolean buscarAutor(Autor autor) {
        List<Autor> lista= autorRepository.findByNombre(autor.getNombre());
        return lista.size()>0;
    }
    public List<Autor> buscarListaAutor(Autor autor) {
        return autorRepository.findByNombre(autor.getNombre());
    }
    public boolean buscarApellido(Autor autor) {
        List<Autor> lista=autorRepository.findByApellido(autor.getApellido());
        return lista.size()>0;
    }
    @Override
    public Autor update(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    public Autor findById(Integer idAutor) {
        Optional<Autor> autor=autorRepository.findById(idAutor);
        return autor.orElse(null);
    }

    @Override
    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    @Override
    public void delete(Integer idAutor) {
        autorRepository.deleteById(idAutor);
    }
}
