package pkg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Change extends JFrame {
	
	

	public Change(String email) {
		setTitle("ȨƮ���̴� �÷���");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 399);
		Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(5,5));
    
		JPanel ct_panel=new JPanel(new GridLayout(9,3,5,5));
		
		JLabel lb_mod= new JLabel("ȸ������ ����");
		lb_mod.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lb_mod.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lb_name = new JLabel("�̸�");
		JTextField txt_name=new JTextField("");
		
		JLabel lb_pw = new JLabel("���� ��й�ȣ");
		JPasswordField txt_pw=new JPasswordField("");
		
		JLabel lb_npw = new JLabel("�� ��й�ȣ");
		JPasswordField txt_npw=new JPasswordField("");

		JLabel lb_pwc=new JLabel("��й�ȣ Ȯ��");
		JPasswordField txt_pwc=new JPasswordField("");
		JLabel lb_check=new JLabel("");
		
		JLabel lb_sex = new JLabel("����");
		JRadioButton rb_man = new JRadioButton("����");
		JRadioButton rb_woman = new JRadioButton("����");
		ButtonGroup bg_sex=new ButtonGroup();
		bg_sex.add(rb_man);
		bg_sex.add(rb_woman);
		
		JLabel lb_dif = new JLabel("� Ƚ��");
		JRadioButton rb_el = new JRadioButton("�� 0 ~ 2ȸ �");
		JRadioButton rb_mi = new JRadioButton("�� 3 ~ 5ȸ �");
		JRadioButton rb_hi = new JRadioButton("�� 6ȸ �̻� �");
		ButtonGroup bg_dif=new ButtonGroup();
		bg_dif.add(rb_el);
		bg_dif.add(rb_mi);
		bg_dif.add(rb_hi);
		
		JLabel lb_pur = new JLabel("� ����");
		JRadioButton rb_diet=new JRadioButton("���̾�Ʈ");
		JRadioButton rb_mus=new JRadioButton("�ٷ����");
		JRadioButton rb_end=new JRadioButton("���������� ��ȭ");
		ButtonGroup bg_pur=new ButtonGroup();
		bg_pur.add(rb_diet);
		bg_pur.add(rb_mus);
		bg_pur.add(rb_end);
		
		JButton btn_enter = new JButton("���� �Ϸ�");

		class Action_Enter implements ActionListener //ȸ������ ���� ��ư �׼� �̺�Ʈ
		{
			String difficulty;
			String sex;
			String purpose;
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
				
				if(lb_check.getText().equals("��й�ȣ ����ġ"))
				{
					JOptionPane.showMessageDialog(null,"�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				}
				else
				{
					if(bg_sex.getSelection()!=null&&bg_dif.getSelection()!=null
					&&bg_pur.getSelection()!=null&&!txt_name.getText().equals("")
					&&!pwToString(txt_pw.getPassword()).equals("")
					&&!pwToString(txt_npw.getPassword()).equals("")
					&&!pwToString(txt_pwc.getPassword()).equals(""))
					{
						ConDB db=new ConDB();
						String res=db.login(email,pwToString(txt_pw.getPassword()));
						
						switch(res)
						{
						
							case "PW":
							JOptionPane.showMessageDialog(null,"���� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
							break;
							case "OK":
							if(rb_el.isSelected())
							{
								difficulty="�ʱ���";
							}
							else if(rb_mi.isSelected())
							{
								difficulty="�ʱ���";
							}
							else if(rb_hi.isSelected())
							{
								difficulty="�ʱ���";
							}
							else
							{
								JOptionPane.showMessageDialog(null,"���� �Է��� ��ĭ ���� ���ּ���");
								return;
							}
							if(rb_man.isSelected())
							{
								sex="����";
							}
							else if(rb_woman.isSelected())
							{
								sex="����";
							}
							else
							{
								JOptionPane.showMessageDialog(null,"���� �Է��� ��ĭ ���� ���ּ���");
								return;
							}
							if(rb_diet.isSelected())
							{
								purpose="���̾�Ʈ";
							}
							else if(rb_mus.isSelected())
							{
								purpose="�ٷ����";
							}
							else if(rb_end.isSelected())
							{
								purpose="������ ��ȭ";
							}
							else
							{
								JOptionPane.showMessageDialog(null,"���� �Է��� ��ĭ ���� ���ּ���");
								return;
							}
								
						
							String res_mod=db.modInfo(email, txt_name.getText(), pwToString(txt_npw.getPassword()), sex,difficulty,purpose);
							System.out.println(res_mod);
							switch(res_mod)
							{
								case "Clear":
								JOptionPane.showMessageDialog(null, "ȸ������ ������ �Ϸ�Ǿ����ϴ�.");
								dispose();
								break;
								default:
								JOptionPane.showMessageDialog(null, "����ġ ���� ������ �߻��߽��ϴ�.");
								break;
							}
							break;
							default:
							JOptionPane.showMessageDialog(null, "����ġ ���� ������ �߻��߽��ϴ�.");
							break;
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"���� �Է��� ��ĭ ���� ���ּ���");
					}
				}

				
			}

			
		}
		
		btn_enter.addActionListener(new Action_Enter());


		PWChecker pwchecker=new PWChecker(txt_npw,txt_pwc,lb_check);
		pwchecker.start();

		ct_panel.add(lb_name);
		ct_panel.add(txt_name);
		ct_panel.add(new JLabel());
		ct_panel.add(lb_pw);
		ct_panel.add(txt_pw);
		ct_panel.add(new JLabel());
		ct_panel.add(lb_npw);
		ct_panel.add(txt_npw);
		ct_panel.add(new JLabel());
		ct_panel.add(lb_pwc);
		ct_panel.add(txt_pwc);
		ct_panel.add(lb_check);
		ct_panel.add(lb_sex);
		ct_panel.add(rb_man);
		ct_panel.add(rb_woman);
		ct_panel.add(lb_dif);
		ct_panel.add(new JLabel());
		ct_panel.add(new JLabel());
		ct_panel.add(rb_el);
		ct_panel.add(rb_mi);
		ct_panel.add(rb_hi);
		ct_panel.add(lb_pur);
		ct_panel.add(new JLabel());
		ct_panel.add(new JLabel());
		ct_panel.add(rb_diet);
		ct_panel.add(rb_mus);
		ct_panel.add(rb_end);
		

		contentPane.add(lb_mod,BorderLayout.NORTH);
		contentPane.add(ct_panel,BorderLayout.CENTER);
		contentPane.add(btn_enter,BorderLayout.SOUTH);
		setSize(600,600);
		setVisible(true);
	}
}