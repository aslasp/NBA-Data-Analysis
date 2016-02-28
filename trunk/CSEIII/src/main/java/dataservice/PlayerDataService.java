package dataservice;

import java.util.ArrayList;
import java.util.Map;

import vo.MatchVO;
import vo.PlayerVO;

public interface PlayerDataService{
	/**
	 * 
	 * @return 历史上所有球员的基础数据（包括当季球员）
	 */
	public ArrayList<PlayerVO> getPlayerHistoricBaseInfo();
	
	/**
	 * 
	 * @return 当季所有球员的基础数据
	 */
	public ArrayList<PlayerVO> getPlayerActiveBaseInfo();

	/**
	 * 准确查找
	 * @param name
	 * @return 根据准确的名字返回该球员
	 */
	public PlayerVO getPlayerBaseInfo(String name);

	/**
	 * 模糊查找
	 * @param name
	 * @return  根据名字（模糊）返回符合条件的所有球员
	 */
    public ArrayList<PlayerVO> getPlayerBaseInfoForVague(String name);
    
    public ArrayList<PlayerVO> getPlayerActiveBaseInfoForVague(String name);
    
    public ArrayList<PlayerVO> getPlayerSeasonInfo(String season,String type);
    
    public ArrayList<PlayerVO> getPlayerSeasonInfo(String season,String type,String name);
    
    public ArrayList<PlayerVO> getPlayerAverageInfo(String season,String type);
    
    public ArrayList<PlayerVO> getPlayerAverageInfo(String season,String type,String name);
    
    public ArrayList<PlayerVO> getPlayerRecentAverageInfo(String name);
    
    public double getRankInNBA(String name,String condition);
    
    public ArrayList<PlayerVO> getOrderedPlayersBySeason(String season,String type,
			String condition, String order, int num);
    
    public ArrayList<PlayerVO> getOrderedPlayersByAverage(String season,String type,String condition,
			String order, int num);
    
    public ArrayList<PlayerVO> selectPlayersBySeason(String season,String type,
			String position, String union, String column,
			String order, int num) ;
    
    public ArrayList<PlayerVO> selectPlayersByAverage(String season,String type,String position,
			String union, String column, String order, int num);
    
    public ArrayList<PlayerVO> getDayHotPlayer(String column, int num);
    
    public ArrayList<PlayerVO> getSeasonHotPlayer(String season,String type,String column,
			int num);
    
    public ArrayList<PlayerVO> getBestImprovedPlayer(String season,String type,String column, int num);
    
    public ArrayList<PlayerVO> getPlayersByInitialName(char character);
    
    public ArrayList<MatchVO> getRecentMatches(String playerName, int num);
    
    public ArrayList<MatchVO> getMatches(String playerName);
    
    public ArrayList<MatchVO> getMatches(String season,String type,String playerName, int num);
    
    public ArrayList<PlayerVO> getPlayersByTeam(String teamAbLocation);
			
    public ArrayList<double[]> singleElementVarianceAnalysis();
    
    public ArrayList<String> singleElementVarianceAnalysis(String name);
}
