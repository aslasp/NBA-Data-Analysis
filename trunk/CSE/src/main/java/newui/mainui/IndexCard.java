package newui.mainui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import newui.Style;

public class IndexCard extends JPanel{

	private static final long serialVersionUID = 1L;
	JPanel iconPnl,namePnl;
	JLabel iconLbl,titleLbl,nameLbl;
	public IndexCard(){

		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		setLayout(gbl);
		iconPnl=new JPanel();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=1;
		gbc.gridheight=7;
		gbc.weightx=1;
		gbc.weighty=7;
		gbl.setConstraints(iconPnl, gbc);
		add(iconPnl);
		iconPnl.setLayout(new GridLayout(1,1));	
		iconPnl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//-----------
		iconLbl=new JLabel();
		iconPnl.add(iconLbl);
		//---------------------------
		namePnl=new JPanel();
		namePnl.setLayout(new GridLayout(2,1));
		gbc.gridy=7;
		gbc.gridheight=2;
		gbc.weighty=2;
		gbl.setConstraints(namePnl, gbc);
		namePnl.setBackground(Style.DEEP_BLUE);
		add(namePnl);
		//--------------------------
		titleLbl=new JLabel();
		titleLbl.setFont(new Font("微软雅黑",Font.PLAIN,18));
		titleLbl.setForeground(Color.white);
		namePnl.add(titleLbl);
		//
		nameLbl=new JLabel();
		nameLbl.setFont(new Font("微软雅黑",Font.PLAIN,16));
		nameLbl.setForeground(Color.white);
		namePnl.add(nameLbl);
	}
	public void setTitleAndName(String title,String name){
		titleLbl.setText(title);
		nameLbl.setText(name);
	}

}
