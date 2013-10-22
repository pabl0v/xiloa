package service;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.User;

import support.Involucrado;
import support.Planificacion;
import support.UCompetencia;
import model.Actividad;
import model.Contacto;
import model.Requisito;
import model.Solicitud;
import model.Unidad;
import model.Usuario;

public interface IService {
	
	public Contacto generarNuevoContactoInatec(String usuario);
	
	public boolean isNuevoContactoInatec(String usuario);
	
	public void guardarContacto(Contacto contacto);
		
	public void guardarCertificacion(	String nombre, 
										String descripcion,
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
										List<Unidad> unidades,
										List<Actividad> actividades,
										List<Solicitud> solicitudes,
										List<Contacto> involucrados,
										String estatus);
	public List<Requisito> getRequisitos(int certificacionId);
	public void updateRequisito(Requisito requisito);
	public List<Usuario> getUsuarios();
	public void updateUsuario(Usuario usuario);
	public List<Planificacion> getPlanificacion();
	public List<UCompetencia> getUcompetenciaSinPlanificar();
	public List<Involucrado> getContactos();
	public Usuario getUsuario(String username);
	public User loadUserByUsernameFromLocal(String username);
	public User loadUserByUsernameFromInatec(String username);
	public void RegistrarUsuario(Usuario usuario);
	public void RegistrarUsuarioOpenId(String login, String nombre, String apellido, String email, String rol);
}