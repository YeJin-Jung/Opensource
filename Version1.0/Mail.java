package pkg;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Mail {

  String host="smtp.naver.com";//���̹� ����

  private String username;
	private String password;
	
	Mail()//���Ϸκ��� �߽ſ� �̸��� ���� ���� ��������
	{
		File file=new File("./mail.txt");
		String readData;
		ArrayList<String> arr_acc=new ArrayList<>();
		try
		{
			FileReader fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			while((readData=br.readLine())!=null)
			{
				arr_acc.add(readData);
			}
			AccDecode ad=new AccDecode();
			username=ad.getPlain(arr_acc.get(0));
			password=ad.getPlain(arr_acc.get(1));
			br.close();
			fr.close();
			arr_acc.clear();
			username+="@naver.com";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	


  public void send(String toAddress,String pw)
	{
	  System.out.println("��� ����:"+toAddress);
		  //�޼��� ���� ����
		  Properties pp = new Properties();
		  pp.put("mail.smtp.host", host);
		  pp.put("mail.smtp.port", 465);
		  pp.put("mail.smtp.auth", "true");
		  pp.put("mail.smtp.ssl.enable","true");
		  pp.put("mail.smtp.ssl.trust","smtp.naver.com");

		  Session session = Session.getDefaultInstance(pp, new javax.mail.Authenticator() {
		   protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(username, password);
		   }
		  });

		  //session.setDebug(true);
		  try {
		   MimeMessage message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(username));
		   message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
      
       message.setSubject("ȨƮ���̴� �÷��� �ӽ� ��й�ȣ�Դϴ�.");
		   message.setText("����� ��й�ȣ�� '"+pw+"'�Դϴ�.");
		   Transport.send(message);//���� ����

		  } 
		  catch (MessagingException e) 
		  {
		      e.printStackTrace();
		  }
		  
	}


}