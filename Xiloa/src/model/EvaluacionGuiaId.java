package model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es auxiliar para la anotación de la entidad EvaluacionGuia 
 * 
 */

@Embeddable
public class EvaluacionGuiaId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Evaluacion evaluacion;
	
	@ManyToOne
	private Guia guia;
	
	public Evaluacion getEvaluacion() {
		return evaluacion;
	}
	
	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Guia getGuia() {
		return guia;
	}

	public void setGuia(Guia guia) {
		this.guia = guia;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof EvaluacionGuiaId))
			return false;
		EvaluacionGuiaId that = (EvaluacionGuiaId) obj;
		if (evaluacion != null ? !evaluacion.equals(that.getEvaluacion()) : that.getEvaluacion() != null)
	            return false;
		if (guia != null ? !guia.equals(that.getGuia()) : that.getGuia() != null)
				return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		int result;
		result = (evaluacion != null ? evaluacion.hashCode() : 0);
		result = 17 * result + (guia != null ? guia.hashCode() : 0);
		return result;
	}
}