package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="solicitudes")
@NamedQueries ({	
	@NamedQuery(name="Solicitud.findById", query="select s from solicitudes s where s.id=?1"),
	@NamedQuery(name="Solicitud.findByIdIfp", query="select s from solicitudes s where s.certificacion.ifpId=?1"),
	@NamedQuery(name="Solicitud.findByIdCert", query="select s from solicitudes s where s.certificacion.id=?1"),
	@NamedQuery(name="Solicitud.findByIfpNombre", query="select s from solicitudes s where s.certificacion.ifpNombre=?1"),
	@NamedQuery(name="Solicitud.findByNombreContacto", query="select s from solicitudes s where s.contacto.nombreCompleto=?1"),
	@NamedQuery(name="Solicitud.findByNombreCert", query="select s from solicitudes s where s.certificacion.nombre=?1"),
	@NamedQuery(name="Solicitud.findByFechaSolicitud", query="select s from solicitudes s where s.fechaRegistro=?1"),
	@NamedQuery(name="Solicitud.findByContactoCorreo", query="select s from solicitudes s where s.contacto.correo1=?1"),
	@NamedQuery(name="Solicitud.findByEstatusSolicitud", query="select s from solicitudes s where s.estatus=?1")	
})
public class Solicitud implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	@ManyToOne 
	@JoinColumn(name="contacto_id")	
	private Contacto contacto;
	
	@NotNull
	@ManyToOne 
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

	public Solicitud(String nombre, String ticket, int estatus,
			Date fechaRegistro, Date fechaMatricula, int experiencia,
			String ocupacion, String oficio, int escolaridad,
			Contacto contacto, Certificacion certificacion,
			List<Evaluacion> evaluaciones) {
		super();
		this.nombre = nombre;
		this.ticket = ticket;
		this.estatus = estatus;
		this.fechaRegistro = fechaRegistro;
		this.fechaMatricula = fechaMatricula;
		this.experiencia = experiencia;
		this.ocupacion = ocupacion;
		this.oficio = oficio;
		this.escolaridad = escolaridad;
		this.contacto = contacto;
		this.certificacion = certificacion;
		this.evaluaciones = evaluaciones;
	}
	
	public Solicitud() {
		super();		
	}
}