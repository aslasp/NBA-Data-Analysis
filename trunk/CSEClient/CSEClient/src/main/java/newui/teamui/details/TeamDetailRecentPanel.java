package newui.teamui.details;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import newui.matchui.TinyCard;
import vo.MatchVO;
import businesslogic.Team;
import businesslogicservice.TeamBLService;

public class TeamDetailRecentPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TeamBLService team;
	String abbrName;
	ArrayList<MatchVO> matches=new ArrayList<MatchVO>();
	public TeamDetailRecentPanel(String abbrName){
		team=new Team();
		setBackground(Color.white);
		matches=team.getRecentMatches(abbrName);
		//-------------------
		setLayout(new GridLayout(1,matches.size()));
		for(int i=0;i<matches.size();i++)
			add(new TinyCard(matches.get(i)));
		
	}
	public static void main(String[] args){
		JFrame f=new JFrame();
		f.setBounds(100, 100, 600, 200);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new TeamDetailRecentPanel("MEM"));
	}
}
