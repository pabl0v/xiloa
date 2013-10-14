package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="laborales")
public class Laboral {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "laboral_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="contacto_id")
	private Contacto contacto;
	
	@NotNull
	@Column(name = "laboral_tipo", nullable = false)
	private int tipo;
	
	@NotNull
	@Column(name = "laboral_nombre", nullable = false)
	private String nombre;
	
	@NotNull
	@Column(name = "laboral_descripcion", nullable = false)
	private String descripcion;
	
	@NotNull
	@Column(name = "laboral_institucion", nullable = false)
	private String institucion;
	
	@NotNull
	@Column(name = "laboral_pais", nullable = false)
	private String pais;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "laboral_fecha_inicia", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaInicia;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "laboral_fecha_finaliza", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaFinaliza;

	@NotNull
	@Column(name = "laboral_institucion_direccion", nullable = false)
	private String institucionDireccion;

	@NotNull
	@Column(name = "laboral_institucion_telefono", nullable = false)
	private String institucionTelefono;

	@NotNull
	@Column(name = "laboral_institucion_cargo", nullable = false)
	private String cargo;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="laboral_file_id")
	private Archivo archivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Date getFechaInicia() {
		return fechaInicia;
	}

	public void setFechaInicia(Date fechaInicia) {
		this.fechaInicia = fechaInicia;
	}

	public Date getFechaFinaliza() {
		return fechaFinaliza;
	}

	public void setFechaFinaliza(Date fechaFinaliza) {
		this.fechaFinaliza = fechaFinaliza;
	}

	public String getInstitucionDireccion() {
		return institucionDireccion;
	}

	public void setInstitucionDireccion(String institucionDireccion) {
		this.institucionDireccion = institucionDireccion;
	}

	public String getInstitucionTelefono() {
		return institucionTelefono;
	}

	public void setInstitucionTelefono(String institucionTelefono) {
		this.institucionTelefono = institucionTelefono;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}
}