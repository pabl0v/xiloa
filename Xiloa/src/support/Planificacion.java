package support;

public class Planificacion {
	
	private int idCentro;
	private String nombreCentro;
	private String unidadCompetencia;
	private int disponibilidad;
	private int solicitudes;
	private String coordina;
	private String evalua;
	private String registrado;
	private String estatus;
	
	public int getIdCentro() {
		return idCentro;
	}
	public void setIdCentro(int i) {
		this.idCentro = i;
	}
	public String getNombreCentro() {
		return nombreCentro;
	}
	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}
	public String getUnidadCompetencia() {
		return unidadCompetencia;
	}
	public void setUnidadCompetencia(String unidadCompetencia) {
		this.unidadCompetencia = unidadCompetencia;
	}
	public int getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public int getSolicitudes() {
		return solicitudes;
	}
	public void setSolicitudes(int solicitudes) {
		this.solicitudes = solicitudes;
	}
	public String getCoordina() {
		return coordina;
	}
	public void setCoordina(String coordina) {
		this.coordina = coordina;
	}
	public String getEvalua() {
		return evalua;
	}
	public void setEvalua(String evalua) {
		this.evalua = evalua;
	}
	public String getRegistrado() {
		return registrado;
	}
	public void setRegistrado(String registrado) {
		this.registrado = registrado;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
}