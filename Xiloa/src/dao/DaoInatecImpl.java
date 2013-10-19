package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	private static final String SQL_SELECT = "SELECT u.nombre_completo FROM admon.usuario u where u.usuario=?";
	private static final String SQL_CERTIFICACIONES_SIN_PLANIFICAR = 
			"select "
			+"ci.centroid as id_centro,"
			+"ci.nombre as nombre,"
			+"ci.alias as nombre_corto,"
			+"o.id_acuerdo_deta as acreditacion,"
			+"cc.id_curso id_curso,"
			+"c.descripcion as nombre_curso,"
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
	
	public String getUsuario(String usuario){
		return jdbcTemplate.queryForObject(SQL_SELECT, String.class, usuario);
	}

	@Override
	public List<UCompetencia> getCertificacionesSinPlanificar() {
		List<UCompetencia> certificaciones = this.jdbcTemplate.query(
				SQL_CERTIFICACIONES_SIN_PLANIFICAR,
		        new RowMapper<UCompetencia>() {
		            public UCompetencia mapRow(ResultSet rs, int rowNum) throws SQLException {
		                UCompetencia certificacion = new UCompetencia();
		                certificacion.setIdCentro(Integer.valueOf(rs.getString("id_centro")));
		                certificacion.setNombreCentro(rs.getString("nombre"));
		                certificacion.setNombreUCompetencia(rs.getString("nombre_curso"));
		                certificacion.setDisponibilidad(Integer.valueOf(rs.getString("disponibilidad")));
		                return certificacion;
		            }
		        });
		return certificaciones;
	}
}