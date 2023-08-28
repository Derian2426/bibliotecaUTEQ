package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.AreaConocimiento;
import com.biblioteca.bibliotecauteq.service.AreaConocimientoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areaConocimiento")
@CrossOrigin(origins = "http://localhost:3000")
public class AreaConocimientoController {
    @Autowired
    private AreaConocimientoServices areaConocimientoServices;
    private AreaConocimiento conocimientoRespuesta;
    @PostMapping
    public ResponseEntity<AreaConocimiento> createAreaConocimiento(@RequestBody AreaConocimiento areaConocimiento){
        if (areaConocimiento!=null){
            conocimientoRespuesta=areaConocimientoServices.create(areaConocimiento);
            if (conocimientoRespuesta.getIdArea()!=null)
                return new ResponseEntity<>(conocimientoRespuesta, HttpStatus.OK);
            else
                return new ResponseEntity<>(new AreaConocimiento(-1L,areaConocimiento.getNombreArea()), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(new AreaConocimiento(-1L,areaConocimiento.getNombreArea()), HttpStatus.CONFLICT);
    }
    @PostMapping("/editar")
    public ResponseEntity<AreaConocimiento> updateAreaConocimiento(@RequestBody AreaConocimiento areaConocimiento){
        if (areaConocimiento!=null){
            conocimientoRespuesta=areaConocimientoServices.update(areaConocimiento);
            if (conocimientoRespuesta.getIdArea()!=null)
                return new ResponseEntity<>(conocimientoRespuesta, HttpStatus.OK);
            else
                return new ResponseEntity<>(new AreaConocimiento(-1L,areaConocimiento.getNombreArea()), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(new AreaConocimiento(-1L,areaConocimiento.getNombreArea()), HttpStatus.CONFLICT);
    }
    @GetMapping
    public ResponseEntity<List<AreaConocimiento>> listaAreaConocimiento(){
        return new ResponseEntity<>(areaConocimientoServices.findAll(), HttpStatus.OK);
    }
}
