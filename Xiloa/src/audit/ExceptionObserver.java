package audit;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import support.JavaEmailSender;

/**
 * 
 * @author Denis Chavez
 * @version 1.0
 *
 * Esta clase utiliza la programacion orientada a aspectos de Spring (ASP Programming)
 * para captar las excepciones que se producen en tiepo de ejecucion dentro del paquete service.
 * Una vez captada la excepcion, el metododo myAfterThrowing envia por email los detalles de la excepcion
 */

@Aspect
@Component
public class ExceptionObserver {
	
	@Autowired
	private JavaEmailSender email;
	private static Log logger = LogFactory.getLog(ExceptionObserver.class);

	/*
	 * Captura la excepciones del servicio y las envia por correo electronico al administrador indicado en la tabla mantenedores
	 */
	
	@AfterThrowing(pointcut = "execution(* service.*.*(..))", throwing = "e")
	public void myAfterThrowing(JoinPoint joinPoint, Throwable e) {

		Signature signature = joinPoint.getSignature();
		String stuff = signature.toString();
		String arguments = Arrays.toString(joinPoint.getArgs());
		
		email.createAndSendEmailResponsible("SCCL - Excepción en tiempo de ejecución", "Clase: " + stuff + "\n\n Argumentos: " + arguments + "\n\n Mensaje de error: "+e.getMessage());
		
		logger.info("Se capturó una excepción en el método: "
				+ stuff + " con los siguientes argumentos "
				+ arguments + "\nla descripción de la excepción es: "
				+ e.getMessage(), e);
	}
}