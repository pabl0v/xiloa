package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IDao <T extends Object>{
	

	/**
	 * Esta clase registra y actualiza en la BD la instancia enviada por parametro 
	 * 
	 * @param T, cualquier instancia de objeto que se quiera persistir
	 * @return T la instancia persistida
	 */

	public T save(T pojo);
	
	/**
	 * Esta clase elimina de la BD la instancia de la clase enviada por parametro y que contiene el id especificado 
	 * 
	 * @param T, id cualquier clase con instancias en BD y su correspondiente id
	 */
	
	public void remove(Class<T> classe, int id);

	public void remove(Class<T> classe, Long id);
	
	/**
	 * Esta clase elimina de la BD la instancia enviada por parametro 
	 * 
	 * @param T cualquier instancia en BD
	 */	

	public void remove(T pojo);
	
	/**
	 * Esta clase busca en la BD la instancia de la clase enviada por parametro y que contiene el id especificado 
	 * 
	 * @param T, id cualquier clase con instancias en BD y su correspondiente id
	 * @return la instancia encontrada
	 */

	public T findById(Class<T> classe, int id);
	
	public T findById(Class<T> classe, Long id);
	
	/**
	 * Esta clase busca en la BD la lista de todas las instancias de la clase enviada por parametro 
	 * 
	 * @param la clase cuyas instancias se van a recuperar
	 * @return List<T> la lista de instancias encontrada
	 */

	public List<T> findAll(Class<T> classe);
	
	/**
	 * Esta clase busca en la BD la lista de todas las instancias retornadas por la consulta sql enviada por parametro 
	 * 
	 * @param la consulta a ejecutar
	 * @return List<T> la lista de instancias encontrada
	 */

	public List<T> findAllByQuery(String jpql);
	
	/**
	 * Esta clase busca en la BD una unica instancia producida por la consulta enviada por parametro   
	 * 
	 * @param la consulta a ejecutar
	 * @return <T> la instancia encontrada
	 */

	public T findOneByQuery(String jpql);
	
	/**
	 * Esta clase busca en la BD la instancia correspondiente al usuario enviado por parametro 
	 * 
	 * @param el nombre de la namedQuery a ejecutar y su lista de parametros
	 * @return <T> la instancia encontrada
	 */

	public T findByUsername(Class<T> classe, String username);
	
	/**
	 * Esta clase busca en la BD una unica instancia producida por la namedquery especificada por parametro   
	 * 
	 * @param el nombre de la namedQuery a ejecutar y su lista de parametros
	 * @return <T> la instancia encontrada
	 */

	public T findOneByNamedQueryParam(String namedQuery, Object [] params);
	
	/**
	 * Esta clase busca en la BD la lista de todas las instancias retornadas por la namedQuery y parametros especificados 
	 * 
	 * @param el nombre de la namedQuery a ejecutar y su lista de parametros
	 * @return List<T> la lista de instancias encontradas
	 */

	public List<T> findAllByNamedQueryParam(String named, Object [] params);
	
	/**
	 * Esta clase busca en la BD la lista de todas las instancias retornadas por la namedQuery sin parametros 
	 * 
	 * @param el nombre de la namedQuery a ejecutar
	 * @return List<T> la lista de instancias encontradas
	 */

	public List<T> findAllByNamedQuery(String named);
	
	/**
	 * Esta clase busca en la BD la lista de todas las instancias retornadas por la namedQuery sin parametros 
	 * 
	 * @param el nombre de la namedQuery a ejecutar
	 * @return List<T> la lista de instancias encontradas
	 */
	
	public List<T> findAllByNativeQuery(String named);
	
	/**
	 * Esta clase busca y retorna la conexion actual 
	 * 
	 * @return Connection retorna la conexion actual
	 */

	public Connection getSqlConexion() throws SQLException;
}