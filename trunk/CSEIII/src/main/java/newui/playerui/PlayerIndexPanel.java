package newui.playerui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
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
import newui.tables.MyTableCellRenderer;
import newui.tables.PlayerBaseInfoTableModel;
import bl.Team;

public class PlayerIndexPanel extends FatherPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel funcPnl;

	MyLabel refreshLbl, modeLbl;
	String[] teams = { "全部", "掘金", "森林狼", "雷霆", "开拓者", "爵士", "勇士", "快船", "湖人",
			"太阳", "国王", "小牛", "火箭", "灰熊", "鹈鹕", "马刺", "凯尔特人", "篮网", "尼克斯",
			"76人", "猛龙", "公牛", "骑士", "活塞", "步行者", "雄鹿", "老鹰", "黄蜂", "热火", "魔术",
			"奇才" };
	// --------------
	JScrollPane jsp;
	JTable table;
	PlayerBaseInfoTableModel pitm = new PlayerBaseInfoTableModel();
	// ---------------
	Font font = new Font("微软雅黑", Font.PLAIN, 15);
	// ---------------
	ArrayList<MyCharacter> characterLblList = new ArrayList<MyCharacter>(26);
	JComboBox<String> teamBox;
	MyCharacter currentLbl = null;

	public PlayerIndexPanel() {

		super();

		// ------funcPnl--------
		funcPnl = new JPanel();
		funcPnl.setBackground(Style.BACK_GREY);
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weighty = 0.8;
		gbl.setConstraints(funcPnl, gbc);
		add(funcPnl);
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 15, 0);
		funcPnl.setLayout(fl);
		// -----A至Z按钮----------
		for (int i = 0; i < 26; i++) {
			MyCharacter temp = new MyCharacter(i);
			characterLblList.add(temp);
			funcPnl.add(characterLblList.get(i));
		}

		// ----按球队查看---------
		funcPnl.add(new JLabel("       "));
		teamBox = new JComboBox<String>(teams);
		teamBox.setBackground(Style.BACK_GREY);
		teamBox.setForeground(Color.white);
		teamBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		teamBox.setMaximumRowCount(20);

		funcPnl.add(teamBox);
		// -----刷新--------------
		refreshLbl = new MyLabel("刷新", new ImageIcon("image/refreshWhite.png"));
		refreshLbl.addMouseListener(this);
		funcPnl.add(refreshLbl);
		// ------切换至列表排名模式----
		modeLbl = new MyLabel("至排名模式", new ImageIcon(
				"image/player/rankmode.png"));
		funcPnl.add(modeLbl);
		// ----jsp--------------
		table = new JTable(pitm);
		// table 渲染器，设置文字内容居中显示，设置背景色等
		table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
		table.setFont(new Font("微软雅黑", 0, 12));
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(Style.FOCUS_BLUE);
		DefaultTableCellRenderer tcr = new MyTableCellRenderer(
		/* pitm.getImgList() */);
		for (int i = 1; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}

		titleBar.setCurrentTableModel(pitm);
		titleBar.setModelEnum(TableModel.PLAYERBASEINFO);
		titleBar.setTable(table);
		gbc.insets = new Insets(0, 2, 1, 2);
		jsp = new JScrollPane(table);

		JLabel jb = new JLabel();
		jb.setOpaque(true);
		jb.setBackground(Color.black);
		jb.setBorder(BorderFactory.createLineBorder(new Color(158, 158, 158),
				20));
		jsp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, jb);
		// 刷新
		pitm.setCurrentTable(table);

		pitm.Refresh();
		table.revalidate();

		// 设置表头颜色
		table.getTableHeader().setBackground(new Color(158, 158, 158));
		table.addMouseListener(this);
		gbc.gridy = 2;
		gbc.gridheight = 10;
		gbc.weighty = 10;
		gbl.setConstraints(jsp, gbc);
		add(jsp);
		// ====按球队筛选监听====
		teamBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String teamName = Team.changeTeamNameCHToEN(teamBox
						.getSelectedItem().toString());
				pitm.findByTeam(teamName);
				table.revalidate();
				// table.repaint();
				if (currentLbl != null) {
					currentLbl.setOpaque(false);
					currentLbl.setBackground(Style.BACK_GREY);
					currentLbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				currentLbl = null;

			}

		});

	}

	class MyCharacter extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyCharacter(int i) {
			char character = (char) ('A' + i);
			String text = " " + character + " ";
			setText(text);
			setForeground(Color.white);
			setFont(font);
			addMouseListener(PlayerIndexPanel.this);
		}
	}

	class MyLabel extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyLabel(String text, ImageIcon img) {
			super(text, img, JLabel.LEFT);
			setFont(new Font("微软雅黑", Font.PLAIN, 13));
			setForeground(Color.white);
			addMouseListener(PlayerIndexPanel.this);
		}

	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == modeLbl)
			MainFrame.getInstance().setContentPanel(new PlayerRankPanel());
		if (e.getSource() instanceof MyCharacter) {
			if (currentLbl != null) {
				currentLbl.setOpaque(false);
				currentLbl.setBackground(Style.BACK_GREY);
				currentLbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			MyCharacter lbl = (MyCharacter) e.getSource();
			String sort = lbl.getText();
			sort = sort.trim();
			teamBox.setSelectedIndex(0);
			pitm.sortByCharacter(sort);
			table.revalidate();
			lbl.setOpaque(true);
			lbl.setBackground(Style.FOCUS_GREY);
			lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
			currentLbl = lbl;
		}
		if (e.getSource() == refreshLbl) {
			pitm.Refresh();
			table.revalidate();
			if (currentLbl != null) {
				currentLbl.setOpaque(false);
				currentLbl.setBackground(Style.BACK_GREY);
				currentLbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			currentLbl = null;

		}
		if (e.getSource() == table) {
			if (e.getClickCount() == 2) {
				int row = table.getSelectedRow();
				String pname = table.getValueAt(row, 1).toString();
				double pre = System.currentTimeMillis();
				MainFrame.setContentPanel(
						new PlayerDetailPanel(pname));
				double post = System.currentTimeMillis();
				System.out.println("playerindexto详情:" + (post - pre));
			}
		}

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource().getClass() == MyCharacter.class) {
			MyCharacter lbl = (MyCharacter) e.getSource();
			lbl.setOpaque(true);
			lbl.setBackground(Style.FOCUS_GREY);
			lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource() == refreshLbl) {
			refreshLbl.setForeground(Style.FOCUS_BLUE);
			refreshLbl.setIcon(new ImageIcon("image/refreshFocus.png"));
			refreshLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource() == modeLbl) {
			modeLbl.setForeground(Style.FOCUS_BLUE);
			modeLbl.setIcon(new ImageIcon("image/player/rankmodeBlue.png"));
			modeLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource().getClass() == MyCharacter.class) {
			MyCharacter lbl = (MyCharacter) e.getSource();
			if (lbl != currentLbl) {
				lbl.setOpaque(false);
				lbl.setBackground(Style.BACK_GREY);
				lbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
		if (e.getSource() == refreshLbl) {
			refreshLbl.setForeground(Color.white);
			refreshLbl.setIcon(new ImageIcon("image/refreshWhite.png"));
			refreshLbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource() == modeLbl) {
			modeLbl.setForeground(Color.white);
			modeLbl.setIcon(new ImageIcon("image/player/rankmode.png"));
			modeLbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
}
