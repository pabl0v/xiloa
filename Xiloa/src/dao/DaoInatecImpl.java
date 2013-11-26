package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

import support.Departamento;
import support.Ifp;
import support.Item;
import support.Municipio;
import support.UCompetencia;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DaoInatecImpl implements IDaoInatec {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SQL_SELECT_CONTACTO_INATEC = 
			"select "
			+"u.usuario as usuarioInatec, "
			+"u.id_centro as entidadId, "
			+"'Usuario Inatec' as primerNombre, "
			+"'Usuario Inatec' as primerApellido, "
			+"u.nombre_completo as nombreCompleto, "
			+"1 as sexo, "
			+"u.correo as correo1, "
			+"'N/D' as telefono1, "
			+"1 as tipoContacto, "
			+"1 as tipoIdentificacion, "
			+"'N/D' as numeroIdentificacion, "
			+"'N/D' as direccionActual, "
			+"now() as fechaRegistro, "
			+"1 as nacionalidadId, "
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
		"select "+
			"forma.id as estructura_id, "+
			"ci.centroid as id_centro, "+
			"ci.nombre as nombre, "+
			"ci.alias as nombre_corto, "+
			"ci.direccion as direccion, "+
			"o.id_acuerdo_deta as acreditacion, "+
			"cc.id_curso as id_curso, "+
			"c.descripcion as nombre_curso, "+
			"o.costo_normal as costo, "+
			"o.grupo as grupo_clase, "+
			"o.cupo as disponibilidad, "+
			"o.finicio as fecha_inicio, "+
			"o.ffin as fecha_fin "+
		"from "+
			"registro_cobranza.rg_oferta o "+
			"inner join sac.acuerdos_detalles a on (a.id=o.id_acuerdo_deta) "+
			"inner join registro_cobranza.cu_curso_clasificacion cc on (cc.id=a.id_curso_clasificacion) "+
			"inner join registro_cobranza.cu_estructura_formativa forma on forma.id=cc.id_estructura_formativa "+
			"inner join registro_cobranza.cu_cat_curso c on (c.id=cc.id_curso) "+
			"inner join public.centros_inatec ci on (ci.centroid=cast(o.id_centro as varchar)) "+
		"where "+
			"forma.id_tipo_estructura in (3,5) "+ 
			"and o.activo=1 "+ 
			"and forma.id_tipo_acreditacion=1 "+ 
			"and forma.nivel_cualificacion='2' "+
			"--and o.id_centro='4020' "+ 
		"order by "+
			"c.descripcion";
	
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
														 "order by ci.nombre";
	
	private static final String SQL_SELECT_DPTOS_INATEC = "select d.departamentoid as dpto_id,  " +
		       											         "d.nombre as dpto_nombre " +
		       											    "from public.departamento d " +
		       											   "order by d.nombre";
	
	private static final String SQL_SELECT_MunicipioByDpto_INATEC = "select m.municipioid as municipio_id,  " +
																	       "m.departamentoid as municipio_dpto_id, " +
		       														       "m.nombre as municipio_nombre " +
		       														  "from public.municipio m ";
	
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
	
	@Override
	public void agregarRol(){
		jdbcTemplate.update(
		        "insert into admon.roles (id_rol,descripcion_rol,id_sistema,activo,usuario_grabacion,usuario_actualizacion,opciones) values (?,?,?,?,?,?,?)",
		        213, "Cenicsa",1,1,"tatiana","tatiana","54,63");
	}

	@Override
	public List<UCompetencia> getCertificacionesSinPlanificar() {
		List<UCompetencia> certificaciones = this.jdbcTemplate.query(
				SQL_CERTIFICACIONES_SIN_PLANIFICAR,
		        new RowMapper<UCompetencia>() {
		            public UCompetencia mapRow(ResultSet rs, int rowNum) throws SQLException {
		                UCompetencia certificacion = new UCompetencia();
		                certificacion.setEsructuraId(rs.getInt("estructura_id"));
		                certificacion.setGrupo(rs.getString("grupo_clase"));
		                certificacion.setIdCentro(rs.getInt("id_centro"));
		                certificacion.setNombreCentro(rs.getString("nombre"));
		                certificacion.setDireccion(rs.getString("direccion"));
		                certificacion.setIdUCompetencia(rs.getInt("id_curso"));
		                certificacion.setNombreUCompetencia(rs.getString("nombre_curso"));
		                certificacion.setCosto(rs.getFloat("costo"));
		                certificacion.setDisponibilidad(rs.getInt("disponibilidad"));
		                certificacion.setFechaInicio(rs.getDate("fecha_inicio"));
		                certificacion.setFechaFin(rs.getDate("fecha_fin"));
		                return certificacion;
		            }
		        });
		return certificaciones;
	}
	
	@Override
	public Map<Long, String> getUnidadesByEstructuraId(Integer estructura) {		
		Map<Long, String> unidades = new HashMap<Long, String>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_UNIDADES_COMPETENCIA + " where catuc.id is not null and ef.id="+estructura);
		for(Map<String, Object> row : rows){
			unidades.put((Long)row.get("codigo"), (String)row.get("descripcion"));
		}
		return unidades;
	}

	@Override
	public List<Contacto> getContactosInatec() {
		return null;
	}

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
				          c.setSexo(rs.getInt("sexo"));
				          c.setCorreo1(rs.getString("correo1"));
				          c.setTelefono1(rs.getString("telefono1"));
				          c.setTipoContacto(rs.getInt("tipoContacto"));
				          c.setTipoIdentificacion(rs.getInt("tipoIdentificacion"));
				          c.setNumeroIdentificacion(rs.getString("numeroIdentificacion"));
				          c.setDireccionActual(rs.getString("direccionActual"));
				          c.setFechaRegistro(rs.getDate("fechaRegistro"));
				          c.setNacionalidadId(rs.getInt("nacionalidadId"));
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

	@Override
	public int getIdRol(String usuario) {
		int id = 0;
		try
		{
			id = jdbcTemplate.queryForInt("select u.id_rol from admon.usuarios_sistemas u where u.id_sistema = 41 and u.usuario="+"'"+usuario+"'");
		}
		catch(EmptyResultDataAccessException e)
		{
			return 0;
		}
		return id;
	}
	
	@Override
	public List<Ifp> getIfpInatec() {
		List<Ifp> ifpList = jdbcTemplate.query(SQL_SELECT_IFP_INATEC, 
												new RowMapper<Ifp>() {
													public Ifp mapRow(ResultSet rs, int rowNum) throws SQLException {
														Ifp ifp = new Ifp();
														ifp.setIfpId(rs.getInt("id_centro"));
														ifp.setIfpNombre(rs.getString("nombre"));
														return ifp;
													}
				      							});	
		return ifpList;		
	}

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

	@Override
	public List<Municipio> getMunicipioByDeptoInatec(Integer idDepto) {
		List<Municipio> muni = jdbcTemplate.query(SQL_SELECT_MunicipioByDpto_INATEC + 
				                                  " where m.departamentoid = " + idDepto +
				                                  " order by m.nombre", 
													new RowMapper<Municipio>() {
														public Municipio mapRow(ResultSet rs, int rowNum) throws SQLException {
															Municipio munic = new Municipio();
															munic.setMunicipio_id(rs.getInt("municipio_id"));
															munic.setMunicipio_dpto_id(rs.getInt("municipio_dpto_id"));
															munic.setMunicipio_nombre(rs.getString("municipio_nombre"));																															
															return munic;
														}
														});	
		return muni;
	}

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

	@Override
	public Map<Long, Item> getCatalogoUnidades() {
		Map<Long, Item> unidades = new HashMap<Long, Item>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("select u.id as codigo, u.descripcion as descripcion from registro_cobranza.cu_cat_uc u");
		for(Map<String, Object> row : rows){
			unidades.put((Long)row.get("codigo"), new Item((Long)row.get("codigo"),(String)row.get("descripcion")));
		}
		return unidades;
	}
}