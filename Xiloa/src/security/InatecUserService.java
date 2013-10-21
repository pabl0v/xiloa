/*package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import service.IService;

@Service
public class InatecUserService implements UserDetailsService {

	@Autowired
	private IService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		//buscar en base de datos local
		try
		{
			return service.loadUserByUsernameFromInatec(username);
		}
		catch(UsernameNotFoundException e1){}

		return null;
	}	
}*/