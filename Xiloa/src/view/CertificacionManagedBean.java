package view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.Involucrado;

@Component
@Scope("request")
public class CertificacionManagedBean {
	
	@Autowired
	private IService service;
	private String descripcionCertificacion;
	private List<Involucrado> contactos = new ArrayList<Involucrado>();
	private Involucrado[] selectedContactos;
	private List<Involucrado> involucrados = new ArrayList<Involucrado>();
	private Date fechaIniciaDivulgacion;
	private Date fechaFinalizaInscripcion;
	private Date fechaIniciaConvocatoria;
	private Date fechaIniciaEvaluacion;
	private String nombreCentroEvaluador;
	private String direccionCentroEvaluador;
	private String estatus;
	
	public IService getService() {
		return service;
	}
	public void setService(IService service) {
		this.service = service;
	}
	public String getDescripcionCertificacion() {
		return descripcionCertificacion;
	}
	public void setDescripcionCertificacion(String descripcionCertificacion) {
		this.descripcionCertificacion = descripcionCertificacion;
	}
	public List<Involucrado> getContactos() {
		return contactos;
	}
	public void setContactos(List<Involucrado> contactos) {
		this.contactos = contactos;
	}
	public Involucrado[] getSelectedContactos() {
		return selectedContactos;
	}
	public void setSelectedContactos(Involucrado[] selectedContactos) {
		this.selectedContactos = selectedContactos;
	}
	public List<Involucrado> getInvolucrados() {
		return involucrados;
	}
	public void setInvolucrados(List<Involucrado> involucrados) {
		this.involucrados = involucrados;
	}
	public Date getFechaIniciaDivulgacion() {
		return fechaIniciaDivulgacion;
	}
	public void setFechaIniciaDivulgacion(Date fechaIniciaDivulgacion) {
		this.fechaIniciaDivulgacion = fechaIniciaDivulgacion;
	}
	public Date getFechaFinalizaInscripcion() {
		return fechaFinalizaInscripcion;
	}
	public void setFechaFinalizaInscripcion(Date fechaFinalizaInscripcion) {
		this.fechaFinalizaInscripcion = fechaFinalizaInscripcion;
	}
	public Date getFechaIniciaConvocatoria() {
		return fechaIniciaConvocatoria;
	}
	public void setFechaIniciaConvocatoria(Date fechaIniciaConvocatoria) {
		this.fechaIniciaConvocatoria = fechaIniciaConvocatoria;
	}
	public Date getFechaIniciaEvaluacion() {
		return fechaIniciaEvaluacion;
	}
	public void setFechaIniciaEvaluacion(Date fechaIniciaEvaluacion) {
		this.fechaIniciaEvaluacion = fechaIniciaEvaluacion;
	}
	public String getNombreCentroEvaluador() {
		return nombreCentroEvaluador;
	}
	public void setNombreCentroEvaluador(String nombreCentroEvaluador) {
		this.nombreCentroEvaluador = nombreCentroEvaluador;
	}
	public String getDireccionCentroEvaluador() {
		return direccionCentroEvaluador;
	}
	public void setDireccionCentroEvaluador(String direccionCentroEvaluador) {
		this.direccionCentroEvaluador = direccionCentroEvaluador;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
}