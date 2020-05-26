import javax.swing.JPasswordField;
import javax.swing.JLabel;

public class PWChecker extends Thread{
  private JPasswordField pw;
  private JPasswordField pwc;
  private JLabel lb;
  private String password;
  private String passwordCheck;


  boolean running;

  PWChecker(JPasswordField pw,JPasswordField pwc,JLabel lb)
  {
    this.pw=pw;
    this.pwc=pwc;
    this.lb=lb;
    running=true;
  }

  

  public void run()
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