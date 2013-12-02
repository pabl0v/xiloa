package support;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaEmailSender {
	 
	private String emailAddressTo = new String();
	private String msgSubject = new String();
	private String msgText = new String();

	final String USER_NAME = "sccl.inatec@gmail.com";
	final String PASSSWORD = "sccl2013";
	final String FROM_ADDRESS = "sccl.inatec@gmail.com";
	final private String HOST = "smtp.gmail.com";
	final private String PORT = "587";
	
	public JavaEmailSender(){
		super();
	}
	 
	public void createAndSendEmail(String emailAddressTo, String msgSubject, String msgText) {
		this.emailAddressTo = emailAddressTo;
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
					return new PasswordAuthentication(USER_NAME, PASSSWORD);
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