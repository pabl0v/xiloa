package service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import support.Ifp;
import support.UCompetencia;
import support.USolicitud;
import model.Actividad;
import model.Certificacion;
import model.Contacto;
import model.Evaluacion;
import model.Guia;
import model.Instrumento;
import model.Laboral;
import model.Mantenedor;
import model.Requisito;
import model.Rol;
import model.Solicitud;
import model.Unidad;
import model.Usuario;

public interface IService {
	
	public List<Certificacion> getCertificaciones();
		
	public List<Mantenedor> getMantenedores();
	
	public List<Mantenedor> getMantenedoresByTipo(Integer tipo);
	
	public Map<Integer, Mantenedor> getMapMantenedoresByTipo(String tipo);
	
	public List<Actividad> getActividades(Long certificacionId);
	
	public List<Mantenedor> getMantenedorActividades();
	
	public List<Mantenedor> getMantenedorEstatusCertificacion();
	
	public Rol getRolById(int id);
	
	public Contacto generarNuevoContactoInatec(String usuario);
	
	public boolean isNuevoContactoInatec(String usuario);
		
	public void guardarCertificacion(	String nombre, 
										String descripcion,
										String codigoCompetencia,
										String nombreCompetencia,
										int disponibilidad,
										Date fechaInicia,
										Date fechaFinaliza,
										int ifp,
										String ifpNombre,
										String ifpDireccion,
										Usuario programador,
										Date fechaIniciaDivulgacion,
										Date fechaFinalizaDivulgacion,
										Date fechaFinalizaInscripcion,
										Date fechaIniciaConvocatoria,
										Date fechaIniciaEvaluacion,
										Usuario creador,
										String referencia,
										int nivelCompetencia,
										List<Requisito> requisitos,
										List<Actividad> actividades,
										List<Solicitud> solicitudes,
										Contacto[] involucrados,
										int estatus);
	public List<Requisito> getRequisitos(int certificacionId);
	public void updateRequisito(Requisito requisito);
	public List<Usuario> getUsuarios();
	public void updateUsuario(Usuario usuario);
	public List<UCompetencia> getUcompetenciaSinPlanificar();
	public List<Contacto> getContactosInatec();
	public Usuario getUsuarioLocal(String usuario);
	public Usuario getUsuarioInatec(String usuario);	
	public void RegistrarUsuarioOpenId(String login, String nombre, String apellido, String email, String rol);
	//Inicio : SCCL || 22.10.2013 || Ing. Miriam Martinez Cano || Metodos definidos para ser utilizados principalmente en el Modulo SOLICITUDES
	public List<USolicitud> getUSolicitudes ();
	public List<Solicitud> getSolicitudes();
	public List<Solicitud> getSolicitudesByParam(HashMap<String, Object> param);
	public Certificacion getCertificacionById(Long id);
	public Contacto getContactoByCedula(String cedula);
	public Object guardar(Object obj);	
	public List<Certificacion> getCertificacionesByIdIfp(Integer id);
	public List<Ifp> getIfpByInatec ();
	public List<Laboral> getListLaboralByTipo(Integer tipo, Contacto contacto);
	public Laboral getLaboralById(Long idLaboral);
	public List<Evaluacion> getEvaluaciones(Solicitud solicitud);
	public Solicitud getSolicitudById(Long idSolicitud);
	public List<Unidad> getUnidadesByCertificacionId(Long certificacionId);
	public List<Instrumento> getInstrumentosByCertificacionId(Long certificacionId);
	public List<Guia> getGuiasByInstrumentoId(Long instrumentoId);
	//Fin : SCCL || 22.10.2013 || Ing. Miriam Martinez Cano || Metodos definidos para ser utilizados principalmente en el Modulo SOLICITUDES
	
}