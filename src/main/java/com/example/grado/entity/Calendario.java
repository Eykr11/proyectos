package com.example.grado.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "calendarios")
public class Calendario {
	
    @Id
    private String id;
    private String actividad;
    private String fecha;
    
	public Calendario() {
		super();
	}

	public Calendario(String id, String actividad, String fecha) {
		super();
		this.id = id;
		this.actividad = actividad;
		this.fecha = fecha;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
    
	

}
