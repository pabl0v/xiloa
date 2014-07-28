package model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.evaluacion_guia 
 * 
 */

@Entity(name = "evaluacion_guia")
@Table(name = "evaluacion_guia", schema = "sccl")
@AssociationOverrides({
	@AssociationOverride(name = "pk.evaluacion", joinColumns = @JoinColumn(name = "evaluacion_id")),
	@AssociationOverride(name = "pk.guia", joinColumns = @JoinColumn(name = "guia_id"))})
@NamedQueries ({
	@NamedQuery(name="EvaluacionGuia.findByEvaluacionId", query="select eg from evaluacion_guia eg where eg.pk.evaluacion.id=?1"),	
	@NamedQuery(name="EvaluacionGuia.findInstrumentoByEvaluacionId", query="select eg.pk.guia.instrumento.id from evaluacion_guia eg where eg.pk.evaluacion.id=?1 group by eg.pk.guia.instrumento.id"),
	@NamedQuery(name="EvaluacionGuia.findGuiasByEvalAndInstrumento", query="select eg.pk.guia from evaluacion_guia eg where eg.pk.evaluacion.id=?1 and eg.pk.guia.instrumento.id=?2")
			})
public class EvaluacionGuia implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private EvaluacionGuiaId pk;
	
	@Column(name = "puntaje", nullable = false, precision=10, scale=2)	
	private Float puntaje;
		
	public EvaluacionGuia(){
		super();
		this.pk =  new EvaluacionGuiaId();
		this.puntaje = new Float(0);
	}
	
	public EvaluacionGuia(Evaluacion evaluacion, Guia guia, Float puntaje){
		super();
		this.pk = new EvaluacionGuiaId();
		this.pk.setEvaluacion(evaluacion);
		this.pk.setGuia(guia);
		this.puntaje = puntaje;
	}
	
	public EvaluacionGuiaId getPk() {
		return pk;
	}
	
	public void setPk(EvaluacionGuiaId pk) {
		this.pk = pk;
	}
	
	public Float getPuntaje() {
		return puntaje;
	}
	
	public String getPuntajeLabel(){
		return String.format("%.2f", (double)puntaje);
	}
	
	public void setPuntaje(Float puntaje) {
		this.puntaje = puntaje;
	}
	
	@Transient
	public Evaluacion getEvaluacion(){
		return pk.getEvaluacion();
	}
	
	public void setEvaluacion(Evaluacion evaluacion){
		pk.setEvaluacion(evaluacion);
	}
	
	@Transient
	public Guia getGuia(){
		return pk.getGuia();
	}
	
	public void setGuia(Guia guia){
		pk.setGuia(guia);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof EvaluacionGuia))
			return false;
		EvaluacionGuia that = (EvaluacionGuia) obj;
		if (pk != null ? !pk.equals(that.getPk()) : that.getPk() != null)
			return false;
		return true;
	 }
	
	@Override
	public int hashCode() {
		return (pk != null ? pk.hashCode() : 0);
	}
}