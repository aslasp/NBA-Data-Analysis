package newui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel titlePnl,namePnl,exitPnl;
	public AboutDialog() {
		int screenWidth = UIhelper.getScreenWidth();
		int screenHeight = UIhelper.getScreenHeight();
		setBounds((screenWidth - 450) / 2, (screenHeight - 150) / 2, 450, 150);
		setUndecorated(true);
		getContentPane().setBackground(Style.DEEP_BLUE);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// ---------------------
		// ------------------------------
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		gbc.insets=new Insets(5,10,5,10);
		//-------------------------------
		titlePnl=new JPanel();
		titlePnl.setBackground(Color.white);
		titlePnl.setLayout(new GridLayout(1,1));
		JLabel title=new JLabel("关于  羽见青柠",JLabel.CENTER);
		title.setFont(new Font("微软雅黑",Font.PLAIN,17));
		title.setForeground(Style.DEEP_BLUE);
		titlePnl.add(title);
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=10;
		gbc.gridheight=3;
		gbc.weightx=10;
		gbc.weighty=1;
		gbl.setConstraints(titlePnl, gbc);
		add(titlePnl);
		//---------------------------------
		gbc.insets=new Insets(5,10,5,10);
		namePnl=new JPanel();
		namePnl.setBackground(Color.white);
		namePnl.setLayout(new GridLayout(1,1));
		JLabel name=new JLabel("本系统由羽见青柠开发，组员：金翠，严顺宽，黄涵倩，王宁。");
		name.setFont(new Font("微软雅黑",Font.PLAIN,15));
		name.setForeground(Style.DEEP_BLUE);
		namePnl.add(name);
		gbc.gridy=3;
		gbc.gridheight=5;
		gbc.weighty=20;
		gbl.setConstraints(namePnl, gbc);
		add(namePnl);
		//---------------------------------
		gbc.insets=new Insets(5,10,15,0);
		exitPnl=new JPanel();
		exitPnl.setBackground(Style.DEEP_BLUE);
		//exitPnl.setLayout(new GridLayout(1,1));
		JButton exit=new JButton("好的");
		exit.setFont(new Font("微软雅黑",Font.PLAIN,15));
		exit.setForeground(Color.white);
		exit.setBackground(Style.DEEP_BLUE);
		exit.setFocusPainted(false);
		exit.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				AboutDialog.this.dispose();
			}
		});
		exitPnl.add(exit);
		gbc.gridy=8;
		gbc.gridheight=2;
		gbc.weighty=0.01;
		gbl.setConstraints(exitPnl, gbc);
		add(exitPnl);
		revalidate();
	}

}
