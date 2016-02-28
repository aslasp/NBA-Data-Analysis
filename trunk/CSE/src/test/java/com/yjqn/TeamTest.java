package com.yjqn;

import java.util.ArrayList;

import vo.MatchVO;
import vo.TeamVO;
import junit.framework.TestCase;
import bl.team.Team;

public class TeamTest extends TestCase{
    Team team;
	
	public void setUp() throws Exception{
		team=new Team();
	}
	

	public void test_1(){
		Team.getTeamsPartition();			
	}
	
	//TUS1-2 成功删除账户
		public void test_2(){
			ArrayList<TeamVO> teams=team.getTeamBaseInfo();
			teams.get(0);
		}
		
	//TUS1-3 成功修改账户
		public void test_3(){
			ArrayList<TeamVO> teams=team.getTeamSeasonInfo("13-14");
			teams.get(0);
		}
				
	//TUS1-4 成功查询账户
		public void test_4(){
			team.getTeamSeasonInfo("14-15");
		}
		
	//TUS2-1
	    public void test_5(){
	    	ArrayList<TeamVO> teams=team.getTeamAverageInfo();
	    	teams.get(0);
		}
	    
	//TUS2-2
	    public void test_6(){
	    	ArrayList<TeamVO> teams=team.getTeamBaseInfo("ATL");
	    	teams.get(0);
		}
	    
	//TUS2-3
	    public void test_7(){
	    	ArrayList<TeamVO> teams=team.getTeamBaseInfo("NOP");
	    	teams.get(0);	
		}
	    
	//TUS3-1
	    public void test_8(){
	    	ArrayList<TeamVO> teams=team.getTeamBaseInfo("a");
	    	teams.get(0);
		}
	    
	//TUS3-2
	    public void test_9(){
	    	ArrayList<TeamVO> teams=team.getTeamSeasonInfo("13-14", "ATL");	
	    	teams.get(0);
		}
	    
	//TUS3-3
	    public void test_10(){
	    	team.getTeamSeasonInfo("14-15", "ATL");	
		}
	    
	  //TUS3-3
	    public void test_11(){
	    	ArrayList<TeamVO> teams=team.getTeamSeasonInfo("13-14", "NOP");	
	    	teams.get(0);
		}
	    
	  //TUS3-3
	    public void test_12(){
	    	ArrayList<TeamVO> teams=team.getTeamAverageInfo("ATL");
	    	teams.get(0);
		}
	    
	  //TUS3-3
	    public void test_13(){
	    	ArrayList<TeamVO> teams=team.getTeamAverageInfo("NOP");
	    	teams.get(0);
		}
	    
	  //TUS3-2
	    public void test_14(){
	    	ArrayList<TeamVO> teams=team.getOrderedTeamsBySeason("13-14", "score", "desc", -1);	
	    	teams.get(0);
		}
	    
	//TUS3-3
	    public void test_15(){
	    	ArrayList<TeamVO> teams=team.getOrderedTeamsBySeason("13-14", "score", "null", 5);
	    	teams.get(0);
		}
	    
	  //TUS3-3
	    public void test_16(){
	    	team.getTeamImage("ATL");
		}
	    
	  //TUS3-3
	    public void test_17(){
	    	ArrayList<MatchVO> matches=team.getRecentMatches("ATL");
	    	matches.get(0);
		}
	    
	  //TUS3-3
	    public void test_18(){
	    	ArrayList<MatchVO> matches=team.getMatches("ATL");
	    	matches.get(0);
		}
	    //TUS3-3
	    public void test_19(){
	    	ArrayList<TeamVO> teams=team.getSeasonHotTeam("13-14", "score", 5);
	    	teams.get(0);
		}	    
	    //TUS3-3
	    public void test_20(){
	    	ArrayList<TeamVO> teams=team.getSeasonHotTeam("13-14", "score", -1);
	    	teams.get(0);
		}
	  //TUS3-3
	    public void test_21(){
	    	Team.changeTeamNameCHToEN("湖人");
		}
	    //TUS3-3
	    public void test_22(){
	    	Team.changeTeamNameCHToEN("人");
		}
	    //TUS3-3
	    public void test_23(){
	    	Team.changeTeamNameENToCH("ATL");
		}
	    //TUS3-3
	    public void test_24(){
	    	Team.changeTeamNameENToCH("A");		
		}
	   
}
