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
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import model.Archivo;
import model.Contacto;
import model.Evaluacion;
import model.Laboral;
import model.Mantenedor;
import support.Pais;
import model.Solicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;

import controller.LoginController;
import service.IService;
import support.Departamento;
import support.FacesUtil;
import support.Municipio;
import util.ValidatorUtil;

@Component
@Scope(value="view")
public class ExpedienteManagedBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;	
	@Autowired
	private LoginController controller;
	private Solicitud solicitudExp;
	private Contacto contactoExp;

	private List<Laboral> listDatosLaborales;
	private List<Laboral> listDatosEstudios;
	private List<Laboral> listDatosCalificacion;
	private List<Laboral> listDatosCertificaciones;
	private Laboral selectedLaboral;
	private Laboral laboral;
	private Map<String, Pais> paises;
	private String selectedPais;
	private Integer tipoLaboral;
	private List<Evaluacion> evaluaciones;
	private List<Archivo> evidencias;
	private Archivo selectedEvidencia;

	private List<Archivo> listPortafolioLaboral;
	private List<Evaluacion> listEvaluaciones;
	private Evaluacion seletedEvaluacion;
	private Evaluacion selectedBeanEvaluacion;
	
	private List<SelectItem> listTipoDatosLaborales;
	private List<SelectItem> listEstadosPortafolio;
	
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
		
	private List<SelectItem> listEvalBySolicitud;	
	
	private Map<Integer, Mantenedor> catalogoTipoDatosLaborales;
	private Map<Integer, Municipio> catalogoMunicipiosByDepto;
	
	private List<SelectItem> listDeptos;
	private List<SelectItem> listMunicipioByDptos;
	private List<SelectItem> listPaises;
	private List<SelectItem> listGenero;
	private List<SelectItem> listNacionalidades;
	
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
				
		listDatosLaborales = new ArrayList<Laboral> ();
		listDatosEstudios = new ArrayList<Laboral> ();
		listDatosCalificacion = new ArrayList<Laboral> ();
		listDatosCertificaciones = new ArrayList<Laboral> ();
		listEvaluaciones = new ArrayList<Evaluacion> ();
		
		listPortafolioContacto = new ArrayList<Archivo> ();
		listPortafolioLaboral = new ArrayList<Archivo> ();
		
		listTipoDatosLaborales = new ArrayList<SelectItem> ();
		listEstadosPortafolio  = new ArrayList<SelectItem> ();
		
		listEvalBySolicitud = new ArrayList<SelectItem> ();	
		
		catalogoTipoDatosLaborales = new HashMap<Integer, Mantenedor>();
						
		catalogoMunicipiosByDepto = new HashMap<Integer, Municipio>();
		
		listDeptos = new ArrayList<SelectItem> ();
		listMunicipioByDptos = new ArrayList<SelectItem> ();
		listPaises = new ArrayList<SelectItem> ();
		listGenero = new ArrayList<SelectItem> ();
		
		laboral = new Laboral();
		evidencias = new ArrayList<Archivo>();

		listNacionalidades = new ArrayList<SelectItem>();
	}
	
	@PostConstruct
	private void init(){
		
		Long candidatoId = (Long)FacesUtil.getParametroSession("candidatoId");
		contactoExp = service.getContactoById(candidatoId);
	
		List<Mantenedor> listaCatalogo = service.getMantenedoresByTipo(new Integer(5));		
		for (Mantenedor dato : listaCatalogo) {
			this.catalogoTipoDatosLaborales.put(dato.getId(), dato);			
			this.listTipoDatosLaborales.add(new SelectItem(dato.getId(), dato.getValor()));			
		}	
		
		//Obtiene el catalogo de los Departamentos					
		listDeptos = new ArrayList<SelectItem> ();
		listDeptos.add(new SelectItem(null, "Seleccione un Departamento"));
		
		if (service.getCatalogoDepartamentos().size() > 0 ) {
			List<Departamento> listaDeptos = new ArrayList<Departamento> (service.getCatalogoDepartamentos().values());
			
			for (Departamento dpto : listaDeptos) 
				this.listDeptos.add(new SelectItem(dpto.getDpto_id(), dpto.getDpto_nombre()));		       
		}
			
		//Obtiene el catalogo de los Paises
		List<Pais> paises = new ArrayList<Pais>(service.getCatalogoPaises().values());
		listPaises = new ArrayList<SelectItem> ();
		listPaises.add(new SelectItem(null, "Seleccione un pais"));
		
		for (Pais p : paises){
			listPaises.add(new SelectItem(p.getCodigo(), p.getNombre()));
		}
		
		archivoExp = new Archivo();
		solicitudExp = service.getSolicitudById(new Long(1));
		contactoExp = service.getContactoById(new Long(7));
		evaluaciones = service.getEvaluacionesBySolicitudId(new Long(7));
		setEvidencias(service.getArchivosByContactoId(candidatoId));
	}

	public List<Archivo> getEvidencias(){
		return evidencias;
	}
	
	public void setEvidencias(List<Archivo> archivos){
		this.evidencias = archivos;
	}
	
	public Archivo getSelectedEvidencia(){
		return selectedEvidencia;
	}
	
	public void setSelectedEvidencia(Archivo evidencia){
		this.selectedEvidencia = evidencia;
	}
	
	public List<SelectItem> getListNacionalidades() {
		return listNacionalidades;
	}

	public void setListNacionalidades(List<SelectItem> listNacionalidades) {
		this.listNacionalidades = listNacionalidades;
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

	public void setContactoExp(Contacto contacto) {
		this.contactoExp = contacto;
	}

	public List<SelectItem> getListEstadosPortafolio() {
		return listEstadosPortafolio;
	}

	public void setListEstadosPortafolio(List<SelectItem> listEstadosPortafolio) {
		this.listEstadosPortafolio = listEstadosPortafolio;
	}
	
	public Laboral getLaboral(){
		return laboral;
	}
	
	public void setLaboral(Laboral laboral){
		this.laboral = laboral;
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

	public List<SelectItem> getListDeptos() {		
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

	public Map<Integer, Municipio> getCatalogoMunicipiosByDepto() {
		return catalogoMunicipiosByDepto;
	}

	public void setCatalogoMunicipiosByDepto(Map<Integer, Municipio> catalogoMunicipiosByDepto) {
		this.catalogoMunicipiosByDepto = catalogoMunicipiosByDepto;
	}

	public boolean isDisableSolicitarCertificacion() {
		return disableSolicitarCertificacion;
	}

	public void setDisableSolicitarCertificacion(boolean disableSolicitarCertificacion) {
		this.disableSolicitarCertificacion = disableSolicitarCertificacion;
	}

	public Solicitud getSolicitudExp() {		
		return solicitudExp;
	}

	public void setSolicitudExp(Solicitud solicitudExp) {
		this.solicitudExp = solicitudExp;
	}

	public List<Laboral> getListDatosLaborales() {		
		this.listDatosLaborales = (this.getContactoExp() != null) ? service.getListLaboralByTipo(new Integer(23), this.getContactoExp()) : this.listDatosLaborales;
		
		return this.listDatosLaborales;
	}

	public void setListDatosLaborales(List<Laboral> listDatosLaborales) {
		this.listDatosLaborales = listDatosLaborales;
	}

	public List<Laboral> getListDatosEstudios() {
		listDatosEstudios = (getContactoExp() != null) ? service.getListLaboralByTipo(new Integer(24), getContactoExp()) : listDatosEstudios;
		return listDatosEstudios;
	}

	public void setListDatosEstudios(List<Laboral> listDatosEstudios) {
		this.listDatosEstudios = listDatosEstudios;
	}

	public List<Laboral> getListDatosCalificacion() {
		this.listDatosCalificacion = (getContactoExp() != null) ? service.getListLaboralByTipo(new Integer(25), getContactoExp()) : listDatosCalificacion;
		return listDatosCalificacion;
	}

	public void setListDatosCalificacion(List<Laboral> listDatosCalificacion) {
		this.listDatosCalificacion = listDatosCalificacion;
	}

	public List<Laboral> getListDatosCertificaciones() {
		this.listDatosCertificaciones = (getContactoExp() != null) ? service.getListLaboralByTipo(new Integer(26), getContactoExp()) : listDatosCertificaciones;
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

	public List<Evaluacion> getListBeanEval() {
		if (this.solicitudExp != null){
			//listBeanEval = getListadoEvaluacionesByParam(this.solicitudExp, true);
		}
		//return listBeanEval;
		return null;
	}		

	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */
	
	//public void setListBeanEval(List<BeanEvaluacion> listBeanEval) {
	public void setListBeanEval(List<Evaluacion> listBeanEval) {
		//this.listBeanEval = listBeanEval;
	}

	public Integer getTipoLaboral() {
		return tipoLaboral;
	}

	public void setTipoLaboral(Integer tipoLaboral) {
		this.tipoLaboral = tipoLaboral;
	}		
	/*
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
	*/
	
	/*
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
	*/

	/*
	public void setEstadoSiguiente(String estadoSiguiente) {
		this.estadoSiguiente = estadoSiguiente;
	}
	
	public Long getIdSeletedLaboral() {
		return idSeletedLaboral;
	}

	public void setIdSeletedLaboral(Long idSeletedLaboral) {
		this.idSeletedLaboral = idSeletedLaboral;
	}
	*/

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

	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */

	//public BeanEvaluacion getSelectedBeanEvaluacion() {
	public Evaluacion getSelectedBeanEvaluacion() {
		return selectedBeanEvaluacion;
	}

	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */

	//public void setSelectedBeanEvaluacion(BeanEvaluacion selectedBeanEvaluacion) {
	public void setSelectedBeanEvaluacion(Evaluacion selectedBeanEvaluacion) {
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

	
	public List<Evaluacion> getListadoEvaluacionesByParam(Solicitud sol, boolean todos) {
		if(todos)
			return service.getEvaluaciones(sol);
		else
			return service.getEvaluacionesPendientes(sol);		
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
			cExp = (this.getSolicitudExp().getContacto() != null ) ? this.getSolicitudExp().getContacto() : null;
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
			    	idValor = claveSet.next();
			    	valor = this.catalogoMunicipiosByDepto.get(idValor);
			    	this.listMunicipioByDptos.add(new SelectItem(valor.getMunicipio_id(), valor.getMunicipio_nombre()));			    			    			        		        
			    }
			}
			
			
		}
	}

	public void editarLaboral(Laboral laboral){
		service.getLaboralById(new Long(1));
		setLaboral(laboral);	
		//actualizaListaPortafolio (new Integer(1));
	}

	public void nuevoLaboral() {		
		limpiarCampos();		
	}

	public void rowEditListenerPortafolio(RowEditEvent event){
		System.out.println("El estado seleccionado es: " + (String)event.getObject());
		System.out.println("Archivo Seleccionado " + selectedArchivoId);
		
	}
	 
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Redirecciona al facet registro_evaluacion.xhtml.
	public String RegistrarEditarEvaluacion() {		
		return "/modulos/solicitudes/registro_evaluacion?faces-redirect=true";		
	}	

	public String actualizarContacto(Contacto contacto) {

		if(contacto.getNumeroIdentificacion()==null){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique la cédula...", true);
			return null;
		}
		
		if(!ValidatorUtil.validateCedula(contacto.getNumeroIdentificacion())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Cédula invalida...", true);
			return null;			
		}
		
		if(contacto.getFechaNacimiento()==null){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique la fecha de nacimiento...", true);
			return null;
		}
		
		Date fecha = ValidatorUtil.obtenerFechaNacimientoDeCedula(contacto.getNumeroIdentificacion());	
		if(fecha.compareTo(contacto.getFechaNacimiento()) != 0){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "La fecha de nacimiento no coincide con la cédula.", true);
			return null;						
		}

		/*if(contacto.getMunicipioId()==null){
			isError = true;
			mensaje = "Debe indicar el departamento y municipio...";
			FacesUtil.getMensaje("SCCL - Mensaje: ", mensaje, isError);
			return null;
		}*/
		
		if(contacto.getSexo()==null){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique el sexo...", true);
			return null;
		}
		
		if (contacto.getTelefono1() == null) {
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique el número de teléfono...", true);
			return null;
		}
		
		if(!ValidatorUtil.validatePhone(contacto.getTelefono1())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "El número de teléfono es invalido...", true);
			return null;
		}
		
		if(contacto.getTelefono2() != null && !ValidatorUtil.validatePhone(contacto.getTelefono2())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "El número de celular es invalido...", true);
			return null;
		}
		
		if (contacto.getDireccionActual() == null) {
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique la dirección...", true);
			return null;	
		}

		if (contacto.getTelefono2() == null) {
			contacto.setTelefono2("");	
		}
		
		if (contacto.getCorreo1() == null) {
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique el correo electronico...", true);
			return null;
		}

		if(!ValidatorUtil.validateEmail(contacto.getCorreo1())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "El correo electronico no es válido...", true);
			return null;
		}
		
		if(contacto.getNacionalidadId()==null){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique la nacionalidad...", true);
			return null;
		}
					
		contactoExp = (Contacto)service.guardar(contacto);

		if (contactoExp != null){
			return "/modulos/solicitudes/candidatos?faces-redirect=true";
		}
		else{
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Error al actualizar datos del candidato...", true);
			return null;
		}
	}

	public void guardarDatosLaborales(Laboral laboral) {
		
		laboral.setInstitucion("test");
		laboral.setNombre(laboral.getCargo());
		laboral.setTipo(tipoLaboral);
		laboral.setContacto(contactoExp);
		laboral = (Laboral)service.guardar(laboral);
		
		if (laboral != null)
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Los cambios ha sido aplicados exitosamente !!", false);						
		else 
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Se genero un error al grabar los datos laborales / academicos. Favor revisar...", true);			

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
			default: 
				break;			
		}
			
	}

	public void limpiarCampos (){
				
		this.setSelectedLaboral(null);
		this.listPortafolioLaboral = new ArrayList<Archivo> ();		
		FacesUtil.setParamBySession("idSelectedLaboral", null);
	}	
	
	public void nuevoArchivo() {
		archivoExp = new Archivo();
		
		listEvalBySolicitud = new ArrayList<SelectItem> ();
		List<Evaluacion> listE = this.getListEvaluaciones();
		
		listEvalBySolicitud.add(new SelectItem(null, "Seleccione la evaluacion"));
		for (Evaluacion dato : listE) {
			listEvalBySolicitud.add(new SelectItem(dato.getId(), service.getCompetenciaDescripcion(dato.getUnidad())));
		}
	}

	public void nuevoPortafolio (Laboral laboral){
	}

	public void guardarArchivo() {
		
		this.archivoExp = null;
						
		if (archivoExp != null) {
			
			archivoExp.setNombre(this.getNombreArchivoExp());
			archivoExp.setVersion(this.getVersionArchivoExp());
			archivoExp.setDescripcion(this.getDescripcionArchivoExp());
						
			archivoExp = (Archivo) service.guardar(archivoExp);
			
			if (archivoExp != null){				
				FacesUtil.getMensaje("SCCL - Mensaje: ", "Los cambios ha sido aplicados exitosamente !!", false);
				FacesUtil.setParamBySession("archivoExp", null);
				FacesUtil.setParamBySession("selectedLaboral", null);
				FacesUtil.setParamBySession("idSelectedLaboral", null);								
			}else{
				FacesUtil.getMensaje("SCCL - Mensaje: ", "Error al grabar el archivo. Favor revisar...", true);				
			}
		}		
		
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Cancela el registro en el portafolio.
	public void cancelaPortafolio (){
		
		this.setNombreArchivoExp(null);
		this.setDescripcionArchivoExp(null);
		this.setVersionArchivoExp(null);
		this.setTipoArchivoExp(null);
		this.setNombreRealArchivoExp(null);
		this.setSizeArchivoExp(null);
		
		FacesUtil.setParamBySession("archivoExp", null);
		FacesUtil.setParamBySession("selectedLaboral", null);
		FacesUtil.setParamBySession("idSelectedLaboral", null);		
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Edita el portafolio.
	public void editarPortafolio(){		
		Object [] objs;
		if (selectedArchivoId != null){			
			objs =  new Object [] {selectedArchivoId};
			this.archivoExp = service.getArchivoOneByParam("Archivo.findById", objs);
			
			this.selectedLaboral = archivoExp.getLaboral();
		}
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Asigna el status aprobado al portafolio.
	public void aprobarPortafolio(){
			
		Mantenedor estadoArchivo = archivoExp.getEstado();
		
		Integer  proxEstado = Integer.valueOf(estadoArchivo.getProximo());
		
		if (proxEstado != null){
			archivoExp.setEstado(this.service.getCatalogoPortafolio().get(proxEstado));
		}
				
		archivoExp = (Archivo) service.guardar(archivoExp);
		
		if (archivoExp != null){
			
		}
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || actualiza lista de portafolio.
	public void actualizaListaPortafolio (Integer tipo){
		Object [] objs;
		if (tipo == 1) {
			System.out.println("Consulta el listado de Archivos por datos laborales");			
			if (selectedLaboral != null) {			
				System.out.println("Datos laborales " + selectedLaboral.getId());
				 objs =  new Object [] {selectedLaboral.getId()};
				 System.out.println("Ejecuta la consulta");
				 //this.listPortafolioLaboral = service.getArchivoByParam ("Archivo.findByLaboralId", objs);				
			}
		} else {		
				if (this.solicitudExp != null){
					Contacto c = solicitudExp.getContacto();
					objs =  new Object [] {c.getId()};
					//this.listPortafolioContacto = service.getArchivoByParam ("Archivo.findByContactoId", objs);
				}
		}
	}

	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Sube el archivo.
	public void uploadFile(FileUploadEvent event){
		
		Date fechaAhora = new Date();
		String tituloMsg = "";
		String mensaje = "";
		boolean isError = false;
		
		FacesMessage msg;   
	    
		//ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		//Obtener nombre del directorio fisico del mantenedor de archivo 
	    String directorio = service.getMantenedorById(58).getValor();	   
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
							
				nombrePropietario = this.contactoExp.getNombreCompleto();
				
				sizeArchivo = (file.getSize()) / 1024; // El tamaño del archivo en KB
				
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
				
				this.selectedLaboral = (Laboral)FacesUtil.getParametroSession("selectedLaboral");
										
				archivoExp.setLaboral(this.selectedLaboral);
				
				archivoExp = (Archivo) service.guardar(archivoExp);
				
				if (archivoExp != null){		            
					mensaje = nombreFile + " ha sido cargado al servidor.";
					isError = false;
					FacesUtil.setParamBySession("archivoExp", "archivoExp");					
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

	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Valida que exista la informacion minima en el contacto para pasar la solicitud al estatus registrado.
	public void validaRegistroCV (){
		FacesMessage msg;
		String       textMsg = "";
		String       titulo = "";
		
		Contacto solicitante = this.getContactoExp();
		
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
		
	public String getUCDescripcion(Long id){
		String unidad = "N/D";
		
		if(id != null){
			//unidad = service.getCatalogoUnidades().get(id).getDescripcion();
			return unidad;
		}
		else
			return "N/D";
	}
	
	public String getSelectedPais(){
		return selectedPais;
	}
	
	public void setSelectedPais(String pais){
		this.selectedPais = pais;
	}
	
	public void onLaboralSelect(SelectEvent event) {
		setSelectedLaboral((Laboral) event.getObject());
		System.out.println("Seleciono el laboral con id:"+selectedLaboral.getId());
		setSelectedPais(selectedLaboral.getPais()); 
    }
  
    public void onLaboralUnselect(UnselectEvent event) {
    }
    
    public List<Evaluacion> getEvaluaciones(){
    	return evaluaciones;
    }
}