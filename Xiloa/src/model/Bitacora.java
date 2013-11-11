package model;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="bitacoras")
@NamedQueries({
	@NamedQuery(name="Bitacoras.findAllByActividadId", query="select b from bitacoras b, actividades a where b member of a.bitacoras and a.id=?1")
})
public class Bitacora {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "bitacora_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="actividad_id")	
	private Actividad actividad;

	@NotNull
	@ManyToOne
	@JoinColumn(name="bitacora_usuario_id")
	private Contacto usuario;
	
	@NotNull
	@Column(name = "bitacora_observaciones", nullable = false)	
	private String observaciones;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "bitacora_fecha_registro", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	
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