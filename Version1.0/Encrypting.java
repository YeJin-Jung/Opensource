package pkg;

import java.security.MessageDigest;


public class Encrypting {


  private Encrypting()
  {
    //스태틱 메소드만 이용하도록 함
  }

  static String encrypt(String pw)
  {
    String sha="";
    try
    {
      MessageDigest sh=MessageDigest.getInstance("SHA-256");
      sh.update(pw.getBytes());
      byte byteData[]=sh.digest();
      StringBuffer sb=new StringBuffer();
      for(int i=0;i<byteData.length;i++)
      {
        sb.append(Integer.toString((byteData[i]&0xff)+0x100,16).substring(1));
      }
      sha=sb.toString();
    }
    catch(Exception e){
      e.printStackTrace();
      sha=null;
    }
    return sha;
  }
  
}