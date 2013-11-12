package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="requisitos")
public class Requisito {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "requisito_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_id")
	private Certificacion certificacion;
		
	@NotNull
	@Column(name = "requisito_codigo", nullable = false)
	private String codigo;

	@NotNull
	@Column(name = "requisito_descripcion", nullable = false)
	private String descripcion;
	
	@NotNull
	@Column(name = "requisito_codigo_acreditacion", nullable = false)
	private String acreditacion;
	
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
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAcreditacion() {
		return acreditacion;
	}

	public void setAcreditacion(String acreditacion) {
		this.acreditacion = acreditacion;
	}
	
	public Requisito() {
		super();		
	}

	public Requisito(Certificacion certificacion, String codigo, String descripcion, String acreditacion) {
		super();
		this.certificacion = certificacion;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.acreditacion = acreditacion;
	}
}