package es.greuze.sandbox.secretsanta;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleSSLMail {

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final int SMTP_HOST_PORT = 465;
	// Reemplazar con la cuenta de e-mail y password que se quieran usar
	private static final String SMTP_AUTH_USER = "my_mail@gmail.com";
	private static final String SMTP_AUTH_PWD = "my_password";

	public static void main(String[] args) throws Exception {
		Participante participante = new Participante("Prueba", "prueba@fake.mail");
		enviarCorreo(participante);
	}

	public static void enviarCorreo(Participante participante) throws Exception {
		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", SMTP_HOST_NAME);
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtps.quitwait", "false"); //

		Session mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		message.setSubject("Asignacion de amigo invisible para " + participante.getNombre());
		message.setContent("A " + participante.getNombre()
								+ " le toca ser el amigo invisible de "
								+ participante.getObjetivoRegalo().getNombre() + ".",
							"text/plain");

		message.addRecipient(Message.RecipientType.TO, new InternetAddress(participante.getCorreo()));

		transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
	}
}
