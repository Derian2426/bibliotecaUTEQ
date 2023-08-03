package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.SubAreasEspecificas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubAreasEspecificasRepository extends JpaRepository<SubAreasEspecificas, Integer> {
    Optional<SubAreasEspecificas> findByNombreSubAreaEspecifica(String nombreSubAreaEspecifica);
    List<SubAreasEspecificas> findBySubAreasConocimiento_IdSubArea(int idSubAreaEspecifica);
}
