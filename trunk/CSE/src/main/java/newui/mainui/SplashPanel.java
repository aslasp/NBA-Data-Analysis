package newui.mainui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SplashPanel extends JPanel {
	private boolean hasStarted = false;
	private boolean hasDone = false;
	private FComponent fc = new FComponent();
	private Image image = new ImageIcon("image/detailBack.png").getImage();// text
	private Image image2 = new ImageIcon("image/NBALogo.png").getImage();// grapics
	private Image image3 = new ImageIcon("image/kobelogo.png").getImage();// moist

	@Override
	protected void paintComponent(Graphics g) {
		ImageIcon icon = new ImageIcon("GUI\\welcome0.jpg");
		Image img = icon.getImage();
		g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(),
				icon.getImageObserver());
	}

	class FComponent extends JComponent {
		float alpha = 0.0f;
		boolean imageFlag = true;// true for 0 false for 1

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			AlphaComposite comp = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, alpha);
			g2d.setComposite(comp);
			if (imageFlag)
				g2d.drawImage(image3, 0, 0, this);
			else {
				g2d.drawImage(image2, 0, 0, this);
			}
		}
	}

	class FController implements Runnable {


		public void run() {
			try {
				fc.alpha = 1.0f;
				fc.repaint();
				Thread.sleep(100);
				for (int i = 60; i > 0; i--) {
					fc.alpha = ((float) i) / 60;
					Thread.sleep(50);
					fc.repaint();
				}
				Thread.sleep(100);
				fc.imageFlag = false;
				for (int i = 0; i <= 40; i++) {
					fc.alpha = ((float) i) / 40;
					Thread.sleep(20);
					fc.repaint();
				}
				Thread.sleep(100);
				hasDone = true;
				

			} catch (Exception e) {
			}
		}
	}

	public SplashPanel() {
		super();
		fc.setSize(1280, 720);
		this.setLayout(null);
		this.setSize(1280, 720);
		this.add(fc);
		this.setVisible(true);
//		this.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				if (hasDone){
//					ViewController.frame.setVisible(false);
//				}
//			}
//		});
		threadStart();
	}

	public void threadStart() {
		if (hasStarted == true)
			return;
		hasStarted = true;
		new Thread(new FController()).start();
	}
public static void main(String[] args) {
	JFrame frame=new JFrame();
	frame.setBounds(0, 0, 1000, 500);
	frame.setVisible(true);
	frame.add(new SplashPanel());
}
}
