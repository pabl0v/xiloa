package support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UCompetencia {
	
	private String grupo;
	private int idCentro;
	private String nombreCentro;
	private String direccion;
	private int idUCompetencia;
	private String nombreUCompetencia;
	private float costo;
	private Date fechaInicio;
	private Date fechaFin;
	private List<String> requisitos = new ArrayList<String>();
	private int disponibilidad;
	
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public int getIdCentro() {
		return idCentro;
	}
	public void setIdCentro(int idCentro) {
		this.idCentro = idCentro;
	}
	public String getNombreCentro() {
		return nombreCentro;
	}
	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getIdUCompetencia() {
		return idUCompetencia;
	}
	public void setIdUCompetencia(int idUCompetencia) {
		this.idUCompetencia = idUCompetencia;
	}
	public String getNombreUCompetencia() {
		return nombreUCompetencia;
	}
	public void setNombreUCompetencia(String nombreUCompetencia) {
		this.nombreUCompetencia = nombreUCompetencia;
	}
	public float getCosto() {
		return costo;
	}
	public void setCosto(float costo) {
		this.costo = costo;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public List<String> getRequisitos() {
		return requisitos;
	}
	public void setRequisitos(List<String> requisitos) {
		this.requisitos = requisitos;
	}
	public int getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
}