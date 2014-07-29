package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * 
 * @author Denis Chavez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.unidades 
 * 
 */

@Entity(name = "unidades")
@Table(name = "unidades", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Unidad.findAll", query="select u from unidades u order by u.id desc"),
	@NamedQuery(name="Unidad.findAllByCertificacionId", query="select u from unidades u where u.certificacionId=?1 order by u.unidadId desc")
})
public class Unidad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="certificacion_id", nullable = false)
	private Long certificacionId;
	
	@Id
	@Column(name="unidad_id", nullable = false)
	private Long unidadId;
	
	@Formula("(select u.descripcion from registro_cobranza.cu_cat_uc u where u.id = unidad_id)")
	private String unidadNombre;
	
	public Unidad(){
		super();
	}
	
	public Unidad(Long certificacion, Long unidad){
		super();
		this.certificacionId = certificacion;
		this.unidadId = unidad;
	}

	public Long getCertificacionId() {
		return certificacionId;
	}

	public void setCertificacion(Long certificacion) {
		this.certificacionId = certificacion;
	}

	public Long getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Long unidadId) {
		this.unidadId = unidadId;
	}
	
	public String getUnidadNombre() {
		return unidadNombre;
	}
}