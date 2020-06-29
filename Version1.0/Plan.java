package pkg;

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
import java.awt.MediaTracker;
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
	
	private String email;
	private String name;
	private String sex;
	private String purpose;
	private String difficulty;

	private JFrame frame;
	static TextField insert_workout;
	static TextField insert_set;
	static TextField insert_workout2;
	static TextField insert_set2;
	static TextField insert_workout3;
	static TextField insert_set3;
	
	private String Train_info;
	private String Date_Log; //�α׿� ����� ��¥ 
	private String Date_Now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); //���� �ð� Ȯ�� 
	private String Today_Log; // today_Train�� ���ϰ��� �޾Ƽ� ���� �� ���������� �ƴ���  �Ǻ�.
	private String RE;
	
	
	
	String[] Img_Link = new String[3];
	String[] Video_Link = new String[3];
	String[] Inserted_quantity = new String[3]; //�Է��� ��ϵ��� �����ϱ� ���� ���ڿ� ����.
	String[] Inserted_set = new String[3]; //�Է��� ��ϵ��� �����ϱ� ���� ���ڿ� ����.
	
	
	
	ArrayList<String> Workout = new ArrayList<>();
	ArrayList<Train> Workout_detail = new ArrayList<>(); // �ҷ��� �α� ���� ���� �迭����Ʈ.
	ArrayList<Train> Workout_saved = new ArrayList<>();	// ���� �� ���ӽ� ������ �α׵��� ���� �迭����Ʈ.
	
 	
	
	
	
	ConDB DB = new ConDB();
	Train t = new Train();
	Train t1 = new Train();
	
	
	/**
	 * Launch the application.
	 */
	public void main() {
		
		
		EventQueue.invokeLater(new Runnable() {
			
			
			public void run() {
				
				try {
					frame.setVisible(true);
					frame.setVisible(true);
					frame.setResizable(true);
					frame.setLocationRelativeTo(null);
					
					
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
		
		email=LoginFram.email;
		setTitle("ȨƮ���̴� �÷���");
		initialize();
		
		
	}
	
	
	//��ũ�� �ɱ����� �żҵ� 
	
	public JLabel linkify(final String text, String URL, String toolTip)
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
		String user_info=DB.getUserInfo(email);
		StringTokenizer st=new StringTokenizer(user_info,"/");
		ArrayList<String> arr_ui=new ArrayList<>();
		while(st.hasMoreTokens())
		{
			arr_ui.add(st.nextToken());
		}
		name=arr_ui.get(0);
		sex=arr_ui.get(1);
		difficulty=arr_ui.get(2);
		purpose=arr_ui.get(3);
		
								         //Setting//
		//////////////////////////////////////////////////////////////////////////////
		
		
		//�α׵��� ù�α��� ���ο� ������� today_Train ���� �켱 üũ�ѵ� ���� ���� ���� ����� ���� ������. 
		 Today_Log = DB.today_Train(email);
		
		//  ���ϵǴ� ��� ���� ��� ( ������ ����  )
		if(Today_Log.equals("N")) 

		{
			System.out.println("N");
			RE="No";
			Train_info = DB.getTrain_Check(email,difficulty, DB.getTrain(email, purpose, difficulty));
			if(Train_info.equals("Error"))
			{
				JOptionPane.showMessageDialog(null, "������ ���� ��� �ҷ����� �� �߽��ϴ�.");
				return;
			}
			
			//���õ� �α� ���� ��ũ�������� ArrayList�� ����
			
			StringTokenizer st_ln = new StringTokenizer(Train_info,"%");
			
			
			while(st_ln.hasMoreTokens())
			{
				Workout.add(st_ln.nextToken());
				
			}
			for(int i = 0 ;  i < Workout.size() ; i++)
			{
				
				StringTokenizer st_att = new StringTokenizer(Workout.get(i),"/");
				
				
				Train t=new Train();
				t.name = st_att.nextToken();
				t.quantity = Integer.parseInt(st_att.nextToken());
				t.unit = st_att.nextToken();
				t.sett = Integer.parseInt(st_att.nextToken());
				
				
				Workout_detail.add(t);
				
				
			}
			
			
			Workout.clear();
			
			
			// ����� ArrayList�� ���� �̹���,���� URL��  Img_Link, Video_Link �迭�� ����.
			for(int i = 0 ; i < Workout_detail.size(); i ++ )
			{
				Img_Link[i] = DB.getImage(Workout_detail.get(i).name,difficulty);
				//� �̸��� �ش�Ǵ� �̹��� ����� .
				if(Img_Link[i].equals("No image"))
				{
					
					JOptionPane.showMessageDialog(null, "� �̹����� �ҷ��� �� �����ϴ�.");
					
				}
				
				Video_Link[i] = DB.getVideo(Workout_detail.get(i).name,difficulty);
				
				//� �̸��� �ش�Ǵ� ���� ��ũ �����.
				if(Video_Link[i].equals("No video"))
				{
					
					JOptionPane.showMessageDialog(null, "� ���� ��ũ�� �ҷ��� �� �����ϴ�.");
					
				}
				
			}
			
		}
		
		
		//���� �߻���
		
		else if(Today_Log.equals("Error")) 
			
		{	
			RE="Error";
			JOptionPane.showMessageDialog(null,"�Է��Ͻ� �ý�Ʈ�� �����Դϴ� �ٽ� �Է��ϼ���.");
			

			}
			
		//�α� ���� ��ȯ�Ҷ� ( ���� �� ������ )	
		else 	
		{
					Date_Log = DB.checkLog(email, Date_Now);
					
					RE = "NO"; //�Ŀ� ����� �αװ� ���� �� YES�� ����
					
					if(Date_Log.equals("Error")) // checkLog �޼ҵ忡�� ���� �߻���.
						
					{
						
						
						JOptionPane.showMessageDialog(null, "�α� Ȯ�� �� ����ġ ���� ������ �߻��Ͽ����ϴ�..\n ���α׷��� �����մϴ�.");
						System.exit(0);
						
						
						
					}
					
					else if(Date_Log.equals("N"))
					{
                        StringTokenizer today_ln = new StringTokenizer(Today_Log,"%");
						
						
						while(today_ln.hasMoreTokens())
						{
							
							
							Workout.add(today_ln.nextToken());
							
						}
						
						for(int i = 0 ;  i < Workout.size() ; i++)
						{
							
							StringTokenizer st_att = new StringTokenizer(Workout.get(i),"/");
							Train t1=new Train();
							t1.name = st_att.nextToken();
							t1.quantity = Integer.parseInt(st_att.nextToken());
							t1.unit = st_att.nextToken();
							t1.sett = Integer.parseInt(st_att.nextToken());
							
							Workout_detail.add(t1);
							
							
							
						}
						
						
						Workout.clear();
						
						// ����� ArrayList�� ���� �̹���,���� URL��  Img_Link, Video_Link �迭�� ����.
						for(int i = 0 ; i < Workout_detail.size(); i ++ )
						{
							Img_Link[i] = DB.getImage(Workout_detail.get(i).name,difficulty);
							//� �̸��� �ش�Ǵ� �̹��� ����� .
							if(Img_Link[i].equals("No image"))
							{
								
								JOptionPane.showMessageDialog(null, "� �̹����� �ҷ��� �� �����ϴ�.");
								
							}
							
							Video_Link[i] = DB.getVideo(Workout_detail.get(i).name,difficulty);
							
							//� �̸��� �ش�Ǵ� ���� ��ũ �����.
							if(Video_Link[i].equals("No video"))
							{
								
								JOptionPane.showMessageDialog(null, "� ���� ��ũ�� �ҷ��� �� �����ϴ�.");
								
							}
							
						}
						
					}
					
					else
					{
						RE = "YES";
						//Date_Log�� ����� � ��� ������ Workout_saved�� ����. 
						StringTokenizer st_ln = new StringTokenizer(Date_Log,"%");
						
						
						while(st_ln.hasMoreTokens())
						{
							
							Workout.add(st_ln.nextToken());
							
						}
						
						for(int i = 0 ;  i < Workout.size() ; i++)
						{
							StringTokenizer st_att = new StringTokenizer(Workout.get(i),"/");
							Train t=new Train();
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
							Train t1=new Train();
							t1.name = st_att.nextToken();
							t1.quantity = Integer.parseInt(st_att.nextToken());
							t1.unit = st_att.nextToken();
							t1.sett = Integer.parseInt(st_att.nextToken());
							
							Workout_detail.add(t1);
							
							
							
						}
						
						
						Workout.clear();
						
						
						// ����� ArrayList�� ���� �̹���,���� URL��  Img_Link, Video_Link �迭�� ����.
						for(int i = 0 ; i < Workout_detail.size(); i ++ )
						{
							Img_Link[i] = DB.getImage(Workout_detail.get(i).name,difficulty);
							//� �̸��� �ش�Ǵ� �̹��� ����� .
							if(Img_Link[i].equals("No image"))
							{
								
								JOptionPane.showMessageDialog(null, "� �̹����� �ҷ��� �� �����ϴ�.");
								
							}
							
							Video_Link[i] = DB.getVideo(Workout_detail.get(i).name,difficulty);
							
							//� �̸��� �ش�Ǵ� ���� ��ũ �����.
							if(Video_Link[i].equals("No video"))
							{
								
								JOptionPane.showMessageDialog(null, "� ���� ��ũ�� �ҷ��� �� �����ϴ�.");
								
							}
							
						}
					}
					
					
					    
				
			}
			
			
		
					
				
		
		
		
		/////////////////////////////////////////////////////////////////////////////
								///Setting End.//
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1250, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Plan = new JLabel("Plan ���� �Ϸ�!");
		Plan.setFont(new Font("����", Font.BOLD, 22));
		Plan.setBounds(552, 0, 130, 29);
		frame.getContentPane().add(Plan);
		
		JLabel Planis = new JLabel("������ ��õ ��� ������ �����ϴ�");
		Planis.setBounds(504, 30, 220, 16);
		frame.getContentPane().add(Planis);
		
		JButton save_button = new JButton("� ��� ����"); 
		save_button.setBounds(6, 0, 200, 29);
		frame.getContentPane().add(save_button);
		
		JButton modInfo_button=new JButton("ȸ�� ���� ����");
		modInfo_button.setBounds(900,0,200,29);
		frame.getContentPane().add(modInfo_button);
		
		JButton logout_button = new JButton("�α׾ƿ�");
		logout_button.setBounds(1127, 0, 117, 29);
		frame.getContentPane().add(logout_button);
		
		// ù��° �̹��� �����̳ʿ� URL�� ���� � �̹��� ����.
		JPanel exercise_1 = new JPanel(){
			
			{ setOpaque( false ) ; }
			
			public void paintComponent(Graphics g) {
				try {
					
					if(Img_Link[0].equals("No image"))
					{
						Image img = new ImageIcon(new URL("https://t1.daumcdn.net/cfile/blog/2278064A547F7D2402")).getImage();
						Image image=img.getScaledInstance(340, 230, Image.SCALE_SMOOTH);
						MediaTracker tracker = new MediaTracker(this);
						tracker.addImage(image, 0);
						 try{
						         tracker.waitForID(0);
						 }catch(InterruptedException e){e.printStackTrace();}
						g.drawImage(image,0,0,this);
					}
					else
					{
						Image img = new ImageIcon(new URL(Img_Link[0])).getImage();
						Image image=img.getScaledInstance(340, 230, Image.SCALE_SMOOTH);
						MediaTracker tracker = new MediaTracker(this);
						tracker.addImage(image, 0);
						 try{
						         tracker.waitForID(0);
						 }catch(InterruptedException e){e.printStackTrace();}
						g.drawImage(image,0,0,this);
					}
				
				} catch(Exception e) {
					e.printStackTrace();
				}
				  super.paintComponent(g);
				  
				
				}
			
		};
		
		exercise_1.setBounds(6, 70, 360, 250);
		frame.getContentPane().add(exercise_1);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("� Ƚ��/�ð�(��) :");
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(6, 350, 105, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("�ǽ��� ��Ʈ :");
		lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 10));
		lblNewLabel_2.setBounds(200, 350, 105, 20);
		frame.getContentPane().add(lblNewLabel_2);
		
		insert_workout = new TextField();
		insert_workout.setBounds(115, 350, 58, 20);
		frame.getContentPane().add(insert_workout);
		insert_workout.setColumns(10);
		
		insert_set = new TextField();
		insert_set.setColumns(10);
		insert_set.setBounds(310, 350, 58, 20);
		frame.getContentPane().add(insert_set);
		
	
		
		
		JButton update_button = new JButton("���� ����");
		update_button.setFont(new Font("����", Font.PLAIN, 10));
		update_button.setBounds(225, 380, 117, 29);
		frame.getContentPane().add(update_button);
		
		
		
		JPanel exercise_2 = new JPanel(){
			
			{ setOpaque( false ) ; }
			
			public void paintComponent(Graphics g) {
				try {
					
					if(Img_Link[1].equals("No image"))
					{
						Image img = new ImageIcon(new URL("https://t1.daumcdn.net/cfile/blog/2278064A547F7D2402")).getImage();
						Image image=img.getScaledInstance(340, 230, Image.SCALE_SMOOTH);
						MediaTracker tracker = new MediaTracker(this);
						tracker.addImage(image, 0);
						 try{
						         tracker.waitForID(0);
						 }catch(InterruptedException e){e.printStackTrace();}

						g.drawImage(image,0,0,this);
					}
					else
					{
						Image img = new ImageIcon(new URL(Img_Link[1])).getImage();
						Image image=img.getScaledInstance(340, 230, Image.SCALE_SMOOTH);
						MediaTracker tracker = new MediaTracker(this);
						tracker.addImage(image, 0);
						 try{
						         tracker.waitForID(0);
						 }catch(InterruptedException e){e.printStackTrace();}
						g.drawImage(image,0,0,this);
					}
				
				} catch(Exception e) {
					e.printStackTrace();
				}
				  super.paintComponent(g);
				  
				
				}
			
		};
		
		
		exercise_2.setBounds(421, 70, 360, 250);
		frame.getContentPane().add(exercise_2);
		
		JLabel label = new JLabel("� Ƚ��/�ð�(��) :");
		label.setFont(new Font("����", Font.PLAIN, 10));
		label.setBounds(421, 350, 105, 20);
		frame.getContentPane().add(label);
		
		insert_workout2 = new TextField();
		insert_workout2.setColumns(10);
		insert_workout2.setBounds(530, 350, 58, 20);
		frame.getContentPane().add(insert_workout2);
		
		insert_set2 = new TextField();
		insert_set2.setColumns(10);
		insert_set2.setBounds(725, 350, 58, 20);
		frame.getContentPane().add(insert_set2);
		
		JLabel label_1 = new JLabel("�ǽ��� ��Ʈ :");
		label_1.setFont(new Font("����", Font.PLAIN, 10));
		label_1.setBounds(615, 350, 105, 20);
		frame.getContentPane().add(label_1);
		
		
		
		JButton update_button2 = new JButton("���� ����");
		update_button2.setFont(new Font("����", Font.PLAIN, 10));
		update_button2.setBounds(640, 380, 117, 29);
		frame.getContentPane().add(update_button2);
		
		
		
		JPanel exercise_3 = new JPanel(){
			
			{ setOpaque( false ) ; }
			
			public void paintComponent(Graphics g) {
				try {
					
					if(Img_Link[2].equals("No image"))
					{
						Image img = new ImageIcon(new URL("https://t1.daumcdn.net/cfile/blog/2278064A547F7D2402")).getImage();
						Image image=img.getScaledInstance(340, 230, Image.SCALE_SMOOTH);
						MediaTracker tracker = new MediaTracker(this);
						tracker.addImage(image, 0);
						 try{
						         tracker.waitForID(0);
						 }catch(InterruptedException e){e.printStackTrace();}
						g.drawImage(image,0,0,this);
					}
					else
					{
						Image img = new ImageIcon(new URL(Img_Link[2])).getImage();
						Image image=img.getScaledInstance(340, 230, Image.SCALE_SMOOTH);
						MediaTracker tracker = new MediaTracker(this);
						tracker.addImage(image, 0);
						 try{
						         tracker.waitForID(0);
						 }catch(InterruptedException e){e.printStackTrace();}
						g.drawImage(image,0,0,this);
					}
				
				} catch(Exception e) {
					e.printStackTrace();
				}
				  super.paintComponent(g);
				  
				
				}
			
		};	
		
		
		exercise_3.setBounds(836, 70, 360, 250);
		frame.getContentPane().add(exercise_3);
		
		JLabel label_2 = new JLabel("� Ƚ��/�ð�(��) :");
		label_2.setFont(new Font("����", Font.PLAIN, 10));
		label_2.setBounds(836, 350, 105, 20);
		frame.getContentPane().add(label_2);
		
		insert_workout3 = new TextField();
		insert_workout3.setColumns(10);
		insert_workout3.setBounds(945, 350, 58, 20);
		frame.getContentPane().add(insert_workout3);
		
		insert_set3 = new TextField();
		insert_set3.setColumns(10);
		insert_set3.setBounds(1140, 350, 58, 20);
		frame.getContentPane().add(insert_set3);
		
		JLabel label_3 = new JLabel("�ǽ��� ��Ʈ :");
		label_3.setFont(new Font("����", Font.PLAIN, 10));
		label_3.setBounds(1030, 350, 105, 20);
		frame.getContentPane().add(label_3);
		
	
		
		JButton update_button3 = new JButton("���� ����");
		update_button3.setFont(new Font("����", Font.PLAIN, 10));
		update_button3.setBounds(1055, 380, 117, 29);
		frame.getContentPane().add(update_button3);
		
		
		JLabel Videolink_1 = linkify("��ڼ� �������� �������",Video_Link[0],"Link");
		Videolink_1.setBounds(10, 380, 193, 29);
		frame.getContentPane().add(Videolink_1);
		
		JLabel Videolink_2 = linkify("��ڼ� �������� �������",Video_Link[1],"Link");
		Videolink_2.setBounds(415, 380, 193, 29);
		frame.getContentPane().add(Videolink_2);
		
		JLabel Videolink_3 = linkify("��ڼ� �������� �������",Video_Link[2],"Link");
		Videolink_3.setBounds(830, 380, 193, 29);
		frame.getContentPane().add(Videolink_3);
		
		JLabel name1 = new JLabel("");
		name1.setBounds(6,40,150,20);
		frame.getContentPane().add(name1);
		
		JLabel unit1 = new JLabel("");
		unit1.setBounds(210,40,30,20);
		frame.getContentPane().add(unit1);
		
		JLabel quantity1 = new JLabel("");
		quantity1.setBounds(160,40,40,20);
		frame.getContentPane().add(quantity1);
		
		JLabel set1 = new JLabel("");
		set1.setBounds(250,40,30,20);
		frame.getContentPane().add(set1);
		
		JLabel lblNewLabel_5 = new JLabel("SET");
		lblNewLabel_5.setBounds(290,40,80,20);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel name2 = new JLabel("");
		name2.setBounds(421,40,150,20);
		frame.getContentPane().add(name2);
		
		JLabel name3 = new JLabel("");
		name3.setBounds(836,40,150,20);
		frame.getContentPane().add(name3);
		
		JLabel quantity2 = new JLabel("");
		quantity2.setBounds(575,40,40,20);
		frame.getContentPane().add(quantity2);
		
		JLabel unit2 = new JLabel("");
		unit2.setBounds(625,40,30,20);
		frame.getContentPane().add(unit2);
		
		JLabel quantity3 = new JLabel("");
		quantity3.setBounds(990,40,40,20);
		frame.getContentPane().add(quantity3);
		
		JLabel unit3 = new JLabel("");
		unit3.setBounds(1040,40,30,20);
		frame.getContentPane().add(unit3);
		
		JLabel set2 = new JLabel("");
		set2.setBounds(665,40,30,20);
		frame.getContentPane().add(set2);
		
		JLabel label_11 = new JLabel("SET");
		label_11.setBounds(705,40,80,20);
		frame.getContentPane().add(label_11);
		
		JLabel set3 = new JLabel("");
		set3.setBounds(1080,40,30,20);
		frame.getContentPane().add(set3);
		
		JLabel label_13 = new JLabel("SET");
		label_13.setBounds(1120,40,80,20);
		frame.getContentPane().add(label_13);
		
		
		
		//������ ���, ���� , ��Ʈ���� �󺧿� Ȱ��ȭ.
		name1.setText(Workout_detail.get(0).name);
		name2.setText(Workout_detail.get(1).name);
		name3.setText(Workout_detail.get(2).name);
		
		quantity1.setText(Integer.toString(Workout_detail.get(0).quantity));
		quantity2.setText(Integer.toString(Workout_detail.get(1).quantity));
		quantity3.setText(Integer.toString(Workout_detail.get(2).quantity));
		
		unit1.setText(Workout_detail.get(0).unit);
		unit2.setText(Workout_detail.get(1).unit);
		unit3.setText(Workout_detail.get(2).unit);
		
		set1.setText(Integer.toString(Workout_detail.get(0).sett));
		set2.setText(Integer.toString(Workout_detail.get(1).sett));
		set3.setText(Integer.toString(Workout_detail.get(2).sett));
		
		
		
		
		// textField�� TextListener Ȱ��ȭ.
		insert_workout.addTextListener(this);
		insert_workout2.addTextListener(this);
		insert_workout3.addTextListener(this);
		
		insert_set.addTextListener(this);
		insert_set2.addTextListener(this);
		insert_set3.addTextListener(this);
		
		
		
		
		//������ �Ͻÿ� ������ ������ ��̷µ� TextField�� ��Ÿ���� ����.
		
		if(RE.equals( "YES" ))

		{	
			
			
			insert_workout.setText(Integer.toString(Workout_saved.get(0).quantity));
			insert_workout2.setText(Integer.toString(Workout_saved.get(1).quantity));
			insert_workout3.setText(Integer.toString(Workout_saved.get(2).quantity));
			
			insert_set.setText(Integer.toString(Workout_saved.get(0).sett));
			insert_set2.setText(Integer.toString(Workout_saved.get(1).sett));
			insert_set3.setText(Integer.toString(Workout_saved.get(2).sett));
			
			//���� �ҷ��Ȱ� ���ÿ� ���� ���� ���� ���ϰ� �ؽ�Ʈ �ʵ� ���.
			
			insert_workout.setEditable(false);
			insert_workout2.setEditable(false);
			insert_workout3.setEditable(false);
			
			insert_set.setEditable(false);
			insert_set2.setEditable(false);
			insert_set3.setEditable(false);
			
		}
				
		
		
									 //ActionListner//
		//////////////////////////////////////////////////////////////////////////////
		
		
		//� ��� ���� ��ư
		save_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(insert_workout.getText().equals("")||insert_workout2.getText().equals("")||insert_workout3.getText().equals("")
						||insert_set.getText().equals("")||insert_set2.getText().equals("")||insert_set3.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "������ ��� ��ġ�� ��� �������ּ���.");
					return;
				}
				
				String Save_result="";
				String Logrow = MakeLog();
				if(DB.checkLog(email, Date_Now).equals("N"))
				{
			        Save_result = DB.saveLog(email, Date_Now, Logrow,"save");
				}
				else
				{
					Save_result=DB.saveLog(email, Date_Now, Logrow, "modify");
				}
				
				if(Save_result.equals("Clear"))
						{
					
							JOptionPane.showMessageDialog(null,"� ����� ���������� ����Ǿ����ϴ�.");
							insert_workout.setEditable(false);
							insert_workout2.setEditable(false);
							insert_workout3.setEditable(false);
							
							insert_set.setEditable(false);
							insert_set2.setEditable(false);
							insert_set3.setEditable(false);
					
						}
				else
						{
							JOptionPane.showMessageDialog(null,"������ �߻��Ͽ� ���������� �������� ���Ͽ����ϴ�.");
						}
				
			}
		});
		
		
		//ȸ������ ���� ��ư
		modInfo_button.addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent e)
			{
				Change change=new Change(email);
			}
				}
				
				);
		
		
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
					
					frame.dispose();
					LoginFram.main(null);
					
				}
				
				
				// "�ƴϿ�" Ŭ����(�ƹ���ȭ ����.)
				else
				{
					
				
					
				}
			}
		});
		
		
		
		// ù��° ���� ���� ��ư(��ư�� ������ ������  �� �ְ� ����)
		
		
		
		
		update_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				insert_workout.setEditable(true);
				insert_set.setEditable(true);
				
				JOptionPane.showMessageDialog(null,"�ش� ��� �Է°��� ������ �ּ���!");
				
			}
		});
		
		
		
		update_button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				insert_workout2.setEditable(true);
				insert_set2.setEditable(true);
				
				JOptionPane.showMessageDialog(null,"�ش� ��� �Է°��� ������ �ּ���!");
				
				
				
			}
		});
		
		
		
		update_button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				insert_workout3.setEditable(true);
				insert_set3.setEditable(true);
				
				JOptionPane.showMessageDialog(null,"�ش� ��� �Է°��� ������ �ּ���!");
				
				
				
			}
		});
		
		
		
		
		
		//////////////////////////////////////////////////////////////////////////////
		   							//ActionListner End.//
		
		
					
	}
	
		//������ �α� ����.
	
	public String MakeLog()
	{
		Inserted_quantity[0] = insert_workout.getText();
		Inserted_quantity[1] = insert_workout2.getText();
		Inserted_quantity[2] = insert_workout3.getText();
		
		
		Inserted_set[0] = insert_set.getText();
		Inserted_set[1] = insert_set2.getText();
		Inserted_set[2] = insert_set3.getText();
		
		String Log = "";
				
		for( int i = 0 ; i < Workout_detail.size() ; i ++)
		{
			   
			   Log = Log + Workout_detail.get(i).name+"/"
						 + Inserted_quantity[i]+"/"
						 + Workout_detail.get(i).unit+"/"
						 + Inserted_set[i]+"%";
						
			
			
		}
		
		return Log;
	}
	
		//���� �Է� ���� �޼ҵ�
	
	private void inhibitChar(TextField t)
	{
		if(!(t.getText().length()==0))
		{
			for( int i = 0 ; i < t.getText().length() ; i ++)
			{
				
				
				int StringNumber = t.getText().charAt(i);
				
				
				if( StringNumber < 48 || StringNumber > 57)
				{
					
					
					JOptionPane.showMessageDialog(null,"�Է��Ͻ� �ý�Ʈ�� �����Դϴ� �ٽ� �Է��ϼ���.");
					t.setText("");
					
					
				}
			}
		}
		
	}
	
	@Override
	public void textValueChanged(TextEvent e) {
		// TODO Auto-generated method stub
		
		
			
			if((TextField)e.getSource()==insert_workout)
			{
				inhibitChar((TextField)e.getSource());
			}
			else if((TextField)e.getSource()==insert_workout2)
			{
				inhibitChar((TextField)e.getSource());
			}
			else if((TextField)e.getSource()==insert_workout3)
			{
				inhibitChar((TextField)e.getSource());
			}
			else if((TextField)e.getSource()==insert_set)
			{
				inhibitChar((TextField)e.getSource());
			}
			else if((TextField)e.getSource()==insert_set2)
			{
				inhibitChar((TextField)e.getSource());
			}
			else if((TextField)e.getSource()==insert_set3)
			{
				inhibitChar((TextField)e.getSource());
			}
			
			
		
	}
}