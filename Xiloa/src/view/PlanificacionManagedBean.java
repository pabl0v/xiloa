package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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
	@Autowired
	private UtilitariosManagedBean utilitarios;
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
	
	public void nuevaCertificacion(UCompetencia competencia){
		
		Mantenedor estatus = service.getMapMantenedoresByTipo("3").get(7);		//estatus pendiente
		Usuario creador = service.getUsuarioLocal("admin");						//actualizar
		
		Certificacion certificacion = new Certificacion();
		certificacion.setEstructuraId(competencia.getEsructuraId());
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
		certificacion.setUnidades(new HashSet<Unidad>());
		certificacion.setInvolucrados(new Contacto[] {});
		certificacion.setDivulgacionInicia(new Date());
		certificacion.setDivulgacionFinaliza(new Date());
		certificacion.setInscripcionFinaliza(new Date());
		certificacion.setEvaluacionInicia(new Date());
		certificacion.setConvocatoriaInicia(new Date());

		certificacion.setEstatus(estatus);
		
		List<Requisito> requisitos = service.getRequisitos(certificacion.getCursoId(), certificacion.getIfpId());
		Map<Long, String> codigos = service.getUnidadesByEstructuraId(certificacion.getEstructuraId());
		List<Unidad> unidades = new ArrayList<Unidad>();
		
		for (Long key : codigos.keySet()) {
			String value = codigos.get(key);
			System.out.println("codigo: "+key);
			System.out.println("valor: "+value);
		    unidades.add(new Unidad(key, value, certificacion, null));
		}
				
		certificacion = service.guardarCertificacion(certificacion, requisitos, unidades);
		certificaciones.add(0,certificacion);
	}
	
	public String configurarInstrumento(Certificacion certificacion){
		//FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put("idCertificacion", certificacion.getId().toString());
		//FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put("nombreCertificacion", certificacion.getNombre());
		return "/modulos/planificacion/instrumentos?faces-redirect=true";
	}
	
	public void onRowSelectCompetencia(SelectEvent event) {  
    }
}