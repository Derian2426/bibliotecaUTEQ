package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.ITipoAutor;
import com.biblioteca.bibliotecauteq.model.TipoAutor;
import com.biblioteca.bibliotecauteq.model.Usuario;
import com.biblioteca.bibliotecauteq.repository.TipoAutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoAutorServices implements ITipoAutor {
    @Autowired
    private TipoAutorRepository tipoAutorRepository;
    @Override
    public TipoAutor create(TipoAutor tipoAutor) {
        if(buscarTipoAutor(tipoAutor)==null)
            return tipoAutorRepository.save(tipoAutor);
        return new TipoAutor();
    }
    public TipoAutor buscarTipoAutor(TipoAutor tipoAutor) {
        return tipoAutorRepository.findByTipoAutor(tipoAutor.getTipoAutor());
    }
    @Override
    public TipoAutor update(TipoAutor tipoAutor) {
        return tipoAutorRepository.save(tipoAutor);
    }

    @Override
    public TipoAutor findById(Integer idTipoAutor) {
        Optional<TipoAutor> tipoAutor=tipoAutorRepository.findById(idTipoAutor);
        return tipoAutor.orElse(null);
    }

    @Override
    public List<TipoAutor> findAll() {
        return tipoAutorRepository.findAll();
    }

    @Override
    public void delete(Integer idTipoAutor) {
        tipoAutorRepository.deleteById(idTipoAutor);
    }
}
