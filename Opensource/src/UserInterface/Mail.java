package UserInterface;


import java.util.Properties;

import javax.mail.*;
import javax.mail.event.*;
import javax.mail.internet.*;
import javax.mail.search.*;
import javax.mail.util.*;

public class Mail {

  String host="smtp.naver.com";//?€?΄λ²? λ©μΌ

  private final String username="hometrainingplanner";
  private final String password="(ZXasqw12)";


  public void send(String toAddress,String pw)
	{
		  //λ©μΈμ§? ? ?‘ ?Έ?
		  Properties pp = new Properties();
		  pp.put("mail.smtp.host", host);
		  pp.put("mail.smtp.auth", "true");

		  Session session = Session.getDefaultInstance(pp, new javax.mail.Authenticator() {
		   protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(username, password);
		   }
		  });

		  
		  try {
		   MimeMessage message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(username));
		   message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
      
       message.setSubject("??Έ? ?΄? ??? ?? λΉλ?λ²νΈ???€.");
		   message.setText("?Ή? ? λΉλ?λ²νΈ? '"+pw+"'???€.");
		   Transport.send(message);//λ©μΌ ? ?‘

		  } 
		  catch (MessagingException e) 
		  {
		      e.printStackTrace();
		  }
		  
	}


}