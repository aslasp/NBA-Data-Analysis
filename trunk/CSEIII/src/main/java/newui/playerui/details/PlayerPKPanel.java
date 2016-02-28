package newui.playerui.details;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import newui.Service;
import newui.Style;
import vo.PlayerVO;
import bl.Match;
import blService.PlayerBLService;

public class PlayerPKPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -----------------------
	public PKNamePanel namePnl;
	public JPanel chartPnl;
	//public JPanel statsPnl;
	PlayerVO defaultVO;
	public PlayerPKPanel(PlayerVO d) {
		defaultVO = d;
		// ------------------------------
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		// ----------大区划分---------------
		namePnl = new PKNamePanel(this);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 2;
		gbc.weightx = 10;
		gbc.weighty = 2;
		gbl.setConstraints(namePnl, gbc);
		add(namePnl);
		// ------------
		chartPnl = new JPanel();
		chartPnl.setBackground(Color.white);
		chartPnl.setLayout(new GridLayout(1,1));
		gbc.gridy = 2;
		gbc.gridheight = 4;
		gbc.weighty = 4;
		gbl.setConstraints(chartPnl, gbc);
		add(chartPnl);
		// -----------
//		statsPnl = new JPanel();
//		gbc.gridy = 6;
//		gbl.setConstraints(statsPnl, gbc);
//		add(statsPnl);

	}

	public void paintChart() {
		if(namePnl.aLbl.getText().equals("点击选择球员")||namePnl.bLbl.getText().equals("点击选择球员"))
			return;
		chartPnl.removeAll();
		PKChart pkChart=new PKChart(PlayerPKPanel.this);
		chartPnl.add(pkChart);
		chartPnl.revalidate();
	}

	
}
