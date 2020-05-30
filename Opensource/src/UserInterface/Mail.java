package UserInterface;


import java.util.Properties;

import javax.mail.*;
import javax.mail.event.*;
import javax.mail.internet.*;
import javax.mail.search.*;
import javax.mail.util.*;

public class Mail {

  String host="smtp.naver.com";//?„¤?´ë²? ë©”ì¼

  private final String username="hometrainingplanner";
  private final String password="(ZXasqw12)";


  public void send(String toAddress,String pw)
	{
		  //ë©”ì„¸ì§? ? „?†¡ ?„¸?Œ…
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
      
       message.setSubject("?™ˆ?Š¸? ˆ?´?‹ ?”Œ?˜?„ˆ ?„?‹œ ë¹„ë?ë²ˆí˜¸?…?‹ˆ?‹¤.");
		   message.setText("?‹¹?‹ ?˜ ë¹„ë?ë²ˆí˜¸?Š” '"+pw+"'?…?‹ˆ?‹¤.");
		   Transport.send(message);//ë©”ì¼ ? „?†¡

		  } 
		  catch (MessagingException e) 
		  {
		      e.printStackTrace();
		  }
		  
	}


}