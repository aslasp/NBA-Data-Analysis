package bl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import blService.PlayerBLService;
import data.PlayerData;
import dataservice.PlayerDataService;
import vo.MatchVO;
import vo.PlayerVO;
import vo.TeamVO;

public class Player implements PlayerBLService{

	Map<String, PlayerVO> playersActive = new HashMap<String, PlayerVO>(32);
	Map<String, PlayerVO> playersHistoric = new HashMap<String, PlayerVO>(256);
	Map<String, TeamVO> teams;
	Map<Integer, MatchVO> matches = new HashMap<Integer, MatchVO>(1024);
	ArrayList<MatchVO> allMatches;
	ArrayList<PlayerVO> playersAveragePreceding;
	ArrayList<PlayerVO> playersAverageRegular;
	ArrayList<PlayerVO> playersAveragePlayoff;
	boolean isCalculateAveragePreceding=false;
	boolean isCalculateAverageRegular=false;
	boolean isCalculateAveragePlayoff=false;
	boolean isGetAllMatches=false;
	PlayerDataService player;
	
	private static String[] player_CH = new String[] { "全部", "前锋", "中锋",
		"后卫","前锋-中锋","中锋-后卫","前锋-后卫", "得分", "篮板数", "助攻数", "得分/篮板/助攻(1:1:1)", "得分/篮板/助攻",
		"投篮命中率", "盖帽数", "抢断数", "罚球命中率", "犯规数", "失误数",
		"在场时间", "效率", "两双", "西部球队", "东部球队", 
		 "真实命中率", "GmSc效率值", "投篮效率", "篮板率", "进攻篮板数",
		 "防守篮板数", "进攻篮板率", "防守篮板率", "助攻率", "抢断率", 
		 "盖帽率", "失误率", "使用率"};
    private static String[] player_EN = new String[] { "all", "F", "C",
		"G", "F-C","C-G","F-G","score", "reboundNum", "assistNum", "score_rebound_assist", "score_rebound_assist",
		"shootHitRate", "blockNum", "stealNum", "freeThrowHitRate", "foulNum", "turnOverNum",
		"presentTime", "efficiency", "doubleDoubleNum", "W", "E",
		"trueHitRate","GmScEfficiencyValue","shootEfficiency","reboundRate","offenReboundNum",
		"defenReboundNum","offenReboundRate","defenReboundRate","assistRate","stealRate",
		"blockRate","turnOverRate","usageRate"};
    
    public Player(){
    	player=new PlayerData();
    }
    
    
    public ArrayList<PlayerVO> getPlayerActiveBaseInfo(){
    	return player.getPlayerActiveBaseInfo();
    }
    
    public ArrayList<PlayerVO> getPlayerHistoricBaseInfo(){
    	return player.getPlayerHistoricBaseInfo();
    }
    
    public ArrayList<PlayerVO> getPlayerBaseInfo(String name){
    	return player.getPlayerBaseInfoForVague(name);
    }
    
    public ArrayList<PlayerVO> getPlayerActiveBaseInfoForVague(String name){
    	return player.getPlayerActiveBaseInfoForVague(name);
    }
    
	
	public ArrayList<PlayerVO> getPlayerSeasonInfo(String season,String type){    
		return player.getPlayerSeasonInfo(season, Match.changeTypeCHToEN(type));
	}
	
	public ArrayList<PlayerVO> getPlayerSeasonInfo(String season,String type,String name){
		return player.getPlayerSeasonInfo(season, Match.changeTypeCHToEN(type), name);
	}

	
	public ArrayList<PlayerVO> getPlayerAverageInfo(String season,String type) {
        return player.getPlayerAverageInfo(season, Match.changeTypeCHToEN(type));
	}
	
	public ArrayList<PlayerVO> getPlayerAverageInfo(String season,String type,String name){
		return player.getPlayerAverageInfo(season, Match.changeTypeCHToEN(type), name);
	}
	
