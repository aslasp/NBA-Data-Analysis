package blService;

import java.util.ArrayList;

import vo.LiveMatchDetailVO;

public interface LiveTxtBLService {
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
	 * 边直播边把直播数据导入数据库
	 * @param s
	 */
	public void addToSql(String s);
}
