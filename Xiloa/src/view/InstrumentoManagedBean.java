package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import model.Guia;
import model.Instrumento;
import model.Mantenedor;
import model.Unidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;

@Component
@Scope("session")
public class InstrumentoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IService service;
	
	private String nombreCertificacion;
	private Instrumento selectedInstrumento;
	private Instrumento nuevoInstrumento;
	private Guia guia;
	private List<Instrumento> instrumentos;
	
	private Integer selectedTipoInstrumento;
	private Map<Integer,Mantenedor> catalogoTiposInstrumento;
	private Long selectedUnidad;
	private Map<Long,Unidad> catalogoUnidades;	

	public InstrumentoManagedBean(){
		super();
		guia = new Guia();
		guia.setEstatus(true);
		selectedInstrumento = new Instrumento();
		nuevoInstrumento = new Instrumento();
		selectedTipoInstrumento = null;
		catalogoTiposInstrumento = new HashMap<Integer, Mantenedor>();
		selectedUnidad = null;
		catalogoUnidades = new HashMap<Long, Unidad>();
	}
	
	@PostConstruct
	private void init(){
		catalogoTiposInstrumento = service.getMapMantenedoresByTipo("6");
	}
	
	public String getNombreCertificacion() {
		return nombreCertificacion;
	}

	public Instrumento getSelectedInstrumento() {
		return selectedInstrumento;
	}

	public void setSelectedInstrumento(Instrumento instrumento) {
		this.selectedInstrumento = instrumento;
		this.selectedInstrumento.setGuias(service.getGuiasByInstrumentoId(instrumento.getId()));
	}
	
	public Instrumento getNuevoInstrumento() {
		return nuevoInstrumento;
	}

	public void setNuevoInstrumento(Instrumento nuevoInstrumento) {
		this.nuevoInstrumento = nuevoInstrumento;
		//this.instrumento.setGuias(service.getGuiasByInstrumentoId(instrumento.getId()));
	}
	
	public Guia getGuia() {
		return guia;
	}

	public void setGuia(Guia guia) {
		this.guia = guia;
	}

	public Integer getSelectedTipoInstrumento() {
		return selectedTipoInstrumento;
	}

	public void setSelectedTipoInstrumento(Integer selectedTipoInstrumento) {
		this.selectedTipoInstrumento = selectedTipoInstrumento;
		this.nuevoInstrumento.setTipo(catalogoTiposInstrumento.get(selectedTipoInstrumento));
	}

	public List<Mantenedor> getCatalogoTiposInstrumento() {
		return new ArrayList<Mantenedor>(catalogoTiposInstrumento.values());
	}

	public void setCatalogoTiposInstrumento(List<Mantenedor> catalogoTiposInstrumento) {
		for(int i=0; i<catalogoTiposInstrumento.size(); i++){
			this.catalogoTiposInstrumento.put(catalogoTiposInstrumento.get(i).getId(), catalogoTiposInstrumento.get(i));
		}
	}

	public Long getSelectedUnidad() {
		return selectedUnidad;
	}

	public void setSelectedUnidad(Long selectedUnidad) {
		this.selectedUnidad = selectedUnidad;
		this.nuevoInstrumento.setUnidad(catalogoUnidades.get(selectedUnidad));
	}

	public String configurarInstrumento(Long idCertificacion, String nombreCertificacion){
		List<Unidad> unidades = service.getUnidadesByCertificacionId(idCertificacion);
		for(int i=0; i<unidades.size(); i++)
			this.catalogoUnidades.put(unidades.get(i).getId(), unidades.get(i));
		this.instrumentos = service.getInstrumentosByCertificacionId(idCertificacion);
		this.nombreCertificacion = nombreCertificacion;
		return "/modulos/planificacion/instrumentos?faces-redirect=true";
	}
		
	public List<Unidad> getCatalogoUnidades() {
		return new ArrayList<Unidad>(catalogoUnidades.values());
	}
	
	public void nuevoInstrumento(Instrumento instrumento){
		instrumento.setDescripcion(instrumento.getNombre());
		instrumento.setGuias(null);
		nuevoInstrumento = (Instrumento) service.guardar(instrumento);
		instrumentos.add(nuevoInstrumento);
		setSelectedInstrumento(nuevoInstrumento);
		nuevoInstrumento = new Instrumento();
	}
		
	public String aceptar(){
		nuevoInstrumento = new Instrumento();
		selectedInstrumento = new Instrumento();
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	
	public void agregarGuia(){
		guia.setInstrumento(selectedInstrumento);
		//selectedInstrumento.addGuia(guia);
		service.guardar(guia);
		setSelectedInstrumento(selectedInstrumento);
		guia = new Guia();
		guia.setEstatus(true);
	}

	public List<Instrumento> getInstrumentos() {
		return instrumentos;
	}

	public void setInstrumentos(List<Instrumento> instrumentos) {
		this.instrumentos = instrumentos;
	}
	
	public void editarInstrumento(Instrumento instrumento){
		
	}
}