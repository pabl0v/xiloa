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

@Entity(name="unidades")
public class Unidad {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "unidad_id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="certificacion_id")	
	private Certificacion certificacion;
	
	@NotNull
	@Column(name = "competencia_codigo", nullable = false)
	private String competenciaCodigo;

	@NotNull
	@Column(name = "competencia_descripcion", nullable = false)	
	private String competenciaDescripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Certificacion getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(Certificacion certificacion) {
		this.certificacion = certificacion;
	}

	public String getCompetenciaCodigo() {
		return competenciaCodigo;
	}

	public void setCompetenciaCodigo(String competenciaCodigo) {
		this.competenciaCodigo = competenciaCodigo;
	}

	public String getCompetenciaDescripcion() {
		return competenciaDescripcion;
	}

	public void setCompetenciaDescripcion(String competenciaDescripcion) {
		this.competenciaDescripcion = competenciaDescripcion;
	}

	public Unidad(Certificacion certificacion, String competenciaCodigo,
			String competenciaDescripcion) {
		super();
		this.certificacion = certificacion;
		this.competenciaCodigo = competenciaCodigo;
		this.competenciaDescripcion = competenciaDescripcion;
	}
	
	public Unidad() {
		super();		
	}
}