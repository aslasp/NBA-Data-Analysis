package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import vo.MatchVO;
import vo.RecordVO;
import vo.TeamVO;
import bl.DataSourse;
import bl.DirtyDataManager;
import bl.match.NewMatch;
import blservice.TeamBLService;

public class FinalTeam implements TeamBLService {
	private Map<String, TeamVO> teamsBaseInfo = new LinkedHashMap<String, TeamVO>();
	private Map<String, MatchVO> matches = new LinkedHashMap<String, MatchVO>();
	private Map<String, TeamVO> teamAverageInfo = new LinkedHashMap<String, TeamVO>();

	// 删去了readFromMatchFile和getMatches两个私有方法，改为创建一个match类，从中得到比赛信息
	public FinalTeam() {
		// TODO 自动生成的构造函数存根
		getTeams();
		NewMatch match = new NewMatch();
		matches = match.getMatchData("全部", "全部", "全部", "全部");
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

		Map<String, TeamVO> teams = new HashMap<String, TeamVO>();
		TeamVO team;
		ArrayList<String[]> result = readFromFile(DataSourse.dataSourse
				+ "/teams/teams");
		for (String[] content : result) {
			abLocation = content[1];
			conference = DirtyDataManager.checkConference(abLocation,
					content[3]);// 东西部
			partition = content[4];

			team = new TeamVO(abLocation, conference, partition);
			teams.put(abLocation, team);
		}
		return teams;
	}

	public ArrayList<TeamVO> getTeamBaseInfo() {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = changeMapToList(teamsBaseInfo);
		return result;
	}

	public ArrayList<TeamVO> getTeamSeasonInfo(String season) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		Iterator<Entry<String, TeamVO>> iter = teamsBaseInfo.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO vo = (TeamVO) entry.getValue();

