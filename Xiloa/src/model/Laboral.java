package model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
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
 * Esta clase se corresponde con la tabla sccl.laborales 
 * 
 */

@Entity(name = "laborales")
@Table(name = "laborales", schema = "sccl")
@NamedQueries ({
	@NamedQuery(name="Laboral.findById", query="select l from laborales l where l.id=?1"),
	@NamedQuery(name="Laboral.findAllByContactoId", query="select l from laborales l inner join fetch l.contacto c where l.tipo in (23,24,25,26) and c.id = ?1"),
	@NamedQuery(name="Laboral.findAllByTipoAndContactoId", query="select l from laborales l inner join fetch l.contacto c where l.tipo = ?1 and c.id = ?2")
})
public class Laboral implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "laboral_id", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="contacto_id", nullable = false)
	private Contacto contacto;
	
	@Column(name = "laboral_tipo", nullable = false)
	private Integer tipo;
	
	@Column(name = "laboral_nombre", nullable = false)
	private String nombre;
	
	@Column(name = "laboral_descripcion", nullable = true)
	private String descripcion;
	
	@Column(name = "laboral_institucion", nullable = false)
	private String institucion;
	
	@Column(name="laboral_pais_id")
	private String pais;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "laboral_fecha_inicia", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaInicia;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "laboral_fecha_finaliza", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaFinaliza;

	@Column(name = "laboral_institucion_direccion", nullable = false)
	private String institucionDireccion;

	@Column(name = "laboral_institucion_telefono", nullable = false)
	private String institucionTelefono;

	@Column(name = "laboral_institucion_cargo", nullable = false)
	private String cargo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="laboral", cascade = CascadeType.ALL)
	private Set<Archivo> archivo;
	
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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
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
			
	public Set<Archivo> getArchivo() {
		return archivo;
	}

	public void setArchivo(Set<Archivo> archivo) {
		this.archivo = archivo;
	}

		
	public Laboral(	Contacto contacto,
					Integer tipo,
					String nombre,
					String descripcion, 
					String institucion, 
					String pais,
					Date fechaInicia, 
					Date fechaFinaliza, 
					String institucionDireccion,
					String institucionTelefono, 
					String cargo, 
					Set<Archivo> archivo) {
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
		this.archivo = new HashSet<Archivo>();
	}
}