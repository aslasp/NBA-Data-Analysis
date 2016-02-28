package newui.hotui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import newui.MyUIDataFormater;
import newui.Service;
import newui.Style;
import newui.mainui.MainFrame;
import newui.tables.HotTableModel;
import newui.tables.MyTableCellRenderer;
import newui.teamui.TeamDetailPanel;
import vo.TeamVO;
import bl.Team;
import blService.MatchBLService;
import blService.TeamBLService;

public class HotTeamPanel extends HotFatherPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	JLabel teamIcon, unionLbl, teamNameLbl, abbrNameLbl, data;
	// --剩余四人的表格---------
	/**
	 * 需要新的表格及TableModel jsp已经在HotFatherPanel里建好了，这里只要table和tableModel
	 * 表头：排名（2，3，4，5），teamIcon，球队全名，缩写，所属联盟，比赛数据
	 */
	// ------bottomBar-----
	BottomButton scoreBtn, reboundBtn, assistBtn, blockBtn, stealBtn,
			threeRateBtn, shootRateBtn, freeRateBtn, currentBtn;
	// ---------------------

	JComboBox<String> seasonBox, seasonTypeBox;
	// ---------------------
	String[] head = { "排名", "", "球队名称", "球队缩写", "所属联盟", "场均得分" };
	JTable table;
	HotTeamModel model;
	TeamBLService team;
	ArrayList<TeamVO> vlist;

	private String[] sortsCH = { "场均得分", "场均篮板", "场均助攻", "场均盖帽", "场均抢断",
			"三分命中率", "投篮命中率", "罚球命中率" };
	private String[] sortsEN = { "score", "reboundNum", "assistNum",
			"blockNum", "stealNum", "threeHitRate", "shootHitRate",
			"freeThrowHitRate" };

	public HotTeamPanel() {

		team = Service.team;
		GridBagLayout bl = new GridBagLayout();
		GridBagConstraints bc = new GridBagConstraints();
		bc.fill = GridBagConstraints.BOTH;
		bestPnl.setLayout(bl);
		// ---seasonBox-----
		JLabel seasonLbl = new JLabel("赛季");
		seasonLbl.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		seasonLbl.setForeground(Style.DEEP_BLUE);
		seasonPnl.add(seasonLbl);
		//
		MatchBLService match = Service.match;
		ArrayList<String> allSeason = new ArrayList<String>();
		allSeason = match.getAllSeasons();
		int size = allSeason.size();
		String[] season = (String[]) allSeason.toArray(new String[size]);
		seasonBox = new JComboBox<String>(season);
		seasonBox.setBackground(Color.white);
		seasonBox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		seasonBox.setForeground(Style.DEEP_BLUE);
		seasonPnl.add(seasonBox);
		// ----seasonTypeBox------
		String[] seasonType = { "季后赛 ", "常规赛", "季前赛" };
		seasonTypeBox = new JComboBox<String>(seasonType);
		seasonTypeBox.setBackground(Color.white);
		seasonTypeBox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		seasonTypeBox.setForeground(Style.DEEP_BLUE);
		seasonPnl.add(seasonTypeBox);
		// -------bestPnl--------------
		teamIcon = new JLabel();
		// 有需要就加上teamIcon.setPreferredSize(new Dimension(width, height));
		bc.gridx = 0;
		bc.gridy = 0;
		bc.gridwidth = 2;
		bc.gridheight = 5;
		bc.weightx = 2;
		bc.weighty = 5;
		bl.setConstraints(teamIcon, bc);
		bestPnl.add(teamIcon);
		teamIcon.setToolTipText("点击查看球队详情");
		teamIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				MainFrame.getInstance().setContentPanel(
						new TeamDetailPanel(Team
								.changeTeamNameCHToEN(teamNameLbl.getText())));
			}
		});
		teamIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// ------------
		JPanel midPnl = new JPanel();
		midPnl.setOpaque(false);
		bc.gridx = 2;
		bc.gridwidth = 5;
		bc.weightx = 5;
		bl.setConstraints(midPnl, bc);
		bestPnl.add(midPnl);
		midPnl.setLayout(new GridLayout(3, 1));
		teamNameLbl = new JLabel();
		teamNameLbl.setHorizontalAlignment(JLabel.CENTER);
		teamNameLbl.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		midPnl.add(teamNameLbl);
		abbrNameLbl = new JLabel();
		abbrNameLbl.setHorizontalAlignment(JLabel.CENTER);
		abbrNameLbl.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		midPnl.add(abbrNameLbl);
		unionLbl = new JLabel();
		unionLbl.setHorizontalAlignment(JLabel.CENTER);
		unionLbl.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		midPnl.add(unionLbl);
		// -------------
		data = new JLabel();
		data.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		data.setForeground(Style.BACK_GREY);
		bc.gridx = 7;
		bc.gridwidth = 1;
		bc.weightx = 8;
		bl.setConstraints(data, bc);
		bestPnl.add(data);
		// ----表格---------------

		// ------------
		bottomBar.setLayout(new GridLayout(1, 8));
		// ------------
		scoreBtn = new BottomButton("场均得分");
		scoreBtn.setBackground(Style.HOT_BLUEFOCUS);
		scoreBtn.addMouseListener(this);
		bottomBar.add(scoreBtn);
		currentBtn = scoreBtn;
		// -----------
		reboundBtn = new BottomButton("场均篮板");
		reboundBtn.setBackground(Style.HOT_BLUE);
		reboundBtn.addMouseListener(this);
		bottomBar.add(reboundBtn);
		// -----------
		assistBtn = new BottomButton("场均助攻");
		assistBtn.setBackground(Style.HOT_BLUE);
		assistBtn.addMouseListener(this);
		bottomBar.add(assistBtn);
		// -----------
		blockBtn = new BottomButton("场均盖帽");
		blockBtn.setBackground(Style.HOT_BLUE);
		blockBtn.addMouseListener(this);
		bottomBar.add(blockBtn);
		// -----------
		stealBtn = new BottomButton("场均抢断");
		stealBtn.setBackground(Style.HOT_BLUE);
		stealBtn.addMouseListener(this);
		bottomBar.add(stealBtn);
		// -----------
		threeRateBtn = new BottomButton("三分命中率");
		threeRateBtn.setBackground(Style.HOT_BLUE);
		threeRateBtn.addMouseListener(this);
		bottomBar.add(threeRateBtn);
		// -----------
		shootRateBtn = new BottomButton("投篮命中率");
		shootRateBtn.setBackground(Style.HOT_BLUE);
		shootRateBtn.addMouseListener(this);
		bottomBar.add(shootRateBtn);
		// -----------
		freeRateBtn = new BottomButton("罚球命中率");
		freeRateBtn.setBackground(Style.HOT_BLUE);
		freeRateBtn.addMouseListener(this);
		bottomBar.add(freeRateBtn);
		model = new HotTeamModel(head);
		table = new JTable(model);
		// table 渲染器，设置文字内容居中显示，设置背景色等
		table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
		table.setFont(new Font("微软雅黑", 0, 12));
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(Style.FOCUS_BLUE);
		DefaultTableCellRenderer tcr = new MyTableCellRenderer();
		table.getColumn(table.getColumnName(0)).setCellRenderer(tcr);
		for (int i = 2; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}
		jsp.getViewport().add(table);
		thr = new HotThread(this, "score");
		Refresh("score");
		// thr.startThread();

		seasonBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				Refresh(changeSortCHToEN(currentBtn.getText()));
				thr = new HotThread(HotTeamPanel.this, currentBtn.getText());
			}

		});
		seasonTypeBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				Refresh(changeSortCHToEN(currentBtn.getText()));
				thr = new HotThread(HotTeamPanel.this, currentBtn.getText());
			}
		});
	}

	public void Refresh(String sort) {
		vlist = team.getSeasonHotTeam((String) seasonBox.getSelectedItem(),
				(String) seasonTypeBox.getSelectedItem(), sort, 5);
		if (vlist != null && vlist.size() != 0) {
			model.setHead(head);
			TeamVO topOne = vlist.get(0);
			ImageIcon bestTeamIco = Team.getTeamImage(topOne.getAbLocation());
			bestTeamIco.setImage(bestTeamIco.getImage().getScaledInstance(160,
					160, Image.SCALE_SMOOTH));
			teamIcon.setIcon(bestTeamIco);
			teamNameLbl.setText(Team.changeTeamNameENToCH(topOne
					.getAbLocation()));
			abbrNameLbl.setText(topOne.getAbLocation());
			data.setText(topOne.getScore() + "");
			// 通过Team调用
			if (topOne.getConference().equals("E"))
				unionLbl.setText("东部联盟");
			else
				unionLbl.setText("西部联盟");
			if (sort.equals("score") || sort.equals("场均得分")) {
				data.setText(MyUIDataFormater.formatTo1(topOne.getScore()));
			} else if (sort.equals("reboundNum") || sort.equals("场均篮板")) {
				data.setText(MyUIDataFormater.formatTo1(topOne.getReboundNum()));
			} else if (sort.equals("assistNum") || sort.equals("场均助攻")) {
				data.setText(MyUIDataFormater.formatTo1(topOne.getAssistNum()));
			} else if (sort.equals("blockNum") || sort.equals("场均盖帽")) {
				data.setText(MyUIDataFormater.formatTo1(topOne.getBlockNum()));
			} else if (sort.equals("stealNum") || sort.equals("场均抢断")) {
				data.setText(MyUIDataFormater.formatTo1(topOne.getStealNum()));
			} else if (sort.equals("threeHitRate") || sort.equals("三分命中率")) {
				data.setText(MyUIDataFormater.formatTo1(topOne
						.getThreeHitRate() * 100));
			} else if (sort.equals("shootHitRate") || sort.equals("投篮命中率")) {
				data.setText(MyUIDataFormater.formatTo1(topOne
						.getShootHitRate() * 100));
			} else if (sort.equals("freeThrowHitRate") || sort.equals("罚球命中率")) {
				data.setText(MyUIDataFormater.formatTo1(topOne
						.getFreeThrowHitRate() * 100));
			} else {
				System.err.println("刷新内容不匹配，为:" + sort);
			}
			model.Refresh(vlist);
			table.revalidate();
			jsp.getViewport().remove(table);
			table = new JTable(model);
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						int row = table.getSelectedRow();
						String name = table.getValueAt(row, 3).toString();
						MainFrame.getInstance().setContentPanel(
								new TeamDetailPanel(name));
					}
				}
			});
			// table 渲染器，设置文字内容居中显示，设置背景色等
			table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
			table.setFont(new Font("微软雅黑", 0, 12));
			table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
			table.getTableHeader().setBackground(new Color(211, 211, 211));
			DefaultTableCellRenderer tcr = new MyTableCellRenderer();
			table.getColumn(table.getColumnName(0)).setCellRenderer(tcr);
			for (int i = 2; i < table.getColumnCount(); i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
			jsp.getViewport().add(table);

			jsp.repaint();
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		currentBtn.setBackground(Style.HOT_BLUE);
		BottomButton m = (BottomButton) e.getSource();
		if (thr != null)
			thr.stopThead();
		if (m == scoreBtn) {
			head[5] = "场均得分";
			currentBtn = scoreBtn;
			Refresh("score");
			thr = new HotThread(HotTeamPanel.this, "score");

		} else if (m == reboundBtn) {
			head[5] = "场均篮板";
			currentBtn = reboundBtn;
			Refresh("reboundNum");
			thr = new HotThread(HotTeamPanel.this, "reboundNum");

		} else if (m == assistBtn) {
			head[5] = "场均助攻";
			currentBtn = assistBtn;
			Refresh("assistNum");
			thr = new HotThread(HotTeamPanel.this, "assistNum");

		} else if (m == blockBtn) {
			head[5] = "场均盖帽";
			currentBtn = blockBtn;
			Refresh("blockNum");
			thr = new HotThread(HotTeamPanel.this, "blockNum");

		} else if (m == stealBtn) {
			head[5] = "场均抢断";
			currentBtn = stealBtn;
			Refresh("stealNum");
			thr = new HotThread(HotTeamPanel.this, "stealNum");

		} else if (m == threeRateBtn) {
			head[5] = "三分命中率";
			currentBtn = threeRateBtn;
			Refresh("threeHitRate");
			thr = new HotThread(HotTeamPanel.this, "threeHitRate");

		} else if (m == shootRateBtn) {
			head[5] = "投篮命中率";
			currentBtn = shootRateBtn;
			Refresh("shootHitRate");
			thr = new HotThread(HotTeamPanel.this, "shootHitRate");

		} else {
			head[5] = "罚球命中率";
			currentBtn = freeRateBtn;
			Refresh("freeThrowHitRate");
			thr = new HotThread(HotTeamPanel.this, "freeThrowHitRate");

		}
		currentBtn.setBackground(Style.HOT_BLUEFOCUS);
		// thr.startThread();
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource().getClass() == BottomButton.class) {
			BottomButton btn = (BottomButton) e.getSource();
			btn.setBackground(Style.HOT_BLUEFOCUS);
		}

	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource().getClass() == BottomButton.class) {
			BottomButton btn = (BottomButton) e.getSource();
			if (currentBtn != btn)
				btn.setBackground(Style.HOT_BLUE);
		}

	}

	private String changeSortCHToEN(String CH) {
		String EN=CH;
		for(int i=0;i<sortsCH.length;i++){
			if(sortsCH[i].equals(CH)){
				EN=sortsEN[i];
				break;
			}
		}
		return EN;
	}

	class HotTeamModel extends HotTableModel {
		public HotTeamModel(String[] head) {
			super(head);
		}

		public void Refresh(ArrayList<TeamVO> vlist) {
			content.clear();
			num = 2;
			for (int i = 1; i < vlist.size(); i++) {
				TeamVO v = vlist.get(i);
				ArrayList<Object> line = new ArrayList<Object>();
				line.add(num);
				num++;
				ImageIcon bestTeamIco = Team.getTeamImage(v.getAbLocation());
				bestTeamIco.setImage(bestTeamIco.getImage().getScaledInstance(
						63, 63, Image.SCALE_SMOOTH));
				// 设置宽高

				line.add(bestTeamIco);
				line.add(Team.changeTeamNameENToCH(v.getAbLocation()));
				line.add(v.getAbLocation());
				if (v.getConference().equals("E"))
					line.add("东部联盟");
				else
					line.add("西部联盟");
				if (currentBtn == scoreBtn) {
					line.add(MyUIDataFormater.formatTo1(v.getScore()));
				} else if (currentBtn == reboundBtn)
					line.add(MyUIDataFormater.formatTo1(v.getReboundNum()));
				else if (currentBtn == assistBtn)
					line.add(MyUIDataFormater.formatTo1(v.getAssistNum()));
				else if (currentBtn == blockBtn)
					line.add(MyUIDataFormater.formatTo1(v.getBlockNum()));
				else if (currentBtn == stealBtn)
					line.add(MyUIDataFormater.formatTo1(v.getStealNum()));
				else if (currentBtn == threeRateBtn)
					line.add(MyUIDataFormater.formatTo1(v.getThreeHitRate() * 100));
				else if (currentBtn == shootRateBtn)
					line.add(MyUIDataFormater.formatTo1(v.getShootHitRate() * 100));
				else
					line.add(MyUIDataFormater.formatTo1(v.getFreeThrowHitRate() * 100));
				content.add(line);
			}

		}
	}

}
