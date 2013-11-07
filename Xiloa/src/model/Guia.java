package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="guias")
@NamedQueries ({
	@NamedQuery (name="Guia.findByIdInstrumento", query="select g from guias g where g.instrumento.id=?1")
})
public class Guia {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "guia_id", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="guia_instrumento_id")
	private Instrumento instrumento;
	
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
	
	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
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
	
	public Guia(Instrumento instrumento, String pregunta, String respuesta, int puntaje, boolean estatus){
		this.instrumento = instrumento;
		this.pregunta = pregunta;
		this.respuesta = respuesta;
		this.puntaje = puntaje;
		this.estatus = estatus;
	}
}