package newui.playerui.details;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerDetailInfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	JPanel headPnl, infoPnl, recentPnl;

	public PlayerDetailInfoPanel(String pname) {
		name = pname;
		// -----------------
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
		JLabel portraitLbl = new JLabel(new ImageIcon(
				"image/player/player65/"+name+".png"));
		headPnl.add(portraitLbl);
		JLabel nameLbl = new JLabel(name);
		nameLbl.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		headPnl.add(nameLbl);
		// -----infoPnl---------------
		infoPnl = new JPanel();
		infoPnl.setOpaque(false);
		gbc.gridy = 2;
		gbc.gridheight = 6;
		gbc.weighty = 6;
		gbl.setConstraints(infoPnl, gbc);
		add(infoPnl);
		//------------------
		infoPnl.setLayout(new GridLayout(4,2));
		MyLabel numLbl=new MyLabel("球衣号码：监听");
		infoPnl.add(numLbl);
		MyLabel positionLbl=new MyLabel("位置：JT");
		infoPnl.add(positionLbl);
		MyLabel heightLbl=new MyLabel("身高（英尺-英寸）：J-T");
		infoPnl.add(heightLbl);
		MyLabel weightLbl=new MyLabel("体重（英镑）：监听");
		infoPnl.add(weightLbl);
		MyLabel birthLbl=new MyLabel("生日：FEB 12,1988");
		infoPnl.add(birthLbl);
		MyLabel ageLbl=new MyLabel("年龄：158");
		infoPnl.add(ageLbl);
		MyLabel expLbl=new MyLabel("球龄：158");
		infoPnl.add(expLbl);
		MyLabel schoolLbl=new MyLabel("毕业学校：监听大学");
		infoPnl.add(schoolLbl);
		// -------recentPnl-----------
		/*
		 * 注意：加监听时把下一行的注释解除，传入其所属的球队
		 * 删除下面的recentPnl=new JPanel();
		 */
		//recentPnl = new TeamDetailRecentPanel("这里传队名");
		recentPnl=new JPanel();
		gbc.gridy = 8;
		gbc.gridheight = 2;
		gbc.weighty = 2;
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
