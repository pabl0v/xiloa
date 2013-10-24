package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="mantenedores")
public class Mantenedor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "mantenedor_id", nullable = false)
	private Long id;

	@Column(name = "mantenedor_etiqueta", nullable = false)
	private String etiqueta;
	
	@Column(name = "mantenedor_valor", nullable = false)
	private String valor;
	
	@Column(name = "mantenedor_tipo", nullable = false)
	private String tipo;
	
	@Column(name = "mantenedor_proximo", nullable = false)
	private String proximo;
	
	@Column(name = "mantenedor_anterior", nullable = false)
	private String anterior;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getProximo() {
		return proximo;
	}

	public void setProximo(String proximo) {
		this.proximo = proximo;
	}

	public String getAnterior() {
		return anterior;
	}

	public void setAnterior(String anterior) {
		this.anterior = anterior;
	}

	public Mantenedor(String etiqueta, String valor, String tipo,
			String proximo, String anterior) {
		super();
		this.etiqueta = etiqueta;
		this.valor = valor;
		this.tipo = tipo;
		this.proximo = proximo;
		this.anterior = anterior;
	}
	
	public Mantenedor() {
		super();		
	}
}