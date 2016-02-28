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
import newui.Style;
import newui.matchui.TinyCard;
import vo.MatchVO;
import blService.PlayerBLService;
import blService.TeamBLService;

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
		// -----------
		cardPnl = new JPanel();
		cardPnl.setOpaque(false);
		add(cardPnl, BorderLayout.CENTER);
		// -----------
		matches = pp.getRecentMatches(pName, 5);
		int matchNum = matches.size();
		if (matchNum == 0) {
			JLabel noMatchLbl = new JLabel("最近没有进行比赛。");
			noMatchLbl.setFont(new Font("华文细黑", Font.PLAIN, 18));
			noMatchLbl.setForeground(Style.DEEP_BLUE);
			cardPnl.add(noMatchLbl);
		} else {
			cardPnl.setLayout(new GridLayout(1, matchNum));
			// double pre = System.currentTimeMillis();
			for (int i = 0; i < matchNum; i++)
				cardPnl.add(new TinyCard(matches.get(i), tName));
			// double post = System.currentTimeMillis();
			// System.out.println("FiveTinyCard:" + (post - pre));
		}
	}

	public TeamDetailRecentPanel(String abbrName) {
		setLayout(new BorderLayout());
		JLabel titleLbl = new JLabel("最近比赛");
		titleLbl.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		titleLbl.setForeground(Color.white);
		add(titleLbl, BorderLayout.NORTH);
		team = Service.team;
		matches = team.getRecentMatches(abbrName,5);
		cardPnl = new JPanel();
		cardPnl.setOpaque(false);
		add(cardPnl, BorderLayout.CENTER);
		// -------------------
		int matchNum = matches.size();
		if (matchNum == 0) {
			JLabel noMatchLbl = new JLabel("最近没有进行比赛。");
			noMatchLbl.setFont(new Font("华文细黑", Font.PLAIN, 18));
			noMatchLbl.setForeground(Style.DEEP_BLUE);
			cardPnl.add(noMatchLbl);
		} else {
			cardPnl.setLayout(new GridLayout(1, matchNum));
			for (int i = 0; i < matchNum; i++)
				cardPnl.add(new TinyCard(matches.get(i), abbrName));
		}
	}

	protected void paintComponent(Graphics g) {
		ImageIcon icon = new ImageIcon("image/card.png");
		Image img = icon.getImage();
		g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(),
				icon.getImageObserver());
	}
}
