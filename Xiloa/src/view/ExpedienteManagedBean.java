package view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
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
	private List<SelectItem> listDeptos;
	private List<SelectItem> listMunicipioByDptos;
	private List<SelectItem> listPaises;
	private List<SelectItem> listNacionalidades;
	private Contacto contactoExpediente;
	private Laboral expedienteLaboral;
	
	private String municipioIdSelected;	
	private Integer departamentoIdSelected;
	//private Map<Integer, Municipio> catalogoMunicipiosByDepto;
	
	private boolean disabledBtnActualizaContacto;
	
	private boolean disabledBtnAgregaLaborales;
	private List<SelectItem> listTipoDatosLaborales;	
	private Laboral selectedLaboral;
	
	private Laboral datoLaboral;
	private List<Laboral> listDatosLaborales;
	private List<Laboral> listDatosEstudios;
	private List<Laboral> listDatosCalificacion;
	private List<Laboral> listDatosCertificaciones;

	private Integer idTipoLaboral;
	private String idPaisLaboral;	
	
	private Archivo archivoExpediente;
	private UploadedFile archivoFisicoSubidoPorUsuario;
	
	private Archivo selectedArchivo;
	private List<Archivo> listaArchivosPortafolioLaboral;
	private List<Evaluacion> evaluaciones;
	private Evaluacion selectedEvaluacion;
	
	@PostConstruct
	private void init(){        
		inicializarListaCombos();
		cargarContactoSolicitudParaExpediente();
	}

	private void inicializarListaCombos() {
		List<Mantenedor> listaCatalogo = service.getMantenedoresByTipo(5);	
		listTipoDatosLaborales = new ArrayList<SelectItem> ();
		for (Mantenedor dato : listaCatalogo) {
					
			this.listTipoDatosLaborales.add(new SelectItem(dato.getId(), dato.getValor()));			
		}	
		
		//Obtiene el catalogo de los Departamentos					
		listDeptos = new ArrayList<SelectItem> ();
		listDeptos.add(new SelectItem(null, "Seleccione un Departamento"));
		
		for(Departamento departamento : service.getDepartamentos())
		{
			listDeptos.add(new SelectItem(departamento.getDpto_id(), departamento.getDpto_nombre()));
		}
			
		//Obtiene el catalogo de los Paises
		List<Pais> paises = new ArrayList<Pais>(this.service.getCatalogoPaises().values()); 
		this.listPaises = new ArrayList<SelectItem> ();
		this.listNacionalidades = new ArrayList<SelectItem> ();
		this.listPaises.add(new SelectItem(null, "Seleccione un pais"));
		this.listNacionalidades.add(new SelectItem(null, "Indique la nacionalidad"));
		
		for (Pais p : paises){
			
			this.listPaises.add(new SelectItem(p.getCodigo(), p.getNombre()));
			this.listNacionalidades.add(new SelectItem(p.getCodigo(), p.getNombre()));
		}

		listaArchivosPortafolioLaboral=new ArrayList<Archivo>();
	}
	
	private void cargarContactoSolicitudParaExpediente()  {
		
		Long candidatoId = (Long)FacesUtil.getParametroSession("candidatoId");
		contactoExpediente = service.getContactoById(candidatoId);
		
		evaluaciones = service.getEvaluacionesByContactoId(candidatoId);
				
		if (contactoExpediente.getMunicipioId() != null){
			municipioIdSelected = String.valueOf(contactoExpediente.getMunicipioId());				
		} else
			municipioIdSelected = null;
			
		if (contactoExpediente.getDepartamentoId() != null){
			departamentoIdSelected = contactoExpediente.getDepartamentoId();
			handleMunicipios();
		} else{
			departamentoIdSelected = null;				
			listMunicipioByDptos = new ArrayList<SelectItem>();
			listMunicipioByDptos.add(new SelectItem(null, "Seleccion un Municipio"));
		}
	}	
 
	 public void handleMunicipios() {
	
		if (getContactoExpediente() != null) {
			
			listMunicipioByDptos = new ArrayList<SelectItem> ();
			listMunicipioByDptos.add(new SelectItem(null, "Seleccione un Municipio"));			
			
			for(Municipio municipio : service.getMunicipios(getContactoExpediente().getDepartamentoId())){
				listMunicipioByDptos.add(new SelectItem(municipio.getMunicipio_id(), municipio.getMunicipio_nombre()));
			}				
		}
	}
	 
	public String actualizarContacto(Contacto contacto) {

		String mensaje = "";
		boolean isError = false;

		if (validarDatosContacto()) {

			contactoExpediente = (Contacto) service.guardar(contacto);

			if (contactoExpediente != null) {
				isError = false;
				return "/modulos/solicitudes/candidatos?faces-redirect=true";
			} else {
				isError = true;
				mensaje = "Error al actualizar los datos del contacto...";
				FacesUtil.getMensaje("SCCL - Mensaje: ", mensaje, isError);
				return "ActualizacionDatosSinExito";
			}
		}
		return "ActualizacionDatosSinExito";
	}
	
	private boolean validarDatosContacto() {
		
		if(contactoExpediente.getNumeroIdentificacion()==null){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique la cédula...", true);
			return false;
		}
		
		if(!ValidatorUtil.validateCedula(contactoExpediente.getNumeroIdentificacion())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Cédula invalida...", true);
			return false;
		}
		
		if(contactoExpediente.getFechaNacimiento()==null){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique la fecha de nacimiento...", true);
			return false;
		}
		
		Date fecha = ValidatorUtil.obtenerFechaNacimientoDeCedula(contactoExpediente.getNumeroIdentificacion());	
		if(fecha.compareTo(contactoExpediente.getFechaNacimiento()) != 0){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "La fecha de nacimiento no coincide con la cédula.", true);
			return false;
		}

		if(contactoExpediente.getSexo()==null){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique el sexo...", true);
			return false;
		}
		
		if (contactoExpediente.getTelefono1() == null) {
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique el número de teléfono...", true);
			return false;
		}
		
		if(!ValidatorUtil.validatePhone(contactoExpediente.getTelefono1())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "El número de teléfono es invalido...", true);
			return false;
		}

		if(contactoExpediente.getTelefono2() != null && !ValidatorUtil.validatePhone(contactoExpediente.getTelefono2())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "El número de celular es invalido...", true);
			return false;
		}
		
		if (contactoExpediente.getTelefono2() == null) {
			contactoExpediente.setTelefono2("");
		}
		
		if (contactoExpediente.getDireccionActual() == null) {
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique la dirección...", true);
			return false;
		}

		if (contactoExpediente.getCorreo1() == null) {
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique el correo electronico...", true);
			return false;
		}

		if(!ValidatorUtil.validateEmail(contactoExpediente.getCorreo1())){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "El correo electronico no es válido...", true);
			return false;
		}
		
		if(contactoExpediente.getNacionalidadId()==null){
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Indique la nacionalidad...", true);
			return false;
		}
			
		return true;
	}
	
	public void agregarLaboral() {
		setDatoLaboral(new Laboral());
	}
	
	public void guardarDatosLaborales() {
		String descripcionLaboral = getDatoLaboral().getCargo().toUpperCase() + " /  " + getDatoLaboral().getInstitucion().toUpperCase().toUpperCase();
	
		getDatoLaboral().setNombre(descripcionLaboral);
		getDatoLaboral().setDescripcion(getDatoLaboral().getCargo());
		getDatoLaboral().setPais(idPaisLaboral);
		getDatoLaboral().setTipo(getIdTipoLaboral());
		getDatoLaboral().setContacto(this.contactoExpediente);
		getDatoLaboral().setCargo(getDatoLaboral().getCargo().toUpperCase());
		Laboral datoLaboral = (Laboral)service.guardar(getDatoLaboral());
	
		if (datoLaboral != null) {		
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Los cambios ha sido aplicados exitosamente !!", false);	
			this.listaArchivosPortafolioLaboral = service.getArchivosByLaboralId(datoLaboral.getId());
		}
		else {
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Se genero un error al grabar los datos laborales / academicos. Favor revisar...", true);
		}
	}

	public void nuevoArchivoExpediente() {
		setArchivoExpediente(new Archivo());
	}

	public void uploadFile(FileUploadEvent event) {

		Date fechaAhora = new Date();
		String tituloMsg = "";
		String mensaje = "";
		boolean isError = false;

		FacesMessage msg;

		// Obtener nombre del directorio fisico del mantenedor de archivo

		String directorio = service.getMantenedorById(58).getValor();
		String nombreFile;
		String nombrePropietario;
		Long sizeArchivo;
		int pos;
		String extension;
		String sizeArchivoExp;

		try {

			this.archivoFisicoSubidoPorUsuario = event.getFile();

			nombreFile = archivoFisicoSubidoPorUsuario.getFileName().trim();

			String nombreRealArchivoExp = nombreFile;

			pos = nombreFile.indexOf(".");

			extension = nombreFile.substring(pos);

			if (!((nombreRealArchivoExp.toLowerCase().endsWith(".pdf"))
					|| (nombreRealArchivoExp.toLowerCase().endsWith(".png")))) {

				mensaje = nombreFile + "Unicamente se permite subir archivo PDF y PNG. Favor revisar...."
						+ nombreFile.toLowerCase();
				isError = true;

			} else {

				nombrePropietario = this.contactoExpediente.getNombreCompleto();

				sizeArchivo = (this.archivoFisicoSubidoPorUsuario.getSize()) / 1024; 

				sizeArchivoExp = String.valueOf(sizeArchivo) + " KB";

				getArchivoExpediente().setNombre(nombreFile);
				getArchivoExpediente().setSize(sizeArchivoExp);
				getArchivoExpediente().setFecha(fechaAhora);
				getArchivoExpediente().setNombreReal(nombreRealArchivoExp);
				getArchivoExpediente().setRuta(
						String.format(directorio + "/%s", nombreFile));
				getArchivoExpediente().setPropietario(nombrePropietario);
				getArchivoExpediente().setTipo(extension);
				getArchivoExpediente().setCategoria(extension);
				getArchivoExpediente().setIcono(nombreFile);
				getArchivoExpediente().setDescripcion(nombreRealArchivoExp);

				FileOutputStream fos = new FileOutputStream(String.format(
						directorio + "/%s", getArchivoFisicoSubidoPorUsuario()
								.getFileName()));
				fos.write(getArchivoFisicoSubidoPorUsuario().getContents());
				fos.flush();
				fos.close();

				String tipoMantenedorEstado = getArchivoExpediente()
						.getTipoMantenedorEstado();

				Mantenedor primerEstado = service
						.getMantenedorMinByTipo(tipoMantenedorEstado);

				getArchivoExpediente().setEstado(primerEstado);
				getArchivoExpediente().setLaboral(getSelectedLaboral());	

			} 

		} catch (IOException e) {
			isError = true;
			e.printStackTrace();
		}

		if (isError) {
			tituloMsg = "Proceso Incompleto: ";
			mensaje = (mensaje.isEmpty()) ? "Se genero un error al subir el archivo. Favor revisar...."
					: mensaje;
		} else {
			tituloMsg = "Proceso Exitoso !!!";
		}

		msg = new FacesMessage(tituloMsg, mensaje);

		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void editarLaboral() {
		//TODO REDEFINIR
	}

	public void guardarArchivo() {
		Archivo resultado = (Archivo) service.guardar(getArchivoExpediente());

		if (resultado != null){				
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Los cambios ha sido aplicados exitosamente !!", false);
			actualizaListaPortafolio ();							
		}else{
			FacesUtil.getMensaje("SCCL - Mensaje: ", "Error al grabar el archivo. Favor revisar...", true);				
		}
	}

	public void actualizaListaPortafolio (){
		if (getSelectedLaboral() != null) {
				listaArchivosPortafolioLaboral = service.getArchivosByLaboralId(getSelectedLaboral().getId());
		} else {
				FacesUtil.getMensaje("SCCL - Error: ", "Debe seleccionar un registro de la trayectoria de experiencia laboral y/o academica.", false);
		}	
	}

	public void onRowSelect(SelectEvent event) {	
		if (selectedLaboral != null) {
			setDatoLaboral(selectedLaboral);
			idPaisLaboral = selectedLaboral.getPais();
			listaArchivosPortafolioLaboral = service.getArchivosByLaboralId(getSelectedLaboral().getId());
			FacesMessage msg = new FacesMessage("faces " + "bla "+ getSelectedLaboral().getId());
			FacesContext.getCurrentInstance().addMessage("", msg);
		}
	}

	public void limpiarSeleccion(TabChangeEvent event) {
		listaArchivosPortafolioLaboral=null;
		
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("wdgListExperiencias.unselectAllRows()");
		context.execute("wdgListEstudios.unselectAllRows()");
		context.execute("wdgListCalificaciones.unselectAllRows()");
		context.execute("wdgListCertificaciones.unselectAllRows()");
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


	public List<SelectItem> getListPaises() {
		return listPaises;
	}

	public void setListPaises(List<SelectItem> listPaises) {
		this.listPaises = listPaises;
	}

	public List<SelectItem> getListNacionalidades() {
		return listNacionalidades;
	}

	public void setListNacionalidades(List<SelectItem> listNacionalidades) {
		this.listNacionalidades = listNacionalidades;
	}

	public Contacto getContactoExpediente() {
		return contactoExpediente;
	}

	public void setContactoExpediente(Contacto contactoExpediente) {
		this.contactoExpediente = contactoExpediente;
	}

	public Laboral getExpedienteLaboral() {
		return expedienteLaboral;
	}

	public void setExpedienteLaboral(Laboral expedienteLaboral) {
		this.expedienteLaboral = expedienteLaboral;
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

	/*
	public Map<Integer, Municipio> getCatalogoMunicipiosByDepto() {
		return catalogoMunicipiosByDepto;
	}

	public void setCatalogoMunicipiosByDepto(
			Map<Integer, Municipio> catalogoMunicipiosByDepto) {
		this.catalogoMunicipiosByDepto = catalogoMunicipiosByDepto;
	}
	*/

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

	public Laboral getDatoLaboral() {
		return datoLaboral;
	}

	public void setDatoLaboral(Laboral datoLaboral) {
		this.datoLaboral = datoLaboral;
	}

	public List<Laboral> getListDatosLaborales() {
		this.listDatosLaborales = (this.getContactoExpediente() != null) ? service.getListLaboralByTipo(new Integer(23), this.getContactoExpediente()) : this.listDatosLaborales;
		
		return listDatosLaborales;
	}

	public void setListDatosLaborales(List<Laboral> listDatosLaborales) {
		this.listDatosLaborales = listDatosLaborales;
	}
	
	
	public List<Laboral> getListDatosEstudios() {
		listDatosEstudios = (getContactoExpediente() != null) ? service.getListLaboralByTipo(new Integer(24), getContactoExpediente()) : listDatosEstudios;
		return listDatosEstudios;
	}

	public void setListDatosEstudios(List<Laboral> listDatosEstudios) {
		this.listDatosEstudios = listDatosEstudios;
	}

	public List<Laboral> getListDatosCalificacion() {
		this.listDatosCalificacion = (getContactoExpediente() != null) ? service.getListLaboralByTipo(new Integer(25), getContactoExpediente()) : listDatosCalificacion;
		return listDatosCalificacion;
	}

	public void setListDatosCalificacion(List<Laboral> listDatosCalificacion) {
		this.listDatosCalificacion = listDatosCalificacion;
	}

	public List<Laboral> getListDatosCertificaciones() {
		this.listDatosCertificaciones = (getContactoExpediente() != null) ? service.getListLaboralByTipo(new Integer(26), getContactoExpediente()) : listDatosCertificaciones;
		return listDatosCertificaciones;
	}

	public void setListDatosCertificaciones(List<Laboral> listDatosCertificaciones) {
		this.listDatosCertificaciones = listDatosCertificaciones;
	}



	public Integer getIdTipoLaboral() {
		return idTipoLaboral;
	}

	public void setIdTipoLaboral(Integer idTipoLaboral) {
		this.idTipoLaboral = idTipoLaboral;
	}

	public String getIdPaisLaboral() {
		return idPaisLaboral;
	}

	public void setIdPaisLaboral(String idPaisLaboral) {
		this.idPaisLaboral = idPaisLaboral;
	}
	
	public Archivo getArchivoExpediente() {
		return archivoExpediente;
	}

	public void setArchivoExpediente(Archivo archivoExpediente) {
		this.archivoExpediente = archivoExpediente;
	}

	public UploadedFile getArchivoFisicoSubidoPorUsuario() {
		return archivoFisicoSubidoPorUsuario;
	}

	public void setArchivoFisicoSubidoPorUsuario(
			UploadedFile archivoFisicoSubidoPorUsuario) {
		this.archivoFisicoSubidoPorUsuario = archivoFisicoSubidoPorUsuario;
	}

	public Archivo getSelectedArchivo() {
		return selectedArchivo;
	}

	public void setSelectedArchivo(Archivo selectedArchivo) {
		this.selectedArchivo = selectedArchivo;
	}

	public List<Archivo> getListaArchivosPortafolioLaboral() {
		return listaArchivosPortafolioLaboral;
	}

	public void setListaArchivosPortafolioLaboral(
			List<Archivo> listaArchivosPortafolioLaboral) {
		this.listaArchivosPortafolioLaboral = listaArchivosPortafolioLaboral;
	}

    public List<Evaluacion> getEvaluaciones(){
    	return evaluaciones;
    }
    
    public Evaluacion getSelectedEvaluacion(){
    	return selectedEvaluacion;
    }
    
    public void setSelectedEvaluacion(Evaluacion evaluacion){
    	this.selectedEvaluacion = evaluacion;
    }
    
    public String getCompetenciaDescripcion(Long codigo){
    	if(codigo != null && codigo != 0)
    		return service.getCompetenciaDescripcion(codigo);
    	else
    		return "N/D";
    }
}