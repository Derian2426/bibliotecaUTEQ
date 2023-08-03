package com.biblioteca.bibliotecauteq.model;

import lombok.Data;

@Data
public class InformacionPeticion {
    private int valorEstado;
    private String iformacionEstado;
    private String estadoPeticion;

    public InformacionPeticion(int valorEstado, String iformacionEstado, String estadoPeticion) {
        this.valorEstado = valorEstado;
        this.iformacionEstado = iformacionEstado;
        this.estadoPeticion = estadoPeticion;
    }
}
