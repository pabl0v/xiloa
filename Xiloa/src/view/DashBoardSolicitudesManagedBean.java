package view;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Contacto;
import model.Laboral;
import model.Mantenedor;
import model.Solicitud;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;

import reporte.ControlGenericoReporte;
import service.IService;
import support.Ifp;
import support.FacesUtil;
import util.Global;

@Component
@Scope(value="view")
public class DashBoardSolicitudesManagedBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String NOMBREREPORTE = "listasolicitudes";

	@Autowired
	private IService service;
	
	@Autowired
	private UtilitariosManagedBean util;
	
	@Autowired
	private LoginController login; 
	
	private List<Solicitud> listaSolicitudes;
	
	private Solicitud selectedSolicitud;
	
	private Long selectedSolicitudId;
	
	private String selectedBuscarByAll;
	private String buscarByAllValue;
	
	private Integer selectedIdIfpSolicitud;
	
	private Integer selectedAccionConvo;
	
	//Implementacion SelectItems	
	private List<SelectItem> listBuscarByAll;
	private List<SelectItem> listCentrosBySolicitud;				
	
	private List<SelectItem> listCertByCentro;
	
	private List<SelectItem> listAccionConvo;
	private Integer selectedIdIfp;
	
	private Long selectedIdCertificacion;
	private Long selectedIdCertByCentro;	
		
	private boolean ck_convo;
	
	private Solicitud [] selectedListSolicitud;
	
	private List<Solicitud> filterSolicitudes;
	
	private Mantenedor estadoInicialSolicitud;
	
	private boolean disableEnviarSolicitud;
	
			
	public DashBoardSolicitudesManagedBean() {
		
		super();	
		
		listaSolicitudes = new ArrayList<Solicitud> ();				
			
		listCentrosBySolicitud = new ArrayList<SelectItem>();
		listCertByCentro = new ArrayList<SelectItem>();
		listBuscarByAll = new ArrayList<SelectItem> ();
		listAccionConvo = new ArrayList<SelectItem> ();
		filterSolicitudes = new ArrayList<Solicitud> ();
		
		selectedIdIfpSolicitud = null;		
		selectedIdCertByCentro = null;	
		
		selectedBuscarByAll = null;	
		
		disableEnviarSolicitud = true;
					
	}	

	
	public boolean isDisableEnviarSolicitud() {
		return disableEnviarSolicitud;
	}


	public void setDisableEnviarSolicitud(boolean disableEnviarSolicitud) {
		this.disableEnviarSolicitud = disableEnviarSolicitud;
	}


	public Mantenedor getEstadoInicialSolicitud() {
		return estadoInicialSolicitud;
	}


	public void setEstadoInicialSolicitud(Mantenedor estadoInicialSolicitud) {
		this.estadoInicialSolicitud = estadoInicialSolicitud;
	}


	public Long getSelectedSolicitudId() {
		return selectedSolicitudId;
	}

	public void setSelectedSolicitudId(Long selectedSolicitudId) {
		this.selectedSolicitudId = selectedSolicitudId;
	}

	public List<Solicitud> getFilterSolicitudes() {
		return filterSolicitudes;
	}

	public void setFilterSolicitudes(List<Solicitud> filterSolicitudes) {
		this.filterSolicitudes = filterSolicitudes;
	}

	public Integer getSelectedIdIfp() {
		return selectedIdIfp;
	}

	public void setSelectedIdIfp(Integer idIfp) {
		this.selectedIdIfp = idIfp;
	}
	
	public Integer getSelectedIdIfpSolicitud() {
		return selectedIdIfpSolicitud;
	}

	public void setSelectedIdIfpSolicitud(Integer selectedIdIfpSolicitud) {
		this.selectedIdIfpSolicitud = selectedIdIfpSolicitud;
	}

	public Long getSelectedIdCertificacion() {
		return selectedIdCertificacion;
	}

	public void setSelectedIdCertificacion(Long idCertificacion) {
		this.selectedIdCertificacion = idCertificacion;
	}
	
	public Long getSelectedIdCertByCentro() {
		return selectedIdCertByCentro;
	}

	public void setSelectedIdCertByCentro(Long selectedIdCertByCentro) {
		this.selectedIdCertByCentro = selectedIdCertByCentro;
	}	
	
	public String getSelectedBuscarByAll() {
		return selectedBuscarByAll;
	}

	public void setSelectedBuscarByAll(String selectedBuscarByAll) {
		this.selectedBuscarByAll = selectedBuscarByAll;
	}

	public List<SelectItem> getListCentrosBySolicitud() {
		return listCentrosBySolicitud;
	}

	public void setListCentrosBySolicitud(List<SelectItem> listCentrosBySolicitud) {
		this.listCentrosBySolicitud = listCentrosBySolicitud;
	}

	public List<SelectItem> getListCertByCentro() {
		return listCertByCentro;
	}

	public void setListCertByCentro(List<SelectItem> listCertByCentro) {
		this.listCertByCentro = listCertByCentro;
	}	
	
	public List<SelectItem> getListBuscarByAll() {
		return listBuscarByAll;
	}

	public void setListBuscarByAll(List<SelectItem> listBuscarByAll) {
		this.listBuscarByAll = listBuscarByAll;
	}	
		
	public List<SelectItem> getListAccionConvo() {
		return listAccionConvo;
	}

	public void setListAccionConvo(List<SelectItem> listAccionConvo) {
		this.listAccionConvo = listAccionConvo;
	}

	public String getBuscarByAllValue() {
		return buscarByAllValue;
	}

	public void setBuscarByAllValue(String buscarByAllValue) {
		this.buscarByAllValue = buscarByAllValue;
	}	
	
	public List<Solicitud> getListaSolicitudes() {		
		return this.listaSolicitudes;
	}
	
	public void setListaSolicitudes(List<Solicitud> listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}
	
	public Integer getSelectedAccionConvo() {
		return selectedAccionConvo;
	}

	public void setSelectedAccionConvo(Integer selectedAccionConvo) {
		this.selectedAccionConvo = selectedAccionConvo;
	}
	
	public boolean isCk_convo() {
		return ck_convo;
	}

	public void setCk_convo(boolean ck_convo) {
		this.ck_convo = ck_convo;
	}
	
	public Solicitud getSelectedSolicitud() {
		return selectedSolicitud;
	}

	public void setSelectedSolicitud(Solicitud selectedSolicitud) {
		this.selectedSolicitud = selectedSolicitud;
	}	
	
	
	public Solicitud[] getSelectedListSolicitud() {
		return selectedListSolicitud;
	}

	public void setSelectedListSolicitud(Solicitud[] selectedListSolicitud) {
		this.selectedListSolicitud = selectedListSolicitud;
	}

	public void llenarListBuscarByAll () {
		this.listBuscarByAll.add(new SelectItem(null, "Todos los campos"));
		this.listBuscarByAll.add(new SelectItem("s.certificacion.ifpNombre", "Centro Evaluador"));
		this.listBuscarByAll.add(new SelectItem("s.contacto.nombreCompleto", "Nombre del Candidato"));
		this.listBuscarByAll.add(new SelectItem("s.certificacion.nombre", "Certificacion a Evaluar"));
		this.listBuscarByAll.add(new SelectItem("s.fechaRegistro", "Fecha Solicitud"));
		this.listBuscarByAll.add(new SelectItem("s.contacto.correo1", "Evaluador"));
		this.listBuscarByAll.add(new SelectItem("s.estatus", "Estado"));		
	}
	
	public void llenarListAccionConvo () {
		this.listAccionConvo = new ArrayList<SelectItem> ();
		this.listAccionConvo.add(new SelectItem(null, "Seleccione la accion"));
		this.listAccionConvo.add(new SelectItem(new Integer(1), "Convocar"));
		this.listAccionConvo.add(new SelectItem(new Integer(2), "Asesorado"));
		this.listAccionConvo.add(new SelectItem(new Integer(3), "Listos para inscripcion"));
		this.listAccionConvo.add(new SelectItem(new Integer(4), "Exportar a Excel"));
		this.listAccionConvo.add(new SelectItem(new Integer(5), "Exportar a PDF"));
	}
			
   //Llenado de Centro
	@PostConstruct
	private void initBeanDBSolicitudes(){
		List<Ifp> lista = service.getIfpByInatec();
		this.listCentrosBySolicitud.add(new SelectItem(null, "Todos"));
		for (Ifp dato : lista) {	
			this.listCentrosBySolicitud.add(new SelectItem(dato.getIfpId(),dato.getIfpNombre()));
		}		
		FacesUtil.setParamBySession("tipoFiltro", null);		
		llenarListBuscarByAll();
		llenarListAccionConvo ();
		handleCertByCentro();
		
		//Asigna el estado inicial de la Solicitud
		this.estadoInicialSolicitud = service.getMantenedorMinByTipo(new String("7"));
		
	}
	
	public void handleCertByCentro() {
		List<Certificacion> certificacionList = service.getCertificacionesByIdIfp(this.getSelectedIdIfpSolicitud());
		listCertByCentro = new ArrayList<SelectItem>();
		this.listCertByCentro.add(new SelectItem(null,"Todas las Unidades"));
		for (Certificacion dato : certificacionList) {
			this.listCertByCentro.add(new SelectItem(dato.getId(),dato.getNombre()));
		}
		
		this.listaSolicitudes = filtraSolicitudes(); // service.getSolicitudesByParam(asignaParams ());
		this.filterSolicitudes = this.listaSolicitudes;
	}
	
	public void handleBuscar () {						
		this.listaSolicitudes = filtraSolicitudes(); //service.getSolicitudesByParam(asignaParams ());
		this.filterSolicitudes = this.listaSolicitudes;
	}
	
	public HashMap<String, Object> asignaParams () {
		HashMap<String, Object> params = new HashMap<String, Object>();
		Contacto contacto = null;
				
		if (this.getSelectedIdIfpSolicitud() != null) {
			params.put("s.certificacion.ifpId", this.getSelectedIdIfpSolicitud());
		}
		
		if (this.selectedIdCertByCentro != null) {
			params.put("s.certificacion.id", this.selectedIdCertByCentro);
		}
		
		if (this.buscarByAllValue != null && this.selectedBuscarByAll != null) {			
			params.put(this.selectedBuscarByAll, this.buscarByAllValue);
		}
		contacto = login.getContacto();
		//if (contacto.getEntidadId() == 1000) // Usuario Inatec
		if (contacto.getEntidadId() == null){ // Usuario OpenId
			params.put("s.contacto.id", contacto.getId());
		} else if (contacto.getEntidadId() != 1000){
			params.put("s.certificacion.ifpId", contacto.getEntidadId());
		}
		
		return params;
	}
	
	public List<Solicitud> filtraSolicitudes(){		
		Integer tipoFiltro = null;		
		tipoFiltro = (Integer)FacesUtil.getParametroSession("tipoFiltro");		
		if (tipoFiltro == null)
			tipoFiltro = new Integer(0);
		
		return service.filtraListaSolicitudes(asignaParams (), tipoFiltro);				
	}
		
	public String nuevaSolicitud(){		
		return "/modulos/solicitudes/registro_solicitud?faces-redirect=true";
	}
	
	public String editaSolicitud(){
		String urlDestino = null;
		Mantenedor estadoActualSolicitud = null;
		
		Integer inicialEstadoKey = null;
		Mantenedor estadoInicial = null;
		
		FacesUtil.setParamBySession("dbSolicitudesBean", this.selectedSolicitud);		
		
		estadoActualSolicitud = this.selectedSolicitud.getEstatus();
		
		if (estadoActualSolicitud != null) {
			estadoInicial = service.getMantenedorMinByTipo(estadoActualSolicitud.getTipo());
			
			inicialEstadoKey = estadoInicial.getId();
			
			if (estadoActualSolicitud.getId() == inicialEstadoKey.intValue())
				urlDestino = "/modulos/solicitudes/expediente?faces-redirect=true";
			else 
				urlDestino = "/modulos/solicitudes/expediente_evaluacion?faces-redirect=true";
		} 	
		
		return urlDestino;
	}
	
	public String cancelarEdicion() {		
		return "/modulos/solicitudes/solicitudes?faces-redirect=true";				
	}
				
	public String indicarConvocar(){
		FacesUtil.setParamBySession("tipoFiltro", new Integer(1));
		return "/modulos/solicitudes/convocatoria?faces-redirect=true";
	}
	
	public String indicarAsesorar(){
		FacesUtil.setParamBySession("tipoFiltro", new Integer(2));
		return "/modulos/solicitudes/convocatoria?faces-redirect=true";
	}
	
	public String indicarInscribir(){
		FacesUtil.setParamBySession("tipoFiltro", new Integer(3));
		return "/modulos/solicitudes/convocatoria?faces-redirect=true";
	}
	
	
	public void solicitarCertificacion (){
		
		Mantenedor estadoActual = null;				
		Integer    proxEstado = null; 
		Solicitud  solicitudExp = null;
		Mantenedor inicialEstado = null;
		
		Mantenedor proximoEstado = null;
		
		solicitudExp = this.getSelectedSolicitud();
		
		if (solicitudExp != null) {
			
			if (validaRegistroCV (solicitudExp)){
			
				estadoActual = this.getSelectedSolicitud().getEstatus();
				
				inicialEstado = this.getEstadoInicialSolicitud();
				
				if (inicialEstado.getId() == estadoActual.getId()){
					
					proxEstado = Integer.valueOf(estadoActual.getProximo());
											
					if (proxEstado != null){
						proximoEstado = util.getCatalogoEstadoSolicitud().get(proxEstado);
						solicitudExp.setEstatus(proximoEstado);
						
						Solicitud sol = (Solicitud) service.guardar(solicitudExp);
						
						if (sol != null)		
							FacesUtil.getMensaje("SCCL - Mensaje: ", "La solicitud a sido registrada exitosamente !!", false);				
						else				
							FacesUtil.getMensaje("SCCL - Mensaje: ", "Error al registrar la solicitud. Favor revisar...", false);
					}				
					
				}
			}
			
		} else
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Debe seleccionar una solicitud.", true);
		
		
	}
	
	public boolean validaRegistroCV (Solicitud solicitud){
		String        textMsg = "";
		String        titulo = "";
		boolean       isValido = true;
		List<Laboral> listDatosLaborales;
		
		Contacto solicitante = solicitud.getContacto();
		
		if (solicitante.getTelefono1() == null) {
			textMsg = "Debe indicar el numero de telefono";
			isValido = false;
		}
			
		if (solicitante.getDireccionActual() == null) {
			textMsg = (textMsg.isEmpty()) ? "Debe indicar la direccion actual" : textMsg + ", la direccion actual";					    
			isValido = false;
		} 
			
		if (solicitante.getDepartamentoId() == null) {
			textMsg = (textMsg.isEmpty()) ? "Debe indicar el departamento" : textMsg + ", el departamento";			
			isValido = false;
		}
			
		if (solicitante.getMunicipioId() == null) {
			textMsg = (textMsg.isEmpty()) ? "Debe indicar el municipio." : textMsg + " y el municipio.";		
			isValido = false;
		}
		
		listDatosLaborales = service.getListLaboralByTipo(new Integer(13), solicitante);
		
		if (listDatosLaborales.size() == 0) {
			textMsg = (textMsg.isEmpty()) ? "Debe indicar los datos Laborales / Academicos." : ". Debe indicar los datos Laborales / Academicos.";
			isValido = false;
		}
			
		if (isValido == false){
			titulo = "Informacion incompleta: ";
			FacesUtil.getMensaje(titulo, textMsg, true);
		}else{
			titulo = "Informacion: ";
			textMsg = "Puede proceder a registrar la solicitud";
			FacesUtil.getMensaje(titulo, textMsg, false);
		}		
		
		return isValido;
		
	}
	
	public void onRowSelectDtSolicitudes(SelectEvent event) {
		this.setSelectedSolicitud((Solicitud) event.getObject());
		
		Mantenedor estadoActual = this.getSelectedSolicitud().getEstatus();
		
		if (estadoActual.getId() == this.getEstadoInicialSolicitud().getId())
			this.setDisableEnviarSolicitud(false);
		else
			this.setDisableEnviarSolicitud(true);
		
    }
  
    public void onRowUnSelectDtSolicitudes(UnselectEvent event) {
    	this.setDisableEnviarSolicitud(true);
    }
	
    public void runReporte() throws Exception {
    	Map<String,Object> params = new HashMap<String,Object>();
    	String nombreRpt = "listadosolicitudes";
   
    	System.out.println("Solicitud seleccionado " + this.selectedSolicitud.getId());
		params.put("id",this.selectedSolicitud.getId());	
		
    	service.imprimirReporte(nombreRpt, params, Global.EXPORT_PDF, true);
		
	//	ControlGenericoReporte.getInstancia().runReporteFisico(nombreRpt, params,Global.EXPORT_PDF, conn, true);		
		//String reporte = nombreRpt.toLowerCase() + "." + Global.EXPORT_PDF.toLowerCase();
		/*String reporte = nombreRpt + "." + Global.EXPORT_PDF.toLowerCase();
		FacesUtil.setParamBySession("file", ControlGenericoReporte.getInstancia().getFile());		
		//context.execute("window.open('" +  FacesUtil.getContentPath() + "/reporte?file="+ reporte + "&formato=" + Global.EXPORT_PDF + "','myWindow');");		
		context.execute("window.open('" +  FacesUtil.getContentPath() + "/reporte/"+ reporte + "','myWindow');");
		*/
	}
	
}
