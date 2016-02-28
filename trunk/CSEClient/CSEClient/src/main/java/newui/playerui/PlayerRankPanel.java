package newui.playerui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
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
import newui.mainui.MainFrame;
import newui.tables.MySortableTable;
import newui.tables.MyTableCellRenderer;
import newui.tables.PlayerTableModel;
import newui.tables.RowHeaderTable;
import vo.PlayerVO;
import businesslogic.Player;
import businesslogicservice.PlayerBLService;

public class PlayerRankPanel extends FatherPanel implements MouseListener,
		ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel funcPnl;
	private PlayerBLService player;
	// --------------
	JScrollPane jsp;
	JTable table;
	PlayerTableModel ptm = new PlayerTableModel();
	// ---------------
	JLabel refreshLbl, filterLbl, modeLbl;
	JComboBox<String> locationBox, partitionBox, filterRankBox, seasonBox,
			typeBox;
	Font font = new Font("微软雅黑", Font.PLAIN, 13);
	String[] locationText = { "全部", "前锋", "中锋", "后卫" };
	String[] partitionText = { "全部", "西部球队", "西北分区", "太平洋分区", "西南分区", "东部球队",
			"大西洋分区", "中央分区", "东南分区" };
	String[] filterRankText = { "得分", "篮板", "助攻", "得分/篮板/助攻(1:1:1)", "投篮",
			"盖帽", "抢断", "罚球", "犯规", "失误", "分钟", "效率", "两双" };

	public PlayerRankPanel() {
		player = new Player();
		// ------funcPnl--------
		funcPnl = new JPanel();
		funcPnl.setBackground(Style.BACK_GREY);
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weighty = 0.8;
		gbl.setConstraints(funcPnl, gbc);
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		funcPnl.setLayout(fl);
		add(funcPnl);
		// -------筛选---------------------------------------
		// -----位置---------
		JLabel locationLbl = new MyJLabel("球员位置：");
		funcPnl.add(locationLbl);
		locationBox = new MyComboBox(locationText);
		funcPnl.add(locationBox);
		// -----分区---------
		JLabel partitionLbl = new MyJLabel("分区：");
		funcPnl.add(partitionLbl);
		partitionBox = new MyComboBox(partitionText);
		partitionBox.setMaximumRowCount(10);
		funcPnl.add(partitionBox);
		// ----排序条件--------
		JLabel filterRankLbl = new MyJLabel("排序条件：");
		funcPnl.add(filterRankLbl);
		filterRankBox = new MyComboBox(filterRankText);
		filterRankBox.setMaximumRowCount(13);
		funcPnl.add(filterRankBox);
		// -----filterLbl-----
		filterLbl = new MyJLabel("筛选", new ImageIcon(
				"image/player/filterWhite.png"));
		filterLbl.addMouseListener(this);
		funcPnl.add(filterLbl);
		funcPnl.add(new JLabel("       "));
		// -----season----------
		JLabel seasonBoxLbl = new MyJLabel("赛季：");
		funcPnl.add(seasonBoxLbl);
		// 暂无获取赛季的bl方法
		String[] seasonBoxText = { "13-14" };
		seasonBox = new MyComboBox(seasonBoxText);
		seasonBox.addItemListener(this);
		funcPnl.add(seasonBox);
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
		// -----modeLbl---------
		modeLbl = new MyJLabel("至快速查询模式", new ImageIcon(
				"image/player/headmode.png"));
		modeLbl.addMouseListener(this);
		funcPnl.add(modeLbl);
		// ----jsp--------------
		table = new MySortableTable(ptm, 0);

		// table 渲染器，设置文字内容居中显示，设置背景色等
		table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
		table.setFont(new Font("微软雅黑", 0, 12));
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
		table.getTableHeader().setBackground(new Color(211, 211, 211));
		DefaultTableCellRenderer tcr = new MyTableCellRenderer();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}
		titleBar.setCurrentTableModel(ptm);
		titleBar.setModelEnum(TableModel.PLAYERRANK);
		titleBar.setTable(table);
		jsp = new JScrollPane(table);
		gbc.gridy = 2;
		gbc.gridheight = 10;
		gbc.weighty = 10;
		gbl.setConstraints(jsp, gbc);
		add(jsp);

		ptm.Refresh(typeBox.getSelectedItem().toString());
		MyTableCellRenderer.adjustTableColumnWidths(table);
		table.revalidate();
		// 设置表头颜色
		table.getTableHeader().setBackground(new Color(158, 158, 158));

		JLabel jb = new JLabel();
		jb.setOpaque(true);
		jb.setBackground(Color.black);
		jb.setBorder(BorderFactory.createLineBorder(new Color(158, 158, 158),
				20));
		jsp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, jb);
		// 设置显示行号
		jsp.setRowHeaderView(new RowHeaderTable(table, 30));
		titleBar.setSeason(seasonBox.getSelectedItem().toString());
		titleBar.setAveOrAll(typeBox.getSelectedItem().toString());
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

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == modeLbl)
			MainFrame.getInstance().setContentPanel(new PlayerIndexPanel());
		if (e.getSource() == filterLbl) {
			// 执行筛选
			String season = seasonBox.getSelectedItem().toString();
			String position = locationBox.getSelectedItem().toString();
			String union = partitionBox.getSelectedItem().toString();
			String sort = filterRankBox.getSelectedItem().toString();
			ArrayList<PlayerVO> vlist;
			String type = typeBox.getSelectedItem().toString();
			if (type.equals("赛季"))
				vlist = player.selectPlayersBySeason(season, position, union,
						sort,50);
			else
				vlist = player.selectPlayersByAverage(season, position, union,
						sort,50);
			// vlist.size()==0显示没有符合条件的球员
			if (vlist != null){
				ptm.refreshContent(vlist);
			}
			table.revalidate();

		}
		if (e.getSource() == refreshLbl) {
			ptm.Refresh(typeBox.getSelectedItem().toString());
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
		if (e.getSource() == filterLbl) {
			filterLbl.setForeground(Style.FOCUS_BLUE);
			filterLbl.setIcon(new ImageIcon("image/player/filterFocus.png"));
			filterLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource() == modeLbl) {
			modeLbl.setForeground(Style.FOCUS_BLUE);
			modeLbl.setIcon(new ImageIcon("image/player/headmodeBlue.png"));
			modeLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == refreshLbl) {
			refreshLbl.setForeground(Color.white);
			refreshLbl.setIcon(new ImageIcon("image/refreshWhite.png"));
			refreshLbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource() == filterLbl) {
			filterLbl.setForeground(Color.white);
			filterLbl.setIcon(new ImageIcon("image/player/filterWhite.png"));
			filterLbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource() == modeLbl) {
			modeLbl.setForeground(Color.white);
			modeLbl.setIcon(new ImageIcon("image/player/headmode.png"));
			modeLbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			String season = seasonBox.getSelectedItem().toString();
			ArrayList<PlayerVO> vlist;
			String s = (String) typeBox.getSelectedItem();
			if (s.equals("赛季"))
				vlist = player.getPlayerSeasonInfo(season);
			else
				vlist = player.getPlayerAverageInfo(season);
			// vlist.size()==0显示没有符合条件的球员
			if (vlist != null)
				ptm.refreshContent(vlist);
			table.revalidate();
			titleBar.setSeason(season);
			titleBar.setAveOrAll(s);
		}
	}

}
