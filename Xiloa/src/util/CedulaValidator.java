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
		int res;
		String digito;

		//valida la longitud

		if (cedula.length() != 16)
			return false;

		//valida el digito verificador de la cedula

		String cadena = cedula.substring(0, 15);
		res = (int) (Long.parseLong(cadena.replace("-", ""))%23);
		digito = Character.toString(cedula.replace("-", "").charAt(13));

		if(res==0 && !digito.equalsIgnoreCase("A"))
			return false;

		if(res==1 && !digito.equalsIgnoreCase("B"))
			return false;

		if(res==2 && !digito.equalsIgnoreCase("C"))
			return false;

		if(res==3 && !digito.equalsIgnoreCase("D"))
			return false;

		if(res==4 && !digito.equalsIgnoreCase("E"))
			return false;

		if(res==5 && !digito.equalsIgnoreCase("F"))
			return false;

		if(res==6 && !digito.equalsIgnoreCase("G"))
			return false;

		if(res==7 && !digito.equalsIgnoreCase("H"))
			return false;

		if(res==8 && !digito.equalsIgnoreCase("J"))
			return false;

		if(res==9 && !digito.equalsIgnoreCase("K"))
			return false;

		if(res==10 && !digito.equalsIgnoreCase("L"))
			return false;

		if(res==11 && !digito.equalsIgnoreCase("M"))
			return false;

		if(res==12 && !digito.equalsIgnoreCase("N"))
			return false;

		if(res==13 && !digito.equalsIgnoreCase("P"))
			return false;

		if(res==14 && !digito.equalsIgnoreCase("Q"))
			return false;

		if(res==15 && !digito.equalsIgnoreCase("R"))
			return false;

		if(res==16 && !digito.equalsIgnoreCase("S"))
			return false;

		if(res==17 && !digito.equalsIgnoreCase("T"))
			return false;

		if(res==18 && !digito.equalsIgnoreCase("U"))
			return false;

		if(res==19 && !digito.equalsIgnoreCase("V"))
			return false;

		if(res==20 && !digito.equalsIgnoreCase("W"))
			return false;

		if(res==21 && !digito.equalsIgnoreCase("X"))
			return false;

		if(res==22 && !digito.equalsIgnoreCase("Y"))
			return false;

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

	    if (mes==2 && ano%4!=0 && dia>=29)  //no es a�o bisiesto
	    	return false;

	    if (ano%4==0 && mes==2 && dia>=30)  // es a�o bisiesto
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