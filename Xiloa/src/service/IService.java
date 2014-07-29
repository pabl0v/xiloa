package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import security.Authority;
import support.Departamento;
import support.Ifp;
import support.Item;
import support.Municipio;
import support.UCompetencia;
import support.USolicitud;
import support.UsuarioExterno;
import model.Actividad;
import model.Archivo;
import model.Auditoria;
import model.Bitacora;
import model.Certificacion;
import model.Contacto;
import model.Convocatoria;
import model.Evaluacion;
import model.EvaluacionGuia;
import model.Guia;
import model.Instrumento;
import model.Laboral;
import model.Mantenedor;
import model.Pais;
import model.Requisito;
import model.Rol;
import model.Solicitud;
import model.Unidad;
import model.Usuario;

/**
 * 
 * @author Denis Chavez, Miriam Martinez
 *
 * @version 1.0
 * 
 * Esta interface agrupa los m�todos con las reglas de negocio y manejo de transacciones que ofrecer� el servicio
 */

public interface IService {

	/*
	 * @return obtiene un map con el cat�logo de pa�ses
	 * 
	 */

	public Map<Long, Pais> getCatalogoPaises();
	
	/*
	 * @return obtiene un map con el cat�logo de departamentos
	 * 
	 */

	public Map<Integer, Departamento> getCatalogoDepartamentos();

	/*
	 * @return obtiene un map con el cat�logo de estados se una evaluaci�n
	 * 
	 */

	public Map<Integer, Mantenedor> getCatalogoEstadosEvaluacion();

	/*
	 * @return obtiene un map con el cat�logo de portafolios de los solicitantes
	 * 
	 */

	public Map<Integer, Mantenedor> getCatalogoPortafolio();

	/*
	 * @return obtiene un map con el cat�logo de posibles estados de una solicitud
	 * 
	 */

	public Map<Integer, Mantenedor> getCatalogoEstadoSolicitud();

	/*
	 * @return obtiene un map con el cat�logo de g�neros
	 * 
	 */

	public Map<Integer, Mantenedor> getCatalogoGenero();

	/*
	 * @return obtiene un map con el cat�logo de posibles estatus de una certificaci�n
	 * 
	 */

	public Map<Integer, Mantenedor> getCatalogoEstatusCertificacion();

	/*
	 * @return obtiene un map con el cat�logo de tipos de actividad en una planificaci�n
	 * 
	 */
	
	public Map<Integer, Mantenedor> getCatalogoTiposActividad();
	
	/*
	 * @return obtiene un map con el cat�logo de posibles estatus de una actividad de planificaci�n
	 * 
	 */

	public Map<Integer, Mantenedor> getCatalogoEstatusActividad();

	/*
	 * @return obtiene un map con el cat�logo de tipos de instrumentos de evaluaci�n
	 * 
	 */
	
	public Map<Integer, Mantenedor> getCatalogoTiposInstrumento();
	
	/*
	 * @return obtiene un map con el cat�logo de tipos de datos laborales posibles
	 * 
	 */	

	public Map<Integer, Mantenedor> getCatalogoTiposDatosLaborales();
	
	/*
	 * @return obtiene la descripci�n de una unidad de competencia
	 * @param el c�digo de la unidad de competencia
	 * 
	 */

	public String getCompetenciaDescripcion(Long codigo);

	/**
	 * @return la instancia del contacto buscado 
	 * @param el login del usuario a buscar, puede ser inatec o local
	 * 
	 */
	
	public Contacto getContactoByLogin(String login);
	
	/**
	 * @return la instancia del contacto buscado 
	 * @param el login del usuario local
	 * 
	 */
	
	public Contacto getContactoLocalByLogin(String login);
	
	/**
	 * @return la instancia del contacto buscado 
	 * @param el login del usuario inatec
	 * 
	 */

	public Contacto getContactoInatecByLogin(String login);
	
	/**
	 * @return lista de permisos del usuario 
	 * @param el id del rol
	 * 
	 */

	public Collection<Authority> getAuthoritiesInatecByRolId(Integer rolId);
	
	/**
	 * @return lista de permisos del usuario 
	 * @param el login del usuario
	 * 
	 */

