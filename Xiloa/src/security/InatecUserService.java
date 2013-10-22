package security;

import model.Contacto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
		User user = service.loadUserByUsernameFromInatec(username);
		if(user == null){
			throw new UsernameNotFoundException("Usuario no existe");
		}
		
		registrarContacto(user.getUsername());
		return user;
	}
		
	private void registrarContacto(String usuario){
		if(service.isNuevoContactoInatec(usuario)){
			Contacto contacto = service.generarNuevoContactoInatec(usuario);
			service.guardarContacto(contacto);			
		}
	}
}