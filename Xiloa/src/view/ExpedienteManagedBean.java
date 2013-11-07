package view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import model.Archivo;
import model.Contacto;
import model.Evaluacion;
import model.Instrumento;
import model.Laboral;
import model.Mantenedor;
import model.Solicitud;
import model.Unidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.BeanEvaluacion;
import support.Ifp;

@Component
@Scope(value="session")
public class ExpedienteManagedBean  {
	
	
	
	@Autowired
	private IService service;	
	
	private Solicitud solicitudExp;
	
	private List<Laboral> listDatosLaborales = new ArrayList<Laboral> ();
	private List<Laboral> listDatosEstudios = new ArrayList<Laboral> ();
	private List<Laboral> listDatosCalificacion = new ArrayList<Laboral> ();
	private List<Laboral> listDatosCertificaciones = new ArrayList<Laboral> ();
	private List<Evaluacion> listEvaluaciones = new ArrayList<Evaluacion> ();
	private List<BeanEvaluacion> listBeanEval = new ArrayList<BeanEvaluacion> ();
	
	private String nombreInstitucion;
	private String nombreCargo;
	private Date fechaDesde;
	private Date fechaHasta;
	private String institucionDireccion;
	private Integer tipoLaboral;
	private String estadoActual;
	private String estadoSiguiente;
	private Long idSeletedLaboral;
	private List<SelectItem> listTipoDatosLaborales = new ArrayList<SelectItem> ();
	private Laboral selectedLaboral;
	
	private Evaluacion seletedEvaluacion;
	private BeanEvaluacion selectedBeanEvaluacion;
	
	
	private Map<Integer, Mantenedor> catalogoTipoDatosLaborales = new HashMap<Integer, Mantenedor>();

	public Solicitud getSolicitudExp() {
		return solicitudExp;
	}

	public void setSolicitudExp(Solicitud solicitudExp) {
		this.solicitudExp = solicitudExp;
	}

	public List<Laboral> getListDatosLaborales() {
		this.listDatosLaborales = service.getListLaboralByTipo(new Integer(13), this.solicitudExp.getContacto());
		return this.listDatosLaborales;
	}

	public void setListDatosLaborales(List<Laboral> listDatosLaborales) {
		this.listDatosLaborales = listDatosLaborales;
	}

	public List<Laboral> getListDatosEstudios() {
		this.listDatosEstudios = service.getListLaboralByTipo(new Integer(14), this.solicitudExp.getContacto());
		return listDatosEstudios;
	}

	public void setListDatosEstudios(List<Laboral> listDatosEstudios) {
		this.listDatosEstudios = listDatosEstudios;
	}

	public List<Laboral> getListDatosCalificacion() {
		this.listDatosCalificacion = service.getListLaboralByTipo(new Integer(15), this.solicitudExp.getContacto());
		return listDatosCalificacion;
	}

	public void setListDatosCalificacion(List<Laboral> listDatosCalificacion) {
		this.listDatosCalificacion = listDatosCalificacion;
	}

	public List<Laboral> getListDatosCertificaciones() {
		this.listDatosCertificaciones = service.getListLaboralByTipo(new Integer(16), this.solicitudExp.getContacto());
		return listDatosCertificaciones;
	}

	public void setListDatosCertificaciones(List<Laboral> listDatosCertificaciones) {
		this.listDatosCertificaciones = listDatosCertificaciones;
	}

	public List<Evaluacion> getListEvaluaciones() {
		listEvaluaciones = service.getEvaluaciones(this.solicitudExp);			
		return listEvaluaciones;
	}

	public void setListEvaluaciones(List<Evaluacion> listEvaluaciones) {
		this.listEvaluaciones = listEvaluaciones;
	}

	public List<BeanEvaluacion> getListBeanEval() {
		List<BeanEvaluacion> listBeanEv = new ArrayList<BeanEvaluacion> ();
		for (Evaluacion eva : listEvaluaciones) {
			List<Instrumento> listInstrumento = new ArrayList<Instrumento> ();
			/*
			listInstrumento = eva.getUnidad().getInstrumentos();
			for (Instrumento ob : listInstrumento) {				
				listBeanEv.add(new BeanEvaluacion( ob, eva.getUnidad(), eva));			    
			}
			*/			
		}
		listBeanEval = listBeanEv;
		return listBeanEval;
	}

