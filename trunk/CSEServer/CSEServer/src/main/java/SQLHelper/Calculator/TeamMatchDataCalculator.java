package SQLHelper.Calculator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import SQLHelper.SqlManager;

/**
 * 
 * 统计分析球队所有比赛中的数据，计算出赛季和场均的技术数据
 *
 */
public class TeamMatchDataCalculator {
	Connection con;
	int sqlID = 1;
	String teamName = null;
	String season = null;
	int matchesNum = 0;
	int winNum = 0;
	// 场均
	int shootHitNum = 0;// 投篮命中数
	int shootAttemptNum = 0;// 投篮出手次数
	int threeHitNum = 0;// 三分命中数
	int threeAttemptNum = 0;// 三分出手次数
	int freeThrowHitNum = 0;// 罚球命中数
	int freeThrowAttemptNum = 0;// 罚球出手次数
	int offenReboundNum = 0;
	int defenReboundNum = 0;
	int reboundNum = 0;
	int assistNum = 0;
	int stealNum = 0;
	int blockNum = 0;
	int turnOverNum = 0;// 失误数
	int foulNum = 0;// 犯规数
	int score = 0;

	double shootHitNumAverage = 0;// 投篮命中数
	double shootAttemptNumAverage = 0;// 投篮出手次数
	double threeHitNumAverage = 0;// 三分命中数
	double threeAttemptNumAverage = 0;// 三分出手次数
	double freeThrowHitNumAverage = 0;// 罚球命中数
	double freeThrowAttemptNumAverage = 0;// 罚球出手次数
	double offenReboundNumAverage = 0;
	double defenReboundNumAverage = 0;
	double reboundNumAverage = 0;
	double assistNumAverage = 0;
	double stealNumAverage = 0;
	double blockNumAverage = 0;
	double turnOverNumAverage = 0;// 失误数
	double foulNumAverage = 0;// 犯规数
	double scoreAverage = 0;

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
	double assistRate = 0; // 助攻率

	// 赛季
	// 赛季平均的数字型数值跟场均是一样的，胜率也是一样的
	double shootHitRateSeason = 0;// 投篮命中率
	double threeHitRateSeason = 0;// 三分命中率
	double freeThrowHitRateSeason = 0;// 罚球命中率
	double offenRoundSeason = 0; // 进攻回合
	double offenEfficiencySeason = 0; // 进攻效率
	double defenEfficiencySeason = 0; // 防守效率
	double offenReboundEfficiencySeason = 0; // 进攻篮板效率
	double defenReboundEfficiencySeason = 0; // 防守篮板效率
	double stealEfficiencySeason = 0; // 抢断效率
	double assistRateSeason = 0; // 助攻率
	int dsDefenReboundNumSeason = 0;// 对手防守篮板数
	int dsOffenReboundNumSeason = 0;// 对手进攻篮板数
	// temp
	int dsScoreSeason = 0;// 总赛季对手得分
	double dsOffenRoundSeason = 0;// 总赛季对手进攻回合，即总赛季球队防守回合

	int dsShootAttempNumSeason = 0;
	int dsFreeThrowAttemptNumSeason = 0;
	int dsShootHitNumSeason = 0;
	int dsTurnOverNumSeason = 0;

	PreparedStatement seasonStatement;
	PreparedStatement averageStatement;

	public TeamMatchDataCalculator() {
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
		TeamMatchDataCalculator teamMatchDataReader = new TeamMatchDataCalculator();
		teamMatchDataReader.calculate("13-14");
	}

