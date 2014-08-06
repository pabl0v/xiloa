package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import model.Convocatoria;
import model.Evaluacion;
import model.EvaluacionGuia;
import model.Mantenedor;
import model.Solicitud;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;
import support.Ifp;
import support.FacesUtil;
import support.Item;
import util.Global;

//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Bean asociada al facet solicitudes.xhtml
@Component
@Scope(value="view")
public class DashBoardSolicitudesManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	@Autowired
	private LoginController login;
	private Solicitud selectedSolicitud;
	private Convocatoria selectedConvocatoria;		
	private List<Item> involucrados;
	private Long selectedEvaluacionId;
	private EvaluacionGuia selectedEvaluacionGuia;
	private List<EvaluacionGuia> evaluacionGuias;
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Constructor de la clase
	public DashBoardSolicitudesManagedBean() {		
		super();
		selectedConvocatoria = new Convocatoria();
		selectedSolicitud = new Solicitud();
		involucrados = new ArrayList<Item>();
		evaluacionGuias = new ArrayList<EvaluacionGuia>();
	}
	
	public List<Solicitud> getListaSolicitudes() {
		return service.getSolicitudesByEntidadId(login.getEntidadUsuario());
	}
		
	public Solicitud getSelectedSolicitud() {
		return selectedSolicitud;
	}

	public void setSelectedSolicitud(Solicitud selectedSolicitud) {
		this.selectedSolicitud = selectedSolicitud;
	}	
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Metodo que redireccion al facet registro_solicitudes.xhtml que permite agregar nueva solicitudes.
	public String nuevaSolicitud(){
		return "/modulos/solicitudes/registro_solicitud?faces-redirect=true";
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Metodo utilizado para redireccionar a la edicion de la solicitud en dependencia del estatus en que se encuentra la solicitud seleccionada.
	public String editaSolicitud(Solicitud solicitud){
		String urlDestino = null;
		Mantenedor estadoActualSolicitud = null;
		
		Integer inicialEstadoKey = null;
		Integer finalEstadoKey = null;
		Mantenedor estadoInicial = null;
		Mantenedor estadoFinal = null;
		
		this.selectedSolicitud = solicitud;

		estadoActualSolicitud = this.selectedSolicitud.getEstatus();
		
		if (estadoActualSolicitud != null) {
			
			estadoInicial = service.getMantenedorById(32);
			estadoFinal = service.getMantenedorById(38);
			
			inicialEstadoKey = estadoInicial.getId();
			finalEstadoKey = estadoFinal.getId();
			
			if (estadoActualSolicitud.getId() == inicialEstadoKey.intValue()){
				FacesUtil.setParamBySession("dbSolicitudesBean", this.selectedSolicitud);
				FacesUtil.setParamBySession("candidato", null);
				urlDestino = "/modulos/solicitudes/expediente?faces-redirect=true";				
			} else if (estadoActualSolicitud.getId() == finalEstadoKey.intValue()) {
				FacesUtil.setParamBySession("candidato", selectedSolicitud.getContacto());
				urlDestino = "/modulos/solicitudes/expediente?faces-redirect=true";
			}else {
				FacesUtil.setParamBySession("dbSolicitudesBean", this.selectedSolicitud);
				FacesUtil.setParamBySession("candidato", null);
				urlDestino = "/modulos/solicitudes/expediente_evaluacion?faces-redirect=true";
			}
		} 	
		
		return urlDestino;
	}	
	
  //Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta reporte pre-matricula.
    public void runPreMatricula () throws Exception{
    	String rptNombre = "prematricula";    	
    	runReporte(rptNombre, true);
    }
    
    //Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta reporte solicitud del candidato.
    public void runSolicitudCandidato() throws Exception{
    	String rptNombre = "solicitud_candidato";
    	runReporte(rptNombre, true);
    }
    
 
	    
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Metodo generico que permite ejecutar reporte.
    public void runReporte(String nombreReporte, boolean desplegar) throws Exception {
    	Map<String,Object> params = new HashMap<String,Object>();
        
    	/*
    	if (this.selectedSolicitud != null){    		
    		params.put("idSolicitud",this.selectedSolicitud.getId());    	
    		service.imprimirReporte(nombreReporte, params, Global.EXPORT_PDF, desplegar);
    	} else
    		FacesUtil.getMensaje("SCCL - Mensaje: ", "Debe seleccionar una solicitud.", true);
    		*/
    	service.imprimirReporte(nombreReporte, params, Global.EXPORT_PDF, desplegar);
	}
    
	public SelectItem[] getListaCentros(){
		List<Ifp> centros = service.getIfpByInatec(login.getEntidadUsuario());
	
		SelectItem[] opciones = new SelectItem[centros.size()+1];
		opciones[0] = new SelectItem("","Seleccione");
		for(int i=0; i<centros.size(); i++)
			opciones[i+1] = new SelectItem(centros.get(i).getIfpNombre(),centros.get(i).getIfpNombre());
		return opciones;
	}
	
	public SelectItem[] getListaEstatus(){

		List<Mantenedor> estatusList = new ArrayList<Mantenedor>(service.getCatalogoEstadoSolicitud().values());
		SelectItem[] estatus = new SelectItem[estatusList.size() + 1];
		
		estatus[0] = new SelectItem("","Seleccione");
		for(int i=0; i<estatusList.size(); i++)
			estatus[i+1] = new SelectItem(estatusList.get(i).getValor(),estatusList.get(i).getValor());
		
		return estatus;
	}
	
	public void actualizarEstadoSolicitud(Solicitud solicitud, int indicador){
		
		/*
		 * 	1: enviar solicitud
		 *  2: autorizar para matricula
		 *  3: rechazar para matricula
		 *  4: matricular
		 *  5: asesoria grupal
		 *  6: asesoria individual
		 *  7: programar
		 *  8: evaluar
		 *  9: completar
		 *  10: anular
		 */

		service.actualizarEstadoSolicitud(solicitud, indicador);
	}
	
	public void registrarMatricula(Date fecha, String recibo) {
		selectedSolicitud.setFechaMatricula(fecha);
		selectedSolicitud.setReciboMatricula(recibo);
		actualizarEstadoSolicitud(selectedSolicitud,4);
	}

	public Convocatoria getSelectedConvocatoria() {
		return selectedConvocatoria;
	}

	public void setSelectedConvocatoria(Convocatoria selectedConvocatoria) {
		this.selectedConvocatoria = selectedConvocatoria;
	}
	
	public Long getSelectedEvaluacionId() {
		return selectedEvaluacionId;
	}

	public void setSelectedEvaluacionId(Long selectedEvaluacionId) {
		this.selectedEvaluacionId = selectedEvaluacionId;
	}
	
	public List<Evaluacion> getEvaluaciones(Long solicitudId){
		return service.getEvaluacionesBySolicitudId(solicitudId);
	}
	
	public EvaluacionGuia getSelectedEvaluacionGuia() {
		return selectedEvaluacionGuia;
	}

	public void setSelectedEvaluacionGuia(EvaluacionGuia evaluacionGuia) {
		this.selectedEvaluacionGuia = evaluacionGuia;
	}
	
	public List<EvaluacionGuia> getEvaluacionGuias(Long evaluacionId){
		//return service.getEvaluacionGuiaByEvaluacionId(evaluacionId);
		return evaluacionGuias;
	}
	
	public void setEvaluacionGuias(List<EvaluacionGuia> guias){
		this.evaluacionGuias = guias;
	}

	public List<Item> getActividades() {
		return service.getActividadesItemBySolicitudId(selectedSolicitud.getId());
	}
	
	public void handleActividadesChange(){
		if(selectedConvocatoria.getActividadId() != null){
			involucrados = service.getInvolucradosItemByActividadId(selectedConvocatoria.getActividadId());
		}
	}

	public List<Item> getInvolucrados() {
		return involucrados;
	}
		
	public List<Mantenedor> getEstados(){
		return service.getMantenedoresByTipo(4);
	}
	
	public List<Convocatoria> getConvocatorias(){
		return service.getConvocatoriasBySolicitudId(selectedSolicitud.getId());
	}
	
	public void registrarConvocatoria(Convocatoria convocatoria) {

		convocatoria.setSolicitudId(selectedSolicitud.getId());

		if(convocatoria.getId()!=null)
			service.guardar(convocatoria);
		else
			service.convocarCandidato(convocatoria);
		
		nuevaConvocatoria();
	}
	
	public void nuevaConvocatoria(){
		this.selectedConvocatoria = new Convocatoria();
		this.selectedConvocatoria.setSolicitudId(selectedSolicitud.getId());
	}
	
	public void enviarSolicitud(Solicitud solicitud){
		service.enviarSolicitud(solicitud);
	}
	
	public void autorizarMatricula(Solicitud solicitud){
		service.autorizarMatricula(solicitud);
	}
	
	public String evaluarSolicitud(Solicitud solicitud){
		FacesUtil.setParamBySession("solicitudId", solicitud.getId());
		return "/modulos/solicitudes/registro_evaluacion?faces-redirect=true";
	}
	
	public void onRowSelect(SelectEvent event) {
		setSelectedSolicitud((Solicitud) event.getObject());
    }
  
    public void onRowUnselect(UnselectEvent event) {
    }
}