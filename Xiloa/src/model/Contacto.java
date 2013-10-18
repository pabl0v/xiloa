package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private String numeroIdentificaicon;

	@NotNull
	@Column(name = "direccion_actual", nullable = false)	
	private String direccionActual;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_nacimiento", nullable = false)
	@Temporal(TemporalType.DATE)	
	private Date fechaNacimiento;
	
	@NotNull
	@Column(name = "nacionalidad_id", nullable = false)		
	private int nacionalidadId;

	@NotNull
	@Column(name = "lugar_nacimiento", nullable = false)	
	private String lugarNacimiento;

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

	public String getNumeroIdentificaicon() {
		return numeroIdentificaicon;
	}

	public void setNumeroIdentificaicon(String numeroIdentificaicon) {
		this.numeroIdentificaicon = numeroIdentificaicon;
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
}