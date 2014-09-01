package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@NamedQuery(name="Unidad.findAllItems", query="select new support.Item(u.unidadId,u.unidadNombre) from unidades u group by u.unidadId, u.unidadNombre order by u.unidadId desc"),
	@NamedQuery(name="Unidad.findAllByCertificacionId", query="select u from unidades u where u.certificacionId=?1 order by u.unidadId desc"),
	@NamedQuery(name="Unidad.findAllItemsByCertificacionId", query="select new support.Item(u.unidadId,u.unidadNombre) from unidades u where u.certificacionId=?1 order by u.unidadId desc"),
	//@NamedQuery(name="Unidad.findAllSinEvaluarBySolicitudId", query="select u from unidades u where u.certificacionId=(select s.certificacion.id from solicitudes s where s.id=?1) and not exists (select 1 from instrumentos i where i.id in (select e.instrumento.id from evaluaciones e where e.solicitud.id=?1 and e.activo=true) and i.tipo.id in (30,31,32) and i.unidad=u.unidadId and i.unidad is not null) order by u.unidadId desc")
	@NamedQuery(name="Unidad.findAllSinEvaluarBySolicitudId", query="select u from unidades u, solicitud_unidades x where u.unidadId=x.unidad and x.solicitud.id=?1 and x.unidad not in (select i.unidad from instrumentos i , evaluaciones e where e.instrumento.id=i.id and e.activo=true and e.solicitud.id=x.solicitud.id and i.unidad is not null and i.tipo.id in (30,31,32,34))")
})
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
	
	@Formula("(select coalesce((select u.descripcion from registro_cobranza.cu_cat_uc u where u.id = unidad_id),'N/D'))")
	private String unidadNombre;
	
	public Unidad(){
		super();
	}
	
	public Unidad(Long certificacion, Long unidad){
		super();
		this.certificacionId = certificacion;
		this.unidadId = unidad;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
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