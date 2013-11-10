package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import model.Actividad;
import model.Certificacion;
import model.Contacto;
import model.Mantenedor;
import model.Unidad;
import model.Usuario;

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
	private Map<Integer,Mantenedor> actividades;
	
	public PlanificacionManagedBean(){
		super();
		certificaciones = new ArrayList<Certificacion>();
		competencias = new ArrayList<UCompetencia>();
		actividades = new HashMap<Integer,Mantenedor>();
	}
	
	@PostConstruct
	private void init(){
		competencias = service.getUcompetenciaSinPlanificar();
		certificaciones = service.getCertificaciones();
		actividades = service.getMapMantenedoresByTipo("1");
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
	
	public void nuevaCertificacion(UCompetencia competencia){
		
		Mantenedor estatus = service.getMapMantenedoresByTipo("3").get(7);		//estatus pendiente
		Usuario creador = service.getUsuarioLocal("admin");						//actualizar
		
		Certificacion certificacion = new Certificacion();
		certificacion.setNombre(competencia.getNombreUCompetencia());
		certificacion.setDescripcion(competencia.getNombreUCompetencia());
		certificacion.setDisponibilidad(competencia.getDisponibilidad());
		certificacion.setCosto(competencia.getCosto());
		certificacion.setCreador(creador);
		certificacion.setFechaRegistro(new Date());
		certificacion.setIfpId(competencia.getIdCentro());
		certificacion.setIfpNombre(competencia.getNombreCentro());
		certificacion.setIfpDireccion(competencia.getDireccion());
		certificacion.setActividades(new ArrayList<Actividad>());
		certificacion.setUnidades(new HashSet<Unidad>());
		certificacion.setInvolucrados(new Contacto[] {});
		
		/*
		certificacion.setDivulgacionInicia(new Date());
		certificacion.setDivulgacionFinaliza(new Date());
		certificacion.setInscripcionFinaliza(new Date());
		certificacion.setEvaluacionInicia(new Date());
		certificacion.setConvocatoriaInicia(new Date());
		*/
		Mantenedor estado = service.getMapMantenedoresByTipo("4").get(10);		//estatus pendiente
		
		Actividad divulgacion = new Actividad(actividades.get(1),"Divulgacion","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado);
		Actividad verificacion = new Actividad(actividades.get(2),"Verificacion","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado);
		Actividad evaluacion = new Actividad(actividades.get(3),"Evaluacion","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado);
		Actividad convocatoria = new Actividad(actividades.get(4),"Convocatoria","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado);
		
		certificacion.addActividad(divulgacion);
		certificacion.addActividad(verificacion);
		certificacion.addActividad(evaluacion);
		certificacion.addActividad(convocatoria);
		
		certificacion.setEstatus(estatus);
		
		certificaciones.add(0, (Certificacion) service.guardar(certificacion));
	}
	
	public void onRowSelectCompetencia(SelectEvent event) {  
    }
}