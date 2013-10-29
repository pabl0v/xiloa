package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Actividad;
import model.Contacto;
import model.Mantenedor;
import model.Unidad;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;  

import service.IService;

@Component
@Scope(value="session")  //@ViewScoped
public class CertificacionManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private IService service;
	private Long certificacionId;
	private String idCurso;
	private String nombreCertificacion;
	private String descripcionCertificacion;
	private int disponibilidad;
	private String idCompetencia;
	private String nombreCompetencia;
	private float costo;
	private List<Contacto> contactos;
	private Contacto[] selectedContactos;
	private Date fechaIniciaDivulgacion;
	private Date fechaFinalizaInscripcion;
	private Date fechaIniciaConvocatoria;
	private String idCentro;
	private String nombreCentro;
	private String direccionCentro;
	private Date fechaIniciaEvaluacion;
	private int selectedTipoActividad;
	private List<Mantenedor> tipoActividades;
	private Map<Integer, Mantenedor> catalogoActividades;
	/*
	 * 
	 */
	private String nombreActividad;
	private String destinoActividad;
	private Date fechaActividad;
	private List<Mantenedor> catalogoEstatusActividades;
	private int selectedEstatusActividad;
	
	/*
	 * 
	 */
	private Actividad actividad;
	private List<Actividad> actividades;
	private List<Mantenedor> estatusList;
	private int selectedEstatus;
	
	public CertificacionManagedBean(){
		super();
		contactos = new ArrayList<Contacto>();
		actividades = new ArrayList<Actividad>();
		estatusList = new ArrayList<Mantenedor>();
		actividad = new Actividad();
		catalogoActividades = new HashMap<Integer, Mantenedor>();
	}
	
	private String getLoggedUser(){
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	public Long getCertificacionId(){
		return certificacionId;
	}
	
	public IService getService() {
		return service;
	}
	public void setService(IService service) {
		this.service = service;
	}
	public String getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}
	public String getNombreCertificacion() {
		return nombreCertificacion;
	}
	public void setNombreCertificacion(String nombreCertificacion) {
		this.nombreCertificacion = nombreCertificacion;
	}
	public String getDescripcionCertificacion() {
		return descripcionCertificacion;
	}
	public void setDescripcionCertificacion(String descripcionCertificacion) {
		this.descripcionCertificacion = descripcionCertificacion;
	}
	public int getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public String getIdCompetencia() {
		return idCompetencia;
	}
	public void setIdCompetencia(String idCompetencia) {
		this.idCompetencia = idCompetencia;
	}
	public String getNombreCompetencia() {
		return nombreCompetencia;
	}
	public void setNombreCompetencia(String nombreCompetencia) {
		this.nombreCompetencia = nombreCompetencia;
	}
	public float getCosto() {
		return costo;
	}
	public void setCosto(float costo) {
		this.costo = costo;
	}
	public List<Contacto> getContactos() {
		contactos = service.getContactosInatec();
		return contactos;
	}
	public Contacto[] getSelectedContactos() {
		return selectedContactos;
	}
	public void setSelectedContactos(Contacto[] selectedContactos) {
		this.selectedContactos = selectedContactos;
	}
	/*
	public void handleDateSelect(DateSelectEvent event){
		Date date = event.getDate();
	}*/
	public Date getFechaIniciaDivulgacion() {
		return fechaIniciaDivulgacion;
	}
	public void setFechaIniciaDivulgacion(Date fechaIniciaDivulgacion) {
		this.fechaIniciaDivulgacion = fechaIniciaDivulgacion;
	}
	public Date getFechaFinalizaInscripcion() {
		return fechaFinalizaInscripcion;
	}
	public void setFechaFinalizaInscripcion(Date fechaFinalizaInscripcion) {
		this.fechaFinalizaInscripcion = fechaFinalizaInscripcion;
	}
	public Date getFechaIniciaConvocatoria() {
		return fechaIniciaConvocatoria;
	}
	public void setFechaIniciaConvocatoria(Date fechaIniciaConvocatoria) {
		this.fechaIniciaConvocatoria = fechaIniciaConvocatoria;
	}
	public String getIdCentro() {
		return idCentro;
	}
	public void setIdCentro(String idCentro) {
		this.idCentro = idCentro;
	}
	public String getNombreCentro() {
		return nombreCentro;
	}
	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}
	public String getDireccionCentro() {
		return direccionCentro;
	}
	public void setDireccionCentro(String direccionCentro) {
		this.direccionCentro = direccionCentro;
	}
	public Date getFechaIniciaEvaluacion() {
		return fechaIniciaEvaluacion;
	}
	public void setFechaIniciaEvaluacion(Date fechaIniciaEvaluacion) {
		this.fechaIniciaEvaluacion = fechaIniciaEvaluacion;
	}
	public int getSelectedEstatus() {
		return selectedEstatus;
	}
	public void setSelectedEstatus(int estatus) {
		this.selectedEstatus = estatus;
	}
	public List<Mantenedor> getEstatusList() {
		estatusList = service.getMantenedorEstatusCertificacion();
		return estatusList;
	}
	public List<Mantenedor> getTipoActividades(){
		tipoActividades = service.getMantenedorActividades();
		System.out.println("Tipos actividad:"+tipoActividades.get(0).getValor());
		System.out.println("Tipos actividad:"+tipoActividades.get(1).getValor());
		for(int i=0; i<tipoActividades.size(); i++){
			catalogoActividades.put(tipoActividades.get(i).getId(), tipoActividades.get(i));
		}
		return tipoActividades;
	}
	public int getSelectedTipoActividad() {
		return selectedTipoActividad;
	}

	public void setSelectedTipoActividad(int selectedTipoActividad) {
		this.selectedTipoActividad = selectedTipoActividad;
	}
	public String getNombreActividad() {
		return nombreActividad;
	}
	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}
	public String getDestinoActividad() {
		return destinoActividad;
	}
	public void setDestinoActividad(String destinoActividad) {
		this.destinoActividad = destinoActividad;
	}
	public Date getFechaActividad() {
		return fechaActividad;
	}
	public void setFechaActividad(Date fechaActividad) {
		this.fechaActividad = fechaActividad;
	}
	public List<Actividad> getActividades() {
		if(certificacionId != null){
			actividades = service.getActividades(certificacionId);
			return actividades;
		}
		else
			return null;
	}
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	
	public void guardar(){	
			
		System.out.println("Fecha Inicia Divulgacion: "+fechaIniciaDivulgacion.toString());
		System.out.println("Fecha Finaliza Inscripcion: "+fechaFinalizaInscripcion.toString());
		System.out.println("Fecha Inicia Convocatoria: "+fechaIniciaConvocatoria.toString());
		System.out.println("Fecha Inicia Evaluacion: "+fechaIniciaEvaluacion.toString());
		System.out.println("Descripcion de la certificacion: "+nombreCertificacion);
		System.out.println("Descripcion de la certificacion: "+descripcionCertificacion);
		System.out.println("Nombre del centro: "+nombreCentro);
		System.out.println("Direccion del centro: "+direccionCentro);
		System.out.println("Costo: "+costo);
		System.out.println("Selected contactos : "+selectedContactos[0].getNombreCompleto());
		//selectedEstatus=estatusList.get(0);
		System.out.println("Selected estatus : "+selectedEstatus);
		System.out.println("Guardar idCompetencia : "+getIdCompetencia());
		System.out.println("Guardar nombreCompetencia : "+getNombreCompetencia());
		
		List<Unidad> unidades = new ArrayList<Unidad>();
		unidades.add(new Unidad(idCompetencia, nombreCompetencia));
		
		service.guardarCertificacion(
				getNombreCertificacion(),
				getDescripcionCertificacion(),
				getIdCompetencia(),
				getNombreCompetencia(),
				getDisponibilidad(),
				null, //new Date(), //fechaInicia,
				null, //new Date(), //fechaFinaliza,
				Integer.valueOf(getIdCentro()),
				getNombreCentro(),
				getDireccionCentro(), 
				null, //programador,
				getFechaIniciaDivulgacion(), 
				new Date(), 
				getFechaFinalizaInscripcion(),
				getFechaIniciaConvocatoria(),
				getFechaIniciaEvaluacion(),
				null, //creador,
				"N/D", //referencia, 
				0, //nivelCompetencia, 
				null, //requisitos, 
				actividades, 
				null, //solicitudes,
				selectedContactos,
				selectedEstatus);
	}
	
	public void guardarActividad(){
		
		Mantenedor tipoActividad = catalogoActividades.get(selectedTipoActividad);
		Mantenedor estadoActividad = catalogoActividades.get(selectedTipoActividad);
		//catalogoActividades.values();
		Usuario usuario = service.getUsuarioLocal(getLoggedUser());
		
		System.out.println("Actividad seleccionada: "+selectedTipoActividad);
		System.out.println("Actividad nombre: "+nombreActividad);
		System.out.println("Actividad destino: "+destinoActividad);
		System.out.println("Actividad fecha: "+fechaActividad);
		
		actividad = new Actividad();
		actividad.setTipo(tipoActividad);
		actividad.setFechaInicial(fechaActividad);
		actividad.setFechaFinal(fechaActividad);
		actividad.setCreador(usuario);
		actividad.setEjecutor(usuario);
		actividad.setNombre(nombreActividad);
		actividad.setDescripcion(nombreActividad);
		actividad.setDestino(destinoActividad);
		actividad.setEstado(estadoActividad);
		actividades.add(actividad);
		
		/*
		System.out.println("antes de imprimrir");
		
		System.out.println("Nombre: "+actividad.getNombre());
		System.out.println("Descripcion: "+actividad.getDescripcion());
		System.out.println("Fecha inicial: "+actividad.getFechaInicial().toString());
		System.out.println("Fecha final: "+actividad.getFechaFinal().toString());
		System.out.println("Destino: "+actividad.getDestino());
		
		actividad.setCreador(service.getUsuarioLocal("admin"));
		actividad.setEjecutor(service.getUsuarioLocal("admin"));
		actividad.setEstado(service.getMantenedorActividades().get(0));
		
		actividades.add(actividad);
		actividades.add(actividad);
		*/
		
		//System.out.println("Creador: "+actividad.getCreador().getUsuarioAlias());
		//System.out.println("Ejecutor: "+actividad.getEjecutor().getUsuarioAlias());
		//System.out.println("Estado: "+actividad.getEstado().getValor());
		
		//service.guardar(actividad);
	}
	
	public String nuevaCertificacion(){
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
	}

	public int getSelectedEstatusActividad() {
		return selectedEstatusActividad;
	}

	public void setSelectedEstatusActividad(int selectedEstatusActividad) {
		this.selectedEstatusActividad = selectedEstatusActividad;
	}
}