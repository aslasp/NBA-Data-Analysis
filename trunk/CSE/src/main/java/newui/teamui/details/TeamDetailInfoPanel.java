package newui.teamui.details;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import newui.Service;
import newui.Style;
import vo.TeamVO;
import bl.team.Team;
import blservice.TeamBLService;

public class TeamDetailInfoPanel extends JPanel {

	/**
	 * 球队详情之基本信息
	 */
	private static final long serialVersionUID = 1L;
	String chName, enName, abbrName;
	TeamBLService team;
	TeamVO basevo;
	JPanel infoPnl, recentPnl;

	public TeamDetailInfoPanel(String ch, String abbr) {
		// -----名字获取及VO生成----------
		team = Service.team;
		chName = ch;
		abbrName = abbr;// Team.changeTeamNameCHToEN(abbr);
		basevo = team.getTeamBaseInfo(Team.changeTeamNameCHToEN(abbrName)).get(
				0);
		enName = basevo.getTeamName();
		// ----------------------------
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 30, 10, 30);
		setLayout(gbl);
		// ----------------------------
		JLabel enNameLbl = new JLabel(enName);
		enNameLbl.setFont(new Font("华文细黑", Font.PLAIN, 35));
		enNameLbl.setForeground(Style.FOCUS_GREY);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 2;
		gbc.weightx = 10;
		gbc.weighty = 2;
		gbl.setConstraints(enNameLbl, gbc);
		add(enNameLbl);
		// ----------------------------
		infoPnl = new JPanel();
		infoPnl.setOpaque(false);
		infoPnl.setLayout(new GridLayout(4, 2));
		gbc.gridy = 2;
		gbc.gridheight = 5;
		gbc.weighty = 5;
		gbl.setConstraints(infoPnl, gbc);
		add(infoPnl);
		// ------base info-------------
		MyLabel chNameLbl = new MyLabel("中文名：" + chName);
		infoPnl.add(chNameLbl);
		MyLabel abbrNameLbl = new MyLabel("缩写：" + abbrName);
		infoPnl.add(abbrNameLbl);
		MyLabel locationLbl = new MyLabel("所在地：" + basevo.getLocation());
		infoPnl.add(locationLbl);
		MyLabel conferenceLbl = new MyLabel("联盟：" + basevo.getConference());
		infoPnl.add(conferenceLbl);
		MyLabel partitionLbl = new MyLabel("分区：" + basevo.getPartition());
		infoPnl.add(partitionLbl);
		MyLabel homeCourtLbl = new MyLabel("主场：" + basevo.getHomeCourt());
		infoPnl.add(homeCourtLbl);
		MyLabel setUpTimeLbl = new MyLabel("成立时间：" + basevo.getSetUpTime());
		infoPnl.add(setUpTimeLbl);
		// ----------------------------
		// 这边严重的有问题
		recentPnl = new TeamDetailRecentPanel(
				Team.changeTeamNameCHToEN(abbrName));
		gbc.gridy = 7;
		gbc.gridheight = 3;
		gbc.weighty = 1;
		gbl.setConstraints(recentPnl, gbc);
		add(recentPnl);
	}

	protected void paintComponent(Graphics g) {
		ImageIcon icon = new ImageIcon("image/detailBack.png");
		Image img = icon.getImage();
		g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(),
				icon.getImageObserver());
	}

	class MyLabel extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyLabel(String t) {
			super(t);
			setForeground(Style.FOCUS_GREY);
			setFont(new Font("华文细黑", Font.PLAIN, 20));
		}

	}
}
