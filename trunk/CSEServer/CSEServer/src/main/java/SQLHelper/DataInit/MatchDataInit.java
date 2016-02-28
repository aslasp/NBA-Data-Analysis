package SQLHelper.DataInit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import po.MatchPO;
import po.RecordPO;
import SQLHelper.FileList;
import SQLHelper.SqlManager;

/**
 * 
 * 从文件中读取数据，用于将比赛数据读到数据库中 数据分为三个表存储
 * matches：存储比赛的编号、赛季、日期、主客队名称、比分等（主客队和相应的比分都分开存）
 * records：存储比赛的每个球员的比赛ID、所属球队名称以及一些基本的技术数据
 * detailscores：存储比赛的ID，每节的比分信息(考虑到加时赛，所以一节比分是一个元组)
 */
public class MatchDataInit {
	int count = 1;
	int time = 0;// 比赛时间

	public MatchPO readFromMatchFile(String fileName) {
		MatchPO matchPO;
		String season;// 赛季
		String date = null;// 比赛日期
		String teams = null;// 对阵队伍
		String score = null;// 比分

		ArrayList<String> detailScores = new ArrayList<String>();// 各节比分
		ArrayList<RecordPO> records = new ArrayList<RecordPO>();// 球员比分数据记录

		String tp[] = fileName.split("matches");
		season = tp[1].substring(1, 6);

		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String temp = null;

			temp = br.readLine();
			String[] fisrtContent = temp.split(";");
			date = fisrtContent[0];
			teams = fisrtContent[1];
			score = fisrtContent[2];

			temp = br.readLine();
			String[] scoresData = temp.split(";");
			for (int i = 0; i < scoresData.length; i++) {
				detailScores.add(scoresData[i]);
			}

			String team = null;// 球队
			String playerName = null;// 球员名
			String position = null;// 位置
			String presentTime = null;// 在场时间
			int shootHitNum = 0;// 投篮命中数
			int shootAttemptNum = 0;// 投篮出手数
			int threeHitNum = 0;// 三分命中数
			int threeAttemptNum = 0;// 三分出手数
			int freeThrowHitNum = 0;// 罚球命中数
			int freeThrowAttemptNum = 0;// 罚球出手数
			int offenReboundNum = 0;// 进攻（前场）篮板数
			int defenReboundNum = 0;// 防守（后场）篮板数
			int reboundNum = 0;// 总篮板数
			int assistNum = 0;// 助攻数
			int stealNum = 0;// 抢断数
			int blockNum = 0;// 盖帽数
			int turnOverNum = 0;// 失误数
			int foulNum = 0;// 犯规数
			int personScore = 0;// 个人得分

			boolean isComplete = false;
			temp = br.readLine();
			while (temp != null) {
				if (fileName.contains(temp)) {
					team = temp;
					isComplete = false;
				} else {
					String[] line = temp.split(";");
					playerName = line[0];
					position = line[1];
					presentTime = DirtyDataManager.checkPresentTime(fileName,
							team, playerName, line[2]);// 在场时间
					shootHitNum = Integer.parseInt(line[3]);// 投篮命中数
					shootAttemptNum = DirtyDataManager.checkShootAndHitNum(
							fileName, Integer.parseInt(line[4]), shootHitNum);// 投篮出手数
					threeHitNum = Integer.parseInt(line[5]);// 三分命中数
					threeAttemptNum = DirtyDataManager.checkShootAndHitNum(
							fileName, Integer.parseInt(line[6]), threeHitNum);// 三分出手数
					freeThrowHitNum = Integer.parseInt(line[7]);// 罚球命中数
					freeThrowAttemptNum = DirtyDataManager.checkShootAndHitNum(
							fileName, Integer.parseInt(line[8]),
							freeThrowHitNum);// 罚球出手数
					offenReboundNum = Integer.parseInt(line[9]);// 进攻（前场）篮板数
					defenReboundNum = Integer.parseInt(line[10]);// 防守（后场）篮板数
					reboundNum = DirtyDataManager.checkReboundNum(fileName,
							offenReboundNum, defenReboundNum,
							Integer.parseInt(line[11]));// 总篮板数
					assistNum = Integer.parseInt(line[12]);// 助攻数
					stealNum = Integer.parseInt(line[13]);// 抢断数
					blockNum = Integer.parseInt(line[14]);// 盖帽数
					turnOverNum = Integer.parseInt(line[15]);// 失误数
					foulNum = Integer.parseInt(line[16]);// 犯规数
					personScore = DirtyDataManager.checkPersonScore(fileName,
							line[17], temp);// 个人得分

					isComplete = true;
				}
				if (isComplete) {
					RecordPO recordPO = new RecordPO(team, playerName,
							position, presentTime, shootHitNum,
							shootAttemptNum, threeHitNum, threeAttemptNum,
							freeThrowHitNum, freeThrowAttemptNum,
							offenReboundNum, defenReboundNum, reboundNum,
							assistNum, stealNum, blockNum, turnOverNum,
							foulNum, personScore);
					records.add(recordPO);
					// System.out.println(playerName);
					isComplete = false;
				}
				temp = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] temp = teams.split("-");
		String visitingTeam = temp[0];
		String homeTeam = temp[1];

		String[] s=score.split("-");
		int visitingScore=Integer.parseInt(s[0]);
		int homeScore=Integer.parseInt(s[1]);
		
		matchPO = new MatchPO(count, season, date, visitingTeam, homeTeam,
				visitingScore,homeScore, detailScores, records);
		count++;
		return matchPO;
	}

