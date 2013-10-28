package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_fecha_registro", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_inicia", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date inicia;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_finaliza", nullable = true)
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
	private Usuario creador;			//revisar

	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_estatus")
	private Mantenedor estatus;
	
	@Column(name = "certificacion_referencial", nullable = false)
	private String referencial;
	
	@Column(name = "certificacion_nivel_competencia", nullable = false)
	private int nivelCompetencia;
	
	@OneToMany(mappedBy = "certificacion")
	private List<Requisito> requisitos;
	
	@OneToMany(mappedBy = "certificacion")
	private List<Unidad> unidades;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="cerfificacion_id", referencedColumnName="certificacion_id")
	private List<Actividad> actividades;
	
	@OneToMany(mappedBy = "certificacion")
	private List<Solicitud> solicitudes;
		
	@ManyToMany
	@JoinTable
	(
			name = "pinvolucrados",
			joinColumns = @JoinColumn(name = "certificacion_id", unique = false),
			inverseJoinColumns = @JoinColumn(name = "contacto_id", unique = false)
	)
	@MapKeyColumn(name="id_rol")
	private Map<Integer, Contacto> involucrados;
	
	public Certificacion(){
		super();
		this.requisitos = new ArrayList<Requisito>();
		this.unidades = new ArrayList<Unidad>();
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

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
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
	
	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	public List<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public Map<Integer, Contacto> getInvolucrados() {
		return involucrados;
	}

	public void setInvolucrados(Contacto[] involucrados) {
		for(int i=0; i<involucrados.length; i++){
			this.involucrados.put(involucrados[i].getRol().getId(), involucrados[i]);
		}
	}

	public Certificacion(	String nombre, 
							String descripcion, 
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
							List<Unidad> unidades,
							List<Actividad> actividades, 
							List<Solicitud> solicitudes,
							Map<Integer, Contacto> involucrados) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
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