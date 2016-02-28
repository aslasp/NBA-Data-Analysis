package newui.teamui.details;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import vo.TeamVO;
import businesslogic.Team;
import businesslogicservice.TeamBLService;

public class TeamDetailInfoPanel extends JPanel{

	/**
	 * 球队详情之基本信息
	 */
	private static final long serialVersionUID = 1L;
	String chName,enName,abbrName;
	TeamBLService team;
	TeamVO basevo;
	JPanel infoPnl,recentPnl;
	public TeamDetailInfoPanel(String ch,String abbr){
		//-----名字获取及VO生成----------
		team=new Team();
		chName=ch;
		abbrName=abbr;
		basevo=team.getTeamBaseInfo(abbr);
		enName=basevo.getTeamName();
		//----------------------------
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		gbc.insets=new Insets(10,30,10,30);
		setLayout(gbl);
		//----------------------------
		JLabel enNameLbl=new JLabel(enName);
		enNameLbl.setFont(new Font("微软雅黑",Font.PLAIN,35));
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=10;
		gbc.gridheight=2;
		gbc.weightx=10;
		gbc.weighty=2;
		gbl.setConstraints(enNameLbl, gbc);
		add(enNameLbl);
		//----------------------------
		infoPnl=new JPanel();
		infoPnl.setOpaque(false);
		infoPnl.setLayout(new GridLayout(4,2));
		gbc.gridy=2;
		gbc.gridheight=5;
		gbc.weighty=5;
		gbl.setConstraints(infoPnl, gbc);
		add(infoPnl);
		//------base info-------------
		MyLabel chNameLbl=new MyLabel("中文名："+chName);
		infoPnl.add(chNameLbl);
		MyLabel abbrNameLbl=new MyLabel("缩写："+abbrName);
		infoPnl.add(abbrNameLbl);
		MyLabel locationLbl=new MyLabel("所在地："+basevo.getLocation());
		infoPnl.add(locationLbl);
		MyLabel conferenceLbl=new MyLabel("联盟："+basevo.getConference());
		infoPnl.add(conferenceLbl);
		MyLabel partitionLbl=new MyLabel("分区："+basevo.getPartition());
		infoPnl.add(partitionLbl);
		MyLabel homeCourtLbl=new MyLabel("主场："+basevo.getHomeCourt());
		infoPnl.add(homeCourtLbl);
		MyLabel setUpTimeLbl=new MyLabel("成立时间："+basevo.getSetUpTime());
		infoPnl.add(setUpTimeLbl);
		//----------------------------
		recentPnl=new TeamDetailRecentPanel(abbrName);
		gbc.gridy=7;
		gbc.gridheight=3;
		gbc.weighty=3;
		gbl.setConstraints(recentPnl, gbc);
		add(recentPnl);
	}
	class MyLabel extends JLabel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public MyLabel(String t){
			super(t);
			setFont(new Font("微软雅黑",Font.PLAIN,15));
		}
		
	}
}
