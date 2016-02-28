package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import po.MatchPO;
import po.PlayerPO;

public interface PlayerDataService extends Remote {
	public ArrayList<PlayerPO> getPlayerBaseInfo() throws RemoteException;

	public ArrayList<PlayerPO> getPlayerSeasonInfo(String season)
			throws RemoteException;

	public PlayerPO getPlayerBaseInfo(String name) throws RemoteException;

	public PlayerPO getPlayerSeasonInfo(String season, String name)
			throws RemoteException;

	public PlayerPO getPlayerAverageInfo(String season, String name)
			throws RemoteException;

	public ArrayList<MatchPO> getRecentMatches(String playerName)
			throws RemoteException;
	
	public ArrayList<MatchPO> getTodayMatches(String playerName) throws RemoteException;

	public ArrayList<MatchPO> getMatches(String playerName)
			throws RemoteException;// 可以模糊

	public ArrayList<PlayerPO> getOrderedPlayersBySeason(String season,
			String condition, String order,int num) throws RemoteException;

	public ArrayList<PlayerPO> getPlayerAverageInfo(String season)
			throws RemoteException;

	public ArrayList<PlayerPO> getOrderedPlayersByAverage(String season,
			String condition, String order,int num) throws RemoteException;

	public ArrayList<PlayerPO> selectPlayersBySeason(String season,
			String position, String union, String column,int num)
			throws RemoteException;

	public ArrayList<PlayerPO> selectPlayersByAverage(String season,
			String position, String union, String column,int num)
			throws RemoteException;

	public ArrayList<PlayerPO> getDayHotPlayer(String column)
			throws RemoteException;

	public ArrayList<PlayerPO> getSeasonHotPlayer(String season,String column)
			throws RemoteException;

	public ArrayList<PlayerPO> getBestImprovedPlayer(String column)
			throws RemoteException;
	
	public ArrayList<PlayerPO> getPlayersByInitialName(char character)
			throws RemoteException;

	public ImageIcon getPlayerActionImage(String name) throws RemoteException;

	public ImageIcon getPlayerPortraitImage(String name) throws RemoteException;
}
