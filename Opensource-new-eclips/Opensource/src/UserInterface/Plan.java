package UserInterface;

import java.awt.Cursor;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.IOException;
import java.awt.Container;



public class Plan extends Frame implements TextListener{

	private JFrame frame;
	static TextField insert_wokrout;
	static TextField insert_set;
	static TextField insert_wokrout2;
	static TextField insert_set2;
	static TextField insert_wokrout3;
	static TextField insert_set3;
	
	private String Train_info;
	private String Date_Log; //�α׿� ����� ��¥ 
	private String Date_Now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); //���� �ð� Ȯ�� 
	private String Today_Log; // today_Train�� ���ϰ��� �޾Ƽ� ���� �� ���������� �ƴ���  �Ǻ�.
	private String RE;
	private String update_quantity ;
	private String update_set ;
	
	
	static String[] Img_Link = new String[3];
	static String[] Video_Link = new String[3];
	static String[] Inserted_quantity = new String[3]; //�Է��� ��ϵ��� �����ϱ� ���� ���ڿ� ����.
	static String[] Inserted_set = new String[3]; //�Է��� ��ϵ��� �����ϱ� ���� ���ڿ� ����.
	
	
	
	static ArrayList<String> Workout = new ArrayList<>();
	static ArrayList<Train> Workout_detail = new ArrayList<>(); // �ҷ��� �α� ���� ���� �迭����Ʈ.
	static ArrayList<Train> Workout_saved = new ArrayList<>();	// ���� �� ���ӽ� ������ �α׵��� ���� �迭����Ʈ.
	
 	
	
	
	
	ConDB DB = new ConDB();
	Train t = new Train();
	Train t1 = new Train();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			
			
			public void run() {
				
				try {
					
					Plan window = new Plan();
					
					window.frame.setVisible(true);
					
					
				} 
				
					catch (Exception e) {
						
						
					e.printStackTrace();
					
					
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Plan() {
		
		
		
		initialize();
		
		
	}
	
	
	//��ũ�� �ɱ����� �żҵ� 
	
	public static JLabel linkify(final String text, String URL, String toolTip)
	{
	       URI temp = null;
	       
	       
	       try
	     
	       {
	    	   
	           temp = new URI(URL);
	           
	       }
	       
	       
	       catch (Exception e)
	       
	       
	       {
	    	   
	           e.printStackTrace();
	           
	       }
	       
	       
	       final URI uri = temp;
	       
	       final JLabel link = new JLabel();
	       
	       link.setText("<HTML><FONT color=\"#000099\">"+text+"</FONT></HTML>");
	       
	       
	       
	       if(!toolTip.equals(""))
	    	   
	           link.setToolTipText(toolTip);
	       
	       
	       link.setCursor(new Cursor(Cursor.HAND_CURSOR));
	       
	       
	       link.addMouseListener(new MouseListener()
	       
	       {
	           public void mouseExited(MouseEvent arg0)
	           
	           {
	        	   
	               link.setText("<HTML><FONT color=\"#000099\">"+text+"</FONT></HTML>");
	               
	           }

	           public void mouseEntered(MouseEvent arg0)
	           {
	        	   
	               link.setText("<HTML><FONT color=\"#000099\"><U>"+text+"</U></FONT></HTML>");
	               
	           }

	           public void mouseClicked(MouseEvent arg0)
	           {
	        	   
	               if (Desktop.isDesktopSupported())
	            	   
	               {
	            	   
	                   try
	                   
	                   {
	                	   
	                       Desktop.getDesktop().browse(uri);
	                       
	                       
	                   }
	                   
	                   
	                   catch (Exception e)
	                   
	                   {
	                	   
	                	   
	                       e.printStackTrace();
	                       
	                       
	                   }
	                   
	               }
	               
	               else
	            	   
	               {
	            	   
	                   JOptionPane pane = new JOptionPane("��ũ �ҷ����⿡ ���� �Ͽ����ϴ�.");
	                   
	                   JDialog dialog = pane.createDialog(new JFrame(), "");
	                   
	                   dialog.setVisible(true);
	                   
	                   
	               }
	           }

	           public void mousePressed(MouseEvent e)
	           {
	           }

	           public void mouseReleased(MouseEvent e)
	           {
	           }
	       });
	       
	       return link;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
								         //Setting//
		//////////////////////////////////////////////////////////////////////////////
		
		
		//�α׵��� ù�α��� ���ο� ������� today_Train ���� �켱 üũ�ѵ� ���� ���� ���� ����� ���� ������. 
		
		 Today_Log = DB.today_Train(LoginFram.email);
		
		//  ���ϵǴ� ��� ���� ��� ( ������ ����  )
		
		if(Today_Log.equals("N")) 

		{
			
			Train_info = DB.getTrain_Check(LoginFram.email, DB.getTrain(LoginFram.email, Purpose.purpose, Count.cnt));
			
			
			
			//���õ� �α� ���� ��ũ�������� ArrayList�� ����
			
			StringTokenizer st_ln = new StringTokenizer(Train_info,"%");
			
			
			while(st_ln.hasMoreTokens())
			{
				
				
				Workout.add(st_ln.nextToken());
				
			}
			
			for(int i = 0 ;  i < Workout.size() ; i++)
			{
				
				
				StringTokenizer st_att = new StringTokenizer(Workout.get(i),"/");
				
				
				
				t.name = st_att.nextToken();
				t.quantity = Integer.parseInt(st_att.nextToken());
				t.unit = st_att.nextToken();
				t.sett = Integer.parseInt(st_att.nextToken());
				
				
				Workout_detail.add(t);
				
				
				
			}
			
			
			Workout.clear();
			
			
			// ����� ArrayList�� ���� �̹���,���� URL��  Img_Link, Video_Link �迭�� ����.
			
			for(int i = 0 ; i < Workout.size(); i ++ )
			{
				int j = 0 ;
				
				Img_Link[j] = DB.getImage(Workout_detail.get(i).name,Count.cnt);
				
				
				//� �̸��� �ش�Ǵ� �̹��� ����� .
				if(Img_Link[j].equals("No image"))
				{
					
					JOptionPane.showMessageDialog(null, "� �̹����� �ҷ��� �� �����ϴ�.");
					
				}
				
				Video_Link[j] = DB.getImage(Workout_detail.get(i).name,Count.cnt);
				
				//� �̸��� �ش�Ǵ� ���� ��ũ �����.
				if(Video_Link[j].equals("No video"))
				{
					
					JOptionPane.showMessageDialog(null, "� ���� ��ũ�� �ҷ��� �� �����ϴ�.");
					
				}
				
				i = i+3 ;
				
				j ++ ;
				
			}
			
		}
		
		
		//���� �߻���
		
		else if(Today_Log.equals("Error")) 
			
		{	
				
			JOptionPane.showMessageDialog(null,"�Է��Ͻ� �ý�Ʈ�� �����Դϴ� �ٽ� �Է��ϼ���.");
			

			}
			
		//�α� ���� ��ȯ�Ҷ� ( ���� �� ������ )	
		else 
				
			{
					Date_Log = DB.checkLog(LoginFram.email, Date_Now);
					
					RE = "YES"; //������ 
					
					if(Date_Log.equals("Error")) // checkLog �޼ҵ忡�� ���� �߻���.
						
					{
						
						
						JOptionPane.showMessageDialog(null, "�α� Ȯ�� �� ����ġ ���� ������ �߻��Ͽ����ϴ�..\n ���α׷��� �����մϴ�.");
						System.exit(0);
						
						
						
					}
						
					
					//Date_Log�� ����� � ��� ������ Workout_saved�� ����. 
					
						StringTokenizer st_ln = new StringTokenizer(Date_Log,"%");
						
						
						while(st_ln.hasMoreTokens())
						{
							
							Workout.add(st_ln.nextToken());
							
						}
						
						for(int i = 0 ;  i < Workout.size() ; i++)
						{
							
							StringTokenizer st_att = new StringTokenizer(Workout.get(i),"/");
							
							t.name = st_att.nextToken();
							t.quantity = Integer.parseInt(st_att.nextToken());
							t.unit = st_att.nextToken();
							t.sett = Integer.parseInt(st_att.nextToken());
							
							Workout_saved.add(t);
							
							
							
						}
						
						
						Workout.clear();
				
						
						
						//Today_Log�� ����� ���� ���õ� ��̷µ��� �������� Workout_detail �� ����
						StringTokenizer today_ln = new StringTokenizer(Today_Log,"%");
						
						
						while(today_ln.hasMoreTokens())
						{
							
							
							Workout.add(today_ln.nextToken());
							
						}
						
						for(int i = 0 ;  i < Workout.size() ; i++)
						{
							
							StringTokenizer st_att = new StringTokenizer(Workout.get(i),"/");
							
							t1.name = st_att.nextToken();
							t1.quantity = Integer.parseInt(st_att.nextToken());
							t1.unit = st_att.nextToken();
							t1.sett = Integer.parseInt(st_att.nextToken());
							
							Workout_detail.add(t1);
							
							
							
						}
						
						
						Workout.clear();
						
						
						// ����� ArrayList�� ���� �̹���,���� URL��  Img_Link, Video_Link �迭�� ����.
						
						for(int i = 0 ; i < Workout.size(); i ++ )
						{
							int j = 0 ;
							
							Img_Link[j] = DB.getImage(Workout_saved.get(i).name,Count.cnt);
							
							
							//� �̸��� �ش�Ǵ� �̹��� ����� .
							if(Img_Link[j].equals("No image"))
							{
								
								JOptionPane.showMessageDialog(null, "� �̹����� �ҷ��� �� �����ϴ�.");
								
							}
							
							Video_Link[j] = DB.getImage(Workout_saved.get(i).name,Count.cnt);
							
							//� �̸��� �ش�Ǵ� ���� ��ũ �����.
							if(Video_Link[j].equals("No video"))
							{
								
								JOptionPane.showMessageDialog(null, "� ���� ��ũ�� �ҷ��� �� �����ϴ�.");
								
							}
							
							i = i+3 ;
							
							j ++ ;
							
						}
				
			}
			
			
		
					
				
		
		
		
		/////////////////////////////////////////////////////////////////////////////
								///Setting End.//
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 737, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Plan = new JLabel("Plan \uC0DD\uC131 \uC644\uB8CC!");
		Plan.setFont(new Font("����", Font.BOLD, 22));
		Plan.setBounds(277, -4, 159, 54);
		frame.getContentPane().add(Plan);
		
		JLabel Planis = new JLabel("\uC0DD\uC131\uB41C \uC6B4\uB3D9\uC740 \uB2E4\uC74C\uACFC \uAC19\uC2B5\uB2C8\uB2E4.");
		Planis.setBounds(268, 45, 179, 15);
		frame.getContentPane().add(Planis);
		
		JButton save_button = new JButton("\uC6B4\uB3D9 \uAE30\uB85D \uC800\uC7A5"); 
		save_button.setBounds(12, 10, 132, 23);
		frame.getContentPane().add(save_button);
		
		JButton logout_button = new JButton("\uB85C\uADF8\uC544\uC6C3");
		logout_button.setBounds(612, 10, 97, 23);
		frame.getContentPane().add(logout_button);
		
		// ù��° �̹��� �����̳ʿ� URL�� ���� � �̹��� ����.
		JTextArea exercise_1 = new JTextArea(){
			
			{ setOpaque( false ) ; }
			
			public void paintComponent(Graphics g) {
				try {
					
					
					Image img = new ImageIcon(new URL(Img_Link[0])).getImage();
					g.drawImage(img,0,0,this);
				
				
				} catch(Exception e) {}
				  super.paintComponent(g);
				
				}
			
		};
		
		exercise_1.setBounds(12, 99, 214, 168);
		frame.getContentPane().add(exercise_1);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("\uC6B4\uB3D9 \uD69F\uC218/\uC2DC\uAC04");
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 8));
		lblNewLabel_1.setBounds(12, 277, 57, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\uC2E4\uC2DC\uD55C \uC138\uD2B8 \uC218");
		lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 8));
		lblNewLabel_2.setBounds(123, 277, 57, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		insert_wokrout = new TextField();
		insert_wokrout.setBounds(69, 277, 44, 21);
		frame.getContentPane().add(insert_wokrout);
		insert_wokrout.setColumns(10);
		
		insert_set = new TextField();
		insert_set.setColumns(10);
		insert_set.setBounds(182, 277, 44, 21);
		frame.getContentPane().add(insert_set);
		
	
		
		
		JButton update_button = new JButton("\uC6B4\uB3D9\uAE30\uB85D \uC218\uC815");
		update_button.setFont(new Font("����", Font.PLAIN, 7));
		update_button.setBounds(133, 302, 87, 23);
		frame.getContentPane().add(update_button);
		
		
		
		JTextArea exercise_2 = new JTextArea(){
			
			{ setOpaque( false ) ; }
			
			public void paintComponent(Graphics g) {
				try {
					
					
					Image img = new ImageIcon(new URL(Img_Link[1])).getImage();
					g.drawImage(img,0,0,this);
				
				
				} catch(Exception e) {}
				  super.paintComponent(g);
				
				}
			
		};
		
		
		exercise_2.setBounds(254, 99, 214, 168);
		frame.getContentPane().add(exercise_2);
		
		JLabel label = new JLabel("\uC6B4\uB3D9 \uD69F\uC218/\uC2DC\uAC04");
		label.setFont(new Font("����", Font.PLAIN, 8));
		label.setBounds(254, 277, 57, 15);
		frame.getContentPane().add(label);
		
		insert_wokrout2 = new TextField();
		insert_wokrout2.setColumns(10);
		insert_wokrout2.setBounds(311, 277, 44, 21);
		frame.getContentPane().add(insert_wokrout2);
		
		insert_set2 = new TextField();
		insert_set2.setColumns(10);
		insert_set2.setBounds(424, 277, 44, 21);
		frame.getContentPane().add(insert_set2);
		
		JLabel label_1 = new JLabel("\uC2E4\uC2DC\uD55C \uC138\uD2B8 \uC218");
		label_1.setFont(new Font("����", Font.PLAIN, 8));
		label_1.setBounds(365, 277, 57, 15);
		frame.getContentPane().add(label_1);
		
		
		
		JButton update_button2 = new JButton("\uC6B4\uB3D9\uAE30\uB85D \uC218\uC815");
		update_button2.setFont(new Font("����", Font.PLAIN, 7));
		update_button2.setBounds(375, 302, 87, 23);
		frame.getContentPane().add(update_button2);
		
		
		
		JTextArea exercise_3 = new JTextArea(){
			
			{ setOpaque( false ) ; }
			
			public void paintComponent(Graphics g) {
				try {
					
					
					Image img = new ImageIcon(new URL(Img_Link[2])).getImage();
					g.drawImage(img,0,0,this);
				
				
				} catch(Exception e) {}
				  super.paintComponent(g);
				
				}
			
		};	
		
		
		exercise_3.setBounds(497, 99, 214, 168);
		frame.getContentPane().add(exercise_3);
		
		JLabel label_2 = new JLabel("\uC6B4\uB3D9 \uD69F\uC218/\uC2DC\uAC04");
		label_2.setFont(new Font("����", Font.PLAIN, 8));
		label_2.setBounds(497, 277, 57, 15);
		frame.getContentPane().add(label_2);
		
		insert_wokrout3 = new TextField();
		insert_wokrout3.setColumns(10);
		insert_wokrout3.setBounds(554, 277, 44, 21);
		frame.getContentPane().add(insert_wokrout3);
		
		insert_set3 = new TextField();
		insert_set3.setColumns(10);
		insert_set3.setBounds(667, 277, 44, 21);
		frame.getContentPane().add(insert_set3);
		
		JLabel label_3 = new JLabel("\uC2E4\uC2DC\uD55C \uC138\uD2B8 \uC218");
		label_3.setFont(new Font("����", Font.PLAIN, 8));
		label_3.setBounds(608, 277, 57, 15);
		frame.getContentPane().add(label_3);
		
	
		
		JButton update_button3 = new JButton("\uC6B4\uB3D9\uAE30\uB85D \uC218\uC815");
		update_button3.setFont(new Font("����", Font.PLAIN, 7));
		update_button3.setBounds(618, 302, 87, 23);
		frame.getContentPane().add(update_button3);
		
		JLabel name1 = new JLabel("");
		name1.setBounds(12, 76, 101, 15);
		frame.getContentPane().add(name1);
		
		JLabel Videolink_1 = linkify("��ڼ� �������� �������",Video_Link[0],"Link");
		Videolink_1.setBounds(22, 304, 91, 15);
		frame.getContentPane().add(Videolink_1);
		
		JLabel Videolink_2 = linkify("��ڼ� �������� �������",Video_Link[1],"Link");
		Videolink_2.setBounds(268, 304, 91, 15);
		frame.getContentPane().add(Videolink_2);
		
		JLabel Videolink_3 = linkify("��ڼ� �������� �������",Video_Link[2],"Link");
		Videolink_3.setBounds(507, 304, 91, 15);
		frame.getContentPane().add(Videolink_3);
		
		JLabel unit1 = new JLabel("");
		unit1.setBounds(140, 76, 12, 15);
		frame.getContentPane().add(unit1);
		
		JLabel quantity1 = new JLabel("");
		quantity1.setBounds(123, 76, 12, 15);
		frame.getContentPane().add(quantity1);
		
		JLabel set1 = new JLabel("");
		set1.setBounds(169, 76, 17, 15);
		frame.getContentPane().add(set1);
		
		JLabel lblNewLabel_5 = new JLabel("SET");
		lblNewLabel_5.setBounds(186, 76, 30, 15);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel name2 = new JLabel("");
		name2.setBounds(254, 76, 101, 15);
		frame.getContentPane().add(name2);
		
		JLabel name3 = new JLabel("");
		name3.setBounds(497, 76, 101, 15);
		frame.getContentPane().add(name3);
		
		JLabel quantity2 = new JLabel("");
		quantity2.setBounds(375, 76, 12, 15);
		frame.getContentPane().add(quantity2);
		
		JLabel unit2 = new JLabel("");
		unit2.setBounds(392, 76, 12, 15);
		frame.getContentPane().add(unit2);
		
		JLabel quantity3 = new JLabel("");
		quantity3.setBounds(618, 76, 12, 15);
		frame.getContentPane().add(quantity3);
		
		JLabel unit3 = new JLabel("");
		unit3.setBounds(635, 76, 12, 15);
		frame.getContentPane().add(unit3);
		
		JLabel set2 = new JLabel("");
		set2.setBounds(416, 76, 17, 15);
		frame.getContentPane().add(set2);
		
		JLabel label_11 = new JLabel("SET");
		label_11.setBounds(433, 76, 30, 15);
		frame.getContentPane().add(label_11);
		
		JLabel set3 = new JLabel("");
		set3.setBounds(650, 76, 17, 15);
		frame.getContentPane().add(set3);
		
		JLabel label_13 = new JLabel("SET");
		label_13.setBounds(670, 76, 30, 15);
		frame.getContentPane().add(label_13);
		
		
		
		//������ ���, ���� , ��Ʈ���� �󺧿� Ȱ��ȭ.
		name1.setText(Workout_detail.get(0).name);
		name2.setText(Workout_detail.get(4).name);
		name1.setText(Workout_detail.get(8).name);
		
		quantity1.setText(Integer.toString(Workout_detail.get(1).quantity));
		quantity2.setText(Integer.toString(Workout_detail.get(5).quantity));
		quantity3.setText(Integer.toString(Workout_detail.get(9).quantity));
		
		unit1.setText(Workout_detail.get(2).unit);
		unit2.setText(Workout_detail.get(6).unit);
		unit3.setText(Workout_detail.get(10).unit);
		
		set1.setText(Integer.toString(Workout_detail.get(3).sett));
		set2.setText(Integer.toString(Workout_detail.get(7).sett));
		set3.setText(Integer.toString(Workout_detail.get(11).sett));
		
		
		
		
		// textField�� TextListener Ȱ��ȭ.
		insert_wokrout.addTextListener(this);
		insert_wokrout2.addTextListener(this);
		insert_wokrout3.addTextListener(this);
		
		insert_set.addTextListener(this);
		insert_set2.addTextListener(this);
		insert_set3.addTextListener(this);
		
		
		
		
		//������ �Ͻÿ� ������ ������ ��̷µ� TextField�� ��Ÿ���� ����.
		
		if(RE.contentEquals( "YES" ))

		{	
			
			
			insert_wokrout.setText(Integer.toString(Workout_saved.get(0).quantity));
			insert_wokrout2.setText(Integer.toString(Workout_saved.get(1).quantity));
			insert_wokrout3.setText(Integer.toString(Workout_saved.get(2).quantity));
			
			insert_set.setText(Integer.toString(Workout_saved.get(0).sett));
			insert_set2.setText(Integer.toString(Workout_saved.get(1).sett));
			insert_set3.setText(Integer.toString(Workout_saved.get(2).sett));
			
			
		}
				
		
		
									 //ActionListner//
		//////////////////////////////////////////////////////////////////////////////
		
		
		//� ��� ���� ��ư
		save_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String Logrow = MakeLog();
				
				String Save_result = DB.saveLog(LoginFram.email, Date_Now, Logrow);
				
				if(Save_result.contentEquals("Clear"))
						{
					
							JOptionPane.showMessageDialog(null,"� ����� ���������� ����Ǿ����ϴ�.");
					
						}
				else
						{
							JOptionPane.showMessageDialog(null,"������ �߻��Ͽ� ���������� �������� ���Ͽ����ϴ�.");
						}
				
			}
		});
		
		
		
		//�α׾ƿ� ��ư 
		logout_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int result = JOptionPane.showConfirmDialog(null, "�α׾ƿ� �Ͻðھ��? \n �������� ���� ������ ��ϵ��� �ʽ��ϴ�.", "Logout", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				
				//"��", "�ƴϿ�"�� ���� ���� ���̾�α� â�� ���� ��� Ŭ����
				if(result == JOptionPane.CLOSED_OPTION)
				{
					
					
					JOptionPane.showMessageDialog(null,"�� �Ǵ� �ƴϿ� ��ư�� �����ּ���.");
					
				}
				
				
				// "��" Ŭ����
				else if(result == JOptionPane.YES_OPTION)
				{
					
					frame.setVisible(false);
					LoginFram.main(null);
					
				}
				
				
				// "�ƴϿ�" Ŭ����(�ƹ���ȭ ����.)
				else
				{
					
				
					
				}
			}
		});
		
		
		
		// ù��° ���� ���� ��ư
		
		
		
		
		update_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				update_quantity = insert_wokrout.getText();
				update_set = insert_set.getText();
				
				JOptionPane.showMessageDialog(null,"���� �Ǿ����ϴ�.\n �ݵ�� � ��� ���� ��ư�� ������ �������ּ���!");
				
			}
		});
		
		
		
		update_button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				update_quantity = insert_wokrout2.getText();
				update_set = insert_set2.getText();
				
				JOptionPane.showMessageDialog(null,"���� �Ǿ����ϴ�.\n �ݵ�� � ��� ���� ��ư�� ������ �������ּ���!");
				
				
				
			}
		});
		
		
		
		update_button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				update_quantity = insert_wokrout3.getText();
				update_set = insert_set3.getText();
				
				JOptionPane.showMessageDialog(null,"���� �Ǿ����ϴ�.\n �ݵ�� � ��� ���� ��ư�� ������ �������ּ���!");
				
				
				
			}
		});
		
		
		
		
		
		//////////////////////////////////////////////////////////////////////////////
		   							//ActionListner End.//
		
		
					
	}
	
		//������ �α� ����.
	
	public String MakeLog()
	{
		Inserted_quantity[0] = insert_wokrout.getText();
		Inserted_quantity[1] = insert_wokrout2.getText();
		Inserted_quantity[2] = insert_wokrout3.getText();
		
		
		Inserted_set[0] = insert_set.getText();
		Inserted_set[1] = insert_set2.getText();
		Inserted_set[2] = insert_set3.getText();
		
		String Log = "";
				
		for( int i = 0 ; i < 3 ; i ++)
		{
			   
			   Log = Log + Workout_detail.get(i)+"/"
						 + Inserted_quantity[i]+"/"
						 + Workout_detail.get(i+2)+"/"
						 + Inserted_set[i]+"%";
						
			
			
		}
		
		return Log;
	}
	
	
	
	
		//���� �Է� ���� �޼ҵ�
	
	@Override
	public void textValueChanged(TextEvent e) {
		// TODO Auto-generated method stub
		
		Inserted_quantity[0] = insert_wokrout.getText();
		Inserted_quantity[1] = insert_wokrout2.getText();
		Inserted_quantity[2] = insert_wokrout3.getText();
		
		
		Inserted_set[0] = insert_set.getText();
		Inserted_set[1] = insert_set2.getText();
		Inserted_set[2] = insert_set3.getText();
		
		
	
			
			String Array = Inserted_quantity[0]+Inserted_quantity[1]+Inserted_quantity[2]+Inserted_set[0]+Inserted_set[1]+Inserted_set[2];
			
			
			
			for( int i = 0 ; i < 6 ; i ++)
			{
				
				
				int StringNumber = Array.charAt(i);
				
				
				if( StringNumber < 48 || StringNumber > 57)
				{
					
					
					JOptionPane.showMessageDialog(null,"�Է��Ͻ� �ý�Ʈ�� �����Դϴ� �ٽ� �Է��ϼ���.");
					
					
				}
			}
		
	}
}
