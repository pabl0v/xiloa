package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name = "unidades", uniqueConstraints=@UniqueConstraint(columnNames={"certificacion_id", "unidad_id"}), schema = "sccl")
public class Unidad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name="certificacion_id", nullable = false)
	private Long certificacionId;
	
	@Column(name="unidad_id", nullable = false)
	private Long unidadId;
	
	@Formula("(select u.descripcion from registro_cobranza.cu_cat_uc u where i.id = unidad_id)")
	private String unidadNombre;
	
	public Unidad(){
		super();
	}
	
	public Unidad(Long certificacion, Long unidad){
		this.certificacionId = certificacion;
		this.unidadId = unidad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCertificacionId() {
		return certificacionId;
	}

	public void setCertificacionId(Long certificacionId) {
		this.certificacionId = certificacionId;
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