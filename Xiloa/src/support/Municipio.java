package support;

public class Municipio {
	Integer municipio_id;
	Integer municipio_dpto_id;
	String municipio_nombre;
	
	public Integer getMunicipio_id() {
		return municipio_id;
	}
	public void setMunicipio_id(Integer municipio_id) {
		this.municipio_id = municipio_id;
	}
	
	public Integer getMunicipio_dpto_id() {
		return municipio_dpto_id;
	}
	
	public void setMunicipio_dpto_id(Integer municipio_dpto_id) {
		this.municipio_dpto_id = municipio_dpto_id;
	}
	
	public String getMunicipio_nombre() {
		return municipio_nombre;
	}
	
	public void setMunicipio_nombre(String municipio_nombre) {
		this.municipio_nombre = municipio_nombre;
	}
	
	public Municipio(Integer municipio_id, Integer municipio_dpto_id,
			String municipio_nombre) {
		super();
		this.municipio_id = municipio_id;
		this.municipio_dpto_id = municipio_dpto_id;
		this.municipio_nombre = municipio_nombre;
	}
	
	public Municipio() {
		super();
	}
	
}
