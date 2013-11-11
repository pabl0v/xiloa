package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import model.Actividad;
import model.Certificacion;
import model.Contacto;
import model.Mantenedor;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;  

import service.IService;

@Component
@Scope(value="session")
public class CertificacionManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IService service;
	
	private Certificacion certificacion;
	private List<Contacto> contactos;
	private Contacto[] selectedContactos;
	private Map<Integer, Mantenedor> catalogoEstatusCertificacion;
	private Integer selectedEstatusCertificacion;
	private Actividad selectedActividad;
	private Usuario usuario;
	
	public CertificacionManagedBean(){
		super();
		certificacion = new Certificacion();
		contactos = new ArrayList<Contacto>();
		selectedActividad = new Actividad();
		catalogoEstatusCertificacion = new HashMap<Integer, Mantenedor>();
	}
	
	@PostConstruct
	private void init(){
		usuario = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());
		contactos = service.getContactosInatec();
		catalogoEstatusCertificacion = service.getMapMantenedoresByTipo("3");
	}
	
	public Certificacion getCertificacion(){
		return this.certificacion;
	}
	
	public void setCertificacion(Certificacion certificacion){
		this.certificacion = certificacion;
	}
	
	public List<Contacto> getContactos() {
		return contactos;
	}
	
	public Contacto[] getSelectedContactos() {
		return selectedContactos;
	}
	
	public void setSelectedContactos(Contacto[] selectedContactos) {
		this.selectedContactos = selectedContactos;
		this.certificacion.setInvolucrados(selectedContactos);
	}
		
	public String cancelar(){
		certificacion = new Certificacion();
		return "/modulos/planificacion/planificacion?faces-redirect=true";
	}
	
	public String guardarEdicion(){
		
		certificacion.setFechaRegistro(new Date());
		certificacion.setFechaActualiza(new Date());
		certificacion.setActualiza(usuario);
		certificacion.setReferencial("N/D");
		certificacion = (Certificacion) service.guardar(certificacion);
		certificacion = new Certificacion();
		return "/modulos/planificacion/planificacion?faces-redirect=true";
	}
	
	public String guardar(){
		
		certificacion.setFechaRegistro(new Date());
		certificacion.setCreador(usuario);
		certificacion.setProgramador(usuario);
		certificacion.setReferencial("N/D");
		certificacion = (Certificacion) service.guardar(certificacion);
		certificacion = new Certificacion();
		return "/modulos/planificacion/planificacion?faces-redirect=true";
	}
	
	private Usuario getUsuario(){
		return this.usuario;
	}
	
	public void agregarActividad(Actividad actividad){
		actividad.setCreador(getUsuario());
		actividad.setFechaRegistro(new Date());
		Mantenedor estado = service.getMapMantenedoresByTipo("4").get(10);		//estatus pendiente
		actividad.setEstado(estado);
		Integer indice;
		if(certificacion.getActividades().isEmpty())
			indice = 0;
		else
			indice = certificacion.getActividades().size();
		actividad.setIndice(indice);
		actividad.setCertificacion(certificacion);
		actividad = (Actividad)service.guardar(actividad);
		certificacion.addActividad(actividad);
	}
		
	public String editarCertificacion(Certificacion certificacion){
		this.certificacion = certificacion;
		this.selectedEstatusCertificacion = certificacion.getEstatus().getId();
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}
	
	public Integer getSelectedEstatusCertificacion() {
		return selectedEstatusCertificacion;
	}

	public void setSelectedEstatusCertificacion(Integer selectedEstatusCertificacion) {
		this.selectedEstatusCertificacion = selectedEstatusCertificacion;
		this.certificacion.setEstatus(this.catalogoEstatusCertificacion.get(selectedEstatusCertificacion));
	}

	public List<Mantenedor> getCatalogoEstatusCertificacion() {
		return new ArrayList<Mantenedor>(this.catalogoEstatusCertificacion.values());
	}
	
	public Actividad getSelectedActividad(){
		return this.selectedActividad;
	}
	
	public void setSelectedActividad(Actividad actividad){
		this.selectedActividad = actividad;
	}
}