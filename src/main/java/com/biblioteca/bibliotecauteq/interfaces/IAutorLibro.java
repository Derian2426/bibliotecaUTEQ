package com.biblioteca.bibliotecauteq.interfaces;

import com.biblioteca.bibliotecauteq.model.AutorLibro;

import java.util.List;

public interface IAutorLibro {
    AutorLibro create(AutorLibro autorLibro);
    AutorLibro update(AutorLibro autorLibro);
    AutorLibro findById(Integer idAutorLibro);
    List<AutorLibro> findAll();
    void delete(Integer idAutorLibro);
}
