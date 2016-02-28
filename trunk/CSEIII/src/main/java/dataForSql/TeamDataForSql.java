package dataForSql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import vo.MatchVO;
import vo.RecordVO;
import vo.TeamVO;
import SQLHelper.SqlManager;
import bl.Match;
import data.MatchData;
import dataservice.MatchDataService;

/**
 * 从文件中读取数据，用于将球队的基本数据存储到数据库中 表名：teams
 */
public class TeamDataForSql {
	private Connection connection = null;
	private Statement sql = null;
	private ResultSet resultSet = null;
	private ArrayList<TeamVO> teamBaseList = new ArrayList<TeamVO>();
	private Map<String, TeamVO> teamsBaseInfo = new LinkedHashMap<String, TeamVO>();
	private MatchDataService matchData;

	public static void main(String[] args) {
		TeamDataForSql teamDataForSql=new TeamDataForSql();
		teamDataForSql.run();
	}

	public void run() {
		TeamDataForSql teamDataForSql = new TeamDataForSql();
		MatchDataService match = new MatchData();
		ArrayList<String> seasons = match.getAllSeasons();
		String[] types = { "Preseason", "Team", "Playoff" };
		for (int i = 0; i < seasons.size(); i++) {
			String season = seasons.get(i);
			for (int j = 0; j < types.length; j++) {
				String type = types[j];
				teamDataForSql.exportCalculatedDataToSql(season, type);
				System.out.println(season + "_" + type + "好啦！");
			}
		}
		System.out.println("好啦");
	}

	public TeamDataForSql(int i){
		//use for init data
	}
	
	public TeamDataForSql() {
		matchData = new MatchData();
		teamBaseList = myGetTeamBaseInfo();
		teamsBaseInfo = changeListToMap(teamBaseList);

	}

