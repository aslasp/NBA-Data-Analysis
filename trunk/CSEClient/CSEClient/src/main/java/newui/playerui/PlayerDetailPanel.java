package newui.playerui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import newui.FatherPanel;
import newui.playerui.details.PlayerDetailHistoryPanel;
import newui.playerui.details.PlayerDetailInfoPanel;

public class PlayerDetailPanel extends FatherPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	JLabel actionPicLbl;
	JTabbedPane tab;
	public PlayerDetailPanel(String pname){
		name=pname;
		//----------------
		actionPicLbl=new JLabel(new ImageIcon("image/player/action/"+name+".png"));
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		gbc.gridheight=10;
		gbc.weightx=1;
		gbc.weighty=100;
		gbl.setConstraints(actionPicLbl, gbc);
		add(actionPicLbl);
		//----------------
		tab=new JTabbedPane();
		gbc.gridx=1;
		gbc.gridwidth=9;
		gbc.weightx=50;
		gbl.setConstraints(tab, gbc);
		add(tab);
		tab.addTab("基本信息", new PlayerDetailInfoPanel(name));
		tab.addTab("过往数据",new PlayerDetailHistoryPanel(name));
	}
	public static void main(String[] args) {
		JFrame f=new JFrame();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(50, 50, 1100, 670);
		f.add(new PlayerDetailPanel("Kobe Bryant"));
		f.repaint();
	}
}
