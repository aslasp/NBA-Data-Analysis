package newui.mainui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import newui.SearchResultPanel;
import newui.Style;
import newui.hotui.HotIndexPanel;
import newui.matchui.MatchIndexPanel;
import newui.playerui.PlayerIndexPanel;
import newui.teamui.TeamIndexPanel;

public class IndexTitleBar extends JPanel implements MouseListener {

	/**
	 * 首页的TitleBar，需对文本框和搜索按钮加监听
	 */
	private static final long serialVersionUID = 1L;
	Font font = new Font("微软雅黑", Font.PLAIN, 15);
	MyButton indexBtn, teamBtn, playerBtn, matchBtn,hotBtn;
	JTextField searchFld;
	JLabel searchBtn;

	public IndexTitleBar() {

		setBackground(Style.DEEP_BLUE);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		// ---------------------------
		indexBtn = new MyButton("首页");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbl.setConstraints(indexBtn, gbc);
		add(indexBtn);
		// ----------------------------
		teamBtn = new MyButton("球队");
		gbc.gridx = 1;
		gbl.setConstraints(teamBtn, gbc);
		add(teamBtn);
		// ----------------------------
		playerBtn = new MyButton("球员");
		gbc.gridx = 2;
		gbl.setConstraints(playerBtn, gbc);
		add(playerBtn);
		// -----------------------------
		matchBtn = new MyButton("比赛");
		gbc.gridx = 3;
		gbl.setConstraints(matchBtn, gbc);
		add(matchBtn);
		// -----------------------------
		hotBtn = new MyButton("热点");
		gbc.gridx = 4;
		gbl.setConstraints(hotBtn, gbc);
		add(hotBtn);
		// -----------------------------
		searchFld = new JTextField(10);
		gbc.insets = new Insets(0, 5, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 5;
		gbc.gridwidth = 3;
		gbc.weightx = 3;
		gbl.setConstraints(searchFld, gbc);
		add(searchFld);
		// -----------------------------
		searchBtn = new JLabel(new ImageIcon("image/search.png"));
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx = 8;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbl.setConstraints(searchBtn, gbc);
		searchBtn.addMouseListener(this);
		searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(searchBtn);
		searchFld.addKeyListener(new KeyAdapter(){

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					//回车监听
					String scontent=searchFld.getText();
					MainFrame.getInstance().setContentPanel(new SearchResultPanel(scontent));
				}
	
			}

		
			
		});

	}

	class MyButton extends JButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyButton(String text) {
			super(text);
			setFont(font);
			setForeground(Color.white);
			setBackground(Style.DEEP_BLUE);
			setFocusPainted(false);
			setBorderPainted(false);
			addMouseListener(IndexTitleBar.this);
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == indexBtn){
			MainFrame.getInstance().setContentPanel(new IndexPanel());
			IndexPanel.startT();
		
		}
		else if (e.getSource() == teamBtn)
			MainFrame.getInstance().setContentPanel(new TeamIndexPanel());
		else if (e.getSource() == playerBtn){
			double pre=System.currentTimeMillis();
			PlayerIndexPanel p=new PlayerIndexPanel();
			MainFrame.setContentPanel(p);
			//frame.setContentPanel(new PlayerIndexPanel());
			double post=System.currentTimeMillis();
			System.out.println("maintoplayerindex:"+(post-pre));
			}
		else if (e.getSource() == matchBtn)
			MainFrame.getInstance().setContentPanel(new MatchIndexPanel());
		else if (e.getSource() == hotBtn)
			MainFrame.getInstance().setContentPanel(new HotIndexPanel());
		else if (e.getSource() == searchBtn) {
			// 监听
			String scontent=searchFld.getText();
			MainFrame.getInstance().setContentPanel(new SearchResultPanel(scontent));
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		if(e.getSource().getClass()==MyButton.class){
			MyButton  btn=(MyButton) e.getSource();
			btn.setBackground(Style.FOCUS_BLUE);
		}
		if (e.getSource() == searchBtn)
			searchBtn.setIcon(new ImageIcon("image/searchFocus.png"));
	}

	public void mouseExited(MouseEvent e) {
		if(e.getSource().getClass()==MyButton.class){
			MyButton  btn=(MyButton) e.getSource();
			btn.setBackground(Style.DEEP_BLUE);
		}
		if (e.getSource() == searchBtn)
			searchBtn.setIcon(new ImageIcon("image/search.png"));

	}
}
