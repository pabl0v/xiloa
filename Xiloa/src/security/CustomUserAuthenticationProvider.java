package security;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
			
			String password = (String) authentication.getCredentials();
			if (!password.equals(user.getPassword())) {
				throw new BadCredentialsException("Password incorrecto.");
			}
			
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
	
	@SuppressWarnings("unused")
	private String sha1(String input) throws NoSuchAlgorithmException {
		java.security.MessageDigest mDigest = java.security.MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }    
        return sb.toString();
    }
	
	public String md5(String md5) {
		try {
				java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
				byte[] array = md.digest(md5.getBytes());
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < array.length; ++i) {
					sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
				}
				return sb.toString();
			} catch (java.security.NoSuchAlgorithmException e) {
			}
			return null;
	}
}