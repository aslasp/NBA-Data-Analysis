package blservice;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import vo.MatchVO;
import vo.PlayerVO;

public interface PlayerBLService {
	/**
	 * 获得球员列表
	 * 
	 * @return 球员最基本信息的列表
	 */
	public ArrayList<PlayerVO> getPlayerBaseInfo();

	/**
	 * 得到球员的某个赛季的技术统计数据
	 * 
	 * @param 赛季
	 * @return
	 */
	public ArrayList<PlayerVO> getPlayerSeasonInfo(String season);

	/**
	 * 得到球员的场均技术统计数据
	 * 
	 * @return
	 */
	public ArrayList<PlayerVO> getPlayerAverageInfo();

	/**
	 * 模糊查询某球员的基础信息
	 * 
	 * @param name
	 *            球员名称
	 * @return
	 */
	public ArrayList<PlayerVO> getPlayerBaseInfo(String name);
	public ArrayList<PlayerVO> getPlayerBaseInfoSeason(String season,String name);
	/**
	 * 根据赛季和球员名称返回球员这个赛季的总的技术数据
	 * 
	 * @param season
	 * @param name
	 * @return
	 */
	public PlayerVO getPlayerSeasonInfo(String season, String name);

	/**
	 * 根据赛季和球员名称返回球员场均技术数据
	 * 
	 * @param name
	 * @return
	 */
	public PlayerVO getPlayerAverageInfo(String name);

	/**
	 * 根据某一项技术分析项，将球员按某个赛季的该项数据进行升降序排序
	 * 
	 * @param season
	 *            赛季
	 * @param condition
	 *            排序条件，即某一项技术分析项，如：篮板率
	 * @param order
	 *            升序还是降序 asc 表示升序 , desc表示降序 , 未明确写明排序方式时默认是升序
	 * @param num
	 *            筛选出前多少名球员
	 * 
	 * @return 按照所给条件排好序的球队列表
	 */
	public ArrayList<PlayerVO> getOrderedPlayersBySeason(String season,
			String condition, String order, int num);

	/**
	 * 根据某一项技术分析项，将球员按场均该项数据进行升降序排序
	 * 
	 * @param season
	 *            赛季
	 * @param condition
	 *            排序条件，即某一项技术分析项，如：篮板率
	 * @param order
	 *            升序还是降序 asc 表示升序 , desc表示降序 , 未明确写明排序方式时默认是升序
	 * 
	 * @return 按照所给条件排好序的球员列表
	 */
	public ArrayList<PlayerVO> getOrderedPlayersByAverage(String condition,
			String order, int num);

	/**
	 * 对某个赛季中的球员信息，根据某一项属性里的值进行筛选
	 * 
	 * @param season
	 *            赛季
	 * @param position
	 *            球员位置
	 * @param union
	 *            联盟
	 * @param ageClass
	 *            哪个年龄时间段范围
	 * @param column
	 *            哪一项属性 ，如"name"
	 * @param num
	 *            需要的数据条数
	 * @return 符合条件的球员列表
	 */
	public ArrayList<PlayerVO> selectPlayersBySeason(String season,
			String position, String union, AgeEnum ageClass, String column,
			String order, int num);

	/**
	 * 对球员场均信息，根据某一项属性里的值进行筛选
	 * 
	 * @param season
	 *            赛季
	 * @param position
	 *            球员位置
	 * @param union
	 *            联盟
	 * @param column
	 *            哪一项属性 ，如"name"
	 * @param num
	 *            需要的数据条数
	 * @return 符合条件的球员列表
	 */
	public ArrayList<PlayerVO> selectPlayersByAverage(String position,
			String union, AgeEnum ageClass, String column, String order, int num);

	/**
	 * 获得球员的大头照
	 * 
	 * @param name球员名
	 * @return
	 */
	public ImageIcon getPlayerPortraitImage(String name);

	/**
	 * 获得球员的全身照
	 * 
	 * @param name球员名
	 * @return 符合条件的球员列表
	 */
	public ImageIcon getPlayerActionImage(String name);

	/**
	 * 得到今日热点球员
	 * 
	 * @param column
	 *            进行球员排序的时候的球员属性值
	 * @return 符合条件的球员列表
	 */
	public ArrayList<PlayerVO> getDayHotPlayer(String column, int num);

	/**
	 * 获得某一赛季的热点球员
	 * 
	 * @param season
	 *            赛季
	 * @param column
	 *            进行球员排序的时候的球员属性值
	 * @return 符合条件的球员列表
	 */
	public ArrayList<PlayerVO> getSeasonHotPlayer(String season, String column,
			int num);

	/**
	 * 获得进步最大的球员
	 * 
	 * @param column
	 *            进行球员排序的时候的球员属性值
	 * @return 符合条件的球员列表
	 */
	public ArrayList<PlayerVO> getBestImprovedPlayer(String column, int num);

	/**
	 * 根据首字母得到球员
	 * 
	 * @param character
	 *            球员姓名首字母
	 * @return 球员列表
	 */
	public ArrayList<PlayerVO> getPlayersByInitialName(char character);

	/**
	 * 
	 * @param minute找出出场时间大于等于该分钟的球员
	 * @return
	 */
	public ArrayList<PlayerVO> selectPlayersUptheTimeAverage(String position,
			String union, AgeEnum ageClass, String column, String order,int minute,int num);
	public ArrayList<PlayerVO> selectPlayersUptheTimeSeason(String season,String position,
			String union, AgeEnum ageClass, String column, String order,int minute,int num);
	/**
	 * 获得该球员最近5场比赛的数据
	 * 
	 * @param playerName
	 *            球员姓名
	 * @return 比赛列表
	 */
	public ArrayList<MatchVO> getRecentMatches(String playerName, int num);

	/**
	 * 获得该球员全部比赛的数据
	 * 
	 * @param playerName
	 *            球员姓名
	 * @return 比赛列表
	 */
	public ArrayList<MatchVO> getMatches(String playerName);// 可以模糊

	/**
	 * 根据球队缩写，找到该球队中的全部球员
	 * 
	 * @param teamAbLocation
	 *            球队缩写
	 * @return 球员名字列表
	 */
	public ArrayList<PlayerVO> getPlayersByTeam(String teamAbLocation);
}
