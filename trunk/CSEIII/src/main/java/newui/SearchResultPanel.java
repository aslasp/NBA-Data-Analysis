package newui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import newui.mainui.MainFrame;
import newui.playerui.PlayerDetailPanel;
import newui.tables.MyBaseTable;
import newui.tables.MyTableCellRenderer;
import newui.tables.PlayerBaseInfoTableModel;
import newui.tables.TeamBaseInfoTableModel;
import newui.teamui.TeamDetailPanel;
import vo.PlayerVO;
import vo.TeamVO;
import blService.PlayerBLService;
import blService.TeamBLService;

public class SearchResultPanel extends FatherPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -----------------
	boolean isTeamTable;
	// -----------------
	PlayerBaseInfoTableModel ptm;
	TeamBaseInfoTableModel ttm;
	JTable table;
	JScrollPane jsp;
	JLabel resultLbl, changeLbl;
	JPanel funcPnl;
	private PlayerBLService pservice;
	private TeamBLService tservice;
	public static String content;
	private teamMouse tmouse;
	private playerMouse pmouse;
	Font font = new Font("微软雅黑", Font.PLAIN, 15);

	public SearchResultPanel(String scontent) {
		isDetail = true;
		tmouse = new teamMouse();
		pmouse = new playerMouse();
		// ------funcPnl--------
		funcPnl = new JPanel();
		content = scontent;
		funcPnl.setBackground(Style.BACK_GREY);
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weighty = 0.1;
		gbl.setConstraints(funcPnl, gbc);
		add(funcPnl);
		// ----resultLbl---------
		resultLbl = new MyLabel("在球队中检索到88条符合关键字" + scontent + "的结果...");
		funcPnl.add(resultLbl);
		funcPnl.add(new JLabel("                             "));
		// ----changeLbl---------
		changeLbl = new MyLabel("切换搜索域");
		changeLbl.addMouseListener(this);
		changeLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		funcPnl.add(changeLbl);
		// ------jsp--------------------
		ttm = new TeamBaseInfoTableModel();
		ptm = new PlayerBaseInfoTableModel();
		ptm.setCurrentLable(resultLbl);
		ttm.setCurrentLable(resultLbl);
		ptm.setContent(scontent);
		ttm.setContent(scontent);
		table = new MyBaseTable(ttm);

		// table 渲染器，设置文字内容居中显示，设置背景色等
		table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
		table.setFont(new Font("微软雅黑", 0, 12));
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(Style.FOCUS_BLUE);
		DefaultTableCellRenderer tcr = new MyTableCellRenderer();
		for (int i = 1; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}

		ptm.setCurrentTable(table);
		ttm.setCurrentTable(table);
		titleBar.setCurrentTableModel(ttm);
		titleBar.setModelEnum(TableModel.RESULTTEAM);
		titleBar.setTable(table);

		gbc.insets = new Insets(0, 2, 1, 2);
		jsp = new JScrollPane(table);
		isTeamTable = true;
		gbc.gridy = 2;
		gbc.gridheight = 10;
		gbc.weighty = 10;
		gbl.setConstraints(jsp, gbc);
		add(jsp);
		pservice = Service.player;// 这里有问题
		tservice = Service.team;
		ArrayList<TeamVO> v = tservice.getTeamBaseInfo(scontent);
		resultLbl
				.setText("在球队中检索到" + v.size() + "条符合关键字" + scontent + "的结果...");
		ttm.SearchRefresh(v);
		// table 渲染器，设置文字内容居中显示，设置背景色等
		table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
		table.setFont(new Font("微软雅黑", 0, 12));
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
		for (int i = 1; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(Style.FOCUS_BLUE);
		table.revalidate();
		table.addMouseListener(tmouse);

	}

	class MyLabel extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyLabel(String text) {
			super(text);
			setFont(font);
			setForeground(Color.white);
		}

	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == changeLbl) {
			// 监听，换掉table里的tableModel
			if (isTeamTable) {
				// 换为ptm
				isTeamTable = false;

				ArrayList<PlayerVO> re = pservice.getPlayerBaseInfo(content);
				table.setModel(ptm);
				// ptm.setCurrentLable(resultLbl);
				// resultLbl.setText("在球员中检索到"+re.size()+"条符合关键字"+content+"的结果...");
				ptm.setContent(content);
				ptm.SearchRefresh(re);
				table.revalidate();
				// table 渲染器，设置文字内容居中显示，设置背景色等
				table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
				table.setFont(new Font("微软雅黑", 0, 12));
				table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
				// table.getTableHeader().setBackground(new Color(211, 211,
				// 211));
				DefaultTableCellRenderer tcr = new MyTableCellRenderer();
				for (int i = 1; i < table.getColumnCount(); i++) {
					table.getColumn(table.getColumnName(i))
							.setCellRenderer(tcr);
				}
				table.getTableHeader().setForeground(Color.white);
				table.getTableHeader().setBackground(Style.FOCUS_BLUE);

				table.removeMouseListener(tmouse);
				table.addMouseListener(pmouse);
				titleBar.setCurrentTableModel(ptm);
				titleBar.setModelEnum(TableModel.RESULTPLAYER);
				titleBar.setTable(table);

			} else {
				// 换为ttm
				isTeamTable = true;

				ArrayList<TeamVO> re = tservice.getTeamBaseInfo(content);
				ttm.setContent(content);
				table.setModel(ttm);
				ttm.SearchRefresh(re);
				table.revalidate();
				ttm.setCurrentTable(table);
				// table 渲染器，设置文字内容居中显示，设置背景色等
				table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
				table.setFont(new Font("微软雅黑", 0, 12));
				table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
				DefaultTableCellRenderer tcr = new MyTableCellRenderer();
				for (int i = 1; i < table.getColumnCount(); i++) {
					table.getColumn(table.getColumnName(i))
							.setCellRenderer(tcr);
				}
				table.getTableHeader().setForeground(Color.white);
				table.getTableHeader().setBackground(Style.FOCUS_BLUE);
				table.removeMouseListener(pmouse);
				table.addMouseListener(tmouse);
				titleBar.setCurrentTableModel(ttm);
				// ttm.setCurrentLable(resultLbl);
				titleBar.setModelEnum(TableModel.RESULTTEAM);
				titleBar.setTable(table);
			}

			jsp.revalidate();
		}

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == changeLbl)
			changeLbl.setForeground(Style.FOCUS_BLUE);

	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == changeLbl)
			changeLbl.setForeground(Color.white);
	}

	class teamMouse extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = table.getSelectedRow();
				String name = table.getValueAt(row, 2).toString();
				MainFrame.getInstance().setContentPanel(
						new TeamDetailPanel(name));
			}
		}
	}

	class playerMouse extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = table.getSelectedRow();
				String name = table.getValueAt(row, 1).toString();
				MainFrame.getInstance().setContentPanel(
						new PlayerDetailPanel(name));
			}
		}
	}

	public static void setContent(String ss) {
		content = ss;

	}
}
