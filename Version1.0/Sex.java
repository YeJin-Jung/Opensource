package pkg;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class Sex extends JFrame {

	private JPanel contentPane;
	Count cnt = new Count();
	
	
	public static String jender;

	
	
	
	
	
	/**
	 * Launch the application.
	 */
	public void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sex frame = new Sex();
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
	public Sex() {
		setTitle("ȨƮ���̴� �÷���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("������ �����ΰ���?");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setBounds(121, 20, 165, 34);
		contentPane.add(lblNewLabel);
		
		JRadioButton btnNewButton = new JRadioButton("����");
		btnNewButton.setBounds(142, 79, 117, 29);
		contentPane.add(btnNewButton);
		
		JRadioButton btnNewButton_1 = new JRadioButton("����");
		btnNewButton_1.setBounds(142, 132, 117, 29);
		contentPane.add(btnNewButton_1);
		
		ButtonGroup gr = new ButtonGroup();
		gr.add(btnNewButton);
		gr.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("����");
		btnNewButton_2.setBounds(346, 213, 69, 29);
		contentPane.add(btnNewButton_2);
		
		
		btnNewButton_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//"����"��ư�� üũ �����ÿ� ������ư ����.
				if(btnNewButton.isSelected()) {
					
					
					jender = btnNewButton.getText();
					
					
					dispose();

					
					cnt.main();
					
				}
				//"����"��ư�� óũ ������ 
				else if(btnNewButton_1.isSelected()) {
					
					jender = btnNewButton_1.getText();
					
					
					dispose();
					
					
					cnt.main();
					
				}
				
				//�Ѵ� üũ ������ ��
				else {
					
					
					JOptionPane.showMessageDialog(null, "������ üũ�Ͽ� �ֽʽÿ�.");
					
				}

			}
		});
	}
	
}