package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.*;
import com.biblioteca.bibliotecauteq.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/upload")
@CrossOrigin
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
            if(libroRequest.getLibro()==null)
                return new InformacionPeticion(-1, "Debe enviar los datos del libro.", "Error");
            if(libroRequest.getListTipoAutor().size()<1)
                return new InformacionPeticion(-1, "Debe contener la lista de los autores.", "Error");
            if(libroRequest.getCapituloFileList().size()<1)
                return new InformacionPeticion(-1, "Debe contener la lista de los capitulos.", "Error");
            if(usuarioService.findById(libroRequest.getCapituloFileList().get(0).getUsuario().getIdUsuario())==null)
                return new InformacionPeticion(-1, "El usuario de la sesion no es valido.", "Error");
            if(files.size()<1)
                return new InformacionPeticion(-1, "La lista de archivos no debe estar vacia.", "Error");
            if(libroServices.busquedaLibro(libroRequest.getLibro().getNombreLibro()))
                return new InformacionPeticion(-1, "El libro ya se encuentra registrado.", "Error");
            if(!seleccionarArchivos(files,"pdf",libroRequest))
                return new InformacionPeticion(-1, "No se encuentra el PDF del libro.", "Error");
            if(!seleccionarArchivos(files,"jpg",libroRequest)&&!seleccionarArchivos(files,"png",libroRequest))
                return new InformacionPeticion(-1, "No se encuentra la portada del libro.", "Error");
            if (!verificarTipoArchivo(files))
                return new InformacionPeticion(-1, "Solo se permiten archivos con extensión .mp3", "Archivo incorrecto");
            if (!crearCarpetaLibro(folder))
                return new InformacionPeticion(-1, "La carpeta ya existe en el servidor", "Carpeta existente");
            actualizarLibroRequest(files,folder, libroRequest);
            Libro book = libroServices.create(libroRequest.getLibro());
            if(book ==null){
                File deleteFolder = new File(folder.toUri());
                deleteFolder(deleteFolder);
                return new InformacionPeticion(-1, "El libro ya se encuentra registrado.", "Error");
            }
            capituloService.createList(capitulosLibro(libroRequest.getCapituloFileList(), book));
            autorLibroServices.createList(autoresLibro(libroRequest.getListTipoAutor(), book));
            return new InformacionPeticion(1, "Archivos de audio subidos con éxito.", "Éxito");
        } catch (Exception e) {
            File deleteFolder = new File(folder.toUri());
            deleteFolder(deleteFolder);
            return new InformacionPeticion(-1, "Error al subir los archivos de audio.", "Error");
        }
    }
    private boolean deleteFolder(File carpeta) {
        try {
            if (carpeta.exists()) {
                File[] files = carpeta.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            deleteFolder(file);
                        } else {
                            file.delete();
                        }
                    }
                }
                return carpeta.delete();
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }



    private LibroRequest actualizarLibroRequest(List<MultipartFile> files,Path carpeta, LibroRequest libroRequest) {
        try {
            for (MultipartFile file : files) {
                Path filePath = Path.of(carpeta.toString(), file.getOriginalFilename());
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                String ruta=removeExtensionFromFile(file);
                int index=posicionArchivo(libroRequest.getCapituloFileList(),ruta);
                Capitulo capitulo=libroRequest.getCapituloFileList().get(index);
                capitulo.setNombreArchivo(file.getOriginalFilename());
                capitulo.setRutaArchivo(libroRequest.getLibro().getNombreLibro());
                libroRequest.getCapituloFileList().set(index,capitulo);
            }
            return libroRequest;
        }catch (Exception e){
            return new LibroRequest();
        }
    }
    public String removeExtensionFromFile(MultipartFile file) {
        String ruta = file.getOriginalFilename();
        int lastDotIndex = ruta.lastIndexOf(".");

        if (lastDotIndex != -1) {
            String contentType = file.getContentType();
            String extension = contentType.substring(contentType.lastIndexOf("/") + 1).toLowerCase();
            String nuevaRuta = ruta.substring(0, lastDotIndex);

            if (nuevaRuta.toLowerCase().endsWith("." + extension)) {
                nuevaRuta = nuevaRuta.substring(0, nuevaRuta.length() - (extension.length() + 1));
            }

            return nuevaRuta;
        }

        return ruta;
    }

    private Boolean seleccionarArchivos(List<MultipartFile> files,String extencion,LibroRequest libroRequest) throws IOException {
        boolean verifica=false;
        Path carpeta = Paths.get(uploadDir+"/"+ extencion);
        crearCarpetaLibro(carpeta);
        for (MultipartFile file : files) {
            if (file.getOriginalFilename().toLowerCase().endsWith("."+extencion)) {
                Path filePath = Path.of(carpeta.toString(), file.getOriginalFilename());
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                files.remove(file);
                libroRequest.getLibro().setCoverImage(file.getOriginalFilename());
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
    private List<AutorLibro> autoresLibro(List<AutorLibro>listaAutores,Libro libro){
        for (int i = 0; i < listaAutores.size(); i++) {
            AutorLibro autorLibro = listaAutores.get(i);
            autorLibro.setLibro(libro);
        }
        return listaAutores;
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
