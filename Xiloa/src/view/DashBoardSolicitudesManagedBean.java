package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import model.Certificacion;
import model.Mantenedor;
import model.Solicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.Ifp;
import support.USolicitud;

@Component
@Scope(value="request")
public class DashBoardSolicitudesManagedBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	
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
		this.listAccionConvo.add(new SelectItem(null, "Seleccione la accion"));
		this.listAccionConvo.add(new SelectItem(new Integer(1), "Autorizar"));
		this.listAccionConvo.add(new SelectItem(new Integer(2), "Exportar a Excel"));
		this.listAccionConvo.add(new SelectItem(new Integer(3), "Exportar a PDF"));
	}
	
	
		
   //Llenado de Centro
	@PostConstruct
	private void initBeanDBSolicitudes(){
		List<Ifp> lista = service.getIfpByInatec();
		this.listCentrosBySolicitud.add(new SelectItem(null, "Todos"));
		for (Ifp dato : lista) {	
			this.listCentrosBySolicitud.add(new SelectItem(dato.getIfpId(),dato.getIfpNombre()));
		}		
		
		llenarListBuscarByAll();
		llenarListAccionConvo ();
		handleCertByCentro();
	}
	
	public void handleCertByCentro() {
		List<Certificacion> certificacionList = service.getCertificacionesByIdIfp(this.getSelectedIdIfpSolicitud());
		listCertByCentro = new ArrayList<SelectItem>();
		this.listCertByCentro.add(new SelectItem(null,"Todas las Unidades"));
		for (Certificacion dato : certificacionList) {
			this.listCertByCentro.add(new SelectItem(dato.getId(),dato.getNombre()));
		}
		
		this.listaSolicitudes = service.getSolicitudesByParam(asignaParams ());
		this.filterSolicitudes = this.listaSolicitudes;
	}
	
	public void handleBuscar () {						
		this.listaSolicitudes = service.getSolicitudesByParam(asignaParams ());
		this.filterSolicitudes = this.listaSolicitudes;
	}
	
	public HashMap<String, Object> asignaParams () {
		HashMap<String, Object> params = new HashMap<String, Object>();
				
		if (this.getSelectedIdIfpSolicitud() != null) {
			params.put("s.certificacion.ifpId", this.getSelectedIdIfpSolicitud());
		}
		
		if (this.selectedIdCertByCentro != null) {
			params.put("s.certificacion.id", this.selectedIdCertByCentro);
		}
		
		if (this.buscarByAllValue != null && this.selectedBuscarByAll != null) {			
			params.put(this.selectedBuscarByAll, this.buscarByAllValue);
		}
		
		return params;
	}
	
	
	
	public String nuevaSolicitud(){		
	return "/modulos/solicitudes/registro_solicitud?faces-redirect=true";
	}
	
	public String editaSolicitud(){	
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("dbSolicitudesBean",this.selectedSolicitud);
		return "/modulos/solicitudes/expediente?faces-redirect=true";
	}
	
	public String cancelarEdicion() {		
		return "/modulos/solicitudes/solicitudes?faces-redirect=true";				
	}
			
	public void handleConvocatoria() {
		boolean isError = false;
		FacesContext context = FacesContext.getCurrentInstance();
		
		// Cuando ha seleccionado la opcion Aplicar - Las solicitudes pasan a convocatoria		
		if (this.selectedAccionConvo == 1) {			
			for (Solicitud sol : this.selectedListSolicitud){				
				
				Integer idEstado = Integer.valueOf(sol.getEstatus().getProximo());
				Mantenedor sigEstado = service.getMantenedorById(idEstado);
				sol.setEstatus(sigEstado); //22 es el estado Convocado
				sol = (Solicitud)service.guardar(sol);
				
				if (sol != null){
					isError = false;
				} else {
					isError = true;
				}
			}
			
			if ( isError) {        
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje: ", "Error al pasar las solicitudes a Convocatoria. Favor revisar..."));
			}else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje: ", "Proceso aplicado exitosamente."));
			}
			
			this.selectedListSolicitud = null;
			
		}	
		
	}

	
}
