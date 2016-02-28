package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;

import vo.MatchVO;
import vo.RecordVO;
import vo.TeamVO;
import bl.DataSourse;
import bl.match.OldMatch;
import blservice.TeamBLService;

public class OldTeam implements TeamBLService {
	private ArrayList<TeamVO> teamsBaseInfo = new ArrayList<TeamVO>();
	private ArrayList<MatchVO> matches = new ArrayList<MatchVO>();
	private ArrayList<TeamVO> teamAverageInfo = new ArrayList<TeamVO>();

	// 删去了readFromMatchFile和getMatches两个私有方法，改为创建一个match类，从中得到比赛信息
	public OldTeam() {
		// TODO 自动生成的构造函数存根
		teamsBaseInfo = getTeams();
		OldMatch match = new OldMatch();
		matches = match.getMatchData("全部", "全部", "全部", "全部");
		teamAverageInfo = calculateTeamAverageInfo();
	}

	public static void main(String[] args) {
		OldTeam team = new OldTeam();
		String season = "13-14";
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		// ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		result = team.getTeamSeasonInfo(season);
		// result = team.getTeamAverageInfo();
		// result = team.getSeasonHotTeam(season, "score");
		// result=team.getTeamSeasonInfo(season);
		// result = team.getRecentMatches("ATL");
		// result=team.getMatches();
		System.out.println(result.size());

		for (TeamVO vo : result) {
			System.out.println(vo.getAbLocation() + " " + vo.getScore());
			System.out.println(vo.getAbLocation());
			System.out.println("winRate：" + vo.getWinRate());
			System.out.println("shootHitNum：" + vo.getShootHitNum());
			System.out.println("shootAttemptNum：" + vo.getShootAttemptNum());
			System.out.println("threeHitNum：" + vo.getThreeHitNum());
			System.out.println("threeAttemptNum：" + vo.getThreeAttemptNum());
			System.out.println("freeThrowHitNum：" + vo.getFreeThrowHitNum());
			System.out.println("freeThrowAttemptNum："
					+ vo.getFreeThrowAttemptNum());
			System.out.println("offenReboundNum：" + vo.getOffenReboundNum());
			System.out.println("defenReboundNum：" + vo.getDefenReboundNum());
			System.out.println("reboundNum：" + vo.getReboundNum());
			System.out.println("assistNum：" + vo.getAssistNum());
			System.out.println("stealNum：" + vo.getStealNum());
			System.out.println("blockNum：" + vo.getBlockNum());
			System.out.println("turnOverNum：" + vo.getTurnOverNum());
			System.out.println("foulNum：" + vo.getFoulNum());
			System.out.println("score：" + vo.getScore());
			System.out.println("shootHitRate:" + vo.getShootHitRate());
			System.out.println("threeHitRate:" + vo.getThreeHitRate());
			System.out.println("freeThrowHitRate:" + vo.getFreeThrowHitRate());
			System.out.println("offenRound:" + vo.getOffenRound());
			System.out.println("offenEfficiency:" + vo.getOffenEfficiency());
			System.out.println("defenEfficiency:" + vo.getDefenEfficiency());
			System.out.println("offenReboundEfficiency:"
					+ vo.getOffenReboundEfficiency());
			System.out.println("defenReboundEfficiency:"
					+ vo.getDefenReboundEfficiency());
			System.out.println("stealEfficiency:" + vo.getStealEfficiency());
			System.out.println("assistRate:" + vo.getAssistRate());
			System.out.println("----------------------------------");

		}

		// for (MatchVO vo : result) {
		// System.out.println(vo.getVisitingScore() + ":" + vo.getHomeScore());
		// }

	}

	/**
	 * 从文件中读取球队信息
	 * 
	 * @param fileName
	 *            存储球队信息的文件名
	 * @return
	 */
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

