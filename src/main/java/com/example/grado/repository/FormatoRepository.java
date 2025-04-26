package com.example.grado.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.grado.entity.Formato;
public interface FormatoRepository extends MongoRepository<Formato, String>{

}
