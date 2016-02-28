package newui.playerui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class MyPanel extends JPanel implements ActionListener,MouseListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable jt;
    MediaTableModel mtm;
    JPanel head,head1,head2,content,bottom;
    JLabel headTxt,timeNow,headPic1,headPic2;
    JButton jie1,jie2,jie3,jie4;
    JScrollPane jsp;
    Timer t;
    boolean isJie1=true;
    boolean isJie2=false;
    boolean isJie3=false;
    boolean isJie4=false;
    
    public MyPanel(ImageIcon img1,ImageIcon img2,String team1,String team2){
    	head=new JPanel();
    	head.setLayout(new BorderLayout());
    	
    	head1=new JPanel();
    	headTxt=new JLabel(team1+"-"+team2);
    	headTxt.setSize(20,20);
    	headTxt.setFont(new Font("宋体",Font.PLAIN,30));
    	headTxt.setForeground(Color.white);
    	headPic1= new JLabel(new ImageIcon("ATL.png"));
    	headPic1.setPreferredSize(new Dimension(60,60));
    	headPic2=new JLabel(new ImageIcon("BKN.png"));
    	headPic2.setPreferredSize(new Dimension(60, 60));
    	head1.setBackground(Style.DEEP_BLUE);
    	head1.add(headPic1);
    	JPanel buffer1=new JPanel();
    	buffer1.setBackground(Style.DEEP_BLUE);
    	head1.add(buffer1);
    	head1.add(headTxt);
    	JPanel buffer2=new JPanel();
    	buffer2.setBackground(Style.DEEP_BLUE);
    	head1.add(buffer2);
    	head1.add(headPic2);
    	
    	head2=new JPanel();
    	head2.setLayout(new FlowLayout());
    	jie1=new JButton("第1节");
    	jie1.setSize(20,20);
    	jie1.setFont(new Font("黑体",Font.PLAIN,20));
    	jie1.setForeground(Color.white);
    	jie1.setBackground(Color.darkGray);
    	jie1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	jie1.setBorderPainted(false);
    	jie1.setFocusPainted(false);
    	jie1.addActionListener(this);
    	jie1.addMouseListener(this);
    	jie2=new JButton("第2节");
    	jie2.setSize(20,20);
    	jie2.setFont(new Font("黑体",Font.PLAIN,20));
    	jie2.setForeground(Color.white);
    	jie2.setBackground(Style.BACK_GREY);
    	jie2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	jie2.setBorderPainted(false);
    	jie2.setFocusPainted(false);
    	jie2.addActionListener(this);
    	jie2.addMouseListener(this);
    	jie3=new JButton("第3节");
    	jie3.setSize(20,20);
    	jie3.setFont(new Font("黑体",Font.PLAIN,20));
    	jie3.setForeground(Color.white);
    	jie3.setBackground(Style.BACK_GREY);
    	jie3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	jie3.setBorderPainted(false);
    	jie3.setFocusPainted(false);
    	jie3.addActionListener(this);
    	jie3.addMouseListener(this);
    	jie4=new JButton("第4节");
    	jie4.setSize(20,20);
    	jie4.setFont(new Font("黑体",Font.PLAIN,20));
    	jie4.setForeground(Color.white);
    	jie4.setBackground(Style.BACK_GREY);
    	jie4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	jie4.setBorderPainted(false);
    	jie4.setFocusPainted(false);
    	jie4.addActionListener(this);
    	jie4.addMouseListener(this);
    	
    	head2.setBackground(Style.BACK_GREY);
    	head2.add(jie1);
    	head2.add(jie2);
    	head2.add(jie3);
    	head2.add(jie4);
    	
    	head.add(head1);
    	head.add(head2,BorderLayout.SOUTH);
    	
    	content=new JPanel();
    	mtm=new MediaTableModel();
    	jt=new JTable(mtm);
     //	jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	jt.getColumnModel().getColumn(0).setPreferredWidth(140);
    	jt.getColumnModel().getColumn(1).setPreferredWidth(180);
    	jt.getColumnModel().getColumn(2).setPreferredWidth(220);
    	jt.getColumnModel().getColumn(3).setPreferredWidth(441);
    	 //JTable边线颜色透明
        jt.setGridColor(new Color(255, 255, 255));
        //获得表头
        JTableHeader tableH = jt.getTableHeader();
        //设置表头的背景色
        tableH.setBackground(Color.lightGray);
        //设置表头的文字颜色
        tableH.setForeground(new Color(0, 0, 0));
        jt.setShowHorizontalLines(false);
        jt.setShowVerticalLines(false);
        DefaultTableCellRenderer render1 = new DefaultTableCellRenderer(){
        	 public Component getTableCellRendererComponent(JTable table,  
        	            Object value, boolean isSelected, boolean hasFocus,  
        	            int row, int column) {  
        	     if(row%2 == 0)  
        	     setBackground(Color.white);  
        	     else  
        	     setBackground(new Color(230, 230, 230));  
        	     return super.getTableCellRendererComponent(table, value,  
        	               isSelected, hasFocus, row, column);  
        	 }
        };
        render1.setHorizontalAlignment(SwingConstants.CENTER);
        jt.getColumnModel().getColumn(0).setCellRenderer(render1);
        jt.getColumnModel().getColumn(1).setCellRenderer(render1);
        jt.getColumnModel().getColumn(2).setCellRenderer(new TableImageCell(((MediaTableModel)jt.getModel()).getNameList()));
        DefaultTableCellRenderer render2 = new DefaultTableCellRenderer(){
       	 public Component getTableCellRendererComponent(JTable table,  
       	            Object value, boolean isSelected, boolean hasFocus,  
       	            int row, int column) {  
       	     if(row%2 == 0)  
       	     setBackground(Color.white);  
       	     else  
       	     setBackground(new Color(230, 230, 230));  
       	     return super.getTableCellRendererComponent(table, value,  
       	               isSelected, hasFocus, row, column);  
       	 }
       };
        render2.setHorizontalAlignment(SwingConstants.LEFT);
        jt.getColumnModel().getColumn(3).setCellRenderer(render2);
        
        jt.setFont(new Font("黑体", Font.PLAIN, 15));
        jt.setRowHeight(50);//设置行高
        ImageIcon tou=new ImageIcon("Acie Law.png");
		tou.setImage(tou.getImage().getScaledInstance(50, 40, Image.SCALE_DEFAULT));
		jt.setValueAt(tou, 1, 2);
    	jsp=new JScrollPane(jt);
    	
    	bottom=new JPanel();
    	bottom.setBackground(Color.darkGray);
    	t=new Timer(1000,this);//每隔一秒触发ActionEvent事件
		t.start();//启动计时器
		timeNow=new JLabel(Calendar.getInstance().getTime().toLocaleString());
		timeNow.setForeground(Color.white);
		bottom.add(timeNow);
    	
    	setLayout(new BorderLayout());
    	add(head,BorderLayout.NORTH);
    	add(jsp);
    	add(bottom,BorderLayout.SOUTH);
    	
    }
    
    public static void main (String[] args) {
		JFrame jFrame=new JFrame();
		ImageIcon img1=new ImageIcon("ATL.png");
		ImageIcon img2=new ImageIcon("BKN.png");
		MyPanel mPanel=new MyPanel(img1,img2,"ATL","BKN");
		jFrame.getContentPane().add(mPanel);
		jFrame.setLocation(150, 50);
		jFrame.setSize(1000,600);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jie1){
			 isJie1=true;
			 isJie2=false;
			 isJie3=false;
			 isJie4=false;
			 jie1.setBackground(Color.darkGray);
			 jie2.setBackground(Style.BACK_GREY);
			 jie3.setBackground(Style.BACK_GREY);
			 jie4.setBackground(Style.BACK_GREY);
		}
		else if(e.getSource()==jie2){
			 isJie1=false;
			 isJie2=true;
			 isJie3=false;
			 isJie4=false;
			 jie1.setBackground(Style.BACK_GREY);
			 jie2.setBackground(Color.darkGray);
			 jie3.setBackground(Style.BACK_GREY);
			 jie4.setBackground(Style.BACK_GREY);
		}
		else if(e.getSource()==jie3){
			 isJie1=false;
			 isJie2=false;
			 isJie3=true;
			 isJie4=false;
			 jie1.setBackground(Style.BACK_GREY);
			 jie2.setBackground(Style.BACK_GREY);
			 jie3.setBackground(Color.darkGray);
			 jie4.setBackground(Style.BACK_GREY);
		}
		else if(e.getSource()==jie4){
			 isJie1=false;
			 isJie2=false;
			 isJie3=false;
			 isJie4=true;
			 jie1.setBackground(Style.BACK_GREY);
			 jie2.setBackground(Style.BACK_GREY);
			 jie3.setBackground(Style.BACK_GREY);
			 jie4.setBackground(Color.darkGray);
		}
		
		this.timeNow.setText(Calendar.getInstance().getTime().toLocaleString());
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==jie1){
			jie1.setBackground(Color.darkGray);
		}
		else if(e.getSource()==jie2){
			jie2.setBackground(Color.darkGray);
		}
		else if(e.getSource()==jie3){
			jie3.setBackground(Color.darkGray);
		}
		else if(e.getSource()==jie4){
			jie4.setBackground(Color.darkGray);
		}
	
		
	}

	public void mouseExited(MouseEvent e) {
		if(e.getSource()==jie1&&isJie1==false){
			jie1.setBackground(Style.BACK_GREY);
		}
		else if(e.getSource()==jie2&&isJie2==false){
			jie2.setBackground(Style.BACK_GREY);
		}
		else if(e.getSource()==jie3&&isJie3==false){
			jie3.setBackground(Style.BACK_GREY);
		}
		if(e.getSource()==jie4&&isJie4==false){
			jie4.setBackground(Style.BACK_GREY);
		}		
	}
	
	class PicPanel extends JPanel{
	   private Image image;
	 
	  public PicPanel(String path){
		  
		 
	     image=new ImageIcon(path).getImage();
	     int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	     int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	     this.setSize(width,height);
	   // this.setPreferredSize(new Dimension(image.getWidth(this),image.getHeight(this)));
	  }
	 
	 public void paint(Graphics g){
		 super.paintComponent(g);
		 Dimension size=this.getParent().getSize();
		 g.drawImage(image,0,0,size.width,size.height,null);
	//  g.drawImage(image,0,0,image.getWidth(this),image.getHeight(this),0,0,image.getWidth(this),image.getHeight(this),this);
	 }
	} 
	
	class TableImageCell extends DefaultTableCellRenderer{
		ArrayList<String> names;
		public TableImageCell(ArrayList<String> names){
			super();
			this.names=names;
		}
	    public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
	        setIcon(null); 
	        setBorder(null); 
	        if(value instanceof ImageIcon){
	            setIcon((Icon) value);
	            if(isSelected) setBorder(new LineBorder(Color.red));
	        }else
	            if(value instanceof String) setText((String) value);
	            else setText("");
	       
    	     if(row%2 == 0)  
    	     setBackground(Color.white);  
    	     else  
    	     setBackground(new Color(230, 230, 230)); 
    	     
    	     setHorizontalAlignment(SwingConstants.LEFT);
     	     return super.getTableCellRendererComponent(table, (Object)names.get(row),  
    	               isSelected, hasFocus, row, column);  
	    }
	}
	
	
}
