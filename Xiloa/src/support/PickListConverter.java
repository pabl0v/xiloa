package support;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Contacto;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter(forClass=Contacto.class, value="pickListConverter")
public class PickListConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		PickList p = (PickList) component;
		@SuppressWarnings("unchecked")
		DualListModel<Contacto> dl = (DualListModel<Contacto>) p.getValue();
    		for (int i = 0; i < dl.getSource().size(); i++) {
    			if (dl.getSource().get(i).toString().contentEquals(submittedValue)) {
    				return dl.getSource().get(i);
    			}
    		}
    		for (int i = 0; i < dl.getTarget().size(); i++) {
    			if (dl.getTarget().get(i).toString().contentEquals(submittedValue)) {
    				return dl.getTarget().get(i);
    			}
    		}
    		return null;
		}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		//PickList p = (PickList) component;
		//DualListModel<Contacto> dl = (DualListModel<Contacto>) p.getValue();
		//return String.valueOf(dl.getSource().indexOf(value));
		return value.toString();
	}
}