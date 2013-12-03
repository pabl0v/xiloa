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

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name = "instrumentos")
@Table(name = "instrumentos", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Instrumento.findAll", query="select i from instrumentos i order by 1 desc"),
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
	
	@NotNull
	@Column(name="instrumento_unidad_id", nullable = false)
	private Long unidad;
	
	@NotNull
	@Column(name = "instrumento_codigo", nullable = false)	
	private String codigo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="instrumento_tipo")
	private Mantenedor tipo;
	
	@NotNull
	@Column(name = "instrumento_cualitativo", nullable = false)	
	private boolean cualitativo;
		
	@NotNull
	@Column(name = "instrumento_nombre", nullable = false)	
	private String nombre;
		
	@Column(name = "instrumento_puntaje_minimo", nullable = true)	
	private Integer puntajeMinimo;
	
	@Column(name = "instrumento_puntaje_maximo", nullable = true)	
	private Integer puntajeMaximo;
	
	@OneToMany(mappedBy="instrumento")
	private List<Guia> guias;

	@NotNull
	@Column(name = "instrumento_entidad_id", nullable = false)	
	private Integer entidadId;

	@NotNull
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

	public Integer getPuntajeMinimo() {
		return puntajeMinimo;
	}

	public void setPuntajeMinimo(Integer puntajeMinimo) {
		this.puntajeMinimo = puntajeMinimo;
	}

	public Integer getPuntajeMaximo() {
		return puntajeMaximo;
	}

	public void setPuntajeMaximo(Integer puntajeMaximo) {
		this.puntajeMaximo = puntajeMaximo;
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
	
	public Instrumento(){
		super();
		this.guias = new ArrayList<Guia>();
	}
	
	public Instrumento(Long unidad, String codigo, Mantenedor tipo, boolean cualitativo, String nombre, Integer puntajeMinimo, Integer puntajeMaximo, List<Guia> guias, Integer entidadId, boolean estatus){
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