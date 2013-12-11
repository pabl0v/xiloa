package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IDao <T extends Object>{
	public T save(T pojo);
	public void remove(Class<T> classe, int id);
	public void remove(T pojo);
	public T findById(Class<T> classe, int id);
	public List<T> findAll(Class<T> classe);
	public List<T> findAllByQuery(String jpql);
	public T findOneByQuery(String jpql);
	public T findByUsername(Class<T> classe, String username);
	public T findOneByNamedQueryParam(String namedQuery, Object [] params);
	public List<T> findAllByNamedQueryParam(String named, Object [] params);
	public List<T> findAllByNamedQuery(String named);
	public Connection getSqlConexion() throws SQLException;
}