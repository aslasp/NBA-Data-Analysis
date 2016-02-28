package RunServer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServerMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	Toolkit kit = Toolkit.getDefaultToolkit();
	int screenWidth = kit.getScreenSize().width;
	int screenHeight = kit.getScreenSize().height;
	int frameWidth = 380;
	int frameHeight = 250;
	Font font = new Font("微软雅黑", Font.PLAIN, 14);
	int xOld, yOld;
	// -------------------------
	JButton exitBtn;
	JLabel title, IPLbl, portLbl;

	public ServerMainFrame(String IP, String portNum)
			throws UnknownHostException {
		this.setTitle("NBA Master服务器界面");
		this.setIconImage(kit.getImage("img/icon.png"));
		this.setBounds((screenWidth - frameWidth) / 2,
				(screenHeight - frameHeight) / 2, frameWidth, frameHeight);
		this.setLayout(new GridLayout(1, 1));
		JPanel pnl = new JPanel() {
			private static final long serialVersionUID = 1L;

			// 给panel加上图片
			protected void paintComponent(Graphics g) {
				ImageIcon icon = new ImageIcon("img/net.png");
				Image img = icon.getImage();
				g.drawImage(img, 0, 0, icon.getIconWidth(),
						icon.getIconHeight(), icon.getImageObserver());
			}
		};
		this.add(pnl);
		// -------------------------
		pnl.setLayout(new GridLayout(4, 1));
		JPanel titlePnl = new JPanel();
		titlePnl.setOpaque(false);
		pnl.add(titlePnl);
		JLabel title = new JLabel("NBA Master服务器界面");
		title.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		title.setForeground(Color.white);
		titlePnl.add(title);
		// -------------------------
		JPanel top = new JPanel();
		top.setOpaque(false);
		pnl.add(top);
		JPanel mid = new JPanel();
		mid.setOpaque(false);
		pnl.add(mid);
		JPanel bottom = new JPanel();
		bottom.setOpaque(false);
		pnl.add(bottom);
		// ---------IP---------------
		IPLbl = new JLabel("服务器IP:" + IP);
		IPLbl.setFont(font);
		IPLbl.setForeground(Color.white);
		top.add(IPLbl);
		// --------port--------------
		portLbl = new JLabel("端口:" + portNum);
		portLbl.setFont(font);
		portLbl.setForeground(Color.white);
		mid.add(portLbl);
		// -------exit---------------
		exitBtn = new JButton("关闭服务器");
		exitBtn.setFont(font);
		exitBtn.setFocusPainted(false);
		exitBtn.setBackground(Color.white);
		bottom.add(exitBtn);
		exitBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		// -------------------
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setVisible(true);
		// 处理窗口拖动
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				xOld = e.getX();
				yOld = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int xOnScreen = e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int xx = xOnScreen - xOld;
				int yy = yOnScreen - yOld;
				ServerMainFrame.this.setLocation(xx, yy);
			}
		});
	}

}
