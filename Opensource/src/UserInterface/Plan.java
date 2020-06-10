package UserInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.Scrollbar;


public class Plan extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;


	 /* Launch the
	private Component scrollPane; application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Plan frame = new Plan();
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
	public Plan() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("저장");
		btnNewButton.setBounds(6, 6, 75, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("로그아웃");
		btnNewButton_1.setBounds(1127, 6, 117, 29);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Plan 생성!");
		lblNewLabel.setBounds(552, 60, 130, 29);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("오늘의 추천 운동은 다음과 같습니다");
		lblNewLabel_1.setBounds(504, 91, 220, 16);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(43, 131, 370, 222);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("운동 횟수/시간(초) :");
		lblNewLabel_2.setBounds(6, 163, 105, 16);
		panel.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(111, 158, 58, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("실시한 세트 :");
		lblNewLabel_2_1.setBounds(215, 163, 76, 16);
		panel.add(lblNewLabel_2_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(289, 158, 58, 26);
		panel.add(textField_1);
		
		JButton btnNewButton_2 = new JButton("운동자세 영상으로 배우기");
		btnNewButton_2.setBounds(6, 191, 193, 29);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("운동기록 수정");
		btnNewButton_3.setBounds(225, 191, 117, 29);
		panel.add(btnNewButton_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 370, 151);
		panel.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(442, 131, 370, 222);
		contentPane.add(panel_2);
		
		JLabel lblNewLabel_2_2 = new JLabel("운동 횟수/시간(초) :");
		lblNewLabel_2_2.setBounds(6, 163, 105, 16);
		panel_2.add(lblNewLabel_2_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(111, 158, 58, 26);
		panel_2.add(textField_2);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("실시한 세트 :");
		lblNewLabel_2_1_1.setBounds(215, 163, 76, 16);
		panel_2.add(lblNewLabel_2_1_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(289, 158, 58, 26);
		panel_2.add(textField_3);
		
		JButton btnNewButton_2_1 = new JButton("운동자세 영상으로 배우기");
		btnNewButton_2_1.setBounds(6, 191, 193, 29);
		panel_2.add(btnNewButton_2_1);
		
		JButton btnNewButton_3_1 = new JButton("운동기록 수정");
		btnNewButton_3_1.setBounds(225, 191, 117, 29);
		panel_2.add(btnNewButton_3_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(0, 0, 370, 151);
		panel_2.add(panel_1_1);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBounds(830, 131, 370, 222);
		contentPane.add(panel_2_1);
		
		JLabel lblNewLabel_2_2_1 = new JLabel("운동 횟수/시간(초) :");
		lblNewLabel_2_2_1.setBounds(6, 163, 105, 16);
		panel_2_1.add(lblNewLabel_2_2_1);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(111, 158, 58, 26);
		panel_2_1.add(textField_4);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("실시한 세트 :");
		lblNewLabel_2_1_1_1.setBounds(215, 163, 76, 16);
		panel_2_1.add(lblNewLabel_2_1_1_1);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(289, 158, 58, 26);
		panel_2_1.add(textField_5);
		
		JButton btnNewButton_2_1_1 = new JButton("운동자세 영상으로 배우기");
		btnNewButton_2_1_1.setBounds(6, 191, 193, 29);
		panel_2_1.add(btnNewButton_2_1_1);
		
		JButton btnNewButton_3_1_1 = new JButton("운동기록 수정");
		btnNewButton_3_1_1.setBounds(225, 191, 117, 29);
		panel_2_1.add(btnNewButton_3_1_1);
		
		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setBounds(0, 0, 370, 151);
		panel_2_1.add(panel_1_1_1);
		
		
		
	
	}
}
