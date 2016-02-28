package bl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import vo.MatchVO;
import vo.TeamVO;
import blService.MatchBLService;
import blService.TeamBLService;
import data.TeamData;
import dataservice.TeamDataService;

public class Team implements TeamBLService {
	private TeamDataService teamData;
	private MatchBLService match;

	private static String[] teamsName_CH = new String[] { "全部", "太阳", "马刺",
			"国王", "火箭", "湖人", "掘金", "森林狼", "小牛", "快船", "爵士", "勇士", "灰熊", "雷霆",
			"鹈鹕", "开拓者", "凯尔特人", "篮网", "尼克斯", "76人", "猛龙", "公牛", "骑士", "活塞",
			"步行者", "雄鹿", "老鹰", "黄蜂", "热火", "魔术", "奇才" };
	private static String[] teamsName_EN = new String[] { "all", "PHX", "SAS",
			"SAC", "HOU", "LAL", "DEN", "MIN", "DAL", "LAC", "UTA", "GSW",
			"MEM", "OKC", "NOP", "POR", "BOS", "BKN", "NYK", "PHI", "TOR",
			"CHI", "CLE", "DET", "IND", "MIL", "ATL", "CHA", "MIA", "ORL",
			"WAS" };

	public static void main(String[] args) {
		String season="12-13";
		String type="Team";
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		TeamBLService team = new Team();
		// result = team.getTeamBaseInfo();
		long start = System.currentTimeMillis();
//		result = team.getTeamAverageInfo(season,"Playoff");
//		System.out.println("结果大小：" + result.size());
//		result = team.getTeamAverageInfo(season,type);
//		System.out.println("结果大小：" + result.size());
		
//		 result = team.getTeamAverageInfo(season,"Team", "NOP");
//		 result=team.getTeamSeasonInfo("12-13", "Team");
//		 result = team.getTeamSeasonInfo("10-11", "Team", "NJN");
//		 result=team.getOrderedTeamsByAverage(season,type, "score", "desc", 5);
		 result = team.getSeasonHotTeam("14-15", "Playoff", "score", 5);
//		ArrayList<MatchVO> matches=new ArrayList<MatchVO>();
//		matches=team.getRecentMatches("BOS", 82);
		long end = System.currentTimeMillis();
		System.out.println("结果大小：" + result.size());

//		for(int i=0;i<matches.size();i++){
//			System.out.println(matches.get(i).getDate());
//		}
		
		for (TeamVO vo : result) {
			 System.out.println(vo.getAbLocation() + " " + vo.getScore());
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
			System.out.println("----------------------------------");
		}

		System.out.println("执行时间：" + (end - start) + "毫秒");
	}

	public Team() {
		teamData = new TeamData();
		match = new Match();
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

		TeamDataService teamData = new TeamData();
		ArrayList<TeamVO> result = teamData.getTeamBaseInfo();
		Map<String, TeamVO> teams = new HashMap<String, TeamVO>();
		TeamVO team;

		for (TeamVO vo : result) {
			abLocation = vo.getAbLocation();
			conference = vo.getConference();// 东西部
			partition = vo.getPartition();

			team = new TeamVO(abLocation, conference, partition);
			teams.put(abLocation, team);
		}
		return teams;
	}

