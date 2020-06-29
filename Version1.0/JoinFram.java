package pkg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class JoinFram extends JFrame {

	private JPanel contentPane;


	public JoinFram() 
	{
		setTitle("ȨƮ���̴� �÷���");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5,5));
		
		JLabel lb_SignUp = new JLabel("ȸ������\n");
		lb_SignUp.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lb_SignUp.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lb_SignUp,BorderLayout.NORTH);
		
		GridLayout grid=new GridLayout(6,2,5,5);
		JPanel ct_panel=new JPanel(grid);
		JLabel lb_name = new JLabel("�̸�");
		JTextField txt_name=new JTextField();
		
		JLabel lb_email = new JLabel("�̸���");
		JTextField txt_email=new JTextField();

		JButton btn_check = new JButton("�̸��� �ߺ�Ȯ��");
		JLabel lb_check=new JLabel();
		
		JLabel lb_pw = new JLabel("��й�ȣ");
		JPasswordField txt_pw=new JPasswordField("");
		
		JLabel lb_pwc = new JLabel("��й�ȣ Ȯ��");
		JPasswordField txt_pwc=new JPasswordField("");
		JLabel lb_PWSame=new JLabel("");

		

		JButton btn_enter=new JButton("ȸ�� ����");
		
		
		
		class Action_ECheck implements ActionListener //�̸��� �ߺ� �˻� ��ư �̺�Ʈ
		{
			public void actionPerformed(ActionEvent e)
			{
				ConDB db=new ConDB();
				String res=db.emailCheck(txt_email.getText());
				if(res.equals("E"))
				{
					lb_check.setText("��� ����");
				}
				else if(res.equals("N"))
				{
					lb_check.setText("��� �Ұ�");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"����ġ ���� ������ �߻��߽��ϴ�.");
				}
			}
		}

		class Action_Enter implements ActionListener //ȸ�� ���� ��ư �̺�Ʈ
		{
			private String pwToString(char[] pw)
			{
				String password="";
				for(int i=0;i<pw.length;i++)
				{
					password+=pw[i];
				}
				return password;
			}



			public void actionPerformed(ActionEvent e)
			{
				if(lb_check.getText().equals("��� ����")&&lb_PWSame.getText().equals("��й�ȣ ��ġ"))
				{
					if(!txt_name.getText().equals("")&&!txt_email.getText().equals("")
					&&!pwToString(txt_pw.getPassword()).equals("")
					&&!pwToString(txt_pwc.getPassword()).equals(""))
					{
						ConDB db=new ConDB();
					  String res=db.SignUp(txt_email.getText(), txt_name.getText(), pwToString(txt_pw.getPassword()));
					  if(res.equals("Clear"))
					  {
						  JOptionPane.showMessageDialog(null,"ȸ�� ���Կ� �����Ͽ����ϴ�.");
						  //new LoginFram();   //����� �� �̰��� �̿��ϵ��� ��
						  dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null,"����ġ ���� ������ �߻��߽��ϴ�.");
						}

					}
					else
					{
						JOptionPane.showMessageDialog(null,"���� �Է��� ��ĭ ���� ���ּ���.");
					}
					
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "���̵� �ߺ� ����,��й�ȣ ��ġ ���θ� Ȯ���ϰ� ���ּ���.");
				}
			}
		}
		
		btn_check.addActionListener(new Action_ECheck());
		btn_enter.addActionListener(new Action_Enter());

		PWChecker pwchecker=new PWChecker(txt_pw,txt_pwc,lb_PWSame);
		pwchecker.start();
		
		ct_panel.add(lb_name);
		ct_panel.add(txt_name);
		ct_panel.add(lb_email);
		ct_panel.add(txt_email);
		ct_panel.add(btn_check);
		ct_panel.add(lb_check);
		ct_panel.add(lb_pw);
		ct_panel.add(txt_pw);
		ct_panel.add(lb_pwc);
		ct_panel.add(txt_pwc);
		ct_panel.add(lb_PWSame);
		
		contentPane.add(lb_SignUp,BorderLayout.NORTH);
		contentPane.add(ct_panel,BorderLayout.CENTER);
		contentPane.add(btn_enter,BorderLayout.SOUTH);
		setSize(600,600);
		setVisible(true);
		
	}
}
