package newui.tables;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;

import newui.Service;
import newui.teamui.BaseInfoTeamThread;
import vo.TeamVO;
import blService.TeamBLService;

public class TeamBaseInfoTableModel extends MyTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String[] head = { "logo", "球队", "缩写", "联盟", "分区", "主场", "成立时间" };
	ArrayList<ArrayList<Object>> content = new ArrayList<ArrayList<Object>>();
	private TeamBLService team;
	private JTable currentTable;
	private ArrayList<ImageIcon> teamIcon;
	private ArrayList<String> nameList;
	private JLabel label;

	public TeamBaseInfoTableModel() {
		team = Service.team;
		teamIcon = new ArrayList<ImageIcon>();
	}

	public int getRowCount() {
		return content.size();
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		content.get(row).set(col, value);
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

	public void Refresh() {
		ArrayList<TeamVO> v = team.getTeamBaseInfo();
		if (v != null)
			Refresh(v);
	}

	// "(logo)","球队","缩写","联盟","分区","主场","成立时间"
	public void Refresh(ArrayList<TeamVO> v) {
		content.clear();
		teamIcon.clear();
		nameList = new ArrayList<String>();
		for (TeamVO vo : v) {
			ArrayList<Object> line = new ArrayList<Object>();
			String name = vo.getTeamName();
			String abLocation = vo.getAbLocation();
			nameList.add(abLocation);
			line.add("加载中");
			line.add(name);
			line.add(abLocation);
			line.add(vo.getConference());
			line.add(vo.getPartition());
			line.add(vo.getHomeCourt());
			line.add(vo.getSetUpTime());
			content.add(line);
		}
		BaseInfoTeamThread btt = new BaseInfoTeamThread(nameList, currentTable);
		btt.start();
		currentTable.revalidate();

	}

	public ArrayList<ImageIcon> getTeamImg() {
		return this.teamIcon;
	}

	public void SearchRefresh(Object vv) {
		ArrayList<TeamVO> v = (ArrayList<TeamVO>) vv;
		if (v != null && v.size() != 0) {

			Refresh(v);
		} else
			content.clear();
		label.setText("在球队中检索到" + v.size() + "条符合关键字" + ss + "的结果...");

	}

	public void setCurrentTable(JTable table) {
		this.currentTable = table;

	}

	public void setCurrentLable(JLabel label) {
		this.label = label;

	}
}
