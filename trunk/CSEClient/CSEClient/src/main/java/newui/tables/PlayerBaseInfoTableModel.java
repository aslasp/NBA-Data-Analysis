package newui.tables;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import vo.PlayerVO;
import businesslogic.Player;
import businesslogic.Team;
import businesslogicservice.PlayerBLService;

public class PlayerBaseInfoTableModel extends MyTableModel {

	private static final long serialVersionUID = 1L;
	static String[] head = { "(头像)", "球员名", "所属球队", "位置", "身高", "体重", "生日",
			"年龄", "球龄" };

	ArrayList<ArrayList<Object>> content = new ArrayList<ArrayList<Object>>();
	private PlayerBLService player;
	private ArrayList<ImageIcon> imgList = new ArrayList<ImageIcon>();
	private ArrayList<PlayerVO> playerlist;
	private JTable currentTable;

	public PlayerBaseInfoTableModel() {

		player = new Player();
		playerlist = new ArrayList<PlayerVO>();
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

	public String getColumnName(int col) {
		return head[col];
	}

	public static String[] getHead() {
		return head;
	}

	public void sortByCharacter(String character) {
		// 没有符合条件的数据 暂时都未处理
		ArrayList<PlayerVO> sortByC = new ArrayList<PlayerVO>();
		ArrayList<ImageIcon> listimg = new ArrayList<ImageIcon>();
		for (int i = 0; i < playerlist.size(); i++) {
			String name = playerlist.get(i).getName();

			if (name.startsWith(character)) {
				sortByC.add(playerlist.get(i));
				listimg.add(imgList.get(i));
			}
		}
		SortRefresh(sortByC, listimg);
	}

	public void findByTeam(String tName) {
		ArrayList<PlayerVO> sortByC = new ArrayList<PlayerVO>();
		ArrayList<ImageIcon> listimg = new ArrayList<ImageIcon>();
		for (int i = 0; i < playerlist.size(); i++) {
			String name = playerlist.get(i).getTeamName();
			if (tName.equals("全部")) {
				sortByC = playerlist;
				listimg = imgList;
				break;
			}
			if (name.contains(Team.changeTeamNameCHToEN(tName))) {
				sortByC.add(playerlist.get(i));
				listimg.add(imgList.get(i));
			}
		}
		SortRefresh(sortByC, listimg);
	}

	public void Refresh() {
		playerlist = player.getPlayerBaseInfo();
		if (playerlist == null || playerlist.size() == 0) {
			// 显示没有符合消息的数据
		} else
			Refresh(playerlist);

	}

	// 图片加载过慢1.每加载一条完整或一定数量行记录 即执行渲染或Revalidate
	// long a=System.currentTimeMillis();
	//
	// System.out.println("\r<br>执行耗时 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
	// static String[] head =
	// { "(头像)", "球员名", "所属球队", "位置", "身高", "体重","生日","年龄" ,"经验" };
	// 适应出网络连接方式来的数据
	public void Refresh(ArrayList<PlayerVO> list) {
		content.clear();
		imgList.clear();
		int i = 0;
		for (PlayerVO vo : list) {
			ArrayList<Object> line = new ArrayList<Object>();
			String name = vo.getName();
			ImageIcon tou = player.getPlayerPortraitImage(name);
			imgList.add(tou);
			ImageIcon icon = new ImageIcon(tou.getImage().getScaledInstance(
					currentTable.getColumn(currentTable.getColumnName(0))
							.getWidth(), 40
					/* currentTable.getRowHeight(i) */, Image.SCALE_DEFAULT));

			line.add(icon);
			line.add(name);
			line.add(vo.getTeamName());
			line.add(vo.getPosition());
			line.add(vo.getHeight());
			line.add(vo.getWeight());
			line.add(vo.getBirth());
			line.add(vo.getAge());
			line.add(vo.getExp());
			content.add(line);

			i++;

		}

	}

	public void SortRefresh(ArrayList<PlayerVO> list, ArrayList<ImageIcon> img) {
		content.clear();
		int i = 0;
		for (PlayerVO vo : list) {
			ArrayList<Object> line = new ArrayList<Object>();
			ImageIcon icon = new ImageIcon(img
					.get(i)
					.getImage()
					.getScaledInstance(
							currentTable.getColumn(
									currentTable.getColumnName(0)).getWidth(),
							40
							/* currentTable.getRowHeight(i) */,
							Image.SCALE_DEFAULT));
			line.add(icon);
			line.add(vo.getName());
			line.add(vo.getTeamName());
			line.add(vo.getPosition());
			line.add(vo.getHeight());
			line.add(vo.getWeight());
			line.add(vo.getBirth());
			line.add(vo.getAge());
			line.add(vo.getExp());
			content.add(line);
			currentTable.revalidate();
			i++;

		}

	}

	public ArrayList<ImageIcon> getImgList() {
		return imgList;
	}
	
	public void SearchRefresh(Object vv){
		ArrayList<PlayerVO> v=(ArrayList<PlayerVO>)vv;
		if(v!=null&&v.size()!=0){
			
			Refresh(v);
		}
		
	}

}
