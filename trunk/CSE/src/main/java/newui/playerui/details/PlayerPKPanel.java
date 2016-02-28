package newui.playerui.details;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import vo.PlayerVO;

public class PlayerPKPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//-----------------------
	public PKNamePanel namePnl;
	public ChartPanel chartPnl;
	public JPanel statsPnl;
	PlayerVO defaultVO;
	public PlayerPKPanel(PlayerVO d){
		defaultVO=d;
		//------------------------------
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		setLayout(gbl);
		//----------大区划分---------------
		namePnl=new PKNamePanel(this);
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=10;
		gbc.gridheight=2;
		gbc.weightx=10;
		gbc.weighty=2;
		gbl.setConstraints(namePnl, gbc);
		add(namePnl);
		//------------
		chartPnl=new ChartPanel(null);
		gbc.gridy=2;
		gbc.gridheight=4;
		gbc.weighty=4;
		gbl.setConstraints(chartPnl, gbc);
		add(chartPnl);
		//-----------
		statsPnl=new JPanel();
		gbc.gridy=6;
		gbl.setConstraints(statsPnl, gbc);
		add(statsPnl);
		
	}
	JFreeChart paintChart(){
		return null;
	}
}