	public void setListBeanEval(List<BeanEvaluacion> listBeanEval) {
		this.listBeanEval = listBeanEval;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public String getNombreCargo() {
		return nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getInstitucionDireccion() {
		return institucionDireccion;
	}

	public void setInstitucionDireccion(String institucionDireccion) {
		this.institucionDireccion = institucionDireccion;
	}

	public Integer getTipoLaboral() {
		return tipoLaboral;
	}

	public void setTipoLaboral(Integer tipoLaboral) {
		this.tipoLaboral = tipoLaboral;
	}		
		
	public String getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
	}

	public String getEstadoSiguiente() {
		return estadoSiguiente;
	}

	public void setEstadoSiguiente(String estadoSiguiente) {
		this.estadoSiguiente = estadoSiguiente;
	}
	
	public Long getIdSeletedLaboral() {
		return idSeletedLaboral;
	}

	public void setIdSeletedLaboral(Long idSeletedLaboral) {
		this.idSeletedLaboral = idSeletedLaboral;
	}

	public List<SelectItem> getListTipoDatosLaborales() {
		return listTipoDatosLaborales;
	}

	public void setListTipoDatosLaborales(List<SelectItem> listTipoDatosLaborales) {
		this.listTipoDatosLaborales = listTipoDatosLaborales;
	}

	public Map<Integer, Mantenedor> getCatalogoTipoDatosLaborales() {
		return catalogoTipoDatosLaborales;
	}

	public void setCatalogoTipoDatosLaborales(
			Map<Integer, Mantenedor> catalogoTipoDatosLaborales) {
		this.catalogoTipoDatosLaborales = catalogoTipoDatosLaborales;
	}
			
	
	public Laboral getSelectedLaboral() {
		return selectedLaboral;
	}

	public void setSelectedLaboral(Laboral selectedLaboral) {
		this.selectedLaboral = selectedLaboral;
	}
	
	public Evaluacion getSeletedEvaluacion() {
		return seletedEvaluacion;
	}

	public void setSeletedEvaluacion(Evaluacion seletedEvaluacion) {
		this.seletedEvaluacion = seletedEvaluacion;
	}

	public BeanEvaluacion getSelectedBeanEvaluacion() {
		return selectedBeanEvaluacion;
	}

	public void setSelectedBeanEvaluacion(BeanEvaluacion selectedBeanEvaluacion) {
		this.selectedBeanEvaluacion = selectedBeanEvaluacion;
	}

	@PostConstruct
	private void fillCatalogos(){
		List<Mantenedor> listaCatalogo = service.getMantenedoresByTipo(new Integer(5));
		for (Mantenedor dato : listaCatalogo) {
			this.catalogoTipoDatosLaborales.put(dato.getId(), dato);			
			this.listTipoDatosLaborales.add(new SelectItem(dato.getId(), dato.getValor()));			
		}			
	}
	
	public void editarLaboral(){		
		this.setNombreInstitucion(this.selectedLaboral.getInstitucion());
		this.setInstitucionDireccion(this.selectedLaboral.getInstitucionDireccion());
		this.setTipoLaboral(this.selectedLaboral.getTipo());
		this.setNombreCargo(this.selectedLaboral.getCargo());
		this.setFechaDesde(this.selectedLaboral.getFechaInicia());
		this.setFechaHasta(this.selectedLaboral.getFechaFinaliza());
		this.setIdSeletedLaboral(this.selectedLaboral.getId());		
	}
	
	public void editarEvaluacion() {
		
	}
	
	
	
	public String registrar_evaluacion() {		
		return "/modulos/solicitudes/registro_evaluacion?faces-redirect=true";
	}
	
	public void actualizarContacto() {
		System.out.println("correo " + solicitudExp.getContacto().getCorreo1());
		if (solicitudExp.getContacto().getDireccionActual() == null) {
			solicitudExp.getContacto().setDireccionActual("");			
		}
		if (solicitudExp.getContacto().getCorreo1() == null) {
			solicitudExp.getContacto().setCorreo1("");			
		}
		if (solicitudExp.getContacto().getDireccionActual() == null) {
			solicitudExp.getContacto().setDireccionActual("");			
		}
		if (solicitudExp.getContacto().getTelefono1() == null) {
			solicitudExp.getContacto().setTelefono1("");			
		}
		Contacto contactoExp = (Contacto)service.guardar(solicitudExp.getContacto());
		System.out.println("Despues de guardar " + contactoExp.getCorreo1());
		  
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje: ", "Los cambios ha sido aplicados exitosamente !!"));
	        
	      
	}

	public void guardarDatosLaborales() {
				
		System.out.println("Valor del selectedLaboral " + this.idSeletedLaboral);
		
		if (this.idSeletedLaboral == null) {
		
			this.selectedLaboral = new Laboral (this.solicitudExp.getContacto(), // contacto, 
									   this.tipoLaboral, // tipo, 
									   "", // nombre,
									   "", // descripcion, 
									   this.nombreInstitucion.toUpperCase(), // institucion, 
									   "Nicaragua".toUpperCase(), // pais,
									   this.fechaDesde, // fechaInicia, 
									   this.fechaHasta, // fechaFinaliza, 
									   this.institucionDireccion.toUpperCase(), // institucionDireccion,
									   "", // institucionTelefono, 
									   this.nombreCargo.toUpperCase(), // cargo, 
									   null // archivo
										);		
			
		} else {
			this.selectedLaboral = service.getLaboralById(this.idSeletedLaboral);
						
			this.selectedLaboral.setInstitucion(this.nombreInstitucion.toUpperCase());
			this.selectedLaboral.setInstitucionDireccion(this.institucionDireccion.toUpperCase());		
			this.selectedLaboral.setCargo(this.nombreCargo.toUpperCase());		
			this.selectedLaboral.setFechaInicia(this.fechaDesde);
			this.selectedLaboral.setFechaFinaliza(this.fechaHasta);	
			
		}
		this.selectedLaboral = (Laboral)service.guardar(this.selectedLaboral);
		
		limpiarCampos ();
	}
	
	public void limpiarCampos (){
		System.out.println("Metodo Limpiar Campos");
		
		this.setSelectedLaboral(null);
		this.setNombreCargo(null);	
		this.setNombreInstitucion(null);
		this.setFechaDesde(null);
		this.setFechaHasta(null);
		this.setInstitucionDireccion(null);
		this.setIdSeletedLaboral(null);	
			
	}	

}
