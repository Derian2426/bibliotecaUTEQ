package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.SubAreasConocimiento;
import com.biblioteca.bibliotecauteq.model.SubAreasEspecificas;
import com.biblioteca.bibliotecauteq.service.SubAreasEspecificasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/subAreaEspecificas")
@CrossOrigin(origins = "http://localhost:3000")
public class SubAreasEspecificasController {
    @Autowired
    private SubAreasEspecificasServices subAreasEspecificasServices;

    @GetMapping("/{id}")
    public ResponseEntity<List<SubAreasEspecificas>> finById(@PathVariable("id") Integer idSubAreaEspecifica) {
        try {
            return new ResponseEntity<>(subAreasEspecificasServices.findById(idSubAreaEspecifica), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<SubAreasEspecificas>> finAll() {
        try {
            return new ResponseEntity<>(subAreasEspecificasServices.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        }
    }
    @PostMapping
    public ResponseEntity<SubAreasEspecificas> createSubAreasEspecificas(@RequestBody SubAreasEspecificas subAreasEspecificas) {
        try {
            if (subAreasEspecificas != null && subAreasEspecificas.getSubAreasConocimiento() != null
                    && subAreasEspecificas.getSubAreasConocimiento().getAreaConocimiento() != null) {
                SubAreasEspecificas areasEspecificas = subAreasEspecificasServices.create(subAreasEspecificas);
                if (areasEspecificas.getIdSubAreaEspecifica() != null)
                    return new ResponseEntity<>(areasEspecificas, HttpStatus.OK);
                else
                    return new ResponseEntity<>(new SubAreasEspecificas(-1L, subAreasEspecificas.getNombreSubAreaEspecifica(),
                            new SubAreasConocimiento()), HttpStatus.CONFLICT);
            } else
                return new ResponseEntity<>(new SubAreasEspecificas(-1L, "Los datos enviados se encuentran inconpletos.",
                        new SubAreasConocimiento()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new SubAreasEspecificas(-1L, "Ocurrio un error.",
                    new SubAreasConocimiento()), HttpStatus.CONFLICT);
        }

    }
}
