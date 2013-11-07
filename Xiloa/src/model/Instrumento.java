package model;

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

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="instrumentos")
@NamedQueries({
	@NamedQuery(name="Instrumento.findAllByUnidadId", query="select i from instrumentos i where i.unidad.id=?1"),
	@NamedQuery(name="Instrumento.findById", query="select i from instrumentos i where i.id=?1")
})
public class Instrumento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "instrumento_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="instrumento_unidad")
	private Unidad unidad;
	
	@NotNull
	@Column(name = "instrumento_codigo", nullable = false)	
	private String codigo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="instrumento_tipo")
	private Mantenedor tipo;
		
	@NotNull
	@Column(name = "instrumento_nombre", nullable = false)	
	private String nombre;
	
	@NotNull
	@Column(name = "instrumento_descripcion", nullable = false)	
	private String descripcion;
	
	@NotNull
	@Column(name = "instrumento_puntaje_minimo", nullable = false)	
	private Integer puntajeMinimo;
	
	@OneToMany(mappedBy="instrumento")
	private List<Guia> guias;

	@NotNull
	@Column(name = "instrumento_estatus", nullable = false)	
	private boolean estatus;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
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

	public List<Guia> getGuias() {
		return guias;
	}

	public void setGuias(List<Guia> guias) {
		this.guias = guias;
	}

	public Integer getPuntajeMinimo() {
		return puntajeMinimo;
	}

	public void setPuntajeMinimo(Integer puntajeMinimo) {
		this.puntajeMinimo = puntajeMinimo;
	}

	public boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}
	
	public Instrumento(){
		super();
		this.guias = new ArrayList<Guia>();
	}
	
	public Instrumento(Unidad unidad, String codigo, Mantenedor tipo, String nombre, String descripcion, Integer puntajeMinimo, List<Guia> guias, boolean estatus){
		super();
		
		this.unidad = unidad;
		this.codigo = codigo;
		this.tipo = tipo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.puntajeMinimo = puntajeMinimo;
		this.guias = guias;
		this.estatus = estatus;
	}
}