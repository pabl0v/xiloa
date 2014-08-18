package support;

public class Pais {
	
	private String codigo;
	private String nombre;

	public Pais(){
		super();
	}
	
	public Pais(String codigo, String nombre){
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String pais) {
		this.nombre = pais;
	}
}