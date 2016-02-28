package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import vo.TeamVO;
import SQLHelper.SqlManager;
import dataservice.TeamDataService;

/**
 * 从文件中读取数据，用于将球队的基本数据存储到数据库中 表名：teams
 */
public class TeamData implements TeamDataService {
	Connection connection = null;
	Statement sql = null;
	ResultSet resultSet = null;
	private ArrayList<TeamVO> teamBaseList = new ArrayList<TeamVO>();
	private Map<String, TeamVO> teamsBaseInfo = new LinkedHashMap<String, TeamVO>();

	public static void main(String[] args) {
		TeamData teamDataReader = new TeamData();
		ArrayList<TeamVO> result=new ArrayList<TeamVO>();
		result=teamDataReader.getTeamSeasonInfo("13-14", "Playoff", "NOP");
		System.out.println(result.size());
	}

	public TeamData() {
		// matchData = new MatchData();
		teamBaseList = myGetTeamBaseInfo();
		teamsBaseInfo = changeListToMap(teamBaseList);

	}
	
	/**
	 * 为球员服务的map
	 * 
	 */
	public static Map<String, TeamVO> getTeamsPartition() {
		// TODO 自动生成的方法存根
		String abLocation;
		String conference;
		String partition;

		TeamData teamData = new TeamData();
		ArrayList<TeamVO> result = teamData.getTeamBaseInfo();
		Map<String, TeamVO> teams = new HashMap<String, TeamVO>();
		TeamVO team;

		for (TeamVO vo : result) {
			abLocation = vo.getAbLocation();
			conference = vo.getConference();// 东西部
			partition = vo.getPartition();

			team = new TeamVO(abLocation, conference, partition);
			teams.put(abLocation, team);
		}
		return teams;
	}

	public ArrayList<TeamVO> getTeamBaseInfo() {
		// TODO 自动生成的方法存根
		return teamBaseList;
	}

	private ArrayList<TeamVO> myGetTeamBaseInfo() {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		try {
			connection = SqlManager.getConnection();
			sql = connection.createStatement();
			String query = "select * from teams";
			resultSet = sql.executeQuery(query);
			while (resultSet.next()) {
				// int id = resultSet.getInt("teamID");
				String teamName = resultSet.getString("teamName");
				String location = resultSet.getString("location");
				String abLocation = resultSet.getString("abLocation");
				String conference = resultSet.getString("conference");
				String partition = resultSet.getString("partition");
				String homeCourt = resultSet.getString("homeCourt");
				int setUpTime = resultSet.getInt("setUpTime");
				TeamVO team = new TeamVO(teamName, abLocation, location,
						conference, partition, homeCourt, setUpTime);
				teams.add(team);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeMySql();
		}
		return teams;
	}

	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String type) {
		// TODO 自动生成的方法存根
		return getTeamInfo(season, type, "TeamSeason");
	}

	public ArrayList<TeamVO> getTeamAverageInfo(String season, String type) {
		// TODO 自动生成的方法存根
		return getTeamInfo(season, type, "TeamAverage");
	}

