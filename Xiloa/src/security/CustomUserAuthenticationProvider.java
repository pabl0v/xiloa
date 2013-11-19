package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private InatecUserService inatecUserService;
	@Autowired
	private LocalUserService localUserService;
	@Autowired
	private OpenIdUserService openIdUserService;
	private ShaPasswordEncoder shaEncoder = new ShaPasswordEncoder();
	private Md5PasswordEncoder md5Encoder = new Md5PasswordEncoder();

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		CustomUsernamePasswordAuthenticationToken token = (CustomUsernamePasswordAuthenticationToken) authentication;
		boolean inatec = token.isInatec();
		User user = null;
		
		if(inatec)
		{
			try
			{
				user = (User) inatecUserService.loadUserByUsername(authentication.getName());
			}
			catch(UsernameNotFoundException e)
			{
				throw new BadCredentialsException("Usuario no existe.");
			}
			
			String password = md5Encoder.encodePassword((String) authentication.getCredentials(),null);
			if (!password.equals(user.getPassword())) {
				throw new BadCredentialsException("Contraseña incorrecta.");
			}
			
			inatecUserService.registrarContacto();
			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		}
		else
		{
			try
			{
				user = (User) localUserService.loadUserByUsername(authentication.getName());
			}
			catch(UsernameNotFoundException e)
			{
				throw new BadCredentialsException("Usuario no existe.");
			}

			String password = shaEncoder.encodePassword((String) authentication.getCredentials(),null);
			if (!password.equals(user.getPassword())) {
				throw new BadCredentialsException("Contraseña incorrecta.");
			}
			
			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());			
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomUsernamePasswordAuthenticationToken.class.equals(authentication);
	}	
}