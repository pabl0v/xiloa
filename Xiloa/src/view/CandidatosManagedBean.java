package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import model.Contacto;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;
import support.FacesUtil;

@Component
@Scope(value="view")
public class CandidatosManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	@Autowired
	private LoginController login; 
	private List<Contacto> candidatos;
	private Contacto selectedCandidato;
				
	public CandidatosManagedBean() {
		super();	
		candidatos = new ArrayList<Contacto>();	
	}
	
	@PostConstruct
	private void init(){		
		candidatos = service.getContactosPortafolio(login.getContacto().getId());
	}

	public List<Contacto> getCandidatos() {
		return candidatos;
	}

	public Contacto getSelectedCandidato() {
		return selectedCandidato;
	}

	public void setSelectedCandidato(Contacto selectedContacto) {
		this.selectedCandidato = selectedContacto;
	}
	
	public String consultarExpediente(Contacto candidato){
		
		String urlDestino = null;
		selectedCandidato = candidato;
		
		if (selectedCandidato == null)
			FacesUtil.getMensaje("Mensaje SCCL ", "Debe seleccionar un candidato...", true);
		else{
			FacesUtil.setParamBySession("candidatoId", selectedCandidato.getId());
			urlDestino = "/modulos/solicitudes/expediente?faces-redirect=true";
		}
		return urlDestino;
	}

	public void onRowSelectCandidato(SelectEvent event) {
		this.setSelectedCandidato((Contacto) event.getObject());
    }
  
    public void onRowUnSelectCandidato(UnselectEvent event) {
    }
}