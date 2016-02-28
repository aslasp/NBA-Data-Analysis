package newui.matchui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bl.team.Team;
import blservice.TeamBLService;
import vo.MatchVO;
import newui.Service;
import newui.Style;
import newui.mainui.MainFrame;
import newui.teamui.TeamDetailPanel;

public class MatchCard extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel dateLbl, detailLbl,liveLbl,  homeIcon, visitingIcon, homeNameLbl,
			visitingNameLbl, homeScoreLbl, visitingScoreLbl;
	JPanel topPnl, detailScoresPnl;
	private TeamBLService team;
	private boolean isHomeHigh=true;
	private MatchVO vo;
//显示单场比赛信息
	public MatchCard(MatchVO vo) {
		this.vo=vo;
		team=Service.team;
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		setBackground(Color.white);
		// ----topPnl----------------
		topPnl = new JPanel();
		topPnl.setBackground(Style.DEEP_BLUE);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 5;
		gbc.gridheight = 1;
		gbc.weightx = 100;
		gbc.weighty = 1;
		gbl.setConstraints(topPnl, gbc);
		add(topPnl);
		// ---内容 of topPnl:date,detail-----------
		//是否需要带上年份
		dateLbl = new JLabel("日期："+vo.getDate());
		dateLbl.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		dateLbl.setForeground(Color.white);
		topPnl.add(dateLbl);
		//
		detailLbl = new JLabel("技术统计");
		detailLbl.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		detailLbl.setForeground(Color.white);
		detailLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		detailLbl.addMouseListener(this);
		topPnl.add(detailLbl);
		
		//-----文字直播---监听不完善  实际使用需根据比赛类型 决定直播类型
		liveLbl=new JLabel("文字直播");
		liveLbl.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		liveLbl.setForeground(Color.white);
		liveLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		liveLbl.addMouseListener(this);
		topPnl.add(liveLbl);
		// --homeicon---------------
		//ImageIcon t=team.getTeamImage(vo.getHomeTeam());
		ImageIcon t=new ImageIcon("image/teamIcon/teamsPng150/"+vo.getHomeTeam()+".png");
		//t.setImage(t.getImage().getScaledInstance(150, 150,0));
		
		homeIcon = new JLabel(
				t);
		homeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//是否需要ToolTipText
		homeIcon.setToolTipText("点击查看球队详情");
		homeIcon.addMouseListener(this);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = 20;
		gbc.weighty = 50;
		gbl.setConstraints(homeIcon, gbc);
		add(homeIcon);
		// --homeName---------------
		homeNameLbl = new JLabel(vo.getHomeTeam());
		homeNameLbl.setFont(new Font("微软雅黑", Font.BOLD, 22));
		homeNameLbl.setForeground(Style.BACK_GREY);
		gbc.gridx = 1;
		gbc.gridheight = 1;
		gbc.weightx = 15;
		gbc.weighty = 25;
		gbl.setConstraints(homeNameLbl, gbc);
		add(homeNameLbl);
		// --homeScore---------------
		if(vo.getVisitingScore()>vo.getHomeScore())
			isHomeHigh=false;
		homeScoreLbl = new JLabel(vo.getHomeScore()+"");
		homeScoreLbl.setFont(new Font("微软雅黑", Font.BOLD, 22));
		homeScoreLbl.setForeground(Style.BACK_GREY);
		gbc.gridy = 2;
		gbl.setConstraints(homeScoreLbl, gbc);
		add(homeScoreLbl);
		// --detailScoresPnl----------------
		detailScoresPnl = this.getDetailScoresPanel(vo);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = 30;
		gbc.weighty = 50;
		gbl.setConstraints(detailScoresPnl, gbc);
		add(detailScoresPnl);
		// --visitingIcon---------------
		//ImageIcon v=team.getTeamImage(vo.getVisitingTeam());
		ImageIcon v=new ImageIcon("image/teamIcon/teamsPng150/"+vo.getVisitingTeam()+".png");
		//v.setImage(v.getImage().getScaledInstance(150, 150,0));
		visitingIcon = new JLabel(v);
		visitingIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		visitingIcon.setToolTipText("点击查看球队详情");
		visitingIcon.addMouseListener(this);
		gbc.gridx = 4;
		gbc.weightx = 20;
		gbc.weighty = 50;
		
		gbl.setConstraints(visitingIcon, gbc);
		add(visitingIcon);
		// --visitingName---------------
		visitingNameLbl = new JLabel(vo.getVisitingTeam());
		visitingNameLbl.setFont(new Font("微软雅黑", Font.BOLD, 22));
		visitingNameLbl.setForeground(Style.BACK_GREY);
		gbc.gridx = 3;
		gbc.gridheight = 1;
		gbc.weightx = 15;
		gbc.weighty = 25;
		gbl.setConstraints(visitingNameLbl, gbc);
		add(visitingNameLbl);
		// --visitingScore---------------
		visitingScoreLbl = new JLabel(vo.getVisitingScore()+"");
		visitingScoreLbl.setFont(new Font("微软雅黑", Font.BOLD, 22));
		if(isHomeHigh){
			homeScoreLbl.setForeground(Style.WINNER_RED);
			visitingScoreLbl.setForeground(Style.BACK_GREY);
		}
		else
			visitingScoreLbl.setForeground(Style.WINNER_RED);
		gbc.gridy = 2;
		gbl.setConstraints(visitingScoreLbl, gbc);
		add(visitingScoreLbl);
	}

	public JPanel getDetailScoresPanel(MatchVO vo) {
		JPanel result = new JPanel();
		result.setBackground(Color.white);
		//根据ArrayList.size()节数确定这个panel分几行 
		ArrayList<String> detail=vo.getDetailScores();
		int rowNum=detail.size();
		result.setLayout(new GridLayout(rowNum,1));
		//JLabel pointLbl=new JLabel(detail.get(0));
	//	pointLbl.setFont(new Font("微软雅黑",Font.PLAIN,14));
		//pointLbl.setForeground(Style.BACK_GREY);
		//result.add(pointLbl);
		for(int i=0;i<rowNum;i++)
			result.add(new JLabel(detail.get(i)));
	
		return result;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == detailLbl)// 注意，这里应当传一个比赛的特征值过去
			MainFrame.getInstance().setContentPanel(new MatchDetailPanel(vo));
		else{
			String name=vo.getVisitingTeam();
			if(e.getSource()==homeIcon)
				name=vo.getHomeTeam();
			MainFrame.getInstance().setContentPanel(new TeamDetailPanel(name));
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == detailLbl)   
			detailLbl.setForeground(Style.FOCUS_BLUE);

	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == detailLbl)
			detailLbl.setForeground(Color.white);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setBounds(5, 5, 800, 170);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	f.add(new MatchCard());
		f.repaint();
	}
}
