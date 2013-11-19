package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import model.Certificacion;
import model.Contacto;
import model.Mantenedor;
import model.Solicitud;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import service.IService;

@Component
@Scope(value="request")
public class InicioManagedBean implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IService service;
	private List<Certificacion> certificaciones;
	private Certificacion selectedCertificacion;
	private int tipoFiltro;
	private String textoBuscar;

	public InicioManagedBean(){
		super();
		certificaciones = new ArrayList<Certificacion>();
	}
	
	@PostConstruct
	private void init(){
		certificaciones = service.getCertificacionesActivas(null,null);
		tipoFiltro = 0;
	}

	public List<Certificacion> getCertificaciones(){
		return certificaciones;
	}
	
	public Certificacion getSelectedCertificacion() {
		return selectedCertificacion;
	}

	public void setSelectedCertificacion(Certificacion selectedCertificacion) {
		this.selectedCertificacion = selectedCertificacion;
	}
	
	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

	public String indicaAplicar(){
		
		Usuario u = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());
		
		//Asigna valores que indican el Centro y la Certificacion que quieren aplicar
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("CertificacionSeleccionada",this.selectedCertificacion);
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute("UsuarioAplica",u);
		
		//Validando que no existan solicitudes Activas del usuario conectado
		Contacto contacto = u.getContacto();
		
		boolean solicitudActiva = false;
		
		String url;
		
		if (contacto != null) {
			Mantenedor estadoSol = service.getMantenedorMaxByTipo(new String("7"));
			Object [] objs =  new Object [] {contacto.getId()};
			
			List<Solicitud> listaSolicitudes = service.getSolicitudesByNQParam("Solicitud.findByIdContacto", objs);
			
			for (Solicitud dato : listaSolicitudes){
				if (!estadoSol.equals(dato.getEstatus())){
					solicitudActiva = true;
					break;
				}
			}			
		}
		
		if (solicitudActiva) {
			url = new String("/modulos/solicitudes/solicitudes?faces-redirect=true");
		} else {
			url = new String("/modulos/solicitudes/registro_solicitud_userExterno?faces-redirect=true");
		}
		
		return url;
	}
	
	public void buscar(int filtro, String texto){
		certificaciones = service.getCertificacionesActivas(filtro,texto);
		System.out.println("filtro: "+filtro);
		System.out.println("texto: "+texto);
		System.out.println("buscar..."+certificaciones.size());
	}
}