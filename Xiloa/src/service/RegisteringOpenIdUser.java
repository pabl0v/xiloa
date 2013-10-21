package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;

//@Service
public class RegisteringOpenIdUser implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
	
	//@Autowired
	private UserDetailsService databaseAuthentication;
	//@Autowired
	private IService service;

	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token) {
		String openId = token.getIdentityUrl();
		try
		{
			return databaseAuthentication.loadUserByUsername(openId);
		}
		catch(UsernameNotFoundException e) {}
		
		//buscar en base de datos local
		/*try
		{
			return service.loadUserByUsernameFromLocal(openId);
		}
		catch(UsernameNotFoundException e1){}*/

		//buscar en base de datos inatec
		/*try
		{
			return service.loadUserByUsernameFromInatec(openId);
		}
		catch(UsernameNotFoundException e2){}*/
		
		service.RegistrarUsuarioOpenId(openId, getUserData(token, "firstname"), getUserData(token, "lastname"), getUserData(token, "email"), "visitante");
		return databaseAuthentication.loadUserByUsername(openId);
	}

	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {
		return null;
	}
	
	private String getUserData(OpenIDAuthenticationToken token, String data) {
	    for (OpenIDAttribute attribute : token.getAttributes()) {
	        if (attribute.getName().equals("data")) {
	            return attribute.getValues().get(0);
	        }
	    }
	    return null;
	}
}