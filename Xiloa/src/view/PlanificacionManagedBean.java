package view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



import service.IService;
import support.Planificacion;
import support.UCompetencia;

@Component
@Scope("session")
public class PlanificacionManagedBean {
	
	@Autowired
	private IService service;
	private List<Planificacion> planificaciones = new ArrayList<Planificacion>();
	private List<UCompetencia> competencias = new ArrayList<UCompetencia>();
	private UCompetencia selectedCompetencia;
		
	public List<Planificacion> getPlanificaciones(){
		System.out.println("getPlanificaciones method called...");
		planificaciones = service.getPlanificacion();
		return planificaciones;
	}
	
	public List<UCompetencia> getCompetencias(){
		System.out.println("getCompetencias method called...");
		competencias = service.getUcompetenciaSinPlanificar();
		return competencias;
	}

	public UCompetencia getSelectedCompetencia() {
		return selectedCompetencia;
	}

	public void setSelectedCompetencia(UCompetencia selectedCompetencia) {
		this.selectedCompetencia = selectedCompetencia;
	}
}