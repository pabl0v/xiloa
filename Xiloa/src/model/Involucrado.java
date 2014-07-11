package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/*
@Entity(name = "involucrados")
@Table(name = "involucrados", schema = "sccl")
public class Involucrado implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "involucrado_id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name="actividad_id", nullable = false)	
	private Actividad actividad;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="contacto_id", nullable = false)	
	private Contacto contacto;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="estatus_id", nullable = false)	
	private Mantenedor estado;
	
	public Involucrado(){
		super();
	}
	
	public Involucrado(Actividad actividad, Contacto contacto, Mantenedor estado){
		super();
		this.actividad = actividad;
		this.contacto = contacto;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Mantenedor getEstado() {
		return estado;
	}

	public void setEstado(Mantenedor estado) {
		this.estado = estado;
	}
}*/