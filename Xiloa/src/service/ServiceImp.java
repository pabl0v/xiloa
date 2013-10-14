package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import support.Planificacion;


import support.UCompetencia;
import dao.IDao;
import model.Requisito;
import model.Usuario;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ServiceImp implements IService {
	
	@Autowired
	private IDao<Requisito> requisitoDao;
	@Autowired
	private IDao<Usuario> usuarioDao;
	
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
		//List<Certificacion> certificaciones = new ArrayList<Certificacion>();
		
		//certificaciones = certificacionDao.findByQuery("Select c from certificacion c where c.estatus = '1' and c.ifpId " + ifpId);
		
		//for(int i = 0; i<certificaciones.size(); i++){
		for(int i = 0; i<5; i++){
			Planificacion p = new Planificacion();
			//p.setIdCentro(certificaciones.get(i).getIfpId());
			p.setIdCentro(i+1);
			//p.setNombreCentro(certificaciones.get(i).getIfpNombre());
			p.setNombreCentro("Centro "+i+1);
			p.setUnidadCompetencia("Unidad "+i+1);
			p.setDisponibilidad(10);
			p.setEstatus("Convocatoria");
			p.setEvalua("narauz");
			p.setCoordina("storres");
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
}