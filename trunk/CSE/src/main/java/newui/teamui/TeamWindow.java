package newui.teamui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import bl.team.Team;
import newui.Style;
import newui.UIhelper;
import newui.mainui.MainFrame;

public class TeamWindow extends JWindow implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static TeamWindow instance = null;
	int screenWidth = UIhelper.getScreenWidth();
	int screenHeight = UIhelper.getScreenHeight();
	int width = screenWidth * 50 / 100;
	int height = screenHeight * 40 / 100;
	JPanel pnl, westPnl, eastPnl;

	private TeamWindow(final int x,final  int y) {
		super(MainFrame.getInstance());
		pnl = new JPanel();
		add(pnl);
		setBounds(x - 55, y, width, height);
		setVisible(true);
	//	addMouseListener(this);
		pnl.setLayout(new GridLayout(1, 2));
		westPnl = new PartitionPanel(0);
		pnl.add(westPnl);
		eastPnl = new PartitionPanel(1);
		pnl.add(eastPnl);
		
		pnl.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				TeamWindow.this.setVisible(true);
			}
			public void mouseExited(MouseEvent e){
			double x=	westPnl.getLocationOnScreen().getX();
			double y=westPnl.getLocationOnScreen().getY();
			int X=e.getXOnScreen();
			int Y=e.getYOnScreen();
				if((x<=X&&X<=x+width&&y<=Y&&Y<=(y+height)))
					;
				else
					TeamWindow.this.setVisible(false);
			}
		});

	}
	

	public static TeamWindow getInstance(int x, int y) {
		if (instance == null)
			instance = new TeamWindow(x, y);
		// instance.setLocation(x-55,y);//实时变动位置
		return instance;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().getClass() == MyButton.class) {
			MyButton btn = (MyButton) e.getSource();
			MainFrame.getInstance().setContentPanel(
					new TeamDetailPanel(Team.changeTeamNameCHToEN(btn.getText())));
			setVisible(false);
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		
		if (e.getSource().getClass() == MyButton.class) {
		//	setVisible(true);
			MyButton btn = (MyButton) e.getSource();
			btn.setForeground(Style.FOCUS_BLUE);
		}
	}

	public void mouseExited(MouseEvent e) {
		
		if (e.getSource().getClass() == MyButton.class) {
			MyButton btn = (MyButton) e.getSource();
			btn.setForeground(Color.black);
		}
	}

	class PartitionPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String[] wText = { "西部球队", "西北分区", "掘金", "森林狼", "雷霆", "开拓者", "爵士",
				"太平洋分区", "勇士", "快船", "湖人", "太阳", "国王", "西南分区", "小牛", "火箭",
				"灰熊", "鹈鹕", "马刺" };
		String[] eText = { "东部球队", "大西洋分区", "凯尔特人", "篮网", "尼克斯", "76人", "猛龙",
				"中央分区", "公牛", "骑士", "活塞", "步行者", "雄鹿", "东南分区", "老鹰", "黄蜂",
				"热火", "魔术", "奇才" };

		public PartitionPanel(int part) {
			setBackground(Color.white);
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			setLayout(gbl);
			String[] text;
			if (part == 0)
				text = wText;
			else
				text = eText;
			// ----------title----------------
			JLabel titleLbl = new JLabel(text[0], new ImageIcon("image/"
					+ text[0] + ".png"), JLabel.CENTER);
			titleLbl.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			titleLbl.setForeground(new Color(6, 73, 130));
			titleLbl.setHorizontalAlignment(JLabel.CENTER);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 3;
			gbc.gridheight = 1;
			gbc.weightx = 3;
			gbc.weighty = 1;
			gbl.setConstraints(titleLbl, gbc);
			add(titleLbl);
			// -------------------------------------------------------第一列
			// -------------标题------------
			JLabel firstColLbl = new JLabel(text[1]);
			firstColLbl.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			firstColLbl.setForeground(new Color(6, 73, 130));
			firstColLbl.setHorizontalAlignment(JLabel.CENTER);
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbl.setConstraints(firstColLbl, gbc);
			add(firstColLbl);
			// -----------按钮--------------
			MyButton btn11 = new MyButton(text[2]);
			gbc.gridy = 2;
			gbl.setConstraints(btn11, gbc);
			add(btn11);
			//
			MyButton btn12 = new MyButton(text[3]);
			gbc.gridy = 3;
			gbl.setConstraints(btn12, gbc);
			add(btn12);
			//
			MyButton btn13 = new MyButton(text[4]);
			gbc.gridy = 4;
			gbl.setConstraints(btn13, gbc);
			add(btn13);
			//
			MyButton btn14 = new MyButton(text[5]);
			gbc.gridy = 5;
			gbl.setConstraints(btn14, gbc);
			add(btn14);
			//
			MyButton btn15 = new MyButton(text[6]);
			gbc.gridy = 6;
			gbl.setConstraints(btn15, gbc);
			add(btn15);
			// -------------------------------------------------------第二列
			// -------------标题------------
			JLabel secondColLbl = new JLabel(text[7]);
			secondColLbl.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			secondColLbl.setForeground(new Color(6, 73, 130));
			secondColLbl.setHorizontalAlignment(JLabel.CENTER);
			gbc.gridx = 1;
			gbc.gridy = 1;
			gbl.setConstraints(secondColLbl, gbc);
			add(secondColLbl);
			// -----------按钮--------------
			MyButton btn21 = new MyButton(text[8]);
			gbc.gridy = 2;
			gbl.setConstraints(btn21, gbc);
			add(btn21);
			//
			MyButton btn22 = new MyButton(text[9]);
			gbc.gridy = 3;
			gbl.setConstraints(btn22, gbc);
			add(btn22);
			//
			MyButton btn23 = new MyButton(text[10]);
			gbc.gridy = 4;
			gbl.setConstraints(btn23, gbc);
			add(btn23);
			//
			MyButton btn24 = new MyButton(text[11]);
			gbc.gridy = 5;
			gbl.setConstraints(btn24, gbc);
			add(btn24);
			//
			MyButton btn25 = new MyButton(text[12]);
			gbc.gridy = 6;
			gbl.setConstraints(btn25, gbc);
			add(btn25);
			// -------------------------------------------------------第三列
			// -------------标题------------
			JLabel thirdColLbl = new JLabel(text[13]);
			thirdColLbl.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			thirdColLbl.setForeground(new Color(6, 73, 130));
			thirdColLbl.setHorizontalAlignment(JLabel.CENTER);
			gbc.gridx = 2;
			gbc.gridy = 1;
			gbl.setConstraints(thirdColLbl, gbc);
			add(thirdColLbl);
			// -----------按钮--------------
			MyButton btn31 = new MyButton(text[14]);
			gbc.gridy = 2;
			gbl.setConstraints(btn31, gbc);
			add(btn31);
			//
			MyButton btn32 = new MyButton(text[15]);
			gbc.gridy = 3;
			gbl.setConstraints(btn32, gbc);
			add(btn32);
			//
			MyButton btn33 = new MyButton(text[16]);
			gbc.gridy = 4;
			gbl.setConstraints(btn33, gbc);
			add(btn33);
			//
			MyButton btn34 = new MyButton(text[17]);
			gbc.gridy = 5;
			gbl.setConstraints(btn34, gbc);
			add(btn34);
			//
			MyButton btn35 = new MyButton(text[18]);
			gbc.gridy = 6;
			gbl.setConstraints(btn35, gbc);
			add(btn35);
			
		}

	}

	class MyButton extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Font font = new Font("微软雅黑", Font.PLAIN, 13);

		public MyButton(String name) {
			super(name, new ImageIcon("image/teamIcon/teamsPng35/" + name
					+ ".png"), JLabel.LEFT);
			setFont(font);
			addMouseListener(TeamWindow.this);
				
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

	}
}
