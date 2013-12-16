package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;

@Entity(name = "instrumentos")
@Table(name = "instrumentos", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Instrumento.findAll", query="select i from instrumentos i order by 1 desc"),
	@NamedQuery(name="Instrumento.findAllByEntidadId", query="select i from instrumentos i where i.entidadId = case ?1 when 1000 then i.entidadId else ?1 end order by 1 desc"),
	@NamedQuery(name="Instrumento.findAllByUnidadId", query="select i from instrumentos i where i.unidad=?1 order by 1 desc"),
	@NamedQuery(name="Instrumento.findById", query="select i from instrumentos i where i.id=?1"),
	@NamedQuery(name="Instrumento.findAllByCertificacionId", query="select i from instrumentos i, certificaciones c where i.unidad member of c.unidades and c.id=?1")
})
public class Instrumento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "instrumento_id", nullable = false)
	private Long id;
	
	//@NotNull(message="La unidad es requerida")
	@Column(name="instrumento_unidad_id", nullable = false)
	private Long unidad;
	
	//@NotNull(message="El código es requerido")
	@Column(name = "instrumento_codigo", nullable = false)	
	private String codigo;
	
	//@NotNull(message="El tipo de instrumento es requerido")
	@ManyToOne
	@JoinColumn(name="instrumento_tipo")
	private Mantenedor tipo;
	
	//@NotNull
	@Column(name = "instrumento_cualitativo", nullable = false)	
	private boolean cualitativo;
		
	//@NotNull(message="El nombre es requerido")
	@Column(name = "instrumento_nombre", nullable = false)	
	private String nombre;
	
	
	@Column(name = "instrumento_puntaje_minimo", nullable = false, precision=10, scale=2)	
	private Float puntajeMinimo;
	
	@Column(name = "instrumento_puntaje_maximo", nullable = false, precision=10, scale=2)	
	private Float puntajeMaximo;

	//@Min( value = 0, message = "La cantidad de preguntas debe ser > 0")
	//@Max( value = 100, message = "La cantidad de preguntas debe ser < 100")	
	@Column(name = "instrumento_cantidad_preguntas", nullable = true)
	private Integer cantidadPreguntas;

	//@Min( value = 0, message = "Las respuestas fallidas deben ser > 0")
	//@Max( value = 100, message = "Las respuestas fallidas deben ser < 100")	
	@Column(name = "instrumento_respuestas_fallidas", nullable = true)
	private Integer respuestasFallidas;
	
	@OneToMany(mappedBy="instrumento")
	private List<Guia> guias;

	//@NotNull
	@Column(name = "instrumento_entidad_id", nullable = false)	
	private Integer entidadId;

	//@NotNull
	@Column(name = "instrumento_estatus", nullable = false)	
	private boolean estatus;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getUnidad() {
		return unidad;
	}

	public void setUnidad(Long unidad) {
		this.unidad = unidad;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Mantenedor getTipo() {
		return tipo;
	}

	public void setTipo(Mantenedor tipo) {
		this.tipo = tipo;
	}

	public boolean isCualitativo() {
		return cualitativo;
	}

	public void setCualitativo(boolean cualitativo) {
		this.cualitativo = cualitativo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Guia> getGuias() {
		return guias;
	}

	public void setGuias(List<Guia> guias) {
		this.guias = guias;
	}
	
	public void addGuia(Guia guia){
		this.guias.add(guia);
	}

	public Integer getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(Integer entidadId) {
		this.entidadId = entidadId;
	}
	
	public String getPuntajeMinimoCualitativo() {
		if(cualitativo)
			return (cantidadPreguntas - respuestasFallidas) + "/" + cantidadPreguntas;
		else
			return "N/D";
	}

	public Float getPuntajeMinimo() {
		return puntajeMinimo;
	}

	public void setPuntajeMinimo(Float puntajeMinimo) {
		this.puntajeMinimo = puntajeMinimo;
	}
	
	public String getPuntajeMaximoCualitativo() {
		if(cualitativo)
			return cantidadPreguntas + "/" + cantidadPreguntas;
		else
			return "N/D";
	}

	public Float getPuntajeMaximo() {
		return puntajeMaximo;
	}

	public void setPuntajeMaximo(Float puntajeMaximo) {
		this.puntajeMaximo = puntajeMaximo;
	}
	
	public Integer getCantidadPreguntas() {
		return cantidadPreguntas;
	}

	public void setCantidadPreguntas(Integer cantidadPreguntas) {
		this.cantidadPreguntas = cantidadPreguntas;
	}

	public Integer getRespuestasFallidas() {
		return respuestasFallidas;
	}

	public void setRespuestasFallidas(Integer respuestasFallidas) {
		this.respuestasFallidas = respuestasFallidas;
	}

	public boolean getEstatus() {
		return estatus;
	}
	
	public String getEstatusLabel(){
		if(estatus)
			return "Activo";
		else
			return "Inactivo";
	}
	
	public String getCualitativoLabel(){
		if(cualitativo)
			return "Si";
		else
			return "No";
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}
	
	@Override
	public String toString(){
	
		String cadena = "Tabla instrumentos -> ";
		
		//cadena = cadena + "instrumento_id=" + getId().toString() + ", ";
		cadena = cadena + "instrumento_unidad_id=" + getUnidad().toString() + ", ";
		cadena = cadena + "instrumento_codigo=" + getCodigo() + ", ";
		cadena = cadena + "instrumento_tipo=" + getTipo().getId() + ", ";
		cadena = cadena + "instrumento_cualitativo=" + Boolean.toString(isCualitativo()) + ", ";
		cadena = cadena + "instrumento_nombre=" + getNombre() + ", ";
		cadena = cadena + "instrumento_puntaje_minimo=" + getPuntajeMinimo().toString() + ", ";
		cadena = cadena + "instrumento_puntaje_maximo=" + getPuntajeMaximo().toString() + ", ";
		cadena = cadena + "instrumento_cantidad_preguntas=" + getCantidadPreguntas() + ", ";
		cadena = cadena + "instrumento_respuestas_fallidas=" + getRespuestasFallidas() + ", ";
		cadena = cadena + "instrumento_entidad_id=" + getEntidadId() + ", ";
		cadena = cadena + "instrumento_estatus=" + Boolean.toString(getEstatus()) + " ";

		return cadena;
	}
	
	public Instrumento(){
		super();
		this.guias = new ArrayList<Guia>();
	}
	
	public Instrumento(Long unidad, String codigo, Mantenedor tipo, boolean cualitativo, String nombre, Float puntajeMinimo, Float puntajeMaximo, List<Guia> guias, Integer entidadId, boolean estatus){
		super();
		
		this.unidad = unidad;
		this.codigo = codigo;
		this.tipo = tipo;
		this.cualitativo = cualitativo;
		this.nombre = nombre;
		this.puntajeMinimo = puntajeMinimo;
		this.puntajeMaximo = puntajeMaximo;
		this.guias = guias;
		this.entidadId = entidadId;
		this.estatus = estatus;
	}	
}