package support;

import model.Evaluacion;
import model.Instrumento;
import model.Unidad;

public class BeanEvaluacion {
	
	Instrumento instrumento;
	Unidad unidad;
	Evaluacion evaluacion;
	
	public Instrumento getInstrumento() {
		return instrumento;
	}
	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}
	public Unidad getUnidad() {
		return unidad;
	}
	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	public Evaluacion getEvaluacion() {
		return evaluacion;
	}
	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}
	
	public BeanEvaluacion(Instrumento instrumento, Unidad unidad,
			Evaluacion evaluacion) {
		super();
		this.instrumento = instrumento;
		this.unidad = unidad;
		this.evaluacion = evaluacion;
	}
	
}
