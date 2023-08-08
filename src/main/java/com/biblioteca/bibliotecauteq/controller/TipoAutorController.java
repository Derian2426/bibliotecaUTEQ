package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.TipoAutor;
import com.biblioteca.bibliotecauteq.model.Usuario;
import com.biblioteca.bibliotecauteq.service.TipoAutorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoAutor")
public class TipoAutorController {
    @Autowired
    private TipoAutorServices tipoAutorServices;
    @GetMapping
    public ResponseEntity<List<TipoAutor>> listaTipoAutor(){
        return new ResponseEntity<>(tipoAutorServices.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TipoAutor> createTipoAutor(@RequestBody TipoAutor tipoAutor){
        TipoAutor autor=tipoAutorServices.create(tipoAutor);
        if (autor!=null)
            return new ResponseEntity<>(autor, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(new TipoAutor(), HttpStatus.CONFLICT);
    }
    @PutMapping
    public  ResponseEntity<TipoAutor> updateTipoAutor(@RequestBody TipoAutor tipoAutor){
        return new ResponseEntity<>(tipoAutorServices.update(tipoAutor), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TipoAutor> finById(@PathVariable("id") Integer idTipoAutor){
        TipoAutor tipoAutor= tipoAutorServices.findById(idTipoAutor);
        if (tipoAutor==null)
            return new ResponseEntity<>(new TipoAutor(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tipoAutor,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTipoAutor(@PathVariable("id") Integer idTipoAutor) throws Exception {
        TipoAutor tipoAutor= tipoAutorServices.findById(idTipoAutor);
        if (tipoAutor==null)
            return new ResponseEntity<>(new Usuario(), HttpStatus.NOT_FOUND);
        tipoAutorServices.delete(idTipoAutor);
        return new ResponseEntity<>(tipoAutor,HttpStatus.OK);
    }
    @PostMapping("/busquedaTipoAutor")
    public  ResponseEntity<TipoAutor> busquedaTipoAutor(@RequestBody TipoAutor tipoAutor){
        TipoAutor autor=tipoAutorServices.buscarTipoAutor(tipoAutor);
        if(autor==null)
            return new ResponseEntity<>(new TipoAutor(), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(autor, HttpStatus.OK);
    }
}
