package UserInterface;

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

public class Count extends JFrame {

	private JPanel contentPane;
	Purpose pur = new Purpose();
	

	
	public static String cnt;
	
	/**
	 * Launch the application.
	 */
	
	
	

	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Sex sex = new Sex();
					Count frame = new Count();
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
	public Count() {
			
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("�� ��ȸ ��Ͻó���?");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
			lblNewLabel.setBounds(121, 20, 165, 34);
			contentPane.add(lblNewLabel);
			
			JRadioButton btnNewButton = new JRadioButton("�� 0~2ȸ �");
			btnNewButton.setBounds(142, 79, 117, 29);
			contentPane.add(btnNewButton);
			
			JRadioButton btnNewButton_1 = new JRadioButton("�� 3~5ȸ �");
			btnNewButton_1.setBounds(142, 132, 117, 29);
			contentPane.add(btnNewButton_1);
			
			JRadioButton btnNewButton_2 = new JRadioButton("�� 6ȸ �̻� �");
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
					
					//"�� 0~2ȸ �"��ư�� üũ �����ÿ� ������ư ����.
					if(btnNewButton.isSelected()) {
						
						
						String difficulty = "�ʱ���";
						cnt = difficulty;
						
						
						dispose();
						
						
						pur.main(null);
						
						
						
						
					}
					
					
					
					else if(btnNewButton_1.isSelected()) {
						
						
						
						String difficulty = "�߱���";
						cnt = difficulty;
						
						
						dispose();
						
						
						pur.main(null);
						
						
				
					}
					
					else if(btnNewButton_2.isSelected()) {
						
						
						String difficulty = "�����";
						cnt = difficulty;
						
						
						dispose();
						
						
						pur.main(null);
						
						
						
						
					}
					
					
					else {
						
						JOptionPane.showMessageDialog(null, "� Ƚ���� üũ�Ͽ� �ֽʽÿ�.");
					}

				}
			});
		}
		
	
	
	
		

}
