package service;

import model.Rol;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;

import dao.IDao;

@Service
public class RegisteringOpenIdUser implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
	
	@Autowired
	private UserDetailsService databaseAuthentication;
	@Autowired
	private IService service;
	@Autowired
	private IDao<Rol> rolDao; 

	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token) {
		String openId = token.getIdentityUrl();
		try
		{
			return databaseAuthentication.loadUserByUsername(openId);
		}
		catch(UsernameNotFoundException e) {}
		
		Usuario newUser = new Usuario();
		newUser.setId(10);
		newUser.setUsuarioAlias(openId);
		newUser.setUsuarioEstatus(true);
		newUser.setUsuarioPwd("");
		newUser.setRol(rolDao.findById(Rol.class, 1));
		service.RegistrarUsuario(newUser);
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