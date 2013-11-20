package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name = "paises")
@Table(name = "paises", schema = "sccl")
@NamedQueries ({
	@NamedQuery(name="Pais.findById", query="select p from paises p where p.id=?1")
})
public class Pais implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_pais", sequenceName = "seq_pais", allocationSize=1, initialValue= 1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "seq_pais")
	@Column(name = "pais_id", nullable = false)	
	private Long id;
	
	@NotNull
	@Column(name = "pais_nombre")
	private String nombre;
	
	@NotNull
	@Column(name = "pais_nacionalidad")
	private String nacionalidad;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Pais(String nombre, String nacionalidad) {
		super();
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
	}

	public Pais() {
		super();
	}
	
}
