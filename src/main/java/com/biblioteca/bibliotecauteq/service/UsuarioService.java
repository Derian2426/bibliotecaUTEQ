package com.biblioteca.bibliotecauteq.service;

import com.biblioteca.bibliotecauteq.model.Usuario;
import com.biblioteca.bibliotecauteq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UserRepository usuarioRepository;

    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Integer idUsuario) {
        Optional<Usuario> usuario=usuarioRepository.findById(idUsuario);
        return usuario.orElse(null);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public void delete(Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    public Usuario findByEmail(String email){
        try {
            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("No se pudo encontrar un usuario con el email: " + email));
        }catch (Exception e){
            return new Usuario();
        }
    }
}
