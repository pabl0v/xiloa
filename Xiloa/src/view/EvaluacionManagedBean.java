package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;

import model.Certificacion;
import model.Evaluacion;
import model.EvaluacionGuia;
import model.EvaluacionGuiaId;
import model.Guia;
import model.Instrumento;
import model.Solicitud;
import model.Unidad;

@Component
@Scope(value="session")
public class EvaluacionManagedBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	
	private Solicitud   solicitudEval;
	private Long        idSelectedUnidad;
	private Long        idSelectedInstrByUnd;
	private Unidad      selectedUnidad;
	private Instrumento selectedInstrumento;
	private Date        fechaEvaluacion;
	private boolean     aprobado;
	private String      observaciones;
	private String      estado;
	
	private Evaluacion selectedEvaluacion;	
	
	private Guia []     selectedGuia;
	private List<Guia>  listGuiaByInstru;
	
	
	private List<SelectItem> listUnidadCompentecia;
	private List<SelectItem> listInstrumentoByUnidad;
	
	
	
	public EvaluacionManagedBean() {
		super();
		
		listUnidadCompentecia = new ArrayList<SelectItem> ();
		listInstrumentoByUnidad = new ArrayList<SelectItem> ();
		
		listGuiaByInstru = new ArrayList<Guia> ();
		
		aprobado = false;		
		
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Evaluacion getSelectedEvaluacion() {
		return selectedEvaluacion;
	}

	public void setSelectedEvaluacion(Evaluacion selectedEvaluacion) {
		this.selectedEvaluacion = selectedEvaluacion;
		this.setIdSelectedUnidad(this.selectedEvaluacion.getUnidad().getId());
		this.setFechaEvaluacion(selectedEvaluacion.getFechaEvaluacion());
		this.setObservaciones(selectedEvaluacion.getObservaciones());
		this.setAprobado(selectedEvaluacion.isAprobado());
		this.setEstado(selectedEvaluacion.getEstado());
	}

	public Solicitud getSolicitudEval() {		
		return solicitudEval;
	}

	public void setSolicitudEval(Solicitud solicitudEval) {		
		this.solicitudEval = solicitudEval;		
		inicializaSelectItems();
	}	
	
	public List<SelectItem> getListUnidadCompentecia() {
		return listUnidadCompentecia;
	}

	public void setListUnidadCompentecia(List<SelectItem> listUnidadCompentecia) {
		this.listUnidadCompentecia = listUnidadCompentecia;
	}
	
	public Unidad getSelectedUnidad() {
		return selectedUnidad;
	}

	public void setSelectedUnidad(Unidad selectedUnidad) {
		this.selectedUnidad = selectedUnidad;
	}

	public Instrumento getSelectedInstrumento() {
		return selectedInstrumento;
	}

	public void setSelectedInstrumento(Instrumento selectedInstrumento) {
		this.selectedInstrumento = selectedInstrumento;
		this.setIdSelectedInstrByUnd(this.selectedInstrumento.getId());
		Object [] objs =  new Object [] {this.selectedEvaluacion.getId(), this.selectedInstrumento.getId()};
		List<Guia> guiasEvalInst = service.getGuiaByParam("EvaluacionGuia.findGuiasByEvalAndInstrumento", objs);
		
		selectedGuia = new Guia [guiasEvalInst.size()];
		int i=0;
		for (Guia dato : guiasEvalInst) {
			selectedGuia[i++] = dato;
		}
	}

	public List<SelectItem> getListInstrumentoByUnidad() {
		return listInstrumentoByUnidad;
	}

	public void setListInstrumentoByUnidad(List<SelectItem> listInstrumentoByUnidad) {
		this.listInstrumentoByUnidad = listInstrumentoByUnidad;
	}
	
	public Long getIdSelectedUnidad() {
		return idSelectedUnidad;
	}

	public void setIdSelectedUnidad(Long idSelectedUnidad) {
		this.idSelectedUnidad = idSelectedUnidad;
	}

	public Long getIdSelectedInstrByUnd() {
		return idSelectedInstrByUnd;
	}

	public void setIdSelectedInstrByUnd(Long idSelectedInstrByUnd) {
		this.idSelectedInstrByUnd = idSelectedInstrByUnd;
	}

	public Date getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(Date fechaEvaluacion) {
		this.fechaEvaluacion = fechaEvaluacion;
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}	
	
	public Guia[] getSelectedGuia() {
		return selectedGuia;
	}

	public void setSelectedGuia(Guia[] selectedGuia) {
		this.selectedGuia = selectedGuia;
	}

	public List<Guia> getListGuiaByInstru() {
		return listGuiaByInstru;
	}

	public void setListGuiaByInstru(List<Guia> listGuiaByInstru) {
		this.listGuiaByInstru = listGuiaByInstru;
	}	
	
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public void handleInstrumentosByUnidad () {
		
		List<Instrumento> listInstru;
		
		if (this.idSelectedUnidad != null) {
			selectedUnidad = service.getUnidadById(this.idSelectedUnidad);
			
			listInstru = service.getInstrumentoByUnidad(selectedUnidad.getId());
			this.listInstrumentoByUnidad = new ArrayList<SelectItem>();
			this.listInstrumentoByUnidad.add(new SelectItem(null, "Seleccione el instrumento"));
			for (Instrumento dato : listInstru){
				this.listInstrumentoByUnidad.add(new SelectItem(dato.getId(), dato.getDescripcion()));
			}
			
		}
	}
	
	public void handleGuiasByInstrumento(){		
		selectedInstrumento = service.getInstrumentoById(idSelectedInstrByUnd);
		Object [] objs =  new Object [] {selectedInstrumento.getId()};
		listGuiaByInstru = service.getGuiaByParam("Guia.findByIdInstrumento", objs);						
	}
	
	public void inicializaSelectItems (){
		if (solicitudEval != null){
			Certificacion c = this.solicitudEval.getCertificacion();
			
			List<Unidad> setUnidades =  service.getUnidadesByCertificacionId(c.getId());				
						
			this.listUnidadCompentecia = new ArrayList<SelectItem>();
			
			this.listUnidadCompentecia.add(new SelectItem(null, "Seleccione la unidad de competencia"));
			
			for(Unidad unidad : setUnidades){
				this.listUnidadCompentecia.add(new SelectItem(unidad.getId(), unidad.getCompetenciaDescripcion()));			
			}
			
			this.listInstrumentoByUnidad = new ArrayList<SelectItem>();
			this.listInstrumentoByUnidad.add(new SelectItem(null, "Seleccione el instrumento"));
		} else {
			this.listUnidadCompentecia.add(new SelectItem(null, "Seleccione la unidad de competencia"));
			this.listInstrumentoByUnidad.add(new SelectItem(null, "Seleccione el instrumento"));
		}
	}
	
	public void guardarEvaluacion() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		Evaluacion eval;	
		
		boolean isError = false;
		
		System.out.println("Metodo GuardarEvaluacion");		
		
		//Se registra nueva evaluacion
		if (selectedEvaluacion == null){
			System.out.println("Nueva evaluacion");			
			eval = new Evaluacion (this.getSolicitudEval(), // solicitud 
			   					   this.getFechaEvaluacion(), // fecha 
								   this.getSelectedUnidad(), // unidad 
								   null , // Set<EvaluacionGuia> guias 
								   new Integer(0), // puntaje, 
								   this.getObservaciones(), // observaciones 
								   this.isAprobado() // aprobado
								   );
			String estadoTipo = eval.getTipoMantenedorEstado();
			
			eval.setEstado(service.getMantenedorMinByTipo(estadoTipo).toString());			
			
			eval = (Evaluacion) service.guardar(eval);
			
			if (eval != null) {
				
				Set<EvaluacionGuia> setEvalGuia = new HashSet<EvaluacionGuia> ();
									
				for (Guia dato : this.selectedGuia) {
													
					EvaluacionGuiaId pkDetalleGuia = new EvaluacionGuiaId();
					
					pkDetalleGuia.setEvaluacion(eval);
					pkDetalleGuia.setGuia(dato);
					
					EvaluacionGuia detalleEvaGuia = new EvaluacionGuia();
					
					detalleEvaGuia.setPk(pkDetalleGuia);
					detalleEvaGuia.setPuntaje(new Integer(0));				
					
					detalleEvaGuia = (EvaluacionGuia) service.guardar(detalleEvaGuia);
					
					setEvalGuia.add(detalleEvaGuia);
				}			
				
			} else {
				isError = true;
			}			
			
		} else { // Guarda los cambios en la edicion de la evaluacion			
						
			eval = selectedEvaluacion;
			eval.setObservaciones(this.observaciones);			
			eval.setAprobado(this.aprobado);			
						
			eval = (Evaluacion) service.guardar(eval);			
			
			if (eval == null)
				isError = true;			
			
		}
			
		if (!isError){						
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje: ", "La evaluacion ha sido registrada exitosamente."));		
			
		}else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje", "Error al grabar la evaluacion. Favor revisar..."));
		}
		
	}	
	
	public void resetValores () {
		this.selectedEvaluacion = null;
		this.selectedGuia = null;
		this.selectedInstrumento = null;
		this.selectedUnidad = null;
		this.idSelectedInstrByUnd = null;
		this.idSelectedUnidad = null;
		this.listGuiaByInstru = null;
		this.listInstrumentoByUnidad = null;
		this.listUnidadCompentecia = null;
		this.aprobado = false;
		this.fechaEvaluacion = null;
		this.observaciones = null;
		this.estado = null;
	}
	
	public String registrar_evaluacion (Solicitud sol) {		
		
		this.setSolicitudEval(sol);
		
		inicializaSelectItems();
		
		return "/modulos/solicitudes/registro_evaluacion?faces-redirect=true";
	}
	
	public String cancelarRegistro () {
		resetValores ();
		return "/modulos/solicitudes/expediente?faces-redirect=true";
	}

}
