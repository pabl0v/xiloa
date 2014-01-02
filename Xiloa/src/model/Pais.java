package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.pais 
 * 
 */

@Entity(name = "paises")
@Table(name = "paises", schema = "sccl")
@NamedQueries ({
	@NamedQuery(name="Pais.findById", query="select p from paises p where p.id=?1")
})
public class Pais implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "pais_id", nullable = false)	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
