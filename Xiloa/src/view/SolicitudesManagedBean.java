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
import javax.servlet.http.HttpServletRequest;

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
import support.Ifp;
import util.ValidatorUtil;

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
	private int    experiencia;
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
				         (this.getSegundoNombre().trim().isEmpty() ? "" : (this.getSegundoNombre().trim() + " " )) +
				         (this.getPrimerApellido().trim().isEmpty() ? "" : (this.getPrimerApellido().trim() + " " )) +
						 (this.getSegundoApellido().trim().isEmpty() ? "" : (this.getSegundoApellido().trim() + " " ));
		
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

	public int getExperiencia() {
		return experiencia;
	}
	
	public void setExperiencia(int experiencia) {
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
			Usuario usuarioExterno = (Usuario)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).getAttribute("UsuarioAplica");
			
			if (usuarioExterno != null) {
				this.usuarioSolicitante = usuarioExterno;
				this.setIndicaUserExterno(true);
				//((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("UsuarioAplica",null);
			}
		}

		if (this.certificacionSolicitante == null) {
			Certificacion cert = (Certificacion)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).getAttribute("CertificacionSeleccionada");
			if (cert != null) {
				this.certificacionSolicitante = cert;
				inicializaDatos ();
				//((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("CertificacionSeleccionada",null);
			}
		}
							
	}
	
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
		
	
	public String nuevaSolicitud(){
		//Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//setSolicitudI(params.get("solicitudI"));			
	return "/modulos/solicitudes/registro_solicitud?faces-redirect=true";
	}
	
	public String editaSolicitud(){
		//Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//setSolicitudI(params.get("solicitudI"));			
	return "/modulos/solicitudes/expediente?faces-redirect=true";
	}
	
	public String cancelarEdicion() {		
		return "/modulos/solicitudes/solicitudes?faces-redirect=true";				
	}
			
	public void guardar(){
				
		FacesContext context = FacesContext.getCurrentInstance();
		
		Solicitud sol = grabarSolicitud(new Integer(1));
		
		if ( sol != null) {        
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje", "La solicitud ha sido registrada exitosamente. El n�mero es: " + sol.getTicket()));
		}else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje", "Error al grabar la solicitud. Favor revisar..."));
		}
          
		
	}
	
	public String guardarBySolicitante(){
				
		if (this.certificacionSolicitante != null){			
			this.selectedIdIfp = certificacionSolicitante.getIfpId();		
			this.selectedIdCertificacion = certificacionSolicitante.getId();
			
			System.out.println("Certificacion " + this.selectedIdCertificacion);
		}
		
		Solicitud sol = grabarSolicitud(new Integer(2));		
		
		if (sol != null) {
			
			System.out.println("La solicitud ha sido grabada " + sol.getId());
			
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("dbSolicitudesBean",sol);
			
			return "/modulos/solicitudes/expediente?faces-redirect=true";
		} else 
			return null;
	}
	
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
			mensaje = "El n�mero de cedula indicado es incorrecto. Favor revisar.... ";
			titulo = "Formato de Cedula Incorrecto: ";
			isError = true;
		} else {
			
			try{
				
				c = service.getCertificacionById(this.getSelectedIdCertificacion());
												
				if (tipoGrabar == 2) {
					u = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());
					r = service.getRolById(u.getRol().getId());
					
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
					solicitante = new Contacto(u, //Usuario
							                   null, //laborales
											  r, //Rol
											  null, //EntidadId
											  this.getPrimerNombre().toUpperCase().trim(), 
											  this.getSegundoNombre().toUpperCase().trim(), 
											  this.getPrimerApellido().toUpperCase().trim(),
											  this.getSegundoApellido().toUpperCase().trim(), 
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
				
				mensaje = "Se gener� un error al confirmar la certificacion. Favor comun�quese con Gerencia de Informatica - INATEC.";
				titulo = "Error al grabar el contacto: ";
				isError = true;
				
				e.printStackTrace();		
				
			}	
					
			if (solicitante != null) {
				s = new Solicitud ();
				
				//Asignando el estado inicial de la solicitud	
				estadoInicialSolicitud = service.getMantenedorMinByTipo(s.getTipomantenedorestado());
				
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
	
	public void inicializaDatos (){
		
		System.out.println("Entra a solicitudesManagedBean.inicializaDatos");
		//Usuario userConectado = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());	
		if (this.usuarioSolicitante != null){
			
		    System.out.println("Indica usuario Ext ");
		    
			if (this.usuarioSolicitante.getContacto() != null) {
				Contacto c = this.usuarioSolicitante.getContacto();
				System.out.println("EXISTE EL CONTACTO");
			    this.userSolicitante = c;
				
				this.primerNombre = (this.userSolicitante.getPrimerNombre() == null) ? "" : this.userSolicitante.getPrimerNombre(); 
				this.segundoNombre = (this.userSolicitante.getSegundoNombre() == null) ? "" : this.userSolicitante.getSegundoNombre();
			    this.primerApellido = (this.userSolicitante.getPrimerApellido() == null) ? "" : this.userSolicitante.getPrimerApellido();
			    this.segundoApellido = (this.userSolicitante.getSegundoApellido() == null) ? "" : this.userSolicitante.getSegundoApellido();
			    this.numeroIdentificacion = (this.userSolicitante.getNumeroIdentificacion() == null) ? "" : this.userSolicitante.getNumeroIdentificacion();
			}		
		}
		
		//this.certificacionSolicitante = service.getCertificacionById(this.getSelectedIdCertificacion());
		if (this.certificacionSolicitante != null){			
			this.selectedIdIfp = certificacionSolicitante.getIfpId();			
			this.selectedIdCertificacion = certificacionSolicitante.getId();
			
			System.out.println("Indica la certificacion: " + this.selectedIdCertificacion);
		}
				
	    this.setDescEmpresaLabora("");
		this.setExperiencia(new Integer(0));
		this.setOcupacion("");		
	}

	public List<SelectItem> getCentros() {
		return new ArrayList<SelectItem>(centros.values());
	}

	public List<SelectItem> getCertificaciones() {
		return new ArrayList<SelectItem>(certificaciones.values());
	}
}