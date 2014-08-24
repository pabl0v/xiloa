package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Contacto;
import model.Requisito;
import model.Solicitud;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;
import support.Departamento;
import support.FacesUtil;
import support.Ifp;
import support.Item;
import support.Municipio;
import util.ValidatorUtil;

@Component
@Scope(value = "view")
public class CertificacionesManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	@Autowired
	private LoginController login;
	private List<Certificacion> certificaciones;
	private Certificacion selectedCertificacion;
	private Contacto solicitante;
	private Solicitud solicitud;
	private List<Departamento> departamentos;
	private List<Municipio> municipios;
	private List<Item> niveles;
	private Integer selectedDepartamento;
	private Integer selectedMunicipio;
	private Integer selectedNivel;

	public CertificacionesManagedBean() {
		super();
		solicitante = new Contacto();
		solicitud = new Solicitud();
		departamentos = new ArrayList<Departamento>();
		municipios = new ArrayList<Municipio>();
		niveles = new ArrayList<Item>();
	}
	
	@PostConstruct
	private void init() {
		Integer entidad = login.getEntidadUsuario();
		
		if(entidad == null)
			entidad = 1000;

		certificaciones = service.getCertificacionesActivasByCentroId(entidad);
		departamentos = service.getDepartamentos();
		niveles = service.getCatalogoNivelAcademico();
	}
	
	public List<Departamento> getDepartamentos(){
		return departamentos;
	}
	
	public Integer getSelectedDepartamento(){
		return selectedDepartamento;
	}

	public void setSelectedDepartamento(Integer departamento){
		this.selectedDepartamento = departamento;
	}
	
	public Integer getSelectedMunicipio(){
		return selectedMunicipio;
	}
	
	public void setSelectedMunicipio(Integer municipio){
		this.selectedMunicipio = municipio;
	}
	
	public Integer getSelectedNivel(){
		return selectedNivel;
	}
	
	public void setSelectedNivel(Integer nivel){
		this.selectedNivel = nivel;
	}
	
	public void handleDepartamentoChange(){
		municipios = service.getMunicipios(selectedDepartamento);
	}

	public List<Municipio> getMunicipios(){
		return municipios;
	}
	
	public List<Item> getNiveles(){
		return niveles;
	}
	
	public List<Certificacion> getCertificaciones(){
		return certificaciones;
	}
	
	public Certificacion getSelectedCertificacion(){
		return selectedCertificacion;
	}
	
	public void setSelectedCertificacion(Certificacion certificacion){
		this.selectedCertificacion = certificacion;
	}
	
	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Contacto getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Contacto solicitante) {
		this.solicitante = solicitante;
	}
	
	public List<Requisito> getRequisitos(Long certificacionId){
		return service.getRequisitos(certificacionId);
	}

	public void onRowSelect(SelectEvent event) {
		setSelectedCertificacion((Certificacion) event.getObject());
    }
  
    public void onRowUnselect(UnselectEvent event) {
    }
    
	public SelectItem[] getListaCentros(){
		List<Ifp> centros = service.getIfpByInatec(login.getEntidadUsuario());
	
		SelectItem[] opciones = new SelectItem[centros.size()+1];
		opciones[0] = new SelectItem("","Seleccione");
		for(int i=0; i<centros.size(); i++)
			opciones[i+1] = new SelectItem(centros.get(i).getIfpNombre(),centros.get(i).getIfpNombre());
		return opciones;
	}
    
    public void nuevaSolicitud(){
    	if(login.getContacto().getRol().getId()==5)		//si es visitante
    	{
    			solicitante = service.getContactoByLogin(login.getLoggedUser());
    			setSelectedDepartamento(solicitante.getDepartamentoId());
    			municipios = service.getMunicipios(selectedDepartamento);
    			setSelectedMunicipio(solicitante.getMunicipioId());
    			setSelectedNivel(solicitante.getNivelAcademico());
    	}
    	else
    		solicitante = new Contacto();

		solicitud = new Solicitud();
    }
    
    public boolean isVisitante(){
    	if(login.getContacto().getRol().getId()==5)
    		return true;
    	else
    		return false;
    }
	
	public String registrarSolicitud(Solicitud solicitud, Contacto solicitante){
		
		//si el rol es visitante, antes de aplicar debe completar su portafolio

		if(login.getContacto().getRol().getId()==5)
		{
			if(		solicitante.getPrimerNombre() == null || 
					solicitante.getPrimerApellido() == null || 
					solicitante.getFechaNacimiento() == null || 
					solicitante.getNumeroIdentificacion() == null || 
					solicitante.getSexo() == null || 
					solicitante.getCorreo1() == null || 
					solicitante.getDireccionActual() == null || 
					solicitante.getNacionalidadId() == null || 
					solicitante.getDepartamentoId() == null || 
					solicitante.getMunicipioId() == null ||
					solicitante.getNivelAcademico() == null)
			{
				FacesUtil.getMensaje("SCCL - Mensaje: ", "Antes de aplicar debe completar su portafolio.", true);
				return null;
			}
		}
		
		//validar si tiene solicitudes pendientes
		if(service.tieneSolicitudesPendientes(solicitante.getNumeroIdentificacion(), selectedCertificacion.getId())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "El candidato ya tiene una solicitud en proceso.", true);
			return null;
		}
		
		//validar la cedula del candidato
		if(!ValidatorUtil.validateCedula(solicitante.getNumeroIdentificacion())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "La cedula es invalida.", true);
			return null;
		}
		
		//validar fecha de nacimiento
		if(!ValidatorUtil.validarEdadSolicitante(solicitante.getFechaNacimiento())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "La edad del canditato debe estar entre 18 y 45 años.", true);
			return null;			
		}
		
		Date fecha = ValidatorUtil.obtenerFechaNacimientoDeCedula(solicitante.getNumeroIdentificacion());	
		if(fecha.compareTo(solicitante.getFechaNacimiento()) != 0){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "La fecha de nacimiento debe coincidir con la cédula.", true);
			return null;						
		}
		
		solicitud.setCertificacion(selectedCertificacion);
		solicitante.setDepartamentoId(selectedDepartamento);
		solicitante.setMunicipioId(selectedMunicipio);
		solicitante.setNivelAcademico(selectedNivel);
		service.registrarSolicitud(solicitud, solicitante);
		return "/modulos/solicitudes/solicitudes?faces-redirect=true";
	}
}