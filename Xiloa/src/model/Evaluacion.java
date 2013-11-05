package model;

import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="evaluaciones")
public class Evaluacion {

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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.evaluacion", cascade = CascadeType.ALL)
	private List<EvaluacionGuia> guias;
	
	@NotNull
	@Column(name = "evaluacion_puntaje", nullable = false)
	private Integer puntaje;

	@Column(name = "evaluacion_observaciones", nullable = false)
	private String observaciones;
			
	@NotNull
	@Column(name = "evaluacion_aprobado", nullable = false)
	private boolean aprobado;

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
		this.guias = new ArrayList<EvaluacionGuia>();		
	}

	public Evaluacion(Long id, Solicitud solicitud, Date fecha, Unidad unidad, List<EvaluacionGuia> guias, Integer puntaje, String observaciones, boolean aprobado) {
		super();
		this.id = id;
		this.solicitud = solicitud;
		this.fechaEvaluacion = fecha;
		this.unidad = unidad;
		this.guias = guias;
		this.puntaje = puntaje;
		this.observaciones = observaciones;
		this.aprobado = aprobado;
	}
}