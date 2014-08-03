package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

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
import support.Ifp;
import support.UCompetencia;

/**
 * 
 * @author Denis Chavez
 * 
 * JSF ManagedBean asociado a la interfaz  planificacion.xhtml
 * Esta clase maneja lo que se muestra en la página, los eventos relacionados 
 * e interacciona con la capa de servicio (paquete service)
 */

@Component
@Scope(value="view")
public class PlanificacionManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IService service;
	@Autowired
	private LoginController controller;
	private List<Certificacion> certificaciones;
	private UCompetencia selectedCompetencia;
	private Certificacion selectedCertificacion;
	private Map<Integer, Mantenedor> catalogoEstatusCertificacion;
	private Integer selectedEstatusCertificacion;
	private List<Actividad> actividades;
	private Actividad selectedActividad;
	private Map<Integer, Mantenedor> catalogoEstatusActividad;
	private Integer selectedEstatusActividad;
	private DualListModel<Contacto> involucrados;

	public PlanificacionManagedBean(){
		super();
		certificaciones = new ArrayList<Certificacion>();
		catalogoEstatusCertificacion = new HashMap<Integer, Mantenedor>();
		catalogoEstatusActividad = new HashMap<Integer, Mantenedor>();
		actividades = new ArrayList<Actividad>();
		involucrados = new DualListModel<Contacto>();
	}
	
	@PostConstruct
	private void init(){
		certificaciones = service.getCertificaciones(controller.getEntidadUsuario());
		catalogoEstatusCertificacion = service.getMapMantenedoresByTipo("3");
		catalogoEstatusActividad = service.getMapMantenedoresByTipo("2");
		setInvolucrados(new DualListModel<Contacto>(new ArrayList<Contacto>(), new ArrayList<Contacto>()));
	}
	
	public List<Certificacion> getCertificaciones(){
		return certificaciones;
	}
	
	public Certificacion getSelectedCertificacion() {
		return selectedCertificacion;
	}

	public void setSelectedCertificacion(Certificacion selectedCertificacion) {
		this.selectedCertificacion = selectedCertificacion;
	}
	
	public Integer getSelectedEstatusCertificacion(){
		return selectedEstatusCertificacion;
	}
	
	public void setSelectedEstatusCertificacion(Integer estatus){
		this.selectedEstatusCertificacion = estatus;
	}
		
	public List<Mantenedor> getCatalogoEstatusCertificacion() {
		return new ArrayList<Mantenedor>(this.catalogoEstatusCertificacion.values());
	}

	public List<UCompetencia> getCompetencias(){
		return service.getUcompetenciaSinPlanificar(controller.getEntidadUsuario());
	}

	public UCompetencia getSelectedCompetencia() {
		return selectedCompetencia;
	}
	
	public void setSelectedCompetencia(UCompetencia selectedCompetencia) {
		this.selectedCompetencia = selectedCompetencia;
	}
	
	public List<Mantenedor> getCatalogoEstatusActividad() {
		return new ArrayList<Mantenedor>(this.catalogoEstatusActividad.values());
	}
	
	public Integer getSelectedEstatusActividad() {
		return selectedEstatusActividad;
	}
	
	public void setSelectedEstatusActividad(Integer estatus){
		this.selectedEstatusActividad = estatus;
	}
	
	public List<Actividad> getActividades(){
		return actividades;
	}
	
	public void setActividades(List<Actividad> actividades){
		this.actividades = actividades;
	}
	
	public Actividad getSelectedActividad(){
		return selectedActividad;
	}
	
	public void setSelectedActividad(Actividad actividad){
		this.selectedActividad = actividad;
		if(selectedActividad.getId() == null)
			setSelectedEstatusActividad(null);
		else
			setSelectedEstatusActividad(selectedActividad.getEstado().getId());
	}
	
	public DualListModel<Contacto> getInvolucrados(){
		return involucrados;
	}
	
	public void setInvolucrados(DualListModel<Contacto> involucrados){
		this.involucrados = involucrados;
	}
	
	public void onActividadSelect(SelectEvent event){
		setSelectedActividad((Actividad) event.getObject());
		setSelectedEstatusActividad(selectedActividad.getEstado().getId());
		setInvolucrados(new DualListModel<Contacto>(service.getInvolucradosNotInActividadId(selectedActividad.getId()), service.getInvolucradosInActividadId(selectedActividad.getId())));		
	}
	
	public void onActividadUnselect(SelectEvent event){		
	}
	
	public void onCertificacionSelect(SelectEvent event){
		setSelectedCertificacion((Certificacion) event.getObject());
		setSelectedEstatusCertificacion(selectedCertificacion.getEstatus().getId());
		setActividades(service.getActividades(selectedCertificacion.getId()));
		setSelectedActividad(new Actividad());
	}
	
	public void onCertificacionUnselect(SelectEvent event){		
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
	
	public void actualizarActividad(Actividad actividad){
		actividad.setEstado(catalogoEstatusActividad.get(selectedEstatusActividad));
		service.guardar(actividad);
	}
	
	public void nuevaCertificacion(UCompetencia competencia){
				
		Certificacion certificacion = new Certificacion(
				competencia.getOfertaId(), 
				competencia.getEstructuraId(), 
				competencia.getIdUCompetencia(), 
				competencia.getNombreUCompetencia(), 
				competencia.getNombreUCompetencia(), 
				competencia.getDisponibilidad(),
				competencia.getCosto(), 
				new Date(), 
				null, 
				null, 
				competencia.getIdCentro(), 
				competencia.getNombreCentro(), 
				competencia.getDireccion(), 
				null, 
				null, 
				controller.getContacto(), 
				null, 
				null, 
				service.getMantenedorById(16));		// estatus pendiente

		certificacion = service.guardarCertificacion(certificacion);
		
		certificaciones = service.getCertificaciones(controller.getEntidadUsuario());
	}
	
	public void actualizarCertificacion(Certificacion certificacion){
		certificacion.setEstatus(catalogoEstatusCertificacion.get(selectedEstatusCertificacion));
		service.guardar(certificacion);
	}
	
	/*
	public String editarActividades(Certificacion certificacion){
		FacesUtil.setParamBySession("certificacionId", certificacion.getId());
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	*/
	
	public void onRowSelectCompetencia(SelectEvent event) {  
    }
	
	public SelectItem[] getListaCentros(){
		List<Ifp> centros = service.getIfpByInatec(controller.getEntidadUsuario());
	
		SelectItem[] opciones = new SelectItem[centros.size()+1];
		opciones[0] = new SelectItem("","Seleccione");
		for(int i=0; i<centros.size(); i++)
			opciones[i+1] = new SelectItem(centros.get(i).getIfpNombre(),centros.get(i).getIfpNombre());
		return opciones;
	}
	
	public SelectItem[] getListaEstatus(){

		List<Mantenedor> estatusList = new ArrayList<Mantenedor>(service.getCatalogoEstatusCertificacion().values());
		SelectItem[] estatus = new SelectItem[estatusList.size() + 1];
		
		estatus[0] = new SelectItem("","Seleccione");
		for(int i=0; i<estatusList.size(); i++)
			estatus[i+1] = new SelectItem(estatusList.get(i).getValor(),estatusList.get(i).getValor());
		
		return estatus;
	}
}