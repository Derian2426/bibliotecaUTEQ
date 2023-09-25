package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.Capitulo;
import com.biblioteca.bibliotecauteq.model.Libro;
import com.biblioteca.bibliotecauteq.service.AutorLibroServices;
import com.biblioteca.bibliotecauteq.service.CapituloService;
import com.biblioteca.bibliotecauteq.service.LibroServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


@RestController
@RequestMapping("/capitulo")
@CrossOrigin
public class CapituloController {
    @Autowired
    private CapituloService capituloService;
    @Autowired
    private LibroServices libroServices;
    @Autowired
    private AutorLibroServices autorLibroServices;
    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<List<Capitulo>> listaCapitulo(@RequestBody Libro libro) {
        try {
            if (libro != null)
                return new ResponseEntity<>(capituloService.findByLibro(libro), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/eliminarLibro")
    public ResponseEntity<Libro> deleteCapitulo(@RequestBody Libro libro) {
        try {
            if (libro != null) {
                autorLibroServices.delete(autorLibroServices.listaAutores(libro));
                capituloService.delete(capituloService.findByLibroAll(libro));
                libroServices.delete(Math.toIntExact(libro.getIdLibro()));
                eliminarCarpeta(uploadDir + "/" + libro.getNombreLibro());
                eliminarArchivo(uploadDir + "/pdf/" + libro.getPdfLibro());
                eliminarArchivo(uploadDir + "/png/" + libro.getCoverImage());
                eliminarArchivo(uploadDir + "/jpg/" + libro.getCoverImage());
                return new ResponseEntity<>(libro, HttpStatus.OK);
            } else
                return new ResponseEntity<>(new Libro(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Libro(), HttpStatus.CONFLICT);
        }
    }

    public static void eliminarCarpeta(String carpeta) {
        Path carpetaPath = Paths.get(carpeta);
        try {
            Files.walkFileTree(carpetaPath, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        if (archivo.exists()) {
            if (archivo.delete()) {
                System.out.println("El archivo se eliminó correctamente.");
            } else {
                System.err.println("No se pudo eliminar el archivo.");
            }
        } else {
            System.err.println("El archivo no existe en la ruta especificada.");
        }
    }
}
