package newui.tables;

import java.util.ArrayList;

import vo.PlayerVO;
import businesslogic.Player;

public class PlayerTableModel extends MyTableModel{

	/**
	 * 表格球员信息列表模式model
	 */
	Player player=new Player();
	private static final long serialVersionUID = 1L;
	ArrayList<ArrayList<Object>> content=new ArrayList<ArrayList<Object>>();
	static String[] head={"球员名称","所属球队","参赛场数","先发场数","篮板数","助攻数","在场时间",
			"投篮命中率","三分命中率","罚球命中率","进攻数","防守数","抢断数","盖帽数",
			"失误数","犯规数","得分","效率","GmSc效率值","真实命中率","投篮效率","篮板率",
			"进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率","得分/篮板/助攻","两双"};
	
	String[] example={"金翠","英雄联盟","30","12","100","50","24h","100%","100%","100%",
			"10","5","1","3","0","0","100","100%","100%","99%","100%","50%","100%",
			"100%","100%","100%","100%","0%","100%","1"};
	String[] ex={"金翠","英雄联盟","30","1","100","70","24h","100%","100%","100%",
			"10","5","1","3","0","0","100","100%","100%","99%","100%","50%","100%",
			"100%","100%","100%","100%","0%","100%","1"};
	public PlayerTableModel(){
		ArrayList<Object> e=new ArrayList<Object>();
		for(Object str:example)
			e.add(str);
		content.add(e);
		
		ArrayList<Object> f=new ArrayList<Object>();
		for(Object str:ex)
			f.add(str);
		content.add(f);
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

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return content.get(rowIndex).get(columnIndex);
	}
	public String getColumnName(int col) {
		return head[col];
	}
	
	public static String[] getHead(){
		return head;
	}
	
	
	//改变表头顺序  同时列内容也要变动
	public void setHead(int front){
		//front为被置前的搜索项  球队名称和所属球队始终置前
		int len=head.length;
		String[] newHead=new String[head.length];
		newHead[0]=head[0];newHead[1]=head[1];
	//	newHead[]
		
	}
	
	public void Refresh(String model){
		ArrayList<PlayerVO> result;
		if(model.equals("赛季"))
			 result=player.getPlayerSeasonInfo("13-14");
		else
			result=player.getPlayerAverageInfo("13-14");
		if(result!=null&&result.size()!=0){
			refreshContent(result);
			    
			
		}
			
	/*d={"球员名称","所属球队","参赛场数","先发场数","篮板数","助攻数","在场时间",
			"投篮命中率","三分命中率","罚球命中率","进攻数","防守数","抢断数","盖帽数",
			"失误数","犯规数","得分","效率","GmSc效率值","真实命中率","投篮效率","篮板率",
			"进攻篮板率","防守篮板率","助攻率","抢断率","盖帽率","失误率","使用率"}*/	
		
		
	}
	
	public void Filter(FilterCondition f){
		ArrayList<PlayerVO> v;
		if(f.getAllOrAve().equals("汇总"))
			v=player.selectPlayersBySeason(f.getSeason(), f.getPosition(), f.getUnion(), 
					f.getSort(),50);
		else
			v=player.selectPlayersByAverage(f.getSeason(), f.getPosition(), f.getUnion(), 
					f.getSort(),50);
		if(v!=null&&v.size()!=0)
			refreshContent(v);
		else
			System.out.println("没有符合本次筛选的球员！");
		
	}
	
	public void refreshContent(ArrayList<PlayerVO> result){
		content.clear();
		for(PlayerVO vo:result){
			ArrayList<Object> line=new ArrayList<Object>();
			line.add(vo.getName());
			line.add(vo.getTeamName());
			line.add(vo.getPlayedGames());
			line.add(vo.getGameStartingNum());
			line.add(vo.getReboundNum());
			line.add(vo.getAssistNum());
		    line.add(vo.getPresentTime());
		    line.add(vo.getShootHitRate());
		    line.add(vo.getThreeHitRate());
		    line.add(vo.getFreeThrowHitRate());
		    line.add(vo.getOffenNum());
		    line.add(vo.getDefenNum());
		    line.add(vo.getStealNum());
		    line.add(vo.getBlockNum());
		    line.add(vo.getTurnOverNum());
		    line.add(vo.getFoulNum());
		    line.add(vo.getScore());
		    line.add(vo.getEfficiency());
		    line.add(vo.getGmScEfficiencyValue());
		    line.add(vo.getTrueHitRate());
		    //这个对吗？有没有用错get方法了
		    line.add(vo.getShootEfficiency());
		    line.add(vo.getReboundRate());
		    line.add(vo.getOffenReboundRate());
		    line.add(vo.getDefenReboundRate());
		    line.add(vo.getAssistRate());
		    line.add(vo.getStealRate());
		    line.add(vo.getBlockRate());
		    line.add(vo.getTurnOverRate());
		    line.add(vo.getUsageRate());
		    line.add(vo.getScore_rebound_assist());
		    line.add(vo.getDoubleDoubleNum());
		    content.add(line);
		    
		}
	}
	
	public void SearchRefresh(Object vv){
		ArrayList<PlayerVO> v=(ArrayList<PlayerVO>)vv;
		if(v!=null&&v.size()!=0){
			refreshContent(v);
		}
		
	}

}
