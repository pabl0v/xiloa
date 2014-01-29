package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @author Denis Chavez, Miriam Martínez
 * 
 * Entity Bean anotado con JPA para el manejo del mapeo Objeto/Relacional y la persistencia en BD
 * Esta clase es usada extensivamente por el servicio (paquete service)
 * Esta clase se corresponde con la tabla sccl.mantenedores 
 * 
 */

@Entity(name = "mantenedores")
@Table(name = "mantenedores", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Mantenedor.findMinByTipo", query="select m from sccl.mantenedores m where m.id = (select min(x.id) from sccl.mantenedores x where x.tipo = ?1)"),
	@NamedQuery(name="Mantenedor.findByTipo", query="select m from mantenedores m where m.tipo = ?1 order by 1"),
	@NamedQuery(name="Mantenedor.findMaxByTipo", query="select m from sccl.mantenedores m where m.id = (select max(x.id) from sccl.mantenedores x where x.tipo = ?1)"),
	@NamedQuery(name="Mantenedor.findAll", query="select m from mantenedores m order by tipo, id")
})
public class Mantenedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "mantenedor_id", nullable = false)
	private int id;

	@Column(name = "mantenedor_etiqueta", nullable = false)
	private String etiqueta;
	
	@Column(name = "mantenedor_valor", nullable = false)
	private String valor;
	
	@Column(name = "mantenedor_tipo", nullable = false)
	private String tipo;
	
	@Column(name = "mantenedor_proximo", nullable = true)
	private String proximo;
	
	@Column(name = "mantenedor_anterior", nullable = true)
	private String anterior;

	public Mantenedor() {
		super();		
	}
	
	public Mantenedor(String tipo, String etiqueta, String valor, String anterior, String proximo) {
		super();
		this.tipo = tipo;
		this.etiqueta = etiqueta;
		this.valor = valor;
		this.anterior = anterior;
		this.proximo = proximo;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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
}