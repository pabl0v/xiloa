package support;

import java.util.Date;
import java.util.List;

public class Planificacion {	

	private int idPlanificacion;
	private int idCentro;
	private String nombreCentro;
	private String unidadCompetencia;
	private String descripcion;
	private int disponibilidad;
	private int solicitudes;
	private Date divulgacionInicia;
	private Date inscripcionFinaliza;
	private Date convocatoriaInicia;
	private Date evaluacionInicia;
	private List<Involucrado> involucrados;
	private String coordina;
	private String evalua;
	private String registrado;
	private String estatus;
	
	public int getIdPlanificacion() {
		return idPlanificacion;
	}
	public void setIdPlanificacion(int idPlanificacion) {
		this.idPlanificacion = idPlanificacion;
	}
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	public Date getDivulgacionInicia() {
		return divulgacionInicia;
	}
	public void setDivulgacionInicia(Date divulgacionInicia) {
		this.divulgacionInicia = divulgacionInicia;
	}
	public Date getInscripcionFinaliza() {
		return inscripcionFinaliza;
	}
	public void setInscripcionFinaliza(Date inscripcionFinaliza) {
		this.inscripcionFinaliza = inscripcionFinaliza;
	}
	public Date getConvocatoriaInicia() {
		return convocatoriaInicia;
	}
	public void setConvocatoriaInicia(Date convocatoriaInicia) {
		this.convocatoriaInicia = convocatoriaInicia;
	}
	public Date getEvaluacionInicia() {
		return evaluacionInicia;
	}
	public void setEvaluacionInicia(Date evaluacionInicia) {
		this.evaluacionInicia = evaluacionInicia;
	}
	public List<Involucrado> getInvolucrados() {
		return involucrados;
	}
	public void setInvolucrados(List<Involucrado> involucrados) {
		this.involucrados = involucrados;
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
	public Planificacion(){
		super();
	}
	public Planificacion(	int idPlanificacion, 
							int idCentro,
							String nombreCentro, 
							String unidadCompetencia, 
							String descripcion,
							int disponibilidad, 
							int solicitudes, 
							Date divulgacionInicia,
							Date inscripcionFinaliza, 
							Date convocatoriaInicia,
							Date evaluacionInicia, 
							List<Involucrado> involucrados,
							String coordina, 
							String evalua, 
							String registrado, 
							String estatus) {
		super();
		this.idPlanificacion = idPlanificacion;
		this.idCentro = idCentro;
		this.nombreCentro = nombreCentro;
		this.unidadCompetencia = unidadCompetencia;
		this.descripcion = descripcion;
		this.disponibilidad = disponibilidad;
		this.solicitudes = solicitudes;
		this.divulgacionInicia = divulgacionInicia;
		this.inscripcionFinaliza = inscripcionFinaliza;
		this.convocatoriaInicia = convocatoriaInicia;
		this.evaluacionInicia = evaluacionInicia;
		this.involucrados = involucrados;
		this.coordina = coordina;
		this.evalua = evalua;
		this.registrado = registrado;
		this.estatus = estatus;
	}
}