package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="configuracion")
public class Configuracion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "configuracion_id", nullable = false)
	private Long id;
	
	@Column(name = "configuracion_nombre", nullable = false)	
	private String nombre;
	
	@Column(name = "configuracion_valor", nullable = false)	
	private String valor;
	
	@Column(name = "configuracion_tipo", nullable = false)
	private String tipo;
	
	@Column(name = "configuracion_grupo", nullable = false)
	private String grupo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
}