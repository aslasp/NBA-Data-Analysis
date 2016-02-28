package newui;

//用于给各个表格界面刷新 包括 player team match hot  是否需要
public class UITableThread extends Thread{
	private int model; //0 player,1 team 2 match,3 hot 决定使用什么方法刷新
	private boolean stop;
	//目前只有match 要刷新
	public void UITableThread(int m){
		this.model=m;
		this.stop=false;
	}
	
	
	public void run(){
		//根据model调用刷新方法
		while(!stop){
			
		}
	}
	
	public void startThread(){
		this.start();		
	}
	public void stopThead(){
		this.stop=true;
	}

}
