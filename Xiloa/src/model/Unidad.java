package model;

import java.io.Serializable;
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
import javax.persistence.Table;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Entity(name = "unidades")
@Table(name = "unidades", schema = "sccl")
@NamedQueries ({
	@NamedQuery(name="Unidad.findById", query="select u from unidades u where u.id=?1")
})
public class Unidad implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "unidad_id", nullable = false)
	private Long id;
	
	@Column(name = "unidad_codigo", nullable = false)
	private Long codigo;
	
	@NotNull
	@Column(name = "unidad_descripcion", nullable = false)	
	private String competenciaDescripcion;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="certificacion_id")
	private Certificacion certificacion;
		
	@OneToMany(mappedBy="unidad")
	private List<Instrumento> instrumentos;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getCompetenciaDescripcion() {
		return competenciaDescripcion;
	}

	public void setCompetenciaDescripcion(String competenciaDescripcion) {
		this.competenciaDescripcion = competenciaDescripcion;
	}

	public Certificacion getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(Certificacion certificacion) {
		this.certificacion = certificacion;
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

	public Unidad(Long codigo, String descripcion, Certificacion certificacion, List<Instrumento> instrumentos) {
		super();
		this.codigo = codigo;
		this.competenciaDescripcion = descripcion;
		this.certificacion = certificacion;
		this.instrumentos = instrumentos;
	}
	
	public Unidad() {
		super();		
	}
}