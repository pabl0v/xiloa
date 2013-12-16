/*
package audit;

import java.util.Date;

import model.Auditoria;
import model.Contacto;
import model.Guia;
import model.Instrumento;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import service.IService;

@Aspect
@Component
public class AuditObserver {
	
	@Autowired
	private IService service;
		
	@After("execution(* service.*.guardar(..)) and args(guia)")
	public void registrarAuditoria(Guia guia){
		Contacto contacto = service.getContactoByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		service.auditar(new Auditoria(new Date(), contacto, guia.toString()));
	}
	
	@After("execution(* service.*.guardar(..)) and args(instrumento)")
	public void registrarAuditoria(Instrumento instrumento){
		Contacto contacto = service.getContactoByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		service.auditar(new Auditoria(new Date(), contacto, instrumento.toString()));
	}
}*/