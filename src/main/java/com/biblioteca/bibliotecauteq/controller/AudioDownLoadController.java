package com.biblioteca.bibliotecauteq.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/download")
@CrossOrigin(origins = "http://localhost:3000")
public class AudioDownLoadController {
    @Value("${upload.dir}")
    private String uploadDir;
    @PostMapping
    public ResponseEntity<byte[]> download(@RequestParam("ruta") String rutaArchivo, @RequestParam("file") String nombreArchivo) {
        try {
            File file = new File(uploadDir + "/" + rutaArchivo + "/" + nombreArchivo);
            if (file.exists()) {
                byte[] fileBytes = Files.readAllBytes(file.toPath());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentLength(fileBytes.length);
                headers.setContentDispositionFormData("attachment", nombreArchivo);
                return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
