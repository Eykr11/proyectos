package com.example.grado.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {
	
	
    @Id
    private String id;
    
    private String nombre;
    private String apellido;
    private String telefono;
    private String correoElectronico;
    private String contrasena;
    private RolEnum rol;

    public enum RolEnum {
        ADMINISTRADOR,
        COORDINACION,
        DIRECTOR,
        EVALUADOR,
        ESTUDIANTE
    }
    
    

	public Usuario() {
		super();
	}



	public Usuario(String id, String nombre, String apellido, String telefono, String correoElectronico,
			String contrasena, RolEnum rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.correoElectronico = correoElectronico;
		this.contrasena = contrasena;
		this.rol = rol;
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



	public String getApellido() {
		return apellido;
	}



	public void setApellido(String apellido) {
		this.apellido = apellido;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public String getCorreoElectronico() {
		return correoElectronico;
	}



	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}



	public String getContrasena() {
		return contrasena;
	}



	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}



	public RolEnum getRol() {
		return rol;
	}



	public void setRol(RolEnum rol) {
		this.rol = rol;
	}

	
    
	
    
}
