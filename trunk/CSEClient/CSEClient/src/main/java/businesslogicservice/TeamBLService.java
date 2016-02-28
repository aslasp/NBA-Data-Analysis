package businesslogicservice;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import vo.MatchVO;
import vo.TeamVO;

public interface TeamBLService {
	public ArrayList<TeamVO> getTeamBaseInfo();

	public ArrayList<TeamVO> getTeamSeasonInfo(String season);

	public ArrayList<TeamVO> getTeamAverageInfo();

	public ArrayList<TeamVO> getTeamBaseInfo(String name);

	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String name);

	public ArrayList<TeamVO> getTeamAverageInfo(String name);

	public ArrayList<TeamVO> getOrderedTeamsBySeason(String season,
			String condition, String order);

	public ArrayList<TeamVO> getOrderedTeamsByAverage(String season,
			String condition, String order);

	public ImageIcon getTeamImage(String name);

	public ArrayList<MatchVO> getRecentMatches(String teamName);

	public ArrayList<MatchVO> getMatches(String teamName);// 可以模糊

	public ArrayList<TeamVO> getSeasonHotTeam(String season, String column);

	public ArrayList<String> getPlayersByTeam(String teamAbLocation);
}
