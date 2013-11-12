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
import model.Requisito;
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
		certificacion.setCursoId(competencia.getIdUCompetencia());
		certificacion.setNombre(competencia.getNombreUCompetencia());
		certificacion.setDescripcion(competencia.getNombreUCompetencia());
		certificacion.setDisponibilidad(competencia.getDisponibilidad());
		certificacion.setCosto(competencia.getCosto());
		certificacion.setCreador(creador);
		certificacion.setFechaRegistro(new Date());
		certificacion.setIfpId(competencia.getIdCentro());
		certificacion.setIfpNombre(competencia.getNombreCentro());
		certificacion.setIfpDireccion(competencia.getDireccion());
		//certificacion.setActividades(new ArrayList<Actividad>());
		certificacion.setUnidades(new HashSet<Unidad>());
		certificacion.setInvolucrados(new Contacto[] {});
		
		certificacion.setDivulgacionInicia(new Date());
		certificacion.setDivulgacionFinaliza(new Date());
		certificacion.setInscripcionFinaliza(new Date());
		certificacion.setEvaluacionInicia(new Date());
		certificacion.setConvocatoriaInicia(new Date());

		certificacion.setEstatus(estatus);
		//certificacion.setRequisitos(service.getRequisitos(certificacion.getCursoId(), certificacion.getIfpId()));
		certificacion = (Certificacion)service.guardar(certificacion);
		
		List<Requisito> requisitos = service.getRequisitos(certificacion);
		service.guardar(requisitos.get(0));
		
		service.guardar(new Unidad(certificacion,"001","UC1",null,true));		
		
		Mantenedor estado = service.getMapMantenedoresByTipo("4").get(10);		//estatus pendiente		
		Actividad divulgacion = new Actividad(certificacion,0,actividades.get(1),"Divulgacion","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado);
		Actividad convocatoria = new Actividad(certificacion,1,actividades.get(4),"Convocatoria","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado);
		Actividad evaluacion = new Actividad(certificacion,2,actividades.get(3),"Evaluacion","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado);
		Actividad verificacion = new Actividad(certificacion,3,actividades.get(2),"Verificacion","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado);
		
		divulgacion = (Actividad)service.guardar(divulgacion);
		convocatoria = (Actividad)service.guardar(convocatoria);
		evaluacion = (Actividad)service.guardar(evaluacion);
		verificacion = (Actividad)service.guardar(verificacion);
		
		certificaciones = service.getCertificaciones();
		/*
		certificacion.addActividad(divulgacion);
		certificacion.addActividad(verificacion);
		certificacion.addActividad(evaluacion);
		certificacion.addActividad(convocatoria);
		
		//certificaciones.add(0, (Certificacion) service.guardar(certificacion));
		certificaciones.add(0, certificacion);*/
	}
	
	public void onRowSelectCompetencia(SelectEvent event) {  
    }
}