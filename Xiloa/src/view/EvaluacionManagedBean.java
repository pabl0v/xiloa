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
import model.Unidad;

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
	private boolean habilitarAgregar;
	private boolean habilitarEvaluar;
	
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
		habilitarAgregar = false;
		habilitarEvaluar = false;
	}
	
	@PostConstruct
	private void init(){

		Long solicitudId = (Long)FacesUtil.getParametroSession("solicitudId");
		solicitud = service.getSolicitudById(solicitudId);
		
		// si la solicitud esta enviada, asesoria individual o programada habilitar la agregacion de instrumentos
		if(solicitud.getEstatus().getId()==36 || solicitud.getEstatus().getId()==40 || solicitud.getEstatus().getId()==41)
			setHabilitarAgregar(true);
		else
			setHabilitarAgregar(false);
		
		// si la solicitud esta programada habilitar tambien el boton evaluar
		if(solicitud.getEstatus().getId()==41)
			setHabilitarEvaluar(true);
		else
			setHabilitarEvaluar(false);

		setEvaluaciones(service.getEvaluacionesBySolicitudId(solicitud.getId()));
		
		setInstrumentos(null);
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
	
	public void setInstrumentos(Long unidad){
		instrumentos.clear();
		for(Item instrumento : service.getInstrumentosPendientesBySolicitud(solicitud, unidad)){
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
	
	public void handleUnidadesChange(){
		if(selectedUnidad != 0 && selectedUnidad != null)
			setInstrumentos(selectedUnidad);
		else
			setInstrumentos(null);
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
		Evaluacion evaluacion = new Evaluacion(solicitud,instrumento,new Date(),instrumento.getPuntajeMinimo(),instrumento.getPuntajeMaximo(),false,null,true);
		
		evaluacion = service.guardarEvaluacion(evaluacion);
		setSelectedEvaluacion(evaluacion);
		setEvaluaciones(service.getEvaluacionesBySolicitudId(solicitud.getId()));
		setEvaluacionGuias(service.getEvaluacionGuiaByEvaluacionId(selectedEvaluacion.getId()));
		setSelectedUnidad(null);
		setSelectedInstrumento(null);
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
	
	public boolean getHabilitarAgregar(){
		return habilitarAgregar;
	}
	
	public void setHabilitarAgregar(boolean habilitar){
		this.habilitarAgregar = habilitar;
	}
	
	public boolean getHabilitarEvaluar(){
		return habilitarEvaluar;
	}
	
	public void setHabilitarEvaluar(boolean habilitar){
		this.habilitarEvaluar = habilitar;
	}
		
	public String retornar(){
		FacesUtil.setParamBySession("solicitudId", null);
		return "/modulos/solicitudes/solicitudes?faces-redirect=true";
	}
	
	public String evaluar(){
		
		List<Unidad> unidades = service.getUnidadesSinEvaluar(solicitud.getId());
		
		if(!unidades.isEmpty())
		{
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Debe evaluar todas las unidades de competencia.", true);
			return null;
		}
		
		List<Evaluacion> evaluaciones = service.getEvaluacionesReprobadas(solicitud.getId());
		
		if(!evaluaciones.isEmpty())
		{
			FacesUtil.getMensaje("SCCL - Mensaje: ", "El candidato no es apto.", false);
			service.actualizarEstadoSolicitud(solicitud, 9);		//no apto
		}
		else
		{
			FacesUtil.getMensaje("SCCL - Mensaje: ", "El candidato es apto.", false);
			service.actualizarEstadoSolicitud(solicitud, 8);		//apto			
		}

		return null;
	}
}