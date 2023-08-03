package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.SubAreasConocimiento;
import com.biblioteca.bibliotecauteq.service.SubAreaConocimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subAreaConocimiento")
@CrossOrigin(origins = "http://localhost:3000")
public class SubAreasConocimientoController {
    @Autowired
    private SubAreaConocimientoService subAreaConocimientoService;
    @GetMapping("/{id}")
    public List<SubAreasConocimiento> finById(@PathVariable("id") Integer idbArea){
       List<SubAreasConocimiento> subAreasConocimiento= subAreaConocimientoService.findById(idbArea);
        return subAreasConocimiento;
    }
}
