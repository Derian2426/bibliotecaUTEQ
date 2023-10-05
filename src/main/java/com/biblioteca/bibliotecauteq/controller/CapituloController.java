package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.Capitulo;
import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.service.AutorLibroServices;
import com.biblioteca.bibliotecauteq.service.CapituloService;
import com.biblioteca.bibliotecauteq.service.LibroServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.biblioteca.bibliotecauteq.file_configuration.Archivos.eliminarArchivo;
import static com.biblioteca.bibliotecauteq.file_configuration.Archivos.eliminarCarpeta;


@RestController
@RequestMapping("/capitulo")
@CrossOrigin
public class CapituloController {
    @Autowired
    private CapituloService capituloService;
    @Autowired
    private LibroServices libroServices;
    @Autowired
    private AutorLibroServices autorLibroServices;
    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<List<Capitulo>> listaCapitulo(@RequestBody Libro libro) {
        try {
            if (libro != null)
                return new ResponseEntity<>(capituloService.findByLibro(libro), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/eliminarLibro")
    public ResponseEntity<Libro> deleteCapitulo(@RequestBody Libro libro) {
        try {
            if (libro != null) {
                autorLibroServices.delete(autorLibroServices.listaAutores(libro));
                capituloService.delete(capituloService.findByLibroAll(libro));
                libroServices.delete(Math.toIntExact(libro.getIdLibro()));
                eliminarCarpeta(uploadDir + "/" + libro.getCarpetaLibro());
                eliminarArchivo(uploadDir + "/pdf/" + libro.getPdfLibro());
                eliminarArchivo(uploadDir + "/png/" + libro.getCoverImage());
                eliminarArchivo(uploadDir + "/jpg.png/" + libro.getCoverImage());
                return new ResponseEntity<>(libro, HttpStatus.OK);
            } else
                return new ResponseEntity<>(new Libro(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Libro(), HttpStatus.CONFLICT);
        }
    }
}
