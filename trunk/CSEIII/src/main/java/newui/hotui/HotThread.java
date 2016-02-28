package newui.hotui;

public class HotThread extends Thread{
	boolean stop=false;
	public String sort;
	public HotFatherPanel p;
	public HotThread(HotFatherPanel p,String s){
		sort=s;
		this.p=p;
		
	}
	
	public void run(){
		while(!stop){
			
		//	System.out.println(p.toString()+":"+sort);
			p.Refresh(sort);
			try {
				this.sleep(5000);
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
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
