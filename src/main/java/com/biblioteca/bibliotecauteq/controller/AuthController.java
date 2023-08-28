package com.biblioteca.bibliotecauteq.controller;

import com.biblioteca.bibliotecauteq.model.InformacionPeticion;
import com.biblioteca.bibliotecauteq.model.UserLogger;
import com.biblioteca.bibliotecauteq.model.Usuario;
import com.biblioteca.bibliotecauteq.security.TokenUtils;
import com.biblioteca.bibliotecauteq.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping
    public UserLogger authenticateUser(@RequestBody Usuario authCredentials) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authCredentials.getEmail(), authCredentials.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = TokenUtils.createToken(userDetails.getNombre(), userDetails.getUsername());
            userDetails.getUsuario().setPassword("");
            return new UserLogger(userDetails.getUsuario(),token);
        } catch (AuthenticationException e) {
            return new UserLogger(new Usuario(),"-1");
        }
    }
    @PostMapping("/validate")
    public InformacionPeticion validateToken(@RequestBody UserLogger userLogger) {
        try {
            if(TokenUtils.getAuthentication(userLogger.getToken())!=null)
                return new InformacionPeticion(1,"Token Valido","OK");
            return new InformacionPeticion(-1,"Token Invalido","Error");
        }catch (Exception e){
            return new InformacionPeticion(-1,"Token Invalido","Error");
        }
    }
//solo mirame con esos ojitos lindos

}
