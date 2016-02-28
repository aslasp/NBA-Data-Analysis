package SQLHelper.Calculator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import SQLHelper.SqlManager;

/**
 * 
 * 统计分析球员所有比赛中的数据，计算出赛季和场均的技术数据
 *
 */
public class NewPlayerMatchDataCalculator {
	Connection con;
	int sqlID = 1;
	String playerName = null;
	String season = null;
	String owingTeam = "";
	int playedGames = 0;
	int gameStartingNum = 0;
	double reboundNum = 0;
	double offenReboundNum = 0;
	double defenReboundNum = 0;
	double assistNum = 0;
	double presentTime = 0;
	double shootHitRate = 0;
	double threeHitNum = 0;
	double threeAttemptNum = 0;
	double threeHitRate = 0;
	double freeThrowHitRate = 0;
	double stealNum = 0;
	double blockNum = 0;
	double foulNum = 0;
	double turnOverNum = 0;
	double score = 0;
	int freeThrowHitNum = 0;
	int freeThrowAttemptNum = 0;
	double efficiency = 0;
	double GmScEfficiencyValue = 0;
	double trueHitRate = 0;
	double shootEfficiency = 0;
	double reboundRate = 0;
	double offenReboundRate = 0;
	double defenReboundRate = 0;
	double assistRate = 0;
	double stealRate = 0;
	double blockRate = 0;
	double turnOverRate = 0;
	double usageRate = 0;
	int reboundNumSeason = 0;
	int offenReboundNumSeason = 0;
	int defenReboundNumSeason = 0;
	int assistNumSeason = 0;
	int presentTimeSeason = 0;
	int shootHitNum = 0;
	int shootAttemptNum = 0;
	int shootHitNumSeason = 0;
	int shootAttemptNumSeason = 0;
	double shootHitRateSeason = 0;
	double threeHitRateSeason = 0;
	double threeHitNumSeason = 0;
	double threeAttemptNumSeason = 0;
	double freeThrowHitRateSeason = 0;
	int stealNumSeason = 0;
	int blockNumSeason = 0;
	int foulNumSeason = 0;
	int turnOverNumSeason = 0;
	int scoreSeason = 0;
	int freeThrowHitNumSeason = 0;
	int freeThrowAttemptNumSeason = 0;
	double recentFiveMatchesScoreUpRate = 0;
	double recentFiveMatchesReboundUpRate = 0;
	double recentFiveMatchesAssistUpRate = 0;
	double efficiencySeason = 0;
	double GmScEfficiencyValueSeason = 0;
	double trueHitRateSeason = 0;
	double shootEfficiencySeason = 0;
	double reboundRateSeason = 0;
	double offenReboundRateSeason = 0;
	double defenReboundRateSeason = 0;
	double assistRateSeason = 0;
	double stealRateSeason = 0;
	double blockRateSeason = 0;
	double turnOverRateSeason = 0;
	double usageRateSeason = 0;
	double matchTimeSeason = 0;
	int allReboundNumSeason = 0;
	int dsAllReboundNumSeason = 0;
	int teamOffenReboundNumSeason = 0;
	int dsTeamOffenReboundNumSeason = 0;
	int teamDefenReboundNumSeason = 0;
	int dsTeamDefenReboundNumSeason = 0;
	int teamShootHitNumSeason = 0;
	int dsTeamTwoAttemptNumSeason = 0;
	int teamShootAttemptNumSeason = 0;
	int teamFreeThrowAttemptNumSeason = 0;
	int teamTurnOverNumSeason = 0;
	int doubleDoubleNum = 0;
	double dsOffenRoundNumSeason = 0;
	double score_rebound_assistSeason = 0;
	double score_rebound_assist = 0;
	PreparedStatement averageStatement;
	PreparedStatement seasonStatement;

