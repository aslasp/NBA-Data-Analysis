package newui.matchui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;









import bl.team.Team;
import vo.MatchVO;


import newui.Style;
import newui.mainui.MainFrame;
import newui.teamui.TeamDetailPanel;

public class DetailCard extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	JPanel homeNamePnl, visitingNamePnl;
	JLabel hchNameLbl, habbrNameLbl, homeScore, homeIcon, visitingIcon,
			vchNameLbl, vabbrNameLbl, visitingScore;
	JPanel detailPnl;
	boolean isHomeHigh=true;
	public DetailCard(MatchVO v){
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 40, 0, 40);
		setOpaque(false);

		setLayout(gbl);
		// ----homeNamePnl--------
		if(v.getHomeScore()<v.getVisitingScore())
			isHomeHigh=false;
		homeNamePnl = new JPanel();
		homeNamePnl.setLayout(new GridLayout(2, 1));
		homeNamePnl.setOpaque(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 2;
		gbc.weighty = 1;
		gbl.setConstraints(homeNamePnl, gbc);
		add(homeNamePnl);
		// -----hchNameLbl------
		hchNameLbl = new CHNameLabel(Team.changeTeamNameENToCH(v.getHomeTeam()));
		homeNamePnl.add(hchNameLbl);
		// ----habbrNameLbl-----
		habbrNameLbl = new AbbrNameLabel(v.getHomeTeam());
		homeNamePnl.add(habbrNameLbl);
		// ------homeScore-------
		homeScore = new ScoreLabel(v.getHomeScore()+"");
		gbc.gridx = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbl.setConstraints(homeScore, gbc);
		add(homeScore);
		// -----homeIcon---------
		homeIcon = new JLabel(
				new ImageIcon("image/teamIcon/teamsPng90/"+v.getHomeTeam()+".png"));
		gbc.gridx = 3;
		gbc.gridwidth = 2;
		gbc.weightx = 2;
		gbl.setConstraints(homeIcon, gbc);
		add(homeIcon);
		homeIcon.addMouseListener(this);
		homeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// -----vs----------------
		JLabel vs = new JLabel("  VS  ");
		vs.setFont(new Font("微软雅黑", Font.BOLD, 22));
		vs.setForeground(Style.HOT_RED);
		gbc.gridx = 5;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbl.setConstraints(vs, gbc);
		add(vs);
		// -----visitingIcon---------
		visitingIcon = new JLabel(new ImageIcon(
				"image/teamIcon/teamsPng90/"+v.getVisitingTeam()+".png"));
		gbc.gridx = 6;
		gbc.gridwidth = 2;
		gbc.weightx = 2;
		gbl.setConstraints(visitingIcon, gbc);
		add(visitingIcon);
		visitingIcon.addMouseListener(this);
		visitingIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// ------visitingScore-------
		visitingScore = new ScoreLabel(v.getVisitingScore()+"");
		gbc.gridx = 8;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbl.setConstraints(visitingScore, gbc);
		add(visitingScore);
		if(isHomeHigh)
			homeScore.setBackground(Style.WINNER_RED);
		else
			visitingScore.setBackground(Style.WINNER_RED);
		// ----visitingNamePnl--------
		visitingNamePnl = new JPanel();
		visitingNamePnl.setLayout(new GridLayout(2, 1));
		visitingNamePnl.setOpaque(false);
		gbc.gridx =9;
		gbc.gridwidth = 2;
		gbc.weightx = 2;
		gbl.setConstraints(visitingNamePnl, gbc);
		add(visitingNamePnl);
		// -----vchNameLbl------
		vchNameLbl = new CHNameLabel(Team.changeTeamNameENToCH(v.getVisitingTeam()));
		visitingNamePnl.add(vchNameLbl);
		// ----habbrNameLbl-----
		vabbrNameLbl = new AbbrNameLabel(v.getVisitingTeam());
		visitingNamePnl.add(vabbrNameLbl);
		
		//----------detailPanel-------
		detailPnl=new JPanel();
		detailPnl.setBorder(BorderFactory.createLineBorder(Style.DEEP_BLUE));
		detailPnl.setOpaque(false);
		gbc.gridx =2;
		gbc.gridy = 1;
		gbc.gridwidth = 7;
		gbc.gridheight = 1;
		gbc.weightx = 7;
		gbc.weighty = 1;
		gbl.setConstraints(detailPnl, gbc);
		add(detailPnl);
		//------------
		ArrayList<String> detail=v.getDetailScores();
		int col=detail.size()+1;
		GridLayout gridLayout=new GridLayout(3,col);
		detailPnl.setLayout(gridLayout);
	//	detailPnl.add(new JLabel());
		JLabel temp1=new DetailLabel("各节比分");
		temp1.setFont(new Font("微软雅黑",Font.BOLD,18));
		temp1.setForeground(Style.FOCUS_GREY);
		temp1.setHorizontalAlignment(JLabel.CENTER);
		detailPnl.add(temp1);
		for(int i=1;i<col;i++){
			JLabel temp=new DetailLabel(String.valueOf(i));
			temp.setFont(new Font("微软雅黑",Font.BOLD,18));
			temp.setForeground(Style.FOCUS_GREY);
			temp.setHorizontalAlignment(JLabel.CENTER);
			detailPnl.add(temp);
		}

		if(detail==null)
			System.out.println("detail wei null");
		ArrayList<String> vdet=new ArrayList<String>();
		ArrayList<String> hdet=new ArrayList<String>();
		for(int i=0;i<detail.size();i++){
			String[] s=detail.get(i).split("-");
			vdet.add(s[0]);
			hdet.add(s[1]);
		}
		DetailLabel detailLabel1=new DetailLabel(v.getVisitingTeam());
		detailLabel1.setHorizontalAlignment(JLabel.CENTER);
		detailPnl.add(detailLabel1);
		for(String s:vdet){
			DetailLabel dl=new DetailLabel(s);
			dl.setHorizontalAlignment(JLabel.CENTER);
			detailPnl.add(dl);
		}
		DetailLabel detailLabel2=new DetailLabel(v.getHomeTeam());
		detailLabel2.setHorizontalAlignment(JLabel.CENTER);
		detailPnl.add(detailLabel2);
		for(String s:hdet){
			DetailLabel dl=new DetailLabel(s);
			dl.setHorizontalAlignment(JLabel.CENTER);
			detailPnl.add(dl);
		}
		
	}
	class DetailLabel extends JLabel{
		private static final long serialVersionUID = 1L;

		public DetailLabel(String txt) {
			super(txt);
			setFont(new Font("微软雅黑", Font.PLAIN,14));
			setForeground(Style.FOCUS_GREY);
		}
	}
	class CHNameLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		public CHNameLabel(String txt) {
			super(txt);
			setFont(new Font("微软雅黑", Font.PLAIN, 22));
			setForeground(Style.FOCUS_GREY);
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			addMouseListener(DetailCard.this);
		}

	}

	class AbbrNameLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		public AbbrNameLabel(String txt) {
			super(txt);
			setFont(new Font("微软雅黑", Font.PLAIN, 16));
			setForeground(Style.FOCUS_GREY);
		}
	}

	class ScoreLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		public ScoreLabel(String txt) {
			super(txt);
			setFont(new Font("微软雅黑", Font.PLAIN, 25));
			setForeground(Style.FOCUS_GREY);
			/**
			 * 谁的分高就是红色(Style.WINNER_RED)
			 */
		}
	}
	
	class LinePanel extends JPanel{
		 public void paintComponent(Graphics g){
             // TODO Auto-generated method stub
             g.drawLine(40,40,80,40);
         }
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		String name=vabbrNameLbl.getText();
		if(e.getSource()==hchNameLbl||e.getSource()==homeIcon){
			name=habbrNameLbl.getText();
		}
	
		MainFrame.getInstance().setContentPanel(new TeamDetailPanel(name));
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource().getClass() == CHNameLabel.class) {
			CHNameLabel lbl = (CHNameLabel) e.getSource();
			lbl.setForeground(Style.FOCUS_BLUE);
		}

	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource().getClass() == CHNameLabel.class) {
			CHNameLabel lbl = (CHNameLabel) e.getSource();
			lbl.setForeground(Style.FOCUS_GREY);
		}
	}
}
