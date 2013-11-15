package support;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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
}