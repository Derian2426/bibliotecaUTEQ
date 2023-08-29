package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.TipoAutor;
import com.biblioteca.bibliotecauteq.model.Usuario;
import com.biblioteca.bibliotecauteq.service.TipoAutorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tipoAutor")
@CrossOrigin(origins = "http://localhost:3000")
public class TipoAutorController {
    @Autowired
    private TipoAutorServices tipoAutorServices;

    @GetMapping
    public ResponseEntity<List<TipoAutor>> listaTipoAutor() {
        try {
            return new ResponseEntity<>(tipoAutorServices.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<TipoAutor> createTipoAutor(@RequestBody TipoAutor tipoAutor) {
        try {
            TipoAutor autor = tipoAutorServices.create(tipoAutor);
            if (autor.getIdAutor() == null)
                return new ResponseEntity<>(new TipoAutor(-1, tipoAutor.getTipoAutor()), HttpStatus.CONFLICT);
            else
                return new ResponseEntity<>(autor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TipoAutor(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<TipoAutor> updateTipoAutor(@RequestBody TipoAutor tipoAutor) {
        return new ResponseEntity<>(tipoAutorServices.update(tipoAutor), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAutor> finById(@PathVariable("id") Integer idTipoAutor) {
        try {
            TipoAutor tipoAutor = tipoAutorServices.findById(idTipoAutor);
            if (tipoAutor == null)
                return new ResponseEntity<>(new TipoAutor(), HttpStatus.CONFLICT);
            return new ResponseEntity<>(tipoAutor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TipoAutor(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTipoAutor(@PathVariable("id") Integer idTipoAutor) throws Exception {
        TipoAutor tipoAutor = tipoAutorServices.findById(idTipoAutor);
        if (tipoAutor == null)
            return new ResponseEntity<>(new Usuario(), HttpStatus.CONFLICT);
        tipoAutorServices.delete(idTipoAutor);
        return new ResponseEntity<>(tipoAutor, HttpStatus.OK);
    }

    @PostMapping("/busquedaTipoAutor")
    public ResponseEntity<TipoAutor> busquedaTipoAutor(@RequestBody TipoAutor tipoAutor) {
        try {
            TipoAutor autor = tipoAutorServices.buscarTipoAutor(tipoAutor);
            if (autor == null)
                return new ResponseEntity<>(new TipoAutor(), HttpStatus.CONFLICT);
            else
                return new ResponseEntity<>(autor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TipoAutor(), HttpStatus.CONFLICT);
        }
    }
}
