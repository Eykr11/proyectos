package com.example.grado.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import com.example.grado.entity.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{

    Usuario findByCorreoElectronicoAndContrasena(String correoElectronico, String contrasena);
    // Puedes agregar más métodos de búsqueda según tus necesidades
    
    @Query("{'rol': ?0}")
    List<Usuario> findByRol(String Rol);
}
