package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("custom.CedulaValidator")
public class CedulaValidator implements Validator {
	
	private static final String CEDULA_PATTERN = "(\\d{3}-)(\\d{6}-)\\d{4}[a-zA-Z]";
	
	public CedulaValidator(){
		super();
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		String cedula = value.toString();
		
		if(!validateCedula(cedula)){
			FacesMessage msg = new FacesMessage("Cedula invalida.", "Cedula invalida.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);			
		}
		
		Date nacimiento = obtenerFechaNacimientoDeCedula(cedula);
		if(!validarEdadSolicitante(nacimiento)){
			FacesMessage msg = new FacesMessage("Debe ser mayor de 20.", "Edad invalida.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	private boolean validateCedula(String cedula) {

		Pattern pat = null;
		Matcher mat = null;

		if (cedula.length() != 16)
			return false;
		else {

			pat = Pattern.compile(CEDULA_PATTERN);
			mat = pat.matcher(cedula);

			if (mat.matches() && validarDiaMesAno(cedula)) {
				return true;
			}

		}
		return false;
	}

	private boolean validarDiaMesAno(String cedula) {
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
	
	private Date obtenerFechaNacimientoDeCedula(String cedula) {

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
	
	private boolean validarEdadSolicitante(Date fechaNacimiento) {
		Calendar date = Calendar.getInstance();
		int anoActual = date.get(Calendar.YEAR);

		Calendar cal=Calendar.getInstance();
		cal.setTime(fechaNacimiento);
		int anoNacimiento=cal.get(Calendar.YEAR);
		int edad=Math.abs(anoActual-anoNacimiento );
		
		return  (edad>=20);	
	}
}