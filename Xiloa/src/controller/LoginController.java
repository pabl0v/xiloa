package controller;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import model.Contacto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;

import service.IService;

/*
 * @author Denis Chavez
 * @version 1.0
 * 
 * Esta clase se encarga de gestionar -del lado de la interfaz- la autenticacion de usuarios por las tres vias: usuario con cuenta inatec, usuario con cuenta local y usuario con cuenta openid
 * Su ambito es session y mantiene entre sus atributos la instancia del contacto autenticado
 * 
 */

@Controller
@Scope("session")
public class LoginController implements PhaseListener {

	private static final long serialVersionUID = 1L;
	
	protected final Log logger = LogFactory.getLog(getClass());
	private String username;
	private String password;
	private boolean inatec = false;
	private Contacto contacto = null;
	
	@Autowired
	private IService service;
	
	/**
	 * 
	 * @return retorna el contacto del usuario conectado
	 */
		
	public Contacto getContacto(){
		if(contacto == null){
			contacto = service.getContactoByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		return contacto;
	}
	
	/**
	 * 
	 * @return retorna el nombre de usuario del usuario conectado
	 */
	
	public String getLoggedUser(){
		getContacto();
		if(contacto.isInatec() == true)
			return contacto.getUsuarioInatec();
		else
			return contacto.getUsuario().getUsuarioAlias();
	}
	
	/**
	 * 
	 * @return retorna la entidad a la que pertenece el usuario conectado
	 */
	
	public Integer getEntidadUsuario(){
		getContacto();
		return contacto.getEntidadId();
	}
	
	/**
	 * Este metodo delega a Spring Security la autenticacion de usuario y contraseña proporcionados en la interfaz de usuario
	 * 
	 * @return null
	 * @throws ServletException
	 * @throws IOException
	 */
	
	public String doLogin() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
		return null;
	}
	
	/**
	 * Este metodo delega a Spring Security la autenticacion de usuario via openId proporcionados en la interfaz de usuario
	 * 
	 * @return null
	 * @throws ServletException
	 * @throws IOException
	 */
	
	public String openIdAuth() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_openid_security_check");
		dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse)context.getResponse());
	    FacesContext.getCurrentInstance().responseComplete();
	    return null;
	}
	
	/**
	 * Este metodo delega a Spring Security el cierre de la sesion actual
	 * 
	 * @return null
	 * @throws ServletException
	 * @throws IOException
	 */
	
	public void logout() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_logout");
		dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse) context.getResponse());
    	SecurityContextHolder.getContext().setAuthentication(null);
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	/**
	 * 
	 * Este metido se utilizaria para mostrar en la interfaz de usuario los mensajes de error de autenticacion generados por Spring Security
	 * 
	 * @param update valor booleano true false
	 * @throws Exception
	 */
	
	public void updateMessages(boolean update) throws Exception {
		Exception ex = (Exception)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (ex != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
			ex = null;
		}
	}
	
	/**
	 * Este metido es parte de la interfaz PhaseListener para visualizar los mensajes de error en la interfaz
	 */
	
	public void afterPhase(PhaseEvent event) {
	}

	/**
	 * Este metido es parte de la interfaz PhaseListener para visualizar los mensajes de error en la interfaz
	 */
	
	public void beforePhase(PhaseEvent event) {
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
 
        if (e instanceof Exception) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Autenticacion fallida: " + e.getMessage(), null));
        }
	}
	
	/**
	 * Este metido es parte de la interfaz PhaseListener para visualizar los mensajes de error en la interfaz
	 */

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
	
	/*
	 * seccion getters y setters 
	 */
	
	public String getUsername(){
		return username;
	}
	 
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	 	 
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getInatec() {
		return inatec;
	}

	public void setInatec(boolean inatec) {
		this.inatec = inatec;
	}
}