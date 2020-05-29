import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Mail {

  String host="smtp.naver.com";//네이버 메일

  private String username;
	private String password;
	
	Mail()//파일로부터 발신용 이메일 계정 정보 가져오기
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	


  public void send(String toAddress,String pw)
	{
		  //메세지 전송 세팅
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
      
       message.setSubject("홈트레이닝 플래너 임시 비밀번호입니다.");
		   message.setText("당신의 비밀번호는 '"+pw+"'입니다.");
		   Transport.send(message);//메일 전송

		  } 
		  catch (MessagingException e) 
		  {
		      e.printStackTrace();
		  }
		  
	}


}