	public Collection<Authority> getAuthoritiesInatecByLogin(String login);
	
	/*
	 * @return obtiene el listado de roles del sistema
	 * 
	 */

	public List<Rol> getRoles();

	/**
	 * @return lista de instrumentos de evaluaci�n 
	 * @param el id de la entidad o centro de formaci�n
	 * 
	 */
	
	public List<Instrumento> getInstrumentos(Integer entidadId);
	
	/**
	 * @return lista de instrumentos de evaluaci�n 
	 * @param el id de la certificacion
	 * 
	 */
	
	public List<Item> getInstrumentosItemByCertificacionId(Long id);


	/**
	 * @return indica si el usuario existe o no 
	 * @param el login del usuario a buscar
	 * 
	 */
	
	public boolean existeUsuario(String usuario);

	/**
	 * @return la instancia del usuario cuyo acceso se registr� 
	 * @param el login del usuario cuyo acceso se registrar�
	 * 
	 */	

	public Usuario registrarAcceso(Usuario usuario);
	
	/** 
	 * @param el login del usuario local cuya contrase�a se va a resetear
	 * 
	 */
	
	public void resetPassword(String usuario);

	/**
	 * @return el objeto usuario externo a registrar en la base de datos local
	 *  
	 */
	
	public void registrarUsuarioExterno(UsuarioExterno usuario);

	/*
	 * @return obtiene un map con el cat�logo de unidades de competencia
	 * 
	 */
	
	public Map<Long, Item> getCatalogoUnidades();
	
	/*
	 * @return obtiene el listado de certificaciones de una unidad de competencia
	 * @param c�digo de la unidad de competencia
	 * 
	 */
	
	public List<Certificacion> getCertificaciones(Integer entidadId);

	/*
	 * @return obtiene el listado de certificaciones activas para el nombre o centro indicados
	 * @param n�mero de par�metro y valor del par�metro (1-> nombre, 2-> centro)
	 * 
	 */
	
	public List<Certificacion> getCertificacionesActivas(Integer parametro, String valor);

	/**
	 * @return un map conteniendo las unidades de competencia de una estructura formativa 
	 * @param el id de la estructura formativa
	 * 
	 */
	
	public Map<Long, String> getUnidadesByEstructuraId(Integer estructura);

	/*
	 * @return obtiene el listado de mantenedores
	 * 
	 */
	
	public List<Mantenedor> getMantenedores();
	
	
	/**
	 * @return el listado de mantenedores
	 * @param el tipo del mantenedores que se quiere buscar
	 */

	public List<Mantenedor> getMantenedoresByTipo(Integer tipo);

	/**
	 * @return el map de mantenedores
	 * @param el tipo del mantenedores que se quiere buscar
	 */
	
	public Map<Integer, Mantenedor> getMapMantenedoresByTipo(String tipo);

	/**
	 * @return un map conteniendo los departamentos del pa�s 
	 * 
	 */
	
	public Map<Integer, Departamento> getDepartamentosByInatec();
	
	/**
	 * @return un map con el listado de municipios de un departamento 
	 * @param el id del departamento cuyos municipios se quiere buscar
	 */	
	
	public Map<Integer, Municipio> getMunicipioDptoByInatec(Integer idDpto);

	/**
	 * @return el listado de actividades de una certificaci�n
	 * @param el c�digo de la certificaci�n cuyas actividades se quiere buscar
	 */
	
	public List<Actividad> getActividades(Long certificacionId);
	
	/**
	 * @return la lista de actividades de una entidad o centro de formaci�n 
	 * @param el id de la entidad o centro
	 * 
	 */
	
	public List<Actividad> getActividadesByEntidadId(Integer entidadId);

	/**
	 * @return el listado de las actividades seg�n los mantenedores
	 * 
	 */
	
	public List<Mantenedor> getMantenedorActividades();

	/**
	 * @return el listado de los estatus de una certificaci�n seg�n los mantenedores
	 */
	
	public List<Mantenedor> getMantenedorEstatusCertificacion();

	/**
	 * @return la instancia del rol buscado
	 * @param el id del rol a buscar
	 */
	
	public Rol getRolById(int id);

	/**
	 * @return la instancia de contacto generada
	 * @param el login del usuario cuyo contacto se quiere registrar
	 */
	
