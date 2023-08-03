package com.biblioteca.bibliotecauteq.model;

import lombok.Data;

@Data
public class InformacionPeticion {
    private int valorError;
    private String iformacionError;
    private String estadoError;

    public InformacionPeticion(int valorError, String iformacionError, String estadoError) {
        this.valorError = valorError;
        this.iformacionError = iformacionError;
        this.estadoError = estadoError;
    }
}