	public ArrayList<MatchPO> getMatchesListFromFile() {
		// TODO 自动生成的方法存根
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		try {
			FileList fl = new FileList("src/data/matches");
			ArrayList<String> names = fl.getList();
			for (String name : names) {
				matches.add(readFromMatchFile(name));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matches;
	}

	public void exportToSql() {
		ArrayList<MatchPO> matches = getMatchesListFromFile();
		try {
			Connection con = SqlManager.getConnection();
			con.setAutoCommit(false);
			Statement sql = con.createStatement();
			sql.execute("drop table if exists matches");
			sql.execute("drop table if exists detailScores");
			sql.execute("drop table if exists records");

			// 创建matches表
			sql.execute("create table matches(matchID int not null auto_increment,"
					+ "season varchar(20) not null default 'null',"
					+ "date varchar(20) not null default 'null',"
					+ "visitingTeam varchar(20) not null default 'null',"
					+ "visitingScore int not null default 0,"
					+ "homeTeam varchar(20) not null default 'null',"
					+ "homeScore int not null default 0,"
					+ "time int not null default 0," + "primary key(matchID));");

			// 创建detailScores表
			sql.execute("create table detailScores(detailID int not null auto_increment,"
					+ "matchID int not null default -1,"
					+ "part int not null default -1,"
					+ "score varchar(20) not null default 'null',"
					+ "primary key(detailID));");

			// 创建records表
			sql.execute("create table records(recordID int not null auto_increment,"
					+ "matchID int not null default -1,"
					+ "team varchar(20) not null default 'null',"
					+ "season varchar(20) not null default 'null',"
					+ "date varchar(20) not null default 'null',"
					+ "playerName varchar(40) not null default 'null',"
					+ "presentTime varchar(20) not null default 'null',"
					+ "position varchar(20) not null default 'null',"
					+ "shootHitNum int not null default 0,"
					+ "shootAttemptNum int not null default 0,"
					+ "threeHitNum int not null default 0,"
					+ "threeAttemptNum int not null default 0,"
					+ "freeThrowHitNum int not null default 0,"
					+ "freeThrowAttemptNum int not null default 0,"
					+ "offenReboundNum int not null default 0,"
					+ "defenReboundNum int not null default 0,"
					+ "reboundNum int not null default 0,"
					+ "assistNum int not null default 0,"
					+ "stealNum int not null default 0,"
					+ "blockNum int not null default 0,"
					+ "turnOverNum int not null default 0,"
					+ "foulNum int not null default 0,"
					+ "score int not null default 0,"
					+ "primary key(recordID));");

			// index分别表示各表的id
			int scoreIndex = 1;
			int recordIndex = 1;

			int test = 1;// 用于标示数据录入过程的，无多大实际意义

			sql.close();
			PreparedStatement matchesStatement = con
					.prepareStatement("INSERT INTO matches VALUES(?,?,?,?,?,?,?,?)");
			PreparedStatement detailScoreStatement = con
					.prepareStatement("INSERT INTO detailScores VALUES(?, ?,?,?)");
			PreparedStatement recordsStatement = con
					.prepareStatement("INSERT INTO records VALUES(?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for (MatchPO matchPO : matches) {
				// 向matches表中插入数据
				String visitingTeam = matchPO.getVisingTeam();
				String homeTeam = matchPO.getHomeTeam();
				int visitingScore = matchPO.getVisitingScore();
				int homeScore = matchPO.getHomeScore();
				int parts = matchPO.getDetailScores().size();
				if (parts <= 4) {
					time = 48;
				} else {
					time = 48 + (parts - 4) * 5;
				}
				time=time*60;
				matchesStatement.setInt(1, matchPO.getMatchID());
				matchesStatement.setString(2, matchPO.getSeason());
				matchesStatement.setString(3, matchPO.getDate());
				matchesStatement.setString(4, visitingTeam);
				matchesStatement.setInt(5, visitingScore);
				matchesStatement.setString(6, homeTeam);
				matchesStatement.setInt(7, homeScore);
				matchesStatement.setInt(8, time);
				matchesStatement.addBatch();
				/*
				 * sql.execute("insert matches values(" + matchPO.getMatchID() +
				 * ",'" + matchPO.getSeason() + "','" + matchPO.getDate() +
				 * "','" + visitingTeam + "'," + visitingScore + ",'" + homeTeam
				 * + "'," + homeScore + "," + time + ")");
				 */

				// 向detailScores表中插入数据
				ArrayList<String> detailScore = matchPO.getDetailScores();
				int partIndex = 1;
				for (String s : detailScore) {
					detailScoreStatement.setInt(1, scoreIndex);
					detailScoreStatement.setInt(2, matchPO.getMatchID());
					detailScoreStatement.setInt(3, partIndex);
					detailScoreStatement.setString(4, s);
					detailScoreStatement.addBatch();
					/*
					 * sql.execute("insert detailScores values(" + scoreIndex +
					 * "," + matchPO.getMatchID() + "," + partIndex + ",'" + s +
					 * "')");
					 */
					partIndex++;
					scoreIndex++;
				}

				// 向records表中插入数据
				ArrayList<RecordPO> records = matchPO.getRecords();
				for (RecordPO recordPO : records) {
					recordsStatement.setInt(1, recordIndex);
					recordsStatement.setInt(2, matchPO.getMatchID());
					recordsStatement.setString(3, recordPO.getTeam());
					recordsStatement.setString(4, matchPO.getSeason());
					recordsStatement.setString(5, matchPO.getDate());

					recordsStatement.setString(6, recordPO.getPlayerName());
					recordsStatement.setString(7, recordPO.getPresentTime());
					recordsStatement.setString(8, recordPO.getPosition());
					recordsStatement.setInt(9, recordPO.getShootHitNum());
					recordsStatement.setInt(10, recordPO.getShootAttemptNum());
					recordsStatement.setInt(11, recordPO.getThreeHitNum());
					recordsStatement.setInt(12, recordPO.getThreeAttemptNum());
					recordsStatement.setInt(13, recordPO.getFreeThrowHitNum());
					recordsStatement.setInt(14,
							recordPO.getFreeThrowAttemptNum());
					recordsStatement.setInt(15, recordPO.getOffenReboundNum());
					recordsStatement.setInt(16, recordPO.getDefenReboundNum());
					recordsStatement.setInt(17, recordPO.getReboundNum());
					recordsStatement.setInt(18, recordPO.getAssistNum());
					recordsStatement.setInt(19, recordPO.getStealNum());
					recordsStatement.setInt(20, recordPO.getBlockNum());
					recordsStatement.setInt(21, recordPO.getTurnOverNum());
					recordsStatement.setInt(22, recordPO.getFoulNum());
					recordsStatement.setInt(23, recordPO.getScore());
					recordsStatement.addBatch();
					/*
					 * sql.execute("insert records values(" + recordIndex + ","
					 * + matchPO.getMatchID() + ",'" + recordPO.getTeam() +
					 * "','" + matchPO.getSeason() + "','" +
					 * recordPO.getPlayerName() + "','" +
					 * recordPO.getPresentTime() + "','" +
					 * recordPO.getPosition() + "'," + recordPO.getShootHitNum()
					 * + "," + recordPO.getShootAttemptNum() + "," +
					 * recordPO.getThreeHitNum() + "," +
					 * recordPO.getThreeAttemptNum() + "," +
					 * recordPO.getFreeThrowHitNum() + "," +
					 * recordPO.getFreeThrowAttemptNum() + "," +
					 * recordPO.getOffenReboundNum() + "," +
					 * recordPO.getDefenReboundNum() + "," +
					 * recordPO.getReboundNum() + "," + recordPO.getAssistNum()
					 * + "," + recordPO.getStealNum() + "," +
					 * recordPO.getBlockNum() + "," + recordPO.getTurnOverNum()
					 * + "," + recordPO.getFoulNum() + "," + recordPO.getScore()
					 * + ")");
					 */
					recordIndex++;
				}
				System.out.println(test++);
			}
			matchesStatement.executeBatch();
			detailScoreStatement.executeBatch();
			recordsStatement.executeBatch();
			con.commit();
			matchesStatement.close();
			detailScoreStatement.close();
			recordsStatement.close();
			con.close();
		} catch (java.lang.ClassNotFoundException e) {
			System.err.println("ClassNotFoundException:" + e.getMessage());
		} catch (SQLException ex) {
			System.out.println("SQLException:" + ex.getMessage());
		}
	}

	public static void main(String[] args) {
		MatchDataInit matchDataReader = new MatchDataInit();
		matchDataReader.exportToSql();
	}
}
