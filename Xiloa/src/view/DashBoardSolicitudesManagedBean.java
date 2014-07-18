package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Convocatoria;
import model.Mantenedor;
import model.Solicitud;

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
	private Item selectedActividad;
	private Item selectedInvolucrado;
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Constructor de la clase
	public DashBoardSolicitudesManagedBean() {		
		super();
		selectedActividad = new Item();
		selectedInvolucrado = new Item();
		selectedConvocatoria = new Convocatoria();
		selectedSolicitud = new Solicitud();
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
    	String rptNombre = "pre_matricula";    	
    	runReporte(rptNombre, true);
    }
    
  //Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta reporte solicitud del candidato.
    public void runSolicitudCandidato() throws Exception{
    	String rptNombre = "solicitud_candidato";
    	runReporte(rptNombre, true);
    }
    
  //Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta reporte Emision del Juicio.
    public void runEmisionJuicio() throws Exception{
    	Map<String,Object> params = new HashMap<String,Object>();
    	Certificacion cert = null;
    	String rptNombre = "emision_juicio";    	

    	if (this.selectedSolicitud != null){    		
    		cert = this.selectedSolicitud.getCertificacion();    		
    		params.put("idSolicitud",this.selectedSolicitud.getId());
    		if (cert.getEvaluador() != null){
    			params.put("idEvaluador",cert.getEvaluador().getId());
    			if (cert.getVerificador() != null){
        			params.put("idVerificador", cert.getVerificador().getId());
        			service.imprimirReporte(rptNombre, params, Global.EXPORT_PDF, true);
    			} else
    				FacesUtil.getMensaje("SCCL - Mensaje: ", "No existen datos del verificador. Favor comuníquese al Departamento de Informatica del INATEC.", true);
    			
    		} else
    			FacesUtil.getMensaje("SCCL - Mensaje: ", "No existen datos del evaluador. Favor comuníquese al Departamento de Informatica del INATEC.", true);
    		    		
    	}else
    		FacesUtil.getMensaje("SCCL - Mensaje: ", "Debe seleccionar una solicitud.", true);
    }

  //Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta reporte Certificado.
    public void runCertificado() throws Exception{
    	Map<String,Object> params = new HashMap<String,Object>();
    	
    	if (this.selectedSolicitud != null){
    		Certificacion cert = this.selectedSolicitud.getCertificacion();
    		params.put("idSolicitud",this.selectedSolicitud.getId());
    		params.put("idCentro", String.valueOf(cert.getIfpId()));
    		service.imprimirReporte("certificado", params, Global.EXPORT_PDF, true);
    	}else
    		FacesUtil.getMensaje("SCCL - Mensaje: ", "Debe seleccionar una solicitud.", true);
    }
    
  //Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta reporte Informe Asesor.
    public void runInformeAsesor() throws Exception{
		String rptNombre = "informeasesor";
		runReporte(rptNombre, true);
	}
	
  //Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta reporte Plan de Capacitacion.
	public void runPlanCapacitacion() throws Exception{
		String rptNombre = "plancapacitacion";
		runReporte(rptNombre, true);
	}
	    
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Metodo generico que permite ejecutar reporte.
    public void runReporte(String nombreReporte, boolean desplegar) throws Exception {
    	Map<String,Object> params = new HashMap<String,Object>();
    	Certificacion cert = null;
    	
    	if (this.selectedSolicitud != null){
    		cert = this.selectedSolicitud.getCertificacion();
    		params.put("idSolicitud",this.selectedSolicitud.getId());
    		if (cert.getEvaluador() != null)
    			params.put("idEvaluador",cert.getEvaluador().getId());
    		if (cert.getVerificador() != null)
    			params.put("idVerificador", cert.getVerificador().getId());
    		service.imprimirReporte(nombreReporte, params, Global.EXPORT_PDF, desplegar);
    	}else
    		FacesUtil.getMensaje("SCCL - Mensaje: ", "Debe seleccionar una solicitud.", true);
				
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
	
	public void registrarMatricula(Date fecha, String recibo){
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

	public List<Item> getActividades() {
		List<Item> actividades = new ArrayList<Item>();
		actividades.add(new Item(new Long(1),"Actividad 1"));
		actividades.add(new Item(new Long(2),"Actividad 2"));
		actividades.add(new Item(new Long(3),"Actividad 3"));
		return actividades;
	}

	public List<Item> getInvolucrados() {
		List<Item> involucrados = new ArrayList<Item>();
		involucrados.add(new Item(new Long(1),"Involucrado 1"));
		involucrados.add(new Item(new Long(2),"Involucrado 2"));
		involucrados.add(new Item(new Long(3),"Involucrado 3"));
		return involucrados;
	}

	public Item getSelectedActividad() {
		return selectedActividad;
	}

	public void setSelectedActividad(Item selectedActividad) {
		this.selectedActividad = selectedActividad;
	}

	public Item getSelectedInvolucrado() {
		return selectedInvolucrado;
	}

	public void setSelectedInvolucrado(Item selectedInvolucrado) {
		this.selectedInvolucrado = selectedInvolucrado;
	}
}