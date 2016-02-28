package dataservice;

import java.util.ArrayList;

import vo.MatchVO;

public interface MatchDataService {

	/**
	 * 得到数据库中记录在案的所有赛季
	 * 
	 * @return 赛季列表
	 */
	public ArrayList<String> getAllSeasons();

	/**
	 * 得到某个赛季的全部比赛
	 * 
	 * @param season
	 *            赛季
	 * @return 该赛季的全部比赛列表
	 */
	public ArrayList<MatchVO> getMatchesBySeason(String season, String type);

	/**
	 * 根据球队名得到该球队参加过的所有比赛，支持模糊查找
	 * 
	 * @param name
	 *            球队名（缩写）
	 * @return 比赛列表
	 */
	public ArrayList<MatchVO> getMatchesByTeam(String season, String type,
			String name);

	/**
	 * 得到比赛数据
	 * 
	 * @param season
	 *            赛季
	 * @param type
	 *            比赛类型
	 * @param date
	 *            日期
	 * @param homeTeam
	 *            主队（缩写）
	 * @param visitingTeam
	 *            客队（缩写）
	 * @return 符合条件的比赛列表
	 */
	public ArrayList<MatchVO> getMatchData(String season, String type,
			String date, String homeTeam, String visitingTeam);
}
