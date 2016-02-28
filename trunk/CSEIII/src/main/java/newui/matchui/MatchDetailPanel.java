package newui.matchui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

import newui.FatherPanel;
import newui.Style;
import newui.mainui.MainFrame;
import newui.playerui.PlayerDetailPanel;
import newui.tables.MatchDetailModel;
import newui.tables.MyTableCellRenderer;
import vo.MatchVO;
import vo.RecordVO;

public class MatchDetailPanel extends FatherPanel {

	private static final long serialVersionUID = 1L;
	DetailCard card;
	// ------------------
	JPanel titlePnl, contentPnl;
	JLabel switchLbl, liveLbl;
	// ------------------
	JScrollPane jsp;
	JTable table;
	MatchDetailModel model;
	LiveTextPanel textpane;
	boolean isHome = true;
	ArrayList<RecordVO> Hrecord = new ArrayList<RecordVO>();
	ArrayList<RecordVO> Vrecord = new ArrayList<RecordVO>();;
	MatchVO vo;
	boolean isLive=false;
	public MatchDetailPanel(final MatchVO v) {
		vo = v;
		isDetail = true;
		// ----card------------
		card = new DetailCard(v);
		gbc.gridy = 1;
		gbc.gridheight = 4;
		gbc.weighty = 0.5;
		gbl.setConstraints(card, gbc);
		add(card);
		// ----titlePnl--------
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		fl.setHgap(20);
		titlePnl = new JPanel();
		titlePnl.setBackground(Style.DEEP_BLUE);
		titlePnl.setLayout(fl);
		gbc.gridy = 5;
		gbc.gridheight = 1;
		gbc.weighty = 0.5;
		gbl.setConstraints(titlePnl, gbc);
		add(titlePnl);
		//
		final JLabel detailTitle = new JLabel("技术统计 - " + v.getHomeTeam()
				+ "      ");
		detailTitle.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		detailTitle.setForeground(Color.white);
		titlePnl.add(detailTitle);
		//
		switchLbl = new JLabel("切换");
		switchLbl.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		switchLbl.setForeground(Color.WHITE);
		switchLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		switchLbl.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				switchLbl.setForeground(Color.white);
			}

			public void mouseEntered(MouseEvent e) {
				switchLbl.setForeground(Style.FOCUS_BLUE);
			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (!isHome) {
					// 展示的为主队数据
					detailTitle.setText("技术统计 - " + v.getHomeTeam() + "      ");
					model.Refresh(Hrecord);
					isHome = true;
					
				} else {
					detailTitle.setText("技术统计 - " + v.getVisitingTeam()
							+ "      ");
					model.Refresh(Vrecord);
					isHome = false;
				}
				contentPnl.removeAll();
				contentPnl.add(jsp);
				table.revalidate();
			}
		});
		titlePnl.add(switchLbl);
		// -----liveLbl----------
		liveLbl = new JLabel("查看文字直播");
		liveLbl.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		liveLbl.setForeground(Color.WHITE);
		liveLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		liveLbl.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				liveLbl.setForeground(Color.white);
			}

			public void mouseEntered(MouseEvent e) {
				liveLbl.setForeground(Style.FOCUS_BLUE);
			}

			public void mouseClicked(MouseEvent e) {
				changeLive();
				
			}
		});
		titlePnl.add(liveLbl);
		// ----contentPnl--------
		contentPnl = new JPanel();
		contentPnl.setLayout(new GridLayout(1, 1));
		gbc.gridy = 6;
		gbc.gridheight = 6;
		gbc.weighty = 14;
		gbl.setConstraints(contentPnl, gbc);
		add(contentPnl);
		// ---jsp---------------
		model = new MatchDetailModel();
		table = new JTable(model);

		table.getColumnModel().getColumn(5).setPreferredWidth(130);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		table.getColumnModel().getColumn(8).setPreferredWidth(130);
		table.getColumnModel().getColumn(9).setPreferredWidth(100);
		table.getColumnModel().getColumn(10).setPreferredWidth(100);
		table.getColumnModel().getColumn(11).setPreferredWidth(130);

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
		jsp.getViewport().setBackground(Color.white);
		model.setTime(v.getMatchTime());
		ArrayList<RecordVO> vv = v.getRecords();
		if(vv!=null){
		for (RecordVO r : vv) {
			if (r.getTeam().equals(v.getHomeTeam()))
				Hrecord.add(r);
			else
				Vrecord.add(r);
		}}
		model.Refresh(Hrecord);
		table.revalidate();
		contentPnl.add(jsp);
	}
	void changeLive(){
		contentPnl.removeAll();
		if(!isLive){
		contentPnl.add(new HistoryLiveTextPanel(vo.getVisitingTeam(),
				vo.getHomeTeam(), vo.getSeason(), vo.getDate()));
		}else{
			if(textpane==null){
			textpane=new LiveTextPanel(vo.getVisitingTeam(),
					vo.getHomeTeam(), vo.getSeason(), vo.getDate()) ;
			textpane.initLiveData(vo.getSeason(), vo.getDate(), 
					vo.getVisitingTeam()+"-"+vo.getHomeTeam());
			
			}
			contentPnl.add(textpane);	
			
		}
		contentPnl.repaint();
		contentPnl.revalidate();
	}
	
	public void setIsLive(boolean t){
		this.isLive=t;
	}
	
	
	public void RefreshLiveAndScore(ArrayList<String> info,int line,String score){
		card.RefershScore(score, line);
		textpane.refresh(info, line);
		
		MatchDetailPanel.this.repaint();
	//	MatchDetailPanel.this.revalidate();
		
	}
}
