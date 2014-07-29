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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({
	@NamedQuery(name="Unidad.findAll", query="select u from unidades u order by u.id desc"),
	@NamedQuery(name="Unidad.findAllByCertificacionId", query="select u from unidades u where u.certificacion.id=?1 order by u.id desc")
})
public class Unidad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="certificacion_id", nullable = false)
	private Certificacion certificacion;
	
	@Column(name="unidad_id", nullable = false)
	private Long unidadId;
	
	@Formula("(select u.descripcion from registro_cobranza.cu_cat_uc u where i.id = unidad_id)")
	private String unidadNombre;
	
	public Unidad(){
		super();
	}
	
	public Unidad(Certificacion certificacion, Long unidad){
		this.certificacion = certificacion;
		this.unidadId = unidad;
	}

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