	public Contacto generarNuevoContactoInatec(String usuario);

	/**
	 * @return indica si se trata de un contacto perteneciente al inatec
	 * @param el login del usuario
	 */
	
	public boolean isNuevoContactoInatec(String usuario);

	/*
	 * @return obtiene el listado de requisitos de la certificaci�n solicitada
	 * @param c�digo de la certificaci�n
	 * 
	 */
	
	public List<Requisito> getRequisitos(int certificacionId);
	
	/*
	 * @param el requisito a guardar
	 * Este m�todo registra o actualiza el requisito en cuesti�n en la base de datos
	 * 
	 */
	
	public void updateRequisito(Requisito requisito);

	/*
	 * @return obtiene el listado de usuarios del sistema
	 * 
	 */
	
	public List<Usuario> getUsuarios();

	/*
	 * @param el usuario a guardar
	 * Este m�todo registra o actualiza el usuario en cuesti�n en la base de datos
	 * 
	 */	

	public void updateUsuario(Usuario usuario);
	
	/**
	 * @return el listado de cursos ofrecidos por una entidad de formaci�n
	 * @param el c�digo de la entidad
	 */
	
	public List<UCompetencia> getUcompetenciaSinPlanificar(Integer entidadId);

	/**
	 * @return el listado de contactos de una entidad de formaci�n
	 * @param el c�digo de la entidad
	 */
	
	public List<Contacto> getContactosInatec(Integer entidadId);

	/**
	 * @return la instancia del usuario local buscado
	 * @param el login del usuario local a buscar
	 */
	
	public Usuario getUsuarioLocal(String usuario);

	/**
	 * @return la instancia del usuario inatec buscado
	 * @param el login del usuario inatec a buscar
	 */
	
	public Usuario getUsuarioInatec(String usuario);	

	/**
	 * Registra el usuario especificado
	 * 
	 * @param el login del usuario
	 * @param el nombre del usuario
	 * @param el apellido del usuario
	 * @param el email del usuario
	 */
	
	public void RegistrarUsuarioOpenId(String login, String nombre, String apellido, String email);

	/**
	 * @return el listado de solicitudes
	 */
	
	public List<USolicitud> getUSolicitudes();
	
	/**
	 * @return el listado de solicitudes
	 */

	public List<Solicitud> getSolicitudes();
	
	/**
	 * @return el listado de solicitudes
	 * @param un map con los par�metros de b�squeda
	 */

	public List<Solicitud> getSolicitudesByParam(HashMap<String, Object> param);

	/**
	 * @return el listado de solicitudes
	 * @param el nombre del namedQuery a usar
	 * @param el arreglo con los par�metros de b�squeda
	 */
	
	public List<Solicitud> getSolicitudesByNQParam(String nQuery, Object [] parametros);

	/**
	 * @return la instancia de la certificaci�n buscada
	 * @param el id de la certificaci�n a buscar
	 */
	
	public Certificacion getCertificacionById(Long id);

	/**
	 * @return la instancia del contacto
	 * @param el n�mero de c�dula del contacto a buscar
	 */
	
	public Contacto getContactoByCedula(String cedula);
	
	/*
	 * @return la instancia de certificaci�n registrada en base de datos
	 * @param la certificaci�n a guardar
	 * 
	 */
		
	public Certificacion guardarCertificacion(Certificacion certificacion);


	/**
	 * @return la instancia del objeto registrado
	 * @param el objeto a guardar
	 */
	
	public Object guardar(Object obj);	

	/**
	 * @return el listado de certificaciones
	 * @param el c�digo de la entidad cuyas certificaciones se quieren buscar
	 */
	
	public List<Certificacion> getCertificacionesByIdIfp(Integer id);

	/**
	 * @return el listado de las entidades o centros de formaci�n
	 * @param el id de la entidad a buscar
	 */
	
	public List<Ifp> getIfpByInatec (Integer entidadId);

	/**
	 * @return el historial laboral del tipo y contacto en cuesti�n
	 * @param el tipo de historial a buscar
	 * @param el contacto cuyo historial interesa
	 */
	
	public List<Laboral> getListLaboralByTipo(Integer tipo, Contacto contacto);

