package newui.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import newui.Service;
import newui.playerui.BaseInfoPlayerThread;
import vo.PlayerVO;
import blservice.PlayerBLService;

public class PlayerBaseInfoTableModel extends MyTableModel {

	private static final long serialVersionUID = 1L;
	static String[] head = { "(头像)", "球员名", "所属球队", "位置", "身高", "体重", "生日",
			"年龄", "球龄" };

	ArrayList<ArrayList<Object>> content = new ArrayList<ArrayList<Object>>();
	private PlayerBLService player;
	// private ArrayList<ImageIcon> imgList = new ArrayList<ImageIcon>();
	// private ArrayList<PlayerVO> playerlist;
	private JTable currentTable;
	private JLabel label;
	private ArrayList<String> nameList;

	public PlayerBaseInfoTableModel() {

		player = Service.player;
		// playerlist = new ArrayList<PlayerVO>();

	}

	public void setCurrentTable(JTable t) {
		currentTable = t;
	}

	private List<Class> colTypes = new ArrayList<Class>();

	@Override
	public Class getColumnClass(int c) {
		return content.get(0).get(c).getClass();
	}

	public int getRowCount() {
		return content.size();
	}

	public int getColumnCount() {
		return head.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return content.get(rowIndex).get(columnIndex);
	}

	public void setValueAt(Object value, int row, int column) {
		content.get(row).set(column, value);
	}

	public String getColumnName(int col) {
		return head[col];
	}

	public static String[] getHead() {
		return head;
	}

	public void sortByCharacter(String character) {
		char c = character.charAt(0);
		ArrayList<PlayerVO> v = player.getPlayersByInitialName(c);
		if (v.size() == 0 || v == null) {
			content.clear();
		} else {
			Refresh(v);
		}
	}

	public void findByTeam(String tName) {
		ArrayList<PlayerVO> v = player.getPlayersByTeam(tName);
		if (v.size() == 0 || v == null) {
			content.clear();
		} else {
			Refresh(v);
		}
	}

	public void Refresh() {
		ArrayList<PlayerVO> playerlist = player.getPlayerBaseInfo();
		if (playerlist == null || playerlist.size() == 0) {
			// 显示没有符合消息的数据
			content.clear();
		} else
			Refresh(playerlist);

	}

	public void Refresh(ArrayList<PlayerVO> list) {

		content.clear();

		nameList = new ArrayList<String>();

		for (PlayerVO vo : list) {
			ArrayList<Object> line = new ArrayList<Object>();
			String name = vo.getName();

			nameList.add(name);
			line.add("加载中");
			line.add(name);
			line.add(vo.getOwingTeam());
			line.add(vo.getPosition());
			line.add(vo.getHeight());
			line.add(vo.getWeight());
			line.add(vo.getBirth());
			line.add(vo.getAge());
			line.add(vo.getExp());
			content.add(line);

		}

		// 线程加载图片
		BaseInfoPlayerThread thre = new BaseInfoPlayerThread(nameList,
				currentTable);
		thre.start();
		currentTable.revalidate();

	}

	/*
	 * public void SortRefresh(ArrayList<PlayerVO> list, ArrayList<ImageIcon>
	 * img) { content.clear(); int i = 0; for (PlayerVO vo : list) {
	 * ArrayList<Object> line = new ArrayList<Object>(); ImageIcon icon = new
	 * ImageIcon(img .get(i) .getImage() .getScaledInstance(
	 * currentTable.getColumn( currentTable.getColumnName(0)).getWidth(), 40 ,
	 * Image.SCALE_DEFAULT)); line.add(icon); //line.add("头像");
	 * line.add(vo.getName()); line.add(vo.getOwingTeam());
	 * line.add(vo.getPosition()); line.add(vo.getHeight());
	 * line.add(vo.getWeight()); line.add(vo.getBirth()); line.add(vo.getAge());
	 * line.add(vo.getExp()); content.add(line); currentTable.revalidate(); i++;
	 * 
	 * }
	 * 
	 * }
	 * 
	 * /*public ArrayList<ImageIcon> getImgList() { return imgList; }
	 */

	public void SearchRefresh(Object vv) {
		ArrayList<PlayerVO> v = (ArrayList<PlayerVO>) vv;
		if (v != null && v.size() != 0) {
			Refresh(v);
		} else
			content.clear();
		if (label != null) {
			label.setText("在球员中检索到" + v.size() + "条符合关键字" + ss + "的结果...");
		}
	}

	public void setCurrentLable(JLabel label) {
		this.label = label;

	}

}
