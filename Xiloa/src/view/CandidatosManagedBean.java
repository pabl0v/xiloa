package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Contacto;
import model.Laboral;
import model.Mantenedor;
import model.Solicitud;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;
import support.Ifp;
import support.FacesUtil;
import util.Global;

@Component
@Scope(value="view")
public class CandidatosManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
		
	@Autowired
	private LoginController login; 
	
	private List<Contacto> listaContactos;
	
	private Contacto selectedContacto;
			
	private Mantenedor estadoInicialSolicitud;
				
	public CandidatosManagedBean() {
		
		super();	
		
		listaContactos = new ArrayList<Contacto>();
						
	}	

	public List<Contacto> getListaContactos() {
		return listaContactos;
	}

	public void setListaContactos(List<Contacto> listaContactos) {
		this.listaContactos = listaContactos;
	}

	public Contacto getSelectedContacto() {
		return selectedContacto;
	}

	public void setSelectedContacto(Contacto selectedContacto) {
		this.selectedContacto = selectedContacto;
	}

	public Mantenedor getEstadoInicialSolicitud() {
		return estadoInicialSolicitud;
	}

	public void setEstadoInicialSolicitud(Mantenedor estadoInicialSolicitud) {
		this.estadoInicialSolicitud = estadoInicialSolicitud;
	}
				
   //Llenado de Centro
	@PostConstruct
	private void initBeanDBSolicitudes(){
		Integer entidadContacto = login.getEntidadUsuario();
		
		List<Ifp> lista = service.getIfpByInatec(entidadContacto);
		
		//Asigna permisos segun contacto conectado
		
		Object [] objs =  null;		
		objs = new Object [] {entidadContacto};
		
		this.listaContactos = service.getContactosByParam("Contacto.findAllPortafolio", objs);
		
		FacesUtil.setParamBySession("candidato", null);
		FacesUtil.setParamBySession("dbSolicitudesBean", null);
		
	}
	
	public String consultarExpediente(){
		String urlDestino = null;
		if (this.selectedContacto == null)
			FacesUtil.getMensaje("Mensaje SCCL ", "Debe seleccionar un candidato. Favor revisar...", true);
		else{
			FacesUtil.setParamBySession("candidato", this.selectedContacto);
			urlDestino = "/modulos/solicitudes/expediente?faces-redirect=true";
		}
			
		return urlDestino;
	}
		
	
	public void onRowSelectDtContactos(SelectEvent event) {
		this.setSelectedContacto((Contacto) event.getObject());
    }
  
    public void onRowUnSelectDtContactos(UnselectEvent event) {
    	this.setSelectedContacto(null);
    }
	
}
