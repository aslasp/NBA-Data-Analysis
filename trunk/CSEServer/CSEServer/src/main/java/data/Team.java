package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import po.MatchPO;
import po.RecordPO;
import po.TeamPO;
import SQLHelper.SqlManager;
import dataservice.TeamDataService;

public class Team extends UnicastRemoteObject implements TeamDataService {
	private static final long serialVersionUID = 1L;

	Connection connection = null;
	Statement sql = null;
	ResultSet resultSet = null;

	public Team() throws RemoteException {
		super();
		// TODO 自动生成的构造函数存根
	}

	public static void main(String[] args) {
		Team team;
		try {
			team = new Team();
			ArrayList<TeamPO> teams = team.getSeasonHotTeam("13-14", "score");
			for (TeamPO po : teams) {
				System.out.println(po.getAbLocation());
			}
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	/**
	 * 从数据库中获得球队列表teams
	 * 
	 * @return 球队最基本信息的列表
	 */
	public ArrayList<TeamPO> getTeamBaseInfo() {
		ArrayList<TeamPO> teams = new ArrayList<TeamPO>();
		try {
			connection = SqlManager.getConnection();
			sql = connection.createStatement();
			String query = "select * from teams";
			resultSet = sql.executeQuery(query);
			while (resultSet.next()) {
				int id = resultSet.getInt("teamID");
				String teamName = resultSet.getString("teamName");
				String location = resultSet.getString("location");
				String abLocation = resultSet.getString("abLocation");
				String conference = resultSet.getString("conference");
				String partition = resultSet.getString("partition");
				String homeCourt = resultSet.getString("homeCourt");
				int setUpTime = resultSet.getInt("setUpTime");
				TeamPO team = new TeamPO(id, teamName, abLocation, location,
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

	/**
	 * 得到球队的该赛季的技术统计数据
	 * 
	 * @param season
	 * @return
	 * 
	 */
	public ArrayList<TeamPO> getTeamSeasonInfo(String season) {
		ArrayList<TeamPO> teams = new ArrayList<TeamPO>();
		teams = getTeamInfo("teamMatchDataSeason", season);
		return teams;
	}

	/**
	 * 得到球队的场均的技术统计数据
	 * 
	 * @param season
	 * @return
	 * 
	 */
	public ArrayList<TeamPO> getTeamAverageInfo(String season) {
		ArrayList<TeamPO> teams = new ArrayList<TeamPO>();
		teams = getTeamInfo("teamMatchDataAverage", season);
		return teams;
	}

	/**
	 * 查询某球队的基础信息
	 * 
	 * @param name
	 *            球队名称
	 * @return teamPO对象
	 */
	public TeamPO getTeamBaseInfo(String name) {
		TeamPO teamPO = null;
		try {
			connection = SqlManager.getConnection();
			sql = connection.createStatement();
			String query = "select * from teams where abLocation like '%" + name
					+ "%'or teamName like '%" + name + "%'";
			resultSet = sql.executeQuery(query);
			resultSet.next();
			int id = resultSet.getInt("teamID");
			String teamName = resultSet.getString("teamName");
			String location = resultSet.getString("location");
			String abLocation = resultSet.getString("abLocation");
			String conference = resultSet.getString("conference");
			String partition = resultSet.getString("partition");
			String homeCourt = resultSet.getString("homeCourt");
			int setUpTime = resultSet.getInt("setUpTime");
			teamPO = new TeamPO(id, teamName, abLocation, location, conference,
					partition, homeCourt, setUpTime);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeMySql();
		}
		return teamPO;
	}

	/**
	 * 根据赛季和球队名称返回球队这个赛季的技术数据
	 * 
	 * @param season
	 * @param name
	 * @return
	 */
	public TeamPO getTeamSeasonInfo(String season, String name) {
		TeamPO po = null;
		po = getTeamPO("teamMatchDataSeason", season, name);
		return po;
	}

	/**
	 * 根据赛季和球队名称返回球队这个赛季的场均技术数据
	 * 
	 * @param season
	 * @param name
	 * @return
	 */
	public TeamPO getTeamAverageInfo(String season, String name) {
		TeamPO po = null;
		po = getTeamPO("teamMatchDataAverage", season, name);
		return po;
	}

	/**
	 * 根据某一项技术分析项，将球队按某个赛季的该项数据进行升降序排序
	 * 
	 * @param season
	 *            赛季
	 * @param condition
	 *            排序条件，即某一项技术分析项，如：篮板率
	 * @param order
	 *            升序还是降序 asc 表示升序 , desc表示降序 , 未明确写明排序方式时默认是升序
	 * 
	 * @return 按照所给条件排好序的球队列表
	 */
	public ArrayList<TeamPO> getOrderedTeamsBySeason(String season,
			String condition, String order) throws RemoteException {
		ArrayList<TeamPO> result = new ArrayList<TeamPO>();
		result = getOrderedTeams("teamMatchDataSeason", season, condition,
				order);
		return result;
	}

	/**
	 * 根据某一项技术分析项，将球队按某个赛季的场均该项数据进行升降序排序
	 * 
	 * @param season
	 *            赛季
	 * @param condition
	 *            排序条件，即某一项技术分析项，如：篮板率
	 * @param order
	 *            升序还是降序 asc 表示升序 , desc表示降序 , 未明确写明排序方式时默认是升序
	 * 
	 * @return 按照所给条件排好序的球队列表
	 */
	public ArrayList<TeamPO> getOrderedTeamsByAverage(String season,
			String condition, String order) throws RemoteException {
		ArrayList<TeamPO> result = new ArrayList<TeamPO>();
		result = getOrderedTeams("teamMatchDataAverage", season, condition,
				order);
		return result;
	}

	/**
	 * 根据球队缩写得到球队队徽
	 * 
	 * @param abLocation
	 *            球队缩写
	 * @return
	 */
	public String getPhotoPath(String abLocation) {
		return "src/data/teamsPng/" + abLocation + ".png";
	}

	public ImageIcon getTeamImage(String name) throws RemoteException {
		ImageIcon imageIcon = new ImageIcon(getPhotoPath(name));
		return imageIcon;
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

	/**
	 * 排序抽象方法，用于按赛季或场均的排序
	 * 
	 * @param table
	 *            表名，区分是按赛季还是按场均
	 * @param season
	 *            赛季
	 * @param condition
	 *            排序条件
	 * @param order
	 *            升序还是降序 asc 表示升序 , desc表示降序 , 未明确写明排序方式时默认是升序
	 * @return
	 */
	private ArrayList<TeamPO> getOrderedTeams(String table, String season,
			String condition, String order) {
		ArrayList<TeamPO> result = new ArrayList<TeamPO>();
		// 未明确写明排序方式时默认是升序
		if (order == null || order.equals("null")) {
			order = "asc";
		}
		try {
			connection = SqlManager.getConnection();
			sql = connection.createStatement();
			String query = "select * from " + table + " where season='"
					+ season + "' order by " + condition + " " + order;
			resultSet = sql.executeQuery(query);
			while (resultSet.next()) {
				String name = resultSet.getString("team");
				int matchesNum = resultSet.getInt("matchesNum"); // 比赛场数
				double shootHitNum = resultSet.getDouble("shootHitNum"); // 投篮命中数
				double shootAttemptNum = resultSet.getDouble("shootAttemptNum"); // 投篮出手次数
				double threeHitNum = resultSet.getDouble("threeHitNum"); // 三分命中数
				double threeAttemptNum = resultSet.getDouble("threeAttemptNum"); // 三分出手数
				double freeThrowHitNum = resultSet.getDouble("freeThrowHitNum"); // 罚球命中数
				double freeThrowAttemptNum = resultSet
						.getDouble("freeThrowAttemptNum"); // 罚球出手数
				double offenReboundNum = resultSet.getDouble("offenReboundNum"); // 进攻篮板数
				double defenReboundNum = resultSet.getDouble("defenReboundNum"); // 防守篮板数
				double reboundNum = resultSet.getDouble("reboundNum");// 篮板数
				double assistNum = resultSet.getDouble("assistNum");// 助攻数
				double stealNum = resultSet.getDouble("stealNum");// 抢断数
				double blockNum = resultSet.getDouble("blockNum");// 盖帽数
				double turnOverNum = resultSet.getDouble("turnOverNum");// 失误数
				double foulNum = resultSet.getDouble("foulNum");// 犯规数
				double score = resultSet.getDouble("score");// 比赛得分
				double shootHitRate = resultSet.getDouble("shootHitRate");// 投篮命中率
				double threeHitRate = resultSet.getDouble("threeHitRate");// 三分命中率
				double freeThrowHitRate = resultSet
						.getDouble("freeThrowHitRate");// 罚球命中率
				double winRate = resultSet.getDouble("winRate"); // 胜率
				double offenRound = resultSet.getDouble("offenRound"); // 进攻回合
				double offenEfficiency = resultSet.getDouble("offenEfficiency"); // 进攻效率
				double defenEfficiency = resultSet.getDouble("defenEfficiency"); // 防守效率
				double offenReboundEfficiency = resultSet
						.getDouble("offenReboundEfficiency"); // 进攻篮板效率
				double defenReboundEfficiency = resultSet
						.getDouble("defenReboundEfficiency"); // 防守篮板效率
				double stealEfficiency = resultSet.getDouble("stealEfficiency"); // 抢断效率
				double assistRate = resultSet.getDouble("assistRate"); // 助攻率

				TeamPO po = new TeamPO(name, matchesNum, shootHitNum,
						shootAttemptNum, threeHitNum, threeAttemptNum,
						freeThrowHitNum, freeThrowAttemptNum, offenReboundNum,
						defenReboundNum, reboundNum, assistNum, stealNum,
						blockNum, turnOverNum, foulNum, score, shootHitRate,
						threeHitRate, freeThrowHitRate, winRate, offenRound,
						offenEfficiency, defenEfficiency,
						offenReboundEfficiency, defenReboundEfficiency,
						stealEfficiency, assistRate);
				result.add(po);
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
		return result;
	}

	/**
	 * 得到球队信息的抽象方法，用于获得球队某个赛季的技术数据
	 * 
	 * @param table
	 *            表名，区分是按赛季还是按场均
	 * @param season
	 *            赛季
	 * @return
	 */
	private ArrayList<TeamPO> getTeamInfo(String table, String season) {
		ArrayList<TeamPO> teams = new ArrayList<TeamPO>();
		teams = getTeamBaseInfo();
		try {
			connection = SqlManager.getConnection();
			sql = connection.createStatement();
			for (TeamPO po : teams) {
				String query = "select * from " + table + " where team='"
						+ po.getAbLocation() + "' and season ='" + season + "'";
				resultSet = sql.executeQuery(query);
				resultSet.next();
				po.setMatchesNum(resultSet.getInt("matchesNum")); // 比赛场数
				po.setShootHitNum(resultSet.getDouble("shootHitNum")); // 投篮命中数
				po.setShootAttemptNum(resultSet.getDouble("shootAttemptNum")); // 投篮出手次数
				po.setThreeHitNum(resultSet.getDouble("threeHitNum")); // 三分命中数
				po.setThreeAttemptNum(resultSet.getDouble("threeAttemptNum")); // 三分出手数
				po.setFreeThrowHitNum(resultSet.getDouble("freeThrowHitNum")); // 罚球命中数
				po.setFreeThrowAttemptNum(resultSet
						.getDouble("freeThrowAttemptNum")); // 罚球出手数
				po.setOffenReboundNum(resultSet.getDouble("offenReboundNum")); // 进攻篮板数
				po.setDefenReboundNum(resultSet.getDouble("defenReboundNum")); // 防守篮板数
				po.setReboundNum(resultSet.getDouble("reboundNum"));// 篮板数
				po.setAssistNum(resultSet.getDouble("assistNum"));// 助攻数
				po.setStealNum(resultSet.getDouble("stealNum"));// 抢断数
				po.setBlockNum(resultSet.getDouble("blockNum"));// 盖帽数
				po.setTurnOverNum(resultSet.getDouble("turnOverNum")); // 失误数
				po.setFoulNum(resultSet.getDouble("foulNum"));// 犯规数
				po.setScore(resultSet.getDouble("score"));// 比赛得分
				po.setShootHitRate(resultSet.getDouble("shootHitRate"));// 投篮命中率
				po.setThreeHitRate(resultSet.getDouble("threeHitRate"));// 三分命中率
				po.setFreeThrowHitRate(resultSet.getDouble("freeThrowHitRate"));// 罚球命中率
				po.setWinRate(resultSet.getDouble("winRate")); // 胜率
				po.setOffenRound(resultSet.getDouble("offenRound")); // 进攻回合
				po.setOffenEfficiency(resultSet.getDouble("offenEfficiency")); // 进攻效率
				po.setDefenEfficiency(resultSet.getDouble("defenEfficiency")); // 防守效率
				po.setOffenReboundEfficiency(resultSet
						.getDouble("offenReboundEfficiency")); // 进攻篮板效率
				po.setDefenReboundEfficiency(resultSet
						.getDouble("defenReboundEfficiency")); // 防守篮板效率
				po.setStealEfficiency(resultSet.getDouble("stealEfficiency")); // 抢断效率
				po.setAssistRate(resultSet.getDouble("assistRate")); // 助攻率

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

	/**
	 * 得到某个球队信息的抽象方法，用于获得该球队的某赛季的技术数据
	 * 
	 * @param table
	 *            表名，区分是按赛季还是按场均
	 * @param season
	 *            赛季
	 * @param name
	 *            球队缩写
	 * @return
	 */
	private TeamPO getTeamPO(String table, String season, String name) {
		// TeamPO teamPO = null;
		// ArrayList<TeamPO> teams = new ArrayList<TeamPO>();
		// teams = getTeamSeasonInfo(season);
		// for (TeamPO po : teams) {
		// if (po.getAbLocation().equals(name)
		// || po.getTeamName().equals(name)) {
		// teamPO = po;
		// break;
		// }
		// }
		// return teamPO;

		TeamPO po = null;
		try {
			connection = SqlManager.getConnection();
			sql = connection.createStatement();
			String query = "select * from teams where teamName like'%" + name
					+ "%' or abLocation like '%" + name + "%'";
			resultSet = sql.executeQuery(query);
			resultSet.next();

			int id = resultSet.getInt("teamID");
			String teamName = resultSet.getString("teamName");
			String location = resultSet.getString("location");
			String abLocation = resultSet.getString("abLocation");
			String conference = resultSet.getString("conference");
			String partition = resultSet.getString("partition");
			String homeCourt = resultSet.getString("homeCourt");
			int setUpTime = resultSet.getInt("setUpTime");
			po = new TeamPO(id, teamName, abLocation, location, conference,
					partition, homeCourt, setUpTime);
			resultSet.close();

			query = "select * from " + table + " where team='" + abLocation
					+ "' and season ='" + season + "'";
			resultSet = sql.executeQuery(query);
			resultSet.next();

			po.setMatchesNum(resultSet.getInt("matchesNum")); // 比赛场数
			po.setShootHitNum(resultSet.getDouble("shootHitNum")); // 投篮命中数
			po.setShootAttemptNum(resultSet.getDouble("shootAttemptNum")); // 投篮出手次数
			po.setThreeHitNum(resultSet.getDouble("threeHitNum")); // 三分命中数
			po.setThreeAttemptNum(resultSet.getDouble("threeAttemptNum")); // 三分出手数
			po.setFreeThrowHitNum(resultSet.getDouble("freeThrowHitNum")); // 罚球命中数
			po.setFreeThrowAttemptNum(resultSet.getDouble("freeThrowAttemptNum")); // 罚球出手数
			po.setOffenReboundNum(resultSet.getDouble("offenReboundNum")); // 进攻篮板数
			po.setDefenReboundNum(resultSet.getDouble("defenReboundNum")); // 防守篮板数
			po.setReboundNum(resultSet.getDouble("reboundNum"));// 篮板数
			po.setAssistNum(resultSet.getDouble("assistNum"));// 助攻数
			po.setStealNum(resultSet.getDouble("stealNum"));// 抢断数
			po.setBlockNum(resultSet.getDouble("blockNum"));// 盖帽数
			po.setTurnOverNum(resultSet.getDouble("turnOverNum")); // 失误数
			po.setFoulNum(resultSet.getDouble("foulNum"));// 犯规数
			po.setScore(resultSet.getDouble("score"));// 比赛得分
			po.setShootHitRate(resultSet.getDouble("shootHitRate"));// 投篮命中率
			po.setThreeHitRate(resultSet.getDouble("threeHitRate"));// 三分命中率
			po.setFreeThrowHitRate(resultSet.getDouble("freeThrowHitRate"));// 罚球命中率
			po.setWinRate(resultSet.getDouble("winRate")); // 胜率
			po.setOffenRound(resultSet.getDouble("offenRound")); // 进攻回合
			po.setOffenEfficiency(resultSet.getDouble("offenEfficiency")); // 进攻效率
			po.setDefenEfficiency(resultSet.getDouble("defenEfficiency")); // 防守效率
			po.setOffenReboundEfficiency(resultSet
					.getDouble("offenReboundEfficiency")); // 进攻篮板效率
			po.setDefenReboundEfficiency(resultSet
					.getDouble("defenReboundEfficiency")); // 防守篮板效率
			po.setStealEfficiency(resultSet.getDouble("stealEfficiency")); // 抢断效率
			po.setAssistRate(resultSet.getDouble("assistRate")); // 助攻率

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeMySql();
		}
		return po;
	}

	/**
	 * 根据球队名称模糊查找球队近期比赛
	 * 
	 * @param teamName
	 *            球队名称
	 * @return 近期五场比赛的列表
	 */
	public ArrayList<MatchPO> getRecentMatches(String teamName)
			throws RemoteException {
		// TODO 自动生成的方法存根
		ArrayList<MatchPO> result = new ArrayList<MatchPO>();
		result = getRencentMatches(teamName, 5);
		return result;
	}

	/**
	 * 根据球队名称模糊查找其过往比赛
	 * 
	 * @param teamName
	 *            球队名称
	 * @return
	 */
	public ArrayList<MatchPO> getMatches(String teamName)
			throws RemoteException {
		// TODO 自动生成的方法存根
		ArrayList<MatchPO> result = new ArrayList<MatchPO>();
		result = getRencentMatches(teamName, -1);
		return result;
	}

	/**
	 * 根据球队名得到近期比赛的某种数据 模糊查找（觉得好像意义不大）
	 * 
	 * @param teamName
	 * @param num
	 *            得到比赛的场数 ，-1则是取出全部数据
	 * @return
	 */
	private ArrayList<MatchPO> getRencentMatches(String teamName, int num) {
		ArrayList<MatchPO> result = new ArrayList<MatchPO>();
		try {
			connection = SqlManager.getConnection();
			Statement sql = connection.createStatement();
			// 模糊查找
			String query = "select * from matches where homeTeam like '%"
					+ teamName + "%' or visitingTeam like '%" + teamName
					+ "%' order by date desc";
			ResultSet resultSet = sql.executeQuery(query);
			int count = 0;
			while (resultSet.next()) {
				count++;

				int matchID = resultSet.getInt("matchID");
				String season = resultSet.getString("season");
				String date = resultSet.getString("date");
				String visingTeam = resultSet.getString("visitingTeam");
				String homeTeam = resultSet.getString("homeTeam");
				int visitingScore = resultSet.getInt("visitingScore");
				int homeScore = resultSet.getInt("homeScore");

				ArrayList<String> detailScores = new ArrayList<String>();
				Statement statement = connection.createStatement();
				String query2 = "select score from detailscores where matchID="
						+ matchID;
				ResultSet rs = statement.executeQuery(query2);
				while (rs.next()) {
					detailScores.add(rs.getString("score"));
				}
				statement.close();
				rs.close();

				ArrayList<RecordPO> records = new ArrayList<RecordPO>();
				Statement stat = connection.createStatement();
				String query3 = "select * from records where matchID="
						+ matchID;
				ResultSet rSet = stat.executeQuery(query3);
				while (rSet.next()) {
					String team = rSet.getString("team");
					String playerName = rSet.getString("playerName");
					String position = rSet.getString("position");
					String presentTime = rSet.getString("presentTime");
					int shootHitNum = rSet.getInt("shootHitNum");
					int shootAttemptNum = rSet.getInt("shootAttemptNum");
					int threeHitNum = rSet.getInt("threeHitNum");
					int threeAttemptNum = rSet.getInt("threeAttemptNum");
					int freeThrowHitNum = rSet.getInt("freeThrowHitNum");
					int freeThrowAttemptNum = rSet
							.getInt("freeThrowAttemptNum");
					int offenReboundNum = rSet.getInt("offenReboundNum");
					int defenReboundNum = rSet.getInt("defenReboundNum");
					int reboundNum = rSet.getInt("reboundNum");
					int assistNum = rSet.getInt("assistNum");
					int stealNum = rSet.getInt("stealNum");
					int blockNum = rSet.getInt("blockNum");
					int turnOverNum = rSet.getInt("turnOverNum");
					int foulNum = rSet.getInt("foulNum");
					int score = rSet.getInt("score");
					RecordPO recordPO = new RecordPO(team, playerName,
							position, presentTime, shootHitNum,
							shootAttemptNum, threeHitNum, threeAttemptNum,
							freeThrowHitNum, freeThrowAttemptNum,
							offenReboundNum, defenReboundNum, reboundNum,
							assistNum, stealNum, blockNum, turnOverNum,
							foulNum, score);
					records.add(recordPO);
				}
				rSet.close();
				stat.close();

				MatchPO matchPO = new MatchPO(matchID, season, date,
						visingTeam, homeTeam, visitingScore, homeScore,
						detailScores, records);
				result.add(matchPO);
				if (num > 0) {// -1则是取出全部数据
					if (count >= num) {
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeMySql();
		}
		return result;
	}

	/**
	 * 筛选出赛季的热点球队——所谓热点球队，就是按照筛选条件排前五的球队
	 * @param column
	 *            筛选条件
	 * @return 返回到 目前为止所有参加过比赛的球队中筛选出前 5 名球队（按照 降序排列进行筛选）
	 */
	public ArrayList<TeamPO> getSeasonHotTeam(String season, String column)
			throws RemoteException {
		// TODO 自动生成的方法存根
		ArrayList<TeamPO> result = new ArrayList<TeamPO>();
		try {
			connection = SqlManager.getConnection();
			Statement sql = connection.createStatement();
			String query = "";
			if (column.equals("score") || column.equals("reboundNum")
					|| column.equals("assistNum") || column.equals("blockNum")
					|| column.equals("stealNum")) {
				query = "select * from teamMatchDataAverage where season='"
						+ season + "' order by " + column + " desc";
			} else {
				query = "select * from teamMatchDataSeason where season='"
						+ season + "' order by " + column + " desc";
			}
			ResultSet rs = sql.executeQuery(query);
			int count = 0;
			while (rs.next()) {
				count++;
				String abLocation = rs.getString("team");
				Statement stat = connection.createStatement();
				String query2 = "select * from teams where abLocation ='"
						+ abLocation + "'";
				ResultSet resultSet = stat.executeQuery(query2);
				resultSet.next();
				// 基础信息
				int id = resultSet.getInt("teamID");
				String teamName = resultSet.getString("teamName");
				String location = resultSet.getString("location");
				String conference = resultSet.getString("conference");
				String partition = resultSet.getString("partition");
				String homeCourt = resultSet.getString("homeCourt");
				int setUpTime = resultSet.getInt("setUpTime");

				TeamPO teamPO = new TeamPO(id, teamName, abLocation, location,
						conference, partition, homeCourt, setUpTime);
				result.add(teamPO);
				if (count >= 5) {
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeMySql();
		}

		return result;
	}

	/**
	 * 根据球队缩写获得该球队的队员
	 * 
	 * @param teamAbLocation
	 *            球队缩写
	 * @return 球员姓名列表
	 */
	public ArrayList<String> getPlayersByTeam(String teamAbLocation) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			connection = SqlManager.getConnection();
			Statement sql = connection.createStatement();
			String query = "select playerName from playerMatchDataSeason where owingTeam like '%"
					+ teamAbLocation + "%'";
			ResultSet rs = sql.executeQuery(query);
			while (rs.next()) {
				result.add(rs.getString("playerName"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeMySql();
		}
		return result;
	}
}