	/**
	 * 得到某一赛季某一类型的数据
	 * 
	 * @param season
	 *            赛季
	 * @param type
	 *            比赛类型
	 * @param flag
	 *            标记是场均TeamAverage还是赛季TeamSeason
	 * @return 该赛季的该类型的比赛数据
	 */
	private ArrayList<TeamVO> getTeamInfo(String season, String type,
			String flag) {
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		String table = getTableName(season, type, flag);
		try {
			connection = SqlManager.getConnection();
			sql = connection.createStatement();
			String query = "select * from " + table;
			resultSet = sql.executeQuery(query);
			result = getTeamVOFromResultSet(resultSet,type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeMySql();
		}
		return result;
	}

	public ArrayList<TeamVO> getTeamBaseInfo(String name) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		TeamVO test = null;
		if (name.equals("NOH")) {
			name = "NOP";
		}
		if (name.equals("NJN")) {
			name = "BKN";
		}
		test = teamsBaseInfo.get(name);
		if (test != null) {
			result.add(test);
		} else {
			for (int i = 0; i < teamBaseList.size(); i++) {
				TeamVO vo = teamBaseList.get(i);
				String teamName = vo.getTeamName();
				String abLocation = vo.getAbLocation();
				if (teamName.toLowerCase().contains(name.toLowerCase())
						|| abLocation.toLowerCase()
								.contains(name.toLowerCase())) {
					result.add(vo);
				}
			}
		}
		return result;
	}

	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String type,
			String name) {
		// TODO 自动生成的方法存根
		return getTeamInfo(season, type, name, "TeamSeason");
	}

	public ArrayList<TeamVO> getTeamAverageInfo(String season, String type,
			String name) {
		// TODO 自动生成的方法存根
		return getTeamInfo(season, type, name, "TeamAverage");
	}

	/**
	 * 得到某只队伍某一赛季某一类型的数据
	 * 
	 * @param season
	 *            赛季
	 * @param type
	 *            比赛类型
	 * @param name
	 *            队伍名称 支持根据队名或缩写进行模糊查找
	 * @param flag
	 *            标记是场均TeamAverage还是赛季TeamSeason
	 * @return 该队伍该赛季该类型比赛的数据
	 */
	private ArrayList<TeamVO> getTeamInfo(String season, String type,
			String name, String flag) {
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		boolean isNOPSeason = false;
		boolean isBKNSeason = false;
		// 处理球队换名的问题
		boolean flagNOP = false;
		int flagNOPInt = season.compareTo("12-13");
		if (flagNOPInt <= 0) {
			flagNOP = true;
		}
		if (flagNOP && name.equals("NOP")) {
			name = "NOH";
			isNOPSeason = true;
		}

		boolean flagBKN = false;
		int flagBKNInt = season.compareTo("11-12");
		if (flagBKNInt <= 0) {
			flagBKN = true;
		}
		if (flagBKN && name.equals("BKN")) {
			name = "NJN";
			isBKNSeason = true;
		}
		String tableName = getTableName(season, type, flag);
		String query = "";
		try {
			connection = SqlManager.getConnection();
			sql = connection.createStatement();
			if (isBKNSeason || isNOPSeason) {
				query = "select * from " + tableName + " where team ='" + name
						+ "'";
			} else {
				query = "select * from " + tableName
						+ " where team like '%" + name
						+ "%'or team like '%" + name + "%'";
			}
			resultSet = sql.executeQuery(query);
			result = getTeamVOFromResultSet(resultSet,type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeMySql();
		}

		return result;
	}

	private ArrayList<TeamVO> getTeamVOFromResultSet(ResultSet resultSet,String seasonType) {
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		try {
			while (resultSet.next()) {
				String abLocation = resultSet.getString("team");
				String originalAbLocation=abLocation;
				if(abLocation.equals("NOH")){
					abLocation="NOP";
				}else if(abLocation.equals("NJN")){
					abLocation="BKN";
				}
				TeamVO vo = teamsBaseInfo.get(abLocation);
				if (vo != null) {
					if(seasonType.equals("Playoff")&&resultSet.getInt("matchesNum")==0){
						continue;
					}
					vo.setAbLocation(originalAbLocation);
					vo.setMatchesNum(resultSet.getInt("matchesNum"));
					vo.setShootHitNum(resultSet.getDouble("shootHitNum"));
					vo.setShootAttemptNum(resultSet
							.getDouble("shootAttemptNum"));
					vo.setThreeHitNum(resultSet.getDouble("threeHitNum"));
					vo.setThreeAttemptNum(resultSet
							.getDouble("threeAttemptNum"));
					vo.setFreeThrowHitNum(resultSet
							.getDouble("freeThrowHitNum"));
					vo.setFreeThrowAttemptNum(resultSet
							.getDouble("freeThrowAttemptNum"));
					vo.setOffenReboundNum(resultSet
							.getDouble("offenReboundNum"));
					vo.setDefenReboundNum(resultSet
							.getDouble("defenReboundNum"));
					vo.setReboundNum(resultSet.getDouble("reboundNum"));
					vo.setAssistNum(resultSet.getDouble("assistNum"));
					vo.setStealNum(resultSet.getDouble("stealNum"));
					vo.setBlockNum(resultSet.getDouble("blockNum"));
					vo.setTurnOverNum(resultSet.getDouble("turnOverNum"));
					vo.setFoulNum(resultSet.getDouble("foulNum"));
					vo.setScore(resultSet.getDouble("score"));
					vo.setShootHitRate(resultSet.getDouble("shootHitRate"));
					vo.setThreeHitRate(resultSet.getDouble("threeHitRate"));
					vo.setFreeThrowHitRate(resultSet
							.getDouble("freeThrowHitRate"));
					vo.setWinRate(resultSet.getDouble("winRate"));
					vo.setOffenRound(resultSet.getDouble("offenRound"));
					vo.setOffenEfficiency(resultSet
							.getDouble("offenEfficiency"));
					vo.setDefenEfficiency(resultSet
							.getDouble("defenEfficiency"));
					vo.setOffenReboundEfficiency(resultSet
							.getDouble("offenReboundEfficiency"));
					vo.setDefenReboundEfficiency(resultSet
							.getDouble("defenReboundEfficiency"));
					vo.setStealEfficiency(resultSet
							.getDouble("stealEfficiency"));
					vo.setAssistRate(resultSet.getDouble("assistRate"));

					result.add(vo);
				} else {
					System.err.println("find TeamVO is null");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	private String getTableName(String season, String type, String extra) {
		return season.replace("-", "") + "_" + type + "_" + extra;
	}

	private HashMap<String, TeamVO> changeListToMap(ArrayList<TeamVO> list) {
		HashMap<String, TeamVO> result = new HashMap<String, TeamVO>();
		for (int i = 0; i < list.size(); i++) {
			TeamVO vo = list.get(i);
			String key = vo.getAbLocation();
			result.put(key, vo);
		}
		return result;
	}

	private void closeMySql() {
		try {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
			if (sql != null) {
				sql.close();
				sql = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
