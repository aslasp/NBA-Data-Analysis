package newui.mainui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import newui.FatherPanel;
import newui.Service;
import newui.Style;
import newui.UIhelper;
import newui.hotui.HotIndexPanel;
import newui.hotui.HotSeasonPanel;
import newui.hotui.HotTeamPanel;
import newui.hotui.HotThread;
import newui.hotui.ProgressPanel;
import vo.PlayerVO;
import vo.TeamVO;
import bl.player.Player;
import bl.team.Team;
import blservice.PlayerBLService;
import blservice.TeamBLService;

public class IndexPanel extends FatherPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel titlePnl, infoPnl;
	PlayerBLService player;
	TeamBLService team;
	public String dname, sname, tname, tPlayername, pname;
	IndexCard todayPnl, seasonPnl, teamPnl, progressPnl;
	HotThread thr;
	static IndexThread th;
	public IndexPanel() {
		removeAll();
		
		player = Service.player;
		team = Service.team;
		
	
		// -----获得热点球员们---------
		PlayerVO dayP = player.getDayHotPlayer("score", 1).get(0);
		PlayerVO seasonP = player.getSeasonHotPlayer("13-14", "score", 1)
				.get(0);
		TeamVO t = team.getSeasonHotTeam("13-14", "score", 1).get(0);
		PlayerVO proP = player.getBestImprovedPlayer("recentFiveMatchesScoreUpRate", 1).get(0);
		//
		dname = dayP.getName();
		sname = seasonP.getName();
		tname = t.getTeamName();
		tPlayername=player.getPlayersByTeam(t.getAbLocation()).get(1).getName();
		pname = proP.getName();
		// ----------------------------
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		// -----titlePnl-----------
		titlePnl = new TitlePanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 3;
		gbc.weightx = 10;
		gbc.weighty = 0.1;
		gbl.setConstraints(titlePnl, gbc);
		add(titlePnl);
		// ----infoPnl-------------
		gbc.insets=new Insets(50,30,50,30);
		infoPnl = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 7;
		gbc.weightx = 10;
		gbc.weighty = 15;
		gbl.setConstraints(infoPnl, gbc);
		add(infoPnl);
		infoPnl.setLayout(new GridLayout(1, 4));
		// -----todayPnl-----------
		 todayPnl = new IndexCard();
		todayPnl.setTitleAndName("今日得分王", dname);
		todayPnl.addMouseListener(this);
		//todayPnl.iconPnl.setBackground(new Color(254,239,182));
		todayPnl.iconPnl.setBackground(Style.HOT_YELLOW);
		ImageIcon todayIcon = new ImageIcon("image/player/action/" + dname
				+ ".png");
		todayIcon.setImage(todayIcon.getImage().getScaledInstance(220, 350,
				Image.SCALE_SMOOTH));
		todayPnl.iconLbl.setIcon(todayIcon);
		//
		todayPnl.namePnl.setBackground(Style.HOT_YELLOWFOCUS);
		infoPnl.add(todayPnl);
		// -----seasonPnl-----------
		 seasonPnl = new IndexCard();
		seasonPnl.setTitleAndName("赛季得分王", sname);
		seasonPnl.addMouseListener(this);
		//seasonPnl.iconPnl.setBackground(new Color(255,147,147));
		seasonPnl.iconPnl.setBackground(Style.HOT_RED);
		ImageIcon seasonIcon = new ImageIcon("image/player/action/" + sname
				+ ".png");
		
		seasonIcon.setImage(seasonIcon.getImage().getScaledInstance(220, 350,
				Image.SCALE_SMOOTH));
		seasonPnl.iconLbl.setIcon(seasonIcon);
		//
		seasonPnl.namePnl.setBackground(Style.HOT_REDFOCUS);
		infoPnl.add(seasonPnl);
		// -----teamPnl-----------
		 teamPnl = new IndexCard();
		teamPnl.setTitleAndName("场均得分最高球队", tname);
		teamPnl.addMouseListener(this);
		//teamPnl.iconPnl.setBackground(new Color(179,255,188));
		teamPnl.iconPnl.setBackground(Style.HOT_BLUE);
		ImageIcon teamIcon = new ImageIcon("image/player/action/" + tPlayername
				+ ".png");
		teamIcon.setImage(teamIcon.getImage().getScaledInstance(220,350,
				Image.SCALE_SMOOTH));
		teamPnl.iconLbl.setIcon(teamIcon);
		//
		teamPnl.namePnl.setBackground(Style.HOT_BLUEFOCUS);
		infoPnl.add(teamPnl);
		// -----progressPnl-----------
		 progressPnl = new IndexCard();
		progressPnl.setTitleAndName("得分进步最快球员", pname);
		progressPnl.addMouseListener(this);
		//progressPnl.iconPnl.setBackground(new Color(204,153,255));
		progressPnl.iconPnl.setBackground(Style.HOT_PURPLE);
		String pImgName=pname;
		if(!UIhelper.isImgExists(pImgName))
			pImgName="null";
		ImageIcon progressIcon = new ImageIcon("image/player/action/" + pImgName
				+ ".png");
		progressIcon.setImage(progressIcon.getImage().getScaledInstance(220, 350,
				Image.SCALE_SMOOTH));
		progressPnl.iconLbl.setIcon(progressIcon);
		//
		progressPnl.namePnl.setBackground(Style.HOT_PURPLEFOCUS);
		infoPnl.add(progressPnl);
		th=new IndexThread(this);
		
	}

	public void mouseClicked(MouseEvent e) {
	
		// TODO Auto-generated method stub
		HotIndexPanel pp=new HotIndexPanel();
		
	if(e.getSource()!=todayPnl){
		pp.downPnl.removeAll();
		pp.downPnl.setLayout(new GridLayout(1,1));
		if(e.getSource()==progressPnl){
			
			ProgressPanel p=new ProgressPanel();
			pp.downPnl.add(p);
			p.Refresh("recentFiveMatchesScoreUpRate");
			
		}else if(e.getSource()==seasonPnl){
			
			HotSeasonPanel p=new HotSeasonPanel();
			pp.downPnl.add(p);
			p.Refresh("score");
		}else if(e.getSource()==teamPnl){
			HotTeamPanel p=new HotTeamPanel();
			pp.downPnl.add(p);
			p.Refresh("score");
		}
		
		pp.downPnl.repaint();
		pp.downPnl.revalidate();
		}
		MainFrame.getInstance().setContentPanel(pp);
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void Refresh(){
		
		PlayerVO dayP = player.getDayHotPlayer("score", 1).get(0);
		PlayerVO seasonP = player.getSeasonHotPlayer("13-14", "score", 1)
				.get(0);
		TeamVO t = team.getSeasonHotTeam("13-14", "score", 1).get(0);
		PlayerVO proP = player.getBestImprovedPlayer("recentFiveMatchesScoreUpRate", 1).get(0);
		String d= dayP.getName();
		String s= seasonP.getName();
		String tn=t.getTeamName();
		String tt=player.getPlayersByTeam(t.getAbLocation()).get(1).getName();
		String p=proP.getName();
		if(!d.equals(dname)){
		dname=d;
		todayPnl.setTitleAndName("今日得分王", dname);
		ImageIcon todayIcon = new ImageIcon("image/player/action/" + dname
				+ ".png");
		todayIcon.setImage(todayIcon.getImage().getScaledInstance(220, 350,
				Image.SCALE_SMOOTH));
		todayPnl.iconLbl.setIcon(todayIcon);
		}
		if(!sname.equals(s)){
		sname=s;
		seasonPnl.setTitleAndName("赛季得分王", sname);
		ImageIcon seasonIcon = new ImageIcon("image/player/action/" + sname
				+ ".png");
		
		seasonIcon.setImage(seasonIcon.getImage().getScaledInstance(220, 350,
				Image.SCALE_SMOOTH));
		seasonPnl.iconLbl.setIcon(seasonIcon);
		}
		if(!tname.equals(tn)){
		tname=tn;
		tPlayername=tt;
		teamPnl.setTitleAndName("场均得分最高球队", tname);
		ImageIcon teamIcon = new ImageIcon("image/player/action/" + tPlayername
				+ ".png");
		teamIcon.setImage(teamIcon.getImage().getScaledInstance(220,350,
				Image.SCALE_SMOOTH));
		teamPnl.iconLbl.setIcon(teamIcon);
		}
		if(!pname.equals(p)){
			pname=p;
		
		progressPnl.setTitleAndName("得分进步最快球员", pname);
		String pImgName=pname;
		if(!UIhelper.isImgExists(pImgName))
			pImgName="null";
		ImageIcon progressIcon = new ImageIcon("image/player/action/" + pImgName
				+ ".png");
		progressIcon.setImage(progressIcon.getImage().getScaledInstance(220, 350,
				Image.SCALE_SMOOTH));
		progressPnl.iconLbl.setIcon(progressIcon);
		}
		this.repaint();
		
	}
	
	public  static void startT(){
		th.startThread();
	}
}
