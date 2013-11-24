package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import support.Departamento;
import support.Ifp;
import support.Municipio;
import support.UCompetencia;
import support.USolicitud;
import support.UsuarioExterno;
import model.Actividad;
import model.Archivo;
import model.Bitacora;
import model.Certificacion;
import model.Contacto;
import model.Evaluacion;
import model.EvaluacionGuia;
import model.Guia;
import model.Instrumento;
import model.Laboral;
import model.Mantenedor;
import model.Pais;
import model.Requisito;
import model.Rol;
import model.Solicitud;
import model.Unidad;
import model.Usuario;

public interface IService {
	
	public void resetPassword(String usuario);
	
	public void registrarUsuarioExterno(UsuarioExterno usuario);
	
	public Map<Long, String> getCatalogoUnidades();
	
	public List<Certificacion> getCertificaciones();
	
	public List<Certificacion> getCertificacionesActivas(Integer parametro, String valor);
	
	public Map<Long, String> getUnidadesByEstructuraId(Integer estructura);
		
	public List<Mantenedor> getMantenedores();
	
	public List<Mantenedor> getMantenedoresByTipo(Integer tipo);
	
	public Map<Integer, Mantenedor> getMapMantenedoresByTipo(String tipo);
	
	public Map<Integer, Departamento> getDepartamentosByInatec();
	
	public Map<Integer, Municipio> getMunicipioDptoByInatec(Integer idDpto);
	
	public List<Actividad> getActividades(Long certificacionId);
	
	public List<Mantenedor> getMantenedorActividades();
	
	public List<Mantenedor> getMantenedorEstatusCertificacion();
	
	public Rol getRolById(int id);
	
	public Contacto generarNuevoContactoInatec(String usuario);
	
	public boolean isNuevoContactoInatec(String usuario);
	
	public Certificacion guardarCertificacion(Certificacion certificacion, List<Requisito> requisitos, List<Unidad> unidades);
	public List<Requisito> getRequisitos(int certificacionId);
	public void updateRequisito(Requisito requisito);
	public List<Usuario> getUsuarios();
	public void updateUsuario(Usuario usuario);
	public List<UCompetencia> getUcompetenciaSinPlanificar();
	public List<Contacto> getContactosInatec();
	public Usuario getUsuarioLocal(String usuario);
	public Usuario getUsuarioInatec(String usuario);	
	public void RegistrarUsuarioOpenId(String login, String nombre, String apellido, String email);
	public List<USolicitud> getUSolicitudes ();
	public List<Solicitud> getSolicitudes();
	public List<Solicitud> getSolicitudesByParam(HashMap<String, Object> param);
	public List<Solicitud> getSolicitudesByNQParam(String nQuery, Object [] parametros);
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
	public Unidad getUnidadById(Long idUnidad);
	public Instrumento getInstrumentoById(Long idInstrumento);
	public List<Instrumento> getInstrumentoByUnidad (Long idUnidad);
	public List<EvaluacionGuia> getEvaluacionGuiaByEvaluacionId(Long evaluacionId);
	public List<Instrumento> getIntrumentoByEvaluacion(Long evaluacionId);
	public Mantenedor getMantenedorMinByTipo(String tipo);
	public Mantenedor getMantenedorMaxByTipo(String tipo);
	public Mantenedor getMantenedorById(Integer idMantenedor);	
	public List<Guia> getGuiaByParam(String namedString, Object [] parametros);
	public List<Archivo> getArchivoByParam (String namedString, Object [] parametros);
	public List<Bitacora> getBitacoras(Long actividadId);
	public List<Requisito> getRequisitos(int cursoId, int centroId);
	public List<Unidad> getUnidades(int cursoId, int centroId);
	public Archivo getArchivoOneByParam (String namedString, Object [] parametros);
	public List<Pais> getPaises ();
	public Pais getPaisByNQParam(String namedString, Object [] param);
}