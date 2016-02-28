package newui.matchui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MatchCard extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel homeTeamIcon,visitingTeamIcon,homeTeamNameLbl,visitingTeamNameLbl,
	homeTeamScoreLbl,visitingTeamScoreLbl;
	JPanel homeIconPnl,visitingIconPnl,homeScorePnl,visitingScorePnl,detailScoresPnl;
	
	public MatchCard(){
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		setLayout(gbl);
		//------iconPnls------------
		homeIconPnl=new JPanel();
		visitingIconPnl=new JPanel();
		
		
	}
	public JPanel getDetailScoresPanel(){
		JPanel result=new JPanel();
		
		return result;
	}
}
