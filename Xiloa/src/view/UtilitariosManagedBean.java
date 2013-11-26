package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import model.Mantenedor;
import model.Rol;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import service.IService;
import support.Item;

@Component
@Scope(value="session")
public class UtilitariosManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IService servicio;
	
	private String usuario;
	private List<Mantenedor> mantenedores;
	private Map<Integer, Mantenedor> catalogoEstatusCertificacion;
	private Map<Integer, Mantenedor> catalogoTiposActividad;
	private Map<Integer, Mantenedor> catalogoEstatusActividad; 
	private Map<Integer, Mantenedor> catalogoTiposInstrumento;
	private Map<Integer, Mantenedor> catalogoTiposDatosLaborales;
	private Map<Long, Item> catalogoUnidades;
	private List<Usuario> usuarios;
	private List<Rol> roles;
	private Map<Integer, Mantenedor> catalogoGenero;

	public UtilitariosManagedBean(){
		super();
		mantenedores = new ArrayList<Mantenedor>();
		catalogoEstatusCertificacion = new HashMap<Integer, Mantenedor>();
		catalogoTiposActividad = new HashMap<Integer, Mantenedor>();
		catalogoEstatusActividad = new HashMap<Integer, Mantenedor>();
		catalogoTiposInstrumento = new HashMap<Integer, Mantenedor>();
		catalogoTiposDatosLaborales = new HashMap<Integer, Mantenedor>();
		catalogoUnidades = new HashMap<Long, Item>();
		usuarios = new ArrayList<Usuario>();
		roles = new ArrayList<Rol>();
		catalogoGenero = new HashMap<Integer, Mantenedor> ();
	}
	
	@PostConstruct
	public void init(){
		mantenedores = servicio.getMantenedores();
		usuarios = servicio.getUsuarios();
		roles = servicio.getRoles();
		
		for(int i=0; i<mantenedores.size(); i++){
			Mantenedor mantenedor = mantenedores.get(i);
			switch(mantenedor.getId()){
				case 1:
				case 2:
				case 3:
				case 4: catalogoTiposActividad.put(mantenedor.getId(), mantenedor); break;
				case 7:
				case 8:
				case 9: catalogoEstatusCertificacion.put(mantenedor.getId(), mantenedor); break;
				case 10:
				case 11:
				case 12: catalogoEstatusActividad.put(mantenedor.getId(), mantenedor); break;
				case 13:
				case 14:
				case 15:
				case 16: catalogoTiposDatosLaborales.put(mantenedor.getId(), mantenedor); break;
				case 17:
				case 18:
				case 19: catalogoTiposInstrumento.put(mantenedor.getId(), mantenedor); break;
			}
			
			if (Integer.valueOf(mantenedor.getTipo()) == new Integer(10))  {
				catalogoGenero.put(mantenedor.getId(), mantenedor);
			}
		}
		
		catalogoUnidades = servicio.getCatalogoUnidades();
		usuario = SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	public Map<Integer, Mantenedor> getCatalogoGenero() {
		return catalogoGenero;
	}
	
	public List<Mantenedor> getMantenedores(){
		return this.mantenedores;
	}
	
	public String getUsuario(){
		return usuario;
	}
	
	public Map<Integer, Mantenedor> getCatalogoEstatusCertificacion(){
		return catalogoEstatusCertificacion;
	}
	
	public Map<Integer, Mantenedor> getCatalogoTiposActividad(){
		return catalogoTiposActividad;
	}

	public Map<Integer, Mantenedor> getCatalogoEstatusActividad(){
		return catalogoEstatusActividad;
	}
	
	public Map<Integer, Mantenedor> getCatalogoTiposInstrumento(){
		return catalogoTiposInstrumento;
	}

	public Map<Integer, Mantenedor> getCatalogoTiposDatosLaborales(){
		return catalogoTiposDatosLaborales;
	}
	
	public Map<Long, Item> getCatalogoUnidades(){
		return catalogoUnidades;
	}
	
	public String getCompetenciaDescripcion(Long codigo){
		return catalogoUnidades.get(codigo).getDescripcion();
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public List<Rol> getRoles() {
		return roles;
	}
}