package pkg;

import javax.swing.JPasswordField;
import javax.swing.JLabel;

public class PWChecker extends Thread{//��й�ȣ ��ġ���� �˻� ������
  private JPasswordField pw;
  private JPasswordField pwc;
  private JLabel lb;
  private String password;
  private String passwordCheck;


  private boolean running;//������ �۵� ����

  PWChecker(JPasswordField pw,JPasswordField pwc,JLabel lb)//JPasswordField�� ��й�ȣ ��ġ���� ǥ�� Label �޾ƿ�
  {
    this.pw=pw;
    this.pwc=pwc;
    this.lb=lb;
    running=true;
  }

  public void closeChecker()//������ ���߱� �޼ҵ�
  {
    running=false;
  }

  public void run()//�˻� �޼ҵ�
  {
    while(running)
    {
      password="";
      passwordCheck="";
      if(pw.getPassword()==null||pwc.getPassword()==null)
      {
    	  
      }
      else if(pw.getPassword().length==0||pwc.getPassword().length==0)
      {
    	  lb.setText("��й�ȣ �� Ȯ�� ������ ��� �Է����ּ���.");
      }
      else
      {
    	  try {
			Thread.currentThread().sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
            lb.setText("��й�ȣ ��ġ");
          }
          else
          {
            lb.setText("��й�ȣ ����ġ");
          }
      }
      
    }
  }
}