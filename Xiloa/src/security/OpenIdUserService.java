/*package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;

import service.IService;

@Service
public class OpenIdUserService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	@Autowired
	private IService service;
	
	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
		String openId = token.getIdentityUrl();
		//buscar en base de datos local
		try
		{
			return service.loadUserByUsernameFromLocal(openId);
		}
		catch(UsernameNotFoundException e1){}
		
		service.RegistrarUsuarioOpenId(openId, getUserData(token, "firstname"), getUserData(token, "lastname"), getUserData(token, "email"), "visitante");
		return service.loadUserByUsernameFromLocal(openId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
}*/