	public void calculate(String season) {
		createTable();
		ArrayList<String> names = new ArrayList<String>();
		names = getTeams();
		try {
			seasonStatement = con
					.prepareStatement("INSERT INTO teamMatchDataSeason VALUES(?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			averageStatement = con
					.prepareStatement("INSERT INTO teamMatchDataAverage VALUES(?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			Statement sql = con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String query = "select * from matchTemp where season='" + season
					+ "'";
			ResultSet result = sql.executeQuery(query);

			for (String name : names) {
				System.out.println(name);
				teamName = name;
				// 遍历一次matchTemp找该球队的信息
				while (result.next()) {
					int matchID = result.getInt("matchID");
					Map<String, String> teams = getTeamsByMathcID(matchID);
					String homeTeam = teams.get("homeTeam");
					String visitingTeam = teams.get("visitingTeam");
					if (name.equals(homeTeam)) {
						// 当前球队在该比赛中是主队
						getTeamData(result, "home");
					} else if (name.equals(visitingTeam)) {
						// 当前球队在该比赛中是客队
						getTeamData(result, "visiting");
					} else {
						// 该球队未参加这场比赛
						// 跳出当前循环，进行下一次循环
						continue;
					}
				}

				// 上面计算的的是每场比赛的效率和，接下来除以总的比赛场数得到场均
				getAverageData();
				this.season = season;
				exportToSQL();
				// 将resultSet光标返回,便于进行下一次遍历
				result.beforeFirst();
				reset();
			}
			sql.close();
			result.close();
			seasonStatement.executeBatch();
			averageStatement.executeBatch();
			con.commit();
			seasonStatement.close();
			averageStatement.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 计算篮板数、投篮数等的赛季总数和场均数据
	 */
	private void getAverageData() {
		shootHitNumAverage = (double) shootHitNum / matchesNum;// 投篮命中数
		shootAttemptNumAverage = (double) shootAttemptNum / matchesNum;// 投篮出手次数
		threeHitNumAverage = (double) threeHitNum / matchesNum;// 三分命中数
		threeAttemptNumAverage = (double) threeAttemptNum / matchesNum;// 三分出手次数
		freeThrowHitNumAverage = (double) freeThrowHitNum / matchesNum;// 罚球命中数
		freeThrowAttemptNumAverage = (double) freeThrowAttemptNum / matchesNum;// 罚球出手次数
		offenReboundNumAverage = (double) offenReboundNum / matchesNum;
		defenReboundNumAverage = (double) defenReboundNum / matchesNum;
		reboundNumAverage = (double) reboundNum / matchesNum;
		assistNumAverage = (double) assistNum / matchesNum;
		stealNumAverage = (double) stealNum / matchesNum;
		blockNumAverage = (double) blockNum / matchesNum;
		turnOverNumAverage = (double) turnOverNum / matchesNum;// 失误数
		foulNumAverage = (double) foulNum / matchesNum;// 犯规数
		scoreAverage = (double) score / matchesNum;

		shootHitRate = (double) shootHitRate / matchesNum;
		threeHitRate = (double) threeHitRate / matchesNum;
		freeThrowHitRate = (double) freeThrowHitRate / matchesNum;
		winRate = (double) winNum / matchesNum;
		offenRound = (double) offenRound / matchesNum;
		offenEfficiency = (double) offenEfficiency / matchesNum;
		defenEfficiency = (double) defenEfficiency / matchesNum;
		offenReboundEfficiency = (double) offenReboundEfficiency / matchesNum;
		defenReboundEfficiency = (double) defenReboundEfficiency / matchesNum;
		stealEfficiency = (double) stealEfficiency / matchesNum;
		assistRate = (double) assistRate / matchesNum;

		// 计算赛季的总数据，对于数量而言，场均和赛季平均一样，胜率也是一样的
		shootHitRateSeason = (double) shootHitNum / shootAttemptNum;
		threeHitRateSeason = (double) threeHitNum / threeAttemptNum;
		freeThrowHitRateSeason = (double) freeThrowHitNum / freeThrowAttemptNum;
		// offenRoundSeason = (double) offenRound *matchesNum;
		offenRoundSeason = shootAttemptNum
				+ 0.4
				* freeThrowAttemptNum
				- 1.07
				* (offenReboundNum
						/ (double) (offenReboundNum + dsDefenReboundNumSeason) * (shootAttemptNum - shootHitNum))
				+ 1.07 * turnOverNum;
		offenEfficiencySeason = score / (double) offenRoundSeason * 100;
		dsOffenRoundSeason = dsShootAttempNumSeason
				+ 0.4
				* dsFreeThrowAttemptNumSeason
				- 1.07
				* (dsOffenReboundNumSeason
						/ (double) (dsOffenReboundNumSeason + defenReboundNum) * (dsShootAttempNumSeason - dsShootHitNumSeason))
				+ 1.07 * dsTurnOverNumSeason;
		defenEfficiencySeason = (double) dsScoreSeason / dsOffenRoundSeason
				* 100;
		offenReboundEfficiencySeason = (double) offenReboundNum
				/ (offenReboundNum + dsDefenReboundNumSeason);
		defenReboundEfficiencySeason = (double) defenReboundNum
				/ (defenReboundNum + dsOffenReboundNumSeason);
		stealEfficiencySeason = (double) stealNum / dsOffenRoundSeason*100;
		assistRateSeason = assistNum / (double) offenRoundSeason * 100;
	}

	private void createTable() {
		try {
			Statement sql = con.createStatement();
			sql.execute("drop table if exists teamMatchDataAverage");
			sql.execute("create table teamMatchDataAverage(teamDataID int not null auto_increment,"
					+ "team varchar(20) not null default 'null',"
					+ "season varchar(20) not null default 'null',"
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

			Statement sql2 = con.createStatement();
			sql2.execute("drop table if exists teamMatchDataSeason");
			sql2.execute("create table teamMatchDataSeason(teamDataID int not null auto_increment,"
					+ "team varchar(20) not null default 'null',"
					+ "season varchar(20) not null default 'null',"
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

			sql2.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 规范率值的小数位数
	 */
	private void foramt() {
		DecimalFormat dec = new DecimalFormat("0.000");
		winRate = Double.parseDouble(dec.format(winRate));
		shootHitNumAverage = Double.parseDouble(dec.format(shootHitNumAverage));// 投篮命中数
		shootAttemptNumAverage = Double.parseDouble(dec
				.format(shootAttemptNumAverage));// 投篮出手次数
		threeHitNumAverage = Double.parseDouble(dec.format(threeHitNumAverage));// 三分命中数
		threeAttemptNumAverage = Double.parseDouble(dec
				.format(threeAttemptNumAverage));// 三分出手次数
		freeThrowHitNumAverage = Double.parseDouble(dec
				.format(freeThrowHitNumAverage));// 罚球命中数
		freeThrowAttemptNumAverage = Double.parseDouble(dec
				.format(freeThrowAttemptNumAverage));// 罚球出手次数
		offenReboundNumAverage = Double.parseDouble(dec
				.format(offenReboundNumAverage));
		defenReboundNumAverage = Double.parseDouble(dec
				.format(defenReboundNumAverage));
		reboundNumAverage = Double.parseDouble(dec.format(reboundNumAverage));
		assistNumAverage = Double.parseDouble(dec.format(assistNumAverage));
		stealNumAverage = Double.parseDouble(dec.format(stealNumAverage));
		blockNumAverage = Double.parseDouble(dec.format(blockNumAverage));
		turnOverNumAverage = Double.parseDouble(dec.format(turnOverNumAverage));// 失误数
		foulNumAverage = Double.parseDouble(dec.format(foulNumAverage));// 犯规数
		scoreAverage = Double.parseDouble(dec.format(scoreAverage));

		shootHitRate = Double.parseDouble(dec.format(shootHitRate));
		shootHitRateSeason = Double.parseDouble(dec.format(shootHitRateSeason));
		threeHitRate = Double.parseDouble(dec.format(threeHitRate));
		threeHitRateSeason = Double.parseDouble(dec.format(threeHitRateSeason));
		freeThrowHitRate = Double.parseDouble(dec.format(freeThrowHitRate));
		freeThrowHitRateSeason = Double.parseDouble(dec
				.format(freeThrowHitRateSeason));
		offenRound = Double.parseDouble(dec.format(offenRound));
		offenRoundSeason = Double.parseDouble(dec.format(offenRoundSeason));
		offenEfficiency = Double.parseDouble(dec.format(offenEfficiency));
		offenEfficiencySeason = Double.parseDouble(dec
				.format(offenEfficiencySeason));
		defenEfficiency = Double.parseDouble(dec.format(defenEfficiency));
		defenEfficiencySeason = Double.parseDouble(dec
				.format(defenEfficiencySeason));
		offenReboundEfficiency = Double.parseDouble(dec
				.format(offenReboundEfficiency));
		offenReboundEfficiencySeason = Double.parseDouble(dec
				.format(offenReboundEfficiencySeason));
		defenReboundEfficiency = Double.parseDouble(dec
				.format(defenReboundEfficiency));
		defenReboundEfficiencySeason = Double.parseDouble(dec
				.format(defenReboundEfficiencySeason));
		stealEfficiency = Double.parseDouble(dec.format(stealEfficiency));
		stealEfficiencySeason = Double.parseDouble(dec
				.format(stealEfficiencySeason));
		assistRate = Double.parseDouble(dec.format(assistRate));
		assistRateSeason = Double.parseDouble(dec.format(assistRateSeason));
	}

	private void exportToSQL() {
		foramt();
		try {
			seasonStatement.setInt(1, sqlID);
			seasonStatement.setString(2, teamName);
			seasonStatement.setString(3, season);
			seasonStatement.setInt(4, matchesNum);
			seasonStatement.setDouble(5, winRate);
			seasonStatement.setInt(6, shootHitNum);
			seasonStatement.setInt(7, shootAttemptNum);
			seasonStatement.setInt(8, threeHitNum);
			seasonStatement.setInt(9, threeAttemptNum);
			seasonStatement.setInt(10, freeThrowHitNum);
			seasonStatement.setInt(11, freeThrowAttemptNum);
			seasonStatement.setInt(12, offenReboundNum);
			seasonStatement.setInt(13, defenReboundNum);
			seasonStatement.setInt(14, reboundNum);
			seasonStatement.setInt(15, assistNum);
			seasonStatement.setInt(16, stealNum);
			seasonStatement.setInt(17, blockNum);
			seasonStatement.setInt(18, turnOverNum);
			seasonStatement.setInt(19, foulNum);
			seasonStatement.setInt(20, score);
			seasonStatement.setDouble(21, shootHitRateSeason);
			seasonStatement.setDouble(22, threeHitRateSeason);
			seasonStatement.setDouble(23, freeThrowHitRateSeason);
			seasonStatement.setDouble(24, offenRoundSeason);
			seasonStatement.setDouble(25, offenEfficiencySeason);
			seasonStatement.setDouble(26, defenEfficiencySeason);
			seasonStatement.setDouble(27, offenReboundEfficiencySeason);
			seasonStatement.setDouble(28, defenReboundEfficiencySeason);
			seasonStatement.setDouble(29, stealEfficiencySeason);
			seasonStatement.setDouble(30, assistRateSeason);
			seasonStatement.addBatch();
			/*
			 * Statement sql = con.createStatement();
			 * sql.execute("insert teamMatchDataSeason values(" + sqlID + ",'" +
			 * teamName + "','" + season + "'," + matchesNum + "," + winRate +
			 * "," + shootHitNum + "," + shootAttemptNum + "," + threeHitNum +
			 * "," + threeAttemptNum + "," + freeThrowHitNum + "," +
			 * freeThrowAttemptNum + "," + offenReboundNum + "," +
			 * defenReboundNum + "," + reboundNum + "," + assistNum + "," +
			 * stealNum + "," + blockNum + "," + turnOverNum + "," + foulNum +
			 * "," + score + "," + shootHitRateSeason + "," + threeHitRateSeason
			 * + "," + freeThrowHitRateSeason + "," + offenRoundSeason + "," +
			 * offenEfficiencySeason + "," + defenEfficiencySeason + "," +
			 * offenReboundEfficiencySeason + "," + defenReboundEfficiencySeason
			 * + "," + stealEfficiencySeason + "," + assistRateSeason + ")");
			 * sql.close();
			 * 
			 * Statement sql2 = con.createStatement();
			 * sql2.execute("insert teamMatchDataAverage values(" + sqlID + ",'"
			 * + teamName + "','" + season + "'," + matchesNum + "," + winRate +
			 * "," + shootHitNumAverage + "," + shootAttemptNumAverage + "," +
			 * threeHitNumAverage + "," + threeAttemptNumAverage + "," +
			 * freeThrowHitNumAverage + "," + freeThrowAttemptNumAverage + "," +
			 * offenReboundNumAverage + "," + defenReboundNumAverage + "," +
			 * reboundNumAverage + "," + assistNumAverage + "," +
			 * stealNumAverage + "," + blockNumAverage + "," +
			 * turnOverNumAverage + "," + foulNumAverage + "," + scoreAverage +
			 * "," + shootHitRate + "," + threeHitRate + "," + freeThrowHitRate
			 * + "," + offenRound + "," + offenEfficiency + "," +
			 * defenEfficiency + "," + offenReboundEfficiency + "," +
			 * defenReboundEfficiency + "," + stealEfficiency + "," + assistRate
			 * + ")"); sql2.close();
			 */
			averageStatement.setInt(1, sqlID);
			averageStatement.setString(2, teamName);
			averageStatement.setString(3, season);
			averageStatement.setInt(4, matchesNum);
			averageStatement.setDouble(5, winRate);
			averageStatement.setDouble(6, shootHitNumAverage);
			averageStatement.setDouble(7, shootAttemptNumAverage);
			averageStatement.setDouble(8, threeHitNumAverage);
			averageStatement.setDouble(9, threeAttemptNumAverage);
			averageStatement.setDouble(10, freeThrowHitNumAverage);
			averageStatement.setDouble(11, freeThrowAttemptNumAverage);
			averageStatement.setDouble(12, offenReboundNumAverage);
			averageStatement.setDouble(13, defenReboundNumAverage);
			averageStatement.setDouble(14, reboundNumAverage);
			averageStatement.setDouble(15, assistNumAverage);
			averageStatement.setDouble(16, stealNumAverage);
			averageStatement.setDouble(17, blockNumAverage);
			averageStatement.setDouble(18, turnOverNumAverage);
			averageStatement.setDouble(19, foulNumAverage);
			averageStatement.setDouble(20, scoreAverage);
			averageStatement.setDouble(21, shootHitRate);
			averageStatement.setDouble(22, threeHitRate);
			averageStatement.setDouble(23, freeThrowHitRate);
			averageStatement.setDouble(24, offenRound);
			averageStatement.setDouble(25, offenEfficiency);
			averageStatement.setDouble(26, defenEfficiency);
			averageStatement.setDouble(27, offenReboundEfficiency);
			averageStatement.setDouble(28, defenReboundEfficiency);
			averageStatement.setDouble(29, stealEfficiency);
			averageStatement.setDouble(30, assistRate);
			averageStatement.addBatch();
			sqlID++;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 根据球队是主队还是客队，修改作为全局变量的那些球队的技术属性——每场数据之和
	 * 
	 * @param resultSet
	 * @param flag
	 *            标志球队是主队还是客队
	 */
	private void getTeamData(ResultSet resultSet, String flag) {
		matchesNum++;
		try {
			// 计算胜利场数
			int homeScore = resultSet.getInt("homeScore");
			int visitingScore = resultSet.getInt("visitingScore");
			if (flag.equals("home") && homeScore > visitingScore) {
				winNum++;
			} else if (flag.equals("visiting") && homeScore < visitingScore) {
				winNum++;
			}

			shootHitNum += resultSet.getInt(flag + "ShootHitNum");
			shootAttemptNum += resultSet.getInt(flag + "ShootAttemptNum");
			threeHitNum += resultSet.getInt(flag + "ThreeHitNum");
			threeAttemptNum += resultSet.getInt(flag + "ThreeAttemptNum");
			freeThrowHitNum += resultSet.getInt(flag + "FreeThrowHitNum");
			freeThrowAttemptNum += resultSet.getInt(flag
					+ "FreeThrowAttemptNum");
			offenReboundNum += resultSet.getInt(flag + "OffenReboundNum");
			defenReboundNum += resultSet.getInt(flag + "DefenReboundNum");
			reboundNum += (resultSet.getInt(flag + "OffenReboundNum") + resultSet
					.getInt(flag + "DefenReboundNum"));
			assistNum += resultSet.getInt(flag + "AssistNum");
			stealNum += resultSet.getInt(flag + "StealNum");
			blockNum += resultSet.getInt(flag + "BlockNum");
			turnOverNum += resultSet.getInt(flag + "TurnOverNum");
			foulNum += resultSet.getInt(flag + "FoulNum");
			score += resultSet.getInt(flag + "Score");

			shootHitRate += (double) resultSet.getInt(flag + "ShootHitNum")
					/ resultSet.getInt(flag + "ShootAttemptNum");
			threeHitRate += (double) resultSet.getInt(flag + "ThreeHitNum")
					/ resultSet.getInt(flag + "ThreeAttemptNum");
			freeThrowHitRate += (double) resultSet.getInt(flag
					+ "FreeThrowHitNum")
					/ resultSet.getInt(flag + "FreeThrowAttemptNum");

			int perHomeShootAttemptNum = resultSet
					.getInt("homeShootAttemptNum");
			int perHomeFreeThrowAttemptNum = resultSet
					.getInt("homeFreeThrowAttemptNum");
			int perHomeOffenReboundNum = resultSet
					.getInt("homeOffenReboundNum");
			int perHomeDefenReboundNum = resultSet
					.getInt("homeDefenReboundNum");
			int perHomeMissShoot = perHomeShootAttemptNum
					- resultSet.getInt("homeShootHitNum");
			int perHomeTurnOverNum = resultSet.getInt("homeTurnOverNum");

			int perVisitingShootAttemptNum = resultSet
					.getInt("visitingShootAttemptNum");
			int perVisitingFreeThrowAttemptNum = resultSet
					.getInt("visitingFreeThrowAttemptNum");
			int perVisitingOffenReboundNum = resultSet
					.getInt("visitingOffenReboundNum");
			int perVisitingDefenReboundNum = resultSet
					.getInt("visitingDefenReboundNum");
			int perVisitingMissShoot = perVisitingShootAttemptNum
					- resultSet.getInt("visitingShootHitNum");
			int perVisitingTurnOverNum = resultSet
					.getInt("visitingTurnOverNum");

			double perHomeOfenRound = perHomeShootAttemptNum
					+ 0.4
					* perHomeFreeThrowAttemptNum
					- 1.07
					* (perHomeOffenReboundNum
							/ (double) (perHomeOffenReboundNum + perVisitingDefenReboundNum) * perHomeMissShoot)
					+ 1.07 * perHomeTurnOverNum;
			double perVisitingOfenRound = perVisitingShootAttemptNum
					+ 0.4
					* perVisitingFreeThrowAttemptNum
					- 1.07
					* (perVisitingOffenReboundNum
							/ (double) (perVisitingOffenReboundNum + perHomeDefenReboundNum) * perVisitingMissShoot)
					+ 1.07 * perVisitingTurnOverNum;
			if (flag.equals("home")) {
				offenRound += perHomeOfenRound;

				offenEfficiency += (double) resultSet.getInt("homeScore")
						/ perHomeOfenRound * 100;
				defenEfficiency += (double) resultSet.getInt("visitingScore")
						/ perVisitingOfenRound * 100;
				offenReboundEfficiency += (double) perHomeOffenReboundNum
						/ (perHomeOffenReboundNum + perVisitingDefenReboundNum);
				defenReboundEfficiency += (double) perHomeDefenReboundNum
						/ (perHomeDefenReboundNum + perVisitingOffenReboundNum);

				stealEfficiency += (double) resultSet.getInt("homeStealNum")
						/ perVisitingOfenRound * 100;
				assistRate += (double) resultSet.getInt("homeAssistNum")
						/ perHomeOfenRound * 100;

				dsScoreSeason += resultSet.getInt("visitingScore");
				// dsOffenRoundSeason += perVisitingOfenRound;
				dsShootAttempNumSeason += perVisitingShootAttemptNum;
				dsFreeThrowAttemptNumSeason += perVisitingFreeThrowAttemptNum;
				dsShootHitNumSeason += resultSet.getInt("visitingShootHitNum");
				dsTurnOverNumSeason += perVisitingTurnOverNum;

				dsOffenReboundNumSeason += perVisitingOffenReboundNum;
				dsDefenReboundNumSeason += perVisitingDefenReboundNum;
			} else {
				offenRound += perVisitingOfenRound;

				offenEfficiency += (double) resultSet.getInt("visitingScore")
						/ perVisitingOfenRound * 100;
				defenEfficiency += (double) resultSet.getInt("homeScore")
						/ perHomeOfenRound * 100;
				offenReboundEfficiency += (double) perVisitingOffenReboundNum
						/ (perVisitingOffenReboundNum + perHomeDefenReboundNum);
				defenReboundEfficiency += (double) perVisitingDefenReboundNum
						/ (perVisitingDefenReboundNum + perHomeOffenReboundNum);

				stealEfficiency += (double) resultSet
						.getInt("visitingStealNum") / perHomeOfenRound * 100;
				assistRate += (double) resultSet.getInt("visitingAssistNum")
						/ perVisitingOfenRound * 100;

				dsScoreSeason += resultSet.getInt("homeScore");
				// dsOffenRoundSeason += perHomeOfenRound;

				dsShootAttempNumSeason += perHomeShootAttemptNum;
				dsFreeThrowAttemptNumSeason += perHomeFreeThrowAttemptNum;
				dsShootHitNumSeason += resultSet.getInt("homeShootHitNum");
				dsTurnOverNumSeason += perHomeTurnOverNum;

				dsOffenReboundNumSeason += perHomeOffenReboundNum;
				dsDefenReboundNumSeason += perHomeDefenReboundNum;
			}

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 将全局变量置为初始值，便于统计下一球队的数据，该方法在每统计完一个球队信息后调用
	 */
	private void reset() {
		teamName = null;
		season = null;
		matchesNum = 0;
		winNum = 0;
		// 场均
		shootHitNum = 0;// 投篮命中数
		shootAttemptNum = 0;// 投篮出手次数
		threeHitNum = 0;// 三分命中数
		threeAttemptNum = 0;// 三分出手次数
		freeThrowHitNum = 0;// 罚球命中数
		freeThrowAttemptNum = 0;// 罚球出手次数
		offenReboundNum = 0;
		defenReboundNum = 0;
		reboundNum = 0;
		assistNum = 0;
		stealNum = 0;
		blockNum = 0;
		turnOverNum = 0;// 失误数
		foulNum = 0;// 犯规数
		score = 0;

		shootHitNumAverage = 0;// 投篮命中数
		shootAttemptNumAverage = 0;// 投篮出手次数
		threeHitNumAverage = 0;// 三分命中数
		threeAttemptNumAverage = 0;// 三分出手次数
		freeThrowHitNumAverage = 0;// 罚球命中数
		freeThrowAttemptNumAverage = 0;// 罚球出手次数
		offenReboundNumAverage = 0;
		defenReboundNumAverage = 0;
		reboundNumAverage = 0;
		assistNumAverage = 0;
		stealNumAverage = 0;
		blockNumAverage = 0;
		turnOverNumAverage = 0;// 失误数
		foulNumAverage = 0;// 犯规数
		scoreAverage = 0;

		shootHitRate = 0;// 投篮命中率
		threeHitRate = 0;// 三分命中率
		freeThrowHitRate = 0;// 罚球命中率
		winRate = 0; // 胜率
		offenRound = 0; // 进攻回合
		offenEfficiency = 0; // 进攻效率
		defenEfficiency = 0; // 防守效率
		offenReboundEfficiency = 0;
		defenReboundEfficiency = 0;
		stealEfficiency = 0; // 抢断效率
		assistRate = 0; // 助攻率

		// 赛季
		shootHitRateSeason = 0;// 投篮命中率
		threeHitRateSeason = 0;// 三分命中率
		freeThrowHitRateSeason = 0;// 罚球命中率
		offenRoundSeason = 0; // 进攻回合
		offenEfficiencySeason = 0; // 进攻效率
		defenEfficiencySeason = 0; // 防守效率
		offenReboundEfficiencySeason = 0;
		defenReboundEfficiencySeason = 0;
		stealEfficiencySeason = 0; // 抢断效率
		assistRateSeason = 0; // 助攻率
		dsDefenReboundNumSeason = 0;// 对手防守篮板数
		dsOffenReboundNumSeason = 0;// 对手进攻篮板数
		dsScoreSeason = 0;// 总赛季对手得分
		dsOffenRoundSeason = 0;// 总赛季对手进攻回合，即总赛季球队防守回合
		dsShootAttempNumSeason = 0;
		dsFreeThrowAttemptNumSeason = 0;
		dsShootHitNumSeason = 0;
		dsTurnOverNumSeason = 0;
	}

	/**
	 * 根据比赛id找到主队是谁，客队是谁
	 * 
	 * @param id
	 * @return
	 */
	private Map<String, String> getTeamsByMathcID(int id) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			Statement sql = con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String query = "select homeTeam,visitingTeam from matches where matchID="
					+ id;
			ResultSet resultSet = sql.executeQuery(query);
			resultSet.next();
			String homeTeam = resultSet.getString("homeTeam");
			String visitingTeam = resultSet.getString("visitingTeam");

			result.put("homeTeam", homeTeam);
			result.put("visitingTeam", visitingTeam);

			resultSet.close();
			sql.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;
	}

	private ArrayList<String> getTeams() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			Statement sql = con.createStatement();
			String query = "select abLocation from teams";
			ResultSet resultSet = sql.executeQuery(query);

			while (resultSet.next()) {
				String name = resultSet.getString("abLocation");
				result.add(name);
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
	 * 查找到表中当前的最后一个id——待测试
	 * 
	 * @param table
	 *            表名
	 * @return 当前表中最后一个id
	 */
	// private int getLastID(String idNameInTable, String table) {
	// int result = 0;
	// try {
	// Statement sql = con.createStatement();
	// String query = "select " + idNameInTable + " from " + table
	// + " order by " + idNameInTable + " desc";
	// ResultSet resultSet = sql.executeQuery(query);
	// resultSet.next();
	// result = resultSet.getInt(idNameInTable);
	// resultSet.close();
	// sql.close();
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	// return result;
	// }
}
