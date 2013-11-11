package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name="unidades")
@NamedQueries ({
	@NamedQuery(name="Unidad.findById", query="select u from unidades u where u.id=?1")
})
public class Unidad {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "unidad_id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_id")
	private Certificacion certificacion;
	
	@NotNull
	@Column(name = "unidad_codigo", nullable = false)
	private String competenciaCodigo;

	@NotNull
	@Column(name = "unidad_descripcion", nullable = false)	
	private String competenciaDescripcion;
	
	@OneToMany(mappedBy="unidad")
	private List<Instrumento> instrumentos;
	
	@NotNull
	@Column(name = "unidad_estatus", nullable = false)	
	private boolean estatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Certificacion getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(Certificacion certificacion) {
		this.certificacion = certificacion;
	}

	public String getCompetenciaCodigo() {
		return competenciaCodigo;
	}

	public void setCompetenciaCodigo(String competenciaCodigo) {
		this.competenciaCodigo = competenciaCodigo;
	}

	public String getCompetenciaDescripcion() {
		return competenciaDescripcion;
	}

	public void setCompetenciaDescripcion(String competenciaDescripcion) {
		this.competenciaDescripcion = competenciaDescripcion;
	}

	public List<Instrumento> getInstrumentos() {
		return instrumentos;
	}

	public void setInstrumentos(List<Instrumento> instrumentos) {
		this.instrumentos = instrumentos;
	}
	
	public void addInstrumento(Instrumento instrumento){
		this.instrumentos.add(instrumento);
	}

	public Unidad(Certificacion certificacion, String competenciaCodigo, String competenciaDescripcion, List<Instrumento> instrumentos, boolean estatus) {
		super();
		this.certificacion = certificacion;
		this.competenciaCodigo = competenciaCodigo;
		this.competenciaDescripcion = competenciaDescripcion;
		this.instrumentos = instrumentos;
		this.estatus = estatus;
	}
	
	public Unidad() {
		super();		
	}

	public boolean isEstatus() {
		return estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}
}