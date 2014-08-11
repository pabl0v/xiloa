package view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Contacto;
import model.Requisito;
import model.Solicitud;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;
import support.FacesUtil;
import support.Ifp;

@Component
@Scope(value = "view")
public class CertificacionesManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	@Autowired
	private LoginController login;
	private List<Certificacion> certificaciones;
	private Certificacion selectedCertificacion;
	private Contacto solicitante;
	private Solicitud solicitud;

	public CertificacionesManagedBean() {
		super();
		solicitante = new Contacto();
		solicitud = new Solicitud();
	}
	
	@PostConstruct
	private void init() {		
		certificaciones = service.getCertificaciones(login.getEntidadUsuario());
	}
	
	public List<Certificacion> getCertificaciones(){
		return certificaciones;
	}
	
	public Certificacion getSelectedCertificacion(){
		return selectedCertificacion;
	}
	
	public void setSelectedCertificacion(Certificacion certificacion){
		this.selectedCertificacion = certificacion;
	}
	
	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Contacto getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Contacto solicitante) {
		this.solicitante = solicitante;
	}
	
	public List<Requisito> getRequisitos(Long certificacionId){
		return service.getRequisitos(certificacionId);
	}

	public void onRowSelect(SelectEvent event) {
		setSelectedCertificacion((Certificacion) event.getObject());
    }
  
    public void onRowUnselect(UnselectEvent event) {
    }
    
	public SelectItem[] getListaCentros(){
		List<Ifp> centros = service.getIfpByInatec(login.getEntidadUsuario());
	
		SelectItem[] opciones = new SelectItem[centros.size()+1];
		opciones[0] = new SelectItem("","Seleccione");
		for(int i=0; i<centros.size(); i++)
			opciones[i+1] = new SelectItem(centros.get(i).getIfpNombre(),centros.get(i).getIfpNombre());
		return opciones;
	}
    
    public void nuevaSolicitud(){
		solicitante = new Contacto();
		solicitud = new Solicitud();
    }
	
	public void registrarSolicitud(Solicitud solicitud, Contacto solicitante){
		
		//validar si tiene solicitudes pendientes
		
		FacesUtil.getMensaje("SCCL - Mensaje: ", "El candidato tiene una solicitud pendiente.", true);
		
		solicitud.setCertificacion(selectedCertificacion);
		service.registrarSolicitud(solicitud, solicitante);
	}
}