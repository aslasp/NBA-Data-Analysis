package SQLHelper.Calculator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import SQLHelper.SqlManager;

/**
 * 
 * 统计每场比赛的数据，存于matchTemp表中
 *
 */
public class MatchTemp {
	Connection con;

	int matchID = -1;
	int count = 1;
	String season = "";
	String date = "";
	String visitingTeam = "";
	String homeTeam = "";

	int visitingShootHitNum = 0; // 投篮命中数
	int visitingShootAttemptNum = 0; // 投篮出手次数
	int visitingThreeHitNum = 0; // 三分命中数
	int visitingThreeAttemptNum = 0; // 三分出手数
	int visitingFreeThrowHitNum = 0; // 罚球命中数
	int visitingFreeThrowAttemptNum = 0; // 罚球出手数
	int visitingOffenReboundNum = 0; // 进攻篮板数
	int visitingDefenReboundNum = 0; // 防守篮板数
	int visitingAssistNum = 0;// 助攻数
	int visitingStealNum = 0;// 抢断数
	int visitingBlockNum = 0;// 盖帽数
	int visitingTurnOverNum = 0;// 失误数
	int visitingFoulNum = 0;// 犯规数
	int visitingScore = 0;// 得分

	int homeShootHitNum = 0; // 投篮命中数
	int homeShootAttemptNum = 0; // 投篮出手次数
	int homeThreeHitNum = 0; // 三分命中数
	int homeThreeAttemptNum = 0; // 三分出手数
	int homeFreeThrowHitNum = 0; // 罚球命中数
	int homeFreeThrowAttemptNum = 0; // 罚球出手数
	int homeOffenReboundNum = 0; // 进攻篮板数
	int homeDefenReboundNum = 0; // 防守篮板数
	int homeAssistNum = 0;// 助攻数
	int homeStealNum = 0;// 抢断数
	int homeBlockNum = 0;// 盖帽数
	int homeTurnOverNum = 0;// 失误数
	int homeFoulNum = 0;// 犯规数
	int homeScore = 0;// 得分
	PreparedStatement statement;

