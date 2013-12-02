package security;

import model.Contacto;
import model.Usuario;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import service.IService;

@Component
public class CustomUserAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private IService service;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		CustomUsernamePasswordAuthenticationToken token = (CustomUsernamePasswordAuthenticationToken) authentication;
		boolean inatec = token.isInatec();
		Contacto contacto = null;
		Usuario usuario = null;
		
		if(inatec)
		{
			try
			{
				usuario = service.getUsuarioInatec(authentication.getName());
			}
			catch(UsernameNotFoundException e)
			{
				throw new BadCredentialsException("Usuario no existe.");
			}
			
			if(usuario == null)
				throw new BadCredentialsException("Usuario no existe.");
			
			String passwordHash = DigestUtils.md5Hex(authentication.getCredentials().toString());
			
			if (!passwordHash.equals(usuario.getUsuarioPwd())) {
				throw new BadCredentialsException("Contraseña incorrecta.");
			}
			
			if(usuario != null)
			{
				try
				{
					contacto = service.getContactoInatecByLogin(authentication.getName());
				}
				catch(Exception e)
				{
					throw new BadCredentialsException("No se pudo dar de alta al usuario.");
				}
				
				if(contacto == null)
				{
					try
					{
						contacto = (Contacto) service.guardar(service.generarNuevoContactoInatec(authentication.getName()));	
					}
					catch(Exception e)
					{
						throw new BadCredentialsException("No se pudo dar de alta al usuario.");
					}
				}
				else
				{
					try
					{
						Contacto actualizado = service.generarNuevoContactoInatec(authentication.getName());
						actualizado.setId(contacto.getId());
						actualizado.setFechaRegistro(contacto.getFechaRegistro());
						contacto = (Contacto) service.guardar(actualizado);
					}
					catch(Exception e)
					{
						throw new BadCredentialsException("No se pudo dar de alta al usuario.");
					}					
				}
			}

			return new UsernamePasswordAuthenticationToken(contacto.getUsuarioInatec(), null, service.getAuthoritiesInatecByLogin(authentication.getName()));
		}
		else
		{
			try
			{
				usuario = service.getUsuarioLocal(authentication.getName());
			}
			catch(UsernameNotFoundException e)
			{
				throw new BadCredentialsException("Usuario no existe.");
			}
			
			if(usuario == null)
				throw new BadCredentialsException("Usuario no existe.");
			
			String passwordHash = DigestUtils.shaHex(authentication.getCredentials().toString());
			
			if (!passwordHash.equals(usuario.getUsuarioPwd())) {
				throw new BadCredentialsException("Contraseña incorrecta.");
			}
			
			service.registrarAcceso(usuario);
			return new UsernamePasswordAuthenticationToken(usuario.getUsuarioAlias(), null, service.getAuthoritiesInatecByRolId(usuario.getRol().getIdRolInatec()));			
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomUsernamePasswordAuthenticationToken.class.equals(authentication);
	}	
}