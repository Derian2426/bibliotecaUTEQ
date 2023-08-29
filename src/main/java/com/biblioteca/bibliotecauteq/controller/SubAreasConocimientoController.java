package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.AreaConocimiento;
import com.biblioteca.bibliotecauteq.model.SubAreasConocimiento;
import com.biblioteca.bibliotecauteq.service.SubAreaConocimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subAreaConocimiento")
@CrossOrigin(origins = "http://localhost:3000")
public class SubAreasConocimientoController {
    @Autowired
    private SubAreaConocimientoService subAreaConocimientoService;

    @GetMapping("/{id}")
    public ResponseEntity<List<SubAreasConocimiento>> finById(@PathVariable("id") Integer idbArea) {
        return new ResponseEntity<>(subAreaConocimientoService.findById(idbArea), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SubAreasConocimiento>> finAll() {
        return new ResponseEntity<>(subAreaConocimientoService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubAreasConocimiento> createSubAreasConocimiento(@RequestBody SubAreasConocimiento subAreasConocimiento) {
        if (subAreasConocimiento != null) {
            SubAreasConocimiento conocimientoRespuesta = subAreaConocimientoService.create(subAreasConocimiento);
            if (conocimientoRespuesta.getIdSubArea() != null)
                return new ResponseEntity<>(conocimientoRespuesta, HttpStatus.OK);
            else
                return new ResponseEntity<>(new SubAreasConocimiento(-1L, subAreasConocimiento.getNombreSubArea(), new AreaConocimiento()), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new SubAreasConocimiento(-1L, "Los datos enviados se encuentran inconpletos.", new AreaConocimiento()), HttpStatus.CONFLICT);
    }
}
