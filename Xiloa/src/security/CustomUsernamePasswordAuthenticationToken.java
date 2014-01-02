package security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 
 * @author Denis Chavez
 * 
 * Esta clase personaliza el token de autenticacion usuario/contraseña agregandole un elemento más
 * que indica si el usuario es inatec o local. Esto para permitir autenticación de usuarios en esquemas diferentes
 *
 */

public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken{
	
	private static final long serialVersionUID = 1L;

	private boolean inatec;
	
	/**
	 * @param principal, el login
	 * @param credentials, el password
	 * @param inatec, el indicador de tipo de usuario
	 * 
	 * Constructor por defecto
	 */

	public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, boolean inatec) {
		super(principal, credentials);
		this.inatec = inatec;
	}
	
	/**
	 * @param principal, el login
	 * @param credentials, el password
	 * 
	 * Constructor por defecto
	 */
	
	public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	
	/**
	 * 
	 * @return indicador de tipo de usuario
	 */
	public boolean isInatec(){
		return this.inatec;
	}
}