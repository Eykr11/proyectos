package com.example.grado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.grado.entity.Usuario;
import com.example.grado.repository.UsuarioRepository;

@SpringBootApplication
public class ProyectosDeGradoApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(ProyectosDeGradoApplication.class, args);

    }
	
	
    /*@Bean
    public Usuario inicializarAdministrador(UsuarioRepository usuarioRepository) {
        Usuario administrador = new Usuario(
            "admin",
            "adminLastName",
            "admin@gmail.com",
            "admin",
            Usuario.RolEnum.ADMINISTRADOR
        );
        usuarioRepository.save(administrador);
        return administrador;
    }*/

}
