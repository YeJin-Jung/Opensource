import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Change extends JFrame {
	private String email;

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	public Change() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 399);
		contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout(5,5));
		setContentPane(contentPane);
    contentPane.setLayout(null);
    
		JPanel ct_panel=new JPanel(new GridLayout(9,3,5,5));
		
		JLabel lb_mod= new JLabel("회원정보 수정");
		lb_mod.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lb_mod.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lb_name = new JLabel("이름");
		JTextField txt_name=new JTextField("");
		
		JLabel lb_pw = new JLabel("기존 비밀번호");
		JPasswordField txt_pw=new JPasswordField("");
		
		JLabel lb_npw = new JLabel("새 비밀번호");
		JPasswordField txt_npw=new JPasswordField("");

		JLabel lb_pwc=new JLabel("비밀번호 확인");
		JPasswordField txt_pwc=new JPasswordField("");
		JLabel lb_check=new JLabel("");
		
		JLabel lb_sex = new JLabel("성별");
		JRadioButton rb_man = new JRadioButton("남성");
		JRadioButton rb_woman = new JRadioButton("여성");
		ButtonGroup bg_sex=new ButtonGroup();
		bg_sex.add(rb_man);
		bg_sex.add(rb_woman);
		
		JLabel lb_dif = new JLabel("운동 횟수");
		JRadioButton rb_el = new JRadioButton("주 0 ~ 2회 운동");
		JRadioButton rb_mi = new JRadioButton("주 3 ~ 5회 운동");
		JRadioButton rb_hi = new JRadioButton("주 6회 이상 운동");
		ButtonGroup bg_dif=new ButtonGroup();
		bg_dif.add(rb_el);
		bg_dif.add(rb_mi);
		bg_dif.add(rb_hi);
		
		JLabel lb_pur = new JLabel("운동 목적");
		JRadioButton rb_diet=new JRadioButton("다이어트");
		JRadioButton rb_mus=new JRadioButton("근력향상");
		JRadioButton rb_end=new JRadioButton("심폐지구력 강화");
		ButtonGroup bg_pur=new ButtonGroup();
		bg_pur.add(rb_diet);
		bg_pur.add(rb_mus);
		bg_pur.add(rb_end);
		
		JButton btn_enter = new JButton("수정 완료");

		class Action_Enter implements ActionListener //회원정보 수정 버튼 액션 이벤트
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
				if(lb_check.getText().equals("비밀번호 불일치"))
				{
					JOptionPane.showMessageDialog(null,"새 비밀번호가 일치하지 않습니다.");
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
							JOptionPane.showMessageDialog(null,"기존 비밀번호가 일치하지 않습니다.");
							break;
							case "OK":
							String difficulty;
							switch(bg_dif.getSelection().toString())
							{
								case "주 0 ~ 2회 운동":
								difficulty="초급자";
								break;
								case "주 3 ~ 5회 운동":
								difficulty="중급자";
								break;
								case "주 6회 이상 운동":
								difficulty="상급자";
								break;
								default:
								JOptionPane.showMessageDialog(null, "예상치 못한 오류가 발생했습니다.");
								return;
							}
							String res_mod=db.modInfo(email, txt_name.getText(), pwToString(txt_npw.getPassword()), bg_sex.getSelection().toString(),difficulty,bg_pur.getSelection().toString());
							switch(res_mod)
							{
								case "Clear":
								JOptionPane.showMessageDialog(null, "회원정보 수정이 완료되었습니다.");
								dispose();
								break;
								default:
								JOptionPane.showMessageDialog(null, "예상치 못한 오류가 발생했습니다.");
							}
							break;
							default:
							JOptionPane.showMessageDialog(null, "예상치 못한 오류가 발생했습니다.");
							break;
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"정보 입력을 빈칸 없이 해주세요");
					}
				}

				
			}

			
		}


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

		
	}
}