	/**
	 * @return la instancia del dato laboral buscado
	 * @param el id del dato laboral a buscar
	 */
	
	public Laboral getLaboralById(Long idLaboral);
	
	/**
	 * @return los datos laborales del contacto
	 * @param el contacto a buscar
	 */
	
	public Map<Integer, List<Laboral>> getLaboralesMapByContacto(Contacto contacto);

	/**
	 * @return el listado de evaluaciones
	 * @param la solicitud cuyas evaluaciones se quieren conocer
	 */
	
	public List<Evaluacion> getEvaluaciones(Solicitud solicitud);

	/**
	 * @return el listado de evaluaciones no aprobadas
	 * @param la solicitud cuyas evaluaciones se quieren conocer
	 */

	public List<Evaluacion> getEvaluacionesPendientes(Solicitud solicitud);

	/**
	 * @return el listado de evaluaciones no aprobadas
	 * @param el contacto cuyas evaluaciones se quieren conocer
	 */

	public List<Evaluacion> getEvaluacionesPendientesByContactoId(Contacto contacto);
	
	/**
	 * @return la instancia de solicitud buscada
	 * @param el c�digo de la solicitud a buscar
	 */

	public Solicitud getSolicitudById(Long idSolicitud);

	/**
	 * @return el listado de las unidades de competencia de la certificaci�n indicada
	 * @param el id de la certificaci�n
	 */
	
	public List<Unidad> getUnidadesByCertificacionId(Long certificacionId);
	
	/**
	 * @return el listado de las unidades de competencia de la certificaci�n indicada
	 * @param el id de la certificaci�n
	 */
	
	public List<Item> getUnidadesItemByCertificacionId(Long certificacionId);

	/**
	 * @return el listado de los instrumentos de la certificaci�n indicada
	 * @param el id de la certificaci�n
	 */
	
	public List<Instrumento> getInstrumentosByCertificacionId(Long certificacionId);

	/**
	 * @return el instrumento buscado
	 * @param el id del instrumento a buscar
	 */
	
	public Instrumento getInstrumentoById(Long idInstrumento);

	/**
	 * @return el listado de instrumentos de la unidad de competencia indicada
	 * @param el id de la unidad de competencia
	 */
	
	public List<Instrumento> getInstrumentoByUnidad (Long idUnidad);

	/**
	 * @return el listado de las gu�as de evaluaci�n de la evaluaci�n indicada
	 * @param el id de la evaluaci�n
	 */
	
	public List<EvaluacionGuia> getEvaluacionGuiaByEvaluacionId(Long evaluacionId);

	/**
	 * @return el listado de instrumentos de la evaluaci�n indicada
	 * @param el id de la evaluaci�n
	 */
	
	public List<Instrumento> getIntrumentoByEvaluacion(Long evaluacionId);

	/**
	 * @return la instancia inicial o primera del tipo de mantenedor indicado 
	 * @param el tipo del mantenedor
	 */
	
	public Mantenedor getMantenedorMinByTipo(String tipo);

	/**
	 * @return la instancia final o �ltima del tipo de mantenedor indicado 
	 * @param el tipo del mantenedor
	 */
	
	public Mantenedor getMantenedorMaxByTipo(String tipo);

	/**
	 * @return la instancia del mantenedor buscado 
	 * @param el id del mantenedor
	 */
	
	public Mantenedor getMantenedorById(Integer idMantenedor);	

	/**
	 * @return lista de guias de evaluaci�n 
	 * @param el nombre del namedQuery a usar
	 * @param el arreglo conteniendo los par�metros para la b�squeda
	 */
	
	public List<Guia> getGuiaByParam(String namedString, Object [] parametros);
	
	/**
	 * @return lista de guias de evaluaci�n 
	 * @param el id del instrumento
	 */
	
	public List<Guia> getGuiasByInstrumentoId(Long instrumento);

	/**
	 * @return lista de archivos de un portafolio 
	 * @param el nombre del namedQuery a usar
	 * @param el arreglo conteniendo los par�metros para la b�squeda
	 */
	
	public List<Archivo> getArchivoByParam (String namedString, Object [] parametros);