	/**
	 * 解析球队信息，转换成teamvo列表，初始化全局变量teamsBaseInfo
	 * 
	 * @return
	 */
	private ArrayList<TeamVO> getTeams() {
		// TODO 自动生成的方法存根
		String name;
		String location;
		String abLocation;
		String conference;
		String partition;
		String homeCourt;
		int setUpTime;

		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
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
			teams.add(team);
		}
		return teams;
	}

	/**
	 * 计算球队所有比赛的平均数据，初始化全局变量teamAverageInfo
	 * 
	 * @return
	 */
	private ArrayList<TeamVO> calculateTeamAverageInfo() {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		teams = getTeamBaseInfo();
		for (TeamVO vo : teams) {
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
			for (MatchVO matchVO : matches) {
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
			result.add(teamVO);
		}

		return result;
	}

	/**
	 * 从数据库中获得球队列表teams
	 * 
	 * @return 球队最基本信息的列表，其他属性值为空，未进行初始化
	 */
	public ArrayList<TeamVO> getTeamBaseInfo() {
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
	public ArrayList<TeamVO> getTeamSeasonInfo(String season) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		teams = getTeamBaseInfo();
		for (TeamVO vo : teams) {
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
			for (MatchVO matchVO : matches) {
				if (matchVO.getSeason().equals(season)) {
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
								shootAttemptNum += recordVO
										.getShootAttemptNum(); // 投篮出手次数
								threeHitNum += recordVO.getThreeHitNum(); // 三分命中数
								threeAttemptNum += recordVO
										.getThreeAttemptNum(); // 三分出手数
								freeThrowHitNum += recordVO
										.getFreeThrowHitNum(); // 罚球命中数
								freeThrowAttemptNum += recordVO
										.getFreeThrowAttemptNum(); // 罚球出手数
								offenReboundNum += recordVO
										.getOffenReboundNum(); // 进攻篮板数
								defenReboundNum += recordVO
										.getDefenReboundNum(); // 防守篮板数
								reboundNum += recordVO.getReboundNum();// 篮板数
								assistNum += recordVO.getAssistNum();// 助攻数
								stealNum += recordVO.getStealNum();// 抢断数
								blockNum += recordVO.getBlockNum();// 盖帽数
								turnOverNum += recordVO.getTurnOverNum();// 失误数
								foulNum += recordVO.getFoulNum();// 犯规数
							} else {
								// 计算对手的
								dsShootHitNum += recordVO.getShootHitNum();
								dsShootAttempNum += recordVO
										.getShootAttemptNum();
								dsFreeThrowAttemptNum += recordVO
										.getFreeThrowAttemptNum();
								dsTurnOverNum += recordVO.getTurnOverNum();
								dsOffenReboundNum += recordVO
										.getOffenReboundNum();
								dsDefenReboundNum += recordVO
										.getDefenReboundNum();
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
						stealEfficiency = (double) stealNum / dsOffenRound
								* 100; // 抢断效率
						assistEfficiency = (double) assistNum / offenRound
								* 100; // 助攻率
					}
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
			result.add(teamVO);
		}

		return result;
	}

	/**
	 * 得到球队的该场均的技术统计数据
	 * 
	 * @return 球队场均统计数据，VO的所有初始值均进行了初始化
	 * 
	 */
	public ArrayList<TeamVO> getTeamAverageInfo() {
		// TODO 自动生成的方法存根
		return teamAverageInfo;
	}

	/**
	 * 模糊查询球队的基础信息，球队名可以是名称，也可以是缩写，大小写均可
	 * 
	 * @param name
	 *            球队名称
	 * @return 符合模糊查询条件的球队对象的列表，球队对象只进行了基础信息的初始化与赋值
	 */
	public ArrayList<TeamVO> getTeamBaseInfo(String name) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		for (TeamVO teamVO : teamsBaseInfo) {
			String teamName = teamVO.getTeamName().toLowerCase();
			String abLocation = teamVO.getAbLocation().toLowerCase();
			name = name.toLowerCase();
			if (teamName.contains(name) || abLocation.contains(name)) {
				result.add(teamVO);
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
	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String name) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		teams = getTeamSeasonInfo(season);
		for (TeamVO vo : teams) {
			String teamName = vo.getTeamName().toLowerCase();
			String abLocation = vo.getAbLocation().toLowerCase();
			name = name.toLowerCase();
			if (teamName.contains(name) || abLocation.contains(name)) {
				result.add(vo);
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
	public ArrayList<TeamVO> getTeamAverageInfo(String name) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		teams = getTeamAverageInfo();
		for (TeamVO vo : teams) {
			String teamName = vo.getTeamName().toLowerCase();
			String abLocation = vo.getAbLocation().toLowerCase();
			name = name.toLowerCase();
			if (teamName.contains(name) || abLocation.contains(name)) {
				result.add(vo);
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
	public ArrayList<TeamVO> getOrderedTeamsBySeason(String season,
			String condition, String order, int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 30;
		}
		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		teams = getTeamSeasonInfo(season);
		// 未明确写明排序方式时默认是升序
		if (order == null || order.equals("null")) {
			order = "asc";
		}
		Collections.sort(teams, new SequenceOfTeam(condition, order));
		if (num == 30) {
			return teams;
		} else {
			ArrayList<TeamVO> result = new ArrayList<TeamVO>();
			for (int i = 0; i < num; i++) {
				result.add(teams.get(i));
			}
			return result;
		}

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
	public ArrayList<TeamVO> getOrderedTeamsByAverage(String condition,
			String order, int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 30;
		}
		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		teams = teamAverageInfo;
		// 未明确写明排序方式时默认是升序
		if (order == null || order.equals("null")) {
			order = "asc";
		}
		Collections.sort(teams, new SequenceOfTeam(condition, order));
		if (num == 30) {
			return teams;
		} else {
			ArrayList<TeamVO> result = new ArrayList<TeamVO>();
			for (int i = 0; i < num; i++) {
				result.add(teams.get(i));
			}
			return result;
		}
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
		ImageIcon imageIcon = new ImageIcon("src/data/teamsPng/" + name
				+ ".png");
		return imageIcon;
	}

	/**
	 * 根据球队名称查找该球队近期比赛
	 * 
	 * @param teamName
	 *            球队缩写 完全匹配
	 * @return 近期五场比赛的列表
	 */
	public ArrayList<MatchVO> getRecentMatches(String teamName) {
		// TODO 自动生成的方法存根
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		result = getRecentMatchesAll(teamName, 5);
		return result;
	}

	/**
	 * 根据球队名称查找其过往比赛
	 * 
	 * @param teamName
	 *            球队缩写 完全匹配
	 * @return 过往比赛列表
	 */
	public ArrayList<MatchVO> getMatches(String teamName) {
		// TODO 自动生成的方法存根
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		result = getRecentMatchesAll(teamName, -1);
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
	private ArrayList<MatchVO> getRecentMatchesAll(String teamName, int num) {
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		int count = 0;
		for (MatchVO matchVO : matches) {
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
	public ArrayList<TeamVO> getSeasonHotTeam(String season, String column,
			int num) {
		// TODO 自动生成的方法存根
		if (num < 0) {
			num = 5;
		}
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		ArrayList<TeamVO> teamSeasonInfo = new ArrayList<TeamVO>();
		teamSeasonInfo = getTeamSeasonInfo(season);
		Collections.sort(teamSeasonInfo, new SequenceOfTeam(column, "desc"));
		for (int i = 0; i < num; i++) {
			TeamVO vo = teamSeasonInfo.get(i);
			result.add(vo);
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
