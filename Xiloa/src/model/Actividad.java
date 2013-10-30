package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

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
	@ManyToOne
	@JoinColumn(name="actividad_tipo_id")
	private Mantenedor tipo;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "actividad_fecha_inicial", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaInicial;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "actividad_fecha_final", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaFinal;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="actividad_creador_id")
	private Usuario creador;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="actividad_ejecutor_id")
	private Usuario ejecutor;
	
	@NotNull
	@Column(name = "actividad_nombre", nullable = false)	
	private String nombre;
	
	@NotNull
	@Column(name = "actividad_descripcion", nullable = false)
	private String descripcion;
	
	@NotNull
	@Column(name = "actividad_destino", nullable = false)
	private String destino;

	@ManyToMany
	@JoinTable
	(
			name = "ainvolucrados",
			joinColumns = { @JoinColumn(name = "actividad_id", referencedColumnName = "actividad_id", unique = false) },
			inverseJoinColumns = { @JoinColumn(name = "contacto_id", referencedColumnName = "contacto_id", unique = false) },
			uniqueConstraints = @UniqueConstraint(columnNames = { "actividad_id", "contacto_id" })
	)
	private List<Contacto> involucrados;

	@NotNull
	@ManyToOne
	@JoinColumn(name="actividad_estado_id")
	private Mantenedor estado;
	
	/*@OneToMany
	private List<Bitacora> bitacora;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*public Certificacion getCertificacion() {
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
	}*/

	public Mantenedor getTipo() {
		return tipo;
	}

	public void setTipo(Mantenedor tipo) {
		this.tipo = tipo;
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

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public Usuario getEjecutor() {
		return ejecutor;
	}

	public void setEjecutor(Usuario ejecutor) {
		this.ejecutor = ejecutor;
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

	public Mantenedor getEstado() {
		return estado;
	}

	public void setEstado(Mantenedor estado) {
		this.estado = estado;
	}
	
	/*
	public List<Bitacora> getBitacora() {
		return bitacora;
	}

	public void setBitacora(List<Bitacora> bitacora) {
		this.bitacora = bitacora;
	}*/

	public Actividad(	String nombre, 
						String descripcion, 
						String destino,
						Date fechaInicial, 
						Date fechaFinal, 
						Usuario creador,
						Usuario ejecutor, 
						List<Contacto> involucrados
						/*List<Bitacora> bitacora*/) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.destino = destino;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
		this.creador = creador;
		this.ejecutor = ejecutor;
		this.involucrados = involucrados;
		//this.bitacora = bitacora;
	}

	public Actividad() {
		super();
		involucrados = new ArrayList<Contacto>();
		//this.bitacora = new ArrayList<Bitacora>();
	}
}