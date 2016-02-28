package newui.teamui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import newui.FatherPanel;
import newui.Style;
import newui.mainui.MainFrame;
import newui.playerui.PlayerDetailPanel;
import newui.teamui.details.TeamDetailHistoryPanel;
import newui.teamui.details.TeamDetailInfoPanel;
import businesslogic.Team;
import businesslogicservice.TeamBLService;

public class TeamDetailPanel extends FatherPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nameCH,abbrName;
	ArrayList<String> players;
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
		team=new Team();
		abbrName=Team.changeTeamNameCHToEN(teamName);
		nameCH=teamName;
		//----------------------
		nameLbl=new JLabel(new ImageIcon("image/teamIcon/teamsPng150/"+abbrName+".png"));
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.weightx=1;
		gbc.weighty=10;
		gbl.setConstraints(nameLbl, gbc);
		add(nameLbl);
		//-----------------------
		playerJsp=new JScrollPane();
		playerJsp.setOpaque(false);
		playerJsp.getViewport().setOpaque(false);
		playerJsp.setBorder(null);
		gbc.gridy=2;
		gbc.gridheight=9;
		gbc.weighty=90;
		gbl.setConstraints(playerJsp, gbc);
		add(playerJsp);
		
		//------显示全部球员大头-----
		players=new ArrayList<String>(team.getPlayersByTeam(abbrName));
		playerPortraits=new ArrayList<JLabel>(players.size());
		playerPnl=new JPanel();
		playerPnl.setOpaque(false);
		playerPnl.setLayout(new GridLayout(players.size(),1));
		for(int i=0;i<players.size();i++){
			JLabel temp=new MyLabel(players.get(i),new ImageIcon("image/player/player46/"+players.get(i)+".png"),JLabel.LEFT);
			playerPortraits.add(temp);
			playerPnl.add(playerPortraits.get(i));
		}
		int height=46*players.size();
		playerPnl.setPreferredSize(new Dimension(150,height));
		playerJsp.getViewport().add(playerPnl);
		//-----------------------
		tab=new JTabbedPane();
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.gridwidth=7;
		gbc.gridheight=10;
		gbc.weightx=9;
		gbc.weighty=100;
		gbl.setConstraints(tab, gbc);
		add(tab);
		//---------------------
		infoPnl=new TeamDetailInfoPanel(nameCH,abbrName);
		historyPnl=new TeamDetailHistoryPanel();
		tab.addTab("基本信息", infoPnl);
		tab.addTab("过往数据",historyPnl);
		tab.setBorder(null);
	}
	public static void main(String[] args) {
		JFrame f=new JFrame();
		f.add(new TeamDetailPanel("马刺"));
		f.setBounds(100,100,1000,600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
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
