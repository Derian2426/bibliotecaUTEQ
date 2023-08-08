package com.biblioteca.bibliotecauteq.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Column(name = "nombre", nullable = false,length = 40)
    private String nombre;
    @Column(name = "apellido", nullable = false,length = 40)
    private String apellido;
    @Column(name = "email", nullable = false,length = 100)
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fechaNacimiento", nullable = false)
    private Date fechaNacimiento;
    @Column(name = "password", nullable = false,length = 200)
    private String password;
}
