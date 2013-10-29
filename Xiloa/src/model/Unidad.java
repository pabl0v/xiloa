package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="unidades")
public class Unidad {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "unidad_id", nullable = false)
	private Long id;
	
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

	public Unidad(String competenciaCodigo, String competenciaDescripcion) {
		super();
		this.competenciaCodigo = competenciaCodigo;
		this.competenciaDescripcion = competenciaDescripcion;
	}
	
	public Unidad() {
		super();		
	}
}