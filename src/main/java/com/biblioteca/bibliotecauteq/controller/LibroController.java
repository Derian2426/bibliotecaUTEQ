package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.service.LibroServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libro")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class LibroController {
    @Autowired
    private LibroServices libroServices;

    @GetMapping
    public ResponseEntity<List<Libro>> listaLibro(){
        return new ResponseEntity<>(libroServices.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Libro>> libroId(@PathVariable("id") Integer idLibro){
        return new ResponseEntity<>(libroServices.findById(idLibro), HttpStatus.OK);
    }
}
