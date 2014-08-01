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
import model.Involucrado;
import model.Mantenedor;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
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
	private Map<Integer, Mantenedor> catalogoEstatusCertificacion;
	private Integer selectedEstatusCertificacion;
	private Map<Integer, Mantenedor> catalogoEstatusActividad;
	private Integer selectedEstatusActividad;
	private List<Actividad> actividades;
	private Actividad selectedActividad;
	private DualListModel<Contacto> involucrados;
	
	public CertificacionManagedBean(){
		super();
		certificacion = new Certificacion();
		actividades = new ArrayList<Actividad>();
		selectedActividad = new Actividad();
		catalogoEstatusCertificacion = new HashMap<Integer, Mantenedor>();
		catalogoEstatusActividad = new HashMap<Integer, Mantenedor>();
		involucrados = new DualListModel<Contacto>();
	}
	
	@PostConstruct
	private void init(){
		
		Long certificacionId = (Long)FacesUtil.getParametroSession("certificacionId");
		setCertificacion(service.getCertificacionById(certificacionId));
		setSelectedEstatusCertificacion(certificacion.getEstatus().getId());
		
		setActividades(service.getActividades(certificacionId));

		catalogoEstatusCertificacion = service.getMapMantenedoresByTipo("3");
		catalogoEstatusActividad = service.getMapMantenedoresByTipo("2");
		
		setInvolucrados(new DualListModel<Contacto>(new ArrayList<Contacto>(), new ArrayList<Contacto>()));
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
	
	public DualListModel<Contacto> getInvolucrados(){
		return involucrados;
	}
	
	public void setInvolucrados(DualListModel<Contacto> involucrados){
		this.involucrados = involucrados;
	}
		
	public String cancelar(){
		certificacion = new Certificacion();
		return "/modulos/planificacion/planificacion?faces-redirect=true";
	}
	
	public String guardarEdicion(){

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
	
	public void onActividadSelect(SelectEvent event){
		setSelectedActividad((Actividad) event.getObject());
		setSelectedEstatusActividad(selectedActividad.getEstado().getId());
		setInvolucrados(new DualListModel<Contacto>(service.getInvolucradosNotInActividadId(selectedActividad.getId()), service.getInvolucradosInActividadId(selectedActividad.getId())));		
	}
	
	public void onActividadUnselect(SelectEvent event){		
	}
	
	public void guardarActividad(){
		service.guardar(selectedEstatusActividad);
	}
	
	public void onElementTransfer(TransferEvent event){
		
		// agregar involucrado
		if(event.isAdd()){
			for(Object item : event.getItems()) {
				if(item instanceof Contacto){
					Contacto contacto = (Contacto) item;
					service.guardar(new Involucrado(selectedActividad, contacto));
				}
			}
		}
		
		//remover involucrado
		if(event.isRemove()){
			for(Object item : event.getItems()) {
				if(item instanceof Contacto){
					Contacto contacto = (Contacto) item;
					service.inactivarInvolucrado(selectedActividad.getId(), contacto.getId());
				}
			}
		}
	}
}