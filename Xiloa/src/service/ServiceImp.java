package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Override
	public List<Certificacion> getCertificaciones(){
		return certificacionDao.findAllByNamedQuery("Certificacion.findAll");
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
		Usuario creador = getUsuarioLocal("admin");								//actualizar
		
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
	public List<Usuario> getUsuarios() {
		return usuarioDao.findAll(Usuario.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)	
	public void updateUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	@Override
	public List<UCompetencia> getUcompetenciaSinPlanificar() {
		return inatecDao.getCertificacionesSinPlanificar();
	}
	
	@Override
	public List<Contacto> getContactosInatec() {
		return contactoDao.findAllByNamedQuery("Contacto.findInvolucradosInatec");
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
		
		if (param.size() > 0 ) {
			
			String campo;
			Object valor;
			
			Iterator<String> claveSet = param.keySet().iterator();			
		    
		    while(claveSet.hasNext()){		      
		    	campo = claveSet.next();		    	
		    	if (param.get(campo) instanceof Integer || param.get(campo) instanceof Long) {
		    		valor = param.get(campo);
		    	} else {
		    		valor = "'" + param.get(campo) + "'";
		    	}		    	
		    	
		    	sqlWhere = (sqlWhere == null) ? "where " + campo + " = " + valor :sqlWhere + " and " + campo + " = " + valor;		        		        
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
			String nombreCompleto = ((Contacto) objeto).getPrimerNombre()+" "+((Contacto)objeto).getPrimerApellido();
			((Contacto)objeto).setNombreCompleto(nombreCompleto);
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
	public List<Ifp> getIfpByInatec () {
		return inatecDao.getIfpInatec();
	}

	@Override
	public List<Actividad> getActividades(Long certificacionId) {
		if(certificacionId != null)
			return actividadDao.findAllByNamedQueryParam("Actividad.findByCertificacionId", new Object[] {certificacionId});
		else
			return actividadDao.findAll(Actividad.class);
	}

	@Override
	public List<Mantenedor> getMantenedores() {
		return mantenedorDao.findAll(Mantenedor.class);		
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
	
	/*
	@Override
	public Unidad getUnidadById(Long idUnidad){
		Object [] objs =  new Object [] {idUnidad};
		return unidadDao.findOneByNamedQueryParam("Unidad.findById", objs);
	}*/
	
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
		//return longDao.findAllByNamedQueryParam("Certificacion.findUnidadesByCert", new Object[] {certificacionId});		
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
	public Map<Long, Item> getCatalogoUnidades() {
		return inatecDao.getCatalogoUnidades();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void registrarUsuarioExterno(UsuarioExterno usuario) {
		
		Usuario user = new Usuario();
		JavaEmailSender emailSender =  new JavaEmailSender();
		
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
		emailSender.createAndSendEmail(usuario.getEmail1(), "Creacion de cuenta...", "Su usuario y contraseña son: "+usuario.getUsuario()+"/"+password);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void resetPassword(String usuario) {
		Usuario user = usuarioDao.findOneByNamedQueryParam("Usuario.findByLogin", new Object[] {usuario});
		JavaEmailSender emailSender =  new JavaEmailSender();
		
		if(user != null){
			String password = PasswordGenerator.randomPassword(8);
			String encoded = PasswordGenerator.encodedPassword(password);
			user.setCambiarPwd(true);
			user.setUsuarioPwd(encoded);
			usuarioDao.save(user);
			emailSender.createAndSendEmail(user.getContacto().getCorreo1(), "Nueva contraseña...", "Su usuario y nueva contraseña son: "+usuario+"/"+password);
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
	public List<Instrumento> getInstrumentos() {
		return instrumentoDao.findAllByNamedQuery("Instrumento.findAll");
	}

	@Override
	public Contacto getContactoByLogin(String login) {
		return contactoDao.findOneByNamedQueryParam("Contacto.findByLogin", new Object[] {login});
	}

	@Override
	public List<Rol> getRoles() {
		return rolDao.findAll(Rol.class);
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
}