package support;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * 
 * @author Denis Chavez, Miriam Martinez
 * 
 * Esta clase implementa el ámbito ViewScope propio de Java Server Faces, ya que este ámbito es inexistente en Spring
 * El ámbito ViewScope mantiene viva la instancia de clase asociada a una página xhtml mientras la página sea visible al usuario
 * Esta implementación fue obtenida del sitio StackOverflow.com
 *
 */
public class ViewScope implements Scope {
	
	public Object get(String name, ObjectFactory<?> objectFactory) {
		if (FacesContext.getCurrentInstance().getViewRoot() != null) {
			Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
			if (viewMap.containsKey(name)) {
				return viewMap.get(name);
			} else {
				Object object = objectFactory.getObject();
				viewMap.put(name, object);
                return object;
			}
		} else {
			return null;
		}
    }

	public Object remove(String name) {
	if (FacesContext.getCurrentInstance().getViewRoot() != null) {
            return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
        } else {
			return null;
        }
	}

    public void registerDestructionCallback(String name, Runnable callback) {
 	}

	public Object resolveContextualObject(String key) {
        return null;
    }

	public String getConversationId() {
        return null;
	}
}