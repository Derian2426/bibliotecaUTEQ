package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.Busqueda;
import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.service.LibroServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libro")
@CrossOrigin
public class LibroController {
    @Autowired
    private LibroServices libroServices;

    @GetMapping
    public ResponseEntity<List<Libro>> listaLibro() {
        try {
            return new ResponseEntity<>(libroServices.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Libro>> libroId(@PathVariable("id") Integer idLibro) {
        try {
            return new ResponseEntity<>(libroServices.findById(idLibro), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Optional.of(new Libro()), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<List<Libro>> libros(@RequestBody Busqueda busqueda) {
        try {
            if (busqueda == null)
                return new ResponseEntity<>(libroServices.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(libroServices.findLibro(busqueda), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(libroServices.findAll(), HttpStatus.OK);
        }
    }
    @PostMapping("/editar")
    public ResponseEntity<Libro> libroEdit(@RequestBody Libro libro) {
        try {
            if (libro == null)
                return new ResponseEntity<>(new Libro() , HttpStatus.NOT_MODIFIED);
            else
                return new ResponseEntity<>(libroServices.update(libro), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Libro() , HttpStatus.NOT_MODIFIED);
        }
    }
}
