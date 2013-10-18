package dao;

import java.util.List;

public interface IDao <T extends Object>{
	T save(T pojo);
	void remove(Class<T> classe, int id);
	void remove(T pojo);
	T findById(Class<T> classe, int id);
	List<T> findAll(Class<T> classe);
	List<T> findAllByQuery(String jpql);
	T findOneByQuery(String jpql);
	T findByUsername(Class<T> classe, String username);	
}