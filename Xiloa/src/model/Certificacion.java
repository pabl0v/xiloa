package model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="certificaciones")
public class Certificacion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "certificacion_id", nullable = false)
	private Long id;
	
	@Column(name = "certificacion_nombre", nullable = false)
	private String nombre;
	
	@Column(name = "certificacion_descripcion", nullable = false)
	private String descripcion;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_inicia", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date inicia;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_finaliza", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date finaliza;
	
	@Column(name = "certificacion_ifp_id", nullable = false)
	private int ifpId;					//revisar
	
	@Column(name = "certificacion_ifp_direccion", nullable = false)
	private String ifpDireccion;
	
	@Column(name = "certificacion_ifp_nombre", nullable = false)
	private String ifpNombre;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_programador_id")	
	private Usuario programador;		//revisar
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_divulgacion_inicia", nullable = false)
	@Temporal(TemporalType.DATE)	
	private Date divulgacionInicia;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_divulgacion_finaliza", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date divulgacionFinaliza;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_inscripcion_finaliza", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date inscripcionFinaliza;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_convocatoria_finaliza", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date convocatoriaFinaliza;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_evaluacion_finaliza", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date evaluacionFinaliza;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_creador_id")	
	private Usuario creador;			//revisar
	
	@Column(name = "certificacion_estatus", nullable = false)
	private int estatus;
	
	@Column(name = "certificacion_referencial", nullable = false)
	private String referencial;
	
	@Column(name = "certificacion_nivel_competencia", nullable = false)
	private int nivelCompetencia;
	
	@OneToMany(mappedBy = "certificacion")
	private List<Requisito> requisitos;
	
	@OneToMany(mappedBy = "certificacion")
	private List<Unidad> unidades;
	
	@OneToMany(mappedBy = "certificacion")
	private List<Actividad> actividades;
	
	@OneToMany(mappedBy = "certificacion")
	private List<Solicitud> solicitudes;
	
	@OneToMany
	@JoinTable
	(
			name = "pinvolucrados",
			joinColumns = { @JoinColumn(name = "certificacion_id", referencedColumnName = "certificacion_id") },
			inverseJoinColumns = { @JoinColumn(name = "contacto_id", referencedColumnName = "contacto_id", unique = true) }
	)
	private List<Contacto> involucrados;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getInicia() {
		return inicia;
	}

	public void setInicia(Date inicia) {
		this.inicia = inicia;
	}

	public Date getFinaliza() {
		return finaliza;
	}

	public void setFinaliza(Date finaliza) {
		this.finaliza = finaliza;
	}

	public int getIfpId() {
		return ifpId;
	}

	public void setIfpId(int ifpId) {
		this.ifpId = ifpId;
	}

	public String getIfpDireccion() {
		return ifpDireccion;
	}

	public void setIfpDireccion(String ifpDireccion) {
		this.ifpDireccion = ifpDireccion;
	}

	public String getIfpNombre() {
		return ifpNombre;
	}

	public void setIfpNombre(String ifpNombre) {
		this.ifpNombre = ifpNombre;
	}

	public Usuario getProgramador() {
		return programador;
	}

	public void setProgramador(Usuario programador) {
		this.programador = programador;
	}

	public Date getDivulgacionInicia() {
		return divulgacionInicia;
	}

	public void setDivulgacionInicia(Date divulgacionInicia) {
		this.divulgacionInicia = divulgacionInicia;
	}

	public Date getDivulgacionFinaliza() {
		return divulgacionFinaliza;
	}

	public void setDivulgacionFinaliza(Date divulgacionFinaliza) {
		this.divulgacionFinaliza = divulgacionFinaliza;
	}

	public Date getInscripcionFinaliza() {
		return inscripcionFinaliza;
	}

	public void setInscripcionFinaliza(Date inscripcionFinaliza) {
		this.inscripcionFinaliza = inscripcionFinaliza;
	}

	public Date getConvocatoriaFinaliza() {
		return convocatoriaFinaliza;
	}

	public void setConvocatoriaFinaliza(Date convocatoriaFinaliza) {
		this.convocatoriaFinaliza = convocatoriaFinaliza;
	}

	public Date getEvaluacionFinaliza() {
		return evaluacionFinaliza;
	}

	public void setEvaluacionFinaliza(Date evaluacionFinaliza) {
		this.evaluacionFinaliza = evaluacionFinaliza;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public String getReferencial() {
		return referencial;
	}

	public void setReferencial(String referencial) {
		this.referencial = referencial;
	}

	public int getNivelCompetencia() {
		return nivelCompetencia;
	}

	public void setNivelCompetencia(int nivelCompetencia) {
		this.nivelCompetencia = nivelCompetencia;
	}

	public List<Requisito> getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(List<Requisito> requisitos) {
		this.requisitos = requisitos;
	}

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	public List<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public List<Contacto> getInvolucrados() {
		return involucrados;
	}

	public void setInvolucrados(List<Contacto> involucrados) {
		this.involucrados = involucrados;
	}	
}