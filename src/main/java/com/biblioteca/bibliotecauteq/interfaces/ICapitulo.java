package com.biblioteca.bibliotecauteq.interfaces;

import com.biblioteca.bibliotecauteq.model.Capitulo;

import java.util.List;

public interface ICapitulo {
    Capitulo create(Capitulo capitulo);
    Capitulo update(Capitulo capitulo);
    Capitulo findById(Capitulo capitulo);
    List<Capitulo> findAll();
    void delete(List<Capitulo> capitulos);
}
