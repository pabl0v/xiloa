package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import model.Actividad;
import model.Mantenedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;

@Component
@Scope("request")
public class ActividadManagedBean {

	@Autowired
	private IService service;

	private int selectedTipoActividad;
	private Map<Integer,Mantenedor> catalogoTiposActividad;
	private int selectedEstatusActividad;
	private Map<Integer,Mantenedor> catalogoEstatusActividad;
	private Actividad actividad;
	
	public ActividadManagedBean(){
		super();
		actividad = new Actividad();
		catalogoTiposActividad = new HashMap<Integer,Mantenedor>();
		catalogoEstatusActividad = new HashMap<Integer,Mantenedor>();
	}
	
	
	@PostConstruct
	private void llenarCatalogos(){
		catalogoTiposActividad = service.getMapMantenedoresByTipo("1");
		catalogoEstatusActividad = service.getMapMantenedoresByTipo("4");
	}
	
	public void reset(){
		this.actividad = new Actividad();
		this.selectedEstatusActividad = 0;
		this.selectedTipoActividad = 0;
	}

	public int getSelectedTipoActividad() {
		return selectedTipoActividad;
	}

	public void setSelectedTipoActividad(int selectedTipoActividad) {
		this.selectedTipoActividad = selectedTipoActividad;
		this.actividad.setTipo(catalogoTiposActividad.get(selectedTipoActividad));
	}

	public int getSelectedEstatusActividad() {
		return selectedEstatusActividad;
	}

	public void setSelectedEstatusActividad(int selectedEstatusActividad) {
		this.selectedEstatusActividad = selectedEstatusActividad;
		this.actividad.setEstado(catalogoEstatusActividad.get(selectedEstatusActividad));
	}
	
	public Actividad getActividad(){
		return actividad;
	}
	
	public void setActividad(Actividad actividad){
		this.actividad = actividad;
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
}