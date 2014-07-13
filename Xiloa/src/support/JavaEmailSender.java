package support;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


/**
 * 
 * @author Denis Chavez
 * 
 * Esta clase implementa los métodos para envío de correos electrónicos desde la aplicación.
 *
 */
@Component
public class JavaEmailSender {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String emailAddressTo = new String();
	private String msgSubject = new String();
	private String msgText = new String();

	private String USER_NAME = null;
	private String PASSWORD = null;
	private String FROM_ADDRESS = null;
	private String HOST = null;
	private String PORT = null;
	private String RESPONSIBLE = null;
	
	public JavaEmailSender(){
		super();
	}
	
	/**
	 * Este método se ejecuta inmediatamente después del constructor y lee desde la tabla de mantenedores los datos de la cuenta desde la que se enviarán los correos
	 */
	
	@PostConstruct
	private void init(){
		try
		{
			USER_NAME = (String) this.jdbcTemplate.queryForObject("select mantenedor_valor from sccl.mantenedores where mantenedor_id = ?", new Object[]{new Long(52)}, String.class);
		}
		catch(EmptyResultDataAccessException e)
		{
			USER_NAME = "sccl.inatec.gmail.com";
		}

		FROM_ADDRESS = USER_NAME;

		try
		{		
			PASSWORD = (String) this.jdbcTemplate.queryForObject("select mantenedor_valor from sccl.mantenedores where mantenedor_id = ?", new Object[]{new Long(53)}, String.class);
		}
		catch(EmptyResultDataAccessException e)
		{
			PASSWORD = "sccl2013";
		}

		try
		{		
			HOST = (String) this.jdbcTemplate.queryForObject("select mantenedor_valor from sccl.mantenedores where mantenedor_id = ?", new Object[]{new Long(54)}, String.class);
		}
		catch(EmptyResultDataAccessException e)
		{
			HOST = "smtp.gmail.com";
		}

		try
		{		
			PORT = (String) this.jdbcTemplate.queryForObject("select mantenedor_valor from sccl.mantenedores where mantenedor_id = ?", new Object[]{new Long(55)}, String.class);
		}
		catch(EmptyResultDataAccessException e)
		{
			PORT = "587";
		}
		
		try
		{
			RESPONSIBLE = (String) this.jdbcTemplate.queryForObject("select mantenedor_valor from sccl.mantenedores where mantenedor_id = ?", new Object[]{new Long(56)}, String.class);
		}
		catch(EmptyResultDataAccessException e)
		{
			RESPONSIBLE = "sccl.inatec@gmail.com";
		}
	}
	
	/*
	 * @param emailAdressTo, el email del destinatario
	 * @param msgSubject, el subject del correo
	 * @param msgText, el texto del mensaje
	 * 
	 * Crea y envía un correo electrónico con los parámetros recibidos
	 */
	 
	public synchronized void createAndSendEmail(String emailAddressTo, String msgSubject, String msgText) {
		this.emailAddressTo = emailAddressTo;
		this.msgSubject = msgSubject;
		this.msgText = msgText;	
		sendEmailMessage();
	}
	
	/**
	 * 
	 * @param msgSubject, el subject del correo
	 * @param msgText, el texto del correo
	 * 
	 * Crea y envía un correo electrónico al responsable del sistema (especificado en los mantenedores)
	 */
	
	public synchronized void createAndSendEmailResponsible(String msgSubject, String msgText) {
		this.emailAddressTo = RESPONSIBLE;
		this.msgSubject = msgSubject;
		this.msgText = msgText;	
		sendEmailMessage();
	}
	
	/**
	 * Envía el mensaje de correo seteando los datos de la cuenta cuyos parámetros están en los mantenedores
	 */

	private void sendEmailMessage() {
		//Create email sending properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
  
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USER_NAME, PASSWORD);
				}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM_ADDRESS)); //Set from address of the email
			message.setContent(msgText,"text/html"); //set content type of the email
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailAddressTo)); //Set email recipient
			message.setSubject(msgSubject); //Set email message subject
			Transport.send(message); //Send email message
			System.out.println("sent email successfully!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}