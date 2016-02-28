package newui.mainui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel{

	/**
	 * 首页上方导航栏及图片
	 */
	private static final long serialVersionUID = 1L;
	JLabel logoLbl,textLbl;
	IndexTitleBar titleBar;
	public TitlePanel(){
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		setLayout(gbl);
		//-------logoLbl----------
		logoLbl=new JLabel(new ImageIcon("image/NBALogo.png"));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 5;
		gbc.weightx =1;
		gbc.weighty = 10;
		gbl.setConstraints(logoLbl, gbc);
		add(logoLbl);
		//------textLbl-----------
		textLbl=new JLabel(new ImageIcon("image/nameText.png"));
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth =4;
		gbc.gridheight =2;
		gbc.weightx =2;
		gbc.weighty = 3;
		gbl.setConstraints(textLbl, gbc);
		add(textLbl);
		//----titleBar-----------
		titleBar=new IndexTitleBar();
		gbc.gridx =6;
		gbc.gridy = 4;
		gbc.gridwidth =GridBagConstraints.REMAINDER;
		gbc.gridheight =1;
		gbc.weightx=20;
		gbc.weighty = 1;
		gbl.setConstraints(titleBar, gbc);
		add(titleBar);
		setBackground(new Color(0,103,175));
		
	}

}