	public MatchTemp() {
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

	public static void main(String[] args) {
		MatchTemp matchTemp = new MatchTemp();
		matchTemp.calculate();
	}

	public void calculate() {
		createTable();
		try {
			Statement sql = con.createStatement();
			String query = "select * from matches";
			ResultSet resultSet = sql.executeQuery(query);
			statement = con
					.prepareStatement("INSERT INTO MatchTemp VALUES(?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			while (resultSet.next()) {
				matchID = resultSet.getInt("matchID");
				season = resultSet.getString("season");
				date = resultSet.getString("date");
				homeTeam = resultSet.getString("homeTeam");
				homeScore = resultSet.getInt("homeScore");
				visitingScore = resultSet.getInt("visitingScore");

				visitingTeam = resultSet.getString("visitingTeam");

				Statement sql2 = con.createStatement();
				String query2 = "select * from records where matchID="
						+ matchID;
				ResultSet rs = sql2.executeQuery(query2);
				while (rs.next()) {
					if (rs.getString("team").equals(homeTeam)) {
						getHomeData(rs);
					} else if (rs.getString("team").equals(visitingTeam)) {
						getVisitingData(rs);
					} else {
						System.out.println("error");
					}
				}
				rs.close();
				sql2.close();

				exportToSQL();
				reSet();
				System.out.println(count);
			}

			sql.close();
			resultSet.close();
			statement.executeBatch();
			con.commit();
			statement.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void getVisitingData(ResultSet resultSet) {
		try {
			visitingShootHitNum += resultSet.getInt("shootHitNum"); // 投篮命中数
			visitingShootAttemptNum += resultSet.getInt("shootAttemptNum"); // 投篮出手次数
			visitingThreeHitNum += resultSet.getInt("threeHitNum"); // 三分命中数
			visitingThreeAttemptNum += resultSet.getInt("threeAttemptNum"); // 三分出手数
			visitingFreeThrowHitNum += resultSet.getInt("freeThrowHitNum"); // 罚球命中数
			visitingFreeThrowAttemptNum += resultSet
					.getInt("freeThrowAttemptNum"); // 罚球出手数
			visitingOffenReboundNum += resultSet.getInt("offenReboundNum"); // 进攻篮板数
			visitingDefenReboundNum += resultSet.getInt("defenReboundNum"); // 防守篮板数
			visitingAssistNum += resultSet.getInt("assistNum");// 助攻数
			visitingStealNum += resultSet.getInt("stealNum");// 抢断数
			visitingBlockNum += resultSet.getInt("blockNum");// 盖帽数
			visitingTurnOverNum += resultSet.getInt("turnOverNum");// 失误数
			visitingFoulNum += resultSet.getInt("foulNum");// 犯规数
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void getHomeData(ResultSet resultSet) {
		try {
			homeShootHitNum += resultSet.getInt("shootHitNum"); // 投篮命中数
			homeShootAttemptNum += resultSet.getInt("shootAttemptNum"); // 投篮出手次数
			homeThreeHitNum += resultSet.getInt("threeHitNum"); // 三分命中数
			homeThreeAttemptNum += resultSet.getInt("threeAttemptNum"); // 三分出手数
			homeFreeThrowHitNum += resultSet.getInt("freeThrowHitNum"); // 罚球命中数
			homeFreeThrowAttemptNum += resultSet.getInt("freeThrowAttemptNum"); // 罚球出手数
			homeOffenReboundNum += resultSet.getInt("offenReboundNum"); // 进攻篮板数
			homeDefenReboundNum += resultSet.getInt("defenReboundNum"); // 防守篮板数
			homeAssistNum += resultSet.getInt("assistNum");// 助攻数
			homeStealNum += resultSet.getInt("stealNum");// 抢断数
			homeBlockNum += resultSet.getInt("blockNum");// 盖帽数
			homeTurnOverNum += resultSet.getInt("turnOverNum");// 失误数
			homeFoulNum += resultSet.getInt("foulNum");// 犯规数
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void reSet() {
		matchID = -1;
		season = "";
		date = "";
		visitingTeam = "";
		homeTeam = "";

		visitingShootHitNum = 0; // 投篮命中数
		visitingShootAttemptNum = 0; // 投篮出手次数
		visitingThreeHitNum = 0; // 三分命中数
		visitingThreeAttemptNum = 0; // 三分出手数
		visitingFreeThrowHitNum = 0; // 罚球命中数
		visitingFreeThrowAttemptNum = 0; // 罚球出手数
		visitingOffenReboundNum = 0; // 进攻篮板数
		visitingDefenReboundNum = 0; // 防守篮板数
		visitingAssistNum = 0;// 助攻数
		visitingStealNum = 0;// 抢断数
		visitingBlockNum = 0;// 盖帽数
		visitingTurnOverNum = 0;// 失误数
		visitingFoulNum = 0;// 犯规数
		visitingScore = 0;// 得分

		homeShootHitNum = 0; // 投篮命中数
		homeShootAttemptNum = 0; // 投篮出手次数
		homeThreeHitNum = 0; // 三分命中数
		homeThreeAttemptNum = 0; // 三分出手数
		homeFreeThrowHitNum = 0; // 罚球命中数
		homeFreeThrowAttemptNum = 0; // 罚球出手数
		homeOffenReboundNum = 0; // 进攻篮板数
		homeDefenReboundNum = 0; // 防守篮板数
		homeAssistNum = 0;// 助攻数
		homeStealNum = 0;// 抢断数
		homeBlockNum = 0;// 盖帽数
		homeTurnOverNum = 0;// 失误数
		homeFoulNum = 0;// 犯规数
		homeScore = 0;// 得分
	}

	private void exportToSQL() {
		try {
			statement.setInt(1, (count++));
			statement.setInt(2, matchID);
			statement.setString(3, season);
			statement.setString(4, date);
			statement.setString(5, visitingTeam);
			statement.setInt(6, visitingShootHitNum);
			statement.setInt(7, visitingShootAttemptNum);
			statement.setInt(8, visitingThreeHitNum);
			statement.setInt(9, visitingThreeAttemptNum);
			statement.setInt(10, visitingFreeThrowHitNum);
			statement.setInt(11, visitingFreeThrowAttemptNum);
			statement.setInt(12, visitingOffenReboundNum);
			statement.setInt(13, visitingDefenReboundNum);
			statement.setInt(14, visitingAssistNum);
			statement.setInt(15, visitingStealNum);
			statement.setInt(16, visitingBlockNum);
			statement.setInt(17, visitingTurnOverNum);
			statement.setInt(18, visitingFoulNum);
			statement.setInt(19, visitingScore);
			statement.setString(20, homeTeam);
			statement.setInt(21, homeShootHitNum);
			statement.setInt(22, homeShootAttemptNum);
			statement.setInt(23, homeThreeHitNum);
			statement.setInt(24, homeThreeAttemptNum);
			statement.setInt(25, homeFreeThrowHitNum);
			statement.setInt(26, homeFreeThrowAttemptNum);
			statement.setInt(27, homeOffenReboundNum);
			statement.setInt(28, homeDefenReboundNum);
			statement.setInt(29, homeAssistNum);
			statement.setInt(30, homeStealNum);
			statement.setInt(31, homeBlockNum);
			statement.setInt(32, homeTurnOverNum);
			statement.setInt(33, homeFoulNum);
			statement.setInt(34, homeScore);
			/*
			 * Statement sql = con.createStatement();
			 * sql.execute("insert MatchTemp values(" + (count++) + "," +
			 * matchID + ",'" + season + "','" + visitingTeam + "'," +
			 * visitingShootHitNum + "," + visitingShootAttemptNum + "," +
			 * visitingThreeHitNum + "," + visitingThreeAttemptNum + "," +
			 * visitingFreeThrowHitNum + "," + visitingFreeThrowAttemptNum + ","
			 * + visitingOffenReboundNum + "," + visitingDefenReboundNum + "," +
			 * visitingAssistNum + "," + visitingStealNum + "," +
			 * visitingBlockNum + "," + visitingTurnOverNum + "," +
			 * visitingFoulNum + "," + visitingScore + ",'" + homeTeam + "'," +
			 * homeShootHitNum + "," + homeShootAttemptNum + "," +
			 * homeThreeHitNum + "," + homeThreeAttemptNum + "," +
			 * homeFreeThrowHitNum + "," + homeFreeThrowAttemptNum + "," +
			 * homeOffenReboundNum + "," + homeDefenReboundNum + "," +
			 * homeAssistNum + "," + homeStealNum + "," + homeBlockNum + "," +
			 * homeTurnOverNum + "," + homeFoulNum + "," + homeScore + ")");
			 * sql.close();
			 */
			statement.addBatch();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void createTable() {
		try {
			Statement sql = con.createStatement();
			sql.execute("drop table if exists MatchTemp");
			sql.execute("create table MatchTemp(matchTempID int not null auto_increment,"
					+ "matchID int not null default 0,"
					+ "season varchar(20) not null default 'null',"
					+ "date varchar(20) not null default 'null',"
					+ "visitingTeam varchar(20) not null default 'null',"
					+ "visitingShootHitNum int not null default 0,"
					+ "visitingShootAttemptNum int not null default 0,"
					+ "visitingThreeHitNum int not null default 0,"
					+ "visitingThreeAttemptNum int not null default 0,"
					+ "visitingFreeThrowHitNum int not null default 0,"
					+ "visitingFreeThrowAttemptNum int not null default 0,"
					+ "visitingOffenReboundNum int not null default 0,"
					+ "visitingDefenReboundNum int not null default 0,"
					+ "visitingAssistNum int not null default 0,"
					+ "visitingStealNum int not null default 0,"
					+ "visitingBlockNum int not null default 0,"
					+ "visitingTurnOverNum int not null default 0,"
					+ "visitingFoulNum int not null default 0,"
					+ "visitingScore int not null default 0,"
					+ "homeTeam varchar(20) not null default 'null',"
					+ "homeShootHitNum int not null default 0,"
					+ "homeShootAttemptNum int not null default 0,"
					+ "homeThreeHitNum int not null default 0,"
					+ "homeThreeAttemptNum int not null default 0,"
					+ "homeFreeThrowHitNum int not null default 0,"
					+ "homeFreeThrowAttemptNum int not null default 0,"
					+ "homeOffenReboundNum int not null default 0,"
					+ "homeDefenReboundNum int not null default 0,"
					+ "homeAssistNum int not null default 0,"
					+ "homeStealNum int not null default 0,"
					+ "homeBlockNum int not null default 0,"
					+ "homeTurnOverNum int not null default 0,"
					+ "homeFoulNum int not null default 0,"
					+ "homeScore int not null default 0,"
					+ "primary key(matchTempID));");
			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
