package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.ISubAreasEspecificas;
import com.biblioteca.bibliotecauteq.model.SubAreasEspecificas;
import com.biblioteca.bibliotecauteq.repository.SubAreasEspecificasRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubAreasEspecificasServices implements ISubAreasEspecificas {
    @Autowired
    private SubAreasEspecificasRepository subAreasEspecificasRepository;

    @Override
    @Transactional
    public SubAreasEspecificas create(SubAreasEspecificas subAreasEspecificas) {
        Optional<SubAreasEspecificas> existingArea = subAreasEspecificasRepository.findByNombreSubAreaEspecifica(subAreasEspecificas.getNombreSubAreaEspecifica());
        if (existingArea.isPresent()) {
            return new SubAreasEspecificas();
        } else {
            try {
                return subAreasEspecificasRepository.save(subAreasEspecificas);
            } catch (Exception e) {
                return new SubAreasEspecificas();
            }
        }
    }

    @Override
    public SubAreasEspecificas update(SubAreasEspecificas subAreasEspecificas) {
        return null;
    }

    @Override
    public List<SubAreasEspecificas> findById(Integer idSubAreasEspecificas) {
        try {
            return subAreasEspecificasRepository.findBySubAreasConocimiento_IdSubArea(idSubAreasEspecificas);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public SubAreasEspecificas findByIdAreaEspecifica(Long idAreaEspecifica) {
        Optional<SubAreasEspecificas> areasEspecificas = subAreasEspecificasRepository.findById(Math.toIntExact(idAreaEspecifica));
        return areasEspecificas.orElse(null);
    }

    @Override
    public List<SubAreasEspecificas> findAll() {
        return subAreasEspecificasRepository.findAll();
    }

    @Override
    public void delete(Integer idSubAreasEspecificas) {

    }
}
