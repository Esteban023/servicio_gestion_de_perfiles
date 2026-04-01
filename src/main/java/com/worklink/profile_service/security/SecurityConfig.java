package com.worklink.profile_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad
     * - Permite acceso público a GET y POST
     * - Requiere autenticación para PUT, DELETE
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Permitir GET sin autenticación
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                // Permitir POST sin autenticación (crear nuevos perfiles)
                .requestMatchers(HttpMethod.POST, "/api/**").permitAll()
                // Requerir autenticación para PUT (actualizar)
                .requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
                // Requerir autenticación para DELETE
                .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                // Cualquier otra solicitud requiere autenticación
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> {});
        
        return http.build();
    }

    /**
     * Codificador de contraseñas BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
