package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import model.Actividad;
import model.Certificacion;
import model.Contacto;
import model.Mantenedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;  

import controller.LoginController;
import service.IService;
import support.FacesUtil;

/**
 * 
 * @author Denis Chavez
 * 
 * JSF ManagedBean asociado a la interfaz  edicion_planificacion.xhtml
 * Esta clase maneja lo que se muestra en la página, los eventos relacionados 
 * e interacciona con la capa de servicio (paquete service)
 */

@Component
@Scope(value="view")
public class CertificacionManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IService service;
	@Autowired
	private LoginController controller;
	
	private Certificacion certificacion;
	private List<Contacto> contactos;
	private Contacto[] selectedContactos;
	private Map<Integer, Mantenedor> catalogoEstatusCertificacion;
	private Integer selectedEstatusCertificacion;
	private Map<Integer, Mantenedor> catalogoEstatusActividad;
	private Integer selectedEstatusActividad;
	private List<Actividad> actividades;
	private Actividad selectedActividad;
	
	public CertificacionManagedBean(){
		super();
		certificacion = new Certificacion();
		contactos = new ArrayList<Contacto>();
		actividades = new ArrayList<Actividad>();
		selectedActividad = new Actividad();
		catalogoEstatusCertificacion = new HashMap<Integer, Mantenedor>();
		catalogoEstatusActividad = new HashMap<Integer, Mantenedor>();
	}
	
	@PostConstruct
	private void init(){
		
		Long certificacionId = (Long)FacesUtil.getParametroSession("certificacionId");
		setCertificacion(service.getCertificacionById(certificacionId));
		setSelectedEstatusCertificacion(certificacion.getEstatus().getId());
		
		setActividades(service.getActividades(certificacionId));
		
		contactos = service.getContactosInatec(controller.getEntidadUsuario());
		catalogoEstatusCertificacion = service.getMapMantenedoresByTipo("3");
		catalogoEstatusActividad = service.getMapMantenedoresByTipo("2");
	}
	
	public Certificacion getCertificacion(){
		return this.certificacion;
	}
	
	public void setCertificacion(Certificacion certificacion){
		this.certificacion = certificacion;
	}
	
	public List<Actividad> getActividades(){
		return this.actividades;
	}
	
	public void setActividades(List<Actividad> actividades){
		this.actividades = actividades;
	}
	
	public List<Contacto> getContactos() {
		return contactos;
	}
	
	public Contacto[] getSelectedContactos() {
		return selectedContactos;
	}
	
	public void setSelectedContactos(Contacto[] selectedContactos) {
		//this.selectedContactos = selectedContactos;
		//this.certificacion.setInvolucrados(selectedContactos);
	}
		
	public String cancelar(){
		certificacion = new Certificacion();
		return "/modulos/planificacion/planificacion?faces-redirect=true";
	}
	
	public String guardarEdicion(){
		
		//certificacion.setFechaRegistro(new Date());
		certificacion.setFechaActualiza(new Date());
		certificacion.setActualiza(controller.getContacto());
		certificacion.setReferencial("N/D");
		certificacion = (Certificacion) service.guardar(certificacion);
		certificacion = new Certificacion();
		return "/modulos/planificacion/planificacion?faces-redirect=true";
	}
	
	public String guardar(){
		
		certificacion.setFechaRegistro(new Date());
		certificacion.setCreador(controller.getContacto());
		certificacion.setReferencial("N/D");
		certificacion = (Certificacion) service.guardar(certificacion);
		certificacion = new Certificacion();
		return "/modulos/planificacion/planificacion?faces-redirect=true";
	}
		
	public void agregarActividad(Actividad actividad){
		actividad.setCreador(controller.getContacto());
		actividad.setFechaRegistro(new Date());
		Mantenedor estado = service.getMantenedorById(12); //estatus pendiente
		actividad.setEstado(estado);
		actividad.setCertificacion(certificacion);
		actividad = (Actividad)service.guardar(actividad);
	}
	
	public Integer getSelectedEstatusCertificacion() {
		return selectedEstatusCertificacion;
	}

	public void setSelectedEstatusCertificacion(Integer selectedEstatusCertificacion) {
		this.selectedEstatusCertificacion = selectedEstatusCertificacion;
		this.certificacion.setEstatus(this.catalogoEstatusCertificacion.get(selectedEstatusCertificacion));
	}

	public List<Mantenedor> getCatalogoEstatusCertificacion() {
		return new ArrayList<Mantenedor>(this.catalogoEstatusCertificacion.values());
	}
	
	public Integer getSelectedEstatusActividad() {
		return selectedEstatusActividad;
	}

	public void setSelectedEstatusActividad(Integer selectedEstatusActividad) {
		this.selectedEstatusActividad = selectedEstatusActividad;
		selectedActividad.setEstado(catalogoEstatusActividad.get(selectedEstatusActividad));
	}
	
	public List<Mantenedor> getCatalogoEstatusActividad() {
		return new ArrayList<Mantenedor>(this.catalogoEstatusActividad.values());
	}
	
	public Actividad getSelectedActividad(){
		return this.selectedActividad;
	}
	
	public void setSelectedActividad(Actividad actividad){
		this.selectedActividad = actividad;
	}
	
	public void editarActividad(Actividad actividad){
		
	}
}