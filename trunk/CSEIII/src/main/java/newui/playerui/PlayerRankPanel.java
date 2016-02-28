package newui.playerui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
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
import javax.swing.JTextField;

import newui.FatherPanel;
import newui.Service;
import newui.Style;
import newui.TableModel;
import newui.mainui.MainFrame;
import newui.tables.MyTableCellRenderer;
import newui.tables.PlayerTableModel;
import newui.tables.RowHeaderTable;
import newui.tables.TableSorter;
import vo.PlayerVO;
import blService.MatchBLService;
import blService.PlayerBLService;

public class PlayerRankPanel extends FatherPanel implements MouseListener,
		ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel funcPnl;
	private PlayerBLService player;
	boolean isHighInfo = false;
	// --------------
	JScrollPane jsp;
	JTable table;
	PlayerTableModel ptm;
	// ---------------
	JLabel refreshLbl, filterLbl, modeLbl, fieldLbl;
//	JTextField timeField;
	JComboBox<String> locationBox, partitionBox, filterRankBox, seasonBox,
			seasonTypeBox, typeBox;
	Font font = new Font("微软雅黑", Font.PLAIN, 13);
	String[] locationText = { "全部", "前锋", "中锋", "后卫", "前锋-中锋", "前锋-后卫" };
	String[] partitionText = { "全部", "西部球队", "东部球队" };
	String[] filterRankText = { "得分", "篮板数", "助攻数", "得分/篮板/助攻", "投篮命中率", "盖帽数",
			"抢断数", "罚球命中率", "犯规数", "失误数", "在场时间", "效率", "两双" };
	String[] filterRankText2 = { "真实命中率", "GmSc效率值", "投篮效率", "篮板率", "进攻篮板数",
			"防守篮板数", "进攻篮板率", "防守篮板率", "助攻率", "抢断率", "盖帽率", "失误率", "使用率" };
	int lastcolumn = -1;
	int clicktime = 0;
	MyTableCellRenderer tcr;
	highlisten listen;
	TableSorter ts;
	int lastTime = 0;

	public PlayerRankPanel() {
		ptm = new PlayerTableModel(0);
		player = Service.player;
		listen = new highlisten();
		// ------funcPnl--------
		funcPnl = new JPanel();
		funcPnl.setBackground(Style.BACK_GREY);
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weighty = 0.8;
		gbl.setConstraints(funcPnl, gbc);
		funcPnl.setLayout(new GridLayout(2, 1));
		add(funcPnl);
		JPanel f1 = new JPanel();
		JPanel f2 = new JPanel();
		f1.setOpaque(false);
		f2.setOpaque(false);
		funcPnl.add(f1);
		funcPnl.add(f2);
		// -------筛选---------------------------------------
		// -----位置---------
		JLabel locationLbl = new MyJLabel("球员位置：");
		f1.add(locationLbl);
		locationBox = new MyComboBox(locationText);
		f1.add(locationBox);
		f1.add(new JLabel("       "));
		locationBox.addItemListener(this);
		// -----分区---------
		JLabel partitionLbl = new MyJLabel("分区：");
		f1.add(partitionLbl);
		partitionBox = new MyComboBox(partitionText);
		partitionBox.setMaximumRowCount(10);
		f1.add(partitionBox);
		f1.add(new JLabel("       "));
		partitionBox.addItemListener(this);
		// ----排序条件--------
		JLabel filterRankLbl = new MyJLabel("排序条件：");
		f1.add(filterRankLbl);
		filterRankBox = new MyComboBox(filterRankText);
		filterRankBox.setMaximumRowCount(13);
		filterRankBox.setMaximumSize(new Dimension(100, 28));
		filterRankBox.setMinimumSize(new Dimension(100, 28));
		filterRankBox.setPreferredSize(new Dimension(100, 28));
		f1.add(filterRankBox);
		filterRankBox.addItemListener(this);
		f1.add(new JLabel("       "));
		// -------timeLbl------
//		timeLbl = new MyJLabel("在场时间大于(分钟)：");
//		f1.add(timeLbl);
//		timeField = new JTextField(5);
//		f1.add(timeField);
//		timeField.addFocusListener(new FocusAdapter() {
//			public void focusLost(FocusEvent e) {
//				String text = timeField.getText();
//				int t = -1;
//				try {
//					t = Integer.parseInt(text);
//				} catch (Exception ex) {
//					// 不处理
//				}
//				if (t != lastTime)
//					Filter();
//			}
//		});
//
//		timeField.addKeyListener(new KeyAdapter() {
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					Filter();
//				}
//			}
//		});
		// -----filterLbl-----
		filterLbl = new MyJLabel("筛选", new ImageIcon(
				"image/player/filterWhite.png"));
		filterLbl.addMouseListener(this);
		filterLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		f1.add(filterLbl);
		// -----season----------
		JLabel seasonBoxLbl = new MyJLabel("赛季：");
		f2.add(seasonBoxLbl);

		MatchBLService match = Service.match;
		ArrayList<String> seasons = match.getAllSeasons();
		int size = seasons.size();
		String[] seasonBoxText = (String[]) match.getAllSeasons().toArray(
				new String[size]);
		seasonBox = new MyComboBox(seasonBoxText);
		seasonBox.addItemListener(this);
		f2.add(seasonBox);
		//
		String[] seasonTypeBoxText = { "季后赛 ", "常规赛", "季前赛" };
		seasonTypeBox = new MyComboBox(seasonTypeBoxText);
		seasonTypeBox.addItemListener(this);
		f2.add(seasonTypeBox);
		f2.add(new JLabel("       "));
		// ----DataType---------
		JLabel typeLbl = new MyJLabel("数据类型：");
		f2.add(typeLbl);
		String[] typeText = { "赛季", "场均" };
		typeBox = new MyComboBox(typeText);
		typeBox.addItemListener(this);
		f2.add(typeBox);
		f2.add(new JLabel("       "));
		// ------fieldLbl--------
		fieldLbl = new MyJLabel("查看高阶数据");
		fieldLbl.addMouseListener(this);
		fieldLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		f2.add(fieldLbl);
		f2.add(new JLabel("       "));
		// -----refreshLbl------
		refreshLbl = new MyJLabel("刷新", new ImageIcon("image/refreshWhite.png"));
		refreshLbl.addMouseListener(this);
		refreshLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		f2.add(refreshLbl);
		f2.add(new JLabel("       "));
		// -----modeLbl---------
		modeLbl = new MyJLabel("至快速查询模式", new ImageIcon(
				"image/player/headmode.png"));
		modeLbl.addMouseListener(this);
		modeLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		f2.add(modeLbl);
		// ----jsp--------------
		// table = new MySortableTable(ptm, 0);
		table = new JTable(ptm);
		ts = new TableSorter(table.getModel(), table.getTableHeader());
		table.setModel(ts);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		titleBar.setCurrentTableModel(ptm);
		titleBar.setModelEnum(TableModel.PLAYERRANK);
		titleBar.setTable(table);

		gbc.insets = new Insets(0, 2, 1, 2);
		jsp = new JScrollPane();
		jsp.getViewport().add(table);
		gbc.gridy = 2;
		gbc.gridheight = 10;
		gbc.weighty = 10;
		gbl.setConstraints(jsp, gbc);
		add(jsp);

		ptm.Refresh((String) seasonBox.getSelectedItem(),
				(String) seasonTypeBox.getSelectedItem(),
				(String) typeBox.getSelectedItem());
		CellRender();

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
		titleBar.setSeasonType(seasonTypeBox.getSelectedItem().toString());

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

			this.setForeground(Color.white);
			this.setBackground(Style.BACK_GREY);

		}

	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			if (e.getClickCount() == 2) {
				int row = table.getSelectedRow();
				String name = table.getValueAt(row, 0).toString();
				MainFrame.getInstance().setContentPanel(
						new PlayerDetailPanel(name));
			}
		} else if (e.getSource() == modeLbl)
			MainFrame.getInstance().setContentPanel(new PlayerIndexPanel());
		else if (e.getSource() == filterLbl) {
			Filter();
		} else if (e.getSource() == fieldLbl) {
			String type = typeBox.getSelectedItem().toString();
			filterRankBox.removeItemListener(this);
			if (isHighInfo) {
				// 监听，切换到基础数据表格
				fieldLbl.setText("查看高阶数据");
				isHighInfo = false;
				ptm = new PlayerTableModel(0);
				filterRankBox.removeAll();
				filterRankBox.removeAllItems();
				for (int i = 0; i < filterRankText.length; i++) {
					filterRankBox.addItem(filterRankText[i]);
				}

			} else {
				// 监听，切换到高阶数据表格
				fieldLbl.setText("查看基础数据");
				isHighInfo = true;
				ptm = new PlayerTableModel(1);
				filterRankBox.removeAllItems();
				for (int i = 0; i < filterRankText2.length; i++) {
					filterRankBox.addItem(filterRankText2[i]);
				}

			}
			filterRankBox.addItemListener(this);
			ptm.Refresh((String) seasonBox.getSelectedItem(),
					(String) seasonTypeBox.getSelectedItem(), type);

			table.revalidate();
			table.repaint();
			jsp.remove(table);
			table = new JTable(ptm);
			ts = new TableSorter(table.getModel(), table.getTableHeader());
			table.setModel(ts);
			titleBar.setCurrentTableModel(ptm);
			// table.addMouseListener(this);
			jsp.getViewport().add(table);
			// table.getTableHeader().addMouseListener(listen);
			CellRender();

		} else if (e.getSource() == refreshLbl) {
			ptm.Refresh((String) seasonBox.getSelectedItem(),
					(String) seasonTypeBox.getSelectedItem(),
					(String) typeBox.getSelectedItem());
			table.revalidate();
			table.repaint();
			tcr.setHighlightColumn(-1);
			ts.cancelSorting();
			lastcolumn = -1;
			clicktime = 0;
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
		} else if (e.getSource() == filterLbl) {
			filterLbl.setForeground(Style.FOCUS_BLUE);
			filterLbl.setIcon(new ImageIcon("image/player/filterFocus.png"));
		} else if (e.getSource() == modeLbl) {
			modeLbl.setForeground(Style.FOCUS_BLUE);
			modeLbl.setIcon(new ImageIcon("image/player/headmodeBlue.png"));
		} else if (e.getSource() == fieldLbl) {
			fieldLbl.setForeground(Style.FOCUS_BLUE);
		}
	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == refreshLbl) {
			refreshLbl.setForeground(Color.white);
			refreshLbl.setIcon(new ImageIcon("image/refreshWhite.png"));
		} else if (e.getSource() == filterLbl) {
			filterLbl.setForeground(Color.white);
			filterLbl.setIcon(new ImageIcon("image/player/filterWhite.png"));
		} else if (e.getSource() == modeLbl) {
			modeLbl.setForeground(Color.white);
			modeLbl.setIcon(new ImageIcon("image/player/headmode.png"));
		} else if (e.getSource() == fieldLbl) {
			fieldLbl.setForeground(Color.white);
		}
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (e.getSource() == typeBox || e.getSource() == seasonBox
					|| e.getSource() == seasonTypeBox) {
				String season = seasonBox.getSelectedItem().toString();
				String seasontype = (String) seasonTypeBox.getSelectedItem();
				ArrayList<PlayerVO> vlist;
				String s = (String) typeBox.getSelectedItem();
				if (s.equals("赛季"))
					vlist = player.getPlayerSeasonInfo(season, seasontype);
				else
					vlist = player.getPlayerAverageInfo(season,seasontype);
				// vlist.size()==0显示没有符合条件的球员
				if (vlist != null) {
					if (isHighInfo)
						ptm.refreshHigh(vlist);
					else
						ptm.refreshBase(vlist);

				}
				tcr.setHighlightColumn(-1);
				ts.cancelSorting();
				table.revalidate();
				table.repaint();
				titleBar.setSeason(season);
				titleBar.setAveOrAll(s);
			} else if (e.getSource() == locationBox
					|| e.getSource() == partitionBox
					|| e.getSource() == filterRankBox) {
				Filter();
			}

		}
	}

	public void CellRender() {
		table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
		table.setFont(new Font("微软雅黑", 0, 12));
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
		table.getTableHeader().setBackground(new Color(211, 211, 211));
		tcr = new MyTableCellRenderer();
		// tcr.setHighlightColumn(1);
		MyTableCellRenderer.adjustTableColumnWidths(table);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(Style.FOCUS_BLUE);

		table.getTableHeader().addMouseListener(listen);
		table.addMouseListener(this);
		table.revalidate();
		table.repaint();

	}

	class highlisten extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			int col = table.getTableHeader().columnAtPoint(e.getPoint());
			if (lastcolumn == -1 || lastcolumn != col) {
				lastcolumn = col;
				clicktime = 1;
				tcr.setHighlightColumn(col);

			} else {
				clicktime++;
				if (clicktime == 3) {
					tcr.setHighlightColumn(-1);
					lastcolumn = -1;
					clicktime = 0;
				}

			}

		}
	}

	public void Filter() {
//		int time = 0;
//		if (timeField.getText() != null || !(timeField.getText().equals(""))
//				|| timeField.getText() != "") {
//
//			try {
//				time = Integer.parseInt(timeField.getText());
//			} catch (NumberFormatException nfe) {
//				time = 0;
//			}
//		}
//		lastTime = time;
		// 执行筛选
		String season = seasonBox.getSelectedItem().toString();
		String position = locationBox.getSelectedItem().toString();
		String union = partitionBox.getSelectedItem().toString();
		String sort = filterRankBox.getSelectedItem().toString();
		ArrayList<PlayerVO> vlist=new ArrayList<PlayerVO>();
		String type = typeBox.getSelectedItem().toString();
		//王宁，如果看到这句话，记得把界面上的在场时间大于_分钟的那个筛选去掉。。。感叹号以显注意！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
		if (type.equals("赛季"))
			vlist = player.selectPlayersBySeason(season,
					(String) seasonTypeBox.getSelectedItem(), position, union, sort, "desc", 50);
		else
			vlist = player.selectPlayersByAverage(season,
					(String) seasonTypeBox.getSelectedItem(), position, union, sort, "desc", 50);
		// vlist.size()==0显示没有符合条件的球
		if (ptm.headmodel != 0) {
			if (isHighInfo == false)
				ptm = new PlayerTableModel(0);
			else
				ptm = new PlayerTableModel(1);
			jsp.remove(table);
			table = new JTable(ptm);

			ts = new TableSorter(table.getModel(), table.getTableHeader());
			table.setModel(ts);

			// table.addMouseListener(this);
			jsp.getViewport().add(table);

		}
		if (vlist != null) {
			if (isHighInfo == false)
				ptm.refreshBase(vlist);
			else
				ptm.refreshHigh(vlist);
		}
		table.revalidate();

		table.repaint();

		int col = ptm.findColumn(sort);

		lastcolumn = col;
		clicktime = 0;
		CellRender();
		tcr.setHighlightColumn(col);
	}
}
