package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.SubAreasConocimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubAreaConocimientoRepository extends JpaRepository<SubAreasConocimiento, Integer> {
    Optional<SubAreasConocimiento> findByNombreSubArea(String nombreSubArea);
    List<SubAreasConocimiento> findByAreaConocimiento_IdArea(int idSubArea);
}
