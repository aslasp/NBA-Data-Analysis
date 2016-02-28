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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import newui.Style;
import bl.LiveTxt;
import blService.LiveTxtBLService;
import vo.LiveMatchDetailVO;

public class HistoryLiveTextPanel extends JPanel implements ActionListener,MouseListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable jt;
    MediaTableModel mtm;
    JPanel head,head2,content;
    ArrayList<JButton> jies=new ArrayList<JButton>();
    ArrayList<Boolean> isJies=new ArrayList<Boolean>();
    JScrollPane jsp;
    
    LiveTxtBLService liveTexts;
    ArrayList<ArrayList<LiveMatchDetailVO>> matches=new ArrayList<ArrayList<LiveMatchDetailVO>>();
    int partNum;
    
    public HistoryLiveTextPanel(String team1,String team2,String season,String date){
    	getMatches(season, date, team1+"-"+team2);
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
    	  jie.setFont(new Font("微软雅黑",Font.PLAIN,20));
    	  jie.setForeground(Color.white);
    	  if(i==1){
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
    	
    	head2.setBackground(Style.BACK_GREY);
    	for(int i=1;i<jies.size();i++)
    	  head2.add(jies.get(i));
    	     	
    	head.add(head2);
    	content=new JPanel();
	    mtm=new MediaTableModel(matches.get(1));
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

    	setLayout(new BorderLayout());
    	add(head,BorderLayout.NORTH);
    	add(jsp);
    }
    
 private  void getMatches(String season,String date,String teams){
	    matches.clear();
    	liveTexts=new LiveTxt();
    	ArrayList<LiveMatchDetailVO> vos=liveTexts.getLiveTxt(season, date, teams);
    	for(int i=0;i<vos.get(i).getPartNum()+1;i++)
    	     matches.add(new ArrayList<LiveMatchDetailVO>());
    	for(LiveMatchDetailVO vo:vos){
    		int num=vo.getPart();
    		matches.get(num).add(vo);
    	}
    	partNum=matches.size()-1;
 }
    
    public static void main (String[] args) {
    	long start = System.currentTimeMillis();
		JFrame jFrame=new JFrame();		
		HistoryLiveTextPanel mPanel=new HistoryLiveTextPanel("MIA","MIL","14-15","03-25");
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
				mtm.refresh(matches.get(i));
				jt.revalidate();
			}
			else if(isJie==true){
				jies.get(i).setBackground(Style.BACK_GREY);
			}
		}
		
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
