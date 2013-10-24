package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="evaluacion")
public class Evaluacion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "evaluacion_id", nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "evaluacion_estado_inicial", nullable = false)
	private int estadoInicial;
	
	@NotNull
	@Column(name = "evaluacion_estado_siguiente", nullable = false)
	private int estadoSiguiente;
	
	@NotNull
	@Column(name = "evaluacion_aprobado", nullable = false)
	private boolean aprobado;
	
	@NotNull
	@Column(name = "evaluacion_descripcion", nullable = false)
	private String descripcion;
	
	@NotNull
	@Column(name = "evaluacion_instrumento", nullable = false)
	private String instrumento;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="solicitud_id")
	private Solicitud solicitud;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(int estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public int getEstadoSiguiente() {
		return estadoSiguiente;
	}

	public void setEstadoSiguiente(int estadoSiguiente) {
		this.estadoSiguiente = estadoSiguiente;
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Evaluacion(int estadoInicial, int estadoSiguiente, boolean aprobado,
			String descripcion, String instrumento, Solicitud solicitud) {
		super();
		this.estadoInicial = estadoInicial;
		this.estadoSiguiente = estadoSiguiente;
		this.aprobado = aprobado;
		this.descripcion = descripcion;
		this.instrumento = instrumento;
		this.solicitud = solicitud;
	}
	
	public Evaluacion() {
		super();		
	}
}