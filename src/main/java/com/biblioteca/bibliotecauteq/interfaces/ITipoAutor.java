package com.biblioteca.bibliotecauteq.interfaces;

import com.biblioteca.bibliotecauteq.model.TipoAutor;

import java.util.List;

public interface ITipoAutor {
    TipoAutor create(TipoAutor tipoAutor);
    TipoAutor update(TipoAutor tipoAutor);
    TipoAutor findById(Integer idTipoAutor);
    List<TipoAutor> findAll();
    void delete(Integer idTipoAutor);
}
