/*
package security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import service.IService;

public class SuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private IService service;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    	//response.sendRedirect(request.getContextPath() + "/modulos/compartido/inicio.xhtml");

    	Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        
        //busca al usuario en bd local, si no lo encuentra lo busca en bd inatec
        
        Usuario usuario = service.getUsuarioLocal(authentication.getName());
        
        if(usuario == null)
        	usuario = service.getUsuarioInatec(authentication.getName());
                 
        //si el rol del usuario es visitante, lo redirecciona a la pagina de visitante, en otro caso valida si tiene permiso para mandarlo a solicitudes o a pagina de advertencia
 
        if(usuario.getRol().getId() == 6)
        	response.sendRedirect(request.getContextPath() + "/modulos/usuario/visitante.xhtml");
        else{
        	
        	if(!roles.isEmpty()){
        		response.sendRedirect(request.getContextPath() + "/modulos/solicitudes/solicitudes.xhtml");
        		return;
        	}
        	else
        		response.sendRedirect(request.getContextPath() + "/modulos/usuario/sinPermiso.xhtml");
        }
    }
}*/