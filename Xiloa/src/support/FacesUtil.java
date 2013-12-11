package support;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    public static FacesContext getCurrentInstance(){
    	return FacesContext.getCurrentInstance();
    }
	
    public static ExternalContext getExternalContext() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getExternalContext();
    }

    public static String getContentPath(){
    	return getHttpServletRequest().getContextPath();    	
    }
    
    public static HttpServletRequest getHttpServletRequest() {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
    }
    
    protected static String getMensajeOriginal(Throwable e){
        Throwable t = getCausaInicial(e);
        return t.getMessage();
    }
    
    public static Throwable getCausaInicial(Throwable e){
        Throwable temp;
        temp = e;
        int i = 0;
        while(temp.getCause()!=null){
            temp = temp.getCause();
            System.out.println("Error" + (i++) + ":" + temp.getMessage());
        }
        return temp;
    }
    
    public static ServletContext getServletContext() {
        return (ServletContext) getExternalContext().getContext();
    }
   
    public static String getRealPath(String nombreFile) { 	  
 	   return FacesUtil.getServletContext().getRealPath(nombreFile);
 	}
    
    public static HttpSession getHttpSession(boolean create) {
        return (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(create);
    }
    
    public static Object getManagedBean(String beanName,Object scope) {
    	if (scope instanceof HttpServletRequest) {
    		return getExternalContext().getRequestMap().get(beanName);
    	}
    	if (scope instanceof HttpSession) {
    		return getExternalContext().getSessionMap().get(beanName);
    	}
    	return null;      
    }

   
    public static void setManagedBeanInSession(String beanName, Object managedBean) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(beanName, managedBean);
    }
    
    public static void setManagedBeanInRequest(String beanName, Object managedBean) {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(beanName, managedBean);
    }
    
    public static Object getRequestParameter(String name) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }
    
   
    public static Object getSessionParameter(String name){
    	return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
    }
  
    public static void addInfoMessage(String msg) {
        addInfoMessage(null, msg);
    }
    
    public static void addInfoMessage(Exception e){
    	addInfoMessage(null,getMensajeOriginal(e));
    }

    public static void addInfoMessage(String clientId, String msg) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
    }

         
    public static void messageFaces(String msg) {    	
    	FacesMessage fm = new FacesMessage(msg);
        FacesContext.getCurrentInstance().addMessage(msg, fm);        
    }
       
    public static Object getUIParameter(List list, String name){
    	for (Object obj : list){
    		if (obj instanceof UIParameter){
    			UIParameter param = (UIParameter)obj;
    			if (name.toUpperCase().equals(param.getName().toUpperCase())){
    				return param.getValue();
    			}
    		}
    	}
    	return null;
    }

    private static Application getApplication() {
        ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        return appFactory.getApplication();
    }

    private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }
    
    public static String getSystemProperties(String variable){
    	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("systemMGD");
    	return resource .getString(variable);    	 
    }
    
    public static FacesContext getFacesContext(){
    	return FacesContext.getCurrentInstance();
    }
    
    public static HttpServletResponse getHttpServletResponse(){
    	  return ((HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse());
    	
    }   
    public static void setRequestAttribute(String variable,Object object){
    	  FacesUtil.getHttpServletRequest().setAttribute(variable,object);
    }
    
    public static Object getRequestAttribute(String variable){
    	return FacesUtil.getHttpServletRequest().getAttribute(variable);
    }    
    
    public static void setSessionAttribute(String variable,Object object){
  	  FacesUtil.getHttpSession(false).setAttribute(variable,object);
    }
    
    public static Object getSessionAttribute(String variable){
    	return FacesUtil.getHttpSession(false).getAttribute(variable);
    }
    
    public static void displayMessage(String id,String sumary,String cadena,String severidad) {
    	  FacesMessage message = new FacesMessage();
    	  if ("I".equals(severidad)) {
    		  message.setSeverity(FacesMessage.SEVERITY_INFO);
    	  }
    	  if ("E".equals(severidad)) {
    		  message.setSeverity(FacesMessage.SEVERITY_ERROR);
    	  } 
    	  if ("F".equals(severidad)) {
    		  message.setSeverity(FacesMessage.SEVERITY_FATAL);
    	  }
    	  if ("W".equals(severidad)) {
    		  message.setSeverity(FacesMessage.SEVERITY_WARN);
    	  }
    	  if (sumary.isEmpty()) {
    		  message.setSummary(sumary);  
    	  }
    	  FacesUtil.getCurrentInstance().addMessage(id,message);    	    
    }
    
    public static Flash getScopeFlash() {    
    	return FacesContext.getCurrentInstance().getExternalContext().getFlash();
    }
    
    public static void setFlashValue(String name,Object value) {
    	getScopeFlash().put(name,value);
    }
    
    public static Object getFlashValue(String name) {
    	return getScopeFlash().get(name);
    }
    public static UIComponent findComponent(UIComponent c, String id) {
        if (id.equals(c.getId())) {
          return c;
        }
        Iterator<UIComponent> kids = c.getFacetsAndChildren();
        while (kids.hasNext()) {
          UIComponent found = findComponent(kids.next(), id);
          if (found != null) {
            return found;
          }
        }
        return null;
    }
       
    public static String getIdSession(){
    	return getHttpSession(false).getId();
    }
    
    public static String getLanguaje(){
    	Application application = FacesUtil.getApplication();
    	FacesContext facesContext = FacesUtil.getFacesContext();
        ValueExpression ve = application.getExpressionFactory()
        					.createValueExpression(facesContext.getELContext(),
                                                "#{language.locale}", Locale.class);
        Locale localeToSet = (Locale) ve.getValue(facesContext.getELContext());
        System.out.println(localeToSet.getLanguage());
        return localeToSet.getLanguage();
    }

}