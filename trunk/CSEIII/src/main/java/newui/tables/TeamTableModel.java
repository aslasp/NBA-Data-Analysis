package newui.tables;

import java.util.ArrayList;

import newui.MyUIDataFormater;
import newui.Service;
import vo.TeamVO;
import bl.Match;
import blService.TeamBLService;

public class TeamTableModel extends MyTableModel {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	TeamBLService team = Service.team;
	ArrayList<ArrayList<Object>> content = new ArrayList<ArrayList<Object>>();
	public int headmodel;
	static String[] head;
	// static String[] head = { "球队名称", "简称", "比赛场数", "投篮命中数", "投篮出手次数",
	// "投篮命中率",
	// "三分命中数", "三分出手数", "三分命中率", "罚球命中数", "罚球出手数", "罚球命中率", "进攻篮板数",
	// "防守篮板数", "篮板数", "进攻篮板效率", "防守篮板效率", "进攻回合", "进攻效率", "防守效率", "助攻数",
	// "助攻率", "抢断数", "抢断效率", "盖帽数", "失误数", "犯规数", "比赛得分", "胜率" };

	String[] headbase = { "球队名称", "简称", "比赛场数", "投篮命%", "三分%", "罚球%",
			"进攻篮板数", "防守篮板数", "篮板数", "助攻数", "抢断数", "盖帽数", "失误数", "犯规数", "比赛得分" };
	String[] headhigh = { "球队名称", "简称", "进攻篮板效率%", "防守篮板效率%", "进攻回合", "进攻效率",
			"防守效率", "助攻率", "抢断效率", "胜率%" };

	public TeamTableModel(int model) {
		if (model == 0) {
			head = headbase;
			headmodel = 0;
		} else {
			head = headhigh;
			headmodel = 1;
		}

	}

	@Override
	public Class getColumnClass(int c) {
		return content.get(0).get(c).getClass();
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return content.size();
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return head.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return content.get(rowIndex).get(columnIndex);
	}

	public String getColumnName(int col) {
		return head[col];
	}

	public static String[] getHead() {
		return head;
	}

	// 切换表头
	public void setHead(int model) {
		// model=0基础 model=1高阶
		if (model == 0)
			head = headbase;
		else
			head = headhigh;
		headmodel = model;
	}

	public void Refresh(String season, String seasonType, String model) {
		ArrayList<TeamVO> teamvo;
		if (model.equals("赛季")){
			teamvo = team.getTeamSeasonInfo(season, Match.changeTypeCHToEN(seasonType));
		}
		else {
			teamvo = team.getTeamAverageInfo(season, Match.changeTypeCHToEN(seasonType));
		}
		if (teamvo != null && teamvo.size() != 0) {
			// refreshContent(teamvo);
			if (headmodel == 0)
				refreshBase(teamvo);
			else {
				refreshHigh(teamvo);
			}
		}
		/*
		 * {"球队名称","比赛场数","投篮命中数","投篮出手次数","投篮命中率","三分命中数","三分出手数",
		 * "三分命中率","罚球命中数","罚球出手数","罚球命中率","进攻篮板数","防守篮板数","篮板数","篮板效率","进攻回合",
		 * "进攻效率","防守效率","助攻数","助攻率","抢断数","抢断效率","盖帽数","失误数","犯规数","比赛得分","胜率"
		 * 
		 * } }
		 */

	}

	// public void refreshContent(ArrayList<TeamVO> result) {
	// content.clear();
	// for (TeamVO vo : result) {
	// ArrayList<Object> line = new ArrayList<Object>();
	// line.add(vo.getTeamName());
	// line.add(vo.getAbLocation());
	// line.add(vo.getMatchesNum());
	// line.add(vo.getShootHitNum());
	// line.add(vo.getShootAttemptNum());
	// line.add(vo.getShootHitRate());
	// line.add(vo.getThreeHitNum());
	// line.add(vo.getThreeAttemptNum());
	// line.add(vo.getThreeHitRate());
	// line.add(vo.getFreeThrowHitNum());
	// line.add(vo.getFreeThrowAttemptNum());
	// line.add(vo.getFreeThrowHitRate());
	// line.add(vo.getOffenReboundNum());
	// line.add(vo.getDefenReboundNum());
	// line.add(vo.getReboundNum());
	// line.add(vo.getOffenReboundEfficiency());
	// line.add(vo.getDefenReboundEfficiency());
	// line.add(vo.getOffenRound());
	// line.add(vo.getOffenEfficiency());
	// line.add(vo.getDefenEfficiency());
	// line.add(vo.getAssistNum());
	// line.add(vo.getAssistRate());
	// line.add(vo.getStealNum());
	// line.add(vo.getStealEfficiency());
	// line.add(vo.getBlockNum());
	// line.add(vo.getTurnOverNum());
	// line.add(vo.getFoulNum());
	// line.add(vo.getScore());
	// line.add(vo.getWinRate());
	// content.add(line);
	// }
	// }

	// "球队名称", "简称", "比赛场数", "投篮命中率", "三分命中率", "罚球命中率",
	// "进攻篮板数", "防守篮板数", "篮板数", "助攻数", "抢断数", "盖帽数", "失误数", "犯规数", "比赛得分"
	public void refreshBase(ArrayList<TeamVO> result) {
		content.clear();
		for (TeamVO vo : result) {
			ArrayList<Object> line = new ArrayList<Object>();
			line.add(vo.getTeamName());
			line.add(vo.getAbLocation());
			line.add(vo.getMatchesNum());
			line.add(MyUIDataFormater.formatTo1(vo.getShootHitRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getThreeHitRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getFreeThrowHitRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getOffenReboundNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getDefenReboundNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getReboundNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getAssistNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getStealNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getBlockNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getTurnOverNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getFoulNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getScore()));
			content.add(line);
		}
	}

	// "球队名称", "简称", "进攻篮板效率", "防守篮板效率", "进攻回合", "进攻效率",
	// "防守效率", "助攻率", "抢断效率", "胜率"
	public void refreshHigh(ArrayList<TeamVO> result) {
		content.clear();
		for (TeamVO vo : result) {
			ArrayList<Object> line = new ArrayList<Object>();
			line.add(vo.getTeamName());
			line.add(vo.getAbLocation());
			line.add(MyUIDataFormater.formatTo1(vo.getOffenReboundEfficiency()*100));
			line.add(MyUIDataFormater.formatTo1(vo.getDefenReboundEfficiency()*100));
			line.add(MyUIDataFormater.formatTo1(vo.getOffenRound()));
			line.add(MyUIDataFormater.formatTo1(vo.getOffenEfficiency()));
			line.add(MyUIDataFormater.formatTo1(vo.getDefenEfficiency()));
			line.add(MyUIDataFormater.formatTo1(vo.getAssistRate()));
			line.add(MyUIDataFormater.formatTo1(vo.getStealEfficiency()));
			line.add(MyUIDataFormater.formatTo1(vo.getWinRate() * 100));
			content.add(line);
		}
	}

	public void SearchRefresh(Object vv) {
		ArrayList<TeamVO> v = (ArrayList<TeamVO>) vv;
		if (v != null && v.size() != 0) {
			if (headmodel == 0)
				refreshBase(v);
			else
				refreshHigh(v);
		} else {
			content.clear();
		}
	}
	
	public int getHeadModel(){
		return headmodel;
	}
}
