package pkg;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class Purpose extends JFrame {

	private JPanel contentPane;
	
	
	ConDB DB = new ConDB();
	
	
	
	public static String purpose;
	

	/**
	 * Launch the application.
	 */
	public void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Purpose frame = new Purpose();
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
	
	
	
	public Purpose() {
		
		setTitle("ȨƮ���̴� �÷���");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("��Ϸ��� ������ �����ΰ���?");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setBounds(121, 20, 165, 34);
		contentPane.add(lblNewLabel);
		
		JRadioButton btnNewButton = new JRadioButton("���̾�Ʈ");
		btnNewButton.setBounds(142, 79, 117, 29);
		contentPane.add(btnNewButton);
		
		JRadioButton btnNewButton_1 = new JRadioButton("�ٷ����");
		btnNewButton_1.setBounds(142, 132, 117, 29);
		contentPane.add(btnNewButton_1);
		
		JRadioButton btnNewButton_2 = new JRadioButton("������ ��ȭ");
		btnNewButton_2.setBounds(142, 185, 117, 29);
		contentPane.add(btnNewButton_2);
		
		ButtonGroup gr = new ButtonGroup();
		gr.add(btnNewButton);
		gr.add(btnNewButton_1);
		gr.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("����");
		btnNewButton_3.setBounds(346, 213, 69, 29);
		contentPane.add(btnNewButton_3);
		
		
		btnNewButton_3.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				//"���̾�Ʈ"��ư�� üũ �����ÿ� ������ư ����.
				if(btnNewButton.isSelected()) {
					
					
					
					purpose = btnNewButton.getText();
					
					
					String update = DB.init_info(LoginFram.email, Sex.jender, Count.cnt, purpose );
					
					
					
					if(update.equals("Clear"))
						
					{
						JOptionPane.showMessageDialog(null, "�߰� ���� �Է��� �Ϸ� �Ǿ����ϴ�!\n����â���� �Ѿ�ϴ�.");
						Plan p=new Plan();
						p.main();
						dispose();
						
					}	
					
					
					else
					{
						
						
						JOptionPane.showMessageDialog(null, "����ġ ���� ������ �߻��Ͽ����ϴ�.");
						System.exit(0);
						
					}
					
				
					
					
					
					
				}
				
				
				
				else if(btnNewButton_1.isSelected()) {
					
					
					
					purpose = btnNewButton_1.getText();
					
					String update = DB.init_info(LoginFram.email, Sex.jender, Count.cnt, purpose);

					if (update.equals("Clear"))

					{

						JOptionPane.showMessageDialog(null, "�߰� ���� �Է��� �Ϸ� �Ǿ����ϴ�!\n����â���� �Ѿ�ϴ�.");
						Plan p=new Plan();
						p.main();
						dispose();

					}

					else {

						JOptionPane.showMessageDialog(null, "����ġ ���� ������ �߻��Ͽ����ϴ�.");
						System.exit(0);

					}

					
					
			
				}
				
				else if(btnNewButton_2.isSelected()) {
					
					
					purpose = btnNewButton_2.getText();
					
					String update = DB.init_info(LoginFram.email, Sex.jender, Count.cnt, purpose);

					if (update.equals("Clear"))

					{

						JOptionPane.showMessageDialog(null, "�߰� ���� �Է��� �Ϸ� �Ǿ����ϴ�!\n����â���� �Ѿ�ϴ�.");
						Plan p=new Plan();
						p.main();
						dispose();

					}

					else {

						JOptionPane.showMessageDialog(null, "����ġ ���� ������ �߻��Ͽ����ϴ�.");
						System.exit(0);

					}					
				
					
					
					
					
				}
				
				else {
					
					JOptionPane.showMessageDialog(null, "� ������ üũ�Ͽ� �ֽʽÿ�.");
				}

			}
		});
	}
	
}