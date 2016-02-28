package newui.matchui;

import vo.MatchVO;
///这个类好像没什么必要
public class MatchHelper {
	//在tinycard中,判断一个队是否是一场比赛的主队
	public static boolean isHomeTeam(MatchVO vo,String team){
		String home=vo.getHomeTeam();
		if(team.equals(home))
			return true;
		return false;
	}
	public static boolean isHomeBigger(MatchVO vo){
		if(vo.getHomeScore()>vo.getVisitingScore())
			return true;
		return false;
	}
	
}
