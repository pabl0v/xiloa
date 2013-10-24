package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="contactos")
public class Contacto {

	@Id
	@SequenceGenerator(name = "seq_contactos", sequenceName = "seq_contactos", allocationSize=1, initialValue= 1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "seq_contactos")
	@Column(name = "contacto_id", nullable = false)	
	private int id;
	
	@OneToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="rol_id")
	private Rol rol;
	
	@NotNull
	@Column(name = "entidad_id", nullable = false)	
	private int entidadId;
	
	@NotNull
	@Column(name = "primer_nombre", nullable = false)
	private String primerNombre;
	
	@Column(name = "segundo_nombre", nullable = true)	
	private String segundoNombre;

	@NotNull
	@Column(name = "primer_apellido", nullable = false)	
	private String primerApellido;

	@Column(name = "segundo_apellido", nullable = true)	
	private String segundoApellido;
	
	@Column(name = "nombre_completo", nullable = true)
	private String nombreCompleto;

	@NotNull
	@Column(name = "sexo_id", nullable = false)
	private int sexo;
	
	@NotNull
	@Column(name = "correo1", nullable = false)
	private String correo1;
	
	@Column(name = "correo2", nullable = true)
	private String correo2;
	
	@NotNull
	@Column(name = "telefono1", nullable = false)	
	private String telefono1;
	
	@Column(name = "telefono2", nullable = true)	
	private String telefono2;

	@NotNull
	@Column(name = "tipo_contacto", nullable = false)		
	private int tipoContacto;
	
	@NotNull
	@Column(name = "tipo_identificacion", nullable = false)	
	private int tipoIdentificacion;
	
	@NotNull
	@Column(name = "numero_identificacion", nullable = false)
	private String numeroIdentificacion;

	@NotNull
	@Column(name = "direccion_actual", nullable = false)	
	private String direccionActual;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_nacimiento", nullable = true)
	@Temporal(TemporalType.DATE)	
	private Date fechaNacimiento;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_registro", nullable = false)
	@Temporal(TemporalType.DATE)	
	private Date fechaRegistro;
	
	@NotNull
	@Column(name = "nacionalidad_id", nullable = false)		
	private int nacionalidadId;

	@NotNull
	@Column(name = "lugar_nacimiento", nullable = false)	
	private String lugarNacimiento;
	
	@Column(name = "inatec", nullable = false)
	private boolean inatec = false;
	
	@Column(name = "usuario_inatec", nullable = true)
	private String usuarioInatec;
	
	@Column(name = "funcion", nullable = true)
	private String funcion;
	
	@Column(name = "id_empleado", nullable = true)
	private Long idEmpleado;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public int getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(int entidadId) {
		this.entidadId = entidadId;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public int getSexo() {
		return sexo;
	}

	public void setSexo(int sexo) {
		this.sexo = sexo;
	}

	public String getCorreo1() {
		return correo1;
	}

	public void setCorreo1(String correo1) {
		this.correo1 = correo1;
	}

	public String getCorreo2() {
		return correo2;
	}

	public void setCorreo2(String correo2) {
		this.correo2 = correo2;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public int getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(int tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	public int getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(int tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getDireccionActual() {
		return direccionActual;
	}

	public void setDireccionActual(String direccionActual) {
		this.direccionActual = direccionActual;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getNacionalidadId() {
		return nacionalidadId;
	}

	public void setNacionalidadId(int nacionalidadId) {
		this.nacionalidadId = nacionalidadId;
	}

	public String getLugarNacimiento() {
		return lugarNacimiento;
	}

	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}

	public boolean isInatec() {
		return inatec;
	}

	public void setInatec(boolean inatec) {
		this.inatec = inatec;
	}

	public String getUsuarioInatec() {
		return usuarioInatec;
	}

	public void setUsuarioInatec(String usuarioInatec) {
		this.usuarioInatec = usuarioInatec;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Contacto(Usuario usuario, Rol rol, int entidadId,
			String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String nombreCompleto, int sexo,
			String correo1, String correo2, String telefono1, String telefono2,
			int tipoContacto, int tipoIdentificacion,
			String numeroIdentificacion, String direccionActual,
			Date fechaNacimiento, Date fechaRegistro, int nacionalidadId,
			String lugarNacimiento, boolean inatec, String usuarioInatec,
			String funcion, Long idEmpleado) {
		super();
		this.usuario = usuario;
		this.rol = rol;
		this.entidadId = entidadId;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.nombreCompleto = nombreCompleto;
		this.sexo = sexo;
		this.correo1 = correo1;
		this.correo2 = correo2;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.tipoContacto = tipoContacto;
		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
		this.direccionActual = direccionActual;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaRegistro = fechaRegistro;
		this.nacionalidadId = nacionalidadId;
		this.lugarNacimiento = lugarNacimiento;
		this.inatec = inatec;
		this.usuarioInatec = usuarioInatec;
		this.funcion = funcion;
		this.idEmpleado = idEmpleado;
	}
	
	public Contacto() {
		super();		
	}
}