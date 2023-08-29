package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.ISubAreasConocimiento;
import com.biblioteca.bibliotecauteq.model.SubAreasConocimiento;
import com.biblioteca.bibliotecauteq.repository.SubAreaConocimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubAreaConocimientoService implements ISubAreasConocimiento {
    @Autowired
    private SubAreaConocimientoRepository subAreaConocimientoRepository;

    @Override
    public SubAreasConocimiento create(SubAreasConocimiento subAreasConocimiento) {
        Optional<SubAreasConocimiento> existingArea = subAreaConocimientoRepository.findByNombreSubArea(subAreasConocimiento.getNombreSubArea());
        if (existingArea.isPresent()) {
            return new SubAreasConocimiento();
        } else {
            try {
                return subAreaConocimientoRepository.save(subAreasConocimiento);
            } catch (Exception e) {
                return new SubAreasConocimiento();
            }
        }
    }

    @Override
    public SubAreasConocimiento update(SubAreasConocimiento subAreasConocimiento) {
        return null;
    }

    @Override
    public List<SubAreasConocimiento> findById(Integer idAreasConocimiento) {
        try {
            return subAreaConocimientoRepository.findByAreaConocimiento_IdArea(idAreasConocimiento);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public SubAreasConocimiento findByIdArea(Long idArea) {
        Optional<SubAreasConocimiento> areaConocimiento = subAreaConocimientoRepository.findById(Math.toIntExact(idArea));
        return areaConocimiento.orElse(null);
    }

    @Override
    public List<SubAreasConocimiento> findAll() {
        try {
            return subAreaConocimientoRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(Integer idSubAreasConocimiento) {

    }
}