	private ArrayList<String[]> readFromFile(String fileName) {

		ArrayList<String[]> result = new ArrayList<String[]>();

		String[] content;
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				content = new String[7];
				if (temp.contains("│")) {
					String[] it = temp.split("│");
					String[] first = it[0].split("║");
					content[0] = first[1].trim();
					content[1] = it[1].trim();
					content[2] = it[2].trim();
					content[3] = it[3].trim();
					content[4] = it[4].trim();
					content[5] = it[5].trim();
					String[] last = it[6].split("║");
					content[6] = last[0].trim();
					result.add(content);
				}
				temp = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private ArrayList<TeamVO> getTeamListFromFile() {
		String name;
		String location;
		String abLocation;
		String conference;
		String partition;
		String homeCourt;
		int setUpTime;

		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		TeamVO team;
		ArrayList<String[]> result = readFromFile("src/data/teams/teams");
		for (String[] content : result) {
			name = content[0];
			abLocation = content[1];
			location = content[2];
			conference = content[3];
			partition = content[4];
			homeCourt = content[5];
			setUpTime = Integer.parseInt(content[6]);

			team = new TeamVO(name, abLocation, location, conference,
					partition, homeCourt, setUpTime);
			teams.add(team);
		}
		return teams;
	}

	public void exportToSql() {
		ArrayList<TeamVO> teams = getTeamListFromFile();
		try {
			Connection con = SqlManager.getConnection();
			con.setAutoCommit(false);
			Statement sql = con.createStatement();
			sql.execute("drop table if exists teams");
			sql.execute("create table teams(teamID int not null auto_increment,teamName varchar(40) not null default 'null',"
					+ "abLocation varchar(20) not null default 'null',location varchar(20) not null default 'null',"
					+ "conference varchar(20) not null default 'null',partition varchar(20) not null default 'null',"
					+ "homeCourt varchar(40) not null default 'null',setUpTime int not null default 0,primary key(teamID));");
			int count = 1;
			sql.close();
			PreparedStatement statement = con
					.prepareStatement("INSERT INTO teams VALUES(?, ?,?,?,?,?,?,?)");
			for (TeamVO team : teams) {
				statement.setInt(1, count++);
				statement.setString(2, team.getTeamName());
				statement.setString(3, team.getAbLocation());
				statement.setString(4, team.getLocation());
				statement.setString(5, team.getConference());
				statement.setString(6, team.getPartition());
				statement.setString(7, team.getHomeCourt());
				statement.setInt(8, team.getSetUpTime());
				statement.addBatch();
				/*
				 * sql.execute("insert teams values(" + (count++) + ",'" +
				 * team.getTeamName() + "','" + team.getAbLocation() + "','" +
				 * team.getLocation() + "','" + team.getConference() + "','" +
				 * team.getPartition() + "','" + team.getHomeCourt() + "'," +
				 * team.getSetUpTime() + ")");
				 */

				System.out.println(count);
			}
			statement.executeBatch();
			con.commit();
			statement.close();
			con.close();
		} catch (java.lang.ClassNotFoundException e) {
			System.err.println("ClassNotFoundException:" + e.getMessage());
		} catch (SQLException ex) {
			System.err.println("SQLException:" + ex.getMessage());
		}
	}

	private void exportCalculatedDataToSql(String season, String type) {
		int test = 1;
		createTable(season, type);
		String tableBaseName = season.replace("-", "") + "_" + type + "_";
		String tableAverageName = tableBaseName + "TeamAverage";
		String tableSeasonName = tableBaseName + "TeamSeason";
		try {
			connection = SqlManager.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement seasonStatement = connection
					.prepareStatement("INSERT INTO "
							+ tableSeasonName
							+ " VALUES(?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			PreparedStatement averageStatement = connection
					.prepareStatement("INSERT INTO "
							+ tableAverageName
							+ " VALUES(?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			sql = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ArrayList<TeamVO> teamSeasonInfo = getTeamSeasonInfo(season, type);
			for (int i = 0; i < teamSeasonInfo.size(); i++) {
				TeamVO vo = teamSeasonInfo.get(i);
				seasonStatement.setInt(1, (i + 1));
				seasonStatement.setString(2, vo.getAbLocation());
				seasonStatement.setString(3, season);
				seasonStatement.setString(4, type);
				seasonStatement.setInt(5, vo.getMatchesNum());
				seasonStatement.setDouble(6, vo.getWinRate());
				seasonStatement.setDouble(7, vo.getShootHitNum());
				seasonStatement.setDouble(8, vo.getShootAttemptNum());
				seasonStatement.setDouble(9, vo.getThreeHitNum());
				seasonStatement.setDouble(10, vo.getThreeAttemptNum());
				seasonStatement.setDouble(11, vo.getFreeThrowHitNum());
				seasonStatement.setDouble(12, vo.getFreeThrowAttemptNum());
				seasonStatement.setDouble(13, vo.getOffenReboundNum());
				seasonStatement.setDouble(14, vo.getDefenReboundNum());
				seasonStatement.setDouble(15, vo.getReboundNum());
				seasonStatement.setDouble(16, vo.getAssistNum());
				seasonStatement.setDouble(17, vo.getStealNum());
				seasonStatement.setDouble(18, vo.getBlockNum());
				seasonStatement.setDouble(19, vo.getTurnOverNum());
				seasonStatement.setDouble(20, vo.getFoulNum());
				seasonStatement.setDouble(21, vo.getScore());
				seasonStatement.setDouble(22, vo.getShootHitRate());
				seasonStatement.setDouble(23, vo.getThreeHitRate());
				seasonStatement.setDouble(24, vo.getFreeThrowHitRate());
				seasonStatement.setDouble(25, vo.getOffenRound());
				seasonStatement.setDouble(26, vo.getOffenEfficiency());
				seasonStatement.setDouble(27, vo.getDefenEfficiency());
				seasonStatement.setDouble(28, vo.getOffenReboundEfficiency());
				seasonStatement.setDouble(29, vo.getDefenReboundEfficiency());
				seasonStatement.setDouble(30, vo.getStealEfficiency());
				seasonStatement.setDouble(31, vo.getAssistRate());
				seasonStatement.addBatch();
				System.out.println(test++);
			}

			ArrayList<TeamVO> teamAverageInfo = getTeamAverageInfo(season, type);
			for (int i = 0; i < teamAverageInfo.size(); i++) {
				TeamVO vo = teamAverageInfo.get(i);
				averageStatement.setInt(1, (i + 1));
				averageStatement.setString(2, vo.getAbLocation());
				averageStatement.setString(3, season);
				averageStatement.setString(4, type);
				averageStatement.setInt(5, vo.getMatchesNum());
				averageStatement.setDouble(6, vo.getWinRate());
				averageStatement.setDouble(7, vo.getShootHitNum());
				averageStatement.setDouble(8, vo.getShootAttemptNum());
				averageStatement.setDouble(9, vo.getThreeHitNum());
				averageStatement.setDouble(10, vo.getThreeAttemptNum());
				averageStatement.setDouble(11, vo.getFreeThrowHitNum());
				averageStatement.setDouble(12, vo.getFreeThrowAttemptNum());
				averageStatement.setDouble(13, vo.getOffenReboundNum());
				averageStatement.setDouble(14, vo.getDefenReboundNum());
				averageStatement.setDouble(15, vo.getReboundNum());
				averageStatement.setDouble(16, vo.getAssistNum());
				averageStatement.setDouble(17, vo.getStealNum());
				averageStatement.setDouble(18, vo.getBlockNum());
				averageStatement.setDouble(19, vo.getTurnOverNum());
				averageStatement.setDouble(20, vo.getFoulNum());
				averageStatement.setDouble(21, vo.getScore());
				averageStatement.setDouble(22, vo.getShootHitRate());
				averageStatement.setDouble(23, vo.getThreeHitRate());
				averageStatement.setDouble(24, vo.getFreeThrowHitRate());
				averageStatement.setDouble(25, vo.getOffenRound());
				averageStatement.setDouble(26, vo.getOffenEfficiency());
				averageStatement.setDouble(27, vo.getDefenEfficiency());
				averageStatement.setDouble(28, vo.getOffenReboundEfficiency());
				averageStatement.setDouble(29, vo.getDefenReboundEfficiency());
				averageStatement.setDouble(30, vo.getStealEfficiency());
				averageStatement.setDouble(31, vo.getAssistRate());
				averageStatement.addBatch();
				System.out.println(test++);
			}
			seasonStatement.executeBatch();
			averageStatement.executeBatch();

			connection.commit();
			seasonStatement.close();
			averageStatement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeMySql();
		}

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

	public ArrayList<MatchVO> getRecentMatches(String teamName) {
		// TODO 自动生成的方法存根
		return getRencentMatches(teamName, 5);
	}

	public ArrayList<MatchVO> getMatches(String teamName) {
		return getRencentMatches(teamName, -1);
	}

	public ArrayList<TeamVO> getTeamBaseInfo(String name) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		try {
			connection = SqlManager.getConnection();
			sql = connection.createStatement();
			String query = "select * from teams where abLocation like '%"
					+ name + "%'or teamName like '%" + name + "%'";
			resultSet = sql.executeQuery(query);
			resultSet.next();
			String teamName = resultSet.getString("teamName");
			String location = resultSet.getString("location");
			String abLocation = resultSet.getString("abLocation");
			String conference = resultSet.getString("conference");
			String partition = resultSet.getString("partition");
			String homeCourt = resultSet.getString("homeCourt");
			int setUpTime = resultSet.getInt("setUpTime");
			TeamVO teamVO = new TeamVO(teamName, abLocation, location,
					conference, partition, homeCourt, setUpTime);
			teams.add(teamVO);

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

	public ArrayList<String> getPlayersByTeam(String teamAbLocation) {
		// TODO 自动生成的方法存根
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

	/**
	 * 根据球队名得到近期比赛的某种数据 模糊查找（觉得好像意义不大）
	 * 
	 * @param teamName
	 * @param num
	 *            得到比赛的场数 ，-1则是取出全部数据
	 * @return
	 */
	private ArrayList<MatchVO> getRencentMatches(String teamName, int num) {
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
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
				String type = resultSet.getString("type");
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

				ArrayList<RecordVO> records = new ArrayList<RecordVO>();
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
					double shootHitRate = rSet.getDouble("shootHitRate");
					int threeHitNum = rSet.getInt("threeHitNum");
					int threeAttemptNum = rSet.getInt("threeAttemptNum");
					double threeHitRate = rSet.getDouble("threeHitRate");
					int freeThrowHitNum = rSet.getInt("freeThrowHitNum");
					int freeThrowAttemptNum = rSet
							.getInt("freeThrowAttemptNum");
					double freeThrowHitRate = rSet
							.getDouble("freeThrowHitRate");
					int offenReboundNum = rSet.getInt("offenReboundNum");
					int defenReboundNum = rSet.getInt("defenReboundNum");
					int reboundNum = rSet.getInt("reboundNum");
					int assistNum = rSet.getInt("assistNum");
					int stealNum = rSet.getInt("stealNum");
					int blockNum = rSet.getInt("blockNum");
					int turnOverNum = rSet.getInt("turnOverNum");
					int foulNum = rSet.getInt("foulNum");
					int score = rSet.getInt("score");
					RecordVO recordPO = new RecordVO(team, playerName,
							position, presentTime, shootHitNum,
							shootAttemptNum, shootHitRate, threeHitNum,
							threeAttemptNum, threeHitRate, freeThrowHitNum,
							freeThrowAttemptNum, freeThrowHitRate,
							offenReboundNum, defenReboundNum, reboundNum,
							assistNum, stealNum, blockNum, turnOverNum,
							foulNum, score);
					records.add(recordPO);
				}
				rSet.close();
				stat.close();

				MatchVO matchVO = new MatchVO(matchID, season, date, type,
						visingTeam, homeTeam, visitingScore, homeScore,
						detailScores, records);
				result.add(matchVO);
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

	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String type) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		for (int i = 0; i < teamBaseList.size(); i++) {
			TeamVO vo = teamBaseList.get(i);
			TeamVO teamVO = calculateTeamInfo(vo, season,
					Match.changeTypeCHToEN(type), false);
			if (teamVO != null) {
				result.add(teamVO);
			}
		}
		return result;
	}

	public ArrayList<TeamVO> getTeamAverageInfo(String season, String type) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		for (int i = 0; i < teamBaseList.size(); i++) {
			TeamVO vo = teamBaseList.get(i);
			TeamVO teamVO = calculateTeamInfo(vo, season,
					Match.changeTypeCHToEN(type), true);
			if (teamVO != null) {
				result.add(teamVO);
			}
		}
		return result;
	}

	private TeamVO calculateTeamInfo(TeamVO vo, String season, String type,
			boolean isAverage) {
		String teamName = vo.getTeamName();
		String team = vo.getAbLocation();
		String location = vo.getLocation();
		String abLocation = vo.getAbLocation();
		String conference = vo.getConference();
		String partition = vo.getPartition();
		String homeCourt = vo.getHomeCourt();
		int setUpTime = vo.getSetUpTime();

		String testSeason = "";
		boolean isCalculate = false;// 标记是否有参加过比赛，防止有球队没有参加这种类型的比赛但是仍对其检索

		int matchesNum = 0; // 比赛场数
		int winNum = 0;
		double shootHitNum = 0; // 投篮命中数
		double shootAttemptNum = 0; // 投篮出手次数
		double threeHitNum = 0; // 三分命中数
		double threeAttemptNum = 0; // 三分出手数
		double freeThrowHitNum = 0; // 罚球命中数
		double freeThrowAttemptNum = 0; // 罚球出手数
		double offenReboundNum = 0; // 进攻篮板数
		double defenReboundNum = 0; // 防守篮板数
		double reboundNum = 0;// 篮板数
		double assistNum = 0;// 助攻数
		double stealNum = 0;// 抢断数
		double blockNum = 0;// 盖帽数
		double turnOverNum = 0;// 失误数
		double foulNum = 0;// 犯规数
		double score = 0;// 比赛得分
		double shootHitRate = 0;// 投篮命中率
		double threeHitRate = 0;// 三分命中率
		double freeThrowHitRate = 0;// 罚球命中率
		double winRate = 0; // 胜率
		double offenRound = 0; // 进攻回合
		double offenEfficiency = 0; // 进攻效率
		double defenEfficiency = 0; // 防守效率
		double offenReboundEfficiency = 0; // 进攻篮板效率
		double defenReboundEfficiency = 0; // 防守篮板效率
		double stealEfficiency = 0; // 抢断效率
		double assistEfficiency = 0; // 助攻率

		int dsShootHitNum = 0;
		int dsShootAttempNum = 0;
		int dsFreeThrowAttemptNum = 0;
		int dsTurnOverNum = 0;
		int dsScore = 0;
		int dsOffenReboundNum = 0;
		int dsDefenReboundNum = 0;
		int dsOffenReboundNumAll = 0;
		int dsDefenReboundNumAll = 0;
		double dsOffenRound = 0;

		boolean isBad = false;

		ArrayList<MatchVO> allMatches = new ArrayList<MatchVO>();

		if (team.equals("NOP") || team.equals("BKN")) {
			isBad = true;
		}

		allMatches = matchData.getMatchData(season, type, "all", "all", "all");

		for (int i = 0; i < allMatches.size(); i++) {
			MatchVO matchVO = allMatches.get(i);
			testSeason = matchVO.getSeason();
			int homeScore = matchVO.getHomeScore();
			int visitingScore = matchVO.getVisitingScore();
			String homeTeam = matchVO.getHomeTeam();
			String visitingTeam = matchVO.getVisitingTeam();

			boolean isHomeTeam = false;
			boolean isVisitingTeam = false;

			if (team.equals("NOP") || team.equals("NOH")) {
				isHomeTeam = homeTeam.equals("NOP") || homeTeam.equals("NOH");
				isVisitingTeam = visitingTeam.equals("NOP")
						|| visitingTeam.equals("NOH");
			} else if (team.equals("BKN") || team.equals("NJN")) {
				isHomeTeam = homeTeam.equals("BKN") || homeTeam.equals("NJN");
				isVisitingTeam = visitingTeam.equals("BKN")
						|| visitingTeam.equals("NJN");
			} else {
				isHomeTeam = homeTeam.equals(team);
				isVisitingTeam = visitingTeam.equals(team);
			}
			if (isHomeTeam) {
				matchesNum++;
				score += homeScore;
				dsScore += visitingScore;
				if (homeScore > visitingScore) {
					winNum++;
				}
			} else if (isVisitingTeam) {
				matchesNum++;
				score += visitingScore;
				dsScore += homeScore;
				if (visitingScore > homeScore) {
					winNum++;
				}
			}
			if (isHomeTeam || isVisitingTeam) {
				isCalculate = true;
				int perShootAttemptNum = 0;
				int perFreeThrowAttemptNum = 0;
				int perOffenReboundNum = 0;
				int perDefenReboundNum = 0;
				int perMissShoot = 0;
				int perTurnOverNum = 0;

				ArrayList<RecordVO> records = matchVO.getRecords();
				for (RecordVO recordVO : records) {
					boolean isRecord = false;
					if (team.equals("NOP") || team.equals("NOH")) {
						isRecord = recordVO.getTeam().equals("NOP")
								|| recordVO.getTeam().equals("NOH");
					} else if (team.equals("BKN") || team.equals("NJN")) {
						isRecord = recordVO.getTeam().equals("BKN")
								|| recordVO.getTeam().equals("NJN");
					} else {
						isRecord = recordVO.getTeam().equals(team);
					}
					if (isRecord) {
						shootHitNum += recordVO.getShootHitNum(); // 投篮命中数
						shootAttemptNum += recordVO.getShootAttemptNum(); // 投篮出手次数
						threeHitNum += recordVO.getThreeHitNum(); // 三分命中数
						threeAttemptNum += recordVO.getThreeAttemptNum(); // 三分出手数
						freeThrowHitNum += recordVO.getFreeThrowHitNum(); // 罚球命中数
						freeThrowAttemptNum += recordVO
								.getFreeThrowAttemptNum(); // 罚球出手数
						offenReboundNum += recordVO.getOffenReboundNum(); // 进攻篮板数
						defenReboundNum += recordVO.getDefenReboundNum(); // 防守篮板数
						reboundNum += recordVO.getReboundNum();// 篮板数
						assistNum += recordVO.getAssistNum();// 助攻数
						stealNum += recordVO.getStealNum();// 抢断数
						blockNum += recordVO.getBlockNum();// 盖帽数
						turnOverNum += recordVO.getTurnOverNum();// 失误数
						foulNum += recordVO.getFoulNum();// 犯规数

						perShootAttemptNum += recordVO.getShootAttemptNum();
						perFreeThrowAttemptNum += recordVO
								.getFreeThrowAttemptNum();
						perOffenReboundNum += recordVO.getOffenReboundNum();
						perDefenReboundNum += recordVO.getDefenReboundNum();
						perMissShoot += recordVO.getShootAttemptNum()
								- recordVO.getShootHitNum();
						perTurnOverNum += recordVO.getTurnOverNum();

					} else {
						// 计算对手的
						dsShootHitNum += recordVO.getShootHitNum();
						dsShootAttempNum += recordVO.getShootAttemptNum();
						dsFreeThrowAttemptNum += recordVO
								.getFreeThrowAttemptNum();
						dsTurnOverNum += recordVO.getTurnOverNum();
						dsOffenReboundNum += recordVO.getOffenReboundNum();
						dsOffenReboundNumAll += recordVO.getOffenReboundNum();
						dsDefenReboundNum += recordVO.getDefenReboundNum();
						dsDefenReboundNumAll += recordVO.getDefenReboundNum();
					}

				}
				shootHitRate = (double) shootHitNum / shootAttemptNum;// 投篮命中率
				threeHitRate = (double) threeHitNum / threeAttemptNum;// 三分命中率
				freeThrowHitRate = (double) freeThrowHitNum
						/ freeThrowAttemptNum;// 罚球命中率
				winRate = (double) winNum / matchesNum; // 胜率
				// 进攻回合——每场的进攻回合累加
				double temp = perShootAttemptNum
						+ 0.4
						* perFreeThrowAttemptNum
						- 1.07
						* (perOffenReboundNum
								/ (double) (perOffenReboundNum + dsDefenReboundNum) * (perMissShoot))
						+ 1.07 * perTurnOverNum;
				offenRound += temp;

				double dstemp = dsShootAttempNum
						+ 0.4
						* dsFreeThrowAttemptNum
						- 1.07
						* (dsOffenReboundNum
								/ (double) (dsOffenReboundNum + perDefenReboundNum) * (dsShootAttempNum - dsShootHitNum))
						+ 1.07 * dsTurnOverNum;
				dsOffenRound += dstemp;

				dsShootHitNum = 0;
				dsShootAttempNum = 0;
				dsFreeThrowAttemptNum = 0;
				dsTurnOverNum = 0;
				dsOffenReboundNum = 0;
				dsDefenReboundNum = 0;
			}
		}
		if (isCalculate) {
			offenEfficiency = (double) score / offenRound * 100; // 进攻效率
			defenEfficiency = (double) dsScore / dsOffenRound * 100; // 防守效率
			offenReboundEfficiency = (double) offenReboundNum
					/ (offenReboundNum + dsDefenReboundNumAll); // 进攻篮板效率
			defenReboundEfficiency = (double) defenReboundNum
					/ (defenReboundNum + dsOffenReboundNumAll); // 防守篮板效率
			stealEfficiency = (double) stealNum / dsOffenRound * 100; // 抢断效率
			assistEfficiency = (double) assistNum / offenRound * 100; // 助攻率

			if (isAverage) {
				// 需要的是场均数据，要除以比赛场数
				offenRound = offenRound / (double) matchesNum;
				shootHitNum = shootHitNum / (double) matchesNum;
				shootAttemptNum = shootAttemptNum / (double) matchesNum;
				threeHitNum = threeHitNum / (double) matchesNum;
				threeAttemptNum = threeAttemptNum / (double) matchesNum;
				freeThrowHitNum = freeThrowHitNum / (double) matchesNum;
				freeThrowAttemptNum = freeThrowAttemptNum / (double) matchesNum;
				offenReboundNum = offenReboundNum / (double) matchesNum;
				defenReboundNum = defenReboundNum / (double) matchesNum;
				reboundNum = reboundNum / (double) matchesNum;
				assistNum = assistNum / (double) matchesNum;
				stealNum = stealNum / (double) matchesNum;
				blockNum = blockNum / (double) matchesNum;
				turnOverNum = turnOverNum / (double) matchesNum;
				foulNum = foulNum / (double) matchesNum;
				score = score / (double) matchesNum;
			}
		}
		boolean flagNOP = false;
		int flagNOPInt = season.compareTo("12-13");
		if (flagNOPInt <= 0) {
			flagNOP = true;
		}
		if (flagNOP && abLocation.equals("NOP")) {
			abLocation = "NOH";
			// isNOPSeason = true;
		}

		boolean flagBKN = false;
		int flagBKNInt = season.compareTo("11-12");
		if (flagBKNInt <= 0) {
			flagBKN = true;
		}
		if (flagBKN && abLocation.equals("BKN")) {
			abLocation = "NJN";
			// isBKNSeason = true;
		}

		TeamVO teamVO = new TeamVO(teamName, abLocation, location, conference,
				partition, homeCourt, setUpTime, matchesNum, shootHitNum,
				shootAttemptNum, threeHitNum, threeAttemptNum, freeThrowHitNum,
				freeThrowAttemptNum, offenReboundNum, defenReboundNum,
				reboundNum, assistNum, stealNum, blockNum, turnOverNum,
				foulNum, score, shootHitRate, threeHitRate, freeThrowHitRate,
				winRate, offenRound, offenEfficiency, defenEfficiency,
				offenReboundEfficiency, defenReboundEfficiency,
				stealEfficiency, assistEfficiency);
		return teamVO;
	}

	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String type,
			String name) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		TeamVO vo = null;
		// 默认先是根据完整的缩写查找
		vo = teamsBaseInfo.get(name);
		// 找不到再去遍历
		if (vo == null) {
			for (int i = 0; i < teamBaseList.size(); i++) {
				vo = teamBaseList.get(i);
				String teamName = vo.getTeamName();
				String team = vo.getAbLocation();

				boolean flag = false;
				if (name.equals("NOP") || name.equals("NOH")) {
					name = teamsBaseInfo.get("NOP").getTeamName();
					if (name == null) {
						name = teamsBaseInfo.get("NOH").getTeamName();
					}
				}
				name = name.toLowerCase();
				flag = teamName.toLowerCase().contains(name)
						|| team.toLowerCase().contains(name);
				if (!flag) {
					// 当前球队不是我要的球队，就跳过他不进行计算
					continue;
				}
				TeamVO teamVO = calculateTeamInfo(vo, season, type, false);
				if (teamVO != null) {
					result.add(teamVO);
				}
			}
		}
		return result;
	}

	public ArrayList<TeamVO> getTeamAverageInfo(String season, String type,
			String name) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		TeamVO vo = null;
		// 默认先是根据完整的缩写查找
		vo = teamsBaseInfo.get(name);
		// 找不到再去遍历
		if (vo == null) {
			for (int i = 0; i < teamBaseList.size(); i++) {
				vo = teamBaseList.get(i);
				String teamName = vo.getTeamName();
				String team = vo.getAbLocation();

				boolean flag = false;
				if (name.equals("NOP") || name.equals("NOH")) {
					name = teamsBaseInfo.get("NOP").getTeamName();
					if (name == null) {
						name = teamsBaseInfo.get("NOH").getTeamName();
					}
				}
				name = name.toLowerCase();
				flag = teamName.toLowerCase().contains(name)
						|| team.toLowerCase().contains(name);
				if (!flag) {
					// 当前球队不是我要的球队，就跳过他不进行计算
					continue;
				}
				TeamVO teamVO = calculateTeamInfo(vo, season, type, true);
				if (teamVO != null) {
					result.add(teamVO);
				}
			}
		}
		return result;
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

	private void createTable(String season, String type) {
		try {
			String tableBaseName = season.replace("-", "") + "_" + type + "_";
			String tableAverageName = tableBaseName + "TeamAverage";
			String tableSeasonName = tableBaseName + "TeamSeason";
			connection = SqlManager.getConnection();
			sql = connection.createStatement();

			sql.execute("drop table if exists " + tableAverageName);
			sql.execute("create table " + tableAverageName
					+ "(teamDataID int not null auto_increment,"
					+ "team varchar(20) not null default 'null',"
					+ "season varchar(20) not null default 'null',"
					+ "type varchar(20) not null default 'null',"
					+ "matchesNum int not null default 0,"
					+ "winRate double not null default 0,"
					+ "shootHitNum double not null default 0,"
					+ "shootAttemptNum double not null default 0,"
					+ "threeHitNum double not null default 0,"
					+ "threeAttemptNum double not null default 0,"
					+ "freeThrowHitNum double not null default 0,"
					+ "freeThrowAttemptNum double not null default 0,"
					+ "offenReboundNum double not null default 0,"
					+ "defenReboundNum double not null default 0,"
					+ "reboundNum double not null default 0,"
					+ "assistNum double not null default 0,"
					+ "stealNum double not null default 0,"
					+ "blockNum double not null default 0,"
					+ "turnOverNum double not null default 0,"
					+ "foulNum double not null default 0,"
					+ "score double not null default 0,"
					+ "shootHitRate double not null default 0,"
					+ "threeHitRate double not null default 0,"
					+ "freeThrowHitRate double not null default 0,"
					+ "offenRound double not null default 0,"
					+ "offenEfficiency double not null default 0,"
					+ "defenEfficiency double not null default 0,"
					+ "offenReboundEfficiency double not null default 0,"
					+ "defenReboundEfficiency double not null default 0,"
					+ "stealEfficiency double not null default 0,"
					+ "assistRate double not null default 0,"
					+ "primary key(teamDataID));");
			sql.close();

			sql = connection.createStatement();
			sql.execute("drop table if exists " + tableSeasonName);
			sql.execute("create table " + tableSeasonName
					+ "(teamDataID int not null auto_increment,"
					+ "team varchar(20) not null default 'null',"
					+ "season varchar(20) not null default 'null',"
					+ "type varchar(20) not null default 'null',"
					+ "matchesNum int not null default 0,"
					+ "winRate double not null default 0,"
					+ "shootHitNum double not null default 0,"
					+ "shootAttemptNum double not null default 0,"
					+ "threeHitNum double not null default 0,"
					+ "threeAttemptNum double not null default 0,"
					+ "freeThrowHitNum double not null default 0,"
					+ "freeThrowAttemptNum double not null default 0,"
					+ "offenReboundNum double not null default 0,"
					+ "defenReboundNum double not null default 0,"
					+ "reboundNum double not null default 0,"
					+ "assistNum double not null default 0,"
					+ "stealNum double not null default 0,"
					+ "blockNum double not null default 0,"
					+ "turnOverNum double not null default 0,"
					+ "foulNum double not null default 0,"
					+ "score double not null default 0,"
					+ "shootHitRate double not null default 0,"
					+ "threeHitRate double not null default 0,"
					+ "freeThrowHitRate double not null default 0,"
					+ "offenRound double not null default 0,"
					+ "offenEfficiency double not null default 0,"
					+ "defenEfficiency double not null default 0,"
					+ "offenReboundEfficiency double not null default 0,"
					+ "defenReboundEfficiency double not null default 0,"
					+ "stealEfficiency double not null default 0,"
					+ "assistRate double not null default 0,"
					+ "primary key(teamDataID));");

			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeMySql();
		}
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
