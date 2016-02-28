package dataservice;

import java.util.ArrayList;

import vo.LiveMatchDetailVO;

public interface LiveTxtDataService {
	/**
	 * 获得一场比赛的直播信息
	 * 
	 * @param season
	 *            赛季
	 * @param date
	 *            日期
	 * @param teams
	 *            对阵队伍 客队-主队
	 * @return 当前比赛的直播信息
	 */
	public ArrayList<LiveMatchDetailVO> getLiveTxt(String season, String date,
			String teams);
	
	/**
	 * 边直播边往数据库里加直播数据
	 * @param s
	 */
	public void addToSql(String s);
}
