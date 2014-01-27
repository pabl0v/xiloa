package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Contacto;
import model.Requisito;
import model.Rol;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import security.Authority;
import support.Departamento;
import support.Ifp;
import support.Item;
import support.Municipio;
import support.UCompetencia;


/**
 * Esta clase implementa la interface IDaoInatec, para interactuar con los demas esquemas de la BD Inatec
 * 
 * @author Denis Chavez, Miriam Martinez
 * @version 1.0
 *
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DaoInatecImpl implements IDaoInatec {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SQL_SELECT_CONTACTO_INATEC = 
			"select "
			+"u.usuario as usuarioInatec, "
			+"u.id_centro as entidadId, "
			+"'' as primerNombre, "
			+"'' as primerApellido, "
			+"u.nombre_completo as nombreCompleto, "
			+"null as sexo, "
			+"u.correo as correo1, "
			+"'N/D' as telefono1, "
			+"null as tipoContacto, "
			+"null as tipoIdentificacion, "
			+"'N/D' as numeroIdentificacion, "
			+"'N/D' as direccionActual, "
			+"now() as fechaRegistro, "
			+"null as nacionalidadId, "
			+"'N/D' as lugarNacimiento, "
			+"true as inatec, "
			+"r.descripcion_rol as funcion, "
			+"u.id_empleado as empleadoId "
			+"from "
			+"admon.usuario u "
			+"inner join "
			+"admon.usuarios_sistemas us "
			+"on u.usuario=us.usuario "
			+"and u.activo=1 "
			+"and us.activo=1 "
			+"and us.id_sistema=41 "
			+"inner join "
			+"admon.roles r "
			+"on us.id_rol=r.id_rol "
			+"and r.activo=1 "
			+"and u.usuario=?";
	
	private static final String SQL_SELECT_USUARIO = 
			"SELECT "
			+ "u.id_empleado as id, "
			+ "null as nombre, "
			+ "u.usuario as usuario, "
			+ "u.clave as clave, "
			+ "null as rol, "
			+ "true as estatus "
			+ "FROM "
			+ "admon.usuario u "
			+ "where u.activo = 1 "
			+ "and u.usuario=?";

	private static final String SQL_CERTIFICACIONES_SIN_PLANIFICAR = 
		"select"
			+ " null as oferta_id, "
			+ " forma.id as estructura_id, "
			+ " ci.centroid as id_centro, " 
			+ " ci.nombre as nombre, " 
			+ " ci.alias as nombre_corto, " 
			+ " case when ci.direccion is null then 'N/D' else ci.direccion end as direccion, " 
			+ " a.id as acreditacion, " 
			+ " cc.id_curso as id_curso, " 
			+ " c.descripcion as nombre_curso, " 
			+ " null as costo, " 
			+ " null as grupo_clase, " 
			+ " null as disponibilidad, " 
			+ " null as fecha_inicio, " 
			+ " null as fecha_fin " 
		+ "from 	sac.acuerdos_detalles a " 
			+ " inner join registro_cobranza.cu_curso_clasificacion cc on (cc.id=a.id_curso_clasificacion) " 
			+ " inner join registro_cobranza.cu_estructura_formativa forma on forma.id=cc.id_estructura_formativa " 
			+ " inner join registro_cobranza.cu_cat_curso c on (c.id=cc.id_curso) " 
			+ " inner join registro_cobranza.cu_centro_curso ccc on (c.id=ccc.id_curso) " 
			+ " inner join public.centros_inatec ci on (ci.centroid=ccc.id_centro ) " 
		+ "where	forma.id_tipo_estructura in (3,5) "
			+ " and (horas=0 or horas=null) " 
			+ " and not exists (select 1 from sccl.certificaciones x where cast(x.certificacion_curso_id as bigint) = cc.id_curso and cast(x.certificacion_ifp_id as varchar) = ci.centroid and x.certificacion_estatus in (7,8)) ";
	
	private static final String SQL_SELECT_UNIDADES_COMPETENCIA = 
		"select	distinct "+
			"catuc.id as codigo, "+
			"catuc.descripcion as descripcion "+
		"from "+
			"registro_cobranza.cu_estructura_formativa ef "+
			"inner join registro_cobranza.cu_estructura_modulo em on (ef.id=em.id_estructura_formativa) "+
			"inner join registro_cobranza.cu_cat_curso cc on (cc.id=ef.id_curso) "+
			"inner join registro_cobranza.cu_cat_curso mm on (mm.id=em.id_curso) "+
			"inner join registro_cobranza.cu_cat_tipo_modulo tm on (tm.id=em.id_tipo_modulo) "+
			"inner join registro_cobranza.cu_cat_tipo_calificacion tc on (tc.id=em.id_tipo_calificacion) "+
			"left join registro_cobranza.cu_cat_uc catuc on catuc.id=em.id_uc ";
	
	private static final String SQL_SELECT_IFP_INATEC = "select ci.centroid as id_centro, " +
														       "ci.nombre as nombre " +
														  "from public.centros_inatec ci " +
														  "where ci.centroid = case ? when '1000' then ci.centroid else ? end " +
														  "order by ci.nombre";
	
	private static final String SQL_SELECT_DPTOS_INATEC = "select d.departamentoid as dpto_id,  " +
		       											         "d.nombre as dpto_nombre " +
		       											    "from public.departamento d " +
		       											   "order by d.nombre";
	
	private static final String SQL_SELECT_MunicipioByDpto_INATEC = "select m.municipioid as municipio_id,  " +
																	       "m.departamentoid as municipio_dpto_id, " +
		       														       "m.nombre as municipio_nombre " +
		       														  "from public.municipio m " +
		       														  "where m.departamentoid = ? " +
		       														  "order by m.nombre ";
	
	
	
	private static final String SQL_SELECT_REQUISITOS_CERTIFICACION = 
			"select "
				+"cr.id as codigo_requisito, "
				+"cr.descripcion as descripcion_requisito, "
				+"a.id as codigo_acreditacion "
			+"from "
				+"sac.acuerdos ac " 
				+"inner join sac.acuerdos_detalles a on (a.acuerdoid=ac.acuerdoid) " 
				+"inner join registro_cobranza.cu_curso_clasificacion cc on (cc.id=a.id_curso_clasificacion) " 
				+"inner join registro_cobranza.cu_cat_curso c on (c.id=cc.id_curso) " 
				+"inner join registro_cobranza.cu_estructura_formativa ef on (ef.id=cc.id_estructura_formativa) " 
				+"inner join registro_cobranza.cu_estructura_requisito er on (er.id_estructura_formativa=ef.id) " 
				+"inner join registro_cobranza.cu_cat_requisito cr on (cr.id=er.id_requisito) "; 
	
	private static final String SQL_SELECT_ROL_USUARIO =
		"select "
			+"r.id_rol as id "
		+"from	"
			+"admon.roles r, "
			+"admon.usuarios_sistemas us "
		+"where	us.id_rol = r.id_rol "
			+"and r.id_sistema = us.id_sistema "
			+"and r.id_sistema = 41 "
			+"and r.activo = 1 "
			+"and us.activo = 1 "
			+"and exists (select 1 from admon.usuario x where x.usuario=us.usuario and x.activo=1) "
			+"and exists (select 1 from admon.sistemas y where y.id_sistema=us.id_sistema and y.activo=1) "
			+"and us.usuario = ?";
	
	private static final String SQL_SELECT_PERMISOS_ROL =
			"select "
				+"r.opciones as opciones "
			+"from	"
				+"admon.roles r, "
				+"admon.sistemas s "
			+"where r.id_sistema = s.id_sistema "
				+"and s.id_sistema = 41 "
				+"and r.activo = 1 "
				+"and s.activo = 1 "
				+"and r.id_rol = ?";
	
	private static final String SQL_VALIDA_EVAL_BY_INSTRUMENTOS = " select count(cu.unidad_id) as existen " +
																     "from sccl.solicitudes s, sccl.certificaciones c, " +
			                                                               "sccl.certificacion_unidades cu, sccl.evaluaciones e " +         
																      "where s.solicitud_id = ?  " + 
																	     "and c.certificacion_id = s.certificacion_id " +
																	     "and cu.certificacion_id = c.certificacion_id " +
																	     "and e.evaluacion_solicitud_id = s.solicitud_id " +
																	     "and e.evaluacion_unidad_id = cu.unidad_id " +
																	     "and exists (select 1  " +
																			         "  from sccl.instrumentos i, sccl.mantenedores m " +
																			         " where m.mantenedor_id = i.instrumento_tipo " +
																					   " and m.mantenedor_tipo = '6'  " +
																					   " and i.instrumento_unidad_id = e.evaluacion_unidad_id " +
																					   " and not exists ( select 1 " + 
																								        "   from sccl.evaluacion_guia eg, sccl.guias g " +
																									    "  where eg.evaluacion_id = e.evaluacion_id " +
																									      "  and g.guia_id = eg.guia_id  " +       
																									      "  and i.instrumento_id = g.instrumento_id  " +      
																									    "  group by g.instrumento_id " +
																									    ") " +
																					  " )";																					
		
	
	/**
	 * Este metodo obtiene la instancia de usuario a partir del login
	 * 
	 * @param username el login del usuario
	 * @return la instancia de usuario correspondiente al parametro
	 */
	
	public Usuario getUsuario(String usuario) {
		Usuario user = null;
		try
		{			
			user = jdbcTemplate.queryForObject(
					SQL_SELECT_USUARIO, 
					new RowMapper<Usuario>() {
				        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
				          Usuario user = new Usuario();
				          user.setId(rs.getLong("id"));
				          user.setUsuarioAlias(rs.getString("usuario"));
				          user.setUsuarioPwd(rs.getString("clave"));
				          user.setUsuarioEstatus(rs.getBoolean("estatus"));
				          return user;
				        }
				      },
					usuario);			
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}

		Rol rol = new Rol();
		rol.setId(100);
		rol.setDescripcion("inatec");
		rol.setNombre("inatec");
		rol.setEstatus(true);		
		user.setRol(rol);
		return user;
	}
	
	/**
	 * Este metodo obtiene la lista de cursos cuya certificacion se planificara, para la entidad indicada como parametro
	 * 
	 * @param entidadId, la entidad cuyos cursos de certificacion se van a planificar
	 * @return la lista de competencias listas para planificar
	 */

	@Override
	public List<UCompetencia> getCertificacionesSinPlanificar(Integer entidadId) {
		List<UCompetencia> certificaciones = this.jdbcTemplate.query(
				SQL_CERTIFICACIONES_SIN_PLANIFICAR + " and ci.centroid = case '" + entidadId + "' when '1000' then ci.centroid else '" + entidadId+ "' end order by c.descripcion",
		        new RowMapper<UCompetencia>() {
		            public UCompetencia mapRow(ResultSet rs, int rowNum) throws SQLException {
		                UCompetencia certificacion = new UCompetencia();
		                //certificacion.setOfertaId(rs.getInt("oferta_id"));
		                certificacion.setOfertaId(null);
		                certificacion.setEstructuraId(rs.getInt("estructura_id"));
		                certificacion.setGrupo(rs.getString("grupo_clase"));
		                certificacion.setIdCentro(rs.getInt("id_centro"));
		                certificacion.setNombreCentro(rs.getString("nombre"));
		                certificacion.setDireccion(rs.getString("direccion"));
		                certificacion.setIdUCompetencia(rs.getInt("id_curso"));
		                certificacion.setNombreUCompetencia(rs.getString("nombre_curso"));
		                //certificacion.setCosto(rs.getFloat("costo"));
		                certificacion.setCosto(null);
		                //certificacion.setDisponibilidad(rs.getInt("disponibilidad"));
		                certificacion.setDisponibilidad(null);
		                certificacion.setFechaInicio(rs.getDate("fecha_inicio"));
		                certificacion.setFechaFin(rs.getDate("fecha_fin"));
		                return certificacion;
		            }
		        });
		return certificaciones;
	}
	
	/**
	 * Este metodo obtiene las unidades de competencia de la estructura pasada como parametro
	 * 
	 * @param estructura el id de estructura cuyas unidades de competencia se quieren conocer
	 * @return un map con las unidades de competencia de la estructura pasada como parametro
	 */
	
	@Override
	public Map<Long, String> getUnidadesByEstructuraId(Integer estructura) {		
		Map<Long, String> unidades = new HashMap<Long, String>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_UNIDADES_COMPETENCIA + " where catuc.id is not null and ef.id="+estructura);
		for(Map<String, Object> row : rows){
			unidades.put((Long)row.get("codigo"), (String)row.get("descripcion"));
		}
		return unidades;
	}
	
	/**
	 * Este metodo obtiene un listado de los usuarios inatec con roles dentro de responsabilidad dentro del sistema SCCL
	 * 
	 * @return lista de contactos de usuarios inatec con los roles del sistema: evaluador, supervisor, registrador, docente, verificador, etc
	 */

	@Override
	public List<Contacto> getContactosInatec() {
		return null;
	}
	
	/**
	 * Este metodo genera una instancia de contacto para el login especificado. Se usa en la autenticacion con los usuario inatec
	 * 
	 * @param usuario el login del usuairo cuyo contacto se quiere generar
	 * @return una instancia de contacto generada a partir del login
	 */

	@Override
	public Contacto generarContacto(String usuario) {
		Contacto contacto = null;
		try
		{
			contacto = jdbcTemplate.queryForObject(
					SQL_SELECT_CONTACTO_INATEC, 
					new RowMapper<Contacto>() {
				        public Contacto mapRow(ResultSet rs, int rowNum) throws SQLException {
				          Contacto c = new Contacto();
				          c.setUsuarioInatec(rs.getString("usuarioInatec"));
				          c.setEntidadId(rs.getInt("entidadId"));
				          c.setPrimerNombre(rs.getString("primerNombre"));
				          c.setPrimerApellido(rs.getString("primerApellido"));
				          c.setNombreCompleto(rs.getString("nombreCompleto"));
				          c.setSexo(null);
				          c.setCorreo1(rs.getString("correo1"));
				          c.setTelefono1(rs.getString("telefono1"));
				          c.setTipoContacto(null);
				          c.setTipoIdentificacion(null);
				          c.setNumeroIdentificacion(rs.getString("numeroIdentificacion"));
				          c.setDireccionActual(rs.getString("direccionActual"));
				          c.setFechaRegistro(rs.getDate("fechaRegistro"));
				          c.setNacionalidadId(null);
				          c.setLugarNacimiento(rs.getString("lugarNacimiento"));
				          c.setInatec(rs.getBoolean("inatec"));
				          c.setFuncion(rs.getString("funcion"));
				          c.setIdEmpleado(rs.getLong("empleadoId"));
				          return c;
				        }
					},
					usuario);		
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
		return contacto;
	}
	
	/**
	 * Este metodo retorna el rolId del usuario pasado por parametro
	 * 
	 * @param usuario cuyo rolId se quiere saber
	 * @return el rolId del usuario en cuestion
	 */

	@Override
	public Integer getIdRol(String usuario) {	
		Integer rolId = null;
		
		try
		{			
			rolId = jdbcTemplate.queryForObject(
					SQL_SELECT_ROL_USUARIO, 
					new RowMapper<Integer>() {
				        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				          return rs.getInt("id");
				        }
				      },
					usuario);
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}

		return rolId;	
	}
	
	/**
	 * Este metodo obtiene la lista de centros (ifps) ligados a una entidadId
	 * 
	 * @param entidadId el id de entidad
	 * @return la lista de centros correspondientes a la entidad
	 */
	
	@Override
	public List<Ifp> getIfpInatec(Integer entidadId) {
		List<Ifp> ifpList = null;
		try{
			
			entidadId = (entidadId == null) ? 1000 : entidadId;
			Object [] params =  new Object [] {String.valueOf(entidadId), String.valueOf(entidadId)};
			int [] paramsType = {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR};
			 
			ifpList = jdbcTemplate.query(SQL_SELECT_IFP_INATEC, 
										params, paramsType, 
										new RowMapper<Ifp>() {
											public Ifp mapRow(ResultSet rs, int rowNum) throws SQLException {
												Ifp ifp = new Ifp();
												ifp.setIfpId(rs.getInt("id_centro"));
												ifp.setIfpNombre(rs.getString("nombre"));
												return ifp;
											}
										});
			
		} catch(EmptyResultDataAccessException e){
			e.printStackTrace();
			ifpList = null;
		}
		return ifpList;		
	}
	
	/**
	 * Este metodo obtiene el listado de departamentos del pais
	 * 
	 * @return el listado de departamentos del pais
	 */

	@Override
	public List<Departamento> getDepartamentosInatec() {
		List<Departamento> dptos = jdbcTemplate.query(SQL_SELECT_DPTOS_INATEC, 
														new RowMapper<Departamento>() {
															public Departamento mapRow(ResultSet rs, int rowNum) throws SQLException {
																Departamento depto = new Departamento();
																depto.setDpto_id(rs.getInt("dpto_id"));																		
																depto.setDpto_nombre(rs.getString("dpto_nombre"));																
																return depto;
															}
															});	
		return dptos;
	}

	/**
	 * Este metodo obtiene los municipios de un departamento pasado como parametro
	 * 
	 * @param idDepto el departamento cuyos municipios se quieren conocer
	 * @return el listado de los municipios de un departamento
	 */

	@Override
	public List<Municipio> getMunicipioByDeptoInatec(Integer idDepto) {
		List<Municipio> muni = null;
		try{
			Object [] params =  new Object [] {idDepto};
			muni = jdbcTemplate.query(SQL_SELECT_MunicipioByDpto_INATEC,
									  params,  
									  new RowMapper<Municipio>() {
											public Municipio mapRow(ResultSet rs, int rowNum) throws SQLException {
												Municipio munic = new Municipio();
												munic.setMunicipio_id(rs.getInt("municipio_id"));
												munic.setMunicipio_dpto_id(rs.getInt("municipio_dpto_id"));
												munic.setMunicipio_nombre(rs.getString("municipio_nombre"));																															
												return munic;
											}
											});	
		} catch(EmptyResultDataAccessException e){
			e.printStackTrace();
			muni = null;
		}
		return muni;
	}

	/**
	 * Este metodo obtiene los requisitos de un curso en un centro dado
	 * 
	 * @param cursoId el id del curso
	 * @param centroId el ide del centro que oferta el curso
	 * @return la lista de requisitos para ese curso en ese centro
	 */

	@Override
	public List<Requisito> getRequisitos(int cursoId, int centroId) {
		List<Requisito> requisitos = new ArrayList<Requisito>();
		try
		{
			requisitos = jdbcTemplate.query(
							SQL_SELECT_REQUISITOS_CERTIFICACION + " where ac.centroid='" + centroId + "' and c.id= " + cursoId + " order by cr.descripcion",
							new RowMapper<Requisito>() {
								public Requisito mapRow(ResultSet rs, int rowNum) throws SQLException {
									Requisito requisito = new Requisito();
									requisito.setCodigo(rs.getString("codigo_requisito"));
									requisito.setDescripcion(rs.getString("descripcion_requisito"));
									requisito.setAcreditacion(rs.getString("codigo_acreditacion"));
									return requisito;
								}
							}); 
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
		return requisitos;
	}

	/**
	 * Este metodo obtiene el catalogo de unidades de copetencia
	 * 
	 * @return un map conteniendo el catalogo completo de las unidades de competencias
	 */

	@Override
	public Map<Long, Item> getCatalogoUnidades() {
		Map<Long, Item> unidades = new HashMap<Long, Item>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("select u.id as codigo, u.descripcion as descripcion from registro_cobranza.cu_cat_uc u where u.id in (select unidad_id from sccl.certificacion_unidades group by unidad_id)");
		for(Map<String, Object> row : rows){
			unidades.put((Long)row.get("codigo"), new Item((Long)row.get("codigo"),(String)row.get("descripcion")));
		}
		return unidades;
	}

	/**
	 * Este metodo obtiene los permisos de un
	 * 
	 * @param rolId, el rolId cuyos permisos se quieren recuperar
	 * @return retorna la coleccion de permisos del rol en cuestion
	 */

	@Override
	public Collection<Authority> getAuthorities(Integer rolId) {
		
		String rights = null;
		try
		{
			rights = jdbcTemplate.queryForObject(
					SQL_SELECT_PERMISOS_ROL, 
					new RowMapper<String>() {
				        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				          return rs.getString("opciones");
				        }
				      },
					rolId);
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		} 

		rights = "(" + rights + ")";
		
		List<Authority> authorities = new ArrayList<Authority>();
		try
		{
			authorities = jdbcTemplate.query("select m.id as id, m.texto as texto from admon.menu m where m.id in " + rights,
							new RowMapper<Authority>() {
								public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
									Authority authority = new Authority(rs.getString("texto"));
									return authority;
								}
							});
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
		return authorities;
	}
	
	/**
	 * Este metodo obtiene la proxima unidad de competencia a evaluar en una solicitud
	 * 
	 * @param idSolicitud el id de solicitud cuyas unidades sin evaluar se quieren saber
	 * @return la proxima unidad a evaluar
	 */
	
	public Integer getUnidadesSinEvaluacion (Integer idSolicitud){
		Integer existeEval = null;		
		try
		{			
			existeEval = jdbcTemplate.queryForObject(
								SQL_VALIDA_EVAL_BY_INSTRUMENTOS, 
								new RowMapper<Integer>() {
									public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
										return rs.getInt("existen");
									}
								},
								idSolicitud);
			return existeEval;
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
}