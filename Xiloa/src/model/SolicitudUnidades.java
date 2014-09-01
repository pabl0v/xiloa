package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity(name = "solicitud_unidades")
@Table(name = "solicitud_unidades", schema = "sccl", uniqueConstraints = @UniqueConstraint(columnNames={"solicitud_id", "unidad_id"}))
@NamedQueries({
	@NamedQuery(name="SolicitudUnidades.findAllItemsBySolicitudId", query="select new support.Item(u.unidad,u.unidadNombre) from solicitud_unidades u where u.solicitud.id=?1 order by u.id")
})
public class SolicitudUnidades implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name="solicitud_id", nullable = false)		
	private Solicitud solicitud;
	
	@Column(name="unidad_id", nullable = false)
	private Long unidad;
	
	@Formula("(select coalesce((select u.descripcion from registro_cobranza.cu_cat_uc u where u.id = unidad_id),'N/D'))")
	private String unidadNombre;
	
	public SolicitudUnidades(){
		super();
	}
	
	public SolicitudUnidades(Solicitud solicitud, Long unidad){
		super();
		this.solicitud = solicitud;
		this.unidad = unidad;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id= id;
	}
	
	public Solicitud getSolicitud() {
		return solicitud;
	}
	
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}
	
	public Long getUnidad() {
		return unidad;
	}
	
	public void setUnidad(Long unidad) {
		this.unidad = unidad;
	}
	
	public String getUnidadNombre(){
		return unidadNombre;
	}
}