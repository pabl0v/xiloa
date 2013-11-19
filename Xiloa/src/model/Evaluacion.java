package model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="evaluaciones")
@NamedQueries({
	@NamedQuery(name="Evaluacion.findAllBySolicitudId", query="select e from evaluaciones e where e.solicitud.id=?1")
})
public class Evaluacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String tipoMantenedorEstado = new String("9");

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "evaluacion_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="evaluacion_solicitud_id")
	private Solicitud solicitud;
				
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "evaluacion_fecha", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaEvaluacion;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="evaluacion_unidad_id")
	private Unidad unidad;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.evaluacion")
	private Set<EvaluacionGuia> guias;
	
	@NotNull
	@Column(name = "evaluacion_puntaje", nullable = false)
	private Integer puntaje;

	@Column(name = "evaluacion_observaciones", nullable = false)
	private String observaciones;
			
	@NotNull
	@Column(name = "evaluacion_aprobado", nullable = false)
	private boolean aprobado;
	
	@Column(name = "evaluacion_estado", nullable=false)
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

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
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

	public Evaluacion(Solicitud solicitud, Date fecha, Unidad unidad, Set<EvaluacionGuia> guias, Integer puntaje, String observaciones, boolean aprobado) {
		super();		
		this.solicitud = solicitud;
		this.fechaEvaluacion = fecha;
		this.unidad = unidad;
		this.guias = guias;
		this.puntaje = puntaje;
		this.observaciones = observaciones;
		this.aprobado = aprobado;
	}
}