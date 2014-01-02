package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.auditoria 
 * 
 */

@Entity(name="auditoria")
@Table(name = "auditoria", schema = "sccl")
public class Auditoria {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "auditoria_id", nullable = false)
	private Long id;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "auditoria_fecha_registro", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name="auditoria_contacto_id")
	private Contacto contacto;
	
	@Column(name = "auditoria_accion", nullable = false, columnDefinition="TEXT")
	private String accion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	public Auditoria(){
		super();
	}
	
	public Auditoria(Date fecha, Contacto contacto, String accion){
		super();
		this.fecha = fecha;
		this.contacto = contacto;
		this.accion = accion;
	}
}