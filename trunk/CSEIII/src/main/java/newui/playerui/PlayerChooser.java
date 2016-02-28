package newui.playerui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import newui.Service;
import newui.Style;
import newui.UIhelper;
import newui.VOLabel;
import newui.playerui.details.PlayerPKPanel;
import vo.PlayerVO;
import bl.Player;
import blService.PlayerBLService;

public class PlayerChooser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField jtf;
	JButton exitBtn;
	JScrollPane jsp;
	PlayerBLService p;
	PlayerPKPanel owner;
	boolean isA;

	public PlayerChooser(PlayerPKPanel o, boolean bool) {
		isA = bool;
		owner = o;
		p = Service.player;
		int screenWidth = UIhelper.getScreenWidth();
		int screenHeight = UIhelper.getScreenHeight();
		setBounds((screenWidth - 550) / 2, (screenHeight - 400) / 2, 550, 400);
		setUndecorated(true);
		getContentPane().setBackground(Style.DEEP_BLUE);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// ------------------------------
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		// ------jtf-------------------
		gbc.insets = new Insets(5, 4, 5, 5);
		jtf = new JTextField();
		jtf.setFont(new Font("华文细黑", Font.PLAIN, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 8;
		gbc.gridheight = 1;
		gbc.weightx = 8;
		gbc.weighty = 0.1;
		gbl.setConstraints(jtf, gbc);
		add(jtf);
		jtf.addKeyListener(new jtfListener());
		// -----exitBtn-----------------
		gbc.insets = new Insets(5, 0, 5, 4);
		exitBtn = new JButton("放弃");
		exitBtn.setBackground(Style.DEEP_BLUE);
		exitBtn.setForeground(Color.white);
		exitBtn.setBorder(BorderFactory.createLineBorder(Color.white));
		exitBtn.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		exitBtn.setFocusPainted(false);
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		gbc.gridx = 8;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbl.setConstraints(exitBtn, gbc);
		add(exitBtn);
		// -----jsp----------------
		gbc.insets = new Insets(0, 4, 3, 4);
		jsp = new JScrollPane();
		jsp.getViewport().setOpaque(false);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 9;
		gbc.gridheight = 9;
		gbc.weightx = 9;
		gbc.weighty = 9;
		gbl.setConstraints(jsp, gbc);
		add(jsp);
		revalidate();
	}

	void refreshJsp(ArrayList<PlayerVO> arr) {
		int size = arr.size();
		JPanel resultPnl = new JPanel();
		resultPnl.setBackground(Color.white);
		resultPnl.setLayout(new GridLayout((size + 1) / 2, 2));
		for (int i = 0; i < size; i++) {
			ResultLabel lbl = new ResultLabel(arr.get(i).getName(), arr.get(i));
			resultPnl.add(lbl);
		}
		resultPnl.setPreferredSize(new Dimension(500, 60 * (size + 1) / 2));
		jsp.getViewport().add(resultPnl);
	}

	// --------------------------------------------------

	class ResultLabel extends VOLabel {
		private static final long serialVersionUID = 1L;

		ResultLabel(String name, PlayerVO p) {
			super(name, p);
			ImageIcon i = Player.getPlayerPortraitImage(name);
			i.setImage(i.getImage().getScaledInstance(75, 60,
					Image.SCALE_SMOOTH));
			setIcon(i);
			setForeground(Style.DEEP_BLUE);
			setFont(new Font("华文细黑", Font.PLAIN, 14));
			addMouseListener(new ResultLblListener());
		}
	}

	class ResultLblListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			ResultLabel temp = (ResultLabel) e.getSource();
			if (isA) {
				owner.namePnl.aLbl.setText(temp.getVO().getName());
				owner.namePnl.aLbl.setVO(temp.getVO());
				ImageIcon i =Player.getPlayerPortraitImage( temp.getVO().getName());
				i.setImage(i.getImage().getScaledInstance(168, 135,
						Image.SCALE_SMOOTH));
				owner.namePnl.aImgLbl.setIcon(i);
				owner.namePnl.aTeamLbl.setText(temp.getVO().getPosition() + "/"
						+ temp.getVO().getOwingTeam());

			} else {
				owner.namePnl.bLbl.setText(temp.getVO().getName());
				owner.namePnl.bLbl.setVO(temp.getVO());
				ImageIcon i = Player.getPlayerPortraitImage( temp.getVO().getName());
				i.setImage(i.getImage().getScaledInstance(168, 135,
						Image.SCALE_SMOOTH));
				owner.namePnl.bImgLbl.setIcon(i);
				owner.namePnl.bTeamLbl.setText(temp.getVO().getPosition() + "/"
						+ temp.getVO().getOwingTeam());
			}
			owner.paintChart();
			owner.repaint();
			owner.revalidate();
			PlayerChooser.this.dispose();
		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseEntered(MouseEvent e) {
			ResultLabel temp = (ResultLabel) e.getSource();
			temp.setForeground(Style.FOCUS_BLUE);
		}

		public void mouseExited(MouseEvent e) {
			ResultLabel temp = (ResultLabel) e.getSource();
			temp.setForeground(Style.DEEP_BLUE);
		}

	}

	class jtfListener implements KeyListener {

		public void keyTyped(KeyEvent e) {
			ArrayList<PlayerVO> searchResult = p.getPlayerActiveBaseInfoForVague(jtf
					.getText());
			// refreshJsp(searchResult);
			RefreshThread thre = new RefreshThread(searchResult);
			thre.start();
		}

		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class RefreshThread extends Thread {
		ArrayList<PlayerVO> searchResult;

		public RefreshThread(ArrayList<PlayerVO> arr) {
			searchResult = arr;
		}

		public void run() {
			refreshJsp(searchResult);
		}

	}
}
