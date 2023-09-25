package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.InformacionPeticion;
import com.biblioteca.bibliotecauteq.model.LibroRequest;
import com.biblioteca.bibliotecauteq.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/libro")
@CrossOrigin
public class LibroAccionesController {
    @Autowired
    private LibroServices libroServices;
    @Autowired
    private AreaConocimientoServices areaConocimientoServices;
    @Autowired
    private SubAreaConocimientoService subAreaConocimientoService;
    @Autowired
    private SubAreasEspecificasServices subAreasEspecificasServices;
    @Autowired
    private CapituloService capituloService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AutorLibroServices autorLibroServices;
    @Value("${upload.dir}")
    private String uploadDir;
    @PostMapping
    public InformacionPeticion uploadAudio(@RequestParam("file") List<MultipartFile> files, @RequestParam("libroRequest") String bookRequest) {
        LibroRequest libroRequest = mapearLibro(bookRequest);
        Path folder = Paths.get(uploadDir+"/"+ libroRequest.getLibro().getNombreLibro().trim());
        try {

            return new InformacionPeticion(1, "Archivos de audio subidos con éxito.", "Éxito");
        } catch (Exception e) {
            return new InformacionPeticion(-1, "Error al subir los archivos de audio.", "Error");
        }
    }
    private LibroRequest mapearLibro(String libroString){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LibroRequest libroMapeo = objectMapper.readValue(libroString, LibroRequest.class);
            libroMapeo.getLibro().getSubAreasEspecificas().getSubAreasConocimiento()
                    .setAreaConocimiento(areaConocimientoServices
                            .findById(libroMapeo.getLibro()
                                    .getSubAreasEspecificas()
                                    .getSubAreasConocimiento()
                                    .getAreaConocimiento()
                                    .getIdArea()));
            libroMapeo.getLibro().getSubAreasEspecificas().setSubAreasConocimiento(subAreaConocimientoService
                    .findByIdArea(libroMapeo.getLibro()
                            .getSubAreasEspecificas()
                            .getSubAreasConocimiento()
                            .getIdSubArea()));
            libroMapeo.getLibro().setSubAreasEspecificas(subAreasEspecificasServices
                    .findByIdAreaEspecifica(libroMapeo.getLibro()
                            .getSubAreasEspecificas().getIdSubAreaEspecifica()));
            return libroMapeo;
        }catch (IOException e){
            return new LibroRequest();
        }
    }
}
