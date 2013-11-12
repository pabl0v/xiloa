package support;

import java.io.Serializable;

public class Departamento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer dpto_id;
	private String dpto_nombre;
	
	public Integer getDpto_id() {
		return dpto_id;
	}
	public void setDpto_id(Integer dpto_id) {
		this.dpto_id = dpto_id;
	}
	public String getDpto_nombre() {
		return dpto_nombre;
	}
	public void setDpto_nombre(String dpto_nombre) {
		this.dpto_nombre = dpto_nombre;
	}
	public Departamento(Integer dpto_id, String dpto_nombre) {
		super();
		this.dpto_id = dpto_id;
		this.dpto_nombre = dpto_nombre;
	}
	public Departamento() {
		super();
	}
	
}
