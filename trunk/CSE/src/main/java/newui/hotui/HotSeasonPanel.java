package newui.hotui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import newui.Service;
import newui.Style;
import newui.mainui.MainFrame;
import newui.playerui.PlayerDetailPanel;
import newui.tables.HotTableModel;
import newui.tables.MyTableCellRenderer;
import newui.teamui.TeamDetailPanel;
import vo.PlayerVO;
import blservice.PlayerBLService;

public class HotSeasonPanel extends HotFatherPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	// ---bestPnl------------
	JLabel bestHead, bestName, bestTeamIcon, positionAndTeamName, data;
	// --剩余四人的表格---------
	/**
	 * 需要新的表格及TableModel jsp已经在HotFatherPanel里建好了，这里只要table和tableModel
	 * 表头：排名（2，3，4，5），头像，球员名，所属球队，位置，比赛数据
	 */
	// ------bottomBar-----
	BottomButton scoreBtn, reboundBtn, assistBtn, blockBtn, stealBtn,
			threeRateBtn, shootRateBtn, freeRateBtn;
	String[] head = { "排名", "", "球员名称", "所属球队", "位置", "场均得分" };
	ArrayList<PlayerVO> vlist;
	PlayerBLService player;
	BottomButton currentBtn;
	JTable table;
	HotSeasonModel model;

	
	public HotSeasonPanel() {
	
		
		player = Service.player;
		GridBagLayout bl = new GridBagLayout();
		GridBagConstraints bc = new GridBagConstraints();
		bc.fill = GridBagConstraints.BOTH;
		bestPnl.setLayout(bl);
		// -------bestPnl--------------
		bestHead = new JLabel();
		// 有需要就加上bestHead.setPreferredSize(new Dimension(width, height));
		bc.gridx = 0;
		bc.gridy = 0;
		bc.gridwidth = 2;
		bc.gridheight = 5;
		bc.weightx = 2;
		bc.weighty = 5;
		bl.setConstraints(bestHead, bc);
		bestPnl.add(bestHead);
		bestHead.setToolTipText("点击查看球员详情");
		bestHead.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				MainFrame.getInstance().setContentPanel(new PlayerDetailPanel(bestName.getText()));
			}
		});
		// ------------
		JPanel midPnl = new JPanel();
		midPnl.setOpaque(false);
		bc.gridx = 2;
		bc.gridwidth = 5;
		bc.weightx = 5;
		bl.setConstraints(midPnl, bc);
		bestPnl.add(midPnl);
		midPnl.setLayout(new GridLayout(2, 1));
		bestName = new JLabel();
		bestName.setHorizontalAlignment(JLabel.CENTER);
		bestName.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		midPnl.add(bestName);
		positionAndTeamName = new JLabel();
		positionAndTeamName.setHorizontalAlignment(JLabel.CENTER);
		positionAndTeamName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		midPnl.add(positionAndTeamName);
		// -------------
		data = new JLabel();
		data.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		data.setForeground(Style.BACK_GREY);
		bc.gridx = 7;
		bc.gridwidth = 1;
		bc.weightx = 8;
		bl.setConstraints(data, bc);
		bestPnl.add(data);
		// ------------
		bestTeamIcon = new JLabel();
		bc.gridx = 8;
		bc.gridwidth = 2;
		bc.weightx = 2;
		bl.setConstraints(bestTeamIcon, bc);
		bestPnl.add(bestTeamIcon);
		bestTeamIcon.setToolTipText("点击查看球队详情");
		bestTeamIcon.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				MainFrame.getInstance().setContentPanel(
						new TeamDetailPanel(positionAndTeamName.getText().split("/")[1]));
			}
		});
		bestTeamIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bestHead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// ------------
		bottomBar.setLayout(new GridLayout(1, 8));
		// ------------
		scoreBtn = new BottomButton("场均得分");
		scoreBtn.setBackground(Style.HOT_REDFOCUS);
		scoreBtn.addMouseListener(this);
		bottomBar.add(scoreBtn);
		currentBtn = scoreBtn;
		// -----------
		reboundBtn = new BottomButton("场均篮板");
		reboundBtn.setBackground(Style.HOT_RED);
		reboundBtn.addMouseListener(this);
		bottomBar.add(reboundBtn);
		// -----------
		assistBtn = new BottomButton("场均助攻");
		assistBtn.setBackground(Style.HOT_RED);
		assistBtn.addMouseListener(this);
		bottomBar.add(assistBtn);
		// -----------
		blockBtn = new BottomButton("场均盖帽");
		blockBtn.setBackground(Style.HOT_RED);
		blockBtn.addMouseListener(this);
		bottomBar.add(blockBtn);
		// ----------
		stealBtn = new BottomButton("场均抢断");
		stealBtn.setBackground(Style.HOT_RED);
		stealBtn.addMouseListener(this);
		bottomBar.add(stealBtn);
		// -----------
		threeRateBtn = new BottomButton("三分命中率");
		threeRateBtn.setBackground(Style.HOT_RED);
		threeRateBtn.addMouseListener(this);
		bottomBar.add(threeRateBtn);
		// -----------
		shootRateBtn = new BottomButton("投篮命中率");
		shootRateBtn.setBackground(Style.HOT_RED);
		shootRateBtn.addMouseListener(this);
		bottomBar.add(shootRateBtn);
		// -----------
		freeRateBtn = new BottomButton("罚球命中率");
		freeRateBtn.setBackground(Style.HOT_RED);
		freeRateBtn.addMouseListener(this);
		bottomBar.add(freeRateBtn);
		// ====
		model = new HotSeasonModel(head);
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
		thr=new HotThread(this,"score");
		Refresh("score");
		thr.startThread();
		
	}

	public void Refresh(String sort) {
		// 默认筛选 按得分
		// 目前只有一个赛季
		vlist = player.getSeasonHotPlayer("13-14", sort, 5);
		if (vlist != null && vlist.size() != 0) {
			model.setHead(head);
			PlayerVO topOne = vlist.get(0);
			bestHead.setIcon(new ImageIcon("image/player/portrait/"
					+ topOne.getName() + ".png"));
			bestName.setText(topOne.getName());
			positionAndTeamName.setText(topOne.getPosition() + "/"
					+ topOne.getOwingTeam());
			// data.setText(topOne.getScore()+"");
			bestTeamIcon.setIcon(new ImageIcon("image/teamIcon/teamsPng150/"
					+ topOne.getOwingTeam() + ".png"));
			if(sort.equals("score")){
				data.setText(topOne.getScore() + "");
			}else if(sort.equals("reboundNum")){
				data.setText(topOne.getReboundNum() + "");
			}else if(sort.equals("assistNum")){
				data.setText(topOne.getAssistNum() + "");
			}else if(sort.equals("blockNum")){
				data.setText(topOne.getBlockNum() + "");
			}else if(sort.equals("stealNum")){
				data.setText(topOne.getStealNum() + "");
			}else if(sort.equals("threeHitRate")){
				data.setText(topOne.getThreeHitRate() + "");
			}else if(sort.equals("shootHitRate")){
				data.setText(topOne.getShootHitRate() + "");
			}else {
				data.setText(topOne.getFreeThrowHitRate() + "");
			}
			model.Refresh(vlist);
			table.revalidate();
			jsp.getViewport().remove(table);
			table = new JTable(model);
			//是否有重复加监听的嫌疑
			table.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					if(e.getClickCount()==2){
						int row=table.getSelectedRow();
						String name=table.getValueAt(row,2).toString();
						MainFrame.getInstance().setContentPanel(new PlayerDetailPanel(name));
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

	// 返回的是场均数据吗

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		currentBtn.setBackground(Style.HOT_RED);
		BottomButton m = (BottomButton) e.getSource();
		if(thr!=null){
			//while(!thr.stop)
				thr.stopThead();	
		}
		if (m == scoreBtn) {
			head[5] = "场均得分";
			currentBtn = scoreBtn;
			Refresh("score");
			thr=new HotThread(HotSeasonPanel.this,"score");
		} else if (m == reboundBtn) {
			head[5] = "场均篮板";
			currentBtn = reboundBtn;
			Refresh("reboundNum");
			thr=new HotThread(HotSeasonPanel.this,"reboundNum");	
		} else if (m == assistBtn) {
			head[5] = "场均助攻";
			currentBtn = assistBtn;
			Refresh("assistNum");
			thr=new HotThread(HotSeasonPanel.this,"assistNum");

		} else if (m == blockBtn) {
			head[5] = "场均盖帽";
			currentBtn = blockBtn;
			Refresh("blockNum");
			thr=new HotThread(HotSeasonPanel.this,"blockNum");

		} else if (m == stealBtn) {
			head[5] = "场均抢断";
			currentBtn = stealBtn;
			Refresh("stealNum");
			thr=new HotThread(HotSeasonPanel.this,"stealNum");
			
		
		} else if (m == threeRateBtn) {
			head[5] = "三分命中率";
			currentBtn = threeRateBtn;
			Refresh("threeHitRate");
			thr=new HotThread(HotSeasonPanel.this,"threeHitRate");
			
		
		} else if (m == shootRateBtn) {
			head[5] = "投篮命中率";
			currentBtn = shootRateBtn;
			Refresh("shootHitRate");
			thr=new HotThread(HotSeasonPanel.this,"shootHitRate");
		
			
		} else {
			head[5] = "罚球命中率";
			currentBtn = freeRateBtn;
			Refresh("freeThrowHitRate");
			thr=new HotThread(HotSeasonPanel.this,"freeThrowHitRate");
			
		
		}
		currentBtn.setBackground(Style.HOT_REDFOCUS);
		//需等待四s 再开启
		thr.startThread();
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
			btn.setBackground(Style.HOT_REDFOCUS);
		}

	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource().getClass() == BottomButton.class) {
			BottomButton btn = (BottomButton) e.getSource();
			if (currentBtn != btn)
				btn.setBackground(Style.HOT_RED);
		}

	}

	class HotSeasonModel extends HotTableModel {
		public HotSeasonModel(String[] head) {
			super(head);
		}

		public void Refresh(ArrayList<PlayerVO> vlist) {
			content.clear();
			num = 2;
			for (int i = 1; i < vlist.size(); i++) {
				PlayerVO v = vlist.get(i);
				ArrayList<Object> line = new ArrayList<Object>();
				line.add(num);
				num++;
				ImageIcon tou = new ImageIcon("image/player/portrait/"
						+ v.getName() + ".png");
				// 设置宽高
				ImageIcon icon = new ImageIcon(
						tou.getImage()
								.getScaledInstance(
								/*
								 * table.getColumn(table.getColumnName(0))
								 * .getWidth()
								 */100, 80
								/* currentTable.getRowHeight(i) */,
										Image.SCALE_DEFAULT));

				line.add(icon);
				line.add(v.getName());
				line.add(v.getOwingTeam());
				line.add(v.getPosition());
				if (currentBtn == scoreBtn) {
					line.add(v.getScore());
				} else if (currentBtn == reboundBtn)
					line.add(v.getReboundNum());
				else if (currentBtn == assistBtn)
					line.add(v.getAssistNum());
				else if (currentBtn == blockBtn)
					line.add(v.getBlockNum());
				else if (currentBtn == stealBtn)
					line.add(v.getStealNum());
				else if (currentBtn == threeRateBtn)
					line.add(v.getThreeHitRate());
				else if (currentBtn == shootRateBtn)
					line.add(v.getShootHitRate());
				else
					line.add(v.getFreeThrowHitRate());
				content.add(line);
			}

		}
	}
	
	
}
