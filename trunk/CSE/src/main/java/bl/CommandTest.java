package bl;

import java.io.PrintStream;
import java.util.ArrayList;

public class CommandTest {
	
	private String[] player_sort={"point","rebound","assist","blockShot","steal","foul","fault","minute","efficient","shot",
			"three","penalty","doubleTwo","realShot","GmSc","shotEfficient","reboundEfficient","offendReboundEfficient","defendReboundEfficient"
			,"assistEfficent","stealEfficient","blockShotEfficient","faultEfficient","frequency"};
	private String[] player_real_sort={"score","reboundNum","assistNum","blockNum","stealNum","foulNum","turnOverNum","presentTime","efficiency",
			"shootHitRate","threeHitRate","freeThrowHitRate","doubleDoubleNum","trueHitRate","GmScEfficiencyValue","shootEfficiency","reboundRate",
			"offenReboundRate","defenReboundRate","assistRate","stealRate","blockRate","turnOverRate","usageRate"};

	private String[] team_sort={"point","rebound","assist","blockShot","steal","foul","fault","shot",
			"three","penalty","defendRebound","offendRebound","offendRound","offendEfficient","defendEfficient","offendReboundEfficient","defendReboundEfficient","stealEfficeint","assistEfficient"};
	private String[] team_sort_real={"score","reboundNum","assistNum","blockNum","stealNum","foulNum","turnOverNum","shootHitRate","threeHitRate","freeThrowHitRate",
			"defenReboundNum","offenReboundNum","offenRound","offenEfficiency","defenEfficiency","offenReboundEfficiency","defenReboundEfficiency",
			"stealEfficiency","assistRate"};
	
	private boolean isPTotal=false;
	private boolean isTTotal=false;
	private String playerhotField="";
	private String teamhotField="";
	private String playerkingField="";
	private boolean isSeason=false;
	private int teamNum=30;
	private int playerNum=50;
	private boolean isPHigh=false;//是否返回高阶数据
	private boolean isTHigh=false;
	private String pAge="All";
	private String pUnion="All";
	private String pPosition="All";
	private ArrayList<String> playerSort=new ArrayList<String>();//
	private ArrayList<String> sortP=new ArrayList<String>();
	private ArrayList<String> playerFilter=new ArrayList<String>();//优先级 field.value
	private ArrayList<String> teamSort=new ArrayList<String>();//aas.dsec ddd.asc 邮件及
	private ArrayList<String> sortT=new ArrayList<String>();//可以不声明
	
	//调用更新
	
	
	
	
	
	public void excute(PrintStream out,String[] args){
		ArrayList<String> command=new ArrayList<String>();
		for(int i=1;i<args.length;i++)
			command.add(args[i]);
		if(args[0].equals("-player")){
			//球队命令解析
			if(command.size()==0)
				return;//返回得分前50的球员的场均比赛数据 调用
			int i=0;
			if((i=command.indexOf("-n"))>=0)
				playerNum=Integer.parseInt(command.get(i+1));
			else
				playerNum=50;
				
			if((i=command.indexOf("-hot"))>=0){
				playerhotField=command.get(i+1);
				PlayerHotFieldChange();
						//调用 player hot 方法
			}
			else if((i=command.indexOf("-king"))>=0){
				playerkingField=command.get(i+1);
				if(command.get(i+2).equals("-season"))
					isSeason=true;	
				else
					isSeason=false;
				PlayerKingFieldChange();
				//调用返回数据王信息
			}else{
				//contain all
				playerSort.clear();
				sortP.clear();
				if((i=command.indexOf("-sort"))>=0){
					
					String[] t=command.get(i+1).split(",");
					for(String s:t){
						String[] p=s.split(".");
					playerSort.add(p[0]);
					sortP.add(p[1]);
					}
				}else{
					playerSort.add("score");playerSort.add("trueHitRate");
					sortP.add("desc");sortP.add("desc");
				}
				if(command.indexOf("-high")>=0){
					isPHigh=true;
					//返回高阶数据的 sort方法  其他返回均未基本数据
				}
				else
					isPHigh=false;
				
				
				if(command.indexOf("-total")>=0)
				//返回的是总和数据
					isPTotal=true;
				else
					isPTotal=false;
				PlayerSortFieldChange();
				if((i=command.indexOf("-filter"))>0){
					
					playerFilter.clear();
					String[] t=command.get(i+1).split(",");
					for(String s:t)
						playerFilter.add(s);
					PlayerFilterChange();
					//调用sort+filter方法
					
					clearFilter();
				}else{
					//调用sort方法
				}
			
		
			}
			
		}else if(args[0].equals("-team")){
			//球员命令解析
			if(command.size()==0)
				return;//返回得分前30的球队的场均比赛数据
			int i=0;
			if((i=command.indexOf("-n"))>=0)
				teamNum=Integer.parseInt(command.get(i+1));
			else
				teamNum=50;
				
			if((i=command.indexOf("-hot"))>=0){
				teamhotField=command.get(i+1);
				TeamHotFieldChange();
						//调用 team hot 方法
			}else{
				//contain all
				teamSort.clear();
				sortT.clear();
				if((i=command.indexOf("-sort"))>=0){

					String[] t=command.get(i+1).split(",");
					for(String s:t){
						String[] p=s.split(".");
						teamSort.add(p[0]);
						sortT.add(p[1]);
					}
						
				}else{
					teamSort.add("score");teamSort.add("winRate");
					sortT.add("desc");sortT.add("desc");
				}
				if(command.indexOf("-high")>=0){
					isTHigh=true;
					//返回高阶数据的 sort方法  其他返回均未基本数据
				}
				else
					isTHigh=false;
				
				
				if(command.indexOf("-total")>=0)
				//返回的是总和数据
					isTTotal=true;
				else
					isTTotal=false;
				TeamSortFieldChange();
					//调用  sort方法
			
		
			}
		}else{
			//set
		}
		
		
	}
	
	public void PlayerHotFieldChange(){
		if(!playerhotField.equals("score"))
			playerhotField+="Num";	
	}
	
	public void TeamHotFieldChange(){
		for(int i=1;i<12;i++)
			if(teamhotField.equals(team_sort[i])){
				teamhotField=team_sort_real[i];
				break;
			}
	}
	public void PlayerKingFieldChange(){
		if(!playerkingField.equals("score"))
			playerkingField+="Num";	
	}
	
	public void PlayerSortFieldChange(){
		for(int i=0;i<playerSort.size();i++){
			String s=playerSort.get(i);
			for(int j=0;j<player_sort.length;j++)
				if(s.equals(player_sort[j])){
					playerSort.set(i,player_real_sort[j]);
					break;
				}
		}
	}
	
	
	public void TeamSortFieldChange(){
		for(int i=0;i<teamSort.size();i++){
			String s=teamSort.get(i);
			for(int j=0;j<team_sort.length;j++)
				if(s.equals(team_sort[j])){
					teamSort.set(i,team_sort_real[j]);
					break;
				}
		}
	}
	
	public void PlayerFilterChange(){
		for(String s:playerFilter){
			if(s.contains("position"))
				pPosition=s.split(".")[1];
			else if(s.contains("league"))
				pUnion=s.split(".")[1];
			else 
				pAge=s.split(".")[1];
		}
		
	}
	
	public void clearFilter(){
		pAge=pPosition=pUnion="All";
	}


}
