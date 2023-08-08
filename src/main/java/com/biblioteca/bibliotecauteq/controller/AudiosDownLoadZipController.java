package com.biblioteca.bibliotecauteq.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
    public void download(HttpServletResponse response, @RequestParam("ruta") String rutaArchivo) throws Exception {
        String zipFileName = rutaArchivo + ".zip";
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + zipFileName + "\"");
        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        zipFolder(uploadDir + "/" + rutaArchivo, zipOut);
        zipOut.close();
    }

    private void zipFolder(String folderPath, ZipOutputStream zipOut) throws IOException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        byte[] buffer = new byte[1024];
        for (File file : files) {
            if (file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOut.putNextEntry(zipEntry);
                FileInputStream fis = new FileInputStream(file);
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    zipOut.write(buffer, 0, length);
                }
                fis.close();
                zipOut.closeEntry();
            } else if (file.isDirectory()) {
                zipFolder(file.getAbsolutePath(), zipOut);
            }
        }
    }
}