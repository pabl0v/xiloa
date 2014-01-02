package support;

import java.util.Random;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * @author Denis Chavez
 * 
 * Esta clase es usada para generar y regenerar la contraseña de las cuentas de usuario externo generadas desde la página de inicio de la aplicación
 */
public class PasswordGenerator {

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();
	static ShaPasswordEncoder encoder = new ShaPasswordEncoder();
	
	/**
	 * 
	 * @param len, longitud del password a generar
	 * @return password generado
	 */
	public static String randomPassword( int len )
	{
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}
	
	/**
	 * 
	 * @param password a codificar
	 * @return el password codificado en SHA1
	 */
	public static String encodedPassword(String password){
		return encoder.encodePassword(password, null);
	}
}