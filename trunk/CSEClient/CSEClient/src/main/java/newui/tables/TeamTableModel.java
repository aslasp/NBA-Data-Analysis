package newui.tables;

import java.util.ArrayList;

import vo.TeamVO;
import businesslogic.Team;
import businesslogicservice.TeamBLService;

public class TeamTableModel extends MyTableModel {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	TeamBLService team = new Team();
	ArrayList<ArrayList<Object>> content = new ArrayList<ArrayList<Object>>();
	static String[] head = { "球队名称", "比赛场数", "投篮命中数", "投篮出手次数", "投篮命中率",
			"三分命中数", "三分出手数", "三分命中率", "罚球命中数", "罚球出手数", "罚球命中率", "进攻篮板数",
			"防守篮板数", "篮板数", "进攻篮板效率","防守篮板效率", "进攻回合", "进攻效率", "防守效率", "助攻数", "助攻率",
			"抢断数", "抢断效率", "盖帽数", "失误数", "犯规数", "比赛得分", "胜率" };

	String[] example = { "金大大", "10", "10", "10", "100%", "10", "10", "100%",
			"0", "0", "100%", "1", "2", "3", "100%", "2", "100%", "100%", "1",
			"100%","100%", "1", "100%", "1", "0", "0", "100", "100%" };

	public TeamTableModel() {
//		ArrayList<Object> e = new ArrayList<Object>();
//		for (Object str : example)
//			e.add(str);
//		content.add(e);
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

	public void Refresh(String model) {
		ArrayList<TeamVO> teamvo;
		if (model.equals("赛季"))
			teamvo = team.getTeamSeasonInfo("13-14");
		else
			teamvo = team.getTeamAverageInfo("13-14");
		if (teamvo != null && teamvo.size() != 0)
			refreshContent(teamvo);

		/*
		 * {"球队名称","比赛场数","投篮命中数","投篮出手次数","投篮命中率","三分命中数","三分出手数",
		 * "三分命中率","罚球命中数","罚球出手数","罚球命中率","进攻篮板数","防守篮板数","篮板数","篮板效率","进攻回合",
		 * "进攻效率","防守效率","助攻数","助攻率","抢断数","抢断效率","盖帽数","失误数","犯规数","比赛得分","胜率"
		 * 
		 * } }
		 */

	}

	public void refreshContent(ArrayList<TeamVO> result) {

		content.clear();
		for (TeamVO vo : result) {
			ArrayList<Object> line = new ArrayList<Object>();
			line.add(vo.getTeamName());
			line.add(vo.getMatchesNum());
			line.add(vo.getShootHitNum());
			line.add(vo.getShootAttemptNum());
			line.add(vo.getShootHitRate());
			line.add(vo.getThreeHitNum());
			line.add(vo.getThreeAttemptNum());
			line.add(vo.getThreeHitRate());
			line.add(vo.getFreeThrowHitNum());
			line.add(vo.getFreeThrowAttemptNum());
			line.add(vo.getFreeThrowHitRate());
			line.add(vo.getOffenReboundNum());
			line.add(vo.getDefenReboundNum());
			line.add(vo.getReboundNum());
			line.add(vo.getOffenReboundEfficiency());
			line.add(vo.getDefenReboundEfficiency());
			line.add(vo.getOffenRound());
			line.add(vo.getOffenEfficiency());
			line.add(vo.getDefenEfficiency());
			line.add(vo.getAssistNum());
			line.add(vo.getAssistEfficiency());
			line.add(vo.getStealNum());
			line.add(vo.getStealEfficiency());
			line.add(vo.getBlockNum());
			line.add(vo.getTurnOverNum());
			line.add(vo.getFoulNum());
			line.add(vo.getScore());
			line.add(vo.getWinRate());
			content.add(line);
		}
	}
	public void SearchRefresh(Object vv){
		ArrayList<TeamVO> v=(ArrayList<TeamVO>)vv;
		if(v!=null&&v.size()!=0){
			
			refreshContent(v);
		}
		
	}
}
