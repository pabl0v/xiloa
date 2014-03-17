package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.actividades 
 * 
 */

@Entity(name = "actividades")
@Table(name = "actividades", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Actividad.findByCertificacionId", query="select a from actividades a where a.certificacion.id=?1"),
	@NamedQuery(name="Actividad.findByEntidadId", query="select a from actividades a where a.certificacion.estatus.id!=9 and a.certificacion.ifpId = case ?1 when 1000 then a.certificacion.ifpId else ?1 end order by a.id desc"),
})
public class Actividad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "actividad_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_id", nullable = false)	
	private Certificacion certificacion;
	
	@NotNull
	@JoinColumn(name="actividad_indice")
	private Integer indice;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="actividad_tipo_id", nullable = false)
	private Mantenedor tipo;
	
	@Column(name = "actividad_nombre", nullable = false)
	private String nombre;
	
	@NotNull
	@Column(name = "actividad_destino", nullable = false)
	private String destino;
	
	@Column(name = "actividad_hora", nullable = true)
	private String hora;
	
	@Column(name = "actividad_alcance", nullable = true)
	private String alcance;
	
	@Column(name = "actividad_materiales", nullable = true)
	private String materiales;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "actividad_fecha_registro", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "actividad_fecha_inicial", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaInicial;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "actividad_fecha_final", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaFinal;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="actividad_creador_id", nullable = false)
	private Contacto creador;
	
	@ManyToOne
	@JoinColumn(name="actividad_ejecutor_id")
	private Contacto ejecutor;
			
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	(
			name = "sccl.ainvolucrados",
			joinColumns = @JoinColumn(name = "actividad_id", unique = false),
			inverseJoinColumns = @JoinColumn(name = "contacto_id", unique = false)
	)
	@MapKeyColumn(name="id_rol")
	private Map<Integer, Contacto> involucrados;
	
	@OneToMany
	@JoinColumn(name="actividad_id", referencedColumnName="actividad_id")
	private List<Bitacora> bitacoras;

	@NotNull
	@ManyToOne
	@JoinColumn(name="actividad_estado_id", nullable = false)
	private Mantenedor estado;
	
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

	public Integer getIndice(){
		return indice;
	}
	
	public void setIndice(Integer indice){
		this.indice = indice;
	}

	public Mantenedor getTipo() {
		return tipo;
	}

	public void setTipo(Mantenedor tipo) {
		this.tipo = tipo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getAlcance() {
		return alcance;
	}

	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}

	public String getMateriales() {
		return materiales;
	}

	public void setMateriales(String materiales) {
		this.materiales = materiales;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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

	public Contacto getCreador() {
		return creador;
	}

	public void setCreador(Contacto creador) {
		this.creador = creador;
	}

	public Contacto getEjecutor() {
		return ejecutor;
	}

	public void setEjecutor(Contacto ejecutor) {
		this.ejecutor = ejecutor;
	}

	public List<Contacto> getInvolucrados() {
		return new ArrayList<Contacto>(involucrados.values());
	}
	
	public void setInvolucrados(Contacto[] involucrados) {
		setEjecutor(involucrados[0]);
		this.involucrados.clear();
		for(int i=0; i<involucrados.length; i++){
			this.involucrados.put(involucrados[i].getRol().getId(), involucrados[i]);
		}
	}
	
	public List<Bitacora> getBitacoras() {
		return bitacoras;
	}

	public void setBitacoras(List<Bitacora> bitacoras) {
		this.bitacoras = bitacoras;
	}

	public Mantenedor getEstado() {
		return estado;
	}

	public void setEstado(Mantenedor estado) {
		this.estado = estado;
	}
	
	public boolean isCompleted(){
		if(this.estado.getId()==39)
			return true;
		else
			return false;
	}
	
	public Actividad() {
		super();
		involucrados = new HashMap<Integer, Contacto>();
		bitacoras = new ArrayList<Bitacora>();
	}
	
	public Actividad(	Certificacion certificacion,
						Integer indice,
						Mantenedor tipo,
						String nombre, 
						String destino,
						String hora,
						String alcance,
						String materiales,
						Date fechaRegistro,
						Date fechaInicial, 
						Date fechaFinal, 
						Contacto creador,
						Contacto ejecutor, 
						Map<Integer,Contacto> involucrados,
						List<Bitacora> bitacoras,
						Mantenedor estado) {
		super();
		this.certificacion = certificacion;
		this.indice = indice;
		this.tipo = tipo;
		this.nombre = nombre;
		this.destino = destino;
		this.hora = hora;
		this.alcance = alcance;
		this.materiales = materiales;
		this.fechaRegistro = fechaRegistro;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
		this.creador = creador;
		this.ejecutor = ejecutor;
		this.involucrados = involucrados;
		this.bitacoras = bitacoras;
		this.estado = estado;
	}
}