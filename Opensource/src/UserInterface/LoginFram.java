package UserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import java.awt.Font;

public class LoginFram extends JFrame {

	private JPanel contentPane;
	private JTextField tfEmail, tfPassword;
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
	
		
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(133, 103, 176, 35);
		contentPane.add(tfPassword);
		
		joinBtn = new JButton("회원가입");
		joinBtn.setBounds(65, 154, 104, 29);
		contentPane.add(joinBtn);
		
		 serchBtn = new JButton("비밀번호찾기");
		 serchBtn.setBounds(262, 154, 106, 29);
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
		//회원가입 액션
		joinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
			}
		});
		
		//로그인 액션


	}
}
