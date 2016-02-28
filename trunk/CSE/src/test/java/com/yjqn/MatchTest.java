package com.yjqn;

import java.util.ArrayList;

import vo.MatchVO;
import bl.match.Match;
import junit.framework.TestCase;

public class MatchTest extends TestCase{
    Match match;
	
	public void setUp() throws Exception{
		match=new Match();
	}
	

	public void test_1(){
		match.getAllMatches();		
	}
	
	//TUS1-2 成功删除账户
		public void test_2(){
			ArrayList<MatchVO> matches=match.getMatchData("13-14", "01-01", "LAC", "CHA");
			matches.get(0);
		}
		
	//TUS1-3 成功修改账户
		public void test_3(){
			ArrayList<MatchVO> matches=match.getMatchData("全部", "全部", "全部", "全部");
			matches.get(0);		
		}
				
	//TUS1-4 成功查询账户
		public void test_4(){
			ArrayList<MatchVO> matches=match.getMatchData("14-15", "全部", "全部", "全部");
		}
		//TUS1-4 成功查询账户
		public void test_5(){
			
		}
}
