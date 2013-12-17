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
		
	public Contacto getContacto(){
		if(contacto == null){
			contacto = service.getContactoByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		return contacto;
	}
	
	public String getLoggedUser(){
		getContacto();
		if(contacto.isInatec() == true)
			return contacto.getUsuarioInatec();
		else
			return contacto.getUsuario().getUsuarioAlias();
	}
	
	public Integer getEntidadUsuario(){
		getContacto();
		return contacto.getEntidadId();
	}
	
	public String doLogin() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
		return null;
	}
	
	public String openIdAuth() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_openid_security_check");
		dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse)context.getResponse());
	    FacesContext.getCurrentInstance().responseComplete();
	    return null;
	}
	
	public void logout() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_logout");
		dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse) context.getResponse());
    	SecurityContextHolder.getContext().setAuthentication(null);
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void updateMessages(boolean update) throws Exception {
		Exception ex = (Exception)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (ex != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
			ex = null;
		}
	}
	
	public void afterPhase(PhaseEvent event) {
	}

	public void beforePhase(PhaseEvent event) {
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
 
        if (e instanceof Exception) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Autenticacion fallida: " + e.getMessage(), null));
        }
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
	
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