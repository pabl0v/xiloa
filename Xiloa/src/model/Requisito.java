package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="requisitos")
public class Requisito {
	
	@Id
	@Column(name = "requisitos_id", nullable = false)
	private String requisitoId;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="certificacion_id")
	private Certificacion certificacion;
	
	@Column(name = "requisitos_descripcion", nullable = false)
	private String descripcion;
	
	@Column(name = "requisitos_vigencia", nullable = false)
	private String vigencia;
	
	@Column(name = "requisitos_acreditacion", nullable = false)
	private String acreditacion;
	
	@Column(name = "requisitos_calificacion", nullable = false)
	private String calificacion;

	public String getRequisitoId() {
		return requisitoId;
	}

	public void setRequisitoId(String requisitoId) {
		this.requisitoId = requisitoId;
	}

	public Certificacion getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(Certificacion certificacion) {
		this.certificacion = certificacion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public String getAcreditacion() {
		return acreditacion;
	}

	public void setAcreditacion(String acreditacion) {
		this.acreditacion = acreditacion;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}
}