package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import service.IService;

@Service
public class LocalUserService implements UserDetailsService {
	
	@Autowired
	private IService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		return service.loadUserByUsernameFromLocal(username);
	}
}