package view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Contacto;
import model.Rol;
import model.Solicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.Ifp;
import support.USolicitud;

@Component
@Scope(value="session")
public class SolicitudesManagedBean {
	
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
			
	private List<USolicitud> solicitudI = new ArrayList<USolicitud>();
	private List<Solicitud> solicitudB = new ArrayList<Solicitud> ();	
			
	//Implementacion SelectItems
	private List<SelectItem> listCentros = new ArrayList<SelectItem>();
	private List<SelectItem> listCertificaciones = new ArrayList<SelectItem>();	
	private List<SelectItem> listCentrosBySolicitud = new ArrayList<SelectItem>();
	private List<SelectItem> listCertByCentro = new ArrayList<SelectItem>();
	private Integer selectedIdIfp;
	private Integer selectedIdIfpSolicitud = null;
	private Long selectedIdCertificacion;
	private Long selectedIdCertByCentro;
	
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

	public List<Solicitud> getSolicitudB() {		
		return this.solicitudB;
	}
	
	public void setSolicitudB(List<Solicitud> solicitudB) {
		this.solicitudB = solicitudB;
	}	
	
	public List<USolicitud> getSolicitudI() {		
		solicitudI = service.getUSolicitudes();
		return solicitudI;
	}

	public void setSolicitudI(List<USolicitud> solicitudI) {
		this.solicitudI = solicitudI;
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
		
		this.solicitudB = service.getSolicitudesByIfp(this.getSelectedIdIfpSolicitud());		
	}	
	
	
	public String nuevaSolicitud(){
		//Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//setSolicitudI(params.get("solicitudI"));			
	return "/modulos/solicitudes/registro_solicitud?faces-redirect=true";
	}
			
	public void guardar(){
				
		Solicitud     s;
		Rol           r = service.getRolById(1);		
		Certificacion c = service.getCertificacionById(this.getSelectedIdCertificacion());			
		
		Contacto solicitante = service.getContactoByCedula(this.getNumeroIdentificacion());
		
		if (solicitante == null ) {
			solicitante = new Contacto(null, //Usuario 
									  r, //Rol
									  1, //EntidadId
									  this.getPrimerNombre(), 
									  this.getSegundoNombre(), 
									  this.getPrimerApellido(),
									  this.getSegundoApellido(), 
									  this.getNombreCompleto(), // NombreCompleto 
									  0, //Sexo
									  "", // correo1 
									  "", //correo2 
									  "", //telefono1 
									  "", //telefono2
									  1, // tipoContacto
									  1, // tipoIdentificacion
									  this.getNumeroIdentificacion(), 
									  "" , // direccionActual
									  null, // fechaNacimiento
									  new Date(), // fechaRegistro 
									  1, // nacionalidadId
									  "", // lugarNacimiento 
									  false, // inatec 
									  "", // usuarioInatec
									  "", // funcion
									  null//idEmpleado
									  );		

			service.guardarContacto(solicitante);
			
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
				
		service.guardar(s);
		
	}

	
}