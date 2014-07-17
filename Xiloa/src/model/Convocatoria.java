package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.convocatorias 
 * 
 */

@Entity(name = "convocatorias")
@Table(name = "convocatorias", schema = "sccl")
public class Convocatoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "convocatoria_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="solicitud_id", nullable = false)	
	private Solicitud solicitud;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="actividad_id", nullable = false)	
	private Actividad actividad;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="contacto_id", nullable = false)	
	private Contacto contacto;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_programacion", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@NotNull
	@ManyToOne
	@JoinColumn(name="estado_id", nullable = false)	
	private Mantenedor estado;
	
	public Convocatoria(){
		super();
	}

	public Convocatoria(Solicitud solicitud, Actividad actividad, Contacto contacto, Date fecha, Mantenedor estado){
		this.solicitud = solicitud;
		this.actividad = actividad;
		this.contacto = contacto;
		this.fecha = fecha;
		this.estado = estado;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Mantenedor getEstado() {
		return estado;
	}

	public void setEstado(Mantenedor estado) {
		this.estado = estado;
	}
}