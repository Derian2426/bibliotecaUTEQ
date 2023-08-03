package com.biblioteca.bibliotecauteq.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("/download")
public class DecargarAudioController {
    @GetMapping
    public void download(HttpServletResponse response) throws Exception {
        // Dirección del archivo, el entorno real se almacena en la base de datos
        String uploadDir = "C:/Users/HP/Desktop/Audio/Office 2016 - 64 bits.rar";
        File file = new File(uploadDir);
        // Llevando objeto de entrada
        FileInputStream fis = new FileInputStream(file);
        // Establecer el formato relevante
        response.setContentType("application/force-download");
        // Establecer el nombre y el encabezado del archivo descargado
        response.addHeader("Content-disposition", "attachment;fileName=" + "Office 2016 - 64 bits.rar");
        // Crear objeto de salida
        OutputStream os = response.getOutputStream();
        // operación normal
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }

}
