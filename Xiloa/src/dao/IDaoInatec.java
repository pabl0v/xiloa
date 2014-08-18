package dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import model.Contacto;
import model.Requisito;
import model.Usuario;
import security.Authority;
import support.Departamento;
import support.Ifp;
import support.Item;
import support.Municipio;
import support.Pais;
import support.UCompetencia;

public interface IDaoInatec {
	
	/**
	 * Este metodo obtiene los permisos de un
	 * 
	 * @param rolId, el rolId cuyos permisos se quieren recuperar
	 * @return retorna la coleccion de permisos del rol en cuestion
	 */

	public Collection<Authority> getAuthorities(Integer rolId);
	
	/**
	 * Este metodo retorna el rolId del usuario pasado por parametro
	 * 
	 * @param usuario cuyo rolId se quiere saber
	 * @return el rolId del usuario en cuestion
	 */
	
	public Integer getIdRol(String usuario);
	
	/**
	 * Este metodo obtiene la lista de cursos cuya certificacion se planificara, para la entidad indicada como parametro
	 * 
	 * @param entidadId, la entidad cuyos cursos de certificacion se van a planificar
	 * @return la lista de competencias listas para planificar
	 */
	public List<UCompetencia> getCertificacionesSinPlanificar(Integer entidadId);
	
	/**
	 * Este metodo obtiene el catalogo de unidades de copetencia
	 * 
	 * @return un map conteniendo el catalogo completo de las unidades de competencias
	 */
	public Map<Long, Item> getCatalogoUnidades();
	
	/**
	 * Este metodo obtiene las unidades de competencia de la estructura pasada como parametro
	 * 
	 * @param estructura el id de estructura cuyas unidades de competencia se quieren conocer
	 * @return un map con las unidades de competencia de la estructura pasada como parametro
	 */
	public Map<Long, String> getUnidadesByEstructuraId(Integer estructura); 
	
	/**
	 * Este metodo obtiene la instancia de usuario a partir del login
	 * 
	 * @param username el login del usuario
	 * @return la instancia de usuario correspondiente al parametro
	 */
	public Usuario getUsuario(String username);
	
	/**
	 * Este metodo obtiene un listado de los usuarios inatec con roles dentro de responsabilidad dentro del sistema SCCL
	 * 
	 * @return lista de contactos de usuarios inatec con los roles del sistema: evaluador, supervisor, registrador, docente, verificador, etc
	 */
	public List<Contacto> getContactosInatec();
	
	/**
	 * Este metodo genera una instancia de contacto para el login especificado. Se usa en la autenticacion con los usuario inatec
	 * 
	 * @param usuario el login del usuairo cuyo contacto se quiere generar
	 * @return una instancia de contacto generada a partir del login
	 */
	public Contacto generarContacto(String usuario);
	
	/**
	 * Este metodo obtiene la lista de centros (ifps) ligados a una entidadId
	 * 
	 * @param entidadId el id de entidad
	 * @return la lista de centros correspondientes a la entidad
	 */
	public List<Ifp> getIfpInatec(Integer entidadId);
	
	/**
	 * Este metodo obtiene el listado de departamentos del pais
	 * 
	 * @return el listado de departamentos del pais
	 */
	public List<Departamento> getDepartamentosInatec();
	
	/**
	 * Este metodo obtiene los municipios de un departamento pasado como parametro
	 * 
	 * @param idDepto el departamento cuyos municipios se quieren conocer
	 * @return el listado de los municipios de un departamento
	 */
	public List<Municipio> getMunicipioByDeptoInatec(Integer idDepto);
	
	/**
	 * Este metodo obtiene los requisitos de un curso en un centro dado
	 * 
	 * @param cursoId el id del curso
	 * @param centroId el ide del centro que oferta el curso
	 * @return la lista de requisitos para ese curso en ese centro
	 */
	public List<Requisito> getRequisitos(int cursoId, int centroId);
	
	/**
	 * Este metodo obtiene la proxima unidad de competencia a evaluar en una solicitud
	 * 
	 * @param idSolicitud el id de solicitud cuyas unidades sin evaluar se quieren saber
	 * @return la proxima unidad a evaluar
	 */
	public Integer getUnidadesSinEvaluacion (Integer idSolicitud);
	
	/**
	 * 
	 * @return un mapa con el catalogo de paises
	 */
	public Map<String, Pais> getCatalogoPaises();
}