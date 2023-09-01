package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.Capitulo;
import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.service.CapituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/capitulo")
@CrossOrigin
public class CapituloController {
    @Autowired
    private CapituloService capituloService;

    @PostMapping
    public ResponseEntity<List<Capitulo>> listaCapitulo(@RequestBody Libro libro) {
        try {
            if (libro != null)
                return new ResponseEntity<>(capituloService.findByLibro(libro), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        }
    }
}
