package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name = "perfiles")
@Table(name = "perfiles", schema = "sccl")
public class Perfil implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_perfiles", sequenceName = "seq_perfiles", allocationSize=1, initialValue= 1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "seq_perfiles")
	@Column(name = "perfil_id", nullable = false)	
	private Long id;
	
	@Column(name = "perfil_nombre", nullable = false)	
	private String nombre;
	
	@Column(name = "perfil_descripcion", nullable = false)
	private String descripcion;
	
	@Column(name = "perfil_permiso", nullable = false)
	private String permiso;
	
	@Column(name = "perfil_modulo", nullable = false)
	private String modulo;
	
	@Column(name = "perfil_seccion", nullable = false)
	private String seccion;

	@NotNull
	@Column(name = "perfil_permitido", nullable = false)	
	private boolean permitido;

	@NotNull
	@Column(name = "perfil_habilitado", nullable = false)	
	private boolean habilitado;

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

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public boolean isPermitido() {
		return permitido;
	}

	public void setPermitido(boolean permitido) {
		this.permitido = permitido;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Perfil(String nombre, String descripcion, String permiso,
			String modulo, String seccion, boolean permitido, boolean habilitado) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.permiso = permiso;
		this.modulo = modulo;
		this.seccion = seccion;
		this.permitido = permitido;
		this.habilitado = habilitado;
	}
	
	public Perfil() {
		super();		
	}
	
}