	public NewPlayerMatchDataCalculator() {
		try {
			con = SqlManager.getConnection();
			con.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public void reset() {
		playerName = null;
		owingTeam = "";
		playedGames = 0;
		gameStartingNum = 0;
		reboundNum = 0;
		offenReboundNum = 0;
		defenReboundNum = 0;
		assistNum = 0;
		presentTime = 0;
		shootHitRate = 0;
		threeHitNum = 0;
		threeAttemptNum = 0;
		threeHitRate = 0;
		freeThrowHitRate = 0;
		stealNum = 0;
		blockNum = 0;
		foulNum = 0;
		turnOverNum = 0;
		score = 0;
		freeThrowHitNum = 0;
		freeThrowAttemptNum = 0;
		efficiency = 0;
		GmScEfficiencyValue = 0;
		trueHitRate = 0;
		shootEfficiency = 0;
		reboundRate = 0;
		offenReboundRate = 0;
		defenReboundRate = 0;
		assistRate = 0;
		stealRate = 0;
		blockRate = 0;
		// foulRate = 0;
		turnOverRate = 0;
		usageRate = 0;
		reboundNumSeason = 0;
		offenReboundNumSeason = 0;
		defenReboundNumSeason = 0;
		assistNumSeason = 0;
		presentTimeSeason = 0;
		shootHitNum = 0;
		shootAttemptNum = 0;
		shootHitNumSeason = 0;
		shootAttemptNumSeason = 0;
		shootHitRateSeason = 0;
		threeHitRateSeason = 0;
		threeHitNumSeason = 0;
		threeAttemptNumSeason = 0;
		freeThrowHitRateSeason = 0;
		stealNumSeason = 0;
		blockNumSeason = 0;
		foulNumSeason = 0;
		turnOverNumSeason = 0;
		scoreSeason = 0;
		freeThrowHitNumSeason = 0;
		freeThrowAttemptNumSeason = 0;
		recentFiveMatchesScoreUpRate = 0;
		recentFiveMatchesReboundUpRate = 0;
		recentFiveMatchesAssistUpRate = 0;
		efficiencySeason = 0;
		GmScEfficiencyValueSeason = 0;
		trueHitRateSeason = 0;
		shootEfficiencySeason = 0;
		reboundRateSeason = 0;
		offenReboundRateSeason = 0;
		defenReboundRateSeason = 0;
		assistRateSeason = 0;
		stealRateSeason = 0;
		blockRateSeason = 0;
		turnOverRateSeason = 0;
		usageRateSeason = 0;
		matchTimeSeason = 0;
		allReboundNumSeason = 0;
		dsAllReboundNumSeason = 0;
		teamOffenReboundNumSeason = 0;
		dsTeamOffenReboundNumSeason = 0;
		teamDefenReboundNumSeason = 0;
		dsTeamDefenReboundNumSeason = 0;
		teamShootHitNumSeason = 0;
		dsTeamTwoAttemptNumSeason = 0;
		teamShootAttemptNumSeason = 0;
		teamFreeThrowAttemptNumSeason = 0;
		teamTurnOverNumSeason = 0;
		doubleDoubleNum = 0;
		score_rebound_assistSeason = 0;
		score_rebound_assist = 0;
	}

	public void format() {
		DecimalFormat dec = new DecimalFormat("0.000");

		reboundNum = Double.parseDouble(dec.format(reboundNum));
		offenReboundNum = Double.parseDouble(dec.format(offenReboundNum));
		defenReboundNum = Double.parseDouble(dec.format(defenReboundNum));
		assistNum = Double.parseDouble(dec.format(assistNum));
		shootHitRate = Double.parseDouble(dec.format(shootHitRate));
		threeHitNum = Double.parseDouble(dec.format(threeHitNum));
		threeAttemptNum = Double.parseDouble(dec.format(threeAttemptNum));
		threeHitRate = Double.parseDouble(dec.format(threeHitRate));
		freeThrowHitRate = Double.parseDouble(dec.format(freeThrowHitRate));
		offenReboundNum = Double.parseDouble(dec.format(offenReboundNum));
		defenReboundNum = Double.parseDouble(dec.format(defenReboundNum));
		presentTime=Double.parseDouble(dec.format(presentTime));
		stealNum = Double.parseDouble(dec.format(stealNum));
		blockNum = Double.parseDouble(dec.format(blockNum));
		foulNum = Double.parseDouble(dec.format(foulNum));
		turnOverNum = Double.parseDouble(dec.format(turnOverNum));
		score = Double.parseDouble(dec.format(score));
		efficiency = Double.parseDouble(dec.format(efficiency));
		GmScEfficiencyValue = Double.parseDouble(dec
				.format(GmScEfficiencyValue));
		trueHitRate = Double.parseDouble(dec.format(trueHitRate));
		shootEfficiency = Double.parseDouble(dec.format(shootEfficiency));
		reboundRate = Double.parseDouble(dec.format(reboundRate));
		offenReboundRate = Double.parseDouble(dec.format(offenReboundRate));
		defenReboundRate = Double.parseDouble(dec.format(defenReboundRate));
		assistRate = Double.parseDouble(dec.format(assistRate));
		stealRate = Double.parseDouble(dec.format(stealRate));
		blockRate = Double.parseDouble(dec.format(blockRate));

		turnOverRate = Double.parseDouble(dec.format(turnOverRate));
		usageRate = Double.parseDouble(dec.format(usageRate));
		shootHitRateSeason = Double.parseDouble(dec.format(shootHitRateSeason));
		threeHitRateSeason = Double.parseDouble(dec.format(threeHitRateSeason));
		threeHitNumSeason = Double.parseDouble(dec.format(threeHitNumSeason));
		threeAttemptNumSeason = Double.parseDouble(dec
				.format(threeAttemptNumSeason));
		freeThrowHitRateSeason = Double.parseDouble(dec
				.format(freeThrowHitRateSeason));
		recentFiveMatchesScoreUpRate = Double.parseDouble(dec
				.format(recentFiveMatchesScoreUpRate));
		recentFiveMatchesReboundUpRate = Double.parseDouble(dec
				.format(recentFiveMatchesReboundUpRate));
		recentFiveMatchesAssistUpRate = Double.parseDouble(dec
				.format(recentFiveMatchesAssistUpRate));
		efficiencySeason = Double.parseDouble(dec.format(efficiencySeason));
		GmScEfficiencyValueSeason = Double.parseDouble(dec
				.format(GmScEfficiencyValueSeason));
		trueHitRateSeason = Double.parseDouble(dec.format(trueHitRateSeason));
		shootEfficiencySeason = Double.parseDouble(dec
				.format(shootEfficiencySeason));
		reboundRateSeason = Double.parseDouble(dec.format(reboundRateSeason));
		offenReboundRateSeason = Double.parseDouble(dec
				.format(offenReboundRateSeason));
		defenReboundRateSeason = Double.parseDouble(dec
				.format(defenReboundRateSeason));
		assistRateSeason = Double.parseDouble(dec.format(assistRateSeason));
		stealRateSeason = Double.parseDouble(dec.format(stealRateSeason));
		blockRateSeason = Double.parseDouble(dec.format(blockRateSeason));
		turnOverRateSeason = Double.parseDouble(dec.format(turnOverRateSeason));
		usageRateSeason = Double.parseDouble(dec.format(usageRateSeason));
		matchTimeSeason = Double.parseDouble(dec.format(matchTimeSeason));
		score_rebound_assist = Double.parseDouble(dec
				.format(score_rebound_assist));
		score_rebound_assistSeason = Double.parseDouble(dec
				.format(score_rebound_assistSeason));
	}

	public void exportToSQL() {

		System.out.println(sqlID);

		format();

		try {
			seasonStatement.setInt(1, sqlID);
			seasonStatement.setString(2, playerName.replace("''", "'"));
			seasonStatement.setString(3, season);
			seasonStatement.setString(4, owingTeam);
			seasonStatement.setInt(5, playedGames);
			seasonStatement.setInt(6, gameStartingNum);
			seasonStatement.setInt(7, reboundNumSeason);
			seasonStatement.setInt(8, assistNumSeason);
			seasonStatement.setInt(9, presentTimeSeason);
			seasonStatement.setDouble(10, shootHitRateSeason);
			seasonStatement.setDouble(11, threeHitRateSeason);
			seasonStatement.setDouble(12, freeThrowHitRateSeason);
			seasonStatement.setInt(13, offenReboundNumSeason);
			seasonStatement.setInt(14, defenReboundNumSeason);
			seasonStatement.setInt(15, stealNumSeason);
			seasonStatement.setInt(16, blockNumSeason);
			seasonStatement.setInt(17, foulNumSeason);
			seasonStatement.setInt(18, turnOverNumSeason);
			seasonStatement.setInt(19, scoreSeason);
			seasonStatement.setDouble(20, efficiencySeason);
			seasonStatement.setDouble(21, recentFiveMatchesScoreUpRate);
			seasonStatement.setDouble(22, recentFiveMatchesReboundUpRate);
			seasonStatement.setDouble(23, recentFiveMatchesAssistUpRate);
			seasonStatement.setDouble(24, GmScEfficiencyValueSeason);
			seasonStatement.setDouble(25, trueHitRateSeason);
			seasonStatement.setDouble(26, shootEfficiencySeason);
			seasonStatement.setDouble(27, reboundRateSeason);
			seasonStatement.setDouble(28, offenReboundRateSeason);
			seasonStatement.setDouble(29, defenReboundRateSeason);
			seasonStatement.setDouble(30, assistRateSeason);
			seasonStatement.setDouble(31, stealRateSeason);
			seasonStatement.setDouble(32, blockRateSeason);
			seasonStatement.setDouble(33, turnOverRateSeason);
			seasonStatement.setDouble(34, usageRateSeason);
			seasonStatement.setDouble(35, score_rebound_assistSeason);
			seasonStatement.setDouble(36, doubleDoubleNum);
			seasonStatement.addBatch();
			/*
			 * sql.execute("insert playerMatchDataSeason values(" + sqlID + ",'"
			 * + playerName + "','" + season + "','" + owingTeam + "'," +
			 * playedGames + "," + gameStartingNum + "," + reboundNumSeason +
			 * "," + assistNumSeason + ",'" + presentTimeSeason + "'," +
			 * shootHitRateSeason + "," + threeHitRateSeason + "," +
			 * freeThrowHitRateSeason + "," + offenReboundNumSeason + "," +
			 * defenReboundNumSeason + "," + stealNumSeason + "," +
			 * blockNumSeason + "," + foulNumSeason + "," + turnOverNumSeason +
			 * "," + scoreSeason + "," + efficiencySeason + "," +
			 * recentFiveMatchesScoreUpRate + "," +
			 * recentFiveMatchesReboundUpRate + "," +
			 * recentFiveMatchesAssistUpRate + "," + GmScEfficiencyValueSeason +
			 * "," + trueHitRateSeason + "," + shootEfficiencySeason + "," +
			 * reboundRateSeason + "," + offenReboundRateSeason + "," +
			 * defenReboundRateSeason + "," + assistRateSeason + "," +
			 * stealRateSeason + "," + blockRateSeason + "," +
			 * turnOverRateSeason + "," + usageRateSeason
			 * +","+score_rebound_assistSeason+","+doubleDoubleNum+")");
			 * sql.close();
			 * 
			 * Statement sql2 = con.createStatement();
			 * sql2.execute("insert playerMatchDataAverage values(" + sqlID +
			 * ",'" + playerName + "','" + season + "','" + owingTeam + "'," +
			 * playedGames + "," + gameStartingNum + "," + reboundNum + "," +
			 * assistNum + ",'" + presentTime + "'," + shootHitRate + "," +
			 * threeHitRate + "," + freeThrowHitRate + "," + offenNum + "," +
			 * defenNum + "," + stealNum + "," + blockNum + "," + foulNum + ","
			 * + turnOverNum + "," + score + "," + efficiency + "," +
			 * recentFiveMatchesScoreUpRate + "," +
			 * recentFiveMatchesReboundUpRate + "," +
			 * recentFiveMatchesAssistUpRate + "," + GmScEfficiencyValue + "," +
			 * trueHitRate + "," + shootEfficiency + "," + reboundRate + "," +
			 * offenReboundRate + "," + defenReboundRate + "," + assistRate +
			 * "," + stealRate + "," + blockRate + "," + turnOverRate + "," +
			 * usageRate +","+score_rebound_assist+","+doubleDoubleNum+ ")");
			 * sql2.close();
			 */
			averageStatement.setInt(1, sqlID);
			averageStatement.setString(2, playerName.replace("''", "'"));
			averageStatement.setString(3, season);
			averageStatement.setString(4, owingTeam);
			averageStatement.setInt(5, playedGames);
			averageStatement.setInt(6, gameStartingNum);
			averageStatement.setDouble(7, reboundNum);
			averageStatement.setDouble(8, assistNum);
			averageStatement.setDouble(9, presentTime);
			averageStatement.setDouble(10, shootHitRate);
			averageStatement.setDouble(11, threeHitRate);
			averageStatement.setDouble(12, freeThrowHitRate);
			averageStatement.setDouble(13, offenReboundNum);
			averageStatement.setDouble(14, defenReboundNum);
			averageStatement.setDouble(15, stealNum);
			averageStatement.setDouble(16, blockNum);
			averageStatement.setDouble(17, foulNum);
			averageStatement.setDouble(18, turnOverNum);
			averageStatement.setDouble(19, score);
			averageStatement.setDouble(20, efficiency);
			averageStatement.setDouble(21, recentFiveMatchesScoreUpRate);
			averageStatement.setDouble(22, recentFiveMatchesReboundUpRate);
			averageStatement.setDouble(23, recentFiveMatchesAssistUpRate);
			averageStatement.setDouble(24, GmScEfficiencyValue);
			averageStatement.setDouble(25, trueHitRate);
			averageStatement.setDouble(26, shootEfficiency);
			averageStatement.setDouble(27, reboundRate);
			averageStatement.setDouble(28, offenReboundRate);
			averageStatement.setDouble(29, defenReboundRate);
			averageStatement.setDouble(30, assistRate);
			averageStatement.setDouble(31, stealRate);
			averageStatement.setDouble(32, blockRate);
			averageStatement.setDouble(33, turnOverRate);
			averageStatement.setDouble(34, usageRate);
			averageStatement.setDouble(35, score_rebound_assist);
			averageStatement.setDouble(36, doubleDoubleNum);
			averageStatement.addBatch();

			sqlID++;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void calculate(String season) {
        
		createTable();

		try {
			averageStatement = con
					.prepareStatement("INSERT INTO playerMatchDataAverage VALUES(?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			seasonStatement = con
					.prepareStatement("INSERT INTO playerMatchDataSeason VALUES(?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			Statement sql = con.createStatement();
			Statement sql1=con.createStatement();
			String query = "select playerName,sum(reboundNum),sum(offenReboundNum),sum(defenReboundNum),sum(assistNum),sum(shootHitNum),"
					+ "sum(shootAttemptNum),sum(threeHitNum),sum(threeAttemptNum),"
					+ "sum(freeThrowHitNum),sum(freeThrowAttemptNum),sum(offenReboundNum),"
					+ "sum(defenReboundNum),sum(stealNum),sum(blockNum),sum(turnOverNum),"
					+ "sum(foulNum),sum(score) from records group by playerName";
			ResultSet rs = sql.executeQuery(query);	
		    while(rs.next()){
		        playerName=rs.getString("playerName");
		        reboundNumSeason=rs.getInt("sum(reboundNum)");
		        offenReboundNumSeason=rs.getInt("sum(offenReboundNum)");
		        defenReboundNumSeason=rs.getInt("sum(defenReboundNum)");
		        assistNumSeason=rs.getInt("sum(assistNum)");
		        shootHitNumSeason=rs.getInt("sum(shootHitNum)");
		        shootAttemptNumSeason=rs.getInt("sum(shootAttemptNum)");
		        threeHitNumSeason=rs.getInt("sum(threeHitNum)");
		        threeAttemptNumSeason=rs.getInt("sum(threeAttemptNum)");
		        freeThrowHitNumSeason=rs.getInt("sum(freeThrowHitNum)");
		        freeThrowAttemptNumSeason=rs.getInt("sum(freeThrowAttemptNum)");
		        offenReboundNumSeason=rs.getInt("sum(offenReboundNum)");
		        defenReboundNumSeason=rs.getInt("sum(defenReboundNum)");
		        stealNumSeason=rs.getInt("sum(stealNum)");
		        blockNumSeason=rs.getInt("sum(blockNum)");
		        turnOverNumSeason=rs.getInt("sum(turnOverNum)");
		        foulNumSeason=rs.getInt("sum(foulNum)");
		        scoreSeason=rs.getInt("sum(score)");
		        if (playerName.contains("'")) {
					playerName = playerName.replace("'", "''");
				}
				String query1 = "select * from records where season='" + season
						+ "' and playerName='" + playerName + "' order by date desc";
				ResultSet resultSet = sql1.executeQuery(query1);
				while (resultSet.next()) {
					if (!owingTeam.contains(resultSet.getString("team"))) {
						owingTeam = owingTeam + resultSet.getString("team")
								+ "  ";
					}
					playedGames++;
					if (!resultSet.getString("position").equals(""))
						gameStartingNum++;
					presentTime = convertMinuteToSecond(resultSet
							.getString("presentTime"));
				    presentTimeSeason = addPresentTime(presentTimeSeason,
							(int) presentTime);
				    matchTimeSeason += getMatchTime(resultSet.getInt("matchID"));
					int allReboundNum = getReboundNum(
							resultSet.getString("team"),
							resultSet.getInt("matchID"), 0);
					allReboundNumSeason += allReboundNum;
					int dsAllReboundNum = getReboundNum(
							getDSTeamName(resultSet.getString("team"),
									resultSet.getInt("matchID")),
							resultSet.getInt("matchID"), 0);
					dsAllReboundNumSeason += dsAllReboundNum;
					int teamOffenReboundNum = getReboundNum(
							resultSet.getString("team"),
							resultSet.getInt("matchID"), 1);
					teamOffenReboundNumSeason += teamOffenReboundNum;
					int teamDefenReboundNum = getReboundNum(
							resultSet.getString("team"),
							resultSet.getInt("matchID"), 2);
					teamDefenReboundNumSeason += teamDefenReboundNum;
					int dsTeamDefenReboundNum = getReboundNum(
							getDSTeamName(resultSet.getString("team"),
									resultSet.getInt("matchID")),
							resultSet.getInt("matchID"), 2);
					dsTeamDefenReboundNumSeason += dsTeamDefenReboundNum;
					int teamShootHitNum = getShootHitNum(
							resultSet.getString("team"),
							resultSet.getInt("matchID"));
					teamShootHitNumSeason += teamShootHitNum;
					double dsOffenRoundNum = getDSOffenRoundNum(
							resultSet.getString("team"),
							resultSet.getInt("matchID"));
					dsOffenRoundNumSeason += dsOffenRoundNum;
					int dsTeamTwoAttemptNum = getDSTwoAttemptNum(
							resultSet.getString("team"),
							resultSet.getInt("matchID"));
					dsTeamTwoAttemptNumSeason += dsTeamTwoAttemptNum;
					int teamShootAttemptNum = getTeamDataNum(
							resultSet.getString("team"),
							resultSet.getInt("matchID"), 1);
					int teamFreeThrowAttemptNum = getTeamDataNum(
							resultSet.getString("team"),
							resultSet.getInt("matchID"), 2);
					int teamTurnOverNum = getTeamDataNum(
							resultSet.getString("team"),
							resultSet.getInt("matchID"), 3);
					teamShootAttemptNumSeason += teamShootAttemptNum;
					teamFreeThrowAttemptNumSeason += teamFreeThrowAttemptNum;
					teamTurnOverNumSeason += teamTurnOverNum;
					score_rebound_assist += resultSet.getInt("score")
							+ resultSet.getInt("reboundNum")
							+ resultSet.getInt("assistNum");

					int temp = 0;
					if (resultSet.getInt("score") >= 10)
						temp++;
					if (resultSet.getInt("reboundNum") >= 10)
						temp++;
					if (resultSet.getInt("assistNum") >= 10)
						temp++;
					if (resultSet.getInt("stealNum") >= 10)
						temp++;
					if (resultSet.getInt("blockNum") >= 10)
						temp++;
					if (temp >= 2)
						doubleDoubleNum++;
		    }

				resultSet.close();				
					GmScEfficiencyValueSeason = scoreSeason
							+ 0.4
							* shootHitNumSeason
							- 0.7
							* shootAttemptNumSeason
							- 0.4
							* (freeThrowAttemptNumSeason - freeThrowHitNumSeason)
							+ 0.7 * offenReboundNumSeason + 0.3
							* defenReboundNumSeason + stealNumSeason + 0.7
							* assistNumSeason + 0.7 * blockNumSeason - 0.4
							* foulNumSeason - turnOverNumSeason;
					GmScEfficiencyValue = GmScEfficiencyValueSeason;
					if((2 * (shootAttemptNumSeason + 0.44 * freeThrowAttemptNumSeason))!=0){
					  trueHitRateSeason = (double) scoreSeason
							/ (2 * (shootAttemptNumSeason + 0.44 * freeThrowAttemptNumSeason));
					}
					trueHitRate = trueHitRateSeason;
					if(shootAttemptNumSeason!=0){
					shootEfficiencySeason = (double) (shootHitNumSeason + 0.5 * threeHitNumSeason)
							/ shootAttemptNumSeason;
					}
					shootEfficiency = shootEfficiencySeason;
					reboundRateSeason = reboundNumSeason * matchTimeSeason
							/ (double) presentTimeSeason
							/ (allReboundNumSeason + dsAllReboundNumSeason);
					reboundRate = reboundRateSeason;
					offenReboundRateSeason = offenReboundNumSeason
							* matchTimeSeason
							/ (double) presentTimeSeason
							/ (teamOffenReboundNumSeason + dsTeamOffenReboundNumSeason);
					offenReboundRate = offenReboundRateSeason;
					defenReboundRateSeason = defenReboundNumSeason
							* matchTimeSeason
							/ (double) presentTimeSeason
							/ (teamDefenReboundNumSeason + dsTeamDefenReboundNumSeason);
					defenReboundRate = defenReboundRateSeason;
					assistRateSeason = (double) assistNumSeason
							/ ((double) presentTimeSeason / matchTimeSeason
									* teamShootHitNumSeason - shootHitNumSeason);
					assistRate = assistRateSeason;
					stealRateSeason = assistNumSeason * matchTimeSeason
							/ (double) presentTimeSeason
							/ dsOffenRoundNumSeason;
					stealRate = stealRateSeason;
					blockRateSeason = blockNumSeason * matchTimeSeason
							/ (double) presentTimeSeason
							/ dsTeamTwoAttemptNumSeason;
					blockRate = blockRateSeason;
					if((shootAttemptNumSeason - threeAttemptNumSeason+ 0.4 * freeThrowAttemptNumSeason + turnOverNumSeason)!=0){
					turnOverRateSeason = (double) turnOverNumSeason
							/ (shootAttemptNumSeason - threeAttemptNumSeason
									+ 0.4 * freeThrowAttemptNumSeason + turnOverNumSeason);
					}
					turnOverRate = turnOverRateSeason;
					usageRateSeason = (shootAttemptNumSeason + 0.44
							* freeThrowAttemptNumSeason + turnOverNumSeason)
							* matchTimeSeason
							/ presentTimeSeason
							/ (teamShootAttemptNumSeason + 0.44
									* teamFreeThrowAttemptNumSeason + teamTurnOverNumSeason);
					usageRate = usageRateSeason;
					score_rebound_assistSeason = (double) (scoreSeason
							+ reboundNumSeason + assistNumSeason) / 3;
					score_rebound_assist = score_rebound_assistSeason;
					reboundNum = (double) reboundNumSeason / playedGames;
					assistNum = (double) assistNumSeason / playedGames;
					presentTime = (double) presentTimeSeason / playedGames;
					if (shootAttemptNumSeason != 0) {
						shootHitRateSeason = (double) shootHitNumSeason
								/ shootAttemptNumSeason;
					}
					shootHitRate = shootHitRateSeason;
					if (threeAttemptNumSeason != 0) {
						threeHitRateSeason = (double) threeHitNumSeason
								/ threeAttemptNumSeason;
					}
					threeHitRate = threeHitRateSeason;
					if (freeThrowAttemptNumSeason != 0) {
						freeThrowHitRateSeason = (double) freeThrowHitNumSeason
								/ freeThrowAttemptNumSeason;
					}
					freeThrowHitRate = freeThrowHitRateSeason;
					offenReboundNum = (double) offenReboundNumSeason
							/ playedGames;
					defenReboundNum = (double) defenReboundNumSeason
							/ playedGames;
					stealNum = (double) stealNumSeason / playedGames;
					blockNum = (double) blockNumSeason / playedGames;
					turnOverNum = (double) turnOverNumSeason / playedGames;
					foulNum = (double) foulNumSeason / playedGames;
					score = (double) scoreSeason / playedGames;
					efficiencySeason = (scoreSeason + reboundNumSeason
							+ assistNumSeason + stealNumSeason + blockNumSeason)
							- (shootAttemptNumSeason - shootHitNumSeason)
							- (freeThrowAttemptNumSeason - freeThrowHitNumSeason)
							- turnOverNumSeason;
					efficiency = efficiencySeason;
					// 近五场的提升率:
					// 得到排好序的该球员参加的比赛的id集合
					getRecentFiveMatchesUpRate(playerName);
				
				this.season = season;
				exportToSQL();
			}
			rs.close();			
			sql.close();
			seasonStatement.executeBatch();
			averageStatement.executeBatch();
			con.commit();
			seasonStatement.close();
			averageStatement.close();
			con.close();
	      
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			con.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 根据这个球员所打的比赛，计算他近五场的提升率 sign=1;//近五场的得分提升率 sign=2;//近五场的篮板提升率
	 * sign=3;//近五场的助攻提升率 后来，因为引进了全局变量，所以不需要提供sign了
	 */
	private void getRecentFiveMatchesUpRate(
			String name) {
		int fiveScore = 0;
		int fiveReboundNum = 0;
		int fiveAssistNum = 0;
		double fiveScoreAverage = 0;
		double fiveReboundAverage = 0;
		double fiveAssistAverage = 0;
		int exFiveScore = 0;
		int exfiveReboundNum = 0;
		int exfiveAssistNum = 0;
		int exFiveMatchesNum = 0;
		double exFiveScoreAverage = 0;
		double exFiveReboundAverage = 0;
		double exFiveAssistAverage = 0;
		try {
			Statement sql = con.createStatement();
			String query = "select score,reboundNum,assistNum from records where playerName='"
					+ name + "' order by season desc,date desc limit 5";
			ResultSet resultSet = sql.executeQuery(query);
			while(resultSet.next()){
			
					fiveScore += resultSet.getInt("score");
					fiveReboundNum += resultSet.getInt("reboundNum");
					fiveAssistNum += resultSet.getInt("assistNum");

			}
			resultSet.close();
			sql.close();

			exFiveScore = scoreSeason - fiveScore;
			exfiveReboundNum = reboundNumSeason - fiveReboundNum;
			exfiveAssistNum = assistNumSeason - fiveAssistNum;
			exFiveMatchesNum = playedGames - 5;

			fiveScoreAverage = (double) fiveScore / 5;
			fiveReboundAverage = (double) fiveReboundNum / 5;
			fiveAssistAverage = (double) fiveAssistNum / 5;
			if (exFiveMatchesNum > 0) {
				exFiveScoreAverage = (double) exFiveScore / exFiveMatchesNum;
				exFiveReboundAverage = (double) exfiveReboundNum
						/ exFiveMatchesNum;
				exFiveAssistAverage = (double) exfiveAssistNum
						/ exFiveMatchesNum;
			} else {
				recentFiveMatchesScoreUpRate = 0;
				recentFiveMatchesReboundUpRate = 0;
				recentFiveMatchesAssistUpRate = 0;
				return;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (exFiveScoreAverage != 0)
			recentFiveMatchesScoreUpRate = (fiveScoreAverage - exFiveScoreAverage)
					/ exFiveScoreAverage;

		if (exFiveReboundAverage != 0)
			recentFiveMatchesReboundUpRate = (fiveReboundAverage - exFiveReboundAverage)
					/ exFiveReboundAverage;

		if (exFiveAssistAverage != 0)
			recentFiveMatchesAssistUpRate = (fiveAssistAverage - exFiveAssistAverage)
					/ exFiveAssistAverage;

	}

	/**
	 * 根据比赛id和当前已知的球队，返回对手球队的名称
	 * 
	 * @param team
	 * @param matchID
	 * @return
	 */
	private String getDSTeamName(String team, int matchID) {
		String result = null;
		try {
			Statement sql = con.createStatement();
			String query = "select homeTeam,visitingTeam from matchtemp where matchID="
					+ matchID + " limit 1";
			ResultSet resultSet = sql.executeQuery(query);
			resultSet.next();
			if (resultSet.getString("homeTeam").equals(team)) {
				result = resultSet.getString("visitingTeam");
			} else {
				result = resultSet.getString("homeTeam");
			}
			resultSet.close();
			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据队名和比赛id返回该队伍该场比赛的篮板数
	 * 
	 * @param team
	 * @param matchID
	 *            sign=0;//是篮板数 sign=1;//是进攻篮板数 sign=2;//是防守篮板数
	 * @return
	 */
	private int getReboundNum(String team, int matchID, int sign) {
		int result0 = 0;
		int result1 = 0;
		int result2 = 0;
		try {
			Statement sql = con.createStatement();
			// 去matchtemp里面找
			String query = "select homeTeam,visitingTeam,homeOffenReboundNum,visitingOffenReboundNum,homeDefenReboundNum,visitingDefenReboundNum from matchtemp where matchID="
					+ matchID + " limit 1";
			ResultSet resultSet = sql.executeQuery(query);
			resultSet.next();
			if (resultSet.getString("homeTeam").equals(team)) {
				result1 = resultSet.getInt("homeOffenReboundNum");
				result2 = resultSet.getInt("homeDefenReboundNum");
				result0 = result1 + result2;
			} else {
				result1 = resultSet.getInt("visitingOffenReboundNum");
				result2 = resultSet.getInt("visitingDefenReboundNum");
				result0 = result1 + result2;
			}
			resultSet.close();
			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (sign == 0)
			return result0;
		else if (sign == 1)
			return result1;
		else
			return result2;
	}

	/**
	 * 根据队名和比赛id返回该队伍该场比赛的总进球数
	 * 
	 * @param team
	 * @param matchID
	 * 
	 * @return
	 */
	private int getShootHitNum(String team, int matchID) {
		int result = 0;
		try {
			Statement sql = con.createStatement();
			// 去matchtemp里面找
			String query = "select homeTeam,visitingTeam,homeShootHitNum,visitingShootHitNum from matchtemp where matchID="
					+ matchID + " limit 1";
			ResultSet resultSet = sql.executeQuery(query);
			resultSet.next();
			if (resultSet.getString("homeTeam").equals(team)) {
				result = resultSet.getInt("homeShootHitNum");
			} else {
				result = resultSet.getInt("visitingShootHitNum");
			}
			resultSet.close();
			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据队名和比赛id返回该队伍该场比赛的所有球员的一些数据
	 * 
	 * @param team
	 * @param matchID
	 *            sign=1;//球队所有球员出手次数 sign=2;//球队所有的球员罚球次数 sign=3;//球队所有球员失误次数
	 * 
	 * @return
	 */
	private int getTeamDataNum(String team, int matchID, int sign) {
		int result1 = 0;
		int result2 = 0;
		int result3 = 0;
		try {
			Statement sql = con.createStatement();
			// 去matchtemp里面找
			String query = "select homeTeam,visitingTeam,homeShootAttemptNum,visitingShootAttemptNum,homeFreeThrowAttemptNum,visitingFreeThrowAttemptNum,homeTurnOverNum,visitingTurnOverNum from matchtemp where matchID="
					+ matchID + " limit 1";
			ResultSet resultSet = sql.executeQuery(query);
			resultSet.next();
			if (resultSet.getString("homeTeam").equals(team)) {
				result1 = resultSet.getInt("homeShootAttemptNum");
				result2 = resultSet.getInt("homeFreeThrowAttemptNum");
				result3 = resultSet.getInt("homeTurnOverNum");
			} else {
				result1 = resultSet.getInt("visitingShootAttemptNum");
				result2 = resultSet.getInt("visitingFreeThrowAttemptNum");
				result3 = resultSet.getInt("visitingTurnOverNum");
			}
			resultSet.close();
			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (sign == 1)
			return result1;
		else if (sign == 2)
			return result2;
		else
			return result3;

	}

	/**
	 * 根据队名和比赛id返回该队伍该场比赛的总进球数
	 * 
	 * @param team
	 * @param matchID
	 * 
	 * @return
	 */
	private int getDSTwoAttemptNum(String team, int matchID) {
		int result = 0;
		try {
			Statement sql = con.createStatement();
			// 去matchtemp里面找
			String query = "select homeTeam,visitingTeam,homeShootAttemptNum,visitingShootAttemptNum,homeThreeAttemptNum,visitingThreeAttemptNum from matchtemp where matchID="
					+ matchID + " limit 1";
			ResultSet resultSet = sql.executeQuery(query);
			resultSet.next();
			if (resultSet.getString("homeTeam").equals(team)) {
				result = resultSet.getInt("visitingShootAttemptNum")
						- resultSet.getInt("visitingThreeAttemptNum");
			} else {
				result = resultSet.getInt("homeShootAttemptNum")
						- resultSet.getInt("homeThreeAttemptNum");
			}
			resultSet.close();
			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据队名和比赛id返回该队伍该场比赛的总进球数
	 * 
	 * @param team
	 * @param matchID
	 * 
	 * @return
	 */
	private double getDSOffenRoundNum(String team, int matchID) {
		double result = 0;
		try {
			Statement sql = con.createStatement();
			// 去matchtemp里面找
			String query = "select homeTeam,visitingShootAttemptNum,visitingFreeThrowAttemptNum,visitingOffenReboundNum,homeDefenReboundNum,visitingShootHitNum,visitingTurnOverNum,homeShootAttemptNum,homeFreeThrowAttemptNum,homeOffenReboundNum,visitingDefenReboundNum,homeShootHitNum,homeTurnOverNum from matchtemp where matchID="
					+ matchID + " limit 1";
			ResultSet resultSet = sql.executeQuery(query);
			resultSet.next();
			if (resultSet.getString("homeTeam").equals(team)) {
				result = resultSet.getInt("visitingShootAttemptNum")
						+ 0.4
						* resultSet.getInt("visitingFreeThrowAttemptNum")
						- 1.07
						* ((double) resultSet.getInt("visitingOffenReboundNum")
								/ (resultSet.getInt("visitingOffenReboundNum") + resultSet
										.getInt("homeDefenReboundNum")) * (resultSet
								.getInt("visitingShootAttemptNum") - resultSet
								.getInt("visitingShootHitNum"))) + 1.07
						* resultSet.getInt("visitingTurnOverNum");

			} else {
				result = resultSet.getInt("homeShootAttemptNum")
						+ 0.4
						* resultSet.getInt("homeFreeThrowAttemptNum")
						- 1.07
						* ((double) resultSet.getInt("homeOffenReboundNum")
								/ (resultSet.getInt("homeOffenReboundNum") + resultSet
										.getInt("visitingDefenReboundNum")) * (resultSet
								.getInt("homeShootAttemptNum") - resultSet
								.getInt("homeShootHitNum"))) + 1.07
						* resultSet.getInt("homeTurnOverNum");
			}
			resultSet.close();
			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将球员的上场时间转换为以分钟为单位的时间返回
	 * 
	 * @param presentTime
	 * @return
	 */
	// public double changeTimeToMinute(String presentTime) {
	// double result = 0;
	// String[] temp = presentTime.split(":");
	// result = Double.parseDouble(temp[0]) + Double.parseDouble(temp[1])
	// / 60.0;
	// return result;
	// }

	/**
	 * 将以分钟为单位的时间转换为球员的上场时间返回
	 * 
	 * @param presentTime
	 * @return
	 */
	// public String changeMinuteToTime(double time) {
	// String result;
	// String temp = String.valueOf(time);
	// String[] tempp = temp.split("\\.");
	// if (tempp.length == 2) {
	// result = tempp[0] + ":";
	// double xiaoshu = Double.parseDouble("0." + tempp[1]);
	//
	// DecimalFormat dec = new DecimalFormat("0");
	// String xiaoshu60 = dec.format(xiaoshu * 60);
	// result = result + xiaoshu60;
	// } else {
	// result = tempp[0];
	// }
	// return result;
	// }

	/**
	 * 将以分钟为单位的时间转换为球员的上场时间返回
	 * 
	 * @param presentTime
	 * @return
	 */
	public String changeMinuteToTime(double time) {
		String result;
		String temp = String.valueOf(time);
		String[] tempp = temp.split("\\.");
		if (tempp.length == 2) {
			result = tempp[0] + ":";
			double xiaoshu = Double.parseDouble("0." + tempp[1]);

			DecimalFormat dec = new DecimalFormat("0");
			String xiaoshu60 = dec.format(xiaoshu * 60);
			result = result + xiaoshu60;
		} else {
			result = tempp[0];
		}
		return result;
	}

	/**
	 * 根据比赛的id得到该场比赛的总时间
	 * 
	 * @param matchID
	 * @return 该场比赛的总时间
	 */
	public int getMatchTime(int matchID) {
		int result = 0;
		try {
			Statement sql = con.createStatement();
			String query = "select time from matches where matchID=" + matchID
					+ " limit 1";
			ResultSet resultSet = sql.executeQuery(query);
			resultSet.next();
			result = resultSet.getInt("time");
			resultSet.close();
			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	private String addPresentTime(String a, String b) {
		String[] aTemp = a.split(":");
		String[] bTemp = b.split(":");
		int a1 = Integer.parseInt(aTemp[0]);
		int a2 = Integer.parseInt(aTemp[1]);
		int b1 = Integer.parseInt(bTemp[0]);
		int b2 = Integer.parseInt(bTemp[1]);
		String result1;
		String result2;
		if (a2 + b2 < 60) {
			result2 = String.valueOf(a2 + b2);
			result1 = String.valueOf(a1 + b1);
		} else {
			result2 = String.valueOf(a2 + b2 - 60);
			result1 = String.valueOf(a1 + b1 + 1);
		}
		return result1 + ":" + result2;
	}

	private int addPresentTime(int a, int b) {
		return a + b;
	}

	private int convertMinuteToSecond(String s) {
		String[] temp = s.split(":");
		int minute = Integer.parseInt(temp[0]);
		int second = Integer.parseInt(temp[1]);
		return minute * 60 + second;
	}

	private ArrayList<String> findPlayerNames() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			Statement sql = con.createStatement();
			String query = "select name from players";
			ResultSet resultSet = sql.executeQuery(query);
			while (resultSet.next()) {
				result.add(resultSet.getString("name"));
			}
			resultSet.close();
			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	private void createTable() {
		try {
			Statement sql = con.createStatement();
			sql.execute("drop table if exists playerMatchDataAverage");
			String query = "create table playerMatchDataAverage(playerDataID int not null auto_increment,"
					+ "playerName varchar(40) not null default 'null',"
					+ "season varchar(20) not null default 'null',"
					+ "owingTeam varchar(200) not null default 'null',"
					+ "playedGames int not null default 0,"
					+ "gameStartingNum int not null default 0,"
					+ "reboundNum double not null default 0,"
					+ "assistNum double not null default 0,"
					+ "presentTime double not null default 0,"
					+ "shootHitRate double not null default 0,"
					+ "threeHitRate double not null default 0,"
					+ "freeThrowHitRate double not null default 0,"
					+ "offenReboundNum double not null default 0,"
					+ "defenReboundNum double not null default 0,"
					+ "stealNum double not null default 0,"
					+ "blockNum double not null default 0,"
					+ "foulNum double not null default 0,"
					+ "turnOverNum double not null default 0,"
					+ "score double not null default 0,"
					+ "efficiency double not null default 0,"
					+ "recentFiveMatchesScoreUpRate double not null default 0,"
					+ "recentFiveMatchesReboundUpRate double not null default 0,"
					+ "recentFiveMatchesAssistUpRate double not null default 0,"
					+ "GmScEfficiencyValue double not null default 0,"
					+ "trueHitRate double not null default 0,"
					+ "shootEfficiency double not null default 0,"
					+ "reboundRate double not null default 0,"
					+ "offenReboundRate double not null default 0,"
					+ "defenReboundRate double not null default 0,"
					+ "assistRate double not null default 0,"
					+ "stealRate double not null default 0,"
					+ "blockRate double not null default 0,"
					+ "turnOverRate double not null default 0,"
					+ "usageRate double not null default 0,"
					+ "score_rebound_assist double not null default 0,"
					+ "doubleDoubleNum double not null default 0,"
					+ "primary key(playerDataID));";
			sql.execute(query);
			sql.close();

			Statement sql2 = con.createStatement();
			sql2.execute("drop table if exists playerMatchDataSeason");
			String query2 = "create table playerMatchDataSeason(playerDataID int not null auto_increment,"
					+ "playerName varchar(40) not null default 'null',"
					+ "season varchar(20) not null default 'null',"
					+ "owingTeam varchar(200) not null default 'null',"
					+ "playedGames int not null default 0,"
					+ "gameStartingNum int not null default 0,"
					+ "reboundNum int not null default 0,"
					+ "assistNum int not null default 0,"
					+ "presentTime int not null default 0,"
					+ "shootHitRate double not null default 0,"
					+ "threeHitRate double not null default 0,"
					+ "freeThrowHitRate double not null default 0,"
					+ "offenReboundNum int not null default 0,"
					+ "defenReboundNum int not null default 0,"
					+ "stealNum int not null default 0,"
					+ "blockNum int not null default 0,"
					+ "foulNum int not null default 0,"
					+ "turnOverNum int not null default 0,"
					+ "score int not null default 0,"
					+ "efficiency double not null default 0,"
					+ "recentFiveMatchesScoreUpRate double not null default 0,"
					+ "recentFiveMatchesReboundUpRate double not null default 0,"
					+ "recentFiveMatchesAssistUpRate double not null default 0,"
					+ "GmScEfficiencyValue double not null default 0,"
					+ "trueHitRate double not null default 0,"
					+ "shootEfficiency double not null default 0,"
					+ "reboundRate double not null default 0,"
					+ "offenReboundRate double not null default 0,"
					+ "defenReboundRate double not null default 0,"
					+ "assistRate double not null default 0,"
					+ "stealRate double not null default 0,"
					+ "blockRate double not null default 0,"
					+ "turnOverRate double not null default 0,"
					+ "usageRate double not null default 0,"
					+ "score_rebound_assist double not null default 0,"
					+ "doubleDoubleNum int not null default 0,"
					+ "primary key(playerDataID));";
			sql2.execute(query2);
			sql2.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		NewPlayerMatchDataCalculator playerMatchDataReader = new NewPlayerMatchDataCalculator();
		playerMatchDataReader.calculate("13-14");
		long end = System.currentTimeMillis();
        System.out.println("运行时间：" + (end - start) + "毫秒");//应该是end - start
	}
}
