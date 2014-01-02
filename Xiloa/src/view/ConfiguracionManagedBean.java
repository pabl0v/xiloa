package view;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import model.Mantenedor;
import model.Rol;
import model.Usuario;

/**
 * 
 * @author Denis Chavez
 * 
 * JSF ManagedBean asociado a la interfaz  configuracion.xhtml
 * Esta clase maneja lo que se muestra en la página, los eventos relacionados 
 * e interacciona con la capa de servicio (paquete service)
 */

@Component
@Scope(value="view")
public class ConfiguracionManagedBean {
	
	private List<Mantenedor> mantenedores;
	private Mantenedor selectedMantenedor;
	private Mantenedor mantenedor;
	
	@Autowired
	private IService service;
	
	@PostConstruct
	public void init(){
		this.mantenedores = service.getMantenedores();
		this.mantenedor = new Mantenedor();
	}

	public List<Mantenedor> getMantenedores() {
		return mantenedores;
	}

	public Mantenedor getSelectedMantenedor() {
		return selectedMantenedor;
	}

	public void setSelectedMantenedor(Mantenedor selectedMantenedor) {
		this.selectedMantenedor = selectedMantenedor;
	}
	
	public void nuevoMantenedor(){
		this.mantenedor = new Mantenedor();
	}

	public void guardarMantenedor(Mantenedor mantenedor){
		this.selectedMantenedor = (Mantenedor) service.guardar(mantenedor);
		mantenedores = service.getMantenedores();
	}
	
	public void editarMantenedor(Mantenedor mantenedor){
		this.mantenedor = mantenedor;
	}
	
	public Mantenedor getMantenedor(){
		return mantenedor;
	}
	
	public void onRowSelect(SelectEvent event) {
		setSelectedMantenedor((Mantenedor) event.getObject());
    }
	
    public void onRowUnselect(UnselectEvent event) {
    }
	
	public List<Usuario> getUsuarios(){
		return service.getUsuarios();
	}
	
	public List<Rol> getRoles(){
		return service.getRoles();
	}
}