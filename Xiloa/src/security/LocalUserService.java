package security;

import java.util.Collection;

import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import service.IService;

@Service
public class LocalUserService implements UserDetailsService {
	
	@Autowired
	private IService service;
	private Usuario usuario;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		usuario = service.getUsuarioLocal(login);
		usuario = service.registrarAcceso(usuario);
		if(usuario == null)
			throw new UsernameNotFoundException("Usuario no existe.");
		else
		{
			Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(usuario.getRol().getNombre());
			return new User(usuario.getUsuarioAlias(),usuario.getUsuarioPwd(),authorities);
		}
	}
	
	public Usuario getUsuario(){
		return usuario;
	}
}