package com.biblioteca.bibliotecauteq.controller;
import com.biblioteca.bibliotecauteq.model.Capitulo;
import com.biblioteca.bibliotecauteq.model.Libro;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
@CrossOrigin
public class FileController {
    @Value("${upload.dir}")
    private String uploadDir;
    @PostMapping
    public ResponseEntity<Resource> serveFileAudio(@RequestBody Capitulo capitulo) {
        try {
            Path carpeta = Paths.get(uploadDir+"/"+ capitulo.getRutaArchivo()+"/"+capitulo.getNombreArchivo());
            File file = new File(carpeta.toString());
            Resource resource = new FileSystemResource(file);
            //
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            String contentType = "audio/mpeg";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/portada")
    public ResponseEntity<Resource> serveFileAudio(@RequestBody Libro libro) {
        try {
            String extencionImagen=getFileExtension(libro.getCoverImage());
            Path carpeta = Paths.get(uploadDir+"/"+extencionImagen+"/"+ libro.getCoverImage());
            File file = new File(carpeta.toString());
            Resource resource = new FileSystemResource(file);
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            String contentType = "image/png";
            if(extencionImagen.equals("jpg"))
                contentType = "image/jpeg";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }
}
