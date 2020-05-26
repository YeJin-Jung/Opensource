import javax.swing.JPasswordField;
import javax.swing.JLabel;

public class PWChecker extends Thread{//비밀번호 일치여부 검사 스레드
  private JPasswordField pw;
  private JPasswordField pwc;
  private JLabel lb;
  private String password;
  private String passwordCheck;


  private boolean running;//스레드 작동 여부

  PWChecker(JPasswordField pw,JPasswordField pwc,JLabel lb)//JPasswordField와 비밀번호 일치여부 표기 Label 받아옴
  {
    this.pw=pw;
    this.pwc=pwc;
    this.lb=lb;
    running=true;
  }

  public void closeChecker()//스레드 멈추기 메소드
  {
    running=false;
  }

  public void run()//검사 메소드
  {
    while(running)
    {
      password="";
      passwordCheck="";
      for(int i=0;i<pw.getPassword().length;i++)
      {
        password+=pw.getPassword()[i];
      }
      for(int i=0;i<pwc.getPassword().length;i++)
      {
        passwordCheck+=pwc.getPassword()[i];
      }
      if(password.equals(passwordCheck))
      {
        lb.setText("비밀번호 일치");
      }
      else
      {
        lb.setText("비밀번호 불일치");
      }
    }
  }
}