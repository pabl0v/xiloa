package view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import service.IService;
import support.Planificacion;

@Component
@Scope("session")
public class PlanificacionManagedBean {
	
	@Autowired
	private IService service;
	private List<Planificacion> planificaciones = new ArrayList<Planificacion>();
		
	public List<Planificacion> getPlanificaciones(){
		planificaciones = service.getPlanificacion();
		return planificaciones;
	}
}