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
import javax.persistence.PrimaryKeyJoinColumn;
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
 * Esta clase se corresponde con la tabla sccl.evaluaciones 
 * 
 */

@Entity(name = "evaluaciones")
@Table(name = "evaluaciones", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Evaluacion.findAllPendientesByFirstSolicitudByContactoId", query="select e from evaluaciones e where e.solicitud.contacto.id=?1 and e.solicitud.id=(select min(x.id) from solicitudes x where x.contacto=e.solicitud.contacto and x.estatus.id!=76) order by e.id desc"),
	@NamedQuery(name="Evaluacion.findAllPendientesBySolicitudId", query="select e from evaluaciones e where e.solicitud.id=?1 and e.vista.aprobado=false and e.activo=true order by e.id desc"),
	@NamedQuery(name="Evaluacion.findAllBySolicitudId", query="select e from evaluaciones e where e.solicitud.id=?1 and e.activo='true' order by e.id desc"),
	@NamedQuery(name="Evaluacion.findById", query="select e from evaluaciones e where e.id=?1"),
	@NamedQuery(name="Evaluacion.findAllBySolicitudUCL", query="select e from evaluaciones e inner join fetch e.solicitud s where s.id=?1 and e.instrumento.unidad=?2"),
	@NamedQuery(name="Evaluacion.findAllByContactoId", query="select e from evaluaciones e where e.activo=true and e.solicitud.id in (select s.id from solicitudes s where s.estatus.id not in (44,45) and s.contacto.id=?1) order by 1 desc")
})
public class Evaluacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "evaluacion_id", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="solicitud_id", nullable = false)
	private Solicitud solicitud;

	@ManyToOne
	@JoinColumn(name="instrumento_id", nullable = false)
	private Instrumento instrumento;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEvaluacion;
	
	@Column(name = "puntaje_minimo", nullable = false, precision=10, scale=2)	
	private Float puntajeMinimo;
	
	@Column(name = "puntaje_maximo", nullable = false, precision=10, scale=2)	
	private Float puntajeMaximo;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name="evaluacion_id")
	private VistaEvaluacion vista;
	
	@Column(name = "requiere_evidencia", nullable = false)
	private boolean requiereEvidencia;
	
	@Column(name = "observaciones", nullable = true)
	private String observaciones;
	
	@Column(name = "activo", nullable = false)
	private boolean activo = true;
			
	public Evaluacion() {
		super();
		this.activo = true;
		this.puntajeMinimo = new Float(0);
		this.puntajeMaximo = new Float(0);
		this.requiereEvidencia = false;
	}

	public Evaluacion(Solicitud solicitud, Instrumento instrumento, Date fecha, Float puntajeMinimo, Float puntajeMaximo, boolean evidencia, String observaciones, boolean activo) {
		super();
		this.solicitud = solicitud;
		this.instrumento = instrumento;
		this.fechaEvaluacion = fecha;
		this.puntajeMinimo = puntajeMinimo;
		this.puntajeMaximo = puntajeMaximo;
		this.requiereEvidencia = evidencia;
		this.observaciones = observaciones;
		this.activo = activo;
	}	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}
	
	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public Date getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(Date fecha) {
		this.fechaEvaluacion = fecha;
	}
	
	public Float getPuntajeMinimo() {
		return puntajeMinimo;
	}
	
	public String getPuntajeMinimoLabel(){
		return String.format("%.2f", (double)puntajeMinimo);
	}

	public void setPuntajeMinimo(Float puntajeMinimo) {
		this.puntajeMinimo = puntajeMinimo;
	}
	
	public Float getPuntajeMaximo() {
		return puntajeMaximo;
	}
	
	public String getPuntajeMaximoLabel(){
		return String.format("%.2f", (double)puntajeMaximo);
	}

	public void setPuntajeMaximo(Float puntajeMaximo) {
		this.puntajeMaximo = puntajeMaximo;
	}
	
	public VistaEvaluacion getVistaEvaluacion(){
		return vista;
	}
	
	public boolean getRequiereEvidencia(){
		return requiereEvidencia;
	}
	
	public void setRequiereEvidencia(boolean evidencia){
		this.requiereEvidencia = evidencia;
	}
	
	public String getObservaciones(){
		return observaciones;
	}
	
	public void setObservaciones(String observaciones){
		this.observaciones = observaciones;
	}
	
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public boolean getActivo() {
		return this.activo;
	}	
	
	public boolean getAprobado() {
		return this.vista.isAprobado();
	}
	
	public String getAprobadoLabel(){
		if(this.vista.isAprobado())
			return "SI";
		else
			return "NO";
	}
	
	public Float getPuntajeObtenido(){
		return this.vista.getPuntajeObtenido();
	}
	
	public String getPuntajeObtenidoLabel(){
		return String.format("%.2f", (double)this.vista.getPuntajeObtenido());
	}

	public Long getUnidad() {
		return this.instrumento.getUnidad();
	}			
}