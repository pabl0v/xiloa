package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.guias 
 * 
 */

@Entity(name = "guias")
@Table(name = "guias", schema = "sccl")
@NamedQueries ({
	@NamedQuery (name="Guia.findByIdInstrumento", query="select g from guias g where g.instrumento.id=?1 order by g.id")
})
public class Guia implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "guia_id", nullable = false)
	private Long id;
	
	@Column(name = "guia_unidad_id", nullable = true)
	private Long unidadId;
	
	@ManyToOne
	@JoinColumn(name="instrumento_id", nullable = false)
	private Instrumento instrumento;
	
	@Column(name = "guia_pregunta", nullable = false)	
	private String pregunta;

	@Column(name = "guia_respuesta", nullable = false)
	private String respuesta;

	@Column(name = "guia_puntaje", nullable = false, precision=10, scale=2)
	private Float puntaje;

	@Column(name = "guia_estatus", nullable = false)
	private boolean estatus;
	
	@Column(name = "guia_contraste", nullable = false)
	private boolean contraste = false;
	
	public Guia(){
		super();
	}
	
	public Guia(Long unidad, Instrumento instrumento, String pregunta, String respuesta, Float puntaje, boolean estatus){
		this.unidadId = unidad;
		this.instrumento = instrumento;
		this.pregunta = pregunta;
		this.respuesta = respuesta;
		this.puntaje = puntaje;
		this.contraste = false;
		this.estatus = estatus;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUnidadId() {
		return unidadId;
	}
	
	public void setUnidadId(Long id) {
		this.unidadId = id;
	}
	
	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public String getPregunta() {
		return pregunta;
	}
	
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	
	public String getRespuesta() {
		return respuesta;
	}
	
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	public String getPesoPregunta(){
		if(instrumento.isCualitativo())
			return "1/"+instrumento.getCantidadPreguntas();
		else
			return "N/D";
	}
			
	public Float getPuntaje() {
		return puntaje;
	}
	
	public String getPuntajeLabel(){
		return String.format("%.2f", (double)puntaje);
	}
	
	public void setPuntaje(Float puntaje) {
		this.puntaje = puntaje;
	}
	
	public boolean getEstatus(){
		return estatus;
	}
	
	public String getEstatusLabel(){
		if(estatus)
			return "Activo";
		else
			return "Inactivo";
	}
	
	public void setEstatus(boolean estatus){
		this.estatus = estatus;
	}
	
	public boolean getContraste() {
		return contraste;
	}

	public void setContraste(boolean valor) {
		this.contraste = valor;
	}
	
	@Override
	public String toString(){
	
		String cadena = "Tabla guias -> ";
		
		cadena = cadena + "instrumento_id=" + getInstrumento().getId() + ", ";
		cadena = cadena + "guia_pregunta=" + getPregunta() + ", ";
		cadena = cadena + "guia_respuesta=" + getRespuesta() + ", ";
		cadena = cadena + "guia_estatus=" + Boolean.toString(getEstatus()) + " ";
		
		return cadena;
	}	
}