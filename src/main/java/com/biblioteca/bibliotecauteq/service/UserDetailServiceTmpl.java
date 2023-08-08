package com.biblioteca.bibliotecauteq.service;


import com.biblioteca.bibliotecauteq.model.Usuario;
import com.biblioteca.bibliotecauteq.repository.UsuarioRepository;
import com.biblioteca.bibliotecauteq.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailServiceTmpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario=usuarioRepository.findOneByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario con el email "+ email+" no existe."));
        return new UserDetailsImpl(usuario);
    }
}
