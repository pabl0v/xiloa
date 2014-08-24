package model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.contactos 
 * 
 */

@Entity(name = "contactos")
@Table(name = "contactos", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Contacto.findByCedulaId", query="select c from contactos c where upper(c.numeroIdentificacion) = upper(?1)"),
	@NamedQuery(name="Contacto.findByLogin", query="select c from contactos c inner join fetch c.rol r inner join fetch c.usuario u where u.usuarioAlias =?1"),
	@NamedQuery(name="Contacto.findByLoginInatec", query="select c from contactos c left join fetch c.rol r where c.inatec = 'true' and c.usuarioInatec =?1"),
	@NamedQuery(name="Contacto.findInvolucradosInatec", query="Select c from contactos c where c.inatec='true' and c.rol.id in (1,2,3,4,5,6,7) and c.entidadId is not null and c.entidadId = case ?1 when 1000 then c.entidadId else ?1 end"),
	@NamedQuery(name="Contacto.findAllPortafolio", query="select c from contactos c where exists (select 1 from solicitudes s where s.contacto.id = c.id and (s.certificacion.ifpId = case ?1 when 1000 then s.certificacion.ifpId else ?1 end)) order by 1 desc"),
	@NamedQuery(name="Contacto.findByActividadId", query="select i.contacto from involucrados i where i.actividad.id=?1 and i.activo=true order by i.id desc"),
	@NamedQuery(name="Contacto.findNotInActividadId",
	query=
	"select c "+
	"from	contactos c, actividades a, certificaciones x "+
	"where	a.id=?1 "+
			"and a.certificacion.id=x.id "+
			"and c.inatec=true "+
			"and c.rol.id=	case "+
						"when a.tipo.id in (8,9,10,11) then 7	"+	//evaluador evalua
						"when a.tipo.id in (6,7) then 1 "+			//asesor con asesoria grupal e individual
						"when a.tipo.id in (5) then 3	"+			//tecnico docente matricula
						"when a.tipo.id in (1,2,3,4) then 4 "+		//informante prematricula y selecciona
						"when a.tipo.id in (12) then 2 "+			//verificador
						"else 0 end "+
			"and c.entidadId=x.ifpId "+
			"and not exists (select 1 from involucrados i where i.activo=true and i.contacto.id=c.id and i.actividad.id=a.id) "+
			"order by c.id desc"
	)
})
public class Contacto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "contacto_id", nullable = false)	
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="contacto", cascade = CascadeType.ALL)
	private Set<Laboral> laborales;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rol_id")
	private Rol rol;
	
	@Column(name = "entidad_id", nullable = true)	
	private Integer entidadId;
	
	@Column(name = "primer_nombre", nullable = false)
	private String primerNombre;
	
	@Column(name = "segundo_nombre", nullable = true)	
	private String segundoNombre;

	@Column(name = "primer_apellido", nullable = false)	
	private String primerApellido;

	@Column(name = "segundo_apellido", nullable = true)	
	private String segundoApellido;
	
	@Column(name = "nombre_completo", nullable = true)
	private String nombreCompleto;

	@Column(name = "sexo_id", nullable = true)
	private Integer sexo;
	
	@Column(name = "correo1", nullable = false)
	private String correo1;
	
	@Column(name = "correo2", nullable = true)
	private String correo2;
	
	@Column(name = "telefono1", nullable = false)	
	private String telefono1;
	
	@Column(name = "telefono2", nullable = true)	
	private String telefono2;

	@Column(name = "tipo_contacto", nullable = true)		
	private Integer tipoContacto;
	
	@Column(name = "tipo_identificacion", nullable = true)	
	private Integer tipoIdentificacion;
	
	@Column(name = "numero_identificacion", nullable = false)
	private String numeroIdentificacion;

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
	
	@Column(name = "nivel_academico", nullable = true)
	private Integer nivelAcademico;
	
	@Column(name = "nacionalidad_id", nullable = true)		
	private String nacionalidadId;
	
	@Column(name = "departamento_id", nullable = true)		
	private Integer departamentoId;
	
	@Column(name = "municipio_id", nullable = true)		
	private Integer municipioId;

	@Column(name = "lugar_nacimiento", nullable = true)	
	private String lugarNacimiento;
	
	@Column(name = "inatec", nullable = false)
	private boolean inatec = false;
	
	@Column(name = "usuario_inatec", unique = true, nullable = true)
	private String usuarioInatec;
	
	@Column(name = "funcion", nullable = true)
	private String funcion;
	
	@Column(name = "id_empleado", nullable = true)
	private Long idEmpleado;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Laboral> getLaborales() {
		return laborales;
	}

	public void setLaborales(Set<Laboral> laborales) {
		this.laborales = laborales;
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

	public Integer getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(Integer entidadId) {
		this.entidadId = entidadId;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		if(segundoNombre == null)
			return "";
		else
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
		if(segundoApellido == null)
			return "";
		else
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

	public Integer getSexo() {
		return sexo;
	}

	public void setSexo(Integer sexo) {
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

	public Integer getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(Integer tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	public Integer getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(Integer tipoIdentificacion) {
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

	public String getNacionalidadId() {
		return nacionalidadId;
	}
	
	public Integer getNivelAcademico(){
		return nivelAcademico;
	}
	
	public void setNivelAcademico(Integer nivel){
		this.nivelAcademico = nivel;
	}

	public void setNacionalidadId(String nacionalidadId) {
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
	
	public Integer getDepartamentoId() {
		return departamentoId;
	}

	public void setDepartamentoId(Integer departamentoId) {
		this.departamentoId = departamentoId;
	}

	public Integer getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Integer municipioId) {
		this.municipioId = municipioId;
	}

	public Contacto(Usuario usuario, Set<Laboral> laborales, Rol rol,
			Integer entidadId, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido,
			String nombreCompleto, Integer sexo, String correo1, String correo2,
			String telefono1, String telefono2, Integer tipoContacto,
			Integer tipoIdentificacion, String numeroIdentificacion,
			String direccionActual, Date fechaNacimiento, Date fechaRegistro,
			String nacionalidadId, Integer departamentoId,
			Integer municipioId, String lugarNacimiento, boolean inatec,
			String usuarioInatec, String funcion, Long idEmpleado) {
		super();		
		this.usuario = usuario;
		this.laborales = laborales;
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
		this.departamentoId = departamentoId;
		this.municipioId = municipioId;
		this.lugarNacimiento = lugarNacimiento;
		this.inatec = inatec;
		this.usuarioInatec = usuarioInatec;
		this.funcion = funcion;
		this.idEmpleado = idEmpleado;
	}

	public Contacto() {
		super();
		this.laborales = new HashSet<Laboral>();
	}
}