	public ArrayList<PlayerVO> getPlayerRecentAverageInfo(String name){
		return player.getPlayerRecentAverageInfo(name);
	}
	
	public double getRankInNBA(String name,String condition){
		return player.getRankInNBA(name, changePlayerCHToEN(condition));
	}

	public ArrayList<PlayerVO> getOrderedPlayersBySeason(String season,String type,
			String condition, String order, int num) {
		return player.getOrderedPlayersBySeason(season, Match.changeTypeCHToEN(type), changePlayerCHToEN(condition), order, num);
	}

	public ArrayList<PlayerVO> getOrderedPlayersByAverage(String season,String type,String condition,
			String order, int num) {
		return player.getOrderedPlayersByAverage(season, Match.changeTypeCHToEN(type), changePlayerCHToEN(condition), order, num);
	}

	public ArrayList<PlayerVO> selectPlayersBySeason(String season,String type,
			String position, String union, String column,
			String order, int num) {
		return player.selectPlayersBySeason(season, Match.changeTypeCHToEN(type), changePlayerCHToEN(position), changePlayerCHToEN(union), changePlayerCHToEN(column), order, num);
	}

	public ArrayList<PlayerVO> selectPlayersByAverage(String season,String type,String position,
			String union, String column, String order, int num) {
		return player.selectPlayersByAverage(season,Match.changeTypeCHToEN(type), changePlayerCHToEN(position), changePlayerCHToEN(union), changePlayerCHToEN(column), order, num);
	}

	public static ImageIcon getPlayerPortraitImage(String name) {
		ImageIcon imageIcon;
		File file=new File("src/data/players/portrait/" + name+ ".png");    
		if(!file.exists()){
			imageIcon=new ImageIcon("src/data/players/portrait/None.png");
		}
		else{
			imageIcon = new ImageIcon("src/data/players/portrait/" + name
				+ ".png");
		}
		return imageIcon;
	}

	public static ImageIcon getPlayerActionImage(String name) {
		ImageIcon imageIcon = new ImageIcon("image/player/action/" + name
				+ ".png");
		return imageIcon;
	}

	public ArrayList<PlayerVO> getDayHotPlayer(String column, int num) {
		return player.getDayHotPlayer(column, num);
	}

	public ArrayList<PlayerVO> getSeasonHotPlayer(String season,String type,String column,
			int num) {

		return player.getSeasonHotPlayer(season, Match.changeTypeCHToEN(type), column, num);
	}

	public ArrayList<PlayerVO> getBestImprovedPlayer(String season,String type,String column, int num) {
		return player.getBestImprovedPlayer(season, Match.changeTypeCHToEN(type), column, num);
	}

	public ArrayList<PlayerVO> getPlayersByInitialName(char character) {
		return player.getPlayersByInitialName(character);
	}

	public ArrayList<MatchVO> getRecentMatches(String playerName, int num) {
		return player.getRecentMatches(playerName, num);
	}

	public ArrayList<MatchVO> getMatches(String playerName) {
		return player.getMatches(playerName);
	}
	
	public ArrayList<MatchVO> getMatches(String season,String type,String playerName, int num){
		return player.getMatches(season, Match.changeTypeCHToEN(type), playerName, num);
	}

	public ArrayList<PlayerVO> getPlayersByTeam(String teamAbLocation) {
		return player.getPlayersByTeam(teamAbLocation);
	}
	
	public ArrayList<double[]> singleElementVarianceAnalysis(){
		return player.singleElementVarianceAnalysis();
	}
	
