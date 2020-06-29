package pkg;

import java.util.ArrayList;

public class Train 
{
    String name;
    int quantity;
    String unit;
    int sett;

    ArrayList<Integer> log_quan=new ArrayList<>();
    ArrayList<Integer> log_sett=new ArrayList<>();

    
    public void setQuan()//운동 quantity 값 평균 내기
    {
    	if(log_quan.size()==0)
    	{
    		return;
    	}
      for(int i=0;i<log_quan.size();i++)
      {
        quantity+=log_quan.get(i);
      }
      double result=(double)quantity/((double)log_quan.size()+(double)1);
      quantity=(int)Math.round(result);
    }

    public void setSett()//운동 sett 값 평균 내기
    {
    	if(log_sett.size()==0)
    	{
    		return;
    	}
      for(int i=0;i<log_sett.size();i++)
      {
        sett+=log_sett.get(i);
      }
      double result=(double)sett/((double)log_sett.size()+(double)1);
      sett=(int)Math.round(result);
    }
    
    

    
  
}