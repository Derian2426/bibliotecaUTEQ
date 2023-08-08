package com.biblioteca.bibliotecauteq.repository;

import com.biblioteca.bibliotecauteq.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByEmail(String email);
}
