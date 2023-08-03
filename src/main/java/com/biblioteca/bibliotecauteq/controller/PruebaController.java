package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.*;
import com.biblioteca.bibliotecauteq.service.AreaConocimientoServices;
import com.biblioteca.bibliotecauteq.service.LibroServices;
import com.biblioteca.bibliotecauteq.service.SubAreaConocimientoService;
import com.biblioteca.bibliotecauteq.service.SubAreasEspecificasServices;
import com.mpatric.mp3agic.Mp3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.Arrays;

@RestController
@RequestMapping("/uploadAudio")
@CrossOrigin(origins = "http://localhost:3000")
public class PruebaController {
    @Autowired
    private LibroServices libroServices;
    @Autowired
    private AreaConocimientoServices areaConocimientoServices;
    //@Autowired
    //private SubAreaConocimientoService subAreaConocimientoService;
    @Autowired
    private SubAreasEspecificasServices subAreasEspecificasServices;
    private Libro libro= new Libro();
    private AreaConocimiento areaConocimiento = new AreaConocimiento();
    private SubAreasConocimiento subAreasConocimiento= new SubAreasConocimiento();
    private SubAreasEspecificas subAreasEspecificas= new SubAreasEspecificas();
    @Value("${upload.dir}")
    private String uploadDir;
    @PostMapping("/guardar")
    @Transactional
    public InformacionPeticion uploadAudio(@RequestBody LibroRequest libroRequest) {
        try {
            //String archivoBase64=libroRequest.getFileLibro().toString().split(",")[1];
            //byte[] fileBytes = Base64.getDecoder().decode(archivoBase64);
            //MultipartFile file = new MockMultipartFile(libroRequest.getLibro().getNombreLibro(), fileBytes);
            //String archivo=libroRequest.getFileLibro().toString().split(",")[0];
            //System.out.println( ""+archivo);
            //if(!isMP3(fileBytes))
            // return new ErrorData(-1,"Solo se permiten archivos con extensión .pdf","Archivo incorrecto");
            //uploadDir+="/"+"carpeta"+libroRequest.getLibro().getNombreLibro();
            //Path carpeta = Paths.get(uploadDir);

            //if(!crearCarpetaLibro(carpeta))
            //  return new ErrorData(-1,"La carpeta ya se existe en el servidor","Carpeta existente");
            //if (guadarAudiosRepositorio(file)){
            //      Path filePath = Path.of(uploadDir, libroRequest.getLibro().getNombreLibro()+".pdf");
            //      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            //file.transferTo(filePath.toFile());
            //}


            areaConocimiento=areaConocimiento.areaConocimiento(libroRequest);
            areaConocimiento=areaConocimientoServices.create(areaConocimiento);
            subAreasConocimiento=subAreasConocimiento.subAreasConocimiento(libroRequest);
            subAreasConocimiento.setAreaConocimiento(areaConocimiento);
            //subAreasConocimiento=subAreaConocimientoService.create(subAreasConocimiento);
            subAreasEspecificas=subAreasEspecificas.subAreasEspecificas(libroRequest);
            subAreasEspecificas.setSubAreasConocimiento(subAreasConocimiento);
            subAreasEspecificas=subAreasEspecificasServices.create(subAreasEspecificas);
            libro=libro.libro(libroRequest);
            libro.setSubAreasEspecificas(subAreasEspecificas);
            libro=libroServices.create(libro);
            return new InformacionPeticion(1,"","");
        } catch (Exception e) {
            // Lanzar una excepción para revertir la transacción
            throw new RuntimeException("Error al registrar el libro y los capitulos", e);
        }
    }
    public boolean isMP3(byte[] fileBytes) {
        try {
            // Crea una instancia de Mp3File con los bytes del archivo
            Mp3File mp3File = new Mp3File(Arrays.toString(fileBytes));

            // Verifica si la instancia es válida y si tiene una etiqueta ID3
            return mp3File.hasId3v1Tag() || mp3File.hasId3v2Tag();
        } catch (Exception e) {
            // Ocurrió una excepción al procesar el archivo MP3
            return false;
        }
    }
    private boolean verificarTipoArchivo(MultipartFile file) {
        try {
            return !file.getOriginalFilename().toLowerCase().endsWith(".pdf")?false:true;
        } catch (Exception e) {
            return false;
        }
    }
    private boolean crearCarpetaLibro(Path carpeta){
        try {
            Files.createDirectories(carpeta);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    private boolean guadarAudiosRepositorio(MultipartFile files){
        try{
                Path filePath = Path.of(uploadDir, files.getOriginalFilename());
                if (Files.exists(filePath))
                    return false;
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
