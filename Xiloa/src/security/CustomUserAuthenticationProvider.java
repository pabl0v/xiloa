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

/**
 * 
 * @author Denis Chavez
 * 
 * Esta clase implementa la interface AuthenticationProvider de Spring Security
 * implementa el método de autenticación vía usuario y contraseña contra la base de datos local e inatec
 * según el método de autenticación seleccionado por el usuario
 *
 */

@Component
public class CustomUserAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private IService service;

	/**
	 * @param el token conteniendo el usuario, la contraseña y el tipo de usuario (inatec o local)
	 * @return la instancia con el resultado de la autenticación (éxito o fallo) y los permisos del usuario 
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		CustomUsernamePasswordAuthenticationToken token = (CustomUsernamePasswordAuthenticationToken) authentication;
		boolean inatec = token.isInatec();
		Contacto contacto = null;
		Usuario usuario = null;
		
		/**
		 * si el usuario es inatec, se autentica contra el esquema de seguridad existente en el inatec
		 */
		
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
				throw new BadCredentialsException("Password incorrecto.");
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

			return new UsernamePasswordAuthenticationToken(authentication.getName(), null, service.getAuthoritiesInatecByLogin(authentication.getName()));
		}
		else		//si el usuario no es inatec, se autentica contra el esquema local de la aplicación : sccl
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
				throw new BadCredentialsException("Password incorrecto.");
			}
			
			service.registrarAcceso(usuario);
			return new UsernamePasswordAuthenticationToken(usuario.getUsuarioAlias(), null, service.getAuthoritiesInatecByRolId(usuario.getRol().getIdRolInatec()));
		}
	}

	/**
	 * @param un token cualquiera de autenticación
	 * @return indica si la clase actual soporta la autenticación del token pasado como parámetro 
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return CustomUsernamePasswordAuthenticationToken.class.equals(authentication);
	}
}