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

//데이터베이스 쿼리용 클래스

public class ConDB {

    private Connection con = null;
    private Statement stmt = null;
    private ResultSet result = null;

    private String url = "jdbc:mysql://localhost/hometrainingplanner?serverTimezone=Asia/Seoul";
    private String user = "";
    private String password = "";

    ConDB() //DB 권한 유저 계정 획득 및 DB 접속 조건 세팅
    {
        File file = new File("./DBuser.txt");//DB 권한 유저 계정 파일
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
            System.out.println("드라이버 로딩 오류: " + ed.getMessage());
            return;
        }
        

    }

    public String SignUp(String email, String name, String pw)// 회원가입
    {
        try {
            setDB();
            String sql = "INSERT INTO user_info\n" + "VALUES(''" + email + "','" + name + "','" + pw + "')";
            stmt.executeUpdate(sql);
            // 해당 회원 운동 로그 테이블 생성
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

    public String emailCheck(String email)// 이메일 중복검사
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

    public String init_info(String email, String sex, String difficulty, String purpose)// 첫 로그인 시의 회원 정보 수정
    {
        try {
            setDB();
            String sql = "UPDATE user_info\n" + "SET sex='" + sex + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE user_info\n" + "SET difficulty='" + difficulty + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE user_info\n" + "SET purpose='" + purpose + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            commit();
            return "Clear";
        } catch (Exception e) {
            e.printStackTrace();
            rollback();

        }
        return "";
    }

    public String findPW(String email,String name)//비밀번호 찾기
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

    public String login(String email,String pw)//로그인
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

    public String firstCheck(String email)//첫 로그인인지 확인
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

    public String getUserInfo(String email)//회원정보 불러오기
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

    public String modInfo(String email,String name,String pw,String sex,String difficulty,String purpose)//회원정보 수정
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

    public String getImage(String train,String difficulty)//이미지 링크 가져오기
    {
        String image,sql;
        setDB();
        try
        {
            switch(difficulty)
            {
                case "초급자":
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
                case "중급자":
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
                case "상급자":
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

    public String getVideo(String train,String difficulty)//비디오 링크 가져오기
    {
        String video,sql;
        setDB();
        try
        {
            switch(difficulty)
            {
                case "초급자":
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
                case "중급자":
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
                case "상급자":
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

    public String getTrain(String email,String purpose,String difficulty)//오늘의 운동 새로 가져오기(이 메소드를 getTrain_Check의 train 매개변수로 사용할 것)
    {
        try
        {
            String sql;
            ArrayList<String> list=new ArrayList<>();
            ArrayList<String> trueList=new ArrayList<>();
            setDB();
            if(purpose.equals("지구력 강화"))
            {
            switch(difficulty)
            {
                case "초급자":
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

                HashSet<Integer> randomNum=new HashSet<>();//운동 중복 방지
                while(randomNum.size()<4)
                {
                    randomNum.add((int)Math.random()*list.size());//운동을 랜덤으로 중복 안 되게 추출
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
                
                case "중급자":
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

                HashSet<Integer> randomNum=new HashSet<>();//운동 중복 방지
                while(randomNum.size()<4)
                {
                    randomNum.add((int)Math.random()*list.size());//운동을 랜덤으로 중복 안 되게 추출
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
                case "상급자":
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

                HashSet<Integer> randomNum=new HashSet<>();//운동 중복 방지
                while(randomNum.size()<4)
                {
                    randomNum.add((int)Math.random()*list.size());//운동을 랜덤으로 중복 안 되게 추출
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
            
            else //지구력 강화가 아닐 때
            {
                String train;
                String selected;
                switch(difficulty)
                {
                    case "초급자":
                    train="";

                    //상체 옵션
                    sql="SELECT name FROM train_elementary\n"
                    +"WHERE purpose='"+purpose+"' AND part='상체'";
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

                    //하체 옵션

                    sql="SELECT name FROM train_elementary\n"
                    +"WHERE purpose='"+purpose+"' AND part='하체'";
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

                    //유산소 옵션

                    sql="SELECT name FROM train_elementary\n"
                    +"WHERE purpose='"+purpose+"' AND part='유산소'";
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
                    
                    //리턴

                    commit();
                    return train;
                    }
                    
                    case "중급자":
                    train="";

                    //상체 옵션
                    sql="SELECT name FROM train_middle\n"
                    +"WHERE purpose='"+purpose+"' AND part='상체'";
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

                    //하체 옵션

                    sql="SELECT name FROM train_middle\n"
                    +"WHERE purpose='"+purpose+"' AND part='하체'";
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

                    //유산소 옵션

                    sql="SELECT name FROM train_middle\n"
                    +"WHERE purpose='"+purpose+"' AND part='유산소'";
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
                    
                    //리턴

                    commit();
                    return train;
                    }

                    case "상급자":
                    train="";

                    //상체 옵션
                    sql="SELECT name FROM train_high\n"
                    +"WHERE purpose='"+purpose+"' AND part='상체'";
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

                    //하체 옵션

                    sql="SELECT name FROM train_high\n"
                    +"WHERE purpose='"+purpose+"' AND part='하체'";
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

                    //유산소 옵션

                    sql="SELECT name FROM train_high\n"
                    +"WHERE purpose='"+purpose+"' AND part='유산소'";
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
                    
                    //리턴

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

    public String getTrain_Check(String email,String train)//기존의 운동 로그와 비교해 오늘의 운동을 새로 가져오기
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

    private String[] getDate()//최근 5일까지의 데이터를 얻기 위함.
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

    public String checkLog(String email,String date)//해당 날짜의 운동 체크
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

    public String saveLog(String email,String date,String log)//로그 저장
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

    private void setDB()//DB 연결
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

    private void commit()//DB에 commit 후 객체 닫기
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

    private void rollback()//DB rollback 후 객체 닫기
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