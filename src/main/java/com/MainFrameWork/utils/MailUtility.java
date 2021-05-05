package com.MainFrameWork.utils; 



import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.MainFrameWork.support.ConfiguratorSupport;

public class MailUtility {

	public static ConfiguratorSupport configProps = new ConfiguratorSupport(
			"config.properties");

	public static  void sendNotification() {

		// Recipient's email ID needs to be mentioned.
		String[] toMailerList = configProps.getProperty("To").split(",");
		System.out.println(configProps.getProperty("To"));
		String[] ccMailerList = configProps.getProperty("CC").split(",");
		System.out.println(configProps.getProperty("CC"));
		final String username = configProps.getProperty("UserName");
		final String password = configProps.getProperty("Password");
		String from = configProps.getProperty("From");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			
			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[toMailerList.length];

			for (int i = 0; i < toMailerList.length; i++) {
				addressTo[i] = new javax.mail.internet.InternetAddress(
						toMailerList[i]);
			}
			message.setRecipients(javax.mail.Message.RecipientType.TO,
					addressTo);
			
			javax.mail.internet.InternetAddress[] addressCC = new javax.mail.internet.InternetAddress[ccMailerList.length];
			for (int i = 0; i < ccMailerList.length; i++) {
				addressCC[i] = new javax.mail.internet.InternetAddress(
						ccMailerList[i]);
			}
			message.setRecipients(javax.mail.Message.RecipientType.CC,
					addressCC);
			// Set Subject: header field
			message.setSubject("Test Mail From Send Mail Utility");//("Quickflix Titles are Exceeded  .... ");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart
					.setContent(
									"Hi, <br><br>"
									+configProps.getProperty("body")
									
								
									+ "<br><br>Thanks,<br>Offshore Team<br><br>",
							"text/html; charset=utf-8");
			// Create a multipart message
			Multipart multipart = new MimeMultipart();
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
	public static void main(String a[]){
		
		sendNotification();
	}

}