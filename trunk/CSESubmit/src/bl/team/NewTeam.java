package bl.team;

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
import bl.match.Match;

//得到赛季热点球队时，根据场均数据，但是只是因为我们只有一个赛季的数据，就直接搞总的场均的吧====蛋蛋疼
public class NewTeam{
	private Map<String, TeamVO> teamsBaseInfo = new LinkedHashMap<String, TeamVO>();
	private Map<String, Map<String, MatchVO>> matches = new LinkedHashMap<String, Map<String, MatchVO>>();
	private Map<String, TeamVO> teamAverageInfo = new LinkedHashMap<String, TeamVO>();
	private Match match;
	private Boolean isSeason = false;

	// 删去了readFromMatchFile和getMatches两个私有方法，改为创建一个match类，从中得到比赛信息
	public NewTeam() {
		// TODO 自动生成的构造函数存根
		getTeams();
		match = new Match();
		matches = match.getAllMatches();
	}

	public static void main(String[] args) {
		Team team = new Team();
		String season = "13-14";
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		// ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		// result = team.getTeamSeasonInfo(season);
		// result = team.getTeamAverageInfo();
		// result = team.getSeasonHotTeam(season, "score", 4);
		// result = team.getTeamSeasonInfo(season, "NOP");
		// result = team.getSeasonHotTeam(season, "blockNum", 5);
		// result=team.getTeamBaseInfo("p");
		result = team.getTeamAverageInfo("SAS");
		// result = team.getTeamAverageInfo("NOH");
		// result = team.getRecentMatches("ATL");
		// result=team.getMatches("ATL");
//		result = team.getSeasonHotTeam(season, "freeThrowHitRate", 5);
		System.out.println(result.size());

		for (TeamVO vo : result) {
			 System.out.println(vo.getAbLocation() + " " + vo.getOffenRound());
//			System.out.println(vo.getAbLocation());
//			System.out.println("winRate：" + vo.getWinRate());
//			System.out.println("shootHitNum：" + vo.getShootHitNum());
//			System.out.println("shootAttemptNum：" + vo.getShootAttemptNum());
//			System.out.println("threeHitNum：" + vo.getThreeHitNum());
//			System.out.println("threeAttemptNum：" + vo.getThreeAttemptNum());
//			System.out.println("freeThrowHitNum：" + vo.getFreeThrowHitNum());
//			System.out.println("freeThrowAttemptNum："
//					+ vo.getFreeThrowAttemptNum());
//			System.out.println("offenReboundNum：" + vo.getOffenReboundNum());
//			System.out.println("defenReboundNum：" + vo.getDefenReboundNum());
//			System.out.println("reboundNum：" + vo.getReboundNum());
//			System.out.println("assistNum：" + vo.getAssistNum());
//			System.out.println("stealNum：" + vo.getStealNum());
//			System.out.println("blockNum：" + vo.getBlockNum());
//			System.out.println("turnOverNum：" + vo.getTurnOverNum());
//			System.out.println("foulNum：" + vo.getFoulNum());
//			System.out.println("score：" + vo.getScore());
//			System.out.println("shootHitRate:" + vo.getShootHitRate());
//			System.out.println("threeHitRate:" + vo.getThreeHitRate());
//			System.out.println("freeThrowHitRate:" + vo.getFreeThrowHitRate());
//			System.out.println("offenRound:" + vo.getOffenRound());
//			System.out.println("offenEfficiency:" + vo.getOffenEfficiency());
//			System.out.println("defenEfficiency:" + vo.getDefenEfficiency());
//			System.out.println("offenReboundEfficiency:"
//					+ vo.getOffenReboundEfficiency());
//			System.out.println("defenReboundEfficiency:"
//					+ vo.getDefenReboundEfficiency());
//			System.out.println("stealEfficiency:" + vo.getStealEfficiency());
//			System.out.println("assistRate:" + vo.getAssistRate());
//			System.out.println("----------------------------------");

		}

		// for (MatchVO vo : result) {
		// System.out.println(vo.getVisitingScore() + ":" + vo.getHomeScore());
		// }
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

			TeamVO teamVO = calculateTeamInfo(vo, season);
			if (teamVO != null) {
				result.add(teamVO);
			}
		}
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
		if (name.equals("NOP") || name.equals("NOH")) {
			ArrayList<TeamVO> result = new ArrayList<TeamVO>();
			TeamVO t1 = teamsBaseInfo.get("NOP");
			if (t1 == null) {
				TeamVO t2 = teamsBaseInfo.get("NOH");
				result.add(t2);
			} else {
				result.add(t1);
			}
			return result;
		}
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
				if (teamName.toLowerCase().contains(name)
						|| abLocation.toLowerCase().contains(name)) {
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

			TeamVO teamVO = calculateTeamInfo(vo, season);
			if (teamVO != null) {
				result.add(teamVO);
			}
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
			TeamVO teamVO = calculateTeamInfo(vo, "all");
			if (teamVO != null) {
				result.add(teamVO);
			}
		}
		return result;
	}

	public ArrayList<TeamVO> getOrderedTeamsBySeason(String season,
			ArrayList<String> condition, ArrayList<String> order, int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 30;
		}
		ArrayList<TeamVO> teams = getTeamSeasonInfo(season);

		Collections.sort(teams, new NewSequenceOfTeam(condition, order));
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		for (int i = 0; i < num; i++) {
			result.add(teams.get(i));
		}
		return result;
	}

	public ArrayList<TeamVO> getOrderedTeamsByAverage(ArrayList<String> condition,
			ArrayList<String> order, int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 30;
		}
		ArrayList<TeamVO> teams = getTeamAverageInfo();

		Collections.sort(teams, new NewSequenceOfTeam(condition, order));
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		for (int i = 0; i < num; i++) {
			result.add(teams.get(i));
		}
		return result;
	}

	public ImageIcon getTeamImage(String name) {
		// TODO 自动生成的方法存根
		ImageIcon imageIcon = null;
		if (name.equals("NOH")) {
			imageIcon = new ImageIcon(DataSourse.dataSourse + "/teamsPng/"
					+ "NOP" + ".png");
		}
		imageIcon = new ImageIcon(DataSourse.dataSourse + "/teamsPng/" + name
				+ ".png");
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
		// ArrayList<TeamVO> teamSeasonInfo = getTeamSeasonInfo(season);
		ArrayList<TeamVO> teamSeasonInfo = getTeamAverageInfo();
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

		// ====================================
		matches = match.getAllMatches();
		Iterator<Entry<String, Map<String, MatchVO>>> iter = matches.entrySet()
				.iterator();
		ArrayList<String> seasons = new ArrayList<String>();
		while (iter.hasNext()) {
			Map.Entry<String, Map<String, MatchVO>> entry = (Map.Entry<String, Map<String, MatchVO>>) iter
					.next();
			String seasonKey = (String) entry.getKey();
			seasons.add(seasonKey);
		}
		Collections.sort(seasons);

		int matchCount = 0;
		int seasonCount = 0;
		while (seasonCount < matches.size()) {
			Map<String, MatchVO> map = matches.get(seasons.get(seasonCount));
			if (num > 0) {// -1则是取出全部数据
				if (matchCount >= num) {
					break;
				}
			}

			Iterator<Entry<String, MatchVO>> iter2 = map.entrySet().iterator();
			while (iter2.hasNext()) {
				if (num > 0) {// -1则是取出全部数据
					if (matchCount >= num) {
						break;
					}
				}
				Map.Entry<String, MatchVO> entry = (Map.Entry<String, MatchVO>) iter2
						.next();
				MatchVO matchVO = (MatchVO) entry.getValue();

				String visitingTeam = matchVO.getVisitingTeam();
				String homeTeam = matchVO.getHomeTeam();

				boolean isHomeTeam = false;
				boolean isVisitingTeam = false;

				if (teamName.equals("NOP") || teamName.equals("NOH")) {
					isHomeTeam = homeTeam.equals("NOP")
							|| homeTeam.equals("NOH");
					isVisitingTeam = visitingTeam.equals("NOP")
							|| visitingTeam.equals("NOH");
				} else {
					isHomeTeam = homeTeam.equals(teamName);
					isVisitingTeam = visitingTeam.equals(teamName);
				}
				if (isHomeTeam || isVisitingTeam) {
					result.add(matchVO);
					matchCount++;
				}
			}
			seasonCount++;
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
			TeamVO teamVO = calculateTeamInfo(vo, "all");
			if (teamVO != null) {
				if (isSeason && abLocation.equals("NOP")) {
					abLocation = "NOH";
				}
				teamAverageInfo.put(abLocation, teamVO);
			}
		}

	}

	private TeamVO calculateTeamInfo(TeamVO vo, String season) {
		String teamName = vo.getTeamName();
		String team = vo.getAbLocation();
		String location = vo.getLocation();
		String abLocation = vo.getAbLocation();
		String conference = vo.getConference();
		String partition = vo.getPartition();
		String homeCourt = vo.getHomeCourt();
		int setUpTime = vo.getSetUpTime();

		String testSeason = "";

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

		boolean isBad = false;

		Map<String, MatchVO> allMatches = new HashMap<String, MatchVO>();

		if (team.equals("NOP")) {
			isBad = true;
		}

		if (season.equals("all")) {
			// 得到全部比赛数据的，还是觉得有点傻逼
			// 思路是将按赛季分的二维map读出来加到一个新的不按赛季分的map中
			int flag = 0;

			// ====================================
			matches = match.getAllMatches();

			Iterator<Entry<String, Map<String, MatchVO>>> iter = matches
					.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Map<String, MatchVO>> entry = (Map.Entry<String, Map<String, MatchVO>>) iter
						.next();
				if (flag == 0) {
					allMatches = entry.getValue();
				} else {
					Map<String, MatchVO> map = new HashMap<String, MatchVO>();
					map = entry.getValue();
					Iterator<Entry<String, MatchVO>> matchIterator = map
							.entrySet().iterator();
					while (matchIterator.hasNext()) {
						Map.Entry<String, MatchVO> matchEntry = (Map.Entry<String, MatchVO>) matchIterator
								.next();
						String key = matchEntry.getKey();
						MatchVO matchVO = matchEntry.getValue();
						allMatches.put(key, matchVO);
					}
				}
				flag++;
			}
		} else {
			// ====================================
			matches = match.getAllMatches();

			allMatches = matches.get(season);
		}
		if (allMatches != null) {
			Iterator<Entry<String, MatchVO>> allMatchIter = allMatches
					.entrySet().iterator();
			while (allMatchIter.hasNext()) {
				Map.Entry<String, MatchVO> matchEntry = (Map.Entry<String, MatchVO>) allMatchIter
						.next();
				MatchVO matchVO = (MatchVO) matchEntry.getValue();
				testSeason = matchVO.getSeason();
				int homeScore = matchVO.getHomeScore();
				int visitingScore = matchVO.getVisitingScore();
				String homeTeam = matchVO.getHomeTeam();
				String visitingTeam = matchVO.getVisitingTeam();

				boolean isHomeTeam = false;
				boolean isVisitingTeam = false;

				if (team.equals("NOP") || team.equals("NOH")) {
					isHomeTeam = homeTeam.equals("NOP")
							|| homeTeam.equals("NOH");
					isVisitingTeam = visitingTeam.equals("NOP")
							|| visitingTeam.equals("NOH");
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
					int perShootAttemptNum = 0;
					int perFreeThrowAttemptNum = 0;
					int perOffenReboundNum = 0;
					int perDefenReboundNum = 0;
					int perMissShoot = 0;
					int perTurnOverNum = 0;
					
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
							
							perShootAttemptNum+=recordVO.getShootAttemptNum();
							perFreeThrowAttemptNum += recordVO.getFreeThrowAttemptNum();
							perOffenReboundNum += recordVO.getOffenReboundNum();
							perDefenReboundNum += recordVO.getDefenReboundNum();
							perMissShoot +=perShootAttemptNum- recordVO.getShootHitNum();
							perTurnOverNum += recordVO.getTurnOverNum();

							
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
					offenRound += perShootAttemptNum
							+ 0.4
							* perFreeThrowAttemptNum
							- 1.07
							* (perOffenReboundNum
									/ (double) (perOffenReboundNum + dsDefenReboundNum) * (perMissShoot))
							+ 1.07 * perTurnOverNum;
					dsOffenRound += dsShootAttempNum
							+ 0.4
							* dsFreeThrowAttemptNum
							- 1.07
							* (dsOffenReboundNum
									/ (double) (dsOffenReboundNum + perDefenReboundNum) * (dsShootAttempNum - dsShootHitNum))
							+ 1.07 * dsTurnOverNum;
				}
			}

			offenEfficiency = (double) score / offenRound * 100; // 进攻效率
			defenEfficiency = (double) dsScore / dsOffenRound * 100; // 防守效率
			offenReboundEfficiency = (double) offenReboundNum
					/ (offenReboundNum + dsDefenReboundNum); // 进攻篮板效率
			defenReboundEfficiency = (double) defenReboundNum
					/ (defenReboundNum + dsOffenReboundNum); // 防守篮板效率
			stealEfficiency = (double) stealNum / dsOffenRound * 100; // 抢断效率
			assistEfficiency = (double) assistNum / offenRound * 100; // 助攻率
			
			if (season.equals("all")) {
				// 需要的是场均数据，要除以比赛场数
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

				if (testSeason.equals("12-13") && isBad) {
					abLocation = "NOH";
				}
			}
			if (season.equals("12-13") && abLocation.equals("NOP")) {
				abLocation = "NOH";
				isSeason = true;
			}

			// DecimalFormat dec = new DecimalFormat("0.000");
			// TeamVO teamVO = new TeamVO(teamName, abLocation, location,
			// conference, partition, homeCourt, setUpTime, matchesNum,
			// Double.parseDouble(dec.format(shootHitNum)),
			// Double.parseDouble(dec.format(shootAttemptNum)),
			// Double.parseDouble(dec.format(threeHitNum)),
			// Double.parseDouble(dec.format(threeAttemptNum)),
			// Double.parseDouble(dec.format(freeThrowHitNum)),
			// Double.parseDouble(dec.format(freeThrowAttemptNum)),
			// Double.parseDouble(dec.format(offenReboundNum)),
			// Double.parseDouble(dec.format(defenReboundNum)),
			// Double.parseDouble(dec.format(reboundNum)),
			// Double.parseDouble(dec.format(assistNum)),
			// Double.parseDouble(dec.format(stealNum)),
			// Double.parseDouble(dec.format(blockNum)),
			// Double.parseDouble(dec.format(turnOverNum)),
			// Double.parseDouble(dec.format(foulNum)),
			// Double.parseDouble(dec.format(score)),
			// Double.parseDouble(dec.format(shootHitRate)),
			// Double.parseDouble(dec.format(threeHitRate)),
			// Double.parseDouble(dec.format(freeThrowHitRate)),
			// Double.parseDouble(dec.format(winRate)),
			// Double.parseDouble(dec.format(offenRound)),
			// Double.parseDouble(dec.format(offenEfficiency)),
			// Double.parseDouble(dec.format(defenEfficiency)),
			// Double.parseDouble(dec.format(offenReboundEfficiency)),
			// Double.parseDouble(dec.format(defenReboundEfficiency)),
			// Double.parseDouble(dec.format(stealEfficiency)),
			// Double.parseDouble(dec.format(assistEfficiency)));
			TeamVO teamVO = new TeamVO(teamName, abLocation, location,
					conference, partition, homeCourt, setUpTime, matchesNum,
					shootHitNum, shootAttemptNum, threeHitNum, threeAttemptNum,
					freeThrowHitNum, freeThrowAttemptNum, offenReboundNum,
					defenReboundNum, reboundNum, assistNum, stealNum, blockNum,
					turnOverNum, foulNum, score, shootHitRate, threeHitRate,
					freeThrowHitRate, winRate, offenRound, offenEfficiency,
					defenEfficiency, offenReboundEfficiency,
					defenReboundEfficiency, stealEfficiency, assistEfficiency);
			return teamVO;
		} else {
			return null;
		}

	}

}