	public ArrayList<TeamVO> getTeamBaseInfo() {
		// TODO 自动生成的方法存根
		return teamData.getTeamBaseInfo();
	}

	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String type) {
		// TODO 自动生成的方法存根
		return teamData.getTeamSeasonInfo(season, Match.changeTypeCHToEN(type));
	}

	public ArrayList<TeamVO> getTeamAverageInfo(String season, String type) {
		// TODO 自动生成的方法存根
		return teamData.getTeamAverageInfo(season, Match.changeTypeCHToEN(type));
	}

	public ArrayList<TeamVO> getTeamBaseInfo(String name) {
		// TODO 自动生成的方法存根
		return teamData.getTeamBaseInfo(name);
	}

	public ArrayList<TeamVO> getTeamSeasonInfo(String season, String type,
			String name) {
		// TODO 自动生成的方法存根
		return teamData.getTeamSeasonInfo(season, Match.changeTypeCHToEN(type), name);
	}

	public ArrayList<TeamVO> getTeamAverageInfo(String season, String type,
			String name) {
		// TODO 自动生成的方法存根
		return teamData.getTeamAverageInfo(season, Match.changeTypeCHToEN(type), name);
	}

	public ArrayList<TeamVO> getOrderedTeamsBySeason(String season,
			String type, String condition, String order, int num) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		// 默认是升序
		if (order == null || order.equals("null")) {
			order = "asc";
		}

		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		teams = getTeamAverageInfo(season, Match.changeTypeCHToEN(type));
		Collections.sort(teams, new SequenceOfTeam(condition, order));
		if (num < 0) {
			result = teams;
		} else {
			for (int i = 0; i < num; i++) {
				TeamVO vo = teams.get(i);
				result.add(vo);
			}
		}
		return result;
	}

	public ArrayList<TeamVO> getOrderedTeamsByAverage(String season,
			String type, String condition, String order, int num) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		// 默认是升序
		if (order == null || order.equals("null")) {
			order = "asc";
		}

		ArrayList<TeamVO> teams = new ArrayList<TeamVO>();
		teams = getTeamAverageInfo(season, Match.changeTypeCHToEN(type));
		Collections.sort(teams, new SequenceOfTeam(condition, order));
		if (num < 0) {
			result = teams;
		} else {
			for (int i = 0; i < num; i++) {
				TeamVO vo = teams.get(i);
				result.add(vo);
			}
		}
		return result;
	}

	public ArrayList<MatchVO> getRecentMatches(String teamName, int num) {
		// TODO 自动生成的方法存根
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		ArrayList<MatchVO> matches = new ArrayList<MatchVO>();
		matches = getMatches(teamName);
		Collections.sort(matches, new SequenceOfMatch());
		for (int i = 0; i < num; i++) {
			MatchVO vo = matches.get(i);
			result.add(vo);
		}
		return result;
	}

	public ArrayList<MatchVO> getMatches(String teamName) {
		// TODO 自动生成的方法存根
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		result = match.getMatchesByTeam("14-15", "all", teamName);
		return result;
	}

	public ArrayList<TeamVO> getSeasonHotTeam(String season, String type,
			String column, int num) {
		// TODO 自动生成的方法存根
		return getOrderedTeamsBySeason(season, Match.changeTypeCHToEN(type), column, "desc", num);
	}

	/**
	 * 根据球队缩写得到球队队徽
	 * 
	 * @param name
	 *            球队缩写
	 * @return 该球队队徽
	 */
	public static ImageIcon getTeamImage(String name) {
		// TODO 自动生成的方法存根
		ImageIcon imageIcon = null;
		if (name.equals("NOH")) {
			imageIcon = new ImageIcon("src/data/teamsPng/" + "NOP" + ".png");
		} else if (name.equals("NJN")) {
			imageIcon = new ImageIcon("src/data/teamsPng/" + "BKN" + ".png");
		} else {
			imageIcon = new ImageIcon("src/data/teamsPng/" + name + ".png");
		}
		return imageIcon;
	}

	/**
	 * 将球队中文名转为相应的英文缩写
	 * 
	 * @param 球队中文名
	 * @return 英文缩写
	 */
	public static String changeTeamNameCHToEN(String CH) {
		int index = -1;
		for (int i = 0; i < teamsName_CH.length; i++) {
			if (teamsName_CH[i].equals(CH)) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			return teamsName_EN[index];
		} else {
			return CH;
		}
	}

	/**
	 * 将球队中文名转为相应的英文缩写
	 * 
	 * @param EN
	 *            球队英文名
	 * @return 中文名
	 */
	public static String changeTeamNameENToCH(String EN) {
		int index = -1;
		for (int i = 0; i < teamsName_CH.length; i++) {
			if (teamsName_EN[i].equals(EN)) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			return teamsName_CH[index];
		} else {
			return EN;
		}
	}

}