package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Contacto;
import model.Mantenedor;
import model.Perfil;
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
	private static Mantenedor mantenedor1, mantenedor2, mantenedor3, mantenedor4, mantenedor5, mantenedor6, 
	               mantenedor7, mantenedor8, mantenedor9, mantenedor10, mantenedor11, mantenedor12,
	               mantenedor13, mantenedor14, mantenedor15, mantenedor16, mantenedor17, mantenedor18, mantenedor19,
	               mantenedor20, mantenedor21, mantenedor22, mantenedor23, mantenedor24, mantenedor25, mantenedor26, 
	               mantenedor27, mantenedor28, mantenedor29, mantenedor30, mantenedor31; 
	               	
	private static Perfil perfil;
	private static List<Perfil> perfiles;
	private static Rol rolAdmin, rolSupervisor, rolVerificador, rolRegistrador, rolDocente, rolVisitante;
	private static Usuario usuarioAdmin, usuarioLopez, usuarioPerez, usuarioRuiz, usuarioArauz;
	private static Contacto contactoLopez, contactoRuiz, contactoPerez, contactoArauz;
	
	private static Pais pais1, pais2, pais3, pais4, pais5, pais6;
	
	@BeforeClass
	public static void setUp() throws Exception {
	}

	@Test
	public void registrarMantenedores(){
		
		//creando mantenedores
		
		mantenedor1 = new Mantenedor("1","Tipo de Actividad","Divulgacion",null,null);
		mantenedor2 = new Mantenedor("1","Tipo de Actividad","Verificacion",null,null);
		mantenedor3 = new Mantenedor("1","Tipo de Actividad","Evaluacion",null,null);
		mantenedor4 = new Mantenedor("1","Tipo de Actividad","Convocatoria",null,null);
		
		mantenedor5 = new Mantenedor("2","Involucrado Certificacion","Creador",null,null);
		mantenedor6 = new Mantenedor("2","Involucrado Certificacion","Ejecutor",null,null);
		
		mantenedor7 = new Mantenedor("3","Estatus certificaciones","Pendiente",null,null);
		mantenedor8 = new Mantenedor("3","Estatus certificaciones","Activo",null,null);
		mantenedor9 = new Mantenedor("3","Estatus certificaciones","Anulado",null,null);
		
		mantenedor10 = new Mantenedor("4","Estatus actividades","Pendiente",null,null);
		mantenedor11 = new Mantenedor("4","Estatus actividades","Activo",null,null);
		mantenedor12 = new Mantenedor("4","Estatus actividades","Anulado",null,null);
		
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
		mantenedor24 = new Mantenedor("7","Estados Solicitud Certificacion","Cerrada",new String("23"),null);
		
		mantenedor25 = new Mantenedor("8","Estados Portafolio Evidencia","Valido",null,new String("26"));		
		mantenedor26 = new Mantenedor("8","Estados Portafolio Evidencia","Registrado",new String("25"), new String("27"));
		mantenedor27 = new Mantenedor("8","Estados Portafolio Evidencia","Verificado",new String("26"),null);
		mantenedor28 = new Mantenedor("9","Estados Evaluaciones","Registradas",null, new String("29"));
		mantenedor29 = new Mantenedor("9","Estados Evaluaciones","Completadas",new String("28"),null);
		mantenedor30 = new Mantenedor("10","Genero","Femenino",null, null);
		mantenedor31 = new Mantenedor("10","Genero","Masculino",null,null);	

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

		//creando perfiles
		
		perfil = new Perfil("admin","admin","all","all","all",true,true);
		
		//creando roles
						
		rolAdmin = new Rol("admin","admin",null,null,true);
		rolSupervisor = new Rol("supervisor","supervisor",213,null,true);
		rolVerificador = new Rol("verificador","Verificador",214,null,true);
		rolDocente = new Rol("tecnico_docente","Tecnico Docente",215,null,true);
		rolRegistrador = new Rol("registro_academico","Registro Academico",216,null,true);
		rolVisitante = new Rol("visitante","Visitante",null,null,true);
		
		perfil = (Perfil)service.guardar(perfil);
		perfiles = new ArrayList<Perfil>();
		perfiles.add(perfil);
		
		rolAdmin.setPerfiles(perfiles);
		rolSupervisor.setPerfiles(perfiles);
		rolVerificador.setPerfiles(perfiles);
		rolDocente.setPerfiles(perfiles);
		rolRegistrador.setPerfiles(perfiles);
		rolVisitante.setPerfiles(perfiles);
		
		rolAdmin = (Rol)service.guardar(rolAdmin);
		rolSupervisor = (Rol)service.guardar(rolSupervisor);
		rolVerificador = (Rol)service.guardar(rolVerificador);
		rolDocente = (Rol)service.guardar(rolDocente);
		rolRegistrador = (Rol)service.guardar(rolRegistrador);
		rolVisitante = (Rol)service.guardar(rolVisitante);
		
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
		contactoLopez = new Contacto(usuarioLopez,null, null, 1,"Norcecy",null,"Lopez",null,"Norcecy Lopez",1,"nlopez@inatec.edu.ni",null,"00000000",null, 1, 1,"12345678901234","Managua",new Date(),new Date(),1,1,null, null, false,null,null,null);
		contactoRuiz = new Contacto(usuarioRuiz,null,null, 1,"Ana",null,"Ruiz",null,"Ana Ruiz",1,"aruiz@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,1, null, null, false,null,null,null);
		contactoPerez = new Contacto(usuarioPerez,null, null,1,"Luis",null,"Perez",null,"Luis Perez",1,"lperez@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,null,null, null, false,null,null,null);
		contactoArauz = new Contacto(usuarioArauz,null,null, 1,"Luz",null,"Arauz",null,"Luz Arauz",1,"larauz@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,null,null, null, false,null,null,null);

		contactoLopez = (Contacto)service.guardar(contactoLopez);
		contactoRuiz = (Contacto)service.guardar(contactoRuiz);
		contactoPerez = (Contacto)service.guardar(contactoPerez);
		contactoArauz = (Contacto)service.guardar(contactoArauz);
	
		//Creando valores iniciales de paises
		pais1 = new Pais ("Nicaragua", "Nicaraguense");
		pais2 = new Pais ("Costa Rica", "Costa Rica");
		pais3 = new Pais ("El Salvador", "Salvadoreño"); 
		pais4 = new Pais ("Panama", "Panameño"); 
		pais5 = new Pais ("Honduras", "Hondureño"); 
		pais6 = new Pais ("Guatemala", "Guatemalteco");
		
		pais1 = (Pais) service.guardar(pais1);
		pais2 = (Pais) service.guardar(pais2);
		pais3 = (Pais) service.guardar(pais3);
		pais4 = (Pais) service.guardar(pais4);
		pais5 = (Pais) service.guardar(pais5);
		pais6 = (Pais) service.guardar(pais6);
		
	}
}