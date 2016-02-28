package bl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import vo.MatchVO;
import blService.MatchBLService;
import data.MatchData;
import dataservice.MatchDataService;

public class Match implements MatchBLService {
	private MatchDataService matchData;
	private ArrayList<MatchVO> allMatches = new ArrayList<MatchVO>();
	private boolean isMatchesInit = false;

	public static void main(String[] args) {
		MatchBLService match = new Match();
		// ArrayList<String> result = new ArrayList<String>();
		// result = match.getAllSeasons();
		// System.out.println(result.get(0));
		// System.out.println(match.getLastMatchType());
		match.getMatchData("all", "all", "all", "all", "all");
	}

	public Match() {
		matchData = new MatchData();
	}

	public void initAllMatches() {
		allMatches = getMatchData("all", "all", "all", "all", "all");
		isMatchesInit = true;
	}

	public ArrayList<String> getAllSeasons() {
		// TODO 自动生成的方法存根
		ArrayList<String> result = new ArrayList<String>();
		result = matchData.getAllSeasons();
		return result;
	}

	/**
	 * 根据当前日期得到当前处于哪个赛季
	 *
	 * @return 赛季 形如：14-15
	 */
	public static String getCurrentSeason() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd EEEE");// 设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间
		String[] dateDetail = date.split(" ");
		int year = Integer.parseInt(dateDetail[0]);
		int month = Integer.parseInt(dateDetail[1]);

		if (month > 6) {
			return String.valueOf(year).substring(2) + "-"
					+ String.valueOf(year + 1).substring(2);
		} else {
			return String.valueOf(year - 1).substring(2) + "-"
					+ String.valueOf(year).substring(2);
		}

	}

	// public static String getCurrentSeasonType() {
	// String result = "";
	// SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd EEEE");// 设置日期格式
	// String date = df.format(new Date());// new Date()为获取当前系统时间
	// String[] dateDetail = date.split(" ");
	// int month = Integer.parseInt(dateDetail[1]);
	// int day = Integer.parseInt(dateDetail[2]);
	// String weekDay = dateDetail[3];
	//
	// if (month > 10 || month < 5) {
	// result = "Team";
	// } else if (month == 6) {
	// result = "Playoff";
	// } else if (month == 10) {
	//
	// } else if (month == 5) {
	//
	// } else {
	//
	// }
	//
	// return result;
	// }

	public String getLastMatchType() {
		String result = "Playoff";
		ArrayList<MatchVO> matches = new ArrayList<MatchVO>();
		System.out.println("af");
		matches = getMatchData("all", "all", "all", "all", "all");
		System.out.println("asdf");
		Collections.sort(matches, new SequenceOfMatch());
		System.out.println("sdf");
		MatchVO vo = matches.get(0);
		result = vo.getType();
		return result;
	}

	public ArrayList<MatchVO> getMatchData(String season, String type,
			String date, String homeTeam, String visitingTeam) {
		// TODO 自动生成的方法存根
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		if (season.equals("all") && type.equals("all") && date.equals("all")
				&& homeTeam.equals("all") && visitingTeam.equals("all")) {
			if(isMatchesInit){
				return allMatches;
			}
		}
		result = matchData.getMatchData(season, Match.changeTypeCHToEN(type),
				date, homeTeam, visitingTeam);
		return result;
	}

	public ArrayList<MatchVO> getMatchesByTeam(String season, String type,
			String name) {
		// TODO 自动生成的方法存根
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		result = matchData.getMatchesByTeam(season,
				Match.changeTypeCHToEN(type), name);
		return result;
	}

	/**
	 * 将比赛类型的中文转为英文
	 * 
	 * @param CH
	 *            比赛类型的中文
	 * @return 对应的英文
	 */
	public static String changeTypeCHToEN(String CH) {
		String CHs[] = { "全部", "季前赛", "常规赛", "季后赛" };
		String ENs[] = { "all", "Preseason", "Team", "Playoff" };
		String EN = CH;
		for (int i = 0; i < CHs.length; i++) {
			if (CHs[i].equals(CH.trim())) {
				EN = ENs[i];
				break;
			}
		}
		return EN;
	}

}
