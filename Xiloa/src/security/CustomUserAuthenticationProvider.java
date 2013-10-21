package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class CustomUserAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private InatecUserService inatecUserService;
	@Autowired
	private LocalUserService localUserService;
	@Autowired
	private OpenIdUserService openIdUserService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		CustomUsernamePasswordAuthenticationToken token = (CustomUsernamePasswordAuthenticationToken) authentication;
		boolean inatec = token.isInatec();
		User user = null;
		
		if(inatec)
		{
			user = (User) inatecUserService.loadUserByUsername(authentication.getName());
			
			if(user == null){
				throw new BadCredentialsException("Usuario incorrecto.");
			}

			String password = (String) authentication.getCredentials();
			if (!password.equals(user.getPassword())) {
				throw new BadCredentialsException("Password incorrecto.");
			}
			
			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		}
		else
		{
			user = (User) localUserService.loadUserByUsername(authentication.getName());
			
			if(user == null){
				throw new BadCredentialsException("Usuario incorrecto.");
			}

			String password = (String) authentication.getCredentials();
			if (!password.equals(user.getPassword())) {
				throw new BadCredentialsException("Password incorrecto.");
			}
			
			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());			
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomUsernamePasswordAuthenticationToken.class.equals(authentication);
	}
}