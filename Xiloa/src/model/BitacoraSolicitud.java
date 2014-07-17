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
 * @author Denis Chavez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.bitacora_solicitud 
 * 
 */

@Entity(name = "bitacora_solicitud")
@Table(name = "bitacora_solicitud", schema = "sccl")
public class BitacoraSolicitud implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "bitacora_id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name="solicitud_id", nullable = false)	
	private Solicitud solicitud;

	@NotNull
	@ManyToOne
	@JoinColumn(name="estado_anterior_id", nullable = false)	
	private Mantenedor estadoAnterior;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="estado_nuevo_id", nullable = false)	
	private Mantenedor estadoNuevo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="actualiza_id", nullable = false)	
	private Contacto actualiza;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_actualiza", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHora;
	
	public BitacoraSolicitud(){
		super();
	}
	
	public BitacoraSolicitud(Solicitud solicitud, Mantenedor estadoAnterior, Mantenedor estadoNuevo, Contacto actualiza, Date fechaHora){
		super();
		this.solicitud = solicitud;
		this.estadoAnterior = estadoAnterior;
		this.estadoNuevo = estadoNuevo;
		this.actualiza = actualiza;
		this.fechaHora = fechaHora;
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

	public Mantenedor getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(Mantenedor estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public Mantenedor getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(Mantenedor estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public Contacto getActualiza() {
		return actualiza;
	}

	public void setActualiza(Contacto actualiza) {
		this.actualiza = actualiza;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
}