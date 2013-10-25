package service;

import java.util.ArrayList;
import java.util.List;

import model.Perfil;
import model.Rol;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations= {"classpath:/applicationContext4Test.xml"})
//@ContextConfiguration(locations= {"file:WebContent/WEB-INF/applicationContext.xml"})
public class ServiceTest {

	@Autowired
	private IService service;

	@BeforeClass
	public static void setUp() throws Exception {
		
		//creando perfiles
		
		Perfil perfil = new Perfil();
		perfil.setNombre("admin");
		perfil.setDescripcion("admin");
		perfil.setHabilitado(true);
		perfil.setPermiso("all");
		perfil.setModulo("all");
		perfil.setSeccion("all");
		
		List<Perfil> perfiles = new ArrayList<Perfil>();
		perfiles.add(perfil);
		
		//creando roles
				
		Rol rolAdmin = new Rol();
		rolAdmin.setNombre("admin");
		rolAdmin.setDescripcion("admin");
		rolAdmin.setEstatus(true);
		rolAdmin.setPerfiles(perfiles);
	}
}