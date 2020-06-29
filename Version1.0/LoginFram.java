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
		
		

		setTitle("ȨƮ���̴� �÷���");
		
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
		
		joinBtn = new JButton("ȸ������");
		joinBtn.setBounds(41, 154, 150, 29);
		contentPane.add(joinBtn);
		
		 serchBtn = new JButton("��й�ȣã��");
		 serchBtn.setBounds(262, 154, 150, 29);
		 contentPane.add(serchBtn);
		
		splitPane = new JSplitPane();
		splitPane.setBounds(0, 0, 0, 0);
		contentPane.add(splitPane);
		
		
		JButton btnNewButton_Login = new JButton("�α���");
		btnNewButton_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton_Login.setBounds(321,72,102,53);
		contentPane.add(btnNewButton_Login);
		
		lblNewLabel = new JLabel("�α���");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(185, 17, 74, 29);
		contentPane.add(lblNewLabel);

		setVisible(true);
		//ȸ������ ���(ȸ������ â���� �̵�)
		joinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				new JoinFram();
				
				
			
			}
		});
		
		//�α��� ���(DB�ǵ� �޼ҵ忡 �°� ���ϰ� ����)
		btnNewButton_Login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String id = tfEmail.getText();
				char[ ] pass = tfPassword.getPassword();
				String password = new String(pass);
				
				//���� �Է��� ��� �޼��� ����
				if(id.equals("")|| password.equals("")) {
		
					JOptionPane.showMessageDialog(null, "���̵� �н����� ���� �Է��� �ּ���.");
				}
				
				//�α��� �� ���� ���θ� �˻�
				else {
					
					String existLogin = DB.login(id, password);
					
					//�̸����� DB�� �������� ������
					if(existLogin.equals("email")) {
						
						JOptionPane.showMessageDialog(null,"�ش� �̸��� ������ �������� �ʽ��ϴ�.");
					}
					
					//���̵�� �н����尡 ��ġ ���� �ʴ� ���
					else if(existLogin.equals("pw")) {
						
						
						
						JOptionPane.showMessageDialog(null,"�̸��� �Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.\n�ٽ� �Է����ּ���.");
					
						
	
						
					}
					
					//�α��� ���� 
					else if(existLogin.equals("OK")) {
						
						
						
						LoginFram.email = tfEmail.getText();
						
						
						String first = DB.firstCheck(LoginFram.email);
						
						if(first.equals("F"))
						{
							JOptionPane.showMessageDialog(null,"ù �α����� ��� � �÷� ��õ�� ���� �߰� ������ �Է��ؾ� �մϴ�.\n�߰� ���� �Է�â���� �Ѿ�ϴ�.");
							dispose();
							
						
							Sex.main();
						}
						
						else if(first.equals("N"))
						{
							JOptionPane.showMessageDialog(null,"������ � �÷����� �Ѿ�ϴ�.");
							Plan p=new Plan();
							p.main();
							dispose();
						}
						
						else
						{
							JOptionPane.showMessageDialog(null,"����ġ ���� ������ �߻��Ͽ����ϴ�.");
							System.exit(0);
						}
						
						
						
						dispose();
						
					}
					
				}
			}
		});
		
		//��й�ȣ ã�� ���(��й�ȣ ã�� â���� �̵�)
		serchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ps.main();
			
				

			}
		});
	}
}