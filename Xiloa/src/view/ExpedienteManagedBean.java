package view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import model.Archivo;
import model.Contacto;
import model.Evaluacion;
import model.Instrumento;
import model.Laboral;
import model.Mantenedor;
import model.Pais;
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
import support.FacesUtil;
import support.Municipio;

@Component
@Scope(value="request")
public class ExpedienteManagedBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	@Autowired
	private transient UtilitariosManagedBean util;
	
	private Solicitud solicitudExp;
	private Contacto contactoExp;
	
	private List<Laboral> listDatosLaborales;
	private List<Laboral> listDatosEstudios;
	private List<Laboral> listDatosCalificacion;
	private List<Laboral> listDatosCertificaciones;
	private List<Evaluacion> listEvaluaciones;
	private List<BeanEvaluacion> listBeanEval;
	private List<BeanEvaluacion> listBeanEvalFormacion;	
	
	private String telefonoInstitucion;	
	private String descripcionCargo;
	private Pais paisInstitucion;
	private Long paisIdLaboral;
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
	private List<SelectItem> listEstadosPortafolio;
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
	private List<SelectItem> listPaises;
	private List<SelectItem> listGenero;
	
	private List<Archivo> listPortafolioLaboral;
	
	private Long selectedArchivoId;
	
	private String municipioIdSelected;
	
	private Integer departamentoIdSelected;
	
	private String nombreArchivoExp;
	private String versionArchivoExp;
	private String tipoArchivoExp;
	private String descripcionArchivoExp;
	private String nombreRealArchivoExp;
	private String sizeArchivoExp;
	
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
		listEstadosPortafolio  = new ArrayList<SelectItem> ();
		
		listEvalBySolicitud = new ArrayList<SelectItem> ();	
		
		catalogoTipoDatosLaborales = new HashMap<Integer, Mantenedor>();
		catalogoEstadosSolicitud = new HashMap<Integer, Mantenedor>();
		catalogoEstadosEvaluacion = new HashMap<Integer, Mantenedor>();
		catalogoEstadosPortafolio = new HashMap<Integer, Mantenedor> ();
		
		catalogoDepartamento = new HashMap<Integer, Departamento>();
		catalogoMunicipiosByDepto = new HashMap<Integer, Municipio>();
		
		listDeptos = new ArrayList<SelectItem> ();
		listMunicipioByDptos = new ArrayList<SelectItem> ();
		listPaises = new ArrayList<SelectItem> ();
		listGenero = new ArrayList<SelectItem> ();
		
		nuevoLaboral = new Laboral();		
		
	}	
	
	public IService getService() {
		return service;
	}

	public void setService(IService service) {
		this.service = service;
	}

	public String getNombreRealArchivoExp() {
		return nombreRealArchivoExp;
	}

	public void setNombreRealArchivoExp(String nombreRealArchivoExp) {
		this.nombreRealArchivoExp = nombreRealArchivoExp;
	}

	public String getSizeArchivoExp() {
		return sizeArchivoExp;
	}

	public void setSizeArchivoExp(String sizeArchivoExp) {
		this.sizeArchivoExp = sizeArchivoExp;
	}

	public String getNombreArchivoExp() {
		return nombreArchivoExp;
	}

	public void setNombreArchivoExp(String nombreArchivoExp) {
		this.nombreArchivoExp = nombreArchivoExp;
	}

	public String getVersionArchivoExp() {
		return versionArchivoExp;
	}

	public void setVersionArchivoExp(String versionArchivoExp) {
		this.versionArchivoExp = versionArchivoExp;
	}

	public String getTipoArchivoExp() {
		return tipoArchivoExp;
	}

	public void setTipoArchivoExp(String tipoArchivoExp) {
		this.tipoArchivoExp = tipoArchivoExp;
	}

	public String getDescripcionArchivoExp() {
		return descripcionArchivoExp;
	}

	public void setDescripcionArchivoExp(String descripcionArchivoExp) {
		this.descripcionArchivoExp = descripcionArchivoExp;
	}

	public List<SelectItem> getListGenero() {
		return listGenero;
	}

	public void setListGenero(List<SelectItem> listGenero) {
		this.listGenero = listGenero;
	}

	public Long getPaisIdLaboral() {
		return paisIdLaboral;
	}

	public void setPaisIdLaboral(Long paisIdLaboral) {
		this.paisIdLaboral = paisIdLaboral;
	}

	public List<SelectItem> getListPaises() {
		return listPaises;
	}

	public void setListPaises(List<SelectItem> listPaises) {
		this.listPaises = listPaises;
	}

	public String getMunicipioIdSelected() {
		return municipioIdSelected;
	}

	public void setMunicipioIdSelected(String municipioIdSelected) {
		this.municipioIdSelected = municipioIdSelected;
	}

	public Integer getDepartamentoIdSelected() {
		return departamentoIdSelected;
	}


	public void setDepartamentoIdSelected(Integer departamentoIdSelected) {
		this.departamentoIdSelected = departamentoIdSelected;
	}


	public Contacto getContactoExp() {
		return contactoExp;
	}


	public void setContactoExp(Contacto contactoExp) {
		this.contactoExp = contactoExp;
	}


	public List<SelectItem> getListEstadosPortafolio() {
		return listEstadosPortafolio;
	}

	public void setListEstadosPortafolio(List<SelectItem> listEstadosPortafolio) {
		this.listEstadosPortafolio = listEstadosPortafolio;
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
		return disabledBtnActualizaContacto;
	}


	public void setDisabledBtnActualizaContacto(boolean disabledBtnActualizaContacto) {
		this.disabledBtnActualizaContacto = disabledBtnActualizaContacto;
	}


	public boolean isDisabledBtnAgregaLaborales() {		
		return disabledBtnAgregaLaborales;
	}


	public void setDisabledBtnAgregaLaborales(boolean disabledBtnAgregaLaborales) {
		this.disabledBtnAgregaLaborales = disabledBtnAgregaLaborales;
	}


	public boolean isDisabledBtnAgregaEvaluacion() {		
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
		if (this.solicitudExp != null){			
			listBeanEvalFormacion = getListadoEvaluacionesByParam(this.solicitudExp, false);
		}
		return listBeanEvalFormacion;
	}

	public void setListBeanEvalFormacion(List<BeanEvaluacion> listBeanEvalFormacion) {
		this.listBeanEvalFormacion = listBeanEvalFormacion;
	}

	public List<Archivo> getListPortafolioContacto() {
		actualizaListaPortafolio (new Integer(2));		
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



	public Pais getPaisInstitucion() {
		return paisInstitucion;
	}



	public void setPaisInstitucion(Pais paisInstitucion) {
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
			listDeptos.add(new SelectItem(null, "Seleccion un Departamento"));
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
		return solicitudExp;
	}

	public void setSolicitudExp(Solicitud solicitudExp) {
		this.solicitudExp = solicitudExp;
	}

	public List<Laboral> getListDatosLaborales() {
		if (this.solicitudExp != null){
			this.listDatosLaborales = service.getListLaboralByTipo(new Integer(13), this.solicitudExp.getContacto());
		}
		return this.listDatosLaborales;
	}

	public void setListDatosLaborales(List<Laboral> listDatosLaborales) {
		this.listDatosLaborales = listDatosLaborales;
	}

	public List<Laboral> getListDatosEstudios() {
		if (this.solicitudExp != null){
			this.listDatosEstudios = service.getListLaboralByTipo(new Integer(14), this.solicitudExp.getContacto());
		}
		return listDatosEstudios;
	}

	public void setListDatosEstudios(List<Laboral> listDatosEstudios) {
		this.listDatosEstudios = listDatosEstudios;
	}

	public List<Laboral> getListDatosCalificacion() {
		if (this.solicitudExp != null){
			this.listDatosCalificacion = service.getListLaboralByTipo(new Integer(15), this.solicitudExp.getContacto());
		}
		return listDatosCalificacion;
	}

	public void setListDatosCalificacion(List<Laboral> listDatosCalificacion) {
		this.listDatosCalificacion = listDatosCalificacion;
	}

	public List<Laboral> getListDatosCertificaciones() {
		if (this.solicitudExp != null){
			this.listDatosCertificaciones = service.getListLaboralByTipo(new Integer(16), this.solicitudExp.getContacto());
		}
		return listDatosCertificaciones;
	}

	public void setListDatosCertificaciones(List<Laboral> listDatosCertificaciones) {
		this.listDatosCertificaciones = listDatosCertificaciones;
	}

	public List<Evaluacion> getListEvaluaciones() {
		if (this.solicitudExp != null){
			listEvaluaciones = service.getEvaluaciones(this.solicitudExp);
		}
		return listEvaluaciones;
	}

	public void setListEvaluaciones(List<Evaluacion> listEvaluaciones) {
		this.listEvaluaciones = listEvaluaciones;
	}

	public List<BeanEvaluacion> getListBeanEval() {
		if (this.solicitudExp != null){
			listBeanEval = getListadoEvaluacionesByParam(this.solicitudExp, true);
		}
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
		if (this.solicitudExp != null){
			
			Mantenedor estado = this.solicitudExp.getEstatus();
			estadoActual = estado.getValor();			
		}
		return estadoActual;
	}

	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
	}

	public String getEstadoSiguiente() {
		if (this.solicitudExp != null){
			
			Mantenedor estadoSolicitud = this.getSolicitudExp().getEstatus();
			
			if (estadoSolicitud.getProximo() != null){
				
				Mantenedor estado = service.getMantenedorById(Integer.valueOf(estadoSolicitud.getProximo()));
				
				if (estado != null)
					estadoSiguiente = estado.getValor();
				else
					estadoSiguiente = estadoSolicitud.getValor();
			} else 
				estadoSiguiente = this.getSolicitudExp().getEstatus().getValor();
				
		}
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
		//Llenando el catalogo de Estados Solicitudes
		Integer tipoEstadoSolicitud;
		tipoEstadoSolicitud = Integer.valueOf(this.solicitudExp.getTipomantenedorestado());
		
		List<Mantenedor> listaEstadosSol = service.getMantenedoresByTipo(tipoEstadoSolicitud);
		
		this.catalogoEstadosSolicitud = new HashMap<Integer, Mantenedor>();
		
		for (Mantenedor dato : listaEstadosSol) {
			this.catalogoEstadosSolicitud.put(dato.getId(), dato);						
		}
		return catalogoEstadosSolicitud;
	}

	public void setCatalogoEstadosSolicitud(
			Map<Integer, Mantenedor> catalogoEstadosSolicitud) {
		this.catalogoEstadosSolicitud = catalogoEstadosSolicitud;
	}

	@PostConstruct
	private void initBean(){        
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
		listEstadosPortafolio = new ArrayList<SelectItem> ();
		for (Mantenedor dato : listaCatalogo) {
			this.catalogoEstadosPortafolio.put(dato.getId(), dato);
			this.listEstadosPortafolio.add(new SelectItem(dato.getId(), dato.getValor()));
		}
		
		this.catalogoDepartamento = service.getDepartamentosByInatec();
		
		List<Pais> paises = service.getPaises();
		this.listPaises = new ArrayList<SelectItem> ();
		this.listPaises.add(new SelectItem(null, "Seleccion un pais"));
		for (Pais p : paises){
			this.listPaises.add(new SelectItem(p.getId(), p.getNombre()));
		}
		
		listGenero = new ArrayList<SelectItem> ();
		
		listGenero.add(new SelectItem(null, "Indique el Genero"));
		
		List<Mantenedor> listaGenero = new ArrayList<Mantenedor> ();
		listaGenero = service.getMantenedoresByTipo(new Integer(10));
		for (Mantenedor dato : listaGenero){
			listGenero.add(new SelectItem(dato.getId(), dato.getValor()));
		}		
		
		archivoExp = new Archivo();
				
	}
	
	@Autowired
	public void iniciaBeanExp (){
		
		Solicitud  solicitud = (Solicitud)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).getAttribute("dbSolicitudesBean");
		
		if (solicitud != null){		
						
			this.solicitudExp = solicitud;
			
						
			this.contactoExp = this.solicitudExp.getContacto();
			if (this.contactoExp.getMunicipioId() != null){
				this.municipioIdSelected = String.valueOf(this.contactoExp.getMunicipioId());				
			} else
				this.municipioIdSelected = null;
			
			if (this.contactoExp.getDepartamentoId() != null){
				this.departamentoIdSelected = this.contactoExp.getDepartamentoId();
				handleMunicipios();
			} else{
				this.departamentoIdSelected = null;
				
				this.listMunicipioByDptos = new ArrayList<SelectItem>();
				this.listMunicipioByDptos.add(new SelectItem(null, "Seleccion un Municipio"));
			}
			
			enabledDisableButton(1);
			enabledDisableButton(3);
			enabledDisableButton(4);
			
		}		
		
		//((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("dbSolicitudesBean",null);
	}
	
	public List<BeanEvaluacion> getListadoEvaluacionesByParam(Solicitud sol, boolean todos) {
		
		List<BeanEvaluacion> listBeanEv = new ArrayList<BeanEvaluacion> ();		
		
		List<Evaluacion> listEval = service.getEvaluaciones(sol);
		
		Mantenedor estadoEval;
		
		for (Evaluacion e : listEval) {
			
			estadoEval = catalogoEstadosEvaluacion.get(e.getEstado().getId());
			
			
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
		System.out.println("Entra a handleMunicipio");
		Contacto cExp;
		this.municipioIdSelected = "1";
		
		if (this.contactoExp == null)
			cExp = this.getSolicitudExp().getContacto();
		else
			cExp = this.contactoExp;
		
		if (cExp != null) {
						
			this.catalogoMunicipiosByDepto = service.getMunicipioDptoByInatec(cExp.getDepartamentoId());			
			
			if (this.catalogoMunicipiosByDepto.size() > 0 ) {
				
				Integer idValor;
				Municipio valor;
				
				Iterator<Integer> claveSet = this.catalogoMunicipiosByDepto.keySet().iterator();
				
				this.listMunicipioByDptos = new ArrayList<SelectItem> ();
				
				this.listMunicipioByDptos.add(new SelectItem(null, "Seleccione un Municipio"));
				
			    while(claveSet.hasNext()){
			    	System.out.println("Crea la lista de Municipios");			    	
			    	idValor = claveSet.next();
			    	valor = this.catalogoMunicipiosByDepto.get(idValor);
			    	System.out.println("IdMunicipio: " + valor.getMunicipio_id());
			    	System.out.println("Descripcion " + valor.getMunicipio_nombre());
			    	this.listMunicipioByDptos.add(new SelectItem(valor.getMunicipio_id(), valor.getMunicipio_nombre()));			    			    			        		        
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
		if (this.selectedLaboral.getPais() != null)
			this.setPaisIdLaboral(this.selectedLaboral.getPais().getId());
		else
			this.setPaisIdLaboral(null);
		
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("idSelectedLaboral",this.selectedLaboral.getId());
		
		//enabledDisableButton(2);
		
		actualizaListaPortafolio (new Integer(1));		
				
	}
		
	public void nuevoLaboral() {		
		limpiarCampos();		
	}
	
	public void rowEditListenerPortafolio(RowEditEvent event){
		System.out.println("El estado seleccionado es: " + (String)event.getObject());
		System.out.println("Archivo Seleccionado " + selectedArchivoId);
		
	}
	
	public String RegistrarEditarEvaluacion() {		
		return "/modulos/solicitudes/registro_evaluacion?faces-redirect=true";		
	}	
	
	public void solicitarCertificacion (){
		
		//Integer estadoActual = new Integer(this.getSolicitudExp().getEstatus().getId());
		Mantenedor estadoActual = this.getSolicitudExp().getEstatus();
		Integer proxEstado = Integer.valueOf(estadoActual.getProximo()); //this.catalogoEstadosSolicitud.get(estadoActual).getProximo();
		
		Mantenedor proximoEstado = this.getCatalogoEstadosSolicitud().get(proxEstado);
		
		if (proxEstado != null)
			this.solicitudExp.setEstatus(proximoEstado);		
		
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
		if (solicitudExp.getContacto().getTelefono1() == null) {
			solicitudExp.getContacto().setTelefono1("");			
		}
		if (solicitudExp.getContacto().getTelefono2() == null) {
			solicitudExp.getContacto().setTelefono2("");			
		}
		
		Contacto contactoExp = (Contacto)service.guardar(solicitudExp.getContacto());
		
		if (contactoExp != null)
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje: ", "Los cambios ha sido aplicados exitosamente !!"));
		else
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje: ", "Error al actualizar los datos del contacto..."));
	      
	}

	public void guardarDatosLaborales() {
		
		System.out.println("Entra al metodo guardarDatosLaborales");
		
		String nombreCargoLaboral;
		
		this.idSeletedLaboral = (Long)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).getAttribute("idSelectedLaboral");
		
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("idSelectedLaboral",null);
		
		nombreCargoLaboral = this.nombreCargo.toUpperCase() + " /  " + this.nombreInstitucion.toUpperCase();
		
		if (this.idSeletedLaboral == null) {
			
			if (this.paisIdLaboral != null){
				Object [] objs =  new Object [] {this.paisIdLaboral};
				paisInstitucion = service.getPaisByNQParam("Pais.findById", objs);
			} 
				
						
			this.selectedLaboral = new Laboral (this.solicitudExp.getContacto(), // contacto, 
									   this.tipoLaboral, // tipo, 
									   nombreCargoLaboral.toUpperCase(), // nombre,
									   this.descripcionCargo, // descripcion, 
									   this.nombreInstitucion.toUpperCase(), // institucion, 
									   this.paisInstitucion, // pais,
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
			this.selectedLaboral.setPais(this.paisInstitucion);			
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
		
		Integer estadoSolicitud;
		
		Integer estadoInicial;
		
		if (sol != null){		
			estadoInicial = service.getMantenedorMinByTipo(sol.getTipomantenedorestado()).getId();
			estadoSolicitud = sol.getEstatus().getId();
		} else{
			estadoInicial = null;
			estadoSolicitud = null;
		}
		
		
		switch(opcion) {
			case 1:	{				
				if ((sol == null) || (estadoInicial == estadoSolicitud)){
					this.setDisabledBtnActualizaContacto(false);
				} else {				
				    this.setDisabledBtnActualizaContacto(true);					
				}
				
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
				if ((sol == null) || (estadoInicial == estadoSolicitud)){
					this.setDisabledBtnAgregaLaborales(false);
				} else {				
					this.setDisabledBtnAgregaLaborales(true);					
				}
								
				break;
			}
			case 4: {
				if ((sol == null) || (estadoInicial == estadoSolicitud)){
					this.setDisabledBtnAgregaEvaluacion(true);
				} else {				
					this.setDisabledBtnAgregaEvaluacion(false);					
				}				
				break;
			}
			default: 
				break;			
		}
			
	}
	
	public void limpiarCampos (){
				
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
		
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("idSelectedLaboral",null);
			
	}	
	
	
	public void nuevoArchivo() {
		archivoExp = new Archivo();
		
		listEvalBySolicitud = new ArrayList<SelectItem> ();
		List<Evaluacion> listE = this.getListEvaluaciones();
		
		System.out.println("Obtiene el listado de las evaluaciones");
		listEvalBySolicitud.add(new SelectItem(null, "Seleccione la evaluacion"));
		for (Evaluacion dato : listE) {
			//listEvalBySolicitud.add(new SelectItem(dato.getId(), dato.getUnidad().getCompetenciaDescripcion()));
			listEvalBySolicitud.add(new SelectItem(dato.getId(), util.getCompetenciaDescripcion(dato.getUnidad())));
		}
	}

	public void nuevoPortafolio (){
		System.out.println("Agrega archivo de evidencia al dato laboral/academico: ");
		
		if (this.selectedLaboral == null)
			
			this.idSeletedLaboral = (Long)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).getAttribute("idSelectedLaboral");
		else
			this.idSeletedLaboral = this.selectedLaboral.getId();		
			
		if (this.idSeletedLaboral != null) {
			
			if (this.selectedLaboral == null)
				
				this.selectedLaboral = service.getLaboralById(this.idSeletedLaboral);
			
				((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("selectedLaboral",this.selectedLaboral);
			
		}
		 
		
	}
	
	public void guardarArchivo() {
		
		this.archivoExp = (Archivo)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).getAttribute("archivoExp");
		
		if (archivoExp != null) {
			//Asignando valores de pantalla
			
			archivoExp.setNombre(this.getNombreArchivoExp());
			archivoExp.setVersion(this.getVersionArchivoExp());
			archivoExp.setDescripcion(this.getDescripcionArchivoExp());
						
			archivoExp = (Archivo) service.guardar(archivoExp);
			
			if (archivoExp != null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SCCL - Mensaje: ", "Los cambios ha sido aplicados exitosamente !!"));
				((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("archivoExp",null);
				((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("selectedLaboral",null);
				((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("idSelectedLaboral",null);				
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SCCL - Mensaje: ", "Error al grabar el archivo. Favor revisar..."));
			}
		}		
		
	}
	
	public void cancelaPortafolio (){
		
		this.setNombreArchivoExp(null);
		this.setDescripcionArchivoExp(null);
		this.setVersionArchivoExp(null);
		this.setTipoArchivoExp(null);
		this.setNombreRealArchivoExp(null);
		this.setSizeArchivoExp(null);
		
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("archivoExp",null);
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("selectedLaboral",null);
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("idSelectedLaboral",null);
	}
	
	public void editarPortafolio(){		
		Object [] objs;
		if (selectedArchivoId != null){			
			objs =  new Object [] {selectedArchivoId};
			this.archivoExp = service.getArchivoOneByParam("Archivo.findById", objs);
			
			this.selectedLaboral = archivoExp.getLaboral();
		}
	}
	
	public void aprobarPortafolio(){
		System.out.println("selectedLaboral " + selectedLaboral);
		System.out.println("archivoExp "+ archivoExp);
		System.out.println("Indica Aprobado: "+ archivoExp.getAprobado());
		
		Mantenedor estadoArchivo = archivoExp.getEstado();
		
		Integer  proxEstado = Integer.valueOf(estadoArchivo.getProximo());
		
		if (proxEstado != null){
			archivoExp.setEstado(this.catalogoEstadosPortafolio.get(proxEstado));
		}
				
		archivoExp = (Archivo) service.guardar(archivoExp);
		
		if (archivoExp != null){
			
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
				if (this.solicitudExp != null){
					Contacto c = solicitudExp.getContacto();
					objs =  new Object [] {c.getId()};
					this.listPortafolioContacto = service.getArchivoByParam ("Archivo.findByContactoId", objs);
				}
		}
	}
	
	public void uploadFile(FileUploadEvent event){
		
		Date fechaAhora = new Date();
		String tituloMsg = "";
		String mensaje = "";
		boolean isError = false;
		
		FacesMessage msg;   
	    
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		
	    String directorio = ec.getRealPath("/portafolio");	    
	    Usuario u = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());
	    String nombreFile;
	    String nombrePropietario;
	    Long   sizeArchivo;
	    int    pos;
	    String extension;
	    	    	    
		try {
		
			this.file = event.getFile();			
	
			nombreFile = file.getFileName().trim();
			
			this.nombreRealArchivoExp = nombreFile;
			
			pos=nombreFile.indexOf(".");
			
			extension = nombreFile.substring(pos); 
			
			if ((this.nombreRealArchivoExp.toLowerCase().endsWith(".pdf")) || (this.nombreRealArchivoExp.toLowerCase().endsWith(".png"))){
							
				nombrePropietario = u.getUsuarioAlias();
				
				sizeArchivo = (file.getSize()) / 1024; // El tama�o del archivo en KB
				
				this.sizeArchivoExp = String.valueOf(sizeArchivo) + " KB";
				
				archivoExp.setNombre(nombreFile);
				archivoExp.setSize(this.sizeArchivoExp);			
				archivoExp.setFecha(fechaAhora);					
				archivoExp.setNombreReal(this.nombreRealArchivoExp);
				archivoExp.setRuta(String.format(directorio+"/%s",nombreFile));
				archivoExp.setPropietario(nombrePropietario);
				archivoExp.setTipo(extension);
				archivoExp.setCategoria(extension);
				archivoExp.setIcono(nombreFile);
				archivoExp.setVersion("1");
				archivoExp.setDescripcion(this.nombreRealArchivoExp);
																	
				FileOutputStream fos = new FileOutputStream(String.format(directorio+"/%s",file.getFileName()));			
	            fos.write(file.getContents());
	            fos.flush();
	            fos.close();            
	            
	            String tipoMantenedorEstado = archivoExp.getTipoMantenedorEstado();
				
				Mantenedor primerEstado = service.getMantenedorMinByTipo(tipoMantenedorEstado);
				
				archivoExp.setEstado(primerEstado);
				
				this.selectedLaboral = (Laboral)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).getAttribute("selectedLaboral");
				
				archivoExp.setLaboral(this.selectedLaboral);
				
				archivoExp = (Archivo) service.guardar(archivoExp);
				
				if (archivoExp != null){		            
					mensaje = nombreFile + " ha sido cargado al servidor.";
					isError = false;
					((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("archivoExp",this.archivoExp);
				}else {
					isError = true;
				}
				
				
	            
			} else {
				 mensaje = nombreFile + "Unicamente se permite subir archivo PDF y PNG. Favor revisar...." + nombreFile.toLowerCase();
				 isError = true;
			}
	
		} catch (IOException e) {
			isError = true;
			e.printStackTrace();
		}		
		
	
		if (isError){
			tituloMsg = "Proceso Incompleto: ";
			mensaje = (mensaje.isEmpty()) ? "Se genero un error al subir el archivo. Favor revisar...." : mensaje;
		} else {
			tituloMsg = "Proceso Exitoso !!!";
		}
		
		msg = new FacesMessage(tituloMsg, mensaje);
		
		FacesContext.getCurrentInstance().addMessage(null, msg);  
	        			
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
			
			if (this.getListDatosLaborales().size() == 0) {
				textMsg = (textMsg.isEmpty()) ? "Debe indicar los datos Laborales / Academicos." : ". Debe indicar los datos Laborales / Academicos.";
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
	
	public String inicializaParametros (ActionEvent event){
		//Recibiendo el parametro del IdSolicitud que se va a editar en el Expediente.
		System.out.println("ExpedienteManagedBean. Obtiendo los parametros ");
		
		System.out.println("IdSolicitud = " + FacesUtil.getActionAttribute(event, "idSolicitudExp"));
		
		Long idSolicitud = Long.valueOf(FacesUtil.getActionAttribute(event, "idSolicitudExp"));
	
		System.out.println("Ya convierte a Long " + idSolicitud);		
		
		this.solicitudExp = service.getSolicitudById(idSolicitud);
		
		System.out.println("Obtiene el objeto solicitud " + solicitudExp.getId());
	
		return "/modulos/solicitudes/expediente?faces-redirect=true";
		
	}
		

	
}
