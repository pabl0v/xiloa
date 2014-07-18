package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Contacto;
import model.Mantenedor;
import model.Rol;
import model.Solicitud;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;
import support.FacesUtil;
import support.Ifp;
import util.ValidatorUtil;

//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Bean asociado al facet registro_solicitud.xhtml y registro_solicitud_userExterno.xhtml
@Component
@Scope(value = "view")
public class SolicitudesManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;

	@Autowired
	private LoginController login;

	private Contacto solicitante;

	private String descEmpresaLabora;
	private Integer experiencia;
	private String ocupacion;
	private boolean indicaTrabaja;

	private Contacto userSolicitante;
	private Certificacion certificacionSolicitante;

	// Implementacion SelectItems
	private List<SelectItem> listCentros;
	private List<SelectItem> listCertificaciones;
	private List<Certificacion> listCertificaciones1;
	private Map<Integer, SelectItem> centros;
	private Map<Long, SelectItem> certificaciones;

	private Integer selectedIdIfp;
	private Long selectedIdCertificacion;

	private Usuario usuarioSolicitante;

	private boolean indicaUserExterno;
	String mensaje;
	boolean isError;
	String titulo;
	

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA ||
	// Constructor de la clase.
	public SolicitudesManagedBean() {

		super();

		listCentros = new ArrayList<SelectItem>();
		listCertificaciones = new ArrayList<SelectItem>();
		listCertificaciones1 = new ArrayList<Certificacion>();

		centros = new HashMap<Integer, SelectItem>();
		certificaciones = new HashMap<Long, SelectItem>();

		this.setIndicaTrabaja(false);
		this.setIndicaUserExterno(false);
		solicitante = new Contacto(); 
	}

	public List<Certificacion> getListCertificaciones1() {
		return listCertificaciones1;
	}

	public void setListCertificaciones1(List<Certificacion> listCertificaciones1) {
		this.listCertificaciones1 = listCertificaciones1;
	}

	public boolean isIndicaUserExterno() {
		return indicaUserExterno;
	}

	public void setIndicaUserExterno(boolean indicaUserExterno) {
		this.indicaUserExterno = indicaUserExterno;
	}

	public Usuario getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(Usuario usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}

	public Integer getSelectedIdIfp() {
		return selectedIdIfp;
	}

	public void setSelectedIdIfp(Integer idIfp) {
		this.selectedIdIfp = idIfp;

		List<Certificacion> certificacionList = service
				.getCertificacionesByIdIfp(this.getSelectedIdIfp());
		this.listCertificaciones1 = new ArrayList<Certificacion>();

		this.listCertificaciones = new ArrayList<SelectItem>();
		this.listCertificaciones.add(new SelectItem(null,
				"Seleccione una certificacion"));
		for (Certificacion dato : certificacionList) {
			this.listCertificaciones.add(new SelectItem(dato.getId(), dato
					.getNombre()));
			this.listCertificaciones1.add(dato);
			certificaciones.put(dato.getId(),
					new SelectItem(dato.getId(), dato.getNombre()));
		}
	}

	public Long getSelectedIdCertificacion() {
		return selectedIdCertificacion;
	}

	public void setSelectedIdCertificacion(Long idCertificacion) {
		this.selectedIdCertificacion = idCertificacion;
		System.out.println("Certificacion seleccionada: " + idCertificacion);
	}

	public List<SelectItem> getListCentros() {
		return listCentros;
	}

	public void setListCentros(List<SelectItem> listCentros) {
		this.listCentros = listCentros;
	}

	public List<SelectItem> getListCertificaciones() {
		return listCertificaciones;
	}

	public void setListCertificaciones(List<SelectItem> listCertificaciones) {
		this.listCertificaciones = listCertificaciones;
	}

	public Contacto getUserSolicitante() {
		return userSolicitante;
	}

	public void setUserSolicitante(Contacto userSolicitante) {
		this.userSolicitante = userSolicitante;
	}

	public Certificacion getCertificacionSolicitante() {
		return certificacionSolicitante;
	}

	public void setCertificacionSolicitante(
			Certificacion certificacionSolicitante) {
		this.certificacionSolicitante = certificacionSolicitante;
	}

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Metodo que
	// se ejecuta posterior al constructor de la clase.
	@PostConstruct
	private void initBean() {


		cargarListadoCentros();
		this.listCertificaciones = new ArrayList<SelectItem>();
		this.listCertificaciones.add(new SelectItem(null,
				"Seleccione una certificacion"));

		// Considerando si se tiene la certificacion seleccionada.
		if (this.usuarioSolicitante == null) {
			Usuario usuarioExterno = (Usuario) FacesUtil
					.getParametroSession("UsuarioAplica");

			if (usuarioExterno != null) {
				this.usuarioSolicitante = usuarioExterno;
				this.setIndicaUserExterno(true);

			}
		}

		if (this.certificacionSolicitante == null) {
			Certificacion cert = (Certificacion) FacesUtil
					.getParametroSession("CertificacionSeleccionada");

			if (cert != null) {
				this.certificacionSolicitante = cert;
				inicializaDatos();

			}
		}

	}

	private void cargarListadoCentros() {
		List<Ifp> lista = service.getIfpByInatec(login.getEntidadUsuario());
		this.listCentros.add(new SelectItem(null,
				"Seleccione un Centro de Capacitacion"));
		for (Ifp dato : lista) {
			this.listCentros.add(new SelectItem(dato.getIfpId(), dato
					.getIfpNombre()));
			String id = String.valueOf(dato.getIfpId());
			centros.put(dato.getIfpId(), new SelectItem(Long.parseLong(id),
					dato.getIfpNombre()));
		}
	}

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Consulta
	// las certificaciones seleccionadas.
	public void handleCertificaciones() {
		List<Certificacion> certificacionList = service
				.getCertificacionesByIdIfp(this.getSelectedIdIfp());
		this.listCertificaciones1 = new ArrayList<Certificacion>();

		this.listCertificaciones = new ArrayList<SelectItem>();
		this.listCertificaciones.add(new SelectItem(null,
				"Seleccione una certificacion"));
		for (Certificacion dato : certificacionList) {
			this.listCertificaciones.add(new SelectItem(dato.getId(), dato
					.getNombre()));
			this.listCertificaciones1.add(dato);
			certificaciones.put(dato.getId(),
					new SelectItem(dato.getId(), dato.getNombre()));
		}
	}

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA ||
	// Redirecciona al facet registro_solicitud.xhtml
	public String nuevaSolicitud() {
		return "/modulos/solicitudes/registro_solicitud?faces-redirect=true";
	}

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA ||
	// Redirecciona al facet expediente.xhtml
	public String editaSolicitud() {
		return "/modulos/solicitudes/expediente?faces-redirect=true";
	}

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA ||
	// Redirecciona al facet solicitudes.xhtml
	public String cancelarEdicion() {
		return "/modulos/solicitudes/solicitudes?faces-redirect=true";
	}

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Guarda la
	// solicitud por usuario interno del inatec.
	public String guardar() {

		// validar que el solicitante no tenga solicitudes pendientes

		FacesContext context = FacesContext.getCurrentInstance();
		Solicitud sol = grabarSolicitud(new Integer(1));

		if (sol != null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "SCCL - Mensaje",
					"La solicitud ha sido registrada exitosamente. El número es: "
							+ sol.getTicket()));
			return "/modulos/solicitudes/solicitudes?faces-redirect=true";
		} else if (isError) {
			
            FacesUtil.getMensaje(titulo, mensaje, false);
		} else
			FacesUtil.getMensaje("SCCL - Mensaje", "Error al grabar la solicitud. Favor revisar...", true);
		return null;
	}

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Guarda la
	// solicitud por el usuario externo via OpenId.
	public String guardarBySolicitante() {

		if (this.certificacionSolicitante != null) {
			this.selectedIdIfp = certificacionSolicitante.getIfpId();
			this.selectedIdCertificacion = certificacionSolicitante.getId();
		}

		Solicitud sol = grabarSolicitud(new Integer(2));

		if (sol != null) {
			FacesUtil.setParamBySession("dbSolicitudesBean", sol);
			return "/modulos/solicitudes/expediente?faces-redirect=true";
		} else
			return null;
	}

	public Solicitud grabarSolicitud(Integer tipoGrabar) {
		Solicitud s = null;
		validarDatos();
		if (!isError) {
			Contacto solicitante = definirSolicitante(tipoGrabar);
			if (solicitante != null) {

				s = new Solicitud();
				// Asignando el estado inicial de la solicitud
				// estadoInicialSolicitud =
				// service.getMavalidarDatos()ntenedorMinByTipo(s.getTipomantenedorestado());
				// estadoInicialSolicitud =
				// service.getCatalogoEstadoSolicitud().get(20);
				// //getMantenedorMinByTipo(s.getTipomantenedorestado());
				// ccarvajal:Linea de codigo temporal para mientras se termina
				// el ajuste de los mantenedores
				Mantenedor estadoInicialSolicitud = service
						.getMantenedorById(35);

				s.setNombre(solicitante.getNombreCompleto()); // Nombre
				s.setTicket("Ninguna");
				s.setEstatus(estadoInicialSolicitud);
				s.setFechaRegistro(new Date());
				s.setFechaMatricula(new Date());
				s.setExperiencia(this.getExperiencia());
				s.setOcupacion(this.getOcupacion());
				s.setOficio(this.getOcupacion());
				s.setEscolaridad(7);
				s.setContacto(solicitante);
				s.setCertificacion(service.getCertificacionById(this
						.getSelectedIdCertificacion()));
				s.setEvaluaciones(null);
				s.setEmpresa(this.descEmpresaLabora);
				s.setSituacion_laboral(this.indicaTrabaja);

				s = (Solicitud) service.guardar(s);

				s.setTicket(s.getId().toString());

				s = (Solicitud) service.guardar(s);

			}
		}
		return s;
	}

	private boolean validarDatos() {
		isError = false;

		if (service.tieneSolicitudesPendientes(
				this.solicitante.getNumeroIdentificacion(),
				this.selectedIdCertificacion)) {

			mensaje = "El candidato tiene solicitudes pendientes o ya posee esta certificacion... ";
			titulo = "Informacion Pendiente: ";

			isError = true;

		}
		if (solicitante.getPrimerNombre() == null) {
			mensaje = "Debe indicar el primer nombre. ";
			titulo = "Informacion incompleta: ";
			isError = true;
		} else if (solicitante.getPrimerApellido() == null) {
			mensaje = "Debe indicar el primer Apellido. ";
			titulo = "Informacion incompleta: ";
			isError = true;
		} else if (solicitante.getNumeroIdentificacion() == null) {
			mensaje = "Debe indicar el numero de cedula. ";
			titulo = "Informacion incompleta: ";
			isError = true;
		} // else if
			// (ValidatorUtil.validateCedula(solicitante.getNumeroIdentificacion())){
			// mensaje =
			// "El número de cedula indicado es incorrecto!. Favor revisar.... ";
			// titulo = "Formato de Cedula Incorrecto: ";
			// isError = true;
		// }
		// System.out.println("error al validar cedula "
		// +ValidatorUtil.validateCedula(solicitante.getNumeroIdentificacion())
		// + "para " +solicitante.getNumeroIdentificacion());

		return isError;

	}

	private Contacto definirSolicitante(int tipoGrabar) {

		Certificacion certificacion;
		Usuario usuario = null;
		Rol rol;

		Contacto contactoByUser = null, contactoByCedula;

		try {

			if (tipoGrabar == 2) {// Miriam Martinez Cano || Proyecto SCCL -
									// INATEC || Se esta agregando una solicitud
									// por usuario externo via OpenId
				usuario = service.getUsuarioLocal(SecurityContextHolder
						.getContext().getAuthentication().getName());

				rol = usuario.getRol();

				if (usuario.getContacto() != null)
					contactoByUser = usuario.getContacto();
			} else {
				usuario = null;
				rol = service.getRolById(new Integer(6)); // Rol visitante
			}
			contactoByCedula = service.getContactoByCedula(solicitante.getNumeroIdentificacion());
			solicitante.setUsuario(usuario);
			solicitante.setRol(rol);

			if (contactoByUser == null) {
				if (contactoByCedula == null) {

					completarDatosSolicitante();

					return solicitante;
				} else
					return contactoByUser;
			} else
				return contactoByCedula;

		} catch (Exception e) {
			String mensaje = "Se geeró un error al confirmar la certificacion. Favor comuníquese con Gerencia de Informatica - INATEC.";
			String titulo = "Error al grabar el contacto: ";
		
            FacesUtil.getMensaje(titulo, titulo, true);
			boolean isError = true;

			e.printStackTrace();
			return null;
		}

	}

	private void completarDatosSolicitante() {
		solicitante.setNumeroIdentificacion(solicitante.getNumeroIdentificacion().toUpperCase().trim());
		solicitante
				.setSegundoNombre((solicitante.getSegundoNombre() == null) ? null: solicitante.getSegundoNombre().toUpperCase().trim());
		solicitante
				.setSegundoApellido((solicitante.getSegundoApellido() == null) ? null: solicitante.getSegundoApellido().toUpperCase().trim());

		solicitante.setPrimerNombre(solicitante.getPrimerNombre().toUpperCase().trim());

		solicitante.setPrimerApellido(solicitante.getPrimerApellido().toUpperCase().trim());

		solicitante.setNombreCompleto(solicitante.getPrimerNombre() + " " + solicitante.getSegundoNombre() + " " +
		                              solicitante.getPrimerApellido() + " " + solicitante.getSegundoApellido()); // NombreCompleto

		solicitante.setCorreo1("");// correo1
		solicitante.setCorreo2(""); // correo2
		solicitante.setTelefono1(""); // telefono1
		solicitante.setTelefono2(""); // telefono2

		solicitante.setNumeroIdentificacion(solicitante
				.getNumeroIdentificacion().toUpperCase().trim());
		solicitante.setDireccionActual(""); // direccionActual

		solicitante.setFechaRegistro(new Date()); // fechaRegistro

		solicitante.setLugarNacimiento(""); // lugarNacimiento
		solicitante.setInatec(false); // inatec

		solicitante.setFuncion(""); // funcion

	}

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Inicializa
	// datos.
	public void inicializaDatos() {

		if (this.usuarioSolicitante != null) {

			if (this.usuarioSolicitante.getContacto() != null) {
				// ccarvajal: innecesario >>Contacto contacto =
				// this.usuarioSolicitante.getContacto();

				this.userSolicitante = this.usuarioSolicitante.getContacto();
				
				this.solicitante = this.userSolicitante;

			}
		}

		if (this.certificacionSolicitante != null) {
			this.selectedIdIfp = certificacionSolicitante.getIfpId();
			this.selectedIdCertificacion = certificacionSolicitante.getId();
		}

		this.setDescEmpresaLabora("");
		this.setExperiencia(new Integer(0));
		this.setOcupacion("");
	}

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Obtiene el
	// listado de centros de formacion profesional.
	public List<SelectItem> getCentros() {
		return new ArrayList<SelectItem>(centros.values());
	}

	// Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Obtiene el
	// listado de certificaciones.
	public List<SelectItem> getCertificaciones() {
		return new ArrayList<SelectItem>(certificaciones.values());
	}

	public String getDescEmpresaLabora() {
		return descEmpresaLabora;
	}

	public void setDescEmpresaLabora(String descEmpresaLabora) {
		this.descEmpresaLabora = descEmpresaLabora;
	}

	public Integer getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(Integer experiencia) {
		this.experiencia = experiencia;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public boolean isIndicaTrabaja() {
		return indicaTrabaja;
	}

	public void setIndicaTrabaja(boolean indicaTrabaja) {
		this.indicaTrabaja = indicaTrabaja;
	}

	public Contacto getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Contacto solicitante) {
		this.solicitante = solicitante;
	}

}