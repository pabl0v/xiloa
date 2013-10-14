package service;

import java.util.List;

import support.Planificacion;
import support.UCompetencia;
import model.Requisito;
import model.Usuario;

public interface IService {
	
	public List<Requisito> getRequisitos(int certificacionId);
	public void updateRequisito(Requisito requisito);
	public List<Usuario> getUsuarios();
	public void updateUsuario(Usuario usuario);
	public List<Planificacion> getPlanificacion();
	public List<UCompetencia> getUcompetenciaSinPlanificar();
}