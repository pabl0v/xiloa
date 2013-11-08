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
	public BeanEvaluacion(Solicitud solicitudBeanEval, Evaluacion evaluacion,
			Instrumento instrumento) {
		super();
		this.solicitudBeanEval = solicitudBeanEval;
		this.evaluacion = evaluacion;
		this.instrumento = instrumento;
	}
	
	
	
}
