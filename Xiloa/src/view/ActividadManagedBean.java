package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import model.Actividad;
import model.Bitacora;
import model.Certificacion;
import model.Contacto;
import model.Mantenedor;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;
/**
 * 
 * @author Denis Chavez
 * 
 * JSF ManagedBean asociado a la interfaz  edicion_actividad.xhtml
 * Esta clase maneja lo que se muestra en la página, los eventos relacionados 
 * e interacciona con la capa de servicio (paquete service)
 */
@Component
@Scope("session")
public class ActividadManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	
	@Autowired
	private LoginController controller;

	private Certificacion certificacion;
	@SuppressWarnings("unused")
	private Integer indiceActividad;
	private Integer selectedTipoActividad;
	private Map<Integer,Mantenedor> catalogoTiposActividad;
	private Integer selectedEstatusActividad;
	private Map<Integer,Mantenedor> catalogoEstatusActividad;
	private Actividad actividad;
	private Bitacora bitacora;
	private Contacto contacto;
	
	private List<Contacto> contactos;
	private Contacto[] selectedContactos;
	private Contacto selectedContacto;
	
	public ActividadManagedBean(){
		super();
		actividad = new Actividad();
		actividad.setFechaRegistro(new Date());
		indiceActividad = 0;
		contactos = new ArrayList<Contacto>();
		catalogoTiposActividad = new HashMap<Integer,Mantenedor>();
		catalogoEstatusActividad = new HashMap<Integer,Mantenedor>();
		bitacora = new Bitacora();
		contacto = new Contacto();
	}

	@PostConstruct
	private void init(){
		catalogoTiposActividad = service.getMapMantenedoresByTipo("1");
		catalogoEstatusActividad = service.getMapMantenedoresByTipo("2");
		contacto = controller.getContacto();
		contactos = service.getContactosInatec(controller.getEntidadUsuario());
	}
	
	public Certificacion getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(Certificacion certificacion) {
		this.certificacion = certificacion;
	}
	
	public List<Contacto> getContactos() {
		return contactos;
	}
	
	public Contacto[] getSelectedContactos() {
		return selectedContactos;
	}
	
	public void setSelectedContactos(Contacto[] selectedContactos) {
		this.selectedContactos = selectedContactos;
		//this.actividad.setInvolucrados(selectedContactos);
	}
	
	public Contacto getSelectedContacto() {
		return selectedContacto;
	}

	public void setSelectedContacto(Contacto selectedContacto) {
		this.selectedContacto = selectedContacto;
		//this.actividad.setInvolucrados(new Contacto[] {selectedContacto});
		this.actividad.addInvolucrado(new Contacto[] {selectedContacto});
	}

	public void reset(){
		this.actividad = new Actividad();
		this.selectedEstatusActividad = 0;
		this.selectedTipoActividad = 0;
	}

	public Integer getSelectedTipoActividad() {
		return selectedTipoActividad;
	}

	public void setSelectedTipoActividad(Integer selectedTipoActividad) {
		this.selectedTipoActividad = selectedTipoActividad;
		this.actividad.setTipo(catalogoTiposActividad.get(selectedTipoActividad));
	}

	public Integer getSelectedEstatusActividad() {
		return selectedEstatusActividad;
	}

	public void setSelectedEstatusActividad(Integer selectedEstatusActividad) {
		
		if(selectedEstatusActividad == 11 && (actividad.getInvolucrados().isEmpty() || actividad.getFechaInicial() == null || actividad.getFechaFinal() == null )){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN , "", "Debe completar las fechas y los involucrados"));
		}
		else{
		
			this.selectedEstatusActividad = selectedEstatusActividad;
			this.actividad.setEstado(catalogoEstatusActividad.get(selectedEstatusActividad));
		}
	}
	
	public Actividad getActividad(){
		return actividad;
	}
	
	public Bitacora getBitacora() {
		return bitacora;
	}
	
	public void agregarBitacora(){
		bitacora = new Bitacora();
	}

	public void guardarBitacora(Bitacora bitacora) {
		bitacora.setActividad(actividad);
		bitacora.setFechaRegistro(new Date());
		bitacora.setUsuario(contacto);
		service.guardar(bitacora);
		this.bitacora = new Bitacora();
	}

	public List<Bitacora> getBitacoras() {
		return service.getBitacoras(actividad.getId());
	}

	public void setCatalogoTiposActividad(Map<Integer, Mantenedor> catalogoTiposActividad) {
		this.catalogoTiposActividad = catalogoTiposActividad;
	}
	
	public List<Mantenedor> getCatalogoTiposActividad() {
		return new ArrayList<Mantenedor>(catalogoTiposActividad.values());
	}
		
	public void setCatalogoEstatusActividad(Map<Integer, Mantenedor> catalogoEstatusActividad) {
		this.catalogoEstatusActividad = catalogoEstatusActividad;
	}

	public List<Mantenedor> getCatalogoEstatusActividad() {
		return new ArrayList<Mantenedor>(catalogoEstatusActividad.values());
	}
		
	public String editarActividad(Certificacion certificacion, Actividad actividad){
		this.certificacion = certificacion;
		this.actividad = actividad;
		this.indiceActividad = certificacion.getActividades().indexOf(actividad);
		this.selectedEstatusActividad = actividad.getEstado().getId();
		return "/modulos/planificacion/edicion_actividad?faces-redirect=true";
	}
	
	public String ejecuciones(Actividad actividad){
		this.actividad = actividad;
		this.certificacion = actividad.getCertificacion();
		this.indiceActividad = certificacion.getActividades().indexOf(actividad);
		this.selectedEstatusActividad = actividad.getEstado().getId();
		return "/modulos/planificacion/bitacoras?faces-redirect=true";
	}
	
	public void completarActividad(Actividad actividad){
		this.actividad = actividad;
		this.actividad.setEstado(service.getMantenedorById(15));
		this.actividad = (Actividad) service.guardar(this.actividad);
	}
		
	public String cancelar(){
		actividad = new Actividad();
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	
	public String cerrar(){
		actividad = new Actividad();
		return "/modulos/planificacion/ejecuciones?faces-redirect=true";
	}
	
	public void selectedActividadListener(SelectEvent event){
		this.actividad = (Actividad)event.getObject();
	}

	public String guardar(Actividad actividad){
				
		actividad.setEstado(catalogoEstatusActividad.get(selectedEstatusActividad));
		actividad = (Actividad)service.guardar(actividad);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Actividad actualizada exitosamente"));
		
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	
	public Contacto getContacto(){
		return contacto;
	}
}