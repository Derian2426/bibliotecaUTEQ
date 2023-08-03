package com.biblioteca.bibliotecauteq.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
public class AudioController {
    @PostMapping
    public String uploadAudio(@RequestParam("file") List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                if (!file.getOriginalFilename().endsWith(".mp3")) {
                    return "Error: Solo se permiten archivos con extensión .mp3.";
                }
            }
            // Guardar el archivo en un directorio local
            String uploadDir = "C:/Users/HP/Desktop/Audio/niidea"; // Cambia esto por la ruta correcta
            Path carpeta = Paths.get(uploadDir);
            try {
                Files.createDirectories(carpeta);
            } catch (IOException e) {
                return "Error al subir el archivo de audio.";
            }
            for (MultipartFile file : files) {
                Path filePath = Path.of(uploadDir, file.getOriginalFilename());
                if (Files.exists(filePath)) {
                    return "Error: El archivo " + file.getOriginalFilename() + " ya existe en el servidor.";
                }
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return "Archivo de audio subido con éxito.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al subir el archivo de audio.";
        }
    }
}
