package com.biblioteca.bibliotecauteq.interfaces;

import com.biblioteca.bibliotecauteq.model.Autor;

import java.util.List;

public interface IAutor {
    Autor create(Autor autor);
    Autor update(Autor autor);
    Autor findById(Integer idAutor);
    List<Autor> findAll();
    void delete(Integer idAutor);
}
