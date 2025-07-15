package com.utp.inventarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    // Inyectamos el UserDetailsService (UserService)
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .requestMatchers("/login", "/register").permitAll()  // Permitir el acceso sin autenticación a login y register
                .anyRequest().authenticated()  // Requiere autenticación para otras páginas
            .and()
            .formLogin()
                .loginPage("/login")  // Página personalizada de login
                .defaultSuccessUrl("/home", true)  // Redirigir a /home después de un login exitoso
                .failureUrl("/login?error=true")  // Redirigir a la misma página de login con error si falla
                .permitAll()
            .and()
            .logout()
                .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usar BCrypt para encriptar las contraseñas
    }
}
