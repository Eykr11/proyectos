package com.example.grado.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.grado.entity.Proyecto;
import com.example.grado.entity.Usuario;
public interface ProyectoRepository extends MongoRepository<Proyecto, String>{
	List<Proyecto> findByEstudiante(Usuario estudiante);

}


