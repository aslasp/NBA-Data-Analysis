package businesslogicservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import vo.MatchVO;
import vo.PlayerVO;

public interface PlayerBLService {
	public ArrayList<PlayerVO> getPlayerBaseInfo();

	public ArrayList<PlayerVO> getPlayerSeasonInfo(String season);

	public ArrayList<PlayerVO> getPlayerAverageInfo();

	public ArrayList<PlayerVO> getPlayerBaseInfo(String name);

	public ArrayList<PlayerVO> getPlayerSeasonInfo(String season, String name);

	public ArrayList<PlayerVO> getPlayerAverageInfo(String name);

	public ArrayList<PlayerVO> getOrderedPlayersBySeason(String season,
			String condition, String order,int num) throws RemoteException;

	public ArrayList<PlayerVO> getOrderedPlayersByAverage(String season,
			String condition, String order,int num);

	public ArrayList<PlayerVO> selectPlayersBySeason(String season,
			String position, String union, String column,int num);

	public ArrayList<PlayerVO> selectPlayersByAverage(String season,
			String position, String union, String column,int num);

	public ImageIcon getPlayerPortraitImage(String name);

	public ImageIcon getPlayerActionImage(String name);

	public ArrayList<PlayerVO> getDayHotPlayer(String column)
			throws RemoteException;

	public ArrayList<PlayerVO> getSeasonHotPlayer(String season, String column);

	public ArrayList<PlayerVO> getBestImprovedPlayer(String column);
	
	public ArrayList<PlayerVO> getPlayersByInitialName(char character);

	public ArrayList<MatchVO> getRecentMatches(String playerName)
			throws RemoteException;

	public ArrayList<MatchVO> getTodayMatches(String playerName);

	public ArrayList<MatchVO> getMatches(String playerName);// 可以模糊
}
