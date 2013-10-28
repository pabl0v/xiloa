package view;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.Planificacion;
import support.UCompetencia;

@Component
@Scope(value="session")
public class PlanificacionManagedBean {
	
	@Autowired
	private IService service;
	private List<Planificacion> planificaciones = new ArrayList<Planificacion>();
	private List<UCompetencia> competencias = new ArrayList<UCompetencia>();
	private UCompetencia selectedCompetencia;
	private Planificacion selectedPlanificacion;
		
	public List<Planificacion> getPlanificaciones(){
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
	
	public void onRowSelectCompetencia(SelectEvent event) {  
    }

	public void setSelectedCompetencia(UCompetencia selectedCompetencia) {
		this.selectedCompetencia = selectedCompetencia;
	}

	public Planificacion getSelectedPlanificacion() {
		return selectedPlanificacion;
	}

	public void setSelectedPlanificacion(Planificacion selectedPlanificacion) {
		this.selectedPlanificacion = selectedPlanificacion;
	}
}