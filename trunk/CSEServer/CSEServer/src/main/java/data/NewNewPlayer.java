package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;

import vo.MatchVO;
import vo.PlayerVO;
import vo.RecordVO;
import vo.TeamVO;
import bl.DirtyDataManager;
import bl.FileList;
import bl.match.OldMatch;
import bl.match.SequenceOfRecord;
import blservice.AgeEnum;
import blservice.PlayerBLService;

//注意一下构造里用个oldmatch==要改
public class NewNewPlayer implements PlayerBLService {

	ArrayList<PlayerVO> players;
	ArrayList<TeamVO> teams;
	ArrayList<MatchVO> matches;

	/**
	 * 
	 * @param flag
	 *            flag=0 看球员的基本信息 flag=1 看球员的赛季信息 flag=2 看球员的场均信息
	 */
	public NewNewPlayer(int flag) {
		players = new ArrayList<PlayerVO>();
		if (flag == 0) {
			baseInfoInit();
		} else if (flag == 2) {
			matches = new ArrayList<MatchVO>();
		}

		else {
			long start = System.currentTimeMillis();
			OldMatch match = new OldMatch();
			// matches=new ArrayList<MatchVO>();
			// matchInfoInit();
			matches = match.getMatchData("全部", "全部", "全部", "全部");
			long end = System.currentTimeMillis();
			System.out.println("运行时间：" + (end - start) + "毫秒");// 应该是end - start
		}
	}