			TeamVO teamVO = calculateTeamAverageInfo(vo, season);
			result.add(teamVO);
		}
		Collections.sort(result, new SequenceOfTeam("teamName", "asc"));
		return result;
	}

	public ArrayList<TeamVO> getTeamAverageInfo() {
		// TODO 自动生成的方法存根
		calculateTeamAverageInfo();
		ArrayList<TeamVO> result = changeMapToList(teamAverageInfo);
		return result;
	}

	public ArrayList<TeamVO> getTeamBaseInfo(String name) {
		// TODO 自动生成的方法存根
		TeamVO test = teamsBaseInfo.get(name);
		if (test != null) {
			ArrayList<TeamVO> result = new ArrayList<TeamVO>();
			result.add(test);
			return result;
		} else {
			Map<String, TeamVO> temp = new LinkedHashMap<String, TeamVO>();
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
					temp.put(abLocation, teamVO);
				}
			}
			ArrayList<TeamVO> result = changeMapToList(temp);
			return result;
		}
	}

	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String name) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		Iterator<Entry<String, TeamVO>> iter = teamsBaseInfo.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO vo = (TeamVO) entry.getValue();

			String teamName = vo.getTeamName();
			String team = vo.getAbLocation();

			name = name.toLowerCase();
			boolean flag = teamName.contains(name) || team.contains(name);
			if (!flag) {
				// 当前球队不是我要的球队，就跳过他不进行计算
				continue;
			}

			TeamVO teamVO = calculateTeamAverageInfo(vo, season);
			result.add(teamVO);
		}
		return result;
	}

	public ArrayList<TeamVO> getTeamAverageInfo(String name) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		Iterator<Entry<String, TeamVO>> iter = teamsBaseInfo.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO vo = (TeamVO) entry.getValue();
			String teamName = vo.getTeamName();
			String team = vo.getAbLocation();

			name = name.toLowerCase();
			boolean flag = teamName.contains(name) || team.contains(name);
			if (!flag) {
				// 当前球队不是我要的球队，就跳过他不进行计算
				continue;
			}
			TeamVO teamVO = calculateTeamAverageInfo(vo, "all");
			result.add(teamVO);
		}
		return result;
	}

	public ArrayList<TeamVO> getOrderedTeamsBySeason(String season,
			String condition, String order, int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 30;
		}
		ArrayList<TeamVO> teams = getTeamSeasonInfo(season);
		// 未明确写明排序方式时默认是升序
		if (order == null || order.equals("null")) {
			order = "asc";
		}
		Collections.sort(teams, new SequenceOfTeam(condition, order));
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		for (int i = 0; i < num; i++) {
			result.add(teams.get(i));
		}
		return result;
	}

	public ArrayList<TeamVO> getOrderedTeamsByAverage(String condition,
			String order, int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 30;
		}
		ArrayList<TeamVO> teams = getTeamAverageInfo();
		// 未明确写明排序方式时默认是升序
		if (order == null || order.equals("null")) {
			order = "asc";
		}
		Collections.sort(teams, new SequenceOfTeam(condition, order));
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		for (int i = 0; i < num; i++) {
			result.add(teams.get(i));
		}
		return result;
	}

	public ImageIcon getTeamImage(String name) {
		// TODO 自动生成的方法存根
		ImageIcon imageIcon = new ImageIcon(DataSourse.dataSourse
				+ "/teamsPng/" + name + ".png");
		return imageIcon;
	}

	public ArrayList<MatchVO> getRecentMatches(String teamName) {
		// TODO 自动生成的方法存根
		ArrayList<MatchVO> result = getRecentMatches(teamName, 5);
		return result;
	}

	public ArrayList<MatchVO> getMatches(String teamName) {
		// TODO 自动生成的方法存根
		ArrayList<MatchVO> result = getRecentMatches(teamName, -1);
		return result;
	}

	public ArrayList<TeamVO> getSeasonHotTeam(String season, String column,
			int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 30;
		}
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		ArrayList<TeamVO> teamSeasonInfo = getTeamSeasonInfo(season);

		Collections.sort(teamSeasonInfo, new SequenceOfTeam(column, "desc"));

		for (int i = 0; i < num; i++) {
			result.add(teamSeasonInfo.get(i));
		}
		return result;
	}

	private ArrayList<TeamVO> changeMapToList(Map<String, TeamVO> teams) {
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

	/**
	 * 根据球队名得到近期比赛的某种数据
	 * 
	 * @param teamName
	 *            球队名缩写，完全匹配
	 * @param num
	 *            得到比赛的场数 ，-1则是取出全部数据
	 * @return 过往比赛的列表
	 */
	private ArrayList<MatchVO> getRecentMatches(String teamName, int num) {
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
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
				result.add(matchVO);
				count++;
			}
		}
		return result;
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
	private void getTeams() {
		// TODO 自动生成的方法存根
		String name;
		String location;
		String abLocation;
		String conference;
		String partition;
		String homeCourt;
		int setUpTime;

		TeamVO team;
		ArrayList<String[]> result = readFromFile(DataSourse.dataSourse
				+ "/teams/teams");
		for (String[] content : result) {
			name = content[0];
			abLocation = content[1];
			location = content[2];
			conference = DirtyDataManager.checkConference(abLocation,
					content[3]);
			partition = content[4];
			homeCourt = content[5];
			setUpTime = Integer.parseInt(content[6]);

			team = new TeamVO(name, abLocation, location, conference,
					partition, homeCourt, setUpTime);
			teamsBaseInfo.put(abLocation, team);
		}
	}

	/**
	 * 计算球队所有比赛的平均数据，初始化全局变量teamAverageInfo
	 * 
	 * @return
	 */
	private void calculateTeamAverageInfo() {
		// TODO 自动生成的方法存根
		Iterator<Entry<String, TeamVO>> iter = teamsBaseInfo.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TeamVO> entry = (Map.Entry<String, TeamVO>) iter
					.next();
			TeamVO vo = (TeamVO) entry.getValue();
			String abLocation = vo.getAbLocation();
			TeamVO teamVO = calculateTeamAverageInfo(vo, "all");
			teamAverageInfo.put(abLocation, teamVO);
		}

	}

	private TeamVO calculateTeamAverageInfo(TeamVO vo, String season) {
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

			if (!season.equals("all")) {
				if (!matchVO.getSeason().equals(season)) {
					continue;
				}
			}

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
