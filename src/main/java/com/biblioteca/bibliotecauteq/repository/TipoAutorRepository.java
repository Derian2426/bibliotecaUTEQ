package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.TipoAutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoAutorRepository extends JpaRepository<TipoAutor, Integer> {
    TipoAutor findByTipoAutor(String tipoAutor);
}
