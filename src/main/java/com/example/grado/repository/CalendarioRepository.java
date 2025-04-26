package com.example.grado.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.grado.entity.Calendario;
public interface CalendarioRepository extends MongoRepository<Calendario, String>{

}
