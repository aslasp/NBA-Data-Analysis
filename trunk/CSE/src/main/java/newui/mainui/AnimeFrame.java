package newui.mainui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import newui.UIhelper;

public class AnimeFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	int sWidth=UIhelper.getScreenWidth();
	int sHeight=UIhelper.getScreenHeight();

	JLabel back;
	public static boolean stop=false;
	public AnimeFrame(){
		setBounds((sWidth-570)/2,(sHeight-304)/2,570,304);
		setUndecorated(true);
		setIconImage(UIhelper.getImage("image/appicon.png"));
//		pnl = new JPanel(){
//			private static final long serialVersionUID = 1L;
//			// 给panel加上图片
//			protected void paintComponent(Graphics g) {
//				 icon = new ImageIcon("image/backup.gif");
//				Image img = icon.getImage();
//				g.drawImage(img, 0, 0, icon.getIconWidth(),
//						icon.getIconHeight(), icon.getImageObserver());
//			}
//		};
	back=new JLabel(new ImageIcon("image/backup.gif"));
		add(back);
		setVisible(true);
		repaint();
	}
	
	public void setBackImage(ImageIcon im){
		back.setIcon(im);
		//AnimeFrame.this.repaint();
		
	}
	public static void setStop(){
		stop=true;
	}
}
