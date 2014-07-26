package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.FacesUtil;
import support.Item;
import model.Evaluacion;
import model.EvaluacionGuia;
import model.Solicitud;

@Component
@Scope(value="view")
public class EvaluacionManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	private Solicitud solicitud;
	private Map<Long, Item> instrumentos;
	private Map<Long, Item> unidades;
	private List<EvaluacionGuia> evaluacionGuias;
	private EvaluacionGuia selectedEvaluacionGuia;
	private Evaluacion selectedEvaluacion;
	
	public EvaluacionManagedBean() {
		super();
		solicitud = null;
		instrumentos = new HashMap<Long, Item>();
		unidades = new HashMap<Long, Item>();
		evaluacionGuias = new ArrayList<EvaluacionGuia>();
		selectedEvaluacion = null;
		selectedEvaluacionGuia = null;
	}
	
	@PostConstruct
	private void init(){

		solicitud = service.getSolicitudById(new Long(1));
		
		for(Item instrumento : service.getInstrumentosItemByCertificacionId(solicitud.getCertificacion().getId())){
			instrumentos.put(instrumento.getId(), instrumento);
		}
		
		for(Item unidad : service.getUnidadesItemByCertificacionId(solicitud.getCertificacion().getId())){
			unidades.put(unidad.getId(), unidad);
		}
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
	
	public List<Item> getUnidades(){
		return new ArrayList<Item>(unidades.values());
	}
	
	public List<Evaluacion> getEvaluaciones(){
		return service.getEvaluaciones(solicitud);
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
			
	public void guardarEvaluacion(Evaluacion evaluacion){
		evaluacion = service.guardarEvaluacion(evaluacion);
		setSelectedEvaluacion(evaluacion);
		setEvaluacionGuias(service.getEvaluacionGuiaByEvaluacionId(selectedEvaluacion.getId()));
		FacesUtil.getMensaje("SCCL - Mensaje", "La evaluación ha sido agregada...", false);
	}
	
	public boolean getAplicar(){
		//if(selectedEvaluacion.getId()!=null)
			//return true;
		//else
			return false;
	}
	
	public void onEvaluacionSelect(SelectEvent event) {
		setSelectedEvaluacion((Evaluacion) event.getObject());
		setEvaluacionGuias(service.getEvaluacionGuiaByEvaluacionId(selectedEvaluacion.getId()));
    }
	
	public void onCellEdit(CellEditEvent event) {
        Float oldValue = (Float)event.getOldValue();
        Float newValue = (Float)event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
        	selectedEvaluacionGuia.setPuntaje(newValue);
        	service.guardar(selectedEvaluacionGuia);
        	FacesUtil.getMensaje("SCCL - Mensaje", "Actualización exitosa...", false);
        }
    }
}