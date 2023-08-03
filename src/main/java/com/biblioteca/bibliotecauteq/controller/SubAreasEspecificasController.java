package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.SubAreasConocimiento;
import com.biblioteca.bibliotecauteq.model.SubAreasEspecificas;
import com.biblioteca.bibliotecauteq.service.SubAreaConocimientoService;
import com.biblioteca.bibliotecauteq.service.SubAreasEspecificasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subAreaEspecificas")
@CrossOrigin(origins = "http://localhost:3000")
public class SubAreasEspecificasController {
    @Autowired
    private SubAreasEspecificasServices subAreasEspecificasServices;
    @GetMapping("/{id}")
    public List<SubAreasEspecificas> finById(@PathVariable("id") Integer idSubAreaEspecifica){
        List<SubAreasEspecificas> subAreasEspecificas= subAreasEspecificasServices.findById(idSubAreaEspecifica);
        return subAreasEspecificas;
    }
}
