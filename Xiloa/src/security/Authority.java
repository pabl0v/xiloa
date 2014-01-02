package security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author Denis Chavez
 * @version 1.0
 * 
 * Esta clase, que implementa la interface GrantedAuthority representa un permiso dentro Spring Security.
 * Se utiliza para manejar los permisos de los roles y usuarios.
 *
 */

public class Authority implements GrantedAuthority {
	 
	private static final long serialVersionUID = 1L;
	private String authority;
	
	/**
	 * Constructor por defecto
	 * @param authority El permiso a otorgar
	 */
 
	public Authority(String authority) {
    	this.authority = authority;
    }
	
	/**
	 * @return El permiso otorgado
	 */
 
    @Override
    public String getAuthority() {
    	return authority;
    }
    
    /**
     * @return el hash del permiso
     */
 
    @Override
    public int hashCode() {
    	return authority.hashCode();
    }
    
    /**
     * @return indica si la instancia pasada como parametro tiene el mismo permiso que la instancia actual
     * @param una instancia de Authority
     */
 
    @Override
    public boolean equals(Object obj) {
    	if(obj == null) return false;
    	if(!(obj instanceof Authority)) return false;
    	return ((Authority) obj).getAuthority().equals(authority);
    }
}