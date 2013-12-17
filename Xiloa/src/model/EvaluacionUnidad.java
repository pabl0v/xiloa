package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name = "evaluaciones_unidad")
@Table(name = "evaluaciones_unidad", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="EvaluacionUnidad.findAllBySolicitudUCL", query="select e from evaluaciones_unidad e where e.solicitud.id=?1 and e.unidad=?2")
})
public class EvaluacionUnidad implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "evaluacion_unidad_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="evaluacion_unidad_solicitud_id")
	private Solicitud solicitud;
	
	@Column(name = "evaluacion_ucl_id", nullable = false)
	private Long unidad;
	
	@Column(name = "evaluacion_unidad_aprobado", nullable = true)
	private boolean aprobado = false;
	
	@ManyToOne
	@JoinColumn(name="evaluacion_unidad_estado", nullable=false)	
	private Mantenedor estado;

	public Long getId() {
		return id;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Long getUnidad() {
		return unidad;
	}

	public void setUnidad(Long unidad) {
		this.unidad = unidad;
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}

	public Mantenedor getEstado() {
		return estado;
	}

	public void setEstado(Mantenedor estado) {
		this.estado = estado;
	}

	public EvaluacionUnidad() {
		super();
	}

	public EvaluacionUnidad(Solicitud solicitud, Long unidad,
			boolean aprobado, Mantenedor estado) {
		super();
		this.solicitud = solicitud;
		this.unidad = unidad;
		this.aprobado = aprobado;
		this.estado = estado;
	}
	
	
}
