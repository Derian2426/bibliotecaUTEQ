package com.biblioteca.bibliotecauteq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogger {
    private Usuario userLogger;
    private String token;

}
