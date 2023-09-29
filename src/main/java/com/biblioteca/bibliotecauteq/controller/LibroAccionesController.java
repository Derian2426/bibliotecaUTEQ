package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.*;
import com.biblioteca.bibliotecauteq.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.biblioteca.bibliotecauteq.file_configuration.Archivos.eliminarArchivo;

@RestController
@RequestMapping("/api/libro")
@CrossOrigin
public class LibroAccionesController {
    @Autowired
    private LibroServices libroServices;
    @Autowired
    private CapituloService capituloService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AutorLibroServices autorLibroServices;
    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping
    @Transactional
    public InformacionPeticion uploadAudio(@RequestParam("file") List<MultipartFile> files, @RequestParam("libroRequest") String bookRequest) {

        try {
            if (files.size() > 0 && !bookRequest.equals("")) {
                ObjectMapper objectMapper = new ObjectMapper();
                LibroRequest bookMap = objectMapper.readValue(bookRequest, LibroRequest.class);
                Libro book = libroServices.findById(Math.toIntExact(bookMap.getLibro().getIdLibro())).orElse(new Libro());
                Libro bookEdit = bookMap.getLibro();
                List<Capitulo> capitulosEdit = bookMap.getCapituloFileList();
                List<Capitulo> capitulos = capituloService.findByLibroAll(book);
                List<AutorLibro> autorLibros = autorLibroServices.listaAutores(book);
                String rutaAbsoluta = capitulos.get(0).getRutaArchivo();
                Usuario usuario = usuarioService.findByEmail(capitulos.get(0).getUsuario().getEmail());
                ActualizarImagenPdf(book, bookEdit, files);
                bookEdit = libroServices.update(bookEdit);
                eliminarArchivosAudio(capitulos, capitulosEdit, rutaAbsoluta.trim());
                actualizarAudios(files, uploadDir + "/" + rutaAbsoluta.trim());
                actualizarCapitulos(capitulosEdit, bookEdit, rutaAbsoluta.trim(), usuario);
                capituloService.createList(capitulosEdit);
                autorLibroServices.createList(autoresLibro(bookMap.getListTipoAutor(), autorLibros, bookEdit));
                return new InformacionPeticion(1, "Archivos de audio modificados con éxito.", "Éxito");
            } else {
                return new InformacionPeticion(-1, "Error al subir los archivos de audio.", "Error");
            }
        } catch (Exception e) {
            return new InformacionPeticion(-1, "Error al subir los archivos de audio.", "Error");
        }
    }

    private void ActualizarImagenPdf(Libro book, Libro bookEdit, List<MultipartFile> files) {
        if (!book.getPdfLibro().equals(bookEdit.getPdfLibro())) {
            eliminarArchivo(uploadDir + "/pdf/" + book.getPdfLibro());
            bookEdit.setPdfLibro(String.valueOf(crearArchivos(files, "application/pdf", uploadDir + "/pdf/")));
        }
        if (!book.getCoverImage().equals(bookEdit.getCoverImage())) {
            if (eliminarArchivo(uploadDir + "/png/" + book.getCoverImage()) || book.getCoverImage().endsWith(".png")) {
                bookEdit.setCoverImage(crearArchivos(files, "image/png", uploadDir + "/png/"));

            } else if (eliminarArchivo(uploadDir + "/jpg/" + book.getCoverImage()) || book.getCoverImage().endsWith(".jpg")) {
                bookEdit.setCoverImage(crearArchivos(files, "image/png", uploadDir + "/png/"));
            }
        }
    }

    private String crearArchivos(List<MultipartFile> files, String extension, String direccion) {
        Optional<String> result = files.stream()
                .filter(file -> file.getContentType() != null && file.getContentType().equals(extension))
                .findFirst()
                .map(file -> {
                    try {
                        Path filePath = Paths.get(direccion, file.getOriginalFilename());
                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                        files.remove(file);
                        return file.getOriginalFilename();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
        return result.orElse(null);
    }

    private void eliminarArchivosAudio(List<Capitulo> capitulos, List<Capitulo> capitulosEdit, String ruta) {
        for (Capitulo capitulo : capitulos) {
            if (!verificarExistencia(capitulo, capitulosEdit)) {
                capituloService.deleteCapitulo(capitulo);
                eliminarArchivo(uploadDir + "/" + ruta + "/" + capitulo.getNombreArchivo());
            }
        }
    }

    private boolean verificarExistencia(Capitulo capitulo, List<Capitulo> capitulos) {
        for (Capitulo capituloBuqueda : capitulos) {
            if (capitulo.getNombreArchivo().equals(capituloBuqueda.getNombreArchivo())) {
                return true;
            }
        }
        return false;
    }

    private void actualizarAudios(List<MultipartFile> files, String ruta) {
        try {
            for (MultipartFile file : files) {
                if (!Objects.equals(file.getOriginalFilename(), "")) {
                    Path filePath = Path.of(ruta, file.getOriginalFilename());
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void actualizarCapitulos(List<Capitulo> capitulos, Libro bookEdit, String rutaAbsoluta, Usuario usuario) {
        for (Capitulo capitulo : capitulos) {
            capitulo.setRutaArchivo(rutaAbsoluta);
            capitulo.setLibro(bookEdit);
            capitulo.setUsuario(usuario);
        }
    }

    private List<AutorLibro> autoresLibro(List<AutorLibro> listaAutoresEdit, List<AutorLibro> listaAutores, Libro libro) {
        for (AutorLibro autorLibro : listaAutores) {
            if(!verificaAutor( listaAutoresEdit, autorLibro)){
                autorLibroServices.deleteAutor(autorLibro);
            }
        }
        for (AutorLibro autorLibro : listaAutoresEdit) {
            autorLibro.setLibro(libro);
        }
        return listaAutoresEdit;
    }

    private boolean verificaAutor(List<AutorLibro> listaAutoresEdit, AutorLibro autorLibroBusqueda) {
        for (AutorLibro autorLibro : listaAutoresEdit) {
            if (autorLibroBusqueda.getAutor().getNombre().equals(autorLibro.getAutor().getNombre()) &&
                    autorLibroBusqueda.getAutor().getApellido().equals(autorLibro.getAutor().getApellido())) {
                return true;
            }
        }
        return false;
    }
}
