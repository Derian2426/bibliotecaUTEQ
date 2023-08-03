package com.biblioteca.bibliotecauteq.interfaces;

import com.biblioteca.bibliotecauteq.model.SubAreasEspecificas;

import java.util.List;

public interface ISubAreasEspecificas {
    SubAreasEspecificas create(SubAreasEspecificas subAreasEspecificas);
    SubAreasEspecificas update(SubAreasEspecificas subAreasEspecificas);
    List<SubAreasEspecificas> findById(Integer idSubAreasEspecificas);
    List<SubAreasEspecificas> findAll();
    void delete(Integer idSubAreasEspecificas);
}
