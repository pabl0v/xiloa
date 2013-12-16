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
import support.UCompetencia;

public interface IDaoInatec {

	public Collection<Authority> getAuthorities(Integer rolId);
	public Integer getIdRol(String usuario);
	public List<UCompetencia> getCertificacionesSinPlanificar(Integer entidadId);
	public Map<Long, Item> getCatalogoUnidades();
	public Map<Long, String> getUnidadesByEstructuraId(Integer estructura); 
	public Usuario getUsuario(String username);
	public List<Contacto> getContactosInatec();
	public Contacto generarContacto(String usuario);	
	public List<Ifp> getIfpInatec(Integer entidadId);
	public List<Departamento> getDepartamentosInatec();
	public List<Municipio> getMunicipioByDeptoInatec(Integer idDepto);
	public List<Requisito> getRequisitos(int cursoId, int centroId);
	public Integer getUnidadesSinEvaluacion (Integer idSolicitud);
}