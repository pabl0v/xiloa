package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private int id;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="usuario")
	private Contacto contacto;
	
	@NotBlank
	@NotNull
	@Length(max = 30)
	@Column(name = "usuario_alias", unique = true, nullable = false)
	private String usuarioAlias;

	@NotBlank
	@NotNull
	@Length(max = 1024)
	@Column(name = "usuario_pwd", nullable = false)	
	private String usuarioPwd;

	@NotNull
	@ManyToOne
	@JoinColumn(name="rol_id")		
	private Rol rol;
	
	@NotNull
	@Column(name = "usuario_estatus", nullable = false)
	private boolean usuarioEstatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public String getUsuarioAlias() {
		return usuarioAlias;
	}

	public void setUsuarioAlias(String usuarioAlias) {
		this.usuarioAlias = usuarioAlias;
	}

	public String getUsuarioPwd() {
		return usuarioPwd;
	}

	public void setUsuarioPwd(String usuarioPwd) {
		this.usuarioPwd = usuarioPwd;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean isUsuarioEstatus() {
		return usuarioEstatus;
	}

	public void setUsuarioEstatus(boolean usuarioEstatus) {
		this.usuarioEstatus = usuarioEstatus;
	}
}