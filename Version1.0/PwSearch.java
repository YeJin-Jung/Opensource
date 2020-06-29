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
		setTitle("ȨƮ���̴� �÷���");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("��й�ȣ ã��");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(152, 25, 117, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("�̸�");
		lblNewLabel_1.setBounds(50, 82, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("�̸���");
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
		
		JButton btnNewButton_1 = new JButton("Ȯ��");
		btnNewButton_1.setBounds(263, 167, 88, 25);
		contentPane.add(btnNewButton_1);
		
		//Ȯ�� ���
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = textField.getText();
				String email = textField_1.getText();
				
				//���� �߻���
				if(email.equals("")  || name.equals(""))
				{
					JOptionPane.showMessageDialog(null, "�̸��� Ȥ�� �̸��� �Է��� �ּ���.");
					
				}
				
				//�� �� �Է������� ��Ī �˻�
				else {
					
					String result = DB.findPW(email, name);
					
					//DB�� ��Ī�Ǵ� ������ ���� �ÿ�
					if(result.equals("E")) {
						
						JOptionPane.showMessageDialog(null, "�ش� ������ �������� �ʽ��ϴ�.\n �ٽ� �ѹ� Ȯ���� �ּ���.");
						
						
					}
					
					//�ӽ� ��й�ȣ �߼�
					else if(result.equals("Clear")) {
						
						JOptionPane.showMessageDialog(null, "�Է��Ͻ� �̸��� �ּҷ� �ӽú�й�ȣ�� �����Ͽ����ϴ�.\n �����Կ��� Ȯ���� �ּ���.");
						dispose();
						
					}
				}

			}
		});
	}

	

}