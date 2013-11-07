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
	private Instrumento instrumento;
	private Guia guia;
	private List<Instrumento> instrumentos;
	
	private Integer selectedTipoInstrumento;
	private Map<Integer,Mantenedor> catalogoTiposInstrumento;
	private Long selectedUnidad;
	private Map<Long,Unidad> catalogoUnidades;	

	public InstrumentoManagedBean(){
		super();
		guia = new Guia();
		instrumento = new Instrumento();
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

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
		this.instrumento.setGuias(service.getGuiasByInstrumentoId(instrumento.getId()));
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
		this.instrumento.setTipo(catalogoTiposInstrumento.get(selectedTipoInstrumento));
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
		this.instrumento.setUnidad(catalogoUnidades.get(selectedUnidad));
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
	
	public String guardar(){
		service.guardar(instrumento);
		instrumento = new Instrumento();
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	
	public String cancelar(){
		instrumento = new Instrumento();
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	
	public void agregarGuia(){
		guia.setInstrumento(instrumento);
		service.guardar(guia);
		guia = new Guia();
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