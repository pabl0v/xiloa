package service;

import java.util.Date;

import model.Contacto;
import model.Mantenedor;
import model.Rol;
import model.Usuario;
import model.Pais;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:/applicationContext4Test.xml"})
//@ContextConfiguration(locations= {"file:WebContent/WEB-INF/applicationContext.xml"})
public class ServiceTest {

	@Autowired
	private IService service;

	/*               	
	private static Perfil perfil;
	private static List<Perfil> perfiles;
	private static Rol rolAdmin, rolSupervisor, rolVerificador, rolRegistrador, rolDocente, rolVisitante, rolEvaluador;
	private static Usuario usuarioAdmin, usuarioLopez, usuarioPerez, usuarioRuiz, usuarioArauz;
	private static Contacto contactoLopez, contactoRuiz, contactoPerez, contactoArauz, contactoAdmin;
	
	private static Pais pais1, pais2, pais3, pais4, pais5, pais6;
	*/
	
	@BeforeClass
	public static void setUp() throws Exception {
	}

	@Test
	public void registrarMantenedores(){
		
		//creando mantenedores

		service.guardar(new Mantenedor("1","Tipo de Actividad","Divulgacion",null,null));
		service.guardar(new Mantenedor("1","Tipo de Actividad","Prematricula",null,null));
		service.guardar(new Mantenedor("1","Tipo de Actividad","Seleccion",null,null));
		service.guardar(new Mantenedor("1","Tipo de Actividad","Publicación de Seleccion",null,null));
		service.guardar(new Mantenedor("1","Tipo de Actividad","Matricula",null,null));
		service.guardar(new Mantenedor("1","Tipo de Actividad","Asesoria Grupal",null,null));
		service.guardar(new Mantenedor("1","Tipo de Actividad","Asesoria Individual",null,null));
		service.guardar(new Mantenedor("1","Tipo de Actividad","Evaluacion",null,null));
		service.guardar(new Mantenedor("1","Tipo de Actividad","Dictamen de Evaluación",null,null));
		service.guardar(new Mantenedor("1","Tipo de Actividad","Plan de Formacion",null,null));
		service.guardar(new Mantenedor("1","Tipo de Actividad","Publicación de Resultados",null,null));
		
		service.guardar(new Mantenedor("2","Estatus actividad","Pendiente",null,null));
		service.guardar(new Mantenedor("2","Estatus actividad","Programada",null,null));
		service.guardar(new Mantenedor("2","Estatus actividad","Anulada",null,null));
		service.guardar(new Mantenedor("2","Estatus actividad","Completada",null,null));
		
		service.guardar(new Mantenedor("3","Estatus certificacion","Pendiente",null,null));
		service.guardar(new Mantenedor("3","Estatus certificacion","Activa",null,null));
		service.guardar(new Mantenedor("3","Estatus certificacion","Anulada",null,null));
		service.guardar(new Mantenedor("3","Estatus certificacion","Completada",null,null));

		service.guardar(new Mantenedor("4","Estatus Convocatoria","Programada",null,null));
		service.guardar(new Mantenedor("4","Estatus Convocatoria","Cumplida",null,null));	
		service.guardar(new Mantenedor("4","Estatus Convocatoria","No Cumplida",null,null));
		
		service.guardar(new Mantenedor("5","Tipo Datos Laborales","Experiencias",null,null));
		service.guardar(new Mantenedor("5","Tipo Datos Laborales","Estudios",null,null));
		service.guardar(new Mantenedor("5","Tipo Datos Laborales","Calificaciones",null,null));
		service.guardar(new Mantenedor("5","Tipo Datos Laborales","Certificaciones",null,null));
		
		service.guardar(new Mantenedor("6","Tipo de Instrumento","Lectura-Escritura",null,null));
		service.guardar(new Mantenedor("6","Tipo de Instrumento","Diagnostico",null,null));
		service.guardar(new Mantenedor("6","Tipo de Instrumento","Autoevaluacion",null,null));	
		service.guardar(new Mantenedor("6","Tipo de Instrumento","Observacion",null,null));
		service.guardar(new Mantenedor("6","Tipo de Instrumento","Simulacion",null,null));
		service.guardar(new Mantenedor("6","Tipo de Instrumento","Guion Entrevista",null,null));
		service.guardar(new Mantenedor("6","Tipo de Instrumento","No Definido",null,null));
		service.guardar(new Mantenedor("6","Tipo de Instrumento","Otro",null,null));
		
		service.guardar(new Mantenedor("7","Estatus Solicitud","Registrado",null,null));	
		service.guardar(new Mantenedor("7","Estatus Solicitud","Enviado",null,null));
		service.guardar(new Mantenedor("7","Estatus Solicitud","Autorizado",null,null));
		service.guardar(new Mantenedor("7","Estatus Solicitud","Matriculado",null,null));
		service.guardar(new Mantenedor("7","Estatus Solicitud","Asesoria Grupal",null,null));
		service.guardar(new Mantenedor("7","Estatus Solicitud","Asesoria Individual",null,null));
		service.guardar(new Mantenedor("7","Estatus Solicitud","Programado",null,null));
		service.guardar(new Mantenedor("7","Estatus Solicitud","Evaluado",null,null));
		service.guardar(new Mantenedor("7","Estatus Solicitud","Completado",null,null));
		service.guardar(new Mantenedor("7","Estatus Solicitud","Rechazado",null,null));
		service.guardar(new Mantenedor("7","Estatus Solicitud","Anulado",null,null));
		
		service.guardar(new Mantenedor("8","Estatus Evidencia","Registrada",null,null));
		service.guardar(new Mantenedor("8","Estatus Evidencia","Valida",null,null));
		service.guardar(new Mantenedor("8","Estatus Evidencia","No Valida",null,null));
		service.guardar(new Mantenedor("8","Estatus Evidencia","Verificada",null,null));
		
		service.guardar(new Mantenedor("9","Estatus Evaluacion","Registrada",null, null));
		service.guardar(new Mantenedor("9","Estatus Evaluacion","Completada",null,null));
		service.guardar(new Mantenedor("9","Estatus Evaluacion","Anulada",null,null));
		
		service.guardar(new Mantenedor("10","Email del administrador","sccl.inatec@gmail.com",null,null));
		service.guardar(new Mantenedor("10","Password del email del administrador","sccl2013",null,null));
		service.guardar(new Mantenedor("10","Host del servidor de correo","smtp.gmail.com",null,null));
		service.guardar(new Mantenedor("10","Puerto del servidor de correo","587",null,null));
		service.guardar(new Mantenedor("10","Email del encargado de incidencias","dnchavez@hotmail.com",null,null));
		service.guardar(new Mantenedor("10","Ruta del directorio de archivos","c:\\archivos",null,null));

		service.guardar(new Mantenedor("11","Catalogo de Generos","Masculino",null,null));
		service.guardar(new Mantenedor("11","Catalogo de Generos","Feminino",null,null));
		
		//creadndo roles

		service.guardar(new Rol("coordinador","Coordinador",213,null,true));
		service.guardar(new Rol("verificador","Verificador",214,null,true));
		service.guardar(new Rol("tecnico_docente","Tecnico Docente",215,null,true));
		service.guardar(new Rol("informante","Informante",216,null,true));
		service.guardar(new Rol("visitante","Visitante",217,null,true));
		Rol rolAdmin = (Rol)service.guardar(new Rol("admin","Administrador",218,null,true));
		service.guardar(new Rol("evaluador","Evaluador",219,null,true));
		service.guardar(new Rol("asesor","Asesor",220,null,true));
		
		//creando usuario administrador
		
		Usuario usuarioAdmin = (Usuario)service.guardar(new Usuario(null,"admin","d033e22ae348aeb5660fc2140aec35850c4da997",rolAdmin,false,true));
		
		//creando contacto del usuario administrador
		
		service.guardar(new Contacto(usuarioAdmin,null, rolAdmin, 1000,"Admin",null,"Admin",null,"Administrador",1,"admin@inatec.edu.ni",null,"00000000",null, 1, 1,"12345678901234","Managua",new Date(),new Date(), 1, 1, 1, "Managua", false, null,null,null));
		
		//catalogo de paises
		
		service.guardar(new Pais ("Nicaragua", "Nicaragua"));
		service.guardar(new Pais ("Costa Rica", "Costa Rica"));
		service.guardar(new Pais ("El Salvador", "El Salvador")); 
		service.guardar(new Pais ("Panama", "Panama")); 
		service.guardar(new Pais ("Honduras", "Honduras")); 
		service.guardar(new Pais ("Guatemala", "Guatemala"));

		/*
		mantenedor1 = new Mantenedor("1","Tipo de Actividad","Divulgacion",null,null);
		mantenedor2 = new Mantenedor("1","Tipo de Actividad","Verificacion",null,null);
		mantenedor3 = new Mantenedor("1","Tipo de Actividad","Evaluacion",null,null);
		mantenedor4 = new Mantenedor("1","Tipo de Actividad","Convocatoria",null,null);
		
		mantenedor5 = new Mantenedor("2","Involucrado Certificacion","Creador",null,null);
		mantenedor6 = new Mantenedor("2","Involucrado Certificacion","Ejecutor",null,null);
		
		mantenedor7 = new Mantenedor("3","Estatus certificaciones","Pendiente",null,null);
		mantenedor8 = new Mantenedor("3","Estatus certificaciones","Activo",null,null);
		mantenedor9 = new Mantenedor("3","Estatus certificaciones","Anulado",null,null);
		mantenedor38 = new Mantenedor("3","Estatus certificaciones","Cerrado",null,null);
		
		mantenedor10 = new Mantenedor("4","Estatus actividades","Pendiente",null,null);
		mantenedor11 = new Mantenedor("4","Estatus actividades","Programada",null,null);
		mantenedor12 = new Mantenedor("4","Estatus actividades","Anulada",null,null);
		mantenedor39 = new Mantenedor("4","Estatus actividades","Completada",null,null);
		
		mantenedor13 = new Mantenedor("5","Tipos Datos Laborales","Experiencias",null,null);
		mantenedor14 = new Mantenedor("5","Tipos Datos Laborales","Estudios",null,null);
		mantenedor15 = new Mantenedor("5","Tipos Datos Laborales","Calificaciones",null,null);
		mantenedor16 = new Mantenedor("5","Tipos Datos Laborales","Certificaciones",null,null);

		mantenedor17 = new Mantenedor("6","Tipos de Instrumento","Prueba Diagnostica",null,null);
		mantenedor18 = new Mantenedor("6","Tipos de Instrumento","Prueba Objetiva",null,null);
		mantenedor19 = new Mantenedor("6","Tipos de Instrumento","Prueba de Desempleño",null,null);
		
		mantenedor20 = new Mantenedor("7","Estados Solicitud Certificacion","Valido",null,new String("21"));		
		mantenedor21 = new Mantenedor("7","Estados Solicitud Certificacion","Registrada",new String("20"), new String("22"));
		mantenedor22 = new Mantenedor("7","Estados Solicitud Certificacion","Convocado",new String("21"),new String("23"));
		mantenedor23 = new Mantenedor("7","Estados Solicitud Certificacion","Asesorado",new String("22"),new String("24"));
		mantenedor24 = new Mantenedor("7","Estados Solicitud Certificacion","Inscrito",new String("23"),new String("37"));

		mantenedor25 = new Mantenedor("8","Estados Portafolio Evidencia","Valido",null,new String("26"));		
		mantenedor26 = new Mantenedor("8","Estados Portafolio Evidencia","Registrado",new String("25"), new String("27"));
		mantenedor27 = new Mantenedor("8","Estados Portafolio Evidencia","Verificado",new String("26"),null);
		mantenedor28 = new Mantenedor("9","Estados Evaluaciones","Registradas",null, new String("29"));
		mantenedor29 = new Mantenedor("9","Estados Evaluaciones","Completadas",new String("28"),null);
		mantenedor30 = new Mantenedor("10","Genero","Femenino",null, null);
		mantenedor31 = new Mantenedor("10","Genero","Masculino",null,null);	
		
		mantenedor32 = new Mantenedor("11","Email del administrador","sccl.inatec@gmail.com",null,null);
		mantenedor33 = new Mantenedor("11","Password del email del administrador","sccl2013",null,null);
		mantenedor34 = new Mantenedor("11","Host del servidor de correo","smtp.gmail.com",null,null);
		mantenedor35 = new Mantenedor("11","Puerto del servidor de correo","587",null,null);
		mantenedor36 = new Mantenedor("11","Email del encargado de incidencias","dnchavez@hotmail.com",null,null);
		
		mantenedor37 = new Mantenedor("7","Estados Solicitud Certificacion","Concluido",new String("24"),null);
		mantenedor40 = new Mantenedor("7","Estados Solicitud Certificacion","Anulado",null,null);

		service.guardar(mantenedor1);
		service.guardar(mantenedor2);
		service.guardar(mantenedor3);
		service.guardar(mantenedor4);
		service.guardar(mantenedor5);
		service.guardar(mantenedor6);
		service.guardar(mantenedor7);
		service.guardar(mantenedor8);
		service.guardar(mantenedor9);
		service.guardar(mantenedor10);
		service.guardar(mantenedor11);
		service.guardar(mantenedor12);
		service.guardar(mantenedor13);
		service.guardar(mantenedor14);
		service.guardar(mantenedor15);
		service.guardar(mantenedor16);
		service.guardar(mantenedor17);
		service.guardar(mantenedor18);
		service.guardar(mantenedor19);
		service.guardar(mantenedor20);
		service.guardar(mantenedor21);
		service.guardar(mantenedor22);
		service.guardar(mantenedor23);
		service.guardar(mantenedor24);
		service.guardar(mantenedor25);
		service.guardar(mantenedor26);
		service.guardar(mantenedor27);
		service.guardar(mantenedor28);
		service.guardar(mantenedor29);
		service.guardar(mantenedor30);
		service.guardar(mantenedor31);
		service.guardar(mantenedor32);
		service.guardar(mantenedor33);
		service.guardar(mantenedor34);
		service.guardar(mantenedor35);
		service.guardar(mantenedor36);
		service.guardar(mantenedor37);
		service.guardar(mantenedor38);
		service.guardar(mantenedor39);
		service.guardar(mantenedor40);

		//creando perfiles
		//perfil = new Perfil("admin","admin","all","all","all",true,true);
		
		//creando roles

		rolSupervisor = new Rol("supervisor","supervisor",213,null,true);
		rolVerificador = new Rol("verificador","Verificador",214,null,true);
		rolDocente = new Rol("tecnico_docente","Tecnico Docente",215,null,true);
		rolRegistrador = new Rol("registro_academico","Registro Academico",216,null,true);
		rolVisitante = new Rol("visitante","Visitante",217,null,true);
		rolAdmin = new Rol("admin","admin",218,null,true);
		rolEvaluador = new Rol("evaluador","Evaluador",219,null,true);
		
		perfil = (Perfil)service.guardar(perfil);
		perfiles = new ArrayList<Perfil>();
		perfiles.add(perfil);
		
		rolAdmin.setPerfiles(perfiles);
		rolSupervisor.setPerfiles(perfiles);
		rolVerificador.setPerfiles(perfiles);
		rolDocente.setPerfiles(perfiles);
		rolRegistrador.setPerfiles(perfiles);
		rolVisitante.setPerfiles(perfiles);
		rolEvaluador.setPerfiles(perfiles);
		
		rolAdmin = (Rol)service.guardar(rolAdmin);
		rolSupervisor = (Rol)service.guardar(rolSupervisor);
		rolVerificador = (Rol)service.guardar(rolVerificador);
		rolDocente = (Rol)service.guardar(rolDocente);
		rolRegistrador = (Rol)service.guardar(rolRegistrador);
		rolVisitante = (Rol)service.guardar(rolVisitante);
		rolEvaluador = (Rol)service.guardar(rolEvaluador);
		
		//creando usuarios
		
		usuarioAdmin = new Usuario(null,"admin","d033e22ae348aeb5660fc2140aec35850c4da997",rolAdmin,false,true);		
		usuarioLopez = new Usuario(null,"nlopez","d033e22ae348aeb5660fc2140aec35850c4da997",rolAdmin,false,true);
		usuarioRuiz = new Usuario(null,"aruiz","d033e22ae348aeb5660fc2140aec35850c4da997",rolAdmin,false,true);
		usuarioPerez = new Usuario(null,"lperez","d033e22ae348aeb5660fc2140aec35850c4da997",rolAdmin,false,true);
		usuarioArauz = new Usuario(null,"larauz","d033e22ae348aeb5660fc2140aec35850c4da997",rolAdmin,false,true);
		
		usuarioAdmin = (Usuario)service.guardar(usuarioAdmin);
		usuarioArauz = (Usuario)service.guardar(usuarioArauz);
		usuarioLopez = (Usuario)service.guardar(usuarioLopez);
		usuarioPerez = (Usuario)service.guardar(usuarioPerez);
		usuarioRuiz = (Usuario)service.guardar(usuarioRuiz);
					
		//creando contactos
		contactoAdmin = new Contacto(usuarioAdmin,null, rolAdmin, 1000,"Admin",null,"Admin",null,"Administrador",1,"nlopez@inatec.edu.ni",null,"00000000",null, 1, 1,"12345678901234","Managua",new Date(),new Date(),1,1,null, null, false,null,null,null);
		contactoLopez = new Contacto(usuarioLopez,null, rolAdmin, 1000,"Norcecy",null,"Lopez",null,"Norcecy Lopez",1,"nlopez@inatec.edu.ni",null,"00000000",null, 1, 1,"12345678901234","Managua",new Date(),new Date(),1,1,null, null, false,null,null,null);
		contactoRuiz = new Contacto(usuarioRuiz,null,rolAdmin, 1000,"Ana",null,"Ruiz",null,"Ana Ruiz",1,"aruiz@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,1, null, null, false,null,null,null);
		contactoPerez = new Contacto(usuarioPerez,null, rolAdmin,1000,"Luis",null,"Perez",null,"Luis Perez",1,"lperez@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,null,null, null, false,null,null,null);
		contactoArauz = new Contacto(usuarioArauz,null,rolAdmin, 1000,"Luz",null,"Arauz",null,"Luz Arauz",1,"larauz@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,null,null, null, false,null,null,null);

		contactoLopez = (Contacto)service.guardar(contactoLopez);
		contactoRuiz = (Contacto)service.guardar(contactoRuiz);
		contactoPerez = (Contacto)service.guardar(contactoPerez);
		contactoArauz = (Contacto)service.guardar(contactoArauz);
		contactoAdmin = (Contacto)service.guardar(contactoAdmin);
	
		//Creando valores iniciales de paises
		pais1 = new Pais ("Nicaragua", "Nicaragua");
		pais2 = new Pais ("Costa Rica", "Costa Rica");
		pais3 = new Pais ("El Salvador", "El Salvador"); 
		pais4 = new Pais ("Panama", "Panama"); 
		pais5 = new Pais ("Honduras", "Honduras"); 
		pais6 = new Pais ("Guatemala", "Guatemala");
		
		pais1 = (Pais) service.guardar(pais1);
		pais2 = (Pais) service.guardar(pais2);
		pais3 = (Pais) service.guardar(pais3);
		pais4 = (Pais) service.guardar(pais4);
		pais5 = (Pais) service.guardar(pais5);
		pais6 = (Pais) service.guardar(pais6);
		*/
	}
}