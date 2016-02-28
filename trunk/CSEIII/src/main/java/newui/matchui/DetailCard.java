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

import newui.Style;
import newui.mainui.MainFrame;
import newui.teamui.TeamDetailPanel;
import vo.MatchVO;
import bl.Team;

public class DetailCard extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	JPanel homeNamePnl, visitingNamePnl;
	JLabel hchNameLbl, habbrNameLbl, homeScore, homeIcon, visitingIcon,
			vchNameLbl, vabbrNameLbl, visitingScore;
	JPanel detailPnl;
	boolean isHomeHigh = true;
	boolean isHomeEqual = false;
	int lastline = -1;
	int col = 5;
	String hometeam, visteam;
	ArrayList<String> vdet;
	ArrayList<String> hdet;

	public DetailCard(MatchVO v, int status) {
		this(v);
		if (status == 3) {
			// over game

		}
	}

	public DetailCard(MatchVO v) {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 40, 0, 40);
		setOpaque(false);

		setLayout(gbl);
		// ----visitingNamePnl--------
		if (v.getHomeScore() < v.getVisitingScore())
			isHomeHigh = false;
		else if (v.getHomeScore() > v.getVisitingScore())
			isHomeHigh = true;
		else
			isHomeEqual = true;
		visitingNamePnl = new JPanel();
		visitingNamePnl.setLayout(new GridLayout(2, 1));
		visitingNamePnl.setOpaque(false);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 2;
		gbc.weighty = 1;
		gbl.setConstraints(visitingNamePnl, gbc);
		add(visitingNamePnl);

		// -----vchNameLbl------
		vchNameLbl = new CHNameLabel(Team.changeTeamNameENToCH(v
				.getVisitingTeam()));
		visitingNamePnl.add(vchNameLbl);
		// ----habbrNameLbl-----
		vabbrNameLbl = new AbbrNameLabel(v.getVisitingTeam());
		visitingNamePnl.add(vabbrNameLbl);

		// ------homeScore-------
		visitingScore = new ScoreLabel(v.getVisitingScore() + "");

		gbc.gridx = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbl.setConstraints(visitingScore, gbc);
		add(visitingScore);
		// -----vIcon---------
		ImageIcon visitingIco = Team.getTeamImage(v.getVisitingTeam());
		visitingIco.setImage(visitingIco.getImage().getScaledInstance(90, 90,
				Image.SCALE_SMOOTH));
		visitingIcon = new JLabel(visitingIco);
		gbc.gridx = 3;
		gbc.gridwidth = 2;
		gbc.weightx = 2;
		gbl.setConstraints(visitingIcon, gbc);
		add(visitingIcon);
		visitingIcon.addMouseListener(this);
		visitingIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
		ImageIcon homeIco = Team.getTeamImage(v.getHomeTeam());
		homeIco.setImage(homeIco.getImage().getScaledInstance(90, 90,
				Image.SCALE_SMOOTH));
		homeIcon = new JLabel(homeIco);
		gbc.gridx = 6;
		gbc.gridwidth = 2;
		gbc.weightx = 2;
		gbl.setConstraints(homeIcon, gbc);
		add(homeIcon);
		homeIcon.addMouseListener(this);
		homeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// ------homeScoreScore-------
		homeScore = new ScoreLabel(v.getHomeScore() + "");
		gbc.gridx = 8;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbl.setConstraints(homeScore, gbc);
		add(homeScore);
		if (isHomeHigh)
			homeScore.setForeground(Style.WINNER_RED);
		else
			visitingScore.setForeground(Style.WINNER_RED);
		// ----homeNamePnl--------
		homeNamePnl = new JPanel();
		homeNamePnl.setLayout(new GridLayout(2, 1));
		homeNamePnl.setOpaque(false);
		gbc.gridx = 9;
		gbc.gridwidth = 2;
		gbc.weightx = 2;
		gbl.setConstraints(homeNamePnl, gbc);
		add(homeNamePnl);
		// -----hchNameLbl------
		hchNameLbl = new CHNameLabel(Team.changeTeamNameENToCH(v.getHomeTeam()));
		homeNamePnl.add(hchNameLbl);
		// ----habbrNameLbl-----
		habbrNameLbl = new AbbrNameLabel(v.getHomeTeam());
		homeNamePnl.add(habbrNameLbl);

		// ----------detailPanel-------
		detailPnl = new JPanel();
		detailPnl.setBorder(BorderFactory.createLineBorder(Style.DEEP_BLUE));
		detailPnl.setOpaque(false);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 7;
		gbc.gridheight = 1;
		gbc.weightx = 7;
		gbc.weighty = 1;
		gbl.setConstraints(detailPnl, gbc);
		add(detailPnl);
		// ------------
		ArrayList<String> detail = v.getDetailScores();

		if (detail != null)
			col = detail.size() + 1;
		// int col = detail.size() + 1;
		GridLayout gridLayout = new GridLayout(3, col);
		detailPnl.setLayout(gridLayout);
		// detailPnl.add(new JLabel());
		JLabel temp1 = new DetailLabel("各节比分");
		temp1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		temp1.setForeground(Style.FOCUS_GREY);
		temp1.setHorizontalAlignment(JLabel.CENTER);
		detailPnl.add(temp1);
		for (int i = 1; i < col; i++) {
			String s = "" + i;
			if (i > 4)
				s = "加时" + (i - 4);
			JLabel temp = new DetailLabel(s);
			temp.setFont(new Font("微软雅黑", Font.BOLD, 18));
			temp.setForeground(Style.FOCUS_GREY);
			temp.setHorizontalAlignment(JLabel.CENTER);
			detailPnl.add(temp);
		}

		if (detail == null)
			System.out.println("detail wei null");
		vdet = new ArrayList<String>();
		hdet = new ArrayList<String>();
		if (detail != null) {
			for (int i = 0; i < col - 1; i++) {
				String[] s = detail.get(i).split("-");
				vdet.add(s[0]);
				hdet.add(s[1]);
			}
		} else {

			for (int i = 0; i < col - 1; i++) {
				vdet.add("-");
				hdet.add("-");
			}

		}
		visteam = v.getVisitingTeam();
		hometeam = v.getHomeTeam();
		DetailLabel detailLabel1 = new DetailLabel(v.getVisitingTeam());
		detailLabel1.setHorizontalAlignment(JLabel.CENTER);
		detailPnl.add(detailLabel1);
		for (int i = 0; i < vdet.size(); i++) {
			String s = vdet.get(i);
			DetailLabel dl = new DetailLabel(s);
			if (!s.equals("-"))
				if (Integer.parseInt(s) > Integer.parseInt(hdet.get(i)))
					dl.setForeground(Style.WINNER_RED);

			dl.setHorizontalAlignment(JLabel.CENTER);
			detailPnl.add(dl);
		}
		DetailLabel detailLabel2 = new DetailLabel(v.getHomeTeam());
		detailLabel2.setHorizontalAlignment(JLabel.CENTER);
		detailPnl.add(detailLabel2);
		for (int i = 0; i < hdet.size(); i++) {
			String s = hdet.get(i);
			DetailLabel dl = new DetailLabel(s);
			if (!s.equals("-"))
				if (Integer.parseInt(s) > Integer.parseInt(vdet.get(i)))
					dl.setForeground(Style.WINNER_RED);
			dl.setHorizontalAlignment(JLabel.CENTER);
			detailPnl.add(dl);
		}

	}

	public void RefershScore(String score, int line) {
		//System.out.println(score+""+line);
		if (score != "") {
			detailPnl.removeAll();
			int vs = 0, hs = 0;
			if (line > 1) {
				for (int i = 0; i < line - 1; i++) {
					vs += Integer.parseInt(vdet.get(i));
					hs += Integer.parseInt(hdet.get(i));

				}
			}
			String[] s = score.split("-");
			if (line > 1) {
				if (line <= vdet.size()) {
					vdet.set(line - 1, "" + (Integer.parseInt(s[0]) - vs));
					hdet.set(line - 1, "" + (Integer.parseInt(s[1]) - hs));
				} else {
					vdet.add("" + (Integer.parseInt(s[0]) - vs));
					hdet.add("" + (Integer.parseInt(s[1]) - hs));
					col = vdet.size() + 1;
				}
			} else {
				vdet.set(line - 1, s[0]);
				hdet.set(line - 1, s[1]);
			}
			lastline = line - 1;
			/*
			 * if(line==lastline){ }else{ lastline=line; }
			 */

			if (Integer.parseInt(s[0]) > Integer.parseInt(s[1])) {
				isHomeHigh = false;
				isHomeEqual = false;
			} else if (Integer.parseInt(s[0]) < Integer.parseInt(s[1])) {
				isHomeHigh = true;
				isHomeEqual = false;
			} else {
				isHomeEqual = true;
			}
			RefreshDetail();
			visitingScore.setText(s[0]);
			homeScore.setText(s[1]);
			detailPnl.repaint();
			detailPnl.revalidate();
		}
	}

	public void RefreshDetail() {
		JLabel temp1 = new DetailLabel("各节比分");
		temp1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		temp1.setForeground(Style.FOCUS_GREY);
		temp1.setHorizontalAlignment(JLabel.CENTER);
		detailPnl.add(temp1);
		for (int i = 1; i < col; i++) {
			String s = "" + i;
			if (i > 4)
				s = "加时" + (i - 4);
			JLabel temp = new DetailLabel(s);
			temp.setFont(new Font("微软雅黑", Font.BOLD, 18));
			temp.setForeground(Style.FOCUS_GREY);
			temp.setHorizontalAlignment(JLabel.CENTER);
			detailPnl.add(temp);
		}

		/*
		 * if (detail == null) System.out.println("detail wei null"); vdet = new
		 * ArrayList<String>(); hdet = new ArrayList<String>(); for (int i = 0;
		 * i < col-1; i++) { String[] s = detail.get(i).split("-");
		 * vdet.add(s[0]); hdet.add(s[1]); }
		 */
		DetailLabel detailLabel1 = new DetailLabel(visteam);
		detailLabel1.setHorizontalAlignment(JLabel.CENTER);
		detailPnl.add(detailLabel1);
		for (int i = 0; i < vdet.size(); i++) {
			String s = vdet.get(i);
			DetailLabel dl = new DetailLabel(s);
			if (i <= lastline) {
				if (Integer.parseInt(s) > Integer.parseInt(hdet.get(i)))
					dl.setForeground(Style.WINNER_RED);
			}
			dl.setHorizontalAlignment(JLabel.CENTER);
			detailPnl.add(dl);
		}
		DetailLabel detailLabel2 = new DetailLabel(hometeam);
		detailLabel2.setHorizontalAlignment(JLabel.CENTER);
		detailPnl.add(detailLabel2);
		for (int i = 0; i < hdet.size(); i++) {
			String s = hdet.get(i);
			DetailLabel dl = new DetailLabel(s);
			if (i <= lastline) {
				if (Integer.parseInt(s) > Integer.parseInt(vdet.get(i)))
					dl.setForeground(Style.WINNER_RED);
			}
			dl.setHorizontalAlignment(JLabel.CENTER);
			detailPnl.add(dl);
		}
		if (isHomeEqual) {
			
			homeScore.setForeground(Style.BACK_GREY);
			visitingScore.setForeground(Style.BACK_GREY);
		} else {
			if (isHomeHigh) {
				homeScore.setForeground(Style.WINNER_RED);
				visitingScore.setForeground(Style.BACK_GREY);
			} else {
				visitingScore.setForeground(Style.WINNER_RED);
				homeScore.setForeground(Style.BACK_GREY);
			}
		}
	}

	class DetailLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		public DetailLabel(String txt) {
			super(txt);
			setFont(new Font("微软雅黑", Font.PLAIN, 14));
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

	class LinePanel extends JPanel {
		public void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			g.drawLine(40, 40, 80, 40);
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		String name = vabbrNameLbl.getText();
		if (e.getSource() == hchNameLbl || e.getSource() == homeIcon) {
			name = habbrNameLbl.getText();
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
