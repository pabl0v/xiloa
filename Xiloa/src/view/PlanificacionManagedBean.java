package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Mantenedor;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;
import service.IService;
import support.FacesUtil;
import support.Ifp;
import support.UCompetencia;

/**
 * 
 * @author Denis Chavez
 * 
 * JSF ManagedBean asociado a la interfaz  planificacion.xhtml
 * Esta clase maneja lo que se muestra en la página, los eventos relacionados 
 * e interacciona con la capa de servicio (paquete service)
 */

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
				
		Certificacion certificacion = new Certificacion(
				competencia.getOfertaId(), 
				competencia.getEstructuraId(), 
				competencia.getIdUCompetencia(), 
				competencia.getNombreUCompetencia(), 
				competencia.getNombreUCompetencia(), 
				competencia.getDisponibilidad(),
				competencia.getCosto(), 
				new Date(), 
				null, 
				null, 
				competencia.getIdCentro(), 
				competencia.getNombreCentro(), 
				competencia.getDireccion(), 
				null, 
				null, 
				controller.getContacto(), 
				null, 
				null, 
				service.getMantenedorById(16));		// estatus pendiente

		certificacion = service.guardarCertificacion(certificacion);
		certificaciones.add(0,certificacion);
	}
	
	public String editarCertificacion(Certificacion certificacion){
		FacesUtil.setParamBySession("certificacionId", certificacion.getId());
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
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