	public ArrayList<String> singleElementVarianceAnalysis(String name){
		return player.singleElementVarianceAnalysis(name);
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
	
	/**
	 * 将球员数据中文名转为相应的英文缩写
	 * 
	 * @param 球员中文名
	 * @return 英文缩写
	 */
	public static String changePlayerCHToEN(String CH) {
		int index = -1;
		for (int i = 0; i < player_CH.length; i++) {
			if (player_CH[i].equals(CH)) {
				index = i;
			}
		}
		if (index != -1) {
			return player_EN[index];
		} else {
			return CH;
		}
	}
	
	/**
	 * 将球员数据英文缩写转为相应的中文名
	 * 
	 * @param EN
	 *            球员数据英文名
	 * @return 中文名
	 */
	public static String changePlayerENToCH(String EN) {
		int index = -1;
		for (int i = 0; i < player_CH.length; i++) {
			if (player_EN[i].equals(EN)) {
				index = i;
			}
		}
		if (index != -1) {
			return player_CH[index];
		} else {
			return EN;
		}
	}

	/**
//	 * 向match中增加比赛信息
//	 * 
//	 * @param newName
//	 *            动态增加的比赛的文件名
//	 */
//	private void add(ArrayList<String> newName) {
//		for (String str : newName) {
//			MatchVO vo = readMatchInfoFromFile(str);
//			getPlayerMatchInfo(vo);
//			ArrayList<RecordVO> records = vo.getRecords();
//			for (RecordVO recordVO : records) {
//				PlayerVO thisPlayer = players.get(recordVO.getPlayerName());
//				if (thisPlayer != null) {
//					calculatePlayerSeason(thisPlayer);
//					needUpdatePlayerSeason = true;
//					needUpdatePlayerAverage = true;
//					needUpdatePlayersToday = true;
//				}
//			}
//		}
//	}

	// 放在Filelist或Match里均可
//	class updateMatch extends Thread {
//		boolean stop;
//
//		public updateMatch() {
//			stop = false;
//		}
//
//		public void run() {
//			while (!stop) {
//				ArrayList<String> addPaths = matchFiles.checkMatchesChange();
//				if (addPaths != null) {
//					add(addPaths);
//					// System.out.println("我在很认真的检查呀，港荣蒸蛋糕真的好吃，字打错了灭");
//					System.out.println("有新文件啦！！");
//					ArrayList<PlayerVO> oo = getPlayerAverageInfo();
//					System.out.println(oo.get(405).getName() + " "
//							+ oo.get(405).getPlayedGames() + " "
//							+ oo.get(405).getEfficiency());
//				}
//				try {
//					this.sleep(2000);// 话说 能不能不睡
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//		public void startThread() {
//			this.start();
//		}
//
//		public void stopThead() {
//			this.stop = true;
//		}
//	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Player player = new Player();
		// result = team.getTeamBaseInfo();
		// result = team.getTeamAverageInfo("all");
		// result = team.getTeamAverageInfo("Playoff", "NOP");
		// result=team.getTeamSeasonInfo("12-13", "Team");
		// result=team.getTeamSeasonInfo("12-13", "Playoff", "NOP");
		// result=team.getOrderedTeamsByAverage( "all", "score", "desc", 5);
		
//		ArrayList<PlayerVO> players=player.getPlayerBaseInfo();
//		ArrayList<PlayerVO> players=player.getPlayerSeasonInfo("12-13", "Playoff");
//		ArrayList<PlayerVO> players=player.getPlayerAverageInfo("Team");
//		ArrayList<PlayerVO> players=player.selectPlayersBySeason("12-13", "Team", "all", "all", "score", "desc", 5);
//		ArrayList<PlayerVO> players=player.selectPlayersByAverage("Team", "all", "all", "score", "desc", 5);
//		ArrayList<PlayerVO> players=player.getOrderedPlayersBySeason("12-13", "Team", "score", "asc", 5);
		ArrayList<PlayerVO> players=player.getOrderedPlayersByAverage("13-14","Team", "score", "desc", 5);
		long end1= System.currentTimeMillis();
		System.out.println("运行时间：" + (end1 - start) + "毫秒");// 应该是end - start
		ArrayList<PlayerVO> players2=player.getOrderedPlayersByAverage("14-15","Preseason", "score", "desc", 5);
		long end2 = System.currentTimeMillis();
		System.out.println("运行时间：" + (end2 - end1) + "毫秒");// 应该是end - start
		ArrayList<PlayerVO> players3=player.getOrderedPlayersByAverage("14-15","Playoff", "score", "desc", 5);
		long end3 = System.currentTimeMillis();
		System.out.println("运行时间：" + (end3 - end2) + "毫秒");// 应该是end - start
		ArrayList<PlayerVO> player4=player.getOrderedPlayersByAverage("14-15", "Team", "score", "desc", 5);
		long end4 = System.currentTimeMillis();
		System.out.println("运行时间：" + (end4 - end3) + "毫秒");// 应该是end - start
		for(PlayerVO vv:players){
			System.out.println(vv.getName());
			System.out.println(vv.getScore());
		}
		
	//	PlayerVO vo = player.getPlayerAverageInfo("Kevin Durant");
	//	PlayerVO vo = player.getPlayerAverageInfo("LeBron James");
//		PlayerVO vo = player.getPlayerSeasonInfo("13-14", "Kevin Durant");
//		 System.out.println("name: "+vo.getName());
//		 System.out.println("owingTeam: "+vo.getOwingTeam());
//		 System.out.println("league: "+vo.getLeague());
//		 System.out.println("playedGames: "+vo.getPlayedGames());
//		 System.out.println("gameStartingNum: "+vo.getGameStartingNum());
//		 System.out.println("reboundNum: "+vo.getReboundNum());
//		 System.out.println("assistNum: "+vo.getAssistNum());
//		 System.out.println("presentTime: "+vo.getPresentTime());
//		 System.out.println("shootHitRate: "+vo.getShootHitRate());
//		 System.out.println("threeHitRate: "+vo.getThreeHitRate());
//		 System.out.println("freeThrowHitRate: "+vo.getFreeThrowHitRate());
//		 System.out.println("offenReboundNum: "+vo.getOffenReboundNum());
//		 System.out.println("defenReboundNum: "+vo.getDefenReboundNum());
//		 System.out.println("stealnum:   "+vo.getStealNum());
//		 System.out.println("blockNum: "+vo.getBlockNum());
//		 System.out.println("foulNum: "+vo.getFoulNum());
//		 System.out.println("turnOverNum: "+vo.getTurnOverNum());
//		 System.out.println("score: "+vo.getScore());
//		 System.out.println("efficiency:"+vo.getEfficiency());
//		 System.out.println("recentFiveMatchesScoreUpRate: "+vo.getRecentFiveMatchesScoreUpRate());
//		 System.out.println("recentFiveMatchesReboundUpRate: "+vo.getRecentFiveMatchesReboundUpRate());
//		 System.out.println("recentFiveMatchesAssistRate: "+vo.getRecentFiveMatchesAssistUpRate());
//		 System.out.println("gmscEfficiency: "+vo.getGmScEfficiencyValue());
//		 System.out.println("truehitrate:  "+vo.getTrueHitRate());
//		 System.out.println("shootHitEfficiency: "+vo.getShootHitEfficiency());
//		 System.out.println("reboundRate: "+vo.getReboundRate());
//		 System.out.println("offenReboundRate: "+vo.getOffenReboundRate());
//		 System.out.println("defenReboundRate: "+vo.getDefenReboundRate());
//		 System.out.println("assistRate: "+vo.getAssistRate());
//		 System.out.println("stealRate: "+vo.getStealRate());
//		 System.out.println("blockRate: "+vo.getBlockRate());
//		 System.out.println("turnOverRate: "+vo.getTurnOverRate());
//		 System.out.println("usageRate: "+vo.getUsageRate());
//		 System.out.println("score_rebound_assist: "+vo.getScore_rebound_assist());
//		 System.out.println("doubleDoubleNum: "+vo.getDoubleDoubleNum());

		long end = System.currentTimeMillis();
		System.out.println("运行时间：" + (end - start) + "毫秒");// 应该是end - start
	}

}
