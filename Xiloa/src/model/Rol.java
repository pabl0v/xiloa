package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name = "roles")
public class Rol {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "rol_id")
	private Long id;
	
	@Column(name = "rol_nombre", nullable = false)	
	private String nombre;
	
	@Column(name = "rol_descripcion", nullable = false)
	private String descripcion;
	
	@ManyToMany
	@JoinTable
	(
			name = "perfiles_roles",
			joinColumns=@JoinColumn(name="rol_id", unique=false),
			inverseJoinColumns=@JoinColumn(name="perfil_id", unique=false),
			uniqueConstraints=@UniqueConstraint(columnNames={"rol_id", "perfil_id"})
	)
	private List<Perfil> perfiles;
	
	@NotNull
	@Column(name = "rol_estatus", nullable = false)
	private boolean estatus;
	
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	public boolean isEstatus() {
		return estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}
}