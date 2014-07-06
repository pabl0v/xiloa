package security;

import model.Usuario;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;

import service.IService;

/**
 * 
 * @author Denis Chavez
 * 
 * Esta clase ayuda en la autenticaci�n v�a OpenId. 
 * Si la autenticaci�n fue exitosa, obtiene el email y el nombre del usuairo y le crea una cuenta en la base de datos local
 *
 */

@Service
public class OpenIdUserService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	@Autowired
	private IService service;
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * @param el token de autenticaci�n de OpenId
	 * @return una instancia con el usuario autenticado
	 */
	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token) {
		String email = getUserData(token, "email");
		
		Usuario usuario = service.getUsuarioLocal(email);
		
		if(usuario == null)
		{
			service.RegistrarUsuarioOpenId(email, getUserData(token, "firstname"), getUserData(token, "lastname"), email);
			usuario = service.getUsuarioLocal(email);
		}
		
		service.registrarAcceso(usuario);
		return new User(usuario.getUsuarioAlias(), "", service.getAuthoritiesInatecByRolId(usuario.getRol().getIdRolInatec()));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
	
	/**
	 * 
	 * @param token, el token de autenticaci�n de donde se extraer� la info indicada en el par�metro "data"
	 * @param data, la informaci�n a extraer del token de autenticaci�n
	 * @return El dato solicitado
	 */
	private String getUserData(OpenIDAuthenticationToken token, String data) {
	    for (OpenIDAttribute attribute : token.getAttributes()) {
	        if (attribute.getName().equals(data)) {
	            return attribute.getValues().get(0);
	        }
	    }
	    return null;
	}
}