	/**
	 * @return el listado de bit�coras de la actividad indicada
	 * @param el id de la actividad
	 */
	
	public List<Bitacora> getBitacoras(Long actividadId);

	/**
	 * @return lista de requisitos de un curso en un centro espec�fico 
	 * @param el id de curso
	 * @param el id del centro
	 */
	
	public List<Requisito> getRequisitos(int cursoId, int centroId);

	/**
	 * @return lista de unidades de competencia 
	 * 
	 */
	
	public List<Long> getUnidades();

	/**
	 * @return la instancia del archivo 
	 * @param el nombre del namedQuery a usar
	 * @param el arreglo conteniendo los par�metros para la b�squeda
	 */
	
	public Archivo getArchivoOneByParam (String namedString, Object [] parametros);

	/**
	 * @return lista de pa�ses
	 *  
	 */
	
	public List<Pais> getPaises ();

	/**
	 * @return la instancia del pa�s buscado 
	 * @param el nombre del namedQuery a usar
	 * @param el arreglo conteniendo los par�metros para la b�squeda
	 */
	
	public Pais getPaisByNQParam(String namedString, Object [] param);

	/**
	 * @return indica si el portafolio fue o no verificado 
	 * @param el contacto
	 * @param el tipo de estado del portafolio
	 */
	
	public boolean portafolioVerificado(Contacto contacto, String tipoEstadoPortafolio);

	/**
	 * @return la instancia de conexi�n a la base de datos 
	 * 
	 */
	
	public Connection getSqlConnection() throws SQLException;

	/** 
	 * @param el nombre del reporte a imprimir
	 * @param el map con los par�metros
	 * @param el formato del reporte
	 * @param indicador si visualiza o no el reporte
	 * 
	 */
	
	public void imprimirReporte(String nombreReporte, Map<String,Object> parametros, String formato, boolean visualiza) throws SQLException;
	
	/**
	 * @return indica si el proceso de evaluaci�n est� concluido o no 
	 * @param la solicitud a validar
	 * @param el indicador de concluido
	 * 
	 */
	
	public boolean validaProcesoConcluido(Solicitud solicitud, boolean validaEvaluacion);

	/**
	 * @return indica si el proceso de evaluaci�n est� aprobado o no 
	 * @param la solicitud a validar
	 * @param el indicador de prueba diagn�stica
	 * @param el id de la unidad de competencia
	 * 
	 */

	public boolean validaEvaluacionAprobada(Solicitud solicitud, boolean diagnostica, Long ucl);

	/** 
	 * @param la pista de auditor�a a registrar
	 * 
	 */
	
	public void auditar(Auditoria auditoria);

	/**
	 * @return la lista de contactos seg�n los par�metros de b�squeda 
	 * @param el nombre del namedQuery a usar
	 * @param el arrego de par�metros para la b�squeda
	 * 
	 */
	
	public List<Contacto> getContactosByParam(String namedString, Object [] parametros);
	
	/**
	 * @return la instancia de evaluaci�n buscada 
	 * @param el id de la evaluaci�n a buscar
	 * 
	 */
	
	public Evaluacion getEvaluacionById(Long evaluacionId);

	/**
	 * @return la lista de evaluaciones de una solicitud e unidad de competencia 
	 * @param la solicitud
	 * @param la unidad de competencia
	 * 
	 */
	
	public List<Evaluacion> getEvaluacionesBySolicitudUnidad(Solicitud solicitud, Long unidad);

	/**
	 * @return indicador de validado para la unidad de competencia de una solicitud 
	 * @param la solicitud
	 * @param la unidad de competencia
	 * 
	 */
	
	public boolean validaEvalUnidad(Solicitud solicitud, Long ucl);

	/**
	 * @return la instancia de evaluaci�n actualizada 
	 * @param la evaluaci�n a actualizar
	 * @param el indicador de validado
	 * 
	 */
	
	public Evaluacion actualizaEvaluacion(Evaluacion evaluacion, boolean valida);

	/**
	 * @return la instancia de la unidad validada 
	 * @param la solicitud validada
	 * @param la unidad de competencia validada
	 * 
	 */
	
	//public EvaluacionUnidad getEvaluacionUnidadBySolicitudUCL(Solicitud solicitud, Long unidad);

