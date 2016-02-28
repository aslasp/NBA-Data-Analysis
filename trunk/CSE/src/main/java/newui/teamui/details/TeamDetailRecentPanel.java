package newui.teamui.details;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import newui.Service;
import newui.matchui.TinyCard;
import vo.MatchVO;
import blservice.PlayerBLService;
import blservice.TeamBLService;

public class TeamDetailRecentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PlayerBLService pp;
	String abbrName;
	TeamBLService team;
	ArrayList<MatchVO> matches = new ArrayList<MatchVO>();
	JPanel cardPnl;

	public TeamDetailRecentPanel(String pName, String tName) {
		setLayout(new BorderLayout());
		JLabel titleLbl = new JLabel("最近比赛");
		titleLbl.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		titleLbl.setForeground(Color.white);
		add(titleLbl, BorderLayout.NORTH);
		pp = Service.player;
		matches = pp.getRecentMatches(pName, 5);
		// -------------------
		cardPnl = new JPanel();
		cardPnl.setOpaque(false);
		add(cardPnl, BorderLayout.CENTER);
		cardPnl.setLayout(new GridLayout(1, 5));
		double pre = System.currentTimeMillis();
		cardPnl.add(new TinyCard(matches.get(0), tName));
		cardPnl.add(new TinyCard(matches.get(1), tName));
		cardPnl.add(new TinyCard(matches.get(2), tName));
		cardPnl.add(new TinyCard(matches.get(3), tName));
		cardPnl.add(new TinyCard(matches.get(4), tName));
		double post = System.currentTimeMillis();
		System.out.println("FiveTinyCard:" + (post - pre));

	}

	public TeamDetailRecentPanel(String abbrName) {
		setLayout(new BorderLayout());
		JLabel titleLbl = new JLabel("最近比赛");
		titleLbl.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		titleLbl.setForeground(Color.white);
		add(titleLbl, BorderLayout.NORTH);
		team = Service.team;
		matches = team.getRecentMatches(abbrName);
		// -------------------
		cardPnl = new JPanel();
		cardPnl.setOpaque(false);
		add(cardPnl, BorderLayout.CENTER);
		cardPnl.setLayout(new GridLayout(1, 5));
		cardPnl.add(new TinyCard(matches.get(0), abbrName));
		cardPnl.add(new TinyCard(matches.get(1), abbrName));
		cardPnl.add(new TinyCard(matches.get(2), abbrName));
		cardPnl.add(new TinyCard(matches.get(3), abbrName));
		cardPnl.add(new TinyCard(matches.get(4), abbrName));
	}

	protected void paintComponent(Graphics g) {
		ImageIcon icon = new ImageIcon("image/card.png");
		Image img = icon.getImage();
		g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(),
				icon.getImageObserver());
	}
}
