package view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Certificacion;
import model.Contacto;
import model.Evaluacion;
import model.Rol;
import model.Solicitud;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.USolicitud;


@Component
@Scope("session")
public class SolicitudesManagedBean {
	
	@Autowired
	private IService service;
	
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String numeroIdentificacion;
	private String descEmpresaLabora;
	private int experiencia;
	private String ocupacion;
		
	
	private List<USolicitud> solicitudI = new ArrayList<USolicitud>();
	private List<Solicitud> solicitudB = new ArrayList<Solicitud> ();
	
	
	public String getPrimerNombre() {
		return primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	
	public String getNombreCompleto() {
		String nombreCompleto = "";
		
		nombreCompleto = (this.getPrimerNombre().trim().isEmpty() ? "" : (this.getPrimerNombre().trim() + " " )) +
				         (this.getSegundoNombre().trim().isEmpty() ? "" : (this.getSegundoNombre().trim() + " " )) +
				         (this.getPrimerApellido().trim().isEmpty() ? "" : (this.getPrimerApellido().trim() + " " )) +
						 (this.getSegundoApellido().trim().isEmpty() ? "" : (this.getSegundoApellido().trim() + " " ));
		
		return nombreCompleto;
	}

	public String getDescEmpresaLabora() {
		return descEmpresaLabora;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public List<Solicitud> getSolicitudB() {
		solicitudB = service.getSolicitudes();
		return solicitudB;
	}
	
	public List<USolicitud> getSolicitudI() {
		System.out.println("getSolicitudI desde SolicitudesManagedBean");
		solicitudI = service.getUSolicitudes();
		return solicitudI;
	}
	public void setSolicitudI(List<USolicitud> solicitudI) {
		this.solicitudI = solicitudI;
	}
	
	public String nuevaSolicitud(){
		//Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//setSolicitudI(params.get("solicitudI"));			
	return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	
	public void guardar(){
		
		Solicitud     s;
		Rol           r = service.getRolById(1);
		Certificacion c = new Certificacion();
				
		Contacto solicitante = new Contacto(null, //Usuario 
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
										  null, // fechaRegistro 
										  1, // nacionalidadId
										  "", // lugarNacimiento 
										  false, // inatec 
										  "", // usuarioInatec
										  "", // funcion
										  null//idEmpleado
										  );		
		
		service.guardarContacto(solicitante);
		
		
		s = new Solicitud (c.getNombre(), //nombre
				           null, // ticket 
				           1, // estatus
				           null, // fechaRegistro
				           null, // fechaMatricula
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

