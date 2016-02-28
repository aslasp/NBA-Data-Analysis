package newui.playerui.details;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import newui.Style;
import newui.VOLabel;
import newui.mainui.MainFrame;
import newui.playerui.PlayerChooser;
import newui.teamui.TeamDetailPanel;
import vo.PlayerVO;

public class PKNamePanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel aInfoPnl, bInfoPnl;
	public JLabel aImgLbl, bImgLbl, vsLbl;
	public JLabel aTeamLbl, bTeamLbl;
	public VOLabel aLbl, bLbl;
	PlayerPKPanel father;
	public PKNamePanel(PlayerPKPanel f) {
		father=f;
		setBackground(Color.white);
		// -----------------------------
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		// --------大区划分---------------
		gbc.insets=new Insets(0,35,0,0);
		aInfoPnl = new JPanel();
		aInfoPnl.setOpaque(false);
		aInfoPnl.setLayout(new GridLayout(2, 1));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 2;
		gbc.weightx = 3;
		gbc.weighty = 2;
		gbl.setConstraints(aInfoPnl, gbc);
		add(aInfoPnl);
		gbc.insets=new Insets(0,0,0,0);
		// ------------
		aImgLbl = new JLabel();
		gbc.gridx = 3;
		gbc.gridwidth = 2;
		gbc.weightx = 2;
		gbl.setConstraints(aImgLbl, gbc);
		add(aImgLbl);
		// ------------
		vsLbl = new JLabel("VS");
		vsLbl.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		vsLbl.setForeground(Style.WINNER_RED);
		gbc.gridx = 5;
		gbc.gridwidth = 1;
		gbc.weightx = 0.1;
		gbl.setConstraints(vsLbl, gbc);
		add(vsLbl);
		// ------------
		bImgLbl = new JLabel();
		gbc.gridx = 6;
		gbc.gridwidth = 2;
		gbc.weightx = 2;
		gbl.setConstraints(bImgLbl, gbc);
		add(bImgLbl);
		// ------------
		gbc.insets=new Insets(0,0,0,35);
		bInfoPnl = new JPanel();
		bInfoPnl.setOpaque(false);
		bInfoPnl.setLayout(new GridLayout(2, 1));
		gbc.gridx = 8;
		gbc.gridwidth = 3;
		gbc.weightx = 3;
		gbl.setConstraints(bInfoPnl, gbc);
		add(bInfoPnl);
		//-----infoPnl------------------------
		aLbl=new NameLabel(father.defaultVO.getName(),father.defaultVO);
		aInfoPnl.add(aLbl);
		aTeamLbl=new MyLabel(father.defaultVO.getPosition()+"/"+father.defaultVO.getOwingTeam());
		aInfoPnl.add(aTeamLbl);
		//----------
		bLbl=new NameLabel("点击选择球员",null);
		bInfoPnl.add(bLbl);
		bTeamLbl=new MyLabel("?/?");
		bInfoPnl.add(bTeamLbl);
		//----imgLbl---------------------------
		ImageIcon aImg=new ImageIcon("image/player/portrait/"+father.defaultVO.getName()+".png");
		aImg.setImage(aImg.getImage().getScaledInstance(168,135,Image.SCALE_SMOOTH ));
		aImgLbl.setIcon(aImg);
		//
		ImageIcon bImg=new ImageIcon("image/player/portrait/null.png");
		bImg.setImage(bImg.getImage().getScaledInstance(168,135,Image.SCALE_SMOOTH ));
		bImgLbl.setIcon(bImg);
		//--------------------------------------
	}
	
	PlayerVO getAvo(){
		return aLbl.getVO();
	}
	PlayerVO getBvo(){
		return bLbl.getVO();
	}
	//-----------------------------------------------------------------------------
	class NameLabel extends VOLabel{
		private static final long serialVersionUID = 1L;
		public NameLabel(String name,PlayerVO p){
			super(name,p);
			setForeground(Style.BACK_GREY);
			setFont(new Font("微软雅黑",Font.PLAIN,25));
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			addMouseListener(PKNamePanel.this);
		}
	}
	class MyLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		public MyLabel(String text) {
			super(text);
			setForeground(Style.DEEP_BLUE);
			setFont(new Font("微软雅黑",Font.PLAIN,18));
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			addMouseListener(PKNamePanel.this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==aLbl){
			PlayerChooser pc=new PlayerChooser(father,true);
		}
		else if(e.getSource()==bLbl){
			PlayerChooser pc=new PlayerChooser(father,false);
		}
		else if(e.getSource().getClass()==MyLabel.class){
			MyLabel temp=(MyLabel)e.getSource();
			String lblTxt=temp.getText();
			String teamName=lblTxt.substring(lblTxt.indexOf('/')+1);
			MainFrame.setContentPanel(new TeamDetailPanel(teamName));
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource().getClass()==NameLabel.class){
			NameLabel temp=(NameLabel)e.getSource();
			temp.setForeground(Style.WINNER_RED);
		}
		else if(e.getSource().getClass()==MyLabel.class){
			MyLabel temp=(MyLabel)e.getSource();
			temp.setForeground(Style.FOCUS_BLUE);
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource().getClass()==NameLabel.class){
			NameLabel temp=(NameLabel)e.getSource();
			temp.setForeground(Style.BACK_GREY);
		}
		else if(e.getSource().getClass()==MyLabel.class){
			MyLabel temp=(MyLabel)e.getSource();
			temp.setForeground(Style.DEEP_BLUE);
		}

	}
}