	/**
	 * @return el indicador de validado de la unidad de competencia 
	 * @param la solicitud a validar
	 * @param la unidad de competencia a validar
	 * 
	 */
	
	public boolean validaEvaluacionByUnidad(Solicitud solicitud, Long ucl);

	/**
	 * @return la lista de unidades de competencia evaluadas 
	 * @param el id de la solicitud cuyas unidades evaluadas se quiere conocer
	 * 
	 */
	
	//public List<EvaluacionUnidad> getListEvalUnidad(Long idSolicitud);
	
	/**
	 * @return la lista de unidades aprobadas o reprobadas a nivel global 
	 * @param la solicitud cuyo resumen de unidades evaluadas se quiere conocer
	 * 
	 */
	
	public List<Item> getListEvaluacionesUnidad(Long idSolicitud);

	/**
	 * @return booleando que indica si la solicitud fue anulada o no 
	 * @param el id de la solicitud que se desea anular
	 * 
	 */

	public Solicitud anularSolicitud(Solicitud solicitud);
	
	/*
	 * @return obtiene el listado de certificaciones ofertadas en un centro
	 * @param c�digo del centro
	 * 
	 */

	public List<Item> getCertificacionesItem(Integer entidadId);
	
	/**
	 * @return booleando que indica si el solicitante tiene solicitudes pendientes 
	 * @param el numero de cedula y opcionalmente el id de la certificacion
	 * 
	 */

	public boolean tieneSolicitudesPendientes(String cedula, Long certificacionId);

	/** 
	 * @return booleando indica si la anulacion fue exitosa
	 * @param la evaluacion a anular
	 * 
	 */

	public boolean anularEvaluacion(Evaluacion evaluacion);

	/** 
	 * @param la solicitud cuya matricula se autoriza. La autorizacion valida que las pruebas de lectura-escritura y diagnostica est�n aprobadas
	 * 
	 */
	public void autorizarMatricula(Solicitud solicitud);
	
	/** 
	 * @param la solicitud cuyo estado se quiere cambiar y el indicador del nuevo estado
	 * 
	 */	
	public void actualizarEstadoSolicitud(Solicitud solicitud, int indicador);

	/** 
	 * @param la entidad cuyo listado de solicitudes se desea
	 * 
	 */
	public List<Solicitud> getSolicitudesByEntidadId(int entidadId);
	
	/** 
	 * @param el id de la solicitud cuyas convocatorias se desean obtener
	 * 
	 */
	public List<Convocatoria> getConvocatoriasBySolicitudId(Long solicitudId);
	
	/** 
	 * @param el id de la solicitud cuyas actividades se desean obtener
	 * 
	 */
	public List<Item> getActividadesItemBySolicitudId(Long solicitudId);

	/** 
	 * @param el id de la actividad cuyos involucrados se desean obtener
	 * 
	 */
	public List<Item> getInvolucradosItemByActividadId(Long actividadId);
	
	/** 
	 * @param el id de la certificacion cuyos involucrados se desean obtener agrupados por actividad id
	 * 
	 */
	public Map<Long,List<Item>> getInvolucradosItemByCertificacionId(Long certificacionId);
	
	/** 
	 * @param el id del contacto a recuperar
	 * 
	 */	
	public Contacto getContactoById(Long id);
	
	/** 
	 * @param el id de la actividad a recuperar
	 * 
	 */	
	public Actividad getActividadById(Long id);
	
	/** 
	 * @param el id de la solicitud cuyas evaluaciones se quiere obtener
	 * 
	 */	
	public List<Evaluacion> getEvaluacionesBySolicitudId(Long solicitudId);
		
	/** 
	 * @param la solicitud a evaluar
	 * 
	 */	
	public Evaluacion guardarEvaluacion(Evaluacion evaluacion);
	
	/** 
	 * @param la solicitud y unidad de competencia cuyos instrumentos pendientes se quiere obtener
	 * @return la lista de instrumentos
	 */	
	public List<Item> getInstrumentosPendientesBySolicitud(Solicitud solicitud, Long unidad);
	

	/** 
	 * @param la solicitud que se desea enviar
	 */	
	public void enviarSolicitud(Solicitud solicitud);
}