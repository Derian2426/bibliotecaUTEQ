package com.biblioteca.bibliotecauteq.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/downloadZip")
public class AudiosDownLoadZipController {
    @GetMapping("/zip")
    public void download(HttpServletResponse response) throws Exception {
        // Ruta de la carpeta que deseas comprimir
        String folderPath = "C:/Users/HP/Desktop/Audio";

        String zipFileName = "archivos.zip";

        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName);
        zipFolder(folderPath, folderPath, zipOut);
        zipOut.close();
    }

    private void zipFolder(String folderPath, String baseFolderPath, ZipOutputStream zipOut) throws IOException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        // Recorrer todos los archivos y subcarpetas en la carpeta actual
        for (File file : files) {
            if (file.isFile()) {
                // Si es un archivo, agregarlo al archivo ZIP
                String relativePath = file.getAbsolutePath().substring(baseFolderPath.length() + 1);
                ZipEntry zipEntry = new ZipEntry(relativePath);
                zipOut.putNextEntry(zipEntry);

                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    zipOut.write(buffer, 0, length);
                }

                fis.close();
                zipOut.closeEntry();
            } else if (file.isDirectory()) {
                // Si es una subcarpeta, llamar recursivamente al método zipFolder
                zipFolder(file.getAbsolutePath(), baseFolderPath, zipOut);
            }
        }
    }
}
