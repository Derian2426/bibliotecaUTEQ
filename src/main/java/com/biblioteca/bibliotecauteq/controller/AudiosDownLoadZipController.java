package com.biblioteca.bibliotecauteq.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/downloadZip")
@CrossOrigin(origins = "http://localhost:3000")
public class AudiosDownLoadZipController {

    @Value("${upload.dir}")
    private String uploadDir;
    @PostMapping
    public ResponseEntity<byte[]> download(@RequestParam("ruta") String rutaArchivo) throws Exception {
        String zipFileName = rutaArchivo + ".zip";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentType(MediaType.valueOf("application/zip"));
        headers.setContentDispositionFormData("Content-Disposition", "attachment; filename=\"" + zipFileName + "\"");
        File sourceFolder = new File(uploadDir + "/" + rutaArchivo);

        if (sourceFolder.exists() && sourceFolder.isDirectory()) {
            byte[] zipBytes = zipFolder(sourceFolder);
            return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private byte[] zipFolder(File folder) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(baos)) {
            zipFolderRecursive(folder, zipOut, "");
        }
        return baos.toByteArray();
    }

    private void zipFolderRecursive(File folder, ZipOutputStream zipOut, String currentPath) throws IOException {
        File[] files = folder.listFiles();
        byte[] buffer = new byte[1024];
        for (File file : files) {
            if (file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(currentPath + file.getName());
                zipOut.putNextEntry(zipEntry);
                try (FileInputStream fis = new FileInputStream(file)) {
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zipOut.write(buffer, 0, length);
                    }
                }
                zipOut.closeEntry();
            } else if (file.isDirectory()) {
                zipFolderRecursive(file, zipOut, currentPath + file.getName() + "/");
            }
        }
    }
}