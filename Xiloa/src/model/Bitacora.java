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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.bitacoras 
 * 
 */

@Entity(name = "bitacoras")
@Table(name = "bitacoras", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Bitacoras.findAllByActividadId", query="select b from bitacoras b where b.actividad.id=?1")
})
public class Bitacora implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "bitacora_id", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="actividad_id", nullable = false)
	private Actividad actividad;

	@ManyToOne
	@JoinColumn(name="bitacora_usuario_id", nullable = false)
	private Contacto usuario;
	
	@Column(name = "bitacora_observaciones", nullable = false)	
	private String observaciones;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "bitacora_fecha_registro", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "bitacora_fecha_evento", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
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

	public Contacto getUsuario() {
		return usuario;
	}

	public void setUsuario(Contacto usuario) {
		this.usuario = usuario;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fecha) {
		this.fechaRegistro = fecha;
	}

	public Date getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}
	
	public Bitacora() {
		super();		
	}

	public Bitacora(Actividad actividad, Contacto usuario, String observaciones, Date fechaRegistro, Date fechaEvento) {
		super();
		this.actividad = actividad;
		this.usuario = usuario;
		this.observaciones = observaciones;
		this.fechaRegistro = fechaRegistro;
		this.fechaEvento = fechaEvento;
	}
}