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

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;

@Component
@Scope("session")
public class ActividadManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;

	private Certificacion certificacion;
	private Integer selectedTipoActividad;
	private Map<Integer,Mantenedor> catalogoTiposActividad;
	private Integer selectedEstatusActividad;
	private Map<Integer,Mantenedor> catalogoEstatusActividad;
	private Actividad actividad;
	private String nombreCertificacion;
	private String nombreCentro;
	private String direccionCentro;
	
	private List<Contacto> contactos;
	private Contacto[] selectedContactos;
	
	public ActividadManagedBean(){
		super();
		actividad = new Actividad();
		actividad.setFechaRegistro(new Date());
		contactos = new ArrayList<Contacto>();
		catalogoTiposActividad = new HashMap<Integer,Mantenedor>();
		catalogoEstatusActividad = new HashMap<Integer,Mantenedor>();
	}
	
	
	@PostConstruct
	private void init(){
		catalogoTiposActividad = service.getMapMantenedoresByTipo("1");
		catalogoEstatusActividad = service.getMapMantenedoresByTipo("4");
		//usuario = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());
		contactos = service.getContactosInatec();
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
		this.actividad.setInvolucrados(selectedContactos);
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
		this.selectedEstatusActividad = selectedEstatusActividad;
		this.actividad.setEstado(catalogoEstatusActividad.get(selectedEstatusActividad));
	}
	
	public Actividad getActividad(){
		return actividad;
	}
	
	public void setCatalogoTiposActividad(Map<Integer, Mantenedor> catalogoTiposActividad) {
		System.out.println("ActividadManagedBean setCatalogoTiposActividad : "+catalogoTiposActividad.size());
		this.catalogoTiposActividad = catalogoTiposActividad;
	}
	
	public List<Mantenedor> getCatalogoTiposActividad() {
		return new ArrayList<Mantenedor>(catalogoTiposActividad.values());
	}
	
	/*
	public Map<Integer, Mantenedor> getCatalogoTiposActividad() {
		return catalogoTiposActividad;
	}*/
	
	public void setCatalogoEstatusActividad(Map<Integer, Mantenedor> catalogoEstatusActividad) {
		System.out.println("ActividadManagedBean setCatalogoEstatusActividad : "+catalogoEstatusActividad.size());
		this.catalogoEstatusActividad = catalogoEstatusActividad;
	}

	public List<Mantenedor> getCatalogoEstatusActividad() {
		return new ArrayList<Mantenedor>(catalogoEstatusActividad.values());
	}
	
	/*
	public Map<Integer, Mantenedor> getCatalogoEstatusActividad() {
		return catalogoEstatusActividad;
	}*/
	
	public String editarActividad(String nombreCertificacion, Actividad actividad, String nombreCentro, String direccionCentro){
		this.actividad = actividad;
		this.setNombreCertificacion(nombreCertificacion);
		this.setNombreCentro(nombreCentro);
		this.setDireccionCentro(direccionCentro);
		this.selectedEstatusActividad = actividad.getEstado().getId();
		return "/modulos/planificacion/edicion_actividad?faces-redirect=true";
	}
	
	public String cancelar(){
		actividad = new Actividad();
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	
	public void selectedActividadListener(SelectEvent event){
		this.actividad = (Actividad)event.getObject();
	}

	public String guardar(Actividad actividad){
		actividad.setEstado(catalogoEstatusActividad.get(selectedEstatusActividad));
		this.actividad = (Actividad)service.guardar(actividad);
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	
	public String getNombreCentro() {
		return nombreCentro;
	}

	public String getNombreCertificacion(){
		return nombreCertificacion;
	}
	
	public void setNombreCertificacion(String nombre){
		this.nombreCertificacion = nombre;
	}
	
	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}

	public String getDireccionCentro() {
		return direccionCentro;
	}

	public void setDireccionCentro(String direccionCentro) {
		this.direccionCentro = direccionCentro;
	}
}