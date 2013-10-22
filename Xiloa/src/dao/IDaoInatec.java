package dao;

import java.util.List;

import model.Contacto;
import model.Usuario;
import support.UCompetencia;

public interface IDaoInatec {

	public void agregarRol();
	public List<UCompetencia> getCertificacionesSinPlanificar();
	public Usuario getUsuario(String username);
	public List<Contacto> getContactosInatec();
	public Contacto getContacto(Usuario usuario);
}