package newui.playerui.details;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import newui.Service;
import newui.Style;
import newui.mainui.MainFrame;
import newui.teamui.TeamDetailPanel;
import newui.teamui.details.TeamDetailRecentPanel;
import vo.PlayerVO;
import bl.Team;
import bl.Player;
import blService.PlayerBLService;


public class PlayerDetailInfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel headPnl, infoPnl, recentPnl;
	JLabel teamIcon;
	PlayerVO vo;
	PlayerBLService pservice;

	public PlayerDetailInfoPanel(PlayerVO vo) {

		this.vo = vo;

		// -----------------
		// pservice = new Player();
		pservice = Service.player;
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		// ------headPnl------------
		headPnl = new JPanel();
		headPnl.setOpaque(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 2;
		gbc.weightx = 10;
		gbc.weighty = 2;
		gbl.setConstraints(headPnl, gbc);
		add(headPnl);
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		fl.setHgap(20);
		headPnl.setLayout(fl);
		// ------------------
		ImageIcon icon=Player.getPlayerPortraitImage(vo.getName());
		icon.setImage(icon.getImage().getScaledInstance(230, 185, Image.SCALE_SMOOTH));

		JLabel portraitLbl = new JLabel(icon);
		headPnl.add(portraitLbl);
		JLabel nameLbl = new JLabel(vo.getName());
		nameLbl.setFont(new Font("华文细黑", Font.PLAIN, 28));
		headPnl.add(nameLbl);
		headPnl.add(new JLabel("             "));
		headPnl.add(new JLabel("             "));
		headPnl.add(new JLabel("             "));
		ImageIcon teamIco=Team.getTeamImage(vo.getOwingTeam());
		teamIco.setImage(teamIco.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));

		teamIcon = new JLabel(teamIco);
		teamIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		teamIcon.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				MainFrame.setContentPanel(new TeamDetailPanel(
						PlayerDetailInfoPanel.this.vo.getOwingTeam()));

			}
		});
		headPnl.add(teamIcon);
		// -----infoPnl---------------
		infoPnl = new JPanel();
		infoPnl.setOpaque(false);
		gbc.gridy = 2;
		gbc.gridheight = 6;
		gbc.weighty = 6;
		gbl.setConstraints(infoPnl, gbc);
		add(infoPnl);
		// ------------------
		infoPnl.setLayout(new GridLayout(5, 2));
		MyLabel teamLbl = new MyLabel("所属球队："
				+ Team.changeTeamNameENToCH(vo.getOwingTeam()));
		infoPnl.add(teamLbl);
		MyLabel numLbl = new MyLabel("球衣号码：" + vo.getNumber());
		infoPnl.add(numLbl);
		MyLabel positionLbl = new MyLabel("位置：" + vo.getPosition());
		infoPnl.add(positionLbl);
		MyLabel heightLbl = new MyLabel("身高（英尺-英寸）：" + vo.getHeight());
		infoPnl.add(heightLbl);
		MyLabel weightLbl = new MyLabel("体重（英镑）:" + vo.getWeight());
		infoPnl.add(weightLbl);
		MyLabel birthLbl = new MyLabel("生日：" + vo.getBirth());
		infoPnl.add(birthLbl);
		MyLabel ageLbl = new MyLabel("年龄：" + vo.getAge());
		infoPnl.add(ageLbl);
		MyLabel expLbl = new MyLabel("球龄：" + vo.getExp());
		infoPnl.add(expLbl);
		MyLabel schoolLbl = new MyLabel("毕业学校：" + vo.getSchool());
		infoPnl.add(schoolLbl);
		// -------recentPnl-----------
		/*
		 * 注意：加监听时把下一行的注释解除，传入其所属的球队 删除下面的recentPnl=new JPanel();
		 */

		double p1 = System.currentTimeMillis();
		recentPnl = new TeamDetailRecentPanel(vo.getName(), vo.getOwingTeam());
		double p2 = System.currentTimeMillis();
		System.out.println("playerRecent:" + (p2 - p1));
		// recentPnl=new JPanel();

		gbc.gridy = 8;
		gbc.gridheight = 2;
		gbc.weighty = 2;
		gbl.setConstraints(recentPnl, gbc);
		add(recentPnl);
		double post = System.currentTimeMillis();

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
