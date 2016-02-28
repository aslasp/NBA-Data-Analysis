package com.yjqn;

import java.util.ArrayList;

import vo.MatchVO;
import vo.PlayerVO;
import bl.player.Player;
import blservice.AgeEnum;
import junit.framework.TestCase;

public class PlayerTest extends TestCase{
	Player player;
	
	public void setUp() throws Exception{
		player=new Player();
	}
	

	public void test_1(){
		ArrayList<PlayerVO> players=player.getPlayerSeasonInfo("13-14");
		String name=players.get(0).getName();	
		assertEquals(name, "Aaron Brooks");				
	}
	
	//TUS1-2 成功删除账户
		public void test_2(){
			ArrayList<PlayerVO> players=player.getPlayerSeasonInfo("14-15");
			String name=players.get(0).getName();	
			assertEquals(name, "Aaron Brooks");	
		}
		
	//TUS1-3 成功修改账户
		public void test_3(){
			ArrayList<PlayerVO> players=player.getPlayerAverageInfo();
			String name=players.get(0).getName();	
			assertEquals(name, "Aaron Brooks");				
		}
				
	//TUS1-4 成功查询账户
		public void test_4(){
			PlayerVO players=player.getPlayerSeasonInfo("13-14", "Aaron Brooks");
			String name=players.getName();	
			assertEquals(name, "Aaron Brooks");	
		}
		
	//TUS2-1
	    public void test_5(){
	    	PlayerVO players=player.getPlayerAverageInfo("Aaron Brooks");
			String name=players.getName();	
			assertEquals(name, "Aaron Brooks");	
		}
	    
	//TUS2-2
	    public void test_6(){
	    	PlayerVO players=player.getPlayerAverageInfo("2333");
			assertEquals(players, null);	
		}
	    
	//TUS2-3
	    public void test_7(){
	    	ArrayList<PlayerVO> players;
			players = player.getOrderedPlayersBySeason("13-14", "score", "desc", 5);
			String name=players.get(0).getName();	
			assertEquals(name, "Kevin Durant");
					
		}
	    
	//TUS3-1
	    public void test_8(){
	    	ArrayList<PlayerVO> players;
			players = player.getOrderedPlayersByAverage("score", "desc", 5);
			String name=players.get(0).getName();	
			assertEquals(name, "Kevin Durant");	
		}
	    
	//TUS3-2
	    public void test_9(){
	    	ArrayList<PlayerVO> players;
			players = player.selectPlayersBySeason("13-14", "all", "all", AgeEnum.ALL, "score", "desc", 5);
			String name=players.get(0).getName();	
			assertEquals(name, "Kevin Durant");			
		}
	    
	//TUS3-3
	    public void test_10(){
	    	ArrayList<PlayerVO> players;
			players = player.selectPlayersBySeason("13-14", "G", "W", AgeEnum.LE22, "score", "desc", 5);
		}
	    
	  //TUS3-3
	    public void test_11(){
	    	ArrayList<PlayerVO> players;
			players = player.selectPlayersBySeason("13-14", "C", "E", AgeEnum.M22_LE25, "reboundNum", "desc", 5);
		}
	    
	  //TUS3-3
	    public void test_12(){
	    	ArrayList<PlayerVO> players;
			players = player.selectPlayersBySeason("13-14", "F", "E", AgeEnum.M25_LE30, "reboundNum", "desc", 5);
		}
	    
	  //TUS3-3
	    public void test_13(){
	    	ArrayList<PlayerVO> players;
			players = player.selectPlayersBySeason("13-14", "F", "W", AgeEnum.M30, "assistNum", "desc", 5);
		}
	    
	  //TUS3-2
	    public void test_14(){
	    	ArrayList<PlayerVO> players;
			players = player.selectPlayersByAverage("all", "all", AgeEnum.ALL, "score", "desc", 5);
			String name=players.get(0).getName();	
			assertEquals(name, "Kevin Durant");			
		}
	    
	//TUS3-3
	    public void test_15(){
	    	ArrayList<PlayerVO> players;
			players = player.selectPlayersByAverage("G", "W", AgeEnum.LE22, "score", "desc", 5);
		}
	    
	  //TUS3-3
	    public void test_16(){
	    	ArrayList<PlayerVO> players;
			players = player.selectPlayersByAverage("C", "E", AgeEnum.M22_LE25, "reboundNum", "desc", 5);
		}
	    
	  //TUS3-3
	    public void test_17(){
	    	ArrayList<PlayerVO> players;
			players = player.selectPlayersByAverage("F", "E", AgeEnum.M25_LE30, "reboundNum", "desc", 5);
		}
	    
	  //TUS3-3
	    public void test_18(){
	    	ArrayList<PlayerVO> players;
			players = player.selectPlayersByAverage("F", "W", AgeEnum.M30, "assistNum", "desc", 5);
		}
	    //TUS3-3
	    public void test_19(){
	    	player.getPlayerPortraitImage("Aaron Brooks");
		}	    
	    //TUS3-3
	    public void test_20(){
	    	player.getPlayerActionImage("Aaron Brooks");
		}
	  //TUS3-3
	    public void test_21(){
	    	ArrayList<PlayerVO> players;
			players = player.getDayHotPlayer("score", 5);
		}
	    //TUS3-3
	    public void test_22(){
	    	ArrayList<PlayerVO> players;
			players = player.getSeasonHotPlayer("13-14", "score", 5);
		}
	    //TUS3-3
	    public void test_23(){
	    	ArrayList<PlayerVO> players;
			players = player.getBestImprovedPlayer("recentFiveMatchesScoreUpRate", 5);
		}
	    //TUS3-3
	    public void test_24(){
	    	ArrayList<PlayerVO> players;
			players = player.getPlayersByInitialName('A');
			
		}
	    //TUS3-3
	    public void test_25(){
	    	ArrayList<MatchVO> matches;
			matches = player.getRecentMatches("Aaron Brooks", 5);			
		}
	    //TUS3-3
	    public void test_26(){
	    	ArrayList<MatchVO> matches;
			matches = player.getMatches("Aaron Brooks");			
		}
	    //TUS3-3
	    public void test_27(){
	    	ArrayList<PlayerVO> players;
			players = player.getPlayersByTeam("ATL");			
		}
	    //TUS3-3
	    public void test_28(){
	    	ArrayList<PlayerVO> players;
			players = player.getPlayerBaseInfo();			
		}
	    //TUS3-3
	    public void test_29(){
	    	ArrayList<PlayerVO> players;
			players = player.getPlayerBaseInfo("Aaron Brooks");			
		}
}
