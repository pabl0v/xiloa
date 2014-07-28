package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.FacesUtil;
import support.Item;
import model.Evaluacion;
import model.EvaluacionGuia;
import model.Instrumento;
import model.Solicitud;

@Component
@Scope(value="view")
public class EvaluacionManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	private Solicitud solicitud;
	private Map<Long, Item> instrumentos;
	private Long selectedInstrumento;
	private Map<Long, Item> unidades;
	private Long selectedUnidad;
	private List<EvaluacionGuia> evaluacionGuias;
	private EvaluacionGuia selectedEvaluacionGuia;
	private List<Evaluacion> evaluaciones;
	private Evaluacion selectedEvaluacion;
	
	public EvaluacionManagedBean() {
		super();
		solicitud = null;
		instrumentos = new HashMap<Long, Item>();
		unidades = new HashMap<Long, Item>();
		evaluacionGuias = new ArrayList<EvaluacionGuia>();
		selectedEvaluacionGuia = null;
		evaluaciones = new ArrayList<Evaluacion>();
		selectedEvaluacion = null;
		selectedInstrumento = null;
		selectedUnidad = null;
	}
	
	@PostConstruct
	private void init(){

		Long solicitudId = (Long)FacesUtil.getParametroSession("solicitudId");
		solicitud = service.getSolicitudById(solicitudId);

		setEvaluaciones(service.getEvaluacionesBySolicitudId(solicitud.getId()));
		
		setInstrumentos();
		setUnidades();		
	}
	
	public Long getSelectedInstrumento(){
		return selectedInstrumento;
	}
	
	public void setSelectedInstrumento(Long instrumento){
		this.selectedInstrumento = instrumento;
	}
	
	public Long getSelectedUnidad(){
		return selectedUnidad;
	}
	
	public void setSelectedUnidad(Long unidad){
		this.selectedUnidad = unidad;
	}	
	
	public Solicitud getSolicitud(){
		return solicitud;
	}
	
	public String getSolicitudId(){
		return solicitud.getId().toString();
	}
	
	public Item getInstrumento(Long id){
		return instrumentos.get(id);
	}
	
	public Item getUnidad(Long id){
		return unidades.get(id);
	}
	
	public List<Item> getInstrumentos(){
		return new ArrayList<Item>(instrumentos.values());
	}
	
	public void setInstrumentos(){
		for(Item instrumento : service.getInstrumentosItemByCertificacionId(solicitud.getCertificacion().getId())){
			instrumentos.put(instrumento.getId(), instrumento);
		}
	}
	
	public List<Item> getUnidades(){
		return new ArrayList<Item>(unidades.values());
	}
	
	public void setUnidades(){
		for(Item unidad : service.getUnidadesItemByCertificacionId(solicitud.getCertificacion().getId())){
			unidades.put(unidad.getId(), unidad);
		}
	}
	
	public List<Evaluacion> getEvaluaciones(){
		return evaluaciones;
	}
	
	public void setEvaluaciones(List<Evaluacion> evaluaciones){
		this.evaluaciones = evaluaciones;
	}
	
	public Evaluacion getSelectedEvaluacion(){
		return selectedEvaluacion;
	}
	
	public void setSelectedEvaluacion(Evaluacion evaluacion){
		this.selectedEvaluacion = evaluacion;
	}
	
	public List<EvaluacionGuia> getEvaluacionGuias(){
		return evaluacionGuias;
	}
	
	public void setEvaluacionGuias(List<EvaluacionGuia> guias){
		this.evaluacionGuias = guias;
	}
	
	public EvaluacionGuia getSelectedEvaluacionGuia(){
		return selectedEvaluacionGuia;
	}
	
	public void setSelectedEvaluacionGuia(EvaluacionGuia guia){
		this.selectedEvaluacionGuia = guia;
	}
	
	public void onEvaluacionSelect(SelectEvent event) {
		setSelectedEvaluacion((Evaluacion) event.getObject());
		setEvaluacionGuias(service.getEvaluacionGuiaByEvaluacionId(selectedEvaluacion.getId()));
    }
	
	public void agregarEvaluacion(Long instrumentoId){
		
		Instrumento instrumento = service.getInstrumentoById(instrumentoId);
		Evaluacion evaluacion = new Evaluacion(solicitud,instrumento,new Date(),instrumento.getPuntajeMinimo(),instrumento.getPuntajeMaximo(),null,true);
		
		evaluacion = service.guardarEvaluacion(evaluacion);
		setSelectedEvaluacion(evaluacion);
		setEvaluaciones(service.getEvaluacionesBySolicitudId(solicitud.getId()));
		setEvaluacionGuias(service.getEvaluacionGuiaByEvaluacionId(selectedEvaluacion.getId()));		
	}
	
	public void editarEvaluacion(Evaluacion evaluacion){
		evaluacion = (Evaluacion)service.guardar(evaluacion);
		setSelectedEvaluacion(evaluacion);
		setEvaluaciones(service.getEvaluacionesBySolicitudId(solicitud.getId()));
		setEvaluacionGuias(service.getEvaluacionGuiaByEvaluacionId(selectedEvaluacion.getId()));
	}
	
	public void editarEvaluacionGuia(EvaluacionGuia guia){
		selectedEvaluacionGuia = guia;
		selectedEvaluacionGuia = (EvaluacionGuia)service.guardar(guia);
		setEvaluaciones(service.getEvaluacionesBySolicitudId(solicitud.getId()));
	}
}