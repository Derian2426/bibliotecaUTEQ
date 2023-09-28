package com.biblioteca.bibliotecauteq.file_configuration;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

public class Archivos {
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

    public static boolean eliminarArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        if (archivo.exists()) {
            if (archivo.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static String removeExtensionFromFile(MultipartFile file) {
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
}
