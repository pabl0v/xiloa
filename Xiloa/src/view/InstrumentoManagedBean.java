package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import model.Contacto;
import model.Guia;
import model.Instrumento;
import model.Mantenedor;
import support.Item;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;

/**
 * 
 * @author Denis Chavez
 * 
 * JSF ManagedBean asociado a la interfaz instrumentos.xhtml
 * Esta clase maneja lo que se muestra en la página, los eventos relacionados 
 * e interacciona con la capa de servicio (paquete service)
 */

@Component
@Scope(value="view")
public class InstrumentoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IService service;
	
	@Autowired
	private LoginController controller;
	
	/* dchavez. 08/04/2014: agregar filtro de unidades de competencia por certificacion
	 * 
	 * */
	
	private Long idCertificacion;
	private String nombreCertificacion;
	private Long selectedCertificacion;
	private List<Item> catalogoCertificaciones;
	private Map<Long,Item> mapCertificaciones;
	
	/*
	 * fin del cambio
	 */
	
	private Instrumento instrumento;
	private Instrumento selectedInstrumento;
	private Guia guia;
	private Guia selectedGuia;
	private List<Instrumento> instrumentos;	
	private Map<Integer,Mantenedor> catalogoTiposInstrumento;
	private Integer selectedTipoInstrumento;
	private List<Item> catalogoUnidades;
	private Long selectedUnidad;
	private Contacto contacto;

	public InstrumentoManagedBean(){
		super();
		instrumento = new Instrumento();
		instrumento.setEstatus(true);
		selectedInstrumento = new Instrumento();
		selectedInstrumento.setEstatus(true);
		guia = new Guia();
		guia.setEstatus(true);
		selectedGuia = new Guia();
		selectedGuia.equals(true);
		catalogoTiposInstrumento = new HashMap<Integer, Mantenedor>();
		selectedTipoInstrumento = null;
		catalogoUnidades = new ArrayList<Item>();
		selectedUnidad = null;
		contacto = new Contacto();
		selectedCertificacion = null;
		catalogoCertificaciones = new ArrayList<Item>();
		mapCertificaciones = new HashMap<Long, Item>();
	}
	
	@PostConstruct
	private void init(){
		contacto = controller.getContacto();
		catalogoTiposInstrumento = service.getCatalogoTiposInstrumento();
		catalogoCertificaciones = service.getCertificacionesItem(contacto.getEntidadId());
		for(int i=0; i<catalogoCertificaciones.size(); i++)
			mapCertificaciones.put(catalogoCertificaciones.get(0).getId(), catalogoCertificaciones.get(0));
		configurarInstrumento();
	}
	
	public Long getIdCertificacion(){
		return idCertificacion;
	}
	
	public void setIdCertificacion(Long id){
		this.idCertificacion = id;
	}
	
	public String getNombreCertificacion() {
		return nombreCertificacion;
	}
	
	public void setNombreCertificacion(String nombre){
		this.nombreCertificacion = nombre;
	}
	
	public List<Item> getCatalogoCertificaciones(){
		return catalogoCertificaciones;
	}
	
	//dchavez. 08/04/2014: agregando filtro de certificaciones
	public void handleCertificacionesChange(){
		if(selectedCertificacion != 0){
			List<Item> unidades = service.getUnidadesItemByCertificacionId(selectedCertificacion);
			setCatalogoUnidades(unidades);
		}
		else
			setCatalogoUnidades(service.getUnidadesItemByCertificacionId(null));
	}
	
	public void setSelectedCertificacion(Long id){
		this.selectedCertificacion = id;
	}
	
	public Long getSelectedCertificacion(){
		return selectedCertificacion;
	}

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public Instrumento getSelectedInstrumento() {
		return selectedInstrumento;
	}

	public void setSelectedInstrumento(Instrumento instrumento) {
		this.selectedInstrumento = instrumento;
		Object [] objs =  new Object [] {instrumento.getId()};
		this.selectedInstrumento.setGuias(service.getGuiaByParam("Guia.findByIdInstrumento", objs));				
	}
		
	public Guia getGuia() {
		return guia;
	}

	public void setGuia(Guia guia) {
		this.guia = guia;
	}
	
	public Guia getSelectedGuia() {
		return selectedGuia;
	}

	public void setSelectedGuia(Guia guia) {
		this.selectedGuia = guia;
	}

	public List<Mantenedor> getCatalogoTiposInstrumento() {
		return new ArrayList<Mantenedor>(catalogoTiposInstrumento.values());
	}

	public void setCatalogoTiposInstrumento(List<Mantenedor> catalogoTiposInstrumento) {
		for(int i=0; i<catalogoTiposInstrumento.size(); i++){
			this.catalogoTiposInstrumento.put(catalogoTiposInstrumento.get(i).getId(), catalogoTiposInstrumento.get(i));
		}
	}

	public Integer getSelectedTipoInstrumento() {
		return selectedTipoInstrumento;
	}

	public void setSelectedTipoInstrumento(Integer selectedTipoInstrumento) {
		this.selectedTipoInstrumento = selectedTipoInstrumento;
	}

	public List<Item> getCatalogoUnidades() {
		return catalogoUnidades;
	}
	
	public void setCatalogoUnidades(List<Item> unidades){
		this.catalogoUnidades= unidades;
	}

	public Long getSelectedUnidad() {
		return selectedUnidad;
	}

	public void setSelectedUnidad(Long selectedUnidad) {
		this.selectedUnidad = selectedUnidad;
	}
	
	public String configurarInstrumento(Long idCertificacion, String nombreCertificacion){
		
		this.idCertificacion = idCertificacion;
		this.nombreCertificacion = nombreCertificacion;
			
		this.catalogoUnidades = service.getUnidadesItemByCertificacionId(idCertificacion);
		this.instrumentos = service.getInstrumentosByCertificacionId(idCertificacion);

		return "/modulos/planificacion/instrumentos?faces-redirect=true";
	}
	
	public String configurarInstrumento(){
		
		this.idCertificacion = null;
		this.nombreCertificacion = "";
		
		this.catalogoUnidades = service.getUnidadesItemByCertificacionId(null);
		this.instrumentos = service.getInstrumentos(controller.getEntidadUsuario());

		return "/modulos/planificacion/instrumentos?faces-redirect=true";
	}
		
	public void guardarInstrumento(Instrumento instrumento){		
		instrumento.setTipo(catalogoTiposInstrumento.get(selectedTipoInstrumento));
		instrumento.setUnidad(selectedUnidad);
		instrumento.setEntidadId(contacto.getEntidadId());
		instrumento.setCertificacionId(selectedCertificacion);

		if(instrumento.isCualitativo()){
			instrumento.setPuntajeMaximo(new Float(100));
			instrumento.setPuntajeMinimo(new Float(100 - ((100/instrumento.getCantidadPreguntas())*instrumento.getRespuestasFallidas())));
		}
			
		Instrumento i = (Instrumento) service.guardar(instrumento);
		instrumentos = service.getInstrumentos(controller.getEntidadUsuario());
		setSelectedInstrumento(i);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Instrumento registrado exitosamente"));			
	}
		
	public void nuevoInstrumento(){
		instrumento = new Instrumento();
		instrumento.setEstatus(true);
		selectedTipoInstrumento = null;
		selectedUnidad = null;
		selectedCertificacion = null;
	}
	
	public void editarInstrumento(Instrumento instrumento){
		this.setInstrumento(instrumento);
		setSelectedTipoInstrumento(instrumento.getTipo().getId());
		//setSelectedUnidad(catalogoUnidades.get(instrumento.getUnidad()).getId());
		setSelectedUnidad(instrumento.getUnidad());
		setSelectedCertificacion(instrumento.getCertificacionId());
	}

	public String aceptar(){
		selectedInstrumento = new Instrumento();
		selectedTipoInstrumento = null;
		selectedUnidad = null;
		selectedCertificacion = null;
		return "/modulos/planificacion/planificacion?faces-redirect=true";
	}
	
	public void nuevaGuia(){
		guia = new Guia();
		guia.setEstatus(true);
		guia.setInstrumento(selectedInstrumento);
		selectedUnidad = null;
	}
	
	public void editarGuia(Guia guia){
		this.guia = guia;
		selectedUnidad = guia.getUnidadId();
	}
		
	public void guardarGuia(Guia guia){		
		if(guia.getInstrumento().isCualitativo()){
			guia.setPuntaje(new Float(100/guia.getInstrumento().getCantidadPreguntas()));
		}
		
		guia.setUnidadId(selectedUnidad);
		service.guardar(guia);
		setSelectedInstrumento(selectedInstrumento);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Guia registrada exitosamente"));
	}
	
	public List<Instrumento> getInstrumentos() {
		return instrumentos;
	}

	public void setInstrumentos(List<Instrumento> instrumentos) {
		this.instrumentos = instrumentos;
	}
		
	public void onRowSelect(SelectEvent event) {
		setSelectedInstrumento((Instrumento) event.getObject());
		setSelectedTipoInstrumento(selectedInstrumento.getTipo().getId());
		//setSelectedUnidad(catalogoUnidades.get(selectedInstrumento.getUnidad()).getId());
		setSelectedUnidad(selectedInstrumento.getUnidad());
    }
  
    public void onRowUnselect(UnselectEvent event) {
    }
    
    public String getCompetenciaDescripcion(Long codigo){
    	if(codigo != null && codigo != 0)
    		return service.getCompetenciaDescripcion(codigo);
    	else
    		return "N/D";
    }
    
    public String getCertificacionDescripcion(Long codigo){
    	return mapCertificaciones.get(codigo).getDescripcion();
    }

	public SelectItem[] getListaUC(){
		List<Item> unidades = new ArrayList<Item>(service.getCatalogoUnidades().values());
	
		SelectItem[] opciones = new SelectItem[unidades.size()+1];
		opciones[0] = new SelectItem("","Seleccione");
		for(int i=0; i<unidades.size(); i++)
			opciones[i+1] = new SelectItem(unidades.get(i).getId(),unidades.get(i).getDescripcion());
		return opciones;
	}
	
	public SelectItem[] getListaCertificaciones(){
		List<Item> certificaciones = new ArrayList<Item>(service.getCertificacionesItem(controller.getEntidadUsuario()));
		
		SelectItem[] opciones = new SelectItem[certificaciones.size()+1];
		opciones[0] = new SelectItem("","Seleccione");
		for(int i=0; i<certificaciones.size(); i++)
			opciones[i+1] = new SelectItem(certificaciones.get(i).getId(),certificaciones.get(i).getDescripcion());
		return opciones;
	}
}