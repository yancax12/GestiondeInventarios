package com.utp.inventarios.repository;

import com.utp.inventarios.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Método para encontrar un usuario por su nombre de usuario
    User findByUsername(String username);
}
