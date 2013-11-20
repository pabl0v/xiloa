package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name = "usuarios")
@Table(name = "usuarios", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Usuario.findByLogin", query="Select u from usuarios u where u.usuarioEstatus='true' and u.usuarioAlias=?1")
})
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_usuarios", sequenceName = "seq_usuarios", allocationSize=1, initialValue= 1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "seq_usuarios")
	@Column(name = "usuario_id")
	private Long id;
	
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Usuario(Contacto contacto, String usuarioAlias, String usuarioPwd,
			Rol rol, boolean usuarioEstatus) {
		super();
		this.contacto = contacto;
		this.usuarioAlias = usuarioAlias;
		this.usuarioPwd = usuarioPwd;
		this.rol = rol;
		this.usuarioEstatus = usuarioEstatus;
	}
	
	public Usuario() {
		super();		
	}
	
}