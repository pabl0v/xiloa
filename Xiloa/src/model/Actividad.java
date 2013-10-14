package model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="actividades")
public class Actividad {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "actividad_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="certificacion_id")	
	private Certificacion certificacion;

	@Column(name = "actividad_tipo_id", nullable = false)
	private int tipoId;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "actividad_fecha_inicial", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaInicial;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "actividad_fecha_final", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaFinal;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="actividad_creador_id")
	private Usuario creadorId;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="actividad_ejecutor_id")
	private Usuario ejecutorId;
	
	@NotNull
	@Column(name = "actividad_nombre", nullable = false)	
	private String nombre;
	
	@NotNull
	@Column(name = "actividad_descripcion", nullable = false)
	private String descripcion;
	
	@NotNull
	@Column(name = "actividad_destino", nullable = false)
	private String destino;

	@OneToMany
	@JoinTable
	(
			name = "ainvolucrados",
			joinColumns = { @JoinColumn(name = "actividad_id", referencedColumnName = "actividad_id") },
			inverseJoinColumns = { @JoinColumn(name = "contacto_id", referencedColumnName = "contacto_id", unique = true) }
	)	
	private List<Contacto> involucrados;
	
	@OneToMany(mappedBy="actividad")
	private List<Bitacora> bitarora;

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

	public int getTipoId() {
		return tipoId;
	}

	public void setTipoId(int tipoId) {
		this.tipoId = tipoId;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Usuario getCreadorId() {
		return creadorId;
	}

	public void setCreadorId(Usuario creadorId) {
		this.creadorId = creadorId;
	}

	public Usuario getEjecutorId() {
		return ejecutorId;
	}

	public void setEjecutorId(Usuario ejecutorId) {
		this.ejecutorId = ejecutorId;
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

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public List<Contacto> getInvolucrados() {
		return involucrados;
	}

	public void setInvolucrados(List<Contacto> involucrados) {
		this.involucrados = involucrados;
	}

	public List<Bitacora> getBitarora() {
		return bitarora;
	}

	public void setBitarora(List<Bitacora> bitarora) {
		this.bitarora = bitarora;
	}	
}