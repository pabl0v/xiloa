package security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
	 
	private static final long serialVersionUID = 1L;
	private String authority;
 
	public Authority(String authority) {
    	this.authority = authority;
    }
 
    @Override
    public String getAuthority() {
    	return authority;
    }
 
    @Override
    public int hashCode() {
    	return authority.hashCode();
    }
 
    @Override
    public boolean equals(Object obj) {
    	if(obj == null) return false;
    	if(!(obj instanceof Authority)) return false;
    	return ((Authority) obj).getAuthority().equals(authority);
    }
}