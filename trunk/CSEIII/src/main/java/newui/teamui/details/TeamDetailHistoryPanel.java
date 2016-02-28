package newui.teamui.details;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import newui.Service;
import newui.Style;
import newui.mainui.MainFrame;
import newui.playerui.PlayerDetailPanel;
import newui.tables.MyTableCellRenderer;
import newui.tables.TeamHistoryTableModel;
import bl.Match;

public class TeamDetailHistoryPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable table;
	TeamHistoryTableModel thtm;
	JScrollPane jsp;
	JPanel funcPnl;
	MyBox seasonBox, seasonTypeBox;
	MyBox typeBox;
	Font font = new Font("微软雅黑", Font.PLAIN, 15);

	public TeamDetailHistoryPanel(String abbrName) {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		setBackground(Color.white);
		// ------------------------
		funcPnl = new JPanel();
		funcPnl.setOpaque(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 1;
		gbc.weightx = 10;
		gbc.weighty = 0.1;
		gbl.setConstraints(funcPnl, gbc);
		add(funcPnl);
		// ------------------------
		MyLabel seasonLbl = new MyLabel("赛季：");
		funcPnl.add(seasonLbl);
		//
		ArrayList<String> seasons = new ArrayList<String>();
		seasons = Service.match.getAllSeasons();
		int size = seasons.size();
		String[] seasonText = (String[]) seasons.toArray(new String[size]);
		seasonBox = new MyBox(seasonText);
		funcPnl.add(seasonBox);
		//
		String[] seasonTypeBoxText = {  "季后赛 ","常规赛", "季前赛"  };
		seasonTypeBox = new MyBox(seasonTypeBoxText);
		funcPnl.add(seasonTypeBox);
		//
		funcPnl.add(new JLabel("              "));
		//
		MyLabel typeLbl = new MyLabel("数据类型：");
		funcPnl.add(typeLbl);
		//
		String[] typeText = { "场均", "赛季" };
		typeBox = new MyBox(typeText);
		funcPnl.add(typeBox);
		// ----------------------
		thtm = new TeamHistoryTableModel();
		table = new JTable(thtm);

		// table 渲染器，设置文字内容居中显示，设置背景色等
		table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
		table.setFont(new Font("微软雅黑", 0, 12));
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(Style.FOCUS_BLUE);
		DefaultTableCellRenderer tcr = new MyTableCellRenderer();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}

		gbc.insets = new Insets(0, 2, 1, 2);
		jsp = new JScrollPane(table);
		gbc.gridy = 1;
		gbc.gridheight = 10;
		gbc.weighty = 10;
		gbl.setConstraints(jsp, gbc);
		add(jsp);
		thtm.Refresh(abbrName);
		if (typeBox.getSelectedItem().toString().equals("场均")) {
			thtm.RefreshAverage((String)seasonBox.getSelectedItem(),(String) seasonTypeBox
					.getSelectedItem());
		} else {
			thtm.RefreshSeason(seasonBox.getSelectedItem().toString(),
					(String) seasonTypeBox.getSelectedItem());
		}
		table.revalidate();

		seasonBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (typeBox.getSelectedItem().toString().equals("场均")) {
					thtm.RefreshAverage((String)seasonBox.getSelectedItem(),(String) seasonTypeBox
							.getSelectedItem());
				} else {
					thtm.RefreshSeason(seasonBox.getSelectedItem().toString(),
							(String) seasonTypeBox.getSelectedItem());
				}
				table.revalidate();
				jsp.repaint();

			}

		});
		seasonTypeBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (typeBox.getSelectedItem().toString().equals("场均")) {
					thtm.RefreshAverage((String)seasonBox.getSelectedItem(),Match
							.changeTypeCHToEN((String) seasonTypeBox
									.getSelectedItem()));
				} else {
					thtm.RefreshSeason(seasonBox.getSelectedItem().toString(),
							Match.changeTypeCHToEN((String) seasonTypeBox
									.getSelectedItem()));
				}
				table.revalidate();
				jsp.repaint();

			}
		});

		typeBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (typeBox.getSelectedItem().toString().equals("场均")) {
					thtm.RefreshAverage((String)seasonBox.getSelectedItem(),(String) seasonTypeBox
							.getSelectedItem());
				} else {
					thtm.RefreshSeason(seasonBox.getSelectedItem().toString(),
							(String) seasonTypeBox.getSelectedItem());
				}
				table.revalidate();
				jsp.repaint();
			}

		});

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					String name = table.getValueAt(row, 0).toString();
					MainFrame.getInstance().setContentPanel(
							new PlayerDetailPanel(name));
				}
			}
		});
	}

	class MyBox extends JComboBox<String> {

		private static final long serialVersionUID = 1L;

		public MyBox(String[] arr) {
			super(arr);
			setFont(font);
			this.setBackground(Color.white);
			this.setForeground(Style.DEEP_BLUE);
		}
	}

	class MyLabel extends JLabel {

		private static final long serialVersionUID = 1L;

		public MyLabel(String text) {
			super(text);
			setFont(font);
		}
	}
}
