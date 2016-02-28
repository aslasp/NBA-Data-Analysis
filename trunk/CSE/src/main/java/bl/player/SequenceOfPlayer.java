package bl.player;

import java.util.ArrayList;
import java.util.Comparator;

import vo.PlayerVO;


public	class SequenceOfPlayer implements Comparator<PlayerVO> {
	
    String sortBy;
    String order;
    public SequenceOfPlayer(){
		this("name");
	}

	public SequenceOfPlayer(String s){
		super();
		sortBy=s;
		order="asc";
	}
	
	public SequenceOfPlayer(String sortBy,String order){
		super();
		this.sortBy=sortBy;
		this.order=order;
	}
	
    public int compare(PlayerVO a1, PlayerVO a2) { 
    	int result=0;
    	
        if(sortBy.equals("name"))
        	result= nameCompare(a1.getName(), a2.getName());      
        else if(sortBy.equals("playedGames")){
		    result= Integer.compare(a1.getPlayedGames(), a2.getPlayedGames());
        }
        else if(sortBy.equals("gameStartingNum")){
        	result=Integer.compare(a1.getGameStartingNum(), a2.getGameStartingNum());
        }
        else if(sortBy.equals("reboundNum")){
       	    result=Double.compare(a1.getReboundNum(), a2.getReboundNum());
       }
        else if(sortBy.equals("assistNum")){
          	result=Double.compare(a1.getAssistNum(), a2.getAssistNum());
          }
        else if(sortBy.equals("presentTime")){
        	result=Double.compare(a1.getPresentTime(), a2.getPresentTime());
         }
        else if(sortBy.equals("shootHitNum")){
        	result=Double.compare(a1.getShootHitNum(), a2.getShootHitNum());
        }
        else if(sortBy.equals("shootAttemptNum")){
        	result=Double.compare(a1.getShootAttemptNum(), a2.getShootAttemptNum());
        }
        else if(sortBy.equals("shootHitRate")){
        	result=Double.compare(a1.getShootHitRate(), a2.getShootHitRate());
        }
        else if(sortBy.equals("threeHitNum")){
        	result=Double.compare(a1.getThreeHitNum(), a2.getThreeHitNum());
        }
        else if(sortBy.equals("threeAttemptNum")){
        	result=Double.compare(a1.getThreeAttemptNum(), a2.getThreeAttemptNum());
        }
        else if(sortBy.equals("threeHitRate")){
        	result=Double.compare(a1.getThreeHitRate(), a2.getThreeHitRate());
        }
        else if(sortBy.equals("freeThrowHitNum")){
        	result=Double.compare(a1.getFreeThrowHitNum(), a2.getFreeThrowHitNum());
        }
        else if(sortBy.equals("freeThrowAttemptNum")){
        	result=Double.compare(a1.getFreeThrowAttemptNum(), a2.getFreeThrowAttemptNum());
        }
        else if(sortBy.equals("freeThrowHitRate")){
        	result=Double.compare(a1.getFreeThrowHitRate(), a2.getFreeThrowHitRate());
        }
        else if(sortBy.equals("offenReboundNum")){
        	result=Double.compare(a1.getOffenReboundNum(), a2.getOffenReboundNum());
        }
        else if(sortBy.equals("defenReboundNum")){
        	result=Double.compare(a1.getDefenReboundNum(), a2.getDefenReboundNum());
        }
        else if(sortBy.equals("stealNum")){
        	result=Double.compare(a1.getStealNum(), a2.getStealNum());
        }
        else if(sortBy.equals("blockNum")){
        	result=Double.compare(a1.getBlockNum(), a2.getBlockNum());
        }
        else if(sortBy.equals("foulNum")){
        	result=Double.compare(a1.getFoulNum(), a2.getFoulNum());
        }
        else if(sortBy.equals("turnOverNum")){
        	result=Double.compare(a1.getTurnOverNum(), a2.getTurnOverNum());
        }
        else if(sortBy.equals("score")){
        	result=Double.compare(a1.getScore(), a2.getScore());
        }
        else if(sortBy.equals("efficiency")){
        	result=Double.compare(a1.getEfficiency(), a2.getEfficiency());
        }
        else if(sortBy.equals("GmScEfficiencyValue")){
        	result=Double.compare(a1.getGmScEfficiencyValue(), a2.getGmScEfficiencyValue());
        }
        else if(sortBy.equals("trueHitRate")){
        	result=Double.compare(a1.getTrueHitRate(), a2.getTrueHitRate());
        }
        else if(sortBy.equals("shootEfficiency")){
        	result=Double.compare(a1.getShootHitEfficiency(), a2.getShootHitEfficiency());
        }
        else if(sortBy.equals("reboundRate")){
        	result=Double.compare(a1.getReboundRate(), a2.getReboundRate());
        }
        else if(sortBy.equals("offenReboundRate")){
        	result=Double.compare(a1.getOffenReboundRate(), a2.getOffenReboundRate());
        }
        else if(sortBy.equals("defenReboundRate")){
        	result=Double.compare(a1.getDefenReboundRate(), a2.getDefenReboundRate());
        }
        else if(sortBy.equals("assistRate")){
        	result=Double.compare(a1.getAssistRate(), a2.getAssistRate());
        }
        else if(sortBy.equals("stealRate")){
        	result=Double.compare(a1.getStealRate(), a2.getStealRate());
        }
        else if(sortBy.equals("blockRate")){
        	result=Double.compare(a1.getBlockRate(), a2.getBlockRate());
        }
        else if(sortBy.equals("turnOverRate")){
        	result=Double.compare(a1.getTurnOverRate(), a2.getTurnOverRate());
        }
        else if(sortBy.equals("usageRate")){
        	result=Double.compare(a1.getUsageRate(), a2.getUsageRate());
        }
        else if(sortBy.equals("score_rebound_assist")){
        	result=Double.compare(a1.getScore_rebound_assist(), a2.getScore_rebound_assist());
        }
        else if(sortBy.equals("doubleDoubleNum")){
        	result=Double.compare(a1.getDoubleDoubleNum(), a2.getDoubleDoubleNum());
        }
        else if(sortBy.equals("age")){
        	result=Integer.compare(a1.getAge(), a2.getAge());
        }
        else if(sortBy.equals("exp")){
        	result=Integer.compare(a1.getExp(), a2.getExp());
        }
        else if(sortBy.equals("weight")){
        	result=Integer.compare(a1.getWeight(), a2.getWeight());
        }
        else if(sortBy.equals("height")){
        	String[] buffer1=a1.getHeight().split("-");
        	String[] buffer2=a2.getHeight().split("-");
        	int foot1=Integer.parseInt(buffer1[0]);
        	int inch1=Integer.parseInt(buffer1[1]);
        	int foot2=Integer.parseInt(buffer2[0]);
        	int inch2=Integer.parseInt(buffer2[1]);
        	if(foot1<foot2)
        		result=-1;
        	else if(foot1>foot2)
        		result=1;
        	else{
        		if(inch1<inch2)
        			result=-1;
        		else if(inch1>inch2)
        			result=1;
        		else 
					result=0;
        	}
        	
        }
        else if(sortBy.equals("recentFiveMatchesScoreUpRate")){
        	result=Double.compare(a1.getRecentFiveMatchesScoreUpRate(), a2.getRecentFiveMatchesScoreUpRate());
        }
        else if(sortBy.equals("recentFiveMatchesReboundUpRate")){
        	result=Double.compare(a1.getRecentFiveMatchesReboundUpRate(), a2.getRecentFiveMatchesReboundUpRate());
        }
        else if(sortBy.equals("recentFiveMatchesAssistUpRate")){
        	result=Double.compare(a1.getRecentFiveMatchesAssistUpRate(), a2.getRecentFiveMatchesAssistUpRate());
        }
        else {
			result=-1;
		}
		    
		    if (order.equals("desc")) {
				// 降序
				return (-result);
			} else {
				// 升序(默认)
				return result;
			}
    
    }
    
    public int nameCompare(String name1,String name2){
    	
			String[] nameTemp1=name1.split(" ");
			String[] nameTemp2=name2.split(" ");
			int size1=nameTemp1.length;
			int size2=nameTemp2.length;		
			String xing1=nameTemp1[size1-1];
			String xing2=nameTemp2[size2-1];
			String ming1="";
			String ming2="";
			
			if(size1!=1){
				for(int i=0;i<size1-1;i++){
					ming1=ming1+nameTemp1[i];
				}
			}
			
			if(size2!=1){
				for(int i=0;i<size2-1;i++){
					ming2=ming2+nameTemp2[i];
				}
			}
			
			String all1=xing1+ming1;
			String all2=xing2+ming2;
			
		    return all1.compareTo(all2);
    }
    
   
}
