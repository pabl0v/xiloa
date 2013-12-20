package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Contacto;
import model.Mantenedor;
import model.Requisito;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;
import support.Ifp;
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
	@Autowired
	private LoginController controller;
	
	public PlanificacionManagedBean(){
		super();
		certificaciones = new ArrayList<Certificacion>();
		competencias = new ArrayList<UCompetencia>();
	}
	
	@PostConstruct
	private void init(){
		competencias = service.getUcompetenciaSinPlanificar(controller.getEntidadUsuario());
		certificaciones = service.getCertificaciones(controller.getEntidadUsuario());
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
		Contacto creador = controller.getContacto();
		
		System.out.println("Competencia=" + competencia);
		System.out.println("OfertaId=" + competencia.getOfertaId());
		
		Certificacion certificacion = new Certificacion();
		certificacion.setOfertaId(competencia.getOfertaId());
		certificacion.setEstructuraId(competencia.getEstructuraId());
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
		certificacion.setUnidades(new HashSet<Long>());
		certificacion.setInvolucrados(new Contacto[] {});

		certificacion.setEstatus(estatus);
		
		List<Requisito> requisitos = service.getRequisitos(certificacion.getCursoId(), certificacion.getIfpId());
		Map<Long, String> codigos = service.getUnidadesByEstructuraId(certificacion.getEstructuraId());
		
		certificacion.setUnidades(new HashSet<Long>(codigos.keySet()));
				
		certificacion = service.guardarCertificacion(certificacion, requisitos);
		certificaciones.add(0,certificacion);
	}
	
	public String configurarInstrumento(Certificacion certificacion){
		return "/modulos/planificacion/instrumentos?faces-redirect=true";
	}
	
	public void onRowSelectCompetencia(SelectEvent event) {  
    }
	
	public SelectItem[] getListaCentros(){
		List<Ifp> centros = service.getIfpByInatec(controller.getEntidadUsuario());
	
		SelectItem[] opciones = new SelectItem[centros.size()+1];
		opciones[0] = new SelectItem("","Seleccione");
		for(int i=0; i<centros.size(); i++)
			opciones[i+1] = new SelectItem(centros.get(i).getIfpNombre(),centros.get(i).getIfpNombre());
		return opciones;
	}
	
	public SelectItem[] getListaEstatus(){

		List<Mantenedor> estatusList = new ArrayList<Mantenedor>(service.getCatalogoEstatusCertificacion().values());
		SelectItem[] estatus = new SelectItem[estatusList.size() + 1];
		
		estatus[0] = new SelectItem("","Seleccione");
		for(int i=0; i<estatusList.size(); i++)
			estatus[i+1] = new SelectItem(estatusList.get(i).getValor(),estatusList.get(i).getValor());
		
		return estatus;
	}
}