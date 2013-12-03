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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JavaEmailSender {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String emailAddressTo = new String();
	private String msgSubject = new String();
	private String msgText = new String();

	private String USER_NAME;
	private String PASSWORD;
	private String FROM_ADDRESS;
	private String HOST;
	private String PORT;
	private String RESPONSIBLE;
	
	public JavaEmailSender(){
		super();
	}
	
	@PostConstruct
	private void init(){
		USER_NAME = (String) this.jdbcTemplate.queryForObject("select mantenedor_valor from sccl.mantenedores where mantenedor_id = ?", new Object[]{new Long(32)}, String.class);
		FROM_ADDRESS = USER_NAME;
		PASSWORD = (String) this.jdbcTemplate.queryForObject("select mantenedor_valor from sccl.mantenedores where mantenedor_id = ?", new Object[]{new Long(33)}, String.class);
		HOST = (String) this.jdbcTemplate.queryForObject("select mantenedor_valor from sccl.mantenedores where mantenedor_id = ?", new Object[]{new Long(34)}, String.class);
		PORT = (String) this.jdbcTemplate.queryForObject("select mantenedor_valor from sccl.mantenedores where mantenedor_id = ?", new Object[]{new Long(35)}, String.class);
		RESPONSIBLE = (String) this.jdbcTemplate.queryForObject("select mantenedor_valor from sccl.mantenedores where mantenedor_id = ?", new Object[]{new Long(36)}, String.class);
	}
	 
	public synchronized void createAndSendEmail(String emailAddressTo, String msgSubject, String msgText) {
		this.emailAddressTo = emailAddressTo;
		this.msgSubject = msgSubject;
		this.msgText = msgText;	
		sendEmailMessage();
	}
	
	public synchronized void createAndSendEmailResponsible(String msgSubject, String msgText) {
		this.emailAddressTo = RESPONSIBLE;
		this.msgSubject = msgSubject;
		this.msgText = msgText;	
		sendEmailMessage();
	}

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