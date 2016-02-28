package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import vo.MatchVO;
import vo.RecordVO;
import vo.TeamVO;
import bl.DataSourse;
import bl.match.NewMatch;

public class NewTeam {
	private Map<String, TeamVO> teamsBaseInfo = new LinkedHashMap<String, TeamVO>();
	private Map<String, MatchVO> matches = new LinkedHashMap<String, MatchVO>();
	private Map<String, TeamVO> teamAverageInfo = new LinkedHashMap<String, TeamVO>();

	// 删去了readFromMatchFile和getMatches两个私有方法，改为创建一个match类，从中得到比赛信息
	public NewTeam() {
		// TODO 自动生成的构造函数存根
		teamsBaseInfo = getTeams();
		NewMatch match = new NewMatch();
		matches = match.getMatchData("全部", "全部", "全部", "全部");
		// teamAverageInfo = calculateTeamAverageInfo();
	}

	public static void main(String[] args) {
		NewTeam newTeam = new NewTeam();
		String season = "13-14";
		Map<String, TeamVO> result = new LinkedHashMap<String, TeamVO>();
		result = newTeam.getSeasonHotTeam(season, "比赛得分", 5);

		System.out.println(result.size());

		Iterator<Entry<String, TeamVO>> iter = result.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO vo = (TeamVO) entry.getValue();

			System.out.println(vo.getAbLocation() + ":" + vo.getScore());

		}
	}

	/**
	 * 从文件中读取球队信息
	 * 
	 * @param fileName
	 *            存储球队信息的文件名
	 * @return
	 */
	private static ArrayList<String[]> readFromFile(String fileName) {
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

	/**
	 * 解析球队信息，转换成teamvo列表，初始化全局变量teamsBaseInfo
	 * 
	 * @return
	 */
	private Map<String, TeamVO> getTeams() {
		// TODO 自动生成的方法存根
		String name;
		String location;
		String abLocation;
		String conference;
		String partition;
		String homeCourt;
		int setUpTime;

		Map<String, TeamVO> teams = new LinkedHashMap<String, TeamVO>();
		TeamVO team;
		ArrayList<String[]> result = readFromFile(DataSourse.dataSourse
				+ "/teams/teams");
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
			teams.put(abLocation, team);
		}
		return teams;
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

		Map<String, TeamVO> teams = new LinkedHashMap<String, TeamVO>();
		TeamVO team;
		ArrayList<String[]> result = readFromFile(DataSourse.dataSourse
				+ "/teams/teams");
		for (String[] content : result) {
			abLocation = content[1];
			conference = content[3];// 东西部
			partition = content[4];

			team = new TeamVO(abLocation, conference, partition);
			teams.put(abLocation, team);
		}
		return teams;
	}

	/**
	 * 计算球队所有比赛的平均数据，初始化全局变量teamAverageInfo
	 * 
	 * @return
	 */
	private Map<String, TeamVO> calculateTeamAverageInfo() {
		// TODO 自动生成的方法存根
		Map<String, TeamVO> result = new LinkedHashMap<String, TeamVO>();
		Map<String, TeamVO> teams = new LinkedHashMap<String, TeamVO>();
		teams = getTeamBaseInfo();
		Iterator<Entry<String, TeamVO>> iter = teams.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO vo = (TeamVO) entry.getValue();
			String teamName = vo.getTeamName();
			String team = vo.getAbLocation();
			String location = vo.getLocation();
			String abLocation = vo.getAbLocation();
			String conference = vo.getConference();
			String partition = vo.getPartition();
			String homeCourt = vo.getHomeCourt();
			int setUpTime = vo.getSetUpTime();

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
			double dsOffenRound = 0;
			Iterator<Entry<String, MatchVO>> matchIter = matches.entrySet()
					.iterator();
			while (matchIter.hasNext()) {
				Map.Entry<String, MatchVO> matchEntry = (Map.Entry<String, MatchVO>) matchIter
						.next();
				MatchVO matchVO = (MatchVO) matchEntry.getValue();

				int homeScore = matchVO.getHomeScore();
				int visitingScore = matchVO.getVisitingScore();
				String homeTeam = matchVO.getHomeTeam();
				String visitingTeam = matchVO.getVisitingTeam();

				boolean isHomeTeam = homeTeam.equals(team);
				boolean isVisitingTeam = visitingTeam.equals(team);
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
					ArrayList<RecordVO> records = matchVO.getRecords();
					for (RecordVO recordVO : records) {
						if (recordVO.getTeam().equals(team)) {
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
						} else {
							// 计算对手的
							dsShootHitNum += recordVO.getShootHitNum();
							dsShootAttempNum += recordVO.getShootAttemptNum();
							dsFreeThrowAttemptNum += recordVO
									.getFreeThrowAttemptNum();
							dsTurnOverNum += recordVO.getTurnOverNum();
							dsOffenReboundNum += recordVO.getOffenReboundNum();
							dsDefenReboundNum += recordVO.getDefenReboundNum();
						}
					}

					shootHitRate = (double) shootHitNum / shootAttemptNum;// 投篮命中率
					threeHitRate = (double) threeHitNum / threeAttemptNum;// 三分命中率
					freeThrowHitRate = (double) freeThrowHitNum
							/ freeThrowAttemptNum;// 罚球命中率
					winRate = (double) winNum / matchesNum; // 胜率
					// 进攻回合
					offenRound = shootAttemptNum
							+ 0.4
							* freeThrowAttemptNum
							- 1.07
							* (offenReboundNum
									/ (double) (offenReboundNum + dsDefenReboundNum) * (shootAttemptNum - shootHitNum))
							+ 1.07 * turnOverNum;
					dsOffenRound = dsShootAttempNum
							+ 0.4
							* dsFreeThrowAttemptNum
							- 1.07
							* (dsOffenReboundNum
									/ (double) (dsOffenReboundNum + defenReboundNum) * (dsShootAttempNum - dsShootHitNum))
							+ 1.07 * dsTurnOverNum;

					offenEfficiency = (double) score / offenRound * 100; // 进攻效率
					defenEfficiency = (double) dsScore / dsOffenRound * 100; // 防守效率
					offenReboundEfficiency = (double) offenReboundNum
							/ (offenReboundNum + dsDefenReboundNum); // 进攻篮板效率
					defenReboundEfficiency = (double) defenReboundNum
							/ (defenReboundNum + dsOffenReboundNum); // 防守篮板效率
					stealEfficiency = (double) stealNum / dsOffenRound * 100; // 抢断效率
					assistEfficiency = (double) assistNum / offenRound * 100; // 助攻率
				}
			}
			TeamVO teamVO = new TeamVO(teamName, abLocation, location,
					conference, partition, homeCourt, setUpTime, matchesNum,
					shootHitNum, shootAttemptNum, threeHitNum, threeAttemptNum,
					freeThrowHitNum, freeThrowAttemptNum, offenReboundNum,
					defenReboundNum, reboundNum, assistNum, stealNum, blockNum,
					turnOverNum, foulNum, score, shootHitRate, threeHitRate,
					freeThrowHitRate, winRate, offenRound, offenEfficiency,
					defenEfficiency, offenReboundEfficiency,
					defenReboundEfficiency, stealEfficiency, assistEfficiency);
			result.put(abLocation, teamVO);
		}

		return result;
	}

	/**
	 * 从数据库中获得球队列表teams
	 * 
	 * @return 球队最基本信息的列表，其他属性值为空，未进行初始化
	 */
	public Map<String, TeamVO> getTeamBaseInfo() {
		return teamsBaseInfo;
	}

	/**
	 * 得到球队的该赛季的技术统计数据
	 * 
	 * @param season
	 *            赛季
	 * @return 球队赛季统计数据，vo的所有初始值均进行了初始化
	 * 
	 */
	public Map<String, TeamVO> getTeamSeasonInfo(String season) {
		// TODO 自动生成的方法存根
		Map<String, TeamVO> result = new LinkedHashMap<String, TeamVO>();
		Map<String, TeamVO> teams = new LinkedHashMap<String, TeamVO>();
		teams = getTeamBaseInfo();
		Iterator<Entry<String, TeamVO>> iter = teams.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO vo = (TeamVO) entry.getValue();

			String teamName = vo.getTeamName();
			String team = vo.getAbLocation();
			String location = vo.getLocation();
			String abLocation = vo.getAbLocation();
			String conference = vo.getConference();
			String partition = vo.getPartition();
			String homeCourt = vo.getHomeCourt();
			int setUpTime = vo.getSetUpTime();

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
			double dsOffenRound = 0;
			Iterator<Entry<String, MatchVO>> matchIter = matches.entrySet()
					.iterator();
			while (matchIter.hasNext()) {
				Map.Entry<String, MatchVO> matchEntry = (Map.Entry<String, MatchVO>) matchIter
						.next();
				MatchVO matchVO = (MatchVO) matchEntry.getValue();

				// if (matchVO.getSeason().equals(season)) {
				int homeScore = matchVO.getHomeScore();
				int visitingScore = matchVO.getVisitingScore();
				String homeTeam = matchVO.getHomeTeam();
				String visitingTeam = matchVO.getVisitingTeam();

				boolean isHomeTeam = homeTeam.equals(team);
				boolean isVisitingTeam = visitingTeam.equals(team);
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
					ArrayList<RecordVO> records = matchVO.getRecords();
					for (RecordVO recordVO : records) {
						if (recordVO.getTeam().equals(team)) {
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
						} else {
							// 计算对手的
							dsShootHitNum += recordVO.getShootHitNum();
							dsShootAttempNum += recordVO.getShootAttemptNum();
							dsFreeThrowAttemptNum += recordVO
									.getFreeThrowAttemptNum();
							dsTurnOverNum += recordVO.getTurnOverNum();
							dsOffenReboundNum += recordVO.getOffenReboundNum();
							dsDefenReboundNum += recordVO.getDefenReboundNum();
						}
					}

					shootHitRate = (double) shootHitNum / shootAttemptNum;// 投篮命中率
					threeHitRate = (double) threeHitNum / threeAttemptNum;// 三分命中率
					freeThrowHitRate = (double) freeThrowHitNum
							/ freeThrowAttemptNum;// 罚球命中率
					winRate = (double) winNum / matchesNum; // 胜率
					// 进攻回合
					offenRound = shootAttemptNum
							+ 0.4
							* freeThrowAttemptNum
							- 1.07
							* (offenReboundNum
									/ (double) (offenReboundNum + dsDefenReboundNum) * (shootAttemptNum - shootHitNum))
							+ 1.07 * turnOverNum;
					dsOffenRound = dsShootAttempNum
							+ 0.4
							* dsFreeThrowAttemptNum
							- 1.07
							* (dsOffenReboundNum
									/ (double) (dsOffenReboundNum + defenReboundNum) * (dsShootAttempNum - dsShootHitNum))
							+ 1.07 * dsTurnOverNum;

					offenEfficiency = (double) score / offenRound * 100; // 进攻效率
					defenEfficiency = (double) dsScore / dsOffenRound * 100; // 防守效率
					offenReboundEfficiency = (double) offenReboundNum
							/ (offenReboundNum + dsDefenReboundNum); // 进攻篮板效率
					defenReboundEfficiency = (double) defenReboundNum
							/ (defenReboundNum + dsOffenReboundNum); // 防守篮板效率
					stealEfficiency = (double) stealNum / dsOffenRound * 100; // 抢断效率
					assistEfficiency = (double) assistNum / offenRound * 100; // 助攻率
				}
				// }
			}
			TeamVO teamVO = new TeamVO(teamName, abLocation, location,
					conference, partition, homeCourt, setUpTime, matchesNum,
					shootHitNum, shootAttemptNum, threeHitNum, threeAttemptNum,
					freeThrowHitNum, freeThrowAttemptNum, offenReboundNum,
					defenReboundNum, reboundNum, assistNum, stealNum, blockNum,
					turnOverNum, foulNum, score, shootHitRate, threeHitRate,
					freeThrowHitRate, winRate, offenRound, offenEfficiency,
					defenEfficiency, offenReboundEfficiency,
					defenReboundEfficiency, stealEfficiency, assistEfficiency);
			result.put(abLocation, teamVO);
		}

		return result;
	}

	/**
	 * 得到球队的该场均的技术统计数据
	 * 
	 * @return 球队场均统计数据，VO的所有初始值均进行了初始化
	 * 
	 */
	public Map<String, TeamVO> getTeamAverageInfo() {
		// TODO 自动生成的方法存根
		teamAverageInfo = calculateTeamAverageInfo();
		return teamAverageInfo;
	}

	/**
	 * 模糊查询球队的基础信息，球队名可以是名称，也可以是缩写，大小写均可
	 * 
	 * @param name
	 *            球队名称
	 * @return 符合模糊查询条件的球队对象的列表，球队对象只进行了基础信息的初始化与赋值
	 */
	public Map<String, TeamVO> getTeamBaseInfo(String name) {
		// TODO 自动生成的方法存根
		Map<String, TeamVO> result = new LinkedHashMap<String, TeamVO>();
		Iterator<Entry<String, TeamVO>> iter = teamsBaseInfo.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO teamVO = (TeamVO) entry.getValue();

			String teamName = teamVO.getTeamName().toLowerCase();
			String abLocation = teamVO.getAbLocation().toLowerCase();
			name = name.toLowerCase();
			if (teamName.contains(name) || abLocation.contains(name)) {
				result.put(abLocation, teamVO);
			}
		}
		return result;
	}

	/**
	 * 模糊查询球队的某个赛季的信息，球队名可以是名称，也可以是缩写，大小写均可
	 * 
	 * @param season
	 *            赛季
	 * @param name
	 *            球队名称
	 * @return 符合模糊查询条件的球队对象的列表
	 */
	public Map<String, TeamVO> getTeamSeasonInfo(String season, String name) {
		// TODO 自动生成的方法存根
		Map<String, TeamVO> result = new LinkedHashMap<String, TeamVO>();
		Map<String, TeamVO> teams = new LinkedHashMap<String, TeamVO>();
		teams = getTeamSeasonInfo(season);
		Iterator<Entry<String, TeamVO>> iter = teams.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO vo = (TeamVO) entry.getValue();
			String teamName = vo.getTeamName().toLowerCase();
			String abLocation = vo.getAbLocation().toLowerCase();
			name = name.toLowerCase();
			if (teamName.contains(name) || abLocation.contains(name)) {
				result.put(abLocation, vo);
			}
		}
		return result;
	}

	/**
	 * 模糊查询球队的场均技术信息，球队名可以是名称，也可以是缩写，大小写均可
	 * 
	 * @param name
	 *            球队名称
	 * @return 符合模糊查询条件的球队对象的列表
	 */
	public Map<String, TeamVO> getTeamAverageInfo(String name) {
		// TODO 自动生成的方法存根
		Map<String, TeamVO> result = new LinkedHashMap<String, TeamVO>();
		Map<String, TeamVO> teams = new LinkedHashMap<String, TeamVO>();
		teams = getTeamAverageInfo();
		Iterator<Entry<String, TeamVO>> iter = teams.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO vo = (TeamVO) entry.getValue();
			String teamName = vo.getTeamName().toLowerCase();
			String abLocation = vo.getAbLocation().toLowerCase();
			name = name.toLowerCase();
			if (teamName.contains(name) || abLocation.contains(name)) {
				result.put(abLocation, vo);
			}
		}
		return result;
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
	public Map<String, TeamVO> getOrderedTeamsBySeason(String season,
			String condition, String order, int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 30;
		}
		Map<String, TeamVO> teams = new LinkedHashMap<String, TeamVO>();
		teams = getTeamSeasonInfo(season);

		// 未明确写明排序方式时默认是升序
		if (order == null || order.equals("null")) {
			order = "asc";
		}
		List<Map.Entry<String, TeamVO>> infoIds = new ArrayList<Map.Entry<String, TeamVO>>(
				teams.entrySet());
		Collections.sort(infoIds, new SequenceOfTeamMap(condition, order));
		Map<String, TeamVO> result = new LinkedHashMap<String, TeamVO>();
		int count = 0;
		for (Map.Entry<String, TeamVO> map : infoIds) {
			String key = map.getKey();
			TeamVO vo = map.getValue();
			result.put(key, vo);
			count++;
			if (num >= infoIds.size()) {
				if (count >= num) {
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 根据某一项技术分析项，将球队按场均该项数据进行升降序排序
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
	public Map<String, TeamVO> getOrderedTeamsByAverage(String condition,
			String order, int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 30;
		}
		Map<String, TeamVO> teams = new LinkedHashMap<String, TeamVO>();
		teams = teamAverageInfo;
		// 未明确写明排序方式时默认是升序
		if (order == null || order.equals("null")) {
			order = "asc";
		}
		List<Map.Entry<String, TeamVO>> infoIds = new ArrayList<Map.Entry<String, TeamVO>>(
				teams.entrySet());
		Collections.sort(infoIds, new SequenceOfTeamMap(condition, order));
		Map<String, TeamVO> result = new LinkedHashMap<String, TeamVO>();
		int count = 0;
		for (Map.Entry<String, TeamVO> map : infoIds) {
			String key = map.getKey();
			TeamVO vo = map.getValue();
			result.put(key, vo);
			count++;
			if (num >= infoIds.size()) {
				if (count >= num) {
					break;
				}
			}
		}

		return result;
	}

	/**
	 * 根据球队缩写得到球队队徽
	 * 
	 * @param name
	 *            球队缩写
	 * @return 该球队队徽
	 */
	public ImageIcon getTeamImage(String name) {
		// TODO 自动生成的方法存根
		ImageIcon imageIcon = new ImageIcon(DataSourse.dataSourse
				+ "/teamsPng/" + name + ".png");
		return imageIcon;
	}

	/**
	 * 根据球队名称查找该球队近期比赛
	 * 
	 * @param teamName
	 *            球队缩写 完全匹配
	 * @return 近期五场比赛的列表
	 */
	public Map<String, MatchVO> getRecentMatches(String teamName) {
		// TODO 自动生成的方法存根
		Map<String, MatchVO> result = new LinkedHashMap<String, MatchVO>();
		result = getRecentMatches(teamName, 5);
		return result;
	}

	/**
	 * 根据球队名称查找其过往比赛
	 * 
	 * @param teamName
	 *            球队缩写 完全匹配
	 * @return 过往比赛列表
	 */
	public Map<String, MatchVO> getMatches(String teamName) {
		// TODO 自动生成的方法存根
		Map<String, MatchVO> result = new LinkedHashMap<String, MatchVO>();
		result = getRecentMatches(teamName, -1);
		return result;
	}

	/**
	 * 根据球队名得到近期比赛的某种数据
	 * 
	 * @param teamName
	 *            球队名缩写，完全匹配
	 * @param num
	 *            得到比赛的场数 ，-1则是取出全部数据
	 * @return 过往比赛的列表
	 */
	private Map<String, MatchVO> getRecentMatches(String teamName, int num) {
		Map<String, MatchVO> result = new LinkedHashMap<String, MatchVO>();
		int count = 0;
		Iterator<Entry<String, MatchVO>> iter = matches.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MatchVO> entry = (Map.Entry<String, MatchVO>) iter
					.next();
			MatchVO matchVO = (MatchVO) entry.getValue();
			if (num > 0) {// -1则是取出全部数据
				if (count >= num) {
					break;
				}
			}
			String visitingTeam = matchVO.getVisitingTeam();
			String homeTeam = matchVO.getHomeTeam();
			if (homeTeam.equals(teamName) || visitingTeam.equals(teamName)) {
				String key = (String) entry.getKey();
				result.put(key, matchVO);
				count++;
			}
		}
		// 要对比赛进行排序吗==
		return result;
	}

	/**
	 * 筛选出赛季的热点球队——所谓热点球队，就是按照筛选条件排前五的球队
	 * 
	 * @param column
	 *            筛选条件
	 * @return 返回到 目前为止所有参加过比赛的球队中筛选出前 5 名球队（按照 降序排列进行筛选）
	 */
	public Map<String, TeamVO> getSeasonHotTeam(String season, String column,
			int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 30;
		}
		Map<String, TeamVO> result = new LinkedHashMap<String, TeamVO>();
		Map<String, TeamVO> teamSeasonInfo = new LinkedHashMap<String, TeamVO>();
		teamSeasonInfo = getTeamSeasonInfo(season);

		List<Map.Entry<String, TeamVO>> infoIds = new ArrayList<Map.Entry<String, TeamVO>>(
				teamSeasonInfo.entrySet());
		Collections.sort(infoIds, new SequenceOfTeamMap(column, "desc"));

		int count = 0;
		for (Map.Entry<String, TeamVO> map : infoIds) {
			String key = map.getKey();
			TeamVO vo = map.getValue();
			result.put(key, vo);
			count++;
			if (count >= num) {
				break;
			}
		}
		return result;
	}

	/**
	 * 将比赛的LinkedHashMap转为Arraylist便于排序
	 * 
	 * @param map
	 *            球队
	 * @return
	 */
	private ArrayList<MatchVO> changeMatchMapToList(Map<String, MatchVO> map) {
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		Iterator<Entry<String, MatchVO>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MatchVO> entry = (Map.Entry<String, MatchVO>) iter
					.next();
			MatchVO vo = (MatchVO) entry.getValue();
			result.add(vo);
		}
		return result;
	}

	/**
	 * 将球队的LinkedHashMap转为Arraylist便于排序
	 * 
	 * @param teams
	 *            球队
	 * @return
	 */
	private ArrayList<TeamVO> changeTeamMapToList(Map<String, TeamVO> teams) {
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		Iterator<Entry<String, TeamVO>> iter = teams.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO vo = (TeamVO) entry.getValue();
			result.add(vo);
		}
		return result;
	}

	private Map<String, TeamVO> changeTeamListToMap(ArrayList<TeamVO> list) {
		Map<String, TeamVO> result = new LinkedHashMap<String, TeamVO>();
		for (TeamVO vo : list) {
			String key = vo.getAbLocation();
			result.put(key, vo);
		}
		return result;
	}

	/**
	 * 将球队中文名转为相应的英文缩写
	 * 
	 * @param CH
	 *            球队中文名
	 * @return 英文缩写
	 */
	public static String changeTeamNameCHToEN(String CH) {
		String EN = "";
		if (CH.equals("太阳")) {
			EN = "PHX";
		} else if (CH.equals("马刺")) {
			EN = "SAS";
		} else if (CH.equals("国王")) {
			EN = "SAC";
		} else if (CH.equals("火箭")) {
			EN = "HOU";
		} else if (CH.equals("湖人")) {
			EN = "LAL";
		} else if (CH.equals("掘金")) {
			EN = "DEN";
		} else if (CH.equals("森林狼")) {
			EN = "MIN";
		} else if (CH.equals("小牛")) {
			EN = "DAL";
		} else if (CH.equals("快船")) {
			EN = "LAC";
		} else if (CH.equals("爵士")) {
			EN = "UTA";
		} else if (CH.equals("勇士")) {
			EN = "GSW";
		} else if (CH.equals("灰熊")) {
			EN = "MEM";
		} else if (CH.equals("雷霆")) {
			EN = "OKC";
		} else if (CH.equals("鹈鹕")) {
			EN = "NOP";
		} else if (CH.equals("开拓者")) {
			EN = "POR";
		} else if (CH.equals("凯尔特人")) {
			EN = "BOS";
		} else if (CH.equals("篮网")) {
			EN = "BKN";
		} else if (CH.equals("尼克斯")) {
			EN = "NYK";
		} else if (CH.equals("76人")) {
			EN = "PHI";
		} else if (CH.equals("猛龙")) {
			EN = "TOR";
		} else if (CH.equals("公牛")) {
			EN = "CHI";
		} else if (CH.equals("骑士")) {
			EN = "CLE";
		} else if (CH.equals("活塞")) {
			EN = "DET";
		} else if (CH.equals("步行者")) {
			EN = "IND";
		} else if (CH.equals("雄鹿")) {
			EN = "MIL";
		} else if (CH.equals("老鹰")) {
			EN = "ATL";
		} else if (CH.equals("黄蜂")) {
			EN = "CHA";
		} else if (CH.equals("热火")) {
			EN = "MIA";
		} else if (CH.equals("魔术")) {
			EN = "ORL";
		} else if (CH.equals("奇才")) {
			EN = "WAS";
		} else {
			EN = CH;
		}

		return EN;
	}
}
