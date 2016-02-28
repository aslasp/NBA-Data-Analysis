package bl.match;

import java.util.Comparator;

import vo.MatchVO;

/**
 * 时间越近的排在前面
 * @author dell
 *
 */
public	class SequenceOfMatch implements Comparator<MatchVO> {

      public int compare(MatchVO a1, MatchVO a2) { 
      
    	  String s1=a1.getSeason()+"_"+a1.getDate();
    	  String s2=a2.getSeason()+"_"+a2.getDate();
          return compareDate(s1, s2);
      
      }
      
      private int compareDate(String s1,String s2){
  		String[] buffer1=s1.split("_");
  		String[] buffer2=s2.split("_");
  		String season1=buffer1[0];
  		String date1=buffer1[1];
  		String season2=buffer2[0];
  		String date2=buffer2[1];
  		
  		if(season1.compareTo(season2)<0)
  			return -1;
  		else if(season1.compareTo(season2)>0)
  			return 1;
  		else{
  			String month1=date1.split("-")[0];
  			String month2=date2.split("-")[0];
  			int type1=0;
  			int type2=0; //type=0代表在6月前，=1代表在6月后
  			if(month1.compareTo("06")>0){
  				type1=1;
  			}
  			if(month2.compareTo("06")>0){
  				type2=1;
  			}
  			
  			if(type1==0&&type2==1){
  				return 1;
  			}
  			
  			if(type1==1&&type2==0){
  				return-1;
  			}
  			
  			//下面是2个都是6月前或6月后的情况
  		     	if(date1.compareTo(date2)<0)
  				   return -1;
  			    else if(date1.compareTo(date2)>0)
  				   return 1;
  			    else 
  				   return 0;
  		     	
  		}
  	}
}