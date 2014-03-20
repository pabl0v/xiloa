package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import model.Archivo;
import model.Certificacion;
import model.Contacto;
import model.Evaluacion;
import model.EvaluacionUnidad;
import model.Laboral;
import model.Mantenedor;
import model.Pais;
import model.Solicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import service.IService;
import support.Departamento;
import support.FacesUtil;
import support.Item;
import support.Municipio;
import util.Global;

@Component
@Scope(value="view")
public class ExpEvalManagedBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	
	private Solicitud solicitudExp;
	private Contacto contactoExp;
	
	private Map<Integer, List<Laboral>> laborales;
	//private List<Laboral> listDatosLaborales;
	//private List<Laboral> listDatosEstudios;
	//private List<Laboral> listDatosCalificacion;
	//private List<Laboral> listDatosCertificaciones;

	private List<Evaluacion> listEvaluaciones;
	private List<Item> listEvaluacionesUnidad;
	
	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */
	
	//private List<BeanEvaluacion> listBeanEval;
	//private List<BeanEvaluacion> listBeanEvalFormacion;
	
	private List<Evaluacion> listBeanEval;
	private List<Evaluacion> listBeanEvalFormacion;	
	
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
	
	private Mantenedor estadoActual;
	private Mantenedor estadoSiguiente;	
	
	private Long idSeletedLaboral;
	private List<SelectItem> listTipoDatosLaborales;
	private List<SelectItem> listEstadosPortafolio;
	private Laboral selectedLaboral;
	private Laboral nuevoLaboral;
	
	private Evaluacion seletedEvaluacion;
	
	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */

	//private BeanEvaluacion selectedBeanEvaluacion;
	private Evaluacion selectedBeanEvaluacion;
	
	private UploadedFile file;
	
	private Archivo archivoExp;
	private Long evalIdByArchivoExp;
	private List<Archivo> listPortafolio;
	private List<Archivo> listPortafolioContacto;
	
	private boolean disableSolicitarCertificacion;
	private boolean disablePortafolio;
	private boolean disabledUploadFile;
	private boolean indicaCVFull;
	private boolean disabledBtnAgregaEvaluacion;
	private boolean disabledConcluido;
	//dchavez 03/03/2014. Anulacion de solicitud
	private boolean disabledAnulado;
	
	private List<SelectItem> listEvalBySolicitud;	

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
	
	private boolean disabledConvocar;
	private boolean disabledAsesorar;
	private boolean disabledCerrar;
	
	private Mantenedor generoContacto;
	
	private Municipio municipioContacto;
	
	private Departamento deptoContacto;
	
	private String tipoEstadosPortafolio;
	
	private List<EvaluacionUnidad> listaEvalUnidad;
	
	private EvaluacionUnidad selectedEvalUnidad;

	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Constructor de la clase.
	public ExpEvalManagedBean() {
		super();
		
		this.setDisableSolicitarCertificacion(true);
		this.setDisablePortafolio(true);
		this.setDisabledUploadFile(true);
		this.setIndicaCVFull(false);
		this.setDisabledBtnAgregaEvaluacion(true);	
		this.setDisabledConcluido(false);
		
		laborales = new HashMap<Integer, List<Laboral>>();
		
		//listDatosLaborales = new ArrayList<Laboral> ();
		//listDatosEstudios = new ArrayList<Laboral> ();
		//listDatosCalificacion = new ArrayList<Laboral> ();
		//listDatosCertificaciones = new ArrayList<Laboral> ();
		listEvaluaciones = new ArrayList<Evaluacion> ();
		
		/**
		 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
		 */
		
		//listBeanEval = new ArrayList<BeanEvaluacion> ();
		//listBeanEvalFormacion = new ArrayList<BeanEvaluacion> ();
		
		listBeanEval = new ArrayList<Evaluacion> ();
		listBeanEvalFormacion = new ArrayList<Evaluacion> ();
		
		listPortafolioContacto = new ArrayList<Archivo> ();
		listPortafolioLaboral = new ArrayList<Archivo> ();
		
		listTipoDatosLaborales = new ArrayList<SelectItem> ();
		listEstadosPortafolio  = new ArrayList<SelectItem> ();
		
		listEvalBySolicitud = new ArrayList<SelectItem> ();	
					
		catalogoMunicipiosByDepto = new HashMap<Integer, Municipio>();
				
		listDeptos = new ArrayList<SelectItem> ();
		listMunicipioByDptos = new ArrayList<SelectItem> ();
		listPaises = new ArrayList<SelectItem> ();
		listGenero = new ArrayList<SelectItem> ();
				
		nuevoLaboral = new Laboral();
		
		listaEvalUnidad = new ArrayList<EvaluacionUnidad> ();
		
		//dchavez> 01/03/2014: lista que retorna el resumen de evaluaciones por unidad. Pestana "Evaluacion por Unidad"
		listEvaluacionesUnidad = new ArrayList<Item>();
		
		selectedEvalUnidad = null;
		
	}	
	
	public EvaluacionUnidad getSelectedEvalUnidad() {
		return selectedEvalUnidad;
	}

	public void setSelectedEvalUnidad(EvaluacionUnidad selectedEvalUnidad) {
		this.selectedEvalUnidad = selectedEvalUnidad;
	}

	/*public List<EvaluacionUnidad> getListaEvalUnidad() {
		if (this.solicitudExp != null){
			List<EvaluacionUnidad> lista = service.getListEvalUnidad(this.solicitudExp.getId());
			listaEvalUnidad = lista;
		}
		return listaEvalUnidad;
	}*/
	
	/**
	 * 
	 * dchavez: 01/03/2014. Retorna lista que indica si esta aprobada o reprobada cada unidad de competencia de forma global
	 * 
	 */
	public List<Item> getListEvaluacionesUnidad() {
		return listEvaluacionesUnidad;
	}
	
	public void setListaEvalUnidad(List<EvaluacionUnidad> listaEvalUnidad) {
		this.listaEvalUnidad = listaEvalUnidad;
	}

	public boolean isDisabledConcluido() {
		return disabledConcluido;
	}

	public void setDisabledConcluido(boolean disabledConcluido) {
		this.disabledConcluido = disabledConcluido;
	}

	//dchavez 03/03/2014. Anulacion de solicitudes
	public boolean isDisabledAnulado() {
		return disabledAnulado;
	}
	
	//dchavez 03/03/2014. Anulacion de solicitudes
	public void setDisabledAnulado(boolean disabledAnulado) {
		this.disabledAnulado = disabledAnulado;
	}
	
	public String getTipoEstadosPortafolio() {
		return tipoEstadosPortafolio;
	}


	public void setTipoEstadosPortafolio(String tipoEstadosPortafolio) {
		this.tipoEstadosPortafolio = tipoEstadosPortafolio;
	}


	public Municipio getMunicipioContacto() {
		return municipioContacto;
	}


	public void setMunicipioContacto(Municipio municipioContacto) {
		this.municipioContacto = municipioContacto;
	}


	public Departamento getDeptoContacto() {
		return deptoContacto;
	}


	public void setDeptoContacto(Departamento deptoContacto) {
		this.deptoContacto = deptoContacto;
	}


	public Mantenedor getGeneroContacto() {
		return generoContacto;
	}


	public void setGeneroContacto(Mantenedor generoContacto) {
		this.generoContacto = generoContacto;
	}


	public boolean isDisabledConvocar() {
		return disabledConvocar;
	}


	public void setDisabledConvocar(boolean disabledConvocar) {
		this.disabledConvocar = disabledConvocar;
	}


	public boolean isDisabledAsesorar() {
		return disabledAsesorar;
	}


	public void setDisabledAsesorar(boolean disabledAsesorar) {
		this.disabledAsesorar = disabledAsesorar;
	}


	public boolean isDisabledCerrar() {
		return disabledCerrar;
	}


	public void setDisabledCerrar(boolean disabledCerrar) {
		this.disabledCerrar = disabledCerrar;
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

	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */
	
	//public List<BeanEvaluacion> getListBeanEvalFormacion() {
	public List<Evaluacion> getListBeanEvalFormacion() {
		if (this.solicitudExp != null){			
			listBeanEvalFormacion = getListadoEvaluacionesByParam(this.solicitudExp, false);
		}
		return listBeanEvalFormacion;
	}

	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */
	
	//public void setListBeanEvalFormacion(List<BeanEvaluacion> listBeanEvalFormacion) {
	public void setListBeanEvalFormacion(List<Evaluacion> listBeanEvalFormacion) {
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
		
		if (service.getCatalogoEstadoSolicitud().size() > 0){
			listDeptos.add(new SelectItem(null, "Seleccion un Departamento"));
			for (Departamento d : service.getCatalogoDepartamentos().values()){
				this.listDeptos.add(new SelectItem(d.getDpto_id(), d.getDpto_nombre()));
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
		return laborales.get(13);
		/*if (this.solicitudExp != null){
			this.listDatosLaborales = service.getListLaboralByTipo(new Integer(13), this.solicitudExp.getContacto());
		}
		return this.listDatosLaborales;*/
	}

	/*public void setListDatosLaborales(List<Laboral> listDatosLaborales) {
		this.listDatosLaborales = listDatosLaborales;
	}*/

	public List<Laboral> getListDatosEstudios() {
		return laborales.get(14);
		/*if (this.solicitudExp != null){
			this.listDatosEstudios = service.getListLaboralByTipo(new Integer(14), this.solicitudExp.getContacto());
		}
		return listDatosEstudios;*/
	}

	/*public void setListDatosEstudios(List<Laboral> listDatosEstudios) {
		this.listDatosEstudios = listDatosEstudios;
	}*/

	public List<Laboral> getListDatosCalificacion() {
		return laborales.get(15);
		/*if (this.solicitudExp != null){
			this.listDatosCalificacion = service.getListLaboralByTipo(new Integer(15), this.solicitudExp.getContacto());
		}
		return listDatosCalificacion;*/
	}

	/*public void setListDatosCalificacion(List<Laboral> listDatosCalificacion) {
		this.listDatosCalificacion = listDatosCalificacion;
	}*/

	public List<Laboral> getListDatosCertificaciones() {
		return laborales.get(16);
		/*if (this.solicitudExp != null){
			this.listDatosCertificaciones = service.getListLaboralByTipo(new Integer(16), this.solicitudExp.getContacto());
		}
		return listDatosCertificaciones;*/
	}

	/*public void setListDatosCertificaciones(List<Laboral> listDatosCertificaciones) {
		this.listDatosCertificaciones = listDatosCertificaciones;
	}*/

	/*
	 * dchavez: 01/03/2014. Retorna la lista de evaluaciones cargada al inicio y que alimenta los tabs del dataTable inferior
	 */
	public List<Evaluacion> getListEvaluaciones() {
		//if (this.solicitudExp != null){
			//listEvaluaciones = service.getEvaluaciones(this.solicitudExp);
		//}
		return listEvaluaciones;
	}

	public void setListEvaluaciones(List<Evaluacion> listEvaluaciones) {
		this.listEvaluaciones = listEvaluaciones;
	}

	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */

	//public List<BeanEvaluacion> getListBeanEval() {
	public List<Evaluacion> getListBeanEval() {
		if (this.solicitudExp != null){
			listBeanEval = getListadoEvaluacionesByParam(this.solicitudExp, true);
		}
		return listBeanEval;
	}		

	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */
	
	//public void setListBeanEval(List<BeanEvaluacion> listBeanEval) {
	public void setListBeanEval(List<Evaluacion> listBeanEval) {
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
		
	public Mantenedor getEstadoActual() {
		if (this.solicitudExp != null){				
			estadoActual = this.solicitudExp.getEstatus();
		}
		return estadoActual;
	}

	public void setEstadoActual(Mantenedor estadoActual) {
		this.estadoActual = estadoActual;
	}

	public Mantenedor getEstadoSiguiente() {
		Integer    proxKey = null;
		Mantenedor actualEstado = null;
		Mantenedor proximoEstado = null;
				
		if (this.solicitudExp != null){
			
			actualEstado = this.getSolicitudExp().getEstatus();
			
			proxKey = Integer.valueOf(actualEstado.getProximo());
			//proxKey = actualEstado.getProximo()==null?null:Integer.valueOf(actualEstado.getProximo());
			
			if (proxKey != null){
				proximoEstado = service.getCatalogoEstadoSolicitud().get(proxKey);
									
				estadoSiguiente = (proximoEstado == null) ? actualEstado : proximoEstado;				
			} else {
				estadoSiguiente = actualEstado;
			}				
		}
		return estadoSiguiente;
	}

	public void setEstadoSiguiente(Mantenedor estadoSiguiente) {
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

	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Metodo que se ejecuta posterior al constructor de la clase.
	@PostConstruct
	private void initBean(){
		
		Collection<Mantenedor> a = service.getCatalogoTiposDatosLaborales().values();
		for (Mantenedor dato : a ) {
			System.out.println("postconstruct-> listTipoDatosLaborales");
			this.listTipoDatosLaborales.add(new SelectItem(dato.getId(), dato.getValor()));			
		}	
						
		listEstadosPortafolio = new ArrayList<SelectItem> ();
		Collection<Mantenedor> b = service.getCatalogoPortafolio().values();
		for (Mantenedor dato : b) {
			this.listEstadosPortafolio.add(new SelectItem(dato.getId(), dato.getValor()));
		}
				
		List<Pais> paises = service.getPaises();
		this.listPaises = new ArrayList<SelectItem> ();
		this.listPaises.add(new SelectItem(null, "Seleccion un pais"));
		for (Pais p : paises){
			this.listPaises.add(new SelectItem(p.getId(), p.getNombre()));
		}
						
		listGenero.add(new SelectItem(null, "Indique el Genero"));
							
		for (Mantenedor dato : service.getCatalogoGenero().values()){
			listGenero.add(new SelectItem(dato.getId(), dato.getValor()));			
		}		
				
		archivoExp = new Archivo();
				
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Metodo que se ejecuta posterior al postConstruct
	@Autowired
	public void iniciaBeanExpEval (){
		
		System.out.println("Comienzo de IniciaBeanExpEval....");
		
		Solicitud  solicitud = (Solicitud)FacesUtil.getParametroSession("dbSolicitudesBean");				
				
		if (solicitud != null){		
						
			this.solicitudExp = solicitud;
									
			this.contactoExp = this.solicitudExp.getContacto();
			
			if (this.contactoExp != null){
				
				if (this.contactoExp.getSexo() != null) 
					this.generoContacto = service.getCatalogoGenero().get(this.contactoExp.getSexo());					
				else 
					this.generoContacto = null; 
				 
				if (this.contactoExp.getDepartamentoId() != null){
					this.departamentoIdSelected = this.contactoExp.getDepartamentoId();					
					handleMunicipios();					
					this.setDeptoContacto(service.getCatalogoDepartamentos().get(this.contactoExp.getDepartamentoId()));							
				} else{
					this.setDeptoContacto(null);
					
					this.departamentoIdSelected = null;					
					this.listMunicipioByDptos = new ArrayList<SelectItem>();
					this.listMunicipioByDptos.add(new SelectItem(null, "Seleccion un Municipio"));
				}
				
				if (this.contactoExp.getMunicipioId() != null){
					this.municipioIdSelected = String.valueOf(this.contactoExp.getMunicipioId());
					this.setMunicipioContacto(this.catalogoMunicipiosByDepto.get(this.contactoExp.getMunicipioId()));					
				} else {
					this.setMunicipioContacto(null);
					this.municipioIdSelected = null;					
				}
				System.out.println("Obtiene datos laborales del contacto: "+contactoExp.getId());
				
				//dchavez: 01/03/2014 llenado de estructuras de datos de dataTable tab al momento de la carga unicamente
				
				laborales = service.getLaboralesMapByContacto(contactoExp);
				listEvaluaciones = service.getEvaluaciones(solicitudExp);
				listEvaluacionesUnidad = service.getListEvaluacionesUnidad (solicitudExp.getId());
			}
														
			//Estado Actual
			this.setEstadoActual(this.solicitudExp.getEstatus());
			
			enabledDisableButton(1);
			enabledDisableButton(2);
			enabledDisableButton(3);
			enabledDisableButton(4);
			enabledDisableButton(5);
		}
		
		System.out.println("Fin de IniciaBeanExpEval....");
	}
	
	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Obtiene el listado de evaluaciones segun el tipo de filtro.
	/*public List<BeanEvaluacion> getListadoEvaluacionesByParam(Solicitud sol, boolean todos) {
		
		List<BeanEvaluacion> listBeanEv = new ArrayList<BeanEvaluacion> ();		
		
		List<Evaluacion> listEval = service.getEvaluaciones(sol);
		
		Mantenedor estadoEval;
		String     unidadDescripcion = "";
		
		for (Evaluacion e : listEval) {
			
			estadoEval = e.getEstado(); 
			
			unidadDescripcion = this.service.getCompetenciaDescripcion(e.getUnidad());
					
			List<Instrumento> listInstrumento = service.getIntrumentoByEvaluacion(e.getId());
			for (Instrumento inst : listInstrumento) {				
				BeanEvaluacion bean = new BeanEvaluacion (sol, //Solicitud, 
						  								  e, //	Evaluacion
						  								  inst,// Instrumento
						  								  estadoEval, // EstadoEvaluacion
						  								  unidadDescripcion // UnidadCompentenciaDescripcion
						  								  );
				
				if (todos == true) {
					listBeanEv.add(bean);
				} else {
					if (e.isAprobado() == false){
						listBeanEv.add(bean);
					} 
				}	
			}
		}			
		
		return listBeanEv;
	}*/
	
	public List<Evaluacion> getListadoEvaluacionesByParam(Solicitud sol, boolean todos) {
		
		if(todos)
			return service.getEvaluaciones(sol);
		else
			return service.getEvaluacionesPendientes(sol);
	}
	
	public String getCompetenciaDescripcion(Long codigo){
		return service.getCompetenciaDescripcion(codigo);
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Listener asociado al upload del portafolio.
	public void handleActivaUpload(){
		if (archivoExp != null) {
			if ((archivoExp.getNombre() != null) && (archivoExp.getTipo() != null) && (archivoExp.getVersion() != null) && (archivoExp.getDescripcion() != null)){
				this.setDisabledUploadFile(false);
			}
		}
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Consulta los municipios por el Departamento seleccionado.
	public void handleMunicipios() {
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
			    	idValor = claveSet.next();
			    	valor = this.catalogoMunicipiosByDepto.get(idValor);
			    	this.listMunicipioByDptos.add(new SelectItem(valor.getMunicipio_id(), valor.getMunicipio_nombre()));			    			    			        		        
			    }
			}
		
		}
	}

	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Prepara datos laborales.
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

		FacesUtil.setParamBySession("idSelectedLaboral", this.selectedLaboral.getId());		
		
		//enabledDisableButton(2);
		
		actualizaListaPortafolio (new Integer(1));		
				
	}
		
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Prepara para nuevo laboral.
	public void nuevoLaboral() {		
		limpiarCampos();		
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Asociado al listener edit del portafolio.
	public void rowEditListenerPortafolio(RowEditEvent event){
		System.out.println("El estado seleccionado es: " + (String)event.getObject());
		System.out.println("Archivo Seleccionado " + selectedArchivoId);
		
	}
	
	/**
	 * dchavez, 16/02/2014: sustituyendo BeanEvaluacion por la entidad Evaluacion
	 */
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Redirecciona al facet registro_evaluacion.xhtml
	/*public String editarEvaluacion(BeanEvaluacion beanEval) {		
		Evaluacion eval = null;
		
		this.setSelectedBeanEvaluacion(beanEval);
		
		FacesUtil.setParamBySession("solicitudEval", this.selectedBeanEvaluacion.getSolicitudBeanEval());		
		FacesUtil.setParamBySession("selectedInstrumento", this.selectedBeanEvaluacion.getInstrumento());
		
		eval = this.selectedBeanEvaluacion.getEvaluacion();
		
		if (eval != null){
			FacesUtil.setParamBySession("selectedEvaluacion", eval);					
			FacesUtil.setParamBySession("selectedUnidad", eval.getUnidad());
		} else {
			FacesUtil.setParamBySession("selectedEvaluacion", null);
			FacesUtil.setParamBySession("selectedUnidad", null);
		}
		
		return "/modulos/solicitudes/registro_evaluacion?faces-redirect=true";		
	}*/
	
	public String editarEvaluacion(Evaluacion beanEval) {		
		Evaluacion eval = null;
		
		this.setSelectedBeanEvaluacion(beanEval);
		
		FacesUtil.setParamBySession("solicitudEval", this.selectedBeanEvaluacion.getSolicitud());		
		FacesUtil.setParamBySession("selectedInstrumento", this.selectedBeanEvaluacion.getInstrumento());
		
		eval = this.getSelectedBeanEvaluacion();
		
		if (eval != null){
			FacesUtil.setParamBySession("selectedEvaluacion", eval);					
			FacesUtil.setParamBySession("selectedUnidad", eval.getUnidad());
		} else {
			FacesUtil.setParamBySession("selectedEvaluacion", null);
			FacesUtil.setParamBySession("selectedUnidad", null);
		}
		
		return "/modulos/solicitudes/registro_evaluacion?faces-redirect=true";		
	}	
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Registra nueva evaluacion.
	public String registrarEvaluacion() {				
		FacesUtil.setParamBySession("solicitudEval", this.solicitudExp);		
		FacesUtil.setParamBySession("selectedInstrumento", null);		
		FacesUtil.setParamBySession("selectedEvaluacion", null);
		FacesUtil.setParamBySession("selectedUnidad", null);
				
		return "/modulos/solicitudes/registro_evaluacion?faces-redirect=true";		
	}	
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA ||
	public void solicitarCertificacion (){
		
		Mantenedor estadoActual = this.getSolicitudExp().getEstatus();
		Integer proxEstado = Integer.valueOf(estadoActual.getProximo()); 
		
		Mantenedor proximoEstado = service.getCatalogoEstadoSolicitud().get(proxEstado);
						
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
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Habilita y deshabilita botones.
	public void enabledDisableButton(int opcion) {
		
		Solicitud sol = this.getSolicitudExp();
		
		Integer inicialKey, convocaKey, asesoraKey, siguienteKey, evaluarKey;
		
		Mantenedor inicialEstado, convocaEstado, asesoraEstado, siguienteEstado, evaluarEstado;				
		
		if (sol != null){		
			
			//inicialEstado = service.getMantenedorMinByTipo(sol.getTipomantenedorestado());
			inicialEstado = service.getCatalogoEstadoSolicitud().get(20);
			inicialKey = inicialEstado.getId();
			
			convocaKey = Integer.valueOf(inicialEstado.getProximo());
			
			convocaEstado = service.getCatalogoEstadoSolicitud().get(convocaKey);
								
			asesoraKey = Integer.valueOf(convocaEstado.getProximo());
			
			asesoraEstado = service.getCatalogoEstadoSolicitud().get(asesoraKey); 
								
			evaluarEstado = sol.getEstatus();
			
			evaluarKey = evaluarEstado.getId();
			
			siguienteKey = (evaluarEstado.getProximo() != null) ? Integer.valueOf(evaluarEstado.getProximo()) : null;
			
			siguienteEstado = (siguienteKey == null) ? null : service.getCatalogoEstadoSolicitud().get(siguienteKey);
							
		} else{
			inicialEstado = null;
			inicialKey = null;			
			convocaKey = null;			
			convocaEstado = null;			
			asesoraKey = null;			
			asesoraEstado = null;			
			evaluarEstado = null;			
			evaluarKey = null;			
			siguienteKey = null;			
			siguienteEstado = null;		
		}
				
		switch(opcion) {
		   //Boton Convocar
			case 1:				
				if ((evaluarKey == convocaKey) && (evaluarKey != null))
					//this.setDisabledConvocar(false);
					disabledConvocar = false;
				else
					//this.setDisabledConvocar(true);
					disabledConvocar = true;
				break;
			//Boton Asesorar
			case 2:
				if ((evaluarKey == asesoraKey) && (evaluarKey != null))
					//this.setDisabledAsesorar(false);
					disabledAsesorar = false;
				else
					//this.setDisabledAsesorar(true);
					disabledAsesorar = true;
				break;
			//Boton Inscripcion
			case 3:
				if ((evaluarKey == Integer.valueOf(asesoraEstado.getProximo())) && (evaluarKey != null))
					//this.setDisabledCerrar(false);
					disabledCerrar = false;
				else
					//this.setDisabledCerrar(true);
					disabledCerrar = true;
				break;
			//Boton Agregar Evaluacion
			case 4:
				if ((evaluarKey == Integer.valueOf(asesoraEstado.getProximo())) && (evaluarKey != null))
					//this.setDisabledBtnAgregaEvaluacion(false);
					disabledBtnAgregaEvaluacion = false;
				else{
					if (service.validaProcesoConcluido(sol, false))
						//this.setDisabledBtnAgregaEvaluacion(false);
						disabledBtnAgregaEvaluacion = false;
					else
						//this.setDisabledBtnAgregaEvaluacion(true);
						disabledBtnAgregaEvaluacion = true;
				}
				break;
			//Boton Proceso Concluido
			case 5:
				if (service.validaProcesoConcluido(sol, true))
					//this.setDisabledConcluido(false);
					disabledConcluido = false;
				else
					//this.setDisabledConcluido(true);
					disabledConcluido = true;
				break;
			default: 
				break;
		}
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Limpiar campos.
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
		
		FacesUtil.setParamBySession("idSelectedLaboral", null);
					
	}	
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA ||
	public void nuevoPortafolio (){
				
		if (this.selectedLaboral == null)			
			this.idSeletedLaboral = (Long)FacesUtil.getParametroSession("idSelectedLaboral");			
		else
			this.idSeletedLaboral = this.selectedLaboral.getId();		
			
		if (this.idSeletedLaboral != null) {
			
			if (this.selectedLaboral == null)				
				this.selectedLaboral = service.getLaboralById(this.idSeletedLaboral);
			FacesUtil.setParamBySession("selectedLaboral", this.selectedLaboral);			
		}		 
		
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Cancela edicion de portafolio.
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
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Aplica estatus de aprobado al portafolio.
	/*public void aprobarPortafolio(){
				
		Mantenedor estadoArchivo = archivoExp.getEstado();
		
		Integer  proxEstado = Integer.valueOf(estadoArchivo.getProximo());
		
		if (proxEstado != null){
			archivoExp.setEstado(service.getCatalogoPortafolio().get(proxEstado));
				
		}
				
		archivoExp = (Archivo) service.guardar(archivoExp);
		
		if (archivoExp != null){
			
		}
	}*/
	
	public void aprobarPortafolio(){
		archivoExp.setEstado(service.getCatalogoPortafolio().get(26));
		archivoExp.setAprobado("Aprobado");
		archivoExp = (Archivo) service.guardar(archivoExp);		
	}
	
	public void reprobarPortafolio(){
		archivoExp.setEstado(service.getCatalogoPortafolio().get(27));
		archivoExp.setAprobado("No Aprobado");
		archivoExp = (Archivo) service.guardar(archivoExp);		
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Actualiza listado de portafolio.
	public void actualizaListaPortafolio (Integer tipo){
		Object [] objs;
		if (tipo == 1) {						
			if (selectedLaboral != null) {			
				 objs =  new Object [] {selectedLaboral.getId()};			
				 this.listPortafolioLaboral = service.getArchivoByParam ("Archivo.findByLaboralId", objs);
				 //this.listPortafolioLaboral = (List<Archivo>)service.getArchivoByParam ("Archivo.findAllArchivosByContactoId", objs);
			}
		} else {		
				if (this.solicitudExp != null){
					Contacto c = solicitudExp.getContacto();
					objs =  new Object [] {c.getId()};
					this.listPortafolioContacto = service.getArchivoByParam ("Archivo.findByContactoId", objs);
					//this.listPortafolioContacto = (List<Archivo>)service.getArchivoByParam ("Archivo.findAllArchivosByContactoId", objs);
				}
		}
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Inicializa parametros.
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
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Aplica estatus convocado.
	public String convocarCertificacion(){
		Contacto solicitante = null;
		boolean pasaConvocatoria = false;
		String  titulo = "";
		String  textoMsg = "";
		boolean isError = false;
		
		if (this.solicitudExp != null){
			solicitante = this.solicitudExp.getContacto();
			pasaConvocatoria = service.portafolioVerificado(solicitante, new String("8"));			
		}
						
		if (pasaConvocatoria == true){
					
			this.solicitudExp.setEstatus(this.getEstadoSiguiente());
			
			//dchavez 14/03/2014. Agregando fecha de convocatoria
			this.solicitudExp.setFechaConvocatoria(new Date());
			
			this.solicitudExp = (Solicitud) service.guardar(this.solicitudExp);
			
			if (this.solicitudExp != null){
				titulo = "SCCL - Proceso exitoso: ";
				textoMsg = "La solicitud ha pasado a convocatoria.";
				isError = false;
				return "/modulos/solicitudes/solicitudes?faces-redirect=true";
			} else {
				titulo = "SCCL - Error: ";
				textoMsg = "Se generó un error al aplicar los cambios. Favor comuníquese al Departamento de Tecnología del INATEC.";
				isError = true;
			}				
		} else {
			titulo = "SCCL - Error: ";
			textoMsg = "No puede ser convocado por que existen archivos del portafolio que deben ser revisado.";
			isError = true;
		}
				
		FacesUtil.getMensaje(titulo, textoMsg, isError);	  
		return textoMsg;
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Aplica estatus asesorado.
	public String asesorarCertificacion (){
		String  textoMsg = "Se generó un error al intentar aplicar los cambios. Favor comuníquese al Departamento de Tecnología del INATEC.";
		boolean indicaAsesorado = true;			
		solicitudExp = avanzaProceso (solicitudExp, "listo para inscripción", indicaAsesorado, textoMsg);
		if(solicitudExp!=null)
			return "/modulos/solicitudes/solicitudes?faces-redirect=true";
		else
			return textoMsg;
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Aplica estatus listo para inscripcion.
	public String inscripcionCertificacion (){
		boolean listoInscripcion = false;	
		String textoMsg = "La solicitud no cumple con las condiciones para proceder con la inscripcion. Favor revisar...";
		
		if (solicitudExp != null) {
			listoInscripcion = service.validaListoInscripcion(solicitudExp);			
			solicitudExp = avanzaProceso (solicitudExp, "listo para inscripción", listoInscripcion, textoMsg);
			if(solicitudExp!=null)
				return "/modulos/solicitudes/solicitudes?faces-redirect=true";
		}
		return textoMsg;
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Aplica estatus finalizado.
	public String concluirCertificacion(){
		boolean concluir = false;
		String textoMsg = "La solicitud no cumple con las condiciones para concluir el proceso de certificacion. Favor revisar...";
		if (solicitudExp != null){
			concluir = service.validaProcesoConcluido(solicitudExp, false);
			solicitudExp.setResultadoEvaluacion(concluir);
			solicitudExp = avanzaProceso(solicitudExp, " Proceso Concluido", concluir, textoMsg );
			if(solicitudExp!=null)
				return "/modulos/solicitudes/solicitudes?faces-redirect=true";			
		}
		return textoMsg;
	}
	
	//dchavez 03/03/2014. Agregando opcion para anular solicitud
	
	/*public String anularSolicitud(){
		if (solicitudExp != null && solicitudExp.getEstatus().getId()!=37){
			solicitudExp = service.anularSolicitud(solicitudExp);
			if(solicitudExp!=null)
				return "/modulos/solicitudes/solicitudes?faces-redirect=true";
			else
				return "Ocurrió un error, vuelva a intentarlo...";
		}
		return "Operación no permitida...";
	}*/
	
	public String retornar(){
		return "/modulos/solicitudes/solicitudes?faces-redirect=true"; 
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Aplica cambio de estatus.
	public Solicitud avanzaProceso (Solicitud sol, String nombreEstado, boolean pasa, String msgNoPasa){
		String 	titulo = "";
		String 	textoMsg = "";
		boolean isError = false;
				
		if (sol == null){
			textoMsg = "Se generó un error al obtener los datos del proceso de certificacion. Favor comuníquese al Departamento de Tecnología del INATEC.";
			isError = true;
		} else {
			if (!pasa){
				textoMsg = msgNoPasa;
				isError = true;
			}else {
				sol.setEstatus(this.getEstadoSiguiente());
				
				//dchavez. 14/03/2014. Se agrega fecha de asesoramiento
				sol.setFechaAsesoramiento(new Date());
				
				sol = (Solicitud) service.guardar(sol);
				
				if (sol != null)					
					textoMsg = "La solicitud se encuentra en estatus " + nombreEstado;					
				else {					
					textoMsg = "Se generó un error al intentar aplicar los cambios. Favor comuníquese al Departamento de Tecnología del INATEC.";
					isError = true;
				}
			}			
			
		}
		
		titulo = (isError) ? "SCCL - Error: " : "SCCL - Proceso exitoso: ";
		
		FacesUtil.getMensaje(titulo, textoMsg, isError);
		
		//return sol;
		return isError==true?null:sol;
	}

	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta reporte Informe del Asesor.
	public void runInformeAsesor() throws Exception{
		String rptNombre = "informeasesor";
		runReporte(rptNombre);
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta reporte Plan de Capacitacion.
	public void runPlanCapacitacion() throws Exception{
		String rptNombre = "plancapacitacion";
		runReporte(rptNombre);
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta reporte generico.
	public void runReporte(String nombreReporte) throws Exception {
    	Map<String,Object> params = new HashMap<String,Object>();
    	Certificacion cert = this.solicitudExp.getCertificacion();
    	
    	if (cert.getEvaluador() != null){
    		params.put("idSolicitud",this.solicitudExp.getId());	
    		params.put("idEvaluador",cert.getEvaluador().getId());
    		    		
        	service.imprimirReporte(nombreReporte, params, Global.EXPORT_PDF, true);
    	}else
    		FacesUtil.getMensaje("Mensaje SCCL ", "Error al consultar los datos del evaluador. Favor comuníquese con el Departamento de Tecnología del INATEC", true);
    	  		
	}	
}