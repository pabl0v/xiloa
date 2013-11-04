package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="guias")
public class Guia {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "guia_id", nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "guia_pregunta", nullable = false)	
	private String pregunta;
	
	@NotNull
	@Column(name = "guia_respuesta", nullable = false)	
	private String respuesta;
	
	@NotNull
	@Column(name = "guia_puntaje", nullable = false)	
	private int puntaje;

	@NotNull
	@Column(name = "guia_estatus", nullable = false)
	private boolean estatus;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPregunta() {
		return pregunta;
	}
	
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	
	public String getRespuesta() {
		return respuesta;
	}
	
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	public int getPuntaje() {
		return puntaje;
	}
	
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	public boolean getEstatus(){
		return estatus;
	}
	
	public void setEstatus(boolean estatus){
		this.estatus = estatus;
	}
	
	public Guia(){
		super();
	}
	
	public Guia(String pregunta, String respuesta, int puntaje, boolean estatus){
		this.pregunta = pregunta;
		this.respuesta = respuesta;
		this.puntaje = puntaje;
		this.estatus = estatus;
	}
}