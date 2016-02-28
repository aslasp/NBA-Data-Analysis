package newui.teamui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import newui.FatherPanel;
import newui.Style;
import newui.TableModel;
import newui.tables.MySortableTable;
import newui.tables.MyTableCellRenderer;
import newui.tables.RowHeaderTable;
import newui.tables.TeamTableModel;
import vo.TeamVO;
import businesslogic.Team;
import businesslogicservice.TeamBLService;

public class TeamIndexPanel extends FatherPanel implements MouseListener,
		ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel funcPnl;
	//
	JScrollPane jsp;
	JTable table;
	TeamTableModel ttm;
	//
	JLabel refreshLbl;
	JComboBox<String> seasonBox, typeBox;
	Font font = new Font("微软雅黑", Font.PLAIN, 13);

	public TeamIndexPanel() {
		super();
		// ------funcPnl--------
		funcPnl = new JPanel();
		funcPnl.setBackground(Style.BACK_GREY);
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weighty = 0.1;
		gbl.setConstraints(funcPnl, gbc);
		add(funcPnl);
		// -----season----------
		JLabel seasonBoxLbl = new MyJLabel("赛季：");
		funcPnl.add(seasonBoxLbl);
		// 暂时没有bl方法
		String[] seasonBoxText = { "13-14" };
		seasonBox = new MyComboBox(seasonBoxText);
		seasonBox.addItemListener(this);
		funcPnl.add(seasonBox);
		funcPnl.add(new JLabel("       "));
		// ----DataType---------
		JLabel typeLbl = new MyJLabel("数据类型：");
		funcPnl.add(typeLbl);
		String[] typeText = { "赛季", "场均" };
		typeBox = new MyComboBox(typeText);
		typeBox.addItemListener(this);
		funcPnl.add(typeBox);
		funcPnl.add(new JLabel("       "));
		// -----refreshLbl------
		refreshLbl = new MyJLabel("刷新", new ImageIcon("image/refreshWhite.png"));
		refreshLbl.addMouseListener(this);
		funcPnl.add(refreshLbl);
		// -----table-----------
		ttm = new TeamTableModel();
		table = new MySortableTable(ttm, 1);
		// table 渲染器，设置文字内容居中显示，设置背景色等

		table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色

		table.setFont(new Font("微软雅黑", 0, 12));
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
		table.getTableHeader().setBackground(new Color(211, 211, 211));
		DefaultTableCellRenderer tcr = new MyTableCellRenderer();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}
		titleBar.setCurrentTableModel(ttm);
		titleBar.setModelEnum(TableModel.TEAMRANK);
		titleBar.setTable(table);
		jsp = new JScrollPane(table);

		gbc.gridy = 2;
		gbc.gridheight = 10;
		gbc.weighty = 10;
		gbl.setConstraints(jsp, gbc);
		add(jsp);

		// ====初始化数据=====
		ttm.Refresh(typeBox.getSelectedItem().toString());
		table.revalidate();
		MyTableCellRenderer.adjustTableColumnWidths(table);// 自动设置列宽
		// 设置表头颜色
		table.getTableHeader().setBackground(new Color(158, 158, 158));
		
		// 设置显示行号
		jsp.setRowHeaderView(new RowHeaderTable(table, 20));
		
		
		JLabel jb = new JLabel();
		jb.setOpaque(true);
		jb.setBackground(Color.black);
		jb.setBorder(BorderFactory.createLineBorder(new Color(158, 158, 158),
				20));
		jsp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, jb);
		titleBar.setSeason(seasonBox.getSelectedItem().toString());
		titleBar.setAveOrAll(typeBox.getSelectedItem().toString());
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == refreshLbl) {
			ttm.Refresh(typeBox.getSelectedItem().toString());
			table.revalidate();
		}

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == refreshLbl) {
			refreshLbl.setForeground(Style.FOCUS_BLUE);
			refreshLbl.setIcon(new ImageIcon("image/refreshFocus.png"));
			refreshLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == refreshLbl) {
			refreshLbl.setForeground(Color.white);
			refreshLbl.setIcon(new ImageIcon("image/refreshWhite.png"));
			refreshLbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

	}

	class MyJLabel extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyJLabel(String text) {
			super(text);
			setFont(font);
			setForeground(Color.white);
		}

		public MyJLabel(String text, ImageIcon img) {
			super(text, img, JLabel.CENTER);
			setFont(font);
			setForeground(Color.white);
		}

	}

	class MyComboBox extends JComboBox<String> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyComboBox(String[] text) {
			super(text);
			setFont(font);
		}
	}

	public void itemStateChanged(ItemEvent e) {
		// TODO 自动生成的方法存根
		if (e.getStateChange() == ItemEvent.SELECTED) {
			String season = seasonBox.getSelectedItem().toString();
			ArrayList<TeamVO> vlist;
			String s = (String) typeBox.getSelectedItem();
			TeamBLService team = new Team();
			if (s.equals("赛季"))
				vlist = team.getTeamSeasonInfo(season);
			else
				vlist = team.getTeamAverageInfo(season);
			// vlist.size()==0显示没有符合条件的球员
			if (vlist != null)
				ttm.refreshContent(vlist);
			table.revalidate();
			titleBar.setSeason(season);
			titleBar.setAveOrAll(s);
		}
	}
}
