package newui.tables;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import vo.PlayerVO;
import vo.TeamVO;
import businesslogic.Team;
import businesslogicservice.TeamBLService;

public class TeamBaseInfoTableModel extends MyTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String[] head ={"(logo)","球队","缩写","联盟","分区","主场","成立时间"};
	ArrayList<ArrayList<Object>> content = new ArrayList<ArrayList<Object>>();
	private TeamBLService team;
	private ArrayList<ImageIcon> teamIcon;
	public TeamBaseInfoTableModel(){
		team=new Team();
		teamIcon=new ArrayList<ImageIcon>();
	}
	public int getRowCount() {
		return content.size();
	}
	@Override
	public Class getColumnClass(int c) {
		return content.get(0).get(c).getClass();
	}

	public int getColumnCount() {
		return head.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return content.get(rowIndex).get(columnIndex);
	}

	public String getColumnName(int col) {
		return head[col];
	}

	public static String[] getHead() {
		return head;
	}
	
	public void Refresh(){
		ArrayList<TeamVO> v=team.getTeamBaseInfo();
		if(v!=null)
			Refresh(v);
	}
	//"(logo)","球队","缩写","联盟","分区","主场","成立时间"
	public void Refresh(ArrayList<TeamVO> v){
		content.clear();
		teamIcon.clear();
		for(TeamVO vo:v){
			ArrayList<Object> line=new ArrayList<Object>();
			String name=vo.getTeamName();
			ImageIcon img=team.getTeamImage(name);
			teamIcon.add(img);
			line.add(img);
			line.add(name);
			line.add(vo.getAbLocation());
			line.add(vo.getConference());
			line.add(vo.getPartition());
			line.add(vo.getHomeCourt());
			line.add(vo.getSetUpTime());
			content.add(line);
		}
		
	}
	
	public ArrayList<ImageIcon> getTeamImg(){
		return this.teamIcon;
	}
	
	public void SearchRefresh(Object vv){
		ArrayList<TeamVO> v=(ArrayList<TeamVO>)vv;
		if(v!=null&&v.size()!=0){
			
			Refresh(v);
		}
		
	}
}
