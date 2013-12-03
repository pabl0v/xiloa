package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.FacesUtil;
import model.Certificacion;
import model.Evaluacion;
import model.EvaluacionGuia;
import model.EvaluacionGuiaId;
import model.Guia;
import model.Instrumento;
import model.Mantenedor;
import model.Solicitud;

@Component
@Scope(value="view")
public class EvaluacionManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	@Autowired
	private UtilitariosManagedBean utilitarios;
	
	private Solicitud   solicitudEval;
	private Long        idSelectedUnidad;
	private Long        idSelectedInstrByUnd;
	private Long      	selectedUnidad;
	private Instrumento selectedInstrumento;
	private Date        fechaEvaluacion;
	private boolean     aprobado;
	private String      observaciones;
	private Mantenedor  estado;
	private List<Mantenedor> listaEstados;
	private int         estadoId;
	
	private Evaluacion selectedEvaluacion;	
	
	private Guia []     selectedGuia;
	private List<Guia>  listGuiaByInstru;
	private List<EvaluacionGuia> listaEvaluacionGuia;
	
	private EvaluacionGuia selectedEvaluacionGuia;
		
	private List<SelectItem> listUnidadCompentecia;
	private List<SelectItem> listInstrumentoByUnidad;
	
	private String preguntaGuia;
	private String respuestaGuia;
	private Integer puntajeGuia;
	private boolean disableAgregaGuias;
	private Mantenedor solicitudAsesorado;
	private boolean aprobadoGuia;
	private Integer puntajeEval;
	private Integer puntajeMinEval;
	private List<SelectItem> listaAprobados;
	
	public EvaluacionManagedBean() {
		super();
		
		listUnidadCompentecia = new ArrayList<SelectItem> ();
		listInstrumentoByUnidad = new ArrayList<SelectItem> ();
		
		listGuiaByInstru = new ArrayList<Guia> ();
		
		aprobado = false;		
		
		listaEstados = new ArrayList<Mantenedor>();
		
		listaEvaluacionGuia = new ArrayList<EvaluacionGuia> ();
		disableAgregaGuias = true;
		listaAprobados = new ArrayList<SelectItem> ();
	}

	public List<SelectItem> getListaAprobados() {		
		return listaAprobados;
	}

	public void setListaAprobados(List<SelectItem> listaAprobados) {
		this.listaAprobados = listaAprobados;
	}

	public Integer getPuntajeMinEval() {
		return puntajeMinEval;
	}

	public void setPuntajeMinEval(Integer puntajeMinEval) {
		this.puntajeMinEval = puntajeMinEval;
	}

	public Integer getPuntajeEval() {
		return puntajeEval;
	}

	public void setPuntajeEval(Integer puntajeEval) {
		this.puntajeEval = puntajeEval;
	}

	public boolean isAprobadoGuia() {
		return aprobadoGuia;
	}

	public void setAprobadoGuia(boolean aprobadoGuia) {
		this.aprobadoGuia = aprobadoGuia;
	}

	public Mantenedor getSolicitudAsesorado() {
		return solicitudAsesorado;
	}

	public void setSolicitudAsesorado(Mantenedor solicitudAsesorado) {
		this.solicitudAsesorado = solicitudAsesorado;
	}

	public boolean isDisableAgregaGuias() {
		return disableAgregaGuias;
	}

	public void setDisableAgregaGuias(boolean disableAgregaGuias) {
		this.disableAgregaGuias = disableAgregaGuias;
	}

	public String getPreguntaGuia() {
		return preguntaGuia;
	}

	public void setPreguntaGuia(String preguntaGuia) {
		this.preguntaGuia = preguntaGuia;
	}

	public String getRespuestaGuia() {
		return respuestaGuia;
	}

	public void setRespuestaGuia(String respuestaGuia) {
		this.respuestaGuia = respuestaGuia;
	}

	public Integer getPuntajeGuia() {
		return puntajeGuia;
	}

	public void setPuntajeGuia(Integer puntajeGuia) {
		this.puntajeGuia = puntajeGuia;
	}

	public EvaluacionGuia getSelectedEvaluacionGuia() {
		return selectedEvaluacionGuia;
	}

	public void setSelectedEvaluacionGuia(EvaluacionGuia selectedEvaluacionGuia) {
		this.selectedEvaluacionGuia = selectedEvaluacionGuia;
	}

	public List<EvaluacionGuia> getListaEvaluacionGuia() {
		if (this.getSelectedEvaluacion() != null)
			this.listaEvaluacionGuia = service.getEvaluacionGuiaByEvaluacionId(this.selectedEvaluacion.getId());
		return listaEvaluacionGuia;
	}

	public void setListaEvaluacionGuia(List<EvaluacionGuia> listaEvaluacionGuia) {
		this.listaEvaluacionGuia = listaEvaluacionGuia;
	}

	public List<Mantenedor> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<Mantenedor> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public int getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(int estadoId) {
		this.estadoId = estadoId;
	}

	public Mantenedor getEstado() {
		return estado;
	}

	public void setEstado(Mantenedor estado) {
		this.estado = estado;
	}

	public Evaluacion getSelectedEvaluacion() {		
		return selectedEvaluacion;
	}

	public void setSelectedEvaluacion(Evaluacion selectedEvaluacion) {		
		this.selectedEvaluacion = selectedEvaluacion;		
		if (this.selectedEvaluacion != null){
			this.setIdSelectedUnidad(this.selectedEvaluacion.getUnidad());
			this.setSelectedUnidad(this.selectedEvaluacion.getUnidad());
			this.setFechaEvaluacion(selectedEvaluacion.getFechaEvaluacion());
			this.setObservaciones(selectedEvaluacion.getObservaciones());
			this.setAprobado(selectedEvaluacion.isAprobado());
			this.setEstado(selectedEvaluacion.getEstado());
			this.setEstadoId(selectedEvaluacion.getEstado().getId());
			this.setPuntajeEval(selectedEvaluacion.getPuntaje());
		}
	}

	public Solicitud getSolicitudEval() {		
		return solicitudEval;
	}

	public void setSolicitudEval(Solicitud solicitudEval) {		
		this.solicitudEval = solicitudEval;			
		inicializaSelectItems(); //Inicializa SelecItems
	}	
	
	public List<SelectItem> getListUnidadCompentecia() {
		return listUnidadCompentecia;
	}

	public void setListUnidadCompentecia(List<SelectItem> listUnidadCompentecia) {
		this.listUnidadCompentecia = listUnidadCompentecia;
	}
	
	public Long getSelectedUnidad() {
		return selectedUnidad;
	}

	public void setSelectedUnidad(Long selectedUnidad) {
		this.selectedUnidad = selectedUnidad;
	}

	public Instrumento getSelectedInstrumento() {
		return selectedInstrumento;
	}

	public void setSelectedInstrumento(Instrumento selectedInstrumento) {
		this.selectedInstrumento = selectedInstrumento;
		if (this.selectedInstrumento != null){
			this.puntajeMinEval = selectedInstrumento.getPuntajeMinimo();
			this.setIdSelectedInstrByUnd(this.selectedInstrumento.getId());
			Object [] objs =  new Object [] {this.selectedEvaluacion.getId(), this.selectedInstrumento.getId()};
			List<Guia> guiasEvalInst = service.getGuiaByParam("EvaluacionGuia.findGuiasByEvalAndInstrumento", objs);
		
			selectedGuia = new Guia [guiasEvalInst.size()];
			int i=0;
			for (Guia dato : guiasEvalInst) {
				selectedGuia[i++] = dato;
			}
					
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
	
	@PostConstruct
	private void initBeanEvaluacion(){
		Solicitud sol = null;
		Evaluacion eval = null;
		Long unidadSelected = null;
		Instrumento instrumentoSel = null;
		
		//Recibiendo los parametros
		
		sol = (Solicitud)FacesUtil.getParametroSession("solicitudEval");
		
		if (sol != null){
			this.setSolicitudEval(sol);
			Mantenedor estadoInicialSolicitud = service.getMantenedorMinByTipo(sol.getTipomantenedorestado());
			Mantenedor segEstado = utilitarios.getCatalogoEstadoSolicitud().get(Integer.valueOf(estadoInicialSolicitud.getProximo()));
			
			if (segEstado != null) {
				Mantenedor estadoConvocado = utilitarios.getCatalogoEstadoSolicitud().get(Integer.valueOf(segEstado.getProximo()));
				
				if (estadoConvocado != null)
					this.solicitudAsesorado = utilitarios.getCatalogoEstadoSolicitud().get(Integer.valueOf(segEstado.getProximo()));
			}
			
			
		}else
			this.setSolicitudEval(null);
					
		eval = (Evaluacion)FacesUtil.getParametroSession("selectedEvaluacion");
		
		if (eval != null){
			this.setSelectedEvaluacion(eval);	
			this.disableAgregaGuias = false;
		}else
			this.setSelectedEvaluacion(null);
		
		unidadSelected = (Long) FacesUtil.getParametroSession("selectedUnidad");
		if (unidadSelected != null){
			this.setSelectedUnidad(unidadSelected);
			this.setIdSelectedUnidad(unidadSelected);	
			handleInstrumentosByUnidad ();
		}else{
			this.setSelectedUnidad(null);
			this.setIdSelectedUnidad(null);
		}
		
		instrumentoSel = (Instrumento)FacesUtil.getParametroSession("selectedInstrumento");
		if (instrumentoSel != null){
			Object [] objs =  new Object [] {instrumentoSel.getId()};
			listGuiaByInstru = service.getGuiaByParam("Guia.findByIdInstrumento", objs);
			
			this.setSelectedInstrumento(instrumentoSel);
		}else
			this.setSelectedInstrumento(null);
			
		listaAprobados = new ArrayList<SelectItem> ();
		listaAprobados.add(new SelectItem(true, "Aprobado"));
		listaAprobados.add(new SelectItem(false, "No Aprobado"));
	}

	public void handleInstrumentosByUnidad () {
		
		List<Instrumento> listInstru;
		
		Mantenedor estadoActualSolicitud = this.solicitudEval.getEstatus();
		Mantenedor diagnostico = service.getMantenedorMinByTipo(new String("6"));
		boolean    isInicial = false;
		
		if (this.solicitudAsesorado.getId() == estadoActualSolicitud.getId())
			isInicial = true;
				
		if (this.idSelectedUnidad != null) {			
			selectedUnidad = (selectedUnidad != this.idSelectedUnidad) ? this.idSelectedUnidad : selectedUnidad;
			
			listInstru = service.getInstrumentoByUnidad(selectedUnidad);
			this.listInstrumentoByUnidad = new ArrayList<SelectItem>();
			this.listInstrumentoByUnidad.add(new SelectItem(null, "Seleccione el instrumento"));
			for (Instrumento dato : listInstru){
				Mantenedor tipoInstrumento = dato.getTipo();
				if (isInicial == true){
					if (tipoInstrumento.getId() != diagnostico.getId())
						this.listInstrumentoByUnidad.add(new SelectItem(dato.getId(), dato.getDescripcion() + " - " + tipoInstrumento.getValor()));					
				} else{
					if (tipoInstrumento.getId() == diagnostico.getId())
						this.listInstrumentoByUnidad.add(new SelectItem(dato.getId(), dato.getDescripcion() + " - " + tipoInstrumento.getValor()));
				}
			}
			
		}
	}
	
	public void handleGuiasByInstrumento(){		
		Instrumento inst = null;
		Long  selectedInstrumentoId = null;
		
		if (idSelectedInstrByUnd != null)
			inst = service.getInstrumentoById(idSelectedInstrByUnd);
		
		if (this.selectedInstrumento == null){
			selectedInstrumento = inst;
			selectedInstrumentoId = inst.getId();
		}
		
		if (inst.getId() != selectedInstrumentoId)
			selectedInstrumento = inst;
			
		Object [] objs =  new Object [] {selectedInstrumento.getId()};
		listGuiaByInstru = service.getGuiaByParam("Guia.findByIdInstrumento", objs);						
	}
	
	public void inicializaSelectItems (){
		if (solicitudEval != null){
			Certificacion c = this.solicitudEval.getCertificacion();
			
			List<Long> setUnidades =  service.getUnidadesByCertificacionId(c.getId());
						
			this.listUnidadCompentecia = new ArrayList<SelectItem>();
			
			this.listUnidadCompentecia.add(new SelectItem(null, "Seleccione la unidad de competencia"));
			
			for(Long unidad : setUnidades){
				this.listUnidadCompentecia.add(new SelectItem(unidad, utilitarios.getCompetenciaDescripcion(unidad)));			
				//this.listUnidadCompentecia.add(new SelectItem(unidad.getId(), utilitarios.getCompetenciaDescripcion(unidad.getCodigo())));
			}
								
			this.listInstrumentoByUnidad = new ArrayList<SelectItem>();
			this.listInstrumentoByUnidad.add(new SelectItem(null, "Seleccione el instrumento"));
		} else {
			this.listUnidadCompentecia.add(new SelectItem(null, "Seleccione la unidad de competencia"));
			this.listInstrumentoByUnidad.add(new SelectItem(null, "Seleccione el instrumento"));
		}
	}
	
	public void guardarEvaluacion() {
				
		Evaluacion eval;			
		boolean isError = false;
		String  mensaje = "";
		
		//Se registra nueva evaluacion
		if (selectedEvaluacion == null){
		
			this.puntajeEval = new Integer(0);
			eval = new Evaluacion (this.getSolicitudEval(), // solicitud 
			   					   this.getFechaEvaluacion(), // fecha 
								   this.getSelectedUnidad(), // unidad 
								   null , // Set<EvaluacionGuia> guias 
								   this.puntajeEval, // puntaje, 
								   this.getObservaciones(), // observaciones 
								   this.isAprobado() // aprobado
								   );
			String estadoTipo = eval.getTipoMantenedorEstado();
			
			eval.setEstado(service.getMantenedorMinByTipo(estadoTipo));			
			
			eval = (Evaluacion) service.guardar(eval);
			
			if (eval != null) {
				this.disableAgregaGuias = false;
				this.selectedEvaluacion = eval;
				
				Set<EvaluacionGuia> setEvalGuia = new HashSet<EvaluacionGuia> ();
				
				for (Guia dato : this.selectedGuia) {
													
					EvaluacionGuiaId pkDetalleGuia = new EvaluacionGuiaId();
					
					pkDetalleGuia.setEvaluacion(this.selectedEvaluacion);
					pkDetalleGuia.setGuia(dato);
					
					EvaluacionGuia detalleEvaGuia = new EvaluacionGuia();
					
					detalleEvaGuia.setPk(pkDetalleGuia);
					detalleEvaGuia.setPuntaje(new Integer(0));				
					
					detalleEvaGuia = (EvaluacionGuia) service.guardar(detalleEvaGuia);
					
					if (detalleEvaGuia == null){
						isError = true;
						mensaje = "Error al registrar el detalle de la evaluacion. Favor revisar...";
					}else
						setEvalGuia.add(detalleEvaGuia);
				}
				mensaje = "La evaluacion ha sido registrada exitosamente.";
			} else {
				isError = true;
				this.disableAgregaGuias = true;
				mensaje = "Error al grabar la evaluacion. Favor revisar...";
			}			
			
		} else { // Guarda los cambios en la edicion de la evaluacion			
			Mantenedor estadoEval = null;		
			Mantenedor sigEstado = null;
			
			eval = selectedEvaluacion;
			estadoEval = eval.getEstado();
			
			if (this.aprobado == true){
				if (this.puntajeEval == null){
					isError = true;
					mensaje = "Debe indicar el puntaje a nivel de la evaluacion";
				} else if (this.puntajeEval == 0){
					isError = true;
					mensaje = "Error, el puntaje a nivel de la evaluacion es cero y se indica que esta APROBADO. Favor revisar...";
				} else if (this.puntajeEval < this.puntajeMinEval){
					isError = true;
					mensaje = "Error, el puntaje a nivel de la evaluacion es menor al puntaje minimo configurado al instrumento. Favor revisar...";
				} else {
					eval.setPuntaje(this.puntajeEval);
					
					if (estadoEval.getProximo() != null){
						sigEstado = service.getMantenedorById(Integer.valueOf(estadoEval.getProximo()));
						
						if (sigEstado != null)
							eval.setEstado(sigEstado);
					}
				}
			}
			
			if (isError == false){
				eval.setObservaciones(this.observaciones);			
				eval.setAprobado(this.aprobado);
										
				eval = (Evaluacion) service.guardar(eval);			
				
				if (eval == null){
					isError = true;
					mensaje = "Error al actualizar la evaluacion. Favor revisar...";
				} else {					
					mensaje = "Proceso completado exitosamente. La evaluacion ha sido actualizada !!!!";
				}
					
			}						
			
		}
		
		FacesUtil.getMensaje("SCCL - Mensaje: ", mensaje, isError);				
		
	}	
	
	public void saveEvalGuia(){
		this.selectedEvaluacionGuia = (this.selectedEvaluacionGuia == null) ? (EvaluacionGuia) FacesUtil.getParametroSession("selectedEvaluacionGuia") : this.selectedEvaluacionGuia;
		FacesUtil.setParamBySession("selectedEvaluacionGuia", null);
		if (this.selectedEvaluacionGuia != null) {			
			this.selectedEvaluacionGuia.setPuntaje(this.puntajeGuia);
			this.selectedEvaluacionGuia.setAprobado(this.aprobadoGuia);
						
			this.selectedEvaluacionGuia = (EvaluacionGuia) service.guardar(this.selectedEvaluacionGuia);			
		}
		
	}
	
	public void editaGuia(){
		FacesUtil.setParamBySession("selectedEvaluacionGuia", this.selectedEvaluacionGuia);	
		if (this.selectedEvaluacionGuia != null){			
			this.preguntaGuia = this.selectedEvaluacionGuia.getPk().getGuia().getPregunta();
			this.respuestaGuia = this.selectedEvaluacionGuia.getPk().getGuia().getRespuesta();
			this.puntajeGuia = this.selectedEvaluacionGuia.getPuntaje();			
		} else {
			this.preguntaGuia = null;
			this.respuestaGuia = null;
			this.puntajeGuia = 0;
		}
		
	}
	
	public void agregaGuias (){
		if (this.selectedEvaluacion != null) {
			Set<EvaluacionGuia> setEvalGuia = new HashSet<EvaluacionGuia> ();
			
			for (Guia dato : this.selectedGuia) {
												
				EvaluacionGuiaId pkDetalleGuia = new EvaluacionGuiaId();
				
				pkDetalleGuia.setEvaluacion(this.selectedEvaluacion);
				pkDetalleGuia.setGuia(dato);
				
				EvaluacionGuia detalleEvaGuia = new EvaluacionGuia();
				
				detalleEvaGuia.setPk(pkDetalleGuia);
				detalleEvaGuia.setPuntaje(new Integer(0));				
				
				detalleEvaGuia = (EvaluacionGuia) service.guardar(detalleEvaGuia);
				
				setEvalGuia.add(detalleEvaGuia);
			}	
				
		}
				
	}
	
	public void cancelEdicionEvalGuia(){
		FacesUtil.setParamBySession("selectedEvaluacionGuia", null);
		this.preguntaGuia = null;
		this.respuestaGuia = null;
		this.puntajeGuia = 0;
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
		String urlDestino = "";
		
		resetValores ();
		urlDestino = "/modulos/solicitudes/expediente_evaluacion?faces-redirect=true";
		FacesUtil.setParamBySession("dbSolicitudesBean", this.getSolicitudEval());
				
		return urlDestino;
	}
}