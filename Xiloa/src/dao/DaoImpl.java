package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 
 * Esta clase implementa la interfaz Dao que contiene metodos de acceso a los datos en la base de datos
 * 
 * @author Denis Chavez, Miriam Martinez
 *
 * @param <T> Cualquier tipo de clase
 *
 */

@Repository("daoImpl")	
public class DaoImpl<T extends Object> implements IDao<T>{

	private static final Log log = LogFactory.getFactory().getInstance(DaoImpl.class);
		
	@PersistenceContext
	protected EntityManager em;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Esta clase registra y actualiza en la BD la instancia enviada por parametro 
	 * 
	 * @param T, cualquier instancia de objeto que se quiera persistir
	 * @return T la instancia persistida
	 */
	
	@Override
	public T save(T pojo) {
		log.debug("merge instance " + pojo.getClass().getName());
		return (T)em.merge(pojo);
	}
	
	/**
	 * Esta clase elimina de la BD la instancia de la clase enviada por parametro y que contiene el id especificado 
	 * 
	 * @param T, id cualquier clase con instancias en BD y su correspondiente id
	 */
	
	public void remove(Class<T> classe, int id) {
		log.debug("removing instance " + classe.getName() + " with id: " + id);
		T pojo = findById(classe, id);
		if(pojo != null) {
			em.remove(pojo);
		}		
	}
	
	public void remove(Class<T> classe, Long id) {
		log.debug("removing instance " + classe.getName() + " with id: " + id);
		T pojo = findById(classe, id);
		if(pojo != null) {
			em.remove(pojo);
		}		
	}
	
	/**
	 * Esta clase elimina de la BD la instancia enviada por parametro 
	 * 
	 * @param T cualquier instancia en BD
	 */	

	public void remove(T pojo) {
		log.debug("removing instance " + pojo.getClass().getName());
		if(pojo != null){
			em.remove(pojo);
		}
	}
	
	/**
	 * Esta clase busca en la BD la instancia de la clase enviada por parametro y que contiene el id especificado 
	 * 
	 * @param T, id cualquier clase con instancias en BD y su correspondiente id
	 * @return la instancia encontrada
	 */

	@Override
	public T findById(Class<T> classe, int id) {
		log.debug("findById instance " + classe.getName() + " with id: " + id);
		return (T) em.find(classe, id);
	}
	
	@Override
	public T findById(Class<T> classe, Long id) {
		log.debug("findById instance " + classe.getName() + " with id: " + id);
		return (T) em.find(classe, id);
	}
	
	/**
	 * Esta clase busca en la BD la lista de todas las instancias de la clase enviada por parametro 
	 * 
	 * @param la clase cuyas instancias se van a recuperar
	 * @return List<T> la lista de instancias encontrada
	 */

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> classe) {
		StringBuffer sb = new StringBuffer(20);
		sb.append("from ").append(classe.getName());
		List<T> result = em.createQuery(sb.toString()).getResultList();
		log.debug("finding all successful, result size : " + result.size());
		return result;
	}
	
	/**
	 * Esta clase busca en la BD la lista de todas las instancias retornadas por la consulta sql enviada por parametro 
	 * 
	 * @param la consulta a ejecutar
	 * @return List<T> la lista de instancias encontrada
	 */

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAllByQuery(String jpql) {
		List<T> result = em.createQuery(jpql).getResultList();
		log.debug("finding by query successful, result size : " + result.size());
		return result;
	}
	
	/**
	 * Esta clase busca en la BD una unica instancia producida por la consulta enviada por parametro   
	 * 
	 * @param la consulta a ejecutar
	 * @return <T> la instancia encontrada
	 */

	@SuppressWarnings("unchecked")
	@Override
	public T findOneByQuery(String jpql) {
		
		if(em.createQuery(jpql).getResultList().isEmpty()){
			return null;
		}
		T result = (T) em.createQuery(jpql).getResultList().get(0);
		log.debug("finding by query successful, result size : " + 1);
		return result;
	}
	
	/**
	 * Esta clase busca en la BD la instancia correspondiente al usuario enviado por parametro 
	 * 
	 * @param el nombre de la namedQuery a ejecutar y su lista de parametros
	 * @return <T> la instancia encontrada
	 */

	@Override
	public T findByUsername(Class<T> classe, String username) {
		return em.find(classe, username);
	}
	
	/**
	 * Esta clase busca en la BD una unica instancia producida por la namedquery especificada por parametro   
	 * 
	 * @param el nombre de la namedQuery a ejecutar y su lista de parametros
	 * @return <T> la instancia encontrada
	 */
	
	@Override
	@SuppressWarnings("unchecked")
	public T findOneByNamedQueryParam(String namedQuery, Object [] params){			
		Query q;
		int i;
		q = em.createNamedQuery(namedQuery);
		i = 1;
		for (Object obj :  params) {
			q.setParameter(i++, obj);
		}
		
		try{			
			return (T) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
			
	}
	
	/**
	 * Esta clase busca en la BD la lista de todas las instancias retornadas por la namedQuery y parametros especificados 
	 * 
	 * @param el nombre de la namedQuery a ejecutar y su lista de parametros
	 * @return List<T> la lista de instancias encontradas
	 */
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAllByNamedQueryParam(String named, Object [] params){
		List<T> result;
		Query q = em.createNamedQuery(named);
		int i = 1;
		for (Object obj :  params) {
			q.setParameter(i++, obj);
		}
		
		try {
			result = q.getResultList();			
		} catch (NoResultException e) {
			result = null;
		}		
		return result;
	}
	
	/**
	 * Esta clase busca en la BD la lista de todas las instancias retornadas por la namedQuery sin parametros 
	 * 
	 * @param el nombre de la namedQuery a ejecutar
	 * @return List<T> la lista de instancias encontradas
	 */
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAllByNamedQuery(String named){
		List<T> result;
		Query q = em.createNamedQuery(named);
		
		try {
			result = q.getResultList();			
		} catch (NoResultException e) {
			result = null;
		}		
		return result;
	}
	
	/**
	 * Esta clase busca en la BD la lista de todas las instancias retornadas por la namedQuery sin parametros 
	 * 
	 * @param el nombre de la namedQuery a ejecutar
	 * @return List<T> la lista de instancias encontradas
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllByNativeQuery(String named){
		List<T> result;
		Query q = em.createNativeQuery(named);
		
		try {
			result = q.getResultList();
		} catch (NoResultException e) {
			result = null;
		}		
		return result;
	}
	
	/**
	 * Esta clase busca y retorna la conexion actual 
	 * 
	 * @return Connection retorna la conexion actual
	 */
	
	@Override
	public Connection getSqlConexion() throws SQLException {			
		DataSource ds = null;
		Connection c = null;
		System.out.println("Entra a DaoImp getSqlConexion");
		try{			
			ds = jdbcTemplate.getDataSource(); //(DataSource)em.getProperties().get("dataSource");			
			System.out.println("DataSource " + ds);
			c = ds.getConnection();
			System.out.println("Conexion " + c);
		}catch (Exception e){
			e.printStackTrace();
			c = null;
		}
	
		return c;
	}
}