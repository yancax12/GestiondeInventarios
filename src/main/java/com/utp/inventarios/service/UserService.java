package com.utp.inventarios.service;

import com.utp.inventarios.model.User;
import com.utp.inventarios.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    // Inyectamos el repositorio de usuarios
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario por nombre de usuario en la base de datos
        User user = userRepository.findByUsername(username);

        // Si el usuario no se encuentra, lanzamos una excepción
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Devolver el usuario con roles
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password("{noop}" + user.getPassword())  // Usamos {noop} para contraseñas sin encriptar (para pruebas)
                .roles(user.getRole())  // Aquí se asignan los roles del usuario
                .build();
    }
}

