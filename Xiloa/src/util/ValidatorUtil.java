package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
	
	private static final String PATTERN_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PATTERN_CEDULA = "^[\\d]{13}[A-Za-z]{1}$";
	private static final String [] letras = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"};
	
	public static boolean validateEmail(String email) {
		 
        // Compila la expresion regular en el patron.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        // Compara con el patron
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }
	
	public static boolean validateCedula(String cedula){
		String cedulaSinGuion = null;
		boolean formato = false;
		String [] partes  = null;
		Integer num_ced = null;
		String caracter = null;
		Double aux = null;
	    Integer temp = null;
	    Integer digito = null;
		
		Pattern pat = null;
		Matcher mat = null;
		
		if (cedula.length() != 16)
			formato = false;
		else {
			partes = cedula.split("[-]");
			cedulaSinGuion = "";
			
			for(int i = 0; i<partes.length; i++){
				cedulaSinGuion = cedulaSinGuion + partes[i];		       
		     }
						
			pat = Pattern.compile(PATTERN_CEDULA);
			mat = pat.matcher(cedulaSinGuion);
			
			formato = mat.matches();
			
			if (formato) {
				
				num_ced = Integer.valueOf(partes[0]) + Integer.valueOf(partes[1]) + Integer.valueOf((partes[2].substring(0,3)));
				caracter = partes[2].substring(4).toUpperCase().trim();
				aux  = new Double(num_ced / 23);
				temp  = (Integer) aux.intValue();
				digito = num_ced-(temp*23);
				
				if (letras[digito].trim().equals(caracter)){
					formato =  true;
				}else{
					formato = false;
				}
				
			} 		
			
		}
			
		return formato;
	}
}
