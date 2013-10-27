package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import support.Ifp;
import support.Planificacion;
import support.USolicitud;
import support.UCompetencia;
import dao.IDao;
import dao.IDaoInatec;
import model.Actividad;
import model.Certificacion;
import model.Contacto;
import model.Mantenedor;
import model.Perfil;
import model.Requisito;
import model.Rol;
import model.Solicitud;
import model.Unidad;
import model.Usuario;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ServiceImp implements IService {
	
	@Autowired
	private IDao<Certificacion> certificacionDao;
	@Autowired
	private IDao<Requisito> requisitoDao;
	@Autowired
	private IDao<Usuario> usuarioDao;
	@Autowired
	private IDao<Contacto> contactoDao;
	@Autowired
	private IDao<Rol> rolDao;
	//Inicio : SCCL || 22.10.2013 || Ing. Miriam Martinez Cano || Propiedades definidas para ser utilizados principalmente en el Modulo SOLICITUDES	
	@Autowired
	private IDao<Solicitud> solicitudDao;
	//Fin : SCCL || 22.10.2013 || Ing. Miriam Martinez Cano || Propiedades definidas para ser utilizados principalmente en el Modulo SOLICITUDES
	@Autowired
	private IDao<Mantenedor> mantenedorDao;
	@Autowired
	private IDao<Actividad> actividadDao;
	@Autowired	
	private IDao<Perfil> perfilDao;
	@Autowired	
	private IDaoInatec inatecDao;
		
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
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
										String referencial,
										int nivelCompetencia, 
										List<Requisito> requisitos,
										List<Unidad> unidades, 
										List<Actividad> actividades,
										List<Solicitud> solicitudes, 
										Contacto[] involucrados,
										Mantenedor estatus) {
		Usuario usuario = usuarioDao.findOneByQuery("select u from usuarios u where u.id=3");
		Certificacion certificacion = new Certificacion();
		certificacion.setNombre(nombre);
		certificacion.setDescripcion(descripcion);
		certificacion.setInicia(fechaInicia);
		certificacion.setFinaliza(fechaFinaliza);
		certificacion.setIfpId(ifp);
		certificacion.setIfpNombre(ifpNombre);
		certificacion.setIfpDireccion(ifpDireccion);
		certificacion.setProgramador(usuario);	//programador
		certificacion.setDivulgacionInicia(fechaIniciaDivulgacion);
		certificacion.setDivulgacionFinaliza(fechaFinalizaDivulgacion);
		certificacion.setInscripcionFinaliza(fechaFinalizaInscripcion);
		certificacion.setConvocatoriaInicia(fechaIniciaConvocatoria);
		certificacion.setEvaluacionInicia(fechaIniciaEvaluacion);
		certificacion.setCreador(usuario);		//creador
		certificacion.setReferencial(referencial);
		certificacion.setNivelCompetencia(nivelCompetencia);
		certificacion.setUnidades(unidades);
		//certificacion.setActividades(actividades);
		certificacion.setSolicitudes(solicitudes);
		certificacion.setInvolucrados(involucrados);
		certificacion.setEstatus(1);
		
		List<Contacto> contactos = new ArrayList<Contacto>();
		contactos.add(usuario.getContacto());
		//contactos.add(usuario.getContacto());
		
		Actividad actividad = new Actividad("Actividad 1","Actividad 1", "Managua", new Date(), new Date(), usuario, usuario, contactos);
		List<Actividad> actividades2 = new ArrayList<Actividad>();
		actividades2.add(actividad);
		certificacion.setActividades(actividades2);
		
		certificacionDao.save(certificacion);
		//certificacion.setActividades(actividades2);
		//certificacionDao.save(certificacion);
	}
	
	@Override
	public List<Requisito> getRequisitos(int certificacionId) {
		return requisitoDao.findAll(Requisito.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateRequisito(Requisito requisito) {
		requisitoDao.save(requisito);
	}

	@Override
	public List<Usuario> getUsuarios() {
		return usuarioDao.findAll(Usuario.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)	
	public void updateUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	@Override
	public List<Planificacion> getPlanificacion() {
		List<Planificacion> planificaciones = new ArrayList<Planificacion>();
		Contacto evalua;
		Contacto coordina;

		for(int i = 0; i<5; i++){
			Planificacion p = new Planificacion();
			p.setIdCentro(i+1);
			p.setNombreCentro("Centro "+i+1);
			p.setUnidadCompetencia("Unidad "+i+1);
			p.setDisponibilidad(i+1);
			p.setEstatus("Convocatoria");
			evalua = contactoDao.findById(Contacto.class, 2);
			coordina = contactoDao.findById(Contacto.class, 3);
			p.setEvalua(evalua.getPrimerNombre()+" "+evalua.getPrimerApellido());
			p.setCoordina(coordina.getPrimerNombre()+" "+coordina.getPrimerApellido());
			p.setRegistrado("11-10-2013");
			planificaciones.add(p);
		}
		return planificaciones;
	}

	@Override
	public List<UCompetencia> getUcompetenciaSinPlanificar() {
		return inatecDao.getCertificacionesSinPlanificar();
	}
	
	@Override
	public List<Contacto> getContactosInatec() {
		return contactoDao.findAllByQuery("Select c from contactos c where c.inatec='true' and c.rol.idRolInatec in (213,214,215,216)");
	}

	@Override
	public Usuario getUsuarioLocal(String usuario) {
		return usuarioDao.findOneByQuery("Select u from usuarios u where u.usuarioEstatus='true' and u.usuarioAlias="+"'"+usuario+"'");
	}
	
	@Override
	public Usuario getUsuarioInatec(String usuario) {
		return inatecDao.getUsuario(usuario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void RegistrarUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void RegistrarUsuarioOpenId(String login, String nombre, String apellido, String email, String rol) {
		Usuario usuario = new Usuario(null, //contacto 
				                      login, //usuarioAlias
				                      "", //usuarioPwd
				                      rolDao.findOneByQuery("Select r from roles r where r.estatus='true' and r.nombre="+"'"+rol+"'"), //rol 
				                      true // usuarioEstatus
				                      );		
		usuarioDao.save(usuario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void guardarContacto(Contacto contacto) {			
			contactoDao.save(contacto);
	}

	@Override
	public boolean isNuevoContactoInatec(String usuario) {
		Contacto contacto = contactoDao.findOneByQuery("select c from contactos c where c.inatec=true and c.usuarioInatec="+"'"+usuario+"'");
		if(contacto == null)
			return true;
		else
			return false;
	}

	@Override
	public Contacto generarNuevoContactoInatec(String usuario) {
		Contacto contacto = inatecDao.generarContacto(usuario);
		Rol rol = rolDao.findOneByQuery("Select r from roles r where r.idRolInatec="+inatecDao.getIdRol(usuario));
		if(rol != null)
			contacto.setRol(rol);
		return contacto;
	}
	
	//Inicio : SCCL || 22.10.2013 || Ing. Miriam Martinez Cano || Metodos definidos para ser utilizados principalmente en el Modulo SOLICITUDES
	@Override
	public List<USolicitud> getUSolicitudes () {
			System.out.println("Desde el metodo getSolicitudes del ServiceImp");
			
			List<Solicitud> Sols = solicitudDao.findAll(Solicitud.class);
			List<USolicitud> uSols = new ArrayList<USolicitud>();;
			
			for(int i = 0; i<Sols.size(); i++){
				
				USolicitud uSolicitud = new USolicitud (Sols.get(i).getCertificacion().getIfpNombre(), 
														Sols.get(i).getContacto().getNombreCompleto(),
														Sols.get(i).getCertificacion().getNombre(), 
														Sols.get(i).getCertificacion().getInvolucrados().get(0).getCorreo1(), 
														Sols.get(i).getEstatus(),
														Sols.get(i).getFechaRegistro());
				
				uSols.add(uSolicitud);			
			}
			
			return uSols;
			
	}
	
	@Override
	public List<Solicitud> getSolicitudes() {
		return solicitudDao.findAll(Solicitud.class);		
	}	
	
	//Fin : SCCL || 22.10.2013 || Ing. Miriam Martinez Cano || Metodos definidos para ser utilizados principalmente en el Modulo SOLICITUDES	

	@Override
	public Rol getRolById(int id) {
		return rolDao.findOneByQuery("Select r from roles r where r.id_rol="+id);
	}

	@Override
	public List<Mantenedor> getMantenedorActividades() {
		return mantenedorDao.findAllByQuery("Select m from mantenedores m where m.tipo='1' order by 1");
	}

	@Override
	public List<Mantenedor> getMantenedorEstatusCertificacion() {
		return mantenedorDao.findAllByQuery("Select m from mantenedores m where m.tipo='3' order by 1");
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Object guardar (Object objeto) {
		if (objeto instanceof Solicitud) {
			return solicitudDao.save((Solicitud) objeto);
		}
		if(objeto instanceof Actividad){
			Usuario usuario = usuarioDao.findOneByQuery("select u from usuarios u where u.id=3");
			((Actividad) objeto).setCreador(usuario);
			((Actividad) objeto).setEjecutor(usuario);
			Mantenedor estado = mantenedorDao.findOneByQuery("Select m from mantenedores m where m.id=1");
			((Actividad) objeto).setEstado(estado);
			((Actividad) objeto).setNombre("actividad 1");
			((Actividad) objeto).setDescripcion("prueba");
			((Actividad) objeto).setDestino("managua");
			((Actividad) objeto).setFechaInicial(new Date());
			((Actividad) objeto).setFechaFinal(new Date());
			return actividadDao.save((Actividad)objeto);
		}
		if(objeto instanceof Mantenedor)
			return mantenedorDao.save((Mantenedor)objeto);
		if(objeto instanceof Perfil)
			return perfilDao.save((Perfil)objeto);
		if(objeto instanceof Rol)
			return rolDao.save((Rol)objeto);
		if(objeto instanceof Contacto)
			return contactoDao.save((Contacto)objeto);
		if(objeto instanceof Usuario)
			return usuarioDao.save((Usuario)objeto);		
		return null;
	}
	
	@Override
	public List<Certificacion> getCertificacionesByIdIfp (int idIfp) {
		return certificacionDao.findAllByQuery("Select c from certificaciones c where c.ifpId="+idIfp);
	}
	
	@Override
	public List<Ifp> getIfpByInatec () {
		return inatecDao.getIfpInatec();
	}

	@Override
	public List<Actividad> getActividades(Long certificacionId) {
		return actividadDao.findAllByQuery("Select a from actividades a where a.certificacion.id="+certificacionId);
	}

	@Override
	public List<Mantenedor> getMantenedores() {
		return mantenedorDao.findAllByQuery("Select m from mantenedores m");
	}
}