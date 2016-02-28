package newui.mainui;

public class IndexThread extends Thread{
	public boolean stop=false;
	public IndexPanel p;
	public IndexThread(IndexPanel p){
		this.p=p;
	}
	
	public void run(){
		while(!stop){
			
			p.Refresh();
			try {
				this.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void startThread(){
		this.start();		
	}
	public void stopThead(){
		this.stop=true;
	}
}
