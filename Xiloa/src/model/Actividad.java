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
 * Esta clase se corresponde con la tabla sccl.actividades 
 * 
 */

@Entity(name = "actividades")
@Table(name = "actividades", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Actividad.findByCertificacionId", query="select a from actividades a where a.certificacion.id=?1 order by a.id asc"),
	@NamedQuery(name="Actividad.findByEntidadId", query="select a from actividades a where a.certificacion.estatus.id!=18 and a.certificacion.ifpId = case ?1 when 1000 then a.certificacion.ifpId else ?1 end order by a.id asc"),
	@NamedQuery(name="Actividad.findItemsBySolicitudId", query="select new support.Item(a.id, a.nombre) from actividades a, certificaciones c, solicitudes s where a.certificacion.id=c.id and a.tipo.id in (6,7,8) and s.certificacion.id=c.id and s.id=?1 order by a.id asc")
})
public class Actividad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "actividad_id", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="actividad_certificacion_id", nullable = false)
	private Certificacion certificacion;
		
	@ManyToOne
	@JoinColumn(name="actividad_tipo_id", nullable = false)
	private Mantenedor tipo;
	
	@Column(name = "actividad_nombre", nullable = false)
	private String nombre;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="actividad_creador_id", nullable = false)
	private Contacto creador;

	@ManyToOne
	@JoinColumn(name="actividad_estado_id", nullable = false)
	private Mantenedor estado;
	
	public Actividad() {
		super();
	}
	
	public Actividad(	Certificacion certificacion,
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
						Mantenedor estado) {
		super();
		this.certificacion = certificacion;
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
		this.estado = estado;
	}

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

	public Mantenedor getEstado() {
		return estado;
	}

	public void setEstado(Mantenedor estado) {
		this.estado = estado;
	}
	
	public boolean isCompleted(){
		if(this.estado.getId()==15)
			return true;
		else
			return false;
	}
}