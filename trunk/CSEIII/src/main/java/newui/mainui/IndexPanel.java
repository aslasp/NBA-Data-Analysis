package newui.mainui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import newui.AboutDialog;
import newui.FatherPanel;
import newui.Style;
import newui.statsui.AgePanel;
import newui.statsui.SalaryPanel;

public class IndexPanel extends FatherPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel titlePnl, infoPnl, jumpPnl;
	JLabel bigImgLbl;
	MyButton ageBtn,salaryBtn,aboutBtn;
	public IndexPanel() {
		removeAll();
		// ----------------------------
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		// -----titlePnl-----------
		titlePnl = new TitlePanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 3;
		gbc.weightx = 10;
		gbc.weighty = 0.1;
		gbl.setConstraints(titlePnl, gbc);
		add(titlePnl);
		//-------infoPnl------------
		gbc.insets = new Insets(20, 20, 20, 0);
		infoPnl=new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 7;
		gbc.gridheight = 7;
		gbc.weightx =7;
		gbc.weighty = 7;
		gbl.setConstraints(infoPnl, gbc);
		add(infoPnl);
		infoPnl.setBackground(Color.white);
		infoPnl.setLayout(new GridLayout(1,1));
		bigImgLbl=new JLabel(new ImageIcon("image/index.png"));
		bigImgLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bigImgLbl.addMouseListener(this);
		//监听还没写
		infoPnl.add(bigImgLbl);
		//------jumpPnl---------------
		gbc.insets = new Insets(20,0, 20,20);
		jumpPnl=new JPanel();
		jumpPnl.setBackground(Style.DEEP_BLUE);
		gbc.gridx = 7;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.gridheight = 7;
		gbc.weightx =5;
		gbc.weighty = 7;
		gbl.setConstraints(jumpPnl, gbc);
		add(jumpPnl);
		jumpPnl.setLayout(new GridLayout(5,1));
		//---------------
		JLabel jumpTitle=new JLabel("传送门",JLabel.CENTER);
		jumpTitle.setFont(new Font("微软雅黑",Font.PLAIN,17));
		jumpTitle.setForeground(Color.white);
		jumpPnl.add(jumpTitle);
		//--------------
		ageBtn=new MyButton("Stats：球龄与效率值的故事");
		jumpPnl.add(ageBtn);
		//--------------
		salaryBtn=new MyButton("Stats：薪水是胡乱发的吗？");
		jumpPnl.add(salaryBtn);
		//---------------
		aboutBtn=new MyButton("关于我们");
		jumpPnl.add(aboutBtn);
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==ageBtn)
			MainFrame.getInstance().setContentPanel(new AgePanel());
		else if(e.getSource()==salaryBtn)
			MainFrame.getInstance().setContentPanel(new SalaryPanel());
		else if(e.getSource()==aboutBtn)
			new AboutDialog();
			
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		if(e.getSource().getClass()==MyButton.class){
			MyButton tempBtn=(MyButton) e.getSource();
			tempBtn.setBackground(Style.FOCUS_BLUE);
		}
	}

	public void mouseExited(MouseEvent e) {
		if(e.getSource().getClass()==MyButton.class){
			MyButton tempBtn=(MyButton) e.getSource();
			tempBtn.setBackground(Style.DEEP_BLUE);
		}
	}
	class MyButton extends JButton{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public MyButton(String txt){
			super(txt);
			setForeground(Color.white);
			setFont(new Font("微软雅黑",Font.PLAIN,15));
			setBackground(Style.DEEP_BLUE);
			setFocusPainted(false);
			setBorderPainted(false);
			addMouseListener(IndexPanel.this);
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		
	}
}
