package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import model.Certificacion;
import model.Requisito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.UsuarioExterno;

/**
 * 
 * @author Denis Chavez
 * 
 * JSF ManagedBean asociado a la interfaz  index.xhtml
 * Esta clase maneja lo que se muestra en la página, los eventos relacionados 
 * e interacciona con la capa de servicio (paquete service)
 */

@Component
@Scope(value="request")
public class InicioManagedBean implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IService service;
	private List<Certificacion> certificaciones;
	private Certificacion selectedCertificacion;
	private int tipoFiltro;
	private String textoBuscar;
	private UsuarioExterno usuarioExterno;
	private String usuarioReset;

	public InicioManagedBean(){
		super();
		certificaciones = new ArrayList<Certificacion>();
		usuarioExterno = new UsuarioExterno();
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
	
	public List<Requisito> getRequisitos(Long certificacionId){
		return service.getRequisitos(certificacionId);
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

	/*
	public String indicaAplicar(){
		
		Usuario u = service.getUsuarioLocal(SecurityContextHolder.getContext().getAuthentication().getName());
		
		//Asigna valores que indican el Centro y la Certificacion que quieren aplicar
		FacesUtil.setParamBySession("CertificacionSeleccionada", this.selectedCertificacion);
		FacesUtil.setParamBySession("UsuarioAplica", u);	
				
		//Validando que no existan solicitudes Activas del usuario conectado
		Contacto contacto = u.getContacto();
		
		boolean solicitudActiva = false;
		
		String url;
		
		if (contacto != null) {
			//Mantenedor estadoSol = service.getMantenedorMaxByTipo(new String("7"));
						
			Object [] objs =  new Object [] {contacto.getId()};
			
			List<Solicitud> listaSolicitudes = service.getSolicitudesByNQParam("Solicitud.findByIdContacto", objs);
			
			for (Solicitud dato : listaSolicitudes){
				
				//dchavez 15/03/2014. considerar pendiente todo lo que no esta concluido o anulado. No permitir una nueva solicitud sobre un curso concluido.
				
				if(dato.getCertificacion().getId() == selectedCertificacion.getId() && dato.getEstatus().getId() != 40){
					solicitudActiva = true;
					break;
				}
					
				if (dato.getEstatus().getId() != 37 && dato.getEstatus().getId() != 40){
					solicitudActiva = true;
					break;
				}
			}
			
			
			//for (Solicitud dato : listaSolicitudes){
				//if (!estadoSol.equals(dato.getEstatus())){
					//solicitudActiva = true;
					//break;
				//}
			//}
		}
		
		if (solicitudActiva) {
			url = new String("/modulos/solicitudes/solicitudes?faces-redirect=true");
		} else {
			url = new String("/modulos/solicitudes/registro_solicitud_userExterno?faces-redirect=true");
		}
		
		return url;
	}
	*/
	
	public void buscar(int filtro, String texto){
		certificaciones = service.getCertificacionesActivas(filtro,texto);
	}
	
	public UsuarioExterno getUsuarioExterno() {
		return usuarioExterno;
	}

	public void setUsuarioExterno(UsuarioExterno nuevoUsuario) {
		this.usuarioExterno = nuevoUsuario;
	}

	public String getUsuarioReset() {
		return usuarioReset;
	}

	public void setUsuarioReset(String usuarioReset) {
		this.usuarioReset = usuarioReset;
	}

	public void registrarUsuarioExterno(UsuarioExterno usuario){
		this.usuarioExterno = usuario;
		FacesContext fContext = FacesContext.getCurrentInstance();
		
		if(!usuario.getEmail1().equals(usuario.getEmail2()))
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Creación de usuario fallida: La dirección de correo y su confirmación no coinciden", null);
			fContext.addMessage(null, message);
			return;			
		}
		
		if(service.existeUsuario(usuario.getUsuario()))
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Creación de usuario fallida: El usuario ya existe", null);
			fContext.addMessage(null, message);
			return;			
		}
		
		try
		{
			service.registrarUsuarioExterno(usuarioExterno);
		}
		catch(Exception excepcion)
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Creación de usuario fallida: "+excepcion.getMessage(), null);
			fContext.addMessage(null, message);
			return;
		}
		
		this.usuarioExterno = new UsuarioExterno();
		FacesMessage message = new FacesMessage("Usuario creado exitosamente");
		fContext.addMessage(null, message);
	}
	
	public void resetearPassword(String usuario){
		this.usuarioReset = usuario;
		FacesContext fContext = FacesContext.getCurrentInstance();
		try
		{
			service.resetPassword(usuario);
		}
		catch(Exception excepcion)
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Generación de contraseña fallida: "+excepcion.getMessage(), null);
			fContext.addMessage(null, message);
			return;
		}
		this.usuarioReset = null;
		FacesMessage message = new FacesMessage("Contraseña generada exitosamente");
		fContext.addMessage(null, message);
	}
}