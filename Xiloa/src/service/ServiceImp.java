package service;

import java.sql.Connection;
import java.sql.SQLException;
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

import org.springframework.beans.factory.annotation.Autowired;
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
import model.Evaluacion;
import model.EvaluacionGuia;
import model.EvaluacionGuiaId;
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
	
	@PostConstruct
	public void init(){
		mantenedores = mantenedorDao.findAll(Mantenedor.class);
		usuarios = usuarioDao.findAll(Usuario.class);
		roles = rolDao.findAll(Rol.class);
		int tipoMantenedor = 0;
		
		for(int i=0; i<mantenedores.size(); i++){
			Mantenedor mantenedor = mantenedores.get(i);
			switch(mantenedor.getId()){
				case 1:
				case 2:
				case 3:
				case 4: catalogoTiposActividad.put(mantenedor.getId(), mantenedor); break;
				case 7:
				case 8:
				case 9: catalogoEstatusCertificacion.put(mantenedor.getId(), mantenedor); break;
				case 10:
				case 11:
				case 12: catalogoEstatusActividad.put(mantenedor.getId(), mantenedor); break;
				case 17:
				case 18:
				case 19: catalogoTiposInstrumento.put(mantenedor.getId(), mantenedor); break;
			}
			
			tipoMantenedor = Integer.valueOf(mantenedor.getTipo()).intValue();
			switch (tipoMantenedor){
				case 1:		
				case 2:
				case 3:
				case 4:
				case 5: catalogoTiposDatosLaborales.put(mantenedor.getId(), mantenedor); break;
				case 7: catalogoEstadoSolicitud.put(mantenedor.getId(), mantenedor); break;
				case 8: catalogoPortafolio.put(mantenedor.getId(), mantenedor); break;
				case 9: catalogoEstadosEvaluacion.put(mantenedor.getId(), mantenedor); break;
				case 10: catalogoGenero.put(mantenedor.getId(), mantenedor); break;
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
	
	@Override
	public Map<Long, Pais> getCatalogoPaises() {
		return catalogoPaises;
	}

	@Override
	public Map<Integer, Departamento> getCatalogoDepartamentos() {
		return catalogoDepartamentos;
	}

	@Override
	public Map<Integer, Mantenedor> getCatalogoEstadosEvaluacion() {
		return catalogoEstadosEvaluacion;
	}

	@Override
	public Map<Integer, Mantenedor> getCatalogoPortafolio() {
		return catalogoPortafolio;
	}
	
	@Override
	public Map<Integer, Mantenedor> getCatalogoEstadoSolicitud() {
		return catalogoEstadoSolicitud;
	}

	@Override
	public Map<Integer, Mantenedor> getCatalogoGenero() {
		return catalogoGenero;
	}

	@Override
	public List<Mantenedor> getMantenedores(){
		return this.mantenedores;
	}

	@Override
	public Map<Integer, Mantenedor> getCatalogoEstatusCertificacion(){
		return catalogoEstatusCertificacion;
	}
	
	@Override
	public Map<Integer, Mantenedor> getCatalogoTiposActividad(){
		return catalogoTiposActividad;
	}

	@Override
	public Map<Integer, Mantenedor> getCatalogoEstatusActividad(){
		return catalogoEstatusActividad;
	}

	@Override
	public Map<Integer, Mantenedor> getCatalogoTiposInstrumento(){
		return catalogoTiposInstrumento;
	}

	@Override
	public Map<Integer, Mantenedor> getCatalogoTiposDatosLaborales(){
		return catalogoTiposDatosLaborales;
	}
	
	@Override
	public Map<Long, Item> getCatalogoUnidades(){
		return catalogoUnidades;
	}

	@Override
	public String getCompetenciaDescripcion(Long codigo){
		return catalogoUnidades.get(codigo).getDescripcion();
	}

	@Override
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	@Override
	public List<Rol> getRoles() {
		return roles;
	}
	
	@Override
	public List<Certificacion> getCertificaciones(Integer entidadId){
		return certificacionDao.findAllByNamedQueryParam("Certificacion.findByIfpId", new Object[] {entidadId});
	}
	
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
		
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Certificacion guardarCertificacion(Certificacion certificacion, List<Requisito> requisitos) {
		
		certificacion = certificacionDao.save(certificacion);
		
		for(int i=0; i<requisitos.size(); i++){
			requisitos.get(i).setCertificacion(certificacion);
			requisitoDao.save(requisitos.get(i));
		}
				
		Mantenedor estado = getMapMantenedoresByTipo("4").get(10);				//estatus pendiente
		Map<Integer, Mantenedor> actividades = getMapMantenedoresByTipo("1");	//actualizar
		Contacto creador = certificacion.getCreador();
		
		actividadDao.save(new Actividad(certificacion,0,actividades.get(1),"Divulgacion","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado));
		actividadDao.save(new Actividad(certificacion,1,actividades.get(4),"Convocatoria","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado));
		actividadDao.save(new Actividad(certificacion,2,actividades.get(3),"Evaluacion","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado));
		actividadDao.save(new Actividad(certificacion,3,actividades.get(2),"Verificacion","A completar",null,null,null,new Date(),null,null,creador,null,null,null,estado));
				
		return certificacionDao.save(certificacion);
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
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)	
	public void updateUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	@Override
	public List<UCompetencia> getUcompetenciaSinPlanificar(Integer entidadId) {
		return inatecDao.getCertificacionesSinPlanificar(entidadId);
	}
	
	@Override
	public List<Contacto> getContactosInatec(Integer entidadId) {
		return contactoDao.findAllByNamedQueryParam("Contacto.findInvolucradosInatec", new Object [] {entidadId});
	}

	@Override
	public Usuario getUsuarioLocal(String usuario) {
		return usuarioDao.findOneByNamedQueryParam("Usuario.findByLogin", new Object[] {usuario});
	}
	
	@Override
	public Usuario getUsuarioInatec(String usuario) {
		return inatecDao.getUsuario(usuario);
	}
	
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
				nombre,
				null,
				apellido,
				null,
				nombre+" "+apellido, 
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
	
	@Override
	public List<Solicitud> getSolicitudes() {
		return solicitudDao.findAll(Solicitud.class);		
	}	
	
	@Override
	public Solicitud getSolicitudById(Long idSolicitud) {
		Object [] objs =  new Object [] {idSolicitud};
		return solicitudDao.findOneByNamedQueryParam("Solicitud.findById", objs);			
	}
	
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
		
		sqlSolicitud = "select s from solicitudes s " + ((sqlWhere == null) ? "" : sqlWhere) ;		
		
		return solicitudDao.findAllByQuery(sqlSolicitud);
	}
	
	@Override
	public List<Solicitud> getSolicitudesByNQParam(String nQuery, Object [] parametros){
		return solicitudDao.findAllByNamedQueryParam(nQuery, parametros);
	}
	
	@Override
	public Contacto getContactoByCedula(String cedula) {
		Object [] objs =  new Object [] {cedula};
		return contactoDao.findOneByNamedQueryParam("Contacto.findByCedulaId", objs);		
	}	

	@Override
	public Rol getRolById(int id) {
		return rolDao.findById(Rol.class, id);
	}

	@Override
	public List<Mantenedor> getMantenedorActividades() {
		return this.getMantenedoresByTipo(new Integer(1));
	}

	@Override
	public List<Mantenedor> getMantenedorEstatusCertificacion() {
		return this.getMantenedoresByTipo(new Integer(3));
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Object guardar (Object objeto) {
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
	
	@Override
	public List<Certificacion> getCertificacionesByIdIfp (Integer id) {
		if (id == null){
			return certificacionDao.findAll(Certificacion.class);					
		} else {
			Object [] objs =  new Object [] {id};
			return certificacionDao.findAllByNamedQueryParam("Certificacion.findByIfpId", objs);
		}		
	}
	
	@Override
	public Certificacion getCertificacionById(Long id) {
		Object [] objs =  new Object [] {id};
		return certificacionDao.findOneByNamedQueryParam("Certificacion.findById", objs);						
	}	
	
	@Override
	public List<Ifp> getIfpByInatec (Integer entidadId) {
		return inatecDao.getIfpInatec(entidadId);
	}

	@Override
	public List<Actividad> getActividades(Long certificacionId) {
			return actividadDao.findAllByNamedQueryParam("Actividad.findByCertificacionId", new Object[] {certificacionId});
	}
	
	@Override
	public List<Mantenedor> getMantenedoresByTipo(Integer tipo) {
		Object [] objs =  new Object [] {tipo.toString()};
		return mantenedorDao.findAllByNamedQueryParam("Mantenedor.findByTipo", objs);		
	}
	
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
	
	@Override
	public List<Laboral> getListLaboralByTipo(Integer tipo, Contacto contacto) {
		Object [] objs =  new Object [] {tipo, contacto.getId()};
		return laboralDao.findAllByNamedQueryParam("Laboral.findAllByTipoAndContactoId", objs);					
	}
	
	@Override
	public Laboral getLaboralById(Long idLaboral) {
		Object [] objs =  new Object [] {idLaboral};
		return laboralDao.findOneByNamedQueryParam("Laboral.findById", objs);				
	}
	
	@Override
	public List<Evaluacion> getEvaluaciones(Solicitud solicitud) {
		Object [] objs =  new Object [] {solicitud.getId()};
		return evaluacionDao.findAllByNamedQueryParam("Evaluacion.findAllBySolicitudId", objs);		
	}
		
	@Override
	public Instrumento getInstrumentoById(Long idInstrumento){
		Object [] objs =  new Object [] {idInstrumento};
		return instrumentoDao.findOneByNamedQueryParam("Instrumento.findById", objs);		
	}
	
	@Override
	public List<Instrumento> getInstrumentoByUnidad (Long idUnidad) {
		Object [] objs =  new Object [] {idUnidad};
		return instrumentoDao.findAllByNamedQueryParam("Instrumento.findAllByUnidadId", objs);				
	}	

	@Override
	public List<Long> getUnidadesByCertificacionId(Long certificacionId) {
		Certificacion certificacion = (Certificacion) certificacionDao.findOneByQuery("select c from certificaciones c where c.id="+certificacionId);
		return new ArrayList<Long>(certificacion.getUnidades());		
	}

	@Override
	public List<Instrumento> getInstrumentosByCertificacionId(Long certificacionId) {
		Object [] objs =  new Object [] {certificacionId};
		return instrumentoDao.findAllByNamedQueryParam("Instrumento.findAllByCertificacionId", objs);
	}
	
	@Override
	public List<EvaluacionGuia> getEvaluacionGuiaByEvaluacionId(Long evaluacionId) {
		Object [] objs =  new Object [] {evaluacionId};
		return evaluacionGuiaDao.findAllByNamedQueryParam("EvaluacionGuia.findByEvaluacionId", objs);		
	}
	
	@Override
	public List<Instrumento> getIntrumentoByEvaluacion(Long evaluacionId){
		Object [] objs =  new Object [] {evaluacionId};
		List<Long> instrumentosId = longDao.findAllByNamedQueryParam("EvaluacionGuia.findInstrumentoByEvaluacionId", objs);
		List<Instrumento> listInstrumentos = new ArrayList<Instrumento> ();
		
		for (Long dato : instrumentosId) {
			listInstrumentos.add(this.getInstrumentoById(dato));
		}
		return listInstrumentos;
	}

	@Override
	public List<Bitacora> getBitacoras(Long actividadId) {
		Object [] objs =  new Object [] {actividadId};
		return bitacoraDao.findAllByNamedQueryParam("Bitacoras.findAllByActividadId", objs);
	}
	
	@Override
	public Mantenedor getMantenedorMinByTipo(String tipo) {		
		Object [] objs =  new Object [] {tipo};
		return mantenedorDao.findOneByNamedQueryParam("Mantenedor.findMinByTipo", objs);				
	}
	
	@Override
	public Mantenedor getMantenedorMaxByTipo(String tipo){		
		Object [] objs =  new Object [] {tipo};
		return mantenedorDao.findOneByNamedQueryParam("Mantenedor.findMaxByTipo", objs);				
	}	

	@Override
	public Mantenedor getMantenedorById(Integer idMantenedor) {
		return mantenedorDao.findById(Mantenedor.class, idMantenedor.intValue());	
	}
	
	@Override
	public Map<Integer, Departamento> getDepartamentosByInatec() {
		List<Departamento> lista = inatecDao.getDepartamentosInatec();
		
		Map<Integer, Departamento> m = new HashMap<Integer, Departamento>();
		
		for (Departamento d : lista){
			m.put(d.getDpto_id(), d);
		}
		
		return m;
	}

	@Override
	public Map<Integer, Municipio> getMunicipioDptoByInatec(Integer idDpto) {
		List<Municipio> lista = inatecDao.getMunicipioByDeptoInatec(idDpto);
		
		Map<Integer, Municipio> m = new HashMap<Integer, Municipio>();
		
		for(Municipio dato : lista) {
			m.put(dato.getMunicipio_id(), dato);
		} 
		return m;
	}	

	@Override
	public List<Guia> getGuiaByParam(String namedString, Object [] parametros){
		return guiaDao.findAllByNamedQueryParam(namedString, parametros);
	}		
	
	@Override
	public List<Archivo> getArchivoByParam (String namedString, Object [] parametros) {
		return archivoDao.findAllByNamedQueryParam(namedString, parametros);
	}
	
	@Override
	public List<Requisito> getRequisitos(int cursoId, int centroId){
		return inatecDao.getRequisitos(cursoId, centroId);
	}

	@Override
	public List<Long> getUnidades() {
		return null;
	}
	
	@Override
	public Archivo getArchivoOneByParam (String namedString, Object [] parametros){
		return archivoDao.findOneByNamedQueryParam(namedString, parametros);
	}
	
	@Override
	public List<Pais> getPaises (){
		return paisDao.findAll(Pais.class);
	}
	
	@Override
	public Pais getPaisByNQParam(String namedString, Object [] param){
		return paisDao.findOneByNamedQueryParam(namedString, param);
	}

	@Override
	public Map<Long, String> getUnidadesByEstructuraId(Integer estructura) {
		return inatecDao.getUnidadesByEstructuraId(estructura);
	}

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

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Usuario registrarAcceso(Usuario usuario) {
		if(usuario != null){
			usuario.setFechaUltimoAcceso(new Date());
			return usuarioDao.save(usuario);
		}
		return null;
	}

	@Override
	public boolean existeUsuario(String usuario) {
		Usuario user = usuarioDao.findOneByNamedQueryParam("Usuario.findByLogin", new Object[] {usuario});
		if(user != null)
			return true;
		else
			return false;
	}

	@Override
	public List<Instrumento> getInstrumentos(Integer entidadId) {
		return instrumentoDao.findAllByNamedQueryParam("Instrumento.findAllByEntidadId", new Object[] {entidadId});
	}

	@Override
	public Contacto getContactoLocalByLogin(String login) {
		return contactoDao.findOneByNamedQueryParam("Contacto.findByLogin", new Object[] {login});
	}

	@Override
	public boolean portafolioVerificado(Contacto contacto, String tipoEstadoPortafolio){
		
		int 	   contador = 0;
		Mantenedor estadoArchivo = null;
		Mantenedor estadoVerificado = null;
		Integer    proxEstado = null;
		
		List<Archivo> portafolio = new ArrayList<Archivo> ();
		
		portafolio = archivoDao.findAllByNamedQueryParam("Archivo.findByContactoId", new Object[] {contacto.getId()});
		
		estadoVerificado = this.getMantenedorMaxByTipo(tipoEstadoPortafolio);
		
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
	}

	@Override
	public Collection<Authority> getAuthoritiesInatecByLogin(String usuario) {
		return inatecDao.getAuthorities(inatecDao.getIdRol(usuario));
	}
	
	@Override
	public Collection<Authority> getAuthoritiesInatecByRolId(Integer rolId) {
		return inatecDao.getAuthorities(rolId);
	}

	@Override
	public Contacto getContactoInatecByLogin(String login) {
		return contactoDao.findOneByNamedQueryParam("Contacto.findByLoginInatec", new Object[] {login});
	}

	@Override
	public Contacto getContactoByLogin(String login) {
		Contacto contacto = contactoDao.findOneByNamedQueryParam("Contacto.findByLoginInatec", new Object[] {login});
		if(contacto != null)
			return contacto;
		else
			return 	contactoDao.findOneByNamedQueryParam("Contacto.findByLogin", new Object[] {login});
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Evaluacion guardarEvaluacion(Evaluacion eval, Guia [] guias) {		
		Evaluacion evaluacion = null;
		Integer    puntajeGuia = new Integer (0);
		Set<EvaluacionGuia> setEvalGuia = null;
		
		//Se registra nueva evaluacion
		if (eval != null){				
			evaluacion = evaluacionDao.save(eval);
				
			if (evaluacion != null) {
				setEvalGuia = new HashSet<EvaluacionGuia> ();
					
				for (Guia g : guias) {
													
					EvaluacionGuiaId pkDetalleGuia = new EvaluacionGuiaId();
					
					pkDetalleGuia.setEvaluacion(evaluacion);
					pkDetalleGuia.setGuia(g);
					
					EvaluacionGuia detalleEvaGuia = new EvaluacionGuia();
					
					detalleEvaGuia.setPk(pkDetalleGuia);
					detalleEvaGuia.setPuntaje(puntajeGuia);		
					detalleEvaGuia.setAprobado(false);
					
					detalleEvaGuia = evaluacionGuiaDao.save(detalleEvaGuia);						
					
					setEvalGuia.add(detalleEvaGuia);
				}
			}			
				
		}
		return evaluacion;		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public EvaluacionGuia updateEvaluacionGuia(EvaluacionGuia evalGuia){				
		Integer   			 sumaPuntaje = new Integer(0);
		Evaluacion 			 evaluacion = null;
		List<EvaluacionGuia> listaEvalGuia = null;
		EvaluacionGuia       detalleEvaluacion = null;
				
		if (evalGuia != null) {			
			detalleEvaluacion = evaluacionGuiaDao.save(evalGuia);
			
			if (detalleEvaluacion != null){
				evaluacion = detalleEvaluacion.getPk().getEvaluacion();
				
				listaEvalGuia = this.getEvaluacionGuiaByEvaluacionId(evaluacion.getId());
				
				for (EvaluacionGuia eG : listaEvalGuia) {
					sumaPuntaje += (eG.getPuntaje() == null) ? 0 : eG.getPuntaje();
				}
				
				evaluacion.setPuntaje(sumaPuntaje);
				
				evaluacion = evaluacionDao.save(evaluacion);
						
			}
		}
		
		return detalleEvaluacion;
	}
	
	@Override
	public boolean validaListoInscripcion(Solicitud solicitud){
		String     tipoMantenedor = null;
		Integer     proximoEstado  = null;
		boolean    pasa = true;
		Mantenedor ultimoEstado = null;
		Mantenedor estadoActual = null;
		System.out.println("Entra al servicio validaListoInscripcion");
		tipoMantenedor = solicitud.getTipomantenedorestado();
		estadoActual  = solicitud.getEstatus();
		proximoEstado = Integer.valueOf(estadoActual.getProximo());
		
		ultimoEstado = getMantenedorMaxByTipo(tipoMantenedor);
		System.out.println("Valores de los estados proximoEstado " + proximoEstado + " ultimo anterior " + ultimoEstado.getAnterior());	
		if ( (proximoEstado != null) && (ultimoEstado != null) && (proximoEstado == Integer.valueOf(ultimoEstado.getAnterior()))){
			pasa = validaEvaluacionAprobada(solicitud);			
		} else
			pasa = false;		
		
		return pasa;
	}

	@Override
	public Connection getSqlConnection() throws SQLException {	
		Connection con = objectDao.getSqlConexion();
		return con;
	}
	
	@Override
	public void imprimirReporte(String nombreReporte, Map<String,Object> parametros, String formato, boolean visualiza) throws SQLException{
						
		try {			
			Connection conn = getSqlConnection();
			System.out.println("Conexion " + conn);											
			ControlGenericoReporte.getInstancia().runReporteFisico(nombreReporte, parametros,formato, conn, visualiza);		
			//String reporte = nombreReporte.toLowerCase() + "." + Global.EXPORT_PDF.toLowerCase();
			//String reporte = nombreReporte + "." + formato.toLowerCase();
			//context.execute("window.open('" +  FacesUtil.getContentPath() + "/reporte?file="+ reporte + "&formato=" + Global.EXPORT_PDF + "','myWindow');");		
			//context.execute("window.open('" +  FacesUtil.getContentPath() + "/reporte/"+ reporte + "','myWindow');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Solicitud> filtraListaSolicitudes(HashMap<String, Object> param, Integer tipoFiltro){
		List<Solicitud> lista = new ArrayList<Solicitud> ();
		List<Solicitud> listaFiltrada = new ArrayList<Solicitud> ();
		Mantenedor inicialEstado = null;
		Mantenedor ultimoEstado = null;
		Mantenedor estadoSolicitud = null;		
		Integer    prxEstadoKey;
		Integer    anteriorEvaluarKey;
		
		Contacto solicitante = null;
		boolean enlistar = false;
					
		lista = getSolicitudesByParam(param);
		
		for (Solicitud dato : lista) {
			solicitante = dato.getContacto();		
			estadoSolicitud = dato.getEstatus();
			
			inicialEstado = (inicialEstado == null) ? getMantenedorMinByTipo(dato.getTipomantenedorestado()) : inicialEstado;
			ultimoEstado = (ultimoEstado == null) ? getMantenedorMaxByTipo(dato.getTipomantenedorestado()) : ultimoEstado;
			
			prxEstadoKey = Integer.valueOf(inicialEstado.getProximo());
			if (estadoSolicitud.getAnterior() != null)
				anteriorEvaluarKey = Integer.valueOf(estadoSolicitud.getAnterior());
			else
				anteriorEvaluarKey = null;
			
			switch(tipoFiltro){
				case 1:{ //Pasa a Estado Convocado
					if (estadoSolicitud.getId() == prxEstadoKey.intValue()) 
						enlistar = portafolioVerificado(solicitante, new String("8"));
					else
						enlistar = false;
					break;
				}
				case 2:{ //Pasa a Asesorado
					if ((anteriorEvaluarKey == prxEstadoKey) && (anteriorEvaluarKey != null))
						enlistar = true;
					else
						enlistar = false;
					
					break;
				}
				case 3: { //Pasa a Listo para Inscripcion					
					enlistar = validaListoInscripcion(dato);
					break;
				}
				default:{
					enlistar = true;
					break;
				}
			}			
								
			if (enlistar == true)
				listaFiltrada.add(dato);
		}
		
		return listaFiltrada;
		
	}
	
	@Override
	public boolean validaProcesoConcluido(Solicitud solicitud, boolean validaEvaluacion){
		boolean pasaConcluido = false;
		Mantenedor ultimoEstado = null;
		Mantenedor estadoActual = solicitud.getEstatus();
		Integer    idMatricula = null;
		
		ultimoEstado = getMantenedorMaxByTipo(solicitud.getTipomantenedorestado());
		
		if (ultimoEstado != null){
			if (Integer.valueOf(estadoActual.getProximo()) == ultimoEstado.getId()){
				idMatricula = solicitud.getIdMatricula();
				pasaConcluido = (idMatricula != null) ? true : false;					
			}				
		}		
		
		return pasaConcluido;
	}
	
	public boolean validaEvaluacionAprobada(Solicitud solicitud){
		boolean pasa = true;
		
		List<Evaluacion> listaEval = getEvaluaciones(solicitud);
		System.out.println("Numero de Evaluaciones " + listaEval.size());
		if (listaEval.size() == 0)
			pasa = false;
		else {
			for (Evaluacion eval : listaEval){
				if (eval.isAprobado() != true){
					pasa = false;
					break;
				}
			}						
		}
		return pasa;
	}

	@Override
	public List<Actividad> getActividadesByEntidadId(Integer entidadId) {
		return actividadDao.findAllByNamedQueryParam("Actividad.findByEntidadId", new Object[] {entidadId});
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void auditar(Auditoria auditoria) {
		auditoriaDao.save(auditoria);
	}
	
	@Override
	public List<Contacto> getContactosByParam(String namedString, Object [] parametros){
		return contactoDao.findAllByNamedQueryParam(namedString, parametros);				
	}
}