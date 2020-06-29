package pkg;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import java.awt.Font;

public class LoginFram extends JFrame{
	
	PwSearch ps = new PwSearch();
	ConDB DB = new ConDB();
	Sex Sex = new Sex();
	
	public static String email;


	private JPanel contentPane;
	private JTextField tfEmail;
	private JPasswordField tfPassword;
	private JButton joinBtn, serchBtn, loginBtn;
	private JSplitPane splitPane;
	private JLabel lblNewLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFram frame = new LoginFram();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFram() {
		
		

		setTitle("홈트레이닝 플래너");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("E-mail");
		lblLogin.setBounds(41, 52, 69, 35);
		contentPane.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(41, 103, 69, 35);
		contentPane.add(lblPassword);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(133, 52, 176, 35);
		contentPane.add(tfEmail);
		tfEmail.setColumns(10);
	
		
		tfPassword = new JPasswordField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(133, 103, 176, 35);
		contentPane.add(tfPassword);
		
		joinBtn = new JButton("회원가입");
		joinBtn.setBounds(41, 154, 150, 29);
		contentPane.add(joinBtn);
		
		 serchBtn = new JButton("비밀번호찾기");
		 serchBtn.setBounds(262, 154, 150, 29);
		 contentPane.add(serchBtn);
		
		splitPane = new JSplitPane();
		splitPane.setBounds(0, 0, 0, 0);
		contentPane.add(splitPane);
		
		
		JButton btnNewButton_Login = new JButton("로그인");
		btnNewButton_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton_Login.setBounds(321,72,102,53);
		contentPane.add(btnNewButton_Login);
		
		lblNewLabel = new JLabel("로그인");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(185, 17, 74, 29);
		contentPane.add(lblNewLabel);

		setVisible(true);
		//회원가입 기능(회원가입 창으로 이동)
		joinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				new JoinFram();
				
				
			
			}
		});
		
		//로그인 기능(DB판독 메소드에 맞게 리턴값 설정)
		btnNewButton_Login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String id = tfEmail.getText();
				char[ ] pass = tfPassword.getPassword();
				String password = new String(pass);
				
				//공란 입력일 경우 메세지 보냄
				if(id.equals("")|| password.equals("")) {
		
					JOptionPane.showMessageDialog(null, "아이디나 패스워드 값을 입력해 주세요.");
				}
				
				//로그인 참 거짓 여부를 검사
				else {
					
					String existLogin = DB.login(id, password);
					
					//이메일이 DB에 존재하지 않을때
					if(existLogin.equals("email")) {
						
						JOptionPane.showMessageDialog(null,"해당 이메일 계정이 존재하지 않습니다.");
					}
					
					//아이디와 패스워드가 일치 하지 않는 경우
					else if(existLogin.equals("pw")) {
						
						
						
						JOptionPane.showMessageDialog(null,"이메일 또는 비밀번호가 일치하지 않습니다.\n다시 입력해주세요.");
					
						
	
						
					}
					
					//로그인 성공 
					else if(existLogin.equals("OK")) {
						
						
						
						LoginFram.email = tfEmail.getText();
						
						
						String first = DB.firstCheck(LoginFram.email);
						
						if(first.equals("F"))
						{
							JOptionPane.showMessageDialog(null,"첫 로그인인 경우 운동 플랜 추천을 위해 추가 정보를 입력해야 합니다.\n추가 정보 입력창으로 넘어갑니다.");
							dispose();
							
						
							Sex.main();
						}
						
						else if(first.equals("N"))
						{
							JOptionPane.showMessageDialog(null,"오늘의 운동 플랜으로 넘어갑니다.");
							Plan p=new Plan();
							p.main();
							dispose();
						}
						
						else
						{
							JOptionPane.showMessageDialog(null,"예상치 못한 오류가 발생하였습니다.");
							System.exit(0);
						}
						
						
						
						dispose();
						
					}
					
				}
			}
		});
		
		//비밀번호 찾기 기능(비밀번호 찾기 창으로 이동)
		serchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ps.main();
			
				

			}
		});
	}
}