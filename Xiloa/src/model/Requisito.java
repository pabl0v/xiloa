package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.requisitos 
 * 
 */

@Entity(name = "requisitos")
@Table(name = "requisitos", schema = "sccl")
public class Requisito implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "requisito_id", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="certificacion_id", nullable = false)
	private Certificacion certificacion;
		
	@Column(name = "requisito_codigo", nullable = false)
	private String codigo;

	@Column(name = "requisito_descripcion", nullable = false)
	private String descripcion;
	
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