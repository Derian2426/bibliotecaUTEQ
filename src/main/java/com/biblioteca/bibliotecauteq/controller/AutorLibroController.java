package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.AutorLibro;
import com.biblioteca.bibliotecauteq.model.Capitulo;
import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.service.AutorLibroServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autoresLibro")
@CrossOrigin
public class AutorLibroController {
    @Autowired
    private AutorLibroServices autorLibroServices;
    @PostMapping
    public ResponseEntity<List<AutorLibro>> listaCapitulo(@RequestBody Libro libro){
        return new ResponseEntity<>(autorLibroServices.listaAutores(libro), HttpStatus.OK);
    }
}
