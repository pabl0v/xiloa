package support;

import org.springframework.beans.factory.annotation.Autowired;

import service.IService;
import model.Evaluacion;
import model.Instrumento;
import model.Solicitud;

public class BeanEvaluacion {
	
	@Autowired
	private IService service;
	
	private Solicitud solicitudBeanEval;
	private Evaluacion evaluacion;
	private Instrumento instrumento;
	private String      estadoEvaluacion;
			
	public Evaluacion getEvaluacion() {
		return evaluacion;
	}
	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}	
	
	public Instrumento getInstrumento() {				
		return instrumento;
	}
	
	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}
	
	public Solicitud getSolicitudBeanEval() {
		return solicitudBeanEval;
	}
	
	public void setSolicitudBeanEval(Solicitud solicitudBeanEval) {
		this.solicitudBeanEval = solicitudBeanEval;
	}
	
	public String getEstadoEvaluacion() {
		return estadoEvaluacion;
	}
	public void setEstadoEvaluacion(String estadoEvaluacion) {
		this.estadoEvaluacion = estadoEvaluacion;
	}
	public BeanEvaluacion(Solicitud solicitudBeanEval, Evaluacion evaluacion,
			Instrumento instrumento, String estado) {
		super();
		this.solicitudBeanEval = solicitudBeanEval;
		this.evaluacion = evaluacion;
		this.instrumento = instrumento;
		this.estadoEvaluacion = estado;
	}
	
	
	
}