	private void baseInfoInit() {

		try {
			FileList fl = new FileList("src/data/players/info");
			ArrayList<String> names = fl.getList();
			for (String name : names) {
				players.add(readBaseInfoFromFile(name));
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
		String[] content = new String[9];
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
				// System.out.println(temp);
				if (temp.contains("│")) {
					String[] it = temp.split("│");
					String[] nit = it[1].split("║");
					content[i++] = nit[0].trim();
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
		weight = Integer.parseInt(content[4]);
		birth = content[5];
		age = Integer.parseInt(content[6]);
		exp = DirtyDataManager.checkExp(content[7]);
		school = content[8];
		player = new PlayerVO(name, number, position, height, weight, birth,
				age, exp, school);
		return player;
	}

	private void matchInfoInit() {

		try {
			FileList fl = new FileList("src/data/matches");
			ArrayList<String> names = fl.getList();
			for (String name : names) {
				readMatchInfoFromFile(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readMatchInfoFromFile(String fileName) {
		String season;// 赛季
		String date = null;// 比赛日期
		String teams = null;// 对阵队伍
		String score = null;// 比分

		String tp[] = fileName.split("matches");
		season = tp[1].substring(1, 6);
		ArrayList<RecordVO> records = new ArrayList<RecordVO>();
		ArrayList<String> details = new ArrayList<String>();
		MatchVO match;

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
			String[] nextContent = temp.split(";");
			for (int i = 0; i < nextContent.length; i++) {
				details.add(nextContent[i]);
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

			temp = br.readLine();
			while (temp != null) {
				if (fileName.contains(temp)) {
					team = temp;
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

					RecordVO recordVO = new RecordVO(team, playerName,
							position, presentTime, shootHitNum,
							shootAttemptNum, threeHitNum, threeAttemptNum,
							freeThrowHitNum, freeThrowAttemptNum,
							offenReboundNum, defenReboundNum, reboundNum,
							assistNum, stealNum, blockNum, turnOverNum,
							foulNum, personScore);

					records.add(recordVO);
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

		String[] s = score.split("-");
		int visitingScore = Integer.parseInt(s[0]);
		int homeScore = Integer.parseInt(s[1]);

		match = new MatchVO(season, date, visitingTeam, homeTeam,
				visitingScore, homeScore, details, records);
		matches.add(match);
	}

	/*
	 * private boolean isMoreRecent(String season1,String date1,String
	 * season2,String date2){ if(season1.compareTo(season2)<0){ return true; }
	 * else if(season1.compareTo(season2)>0){ return false; } else{
	 * if(date1.compareTo(date2)<0){ return true; } else
	 * if(date1.compareTo(date2)>0){ return false; } else{ return true; } } }
	 */

	public ArrayList<PlayerVO> getPlayerBaseInfo() {
		return players;
	}

	public ArrayList<PlayerVO> getPlayerSeasonInfo(String season) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getPlayerAverageInfo() {
		FileList fl;
		ArrayList<String> names = null;
		try {
			fl = new FileList("src/data/players/info");
			names = fl.getList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String name : names) {
			getPlayerAverageInfo(name);
		}

		return null;
	}

	public ArrayList<PlayerVO> getPlayerBaseInfo(String name) {
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		for (PlayerVO vo : players) {
			if (vo.getName().contains(name)) {
				result.add(vo);
			}
		}
		return result;
	}

	public PlayerVO getPlayerSeasonInfo(String season, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public PlayerVO getPlayerAverageInfo(String name) {
		PlayerVO player = new PlayerVO();
		String owingTeam = "";
		int playedGames = 0;
		int gameStartingNum = 0;
		int reboundNum = 0;
		int assistNum = 0;
		String presentTime = "";
		int shootHitNum = 0;
		int shootAttemptNum = 0;
		double shootHitRate = 0;
		int threeHitNum = 0;
		int threeAttemptNum = 0;
		double threeHitRate = 0;
		int freeThrowHitNum = 0;
		int freeThrowAttemptNum = 0;
		double freeThrowHitRate = 0;
		int offenReboundNum = 0;
		int defenReboundNum = 0;
		int stealNum = 0;
		int blockNum = 0;
		int foulNum = 0;
		int turnOverNum = 0;
		int score = 0;
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
		double doubleDoubleNum = 0;
		boolean isThePlayerInTheMatch = false;
		String thePlayerTeam;
		ArrayList<RecordVO> bufferRecords = new ArrayList<RecordVO>();
		double matchTime = 0;// 以秒计
		int teamAllReboundNum = 0;
		int dsAllReboundNum = 0;
		int teamAllOffenReboundNum = 0;
		int dsAllOffenReboundNum = 0;
		int teamAllDefenReboundNum = 0;
		int dsAllDefenReboundNum = 0;
		int teamShootHitNum = 0;
		int dsTwoAttemptNum = 0;
		int teamShootAttemptNum = 0;
		int teamFreeThrowAttemptNum = 0;
		int teamTurnOverNum = 0;
		double dsOffenRoundNum = 0;
		int dsShootAttemptNum = 0;
		int dsFreeThrowAttemptNum = 0;
		int dsShootMissNum = 0;
		int dsTurnOverNum = 0;

		double areboundNum = 0;
		double aassistNum = 0;
		double apresentTime = 0;
		double ashootHitNum = 0;
		double ashootAttemptNum = 0;
		double athreeHitNum = 0;
		double athreeAttemptNum = 0;
		double afreeThrowHitNum = 0;
		double afreeThrowAttemptNum = 0;
		double ascore_rebound_assist = 0;
		double adoubleDoubleNum = 0;
		double aoffenReboundNum = 0;
		double adefenReboundNum = 0;
		double astealNum = 0;
		double ablockNum = 0;
		double afoulNum = 0;
		double aturnOverNum = 0;
		double ascore = 0;

		for (MatchVO match : matches) {
			isThePlayerInTheMatch = false;
			thePlayerTeam = "";
			for (RecordVO record : match.getRecords()) {
				if (record.getPlayerName().equals(name)) {
					record.setSeason(match.getSeason());
					record.setDate(match.getDate());
					bufferRecords.add(record);
					isThePlayerInTheMatch = true;
					thePlayerTeam = record.getTeam();
					playedGames++;
					if (!record.getPosition().equals("")) {
						gameStartingNum++;
					}
					reboundNum += record.getReboundNum();
					assistNum += record.getAssistNum();
					presentTime = addPresentTime(presentTime,
							record.getPresentTime());
					shootHitNum += record.getShootHitNum();
					shootAttemptNum += record.getShootAttemptNum();
					threeHitNum += record.getThreeHitNum();
					threeAttemptNum += record.getThreeAttemptNum();
					freeThrowHitNum += record.getFreeThrowHitNum();
					freeThrowAttemptNum += record.getFreeThrowAttemptNum();
					offenReboundNum += record.getOffenReboundNum();
					defenReboundNum += record.getDefenReboundNum();
					stealNum += record.getStealNum();
					blockNum += record.getBlockNum();
					turnOverNum += record.getTurnOverNum();
					foulNum += record.getFoulNum();
					score += record.getScore();
					int temp = 0;
					if (record.getScore() >= 10)
						temp++;
					if (record.getReboundNum() >= 10)
						temp++;
					if (record.getAssistNum() >= 10)
						temp++;
					if (record.getStealNum() >= 10)
						temp++;
					if (record.getBlockNum() >= 10)
						temp++;
					if (temp >= 2)
						doubleDoubleNum++;

				}
			}
			if (isThePlayerInTheMatch == true) {
				if (!owingTeam.contains(thePlayerTeam)) {
					owingTeam = owingTeam + thePlayerTeam + " ";
				}
				matchTime += getMatchTime(match);
				// --------
				ArrayList<RecordVO> records = match.getRecords();
				int thisteamAllReboundNum = 0;
				int thisDSAllReboundNum = 0;
				int thisteamAllOffenReboundNum = 0;
				int thisDSAllOffenReboundNum = 0;
				int thisteamAllDefenReboundNum = 0;
				int thisDSAllDefenReboundNum = 0;
				int thisteamShootHitNum = 0;
				int thisDSTwoAttemptNum = 0;
				int thisteamShootAttemptNum = 0;
				int thisteamFreeThrowAttemptNum = 0;
				int thisteamTurnOverNum = 0;
				int thisDSShootAttemptNum = 0;
				int thisDSFreeThrowAttemptNum = 0;
				int thisDSShootMissNum = 0;
				int thisDSTurnOverNum = 0;
				for (RecordVO record : records) {
					if (record.getTeam().equals(thePlayerTeam)) {
						thisteamAllReboundNum += record.getReboundNum();
						thisteamAllOffenReboundNum += record
								.getOffenReboundNum();
						thisteamAllDefenReboundNum += record
								.getDefenReboundNum();
						thisteamShootHitNum += record.getShootHitNum();
						thisteamShootAttemptNum += record.getShootAttemptNum();
						thisteamFreeThrowAttemptNum += record
								.getFreeThrowAttemptNum();
						thisteamTurnOverNum += record.getTurnOverNum();
					} else {
						thisDSAllReboundNum += record.getReboundNum();
						thisDSAllOffenReboundNum += record.getOffenReboundNum();
						thisDSAllDefenReboundNum += record.getDefenReboundNum();
						thisDSTwoAttemptNum += record.getShootAttemptNum()
								- record.getThreeAttemptNum();
						thisDSShootAttemptNum += record.getShootAttemptNum();
						thisDSFreeThrowAttemptNum += record
								.getFreeThrowAttemptNum();
						thisDSShootMissNum += record.getShootAttemptNum()
								- record.getShootHitNum();
						thisDSTurnOverNum += record.getTurnOverNum();
					}
				}
				// teamAllReboundNum+=getTeamAllReboundNum(match,
				// thePlayerTeam);
				teamAllReboundNum += thisteamAllReboundNum;
				// dsAllReboundNum+=getDSAllReboundNum(match, thePlayerTeam);
				dsAllReboundNum += thisDSAllReboundNum;
				// teamAllOffenReboundNum+=getTeamAllOffenReboundNum(match,
				// thePlayerTeam);
				teamAllOffenReboundNum += thisteamAllOffenReboundNum;
				// thisDSAllOffenReboundNum=getDSAllOffenReboundNum(match,
				// thePlayerTeam);
				dsAllOffenReboundNum += thisDSAllOffenReboundNum;
				// thisteamAllDefenReboundNum=getTeamAllDefenReboundNum(match,
				// thePlayerTeam);
				teamAllDefenReboundNum += thisteamAllDefenReboundNum;
				// dsAllDefenReboundNum+=getDSAllDefenReboundNum(match,
				// thePlayerTeam);
				dsAllDefenReboundNum += thisDSAllDefenReboundNum;
				// teamShootHitNum+=getTeamShootHitNum(match, thePlayerTeam);
				teamShootHitNum += thisteamShootHitNum;
				// dsTwoAttemptNum+=getDSTwoAttemptNum(match, thePlayerTeam);
				dsTwoAttemptNum += thisDSTwoAttemptNum;
				// teamShootAttemptNum+=getTeamShootAttemptNum(match,
				// thePlayerTeam);
				teamShootAttemptNum += thisteamShootAttemptNum;
				// teamFreeThrowAttemptNum+=getTeamFreeThrowAttemptNum(match,
				// thePlayerTeam);
				teamFreeThrowAttemptNum += thisteamFreeThrowAttemptNum;
				// teamTurnOverNum+=getTeamTurnOverNum(match, thePlayerTeam);
				teamTurnOverNum += thisteamTurnOverNum;
				// thisDSShootAttemptNum=getDSShootAttemptNum(match,
				// thePlayerTeam);
				dsShootAttemptNum += thisDSShootAttemptNum;
				dsFreeThrowAttemptNum += thisDSFreeThrowAttemptNum;
				dsShootMissNum += thisDSShootMissNum;
				dsTurnOverNum += thisDSTurnOverNum;
				dsOffenRoundNum += thisDSShootAttemptNum
						+ 0.4
						* thisDSFreeThrowAttemptNum
						- 1.07
						* ((double) thisDSAllOffenReboundNum
								/ (thisDSAllOffenReboundNum + thisteamAllDefenReboundNum) * thisDSShootMissNum)
						+ 1.07 * thisDSTurnOverNum;
			}
		}

		if (playedGames == 0)
			return new PlayerVO(name, owingTeam, "", playedGames,
					gameStartingNum, reboundNum, assistNum, 0, shootHitNum,
					shootAttemptNum, shootHitRate, threeHitNum,
					threeAttemptNum, threeHitRate, freeThrowHitNum,
					freeThrowAttemptNum, freeThrowHitRate, offenReboundNum,
					defenReboundNum, stealNum, blockNum, turnOverNum, foulNum,
					score, efficiency, recentFiveMatchesScoreUpRate,
					recentFiveMatchesReboundUpRate,
					recentFiveMatchesAssistUpRate, GmScEfficiencyValue,
					trueHitRate, shootEfficiency, reboundRate,
					offenReboundRate, defenReboundRate, assistRate, stealRate,
					blockRate, turnOverRate, usageRate, score_rebound_assist,
					doubleDoubleNum);
		else {

			areboundNum = (double) reboundNum / playedGames;
			aassistNum = (double) assistNum / playedGames;
			apresentTime = (double) convertMinuteToSecond(presentTime)
					/ playedGames;
			ashootHitNum = (double) shootHitNum / playedGames;
			ashootAttemptNum = (double) shootAttemptNum / playedGames;
			shootHitRate = (double) shootHitNum / shootAttemptNum;
			athreeHitNum = (double) threeHitNum / playedGames;
			athreeAttemptNum = (double) threeAttemptNum / playedGames;
			threeHitRate = (double) threeHitNum / threeAttemptNum;
			afreeThrowHitNum = (double) freeThrowHitNum / playedGames;
			afreeThrowAttemptNum = (double) freeThrowAttemptNum / playedGames;
			freeThrowHitRate = (double) freeThrowHitNum / freeThrowAttemptNum;
			aoffenReboundNum = (double) offenReboundNum / playedGames;
			adefenReboundNum = (double) defenReboundNum / playedGames;
			astealNum = (double) stealNum / playedGames;
			ablockNum = (double) blockNum / playedGames;
			afoulNum = (double) foulNum / playedGames;
			aturnOverNum = (double) turnOverNum / playedGames;
			ascore = (double) score / playedGames;
			efficiency = (score + reboundNum + assistNum + stealNum + blockNum)
					- (shootAttemptNum - shootHitNum)
					- (freeThrowAttemptNum - freeThrowHitNum) - turnOverNum;
			Collections.sort(bufferRecords, new SequenceOfRecord());
			int recentFiveScore = 0;
			int recentReboundNum = 0;
			int recentAssistNum = 0;
			for (int i = 0; i < 5; i++) {
				RecordVO vo = bufferRecords.get(i);
				recentFiveScore += vo.getScore();
				recentReboundNum += vo.getReboundNum();
				recentAssistNum += vo.getAssistNum();
			}
			int beforeRecentFiveScore = score - recentFiveScore;
			int beforeRecentReboundNum = reboundNum - recentReboundNum;
			int beforeRecentAssistNum = assistNum - recentAssistNum;
			recentFiveMatchesScoreUpRate = (((double) recentFiveScore / 5) - (double) beforeRecentFiveScore
					/ (playedGames - 5))
					/ ((double) beforeRecentFiveScore / (playedGames - 5));
			recentFiveMatchesReboundUpRate = (((double) recentReboundNum / 5) - (double) beforeRecentReboundNum
					/ (playedGames - 5))
					/ ((double) beforeRecentReboundNum / (playedGames - 5));
			recentFiveMatchesAssistUpRate = (((double) recentAssistNum / 5) - (double) beforeRecentAssistNum
					/ (playedGames - 5))
					/ ((double) beforeRecentAssistNum / (playedGames - 5));
			GmScEfficiencyValue = score + 0.4 * shootHitNum - 0.7
					* shootAttemptNum - 0.4
					* (freeThrowAttemptNum - freeThrowHitNum) + 0.7
					* offenReboundNum + 0.3 * defenReboundNum + stealNum + 0.7
					* assistNum + 0.7 * blockNum - 0.4 * foulNum - turnOverNum;
			trueHitRate = (double) score
					/ (2 * (shootAttemptNum + 0.44 * freeThrowAttemptNum));
			shootEfficiency = (double) (shootHitNum + 0.5 * threeHitNum)
					/ shootAttemptNum;

			reboundRate = reboundNum * matchTime
					/ convertMinuteToSecond(presentTime)
					/ (teamAllReboundNum + dsAllReboundNum);
			offenReboundRate = offenReboundNum * matchTime
					/ convertMinuteToSecond(presentTime)
					/ (teamAllOffenReboundNum + dsAllOffenReboundNum);
			defenReboundRate = defenReboundNum * matchTime
					/ convertMinuteToSecond(presentTime)
					/ (teamAllDefenReboundNum + dsAllDefenReboundNum);
			assistRate = (double) assistNum
					/ (convertMinuteToSecond(presentTime) / matchTime
							* teamShootHitNum - shootHitNum);
			stealRate = assistNum * matchTime
					/ convertMinuteToSecond(presentTime) / dsOffenRoundNum;
			blockRate = blockNum * matchTime
					/ convertMinuteToSecond(presentTime) / dsTwoAttemptNum;
			turnOverRate = (double) turnOverNum
					/ (shootAttemptNum - threeAttemptNum + 0.4
							* freeThrowAttemptNum + turnOverNum);
			usageRate = (shootAttemptNum + 0.44 * freeThrowAttemptNum + turnOverNum)
					* matchTime
					/ convertMinuteToSecond(presentTime)
					/ (teamShootAttemptNum + 0.44 * teamFreeThrowAttemptNum + teamTurnOverNum);
			score_rebound_assist = (double) (score + reboundNum + assistNum) / 3;
			ascore_rebound_assist = score_rebound_assist / playedGames;
			adoubleDoubleNum = doubleDoubleNum / playedGames;

		}

		player.setName(name);
		player.setOwingTeam(owingTeam);
		player.setPlayedGames(playedGames);
		player.setGameStartingNum(gameStartingNum);
		player.setReboundNum(areboundNum);
		player.setAssistNum(aassistNum);
		player.setPresentTime(apresentTime);
		player.setShootHitRate(shootHitRate);
		player.setThreeHitRate(threeHitRate);
		player.setFreeThrowHitRate(freeThrowHitRate);
		player.setOffenReboundNum(aoffenReboundNum);
		player.setDefenReboundNum(adefenReboundNum);
		player.setStealNum(astealNum);
		player.setBlockNum(ablockNum);
		player.setFoulNum(afoulNum);
		player.setTurnOverNum(aturnOverNum);
		player.setScore(ascore);
		player.setEfficiency(efficiency);
		player.setRecentFiveMatchesScoreUpRate(recentFiveMatchesScoreUpRate);
		player.setRecentFiveMatchesReboundUpRate(recentFiveMatchesReboundUpRate);
		player.setRecentFiveMatchesAssistUpRate(recentFiveMatchesAssistUpRate);
		player.setGmScEfficiencyValue(GmScEfficiencyValue);
		player.setTrueHitRate(trueHitRate);
		player.setShootEfficiency(shootEfficiency);
		player.setReboundRate(reboundRate);
		player.setOffenReboundRate(offenReboundRate);
		player.setDefenReboundRate(defenReboundRate);
		player.setAssistRate(assistRate);
		player.setStealRate(stealRate);
		player.setBlockRate(blockRate);
		player.setTurnOverRate(turnOverRate);
		player.setUsageRate(usageRate);
		player.setScore_rebound_assist(ascore_rebound_assist);
		player.setDoubleDoubleNum(adoubleDoubleNum);
		return player;
	}

	// /***
	// *
	// * @param match
	// * @return获得球队总篮板
	// */
	// private int getTeamAllReboundNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(record.getTeam().equals(team)){
	// result+=record.getReboundNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得对手总篮板
	// */
	// private int getDSAllReboundNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(!record.getTeam().equals(team)){
	// result+=record.getReboundNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得球队总进攻篮板
	// */
	// private int getTeamAllOffenReboundNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(record.getTeam().equals(team)){
	// result+=record.getOffenReboundNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得对手总进攻篮板
	// */
	// private int getDSAllOffenReboundNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(!record.getTeam().equals(team)){
	// result+=record.getOffenReboundNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得球队总防守篮板
	// */
	// private int getTeamAllDefenReboundNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(record.getTeam().equals(team)){
	// result+=record.getDefenReboundNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得对手总防守篮板
	// */
	// private int getDSAllDefenReboundNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(!record.getTeam().equals(team)){
	// result+=record.getDefenReboundNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得球队总进球数
	// */
	// private int getTeamShootHitNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(record.getTeam().equals(team)){
	// result+=record.getShootHitNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得球队总出手数
	// */
	// private int getTeamShootAttemptNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(record.getTeam().equals(team)){
	// result+=record.getShootAttemptNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得对手球队总出手数
	// */
	// private int getDSShootAttemptNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(!record.getTeam().equals(team)){
	// result+=record.getShootAttemptNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得球队总罚球出手数
	// */
	// private int getTeamFreeThrowAttemptNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(record.getTeam().equals(team)){
	// result+=record.getFreeThrowAttemptNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得对手球队总罚球出手数
	// */
	// private int getDSFreeThrowAttemptNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(!record.getTeam().equals(team)){
	// result+=record.getFreeThrowAttemptNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得球队总失误数
	// */
	// private int getTeamTurnOverNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(record.getTeam().equals(team)){
	// result+=record.getTurnOverNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得对手球队总失误数
	// */
	// private int getDSTurnOverNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(!record.getTeam().equals(team)){
	// result+=record.getTurnOverNum();
	// }
	// }
	// return result;
	// }
	//
	// /***
	// *
	// * @param match
	// * @return获得对手球队总投失球数
	// */
	// private int getDSShootMissNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(!record.getTeam().equals(team)){
	// result+=record.getShootAttemptNum()-record.getShootHitNum();
	// }
	// }
	// return result;
	// }
	//
	//
	// /***
	// *
	// * @param match
	// * @return获得球队总进球数
	// */
	// private int getDSTwoAttemptNum(MatchVO match,String team){
	// ArrayList<RecordVO> records=match.getRecords();
	// int result=0;
	// for(RecordVO record:records){
	// if(!record.getTeam().equals(team)){
	// result+=record.getShootAttemptNum()-record.getThreeAttemptNum();
	// }
	// }
	// return result;
	// }

	/**
	 * 
	 * @param match
	 * @return 返回比赛的秒数
	 */
	private double getMatchTime(MatchVO match) {
		int jieshu = match.getDetailScores().size();
		if (jieshu == 4) {
			return 48 * 60;
		} else {
			return (48 + (jieshu - 4) * 5) * 60;
		}
	}

	public ArrayList<PlayerVO> getOrderedPlayersBySeason(String season,
			String condition, String order, int num)  {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getOrderedPlayersByAverage(String condition,
			String order, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> selectPlayersBySeason(String season,
			String position, String union, String column, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> selectPlayersByAverage(String position,
			String union, String column, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ImageIcon getPlayerPortraitImage(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public ImageIcon getPlayerActionImage(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getDayHotPlayer(String column) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getSeasonHotPlayer(String season, String column) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getBestImprovedPlayer(String column) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getPlayersByInitialName(char character) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MatchVO> getRecentMatches(String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MatchVO> getTodayMatches(String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MatchVO> getMatches(String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getPlayersByTeam(String teamAbLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	private String addPresentTime(String a, String b) {
		if (a.equals(""))
			return b;
		if (b.equals(""))
			return a;
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

	private int convertMinuteToSecond(String s) {
		String[] temp = s.split(":");
		int minute = Integer.parseInt(temp[0]);
		int second = Integer.parseInt(temp[1]);
		return minute * 60 + second;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		NewNewPlayer player = new NewNewPlayer(3);
		PlayerVO vo = player.getPlayerAverageInfo("Al Horford");
		System.out.println(vo.getOwingTeam());
		System.out.println(vo.getPlayedGames());
		System.out.println(vo.getGameStartingNum());
		System.out.println(vo.getReboundNum() * vo.getPlayedGames());
		System.out.println(vo.getAssistNum() * vo.getPlayedGames());
		System.out.println(vo.getPresentTime() * vo.getPlayedGames());
		System.out.println(vo.getShootHitRate());
		System.out.println(vo.getThreeHitRate());
		System.out.println(vo.getFreeThrowHitRate());
		System.out.println(vo.getOffenReboundNum() * vo.getPlayedGames());
		System.out.println(vo.getDefenReboundNum() * vo.getPlayedGames());
		System.out.println(vo.getStealNum() * vo.getPlayedGames());
		System.out.println(vo.getBlockNum() * vo.getPlayedGames());
		System.out.println(vo.getFoulNum() * vo.getPlayedGames());
		System.out.println(vo.getTurnOverNum() * vo.getPlayedGames());
		System.out.println(vo.getScore() * vo.getPlayedGames());
		System.out.println(vo.getEfficiency());
		System.out.println(vo.getRecentFiveMatchesScoreUpRate());
		System.out.println(vo.getRecentFiveMatchesReboundUpRate());
		System.out.println(vo.getRecentFiveMatchesAssistUpRate());
		System.out.println(vo.getGmScEfficiencyValue());
		System.out.println(vo.getTrueHitRate());
		System.out.println(vo.getShootHitEfficiency());
		System.out.println(vo.getReboundRate());
		System.out.println(vo.getOffenReboundRate());
		System.out.println(vo.getDefenReboundRate());
		System.out.println(vo.getAssistRate());
		System.out.println(vo.getStealRate());
		System.out.println(vo.getBlockRate());
		System.out.println(vo.getTurnOverRate());
		System.out.println(vo.getUsageRate());
		System.out.println(vo.getScore_rebound_assist() * vo.getPlayedGames());
		System.out.println(vo.getDoubleDoubleNum() * vo.getPlayedGames());
		PlayerVO vo2 = player.getPlayerAverageInfo("Aaron Brooks");
		PlayerVO vo3 = player.getPlayerAverageInfo("Adonis Thomas");
		PlayerVO vo4 = player.getPlayerAverageInfo("Aaron Gray");
		PlayerVO vo5 = player.getPlayerAverageInfo("Al Harrington");
		PlayerVO vo6 = player.getPlayerAverageInfo("Al Jefferson");
		player.getPlayerAverageInfo();
		long end = System.currentTimeMillis();
		System.out.println("运行时间：" + (end - start) + "毫秒");// 应该是end - start
	}

	public ArrayList<PlayerVO> selectPlayersBySeason(String season,
			String position, String union, int ageClass, String column,
			String order, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> selectPlayersByAverage(String position,
			String union, int ageClass, String column, String order, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getDayHotPlayer(String column, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getSeasonHotPlayer(String season, String column,
			int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getBestImprovedPlayer(String column, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MatchVO> getRecentMatches(String playerName, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> selectPlayersBySeason(String season,
			String position, String union, AgeEnum ageClass, String column,
			String order, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> selectPlayersByAverage(String position,
			String union, AgeEnum ageClass, String column, String order, int num) {
		// TODO Auto-generated method stub
		return null;
	}

}