package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Contacto;
import model.Evaluacion;
import model.Laboral;
import model.Rol;
import model.Solicitud;
import model.Unidad;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import service.IService;
import support.Ifp;
import support.USolicitud;

@Component
@Scope(value="session")
public class SolicitudesManagedBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String numeroIdentificacion;
	private String descEmpresaLabora;
	private int    experiencia;
	private String ocupacion;
	
	private Contacto userSolicitante;
	private Certificacion certificacionSolicitante;
			
	private List<USolicitud> listSolicitudI;
	private List<Solicitud> solicitudB;	
			
	//Implementacion SelectItems
	private List<SelectItem> listCentros;
	private List<SelectItem> listCertificaciones;	
	private List<SelectItem> listCentrosBySolicitud;
	private List<SelectItem> listCertByCentro;
	private List<SelectItem> listBuscarByAll;
	private List<SelectItem> listAccionConvo;
	private Integer selectedIdIfp;
	private Integer selectedIdIfpSolicitud;
	private Long selectedIdCertificacion;
	private Long selectedIdCertByCentro;	
	
	private String selectedBuscarByAll;
	private String buscarByAllValue;	
	
	private Integer selectedAccionConvo;
	private boolean ck_convo;
	private Solicitud selectedSolicitud;
	private USolicitud [] selectedUSolicitud;
	

	
	public SolicitudesManagedBean() {
		
		super();
		
		listSolicitudI = new ArrayList<USolicitud>();
		solicitudB = new ArrayList<Solicitud> ();	
				
		listCentros = new ArrayList<SelectItem>();
		listCertificaciones = new ArrayList<SelectItem>();	
		listCentrosBySolicitud = new ArrayList<SelectItem>();
		listCertByCentro = new ArrayList<SelectItem>();
		listBuscarByAll = new ArrayList<SelectItem> ();
		listAccionConvo = new ArrayList<SelectItem> ();
		
		selectedIdIfpSolicitud = null;		
		selectedIdCertByCentro = null;	
		
		selectedBuscarByAll = null;
		
	}

	public SolicitudesManagedBean(String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido,
			String numeroIdentificacion, String descEmpresaLabora,
			int experiencia, String ocupacion, Contacto userSolicitante,
			Certificacion certificacionSolicitante,
			List<USolicitud> listSolicitudI, List<Solicitud> solicitudB,
			List<SelectItem> listCentros, List<SelectItem> listCertificaciones,
			List<SelectItem> listCentrosBySolicitud,
			List<SelectItem> listCertByCentro,
			List<SelectItem> listBuscarByAll, List<SelectItem> listAccionConvo,
			Integer selectedIdIfp, Integer selectedIdIfpSolicitud,
			Long selectedIdCertificacion, Long selectedIdCertByCentro,
			String selectedBuscarByAll, String buscarByAllValue,
			Integer selectedAccionConvo, boolean ck_convo,
			Solicitud selectedSolicitud, USolicitud[] selectedUSolicitud) {
		super();
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.numeroIdentificacion = numeroIdentificacion;
		this.descEmpresaLabora = descEmpresaLabora;
		this.experiencia = experiencia;
		this.ocupacion = ocupacion;
		this.userSolicitante = userSolicitante;
		this.certificacionSolicitante = certificacionSolicitante;
		this.listSolicitudI = listSolicitudI;
		this.solicitudB = solicitudB;
		this.listCentros = listCentros;
		this.listCertificaciones = listCertificaciones;
		this.listCentrosBySolicitud = listCentrosBySolicitud;
		this.listCertByCentro = listCertByCentro;
		this.listBuscarByAll = listBuscarByAll;
		this.listAccionConvo = listAccionConvo;
		this.selectedIdIfp = selectedIdIfp;
		this.selectedIdIfpSolicitud = selectedIdIfpSolicitud;
		this.selectedIdCertificacion = selectedIdCertificacion;
		this.selectedIdCertByCentro = selectedIdCertByCentro;
		this.selectedBuscarByAll = selectedBuscarByAll;
		this.buscarByAllValue = buscarByAllValue;
		this.selectedAccionConvo = selectedAccionConvo;
		this.ck_convo = ck_convo;
		this.selectedSolicitud = selectedSolicitud;
		this.selectedUSolicitud = selectedUSolicitud;
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
	}
	
	public Integer getSelectedIdIfpSolicitud() {
		return selectedIdIfpSolicitud;
	}

	public void setSelectedIdIfpSolicitud(Integer selectedIdIfpSolicitud) {
		this.selectedIdIfpSolicitud = selectedIdIfpSolicitud;
	}

	public Long getSelectedIdCertificacion() {
		return selectedIdCertificacion;
	}

	public void setSelectedIdCertificacion(Long idCertificacion) {
		this.selectedIdCertificacion = idCertificacion;
	}
	
	public Long getSelectedIdCertByCentro() {
		return selectedIdCertByCentro;
	}

	public void setSelectedIdCertByCentro(Long selectedIdCertByCentro) {
		this.selectedIdCertByCentro = selectedIdCertByCentro;
	}	
	
	public String getSelectedBuscarByAll() {
		return selectedBuscarByAll;
	}

	public void setSelectedBuscarByAll(String selectedBuscarByAll) {
		this.selectedBuscarByAll = selectedBuscarByAll;
	}

	public List<SelectItem> getListCentros() {
		return listCentros;
	}

	public void setListCentros(List<SelectItem> listCentros) {
		this.listCentros = listCentros;
	}
		
	public List<SelectItem> getListCentrosBySolicitud() {
		return listCentrosBySolicitud;
	}

	public void setListCentrosBySolicitud(List<SelectItem> listCentrosBySolicitud) {
		this.listCentrosBySolicitud = listCentrosBySolicitud;
	}

	public List<SelectItem> getListCertificaciones() {
		return listCertificaciones;
	}
	
	public void setListCertificaciones(List<SelectItem> listCertificaciones) {
		this.listCertificaciones = listCertificaciones;
	}
			
	public List<SelectItem> getListCertByCentro() {
		return listCertByCentro;
	}

	public void setListCertByCentro(List<SelectItem> listCertByCentro) {
		this.listCertByCentro = listCertByCentro;
	}	
	
	public List<SelectItem> getListBuscarByAll() {
		return listBuscarByAll;
	}

	public void setListBuscarByAll(List<SelectItem> listBuscarByAll) {
		this.listBuscarByAll = listBuscarByAll;
	}	
		
	public List<SelectItem> getListAccionConvo() {
		return listAccionConvo;
	}

	public void setListAccionConvo(List<SelectItem> listAccionConvo) {
		this.listAccionConvo = listAccionConvo;
	}

	public String getBuscarByAllValue() {
		return buscarByAllValue;
	}

	public void setBuscarByAllValue(String buscarByAllValue) {
		this.buscarByAllValue = buscarByAllValue;
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

	public List<Solicitud> getSolicitudB() {		
		return this.solicitudB;
	}
	
	public void setSolicitudB(List<Solicitud> solicitudB) {
		this.solicitudB = solicitudB;
	}	
	
	public List<USolicitud> getListSolicitudI() {		
		listSolicitudI = service.getUSolicitudes();
		return listSolicitudI;
	}

	public void setListSolicitudI(List<USolicitud> solicitudI) {
		this.listSolicitudI = solicitudI;
	}
	
	public Integer getSelectedAccionConvo() {
		return selectedAccionConvo;
	}

	public void setSelectedAccionConvo(Integer selectedAccionConvo) {
		this.selectedAccionConvo = selectedAccionConvo;
	}
	
	public boolean isCk_convo() {
		return ck_convo;
	}

	public void setCk_convo(boolean ck_convo) {
		this.ck_convo = ck_convo;
	}
	
	public Solicitud getSelectedSolicitud() {
		return selectedSolicitud;
	}

	public void setSelectedSolicitud(Solicitud selectedSolicitud) {
		this.selectedSolicitud = selectedSolicitud;
	}	
	
	public USolicitud[] getSelectedUSolicitud() {
		return selectedUSolicitud;
	}

	public void setSelectedUSolicitud(USolicitud[] selectedUSolicitud) {
		this.selectedUSolicitud = selectedUSolicitud;
	}

	public void llenarListBuscarByAll () {
		this.listBuscarByAll.add(new SelectItem(null, "Todos los campos"));
		this.listBuscarByAll.add(new SelectItem("s.certificacion.ifpNombre", "Centro Evaluador"));
		this.listBuscarByAll.add(new SelectItem("s.contacto.nombreCompleto", "Nombre del Candidato"));
		this.listBuscarByAll.add(new SelectItem("s.certificacion.nombre", "Certificacion a Evaluar"));
		this.listBuscarByAll.add(new SelectItem("s.fechaRegistro", "Fecha Solicitud"));
		this.listBuscarByAll.add(new SelectItem("s.contacto.correo1", "Evaluador"));
		this.listBuscarByAll.add(new SelectItem("s.estatus", "Estado"));		
	}
	
	public void llenarListAccionConvo () {
		this.listAccionConvo.add(new SelectItem(null, "Seleccione la accion"));
		this.listAccionConvo.add(new SelectItem(new Integer(1), "Autorizar"));
		this.listAccionConvo.add(new SelectItem(new Integer(2), "Exportar a Excel"));
		this.listAccionConvo.add(new SelectItem(new Integer(3), "Exportar a PDF"));
	}
	
	
		
   //Llenado de Centro
	@PostConstruct
	private void fillListCentro(){
		List<Ifp> lista = service.getIfpByInatec();
		this.listCentrosBySolicitud.add(new SelectItem(null, "Todos"));
		for (Ifp dato : lista) {
			this.listCentros.add(new SelectItem(dato.getIfpId(),dato.getIfpNombre()));
			this.listCentrosBySolicitud.add(new SelectItem(dato.getIfpId(),dato.getIfpNombre()));
		}		
		
		llenarListBuscarByAll();
		llenarListAccionConvo ();
		handleCertByCentro();
	}
	
	public void handleCertificaciones() {				
		List<Certificacion> certificacionList = service.getCertificacionesByIdIfp(this.getSelectedIdIfp());
		listCertificaciones = new ArrayList<SelectItem>();
		for (Certificacion dato : certificacionList) {
			this.listCertificaciones.add(new SelectItem(dato.getId(),dato.getNombre()));
		}			
	}
	
	public void handleCertByCentro() {
		List<Certificacion> certificacionList = service.getCertificacionesByIdIfp(this.getSelectedIdIfpSolicitud());
		listCertByCentro = new ArrayList<SelectItem>();
		this.listCertByCentro.add(new SelectItem(null,"Todas las Unidades"));
		for (Certificacion dato : certificacionList) {
			this.listCertByCentro.add(new SelectItem(dato.getId(),dato.getNombre()));
		}
		
		this.solicitudB = service.getSolicitudesByParam(asignaParams ());		
	}
	
	public void handleBuscar () {						
		this.solicitudB = service.getSolicitudesByParam(asignaParams ());				
	}
	
	public HashMap<String, Object> asignaParams () {
		HashMap<String, Object> params = new HashMap<String, Object>();
				
		if (this.getSelectedIdIfpSolicitud() != null) {
			params.put("s.certificacion.ifpId", this.getSelectedIdIfpSolicitud());
		}
		
		if (this.selectedIdCertByCentro != null) {
			params.put("s.certificacion.id", this.selectedIdCertByCentro);
		}
		
		if (this.buscarByAllValue != null && this.selectedBuscarByAll != null) {			
			params.put(this.selectedBuscarByAll, this.buscarByAllValue);
		}
		
		return params;
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
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje", "La solicitud ha sido registrada exitosamente. El número es: " + sol.getTicket()));
		}else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje", "Error al grabar la solicitud. Favor revisar..."));
		}
          
		
	}
	
	public String guardarBySolicitante(){
		Solicitud sol = grabarSolicitud(new Integer(2));
		
		System.out.println("La solicitud ha sido grabada " + sol.getId());
		return "/modulos/solicitudes/solicitudes?faces-redirect=true";
		
	}
	
	public Solicitud grabarSolicitud(Integer tipoGrabar){
		
		Solicitud     s;
		Usuario       u;
		Rol           r = service.getRolById(1);		
		Certificacion c = service.getCertificacionById(this.getSelectedIdCertificacion());			
		
		Contacto solicitante = service.getContactoByCedula(this.getNumeroIdentificacion());
		
		if (solicitante == null ) {
			
			if (tipoGrabar == 2) {
				u = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());
			} else {
				u = null;
			}			
			
			solicitante = new Contacto(u, //Usuario
					                   null, //laborales
									  r, //Rol
									  1, //EntidadId
									  this.getPrimerNombre().toUpperCase().trim(), 
									  this.getSegundoNombre().toUpperCase().trim(), 
									  this.getPrimerApellido().toUpperCase().trim(),
									  this.getSegundoApellido().toUpperCase().trim(), 
									  this.getNombreCompleto().toUpperCase().trim(), // NombreCompleto 
									  0, //Sexo
									  "", // correo1 
									  "", //correo2 
									  "", //telefono1 
									  "", //telefono2
									  1, // tipoContacto
									  1, // tipoIdentificacion
									  this.getNumeroIdentificacion().toUpperCase().trim(), 
									  "" , // direccionActual
									  null, // fechaNacimiento
									  new Date(), // fechaRegistro 
									  1, // nacionalidadId
									  null, //departamentoId
									  null, // municipioId
									  "", // lugarNacimiento 
									  false, // inatec 
									  "", // usuarioInatec
									  "", // funcion
									  null//idEmpleado									  
									  );		

			service.guardar(solicitante);
			
			
			solicitante = service.getContactoByCedula(solicitante.getNumeroIdentificacion());
		}		
				 		
		s = new Solicitud (c.getNombre(), //nombre
				           "Ninguna", // ticket 
				           1,    // estatus
				           new Date(), // fechaRegistro
				           new Date(), // fechaMatricula
				           this.getExperiencia(), // experiencia
				           this.getOcupacion(), // ocupacion 
				           this.getOcupacion(), // oficio
				           7, // escolaridad
				           solicitante, // contacto
				           c, // certificacion
				           null // evaluaciones
				           );
		
		//Asignando el estado inicial de la solicitud
		s.setEstatus(service.getMantenedorMinByTipo(s.getTipomantenedorestado()));
		
		s = (Solicitud) service.guardar(s);
		
		s.setTicket(s.getId().toString());
		
		s = (Solicitud) service.guardar(s);
		
		/* Por cada unidad de competencia se debe registrar el instrumento a utilizar en la evaluacion */
		
		Set<Unidad> setUnidades =  c.getUnidades();
		
		/*
		for(Unidad unidad : setUnidades){			
			Evaluacion e = new Evaluacion(s, //solicitud
										  new Date(), // fecha
										  unidad, // unidad
										  0, // puntaje
										  "",// observaciones
										  false// aprobado
										  );
			
			e = (Evaluacion) service.guardar(e);
			
		}
		*/			   
		
		return s;
	}
	
	public void inicializaDatos (){
		Usuario userConectado = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());	
		
		if (userConectado.getContacto() != null) {			
			System.out.println("EXISTE EL CONTACTO");
		    this.userSolicitante = userConectado.getContacto();
			
			this.primerNombre = (this.userSolicitante.getPrimerNombre() == null) ? "" : this.userSolicitante.getPrimerNombre(); 
			this.segundoNombre = (this.userSolicitante.getSegundoNombre() == null) ? "" : this.userSolicitante.getSegundoNombre();
		    this.primerApellido = (this.userSolicitante.getPrimerApellido() == null) ? "" : this.userSolicitante.getPrimerApellido();
		    this.segundoApellido = (this.userSolicitante.getSegundoApellido() == null) ? "" : this.userSolicitante.getSegundoApellido();
		    this.numeroIdentificacion = (this.userSolicitante.getNumeroIdentificacion() == null) ? "" : this.userSolicitante.getNumeroIdentificacion();
		}		
		
		this.certificacionSolicitante = service.getCertificacionById(this.getSelectedIdCertificacion());
				
	    this.setDescEmpresaLabora("");
		this.setExperiencia(new Integer(0));
		this.setOcupacion("");		
	}
	
	
	public void handleConvocatoria() {
		boolean isError = false;
		FacesContext context = FacesContext.getCurrentInstance();
		
		// Cuando ha seleccionado la opcion Aplicar - Las solicitudes pasan a convocatoria		
		if (this.selectedAccionConvo == 1) {			
			for (USolicitud obj : this.selectedUSolicitud){				
				Solicitud sol = obj.getSolicitud();
				sol.setEstatus(3);
				sol = (Solicitud)service.guardar(sol);
				
				if (sol != null){
					isError = false;
				} else {
					isError = true;
				}
			}
			
			if ( isError) {        
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje: ", "Error al pasar las solicitudes a Convocatoria. Favor revisar..."));
			}else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje: ", "Proceso aplicado exitosamente."));
			}
			
			this.selectedUSolicitud = null;
			
		}	
		
	}

	
}