package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity(name="historia")
public class Historia {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "historia_id", nullable = false)
	private Long id;

	@Column(name = "historia_tabla", nullable = false)
	private String tabla;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "historia_fecha", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(name = "historia_usuario", nullable = false)
	private String usuario;

	@Column(name = "historia_accion", nullable = false)
	private String accion;
	
	@Column(name = "historia_item", nullable = false)
	private String item;

	@Column(name = "historia_certificacion", nullable = false)
	private String certificacion;

	@Column(name = "historia_nombre", nullable = false)
	private String nombre;

	@Column(name = "historia_cambios", nullable = false)
	private String cambios;

	@Column(name = "historia_descripcion", nullable = false)
	private String descripcion;
	
	@Column(name = "historia_ip_origen", nullable = false)
	private String ipOrigen;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(String certificacion) {
		this.certificacion = certificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCambios() {
		return cambios;
	}

	public void setCambios(String cambios) {
		this.cambios = cambios;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIpOrigen() {
		return ipOrigen;
	}

	public void setIpOrigen(String ipOrigen) {
		this.ipOrigen = ipOrigen;
	}

	public Historia(String tabla, Date fecha, String usuario, String accion,
			String item, String certificacion, String nombre, String cambios,
			String descripcion, String ipOrigen) {
		super();
		this.tabla = tabla;
		this.fecha = fecha;
		this.usuario = usuario;
		this.accion = accion;
		this.item = item;
		this.certificacion = certificacion;
		this.nombre = nombre;
		this.cambios = cambios;
		this.descripcion = descripcion;
		this.ipOrigen = ipOrigen;
	}

	public Historia() {
		super();		
	}
}