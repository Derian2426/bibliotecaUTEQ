package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.*;
import com.biblioteca.bibliotecauteq.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:3000")
public class AudioUploadController {
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
    @Value("${upload.dir}")
    private String uploadDir;
    private Libro libro= new Libro();
    private AreaConocimiento areaConocimiento = new AreaConocimiento();
    private SubAreasConocimiento subAreasConocimiento= new SubAreasConocimiento();
    private SubAreasEspecificas subAreasEspecificas= new SubAreasEspecificas();
    @PostMapping
    public InformacionPeticion uploadAudio(@RequestParam("file") List<MultipartFile> files, @RequestParam("libroRequest") String libro) {
        try {
            LibroRequest libroRequest = mapearLibro(libro);
            if(libroServices.busquedaLibro(libroRequest.getLibro().getNombreLibro()))
                return new InformacionPeticion(-1, "El libro ya se encuentra registrado.", "Error");
            if(!seleccionarArchivos(files,"pdf"))
                return new InformacionPeticion(-1, "No se encuentra el PDF del libro.", "Error");
            if(!seleccionarArchivos(files,"jpg")&&!seleccionarArchivos(files,"png"))
                return new InformacionPeticion(-1, "No se encuentra la portada del libro.", "Error");
            if (!verificarTipoArchivo(files))
                return new InformacionPeticion(-1, "Solo se permiten archivos con extensión .mp3", "Archivo incorrecto");
            Path carpeta = Paths.get(uploadDir+"/"+ libroRequest.getLibro().getNombreLibro());
            if (!crearCarpetaLibro(carpeta))
                return new InformacionPeticion(-1, "La carpeta ya existe en el servidor", "Carpeta existente");
            for (MultipartFile file : files) {
                Path filePath = Path.of(carpeta.toString(), file.getOriginalFilename());
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                String contentType = file.getContentType();
                int indice=posicionArchivo(libroRequest.getCapituloFileList(),file.getOriginalFilename().replace("."+contentType.substring(contentType.lastIndexOf("/") + 1).toLowerCase(), ""));
                Capitulo capitulo=libroRequest.getCapituloFileList().get(indice);
                capitulo.setNombreArchivo(file.getOriginalFilename());
                capitulo.setRutaArchivo(libroRequest.getLibro().getNombreLibro());
                libroRequest.getCapituloFileList().set(indice,capitulo);
            }
            this.libro=libroServices.create(libroRequest.getLibro());
            if(this.libro==null)
                return new InformacionPeticion(-1, "El libro ya se encuentra registrado.", "Error");
            capituloService.createList(capitulosLibro(libroRequest.getCapituloFileList(),this.libro));
            return new InformacionPeticion(1, "Archivos de audio subidos con éxito.", "Éxito");
        } catch (IOException e) {
            return new InformacionPeticion(-1, "Error al subir los archivos de audio.", "Error");
        }
    }
    private Boolean seleccionarArchivos(List<MultipartFile> files,String extencion) throws IOException {
        boolean verifica=false;
        Path carpeta = Paths.get(uploadDir+"/"+ extencion);
        crearCarpetaLibro(carpeta);
        for (MultipartFile file : files) {
            if (file.getOriginalFilename().toLowerCase().endsWith("."+extencion)) {
                Path filePath = Path.of(carpeta.toString(), file.getOriginalFilename());
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                files.remove(file);
                verifica= true;
                break;
            }
        }
        return verifica;
    }
    private List<Capitulo> capitulosLibro(List<Capitulo>listaCapitulos,Libro libro){
        for (int i = 0; i < listaCapitulos.size(); i++) {
            Capitulo capitulo = listaCapitulos.get(i);
            capitulo.setLibro(libro);
        }
        return listaCapitulos;
    }
    private int posicionArchivo(List<Capitulo>listaCapitulos,String elementoCapitulo){
        int posicion=-1;
        try {
            for (int i = 0; i < listaCapitulos.size(); i++) {
                Capitulo capitulo = listaCapitulos.get(i);
                if (Objects.equals(capitulo.getNombreArchivo(), elementoCapitulo)) {
                    posicion=i;
                    break;
                }
            }
            return posicion;
        }catch (Exception e){
            return posicion;
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
    private boolean verificarTipoArchivo(List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                if (!file.getOriginalFilename().toLowerCase().endsWith(".mp3")&&!file.getOriginalFilename().toLowerCase().endsWith(".wav")) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private boolean crearCarpetaLibro(Path carpeta){
        try {
            if (Files.exists(carpeta)) {
                return false;
            } else {
                Files.createDirectories(carpeta);
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }
}
