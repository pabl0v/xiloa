package model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="laborales")
@NamedQueries ({
	@NamedQuery(name="Laboral.findById", query="select l from laborales l where l.id=?1"),
	@NamedQuery(name="Laboral.findAllByTipoAndContactoId", query="select l from laborales l where l.tipo = ?1 and l.contacto.id = ?2")
})
public class Laboral implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "laboral_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
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
	@Column(name = "laboral_institucion_telefono", nullable = true)
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
	
	public Laboral(Contacto contacto, int tipo, String nombre,
			String descripcion, String institucion, String pais,
			Date fechaInicia, Date fechaFinaliza, String institucionDireccion,
			String institucionTelefono, String cargo, Archivo archivo) {
		super();
		this.contacto = contacto;
		this.tipo = tipo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.institucion = institucion;
		this.pais = pais;
		this.fechaInicia = fechaInicia;
		this.fechaFinaliza = fechaFinaliza;
		this.institucionDireccion = institucionDireccion;
		this.institucionTelefono = institucionTelefono;
		this.cargo = cargo;
		this.archivo = archivo;
	}
	
	public Laboral() {
		super();		
	}
}