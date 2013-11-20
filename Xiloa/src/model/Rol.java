package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name = "roles")
@Table(name = "roles", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Rol.findByNombre", query="select r from roles r where r.estatus='true' and r.nombre=?1")
})
public class Rol implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_roles", sequenceName = "seq_roles", allocationSize=1, initialValue= 1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "seq_roles")
	@Column(name = "rol_id", nullable = false)
	private int id;
	
	@Column(name = "rol_nombre", nullable = false)	
	private String nombre;
	
	@Column(name = "rol_descripcion", nullable = false)
	private String descripcion;
	
	@Column(name = "id_rol_inatec", nullable = true)
	private Integer idRolInatec;
	
	@ManyToMany
	@JoinTable
	(
			name = "perfiles_roles",
			joinColumns = @JoinColumn(name = "rol_id", unique = false),
			inverseJoinColumns = @JoinColumn(name = "perfil_id", unique = false),
			uniqueConstraints = @UniqueConstraint(columnNames = {"rol_id", "perfil_id"})
	)
	private List<Perfil> perfiles;
	
	@NotNull
	@Column(name = "rol_estatus", nullable = false)
	private boolean estatus;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getIdRolInatec() {
		return idRolInatec;
	}

	public void setIdRolInatec(int idRolInatec) {
		this.idRolInatec = idRolInatec;
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

	public Rol(String nombre, String descripcion, Integer idRolInatec,
			List<Perfil> perfiles, boolean estatus) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.idRolInatec = idRolInatec;
		this.perfiles = perfiles;
		this.estatus = estatus;
	}
	
	public Rol() {
		super();		
	}
}