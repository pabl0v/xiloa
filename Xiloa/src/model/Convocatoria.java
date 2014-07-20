package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * 
 * @author Denis Chavez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.convocatorias 
 * 
 */

@Entity(name = "convocatorias")
@Table(name = "convocatorias", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Convocatoria.findAllBySolicitudId", query="select c from convocatorias c where c.solicitudId=?1 order by c.id desc")
})
public class Convocatoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "convocatoria_id", nullable = false)
	private Long id;
	
	@NotNull
	@Column(name="solicitud_id", nullable = false)	
	private Long solicitudId;
	
	@NotNull
	@Column(name="actividad_id", nullable = false)	
	private Long actividadId;
	
	@Formula("(select a.actividad_nombre from sccl.actividades a where a.actividad_id = actividad_id)")
	private String actividadNombre;
	
	@NotNull
	@Column(name="contacto_id", nullable = false)	
	private Long contactoId;
	
	@Formula("(select c.nombre_completo from sccl.contactos c where c.contacto_id = contacto_id)")
	private String contactoNombre;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_programacion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@NotNull
	@Column(name="estado_id", nullable = false)	
	private Integer estadoId;

	@Formula("(select m.mantenedor_valor from sccl.mantenedores m where m.mantenedor_id = estado_id)")
	private String estadoNombre;
	
	public Convocatoria(){
		super();
	}

	public Convocatoria(Long solicitud, Long actividad, Long contacto, Date fecha, Integer estado){
		this.solicitudId = solicitud;
		this.actividadId = actividad;
		this.contactoId = contacto;
		this.fecha = fecha;
		this.estadoId = estado;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSolicitudId() {
		return solicitudId;
	}

	public void setSolicitudId(Long solicitud) {
		this.solicitudId = solicitud;
	}

	public Long getActividadId() {
		return actividadId;
	}

	public void setActividadId(Long actividad) {
		this.actividadId = actividad;
	}
	
	public String getActividadNombre(){
		return this.actividadNombre;
	}

	public Long getContactoId() {
		return contactoId;
	}

	public void setContactoId(Long contacto) {
		this.contactoId = contacto;
	}
	
	public String getContactoNombre(){
		return this.contactoNombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Integer estado) {
		this.estadoId = estado;
	}
	
	public String getEstadoNombre(){
		return this.estadoNombre;
	}
}