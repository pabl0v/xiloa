package support;

import java.util.Date;

public class USolicitud {
	private String centroEvaluador;
	private String nombreCandidato;
	private String nombreCertificacion;
    private String nombreEvaluador;
    private int estatus;
    private Date fechaRegistro;
    
    
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getCentroEvaluador() {
		return centroEvaluador;
	}
	public void setCentroEvaluador(String centroEvaluador) {
		this.centroEvaluador = centroEvaluador;
	}
	public String getNombreCandidato() {
		return nombreCandidato;
	}
	public void setNombreCandidato(String nombreCandidato) {
		this.nombreCandidato = nombreCandidato;
	}
	public String getNombreCertificacion() {
		return nombreCertificacion;
	}
	public void setNombreCertificacion(String nombreCertificacion) {
		this.nombreCertificacion = nombreCertificacion;
	}
	public String getNombreEvaluador() {
		return nombreEvaluador;
	}
	public void setNombreEvaluador(String nombreEvaluador) {
		this.nombreEvaluador = nombreEvaluador;
	}
	public USolicitud(String centroEvaluador, String nombreCandidato,
			String nombreCertificacion, String nombreEvaluador, int estatus,
			Date fechaRegistro) {
		super();
		this.centroEvaluador = centroEvaluador;
		this.nombreCandidato = nombreCandidato;
		this.nombreCertificacion = nombreCertificacion;
		this.nombreEvaluador = nombreEvaluador;
		this.estatus = estatus;
		this.fechaRegistro = fechaRegistro;
	}
	
	
	    
}

