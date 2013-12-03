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
		//String methodName = signature.getName();
		String stuff = signature.toString();
		String arguments = Arrays.toString(joinPoint.getArgs());
		
		email.createAndSendEmailResponsible("SCCL - Excepci�n en tiempo de ejecuci�n", "Clase: " + stuff + "\n\n Argumentos: " + arguments + "\n\n Mensaje de error: "+e.getMessage());
		
		logger.info("Se captur� una excepci�n en el m�todo: "
				+ stuff + " con los siguientes argumentos "
				+ arguments + "\nla descripci�n de la excepci�n es: "
				+ e.getMessage(), e);
	}
}