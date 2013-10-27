package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Contacto;
import model.Mantenedor;
import model.Perfil;
import model.Rol;
import model.Usuario;

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
	private static Mantenedor mantenedor1, mantenedor2, mantenedor3, mantenedor4, mantenedor5, mantenedor6, mantenedor7, mantenedor8, mantenedor9;
	private static Perfil perfil;
	private static List<Perfil> perfiles;
	private static Rol rolAdmin, rolSupervisor, rolVerificador, rolRegistrador, rolDocente, rolVisitante;
	private static Usuario usuarioAdmin, usuarioLopez, usuarioPerez, usuarioRuiz, usuarioArauz;
	private static Contacto contactoLopez, contactoRuiz, contactoPerez, contactoArauz;

	@BeforeClass
	public static void setUp() throws Exception {
	}

	@Test
	public void registrarMantenedores(){
		
		//creando mantenedores
		
		mantenedor1 = new Mantenedor("1","Actividades","Divulgacion",null,null);
		mantenedor2 = new Mantenedor("1","Actividades","Verificacion",null,null);
		mantenedor3 = new Mantenedor("1","Actividades","Evaluacion",null,null);
		mantenedor4 = new Mantenedor("1","Actividades","Convocatoria",null,null);
		
		mantenedor5 = new Mantenedor("2","Involucrados","Creador",null,null);
		mantenedor6 = new Mantenedor("2","Involucrados","Ejecutor",null,null);
		
		mantenedor7 = new Mantenedor("3","Estatus certificaciones","Pendiente",null,null);
		mantenedor8 = new Mantenedor("3","Estatus certificaciones","Activo",null,null);
		mantenedor9 = new Mantenedor("3","Estatus certificaciones","Anulado",null,null);

		service.guardar(mantenedor1);
		service.guardar(mantenedor2);
		service.guardar(mantenedor3);
		service.guardar(mantenedor4);
		service.guardar(mantenedor5);
		service.guardar(mantenedor6);
		service.guardar(mantenedor7);
		service.guardar(mantenedor8);
		service.guardar(mantenedor9);

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
		
		usuarioAdmin = new Usuario(null,"admin","admin",rolAdmin,true);		
		usuarioLopez = new Usuario(null,"nlopez","nlopez",rolAdmin,true);
		usuarioRuiz = new Usuario(null,"aruiz","aruiz",rolAdmin,true);
		usuarioPerez = new Usuario(null,"lperez","lperez",rolAdmin,true);
		usuarioArauz = new Usuario(null,"larauz","larauz",rolAdmin,true);
		
		usuarioAdmin = (Usuario)service.guardar(usuarioAdmin);
		usuarioArauz = (Usuario)service.guardar(usuarioArauz);
		usuarioLopez = (Usuario)service.guardar(usuarioLopez);
		usuarioPerez = (Usuario)service.guardar(usuarioPerez);
		usuarioRuiz = (Usuario)service.guardar(usuarioRuiz);		
		
		//creando contactos
		
		contactoLopez = new Contacto(usuarioLopez,null,1,"Norcecy",null,"Lopez",null,"Norcecy Lopez",1,"nlopez@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,"Managua",false,null,null,null);
		contactoRuiz = new Contacto(usuarioRuiz,null,1,"Ana",null,"Ruiz",null,"Ana Ruiz",1,"aruiz@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,"Managua",false,null,null,null);
		contactoPerez = new Contacto(usuarioPerez,null,1,"Luis",null,"Perez",null,"Luis Perez",1,"lperez@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,"Managua",false,null,null,null);
		contactoArauz = new Contacto(usuarioArauz,null,1,"Luz",null,"Arauz",null,"Luz Arauz",1,"larauz@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,"Managua",false,null,null,null);

		contactoLopez = (Contacto)service.guardar(contactoLopez);
		contactoRuiz = (Contacto)service.guardar(contactoRuiz);
		contactoPerez = (Contacto)service.guardar(contactoPerez);
		contactoArauz = (Contacto)service.guardar(contactoArauz);		
	}

	/*
	@Test
	public void registrarPerfiles(){
		
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
	}
	
	@Test
	public void registrarUsuarios(){
		
		//creando usuarios
		
		usuarioAdmin = new Usuario(null,"admin","admin",rolAdmin,true);		
		usuarioLopez = new Usuario(null,"nlopez","nlopez",rolAdmin,true);
		usuarioRuiz = new Usuario(null,"aruiz","aruiz",rolAdmin,true);
		usuarioPerez = new Usuario(null,"lperez","lperez",rolAdmin,true);
		usuarioArauz = new Usuario(null,"larauz","larauz",rolAdmin,true);
		
		usuarioAdmin = (Usuario)service.guardar(usuarioAdmin);
		usuarioArauz = (Usuario)service.guardar(usuarioArauz);
		usuarioLopez = (Usuario)service.guardar(usuarioLopez);
		usuarioPerez = (Usuario)service.guardar(usuarioPerez);
		usuarioRuiz = (Usuario)service.guardar(usuarioRuiz);		
		
		//creando contactos
		
		contactoLopez = new Contacto(usuarioLopez,null,1,"Norcecy",null,"Lopez",null,"Norcecy Lopez",1,"nlopez@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,"Managua",false,null,null,null);
		contactoRuiz = new Contacto(usuarioRuiz,null,1,"Ana",null,"Ruiz",null,"Ana Ruiz",1,"aruiz@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,"Managua",false,null,null,null);
		contactoPerez = new Contacto(usuarioPerez,null,1,"Luis",null,"Perez",null,"Luis Perez",1,"lperez@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,"Managua",false,null,null,null);
		contactoArauz = new Contacto(usuarioArauz,null,1,"Luz",null,"Arauz",null,"Luz Arauz",1,"larauz@inatec.edu.ni",null,"00000000",null,1,1,"12345678901234","Managua",new Date(),new Date(),1,"Managua",false,null,null,null);

		contactoLopez = (Contacto)service.guardar(contactoLopez);
		contactoRuiz = (Contacto)service.guardar(contactoRuiz);
		contactoPerez = (Contacto)service.guardar(contactoPerez);
		contactoArauz = (Contacto)service.guardar(contactoArauz);				
	}*/
}