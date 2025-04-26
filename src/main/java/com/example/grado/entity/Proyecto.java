package com.example.grado.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "proyectos")
public class Proyecto {
	
    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private EstadoProyecto estado;
    private EstadoRevisionDirector estadoRevisionDirector;
	

    private Usuario estudiante;

    private Usuario director;

    private Usuario evaluador;

	public enum EstadoProyecto {
        IDEA,
        ANTEPROYECTO,
        APROBADO,
        EN_DESARROLLO,
        FINALIZADO
    }
	
	
	public enum EstadoRevisionDirector {
	    PENDIENTE,
	    APROBADO,
	    RECHAZADO
	}

	
	
	
	public Proyecto() {
		super();
	}




	public Proyecto(String id, String titulo, String descripcion, EstadoProyecto estado,
			EstadoRevisionDirector estadoRevisionDirector, Usuario estudiante, Usuario director, Usuario evaluador) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.estadoRevisionDirector = estadoRevisionDirector;
		this.estudiante = estudiante;
		this.director = director;
		this.evaluador = evaluador;
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getTitulo() {
		return titulo;
	}




	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}




	public String getDescripcion() {
		return descripcion;
	}




	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}




	public EstadoProyecto getEstado() {
		return estado;
	}




	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}




	public EstadoRevisionDirector getEstadoRevisionDirector() {
		return estadoRevisionDirector;
	}




	public void setEstadoRevisionDirector(EstadoRevisionDirector estadoRevisionDirector) {
		this.estadoRevisionDirector = estadoRevisionDirector;
	}




	public Usuario getEstudiante() {
		return estudiante;
	}




	public void setEstudiante(Usuario estudiante) {
		this.estudiante = estudiante;
	}




	public Usuario getDirector() {
		return director;
	}




	public void setDirector(Usuario director) {
		this.director = director;
	}




	public Usuario getEvaluador() {
		return evaluador;
	}




	public void setEvaluador(Usuario evaluador) {
		this.evaluador = evaluador;
	}





}


