package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.AreaConocimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaConocimientoRepository extends JpaRepository<AreaConocimiento, Integer> {
    Optional<AreaConocimiento> findByNombreArea(String nombreArea);
}
