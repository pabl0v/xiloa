package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * 
 * @author Denis Chavez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la vista sccl.evaluaciones_view 
 * 
 */

@Entity(name = "vista_evaluaciones")
@Table(name = "vista_evaluaciones", schema = "sccl")
@Immutable
public class VistaEvaluacion {

	@Id
	@Column(name = "evaluacion_id")
	private Long id;

	@Column(name = "aprobado")
	private boolean aprobado;

	@Column(name = "puntaje_obtenido")
	private Float puntajeObtenido;

	public Long getId() {
		return id;
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public Float getPuntajeObtenido() {
		return puntajeObtenido;
	}
}