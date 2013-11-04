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
import javax.persistence.OneToMany;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="instrumentos")
public class Instrumento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "instrumento_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="instrumento_tipo")
	private Mantenedor tipo;
		
	@NotNull
	@Column(name = "instrumento_nombre", nullable = false)	
	private String nombre;
	
	@OneToMany//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="instrumento_id", referencedColumnName="instrumento_id")	
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

	public List<Guia> getGuias() {
		return guias;
	}

	public void setGuias(List<Guia> guias) {
		this.guias = guias;
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
	
	public Instrumento(Mantenedor tipo, String nombre, List<Guia> guias, boolean estatus){
		super();
		
		this.tipo = tipo;
		this.nombre = nombre;
		this.guias = guias;
		this.estatus = estatus;
	}
}