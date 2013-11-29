package support;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

public class FacesUtil {
	
	public static String getActionAttribute(ActionEvent event, String name) {
		return (String) event.getComponent().getAttributes().get(name);
	}

	public static Object getSessionMapValue(String key) {
    	return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public static void setSessionMapValue(String key, Object value) {
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }
    
    public static Object getApplicationMapValue(String key) {
    	return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(key);
    }
    
    public static void setApplicationMapValue(String key, Object value) {
    	FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(key, value);
    }
    
    public static void getMensaje(String titulo, String texto, boolean isError){
    	FacesContext context = FacesContext.getCurrentInstance();
    	FacesMessage msg = null;
    	
		if (isError == true)
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, texto);
		else
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, texto);
    	
		context.addMessage(null, msg);
    }
    
    public static void setParamBySession(String nombre, Object valor){		
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).setAttribute(nombre,valor);		
	}
	
	public static Object getParametroSession(String nombre){
		return ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false).getAttribute(nombre);
	}
}