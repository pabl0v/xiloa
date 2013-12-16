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

@Component
@Scope(value="view")
public class ConfiguracionManagedBean {
	
	private List<Mantenedor> mantenedores;
	private Mantenedor selectedMantenedor;
	private Mantenedor mantenedor;
	private boolean add = false;
	
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
		this.add = true;
	}

	public void guardarMantenedor(Mantenedor mantenedor){
		this.mantenedor = (Mantenedor) service.guardar(this.mantenedor);
		if(add)
			this.mantenedores.add(this.mantenedor);
		add = false;
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