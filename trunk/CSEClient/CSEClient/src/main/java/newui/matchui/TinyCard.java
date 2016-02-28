package newui.matchui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import newui.Style;
import vo.MatchVO;

public class TinyCard extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean isHomeBigger = false;
	public TinyCard(MatchVO vo) {
		setOpaque(false);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		isHomeBigger = MatchHelper.isHomeBigger(vo);
		// ----------------
		JLabel icon = new JLabel(new ImageIcon("image/teamIcon/teamsPng90/"
				+ ".png"));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 3;
		gbc.weightx = 2;
		gbc.weighty = 3;
		gbl.setConstraints(icon, gbc);
		add(icon);
		// ----------------
		JLabel nameLbl = new JLabel(vo.getHomeTeam() + "  -  "
				+ vo.getVisitingTeam());
		nameLbl.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.weighty = 1;
		gbl.setConstraints(nameLbl, gbc);
		add(nameLbl);
		// -----------------
		JLabel homeScoreLbl = new JLabel(String.valueOf(vo.getHomeScore()));
		if (isHomeBigger)
			homeScoreLbl.setForeground(Style.WINNER_RED);
		homeScoreLbl.setFont(new Font("微软雅黑", Font.BOLD, 24));
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = 1;
		gbc.weighty = 2;
		gbl.setConstraints(homeScoreLbl, gbc);
		add(homeScoreLbl);
		//---------------------
		JLabel visitingScoreLbl = new JLabel(String.valueOf(vo.getVisitingScore()));
		if (!isHomeBigger)
			visitingScoreLbl.setForeground(Style.WINNER_RED);
		visitingScoreLbl.setFont(new Font("微软雅黑", Font.BOLD, 24));
		gbc.gridx = 1;
		gbl.setConstraints(visitingScoreLbl, gbc);
		add(visitingScoreLbl);
	}
}
