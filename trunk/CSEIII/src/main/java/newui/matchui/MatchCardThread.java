package newui.matchui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import newui.UIhelper;
import vo.MatchVO;

public class MatchCardThread extends Thread{
	private ArrayList<MatchVO> matchlist;
	//private ArrayList<MatchCard> cardlist;
	private JPanel p;
	JScrollPane pp;
	ArrayList<ArrayList<String>> line;
	public MatchCardThread(ArrayList<MatchVO> matchlist,/*ArrayList<MatchCard> cardlist
			,*/JPanel p,JScrollPane pp){
		this.matchlist=matchlist;
	//	this.cardlist=cardlist;
		this.p=p;
		this.pp=pp;
	}
	public MatchCardThread(ArrayList<MatchVO> matchlist,/*ArrayList<MatchCard> cardlist
			,*/JPanel p,JScrollPane pp,ArrayList<ArrayList<String> >id){
		this.matchlist=matchlist;
	//	this.cardlist=cardlist;
		this.p=p;
		this.pp=pp;
		this.line=id;
	}
	
	
	public void run(){
		int n=matchlist.size();
		
		int row =n / 2;
		if (n% 2 != 0)
			row += 1;
		if(row==1)
			row+=1;
		GridLayout layout=new GridLayout(row,2);
		p.setLayout(layout);
		//p.setLayout(new FlowLayout());
		int heightOfBIGPNL = 170 * row;// 这里一定要设置为180-200之间的值，代表每个卡片的高度，乘以卡片数量之后是整个BIGPNL的高度
		int screenWidth = UIhelper.getScreenWidth();

		int width = screenWidth * 85/ 100;
	
	
		p.setPreferredSize(new Dimension(width, heightOfBIGPNL));
		for (int i=0;i<matchlist.size();i++) {
			MatchVO m=matchlist.get(i);
			MatchCard card1 = new MatchCard(m);
		
				
				if(line!=null)
					card1.setGameID(line.get(i));
				card1.setLive(true);
			p.add(card1);
			pp.revalidate();
			
			
		}
		if(n==2||n==1){
			for(int i=0;i<4-n;i++){
				JPanel t=new JPanel();
				t.setBackground(Color.WHITE);
				p.add(t);
				pp.revalidate();
			}
		}

	}

}
