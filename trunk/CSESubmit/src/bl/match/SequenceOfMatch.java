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
      
           if(a1.getSeason().equals(a2.getSeason())){
    	      return a2.getDate().compareTo(a1.getDate());
           }
           else{
    	      return a2.getSeason().compareTo(a1.getSeason());
           }
      
      }
}