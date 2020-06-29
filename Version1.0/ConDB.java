package pkg;

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
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Stack;

//�����ͺ��̽� ������ Ŭ����

public class ConDB {

    private Connection con = null;
    private Statement stmt = null;
    private ResultSet result = null;

    private String url = "jdbc:mysql://192.168.0.138/hometraining?serverTimezone=Asia/Seoul";
    private String user = "";
    private String password = "";

    ConDB() // DB ���� ���� ���� ȹ�� �� DB ���� ���� ����
    {
        File file = new File("./DBuser.txt");// DB ���� ���� ���� ����
        try {
            ArrayList<String> al = new ArrayList<>();
            String readData;
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try {
                while ((readData = br.readLine()) != null) {
                    al.add(readData);
                }
                AccDecode ad = new AccDecode();
                user = ad.getPlain(al.get(0));
                password = ad.getPlain(al.get(1));
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
            System.out.println("����̹� �ε� ����: " + ed.getMessage());
            return;
        }

    }

    public String SignUp(String email, String name, String pw)// ȸ������
    {
        try {
            setDB();
            String sql = "INSERT INTO user_info\n" + "VALUES('" + email + "','" + name + "','" + Encrypting.encrypt(pw) + "',null,null,null,1)";
            stmt.executeUpdate(sql);
            sql = "UPDATE user_info\n" + "SET first=1\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            // �ش� ȸ�� � �α� ���̺� ����
            sql = "CREATE TABLE `hometraining`.`" + email + "_train_log` (\n" + "`date` VARCHAR(45) NOT NULL,\n"
                    + "`log` VARCHAR(5000) NOT NULL,\n" + "PRIMARY KEY(`date`));";
            stmt.execute(sql);
            sql = "CREATE TABLE `hometraining`.`" + email + "_today` (\n" + "`name` VARCHAR(100) NOT NULL,\n"
                    + "`quantity` INT(11) NOT NULL,\n" + "`unit` VARCHAR(10) NOT NULL,\n" + "`sett` INT(11) NOT NULL,\n"
                    + "PRIMARY KEY(`name`));";
            stmt.execute(sql);
            signUpCommit();
            return "Clear";
        } catch (SQLException e) {
            e.printStackTrace();
            signUpRollback();
            return "Error";
        }
    }

    public String emailCheck(String email)// �̸��� �ߺ��˻�
    {
        try {
            setDB();
            String sql = "SELECT email FROM user_info\n" + "WHERE email='" + email + "'";
            result = stmt.executeQuery(sql);
            if (!result.next()) {
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

    public String init_info(String email, String sex, String difficulty, String purpose)// ù �α��� ���� ȸ�� ���� ����
    {
        try {
            setDB();
            String sql = "UPDATE `hometraining`.`user_info`\n" + "SET sex='" + sex + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE `hometraining`.`user_info`\n" + "SET difficulty='" + difficulty + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE `hometraining`.`user_info`\n" + "SET purpose='" + purpose + "'\n" + "WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            sql = "UPDATE user_info\n" + "SET first=" + 0 + " WHERE email='" + email + "'";
            stmt.executeUpdate(sql);
            signUpCommit();
            return "Clear";
        } catch (Exception e) {
            e.printStackTrace();
            signUpRollback();
            return "Error";

        }
        
    }

    public String findPW(String email, String name)// ��й�ȣ ã��
    {
        try {
            setDB();
            String sql = "SELECT email,name FROM user_info\n" + "WHERE email='" + email + "'AND name='" + name + "'";
            result = stmt.executeQuery(sql);
            if (!result.next()) {
                commit();
                return "E";
            } else {
                Mail mail = new Mail();
                String pw = MakePW.mkpw();
                mail.send(result.getString("email"), pw);
                sql = "UPDATE user_info\n" + "SET pw='" + Encrypting.encrypt(pw) + "'\n" + "WHERE email='" + email
                        + "'";
                stmt.executeUpdate(sql);
                commit();
                return "Clear";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String login(String email, String pw)// �α���
    {
        try {
            setDB();
            String sql = "SELECT email FROM user_info\n" + "WHERE email='" + email + "'";
            result = stmt.executeQuery(sql);
            if (!result.next()) {
                commit();
                return "email";
            } 
            else 
            {
                sql = "SELECT pw FROM user_info\n" + "WHERE email='" + email + "'";
                ResultSet result_pw = stmt.executeQuery(sql);
                if(result_pw.next())
                {
                	if (result_pw.getString("pw").equals(Encrypting.encrypt(pw))) {
                    	result_pw.close();
                        commit();
                        return "OK";
                    } else {
                    	result_pw.close();
                        commit();
                        return "pw";
                    }
                }
                else return "Error";
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String firstCheck(String email)// ù �α������� Ȯ��
    {
        try {
            setDB();
            String sql = "SELECT first FROM user_info\n" + "WHERE email='" + email + "'";
            result = stmt.executeQuery(sql);
            if(result.next())
            {
            	if (result.getInt("first") == 1) {
                    commit();
                    return "F";
                } else {
                    commit();
                    return "N";
                }
            }
            else return "Error";

        } catch (Exception e) {
            e.printStackTrace();
            rollback();
            return "Error";
        }

    }

    public String getUserInfo(String email)// ȸ������ �ҷ�����
    {
        try {
            setDB();
            String sql = "SELECT name,sex,difficulty,purpose FROM user_info\n" + "WHERE email='" + email + "'";
            result = stmt.executeQuery(sql);
            if (!result.next()) {
                commit();
                return "Invalid Email";
            } else {
                String toReturn = result.getString("name") + "/" + result.getString("sex") + "/"
                        + result.getString("difficulty") + "/" + result.getString("purpose");
                commit();
                return toReturn;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String modInfo(String email, String name, String pw, String sex, String difficulty, String purpose)// ȸ������ ����
    {
        try {
            setDB();
            String sql = "UPDATE user_info\n" + "SET name='" + name + "'\n" + "WHERE email='" + email + "'";
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
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String getImage(String train, String difficulty)// �̹��� ��ũ ��������
    {
        String image, sql;
        setDB();
        try {
            switch (difficulty) {
                case "�ʱ���":
                    sql = "SELECT image FROM `hometraining`.`train_elementary`\n" + "WHERE name='" + train + "'";
                    result = stmt.executeQuery(sql);
                    if(!result.next())
                    {
                    	commit();
                    	return "No image";
                    }
                    else if(result.getString("image")==null)
                    {
                    	commit();
                    	return "No image";
                    }
                    else {
                    	image=result.getString("image");
                        commit();
                        return image;
                    }
                case "�߱���":
                    sql = "SELECT image FROM `hometraining`.`train_middle`\n" + "WHERE name='" + train + "'";
                    result = stmt.executeQuery(sql);
                    if(!result.next())
                    {
                    	commit();
                    	return "No image";
                    }
                    else if(result.getString("image")==null)
                    {
                    	commit();
                    	return "No image";
                    }
                    else {
                    	image=result.getString("image");
                        commit();
                        return image;
                    }
                case "�����":
                    sql = "SELECT image FROM `hometraining`.`train_high`\n" + "WHERE name='" + train + "'";
                    result = stmt.executeQuery(sql);
                    if(!result.next())
                    {
                    	commit();
                    	return "No image";
                    }
                    else if(result.getString("image")==null)
                    {
                    	commit();
                    	return "No image";
                    }
                    else {
                    	image=result.getString("image");
                        commit();
                        return image;
                    }
                default:
                    return "Invalid Difficulty";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String getVideo(String train, String difficulty)// ���� ��ũ ��������
    {
        String video, sql;
        setDB();
        try {
            switch (difficulty) {
                case "�ʱ���":
                    sql = "SELECT video FROM `hometraining`.`train_elementary`\n" + "WHERE name='" + train + "'";
                    result = stmt.executeQuery(sql);
                    if (!result.next()) 
                    {
                        commit();
                        return "No video";
                    } 
                    else if(result.getString("video")==null)
                    {
                    	commit();
                    	return "No video";
                    }
                    else 
                    {
                    	video=result.getString("video");
                        commit();
                        return video;
                    }
                case "�߱���":
                    sql = "SELECT video FROM `hometraining`.`train_middle`\n" + "WHERE name='" + train + "'";
                    result = stmt.executeQuery(sql);
                    if (!result.next()) 
                    {
                        commit();
                        return "No video";
                    } 
                    else if(result.getString("video")==null)
                    {
                    	commit();
                    	return "No video";
                    }
                    else 
                    {
                    	video=result.getString("video");
                        commit();
                        return video;
                    }
                case "�����":
                    sql = "SELECT video FROM `hometraining`.`train_high`\n" + "WHERE name='" + train + "'";
                    result = stmt.executeQuery(sql);
                    if (!result.next()) 
                    {
                        commit();
                        return "No video";
                    } 
                    else if(result.getString("video")==null)
                    {
                    	commit();
                    	return "No video";
                    }
                    else 
                    {
                    	video=result.getString("video");
                        commit();
                        return video;
                    }
                default:
                    commit();
                    return "Invalid Difficulty";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String getTrain(String email, String purpose, String difficulty)// ������ � ���� ��������(�� �޼ҵ带 getTrain_Check��
                                                                           // train �Ű������� ����� ��)
    {
        try {
            String sql;
            ArrayList<String> list = new ArrayList<>();
            setDB();
            if (purpose.equals("������ ��ȭ")) {
                switch (difficulty) {
                    case "�ʱ���":
                        sql = "SELECT name FROM `hometraining`.`train_elementary`\n" + "WHERE purpose='" + purpose + "'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Value";
                        } else {
                            String train = "";
                            list.add(result.getString("name"));
                            while (result.next()) {
                                list.add(result.getString("name"));
                                System.out.println(result.getString("name"));
                            }

                            HashSet<Integer> randomNum = new HashSet<>();// � �ߺ� ����
                            Random r=new Random();
                            while(randomNum.size()<3) {
                            	
                                randomNum.add(r.nextInt(list.size()));// ��� �������� �ߺ� �� �ǰ� ����
                            }
                            Iterator<Integer> it = randomNum.iterator();
                            Stack<String> stack = new Stack<>();
                            while (it.hasNext()) {
                                stack.push(list.get(it.next()));
                            }
                            int stacksize=stack.size();

                            for (int i = 0; i < stacksize; i++) {
                                sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_elementary`\n" + "WHERE name='"
                                        + stack.pop() + "'";
                                result=stmt.executeQuery(sql);
                                if(result.next())
                                {
                                	train = train + result.getString("name") + "/"
                                            + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                            + "/" + Integer.toString(result.getInt("sett")) + "%";
                                }
                                
                            }
                            commit();
                            return train;
                        }

                    case "�߱���":
                        sql = "SELECT name FROM `hometraining`.`train_middle`\n" + "WHERE purpose='" + purpose + "'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Value";
                        } else {
                            String train = "";
                            while (result.next()) {
                                list.add(result.getString("name"));
                            }

                            HashSet<Integer> randomNum = new HashSet<>();// � �ߺ� ����
                            Random r=new Random();
                            while(randomNum.size()<3) {
                            	
                                randomNum.add(r.nextInt(list.size()));// ��� �������� �ߺ� �� �ǰ� ����
                            }
                            Iterator it = randomNum.iterator();
                            Stack<String> stack = new Stack<>();

                            while (it.hasNext()) {
                                stack.push(list.get((int) it.next()));
                            }
                            
                            int stacksize=stack.size();

                            for (int i = 0; i < stacksize; i++) {
                                sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_middle`\n" + "WHERE name='"
                                        + stack.pop() + "'";
                                result=stmt.executeQuery(sql);
                                if(result.next())
                                {
                                	train = train + result.getString("name") + "/"
                                            + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                            + "/" + Integer.toString(result.getInt("sett")) + "%";
                                }
                            }
                            commit();
                            return train;
                        }
                    case "�����":
                        sql = "SELECT name FROM `hometraining`.`train_high`\n" + "WHERE purpose='" + purpose + "'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Value";
                        } else {
                            String train = "";
                            while (result.next()) {
                                list.add(result.getString("name"));
                            }

                            HashSet<Integer> randomNum = new HashSet<>();// � �ߺ� ����
                            Random r=new Random();
                            int d=0;
                            while(randomNum.size()<3) {
                            	System.out.println(d++);
                                randomNum.add(r.nextInt(list.size()));// ��� �������� �ߺ� �� �ǰ� ����
                            }
                            Iterator it = randomNum.iterator();
                            Stack<String> stack = new Stack<>();
                            while (it.hasNext()) {
                                stack.push(list.get((int) it.next()));
                            }
                            int stacksize=stack.size();

                            for (int i = 0; i < stacksize; i++) {
                                sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_high`\n" + "WHERE name='" + stack.pop()
                                        + "'";
                                result=stmt.executeQuery(sql);
                                if(result.next())
                                {
                                	train = train + result.getString("name") + "/"
                                            + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                            + "/" + Integer.toString(result.getInt("sett")) + "%";
                                }
                            }
                            commit();
                            System.out.println(train);
                            return train;
                        }
                    default:
                        commit();
                        return "Invalid Difficulty";
                }

            }

            else // ������ ��ȭ�� �ƴ� ��
            {
                String train;
                String selected;
                switch (difficulty) {
                    case "�ʱ���":
                        train = "";

                        // ��ü �ɼ�
                        sql = "SELECT name FROM `hometraining`.`train_elementary`\n" + "WHERE purpose='" + purpose + "' AND part='��ü'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Difficulty";
                        } else {

                            while (result.next()) {
                                list.add(result.getString("name"));
                            }
                            Random r=new Random();
                            selected = list.get((int) r.nextInt(list.size()));

                            sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_elementary`\n" + "WHERE name='" + selected
                                    + "'";
                            result=stmt.executeQuery(sql);
                            if(result.next())
                            {
                            	train = train + result.getString("name") + "/"
                                        + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                        + "/" + Integer.toString(result.getInt("sett")) + "%";
                            }
                            

                            list.clear();

                        }

                        // ��ü �ɼ�

                        sql = "SELECT name FROM `hometraining`.`train_elementary`\n" + "WHERE purpose='" + purpose + "' AND part='��ü'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Difficulty";
                        } else {

                            while (result.next()) {
                                list.add(result.getString("name"));
                            }
                            Random r=new Random();
                            selected = list.get((int) r.nextInt(list.size()));

                            sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_elementary`\n" + "WHERE name='" + selected
                                    + "'";
                            result=stmt.executeQuery(sql);
                            if(result.next())
                            {
                            	train = train + result.getString("name") + "/"
                                        + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                        + "/" + Integer.toString(result.getInt("sett")) + "%";
                            }

                            list.clear();

                        }

                        // ����� �ɼ�

                        sql = "SELECT name FROM `hometraining`.`train_elementary`\n" + "WHERE purpose='" + purpose + "' AND part='�����'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Difficulty";
                        } else {

                            while (result.next()) {
                                list.add(result.getString("name"));
                            }
                            Random r=new Random();
                            selected = list.get((int) r.nextInt(list.size()));

                            sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_elementary`\n" + "WHERE name='" + selected
                                    + "'";
                            result=stmt.executeQuery(sql);
                            if(result.next())
                            {
                            	train = train + result.getString("name") + "/"
                                        + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                        + "/" + Integer.toString(result.getInt("sett")) + "%";
                            }

                            list.clear();

                            // ����

                            commit();
                            System.out.println(train);
                            return train;
                        }

                    case "�߱���":
                        train = "";

                        // ��ü �ɼ�
                        sql = "SELECT name FROM `hometraining`.`train_middle`\n" + "WHERE purpose='" + purpose + "' AND part='��ü'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Difficulty";
                        } else {

                            while (result.next()) {
                                list.add(result.getString("name"));
                            }
                            Random r=new Random();
                            selected = list.get((int) r.nextInt(list.size()));

                            sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_middle`\n" + "WHERE name='" + selected
                                    + "'";
                            result=stmt.executeQuery(sql);
                            if(result.next())
                            {
                            	train = train + result.getString("name") + "/"
                                        + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                        + "/" + Integer.toString(result.getInt("sett")) + "%";
                            }

                            list.clear();

                        }

                        // ��ü �ɼ�

                        sql = "SELECT name FROM `hometraining`.`train_middle`\n" + "WHERE purpose='" + purpose + "' AND part='��ü'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Difficulty";
                        } else {

                            while (result.next()) {
                                list.add(result.getString("name"));
                            }
                            Random r=new Random();
                            selected = list.get((int) r.nextInt(list.size()));

                            sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_middle`\n" + "WHERE name='" + selected
                                    + "'";
                            result=stmt.executeQuery(sql);
                            if(result.next())
                            {
                            	train = train + result.getString("name") + "/"
                                        + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                        + "/" + Integer.toString(result.getInt("sett")) + "%";
                            }

                            list.clear();

                        }

                        // ����� �ɼ�

                        sql = "SELECT name FROM `hometraining`.`train_middle`\n" + "WHERE purpose='" + purpose + "' AND part='�����'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Difficulty";
                        } else {

                            while (result.next()) {
                                list.add(result.getString("name"));
                            }
                            Random r=new Random();
                            selected = list.get((int) r.nextInt(list.size()));

                            sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_middle`\n" + "WHERE name='" + selected
                                    + "'";
                            result=stmt.executeQuery(sql);
                            if(result.next())
                            {
                            	train = train + result.getString("name") + "/"
                                        + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                        + "/" + Integer.toString(result.getInt("sett")) + "%";
                            }

                            list.clear();

                            // ����

                            commit();
                            return train;
                        }

                    case "�����":
                        train = "";

                        // ��ü �ɼ�
                        sql = "SELECT name FROM `hometraining`.`train_high`\n" + "WHERE purpose='" + purpose + "' AND part='��ü'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Difficulty";
                        } else {

                            while (result.next()) {
                                list.add(result.getString("name"));
                            }
                            Random r=new Random();
                            selected = list.get((int) r.nextInt(list.size()));

                            sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_high`\n" + "WHERE name='" + selected + "'";
                            result=stmt.executeQuery(sql);
                            if(result.next())
                            {
                            	train = train + result.getString("name") + "/"
                                        + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                        + "/" + Integer.toString(result.getInt("sett")) + "%";
                            }

                            list.clear();

                        }

                        // ��ü �ɼ�

                        sql = "SELECT name FROM `hometraining`.`train_high`\n" + "WHERE purpose='" + purpose + "' AND part='��ü'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Difficulty";
                        } else {

                            while (result.next()) {
                                list.add(result.getString("name"));
                            }
                            Random r=new Random();
                            selected = list.get((int) r.nextInt(list.size()));

                            sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_high`\n" + "WHERE name='" + selected + "'";
                            result=stmt.executeQuery(sql);
                            if(result.next())
                            {
                            	train = train + result.getString("name") + "/"
                                        + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                        + "/" + Integer.toString(result.getInt("sett")) + "%";
                            }

                            list.clear();

                        }

                        // ����� �ɼ�

                        sql = "SELECT name FROM `hometraining`.`train_high`\n" + "WHERE purpose='" + purpose + "' AND part='�����'";
                        result = stmt.executeQuery(sql);
                        if (!result.next()) {
                            rollback();
                            return "Invalid Difficulty";
                        } else {

                            while (result.next()) {
                                list.add(result.getString("name"));
                            }
                            Random r=new Random();
                            selected = list.get((int) r.nextInt(list.size()));

                            sql = "SELECT name,quantity,unit,sett FROM `hometraining`.`train_high`\n" + "WHERE name='" + selected + "'";
                            result=stmt.executeQuery(sql);
                            if(result.next())
                            {
                            	train = train + result.getString("name") + "/"
                                        + Integer.toString(result.getInt("quantity")) + "/" + result.getString("unit")
                                        + "/" + Integer.toString(result.getInt("sett")) + "%";
                            }

                            list.clear();

                            // ����

                            commit();
                            return train;
                        }
                    default:
                        rollback();
                        return "Invalid Difficulty";

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String getTrain_Check(String email,String difficulty, String train)// ������ � �α׿� ���� ������ ��� ���� ��������
    {
    	String copy_train=train;
        ArrayList<Train> arr_train = new ArrayList<>();
        ArrayList<String> arr_log = new ArrayList<>();
        StringTokenizer st_ln = new StringTokenizer(train, "%");
        ArrayList<String> train_ln = new ArrayList<>();
        while (st_ln.hasMoreTokens()) {
            train_ln.add(st_ln.nextToken());
        }
        for (int i = 0; i < train_ln.size(); i++) {
            StringTokenizer st_att = new StringTokenizer(train_ln.get(i), "/");
            Train t = new Train();
            t.name = st_att.nextToken();
            t.quantity = Integer.parseInt(st_att.nextToken());
            t.unit = st_att.nextToken();
            t.sett = Integer.parseInt(st_att.nextToken());
            arr_train.add(t);
        }

        train_ln.clear();
        String[] recent = getDate();
        for (int i = 0; i < recent.length; i++) {
            String log = checkLog(email, recent[i]);
            if (log.equals("N")) 
            {

            } 
            else if (log.equals("Error")) 
            {
                return "Error";
            } 
            else 
            {
                arr_log.add(log);
            }
        }
        if(arr_log.size()==0)
        {
        	for(int i=0;i<arr_train.size();i++)
        	{
        		String sql = "INSERT INTO `hometraining`.`" + email + "_today`\n" + "VALUES('" + arr_train.get(i).name + "',"
                        + arr_train.get(i).quantity + ",'" + arr_train.get(i).unit + "'," + arr_train.get(i).sett + ");";
                try {
                	setDB();
                    stmt.executeUpdate(sql);
                    signUpCommit();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    signUpRollback();
                }
        	}
        	return copy_train;
        }
        else
        {
        	for (int i = 0; i < arr_log.size(); i++) {
                st_ln = new StringTokenizer(arr_log.get(i), "%");
                while (st_ln.hasMoreTokens()) 
                {
                    train_ln.add(st_ln.nextToken());
                }
                for (int j = 0; j < arr_train.size(); j++) {
                    StringTokenizer st_att = new StringTokenizer(train_ln.get(i), "/");
                    if (arr_train.get(j).name.equals(st_att.nextToken())) {
                        arr_train.get(j).log_quan.add(Integer.parseInt(st_att.nextToken()));
                        String unit = st_att.nextToken();
                        arr_train.get(j).log_sett.add(Integer.parseInt(st_att.nextToken()));

                    }
                }
                train_ln.clear();
            }
        	

            for (int i = 0; i < arr_train.size(); i++) {
                arr_train.get(i).setQuan();
                arr_train.get(i).setSett();
            }
            
            String toReturn = "";

            for (int i = 0; i < arr_train.size(); i++) {
                toReturn = toReturn + arr_train.get(i).name + "/" + arr_train.get(i).quantity + "/" + arr_train.get(i).unit
                        + "/" + arr_train.get(i).sett + "%";

            }
            for (int i = 0; i < arr_train.size(); i++) {
                String sql = "INSERT INTO `hometraining`.`" + email + "_today`\n" + "VALUES('" + arr_train.get(i).name + "',"
                        + arr_train.get(i).quantity + ",'" + arr_train.get(i).unit + "'," + arr_train.get(i).sett + ");";
                try {
                	setDB();
                    stmt.executeUpdate(sql);
                    signUpCommit();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    signUpRollback();
                }
            }
            return toReturn;
        }
        
    }

    public String today_Train(String email)//���� ���õ� � �� �Ҵ緮�� �������� �޼ҵ�
    {
        setDB();
        try
        {
            String sql="SELECT * FROM `hometraining`.`"+email+"_today`";
            result=stmt.executeQuery(sql);
            if(!result.next())
            {
                return "N";
            }
            else
            {
                String log="";
                log=log+result.getString("name")+"/"
                        +Integer.toString(result.getInt("quantity"))+"/"
                        +result.getString("unit")+"/"
                        +Integer.toString(result.getInt("sett"))+"%";
                while(result.next())
                {
                    log=log+result.getString("name")+"/"
                    +Integer.toString(result.getInt("quantity"))+"/"
                    +result.getString("unit")+"/"
                    +Integer.toString(result.getInt("sett"))+"%";
                }
                return log; 
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            rollback();
            return "Error";
        }
    }
    
    private String[] getDate()//�ֱ� 5�ϱ����� �����͸� ��� ����.
    {
        String[] recent=new String[6];
        for(int i=0;i<6;i++)
        {
            Date date=new Date();
            date=new Date(date.getTime()+(1000*60*60*24*(-i)));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA);
            recent[i]=sdf.format(date);
            System.out.println(recent[i]);
        }
        
        return recent;
    }

    public String checkLog(String email,String date)//�ش� ��¥�� � üũ
    {
        try{
            setDB();
            String sql="SELECT log FROM `hometraining`.`"+email+"_train_log`\n"
            +"WHERE date='"+date+"'";
            result=stmt.executeQuery(sql);
            if(!result.next())
            {
                commit();
                return "N";
            }
            else
            {
                String log=result.getString("log");
                System.out.println("�α�:"+log);
                commit();
                return log;
            }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            rollback();
            return "Error";
        }
    }

    public String saveLog(String email,String date,String log,String option)//�α� ����
    {
        try{
            setDB();
            if(option.equals("modify"))
            {
            	String sql="DELETE FROM `hometraining`.`"+email+"_train_log`\n"
            			+"WHERE date='"+date+"'";
            	stmt.execute(sql);
            }
            String sql="INSERT INTO `hometraining`.`"+email+"_train_log`\n"
            +"VALUES('"+date+"','"+log+"')";
            stmt.executeUpdate(sql);
            signUpCommit();
            return "Clear";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            signUpRollback();
            return "Error";
        }
        
    }

    private void setDB()//DB ����
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

    private void commit()//DB�� commit �� ��ü �ݱ�
    {
        try
        {
            stmt.execute("COMMIT;");
            if(!result.isClosed())result.close();
            if(!stmt.isClosed()||stmt!=null)stmt.close();
            if(!con.isClosed()||con!=null)con.close();

        }
        catch(SQLException e)
        {
            e.printStackTrace();

        }
      }
    
    private void signUpCommit()//SignUp �� ������ ���� ���� �� DB�� commit �� ��ü �ݱ�
    {
        try
        {
            stmt.execute("COMMIT;");
            if(!stmt.isClosed()||stmt!=null)stmt.close();
            if(!con.isClosed()||con!=null)con.close();

        }
        catch(SQLException e)
        {
            e.printStackTrace();

        }
      }

    private void rollback()//DB rollback �� ��ü �ݱ�
    {
        try
        {
            stmt.execute("ROLLBACK;");
            if(!result.isClosed()||result!=null)result.close();
            if(!stmt.isClosed()||stmt!=null)stmt.close();
            if(!con.isClosed()||con!=null)con.close();

        }
        catch(SQLException e)
        {
            e.printStackTrace();

        }
    }
    
    private void signUpRollback()//SignUp �� ������ ���� ���� �� DB rollback �� ��ü �ݱ�
    {
        try
        {
            stmt.execute("ROLLBACK;");
            if(!stmt.isClosed()||stmt!=null)stmt.close();
            if(!con.isClosed()||con!=null)con.close();

        }
        catch(SQLException e)
        {
            e.printStackTrace();

        }
    }
}
