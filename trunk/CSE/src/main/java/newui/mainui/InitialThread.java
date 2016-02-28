package newui.mainui;

import blservice.MatchBLService;
import blservice.PlayerBLService;
import blservice.TeamBLService;
import newui.Service;

//界面服务初始化Thread
public class InitialThread extends Thread{
	boolean stop=false;
	AnimeFrame frame;
	
	public InitialThread(AnimeFrame frame){
		this.frame=frame;
	}
	public void run(){
		//初始化数据
		double pre=System.currentTimeMillis();
		PlayerBLService p=Service.player;
		TeamBLService t=Service.team;
		MatchBLService m=Service.match;
		p.getPlayerAverageInfo();
		p.getPlayerSeasonInfo("13-14");
		MainFrame.getInstance();
		frame.setStop();
		double post=System.currentTimeMillis();
		System.out.println("initialThread:"+(post-pre));
	
	}
	
	public void startThread(){
		this.start();		
	}
	public void stopThead(){
		this.stop=true;
	}
	

}
