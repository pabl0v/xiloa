package view;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import model.Mantenedor;

@Component
@Scope(value="request")
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
		Mantenedor m = (Mantenedor) service.guardar(mantenedor);
		this.mantenedores.add(m);
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
}