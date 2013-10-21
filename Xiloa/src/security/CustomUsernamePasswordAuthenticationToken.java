package security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken{
	
	private static final long serialVersionUID = 1L;

	private boolean inatec;

	public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, boolean inatec) {
		super(principal, credentials);
		this.inatec = inatec;
	}	
	
	public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public boolean isInatec(){
		return this.inatec;
	}
}