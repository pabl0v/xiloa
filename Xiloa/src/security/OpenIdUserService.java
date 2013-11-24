package security;

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
	private LocalUserService localUserService;
	@Autowired
	private IService service;

	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token) {
		String email = getUserData(token, "email");
		try
		{
			return localUserService.loadUserByUsername(email);
		}
		catch(UsernameNotFoundException e){}
		
		service.RegistrarUsuarioOpenId(email, getUserData(token, "firstname"), getUserData(token, "lastname"), email);
		return localUserService.loadUserByUsername(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
	
	private String getUserData(OpenIDAuthenticationToken token, String data) {
	    for (OpenIDAttribute attribute : token.getAttributes()) {
	        if (attribute.getName().equals(data)) {
	            return attribute.getValues().get(0);
	        }
	    }
	    return null;
	}
}