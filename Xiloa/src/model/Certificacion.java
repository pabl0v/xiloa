package model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

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
 * Esta clase se corresponde con la tabla sccl.certificaciones 
 * 
 */

@Entity(name = "certificaciones")
@Table(name = "certificaciones", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Certificacion.findAll", query="select c from certificaciones c order by c.id desc"),
	@NamedQuery(name="Certificacion.findActivas", query="select c from certificaciones c where c.estatus.id=17 order by c.id desc"),
	@NamedQuery(name="Certificacion.findActivasByCentroId", query="select c from certificaciones c where c.estatus.id=17 and c.ifpId = case ?1 when 1000 then c.ifpId else ?1 end order by c.id desc"),
	@NamedQuery(name="Certificacion.findByIfpId", query="select c from certificaciones c where c.estatus.id!=18 and c.ifpId = case ?1 when 1000 then c.ifpId else ?1 end order by 1 desc"),
	@NamedQuery(name="Certificacion.findById", query="select c from certificaciones c where c.id=?1"),
	@NamedQuery(name="Certificacion.findAllByNombre", query="select c from certificaciones c where c.estatus.id=17 and c.nombre like ?1 order by c.id desc"),
	@NamedQuery(name="Certificacion.findAllByCentro", query="select c from certificaciones c where c.estatus.id=17 and c.ifpNombre like ?1 order by c.id desc"),
	@NamedQuery(name="Certificacion.findItemsByIfpId", query="select new support.Item(c.id, c.nombre) from certificaciones c where c.estatus.id!=18 and c.ifpId = case ?1 when 1000 then c.ifpId else ?1 end"),
})
public class Certificacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "certificacion_id", nullable = false)
	private Long id;
	
	@Column(name = "certificacion_oferta_id", nullable = true)
	private Integer ofertaId;	
	
	@Column(name = "certificacion_estructura_id", nullable = false)
	private Integer estructuraId;
	
	@Column(name = "certificacion_curso_id", nullable = false)
	private Integer cursoId;
	
	@Column(name = "certificacion_nombre", nullable = false)
	private String nombre;
	
	@Column(name = "certificacion_descripcion", nullable = false)
	private String descripcion;
		
	@Column(name = "certificacion_disponibilidad", nullable = true)
	private Integer disponibilidad;
	
	@Column(name = "certificacion_costo", nullable = true)
	private Float costo;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_fecha_registro", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_fecha_actualiza", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaActualiza;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_inicia", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date inicia;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "certificacion_finaliza", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date finaliza;
	
	@Column(name = "certificacion_ifp_id", nullable = false)
	private Integer ifpId;
	
	@Column(name = "certificacion_ifp_nombre", nullable = false)
	private String ifpNombre;

	@Column(name = "certificacion_ifp_direccion", nullable = true)
	private String ifpDireccion;

	@Column(name = "certificacion_coordinador", nullable = true)
	private String coordinador;
	
	@Column(name = "certificacion_informante", nullable = true)
	private String informante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="certificacion_creador_id", nullable = false)
	private Contacto creador;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="certificacion_actualiza_id", nullable = true)
	private Contacto actualiza;
		
	@Column(name = "certificacion_referencial", nullable = true)
	private String referencial;
	
	@Column(name = "certificacion_nivel_competencia", nullable = true)
	private Integer nivelCompetencia;
	
	@ManyToOne
	@JoinColumn(name="certificacion_estatus", nullable = false)
	private Mantenedor estatus;
	
	public Certificacion(){
		super();
	}
	
	public Certificacion(	Integer ofertaId,
							Integer estructuraId,
							Integer cursoId,
							String nombre, 
							String descripcion,
							Integer disponibilidad,
							Float costo,
							Date fechaRegistro,
							Date inicia,
							Date finaliza, 
							Integer ifpId,
							String ifpNombre,
							String ifpDireccion,
							String coordinador,
							String informante,
							Contacto creador,
							String referencial, 
							Integer nivelCompetencia,
							Mantenedor estatus)
		{
			super();
			this.ofertaId = ofertaId;
			this.estructuraId = estructuraId;
			this.cursoId = cursoId;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.disponibilidad = disponibilidad;
			this.costo = costo;
			this.fechaRegistro = fechaRegistro;
			this.fechaActualiza = null;
			this.inicia = inicia;
			this.finaliza = finaliza;
			this.ifpId = ifpId;
			this.ifpNombre = ifpNombre;
			this.ifpDireccion = ifpDireccion;
			this.coordinador = coordinador;
			this.informante = informante;
			this.creador = creador;
			this.actualiza = null;
			this.referencial = referencial;
			this.nivelCompetencia = nivelCompetencia;
			this.estatus = estatus;
		}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getOfertaId() {
		return ofertaId;
	}

	public void setOfertaId(Integer ofertaId) {
		this.ofertaId = ofertaId;
	}
	
	public Integer getEstructuraId() {
		return estructuraId;
	}

	public void setEstructuraId(Integer estructuraId) {
		this.estructuraId = estructuraId;
	}

	public Integer getCursoId() {
		return cursoId;
	}

	public void setCursoId(Integer cursoId) {
		this.cursoId = cursoId;
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

	public Integer getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Integer disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getCostoFormateado() {
		if(costo == null)
			return "N/D";
		else
		{
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			format.setMaximumFractionDigits(2);
			format.setMinimumFractionDigits(2);
			return format.format(costo);
		}
	}
	
	public Float getCosto() {
		return costo;
	}

	public void setCosto(Float costo) {
		this.costo = costo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaActualiza() {
		return fechaActualiza;
	}

	public void setFechaActualiza(Date fechaActualiza) {
		this.fechaActualiza = fechaActualiza;
	}

	public Date getInicia() {
		return inicia;
	}

	public void setInicia(Date inicia) {
		this.inicia = inicia;
	}

	public Date getFinaliza() {
		return finaliza;
	}

	public void setFinaliza(Date finaliza) {
		this.finaliza = finaliza;
	}

	public Integer getIfpId() {
		return ifpId;
	}

	public void setIfpId(Integer ifpId) {
		this.ifpId = ifpId;
	}
	
	public String getIfpNombre() {
		return ifpNombre;
	}

	public void setIfpNombre(String ifpNombre) {
		this.ifpNombre = ifpNombre;
	}

	public String getIfpDireccion() {
		return ifpDireccion;
	}

	public void setIfpDireccion(String ifpDireccion) {
		this.ifpDireccion = ifpDireccion;
	}
	
	public String getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(String coordinador) {
		this.coordinador = coordinador;
	}

	public String getInformante() {
		return informante;
	}

	public void setInformante(String informante) {
		this.informante = informante;
	}

	public Contacto getCreador() {
		return creador;
	}

	public void setCreador(Contacto creador) {
		this.creador = creador;
	}

	public Contacto getActualiza() {
		return actualiza;
	}

	public void setActualiza(Contacto actualiza) {
		this.actualiza = actualiza;
	}
	
	public String getReferencial() {
		return referencial;
	}

	public void setReferencial(String referencial) {
		this.referencial = referencial;
	}

	public Integer getNivelCompetencia() {
		return nivelCompetencia;
	}

	public void setNivelCompetencia(Integer nivelCompetencia) {
		this.nivelCompetencia = nivelCompetencia;
	}

	public Mantenedor getEstatus() {
		return estatus;
	}

	public void setEstatus(Mantenedor estatus) {
		this.estatus = estatus;
	}
}