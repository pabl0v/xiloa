package security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 
 * @author Denis Chavez
 * 
 * Esta clase implementa la interface de Spring Security UsernamePasswordAuthenticationFilter. 
 * El filtro captura el request y lo redirecciona a la clase encargada de la autenticación
 *
 */

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	/**
	 * @param request
	 * @param response
	 * @return El resultado de la autenticación
	 */
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		if(!request.getMethod().equals("POST")){
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		boolean inatec = request.getParameter("inatec")==null?false:true;
		
		CustomUsernamePasswordAuthenticationToken authRequest =  new CustomUsernamePasswordAuthenticationToken(username, password, inatec);
		setDetails(request, authRequest);
		
		return this.getAuthenticationManager().authenticate(authRequest);			
	}	
}