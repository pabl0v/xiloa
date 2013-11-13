package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import model.Archivo;
import model.Contacto;
import model.Evaluacion;
import model.EvaluacionGuia;
import model.Guia;
import model.Instrumento;
import model.Laboral;
import model.Mantenedor;
import model.Solicitud;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import service.IService;
import support.BeanEvaluacion;
import support.Departamento;
import support.Municipio;

@Component
@Scope(value="session")
public class ExpedienteManagedBean implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;	
	
	private Solicitud solicitudExp;
	
	private List<Laboral> listDatosLaborales;
	private List<Laboral> listDatosEstudios;
	private List<Laboral> listDatosCalificacion;
	private List<Laboral> listDatosCertificaciones;
	private List<Evaluacion> listEvaluaciones;
	private List<BeanEvaluacion> listBeanEval;
	private List<BeanEvaluacion> listBeanEvalFormacion;	
	
	private String telefonoInstitucion;	
	private String descripcionCargo;
	private String paisInstitucion;
	private String nombreInstitucion;
	private String nombreCargo;
	private Date fechaDesde;
	private Date fechaHasta;
	private String institucionDireccion;
	private Integer tipoLaboral;
	
	private String estadoActual;
	private String estadoSiguiente;	
	
	private Long idSeletedLaboral;
	private List<SelectItem> listTipoDatosLaborales;
	private Laboral selectedLaboral;
	private Laboral nuevoLaboral;
	
	private Evaluacion seletedEvaluacion;
	private BeanEvaluacion selectedBeanEvaluacion;
	
	private UploadedFile file;
	
	private Archivo archivoExp;
	private Long evalIdByArchivoExp;
	private List<Archivo> listPortafolio;
	private List<Archivo> listPortafolioContacto;
	
	private boolean disableSolicitarCertificacion;
	private boolean disablePortafolio;
	private boolean disabledUploadFile;
	private boolean indicaCVFull;
	private boolean disabledBtnActualizaContacto;
	private boolean disabledBtnAgregaLaborales;
	private boolean disabledBtnAgregaEvaluacion;
	
	private List<SelectItem> listEvalBySolicitud;	
	
	private Map<Integer, Mantenedor> catalogoTipoDatosLaborales;
	private Map<Integer, Mantenedor> catalogoEstadosSolicitud;
	private Map<Integer, Mantenedor> catalogoEstadosEvaluacion;
	private Map<Integer, Mantenedor> catalogoEstadosPortafolio;
	
	
	private Map<Integer, Departamento> catalogoDepartamento;
	private Map<Integer, Municipio> catalogoMunicipiosByDepto;
	
	private List<SelectItem> listDeptos;
	private List<SelectItem> listMunicipioByDptos;
	
	private List<Archivo> listPortafolioLaboral;
	
	private Long selectedArchivoId;
	
	public ExpedienteManagedBean() {
		super();
		
		this.setDisableSolicitarCertificacion(true);
		this.setDisablePortafolio(true);
		this.setDisabledUploadFile(true);
		this.setIndicaCVFull(false);
		this.setDisabledBtnActualizaContacto(false);
		this.setDisabledBtnAgregaLaborales(false);
		this.setDisabledBtnAgregaEvaluacion(true);		
		
		listDatosLaborales = new ArrayList<Laboral> ();
		listDatosEstudios = new ArrayList<Laboral> ();
		listDatosCalificacion = new ArrayList<Laboral> ();
		listDatosCertificaciones = new ArrayList<Laboral> ();
		listEvaluaciones = new ArrayList<Evaluacion> ();
		listBeanEval = new ArrayList<BeanEvaluacion> ();
		listBeanEvalFormacion = new ArrayList<BeanEvaluacion> ();
		listPortafolioContacto = new ArrayList<Archivo> ();
		listPortafolioLaboral = new ArrayList<Archivo> ();
		
		listTipoDatosLaborales = new ArrayList<SelectItem> ();
		
		listEvalBySolicitud = new ArrayList<SelectItem> ();	
		
		catalogoTipoDatosLaborales = new HashMap<Integer, Mantenedor>();
		catalogoEstadosSolicitud = new HashMap<Integer, Mantenedor>();
		catalogoEstadosEvaluacion = new HashMap<Integer, Mantenedor>();
		catalogoEstadosPortafolio = new HashMap<Integer, Mantenedor> ();
		
		catalogoDepartamento = new HashMap<Integer, Departamento>();
		catalogoMunicipiosByDepto = new HashMap<Integer, Municipio>();
		
		listDeptos = new ArrayList<SelectItem> ();
		listMunicipioByDptos = new ArrayList<SelectItem> ();
		
		nuevoLaboral = new Laboral();
	}
	
   
	public Map<Integer, Mantenedor> getCatalogoEstadosEvaluacion() {
		return catalogoEstadosEvaluacion;
	}


	public void setCatalogoEstadosEvaluacion(
			Map<Integer, Mantenedor> catalogoEstadosEvaluacion) {
		this.catalogoEstadosEvaluacion = catalogoEstadosEvaluacion;
	}


	public Map<Integer, Mantenedor> getCatalogoEstadosPortafolio() {
		return catalogoEstadosPortafolio;
	}


	public void setCatalogoEstadosPortafolio(
			Map<Integer, Mantenedor> catalogoEstadosPortafolio) {
		this.catalogoEstadosPortafolio = catalogoEstadosPortafolio;
	}


	public boolean isDisabledBtnActualizaContacto() {
		enabledDisableButton(1);
		return disabledBtnActualizaContacto;
	}


	public void setDisabledBtnActualizaContacto(boolean disabledBtnActualizaContacto) {
		this.disabledBtnActualizaContacto = disabledBtnActualizaContacto;
	}


	public boolean isDisabledBtnAgregaLaborales() {
		enabledDisableButton(3);
		return disabledBtnAgregaLaborales;
	}


	public void setDisabledBtnAgregaLaborales(boolean disabledBtnAgregaLaborales) {
		this.disabledBtnAgregaLaborales = disabledBtnAgregaLaborales;
	}


	public boolean isDisabledBtnAgregaEvaluacion() {
		enabledDisableButton(4);
		return disabledBtnAgregaEvaluacion;
	}


	public void setDisabledBtnAgregaEvaluacion(boolean disabledBtnAgregaEvaluacion) {
		this.disabledBtnAgregaEvaluacion = disabledBtnAgregaEvaluacion;
	}


	public boolean isIndicaCVFull() {
		return indicaCVFull;
	}

	public void setIndicaCVFull(boolean indicaCVFull) {
		this.indicaCVFull = indicaCVFull;
	}

	public boolean isDisabledUploadFile() {
		return disabledUploadFile;
	}



	public void setDisabledUploadFile(boolean disabledUploadFile) {
		this.disabledUploadFile = disabledUploadFile;
	}



	public boolean isDisablePortafolio() {
		return disablePortafolio;
	}



	public void setDisablePortafolio(boolean disablePortafolio) {
		this.disablePortafolio = disablePortafolio;
	}


	public List<BeanEvaluacion> getListBeanEvalFormacion() {
		listBeanEvalFormacion = getListadoEvaluacionesByParam(this.solicitudExp, false);
		return listBeanEvalFormacion;
	}

	public void setListBeanEvalFormacion(List<BeanEvaluacion> listBeanEvalFormacion) {
		this.listBeanEvalFormacion = listBeanEvalFormacion;
	}

	public List<Archivo> getListPortafolioContacto() {
		actualizaListaPortafolio (new Integer(2));
		/*Contacto c = solicitudExp.getContacto();
		Object [] objs;
		objs =  new Object [] {c.getId()};
		this.listPortafolioContacto = service.getArchivoByParam ("Archivo.findByContactoId", objs);
		*/
		return listPortafolioContacto;
	}


	public void setListPortafolioContacto(List<Archivo> listPortafolioContacto) {
		this.listPortafolioContacto = listPortafolioContacto;
	}



	public Long getSelectedArchivoId() {
		return selectedArchivoId;
	}



	public void setSelectedArchivoId(Long selectedArchivoId) {
		this.selectedArchivoId = selectedArchivoId;
	}



	public List<Archivo> getListPortafolioLaboral() {		
		return listPortafolioLaboral;
	}



	public void setListPortafolioLaboral(List<Archivo> listPortafolioLaboral) {
		this.listPortafolioLaboral = listPortafolioLaboral;
	}



	public String getTelefonoInstitucion() {
		return telefonoInstitucion;
	}



	public void setTelefonoInstitucion(String telefonoInstitucion) {
		this.telefonoInstitucion = telefonoInstitucion;
	}



	public String getDescripcionCargo() {
		return descripcionCargo;
	}



	public void setDescripcionCargo(String descripcionCargo) {
		this.descripcionCargo = descripcionCargo;
	}



	public String getPaisInstitucion() {
		return paisInstitucion;
	}



	public void setPaisInstitucion(String paisInstitucion) {
		this.paisInstitucion = paisInstitucion;
	}

	public Laboral getNuevoLaboral() {
		return nuevoLaboral;
	}

	public void setNuevoLaboral(Laboral nuevoLaboral) {
		this.nuevoLaboral = nuevoLaboral;
	}

	public List<SelectItem> getListDeptos() {
		if (this.catalogoDepartamento.size() > 0 ) {
			
			Integer idValor;
			Departamento valor;
			
			Iterator<Integer> claveSet = this.catalogoDepartamento.keySet().iterator();
			
			listDeptos = new ArrayList<SelectItem> ();
		    
		    while(claveSet.hasNext()){		      
		    	idValor = claveSet.next();
		    	valor = this.catalogoDepartamento.get(idValor);
		    	this.listDeptos.add(new SelectItem(idValor, valor.getDpto_nombre()));		    			        		        
		    }
		}
		
		return listDeptos;
	}


	public void setListDeptos(List<SelectItem> listDeptos) {
		this.listDeptos = listDeptos;
	}


	public List<SelectItem> getListMunicipioByDptos() {
		return listMunicipioByDptos;
	}


	public void setListMunicipioByDptos(List<SelectItem> listMunicipioByDptos) {
		this.listMunicipioByDptos = listMunicipioByDptos;
	}


	public Map<Integer, Departamento> getCatalogoDepartamento() {		
		return this.catalogoDepartamento;
	}



	public void setCatalogoDepartamento(Map<Integer, Departamento> catalogoDepartamento) {
		this.catalogoDepartamento = catalogoDepartamento;
	}



	public Map<Integer, Municipio> getCatalogoMunicipiosByDepto() {
		return catalogoMunicipiosByDepto;
	}



	public void setCatalogoMunicipiosByDepto(Map<Integer, Municipio> catalogoMunicipiosByDepto) {
		this.catalogoMunicipiosByDepto = catalogoMunicipiosByDepto;
	}



	public boolean isDisableSolicitarCertificacion() {
		return disableSolicitarCertificacion;
	}

	public void setDisableSolicitarCertificacion(
			boolean disableSolicitarCertificacion) {
		this.disableSolicitarCertificacion = disableSolicitarCertificacion;
	}



	public Solicitud getSolicitudExp() {
		List<Mantenedor> listaEstadosSol = service.getMantenedoresByTipo(Integer.valueOf(this.solicitudExp.getTipomantenedorestado()));
		
		for (Mantenedor dato : listaEstadosSol) {
			this.catalogoEstadosSolicitud.put(dato.getId(), dato);						
		}
		
		this.catalogoDepartamento = service.getDepartamentosByInatec();
		// habilita y desabilita los botones
		//enabledDisableButton(1);
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
		listBeanEval = getListadoEvaluacionesByParam(this.solicitudExp, true);		
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
		String estadoSolicitud = this.catalogoEstadosSolicitud.get(this.solicitudExp.getEstatus()).getValor();
		estadoActual = estadoSolicitud;
		return estadoActual;
	}

	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
	}

	public String getEstadoSiguiente() {
		Mantenedor estado = service.getMantenedorById(new Integer(this.getSolicitudExp().getEstatus()));		
		String sigEstadoSolicitud = this.catalogoEstadosSolicitud.get(Integer.valueOf(estado.getProximo())).getValor();
		estadoSiguiente = sigEstadoSolicitud;
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
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Archivo getArchivoExp() {
		return archivoExp;
	}

	public void setArchivoExp(Archivo archivoExp) {
		this.archivoExp = archivoExp;
	}

	public List<Archivo> getListPortafolio() {
		return listPortafolio;
	}

	public void setListPortafolio(List<Archivo> listPortafolio) {
		this.listPortafolio = listPortafolio;
	}

	public List<SelectItem> getListEvalBySolicitud() {		
		return listEvalBySolicitud;
	}

	public void setListEvalBySolicitud(List<SelectItem> listEvalBySolicitud) {
		this.listEvalBySolicitud = listEvalBySolicitud;
	}	

	public Long getEvalIdByArchivoExp() {
		return evalIdByArchivoExp;
	}

	public void setEvalIdByArchivoExp(Long evalIdByArchivoExp) {
		this.evalIdByArchivoExp = evalIdByArchivoExp;
	}

	public Map<Integer, Mantenedor> getCatalogoEstadosSolicitud() {
		return catalogoEstadosSolicitud;
	}

	public void setCatalogoEstadosSolicitud(
			Map<Integer, Mantenedor> catalogoEstadosSolicitud) {
		this.catalogoEstadosSolicitud = catalogoEstadosSolicitud;
	}

	@PostConstruct
	private void fillCatalogos(){
		List<Mantenedor> listaCatalogo = service.getMantenedoresByTipo(new Integer(5));		
		for (Mantenedor dato : listaCatalogo) {
			this.catalogoTipoDatosLaborales.put(dato.getId(), dato);			
			this.listTipoDatosLaborales.add(new SelectItem(dato.getId(), dato.getValor()));			
		}	
		
		//Obtiene el catalogo de Estados Evaluacion
		listaCatalogo = service.getMantenedoresByTipo(new Integer(9));		
		for (Mantenedor dato : listaCatalogo) {
			this.catalogoEstadosEvaluacion.put(dato.getId(), dato);						
		}
		
		listaCatalogo = service.getMantenedoresByTipo(new Integer(8));		
		for (Mantenedor dato : listaCatalogo) {
			this.catalogoEstadosPortafolio.put(dato.getId(), dato);						
		}
		
		archivoExp = new Archivo();
	}
	
	public List<BeanEvaluacion> getListadoEvaluacionesByParam(Solicitud sol, boolean todos) {		
		List<BeanEvaluacion> listBeanEv = new ArrayList<BeanEvaluacion> ();		
		
		List<Evaluacion> listEval = service.getEvaluaciones(sol);
		
		String estadoEval;
		
		for (Evaluacion e : listEval) {
			
			estadoEval = catalogoEstadosEvaluacion.get(Integer.valueOf(e.getEstado())).getValor();
			
			
			List<Instrumento> listInstrumento = service.getIntrumentoByEvaluacion(e.getId());
			for (Instrumento inst : listInstrumento) {				
				BeanEvaluacion bean = new BeanEvaluacion (sol, //Solicitud, 
						  								  e, //	Evaluacion
						  								  inst,// Instrumento
						  								  estadoEval // EstadoEvaluacion
						  								  );
				
				if (todos) {
					listBeanEv.add(bean);
				} else {
					if (! e.isAprobado()){
						listBeanEv.add(bean);
					} 
				}	
			}
		}			
		
		return listBeanEv;
	}
	
	public void handleActivaUpload(){
		if (archivoExp != null) {
			if ((archivoExp.getNombre() != null) && (archivoExp.getTipo() != null) && (archivoExp.getVersion() != null) && (archivoExp.getDescripcion() != null)){
				this.setDisabledUploadFile(false);
			}
		}
	}
	
	public void handleMunicipios() {
		
		Contacto cExp = this.getSolicitudExp().getContacto();
		
		if (cExp != null) {
			
			System.out.println("Consulta la informacion del Departamento");
			System.out.println("Departamento " + cExp.getDepartamentoId());
			this.catalogoMunicipiosByDepto = service.getMunicipioDptoByInatec(cExp.getDepartamentoId());
			
			if (this.catalogoMunicipiosByDepto.size() > 0 ) {
				
				Integer idValor;
				Municipio valor;
				
				Iterator<Integer> claveSet = this.catalogoMunicipiosByDepto.keySet().iterator();
				
				this.listMunicipioByDptos = new ArrayList<SelectItem> ();
				
			    while(claveSet.hasNext()){		      
			    	idValor = claveSet.next();
			    	valor = this.catalogoMunicipiosByDepto.get(idValor);
			    	this.listMunicipioByDptos.add(new SelectItem(idValor, valor.getMunicipio_nombre()));			    			    			        		        
			    }
			}
			
			
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
		this.setTelefonoInstitucion(this.selectedLaboral.getInstitucionTelefono());
		this.setPaisInstitucion(this.selectedLaboral.getPais());
		this.setDescripcionCargo(this.selectedLaboral.getDescripcion());
		
		enabledDisableButton(2);
		
		//actualizaListaPortafolio (new Integer(1));		
		
		Object [] objs =  new Object [] {this.selectedLaboral.getId()};
		System.out.println("Ejecuta la consulta");
		this.listPortafolioLaboral = service.getArchivoByParam ("Archivo.findByLaboralId", objs);
		
	}
	
	public void nuevoLaboral() {		
		limpiarCampos();
		
		this.setPaisInstitucion(new String("Nicaragua"));
	}
	
	public String RegistrarEditarEvaluacion() {		
		return "/modulos/solicitudes/registro_evaluacion?faces-redirect=true";		
	}	
	
	public void solicitarCertificacion (){
		Integer estadoActual = new Integer(this.getSolicitudExp().getEstatus());
		String proxEstado = this.catalogoEstadosSolicitud.get(estadoActual).getProximo();
		
		this.solicitudExp.setEstatus(Integer.valueOf(proxEstado));
		
		Solicitud sol = (Solicitud) service.guardar(this.solicitudExp);
		
		if (sol != null){
			this.setSolicitudExp(sol);
			// habilita y desabilita los botones
			enabledDisableButton(1);
			enabledDisableButton(3);
			enabledDisableButton(4);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje: ", "La solicitud a sido registrada exitosamente !!"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje: ", "Error al registrar la solicitud. Favor revisar..."));
		}
		
		
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
		String nombreCargoLaboral;
		
		nombreCargoLaboral = this.nombreCargo.toUpperCase() + " /  " + this.nombreInstitucion.toUpperCase();
		
		if (this.idSeletedLaboral == null) {			
						
			this.selectedLaboral = new Laboral (this.solicitudExp.getContacto(), // contacto, 
									   this.tipoLaboral, // tipo, 
									   nombreCargoLaboral.toUpperCase(), // nombre,
									   this.descripcionCargo, // descripcion, 
									   this.nombreInstitucion.toUpperCase(), // institucion, 
									   this.paisInstitucion.toUpperCase(), // pais,
									   this.fechaDesde, // fechaInicia, 
									   this.fechaHasta, // fechaFinaliza, 
									   this.institucionDireccion.toUpperCase(), // institucionDireccion,
									   this.telefonoInstitucion, // institucionTelefono, 
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
			this.selectedLaboral.setDescripcion(this.descripcionCargo.toUpperCase());
			this.selectedLaboral.setNombre(nombreCargoLaboral.toUpperCase());
			this.selectedLaboral.setInstitucionTelefono(this.telefonoInstitucion.toUpperCase());
			this.selectedLaboral.setPais(this.paisInstitucion.toUpperCase());			
		}
		this.selectedLaboral = (Laboral)service.guardar(this.selectedLaboral);
		
		if (this.selectedLaboral != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje: ", "Los cambios ha sido aplicados exitosamente !!"));			
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje: ", "Se genero un error al grabar los datos laborales / academicos. Favor revisar..."));
		}
		
		enabledDisableButton(2);		
	}
	
	public void enabledDisableButton(int opcion) {
		
		Solicitud sol = this.getSolicitudExp();
		Integer estadoInicial = service.getMantenedorMinByTipo(sol.getTipomantenedorestado());
		boolean asignaDisable = estadoInicial.equals(new Integer (sol.getEstatus()));
		
		switch(opcion) {
			case 1:	{				
				this.setDisabledBtnActualizaContacto(!asignaDisable);				
				break;	
			}
			case 2: {
				if (this.selectedLaboral == null) 
					this.setDisablePortafolio(true);
				else
					this.setDisablePortafolio(false);
				break;
			}
			case 3: {
				this.setDisabledBtnAgregaLaborales(!asignaDisable);				
				break;
			}
			case 4: {
				this.setDisabledBtnAgregaEvaluacion(asignaDisable);
				break;
			}
			default: 
				break;			
		}
			
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
		this.setDescripcionCargo(null);
		this.setPaisInstitucion(null);
		this.setTelefonoInstitucion(null);
		this.listPortafolioLaboral = new ArrayList<Archivo> ();	
			
	}	
	
	
	public void nuevoArchivo() {
		archivoExp = new Archivo();
		
		listEvalBySolicitud = new ArrayList<SelectItem> ();
		List<Evaluacion> listE = this.getListEvaluaciones();
		
		System.out.println("Obtiene el listado de las evaluaciones");
		listEvalBySolicitud.add(new SelectItem(null, "Seleccione la evaluacion"));
		for (Evaluacion dato : listE) {
			listEvalBySolicitud.add(new SelectItem(dato.getId(), dato.getUnidad().getCompetenciaDescripcion()));
		}
	}

	public void nuevoPortafolio (){
		System.out.println("Agrega archivo de evidencia al dato laboral/academico: ");
		
		if (this.selectedArchivoId == null) {
			
			if (this.idSeletedLaboral != null) {
				this.selectedLaboral = service.getLaboralById(this.idSeletedLaboral);
				archivoExp.setLaboral(this.selectedLaboral);
			}
		} 
		
	}
	
	public void guardarArchivo() {
		if (archivoExp != null) {
			//Asignando el estado inicial del Portafolio
			String tipoMantenedorEstado = archivoExp.getTipoMantenedorEstado();
			
			archivoExp.setEstado(service.getMantenedorMinByTipo(tipoMantenedorEstado).toString());
			
			archivoExp = (Archivo) service.guardar(archivoExp);
			
			if (archivoExp != null)
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje: ", "Los cambios ha sido aplicados exitosamente !!"));
			else
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje: ", "Error al grabar el archivo. Favor revisar..."));
		}
		
		
	}
	
	public void actualizaListaPortafolio (Integer tipo){
		Object [] objs;
		if (tipo == 1) {
			System.out.println("Consulta el listado de Archivos por datos laborales");			
			if (selectedLaboral != null) {			
				System.out.println("Datos laborales " + selectedLaboral.getId());
				 objs =  new Object [] {selectedLaboral.getId()};
				 System.out.println("Ejecuta la consulta");
				 this.listPortafolioLaboral = service.getArchivoByParam ("Archivo.findByLaboralId", objs);				
			}
		} else {							
				Contacto c = solicitudExp.getContacto();
				objs =  new Object [] {c.getId()};
				this.listPortafolioContacto = service.getArchivoByParam ("Archivo.findByContactoId", objs);
		}
	}
	
	public void uploadFile(FileUploadEvent event){
		
		Date fechaAhora = new Date();
		
		FacesMessage msg = new FacesMessage("Proceso Exitoso !!!", event.getFile().getFileName() + " ha sido cargado al servidor.");  
	    FacesContext.getCurrentInstance().addMessage(null, msg);  
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		
	    String directorio = ec.getRealPath("/portafolio");
	    
	    System.out.println("Valor Directorio " + directorio);
	    Usuario u = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());
	    String nombreFile;
	    String nombrePropietario;
	    Long   sizeArchivo;
		try {
			
			//File targetFolder = new File(directorio);
			
			this.file = event.getFile();			
	
			nombreFile = file.getFileName();
			/*
			if (u.getContacto().getNombreCompleto() != null)
				nombrePropietario = u.getContacto().getNombreCompleto();
			else
				nombrePropietario = u.getContacto().getPrimerNombre() + " " + u.getContacto().getPrimerApellido();
			*/
			nombrePropietario = u.getUsuarioAlias();
			sizeArchivo = (file.getSize()) / 1024;
			
			archivoExp.setNombre(nombreFile);
			archivoExp.setSize(String.valueOf(sizeArchivo) + " KB");
			//archivoExp.setArchivoFisico(file.getContents());
			archivoExp.setFecha(fechaAhora);					
			archivoExp.setNombreReal(nombreFile);
			archivoExp.setRuta(nombreFile);
			archivoExp.setPropietario(nombrePropietario);
			archivoExp.setTipo(nombreFile);
			archivoExp.setCategoria(nombreFile);
			archivoExp.setIcono(nombreFile);
			archivoExp.setVersion(nombreFile);
			
					
			FileOutputStream fos = new FileOutputStream(String.format(directorio+"/%s",file.getFileName()));			
            fos.write(file.getContents());
            fos.flush();
            fos.close();
			/*
			InputStream inputStream = file.getInputstream(); 
			//OutputStream out = new FileOutputStream(new File(targetFolder,event.getFile().getFileName()));
			OutputStream out = new FileOutputStream(new File(targetFolder,file.getFileName()));
			int read = 0;
			byte[] bytes = new byte[1024];
			
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);			
			}
			inputStream.close();
			out.flush();
			out.close();
			*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	        			
	}
	
	public void validaRegistroCV (){
		FacesMessage msg;
		String       textMsg = "";
		String       titulo = "";
		
		Contacto solicitante = this.solicitudExp.getContacto();
		
		if (this.isIndicaCVFull()) {
			this.setDisableSolicitarCertificacion(false);		
			
			if (solicitante.getTelefono1() == null) {
				textMsg = "Debe indicar el numero de telefono";					    
				this.setDisableSolicitarCertificacion(true);
			}
			
			if (solicitante.getDireccionActual() == null) {
				textMsg = (textMsg.isEmpty()) ? "Debe indicar la direccion actual" : textMsg + ", la direccion actual";					    
				this.setDisableSolicitarCertificacion(true);
			} 
			
			if (solicitante.getDepartamentoId() == null) {
				textMsg = (textMsg.isEmpty()) ? "Debe indicar el departamento" : textMsg + ", el departamento";			
				this.setDisableSolicitarCertificacion(true);
			}
			
			if (solicitante.getMunicipioId() == null) {
				textMsg = (textMsg.isEmpty()) ? "Debe indicar el municipio." : textMsg + " y el municipio.";		
				this.setDisableSolicitarCertificacion(true);
			}
			
			if (this.isDisableSolicitarCertificacion())
				titulo = "Informacion incompleta: ";
			else{
				titulo = "Informacion: ";
				textMsg = "Puede proceder a registrar la solicitud";
			}
			
			msg = new FacesMessage(titulo, textMsg);
			
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else
		{
			this.setDisableSolicitarCertificacion(true);
		}
	}

}
