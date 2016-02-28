package newui.tables;

import java.util.ArrayList;

import newui.MyUIDataFormater;
import newui.Service;
import vo.PlayerVO;
import bl.Player;
import blService.PlayerBLService;

public class PlayerTableModel extends MyTableModel {

	/**
	 * 表格球员信息列表模式model
	 */
	PlayerBLService player = Service.player;
	private static final long serialVersionUID = 1L;
	ArrayList<ArrayList<Object>> content = new ArrayList<ArrayList<Object>>();
	static String[] head;
	String[] headbase = { "球员名称", "所属球队", "位置", "参赛场数", "先发场数", "在场时间", "篮板数",
			"助攻数", "盖帽数", "抢断数", "犯规数", "失误数", "效率", "得分", "投篮命中率%", "三分命中率%",
			"罚球命中率%", "两双", "得分/篮板/助攻" };
	String[] headhigh = { "球员名称", "所属球队", "真实命中率%", "GmSc效率值", "投篮效率%", "篮板率%",

	"进攻篮板数", "防守篮板数", "进攻篮板率%", "防守篮板率%", "助攻率%", "抢断率%", "盖帽率%", "失误率%",
			"使用率%" };

	public int headmodel;

	public PlayerTableModel(int model) {
		if (model == 0) {
			head = headbase;// 初始显示基础数据
			headmodel = 0;
		} else {
			head = headhigh;
			headmodel = 1;

		}

	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return content.size();
	}

	@Override
	public Class getColumnClass(int c) {
		return content.get(0).get(c).getClass();
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return head.length;
	}

	@Override
	public int findColumn(String sort) {
		int a = -1;
		for (int i = 0; i < head.length; i++)
			if (head[i].contains(sort))
				return i;
		return a;
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
		ArrayList<PlayerVO> result;
		if (model.equals("赛季"))
			result = player.getPlayerSeasonInfo(season, seasonType);
		else
			result = player.getPlayerAverageInfo(season, seasonType);
		if (result != null && result.size() != 0) {
			if (headmodel == 0)
				refreshBase(result);
			else
				refreshHigh(result);

		} else
			content.clear();

		/*
		 * d={"球员名称","所属球队","参赛场数","先发场数","篮板数","助攻数","在场时间",
		 * "投篮命中率","三分命中率","罚球命中率","进攻数","防守数","抢断数","盖帽数",
		 * "失误数","犯规数","得分","效率","GmSc效率值","真实命中率","投篮效率","篮板率",
		 * "进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率"}
		 */

	}

	/*
	 * public void Filter(FilterCondition f){ ArrayList<PlayerVO> v;
	 * //填赛季还是汇总？？？ if(f.getAllOrAve().equals("赛季"))
	 * v=player.selectPlayersBySeason(f.getSeason(), f.getPosition(),
	 * f.getUnion(), AgeEnum.ALL, f.getUnion(), f.getSort(),50); else
	 * v=player.selectPlayersByAverage(f.getSeason(),
	 * f.getPosition(),AgeEnum.ALL, f.getUnion(), f.getSort(),50);
	 * if(v!=null&&v.size()!=0){ if(headmodel==0) refreshBase(v); else
	 * refreshHigh(v); } else System.out.println("没有符合本次筛选的球员！");
	 * 
	 * }
	 */
	// {"球员名称","所属球队","参赛场数","先发场数","在场时间","篮板数","助攻数","盖帽数","抢断数","犯规数","失误数",
	// "效率","得分","投篮命中率","三分命中率","罚球命中率","两双"
	public void refreshBase(ArrayList<PlayerVO> result) {
		content.clear();
		for (PlayerVO vo : result) {
			ArrayList<Object> line = new ArrayList<Object>();
			line.add(vo.getName());
			line.add(vo.getOwingTeam());
			line.add(vo.getPosition());
			line.add(vo.getPlayedGames());
			line.add(vo.getGameStartingNum());
			line.add(Player.changeSecondToTime(vo.getPresentTime()));
			line.add(MyUIDataFormater.formatTo1(vo.getReboundNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getAssistNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getBlockNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getStealNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getFoulNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getTurnOverNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getEfficiency()));
			line.add(MyUIDataFormater.formatTo1(vo.getScore()));
			line.add(MyUIDataFormater.formatTo1(vo.getShootHitRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getThreeHitRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getFreeThrowHitRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getDoubleDoubleNum()));
			line.add(MyUIDataFormater.formatTo1(vo.getScore_rebound_assist()));
			content.add(line);

		}
	}

	// "球员名称","所属球队","真实命中率","GmSc效率值","投篮效率","篮板率",
	// "进攻数","防守数","进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率","得分/篮板/助攻"
	public void refreshHigh(ArrayList<PlayerVO> vv) {
		content.clear();
		for (PlayerVO vo : vv) {
			ArrayList<Object> line = new ArrayList<Object>();
			line.add(vo.getName());
			line.add(vo.getOwingTeam());
			line.add(MyUIDataFormater.formatTo1(vo.getTrueHitRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getGmScEfficiencyValue()));
			line.add(MyUIDataFormater.formatTo1(vo.getShootHitEfficiency() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getReboundRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getOffenReboundNum() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getDefenReboundNum() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getOffenReboundRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getDefenReboundRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getAssistRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getStealRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getBlockRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getTurnOverRate() * 100));
			line.add(MyUIDataFormater.formatTo1(vo.getUsageRate() * 100));
			content.add(line);
		}

	}

	public void SearchRefresh(Object vv) {
		ArrayList<PlayerVO> v = (ArrayList<PlayerVO>) vv;
		if (v != null && v.size() != 0) {
			if (headmodel == 0)
				refreshBase(v);
			else
				refreshHigh(v);
		} else {
			content.clear();
		}

	}

}
