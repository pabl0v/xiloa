package view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.Involucrado;
import support.Planificacion;
import support.UCompetencia;

@Component
@Scope("session")
public class PlanificacionManagedBean {
	
	@Autowired
	private IService service;
	private List<Planificacion> planificaciones = new ArrayList<Planificacion>();
	private List<UCompetencia> competencias = new ArrayList<UCompetencia>();
	private List<Involucrado> contactos = new ArrayList<Involucrado>();
	private UCompetencia selectedCompetencia;
	private Involucrado[] selectedContactos;
	private Planificacion selectedPlanificacion;
		
	public List<Planificacion> getPlanificaciones(){
		System.out.println("getPlanificaciones method called...");
		planificaciones = service.getPlanificacion();
		return planificaciones;
	}
	
	public List<UCompetencia> getCompetencias(){
		competencias = service.getUcompetenciaSinPlanificar();
		return competencias;
	}

	public UCompetencia getSelectedCompetencia() {
		return selectedCompetencia;
	}

	public void setSelectedCompetencia(UCompetencia selectedCompetencia) {
		this.selectedCompetencia = selectedCompetencia;
	}

	public List<Involucrado> getContactos() {
		contactos = service.getContactos();
		return contactos;
	}

	public Involucrado[] getSelectedContactos() {
		return selectedContactos;
	}

	public void setSelectedContactos(Involucrado[] selectedContactos) {
		this.selectedContactos = selectedContactos;
	}

	public Planificacion getSelectedPlanificacion() {
		return selectedPlanificacion;
	}

	public void setSelectedPlanificacion(Planificacion selectedPlanificacion) {
		this.selectedPlanificacion = selectedPlanificacion;
	}
}