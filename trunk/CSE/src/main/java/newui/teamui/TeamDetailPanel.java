package newui.teamui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import vo.PlayerVO;
import bl.player.Player;
import bl.team.Team;
import blservice.PlayerBLService;
import blservice.TeamBLService;
import newui.FatherPanel;
import newui.Service;
import newui.Style;
import newui.mainui.MainFrame;
import newui.playerui.PlayerDetailPanel;
import newui.teamui.details.TeamDetailHistoryPanel;
import newui.teamui.details.TeamDetailInfoPanel;


public class TeamDetailPanel extends FatherPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nameCH,abbrName;
	ArrayList<PlayerVO> players;
	ArrayList<JLabel> playerPortraits;
	//-----------------------
	JScrollPane playerJsp;
	JPanel playerPnl;
	JPanel infoPnl,historyPnl;
	JTabbedPane tab;
	Font font = new Font("微软雅黑", Font.PLAIN, 14);
	JLabel nameLbl;
	TeamBLService team;
	public TeamDetailPanel(String teamName){
		isDetail=true;
		team=Service.team;
		//abbrName=Team.changeTeamNameCHToEN(teamName);
		abbrName=teamName;
		nameCH=Team.changeTeamNameENToCH(teamName);
		//----------------------
	//	ImageIcon icon=team.getTeamImage(abbrName);
		nameLbl=new JLabel(new ImageIcon("image/teamIcon/teamsPng150/"+abbrName+".png"));
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=2;
		gbc.gridheight=1;
		gbc.weightx=4.5;
		gbc.weighty=10;
		gbl.setConstraints(nameLbl, gbc);
		add(nameLbl);
		//-----------------------
		playerJsp=new JScrollPane();
		playerJsp.setOpaque(false);
		playerJsp.getViewport().setOpaque(false);
		playerJsp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Style.HOT_YELLOWFOCUS),"球队成员",TitledBorder.CENTER,TitledBorder.TOP,new Font("微软雅黑",Font.PLAIN,17),Style.HOT_YELLOWFOCUS));
		gbc.gridy=2;
		gbc.gridheight=9;
		gbc.weighty=90;
		gbl.setConstraints(playerJsp, gbc);
		add(playerJsp);
		
		//------显示全部球员大头-----
		PlayerBLService play=Service.player;
		players=play.getPlayersByTeam(Team.changeTeamNameCHToEN(abbrName));
		playerPortraits=new ArrayList<JLabel>(players.size());
		playerPnl=new JPanel();
		playerPnl.setOpaque(false);
		playerPnl.setLayout(new GridLayout(players.size(),1));
		for(int i=0;i<players.size();i++){
			JLabel temp=new MyLabel(players.get(i).getName(),new ImageIcon("image/player/player46/"+players.get(i).getName()+".png"),JLabel.LEFT);
			playerPortraits.add(temp);
			playerPnl.add(playerPortraits.get(i));
		}
		int height=46*players.size();
		playerPnl.setPreferredSize(new Dimension(150,height));
		playerJsp.getViewport().add(playerPnl);
		//-----------------------
		tab=new JTabbedPane();
		gbc.gridx=2;
		gbc.gridy=1;
		gbc.gridwidth=7;
		gbc.gridheight=10;
		gbc.weightx=7;
		gbc.weighty=100;
		gbl.setConstraints(tab, gbc);
		add(tab);
		tab.setFont(new Font("微软雅黑",Font.PLAIN,15));
		tab.setBackground(Color.white);
		tab.setForeground(Style.DEEP_BLUE);
		//---------------------
		infoPnl=new TeamDetailInfoPanel(nameCH,abbrName);
		historyPnl=new TeamDetailHistoryPanel(abbrName);
		tab.addTab("基本信息", infoPnl);
		tab.addTab("过往数据",historyPnl);
		tab.setBorder(null);
	}
	class MyLabel extends JLabel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public MyLabel(String t,ImageIcon i,int a){
			super(t,i,a);
			setFont(font);
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				public void mouseExited(MouseEvent e) {
					setForeground(Color.black);
					
				}
				
				public void mouseEntered(MouseEvent e) {
					setForeground(Style.FOCUS_BLUE);
					
				}
				
				public void mouseClicked(MouseEvent e) {
					MainFrame.getInstance().setContentPanel(new PlayerDetailPanel(getText()));
				}
			});
		}
		
	}
}
