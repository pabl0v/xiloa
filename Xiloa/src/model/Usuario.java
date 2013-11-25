package model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name = "usuarios")
@Table(name = "usuarios", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Usuario.findByLogin", query="Select u from usuarios u where u.usuarioEstatus='true' and u.usuarioAlias=?1"),
	@NamedQuery(name="Usuario.findContactoByLogin", query="Select u.contacto from usuarios u where u.usuarioEstatus='true' and u.usuarioAlias=?1")
})
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long id;	
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy="usuario")
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
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "usuario_fecha_registro", nullable = false)
	@Temporal(TemporalType.DATE)	
	private Date fechaRegistro;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "usuario_fecha_ultimo_acceso", nullable = true)
	@Temporal(TemporalType.DATE)	
	private Date fechaUltimoAcceso;
	
	@NotNull
	@Column(name = "usuario_cambiar_pwd", nullable = false)	
	private boolean cambiarPwd = false;
	
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

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	public boolean isCambiarPwd() {
		return cambiarPwd;
	}

	public void setCambiarPwd(boolean cambiarPwd) {
		this.cambiarPwd = cambiarPwd;
	}

	public boolean isUsuarioEstatus() {
		return usuarioEstatus;
	}

	public void setUsuarioEstatus(boolean usuarioEstatus) {
		this.usuarioEstatus = usuarioEstatus;
	}

	public Usuario(Contacto contacto, String usuarioAlias, String usuarioPwd, Rol rol, boolean cambiarPwd, boolean usuarioEstatus) {
		super();
		this.contacto = contacto;
		this.usuarioAlias = usuarioAlias;
		this.usuarioPwd = usuarioPwd;
		this.rol = rol;
		this.fechaRegistro = new Date();
		this.cambiarPwd = cambiarPwd;
		this.usuarioEstatus = usuarioEstatus;
	}
	
	public Usuario() {
		super();		
	}	
}