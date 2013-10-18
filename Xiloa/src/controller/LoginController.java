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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;

import service.IService;

//@ManagedBean(name="loginController")
//@RequestScoped
@Controller
@Scope("request")
public class LoginController implements PhaseListener {

	private static final long serialVersionUID = -92971891224906450L;
	protected final Log logger = LogFactory.getLog(getClass());
	private String username;
	private String password;
	//@ManagedProperty(value="#{authenticationManager}")
	@Autowired
	private AuthenticationManager authenticationManager;
	
    public AuthenticationManager getAuthenticationManager() {
    	return authenticationManager;
    }
 
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    	this.authenticationManager = authenticationManager;
    }
	
	public String login(){
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		try{
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContext sContext = SecurityContextHolder.getContext();
			sContext.setAuthentication(authentication);
			return "/modulos/planificacion/planificacion?faces-redirect=true";
		} catch(AuthenticationException loginError){
			@SuppressWarnings("unused")
			FacesContext fContext = FacesContext.getCurrentInstance();
			@SuppressWarnings("unused")
			FacesMessage message = new FacesMessage("Invalid username/password. Reason " + loginError.getMessage());
			return "index";
		}
	}
	
	/**
	 *
	 * Redirects the login request directly to spring security check.
	 * Leave this method as it is to properly support spring security.
	 * 
	 * @return
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
	
	public String openIdAuth() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_openid_security_check");
		dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse)context.getResponse());
	    FacesContext.getCurrentInstance().responseComplete();	    
	    return null;
	}
	
	public void logout() throws ServletException, IOException {
    	//SecurityContextHolder.getContext().setAuthentication(null);
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_logout");
		dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void afterPhase(PhaseEvent event) {
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 * 
	 * Do something before rendering phase.
	 */
	public void beforePhase(PhaseEvent event) {
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(
				WebAttributes.AUTHENTICATION_EXCEPTION);
 
        if (e instanceof BadCredentialsException) {
        	logger.debug("Found exception in session map: "+e);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
            		WebAttributes.AUTHENTICATION_EXCEPTION, null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Username or password not valid.", "Username or password not valid"));
        }
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 * 
	 * In which phase you want to interfere?
	 */
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	public String getUsername() {
		return username;
	}
	 
	public void setUsername(String username) {
		this.username = username;
	}
	 
	public String getPassword() {
		return password;
	}
	 
	public void setPassword(String password) {
		this.password = password;
	}
}