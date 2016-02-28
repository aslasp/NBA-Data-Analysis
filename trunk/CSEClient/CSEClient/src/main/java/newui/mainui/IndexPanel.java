package newui.mainui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class IndexPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel titlePnl,infoPnl;

	public IndexPanel(){
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		setLayout(gbl);
		//-----titlePnl-----------
		titlePnl=new TitlePanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 3;
		gbc.weightx = 10;
		gbc.weighty =0.1;
		gbl.setConstraints(titlePnl, gbc);
		add(titlePnl);
		//----infoPnl-------------
		infoPnl=new JPanel();
		infoPnl.setBackground(Color.white);
		gbc.gridx = 0;
		gbc.gridy =3;
		gbc.gridheight = 7;
		gbc.weightx = 10;
		gbc.weighty = 15;
		gbl.setConstraints(infoPnl, gbc);
		add(infoPnl);
	}
}
