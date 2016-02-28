package blService;

import java.util.ArrayList;

import vo.MatchVO;
import vo.TeamVO;

public interface TeamBLService {
	/**
	 * 从数据库中获得球队列表teams
	 * 
	 * @return 球队最基本信息的列表，其他属性值为空，未进行初始化
	 */
	public ArrayList<TeamVO> getTeamBaseInfo();

	/**
	 * 得到球队的该赛季的技术统计数据
	 * 
	 * @param season
	 *            赛季
	 * @param type
	 *            比赛类型
	 * @return 球队赛季统计数据，vo的所有初始值均进行了初始化
	 * 
	 */
	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String type);

	/**
	 * 得到球队的该场均的技术统计数据
	 * 
	 * @param season
	 *            赛季
	 * @param type
	 *            比赛类型
	 * @return 球队场均统计数据，VO的所有初始值均进行了初始化
	 * 
	 */
	public ArrayList<TeamVO> getTeamAverageInfo(String season, String type);

	/**
	 * 模糊查询球队的基础信息，球队名可以是名称，也可以是缩写，大小写均可
	 * 
	 * @param name
	 *            球队名称
	 * @return 符合模糊查询条件的球队对象的列表，球队对象只进行了基础信息的初始化与赋值
	 */
	public ArrayList<TeamVO> getTeamBaseInfo(String name);

	/**
	 * 模糊查询球队的某个赛季的信息，球队名可以是名称，也可以是缩写，大小写均可
	 * 
	 * @param season
	 *            赛季
	 * @param type
	 *            比赛类型
	 * @param name
	 *            球队名称
	 * @return 符合模糊查询条件的球队对象的列表
	 */
	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String type,
			String name);

	/**
	 * 模糊查询球队的场均技术信息，球队名可以是名称，也可以是缩写，大小写均可
	 * 
	 * @param season
	 *            赛季
	 * @param type
	 *            比赛类型
	 * @param name
	 *            球队名称
	 * 
	 * @return 符合模糊查询条件的球队对象的列表
	 */
	public ArrayList<TeamVO> getTeamAverageInfo(String season, String type,
			String name);

	/**
	 * 根据某一项技术分析项，将球队按某个赛季的该项数据进行升降序排序
	 * 
	 * @param season
	 *            赛季
	 * @param condition
	 *            排序条件，即某一项技术分析项，如：篮板率(英文)
	 * @param order
	 *            升序还是降序 asc 表示升序 , desc表示降序 , 未明确写明排序方式时默认是升序
	 * 
	 * @return 按照所给条件排好序的球队列表
	 */
	public ArrayList<TeamVO> getOrderedTeamsBySeason(String season,
			String type, String condition, String order, int num);

	/**
	 * 根据某一项技术分析项，将球队按场均该项数据进行升降序排序
	 * 
	 * @param season
	 *            赛季
	 * @param type
	 *            比赛类型
	 * @param condition
	 *            排序条件，即某一项技术分析项，如：篮板率(英文)
	 * @param order
	 *            升序还是降序 asc 表示升序 , desc表示降序 , 未明确写明排序方式时默认是升序
	 * 
	 * @return 按照所给条件排好序的球队列表
	 */
	public ArrayList<TeamVO> getOrderedTeamsByAverage(String season,
			String type, String condition, String order, int num);

	/**
	 * 根据球队名称查找该球队近期比赛
	 * 
	 * @param teamName
	 *            球队缩写 完全匹配
	 * @param num
	 *            比赛数量
	 * @return 近期num场比赛的列表
	 */
	public ArrayList<MatchVO> getRecentMatches(String teamName, int num);

	/**
	 * 根据球队名称查找其过往比赛
	 * 
	 * @param teamName
	 *            球队缩写 完全匹配
	 * @return 过往比赛列表
	 */
	public ArrayList<MatchVO> getMatches(String teamName);// 可以模糊

	/**
	 * 筛选出赛季的热点球队——所谓热点球队，就是按照筛选条件排前五的球队
	 * 
	 * @param column
	 *            筛选条件
	 * @return 返回到 目前为止所有参加过比赛的球队中筛选出前 5 名球队（按照 降序排列进行筛选）
	 */
	public ArrayList<TeamVO> getSeasonHotTeam(String season, String type,
			String column, int num);

}
