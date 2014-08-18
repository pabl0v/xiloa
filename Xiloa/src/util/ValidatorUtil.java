package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Clase que utilizada para validar formatos.
public class ValidatorUtil {
	
	private static final String PATTERN_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PATTERN_CEDULA = "(\\d{3}-)(\\d{6}-)\\d{4}[a-zA-Z]"; 
	private static final String PATTERN_PHONE = "\\d{8}";
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Funcion que valida por expresion regular el formato de email.   
	public static boolean validateEmail(String email) {
		 
        // Compila la expresion regular en el patron.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        // Compara con el patron
        Matcher matcher = pattern.matcher(email);
        return matcher.matches(); 
    }
	
	public static boolean validatePhone(String phone){
        Pattern pattern = Pattern.compile(PATTERN_PHONE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Funcion que valida el formato de cedula, así como el digito verificador.
	public static boolean validateCedula(String cedula) {

		Pattern pat = null;
		Matcher mat = null;

		if (cedula.length() != 16)
			return false;
		else {

			pat = Pattern.compile(PATTERN_CEDULA);
			mat = pat.matcher(cedula);

			if (mat.matches() && validarDiaMesAno(cedula)) {
				return true;
			}

		}
		return false;
	}

	private static boolean validarDiaMesAno(String cedula) {
		int dia, mes, ano;
		
		dia=Integer.valueOf(cedula.substring(4, 6));
	    mes=Integer.valueOf(cedula.substring(6, 8));
	    ano=Integer.valueOf(cedula.substring(8, 10));  
	    
	    
	    if ((mes==2 || mes==4 || mes==6|| mes==9|| mes==11) && dia==31)
	      return false;
	    
	    if (mes==2 && ano%4!=0 && dia>=29)  //no es año bisiesto
	    	return false;
	    
	    if (ano%4==0 && mes==2 && dia>=30)  // es año bisiesto
	    	return false;
	    
	    return ((dia>=1 && dia<=31) && 
	    		(mes>=1 && mes<=12));
	}

	public static Date obtenerFechaNacimientoDeCedula(String cedula) {

		if (!validateCedula(cedula))
			return null;
		else {
			try {

				Integer digitosAnoCedula = Integer.valueOf(cedula.substring(8, 10));
				int anoRealFechaNacimiento;
				int anoActual=Calendar.getInstance().get(Calendar.YEAR);

				if ( digitosAnoCedula> (anoActual-2000)   &&  digitosAnoCedula <=99 )
					anoRealFechaNacimiento = 1900 + digitosAnoCedula;
				else
					anoRealFechaNacimiento = 2000 + digitosAnoCedula;

				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				
				String dateInString = cedula.substring(4, 6) + "-" 	+ cedula.substring(6, 8) + "-"
						+ anoRealFechaNacimiento;
				
				Date fechaNacimiento = formatter.parse(dateInString);
				return fechaNacimiento;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static boolean validarEdadSolicitante(Date fechaNacimiento) {
		Calendar date = Calendar.getInstance();
		int anoActual = date.get(Calendar.YEAR);

		Calendar cal=Calendar.getInstance();
		cal.setTime(fechaNacimiento);
		int anoNacimiento=cal.get(Calendar.YEAR);
		int edad=Math.abs(anoActual-anoNacimiento );
		
		return  (edad>=18 && edad<=45);	
	}
}