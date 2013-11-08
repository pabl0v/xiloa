package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import service.IService;
import support.BeanEvaluacion;

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
	
	private UploadedFile file;
	
	private Archivo archivoExp;
	private Long evalIdByArchivoExp;
	private List<Archivo> listPortafolio;
	
	private List<SelectItem> listEvalBySolicitud = new ArrayList<SelectItem> ();	
	
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
		
		List<Evaluacion> listEval = service.getEvaluaciones(this.solicitudExp);
		for (Evaluacion e : listEval) {
			List<Instrumento> listInstrumento = service.getIntrumentoByEvaluacion(e.getId());
			for (Instrumento inst : listInstrumento) {				
				listBeanEv.add(new BeanEvaluacion(this.solicitudExp, //Solicitud, 
												  e, //	Evaluacion
												  inst// Instrumento
												  )
							  );
			}
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

	@PostConstruct
	private void fillCatalogos(){
		List<Mantenedor> listaCatalogo = service.getMantenedoresByTipo(new Integer(5));
		for (Mantenedor dato : listaCatalogo) {
			this.catalogoTipoDatosLaborales.put(dato.getId(), dato);			
			this.listTipoDatosLaborales.add(new SelectItem(dato.getId(), dato.getValor()));			
		}			
		
		archivoExp = new Archivo();
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
									   this.nombreCargo.toUpperCase()//, // cargo, 
									   //null // archivo
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
	
	public void uploadFile(){
		System.out.println("Entra al uploadFile");
		//System.out.println("Nombre del archivo " + file.getFileName());
		//System.out.println("Tamaño " + file.getSize());
		
		try {
			File targetFolder = new File("/portafolio");
			System.out.println("La ruta donde se copiara el archivo: " +targetFolder.getAbsolutePath());
			
			System.out.println("Direccion del contex " + FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo());
			
			System.out.println("Revisando los otros valores: ");
			System.out.println("Nombre " + archivoExp.getNombre());
			System.out.println("Descripcion " + archivoExp.getDescripcion());
			
			InputStream inputStream = file.getInputstream(); //event.getFile().getInputstream();
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		/*
			Date fechaAhora = new Date();
			//this.file = event.getFile();
			
	        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        String txtField = ec.getRequestParameterMap().get("formExpediente:txtField");
	        String filePath = ec.getRealPath(String.format("/portafolio/%s",file.getFileName() + "_" + this.solicitudExp.getId() + "_" + fechaAhora));
	        
	        try {
	            FileOutputStream fos = new FileOutputStream(filePath);
	            fos.write(file.getContents());
	            fos.flush();
	            fos.close();
	 
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        FacesContext context = FacesContext.getCurrentInstance();
	        
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,String.format("Archivo cargado: %s ", file.getFileName()),
	                String.format("Mensaje: %s", txtField)));
	        */
	        			
	}

}
