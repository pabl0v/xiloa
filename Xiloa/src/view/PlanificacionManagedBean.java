package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import model.Certificacion;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.UCompetencia;

@Component
@Scope(value="request")
public class PlanificacionManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IService service;
	private List<Certificacion> certificaciones;
	private List<UCompetencia> competencias;
	private UCompetencia selectedCompetencia;
	private Certificacion selectedCertificacion;
	
	public PlanificacionManagedBean(){
		super();
		certificaciones = new ArrayList<Certificacion>();
		competencias = new ArrayList<UCompetencia>();
	}
	
	@PostConstruct
	private void init(){
		competencias = service.getUcompetenciaSinPlanificar();
		certificaciones = service.getCertificaciones();
	}
	
	public List<Certificacion> getCertificaciones(){
		return certificaciones;
	}
	
	public List<UCompetencia> getCompetencias(){
		return competencias;
	}
		
	public Certificacion getSelectedCertificacion() {
		return selectedCertificacion;
	}

	public void setSelectedCertificacion(Certificacion selectedCertificacion) {
		this.selectedCertificacion = selectedCertificacion;
	}

	public UCompetencia getSelectedCompetencia() {
		return selectedCompetencia;
	}
	
	public void setSelectedCompetencia(UCompetencia selectedCompetencia) {
		this.selectedCompetencia = selectedCompetencia;
	}
	
	public void onRowSelectCompetencia(SelectEvent event) {  
    }
}