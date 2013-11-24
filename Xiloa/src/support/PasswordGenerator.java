package support;

import java.util.Random;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PasswordGenerator {

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();
	static ShaPasswordEncoder encoder = new ShaPasswordEncoder();
	
	public static String randomPassword( int len )
	{
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}
	
	public static String encodedPassword(String password){
		return encoder.encodePassword(password, null);
	}
}