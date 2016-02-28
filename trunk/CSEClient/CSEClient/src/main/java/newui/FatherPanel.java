package newui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class FatherPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected GridBagLayout gbl;
	protected GridBagConstraints gbc;
	protected TitleBar titleBar;
	public FatherPanel(){
		gbl=new GridBagLayout();
		gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		setLayout(gbl);
		//---TitleBar-------------
		titleBar=new TitleBar();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 1;
		gbc.weightx = 10;
		gbc.weighty = 1;
		gbl.setConstraints(titleBar, gbc);
		add(titleBar);
		setBackground(Color.white);
	}
	public void SearchRefresh(ArrayList<Object> os){
		
	}
	
	
}
