package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import model.Actividad;
import model.Certificacion;
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
@Scope(value="session")
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
	private List<Mantenedor> tipoActividades;
	private Usuario usuario;
	private Map<Integer, Mantenedor> catalogoTiposActividad;
	private int selectedTipoActividad;
	private Map<Integer, Mantenedor> catalogoEstatusActividad;
	private int selectedEstatusActividad;
	private Certificacion certificacion;
	
	/*
	 * 
	 */
	private String nombreActividad;
	private String destinoActividad;
	private Date fechaActividad;
	
	/*
	 * 
	 */
	private Actividad actividad;
	private List<Actividad> actividades;
	private List<Mantenedor> estatusList;
	private int selectedEstatus;
	
	public CertificacionManagedBean(){
		super();
		certificacion = new Certificacion();
		contactos = new ArrayList<Contacto>();
		actividad = new Actividad();
		actividades = new ArrayList<Actividad>();
		estatusList = new ArrayList<Mantenedor>();
		catalogoTiposActividad = new HashMap<Integer, Mantenedor>();
		catalogoEstatusActividad = new HashMap<Integer, Mantenedor>();
	}
	
	@PostConstruct
	private void init(){
		usuario = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());
		catalogoTiposActividad = service.getMapMantenedoresByTipo("1");
		System.out.println("Catalogo de tipos de actividad: "+catalogoTiposActividad.size());
		catalogoEstatusActividad = service.getMapMantenedoresByTipo("4");
		System.out.println("Catalogo de estatus de actividad: "+catalogoEstatusActividad.size());
	}
	
	public Certificacion getCertificacion(){
		return this.certificacion;
	}
	
	public void setCertificacion(Certificacion certificacion){
		this.certificacion = certificacion;
	}
	
	public void agregarActividad(Actividad actividad){
		actividades.add(actividad);
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
			catalogoTiposActividad.put(tipoActividades.get(i).getId(), tipoActividades.get(i));
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
			return actividades;
	}
	
	/*public List<Actividad> getActividades(int codigo){
		System.out.println("Codigo de actividad: "+codigo);
		List<Actividad> lista = new ArrayList<Actividad>();
		if(codigo==0)
			return actividades;
		else{
			for(int i=0; i<actividades.size(); i++){
				if(actividades.get(i).getId()==(long)codigo)
					lista.add(actividades.get(i));
			}
		}
		return lista;
	}*/
	
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	
	public String guardar(){
					
		List<Unidad> unidades = new ArrayList<Unidad>();
		unidades.add(new Unidad(idCompetencia, nombreCompetencia,true));
		
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
		
		return "/modulos/planificacion/planificacion?faces-redirect=true";
	}
	
	private Usuario getUsuario(){
		return this.usuario;
	}
	
	public void guardarActividad(Actividad actividad){
		actividad.setCreador(getUsuario());
		System.out.println("Nueva Actividad: "+actividad.getNombre());
		this.actividades.add(actividad);
	}
	public String nuevaCertificacion(){
		return "/modulos/planificacion/edicion_planificacion?faces-redirect=true";
		//return "/modulos/planificacion/edicion?faces-redirect=true";
	}
	
	public String editarCertificacion(Certificacion certificacion){
		this.certificacion = certificacion;
		System.out.println("Nombre de la certificacion a editar: "+certificacion.getNombre());
		return "/modulos/planificacion/edicion?faces-redirect=true";
	}

	public int getSelectedEstatusActividad() {
		return selectedEstatusActividad;
	}

	public void setSelectedEstatusActividad(int selectedEstatusActividad) {
		this.selectedEstatusActividad = selectedEstatusActividad;
	}

	public Map<Integer, Mantenedor> getCatalogoTiposActividad() {
		return this.catalogoTiposActividad;
	}

	public Map<Integer, Mantenedor> getCatalogoEstatusActividad() {
		return this.catalogoEstatusActividad;
	}
}