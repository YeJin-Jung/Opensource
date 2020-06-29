package pkg;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PwSearch extends JFrame {

	static JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	ConDB DB = new ConDB();
	

	/**
	 * Launch the application.
	 */
	public void launch() {
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	/**
	 * Create the frame.
	 */
	public PwSearch() {
		setTitle("홈트레이닝 플래너");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("비밀번호 찾기");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(152, 25, 117, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("이름");
		lblNewLabel_1.setBounds(50, 82, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("이메일");
		lblNewLabel_1_1.setBounds(50, 124, 61, 16);
		contentPane.add(lblNewLabel_1_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(139, 77, 130, 26);
		contentPane.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(139, 119, 130, 26);
		contentPane.add(textField_1);
		
		JButton btnNewButton_1 = new JButton("확인");
		btnNewButton_1.setBounds(263, 167, 88, 25);
		contentPane.add(btnNewButton_1);
		
		//확인 기능
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = textField.getText();
				String email = textField_1.getText();
				
				//공란 발생시
				if(email.equals("")  || name.equals(""))
				{
					JOptionPane.showMessageDialog(null, "이메일 혹은 이름을 입력해 주세요.");
					
				}
				
				//둘 다 입력했을시 매칭 검사
				else {
					
					String result = DB.findPW(email, name);
					
					//DB에 매칭되는 정보가 없을 시에
					if(result.equals("E")) {
						
						JOptionPane.showMessageDialog(null, "해당 계정이 존재하지 않습니다.\n 다시 한번 확인해 주세요.");
						
						
					}
					
					//임시 비밀번호 발송
					else if(result.equals("Clear")) {
						
						JOptionPane.showMessageDialog(null, "입력하신 이메일 주소로 임시비밀번호를 전송하였습니다.\n 메일함에서 확인해 주세요.");
						dispose();
						
					}
				}

			}
		});
	}

	

}