package dataservice;

import java.util.ArrayList;

import vo.TeamVO;

public interface TeamDataService {
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

}
