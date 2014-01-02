package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import model.Actividad;
import model.Bitacora;
import model.Certificacion;
import model.Mantenedor;

import org.primefaces.event.SelectEvent;
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
@Scope("request")
public class EjecucionesManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	
	@Autowired
	private LoginController controller;

	private List<Actividad> actividades;
	private List<Actividad> filteredActividades;
	private Actividad selectedActividad;
	private Bitacora bitacora;
	private Map<Integer,Mantenedor> catalogoTiposActividad;
	private Integer selectedTipoActividad;
	private Map<Integer,Mantenedor> catalogoEstatusActividad;
	private Integer selectedEstatusActividad;
		
	public EjecucionesManagedBean(){
		super();
		selectedActividad = new Actividad();
		bitacora = new Bitacora();
		actividades = new ArrayList<Actividad>(); 
	}

	@PostConstruct
	private void init(){
		actividades = service.getActividadesByEntidadId(controller.getEntidadUsuario());
		catalogoTiposActividad = service.getMapMantenedoresByTipo("1");
		catalogoEstatusActividad = service.getMapMantenedoresByTipo("4");
	}

	public void reset(){
		this.bitacora = new Bitacora();
	}

	public List<Actividad> getActividades(){
		this.actividades = service.getActividadesByEntidadId(controller.getEntidadUsuario());
		return this.actividades;
	}	
	
	public Actividad getSelectedActividad(){
		return this.selectedActividad;
	}
	
	public void setSelectedActividad(Actividad actividad){
		this.selectedActividad = actividad;
	}
	
	public List<Actividad> getFilteredActividades() {
		return filteredActividades;
	}

	public void setFilteredActividades(List<Actividad> filteredActividades) {
		this.filteredActividades = filteredActividades;
	}

	public List<Bitacora> getBitacoras() {
		return service.getBitacoras(selectedActividad.getId());
	}
	
	public Bitacora getBitacora() {
		return bitacora;
	}
	
	public void agregarBitacora(){
		bitacora = new Bitacora();
	}

	public void guardarBitacora(Bitacora bitacora) {
		bitacora.setActividad(selectedActividad);
		bitacora.setFechaRegistro(new Date());
		bitacora.setUsuario(controller.getContacto());
		service.guardar(bitacora);
		this.bitacora = new Bitacora();
	}
	
	public List<Mantenedor> getCatalogoTiposActividad() {
		return new ArrayList<Mantenedor>(catalogoTiposActividad.values());
	}
	
	public SelectItem[] getTiposActividad(){

		List<Mantenedor> actividadesList = getCatalogoTiposActividad();
		SelectItem opciones[] = new SelectItem[actividadesList.size() + 1];

		opciones[0] = new SelectItem("", "Seleccione");
		for(int i = 0; i<actividadesList.size(); i++)
			opciones[i + 1] = new SelectItem(actividadesList.get(i).getValor(), actividadesList.get(i).getValor());
		return opciones;
	}
	
	public SelectItem[] getEstatusActividad(){

		List<Mantenedor> estatusList = getCatalogoEstatusActividad();
		SelectItem opciones[] = new SelectItem[catalogoEstatusActividad.size() + 1];

		opciones[0] = new SelectItem("", "Seleccione");
		for(int i = 0; i<estatusList.size(); i++)
			opciones[i + 1] = new SelectItem(estatusList.get(i).getValor(), estatusList.get(i).getValor());
		return opciones;
	}
	
	public SelectItem[] getNombreCertificacion(){

		List<Certificacion> certificaciones = service.getCertificaciones(controller.getEntidadUsuario());
		SelectItem opciones[] = new SelectItem[certificaciones.size() + 1];

		opciones[0] = new SelectItem("", "Seleccione");
		for(int i = 0; i<certificaciones.size(); i++){
			opciones[i + 1] = new SelectItem(certificaciones.get(i).getNombre(), certificaciones.get(i).getNombre());
		}
		return opciones;
	}

	public Integer getSelectedTipoActividad() {
		return selectedTipoActividad;
	}

	public void setSelectedTipoActividad(Integer selectedTipoActividad) {
		this.selectedTipoActividad = selectedTipoActividad;
	}

	public List<Mantenedor> getCatalogoEstatusActividad() {
		return new ArrayList<Mantenedor>(catalogoEstatusActividad.values());
	}
	
	public Integer getSelectedEstatusActividad() {
		return selectedEstatusActividad;
	}

	public void setSelectedEstatusActividad(Integer selectedEstatusActividad) {
		this.selectedEstatusActividad = selectedEstatusActividad;
	}

	public void selectedActividadListener(SelectEvent event){
		this.selectedActividad = (Actividad)event.getObject();
	}

	public String editarActividad(Actividad actividad){
		this.selectedActividad = actividad;
		System.out.println("Editar actividad: "+actividad.getNombre());
		return "/modulos/planificacion/edicion_actividad?faces-redirect=true";
	}	
}