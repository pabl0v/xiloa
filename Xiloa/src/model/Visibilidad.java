package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "visibilidades")
@Table(name = "visibilidades", schema = "sccl")
public class Visibilidad implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="contacto_id")	
	private Contacto contacto;
	
	@Id
	@Column(name = "entidad_id", nullable = false)
	private Integer entidad;

	public Visibilidad(){
		super();
	}
	
	public Contacto getContacto(){
		return contacto;
	}
	
	public void setContacto(Contacto contacto){
		this.contacto = contacto;
	}
	
	public Integer getEntidad(){
		return entidad;
	}
	
	public void setEntidad(Integer entidad){
		this.entidad = entidad;
	}
	
	public Visibilidad(Contacto contacto, Integer entidad){
		this.contacto = contacto;
		this.entidad = entidad;
	}
}