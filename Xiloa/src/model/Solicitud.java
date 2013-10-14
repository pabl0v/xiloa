package model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="solicitudes")
public class Solicitud {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "solicitud_id", nullable = false)
	private Long id;

	@Column(name = "solicitud_nombre", nullable = false)
	private String nombre;
	
	@Column(name = "solicitud_ticket", nullable = false)
	private String ticket;
	
	@Column(name = "solicitud_estatus", nullable = false)
	private int estatus;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_registro", nullable = false)
	@Temporal(TemporalType.DATE)	
	private Date fechaRegistro;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_matricula", nullable = false)
	@Temporal(TemporalType.DATE)	
	private Date fechaMatricula;
	
	@Column(name = "experiencia", nullable = false)
	private int experiencia;
	
	@Column(name = "ocupacion", nullable = false)
	private String ocupacion;
	
	@Column(name = "oficio", nullable = false)
	private String oficio;
	
	@Column(name = "escolaridad_id", nullable = false)
	private int escolaridad;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="contacto_id")	
	private Contacto contacto;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="certificacion_id")	
	private Certificacion certificacion;
	
	@OneToMany(mappedBy="solicitud")
	private List<Evaluacion> evaluaciones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaMatricula() {
		return fechaMatricula;
	}

	public void setFechaMatricula(Date fechaMatricula) {
		this.fechaMatricula = fechaMatricula;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getOficio() {
		return oficio;
	}

	public void setOficio(String oficio) {
		this.oficio = oficio;
	}

	public int getEscolaridad() {
		return escolaridad;
	}

	public void setEscolaridad(int escolaridad) {
		this.escolaridad = escolaridad;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Certificacion getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(Certificacion certificacion) {
		this.certificacion = certificacion;
	}

	public List<Evaluacion> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<Evaluacion> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}
}