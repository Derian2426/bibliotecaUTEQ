package com.biblioteca.bibliotecauteq.interfaces;

import com.biblioteca.bibliotecauteq.model.AreaConocimiento;

import java.util.List;

public interface IAreaConocimiento {
    AreaConocimiento create(AreaConocimiento areaConocimiento);
    AreaConocimiento update(AreaConocimiento areaConocimiento);
    AreaConocimiento findById(Long idArea);
    List<AreaConocimiento> findAll();
    void delete(Integer idArea);
}
