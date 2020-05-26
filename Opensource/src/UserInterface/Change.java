package UserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class Change extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Change frame = new Change();
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
	public Change() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("회원정보");
		lblNewLabel.setBounds(182, 27, 76, 19);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("이름");
		lblNewLabel_1.setBounds(26, 60, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("기존 비밀번호");
		lblNewLabel_1_1.setBounds(26, 88, 76, 16);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("새 비밀번호");
		lblNewLabel_1_2.setBounds(26, 117, 61, 16);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("성별");
		lblNewLabel_1_3.setBounds(26, 147, 61, 16);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("운동 횟수");
		lblNewLabel_1_4.setBounds(26, 175, 61, 16);
		contentPane.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("운동 목적");
		lblNewLabel_1_5.setBounds(26, 247, 61, 16);
		contentPane.add(lblNewLabel_1_5);
		
		textField = new JTextField();
		textField.setBounds(152, 55, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(152, 83, 130, 26);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(152, 112, 130, 26);
		contentPane.add(passwordField_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("남성");
		rdbtnNewRadioButton.setBounds(152, 143, 61, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("여성");
		rdbtnNewRadioButton_1.setBounds(221, 143, 61, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("주 0 ~ 2회 운동");
		chckbxNewCheckBox.setBounds(152, 171, 128, 23);
		contentPane.add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("주 3 ~ 5회 운동");
		chckbxNewCheckBox_1.setBounds(152, 194, 128, 23);
		contentPane.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("주 6회 이상 운동");
		chckbxNewCheckBox_2.setBounds(152, 216, 128, 23);
		contentPane.add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("다이어트");
		chckbxNewCheckBox_3.setBounds(152, 243, 128, 23);
		contentPane.add(chckbxNewCheckBox_3);
		
		JCheckBox chckbxNewCheckBox_3_1 = new JCheckBox("근력향상");
		chckbxNewCheckBox_3_1.setBounds(152, 268, 128, 23);
		contentPane.add(chckbxNewCheckBox_3_1);
		
		JCheckBox chckbxNewCheckBox_3_2 = new JCheckBox("심폐지구력 강화");
		chckbxNewCheckBox_3_2.setBounds(152, 292, 128, 23);
		contentPane.add(chckbxNewCheckBox_3_2);
		
		JButton btnNewButton = new JButton("수정 완료");
		btnNewButton.setBounds(309, 331, 117, 29);
		contentPane.add(btnNewButton);
	}
}
