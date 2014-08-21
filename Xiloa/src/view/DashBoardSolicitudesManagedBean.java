package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import model.Contacto;
import model.Convocatoria;
import model.Mantenedor;
import model.Solicitud;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;
import support.FacesUtil;
import support.Item;
import util.Global;

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
	private List<Mantenedor> estadosConvocatoria;
	private List<Item> involucrados;
	private SelectItem[] estadosSolicitud;
	private boolean habilitarAcciones;
	private boolean habilitarReportes;

	public DashBoardSolicitudesManagedBean() {		
		super();
		selectedSolicitud = new Solicitud();
		selectedConvocatoria = new Convocatoria();
		estadosConvocatoria = new ArrayList<Mantenedor>();
		involucrados = new ArrayList<Item>();
		setHabilitarAcciones(false);
		setHabilitarReportes(false);
	}
	
	@PostConstruct
	private void init(){
		estadosConvocatoria = service.getMantenedoresByTipo(4);
		setEstadosSolicitud();		
	}
	
	//optimizar este metodo con un list
	public List<Solicitud> getSolicitudes(){
		Contacto contacto = login.getContacto();
		if(contacto.isInatec())
			return service.getSolicitudesByEntidadId(login.getEntidadUsuario()); 
		else
			return service.getSolicitudesByContactoId(contacto.getId());
	}
	
	public Solicitud getSelectedSolicitud() {
		return selectedSolicitud;
	}

	public void setSelectedSolicitud(Solicitud selectedSolicitud) {
		this.selectedSolicitud = selectedSolicitud;
	}
	
	public SelectItem[] getEstadosSolicitud(){
		return estadosSolicitud;
	}
	
	private void setEstadosSolicitud(){
		List<Mantenedor> estatusList = new ArrayList<Mantenedor>(service.getCatalogoEstadoSolicitud().values());

		estadosSolicitud = new SelectItem[estatusList.size() + 1];		
		estadosSolicitud[0] = new SelectItem("","Seleccione");
		for(int i=0; i<estatusList.size(); i++)
			estadosSolicitud[i+1] = new SelectItem(estatusList.get(i).getValor(),estatusList.get(i).getValor());
	}
	
	public List<Mantenedor> getEstadosConvocatoria(){
		return estadosConvocatoria;
	}
	
	public void enviarSolicitud(Solicitud solicitud){
		service.enviarSolicitud(solicitud);
	}
	
	public void autorizarMatricula(Solicitud solicitud){
		service.autorizarMatricula(solicitud);
	}
	
	public void registrarMatricula(Date fecha, String recibo) {
		selectedSolicitud.setFechaMatricula(fecha);
		selectedSolicitud.setReciboMatricula(recibo);
		actualizarEstadoSolicitud(selectedSolicitud,4);
	}
	
	public List<Convocatoria> getConvocatorias(){
		return service.getConvocatoriasBySolicitudId(selectedSolicitud.getId());
	}

	public Convocatoria getSelectedConvocatoria() {
		return selectedConvocatoria;
	}

	public void setSelectedConvocatoria(Convocatoria selectedConvocatoria) {
		this.selectedConvocatoria = selectedConvocatoria;
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
	
	public String evaluarSolicitud(Solicitud solicitud){
		FacesUtil.setParamBySession("solicitudId", solicitud.getId());
		return "/modulos/solicitudes/registro_evaluacion?faces-redirect=true";
	}
	
	public boolean getHabilitarAcciones(){
		return habilitarAcciones;
	}
	
	public void setHabilitarAcciones(boolean accion){
		this.habilitarAcciones = accion;
	}
	
	public boolean getHabilitarReportes(){
		return habilitarReportes;
	}
	
	public void setHabilitarReportes(boolean accion){
		this.habilitarReportes = accion;
	}
	
	public void onRowSelect(SelectEvent event) {
		setSelectedSolicitud((Solicitud) event.getObject());
		setHabilitarAcciones(true);
		setHabilitarReportes(true);
    }
  
    public void onRowUnselect(UnselectEvent event) {
    	setHabilitarAcciones(false);
    	setHabilitarReportes(false);
    }

    public void runReporte(String nombreReporte, boolean desplegar) throws Exception {
    	Map<String,Object> params = new HashMap<String,Object>();
    	
    	if(nombreReporte.equalsIgnoreCase("rpt_registro_participantes"))
    	{
    		params.put("fecha", new Date());
    		params.put("centroId", selectedSolicitud.getCertificacion().getIfpId());
    	}
    	else
    	{
    		params.put("solicitudId", selectedSolicitud.getId());
    	}
        
    	service.imprimirReporte(nombreReporte, params, Global.EXPORT_PDF, desplegar);
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
}