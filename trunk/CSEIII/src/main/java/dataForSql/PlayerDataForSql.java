package dataForSql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import vo.LittleRecordVO;
import vo.MatchVO;
import vo.PlayerVO;
import vo.RecordVO;
import vo.TeamVO;
import SQLHelper.FileList;
import SQLHelper.SqlManager;
import data.MatchData;
import data.SequenceOfPlayer;
import data.TeamData;
import dataservice.MatchDataService;

public class PlayerDataForSql {
	ArrayList<PlayerVO> players = new ArrayList<PlayerVO>();
	static int count = 1;
	boolean isReadSqlActive = false;
	boolean isReadSqlHistoric = false;
	Map<String, PlayerVO> playersActive = new HashMap<String, PlayerVO>(32);
	Map<String, PlayerVO> playersHistoric = new HashMap<String, PlayerVO>(256);
	Map<String, TeamVO> teams;
	Map<Integer, MatchVO> matches = new HashMap<Integer, MatchVO>(1024);

	public PlayerDataForSql(){
		teams=TeamData.getTeamsPartition();
	}
	
	private void baseInfoInitActive() {
		try {
			FileList fl = new FileList("src/data/players/info/active");
			ArrayList<String> names = fl.getList();
			players.clear();
			for (String name : names) {
				PlayerVO player=readBaseInfoFromFile(name);
				playersActive.put(player.getName(), player);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void baseInfoInitHistoric() {
		try {
			FileList fl = new FileList("src/data/players/info/historic");
			ArrayList<String> names = fl.getList();
			players.clear();
			for (String name : names) {
				PlayerVO player=readBaseInfoFromFile(name);
				playersHistoric.put(player.getName(), player);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private PlayerVO readBaseInfoFromFile(String fileName) {
		PlayerVO player;
		String name;
		String number;
		String position;
		String height;
		int weight;
		String birth;
		int age;
		int exp;
		String school;
		String owingTeam;
		String[] content = new String[10];
		try {
			File file = new File(fileName);
			if (!file.exists()) {

				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			int i = 0;
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				String[] nit = temp.split(":");
				if (nit.length == 1) {
					content[i++] = "";
				} else {
					content[i++] = nit[1].trim();
				}
				temp = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		name = content[0];
		number = content[1];
		position = content[2];
		height = content[3];
		if (content[4].equals("")) {
			weight = 0;
		} else {
			weight = Integer.parseInt(content[4]);
		}
		birth = content[5];
		if (content[6].equals("")) {
			age = 0;
		} else {
			age = Integer.parseInt(content[6]);
		}
		if (content[7].equals("")) {
			exp = 0;
		} else {
			exp = Integer.parseInt(content[7]);
		}
		school = content[8];
		owingTeam=content[9];
		player = new PlayerVO(name, number, position, height, weight, birth,
				age, exp, school,owingTeam);
		players.add(player);
		return player;
	}

	public void exportBaseInfoToSql() {
		try {
			baseInfoInitActive();
			Connection con = SqlManager.getConnection();
			con.setAutoCommit(false);
			Statement sql = con.createStatement();
			sql.execute("drop table if exists playersActive");
			sql.execute("create table playersActive(playerID int not null auto_increment,name varchar(40) not null default 'null',"
					+ "number varchar(40) not null default 'null',position varchar(20) not null default 'null',"
					+ "height varchar(20) not null default 'null',weight int not null default 0,"
					+ "birth varchar(20) not null default 'null',age int not null default 0,exp int not null default 0,"
					+ "school varchar(40) not null default 'null',owingTeam varchar(20) not null default 'null',"
					+ "primary key(playerID));");
			PreparedStatement statement = con
					.prepareStatement("INSERT INTO playersActive VALUES(?, ?,?,?,?,?,?,?,?,?,?)");
			int count = 1;

			for (PlayerVO player : players) {

				statement.setInt(1, count++);
				statement.setString(2, player.getName());
				statement.setString(3, player.getNumber());
				statement.setString(4, player.getPosition());
				statement.setString(5, player.getHeight());
				statement.setInt(6, player.getWeight());
				statement.setString(7, player.getBirth());
				statement.setInt(8, player.getAge());
				statement.setInt(9, player.getExp());
				statement.setString(10, player.getSchool());
				statement.setString(11, player.getOwingTeam());
				statement.addBatch();

				System.out.println(count - 1);
			}
			statement.executeBatch();
			con.commit();

			baseInfoInitHistoric();
			sql.execute("drop table if exists playersHistoric");
			sql.execute("create table playersHistoric(playerID int not null auto_increment,name varchar(40) not null default 'null',"
					+ "number varchar(40) not null default 'null',position varchar(20) not null default 'null',"
					+ "height varchar(20) not null default 'null',weight int not null default 0,"
					+ "birth varchar(20) not null default 'null',age int not null default 0,exp int not null default 0,"
					+ "school varchar(40)not null default 'null',owingTeam varchar(20) not null default 'null',"
					+ "primary key(playerID));");
			sql.close();
			statement = con
					.prepareStatement("INSERT INTO playersHistoric VALUES(?, ?,?,?,?,?,?,?,?,?,?)");
			count = 1;

			for (PlayerVO player : players) {

				statement.setInt(1, count++);
				statement.setString(2, player.getName());
				statement.setString(3, player.getNumber());
				statement.setString(4, player.getPosition());
				statement.setString(5, player.getHeight());
				statement.setInt(6, player.getWeight());
				statement.setString(7, player.getBirth());
				statement.setInt(8, player.getAge());
				statement.setInt(9, player.getExp());
				statement.setString(10, player.getSchool());
				statement.setString(11, player.getOwingTeam());
				statement.addBatch();

				System.out.println(count - 1);
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
	    	    
		private ArrayList<MatchVO> getSeasonMatches(String season,String type) {
	          MatchDataService match=new MatchData();
	          return match.getMatchData(season,type,"all", "all", "all");
		}

		
		
		

	    private ArrayList<PlayerVO> calculatePlayer(Map<String, PlayerVO> playersSeason){
		  ArrayList<PlayerVO> result=new ArrayList<PlayerVO>();
		  Iterator<Entry<String, PlayerVO>> iter = playersSeason.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				PlayerVO playerSeason = (PlayerVO) entry.getValue();
			    int playedGames;

			    playedGames = playerSeason.getPlayedGames();

			if (playedGames != 0&&playerSeason.getPresentTime()!=0) {

				int allMatchTime = 0;
				int teamReboundNum = 0;
				int dsReboundNum = 0;
				int teamOffenReboundNum = 0;
				int dsOffenReboundNum = 0;
				int teamDefenReboundNum = 0;
				int dsDefenReboundNum = 0;
				int teamShootHitNum = 0;
				int dsTwoAttemptNum = 0;
				int teamShootAttemptNum = 0;
				int teamFreeThrowAttemptNum = 0;
				int teamTurnOverNum = 0;
				double dsOffenRoundNum = 0;
				double shootHitRate = 0;
				double threeHitRate = 0;
				double freeThrowHitRate = 0;
				double efficiency = 0;
				double recentFiveMatchesScoreUpRate = 0;
				double recentFiveMatchesReboundUpRate = 0;
				double recentFiveMatchesAssistUpRate = 0;
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
				double score_rebound_assist = 0;

				double allreboundNum = playerSeason.getReboundNum();
				double allassistNum = playerSeason.getAssistNum();
				double allpresentTime = playerSeason.getPresentTime();
				double allshootHitNum = playerSeason.getShootHitNum();
				double allshootAttemptNum = playerSeason.getShootAttemptNum();
				double allthreeHitNum = playerSeason.getThreeHitNum();
				double allthreeAttemptNum = playerSeason.getThreeAttemptNum();
				double allfreeThrowHitNum = playerSeason.getFreeThrowHitNum();
				double allfreeThrowAttemptNum = playerSeason
						.getFreeThrowAttemptNum();
				double alloffenReboundNum = playerSeason.getOffenReboundNum();
				double alldefenReboundNum = playerSeason.getDefenReboundNum();
				double allstealNum = playerSeason.getStealNum();
				double allblockNum = playerSeason.getBlockNum();
				double allfoulNum = playerSeason.getFoulNum();
				double allturnOverNum = playerSeason.getTurnOverNum();
				double allscore = playerSeason.getScore();
				double allEfficiency=playerSeason.getEfficiency();
				double allGmScEfficiency=playerSeason.getGmScEfficiencyValue();

//				DecimalFormat dec = new DecimalFormat("0.00");

				if(allshootAttemptNum!=0){
					shootHitRate = allshootHitNum / allshootAttemptNum;
				}else {
					shootHitRate=0;
				}
//				shootHitRate = Double.parseDouble(dec.format(shootHitRate));
				if (allthreeAttemptNum != 0) {
					threeHitRate = allthreeHitNum / allthreeAttemptNum;
//					threeHitRate = Double.parseDouble(dec.format(threeHitRate));
				} else {
					threeHitRate = 0;
				}
				if (allfreeThrowAttemptNum != 0) {
					freeThrowHitRate = allfreeThrowHitNum / allfreeThrowAttemptNum;
//					freeThrowHitRate = Double.parseDouble(dec
//							.format(freeThrowHitRate));
				} else {
					freeThrowHitRate = 0;
				}
//				efficiency = (allscore + allreboundNum + allassistNum + allstealNum + allblockNum)
//						- (allshootAttemptNum - allshootHitNum)
//						- (allfreeThrowAttemptNum - allfreeThrowHitNum)
//						- allturnOverNum;
//				efficiency = Double.parseDouble(dec.format(efficiency));
				efficiency=allEfficiency/playedGames;
				ArrayList<Integer> matchIDs = playerSeason.getMatchesID();
				Map<Integer, Boolean> isVisitingTeam = playerSeason
						.getIsVisitingTeam();
				if (matchIDs.size() <= 5) {
					recentFiveMatchesScoreUpRate = 0;
					recentFiveMatchesReboundUpRate = 0;
					recentFiveMatchesAssistUpRate = 0;
				} else {
					LittleRecordVO[] fiveRecordVOs = playerSeason
							.getFiveRecentRecords();

					double recentFiveScore = 0;
					double recentReboundNum = 0;
					double recentAssistNum = 0;
					for (int i = 0; i < 5; i++) {
						LittleRecordVO vo = fiveRecordVOs[i];
						recentFiveScore += vo.getScore();
						recentReboundNum += vo.getReboundNum();
						recentAssistNum += vo.getAssistNum();
					}
					double beforeRecentFiveScore = allscore - recentFiveScore;
					double beforeRecentReboundNum = allreboundNum
							- recentReboundNum;
					double beforeRecentAssistNum = allassistNum - recentAssistNum;
					if (beforeRecentFiveScore != 0&&beforeRecentFiveScore!=0) {
						recentFiveMatchesScoreUpRate = ((recentFiveScore / 5) - beforeRecentFiveScore
								/ (playedGames - 5))
								/ (beforeRecentFiveScore / (playedGames - 5));
//						recentFiveMatchesScoreUpRate = Double.parseDouble(dec
//								.format(recentFiveMatchesScoreUpRate));
					} else {
						recentFiveMatchesScoreUpRate = 0;
					}
					if (beforeRecentReboundNum != 0&&beforeRecentReboundNum!=0) {
						recentFiveMatchesReboundUpRate = ((recentReboundNum / 5) - beforeRecentReboundNum
								/ (playedGames - 5))
								/ (beforeRecentReboundNum / (playedGames - 5));
//						recentFiveMatchesReboundUpRate = Double.parseDouble(dec
//								.format(recentFiveMatchesReboundUpRate));
					} else {
						recentFiveMatchesReboundUpRate = 0;
					}
					if (beforeRecentAssistNum != 0&&beforeRecentAssistNum!=0) {
						recentFiveMatchesAssistUpRate = ((recentAssistNum / 5) - beforeRecentAssistNum
								/ (playedGames - 5))
								/ (beforeRecentAssistNum / (playedGames - 5));
//						recentFiveMatchesAssistUpRate = Double.parseDouble(dec
//								.format(recentFiveMatchesAssistUpRate));
					} else {
						recentFiveMatchesAssistUpRate = 0;
					}
				}

//				GmScEfficiencyValue = allscore + 0.4 * allshootHitNum - 0.7
//						* allshootAttemptNum - 0.4
//						* (allfreeThrowAttemptNum - allfreeThrowHitNum) + 0.7
//						* alloffenReboundNum + 0.3 * alldefenReboundNum
//						+ allstealNum + 0.7 * allassistNum + 0.7 * allblockNum
//						- 0.4 * allfoulNum - allturnOverNum;
//				GmScEfficiencyValue = Double.parseDouble(dec
//						.format(GmScEfficiencyValue));
				GmScEfficiencyValue=allGmScEfficiency/playedGames;
				if((2 * (allshootAttemptNum + 0.44 * allfreeThrowAttemptNum))!=0)
				   trueHitRate = (double) allscore
						/ (2 * (allshootAttemptNum + 0.44 * allfreeThrowAttemptNum));
				else {
					trueHitRate=0;
				}
//				trueHitRate = Double.parseDouble(dec.format(trueHitRate));
				if (allshootAttemptNum != 0) {
					shootEfficiency = (double) (allshootHitNum + 0.5 * allthreeHitNum)
							/ allshootAttemptNum;
//					shootEfficiency = Double.parseDouble(dec
//							.format(shootEfficiency));
				} else {
					shootEfficiency = 0;
				}
				for (Integer id : matchIDs) {
					MatchVO match = matches.get(id);
					allMatchTime += match.getMatchTime();
					if (isVisitingTeam.get(id) == true) {
						teamOffenReboundNum += match.getVisitingOffenReboundNum();
						dsOffenReboundNum += match.getHomeOffenReboundNum();
						teamDefenReboundNum += match.getVisitingDefenReboundNum();
						dsDefenReboundNum += match.getHomeDefenReboundNum();
						teamShootHitNum += match.getVisitingShootHitNum();
						dsTwoAttemptNum += match.getHomeShootAttemptNum()
								- match.getHomeThreeAttemptNum();
						teamShootAttemptNum += match.getVisitingShootAttemptNum();
						teamFreeThrowAttemptNum += match
								.getVisitingFreeThrowAttemptNum();
						teamTurnOverNum += match.getVisitingTurnOverNum();
						dsOffenRoundNum += match.getHomeShootAttemptNum()
								+ 0.4
								* match.getHomeFreeThrowAttemptNum()
								- 1.07
								* ((double) match.getHomeOffenReboundNum()
										/ (match.getHomeOffenReboundNum() + match
												.getVisitingDefenReboundNum()) * (match
										.getHomeShootAttemptNum() - match
										.getHomeShootHitNum())) + 1.07
								* match.getHomeTurnOverNum();
					} else {
						teamOffenReboundNum += match.getHomeOffenReboundNum();
						dsOffenReboundNum += match.getVisitingOffenReboundNum();
						teamDefenReboundNum += match.getHomeDefenReboundNum();
						dsDefenReboundNum += match.getVisitingDefenReboundNum();
						teamShootHitNum += match.getHomeShootHitNum();
						dsTwoAttemptNum += match.getVisitingShootAttemptNum()
								- match.getVisitingThreeAttemptNum();
						teamShootAttemptNum += match.getHomeShootAttemptNum();
						teamFreeThrowAttemptNum += match
								.getHomeFreeThrowAttemptNum();
						teamTurnOverNum += match.getHomeTurnOverNum();
						dsOffenRoundNum += match.getVisitingShootAttemptNum()
								+ 0.4
								* match.getVisitingFreeThrowAttemptNum()
								- 1.07
								* ((double) match.getVisitingOffenReboundNum()
										/ (match.getVisitingOffenReboundNum() + match
												.getHomeDefenReboundNum()) * (match
										.getVisitingShootAttemptNum() - match
										.getVisitingShootHitNum())) + 1.07
								* match.getVisitingTurnOverNum();

					}
				}
				teamReboundNum = teamOffenReboundNum + teamDefenReboundNum;
				dsReboundNum = dsOffenReboundNum + dsDefenReboundNum;

				if(teamReboundNum+dsReboundNum!=0&&allpresentTime!=0)
				reboundRate = allreboundNum * allMatchTime / allpresentTime
						/ (teamReboundNum + dsReboundNum);
				else {
					reboundRate=0;
				}
//				reboundRate = Double.parseDouble(dec.format(reboundRate));
				

				offenReboundRate = alloffenReboundNum * allMatchTime
						/ allpresentTime
						/ (teamOffenReboundNum + dsOffenReboundNum);

//				offenReboundRate = Double.parseDouble(dec.format(offenReboundRate));

				defenReboundRate = alldefenReboundNum * allMatchTime
						/ allpresentTime
						/ (teamDefenReboundNum + dsDefenReboundNum);

//				defenReboundRate = Double.parseDouble(dec.format(defenReboundRate));

				assistRate = allassistNum
						/ (allpresentTime / allMatchTime * teamShootHitNum - allshootHitNum);
//				assistRate = Double.parseDouble(dec.format(assistRate));
				stealRate = (double) allstealNum * allMatchTime / allpresentTime
						/ dsOffenRoundNum;
//				stealRate = Double.parseDouble(dec.format(stealRate));
				blockRate = allblockNum * allMatchTime / allpresentTime
						/ dsTwoAttemptNum;
//				blockRate = Double.parseDouble(dec.format(blockRate));
				if( (allshootAttemptNum - allthreeAttemptNum + 0.44* allfreeThrowAttemptNum + allturnOverNum)!=0){
				  turnOverRate = allturnOverNum
						/ (allshootAttemptNum - allthreeAttemptNum + 0.44
								* allfreeThrowAttemptNum + allturnOverNum);
				}
				else{
					turnOverRate=0;
				}
//				turnOverRate = Double.parseDouble(dec.format(turnOverRate));
				usageRate = (allshootAttemptNum + 0.44 * allfreeThrowAttemptNum + allturnOverNum)
						* allMatchTime
						/ allpresentTime
						/ (teamShootAttemptNum + 0.44 * teamFreeThrowAttemptNum + teamTurnOverNum);
//				usageRate = Double.parseDouble(dec.format(usageRate));
				score_rebound_assist = allscore + allreboundNum + allassistNum;
//				score_rebound_assist = Double.parseDouble(dec
//						.format(score_rebound_assist));
				playerSeason.setShootHitRate(shootHitRate);
				playerSeason.setThreeHitRate(threeHitRate);
				playerSeason.setFreeThrowHitRate(freeThrowHitRate);
				playerSeason.setEfficiency(efficiency);
				playerSeason
						.setRecentFiveMatchesScoreUpRate(recentFiveMatchesScoreUpRate);
				playerSeason
						.setRecentFiveMatchesReboundUpRate(recentFiveMatchesReboundUpRate);
				playerSeason
						.setRecentFiveMatchesAssistUpRate(recentFiveMatchesAssistUpRate);
				playerSeason.setGmScEfficiencyValue(GmScEfficiencyValue);
				playerSeason.setTrueHitRate(trueHitRate);
				playerSeason.setShootEfficiency(shootEfficiency);
				playerSeason.setReboundRate(reboundRate);
				playerSeason.setOffenReboundRate(offenReboundRate);
				playerSeason.setDefenReboundRate(defenReboundRate);
				playerSeason.setAssistRate(assistRate);
				playerSeason.setStealRate(stealRate);
				playerSeason.setBlockRate(blockRate);
				playerSeason.setTurnOverRate(turnOverRate);
				playerSeason.setUsageRate(usageRate);
				playerSeason.setScore_rebound_assist(score_rebound_assist);
			}
			else{
				continue;
			}
			result.add(playerSeason);
		  }
			return result;
		}

		private void getPlayerMatchInfo(Map<String, PlayerVO> theSeasonPlayers,MatchVO match) {

			int matchCount = 0;
			String season = match.getSeason();
			String date = match.getDate();
			String homeTeam = match.getHomeTeam();
			String visitingTeam = match.getVisitingTeam();
			String type=match.getType();

			MatchVO thisMatch = new MatchVO(season, date, type,visitingTeam, homeTeam);
			thisMatch.setMatchTime(match.getMatchTime());

			String team = null;// 球队
			String playerName = null;// 球员名
			String position = null;// 位置
			int presentTime = 0;// 在场时间
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

			for (RecordVO record : match.getRecords()) {
				team = record.getTeam();
				playerName = record.getPlayerName();
				position = record.getPosition();
				presentTime = convertMinuteToSecond(record.getPresentTime());
				shootHitNum = record.getShootHitNum();// 投篮命中数
				shootAttemptNum = record.getShootAttemptNum();// 投篮出手数
				threeHitNum = record.getThreeHitNum();// 三分命中数
				threeAttemptNum = record.getThreeAttemptNum();// 三分出手数
				freeThrowHitNum = record.getFreeThrowHitNum();// 罚球命中数
				freeThrowAttemptNum = record.getFreeThrowAttemptNum();// 罚球出手数
				offenReboundNum = record.getOffenReboundNum();// 进攻（前场）篮板数
				defenReboundNum = record.getDefenReboundNum();// 防守（后场）篮板数
				reboundNum = record.getReboundNum();// 总篮板数
				assistNum = record.getAssistNum();// 助攻数
				stealNum = record.getStealNum();// 抢断数
				blockNum = record.getBlockNum();// 盖帽数
				turnOverNum = record.getTurnOverNum();// 失误数
				foulNum = record.getFoulNum();// 犯规数
				personScore = record.getScore();// 个人得分
				PlayerVO thisPlayer=theSeasonPlayers.get(playerName);
				if(thisPlayer==null){
					PlayerVO pp;
					if(season.equals("14-15"))
				       pp=playersActive.get(playerName);
					else 
					   pp=playersHistoric.get(playerName);			
				   if(pp!=null){
				      thisPlayer =new PlayerVO(pp.getName(), pp.getNumber(), pp.getPosition(), pp.getHeight(), pp.getWeight(), pp.getBirth(), pp.getAge(), pp.getExp(), pp.getSchool());
	                  theSeasonPlayers.put(playerName, thisPlayer);
				   }
				}
				if (thisPlayer == null){
					if (team.equals(homeTeam)) {
						thisMatch.addHomeShootHitNum(shootHitNum);
						thisMatch.addHomeShootAttemptNum(shootAttemptNum);
						thisMatch.addHomeThreeHitNum(threeHitNum);
						thisMatch.addHomeThreeAttemptNum(threeAttemptNum);
						thisMatch.addHomeFreeThrowHitNum(freeThrowHitNum);
						thisMatch.addHomeFreeThrowAttemptNum(freeThrowAttemptNum);
						thisMatch.addHomeOffenReboundNum(offenReboundNum);
						thisMatch.addHomeDefenReboundNum(defenReboundNum);
						thisMatch.addHomeAssistNum(assistNum);
						thisMatch.addHomeStealNum(stealNum);
						thisMatch.addHomeBlockNum(blockNum);
						thisMatch.addHomeTurnOverNum(turnOverNum);
						thisMatch.addHomeFoulNum(foulNum);
					} else {
						thisMatch.addVisitingShootHitNum(shootHitNum);
						thisMatch.addVisitingShootAttemptNum(shootAttemptNum);
						thisMatch.addVisitingThreeHitNum(threeHitNum);
						thisMatch.addVisitingThreeAttemptNum(threeAttemptNum);
						thisMatch.addVisitingFreeThrowHitNum(freeThrowHitNum);
						thisMatch.addVisitingFreeThrowAttemptNum(freeThrowAttemptNum);
						thisMatch.addVisitingOffenReboundNum(offenReboundNum);
						thisMatch.addVisitingDefenReboundNum(defenReboundNum);
						thisMatch.addVisitingAssistNum(assistNum);
						thisMatch.addVisitingStealNum(stealNum);
						thisMatch.addVisitingBlockNum(blockNum);
						thisMatch.addVisitingTurnOverNum(turnOverNum);
						thisMatch.addVisitingFoulNum(foulNum);
					}
					continue;
				}
				LittleRecordVO littleRecordVO = new LittleRecordVO(season, date,
						personScore, reboundNum, assistNum);

				thisPlayer.addPlayedGames();
				if (!position.equals(""))
					thisPlayer.addGameStartingNum();

				if (thisPlayer.getMostRecentMatch() == null) {
					thisPlayer.setOwingTeam(team);				
					TeamVO teamVO = teams.get(team);
					if(team.equals("NJN"))
						teamVO = teams.get("BKN");
					else if (teamVO == null) {
						teamVO = teams.get("NOP");
					}
					
					thisPlayer.setLeague(teamVO.getConference());
					thisPlayer.setMostRecentMatch(season + "_" + date);
				} else {
					if (compare(thisPlayer.getMostRecentMatch(),season + "_" + date) < 0) {
						thisPlayer.setOwingTeam(team);
						TeamVO teamVO = teams.get(team);
						if(team.equals("NJN"))
							teamVO = teams.get("BKN");
						else if (teamVO == null) {
							teamVO = teams.get("NOP");
						}
						thisPlayer.setLeague(teamVO.getConference());
						thisPlayer.setMostRecentMatch(season + "_" + date);
					}
				}

	            int theEffiency= (personScore + reboundNum + assistNum + stealNum + blockNum)
						- (shootAttemptNum - shootHitNum)
						- (freeThrowAttemptNum - freeThrowHitNum)
						- turnOverNum;
	            double theGmScEffiency=personScore + 0.4 * shootHitNum - 0.7* shootAttemptNum - 0.4
						* (freeThrowAttemptNum - freeThrowHitNum) + 0.7
						* offenReboundNum + 0.3 * defenReboundNum
						+ stealNum + 0.7 * assistNum + 0.7 * blockNum
						- 0.4 * foulNum - turnOverNum;
	            thisPlayer.addEfficiency(theEffiency);
	            thisPlayer.addGmScEfficiencyValue(theGmScEffiency);
				thisPlayer.addPresentTime(presentTime);
				thisPlayer.addShootHitNum(shootHitNum);
				thisPlayer.addShootAttemptNum(shootAttemptNum);
				thisPlayer.addThreeHitNum(threeHitNum);
				thisPlayer.addThreeAttemptNum(threeAttemptNum);
				thisPlayer.addFreeThrowHitNum(freeThrowHitNum);
				thisPlayer.addFreeThrowAttemptNum(freeThrowAttemptNum);
				thisPlayer.addOffenReboundNum(offenReboundNum);
				thisPlayer.addDefenReboundNum(defenReboundNum);
				thisPlayer.addReboundNum(reboundNum);
				thisPlayer.addAssistNum(assistNum);
				thisPlayer.addStealNum(stealNum);
				thisPlayer.addBlockNum(blockNum);
				thisPlayer.addTurnOverNum(turnOverNum);
				thisPlayer.addFoulNum(foulNum);
				thisPlayer.addScore(personScore);

				if (team.equals(visitingTeam))
					thisPlayer.addMatchesID(matchCount, true);
				else
					thisPlayer.addMatchesID(matchCount, false);

				thisPlayer.addFiveRecentRecords(littleRecordVO);

				// 计算两双
				int tempDouble = 0;
				if (personScore >= 10)
					tempDouble++;
				if (reboundNum >= 10)
					tempDouble++;
				if (assistNum >= 10)
					tempDouble++;
				if (stealNum >= 10)
					tempDouble++;
				if (blockNum >= 10)
					tempDouble++;
				if (tempDouble >= 2)
					thisPlayer.addDoubleDoubleNum();

				if (team.equals(homeTeam)) {
					thisMatch.addHomeShootHitNum(shootHitNum);
					thisMatch.addHomeShootAttemptNum(shootAttemptNum);
					thisMatch.addHomeThreeHitNum(threeHitNum);
					thisMatch.addHomeThreeAttemptNum(threeAttemptNum);
					thisMatch.addHomeFreeThrowHitNum(freeThrowHitNum);
					thisMatch.addHomeFreeThrowAttemptNum(freeThrowAttemptNum);
					thisMatch.addHomeOffenReboundNum(offenReboundNum);
					thisMatch.addHomeDefenReboundNum(defenReboundNum);
					thisMatch.addHomeAssistNum(assistNum);
					thisMatch.addHomeStealNum(stealNum);
					thisMatch.addHomeBlockNum(blockNum);
					thisMatch.addHomeTurnOverNum(turnOverNum);
					thisMatch.addHomeFoulNum(foulNum);
				} else {
					thisMatch.addVisitingShootHitNum(shootHitNum);
					thisMatch.addVisitingShootAttemptNum(shootAttemptNum);
					thisMatch.addVisitingThreeHitNum(threeHitNum);
					thisMatch.addVisitingThreeAttemptNum(threeAttemptNum);
					thisMatch.addVisitingFreeThrowHitNum(freeThrowHitNum);
					thisMatch.addVisitingFreeThrowAttemptNum(freeThrowAttemptNum);
					thisMatch.addVisitingOffenReboundNum(offenReboundNum);
					thisMatch.addVisitingDefenReboundNum(defenReboundNum);
					thisMatch.addVisitingAssistNum(assistNum);
					thisMatch.addVisitingStealNum(stealNum);
					thisMatch.addVisitingBlockNum(blockNum);
					thisMatch.addVisitingTurnOverNum(turnOverNum);
					thisMatch.addVisitingFoulNum(foulNum);
				}

			}
			matches.put(matchCount, thisMatch);
			matchCount++;

		}
		
	    public ArrayList<PlayerVO> getPlayerSeasonInfo(String season,String type){
	     
			Map<String, PlayerVO> theSeasonPlayers=new HashMap<String, PlayerVO>(32);
			ArrayList<PlayerVO> result;
			matches.clear();
			
			for (MatchVO match : getSeasonMatches(season,type)) {
				getPlayerMatchInfo(theSeasonPlayers,match);
			}

		    result=calculatePlayer(theSeasonPlayers);
		    Collections.sort(result, new SequenceOfPlayer());
	        return result;
		}	
	
		

		public ArrayList<PlayerVO> getPlayerAverageInfo(String season,String type) {

			ArrayList<PlayerVO> result=new ArrayList<PlayerVO>();
			ArrayList<PlayerVO> buffer=getPlayerSeasonInfo(season, type);
	       
			for(PlayerVO playerSeason:buffer){
				int playedGames = playerSeason.getPlayedGames();
				PlayerVO newPlayer;
				if (playedGames != 0) {
//					DecimalFormat dec = new DecimalFormat("0.00");
					newPlayer = new PlayerVO(playerSeason.getName(),
							playerSeason.getNumber(), playerSeason.getPosition(),
							playerSeason.getHeight(), playerSeason.getWeight(),
							playerSeason.getBirth(), playerSeason.getAge(),
							playerSeason.getExp(), playerSeason.getSchool(),
							playerSeason.getOwingTeam(), playerSeason.getLeague(),
							playedGames, playerSeason.getGameStartingNum(),
							playerSeason
									.getReboundNum() / playedGames,
							playerSeason
									.getAssistNum() / playedGames,
							playerSeason
									.getPresentTime() / playedGames,
							playerSeason
									.getShootHitNum() / playedGames,
							playerSeason
									.getShootAttemptNum() / playedGames,
							playerSeason.getShootHitRate(), playerSeason.getThreeHitNum()
											/ playedGames, playerSeason.getThreeAttemptNum()
											/ playedGames,
							playerSeason.getThreeHitRate(), playerSeason.getFreeThrowHitNum()
											/ playedGames, playerSeason.getFreeThrowAttemptNum()
											/ playedGames,
							playerSeason.getFreeThrowHitRate(),
							playerSeason
									.getOffenReboundNum() / playedGames,
							playerSeason
									.getDefenReboundNum() / playedGames,
							playerSeason
									.getStealNum() / playedGames,
							playerSeason
									.getBlockNum() / playedGames,
							playerSeason
									.getTurnOverNum() / playedGames,
							playerSeason.getFoulNum()
									/ playedGames,
							playerSeason.getScore()
									/ playedGames, playerSeason.getEfficiency(),
							playerSeason.getRecentFiveMatchesScoreUpRate(),
							playerSeason.getRecentFiveMatchesReboundUpRate(),
							playerSeason.getRecentFiveMatchesAssistUpRate(),
							playerSeason.getGmScEfficiencyValue(),
							playerSeason.getTrueHitRate(),
							playerSeason.getShootHitEfficiency(),
							playerSeason.getReboundRate(),
							playerSeason.getOffenReboundRate(),
							playerSeason.getDefenReboundRate(),
							playerSeason.getAssistRate(),
							playerSeason.getStealRate(),
							playerSeason.getBlockRate(),
							playerSeason.getTurnOverRate(),
							playerSeason.getUsageRate(),playerSeason.getScore_rebound_assist()
											/ playedGames, playerSeason.getDoubleDoubleNum()
											/ playedGames);
				} else {
					newPlayer = new PlayerVO(playerSeason.getName(),
							playerSeason.getNumber(), playerSeason.getPosition(),
							playerSeason.getHeight(), playerSeason.getWeight(),
							playerSeason.getBirth(), playerSeason.getAge(),
							playerSeason.getExp(), playerSeason.getSchool(),
							playerSeason.getOwingTeam(), playerSeason.getLeague(),
							playedGames, playerSeason.getGameStartingNum(), 0, 0,
							0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				}

				result.add(newPlayer);
			}
			
			return result;
		}
		
		private void exportSeasonInfoToSql(String season,String type) {
			try {
				Connection con = SqlManager.getConnection();
				con.setAutoCommit(false);
				Statement sql = con.createStatement();
				String seasonbuffer=season.replace("-", "");
				sql.execute("drop table if exists "+seasonbuffer+"_"+type+"_"+"playerSeason");
				sql.execute("create table "+seasonbuffer+"_"+type+"_"+"playerSeason(playerID int not null auto_increment,"
						+ "name varchar(40) not null default 'null',"
						+ "owingTeam varchar(20) not null default 'null',"
						+ "league varchar(20) not null default 'null',"
						+ "position varchar(20) not null default 'null',"
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
						+ "primary key(playerID));");
				PreparedStatement statement = con
						.prepareStatement("INSERT INTO  "+seasonbuffer+"_"+type+"_"+"playerSeason"+" VALUES(?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				int count = 1;

				ArrayList<PlayerVO> players=getPlayerSeasonInfo(season, type);
				for (PlayerVO player : players) {

					statement.setInt(1, count++);
					statement.setString(2, player.getName().replace("''", "'"));
					statement.setString(3, player.getOwingTeam());
					statement.setString(4, player.getLeague());
					statement.setString(5, player.getPosition());
					statement.setInt(6, player.getPlayedGames());
					statement.setInt(7, player.getGameStartingNum());
					statement.setDouble(8, player.getReboundNum());
					statement.setDouble(9, player.getAssistNum());
					statement.setDouble(10, player.getPresentTime());
					statement.setDouble(11, player.getShootHitRate());
					statement.setDouble(12, player.getThreeHitRate());
					statement.setDouble(13, player.getFreeThrowHitRate());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
					statement.setDouble(14, player.getOffenReboundNum());
					statement.setDouble(15, player.getDefenReboundNum());
					statement.setDouble(16, player.getStealNum());
					statement.setDouble(17, player.getBlockNum());
					statement.setDouble(18, player.getFoulNum());
					statement.setDouble(19, player.getTurnOverNum());
					statement.setDouble(20, player.getScore());
					statement.setDouble(21, player.getEfficiency());
					statement.setDouble(22, player.getRecentFiveMatchesScoreUpRate());
					statement.setDouble(23, player.getRecentFiveMatchesReboundUpRate());
					statement.setDouble(24, player.getRecentFiveMatchesAssistUpRate());
					statement.setDouble(25, player.getGmScEfficiencyValue());
					statement.setDouble(26, player.getTrueHitRate());
					statement.setDouble(27, player.getShootHitEfficiency());
					statement.setDouble(28, player.getReboundRate());
					statement.setDouble(29, player.getOffenReboundRate());
					statement.setDouble(30, player.getDefenReboundRate());
					statement.setDouble(31, player.getAssistRate());
					statement.setDouble(32, player.getStealRate());
					statement.setDouble(33, player.getBlockRate());
					statement.setDouble(34, player.getTurnOverRate());
					statement.setDouble(35, player.getUsageRate());
					statement.setDouble(36, player.getScore_rebound_assist());
					statement.setDouble(37, player.getDoubleDoubleNum());
					statement.addBatch();

					System.out.println(count - 1);
				}
				statement.executeBatch();
				con.commit();
				statement.close();
				con.close();
			} catch (java.lang.ClassNotFoundException e) {
				System.err.println("ClassNotFoundException:" + e.getMessage());
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.err.println("SQLException:" + ex.getMessage());
			}
		}
		
		private void exportAverageInfoToSql(String season,String type) {
			try {
				Connection con = SqlManager.getConnection();
				con.setAutoCommit(false);
				Statement sql = con.createStatement();
				String seasonbuffer=season.replace("-", "");
				sql.execute("drop table if exists "+seasonbuffer+"_"+type+"_"+"playerAverage");
				sql.execute("create table "+seasonbuffer+"_"+type+"_"+"playerAverage(playerID int not null auto_increment,"
						+ "name varchar(40) not null default 'null',"
						+ "owingTeam varchar(20) not null default 'null',"
						+ "league varchar(20) not null default 'null',"
						+ "position varchar(20) not null default 'null',"
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
						+ "primary key(playerID));");
				PreparedStatement statement = con
						.prepareStatement("INSERT INTO  "+seasonbuffer+"_"+type+"_"+"playerAverage"+" VALUES(?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				int count = 1;

				for (PlayerVO player : getPlayerAverageInfo(season, type)) {

					statement.setInt(1, count++);
					statement.setString(2, player.getName().replace("''", "'"));
					statement.setString(3, player.getOwingTeam());
					statement.setString(4, player.getLeague());
					statement.setString(5, player.getPosition());
					statement.setInt(6, player.getPlayedGames());
					statement.setInt(7, player.getGameStartingNum());
					statement.setDouble(8, player.getReboundNum());
					statement.setDouble(9, player.getAssistNum());
					statement.setDouble(10, player.getPresentTime());
					statement.setDouble(11, player.getShootHitRate());
					statement.setDouble(12, player.getThreeHitRate());
					statement.setDouble(13, player.getFreeThrowHitRate());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
					statement.setDouble(14, player.getOffenReboundNum());
					statement.setDouble(15, player.getDefenReboundNum());
					statement.setDouble(16, player.getStealNum());
					statement.setDouble(17, player.getBlockNum());
					statement.setDouble(18, player.getFoulNum());
					statement.setDouble(19, player.getTurnOverNum());
					statement.setDouble(20, player.getScore());
					statement.setDouble(21, player.getEfficiency());
					statement.setDouble(22, player.getRecentFiveMatchesScoreUpRate());
					statement.setDouble(23, player.getRecentFiveMatchesReboundUpRate());
					statement.setDouble(24, player.getRecentFiveMatchesAssistUpRate());
					statement.setDouble(25, player.getGmScEfficiencyValue());
					statement.setDouble(26, player.getTrueHitRate());
					statement.setDouble(27, player.getShootHitEfficiency());
					statement.setDouble(28, player.getReboundRate());
					statement.setDouble(29, player.getOffenReboundRate());
					statement.setDouble(30, player.getDefenReboundRate());
					statement.setDouble(31, player.getAssistRate());
					statement.setDouble(32, player.getStealRate());
					statement.setDouble(33, player.getBlockRate());
					statement.setDouble(34, player.getTurnOverRate());
					statement.setDouble(35, player.getUsageRate());
					statement.setDouble(36, player.getScore_rebound_assist());
					statement.setDouble(37, player.getDoubleDoubleNum());
					statement.addBatch();

					System.out.println(count - 1);
				}
				statement.executeBatch();
				con.commit();
				statement.close();
				con.close();
			} catch (java.lang.ClassNotFoundException e) {
				System.err.println("ClassNotFoundException:" + e.getMessage());
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.err.println("SQLException:" + ex.getMessage());
			}

		}
		
	    private int compare(String s1,String s2){
			String[] buffer1=s1.split("_");
			String[] buffer2=s2.split("_");
			String season1=buffer1[0];
			String date1=buffer1[1];
			String season2=buffer2[0];
			String date2=buffer2[1];
			
			if(season1.compareTo(season2)<0)
				return -1;
			else if(season1.compareTo(season2)>0)
				return 1;
			else{
				String month1=date1.split("-")[0];
				String month2=date2.split("-")[0];
				int type1=0;
				int type2=0; //type=0代表在6月前，=1代表在6月后
				if(month1.compareTo("06")>0){
					type1=1;
				}
				if(month2.compareTo("06")>0){
					type2=1;
				}
				
				if(type1==0&&type2==1){
					return 1;
				}
				
				if(type1==1&&type2==0){
					return-1;
				}
				
				//下面是2个都是6月前或6月后的情况
			     	if(date1.compareTo(date2)<0)
					   return -1;
				    else if(date1.compareTo(date2)>0)
					   return 1;
				    else 
					   return 0;
			     	
			}
		}	

		private int convertMinuteToSecond(String s) {
			if(!s.contains(":")){
				int minute = Integer.parseInt(s);
				return minute * 60;
			}
			String[] temp = s.split(":");
			int minute = Integer.parseInt(temp[0]);
			int second = Integer.parseInt(temp[1]);
			return minute * 60 + second;
		}

		public static double convertSecondToMinuete(double second) {
			double minute= second / 60;
		//	DecimalFormat dec=new DecimalFormat("0.00");
			return minute;
		}
		
		public static String changeSecondToTime(double second) {
			double time=convertSecondToMinuete(second);
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
		
		public void allInit(){
			exportBaseInfoToSql();
			
			String type[]=new String[]{"Preseason","Team","Playoff"};
			String season[]=new String[]{"10-11","11-12","12-13","13-14","14-15"};
		//	String season[]=new String[]{"14-15"};
			for(int i=0;i<season.length;i++){
				for(int j=0;j<type.length;j++){
					exportSeasonInfoToSql(season[i], type[j]);
					exportAverageInfoToSql(season[i], type[j]);
					System.out.println(season[i]+"_"+type[j]+"好啦！");
				}
			}
		}
		
		public void exportLiveToSql(String season,String seasonType){
			exportSeasonInfoToSql(season, seasonType);
			exportAverageInfoToSql(season, seasonType);
		}
		
		public static void main(String[] args) {
			PlayerDataForSql playerData=new PlayerDataForSql();
			playerData.allInit();
		}
}
