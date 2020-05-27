
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class JoinFram extends JFrame {

	private JPanel contentPane;


	public JoinFram() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5,5));
		
		JLabel lb_SignUp = new JLabel("회원가입\n");
		lb_SignUp.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lb_SignUp.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lb_SignUp,BorderLayout.NORTH);
		
		GridLayout grid=new GridLayout(6,2,5,5);
		JPanel ct_panel=new JPanel(grid);
		JLabel lb_name = new JLabel("이름");
		JTextField txt_name=new JTextField();
		
		JLabel lb_email = new JLabel("이메일");
		JTextField txt_email=new JTextField();

		JButton btn_check = new JButton("이메일 중복확인");
		JLabel lb_check=new JLabel();
		
		JLabel lb_pw = new JLabel("비밀번호");
		JPasswordField txt_pw=new JPasswordField("");
		
		JLabel lb_pwc = new JLabel("비밀번호 확인");
		JPasswordField txt_pwc=new JPasswordField("");
		JLabel lb_PWSame=new JLabel("");

		PWChecker pwchecker=new PWChecker(txt_pw,txt_pwc,lb_PWSame);
		pwchecker.start();

		JButton btn_enter=new JButton("회원 가입");
		
		
		
		class Action_ECheck implements ActionListener //이메일 중복 검사 버튼 이벤트
		{
			public void actionPerformed(ActionEvent e)
			{
				ConDB db=new ConDB();
				String res=db.emailCheck(txt_email.getText());
				if(res.equals("E"))
				{
					lb_check.setText("사용 가능");
				}
				else
				{
					lb_check.setText("사용 불가");
				}
			}
		}

		class Action_Enter implements ActionListener //회원 가입 버튼 이벤트
		{
			public void actionPerformed(ActionEvent e)
			{
				if(lb_check.getText().equals("사용 가능")&&lb_PWSame.getText().equals("비밀번호 일치"))
				{
					String password="";
					for(int i=0;i<txt_pw.getPassword().length;i++)
					{
						password+=txt_pw.getPassword()[i];
					}
					ConDB db=new ConDB();
					String res=db.SignUp(txt_email.getText(), txt_name.getText(), password);
					if(res.equals("Clear"))
					{
						JOptionPane.showMessageDialog(null,"회원 가입에 성공하였습니다.");
						new LoginFram();   //사용할 땐 이것을 이용하도록 함
						dispose();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "아이디 중복 여부,비밀번호 일치 여부를 확실하게 해주세요.");
				}
			}
		}
		
		btn_check.addActionListener(new Action_ECheck());
		btn_enter.addActionListener(new Action_Enter());
		
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
		contentPane.add(ct_panel,BorderLayout.CENTER;
		contentPane.add(btn_enter,BorderLayout.SOUTH);
		setSize(600,600);
		setVisible(true);
		
	}
}
