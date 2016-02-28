package newui.matchui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import newui.FatherPanel;
import newui.Style;

public class MatchIndexPanel extends FatherPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane jsp;
	JPanel funcPnl;
	JComboBox<String> seasonBox;
	//暂无赛季选择的bl层方法
	String[] seasonText={"13-14"};
	Font font = new Font("微软雅黑", Font.PLAIN, 13);
	public MatchIndexPanel() {
		// ------funcPnl--------
		funcPnl = new JPanel();
		funcPnl.setBackground(Style.BACK_GREY);
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weighty = 0.1;
		gbl.setConstraints(funcPnl, gbc);
		add(funcPnl);
		//-----赛季选择--------
		JLabel seasonLbl=new JLabel("赛季：");
		seasonLbl.setForeground(Color.white);
		seasonLbl.setFont(font);
		funcPnl.add(seasonLbl);
		seasonBox=new JComboBox<String>(seasonText);
		seasonBox.setFont(font);
		seasonBox.setForeground(Color.white);
		seasonBox.setBackground(Style.BACK_GREY);
		funcPnl.add(seasonBox);
		
		jsp = new JScrollPane();
		gbc.gridy = 2;
		gbc.gridheight = 10;
		gbc.weighty = 10;
		gbl.setConstraints(jsp, gbc);
		add(jsp);
	}

}
