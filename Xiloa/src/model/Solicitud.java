package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	@NamedQuery(name="Solicitud.findAll", query="select s from solicitudes s where s.estatus.id != 45 and s.certificacion.ifpId = case ?1 when 1000 then s.certificacion.ifpId else ?1 end order by s.id desc"),
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
	
	@ManyToOne
	@JoinColumn(name="solicitud_estatus", nullable = false)		
	private Mantenedor estatus;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_registro", nullable = false)
	@Temporal(TemporalType.DATE)	
	private Date fechaRegistro;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_actualiza", nullable = true)
	@Temporal(TemporalType.DATE)	
	private Date fechaActualiza;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_matricula", nullable = true)
	@Temporal(TemporalType.DATE)	
	private Date fechaMatricula;
	
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

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="contacto_id", nullable = false)
	private Contacto contacto;
	
	@ManyToOne
	@JoinColumn(name="certificacion_id", nullable = false)	
	private Certificacion certificacion;
	
	@OneToMany(mappedBy="solicitud", fetch = FetchType.LAZY)
	private List<Evaluacion> evaluaciones;
	
	@Column(name="id_matricula", nullable=true)
	private Integer idMatricula;
	
	@Column(name="resultado_evaluacion", nullable=true)
	private boolean resultadoEvaluacion = false;
	
	@Column(name = "empresa", nullable = true)
	private String empresa;
	
	@Column(name = "situacion_laboral", nullable = true)
	private boolean situacion_laboral = false;
	
	@Column(name = "recibo_matricula", nullable = true)
	private String reciboMatricula;
		
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
	
	public Date getFechaActualiza() {
		return fechaActualiza;
	}

	public void setFechaActualiza(Date fecha) {
		this.fechaActualiza = fecha;
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

	public Solicitud(Mantenedor estatus,
			Date fechaRegistro, Date fechaMatricula, int experiencia,
			String ocupacion, String oficio, int escolaridad,
			Contacto contacto, Certificacion certificacion,
			List<Evaluacion> evaluaciones, Integer idMatricula, boolean resultadoEvaluacion,
			String empresa, boolean situacion_laboral) {
		super();
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

	public Date getFechaRemisionDiploma() {
		return fechaRemisionDiploma;
	}

	public void setFechaRemisionDiploma(Date fechaRemisionDiploma) {
		this.fechaRemisionDiploma = fechaRemisionDiploma;
	}
	
	public boolean isErasable(){
		if(this.estatus.getId()==42 || this.estatus.getId()==43 || this.estatus.getId()==44)
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

	public String getReciboMatricula() {
		return reciboMatricula;
	}

	public void setReciboMatricula(String reciboMatricula) {
		this.reciboMatricula = reciboMatricula;
	}
	
	public boolean getHabilitarEnviar(){
		if(estatus != null && estatus.getId()==35)		//si registrado entonces se puede enviar
			return true;
		else
			return false;
	}
	
	public boolean getHabilitarAutorizar(){
		if(estatus != null && estatus.getId()==36)		//si enviado entonces puede se autorizar
			return true;
		else
			return false;
	}
	
	public boolean getHabilitarMatricular(){
		if(estatus != null && estatus.getId()==37)		//si autorizado entonces matricular
			return true;
		else
			return false;
	}
	
	public boolean getHabilitarEvaluar(){
		if(estatus != null && (estatus.getId()==36 || estatus.getId()==40 || estatus.getId()==41))		//si enviado, asesoria individual o programado entonces se puede evaluar
			return true;
		else
			return false;
	}
	
	public boolean getHabilitarConvocar(){
		if(estatus != null && (estatus.getId()==38 || estatus.getId()==39 || estatus.getId()==40))		//si matriculado, asesoria grupal o individual entonces se puede convocar
			return true;
		else
			return false;
	}
	
	public boolean getHabilitarAnular(){
		if(estatus != null && (estatus.getId()==42 || estatus.getId()==43 || estatus.getId()==44 || estatus.getId()==45))		//si apto, no apto, rechazado o anulado entonces no se puede anular
			return false;
		else
			return true;
	}
}