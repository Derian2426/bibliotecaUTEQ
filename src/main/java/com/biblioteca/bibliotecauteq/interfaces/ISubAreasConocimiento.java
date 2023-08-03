package com.biblioteca.bibliotecauteq.interfaces;

import com.biblioteca.bibliotecauteq.model.SubAreasConocimiento;

import java.util.List;
import java.util.Optional;

public interface ISubAreasConocimiento {
    SubAreasConocimiento create(SubAreasConocimiento subAreasConocimiento);
    SubAreasConocimiento update(SubAreasConocimiento subAreasConocimiento);
    List<SubAreasConocimiento> findById(Integer idAreasConocimiento);
    List<SubAreasConocimiento> findAll();
    void delete(Integer idSubAreasConocimiento);
}
