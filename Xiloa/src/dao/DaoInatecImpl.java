package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Contacto;
import model.Rol;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import support.UCompetencia;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DaoInatecImpl implements IDaoInatec {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SQL_SELECT_CONTACTO_INATEC = 
			"SELECT "
			+ "u.nombre_completo as nombre_completo, "
			+ "u.cargo_usuario as funcion, "
			+ "u.usuario as usuario, "
			+ "u.clave as clave, "
			+ "null as rol, "
			+ "true as estatus "
			+ "FROM "
			+ "admon.usuario u "
			+ "where u.activo = 1 "
			+ "and u.usuario=?";
	
	private static final String SQL_SELECT_USER = 
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

	private static final String SQL_SELECT_ROLES = 
			"select "
			+"r.id_rol, "
			+"r.descripcion_rol, "
			+"r.descripcion_rol "
			+"from "
			+"admon.usuarios_sistemas s "
			+"inner join "
			+"admon.usuario u "
			+"on u.usuario = s.usuario "
			+"and u.id_empleado = s.id_empleado "
			+"and u.activo = 1 "
			+"and s.activo = 1 "
			+"inner join "
			+"admon.roles r "
			+"on r.id_rol = s.id_rol "
			+"and r.activo = 1 "
			+"and u.usuario=? "
			+"limit 1";

	private static final String SQL_CERTIFICACIONES_SIN_PLANIFICAR = 
			"select "
			+"ci.centroid as id_centro,"
			+"ci.nombre as nombre,"
			+"ci.alias as nombre_corto,"
			+"ci.direccion as direccion,"
			+"o.id_acuerdo_deta as acreditacion,"
			+"cc.id_curso id_curso,"
			+"c.descripcion as nombre_curso,"
			+"o.costo_normal as costo, "
			+"o.grupo as grupo_clase,"
			+"o.cupo as disponibilidad "
			+"from " 
			+"registro_cobranza.rg_oferta o "
			+"inner join sac.acuerdos_detalles a on (a.id=o.id_acuerdo_deta) "
			+"inner join registro_cobranza.cu_curso_clasificacion cc on (cc.id=a.id_curso_clasificacion) "
			+"inner join registro_cobranza.cu_cat_curso c on (c.id=cc.id_curso) "
			+"inner join public.centros_inatec ci on (ci.centroid=cast(o.id_centro as varchar)) " 
			+"where "
			+"c.id_tipo_evento=4 "
			+"order by c.descripcion";
	
	public Usuario getUsuario(String usuario) {
		Usuario user = null;
		try
		{
			user = jdbcTemplate.queryForObject(
					SQL_SELECT_USER, 
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
		rol.setId((long)100);
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
		                certificacion.setIdCentro(rs.getInt("id_centro"));
		                certificacion.setNombreCentro(rs.getString("nombre"));
		                certificacion.setDireccion(rs.getString("direccion"));
		                certificacion.setIdUCompetencia(rs.getInt("id_curso"));
		                certificacion.setNombreUCompetencia(rs.getString("nombre_curso"));
		                certificacion.setCosto(rs.getFloat("costo"));
		                certificacion.setDisponibilidad(rs.getInt("disponibilidad"));
		                return certificacion;
		            }
		        });
		return certificaciones;
	}

	@Override
	public List<Contacto> getContactosInatec() {
		
		return null;
	}

	@Override
	public Contacto getContacto(Usuario usuario) {
		
		return null;
	}
}