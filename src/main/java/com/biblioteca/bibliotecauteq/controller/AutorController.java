package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.Autor;
import com.biblioteca.bibliotecauteq.model.Usuario;
import com.biblioteca.bibliotecauteq.service.AutorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/autor")
@CrossOrigin
public class AutorController {
    @Autowired
    private AutorServices autorServices;

    @GetMapping
    public ResponseEntity<List<Autor>> listaAutor() {
        return new ResponseEntity<>(autorServices.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Autor> createAutor(@RequestBody Autor autor) {
        try {
            if (autor != null) {
                Autor autorVerificacion = autorServices.create(autor);
                if (autorVerificacion.getIdAutor() == null)
                    return new ResponseEntity<>(new Autor(-1, autor.getNombre(), autor.getApellido()), HttpStatus.CONFLICT);
                else
                    return new ResponseEntity<>(autorVerificacion, HttpStatus.OK);
            } else
                return new ResponseEntity<>(new Autor(), HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(new Autor(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Autor> updateAutor(@RequestBody Autor autor) {
        return new ResponseEntity<>(autorServices.update(autor), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> finById(@PathVariable("id") Integer idAutor) {
        Autor autor = autorServices.findById(idAutor);
        if (autor == null)
            return new ResponseEntity<>(new Autor(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(autor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAutor(@PathVariable("id") Integer idTipoAutor) {
        try {
            Autor autor = autorServices.findById(idTipoAutor);
            if (autor == null)
                return new ResponseEntity<>(new Usuario(), HttpStatus.CONFLICT);
            autorServices.delete(idTipoAutor);
            return new ResponseEntity<>(autor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Usuario(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/busquedaTipoAutor")
    public ResponseEntity<List<Autor>> busquedaAutor(@RequestBody Autor tipoAutor) {
        try {
            List<Autor> listaAutor = autorServices.buscarListaAutor(tipoAutor);
            if (listaAutor.size() > 0)
                return new ResponseEntity<>(listaAutor, HttpStatus.OK);
            else
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        }

    }
}
