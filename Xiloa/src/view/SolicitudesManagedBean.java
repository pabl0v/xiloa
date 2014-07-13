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
@Scope(value="view")
public class SolicitudesManagedBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	
	@Autowired
	private LoginController login; 
	
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String numeroIdentificacion;
	private String descEmpresaLabora;
	private Integer experiencia;
	private String ocupacion;
	private boolean indicaTrabaja;
	
	private Contacto userSolicitante;
	private Certificacion certificacionSolicitante;
			
				
	//Implementacion SelectItems
	private List<SelectItem> listCentros;
	private List<SelectItem> listCertificaciones;	
	private List<Certificacion> listCertificaciones1;
	private Map<Integer, SelectItem> centros;
	private Map<Long, SelectItem> certificaciones;
	
	private Integer selectedIdIfp;		
	private Long selectedIdCertificacion;		
			
	private Usuario usuarioSolicitante;
	
	private boolean indicaUserExterno;
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Constructor de la clase.
	public SolicitudesManagedBean() {
		
		super();			
				
		listCentros = new ArrayList<SelectItem>();
		listCertificaciones = new ArrayList<SelectItem>();	
		listCertificaciones1 = new ArrayList<Certificacion> ();
		
		centros = new HashMap<Integer, SelectItem>();
		certificaciones = new HashMap<Long, SelectItem>();
		
		this.setIndicaTrabaja(false);
		this.setIndicaUserExterno(false);
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

	public boolean isIndicaTrabaja() {
		return indicaTrabaja;
	}

	public void setIndicaTrabaja(boolean indicaTrabaja) {
		this.indicaTrabaja = indicaTrabaja;
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
		String nombreCompleto = "";
		
		nombreCompleto = (this.getPrimerNombre().trim().isEmpty() ? "" : (this.getPrimerNombre().trim() + " " )) +
				         ((this.getSegundoNombre() == null) ? "" : (this.getSegundoNombre().trim() + " " )) +
				         (this.getPrimerApellido().trim().isEmpty() ? "" : (this.getPrimerApellido().trim() + " " )) +
						 ((this.getSegundoApellido() == null) ? "" : (this.getSegundoApellido().trim() + " " ));
		
		return nombreCompleto;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
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

	public Integer getSelectedIdIfp() {
		return selectedIdIfp;
	}

	public void setSelectedIdIfp(Integer idIfp) {
		this.selectedIdIfp = idIfp;
		
		List<Certificacion> certificacionList = service.getCertificacionesByIdIfp(this.getSelectedIdIfp());	
		this.listCertificaciones1 = new ArrayList<Certificacion> ();
		
		this.listCertificaciones = new ArrayList<SelectItem>();
		this.listCertificaciones.add(new SelectItem(null,"Seleccione una certificacion"));
		for (Certificacion dato : certificacionList) {
			this.listCertificaciones.add(new SelectItem(dato.getId(),dato.getNombre()));
			this.listCertificaciones1.add(dato);
			certificaciones.put(dato.getId(), new SelectItem(dato.getId(),dato.getNombre()));
		}
	}
	
	public Long getSelectedIdCertificacion() {		
		return selectedIdCertificacion;
	}

	public void setSelectedIdCertificacion(Long idCertificacion) {
		this.selectedIdCertificacion = idCertificacion;
		System.out.println("Certificacion seleccionada: "+idCertificacion);
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

	public void setCertificacionSolicitante(Certificacion certificacionSolicitante) {
		this.certificacionSolicitante = certificacionSolicitante;
	}

	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Metodo que se ejecuta posterior al constructor de la clase.
	@PostConstruct
	private void initBean(){
		List<Ifp> lista = service.getIfpByInatec(login.getEntidadUsuario());		
		this.listCentros.add(new SelectItem(null,"Seleccione un Centro de Capacitacion"));
		for (Ifp dato : lista) {
			this.listCentros.add(new SelectItem(dato.getIfpId(),dato.getIfpNombre()));
			String id = String.valueOf(dato.getIfpId());
			centros.put(dato.getIfpId(), new SelectItem(Long.parseLong(id),dato.getIfpNombre()));
		}
		
		this.listCertificaciones = new ArrayList<SelectItem>();		
		this.listCertificaciones.add(new SelectItem(null,"Seleccione una certificacion"));
			
		//Considerando si se tiene la certificacion seleccionada.
		if (this.usuarioSolicitante == null){
			Usuario usuarioExterno = (Usuario)FacesUtil.getParametroSession("UsuarioAplica");
			
			if (usuarioExterno != null) {
				this.usuarioSolicitante = usuarioExterno;
				this.setIndicaUserExterno(true);
				
			}
		}

		if (this.certificacionSolicitante == null) {
			Certificacion cert = (Certificacion)FacesUtil.getParametroSession("CertificacionSeleccionada");
					
			if (cert != null) {
				this.certificacionSolicitante = cert;
				inicializaDatos ();
				
			}
		}
							
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Consulta las certificaciones seleccionadas.
	public void handleCertificaciones() {				
		List<Certificacion> certificacionList = service.getCertificacionesByIdIfp(this.getSelectedIdIfp());	
		this.listCertificaciones1 = new ArrayList<Certificacion> ();
		
		this.listCertificaciones = new ArrayList<SelectItem>();		
		this.listCertificaciones.add(new SelectItem(null,"Seleccione una certificacion"));
		for (Certificacion dato : certificacionList) {
			this.listCertificaciones.add(new SelectItem(dato.getId(),dato.getNombre()));
			this.listCertificaciones1.add(dato);
			certificaciones.put(dato.getId(), new SelectItem(dato.getId(),dato.getNombre()));
		}
	}
		
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Redirecciona al facet registro_solicitud.xhtml
	public String nuevaSolicitud(){		
		return "/modulos/solicitudes/registro_solicitud?faces-redirect=true";
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Redirecciona al facet expediente.xhtml
	public String editaSolicitud(){		
	return "/modulos/solicitudes/expediente?faces-redirect=true";
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Redirecciona al facet solicitudes.xhtml
	public String cancelarEdicion() {		
		return "/modulos/solicitudes/solicitudes?faces-redirect=true";				
	}

	/*
	public String guardar(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (this.getNumeroIdentificacion() == null){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje", "Debe indicar el numero de cedula..."));
			return null;
		} 
		
		if (ValidatorUtil.validateCedula(this.getNumeroIdentificacion()) == false){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje", "El número de cedula indicado es incorrecto. Favor revisar.... "));
			return null;
		}
		
		//validar que el usuario no tenga solicitudes pendientes
		

		return null;
	}*/
			
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Guarda la solicitud por usuario interno del inatec.
	public String guardar(){
						
		FacesContext context = FacesContext.getCurrentInstance();
		
		//validar que el solicitante no tenga solicitudes pendientes
		
		if (this.getNumeroIdentificacion() == null){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje", "Debe indicar el número de cédula..."));
			return null;
		}
		
		if (ValidatorUtil.validateCedula(this.getNumeroIdentificacion()) == false){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje", "El número de cedula indicado es incorrecto. Favor revisar.... "));
			return null;
		}
		
		if(service.tieneSolicitudesPendientes(this.numeroIdentificacion, this.selectedIdCertificacion)){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje", "El candidato tiene solicitudes pendientes o ya posee esta certificacion..."));
			return null;
		}
				
		Solicitud sol = grabarSolicitud(new Integer(1));
		
		if ( sol != null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje", "La solicitud ha sido registrada exitosamente. El número es: " + sol.getTicket()));
			return "/modulos/solicitudes/solicitudes?faces-redirect=true";
		}else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje", "Error al grabar la solicitud. Favor revisar..."));
		}
		return null;
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Guarda la solicitud por el usuario externo via OpenId.
	public String guardarBySolicitante(){
				
		if (this.certificacionSolicitante != null){			
			this.selectedIdIfp = certificacionSolicitante.getIfpId();		
			this.selectedIdCertificacion = certificacionSolicitante.getId();			
		}
		
		Solicitud sol = grabarSolicitud(new Integer(2));		
		
		if (sol != null) {			
			FacesUtil.setParamBySession("dbSolicitudesBean",sol);			
			return "/modulos/solicitudes/expediente?faces-redirect=true";
		} else 
			return null;
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Graba la solicitud.
	public Solicitud grabarSolicitud(Integer tipoGrabar){
		
		Solicitud 		s 		= null;
		Usuario   		u 		= null;
		Rol       		r 		= null;		
		Certificacion 	c 		= null;		
		FacesMessage 	msg 	= null;
		String    		mensaje = "";
		String    		titulo  = "";
		boolean   		isError = false;
		Contacto 		solicitante      = null;
		Contacto 		contactoByUser   = null;
		Contacto 		contactoByCedula = null;
		
		Mantenedor 		estadoInicialSolicitud = null;
				
		//Validaciones
		if (this.getPrimerNombre() == null){
			mensaje = "Debe indicar el primer nombre. ";
			titulo = "Informacion incompleta: ";
			isError = true;
		} else if (this.getPrimerApellido() == null){
			mensaje = "Debe indicar el primer Apellido. ";
			titulo = "Informacion incompleta: ";
			isError = true;
		} else if (this.getNumeroIdentificacion() == null){
			mensaje = "Debe indicar el numero de cedula. ";
			titulo = "Informacion incompleta: ";
			isError = true;
		} else if (ValidatorUtil.validateCedula(this.getNumeroIdentificacion()) == false){
			mensaje = "El número de cedula indicado es incorrecto. Favor revisar.... ";
			titulo = "Formato de Cedula Incorrecto: ";
			isError = true;
		} else {
			
			try{
				
				c = service.getCertificacionById(this.getSelectedIdCertificacion());
												
				if (tipoGrabar == 2) {//Miriam Martinez Cano || Proyecto SCCL - INATEC || Se esta agregando una solicitud por usuario externo via OpenId
					u = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());
					//r = service.getRolById(u.getRol().getId());
					r = u.getRol();
					
					if (u.getContacto() != null) 
						contactoByUser = u.getContacto();					
				} else {
					u = null;
					r = service.getRolById(new Integer(6)); // Rol visitante
				}			
				
				contactoByCedula = service.getContactoByCedula(this.getNumeroIdentificacion());
												
				//Nuevo Contacto
				if ((contactoByUser == null) && (contactoByCedula == null)) {
					System.out.println("Usuario Indicado " + u);				
					String segundoNombre = null;
					String segundoApellido = null;
					
					segundoNombre = (this.getSegundoNombre() == null) ? null : this.getSegundoNombre().toUpperCase().trim();
					segundoApellido = (this.getSegundoApellido() == null) ? null : this.getSegundoApellido().toUpperCase().trim();
					
					solicitante = new Contacto(u, //Usuario
							                   null, //laborales
											  r, //Rol
											  null, //EntidadId
											  this.getPrimerNombre().toUpperCase().trim(), 
											  segundoNombre, 
											  this.getPrimerApellido().toUpperCase().trim(),
											  segundoApellido, 
											  this.getNombreCompleto().toUpperCase().trim(), // NombreCompleto 
											  null, //Sexo
											  "", // correo1 
											  "", //correo2 
											  "", //telefono1 
											  "", //telefono2
											  null, // tipoContacto
											  null, // tipoIdentificacion
											  this.getNumeroIdentificacion().toUpperCase().trim(), 
											  "" , // direccionActual
											  null, // fechaNacimiento
											  new Date(), // fechaRegistro 
											  null, // nacionalidadId
											  null, //departamentoId
											  null, // municipioId
											  "", // lugarNacimiento 
											  false, // inatec 
											  null, // usuarioInatec
											  "", // funcion
											  null//idEmpleado									  
											  );		
		
					solicitante = (Contacto)service.guardar(solicitante);
					
				} else {
				 //Actualiza los datos del contacto
					solicitante = (contactoByUser == null) ? contactoByCedula : contactoByUser;
					
					if (contactoByCedula == null){
						solicitante.setNumeroIdentificacion(this.getNumeroIdentificacion().toUpperCase().trim());
						
						solicitante = (Contacto)service.guardar(solicitante);
					}
										
				}					
				
			} catch (Exception e) {
				
				mensaje = "Se generó un error al confirmar la certificacion. Favor comuníquese con Gerencia de Informatica - INATEC.";
				titulo = "Error al grabar el contacto: ";
				isError = true;
				
				e.printStackTrace();
			}
					
			if (solicitante != null) {
				s = new Solicitud ();
				
				//Asignando el estado inicial de la solicitud	
				//estadoInicialSolicitud = service.getMantenedorMinByTipo(s.getTipomantenedorestado());
				//estadoInicialSolicitud = service.getCatalogoEstadoSolicitud().get(20); //getMantenedorMinByTipo(s.getTipomantenedorestado());
				estadoInicialSolicitud = service.getMantenedorById(35);
				
				s.setNombre(c.getNombre()); // Nombre
				s.setTicket("Ninguna");
				s.setEstatus(estadoInicialSolicitud);
				s.setFechaRegistro(new Date());
				s.setFechaMatricula(new Date());
				s.setExperiencia(this.getExperiencia());
				s.setOcupacion(this.getOcupacion());
				s.setOficio(this.getOcupacion());
				s.setEscolaridad(7);
				s.setContacto(solicitante);
				s.setCertificacion(c);
				s.setEvaluaciones(null);
				s.setEmpresa(this.descEmpresaLabora);
				s.setSituacion_laboral(this.indicaTrabaja);
					  
				s = (Solicitud) service.guardar(s);
				
				s.setTicket(s.getId().toString());
				
				s = (Solicitud) service.guardar(s);				
			}
			
		} 
		
		if (isError) {			
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensaje);			
			FacesContext.getCurrentInstance().addMessage(null, msg);			
			return null;
		} else{
			return s;
		}
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Inicializa datos.
	public void inicializaDatos (){
		
		if (this.usuarioSolicitante != null){
				    
			if (this.usuarioSolicitante.getContacto() != null) {
				Contacto c = this.usuarioSolicitante.getContacto();
			
			    this.userSolicitante = c;
				
				this.primerNombre = (this.userSolicitante.getPrimerNombre() == null) ? "" : this.userSolicitante.getPrimerNombre(); 
				this.segundoNombre = (this.userSolicitante.getSegundoNombre() == null) ? "" : this.userSolicitante.getSegundoNombre();
			    this.primerApellido = (this.userSolicitante.getPrimerApellido() == null) ? "" : this.userSolicitante.getPrimerApellido();
			    this.segundoApellido = (this.userSolicitante.getSegundoApellido() == null) ? "" : this.userSolicitante.getSegundoApellido();
			    this.numeroIdentificacion = (this.userSolicitante.getNumeroIdentificacion() == null) ? "" : this.userSolicitante.getNumeroIdentificacion();
			}		
		}
		
		if (this.certificacionSolicitante != null){			
			this.selectedIdIfp = certificacionSolicitante.getIfpId();			
			this.selectedIdCertificacion = certificacionSolicitante.getId();			
		}
				
	    this.setDescEmpresaLabora("");
		this.setExperiencia(new Integer(0));
		this.setOcupacion("");		
	}

	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Obtiene el listado de centros de formacion profesional.
	public List<SelectItem> getCentros() {
		return new ArrayList<SelectItem>(centros.values());
	}

	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Obtiene el listado de certificaciones.
	public List<SelectItem> getCertificaciones() {
		return new ArrayList<SelectItem>(certificaciones.values());
	}
}