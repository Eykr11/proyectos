package com.example.grado.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "formatos")
public class Formato {
	
    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private String link;

    
	public Formato() {
		super();
	}

	public Formato(String id, String nombre, String descripcion, String link) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.link = link;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
    
	
    
}
