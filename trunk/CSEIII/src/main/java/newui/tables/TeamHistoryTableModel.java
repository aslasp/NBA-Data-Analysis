package newui.tables;

import java.util.ArrayList;

import newui.MyUIDataFormater;
import newui.Service;
import vo.PlayerVO;
import bl.Player;
import blService.PlayerBLService;

public class TeamHistoryTableModel extends MyTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String[] head = { "球员", "出场", "首发", "时间", "投篮%", "三分%", "罚球%", "前篮板",
			"后篮板", "总篮板", "助攻", "抢断", "盖帽", "失误", "犯规", "得分" };
	PlayerBLService player;
	public ArrayList<PlayerVO> teamMember;
	String tname;
	// 最后一行有统计
	ArrayList<ArrayList<Object>> content = new ArrayList<ArrayList<Object>>();

	public TeamHistoryTableModel() {
		player = Service.player;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return content.size();
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return head.length;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		return content.get(0).get(column).getClass();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return content.get(rowIndex).get(columnIndex);
	}

	public String getColumnName(int col) {
		return head[col];
	}

	public void Refresh(String teamName) {

		teamMember = player.getPlayersByTeam(teamName);
		if (teamMember != null && teamMember.size() != 0)
			Refresh(teamMember, teamName);
		this.tname = teamName;
	}

	// 当这个球员这个赛季不在该球队效力时，直接将其从teamMember中除去
	// teamMember得到的是所有曾在该球队打过比赛的球员姓名
	public void RefreshSeason(String season, String seasonType) {
		ArrayList<PlayerVO> vo = new ArrayList<PlayerVO>();
		ArrayList<PlayerVO> teamMember1=clone(teamMember);
		for (int i = 0; i < teamMember1.size(); i++) {
			PlayerVO v = teamMember1.get(i);
			ArrayList<PlayerVO> playerVO = player.getPlayerSeasonInfo(season,
					seasonType, v.getName());
			if (playerVO == null || playerVO.size() == 0) {
				teamMember1.remove(i);
				i--;
			} else {
				vo.add(playerVO.get(0));
			}
		}
		Refresh(vo, tname);
	}

	public void RefreshAverage(String season,String seasonType) {
		ArrayList<PlayerVO> vo = new ArrayList<PlayerVO>();
		ArrayList<PlayerVO> teamMember1=clone(teamMember);
		for (int i = 0; i < teamMember1.size(); i++) {
			ArrayList<PlayerVO> playerVO = player.getPlayerAverageInfo(
					season,seasonType);
			if (playerVO == null || playerVO.size() == 0) {
				teamMember1.remove(i);
				i--;
			} else {
				vo.add(playerVO.get(i));
			}
		}
		Refresh(vo, tname);
	}

	public ArrayList<PlayerVO> clone(ArrayList<PlayerVO> teamMember){
		ArrayList<PlayerVO> result=new ArrayList<PlayerVO>();
		for(PlayerVO playerSeason:teamMember){
			int playedGames = playerSeason.getPlayedGames();
			PlayerVO newPlayer;
			newPlayer = new PlayerVO(playerSeason.getName(),
						playerSeason.getNumber(), playerSeason.getPosition(),
						playerSeason.getHeight(), playerSeason.getWeight(),
						playerSeason.getBirth(), playerSeason.getAge(),
						playerSeason.getExp(), playerSeason.getSchool(),
						playerSeason.getOwingTeam(), playerSeason.getLeague(),
						playedGames, playerSeason.getGameStartingNum(),
						playerSeason.getReboundNum() ,
						playerSeason.getAssistNum() ,
						playerSeason.getPresentTime() ,
						playerSeason.getShootHitNum(),
						playerSeason.getShootAttemptNum(),
						playerSeason.getShootHitRate(),
						playerSeason.getThreeHitNum(),
						playerSeason.getThreeAttemptNum(),
						playerSeason.getThreeHitRate(), 
						playerSeason.getFreeThrowHitNum(), 
						playerSeason.getFreeThrowAttemptNum(),
						playerSeason.getFreeThrowHitRate(),
						playerSeason.getOffenReboundNum(),
						playerSeason.getDefenReboundNum(),
						playerSeason.getStealNum(),
						playerSeason.getBlockNum(),
						playerSeason.getTurnOverNum(),
						playerSeason.getFoulNum(),
						playerSeason.getScore(),
						playerSeason.getEfficiency(),
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
						playerSeason.getUsageRate(),
						playerSeason.getScore_rebound_assist(), 
						playerSeason.getDoubleDoubleNum());
			result.add(newPlayer);
		}
		return result;
	}
	
	public void Refresh(ArrayList<PlayerVO> pp, String tname) {
		content.clear();
		// 据图 率以后的都是累加 之前的都是平均 首发为"-" 时间为"0" 不理解 为啥不平均一下呢
		ArrayList<Object> last = new ArrayList<Object>();
		int present = 0;// 出场次数
		double shootHitRate = 0;//
		double threeHitRate = 0;
		double freeHitRate = 0;// 罚球
		double offendNum = 0;// 进攻数=进攻篮板数 感觉好像是的呢
		double defendNum = 0;
		double reboundNum = 0;// 总篮板数
		double assistNum = 0;
		double stealNum = 0;
		double blockNum = 0;
		double turnOverNum = 0;
		double foulNum = 0;// 犯规数
		double score = 0;
		int n = pp.size();
		for (PlayerVO p : pp) {
			ArrayList<Object> line = new ArrayList<Object>();
			line.add(p.getName());
			line.add(p.getPlayedGames());
			present += p.getPlayedGames();
			line.add(p.getGameStartingNum());
			line.add(Player.changeSecondToTime(p.getPresentTime()));
			line.add(MyUIDataFormater.formatTo1(p.getShootHitRate()*100));
			shootHitRate += p.getShootHitRate();
			line.add(MyUIDataFormater.formatTo1(p.getThreeHitRate()*100));
			threeHitRate += p.getThreeHitRate();
			line.add(MyUIDataFormater.formatTo1(p.getFreeThrowHitRate()*100));
			freeHitRate += p.getFreeThrowHitRate();
			line.add(MyUIDataFormater.formatTo1(p.getOffenReboundNum()));
			offendNum += p.getOffenReboundNum();
			line.add(MyUIDataFormater.formatTo1(p.getDefenReboundNum()));
			defendNum += p.getDefenReboundNum();
			line.add(MyUIDataFormater.formatTo1(p.getReboundNum()));
			reboundNum += p.getReboundNum();
			line.add(MyUIDataFormater.formatTo1(p.getAssistNum()));
			assistNum += p.getAssistNum();
			line.add(MyUIDataFormater.formatTo1(p.getStealNum()));
			stealNum += p.getStealNum();
			line.add(MyUIDataFormater.formatTo1(p.getBlockNum()));
			blockNum += p.getBlockNum();
			line.add(MyUIDataFormater.formatTo1(p.getTurnOverNum()));
			turnOverNum += p.getTurnOverNum();
			line.add(MyUIDataFormater.formatTo1(p.getFoulNum()));
			foulNum += p.getFoulNum();
			line.add(MyUIDataFormater.formatTo1(p.getScore()));
			score += p.getScore();
			content.add(line);
		}

		last.add(tname + "全队");
		if (n == 0) {
			last.add("-");
			last.add("-");
			last.add("-");
			last.add(0.0);// 投篮命中率
			last.add(0.0);// 三分命中率
			last.add(0.0);// 罚球命中率
		} else {
			last.add("-");
			last.add("-");
			last.add("-");
			last.add(MyUIDataFormater.formatTo3(shootHitRate*100 / n));
			last.add(MyUIDataFormater.formatTo3(threeHitRate*100 / n));
			last.add(MyUIDataFormater.formatTo3(freeHitRate*100 / n));
		}
		last.add(MyUIDataFormater.formatTo1(offendNum));
		last.add(MyUIDataFormater.formatTo1(defendNum));
		last.add(MyUIDataFormater.formatTo1(reboundNum));
		last.add(MyUIDataFormater.formatTo1(assistNum));
		last.add(MyUIDataFormater.formatTo1(stealNum));
		last.add(MyUIDataFormater.formatTo1(blockNum));
		last.add(MyUIDataFormater.formatTo1(turnOverNum));
		last.add(MyUIDataFormater.formatTo1(foulNum));
		last.add(MyUIDataFormater.formatTo1(score));
		content.add(last);

	}

}
