package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import model.Actividad;
import model.Bitacora;
import model.Contacto;
import model.Mantenedor;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import service.IService;

@Component
@Scope("request")
public class EjecucionesManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	@Autowired
	private UtilitariosManagedBean utilitarios;

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
		actividades = service.getActividades(null);
		catalogoTiposActividad = service.getMapMantenedoresByTipo("1");
		catalogoEstatusActividad = service.getMapMantenedoresByTipo("4");
	}

	public void reset(){
		this.bitacora = new Bitacora();
	}

	public List<Actividad> getActividades(){
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
		//bitacora.setUsuario(service.getContactoByLogin("admin"));		//actualizar
		bitacora.setUsuario((Contacto)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		service.guardar(bitacora);
		this.bitacora = new Bitacora();
	}
	
	public List<Mantenedor> getCatalogoTiposActividad() {
		return new ArrayList<Mantenedor>(catalogoTiposActividad.values());
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