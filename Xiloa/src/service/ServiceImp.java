package service;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import reporte.ControlGenericoReporte;
import security.Authority;
import support.Departamento;
import support.Ifp;
import support.Item;
import support.JavaEmailSender;
import support.Municipio;
import support.PasswordGenerator;
import support.USolicitud;
import support.UCompetencia;
import support.UsuarioExterno;
import dao.IDao;
import dao.IDaoInatec;
import model.Actividad;
import model.Archivo;
import model.Auditoria;
import model.Bitacora;
import model.Certificacion;
import model.Contacto;
import model.Convocatoria;
import model.Evaluacion;
import model.EvaluacionGuia;
import model.EvaluacionGuiaId;
//import model.EvaluacionUnidad;
import model.Guia;
import model.Instrumento;
import model.Laboral;
import model.Mantenedor;
import model.Pais;
import model.Perfil;
import model.Requisito;
import model.Rol;
import model.Solicitud;
import model.Usuario;

/**
 * 
 * @author Denis Chavez, Miriam Martinez
 *
 * @version 1.0
 * 
 * Esta clase implementa la interface de servicio IService con todas las reglas de negocio y manejo de transacciones
 */

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
	@Autowired
	private IDao<Solicitud> solicitudDao;
	@Autowired
	private IDao<Mantenedor> mantenedorDao;
	@Autowired
	private IDao<Actividad> actividadDao;
	@Autowired	
	private IDao<Perfil> perfilDao;
	@Autowired	
	private IDao<Guia> guiaDao;
	@Autowired	
	private IDao<Instrumento> instrumentoDao;
	@Autowired	
	private IDaoInatec inatecDao;
	@Autowired	
	private IDao<Laboral> laboralDao;
	@Autowired	
	private IDao<Evaluacion> evaluacionDao;
	@Autowired	
	private IDao<EvaluacionGuia> evaluacionGuiaDao;
	@Autowired
	private IDao<Long> longDao;
	@Autowired
	private IDao<BigInteger> bigIntegerDao;
	@Autowired
	private IDao<Bitacora> bitacoraDao;
	@Autowired
	private IDao<Integer> integerDao;
	@Autowired
	private IDao<Archivo> archivoDao;
	@Autowired
	private IDao<Pais> paisDao;
	@Autowired
	private JavaEmailSender email;
	@Autowired
	private IDao<Object> objectDao;
	@Autowired
	private IDao<Auditoria> auditoriaDao;
	@Autowired
	private IDao<Item> itemDao;
	@Autowired
	private IDao<Convocatoria> convocatoriaDao;

	private List<Mantenedor> mantenedores;
	private Map<Integer, Mantenedor> catalogoEstatusCertificacion;
	private Map<Integer, Mantenedor> catalogoTiposActividad;
	private Map<Integer, Mantenedor> catalogoEstatusActividad; 
	private Map<Integer, Mantenedor> catalogoTiposInstrumento;
	private Map<Integer, Mantenedor> catalogoTiposDatosLaborales;	
	private Map<Long, Item> catalogoUnidades;
	private List<Usuario> usuarios;
	private List<Rol> roles;
	private Map<Integer, Mantenedor> catalogoGenero;
	private Map<Integer, Mantenedor> catalogoEstadoSolicitud;
	private Map<Integer, Mantenedor> catalogoPortafolio;
	private Map<Integer, Mantenedor> catalogoEstadosEvaluacion;
	private Map<Long, Pais> catalogoPaises;
	private Map<Integer, Departamento> catalogoDepartamentos;
	protected final Log logger = LogFactory.getLog(getClass());
	
	/*
	 * Constructor por defecto
	 */
	
	public ServiceImp(){
		super();
		mantenedores = new ArrayList<Mantenedor>();
		catalogoEstatusCertificacion = new HashMap<Integer, Mantenedor>();
		catalogoTiposActividad = new HashMap<Integer, Mantenedor>();
		catalogoEstatusActividad = new HashMap<Integer, Mantenedor>();
		catalogoTiposInstrumento = new HashMap<Integer, Mantenedor>();
		catalogoTiposDatosLaborales = new HashMap<Integer, Mantenedor>();
		catalogoUnidades = new HashMap<Long, Item>();
		usuarios = new ArrayList<Usuario>();
		roles = new ArrayList<Rol>();
		catalogoGenero = new HashMap<Integer, Mantenedor> ();
		catalogoEstadoSolicitud = new HashMap<Integer, Mantenedor>();
		catalogoPortafolio = new HashMap<Integer, Mantenedor>();
		catalogoEstadosEvaluacion = new HashMap<Integer, Mantenedor>();
		catalogoPaises = new HashMap<Long, Pais>();
		catalogoDepartamentos = new HashMap<Integer, Departamento>();
	}
	
	/*
	 * Método para poblar los catálogos básicos y variables globales de la aplicación.
	 */
	
	@PostConstruct
	public void init(){
		mantenedores = mantenedorDao.findAllByNamedQuery("Mantenedor.findAll");
		usuarios = usuarioDao.findAll(Usuario.class);
		roles = rolDao.findAll(Rol.class);
		
		for(int i=0; i<mantenedores.size(); i++){
			Mantenedor mantenedor = mantenedores.get(i);
			
			switch (Integer.valueOf(mantenedor.getTipo()).intValue()){
				case 1: catalogoTiposActividad.put(mantenedor.getId(), mantenedor); break;		
				case 2: catalogoEstatusActividad.put(mantenedor.getId(), mantenedor); break;
				case 3: catalogoEstatusCertificacion.put(mantenedor.getId(), mantenedor); break;
				case 4:
				case 5: catalogoTiposDatosLaborales.put(mantenedor.getId(), mantenedor); break;
				case 6: catalogoTiposInstrumento.put(mantenedor.getId(), mantenedor); break;
				case 7: catalogoEstadoSolicitud.put(mantenedor.getId(), mantenedor); break;
				case 8: catalogoPortafolio.put(mantenedor.getId(), mantenedor); break;
				case 9: catalogoEstadosEvaluacion.put(mantenedor.getId(), mantenedor); break;
				case 11: catalogoGenero.put(mantenedor.getId(), mantenedor); break;
			}
		}
		
		catalogoUnidades = inatecDao.getCatalogoUnidades();
		
	    List<Pais> listaPaises = new ArrayList<Pais> ();
	    listaPaises = getPaises();
	    
	    for (Pais p : listaPaises) {
	    	this.catalogoPaises.put(p.getId(), p);
	    }		 
		
		this.catalogoDepartamentos = getDepartamentosByInatec();
	}
	
	/*
	 * @return obtiene un map con el catálogo de países
	 * 
	 */
	
	@Override
	public Map<Long, Pais> getCatalogoPaises() {
		return catalogoPaises;
	}

	/*
	 * @return obtiene un map con el catálogo de departamentos
	 * 
	 */
	
	@Override
	public Map<Integer, Departamento> getCatalogoDepartamentos() {
		return catalogoDepartamentos;
	}

	/*
	 * @return obtiene un map con el catálogo de estados se una evaluación
	 * 
	 */

	@Override
	public Map<Integer, Mantenedor> getCatalogoEstadosEvaluacion() {
		return catalogoEstadosEvaluacion;
	}
	
	/*
	 * @return obtiene un map con el catálogo de portafolios de los solicitantes
	 * 
	 */

	@Override
	public Map<Integer, Mantenedor> getCatalogoPortafolio() {
		return catalogoPortafolio;
	}
	
	/*
	 * @return obtiene un map con el catálogo de posibles estados de una solicitud
	 * 
	 */
	
	@Override
	public Map<Integer, Mantenedor> getCatalogoEstadoSolicitud() {
		return catalogoEstadoSolicitud;
	}
	
	/*
	 * @return obtiene un map con el catálogo de géneros
	 * 
	 */

	@Override
	public Map<Integer, Mantenedor> getCatalogoGenero() {
		return catalogoGenero;
	}
	
	/*
	 * @return obtiene el listado de mantenedores
	 * 
	 */

	@Override
	public List<Mantenedor> getMantenedores(){
		mantenedores = mantenedorDao.findAllByNamedQuery("Mantenedor.findAll");
		return  mantenedores;
	}
	
	/*
	 * @return obtiene un map con el catálogo de posibles estatus de una certificación
	 * 
	 */

	@Override
	public Map<Integer, Mantenedor> getCatalogoEstatusCertificacion(){
		return catalogoEstatusCertificacion;
	}
	
	/*
	 * @return obtiene un map con el catálogo de tipos de actividad en una planificación
	 * 
	 */
	
	@Override
	public Map<Integer, Mantenedor> getCatalogoTiposActividad(){
		return catalogoTiposActividad;
	}
	
	/*
	 * @return obtiene un map con el catálogo de posibles estatus de una actividad de planificación
	 * 
	 */

	@Override
	public Map<Integer, Mantenedor> getCatalogoEstatusActividad(){
		return catalogoEstatusActividad;
	}
	
	/*
	 * @return obtiene un map con el catálogo de tipos de instrumentos de evaluación
	 * 
	 */

	@Override
	public Map<Integer, Mantenedor> getCatalogoTiposInstrumento(){
		return catalogoTiposInstrumento;
	}
	
	/*
	 * @return obtiene un map con el catálogo de tipos de datos laborales posibles
	 * 
	 */

	@Override
	public Map<Integer, Mantenedor> getCatalogoTiposDatosLaborales(){
		return catalogoTiposDatosLaborales;
	}
	
	/*
	 * @return obtiene un map con el catálogo de unidades de competencia
	 * 
	 */
	
	@Override
	public Map<Long, Item> getCatalogoUnidades(){
		this.catalogoUnidades = inatecDao.getCatalogoUnidades();
		return catalogoUnidades;
	}
	
	/*
	 * @return obtiene la descripción de una unidad de competencia
	 * @param el código de la unidad de competencia
	 * 
	 */

	@Override
	public String getCompetenciaDescripcion(Long codigo){
		return catalogoUnidades.get(codigo).getDescripcion();
	}
	
	/*
	 * @return obtiene el listado de usuarios del sistema
	 * 
	 */

	@Override
	public List<Usuario> getUsuarios() {
		usuarios = usuarioDao.findAll(Usuario.class); 
		return usuarios;
	}
	
	/*
	 * @return obtiene el listado de roles del sistema
	 * 
	 */

	@Override
	public List<Rol> getRoles() {
		roles = rolDao.findAll(Rol.class); 
		return roles;
	}
	
	/*
	 * @return obtiene el listado de certificaciones ofertadas en un centro
	 * @param código del centro
	 * 
	 */
	
	@Override
	public List<Certificacion> getCertificaciones(Integer entidadId){
		return certificacionDao.findAllByNamedQueryParam("Certificacion.findByIfpId", new Object[] {entidadId});
	}

	/*
	 * @return obtiene el listado de certificaciones ofertadas en un centro
	 * @param código del centro
	 * 
	 */

	//dchavez. 08/04/2014. Para agregar filtro de unidades de competencia por certificacion 
	
	public List<Item> getCertificacionesItem(Integer entidadId){
		return itemDao.findAllByNamedQueryParam("Certificacion.findItemsByIfpId", new Object[] {entidadId});
	}
	
	/*
	 * @return obtiene el listado de certificaciones activas para el nombre o centro indicados
	 * @param número de parámetro y valor del parámetro (1-> nombre, 2-> centro)
	 * 
	 */
	
	@Override
	public List<Certificacion> getCertificacionesActivas(Integer parametro, String valor){
		if(parametro == null)
			return certificacionDao.findAllByNamedQuery("Certificacion.findActivas");
		if(parametro == 1){
			Object [] objs =  new Object [] {"%"+valor.toUpperCase()+"%"};
			return certificacionDao.findAllByNamedQueryParam("Certificacion.findAllByNombre",objs);
		}
		if(parametro == 2){
			Object [] objs =  new Object [] {"%"+valor.toUpperCase()+"%"};
			return certificacionDao.findAllByNamedQueryParam("Certificacion.findAllByCentro",objs);
		}
		return certificacionDao.findAllByNamedQuery("Certificacion.findActivas");
	}	
		
	/*
	 * @return obtiene el listado de requisitos de la certificación solicitada
	 * @param código de la certificación
	 * 
	 */
	
	@Override
	public List<Requisito> getRequisitos(int certificacionId) {
		return requisitoDao.findAll(Requisito.class);
	}
	
	/*
	 * @param el requisito a guardar
	 * Este método registra o actualiza el requisito en cuestión en la base de datos
	 * 
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateRequisito(Requisito requisito) {
		requisitoDao.save(requisito);
	}
	
	/*
	 * @param el usuario a guardar
	 * Este método registra o actualiza el usuario en cuestión en la base de datos
	 * 
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)	
	public void updateUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
	}
	
	/**
	 * @return el listado de cursos ofrecidos por una entidad de formación
	 * @param el código de la entidad
	 */

	@Override
	public List<UCompetencia> getUcompetenciaSinPlanificar(Integer entidadId) {
		return inatecDao.getCertificacionesSinPlanificar(entidadId);
	}
	
	/**
	 * @return el listado de contactos de una entidad de formación
	 * @param el código de la entidad
	 */
	
	@Override
	public List<Contacto> getContactosInatec(Integer entidadId) {
		return contactoDao.findAllByNamedQueryParam("Contacto.findInvolucradosInatec", new Object [] {entidadId});
	}
	
	/**
	 * @return la instancia del usuario local buscado
	 * @param el login del usuario local a buscar
	 */

	@Override
	public Usuario getUsuarioLocal(String usuario) {
		return usuarioDao.findOneByNamedQueryParam("Usuario.findByLogin", new Object[] {usuario});
	}
	
	/**
	 * @return la instancia del usuario inatec buscado
	 * @param el login del usuario inatec a buscar
	 */
	
	@Override
	public Usuario getUsuarioInatec(String usuario) {
		return inatecDao.getUsuario(usuario);
	}
	
	/**
	 * Registra el usuario especificado
	 * 
	 * @param el login del usuario
	 * @param el nombre del usuario
	 * @param el apellido del usuario
	 * @param el email del usuario
	 */
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void RegistrarUsuarioOpenId(String login, String nombre, String apellido, String email) {
				
		Usuario user = usuarioDao.save(new Usuario(null, login, "", rolDao.findById(Rol.class, 6), false, true));
		
		//registrando el contacto
		contactoDao.save(new Contacto(
				user, 
				new HashSet<Laboral>(), 
				user.getRol(), 
				null, 
				(nombre==null)?"N/D":nombre,
				null,
				(apellido==null)?"N/D":apellido,
				null,
				((nombre==null)?"N/D":nombre)+" "+((apellido==null)?"N/D":apellido), 
				null,
				email, 
				null, 
				"N/D", 
				null, 
				null, 
				null, 
				"N/D", 
				"N/D",
				null, 
				new Date(), 
				null, 
				null, 
				null, 
				"N/D", 
				false, 
				null, 
				null,  
				null));
	}
	
	/**
	 * @return indica si se trata de un contacto perteneciente al inatec
	 * @param el login del usuario
	 */

	@Override
	public boolean isNuevoContactoInatec(String usuario) {
		Contacto contacto = contactoDao.findOneByQuery("select c from contactos c where c.inatec=true and c.usuarioInatec="+"'"+usuario+"'");
		if(contacto == null)
			return true;
		else
			return false;
	}
	
	/**
	 * @return la instancia de contacto generada
	 * @param el login del usuario cuyo contacto se quiere registrar
	 */

	@Override
	public Contacto generarNuevoContactoInatec(String usuario) {
		Contacto contacto = inatecDao.generarContacto(usuario);
		Rol rol = rolDao.findOneByQuery("Select r from roles r where r.idRolInatec="+inatecDao.getIdRol(usuario));
		if(rol != null)
			contacto.setRol(rol);
		return contacto;
	}
	
	/**
	 * @return el listado de solicitudes
	 */

	@Override
	public List<USolicitud> getUSolicitudes () {			
			
			List<Solicitud> Sols = solicitudDao.findAll(Solicitud.class);
			List<USolicitud> uSols = new ArrayList<USolicitud>();;
			
			for(int i = 0; i<Sols.size(); i++){				
				USolicitud uSolicitud = new USolicitud (Sols.get(i), false);
				uSols.add(uSolicitud);			
			}
			return uSols;
	}
	
	/**
	 * @return el listado de solicitudes
	 */
	
	@Override
	public List<Solicitud> getSolicitudes() {
		return solicitudDao.findAll(Solicitud.class);		
	}
	
	/**
	 * @return la instancia de solicitud buscada
	 * @param el código de la solicitud a buscar
	 */
	
	@Override
	public Solicitud getSolicitudById(Long idSolicitud) {
		Object [] objs =  new Object [] {idSolicitud};
		return solicitudDao.findOneByNamedQueryParam("Solicitud.findById", objs);			
	}
	
	/**
	 * @return el listado de solicitudes
	 * @param un map con los parámetros de búsqueda
	 */
	
	@Override
	public List<Solicitud> getSolicitudesByParam(HashMap<String, Object> param) {
		
		String sqlSolicitud;		
		
		String sqlWhere = null;		
		String condicion = "";
		
		if (param.size() > 0 ) {
			
			String campo;
			Object valor;
			
			Iterator<String> claveSet = param.keySet().iterator();			
		    
		    while(claveSet.hasNext()){		 
		    	condicion = "";
		    	campo = claveSet.next();		
		    	valor = param.get(campo);
		    	if (param.get(campo) instanceof Integer || param.get(campo) instanceof Long) {
		    		condicion = campo + " = " + valor;		    		
		    	} else {
		    		condicion = " lower(" + campo + ") like '%" + valor.toString().trim().toLowerCase() + "%'";			    			    		    		
		    	}		    	
		    	
		    	sqlWhere = (sqlWhere == null) ? "where " + condicion  :sqlWhere + " and " + condicion;		        		        
		    }
		}
		
		sqlSolicitud = "select s from solicitudes s " + ((sqlWhere == null) ? "" : sqlWhere + " order by s.id desc");
		//sqlSolicitud = "select s from solicitudes s " + ((sqlWhere == null) ? " where s.estatus.id != 40 " : sqlWhere + " and s.estatus.id != 40 ") ;
		
		return solicitudDao.findAllByQuery(sqlSolicitud+" order by 1");
	}
	
	/**
	 * @return el listado de solicitudes
	 * @param el nombre del namedQuery a usar
	 * @param el arreglo con los parámetros de búsqueda
	 */
	
	@Override
	public List<Solicitud> getSolicitudesByNQParam(String nQuery, Object [] parametros){
		return solicitudDao.findAllByNamedQueryParam(nQuery, parametros);
	}
	
	/**
	 * @return la instancia del contacto
	 * @param el número de cédula del contacto a buscar
	 */
	
	@Override
	public Contacto getContactoByCedula(String cedula) {
		Object [] objs =  new Object [] {cedula};
		return contactoDao.findOneByNamedQueryParam("Contacto.findByCedulaId", objs);		
	}	
	
	/**
	 * @return la instancia del rol buscado
	 * @param el id del rol a buscar
	 */

	@Override
	public Rol getRolById(int id) {
		return rolDao.findById(Rol.class, id);
	}
	
	/**
	 * @return el listado de las actividades según los mantenedores
	 * 
	 */

	@Override
	public List<Mantenedor> getMantenedorActividades() {
		return this.getMantenedoresByTipo(new Integer(1));
	}
	
	/**
	 * @return el listado de los estatus de una certificación según los mantenedores
	 */

	@Override
	public List<Mantenedor> getMantenedorEstatusCertificacion() {
		return this.getMantenedoresByTipo(new Integer(3));
	}
	
	/*
	 * @return la instancia de certificación registrada en base de datos
	 * @param la certificación a guardar y su listado de sus requisitos
	 * 
	 */
		
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Certificacion guardarCertificacion(Certificacion certificacion, List<Requisito> requisitos) {
		
		certificacion = certificacionDao.save(certificacion);
		
		for(int i=0; i<requisitos.size(); i++){
			requisitos.get(i).setCertificacion(certificacion);
			requisitoDao.save(requisitos.get(i));
		}

		List<Mantenedor> actividades = getMantenedoresByTipo(1);
		
		for(int i=0; i<actividades.size(); i++){
			actividadDao.save(new Actividad(certificacion,i+1,actividades.get(i),actividades.get(i).getValor(),"A completar",null,null,null,new Date(),null,null,certificacion.getCreador(),null,null,null,getMantenedorById(12)));
		}
		
		return certificacionDao.findById(Certificacion.class, certificacion.getId());
	}
	
	/**
	 * @return la instancia del objeto registrado
	 * @param el objeto a guardar
	 */
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Object guardar (Object objeto) {
		if (objeto instanceof Convocatoria) {
			return convocatoriaDao.save((Convocatoria) objeto);
		}
		if (objeto instanceof Solicitud) {
			return solicitudDao.save((Solicitud) objeto);
		}
		if (objeto instanceof Laboral) {
			return laboralDao.save((Laboral) objeto);
		}
		if (objeto instanceof Archivo) {
			return archivoDao.save((Archivo) objeto);
		}
		if (objeto instanceof Evaluacion) {
			return evaluacionDao.save((Evaluacion) objeto);
		}
		if (objeto instanceof EvaluacionGuia) {
			return evaluacionGuiaDao.save((EvaluacionGuia) objeto);
		}
		if(objeto instanceof Actividad){
			return actividadDao.save((Actividad)objeto);
		}
		if(objeto instanceof Mantenedor)
			return mantenedorDao.save((Mantenedor)objeto);
		if(objeto instanceof Perfil)
			return perfilDao.save((Perfil)objeto);
		if(objeto instanceof Rol)
			return rolDao.save((Rol)objeto);
		if(objeto instanceof Contacto){
			if(((Contacto)objeto).getNombreCompleto() == null){
				String nombreCompleto = ((Contacto) objeto).getPrimerNombre()+" "+((Contacto)objeto).getPrimerApellido();
				((Contacto)objeto).setNombreCompleto(nombreCompleto);
			}
			return contactoDao.save((Contacto)objeto);
		}
		if(objeto instanceof Usuario)
			return usuarioDao.save((Usuario)objeto);
		if(objeto instanceof Certificacion)
			return certificacionDao.save((Certificacion)objeto);
		if(objeto instanceof Guia)
			return guiaDao.save((Guia)objeto);
		if(objeto instanceof Instrumento)
			return instrumentoDao.save((Instrumento)objeto);
		if(objeto instanceof Actividad)
			return actividadDao.save((Actividad)objeto);
		if(objeto instanceof Bitacora)
			return bitacoraDao.save((Bitacora)objeto);
		if(objeto instanceof Requisito)
			return requisitoDao.save((Requisito)objeto);
		if (objeto instanceof Pais)
			return paisDao.save((Pais) objeto);
		return null;
	}
	
	/**
	 * @return el listado de certificaciones
	 * @param el código de la entidad cuyas certificaciones se quieren buscar
	 */
	
	@Override
	public List<Certificacion> getCertificacionesByIdIfp (Integer id) {
		if (id == null){
			return certificacionDao.findAll(Certificacion.class);					
		} else {
			Object [] objs =  new Object [] {id};
			return certificacionDao.findAllByNamedQueryParam("Certificacion.findByIfpId", objs);
		}		
	}
	
	/**
	 * @return la instancia de la certificación buscada
	 * @param el id de la certificación a buscar
	 */
	
	@Override
	public Certificacion getCertificacionById(Long id) {
		Object [] objs =  new Object [] {id};
		return certificacionDao.findOneByNamedQueryParam("Certificacion.findById", objs);						
	}	
	
	/**
	 * @return el listado de las entidades o centros de formación
	 * @param el id de la entidad a buscar
	 */
	
	@Override
	public List<Ifp> getIfpByInatec (Integer entidadId) {
		return inatecDao.getIfpInatec(entidadId);
	}
	
	/**
	 * @return el listado de actividades de una certificación
	 * @param el código de la certificación cuyas actividades se quiere buscar
	 */

	@Override
	public List<Actividad> getActividades(Long certificacionId) {
			return actividadDao.findAllByNamedQueryParam("Actividad.findByCertificacionId", new Object[] {certificacionId});
	}
	
	/**
	 * @return el listado de mantenedores
	 * @param el tipo del mantenedores que se quiere buscar
	 */
	
	@Override
	public List<Mantenedor> getMantenedoresByTipo(Integer tipo) {
		Object [] objs =  new Object [] {tipo.toString()};
		return mantenedorDao.findAllByNamedQueryParam("Mantenedor.findByTipo", objs);		
	}
	
	/**
	 * @return el map de mantenedores
	 * @param el tipo del mantenedores que se quiere buscar
	 */
	
	@Override
	public Map<Integer, Mantenedor> getMapMantenedoresByTipo(String tipo) {
		Object [] objs =  new Object [] {tipo};
		
		List<Mantenedor> l = mantenedorDao.findAllByNamedQueryParam("Mantenedor.findByTipo", objs);				
		Map<Integer, Mantenedor> m = new HashMap<Integer, Mantenedor>();
		if(l != null){
			for(int i=0; i<l.size(); i++){
				m.put(l.get(i).getId(), l.get(i));
			}			
		}
		return m;
	}
	
	/**
	 * @return el historial laboral del tipo y contacto en cuestión
	 * @param el tipo de historial a buscar
	 * @param el contacto cuyo historial interesa
	 */
	
	@Override
	public List<Laboral> getListLaboralByTipo(Integer tipo, Contacto contacto) {
		Object [] objs =  new Object [] {tipo, contacto.getId()};
		return laboralDao.findAllByNamedQueryParam("Laboral.findAllByTipoAndContactoId", objs);					
	}
	
	/**
	 * @return la instancia del dato laboral buscado
	 * @param el id del dato laboral a buscar
	 */

	@Override
	public Laboral getLaboralById(Long idLaboral) {
		Object [] objs =  new Object [] {idLaboral};
		return laboralDao.findOneByNamedQueryParam("Laboral.findById", objs);				
	}
	
	/**
	 * @return los datos laborales del contacto
	 * @param el contacto a buscar
	 */
	@Override
	public Map<Integer, List<Laboral>> getLaboralesMapByContacto(Contacto contacto){
		Object [] objs =  new Object [] {contacto.getId()};
		List<Laboral> laborales = laboralDao.findAllByNamedQueryParam("Laboral.findAllByContactoId", objs);
		
		List<Laboral> experiencias = new ArrayList<Laboral>();
		List<Laboral> estudios = new ArrayList<Laboral>();
		List<Laboral> calificaciones = new ArrayList<Laboral>();
		List<Laboral> certificaciones = new ArrayList<Laboral>();
		
		Map<Integer, List<Laboral>> laboralesMap = new HashMap<Integer, List<Laboral>>();
		
		for(int i=0; i<laborales.size(); i++){
			
			if(laborales.get(i).getTipo()==13)
				experiencias.add(laborales.get(i));

			if(laborales.get(i).getTipo()==14)
				estudios.add(laborales.get(i));

			if(laborales.get(i).getTipo()==15)
				calificaciones.add(laborales.get(i));

			if(laborales.get(i).getTipo()==16)
				certificaciones.add(laborales.get(i));
		}

		laboralesMap.put(13, experiencias);
		laboralesMap.put(14, estudios);
		laboralesMap.put(15, calificaciones);
		laboralesMap.put(16, certificaciones);
		
		return laboralesMap;
	}
	
	/**
	 * @return el listado de evaluaciones
	 * @param la solicitud cuyas evaluaciones se quieren conocer
	 */

	@Override
	public List<Evaluacion> getEvaluaciones(Solicitud solicitud) {
		Object [] objs =  new Object [] {solicitud.getId()};
		return evaluacionDao.findAllByNamedQueryParam("Evaluacion.findAllBySolicitudId", objs);
		/*
		final Solicitud sol = solicitud;
		
		String query =
				"select	a.id, "+
				"a.solicitud_id, "+
				"a.instrumento_id, "+
				"a.fecha, "+
				"a.observaciones, "+
				"a.activo, "+
				"a.estado "+
			"from	(select null id, null solicitud_id, i.instrumento_id instrumento_id, current_date fecha, null observaciones, true activo, 50 estado from sccl.instrumentos i where i.instrumento_estatus='true' and not exists (select 1 from sccl.evaluaciones e where e.evaluacion_solicitud_id="+solicitud.getId()+" and e.evaluacion_instrumento_id=i.instrumento_id and e.evaluacion_estado!=52) "+
					"union "+
					"select e.evaluacion_id id, e.evaluacion_solicitud_id solicitud_id, e.evaluacion_instrumento_id instrumento_id, e.evaluacion_fecha fecha, e.evaluacion_observaciones observaciones, e.evaluacion_activo activo, e.evaluacion_estado estado from sccl.evaluaciones e where e.evaluacion_solicitud_id="+solicitud.getId()+" and e.evaluacion_activo=true) a "+
			"order by a.instrumento_id";

			List<Evaluacion> evaluaciones = new ArrayList<Evaluacion>();
			try
			{
			evaluaciones = jdbcTemplate.query(
					query,
					new RowMapper<Evaluacion>() {
						public Evaluacion mapRow(ResultSet rs, int rowNum) throws SQLException {
							Evaluacion evaluacion = new Evaluacion();
							evaluacion.setId(rs.getLong("id"));
							evaluacion.setInstrumento(getInstrumentoById(rs.getLong("instrumento_id")));
							evaluacion.setSolicitud(sol);
							evaluacion.setFechaEvaluacion(rs.getDate("fecha"));
							evaluacion.setObservaciones(rs.getString("observaciones"));
							evaluacion.setActivo(rs.getBoolean("activo"));
							evaluacion.setEstado(getMantenedorById(rs.getInt("estado")));
							return evaluacion;
						}
					});
			}
			catch(EmptyResultDataAccessException e)
			{
				return null;
			}

			return evaluaciones;
		*/
	}

	/**
	 * @return el listado de evaluaciones no aprobadas
	 * @param la solicitud cuyas evaluaciones se quieren conocer
	 */

	@Override
	public List<Evaluacion> getEvaluacionesPendientes(Solicitud solicitud) {
		Object [] objs =  new Object [] {solicitud.getId()};
		return evaluacionDao.findAllByNamedQueryParam("Evaluacion.findAllPendientesBySolicitudId", objs);		
	}
	
	/**
	 * @return el listado de evaluaciones no aprobadas
	 * @param el contacto cuyas evaluaciones se quieren conocer
	 */

	public List<Evaluacion> getEvaluacionesPendientesByContactoId(Contacto contacto){
		Object [] objs =  new Object [] {contacto.getId()};
		return evaluacionDao.findAllByNamedQueryParam("Evaluacion.findAllPendientesByFirstSolicitudByContactoId", objs);				
	}
	
	/**
	 * @return el instrumento buscado
	 * @param el id del instrumento a buscar
	 */
		
	@Override
	public Instrumento getInstrumentoById(Long idInstrumento){
		Object [] objs =  new Object [] {idInstrumento};
		return instrumentoDao.findOneByNamedQueryParam("Instrumento.findById", objs);		
	}

	/**
	 * @return el listado de instrumentos de la unidad de competencia indicada
	 * @param el id de la unidad de competencia
	 */

	@Override
	public List<Instrumento> getInstrumentoByUnidad (Long idUnidad) {
		Object [] objs =  new Object [] {idUnidad};
		return instrumentoDao.findAllByNamedQueryParam("Instrumento.findAllByUnidadId", objs);				
	}	

	/**
	 * @return el listado de las unidades de competencia de la certificación indicada
	 * @param el id de la certificación
	 */

	@Override
	public List<Long> getUnidadesByCertificacionId(Long certificacionId) {
		List<BigInteger> unidades = new ArrayList<BigInteger>();
		List<Long> unidadesLong = new ArrayList<Long>();
		if(certificacionId!=null)
			unidades = bigIntegerDao.findAllByNativeQuery("select unidad_id from sccl.certificacion_unidades where certificacion_id="+certificacionId+" order by 1");
		else
			unidades = bigIntegerDao.findAllByNativeQuery("select distinct unidad_id from sccl.certificacion_unidades order by 1");
		
		for(int i=0; i<unidades.size(); i++)
			unidadesLong.add(unidades.get(i).longValue());
		
		return unidadesLong;
	}
	
	/**
	 * @return el listado de las unidades de competencia de la certificación indicada
	 * @param el id de la certificación
	 */
	
	public List<Item> getUnidadesItemByCertificacionId(Long certificacionId){
		List<BigInteger> codigos = new ArrayList<BigInteger>();
		List<Item> unidades = new ArrayList<Item>();
		
		if(certificacionId!=null)
			codigos = bigIntegerDao.findAllByNativeQuery("select unidad_id from sccl.certificacion_unidades where certificacion_id="+certificacionId);
		else
			return new ArrayList<Item>(catalogoUnidades.values());
		
		for(int i=0; i<codigos.size(); i++)
			unidades.add(catalogoUnidades.get(codigos.get(i).longValue()));

		return unidades;
	}

	/**
	 * @return el listado de los instrumentos de la certificación indicada
	 * @param el id de la certificación
	 */

	@Override
	public List<Instrumento> getInstrumentosByCertificacionId(Long certificacionId) {
		Object [] objs =  new Object [] {certificacionId};
		return instrumentoDao.findAllByNamedQueryParam("Instrumento.findAllByCertificacionId", objs);
	}
	
	/**
	 * @return el listado de las guías de evaluación de la evaluación indicada
	 * @param el id de la evaluación
	 */

	@Override
	public List<EvaluacionGuia> getEvaluacionGuiaByEvaluacionId(Long evaluacionId) {
		Object [] objs =  new Object [] {evaluacionId};
		return evaluacionGuiaDao.findAllByNamedQueryParam("EvaluacionGuia.findByEvaluacionId", objs);		
	}
	
	/**
	 * @return el listado de instrumentos de la evaluación indicada
	 * @param el id de la evaluación
	 */

	@Override
	public List<Instrumento> getIntrumentoByEvaluacion(Long evaluacionId){
		Object [] objs =  new Object [] {evaluacionId};

		/*
		 * 2014/02/15: dchavez. Obtencion de los instrumentos de una evaluacion via sql directamente.
		 * Ordinariamente es un instrumento por cada tipo de prueba (diagnostica, objetiva, desempeño)
		 */
		
		return instrumentoDao.findAllByNamedQueryParam("Instrumento.findInstrumentosByEvaluacionId", objs);

		/*
		List<Long> instrumentosId = longDao.findAllByNamedQueryParam("EvaluacionGuia.findInstrumentoByEvaluacionId", objs);
		List<Instrumento> listInstrumentos = new ArrayList<Instrumento> ();
		
		for (Long dato : instrumentosId) {
			listInstrumentos.add(this.getInstrumentoById(dato));
		}
		return listInstrumentos;*/
	}

	/**
	 * @return el listado de bitácoras de la actividad indicada
	 * @param el id de la actividad
	 */

	@Override
	public List<Bitacora> getBitacoras(Long actividadId) {
		Object [] objs =  new Object [] {actividadId};
		return bitacoraDao.findAllByNamedQueryParam("Bitacoras.findAllByActividadId", objs);
	}

	/**
	 * @return la instancia inicial o primera del tipo de mantenedor indicado 
	 * @param el tipo del mantenedor
	 */

	@Override
	public Mantenedor getMantenedorMinByTipo(String tipo) {		
		Object [] objs =  new Object [] {tipo};
		return mantenedorDao.findOneByNamedQueryParam("Mantenedor.findMinByTipo", objs);				
	}

	/**
	 * @return la instancia final o última del tipo de mantenedor indicado 
	 * @param el tipo del mantenedor
	 */

	@Override
	public Mantenedor getMantenedorMaxByTipo(String tipo){		
		Object [] objs =  new Object [] {tipo};
		return mantenedorDao.findOneByNamedQueryParam("Mantenedor.findMaxByTipo", objs);				
	}	

	/**
	 * @return la instancia del mantenedor buscado 
	 * @param el id del mantenedor
	 */

	@Override
	public Mantenedor getMantenedorById(Integer idMantenedor) {
		return mantenedorDao.findById(Mantenedor.class, idMantenedor.intValue());	
	}
	
	/**
	 * @return un map conteniendo los departamentos del país 
	 * 
	 */

	@Override
	public Map<Integer, Departamento> getDepartamentosByInatec() {
		List<Departamento> lista = inatecDao.getDepartamentosInatec();
		
		Map<Integer, Departamento> m = new HashMap<Integer, Departamento>();
		
		for (Departamento d : lista){
			m.put(d.getDpto_id(), d);
		}
		
		return m;
	}

	/**
	 * @return un map con el listado de municipios de un departamento 
	 * @param el id del departamento cuyos municipios se quiere buscar
	 */	

	@Override
	public Map<Integer, Municipio> getMunicipioDptoByInatec(Integer idDpto) {
		List<Municipio> lista = inatecDao.getMunicipioByDeptoInatec(idDpto);
		
		Map<Integer, Municipio> m = new HashMap<Integer, Municipio>();
		
		for(Municipio dato : lista) {
			m.put(dato.getMunicipio_id(), dato);
		} 
		return m;
	}	

	/**
	 * @return lista de guias de evaluación 
	 * @param el nombre del namedQuery a usar
	 * @param el arreglo conteniendo los parámetros para la búsqueda
	 */

	@Override
	public List<Guia> getGuiaByParam(String namedString, Object [] parametros){
		return guiaDao.findAllByNamedQueryParam(namedString, parametros);
	}		

	/**
	 * @return lista de archivos de un portafolio 
	 * @param el nombre del namedQuery a usar
	 * @param el arreglo conteniendo los parámetros para la búsqueda
	 */

	@Override
	public List<Archivo> getArchivoByParam (String namedString, Object [] parametros) {
		return archivoDao.findAllByNamedQueryParam(namedString, parametros);
	}

	/**
	 * @return lista de requisitos de un curso en un centro específico 
	 * @param el id de curso
	 * @param el id del centro
	 */

	@Override
	public List<Requisito> getRequisitos(int cursoId, int centroId){
		return inatecDao.getRequisitos(cursoId, centroId);
	}

	/**
	 * @return lista de unidades de competencia 
	 * 
	 */

	@Override
	public List<Long> getUnidades() {
		return null;
	}
	
	/**
	 * @return la instancia del archivo 
	 * @param el nombre del namedQuery a usar
	 * @param el arreglo conteniendo los parámetros para la búsqueda
	 */

	@Override
	public Archivo getArchivoOneByParam (String namedString, Object [] parametros){
		return archivoDao.findOneByNamedQueryParam(namedString, parametros);
	}
	
	/**
	 * @return lista de países
	 *  
	 */

	@Override
	public List<Pais> getPaises (){
		return paisDao.findAll(Pais.class);
	}
	
	/**
	 * @return la instancia del país buscado 
	 * @param el nombre del namedQuery a usar
	 * @param el arreglo conteniendo los parámetros para la búsqueda
	 */

	@Override
	public Pais getPaisByNQParam(String namedString, Object [] param){
		return paisDao.findOneByNamedQueryParam(namedString, param);
	}
	
	/**
	 * @return un map conteniendo las unidades de competencia de una estructura formativa 
	 * @param el id de la estructura formativa
	 * 
	 */

	@Override
	public Map<Long, String> getUnidadesByEstructuraId(Integer estructura) {
		return inatecDao.getUnidadesByEstructuraId(estructura);
	}

	/**
	 * @return el objeto usuario externo a registrar en la base de datos local
	 *  
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void registrarUsuarioExterno(UsuarioExterno usuario) {
		
		Usuario user = new Usuario();
		
		//registrando el usuario
		
		user.setContacto(null);
		user.setUsuarioAlias(usuario.getUsuario());
		user.setUsuarioEstatus(true);
		user.setFechaRegistro(new Date());
		user.setCambiarPwd(true);
		String password = PasswordGenerator.randomPassword(8);
		String encoded = PasswordGenerator.encodedPassword(password);
		user.setUsuarioPwd(encoded);
		user.setRol(rolDao.findById(Rol.class, 6));
		user = usuarioDao.save(user);
		
		//registrando el contacto
		contactoDao.save(new Contacto(
				user, 
				new HashSet<Laboral>(), 
				user.getRol(), 
				null, 
				usuario.getNombre(),
				null,
				usuario.getApellido(),
				null,
				usuario.getNombre()+" "+usuario.getApellido(), 
				null,
				usuario.getEmail1(), 
				null, 
				"N/D", 
				null, 
				null, 
				null, 
				"N/D", 
				"N/D",
				null, 
				new Date(), 
				null,	//nacionalidad 
				null, 
				null, 
				"N/D", 
				false, 
				null, 
				null,  
				null));
		email.createAndSendEmail(usuario.getEmail1(), "Creacion de cuenta...", "Su usuario y contraseña son: "+usuario.getUsuario()+"/"+password);
	}

	/** 
	 * @param el login del usuario local cuya contraseña se va a resetear
	 * 
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void resetPassword(String usuario) {
		Usuario user = usuarioDao.findOneByNamedQueryParam("Usuario.findByLogin", new Object[] {usuario});
		
		if(user != null){
			String password = PasswordGenerator.randomPassword(8);
			String encoded = PasswordGenerator.encodedPassword(password);
			user.setCambiarPwd(true);
			user.setUsuarioPwd(encoded);
			usuarioDao.save(user);
			email.createAndSendEmail(user.getContacto().getCorreo1(), "Nueva contraseña...", "Su usuario y nueva contraseña son: "+usuario+"/"+password);
		}
	}

	/**
	 * @return la instancia del usuario cuyo acceso se registró 
	 * @param el login del usuario cuyo acceso se registrará
	 * 
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Usuario registrarAcceso(Usuario usuario) {
		if(usuario != null){
			usuario.setFechaUltimoAcceso(new Date());
			return usuarioDao.save(usuario);
		}
		return null;
	}

	/**
	 * @return indica si el usuario existe o no 
	 * @param el login del usuario a buscar
	 * 
	 */

	@Override
	public boolean existeUsuario(String usuario) {
		Usuario user = usuarioDao.findOneByNamedQueryParam("Usuario.findByLogin", new Object[] {usuario});
		if(user != null)
			return true;
		else
			return false;
	}
	
	/**
	 * @return lista de instrumentos de evaluación 
	 * @param el id de la entidad o centro de formación
	 * 
	 */

	@Override
	public List<Instrumento> getInstrumentos(Integer entidadId) {
		return instrumentoDao.findAllByNamedQueryParam("Instrumento.findAllByEntidadId", new Object[] {entidadId});
	}
	
	/**
	 * @return lista de instrumentos de evaluación 
	 * @param el id de la certificacion
	 * 
	 */
	@Override
	public List<Item> getInstrumentosItemByCertificacionId(Long id){
		return itemDao.findAllByQuery("select new support.Item(i.id,i.nombre) from instrumentos i where i.estatus='true' and i.certificacionId=" + id + " order by i.id desc");
	}

	/**
	 * @return la instancia del contacto buscado 
	 * @param el login del usuario a buscar
	 * 
	 */

	@Override
	public Contacto getContactoLocalByLogin(String login) {
		return contactoDao.findOneByNamedQueryParam("Contacto.findByLogin", new Object[] {login});
	}

	/**
	 * @return indica si el portafolio fue o no verificado 
	 * @param el contacto
	 * @param el tipo de estado del portafolio
	 */

	/*@Override
	public boolean portafolioVerificado(Contacto contacto, String tipoEstadoPortafolio){
		
		int 	   contador = 0;
		Mantenedor estadoArchivo = null;
		Mantenedor estadoVerificado = null;
		Integer    proxEstado = null;
		
		List<Archivo> portafolio = new ArrayList<Archivo> ();
		
		portafolio = archivoDao.findAllByNamedQueryParam("Archivo.findByContactoId", new Object[] {contacto.getId()});
		
		//estadoVerificado = this.getMantenedorMaxByTipo(tipoEstadoPortafolio);
		estadoVerificado = catalogoPortafolio.get(27);
		
		for (Archivo dato : portafolio) {
			estadoArchivo = dato.getEstado();
			proxEstado = Integer.valueOf(estadoArchivo.getProximo());
			
			if (estadoArchivo.getId() != estadoVerificado.getId()){
				if (dato.getAprobado() != null) {
					if (dato.getAprobado().toUpperCase().trim().equals("APROBADO")){
						if (proxEstado.intValue() == estadoVerificado.getId()){						
							dato.setEstado(estadoVerificado);
							dato = (Archivo) this.guardar(dato);
							
							if (dato == null){
								contador += 1;
							}
						} else
							contador += 1;
					} else
						contador += 1;
				} else 
					contador += 1;
			}		
			
		}
		
		
		if (contador == 0)
			return true;
		else
			return false;			
	}*/
	
	public boolean portafolioVerificado(Contacto contacto, String tipoEstadoPortafolio){
		
		//si no hay reprobados -> portafolio verificado
		//si hay reprobados -> portafolio no verificado
		if(archivoDao.findAllByNamedQueryParam("Archivo.findAllReprobadosByContactoId", new Object[] {contacto.getId()}).isEmpty())
			return true;
		else
			return false;
	}
	
	/**
	 * @return lista de permisos del usuario 
	 * @param el login del usuario
	 * 
	 */

	@Override
	public Collection<Authority> getAuthoritiesInatecByLogin(String usuario) {
		return inatecDao.getAuthorities(inatecDao.getIdRol(usuario));
	}
	
	/**
	 * @return lista de permisos del usuario 
	 * @param el id del rol
	 * 
	 */

	@Override
	public Collection<Authority> getAuthoritiesInatecByRolId(Integer rolId) {
		return inatecDao.getAuthorities(rolId);
	}

	/**
	 * @return la instancia del contacto buscado 
	 * @param el login del usuario inatec
	 * 
	 */

	@Override
	public Contacto getContactoInatecByLogin(String login) {
		return contactoDao.findOneByNamedQueryParam("Contacto.findByLoginInatec", new Object[] {login});
	}

	/**
	 * @return la instancia del contacto buscado 
	 * @param el login del usuario a buscar, puede ser inatec o local
	 * 
	 */
	
	@Override
	public Contacto getContactoByLogin(String login) {
		Contacto contacto = contactoDao.findOneByNamedQueryParam("Contacto.findByLoginInatec", new Object[] {login});
		if(contacto != null)
			return contacto;
		else
			return 	contactoDao.findOneByNamedQueryParam("Contacto.findByLogin", new Object[] {login});
	}

	/**
	 * @return la instancia de conexión a la base de datos 
	 * 
	 */

	@Override
	public Connection getSqlConnection() throws SQLException {	
		Connection con = objectDao.getSqlConexion();
		return con;
	}
	
	/** 
	 * @param el nombre del reporte a imprimir
	 * @param el map con los parámetros
	 * @param el formato del reporte
	 * @param indicador si visualiza o no el reporte
	 * 
	 */

	@Override
	public void imprimirReporte(String nombreReporte, Map<String,Object> parametros, String formato, boolean visualiza) throws SQLException{
						
		try {			
			Connection conn = getSqlConnection();
			logger.info("Conexion " + conn);											
			ControlGenericoReporte.getInstancia().runReporteFisico(nombreReporte, parametros,formato, conn, visualiza);		
			//String reporte = nombreReporte.toLowerCase() + "." + Global.EXPORT_PDF.toLowerCase();
			//String reporte = nombreReporte + "." + formato.toLowerCase();
			//context.execute("window.open('" +  FacesUtil.getContentPath() + "/reporte?file="+ reporte + "&formato=" + Global.EXPORT_PDF + "','myWindow');");		
			//context.execute("window.open('" +  FacesUtil.getContentPath() + "/reporte/"+ reporte + "','myWindow');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return indica si el proceso de evaluación está concluido o no 
	 * @param la solicitud a validar
	 * @param el indicador de concluido
	 * 
	 */

	@Override
	public boolean validaProcesoConcluido(Solicitud solicitud, boolean validaEvaluacion){
		
		if(solicitud.getEstatus().getId()==42 || solicitud.getEstatus().getId()==43 || solicitud.getEstatus().getId()==44)
			return true;
		else
			return false;
		
		/*
		boolean pasaConcluido = false;
		Mantenedor ultimoEstado = null;
		Mantenedor estadoActual = solicitud.getEstatus();
		Integer    idMatricula = null;
		
		//ultimoEstado = getMantenedorMaxByTipo(solicitud.getTipomantenedorestado());
		ultimoEstado = catalogoEstadoSolicitud.get(42);
		
		if (ultimoEstado != null){
			
			if(estadoActual.getId()==ultimoEstado.getId())
				return true;
			
			//if(estadoActual.getId()==catalogoEstadoSolicitud.get(40).getId())
				//return true;
						
			if (Integer.valueOf(estadoActual.getProximo()) == ultimoEstado.getId()){
				idMatricula = solicitud.getIdMatricula();	
				if (idMatricula == null)
					pasaConcluido =  false;
				else {
					pasaConcluido = (validaEvaluacion) ? validaEvaluacionByUnidad(solicitud, null) : true;
				}
			}
		}
		return pasaConcluido;
		*/
	}
	
	/**
	 * @return indica si el proceso de evaluación está aprobado o no 
	 * @param la solicitud a validar
	 * @param el indicador de prueba diagnóstica
	 * @param el id de la unidad de competencia
	 * 
	 */

	@Override	
	public boolean validaEvaluacionAprobada(Solicitud solicitud, boolean diagnostica, Long ucl){
		Object [] objs = null;	
		boolean pasa = true;
		//boolean aprobado = true;
		List<Instrumento> listaInstrumento = null;
		Certificacion c = solicitud.getCertificacion();
		Integer idTipoInstrumento = diagnostica ? 17 : null;
		
		if (ucl == null){ // Evalua todas las unidades de compentencia
			List<Long> setUnidades =  getUnidadesByCertificacionId(c.getId());
			
			for(Long unidad : setUnidades){
				
				objs =  new Object [] {new String("6"), idTipoInstrumento, unidad, solicitud.getId()};	
				listaInstrumento = instrumentoDao.findAllByNamedQueryParam("Instrumento.findPendientesEvaluar", objs);
				
				//Existen evaluaciones pendientes por unidad de compentencia
				if (listaInstrumento.size() > 0) {
					pasa = false;
					break;
				} else { // La unidad de competencia ha sido evaluada
					if (diagnostica == false){
						pasa = validaEvalUnidad(solicitud, ucl);
					}
				}
				
			}
		}else { // Evalua por Unidad de compentencia
			objs =  new Object [] {new String("6"), idTipoInstrumento, ucl, solicitud.getId()};
			logger.info("PARAMETROS " + idTipoInstrumento + " ucl " + ucl + " solicitud " + solicitud.getId());
			listaInstrumento = instrumentoDao.findAllByNamedQueryParam("Instrumento.findPendientesEvaluar", objs);
			logger.info("Indica si falta o no instrumentos " + listaInstrumento.size());
			//Existen evaluaciones pendientes por unidad de compentencia
			if (listaInstrumento.size() > 0) {
				pasa = false;				
			} else { // La unidad de competencia ha sido evaluada		
				if (diagnostica == false){
					logger.info("Debe agregar en la tabla ");
					pasa = validaEvalUnidad(solicitud, ucl);
				}
			}
		}		
		
		return pasa;
	}

	/**
	 * @return la lista de actividades de una entidad o centro de formación 
	 * @param el id de la entidad o centro
	 * 
	 */

	@Override
	public List<Actividad> getActividadesByEntidadId(Integer entidadId) {
		return actividadDao.findAllByNamedQueryParam("Actividad.findByEntidadId", new Object[] {entidadId});
	}
	
	/** 
	 * @param la pista de auditoría a registrar
	 * 
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void auditar(Auditoria auditoria) {
		auditoriaDao.save(auditoria);
	}
	
	/**
	 * @return la lista de contactos según los parámetros de búsqueda 
	 * @param el nombre del namedQuery a usar
	 * @param el arrego de parámetros para la búsqueda
	 * 
	 */

	@Override
	public List<Contacto> getContactosByParam(String namedString, Object [] parametros){
		return contactoDao.findAllByNamedQueryParam(namedString, parametros);				
	}

	/**
	 * @return la instancia de evaluación buscada 
	 * @param el id de la evaluación a buscar
	 * 
	 */

	@Override
	public Evaluacion getEvaluacionById(Long evaluacionId){
		Object [] objs =  new Object [] {evaluacionId};		
		return evaluacionDao.findOneByNamedQueryParam("Evaluacion.findById", objs);	
	}

	/**
	 * @return la lista de evaluaciones de una solicitud e unidad de competencia 
	 * @param la solicitud
	 * @param la unidad de competencia
	 * 
	 */

	@Override
	public List<Evaluacion> getEvaluacionesBySolicitudUnidad(Solicitud solicitud, Long unidad){
		Object [] objs =  new Object [] {solicitud.getId(), unidad};	
		return evaluacionDao.findAllByNamedQueryParam("Evaluacion.findAllBySolicitudUCL", objs);
		
	}
	
	/**
	 * @return indicador de validado para la unidad de competencia de una solicitud 
	 * @param la solicitud
	 * @param la unidad de competencia
	 * 
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean validaEvalUnidad(Solicitud solicitud, Long ucl){
		boolean aprobado = true;
		
		List<Evaluacion> listaEval = this.getEvaluacionesBySolicitudUnidad(solicitud, ucl);
		if (listaEval.size() == 0)// No existen evaluaciones para la unidad de compentencia
			aprobado = false;
		else {
			for (Evaluacion eval : listaEval){
				if (eval.getAprobado() != true){
					aprobado = false;
					break;
				}
			}
			
			boolean existeEUcl = validaEvaluacionByUnidad(solicitud, ucl);
			//Julio/2014
			//EvaluacionUnidad eUcl = null;
			
			if (existeEUcl == false){
				Mantenedor estatusEval = this.getMantenedorById(29);
				String     nombreUCL = getCompetenciaDescripcion(ucl);
				//Julio/2014
				/*
				eUcl = new EvaluacionUnidad(solicitud, ucl, nombreUCL, aprobado, estatusEval);
				eUcl = evaluacionUnidadDao.save(eUcl);
				*/				
			}
			//Julio/2014
			//aprobado = (eUcl == null) ? false : true;
		}
		return aprobado;
	}
		
	/**
	 * @return la instancia de evaluación actualizada 
	 * @param la evaluación a actualizar
	 * @param el indicador de validado
	 * 
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Evaluacion actualizaEvaluacion(Evaluacion evaluacion, boolean valida){
		//boolean val = false;
		Evaluacion eval = null;
		
		eval = evaluacionDao.save(evaluacion);
		
		if (eval != null){
			if (valida) //Evaluacion Completada 
				//val = validaEvaluacionAprobada(eval.getSolicitud(), false, eval.getUnidad());
				validaEvaluacionAprobada(eval.getSolicitud(), false, eval.getUnidad());
		}
		return eval;
		
	}
	
	/**
	 * @return la instancia de la unidad validada 
	 * @param la solicitud validada
	 * @param la unidad de competencia validada
	 * 
	 */
	/*
	@Override
	public EvaluacionUnidad getEvaluacionUnidadBySolicitudUCL(Solicitud solicitud, Long unidad){
		Object [] objs =  new Object [] {solicitud.getId(), unidad};
		return evaluacionUnidadDao.findOneByNamedQueryParam("EvaluacionUnidad.findAllBySolicitudUCL", objs);		
	}
	*/
	
	/**
	 * @return el indicador de validado de la unidad de competencia 
	 * @param la solicitud a validar
	 * @param la unidad de competencia a validar
	 * 
	 */

	@Override
	public boolean validaEvaluacionByUnidad(Solicitud solicitud, Long ucl){
		boolean existe = true;
		Certificacion c = null;
		//Julio/2014
		//EvaluacionUnidad eUcl = null;
		c = solicitud.getCertificacion();
				
		if (ucl == null){ // Evalua por todas las unidades de competencia
			List<Long> setUnidades =  getUnidadesByCertificacionId(c.getId());
			
			//Julio/2014
			/*
			for(Long unidad : setUnidades){
				eUcl = null;
				
				eUcl = getEvaluacionUnidadBySolicitudUCL(solicitud, unidad);
				if (eUcl == null){
					existe = false;
					break;
				}					
			}*/
		} 
		//Julio/2014
		/*
		else {//Evalua por unidad de compentencia
			eUcl = null;
			//eUcl = getEvaluacionUnidadBySolicitudUCL(solicitud, ucl);
			existe = (eUcl == null) ? false : true;
		}*/
		
		return existe;
	}
	
	/**
	 * @return la lista de unidades de competencia evaluadas 
	 * @param el id de la solicitud cuyas unidades evaluadas se quiere conocer
	 * 
	 */
	/*
	@Override
	public List<EvaluacionUnidad> getListEvalUnidad(Long idSolicitud){
		Object [] objs =  new Object [] {idSolicitud};
		return evaluacionUnidadDao.findAllByNamedQueryParam("EvaluacionUnidad.findAllBySolicitud", objs);		
	}
	*/
	@Override
	public List<Item> getListEvaluacionesUnidad(Long idSolicitud){
		Object [] objs =  new Object [] {idSolicitud};
		return itemDao.findAllByNamedQueryParam("Evaluacion.findAllUnidadesBySolicitudId", objs);		
	}
	
	/**
	 * @return booleando que indica si la solicitud fue anulada o no 
	 * @param el id de la solicitud que se desea anular
	 * 
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Solicitud anularSolicitud(Solicitud solicitud){
		solicitud.setEstatus(catalogoEstadoSolicitud.get(40));
		return solicitudDao.save(solicitud);
	}

	/**
	 * @return booleando que indica si el solicitante tiene solicitudes pendientes 
	 * @param el numero de cedula y opcionalmente el id de la certificacion
	 * 
	 */

	@Override
	public boolean tieneSolicitudesPendientes(String cedula, Long certificacionId){
		
		//indica si el solicitante con cedula indicada tiene solicitudes pendientes o si la certificacion que solicita ya la tiene aprobada
		
		String query = 
					" select solicitud_id from sccl.solicitudes where solicitud_estatus not in (37, 40) and contacto_id in (select contacto_id from sccl.contactos where numero_identificacion='"+cedula+"') "
				+ 	" union"
				+ 	" select solicitud_id from sccl.solicitudes where solicitud_estatus in (37) and contacto_id in (select contacto_id from sccl.contactos where numero_identificacion='"+cedula+"') and certificacion_id="+certificacionId;
		
		List<Long> pendientes = longDao.findAllByNativeQuery(query);
		if(pendientes.isEmpty())
			return false;
		else
			return true;
	}
	
	/** 
	 * @return booleando indica si la anulacion fue exitosa
	 * @param la evaluacion a anular
	 * 
	 */
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean anularEvaluacion(Evaluacion evaluacion){
		evaluacionDao.remove(Evaluacion.class, evaluacion.getId());
		return true;
	}

	/** 
	 * @param la solicitud cuya matricula se autoriza. La autorizacion valida que las pruebas de lectura-escritura y diagnostica estén aprobadas
	 * 
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void autorizarMatricula(Solicitud solicitud){
		
		//si el estado de la solicitud es enviado
		if(solicitud.getEstatus().getId()==36){
		
			// buscando instrumentos pendientes de evaluar y que no estén aprobados
			List<Instrumento> instrumentosPendientes = instrumentoDao.findAllByQuery("select i from instrumentos i where i.estatus='true' and i.certificacionId="+solicitud.getCertificacion().getId()+" and i.tipo.id in (27,28) and not exists (select 1 from evaluaciones e where e.activo='true' and e.aprobado='true' and e.instrumento.id=i.id and e.solicitud.id="+solicitud.getId()+")");
		
			// si no hay instrumentos pendientes de evaluar (prueba de lectura-escritura o diagnostica), autorizar matricula		
			if(instrumentosPendientes.isEmpty()){
			
				//actualiza estado de solicitud a autorizado para matricula			
				actualizarEstadoSolicitud(solicitud, 2);
			}
		}
	}
	
	/** 
	 * @param la solicitud cuya matricula se resuelve y la el valor de autorizacion
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void actualizarEstadoSolicitud(Solicitud solicitud, int indicador){

		Mantenedor estado = null;
		
		switch(indicador){
			case 1: estado = getMantenedorById(36); break; //enviar
			case 2: estado = getMantenedorById(37); break; //autorizar matricula
			case 3: estado = getMantenedorById(44); break; //rechazar matricula
			case 4: estado = getMantenedorById(38); break; //matricular
			case 5: estado = getMantenedorById(39); break; //asesoria grupal
			case 6: estado = getMantenedorById(40); break; //asesoria individual
			case 7: estado = getMantenedorById(41); break; //programar
			case 8: estado = getMantenedorById(42); break; //evaluar
			case 9: estado = getMantenedorById(43); break; //completar
			case 10: estado = getMantenedorById(45); break; //anular
			default: break;
		}
			
		if(estado!=null){
			solicitud.setEstatus(estado);
			solicitud.setFechaActualiza(new Date());
			solicitudDao.save(solicitud);
		}
	}

	/** 
	 * @param la entidad cuyo listado de solicitudes se desea
	 * 
	 */
	@Override
	public List<Solicitud> getSolicitudesByEntidadId(int entidadId){
		Object [] objs =  new Object [] {entidadId};
		return solicitudDao.findAllByNamedQueryParam("Solicitud.findAll", objs);
	}
	
	/** 
	 * @param el id de la solicitud cuyas convocatorias se desean obtener
	 * 
	 */
	@Override
	public List<Convocatoria> getConvocatoriasBySolicitudId(Long solicitudId){
		Object [] objs =  new Object [] {solicitudId};
		return convocatoriaDao.findAllByNamedQueryParam("Convocatoria.findAllBySolicitudId", objs);
	}
	
	/** 
	 * @param el id de la solicitud cuyas actividades se desean obtener
	 * 
	 */
	@Override
	public List<Item> getActividadesItemBySolicitudId(Long solicitudId){
		Object [] objs =  new Object [] {solicitudId};
		return itemDao.findAllByNamedQueryParam("Actividad.findItemsBySolicitudId", objs);
	}
	
	/** 
	 * @param el id de la actividad cuyos involucrados se desean obtener
	 * 
	 */
	@Override
	public List<Item> getInvolucradosItemByActividadId(Long actividadId){
		Object [] objs =  new Object [] {actividadId};
		return itemDao.findAllByNamedQueryParam("Involucrado.findItemsByActividadId", objs);
	}
	
	/** 
	 * @param el id de la certificacion cuyos involucrados se desean obtener agrupados por actividad id
	 * 
	 */
	@Override
	public Map<Long, List<Item>> getInvolucradosItemByCertificacionId(Long certificacionId){
		return null;
	}
	
	/** 
	 * @param el id del contacto a recuperar
	 * 
	 */	
	@Override
	public Contacto getContactoById(Long id){
		return contactoDao.findById(Contacto.class, id);
	}
	
	/** 
	 * @param el id de la actividad a recuperar
	 * 
	 */	
	@Override
	public Actividad getActividadById(Long id){
		return actividadDao.findById(Actividad.class, id);
	}
	
	/** 
	 * @param el id de la solicitud cuyas evaluaciones se quiere obtener
	 * 
	 */
	@Override
	public List<Evaluacion> getEvaluacionesBySolicitudId(Long solicitudId){
		return evaluacionDao.findAllByNamedQueryParam("Evaluacion.findAllBySolicitudId", new Object [] {solicitudId});		
	}
	
	/**
	 * @return lista de guias de evaluación 
	 * @param el id del instrumento
	 */
	@Override
	public List<Guia> getGuiasByInstrumentoId(Long instrumento){
		return guiaDao.findAllByQuery("select g from guias g where g.instrumento.id="+instrumento+" order by g.id desc");
	}
	
	/** 
	 * @param la solicitud a evaluar
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Evaluacion guardarEvaluacion(Evaluacion evaluacion){

		evaluacion = evaluacionDao.save(evaluacion);
		
		for(Guia guia : getGuiasByInstrumentoId(evaluacion.getInstrumento().getId())){
			evaluacionGuiaDao.save(new EvaluacionGuia(evaluacion, guia, new Float(0)));
		}
		
		return evaluacion;
	}
	
	/** 
	 * @param la solicitud cuyos instrumentos pendientes se quiere obtener
	 * @return la lista de instrumentos
	 */	
	@Override
	public List<Item> getInstrumentosPendientesBySolicitud(Solicitud solicitud, Long unidad){
		return itemDao.findAllByQuery("select new support.Item(i.id,i.nombre) from instrumentos i where i.unidad=case when "+unidad+" is null then i.unidad else "+unidad+" end and i.estatus='true' and i.certificacionId="+solicitud.getCertificacion().getId()+" and not exists (select 1 from evaluaciones e where e.instrumento.id=i.id and e.activo='true' and e.solicitud.id="+solicitud.getId()+")");
	}
	
	/** 
	 * @param la solicitud que se desea enviar
	 */	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void enviarSolicitud(Solicitud solicitud){
		
		if(solicitud.getEstatus().getId()==35){		// si el estatus de la solicitud es registrada
		
			// obtiene los instrumentos de lectura-escritura y diagnostico
		
			List<Instrumento> instrumentos = instrumentoDao.findAllByQuery("select i from instrumentos i where i.tipo.id in (27,28) and i.estatus='true' and i.certificacionId="+solicitud.getCertificacion().getId()+" and not exists (select 1 from evaluaciones e where e.instrumento.id=i.id and e.activo='true' and e.solicitud.id="+solicitud.getId()+")");
		
			// registra automaticamente las evaluaciones para las pruebas de lectura-escritura y diagnostica
		
			for(Instrumento instrumento : instrumentos){
				Evaluacion evaluacion = new Evaluacion(solicitud,instrumento,new Date(),instrumento.getPuntajeMinimo(),instrumento.getPuntajeMaximo(),null,true);
			
				guardarEvaluacion(evaluacion);	
			}
		
			//actualiza estado de solicitud a enviado
		
			actualizarEstadoSolicitud(solicitud, 1);
		}
	}
}