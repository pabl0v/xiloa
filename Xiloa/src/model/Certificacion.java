package model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
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
	
	@Column(name = "certificacion_codigo_competencia", nullable = false)
	private String codigoCompetencia;
	
	@Column(name = "certificacion_nombre_competencia", nullable = false)
	private String nombreCompetencia;
	
	@Column(name = "certificacion_disponibilidad", nullable = false)
	private int disponibilidad;
	
	@Column(name = "certificacion_costo", nullable = false)
	private float costo;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_fecha_registro", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_fecha_actualiza", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaActualiza;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_inicia", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date inicia;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_finaliza", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date finaliza;
	
	@Column(name = "certificacion_ifp_id", nullable = false)
	private int ifpId;
	
	@Column(name = "certificacion_ifp_direccion", nullable = false)
	private String ifpDireccion;
	
	@Column(name = "certificacion_ifp_nombre", nullable = false)
	private String ifpNombre;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_programador_id")	
	private Usuario programador;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_divulgacion_inicia", nullable = false)
	@Temporal(TemporalType.DATE)	
	private Date divulgacionInicia;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_divulgacion_finaliza", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date divulgacionFinaliza;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_inscripcion_finaliza", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date inscripcionFinaliza;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_convocatoria_inicia", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date convocatoriaInicia;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_evaluacion_inicia", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date evaluacionInicia;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_creador_id")	
	private Usuario creador;
	
	@ManyToOne
	@JoinColumn(name="certificacion_actualiza_id")	
	private Usuario actualiza;

	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_estatus")
	private Mantenedor estatus;
	
	@Column(name = "certificacion_referencial", nullable = false)
	private String referencial;
	
	@Column(name = "certificacion_nivel_competencia", nullable = false)
	private int nivelCompetencia;
	
	@OneToMany//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="certificacion_id", referencedColumnName="certificacion_id")
	private Set<Unidad> unidades;
	
	@OneToMany(mappedBy = "certificacion")
	private List<Requisito> requisitos;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="certificacion_id", referencedColumnName="certificacion_id")
	private List<Actividad> actividades;
	
	@OneToMany(mappedBy = "certificacion")
	private List<Solicitud> solicitudes;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	(
			name = "pinvolucrados",
			joinColumns = @JoinColumn(name = "certificacion_id", unique = false),
			inverseJoinColumns = @JoinColumn(name = "contacto_id", unique = false)
	)
	@MapKeyColumn(name="id_rol")
	private Map<Integer, Contacto> involucrados;
	
	public Contacto getCoordinador(){
		return involucrados.get(2);
	}
	
	public Contacto getEvaluador(){
		return involucrados.get(3);
	}
	
	public Certificacion(){
		super();
		this.requisitos = new ArrayList<Requisito>();
		this.unidades = new HashSet<Unidad>();
		this.disponibilidad = 0;
		this.actividades = new ArrayList<Actividad>();
		this.solicitudes = new ArrayList<Solicitud>();
		this.involucrados = new HashMap<Integer, Contacto>();
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoCompetencia() {
		return codigoCompetencia;
	}

	public void setCodigoCompetencia(String codigoCompetencia) {
		this.codigoCompetencia = codigoCompetencia;
	}

	public String getNombreCompetencia() {
		return nombreCompetencia;
	}

	public void setNombreCompetencia(String nombreCompetencia) {
		this.nombreCompetencia = nombreCompetencia;
	}

	public int getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getCostoFormateado() {
		NumberFormat format = NumberFormat.getInstance(Locale.US);
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);
		return format.format(costo);
	}
	
	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
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

	public void setFechaActualiza(Date fechaActualiza) {
		this.fechaActualiza = fechaActualiza;
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

	public Date getConvocatoriaInicia() {
		return convocatoriaInicia;
	}

	public void setConvocatoriaInicia(Date convocatoriaInicia) {
		this.convocatoriaInicia = convocatoriaInicia;
	}

	public Date getEvaluacionInicia() {
		return evaluacionInicia;
	}

	public void setEvaluacionInicia(Date evaluacionInicia) {
		this.evaluacionInicia = evaluacionInicia;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public Usuario getActualiza() {
		return actualiza;
	}

	public void setActualiza(Usuario actualiza) {
		this.actualiza = actualiza;
	}

	public Mantenedor getEstatus() {
		return estatus;
	}

	public void setEstatus(Mantenedor estatus) {
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

	public Set<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(Set<Unidad> unidades) {
		this.unidades = unidades;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(Actividad[] actividades) {
		for(int i=0; i<actividades.length; i++){
			this.actividades.add(actividades[i]);
		}
	}
	
	public void addActividad(Actividad actividad){
		this.actividades.add(actividad);
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

	/*
	public Map<Integer, Contacto> getInvolucrados() {
		return involucrados;
	}*/
	
	public List<Contacto> getInvolucrados() {
		return new ArrayList<Contacto>(involucrados.values());
	}	

	public void setInvolucrados(Contacto[] involucrados) {
		for(int i=0; i<involucrados.length; i++){
			this.involucrados.put(involucrados[i].getRol().getId(), involucrados[i]);
		}
	}

	public Certificacion(	String nombre, 
							String descripcion,
							String codigoCompetencia,
							String nombreCompetencia,
							int disponibilidad,
							Date inicia,
							Date finaliza, 
							int ifpId, 
							String ifpDireccion, 
							String ifpNombre,
							Usuario programador, 
							Date divulgacionInicia,
							Date divulgacionFinaliza, 
							Date inscripcionFinaliza,
							Date convocatoriaInicia, 
							Date evaluacionInicia, 
							Usuario creador,
							Mantenedor estatus, 
							String referencial, 
							int nivelCompetencia,
							List<Requisito> requisitos, 
							Set<Unidad> unidades,
							List<Actividad> actividades, 
							List<Solicitud> solicitudes,
							Map<Integer, Contacto> involucrados) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.codigoCompetencia = codigoCompetencia;
		this.nombreCompetencia = nombreCompetencia;
		this.disponibilidad = disponibilidad;
		this.inicia = inicia;
		this.finaliza = finaliza;
		this.ifpId = ifpId;
		this.ifpDireccion = ifpDireccion;
		this.ifpNombre = ifpNombre;
		this.programador = programador;
		this.divulgacionInicia = divulgacionInicia;
		this.divulgacionFinaliza = divulgacionFinaliza;
		this.inscripcionFinaliza = inscripcionFinaliza;
		this.convocatoriaInicia = convocatoriaInicia;
		this.evaluacionInicia = evaluacionInicia;
		this.creador = creador;
		this.estatus = estatus;
		this.referencial = referencial;
		this.nivelCompetencia = nivelCompetencia;
		this.requisitos = requisitos;
		this.unidades = unidades;
		this.actividades = actividades;
		this.solicitudes = solicitudes;
		this.involucrados = involucrados;
	}
}