/*
package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="bitacora")
public class Bitacora {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "bitacora_id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="actividad_id")
	private Actividad actividad;	
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="contacto_id")
	private Contacto contacto;
	
	@NotNull
	@Column(name = "bitacora_nombre", nullable = false)	
	private String nombre;

	@NotNull
	@Column(name = "bitacora_descripcion", nullable = false)	
	private String descripcion;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "bitacora_fecha", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "bitacora_fecha_evento", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaEvento;

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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public Bitacora(Actividad actividad, Contacto contacto, String nombre,
			String descripcion, Date fecha, Date fechaEvento) {
		super();
		this.actividad = actividad;
		this.contacto = contacto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.fechaEvento = fechaEvento;
	}
	
	public Bitacora() {
		super();		
	}
}*/