package support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Denis Chavez
 * 
 * Esta clase agrupa información proporcionada por el sistema de ofertas del inatec.
 * Esta información será usuada para mostrar las certificaciones disponibles y permitir su creación.
 *
 */
public class UCompetencia {
	
	private Integer ofertaId;
	private Integer estructuraId;
	private String grupo;
	private int idCentro;
	private String nombreCentro;
	private String direccion;
	private int idUCompetencia;
	private String nombreUCompetencia;
	private Float costo;
	private Date fechaInicio;
	private Date fechaFin;
	private List<String> requisitos = new ArrayList<String>();
	private Integer disponibilidad;
	
	public Integer getOfertaId() {
		return ofertaId;
	}
	public void setOfertaId(Integer ofertaId) {
		this.ofertaId = ofertaId;
	}
	public Integer getEstructuraId() {
		return estructuraId;
	}
	public void setEstructuraId(Integer estructuraId) {
		this.estructuraId = estructuraId;
	}
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
	public Float getCosto() {
		return costo;
	}
	public void setCosto(Float costo) {
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
	public Integer getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(Integer disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
}