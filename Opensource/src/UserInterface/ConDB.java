package UserInterface;



import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

import Encrypting;
import Mail;
import MakePW;
import Train;

//�뜲�씠�꽣踰좎씠�뒪 荑쇰━�슜 �겢�옒�뒪

public class ConDB {

    private Connection con = null;
    private Statement stmt = null;
    private ResultSet result = null;

    private String url = "jdbc:mysql://localhost/hometrainingplanner?serverTimezone=Asia/Seoul";
    private String user = "";
    private String password = "";

    ConDB() //DB 沅뚰븳 �쑀�� 怨꾩젙 �쉷�뱷 諛� DB �젒�냽 議곌굔 �꽭�똿
    {
        File file = new File("./DBuser.txt");//DB 沅뚰븳 �쑀�� 怨꾩젙 �뙆�씪
        try {
            ArrayList<String> al = new ArrayList<>();
            String readData;
            FileReader fr=new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try {
                while ((readData = br.readLine()) != null) 
                {
                    al.add(readData);
                }
                user=al.get(0);
                password=al.get(1);
                br.close();
                fr.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }

        catch (java.lang.ClassNotFoundException ed) {
            System.out.print("ClassNotFoundException: ");
            System.out.println("�뱶�씪�씠踰� 濡쒕뵫 �삤瑜�: " + ed.getMessage());
            return;
        }
        

    }

    public String SignUp(String email, String name, String pw)// �쉶�썝媛��엯
    {
        try {
            setDB();
            String sql = "INSERT INTO user_info\n" + "VALUES(''" + email + "','" + name + "','" + pw + "')";
            stmt.executeUpdate(sql);
            // �빐�떦 �쉶�썝 �슫�룞 濡쒓렇 �뀒�씠釉� �깮�꽦
            sql = "CREATE TABLE `hometrainingplanner`.`" + email + "_train_log` (\n" + "`date` VARCHAR(45) NOT NULL,\n"
                    + "`log` VARCHAR(5000) NOT NULL,\n" + "PRIMARY KEY(`date`));";
            stmt.execute(sql);
            commit();
            return "Clear";
        } catch (SQLException e) {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String emailCheck(String email)// �씠硫붿씪 以묐났寃��궗
    {
        try {
            setDB();
            String sql = "SELECT email FROM user_info\n" + "WHERE email='" + email + "'";
            result = stmt.executeQuery(sql);
            if (result.getString("email") == null) {
                commit();
                return "E";
            } else {
                commit();
                return "N";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String init_info(String email, String sex, String difficulty, String purpose)// 泥� 濡쒓렇�씤 �떆�쓽 �쉶�썝 �젙蹂� �닔�젙
    {
        try {
            setDB();
            String sql = "UPDATE user_info\n" + "SET sex='" + sex + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE user_info\n" + "SET difficulty='" + difficulty + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE user_info\n" + "SET purpose='" + purpose + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql="UPDATE user_info\n"
            +"SET first="+0
            +"WHERE email='"+email+"'";
            stmt.executeUpdate(sql);
            commit();
            return "Clear";
        } catch (Exception e) {
            e.printStackTrace();
            rollback();

        }
        return "";
    }

    public String findPW(String email,String name)//鍮꾨�踰덊샇 李얘린
    {
        try{
            setDB();
            String sql="SELECT email,name FROM user_info\n"
            +"WHERE email='"+email+"'AND name='"+name+"'";
            result=stmt.executeQuery(sql);
            if(result.getString(0)==null)
            {
                commit();
                return "E";
            }
            else
            {
                Mail mail=new Mail();
                String pw=MakePW.mkpw();
                mail.send(result.getString("email"),pw);
                sql="UPDATE user_info\n"
                +"SET pw=''"+Encrypting.encrypt(pw)+"'\n"
                +"WHERE email='"+email+"'";
                stmt.executeUpdate(sql);
                commit();
                return "Clear";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String login(String email,String pw)//濡쒓렇�씤
    {
        try{
            setDB();
            String sql="SELECT email FROM user_info\n"
            +"WHERE email='"+email+"'";
            result=stmt.executeQuery(sql);
            if(result.getString("email")==null)
            {
                commit();
                return "email";
            }
            else
            {
                sql="SELECT pw FROM user_info\n"
                +"WHERE email='"+email+"'";
                result=stmt.executeQuery(sql);
                if(result.getString("pw").equals(Encrypting.encrypt(pw)))
                {
                    commit();
                    return "OK";
                }
                else
                {
                    commit();
                    return "pw";
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String firstCheck(String email)//泥� 濡쒓렇�씤�씤吏� �솗�씤
    {
        try
        {
            setDB();
            String sql="SELECT first FROM user_info\n"
            +"WHERE email='"+email+"'";
            result=stmt.executeQuery(sql);
            if(result.getInt("first")==1)
            {
                commit();
                return "F";
            }
            else
            {
                commit();
                return "N";
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            rollback();
            return "Error";
        }

    }

    public String getUserInfo(String email)//�쉶�썝�젙蹂� 遺덈윭�삤湲�
    {
        try
        {
            setDB();
            String sql="SELECT name,sex,difficulty,purpose FROM user_info\n"
            +"WHERE email='"+email+"'";
            result=stmt.executeQuery(sql);
            if(result.getString("name")==null)
            {
                commit();
                return "Invalid Email";
            }
            else
            {
                String toReturn=result.getString("name")+"/"
                +result.getString("sex")+"/"
                +result.getString("difficulty")+"/"
                +result.getString("purpose");
                commit();
                return toReturn;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String modInfo(String email,String name,String pw,String sex,String difficulty,String purpose)//�쉶�썝�젙蹂� �닔�젙
    {
        try
        {
            setDB();
            String sql="UPDATE user_info\n" + "SET name='" + name + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE user_info\n" + "SET pw='" + Encrypting.encrypt(pw) + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE user_info\n" + "SET sex='" + sex + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE user_info\n" + "SET difficulty='" + difficulty + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE user_info\n" + "SET purpose='" + purpose + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            commit();
            return "Clear";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }
    
    public String getImage(String train,String difficulty)//�씠誘몄� 留곹겕 媛��졇�삤湲�
    {
        String image,sql;
        setDB();
        try
        {
            switch(difficulty)
            {
                case "珥덇툒�옄":
                sql="SELECT image FROM train_elementary\n"
                +"WHERE name='"+train+"'";
                result=stmt.executeQuery(sql);
                if((image=result.getString("image"))==null)
                {
                    commit();
                    return "No image";
                }
                else
                {
                    commit();
                    return image;
                }
                case "以묎툒�옄":
                sql="SELECT image FROM train_middle\n"
                +"WHERE name='"+train+"'";
                result=stmt.executeQuery(sql);
                if((image=result.getString("image"))==null)
                {
                    commit();
                    return "No image";
                }
                else
                {
                    commit();
                    return image;
                }
                case "�긽湲됱옄":
                sql="SELECT image FROM train_high\n"
                +"WHERE name='"+train+"'";
                result=stmt.executeQuery(sql);
                if((image=result.getString("image"))==null)
                {
                    commit();
                    return "No image";
                }
                else
                {
                    commit();
                    return image;
                }
                default:
                return "Invalid Difficulty";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String getVideo(String train,String difficulty)//鍮꾨뵒�삤 留곹겕 媛��졇�삤湲�
    {
        String video,sql;
        setDB();
        try
        {
            switch(difficulty)
            {
                case "珥덇툒�옄":
                sql="SELECT video FROM train_elementary\n"
                +"WHERE name='"+train+"'";
                result=stmt.executeQuery(sql);
                if((video=result.getString("image"))==null)
                {
                    commit();
                    return "No video";
                }
                else
                {
                    commit();
                    return video;
                }
                case "以묎툒�옄":
                sql="SELECT video FROM train_middle\n"
                +"WHERE name='"+train+"'";
                result=stmt.executeQuery(sql);
                if((video=result.getString("image"))==null)
                {
                    commit();
                    return "No video";
                }
                else
                {
                    commit();
                    return video;
                }
                case "�긽湲됱옄":
                sql="SELECT video FROM train_high\n"
                +"WHERE name='"+train+"'";
                result=stmt.executeQuery(sql);
                if((video=result.getString("image"))==null)
                {
                    commit();
                    return "No video";
                }
                else
                {
                    commit();
                    return video;
                }
                default:
                commit();
                return "Invalid Difficulty";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String getTrain(String email,String purpose,String difficulty)//�삤�뒛�쓽 �슫�룞 �깉濡� 媛��졇�삤湲�(�씠 硫붿냼�뱶瑜� getTrain_Check�쓽 train 留ㅺ컻蹂��닔濡� �궗�슜�븷 寃�)
    {
        try
        {
            String sql;
            ArrayList<String> list=new ArrayList<>();
            ArrayList<String> trueList=new ArrayList<>();
            setDB();
            if(purpose.equals("吏�援щ젰 媛뺥솕"))
            {
            switch(difficulty)
            {
                case "珥덇툒�옄":
                sql="SELECT name FROM train_elementary\n"
                +"WHERE purpose='"+purpose+"'";
                result=stmt.executeQuery(sql);
            if(result.getString("name")==null)
            {
                rollback();
                return "Invalid Value";
            }
            else
            {
                String train="";
                while(result.next())
                {
                    list.add(result.getString("name"));
                }

                HashSet<Integer> randomNum=new HashSet<>();//�슫�룞 以묐났 諛⑹�
                while(randomNum.size()<4)
                {
                    randomNum.add((int)Math.random()*list.size());//�슫�룞�쓣 �옖�뜡�쑝濡� 以묐났 �븞 �릺寃� 異붿텧
                }
                Iterator it=randomNum.iterator();
                while(it.hasNext())
                {
                    trueList.add(list.get((int)it.next()));
                }

                for(int i=0;i<trueList.size();i++)
                {
                    sql="SELECT name,quantity,unit,sett FROM train_elementary\n"
                    +"WHERE name='"+trueList.get(i)+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";
                }
                commit();
                return train;
            }
                
                case "以묎툒�옄":
                sql="SELECT name FROM train_middle\n"
                +"WHERE purpose='"+purpose+"'";
                result=stmt.executeQuery(sql);
            if(result.getString("name")==null)
            {
                rollback();
                return "Invalid Value";
            }
            else
            {
                String train="";
                while(result.next())
                {
                    list.add(result.getString("name"));
                }

                HashSet<Integer> randomNum=new HashSet<>();//�슫�룞 以묐났 諛⑹�
                while(randomNum.size()<4)
                {
                    randomNum.add((int)Math.random()*list.size());//�슫�룞�쓣 �옖�뜡�쑝濡� 以묐났 �븞 �릺寃� 異붿텧
                }
                Iterator it=randomNum.iterator();
                while(it.hasNext())
                {
                    trueList.add(list.get((int)it.next()));
                }

                for(int i=0;i<trueList.size();i++)
                {
                    sql="SELECT name,quantity,unit,sett FROM train_middle\n"
                    +"WHERE name='"+trueList.get(i)+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";
                }
                commit();
                return train;
            }
                case "�긽湲됱옄":
                sql="SELECT name FROM train_high\n"
                +"WHERE purpose='"+purpose+"'";
                result=stmt.executeQuery(sql);
            if(result.getString("name")==null)
            {
                rollback();
                return "Invalid Value";
            }
            else
            {
                String train="";
                while(result.next())
                {
                    list.add(result.getString("name"));
                }

                HashSet<Integer> randomNum=new HashSet<>();//�슫�룞 以묐났 諛⑹�
                while(randomNum.size()<4)
                {
                    randomNum.add((int)Math.random()*list.size());//�슫�룞�쓣 �옖�뜡�쑝濡� 以묐났 �븞 �릺寃� 異붿텧
                }
                Iterator it=randomNum.iterator();
                while(it.hasNext())
                {
                    trueList.add(list.get((int)it.next()));
                }

                for(int i=0;i<trueList.size();i++)
                {
                    sql="SELECT name,quantity,unit,sett FROM train_high\n"
                    +"WHERE name='"+trueList.get(i)+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";
                }
                commit();
                return train;
            }
                default:
                commit();
                return "Invalid Difficulty";
            }
            

            }
            
            else //吏�援щ젰 媛뺥솕媛� �븘�땺 �븣
            {
                String train;
                String selected;
                switch(difficulty)
                {
                    case "珥덇툒�옄":
                    train="";

                    //�긽泥� �샃�뀡
                    sql="SELECT name FROM train_elementary\n"
                    +"WHERE purpose='"+purpose+"' AND part='�긽泥�'";
                    result=stmt.executeQuery(sql);
                    if(result.getString("name")==null)
                    {
                        rollback();
                        return "Invalid Difficulty";
                    }
                    else
                    {
                        
                while(result.next())
                {
                    list.add(result.getString("name"));
                }
                selected=list.get((int)Math.random()*list.size());
        
                sql="SELECT name,quantity,unit,sett FROM train_elementary\n"
                    +"WHERE name='"+selected+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";

                    list.clear();
                     
                    }

                    //�븯泥� �샃�뀡

                    sql="SELECT name FROM train_elementary\n"
                    +"WHERE purpose='"+purpose+"' AND part='�븯泥�'";
                    result=stmt.executeQuery(sql);
                    if(result.getString("name")==null)
                    {
                        rollback();
                        return "Invalid Difficulty";
                    }
                    else
                    {
                        
                while(result.next())
                {
                    list.add(result.getString("name"));
                }
                selected=list.get((int)Math.random()*list.size());
        
                sql="SELECT name,quantity,unit,sett FROM train_elementary\n"
                    +"WHERE name='"+selected+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";

                    list.clear();
                     
                    }

                    //�쑀�궛�냼 �샃�뀡

                    sql="SELECT name FROM train_elementary\n"
                    +"WHERE purpose='"+purpose+"' AND part='�쑀�궛�냼'";
                    result=stmt.executeQuery(sql);
                    if(result.getString("name")==null)
                    {
                        rollback();
                        return "Invalid Difficulty";
                    }
                    else
                    {
                        
                while(result.next())
                {
                    list.add(result.getString("name"));
                }
                selected=list.get((int)Math.random()*list.size());
        
                sql="SELECT name,quantity,unit,sett FROM train_elementary\n"
                    +"WHERE name='"+selected+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";

                    list.clear();
                    
                    //由ы꽩

                    commit();
                    return train;
                    }
                    
                    case "以묎툒�옄":
                    train="";

                    //�긽泥� �샃�뀡
                    sql="SELECT name FROM train_middle\n"
                    +"WHERE purpose='"+purpose+"' AND part='�긽泥�'";
                    result=stmt.executeQuery(sql);
                    if(result.getString("name")==null)
                    {
                        rollback();
                        return "Invalid Difficulty";
                    }
                    else
                    {
                        
                while(result.next())
                {
                    list.add(result.getString("name"));
                }
                selected=list.get((int)Math.random()*list.size());
        
                sql="SELECT name,quantity,unit,sett FROM train_middle\n"
                    +"WHERE name='"+selected+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";

                    list.clear();
                     
                    }

                    //�븯泥� �샃�뀡

                    sql="SELECT name FROM train_middle\n"
                    +"WHERE purpose='"+purpose+"' AND part='�븯泥�'";
                    result=stmt.executeQuery(sql);
                    if(result.getString("name")==null)
                    {
                        rollback();
                        return "Invalid Difficulty";
                    }
                    else
                    {
                        
                while(result.next())
                {
                    list.add(result.getString("name"));
                }
                selected=list.get((int)Math.random()*list.size());
        
                sql="SELECT name,quantity,unit,sett FROM train_middle\n"
                    +"WHERE name='"+selected+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";

                    list.clear();
                     
                    }

                    //�쑀�궛�냼 �샃�뀡

                    sql="SELECT name FROM train_middle\n"
                    +"WHERE purpose='"+purpose+"' AND part='�쑀�궛�냼'";
                    result=stmt.executeQuery(sql);
                    if(result.getString("name")==null)
                    {
                        rollback();
                        return "Invalid Difficulty";
                    }
                    else
                    {
                        
                while(result.next())
                {
                    list.add(result.getString("name"));
                }
                selected=list.get((int)Math.random()*list.size());
        
                sql="SELECT name,quantity,unit,sett FROM train_middle\n"
                    +"WHERE name='"+selected+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";

                    list.clear();
                    
                    //由ы꽩

                    commit();
                    return train;
                    }

                    case "�긽湲됱옄":
                    train="";

                    //�긽泥� �샃�뀡
                    sql="SELECT name FROM train_high\n"
                    +"WHERE purpose='"+purpose+"' AND part='�긽泥�'";
                    result=stmt.executeQuery(sql);
                    if(result.getString("name")==null)
                    {
                        rollback();
                        return "Invalid Difficulty";
                    }
                    else
                    {
                        
                while(result.next())
                {
                    list.add(result.getString("name"));
                }
                selected=list.get((int)Math.random()*list.size());
        
                sql="SELECT name,quantity,unit,sett FROM train_high\n"
                    +"WHERE name='"+selected+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";

                    list.clear();
                     
                    }

                    //�븯泥� �샃�뀡

                    sql="SELECT name FROM train_high\n"
                    +"WHERE purpose='"+purpose+"' AND part='�븯泥�'";
                    result=stmt.executeQuery(sql);
                    if(result.getString("name")==null)
                    {
                        rollback();
                        return "Invalid Difficulty";
                    }
                    else
                    {
                        
                while(result.next())
                {
                    list.add(result.getString("name"));
                }
                selected=list.get((int)Math.random()*list.size());
        
                sql="SELECT name,quantity,unit,sett FROM train_high\n"
                    +"WHERE name='"+selected+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";

                    list.clear();
                     
                    }

                    //�쑀�궛�냼 �샃�뀡

                    sql="SELECT name FROM train_high\n"
                    +"WHERE purpose='"+purpose+"' AND part='�쑀�궛�냼'";
                    result=stmt.executeQuery(sql);
                    if(result.getString("name")==null)
                    {
                        rollback();
                        return "Invalid Difficulty";
                    }
                    else
                    {
                        
                while(result.next())
                {
                    list.add(result.getString("name"));
                }
                selected=list.get((int)Math.random()*list.size());
        
                sql="SELECT name,quantity,unit,sett FROM train_high\n"
                    +"WHERE name='"+selected+"'";
                    stmt.executeQuery(sql);
                    train=train+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";

                    list.clear();
                    
                    //由ы꽩

                    commit();
                    return train;
                    }
                    default:
                    rollback();
                    return "Invalid Difficulty";

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String getTrain_Check(String email,String train)//湲곗〈�쓽 �슫�룞 濡쒓렇�� 鍮꾧탳�빐 �삤�뒛�쓽 �슫�룞�쓣 �깉濡� 媛��졇�삤湲�
    {
        ArrayList<Train> arr_train=new ArrayList<>();
        ArrayList<String> arr_log=new ArrayList<>();
        StringTokenizer st_ln=new StringTokenizer(train,"%");
        ArrayList<String> train_ln=new ArrayList<>();
        while(st_ln.hasMoreTokens())
        {
            train_ln.add(st_ln.nextToken());
        }
        for(int i=0;i<train_ln.size();i++)
        {
            StringTokenizer st_att=new StringTokenizer(train_ln.get(i),"/");
            Train t=new Train();
            t.name=st_att.nextToken();
            t.quantity=Integer.parseInt(st_att.nextToken());
            t.unit=st_att.nextToken();
            t.sett=Integer.parseInt(st_att.nextToken());
            arr_train.add(t);
        }

        train_ln.clear();
        String[] recent=getDate();
        for(int i=0;i<recent.length;i++)
        {
            String log=checkLog(email,recent[i]);
            if(log.equals("N"))
            {
                
            }
            else if(log.equals("Error"))
            {
                return "Error";
            }
            else
            {
                arr_log.add(log);
            }
        }
        for(int i=0;i<arr_log.size();i++)
        {
            st_ln=new StringTokenizer(arr_log.get(i),"%");
            while(st_ln.hasMoreTokens())
            {
                train_ln.add(st_ln.nextToken());
            }
            for(int j=0;j<train_ln.size();j++)
            {
                StringTokenizer st_att=new StringTokenizer(train_ln.get(j),"/");
                for(int k=0;k<arr_train.size();k++)
                {
                    if(arr_train.get(k).name.equals(st_att.nextToken()))
                    {
                        arr_train.get(k).log_quan.add(Integer.parseInt(st_att.nextToken()));
                        String unit=st_att.nextToken();
                        arr_train.get(k).log_sett.add(Integer.parseInt(st_att.nextToken()));
                    }
                }
            }            
        }

        for(int i=0;i<arr_train.size();i++)
        {
            arr_train.get(i).setQuan();
            arr_train.get(i).setSett();
        }

        String toReturn="";

        for(int i=0;i<arr_train.size();i++)
        {
            toReturn=toReturn+arr_train.get(i).name+"/"
            +arr_train.get(i).quantity+"/"
            +arr_train.get(i).unit+"/"
            +arr_train.get(i).sett+"%";
        }
        return toReturn;
    }

    private String[] getDate()//理쒓렐 5�씪源뚯��쓽 �뜲�씠�꽣瑜� �뼸湲� �쐞�븿.
    {
        String[] recent=new String[6];
        for(int i=0;i<6;i++)
        {
            Date date=new Date();
            date=new Date(date.getTime()+(1000*60*60*24*(-i)));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd",Locale.KOREA);
            recent[i]=sdf.format(date);
        }
        return recent;
    }

    public String checkLog(String email,String date)//�빐�떦 �궇吏쒖쓽 �슫�룞 泥댄겕
    {
        try{
            setDB();
            String sql="SELECT date FROM "+email+"_train_log\n"
            +"WHERE date='"+date+"'";
            result=stmt.executeQuery(sql);
            if(result.getString("date")==null)
            {
                commit();
                return "N";
            }
            else
            {
                String log=result.getString("log");
                commit();
                return log;
            }
        }
        catch(Exception e)
        {
            rollback();
            return "Error";
        }
    }

    public String saveLog(String email,String date,String log)//濡쒓렇 ���옣
    {
        try{
            setDB();
            String sql="INSERT INTO "+email+"_train_log\n"
            +"VALUES('"+date+",',"+log+"')";
            stmt.executeUpdate(sql);
            commit();
            return "Clear";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            rollback();
            return "Error";
        }
        
    }

    private void setDB()//DB �뿰寃�
    {
        try
        {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void commit()//DB�뿉 commit �썑 媛앹껜 �떕湲�
    {
        try
        {
            stmt.execute("COMMIT;");
            if(!result.isClosed())result.close();
            if(!stmt.isClosed())stmt.close();
            if(!con.isClosed())con.close();

        }
        catch(SQLException e)
        {
            e.printStackTrace();

        }
      }

    private void rollback()//DB rollback �썑 媛앹껜 �떕湲�
    {
        try
        {
            stmt.execute("ROLLBACK;");
            if(!result.isClosed())result.close();
            if(!stmt.isClosed())stmt.close();
            if(!con.isClosed())con.close();

        }
        catch(SQLException e)
        {
            e.printStackTrace();

        }
    }
}