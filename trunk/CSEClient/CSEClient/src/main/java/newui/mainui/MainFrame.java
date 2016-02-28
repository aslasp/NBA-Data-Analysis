package newui.mainui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import newui.TopPanel;
import newui.UIhelper;


public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MainFrame instance=null;
	private int fx,fy;//实时坐标位置
	private Point origin;
	int screenWidth=UIhelper.getScreenWidth();
	int screenHeight=UIhelper.getScreenHeight();
	int width=screenWidth*90/100;
	int height=screenHeight*95/100;
	JPanel topPnl;
	JPanel contentPnl=new JPanel();
	private MainFrame(){
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		setLayout(gbl);
		setBounds((screenWidth-width)/2,(screenHeight-height)/2,width,height);
		setIconImage(UIhelper.getImage("image/appicon.png"));
		this.setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//-------下面是布局---------------
		
		//-------topPnl关闭按钮与最小化-----
		topPnl=new TopPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 1;
		gbc.weightx = 10;
		gbc.weighty = 0.01;
		gbl.setConstraints(topPnl, gbc);
		add(topPnl);
		//------contentPnl内容部分-------
		gbc.gridy =1;
		gbc.gridheight=10;
		gbc.weighty=10;
		gbl.setConstraints(contentPnl, gbc);
		add(contentPnl);
		setContentPanel(new IndexPanel());
		this.origin=new Point();
		
		 //由于取消了默认的窗体结构，所以我们要手动设置一下移动窗体的方法
		  this.addMouseListener( 
		        new MouseAdapter(){
		          public void mousePressed(MouseEvent e){
		            origin.x = e.getX();
		            origin.y = e.getY();
		          }
		          //窗体上单击鼠标右键关闭程序
		          public void mouseClicked(MouseEvent e) {
		            if(e.getButton()==MouseEvent.BUTTON3)
		              System.exit(0);
		          }
		          public void mouseReleased(MouseEvent e) {
		            super.mouseReleased(e);
		          }
		          @Override
		          public void mouseEntered(MouseEvent e) {
		            repaint();              
		          }            
		        }
		    );

		    addMouseMotionListener(
		        new MouseMotionAdapter(){
		          public void mouseDragged(MouseEvent e){
		            Point p = getLocation();
		            fx=p.x + e.getX() - origin.x;
		            fy=p.y + e.getY() - origin.y;
		            setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y );
		          }          
		        }
		    );  
	}
	public void setContentPanel(JPanel pnl){
		contentPnl.removeAll();
		contentPnl.setLayout(new GridLayout(1,1));
		contentPnl.add(pnl);
		contentPnl.repaint();
		contentPnl.revalidate();
	}
	public static MainFrame getInstance(){
		if(instance==null)
			instance=new MainFrame();
		return instance;
	}
	public static void main(String[] args) {
		MainFrame.getInstance();
	}
	
}
