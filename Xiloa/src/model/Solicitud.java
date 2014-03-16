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
 * Esta clase se corresponde con la tabla sccl.solicitudes 
 * 
 */

@Entity(name = "solicitudes")
@Table(name = "solicitudes", schema = "sccl")
@NamedQueries ({	
	@NamedQuery(name="Solicitud.findById", query="select s from solicitudes s where s.id=?1 order by s.id desc"),
	@NamedQuery(name="Solicitud.findByIdIfp", query="select s from solicitudes s where s.certificacion.ifpId=?1 order by s.id desc"),
	@NamedQuery(name="Solicitud.findByIdCert", query="select s from solicitudes s where s.certificacion.id=?1 order by s.id desc"),
	@NamedQuery(name="Solicitud.findByIdContacto", query="select s from solicitudes s where s.contacto.id=?1 order by s.id desc"),
	@NamedQuery(name="Solicitud.findByIfpNombre", query="select s from solicitudes s where s.certificacion.ifpNombre=?1 order by s.id desc"),
	@NamedQuery(name="Solicitud.findByNombreContacto", query="select s from solicitudes s where s.contacto.nombreCompleto=?1 order by s.id desc"),
	@NamedQuery(name="Solicitud.findByNombreCert", query="select s from solicitudes s where s.certificacion.nombre=?1 order by s.id desc"),
	@NamedQuery(name="Solicitud.findByFechaSolicitud", query="select s from solicitudes s where s.fechaRegistro=?1 order by s.id desc"),
	@NamedQuery(name="Solicitud.findByContactoCorreo", query="select s from solicitudes s where s.contacto.correo1=?1 order by s.id desc"),
	@NamedQuery(name="Solicitud.findByEstatusSolicitud", query="select s from solicitudes s where s.estatus=?1 order by s.id desc"),
	@NamedQuery(name="Solicitud.findActivaByIdContacto", query="select s from solicitudes s where s.contacto.id=?1 and s.estatus.id != ?2 order by s.id desc")
})
public class Solicitud implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String tipoMantenedorEstado = new String("7");

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "solicitud_id", nullable = false)
	private Long id;

	@Column(name = "solicitud_nombre", nullable = false)
	private String nombre;
	
	@Column(name = "solicitud_ticket", nullable = false)
	private String ticket;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="solicitud_estatus", nullable = false)		
	private Mantenedor estatus;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_registro", nullable = false)
	@Temporal(TemporalType.DATE)	
	private Date fechaRegistro;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_matricula", nullable = true)
	@Temporal(TemporalType.DATE)	
	private Date fechaMatricula;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_convocatoria", nullable = true)
	@Temporal(TemporalType.DATE)	
	private Date fechaConvocatoria;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_asesoramiento", nullable = true)
	@Temporal(TemporalType.DATE)	
	private Date fechaAsesoramiento;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_remision_diploma", nullable = true)
	@Temporal(TemporalType.DATE)	
	private Date fechaRemisionDiploma;
	
	@Column(name = "experiencia", nullable = true)
	private Integer experiencia;
	
	@Column(name = "ocupacion", nullable = true)
	private String ocupacion;
	
	@Column(name = "oficio", nullable = true)
	private String oficio;
	
	@Column(name = "escolaridad_id", nullable = false)
	private int escolaridad;

	@NotNull
	@ManyToOne
	@JoinColumn(name="contacto_id", nullable = false)
	private Contacto contacto;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_id", nullable = false)	
	private Certificacion certificacion;
	
	@OneToMany(mappedBy="solicitud")
	private List<Evaluacion> evaluaciones;
	
	@Column(name="id_matricula", nullable=true)
	private Integer idMatricula;
	
	@Column(name="resultado_evaluacion", nullable=true)
	private boolean resultadoEvaluacion = false;
	
	@Column(name = "empresa", nullable = true)
	private String empresa;
	
	@Column(name = "situacion_laboral", nullable = true)
	private boolean situacion_laboral = false;
		
	public boolean isSituacion_laboral() {
		return situacion_laboral;
	}

	public void setSituacion_laboral(boolean situacion_laboral) {
		this.situacion_laboral = situacion_laboral;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTipomantenedorestado() {
		return tipoMantenedorEstado;
	}

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

	public Mantenedor getEstatus() {
		return estatus;
	}

	public void setEstatus(Mantenedor estatus) {
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

	public Integer getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(Integer experiencia) {
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

	public Integer getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(Integer idMatricula) {
		this.idMatricula = idMatricula;
	}

	public boolean isResultadoEvaluacion() {
		return resultadoEvaluacion;
	}

	public void setResultadoEvaluacion(boolean resultadoEvaluacion) {
		this.resultadoEvaluacion = resultadoEvaluacion;
	}

	public Solicitud(String nombre, String ticket, Mantenedor estatus,
			Date fechaRegistro, Date fechaMatricula, int experiencia,
			String ocupacion, String oficio, int escolaridad,
			Contacto contacto, Certificacion certificacion,
			List<Evaluacion> evaluaciones, Integer idMatricula, boolean resultadoEvaluacion,
			String empresa, boolean situacion_laboral) {
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
		this.idMatricula = idMatricula;
		this.resultadoEvaluacion = resultadoEvaluacion;
		this.empresa = empresa;
		this.situacion_laboral = situacion_laboral;
	}
	
	public Solicitud() {
		super();		
	}

	public Date getFechaConvocatoria() {
		return fechaConvocatoria;
	}

	public void setFechaConvocatoria(Date fechaConvocatoria) {
		this.fechaConvocatoria = fechaConvocatoria;
	}

	public Date getFechaAsesoramiento() {
		return fechaAsesoramiento;
	}

	public void setFechaAsesoramiento(Date fechaAsesoramiento) {
		this.fechaAsesoramiento = fechaAsesoramiento;
	}

	public Date getFechaRemisionDiploma() {
		return fechaRemisionDiploma;
	}

	public void setFechaRemisionDiploma(Date fechaRemisionDiploma) {
		this.fechaRemisionDiploma = fechaRemisionDiploma;
	}
	
	public boolean isErasable(){
		if(this.estatus.getId()==40 || this.estatus.getId()==37)
			return false;
		else
			return true;
	}
	
	public boolean isEditable(){
		if(this.estatus.getId()==40 || this.estatus.getId()==37)
			return false;
		else
			return true;
	}
}