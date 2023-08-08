package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.InformacionPeticion;
import com.biblioteca.bibliotecauteq.model.Usuario;
import com.biblioteca.bibliotecauteq.security.TokenUtils;
import com.biblioteca.bibliotecauteq.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping
    public ResponseEntity<InformacionPeticion> authenticateUser(@RequestBody Usuario authCredentials) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authCredentials.getEmail(), authCredentials.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = TokenUtils.createToken(userDetails.getNombre(), userDetails.getUsername());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            return ResponseEntity.ok().headers(headers).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new InformacionPeticion(-1,"Inicio de sesión incorrecto.","Error"));
        }
    }

}
