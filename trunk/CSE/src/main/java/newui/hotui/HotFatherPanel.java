package newui.hotui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HotFatherPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	GridBagLayout gbl;
	GridBagConstraints gbc;
	JPanel bottomBar;
	JPanel bestPnl;
	JScrollPane jsp;
	protected HotThread thr;
	
	public HotFatherPanel(){
		
		gbl=new GridBagLayout();
		gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		setLayout(gbl);
		//---------------------
		bestPnl=new JPanel();
		bestPnl.setBackground(Color.white);
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=10;
		gbc.gridheight=3;
		gbc.weightx=10;
		gbc.weighty=3;
		gbl.setConstraints(bestPnl, gbc);
		add(bestPnl);
		//----------------------
		gbc.insets=new Insets(0, 2, 1, 2);
		jsp=new JScrollPane();
		gbc.gridy=3;
		gbc.gridheight=5;
		gbc.weighty=5;
		gbl.setConstraints(jsp, gbc);
		add(jsp);
		//----------------------
		bottomBar=new JPanel();
		gbc.gridy=8;
		gbc.gridheight=1;
		gbc.weighty=1;
		gbl.setConstraints(bottomBar, gbc);
		add(bottomBar);
	}
	class BottomButton extends JButton{
		private static final long serialVersionUID = 1L;
		public BottomButton(String txt){
			super(txt);
			setFont(new Font("微软雅黑",Font.PLAIN,15));
			setBorderPainted(false);
			setFocusPainted(false);
			setForeground(Color.white);
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}
	
	public void Refresh(String s){
		
	}
}
