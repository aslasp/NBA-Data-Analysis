package newui.matchui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import bl.LiveTxt;
import blService.LiveTxtBLService;
import newui.Style;
import vo.LiveMatchDetailVO;

public class LiveTextPanel extends JPanel implements ActionListener,MouseListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable jt;
    MediaTableModel mtm;
    JPanel head,head2,bottom;
    JLabel timeNow;
    ArrayList<JButton> jies=new ArrayList<JButton>();
    ArrayList<Boolean> isJies=new ArrayList<Boolean>();
    JScrollPane jsp;
    Timer t;
    
    ArrayList<ArrayList<LiveMatchDetailVO>> matches=new ArrayList<ArrayList<LiveMatchDetailVO>>();
    int partNum;
    boolean isBegin=false;
	ArrayList<LiveMatchDetailVO> bus=new ArrayList<LiveMatchDetailVO>();
	String season;
	String date;
	String teams;
	LiveTxtBLService livetext=new LiveTxt();
    
    public LiveTextPanel(String team1,String team2,String season,String date){
    	partNum=1;
    	for(int i=0;i<jies.size();i++){
    		if(i==1)
    			isJies.add(true);
    		else 
				isJies.add(false);
    	}
    	head=new JPanel();
    	head.setLayout(new BorderLayout());
    	

    	
    	head2=new JPanel();
    	head2.setLayout(new FlowLayout());
    	
    	initButton(1);    	
    	
    	head2.setBackground(Style.BACK_GREY);
    	for(int i=1;i<jies.size();i++)
    	  head2.add(jies.get(i));
    	     	
    	head.add(head2);
    	LiveMatchDetailVO vo=new LiveMatchDetailVO("null", "null", "null", 0, 0, "0,00:00,0,本场比赛尚未开始，请耐心等待");
    	bus.add(vo);
	    mtm=new MediaTableModel(bus);
    	jt=new JTable(mtm);
     //	jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	jt.getColumnModel().getColumn(0).setPreferredWidth(100);
    	jt.getColumnModel().getColumn(1).setPreferredWidth(140);
    	jt.getColumnModel().getColumn(2).setPreferredWidth(260);
    	jt.getColumnModel().getColumn(3).setPreferredWidth(481);
    	 //JTable边线颜色透明
//        jt.setGridColor(new Color(255, 255, 255));
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
        jt.getColumnModel().getColumn(2).setCellRenderer(new TableImageCell(mtm.getNameList()));
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
        
        jt.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        jt.setRowHeight(50);//设置行高
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
    
 public void initLiveData(String season,String date,String teams){
	 this.season=season;
	 this.date=date;
	 this.teams=teams;
 }
 
 public void refresh(ArrayList<String> data,int line){	   
	    if(isBegin==false){
	    	matches.add(new ArrayList<LiveMatchDetailVO>());
	 	    matches.add(new ArrayList<LiveMatchDetailVO>());
	    	mtm.clearAll();
	    	mtm.add(data);
	    	jt.revalidate();
	    	for(int i=data.size()-1;i>=0;i--){
	    	   String s=data.get(i);
	    	   matches.get(1).add(0,new LiveMatchDetailVO("null", "null", "null", partNum, partNum, s));
	    	}
	    	isBegin=true;
	    }
	    else{
	    	if(line!=partNum){
	    		partNum++;
	    		initButton(line);
	    		head2Refresh();
	    		matches.add(new ArrayList<LiveMatchDetailVO>());
	    		mtm.clearAll();
	    	}
	    	
	    	for(int i=data.size()-1;i>=0;i--){
	    		String s=data.get(i);
		    	matches.get(partNum).add(0,new LiveMatchDetailVO("null", "null", "null", partNum, partNum, s));
		    	livetext.addToSql(s);
	    	}
		    mtm.add(data);
	        jt.revalidate();
	        jt.repaint();
	    }
 }
 
 private void initButton(int line){
	jies.clear();
	isJies.clear();
	
	jies.add(new JButton());
 	isJies.add(false);
 	JButton jie;
 	for(int i=1;i<=partNum;i++){
 		if(i<=4)
 	       jie=new JButton("第"+i+"节");
 		else {
		   jie=new JButton("加时"+(i-4));
		}
 	  jie.setSize(20,20);
 	  jie.setFont(new Font("黑体",Font.PLAIN,20));
 	  jie.setForeground(Color.white);
 	  if(i==line){
 	      jie.setBackground(Color.darkGray);
 	      isJies.add(true);
 	  }
 	  else{
 		  jie.setBackground(Style.BACK_GREY);
 		  isJies.add(false);
 	  }
 	  jie.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
 	  jie.setBorderPainted(false);
 	  jie.setFocusPainted(false);
 	  jie.addActionListener(this);
 	  jie.addMouseListener(this);
 	  jies.add(jie);
 	}
 }
 
 public void head2Refresh(){
		
 	head2.removeAll();
	
 	for(int i=1;i<jies.size();i++)
 	  head2.add(jies.get(i));
 	     	
    head2.validate();
 }
    
    public static void main (String[] args) {
    	long start = System.currentTimeMillis();
		JFrame jFrame=new JFrame();		
		LiveTextPanel mPanel=new LiveTextPanel("CLE","GSW","00-00","00-00");
		jFrame.getContentPane().add(mPanel);
		jFrame.setLocation(150, 50);
		jFrame.setSize(1000,600);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		long end = System.currentTimeMillis();
		System.out.println("运行时间总：" + (end - start) + "毫秒");// 应该是end - start
	}

	public void actionPerformed(ActionEvent e) {
		boolean isJie=false;
		
		for(int i=1;i<jies.size();i++){
			if(e.getSource()==jies.get(i)){
				isJie=true;
				break;
			}
		}
		for(int i=1;i<jies.size();i++){
			if(e.getSource()==jies.get(i)){
				for(int j=1;j<isJies.size();j++){
					if(i==j)
						isJies.set(j, true);
					else 
						isJies.set(j, false);
				}
				jies.get(i).setBackground(Color.darkGray); 
				if(isBegin==true){
					mtm.refresh(matches.get(i));
					jt.revalidate();
					jt.repaint();
				}
				
			}
			else if(isJie==true){
				jies.get(i).setBackground(Style.BACK_GREY);
			}
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
		for(int i=1;i<jies.size();i++){
			if(e.getSource()==jies.get(i)&&isJies.get(i)==false){
				jies.get(i).setBackground(Style.BACK_GREY);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		
		for(int i=1;i<jies.size();i++){
			if(e.getSource()==jies.get(i)){
				jies.get(i).setBackground(Color.darkGray);
			}
		}
		
	}

	public void mouseExited(MouseEvent e) {
		
		for(int i=1;i<jies.size();i++){
			if(e.getSource()==jies.get(i)&&isJies.get(i)==false){
				jies.get(i).setBackground(Style.BACK_GREY);
			}
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
    	     String tt=(String)names.get(row);
    	     tt.contains(".");
    	     return super.getTableCellRendererComponent(table, (String)names.get(row),  
   	               isSelected, hasFocus, row, column);  
//    	     return this;
//    	     return super.getTableCellRendererComponent(table, "Alis",  
//  	               isSelected, hasFocus, row, column); 
	    }
	}
	
	
}

