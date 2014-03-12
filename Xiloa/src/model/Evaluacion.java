package model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.evaluaciones 
 * 
 */

@Entity(name = "evaluaciones")
@Table(name = "evaluaciones", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Evaluacion.findAllPendientesByFirstSolicitudByContactoId", query="select e from evaluaciones e where e.solicitud.contacto.id=?1 and e.solicitud.id=(select min(x.id) from solicitudes x where x.contacto=e.solicitud.contacto and x.estatus.id!=76) order by e.id desc"),
	@NamedQuery(name="Evaluacion.findAllPendientesBySolicitudId", query="select e from evaluaciones e inner join fetch e.solicitud s where e.aprobado=false and s.id=?1 order by e.id desc"),
	//@NamedQuery(name="Evaluacion.findAllBySolicitudId", query="select e from evaluaciones e where e.solicitud.id=?1 order by e.id desc"),
	//@NamedQuery(name="Evaluacion.findAllBySolicitudId", query="select e from evaluaciones e inner join fetch e.instrumento inner join fetch e.solicitud s where s.id=?1 order by e.id desc"),
	@NamedQuery(name="Evaluacion.findAllBySolicitudId", query="select e from evaluaciones e inner join fetch e.solicitud s where s.id=?1 order by e.id desc"),
	@NamedQuery(name="Evaluacion.findById", query="select e from evaluaciones e where e.id=?1"),
	@NamedQuery(name="Evaluacion.findAllBySolicitudUCL", query="select e from evaluaciones e inner join fetch e.solicitud s where s.id=?1 and e.unidad=?2"),
	//dchavez: 01/03/2014. NamedQuery para obtener el resumen de aprobado/reprobado por cada unidad de competencia evaluada en una solicitud.
	@NamedQuery(name="Evaluacion.findAllUnidadesBySolicitudId", query="select new support.Item(e.unidad, min(case e.aprobado when true then '1' else '0' end)) from evaluaciones e where e.solicitud.id=?1 group by e.unidad having count(e.id)>2 order by 1")
})
public class Evaluacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String tipoMantenedorEstado = new String("9");

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "evaluacion_id", nullable = false)
	private Long id;
	
	/**
	 * dchavez, 16/02/2014: agregando instrumento a la entidad evaluacion
	 */
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="evaluacion_instrumento_id")
	private Instrumento instrumento;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="evaluacion_solicitud_id")
	private Solicitud solicitud;
				
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "evaluacion_fecha", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaEvaluacion;
	
	@NotNull
	@Column(name = "evaluacion_unidad_id", nullable = false)
	private Long unidad;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.evaluacion")
	private Set<EvaluacionGuia> guias;
	
	@NotNull
	@Column(name = "evaluacion_puntaje", nullable = false)
	private Integer puntaje;

	@Column(name = "evaluacion_observaciones", nullable = true)
	private String observaciones;
			
	@Column(name = "evaluacion_aprobado", nullable = true)
	private boolean aprobado;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="evaluacion_estado", nullable=false)	
	private Mantenedor estado;
	
	public Mantenedor getEstado() {
		return estado;
	}

	public void setEstado(Mantenedor estado) {
		this.estado = estado;
	}

	public String getTipoMantenedorEstado() {
		return tipoMantenedorEstado;
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
	
	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public Date getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(Date fecha) {
		this.fechaEvaluacion = fecha;
	}
		
	public Set<EvaluacionGuia> getGuias() {
		return guias;
	}

	public void setGuias(Set<EvaluacionGuia> guias) {
		this.guias = guias;
	}

	public Integer getPuntaje(){
		return puntaje;
	}
	
	public void setPuntaje(Integer puntaje){
		this.puntaje = puntaje;
	}

	public Long getUnidad() {
		return unidad;
	}

	public void setUnidad(Long unidad) {
		this.unidad = unidad;
	}
	
	public String getObservaciones(){
		return observaciones;
	}
	
	public void setObservaciones(String observaciones){
		this.observaciones = observaciones;
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}

	public Evaluacion() {
		super();
		this.guias = new HashSet<EvaluacionGuia>();		
	}

	public Evaluacion(Solicitud solicitud, Instrumento instrumento, Date fecha, Long unidad, Set<EvaluacionGuia> guias, Integer puntaje, String observaciones, boolean aprobado, Mantenedor estado) {
		super();		
		this.solicitud = solicitud;
		this.instrumento = instrumento;
		this.fechaEvaluacion = fecha;
		this.unidad = unidad;
		this.guias = guias;
		this.puntaje = puntaje;
		this.observaciones = observaciones;
		this.aprobado = aprobado;
		this.estado = estado;
	}
}