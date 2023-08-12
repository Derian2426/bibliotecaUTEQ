package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.AreaConocimiento;
import com.biblioteca.bibliotecauteq.model.SubAreasConocimiento;
import com.biblioteca.bibliotecauteq.model.SubAreasEspecificas;
import com.biblioteca.bibliotecauteq.service.SubAreaConocimientoService;
import com.biblioteca.bibliotecauteq.service.SubAreasEspecificasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subAreaEspecificas")
@CrossOrigin(origins = "http://localhost:3000")
public class SubAreasEspecificasController {
    @Autowired
    private SubAreasEspecificasServices subAreasEspecificasServices;
    private SubAreasEspecificas conocimientoRespuesta;
    @GetMapping("/{id}")
    public List<SubAreasEspecificas> finById(@PathVariable("id") Integer idSubAreaEspecifica){
        List<SubAreasEspecificas> subAreasEspecificas= subAreasEspecificasServices.findById(idSubAreaEspecifica);
        return subAreasEspecificas;
    }
    @GetMapping
    public List<SubAreasEspecificas> finById(){
        List<SubAreasEspecificas> subAreasEspecificas= subAreasEspecificasServices.findAll();
        return subAreasEspecificas;
    }
    @PostMapping
    public ResponseEntity<SubAreasEspecificas> createSubAreasConocimiento(@RequestBody SubAreasEspecificas subAreasEspecificas){
        if (subAreasEspecificas!=null){
            conocimientoRespuesta=subAreasEspecificasServices.create(subAreasEspecificas);
            if (conocimientoRespuesta.getIdSubAreaEspecifica()!=null)
                return new ResponseEntity<>(conocimientoRespuesta, HttpStatus.OK);
            else
                return new ResponseEntity<>(new SubAreasEspecificas(-1L,subAreasEspecificas.getNombreSubAreaEspecifica(),
                        new SubAreasConocimiento()), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(new SubAreasEspecificas(-1L,subAreasEspecificas.getNombreSubAreaEspecifica(),
                    new SubAreasConocimiento()), HttpStatus.CONFLICT);
    }
}
