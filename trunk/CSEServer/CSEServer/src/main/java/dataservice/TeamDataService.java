package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import po.MatchPO;
import po.TeamPO;

public interface TeamDataService extends Remote {
	public ArrayList<TeamPO> getTeamBaseInfo() throws RemoteException;

	public ArrayList<TeamPO> getTeamSeasonInfo(String season)
			throws RemoteException;

	public ArrayList<TeamPO> getTeamAverageInfo(String season)
			throws RemoteException;

	public ArrayList<MatchPO> getRecentMatches(String teamName)
			throws RemoteException;

	public ArrayList<MatchPO> getMatches(String teamName)
			throws RemoteException;// 可以模糊

	public ArrayList<TeamPO> getSeasonHotTeam(String season, String column)
			throws RemoteException;

	public TeamPO getTeamBaseInfo(String name) throws RemoteException;

	public TeamPO getTeamSeasonInfo(String season, String name)
			throws RemoteException;

	public TeamPO getTeamAverageInfo(String season, String name)
			throws RemoteException;

	public ArrayList<TeamPO> getOrderedTeamsBySeason(String season,
			String condition, String order) throws RemoteException;

	public ArrayList<TeamPO> getOrderedTeamsByAverage(String season,
			String condition, String order) throws RemoteException;

	public ImageIcon getTeamImage(String name) throws RemoteException;

	public ArrayList<String> getPlayersByTeam(String teamAbLocation)
			throws RemoteException;
}
