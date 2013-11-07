package support;

import model.Evaluacion;
import model.Instrumento;
import model.Unidad;

public class BeanEvaluacion {
	
	Evaluacion evaluacion;
	Instrumento instrumento;
			
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
	public BeanEvaluacion(Instrumento instrumento, Evaluacion evaluacion) {
		super();			
		this.evaluacion = evaluacion;
		this.instrumento = instrumento;
	}
	
}
