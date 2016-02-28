package newui.teamui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

import newui.FatherPanel;
import newui.Service;
import newui.Style;
import newui.TableModel;
import newui.mainui.MainFrame;
import newui.tables.MyTableCellRenderer;
import newui.tables.RowHeaderTable;
import newui.tables.TableSorter;
import newui.tables.TeamTableModel;
import blService.MatchBLService;

public class TeamIndexPanel extends FatherPanel implements MouseListener {
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
	JLabel refreshLbl, fieldLbl;
	JComboBox<String> seasonBox, seasonTypeBox, typeBox;
	Font font = new Font("微软雅黑", Font.PLAIN, 13);
	boolean isHighInfo = false;
	int clicktime = 0;// 升序 降序 恢复
	int lastcolumn = -1;
	MyTableCellRenderer tcr;
	highlisten listen;

	TableSorter ts;

	public TeamIndexPanel() {
		super();
		listen = new highlisten();
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
		MatchBLService match = Service.match;
		ArrayList<String> seasons = match.getAllSeasons();
		int size = seasons.size();
		String[] seasonBoxText = (String[]) match.getAllSeasons().toArray(
				new String[size]);
		seasonBox = new MyComboBox(seasonBoxText);
		funcPnl.add(seasonBox);
		// ------seasonType------
		String[] seasonTypeBoxText = { "季后赛 ","常规赛",  "季前赛" };
		seasonTypeBox = new MyComboBox(seasonTypeBoxText);

		funcPnl.add(seasonTypeBox);
		funcPnl.add(new JLabel("       "));
		// ----DataType---------
		JLabel typeLbl = new MyJLabel("数据类型：");
		funcPnl.add(typeLbl);
		String[] typeText = { "赛季", "场均" };
		typeBox = new MyComboBox(typeText);
		funcPnl.add(typeBox);
		funcPnl.add(new JLabel("       "));
		// ------fieldLbl--------
		fieldLbl = new MyJLabel("查看高阶数据");
		fieldLbl.addMouseListener(this);
		fieldLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		funcPnl.add(fieldLbl);
		funcPnl.add(new JLabel("       "));
		// -----refreshLbl------
		refreshLbl = new MyJLabel("刷新", new ImageIcon("image/refreshWhite.png"));
		refreshLbl.addMouseListener(this);
		refreshLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		funcPnl.add(refreshLbl);
		// -----table-----------
		ttm = new TeamTableModel(0);
		// table = new MySortableTable(ttm, 1);
		table = new JTable(ttm);
		ts = new TableSorter(table.getModel(), table.getTableHeader());
		table.setModel(ts);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		table.setShowGrid(false);

		titleBar.setCurrentTableModel(ttm);
		titleBar.setModelEnum(TableModel.TEAMRANK);
		titleBar.setTable(table);
		gbc.insets = new Insets(0, 2, 1, 2);
		jsp = new JScrollPane(table);

		jsp.getViewport().setOpaque(false);
		jsp.setOpaque(false);

		gbc.gridy = 2;
		gbc.gridheight = 10;
		gbc.weighty = 10;
		gbl.setConstraints(jsp, gbc);
		add(jsp);

		// ====初始化数据=====
		String type = typeBox.getSelectedItem().toString();
		String season = seasonBox.getSelectedItem().toString();
		String seasonType = seasonTypeBox.getSelectedItem().toString();
		ttm.Refresh(season, seasonType, type);
		table.revalidate();
		table.repaint();
		CellRender();
		MyTableCellRenderer.adjustTableColumnWidths(table);// 自动设置列宽

		jsp.setRowHeaderView(new RowHeaderTable(table, 20));

		JLabel jb = new JLabel();
		jb.setOpaque(true);
		jb.setBackground(Color.black);
		jb.setBorder(BorderFactory.createLineBorder(new Color(158, 158, 158),
				20));
		jsp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, jb);
		titleBar.setSeason(seasonBox.getSelectedItem().toString());
		titleBar.setSeasonType(seasonTypeBox.getSelectedItem().toString());
		titleBar.setAveOrAll(typeBox.getSelectedItem().toString());

		seasonBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String type = typeBox.getSelectedItem().toString();
				String season = seasonBox.getSelectedItem().toString();
				String seasonType = seasonTypeBox.getSelectedItem().toString();
				ttm.Refresh(season, seasonType, type);
				table.revalidate();
				table.repaint();

			}

		});
		seasonTypeBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String type = typeBox.getSelectedItem().toString();
				String season = seasonBox.getSelectedItem().toString();
				String seasonType = seasonTypeBox.getSelectedItem().toString();
				ttm.Refresh(season, seasonType, type);
				//可以动态的增加表格的行，但是表头大小会变==
				ttm.fireTableStructureChanged();// JTable刷新结构
				ttm.fireTableDataChanged();// 刷新JTable数据
				table.revalidate();
				table.repaint();
				
//				int headModel=ttm.getHeadModel();
//				ttm=new TeamTableModel(headModel);
//				ttm.Refresh(season, seasonType, type);
//				table.setModel(ttm);
//				table.revalidate();
//				table.repaint();
				CellRender();
				MyTableCellRenderer.adjustTableColumnWidths(table);// 自动设置列宽
			}
		});
		typeBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String type = typeBox.getSelectedItem().toString();
				String season = seasonBox.getSelectedItem().toString();
				String seasonType = seasonTypeBox.getSelectedItem().toString();
				ttm.Refresh(season, seasonType, type);
				table.revalidate();
				table.repaint();

			}

		});
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == refreshLbl) {
			String type = typeBox.getSelectedItem().toString();
			String season = seasonBox.getSelectedItem().toString();
			String seasonType = seasonTypeBox.getSelectedItem().toString();
			ttm.Refresh(season, seasonType, type);
			table.revalidate();
			table.repaint();
			tcr.setHighlightColumn(-1);
			ts.cancelSorting();
			lastcolumn = -1;

			clicktime = 0;
		}

		else if (e.getSource() == table) {
			if (e.getClickCount() == 2) {
				int row = table.getSelectedRow();
				String tname = table.getValueAt(row, 1).toString();
				MainFrame.getInstance().setContentPanel(
						new TeamDetailPanel(tname));
			}
		} else if (e.getSource() == fieldLbl) {
			String season = seasonBox.getSelectedItem().toString();
			String seasonType = seasonTypeBox.getSelectedItem().toString();
			String type = typeBox.getSelectedItem().toString();
			if (isHighInfo) {
				// 监听,切换到基础数据表格
				fieldLbl.setText("查看高阶数据");
				isHighInfo = false;
				ttm = new TeamTableModel(0);

			} else {
				// 监听,切换到高阶数据表格
				fieldLbl.setText("查看基础数据");
				isHighInfo = true;
				ttm = new TeamTableModel(1);

			}

			ttm.Refresh(season, seasonType, type);
			table.revalidate();
			table.repaint();
			;
			jsp.remove(table);
			table = new JTable(ttm);
			ts = new TableSorter(table.getModel(), table.getTableHeader());
			table.setModel(ts);

			// table.getTableHeader().addMouseListener(listen);
			titleBar.setCurrentTableModel(ttm);
			titleBar.setSeason(season);
			titleBar.setSeasonType(seasonType);

			jsp.getViewport().add(table);
			CellRender();
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
		}
		if (e.getSource() == fieldLbl) {
			fieldLbl.setForeground(Style.FOCUS_BLUE);
		}
	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == refreshLbl) {
			refreshLbl.setForeground(Color.white);
			refreshLbl.setIcon(new ImageIcon("image/refreshWhite.png"));
		}
		if (e.getSource() == fieldLbl) {
			fieldLbl.setForeground(Color.white);
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
			this.setBackground(Style.BACK_GREY);
			this.setForeground(Color.white);
		}
	}

	public void CellRender() {
		table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
		table.setFont(new Font("微软雅黑", 0, 12));
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(Style.FOCUS_BLUE);
		tcr = new MyTableCellRenderer();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}
		// table.getTableHeader().setBackground(new Color(158, 158, 158));

		table.getTableHeader().addMouseListener(listen);
		table.addMouseListener(this);

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
}
