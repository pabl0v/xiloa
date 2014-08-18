package service;

import model.Mantenedor;
import model.Rol;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:/applicationContext4Test.xml"})
public class ServiceTest {

	@Autowired
	private IService service;
	
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
		service.guardar(new Mantenedor("1","Tipo de Actividad","Verificación",null,null));
		
		//service.guardar(new Mantenedor("2","Estatus actividad","Pendiente",null,null));
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

		//service.guardar(new Rol("coordinador","Coordinador",213,null,true));
		service.guardar(new Rol("asesor","Asesor",226,true));
		service.guardar(new Rol("verificador","Verificador",227,true));
		service.guardar(new Rol("tecnico_docente","Tecnico Docente",228,true));
		service.guardar(new Rol("informante","Informante",229,true));
		service.guardar(new Rol("visitante","Visitante",230,true));
		//Rol rolAdmin = (Rol)service.guardar(new Rol("admin","Administrador",231,true));
		service.guardar(new Rol("admin","Administrador",231,true));
		service.guardar(new Rol("evaluador","Evaluador",232,true));
		
		//creando usuario administrador
		
		//Usuario usuarioAdmin = (Usuario)service.guardar(new Usuario(null,"admin","d033e22ae348aeb5660fc2140aec35850c4da997",rolAdmin,false,true));
		
		//creando contacto del usuario administrador
		
		//service.guardar(new Contacto(usuarioAdmin,null, rolAdmin, 1000,"Admin",null,"Admin",null,"Administrador",1,"admin@inatec.edu.ni",null,"00000000",null, 1, 1,"12345678901234","Managua",new Date(),new Date(), 1, 1, 1, "Managua", false, null,null,null));
		
		//catalogo de paises
		/*
		service.guardar(new Pais ("Nicaragua", "Nicaragua"));
		service.guardar(new Pais ("Costa Rica", "Costa Rica"));
		service.guardar(new Pais ("El Salvador", "El Salvador")); 
		service.guardar(new Pais ("Panama", "Panama")); 
		service.guardar(new Pais ("Honduras", "Honduras")); 
		service.guardar(new Pais ("Guatemala", "Guatemala"));
		*/
	}
}