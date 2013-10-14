package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import support.Involucrado;
import support.Planificacion;


import support.UCompetencia;
import dao.IDao;
import model.Contacto;
import model.Requisito;
import model.Usuario;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ServiceImp implements IService {
	
	@Autowired
	private IDao<Requisito> requisitoDao;
	@Autowired
	private IDao<Usuario> usuarioDao;
	@Autowired
	private IDao<Contacto> contactoDao;
	
	@Override
	public List<Requisito> getRequisitos(int certificacionId) {
		return requisitoDao.findAll(Requisito.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateRequisito(Requisito requisito) {
		requisitoDao.save(requisito);
	}

	@Override
	public List<Usuario> getUsuarios() {
		return usuarioDao.findAll(Usuario.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)	
	public void updateUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	@Override
	public List<Planificacion> getPlanificacion() {
		List<Planificacion> planificaciones = new ArrayList<Planificacion>();
		Contacto evalua;
		Contacto coordina;

		for(int i = 0; i<5; i++){
			Planificacion p = new Planificacion();
			p.setIdCentro(i+1);
			p.setNombreCentro("Centro "+i+1);
			p.setUnidadCompetencia("Unidad "+i+1);
			p.setDisponibilidad(i+1);
			p.setEstatus("Convocatoria");
			evalua = contactoDao.findById(Contacto.class, 2);
			coordina = contactoDao.findById(Contacto.class, 3);
			p.setEvalua(evalua.getPrimerNombre()+" "+evalua.getPrimerApellido());
			p.setCoordina(coordina.getPrimerNombre()+" "+coordina.getPrimerApellido());
			p.setRegistrado("11-10-2013");
			planificaciones.add(p);
		}
		return planificaciones;
	}

	@Override
	public List<UCompetencia> getUcompetenciaSinPlanificar() {
		List<UCompetencia> competenciaSinPlanificarList = new ArrayList<UCompetencia>();
		for(int i = 0; i<5; i++){
			UCompetencia c = new UCompetencia();
			c.setIdCentro(i+6);
			c.setNombreCentro("Centro "+i+6);
			c.setIdUCompetencia(i+6);
			c.setNombreUCompetencia("Competencia "+i+6);
			c.setDisponibilidad(10);
			competenciaSinPlanificarList.add(c);
		}		
		return competenciaSinPlanificarList;
	}

	@Override
	public List<Involucrado> getContactos() {
		List<Contacto> contactos = new ArrayList<Contacto>();
		List<Involucrado> involucrados = new ArrayList<Involucrado>();
		contactos = contactoDao.findAll(Contacto.class);
		for(int i = 0; i<contactos.size(); i++){
			Involucrado involucrado = new Involucrado();
			involucrado.setIdContacto(contactos.get(i).getId());
			involucrado.setNombre(contactos.get(i).getPrimerNombre()+" "+contactos.get(i).getPrimerApellido());
			involucrado.setIdFuncion(i);
			if(i==0) involucrado.setFuncion("Supervisor");
			if(i==1) involucrado.setFuncion("Evaluador");
			if(i==2) involucrado.setFuncion("Tecnico docente");
			if(i==3) involucrado.setFuncion("Registro Academico");
			involucrado.setCorreo(contactos.get(i).getCorreo1());
			involucrados.add(involucrado);
		}
		return involucrados;
	}
}