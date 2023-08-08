package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.Autor;
import com.biblioteca.bibliotecauteq.model.Usuario;
import com.biblioteca.bibliotecauteq.service.AutorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {
    @Autowired
    private AutorServices autorServices;
    @GetMapping
    public ResponseEntity<List<Autor>> listaAutor(){
        return new ResponseEntity<>(autorServices.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Autor> createAutor(@RequestBody Autor autor){
        Autor autorVerificacion=autorServices.create(autor);
        if (autorVerificacion!=null)
            return new ResponseEntity<>(autorVerificacion, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(new Autor(), HttpStatus.CONFLICT);
    }
    @PutMapping
    public  ResponseEntity<Autor> updateAutor(@RequestBody Autor autor){
        return new ResponseEntity<>(autorServices.update(autor), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Autor> finById(@PathVariable("id") Integer idAutor){
        Autor autor= autorServices.findById(idAutor);
        if (autor==null)
            return new ResponseEntity<>(new Autor(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(autor,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAutor(@PathVariable("id") Integer idTipoAutor) throws Exception {
        Autor autor= autorServices.findById(idTipoAutor);
        if (autor==null)
            return new ResponseEntity<>(new Usuario(), HttpStatus.NOT_FOUND);
        autorServices.delete(idTipoAutor);
        return new ResponseEntity<>(autor,HttpStatus.OK);
    }
    @PostMapping("/busquedaTipoAutor")
    public  ResponseEntity<Autor> busquedaAutor(@RequestBody Autor tipoAutor){
        Autor autor=autorServices.buscarAutor(tipoAutor);
        if(autor==null)
            return new ResponseEntity<>(new Autor(), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(autor, HttpStatus.OK);
    }
}
