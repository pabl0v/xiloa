package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import model.Solicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.USolicitud;


@Component
@Scope("session")
public class SolicitudesManagedBean {
	
	@Autowired
	private IService service;
	
	private List<USolicitud> solicitudI = new ArrayList<USolicitud>();
	
	public List<USolicitud> getSolicitudI() {
		System.out.println("getSolicitudI desde SolicitudesManagedBean");
		solicitudI = service.getUSolicitudes();
		return solicitudI;
	}
	public void setSolicitudI(List<USolicitud> solicitudI) {
		this.solicitudI = solicitudI;
	}
	
	public String nuevaSolicitud(){
		//Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//setSolicitudI(params.get("solicitudI"));			
	return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	
	public void guardar(){
		//service.guardarSolicitud(getSolicitudI());
		
	}
	
}

