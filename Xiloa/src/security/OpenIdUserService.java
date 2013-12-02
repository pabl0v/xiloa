package security;

import model.Usuario;

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

@Service
public class OpenIdUserService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	@Autowired
	private IService service;

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
	
	private String getUserData(OpenIDAuthenticationToken token, String data) {
	    for (OpenIDAttribute attribute : token.getAttributes()) {
	        if (attribute.getName().equals(data)) {
	            return attribute.getValues().get(0);
	        }
	    }
	    return null;
	}
}