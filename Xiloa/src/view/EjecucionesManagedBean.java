package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import model.Actividad;
import model.Bitacora;
import model.Certificacion;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;

/**
 * 
 * @author Denis Chavez
 * 
 * JSF ManagedBean asociado a la interfaz  ejecuciones.xhtml
 * Esta clase maneja lo que se muestra en la página, los eventos relacionados 
 * e interacciona con la capa de servicio (paquete service)
 */

@Component
@Scope("view")
public class EjecucionesManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	
	@Autowired
	private LoginController controller;

	private List<Certificacion> certificaciones;
	private Certificacion selectedCertificacion;
	private List<Actividad> actividades;
	private Actividad selectedActividad;
	private List<Bitacora> bitacoras;
	private Bitacora selectedBitacora;
		
	public EjecucionesManagedBean(){
		super();
		certificaciones = new ArrayList<Certificacion>();
		selectedCertificacion = new Certificacion();
		actividades = new ArrayList<Actividad>();
		selectedActividad = new Actividad();
		bitacoras = new ArrayList<Bitacora>();
		selectedBitacora = new Bitacora();
	}

	@PostConstruct
	private void init(){
		certificaciones = service.getCertificaciones(controller.getEntidadUsuario());
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
	
	public void onRowSelectCertificacion(SelectEvent event) {
		setSelectedCertificacion((Certificacion) event.getObject());
		setActividades(service.getActividades(selectedCertificacion.getId()));
    }
  
    public void onRowUnselectCertificacion(UnselectEvent event) {
    }
    
    public void setActividades(List<Actividad> actividades){
    	this.actividades = actividades;
    }
    
    public List<Actividad> getActividades(){
    	return actividades;
    }
	
	public Actividad getSelectedActividad(){
		return this.selectedActividad;
	}
	
	public void setSelectedActividad(Actividad actividad){
		this.selectedActividad = actividad;
	}
	
	public void onRowSelectActividad(SelectEvent event) {
		setSelectedActividad((Actividad) event.getObject());
		setBitacoras(service.getBitacoras(selectedActividad.getId()));
    }
  
    public void onRowUnselectActividad(UnselectEvent event) {
    }
    
    public List<Bitacora> getBitacoras(){
    	return bitacoras;
    }
    
    public void setBitacoras(List<Bitacora> bitacoras){
    	this.bitacoras = bitacoras;
    }
		
	public Bitacora getSelectedBitacora() {
		return selectedBitacora;
	}
	
	public void setSelectedBitacora(Bitacora bitacora){
		this.selectedBitacora = bitacora;
	}
	
	public void onRowSelectBitacora(SelectEvent event) {
		setSelectedBitacora((Bitacora) event.getObject());
    }
	
	public void agregarBitacora(){
		selectedBitacora = new Bitacora();
	}

	public void guardarBitacora(Bitacora bitacora) {
		bitacora.setActividad(selectedActividad);
		bitacora.setFechaRegistro(new Date());
		bitacora.setUsuario(controller.getContacto());
		service.guardar(bitacora);
		this.selectedBitacora = new Bitacora();
		setBitacoras(service.getBitacoras(selectedActividad.getId()));
	}
	
	public void completarActividad(Actividad actividad){
		actividad.setEstado(service.getMantenedorById(15));
		service.guardar(actividad);
	}
}