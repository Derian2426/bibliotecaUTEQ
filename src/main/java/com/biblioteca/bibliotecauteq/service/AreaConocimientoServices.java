package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.interfaces.IAreaConocimiento;
import com.biblioteca.bibliotecauteq.model.AreaConocimiento;
import com.biblioteca.bibliotecauteq.repository.AreaConocimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaConocimientoServices implements IAreaConocimiento {
    @Autowired
    private AreaConocimientoRepository areaConocimientoRepository;

    @Override
    public AreaConocimiento create(AreaConocimiento areaConocimiento) {
        Optional<AreaConocimiento> existingArea = areaConocimientoRepository.findByNombreArea(areaConocimiento.getNombreArea());
        //if (existingArea.isPresent()) {
          //  return existingArea.get();
        //} else {
            try {
                return areaConocimientoRepository.save(areaConocimiento);
            } catch (Exception e) {
                return new AreaConocimiento();
            }
        //}
    }
    @Override
    public AreaConocimiento update(AreaConocimiento areaConocimiento) {
        try {
            return areaConocimientoRepository.save(areaConocimiento);
        }catch (Exception e){
            return new AreaConocimiento();
        }
    }

    @Override
    public AreaConocimiento findById(Long idArea) {
        Optional<AreaConocimiento> areaConocimiento=areaConocimientoRepository.findById(Math.toIntExact(idArea));
        return areaConocimiento.orElse(null);
    }
    @Override
    public List<AreaConocimiento> findAll() {
        return areaConocimientoRepository.findAll();
    }
    @Override
    public void delete(Integer idArea) {

    }
}
