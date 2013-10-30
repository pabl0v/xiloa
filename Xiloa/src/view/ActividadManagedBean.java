package view;

import java.util.ArrayList;
import java.util.Date;
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

	private String nombreActividad;
	private String descripcionActividad;
	private int selectedTipoActividad;
	private Map<Integer,Mantenedor> catalogoTiposActividad;
	private Date fechaInicial;
	private Date fechaFinal;
	private String destino;
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
		List<Mantenedor> catalogos = service.getMantenedores();
		for(int i=0; i<catalogos.size(); i++){
			Mantenedor catalogo = catalogos.get(i);
			if(catalogo.getTipo().equalsIgnoreCase("1"))
				catalogoTiposActividad.put(catalogo.getId(), catalogo);
			if(catalogo.getTipo().equalsIgnoreCase("4"))
				catalogoEstatusActividad.put(catalogo.getId(), catalogo);
		}
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
		this.descripcionActividad = nombreActividad;
		this.actividad.setNombre(nombreActividad);
		this.actividad.setDescripcion(nombreActividad);
	}

	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
		this.actividad.setDescripcion(descripcionActividad);
	}

	public int getSelectedTipoActividad() {
		return selectedTipoActividad;
	}

	public void setSelectedTipoActividad(int selectedTipoActividad) {
		this.selectedTipoActividad = selectedTipoActividad;
		this.actividad.setTipo(catalogoTiposActividad.get(selectedTipoActividad));
	}

	public List<Mantenedor> getCatalogoTiposActividad() {
		return new ArrayList<Mantenedor>(catalogoTiposActividad.values());
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
		this.actividad.setFechaInicial(fechaInicial);
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
		this.actividad.setFechaFinal(fechaFinal);
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
		this.actividad.setDestino(destino);
	}

	public int getSelectedEstatusActividad() {
		return selectedEstatusActividad;
	}

	public void setSelectedEstatusActividad(int selectedEstatusActividad) {
		this.selectedEstatusActividad = selectedEstatusActividad;
		this.actividad.setEstado(catalogoEstatusActividad.get(selectedEstatusActividad));
	}

	public List<Mantenedor> getCatalogoEstatusActividad() {
		return new ArrayList<Mantenedor>(catalogoEstatusActividad.values());
	}
	
	public Actividad getActividad(){
		return actividad;
	}
	
	/*
	private Actividad actividad;
	private Map<Integer, Mantenedor> catalogoActividades;
	private int selectedTipoActividad;
	private Map<Integer, Mantenedor> catalogoEstatusActividades;
	private int selectedEstatusActividad;
	
	public ActividadManagedBean(){
		super();
		actividad = new Actividad();
	}
	
	public Actividad getActividad(){
		return this.actividad;
	}

	public int getSelectedTipoActividad() {
		return selectedTipoActividad;
	}

	public void setSelectedTipoActividad(int selectedTipoActividad) {
		this.selectedTipoActividad = selectedTipoActividad;
	}

	public int getSelectedEstatusActividad() {
		return selectedEstatusActividad;
	}

	public void setSelectedEstatusActividad(int selectedEstatusActividad) {
		this.selectedEstatusActividad = selectedEstatusActividad;
	}

	public List<Mantenedor> getCatalogoActividades() {
		return (List<Mantenedor>) catalogoActividades.values();
	}

	public void setCatalogoActividades(Map<Integer, Mantenedor> catalogoActividades) {
		this.catalogoActividades = catalogoActividades;
	}

	public List<Mantenedor> getCatalogoEstatusActividades() {
		return (List<Mantenedor>) catalogoEstatusActividades.values();
	}

	public void setCatalogoEstatusActividades(Map<Integer, Mantenedor> catalogoEstatusActividades) {
		this.catalogoEstatusActividades = catalogoEstatusActividades;
	}
	*/
}