package newui.matchui;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import newui.WebSec;


public class LiveWebInc {
	/**
	 * ԭ��
	 * 1.ͨ��json���ڻ�ȡ���ձ����gameid  ��if status!=3������ѽ��� �����״̬Ϊ������
	 * 2.ͨ��gameid����json�ļ� ��ȡ���ڱ���ֱ��
	 * 3.δ��ʼ����ȡ�������߳���ȡ���ѽ���ֱ�ӻ�ȡ
	 * */
	//ƥ���ַ�ʾ��
	String rexz="[\\u4E00-\\u9FA5]";
	String liveRex="(\\[([\\u4E00-\\u9FA5]|[\\w \\.-])+( [\\d]+-[\\d]+)?\\])?([\\w:�� \\(\\)����\\.-]|[\\u4E00-\\u9FA5])*\"(,\"displayPlayerId\":\"[\\d]+\")?,\"gameClock\":\"[\\d]{2}:[\\d]{2}(.\\d)?";
	String url="http://china.nba.com/wap/static/data/scores/gamedaystatus_2015-05-16.json";
	String lurl="http://china.nba.com/wap/static/data/game/playbyplay_0041400315_1.json";
	String gameRex="[\\d]{10}\",\"status\":\"[\\d]";
	String rex="\\[[\\w]{2}( [\\d]+-[\\d]+)?\\]";//ȡ����
	//0041400206","status":"3
	String teamRex="teamId\":\"([\\d]{10}|0)";
	String gameDate="05%2F12%2F2015";
	String infoRex="[\\d]{10}\",[\\d],\"[\\w -:]+\",\"[\\d]{8}/[A-Z]{6}\"(,[\\d]{10}){2}";
	//id",status,Final","20150512/CHICLE
	//CLEGSW",1610612744,1610612739
	String hometeam="GSW",visteam="CLE",homeid="1610612744",visid="1610612739";
	String season="14-15";
	static ScoresWebInc scoreweb;
	public static void main(String[] args) {
		 scoreweb=new ScoresWebInc();
		File file=new File("14-15_MatchLive");
		if(!file.exists())
			file.mkdir();
		
		LiveWebInc lv=new LiveWebInc();
		lv.setDate();
	}
	
	//��Ҫ�������ڱ任
	public void setDate(){
		String[] month={"10","11","12","01","02","03","04","05"/*,"06"*/};
		//season="2010-11";
		//26 16
		for(int i=0;i<month.length;i++){
			int m=Integer.parseInt(month[i]);
			int daysiz=30;
			if(m>=8){
				if(m%2==0){daysiz=31;}
			}else{
				if(m==2){
					daysiz=28;
				}else
					if(m%2!=0) daysiz=31;
			}
			for(int j=1;j<=daysiz;j++){
				/*if(m==10&&j<26){
					gameType="Preseason";
					recordType="Preseason";
				}else if((m==4&&j>=16)||m==5||m==6){
					gameType="Playoffs";
					recordType="Playoff";
				}else{
					gameType="Regular+Season";
					recordType="Team";
				}*/
				String day=j+"";
				if(day.length()==1)
					day="0"+day;
				String year="2014";
				if(m<10)
					year="2015";
					
				 getLive(month[i]+"%2F"+day+"%2F"+year);
			}
		}
		
	}
	//һ������ һ�����ڵĴ���
	//game one by one
	public void getLive(String date){
		System.out.println(date);
		// id ,status ,teamname  only process when status.equals 3
		ArrayList<ArrayList<String>> IdAndStatus=getGameStatus(date);
		for(ArrayList<String> line:IdAndStatus){
			if(line.get(1).equals("3")){
				ArrayList<String> live=new ArrayList<String>();
				//4�ڳ��� ���10�ڼ�ʱ
				String s=line.get(2).split("/")[1];
				visteam=s.substring(0, 3);
				hometeam=s.substring(3, 6);
				homeid=line.get(3);visid=line.get(4);
			for(int i=1;i<=14;i++){
					ArrayList<String> scorelive=getGameLive(line.get(0),i
							);
					System.out.println(line.get(0));
					if(scorelive.size()!=0){
						live.add(i+"");
						live.addAll(scorelive);
					}else 
						break;
				}
				writeToFile(line,live);
			}
		}
	}
	
	
	//����gameId,gamestatus
	public ArrayList<ArrayList<String>>  getGameStatus(String date){
	//	String url1="http://china.nba.com/wap/static/data/scores/gamedaystatus_";
	//	String realurl=url1+date+".json";
		String scoreUrl="http://stats.nba.com/stats/scoreboardV2?DayOffset=0&LeagueID=00&gameDate=";
		String realurl=scoreUrl+date;
		String dir=WebSec.getURLContent(realurl, "gbk");
		ArrayList<String> res=WebSec.getMatcherSubstrs(dir, infoRex);
		//final game only processss
		
		ArrayList<ArrayList<String>> id_status=new ArrayList<ArrayList<String>>();
		for(String s:res){
			ArrayList<String> temp=new ArrayList<String>();
			String[] ss=s.replace("\"", "").split(",");
			temp.add(ss[0]);
			temp.add(ss[1]);//123  3������
			temp.add(ss[3]);//�Ͷ�����
			temp.add(ss[4]);//home team id
			temp.add(ss[5]);//vis team id
			id_status.add(temp);
		}
		return id_status;
		
	}
	
	
	

	
	/*
	 * 	
	 * 
	 * */
	public ArrayList<String>  getGameLive(String gameID,int line
			){
		ArrayList<String> result=new ArrayList<String>();
		
		String url1="http://china.nba.com/wap/static/data/game/playbyplay_";
		String realrul=url1+gameID+"_"+line+".json";
		String dir=WebSec.getURLContent(realrul, "utf-8");
		ArrayList<String> res=WebSec.getMatcherSubstrs(dir,liveRex);
		ArrayList<String> idlist=WebSec.getMatcherSubstrs(dir, teamRex);
		for(int i=0;i<res.size();i++){
			String record="";
			String s=res.get(i);
			String id=idlist.get(i).replace("teamId\":\"", "");
		//	System.out.println(s);
			s=s.replace("\"displayPlayerId\":", "")
					.replace("\"gameClock\":", "")
					.replace("\"", "");
		//	System.out.println(s);
			if(!id.equals("0")){
				if(id.equals(homeid))
					record+=hometeam;
				else record+=visteam;	
			}else record+="0";
			record+=",";
			String[] ss=s.split(",");
			if(ss.length==2){//��ǰ��
				record+=ss[1]+",";
				record+="0"+",";
			}else{
			record+=ss[2]+",";//time
			record+=ss[1]+",";}//id û����Ϊ0
			record+=ss[0];//live text
			//System.out.println(record);
			result.add(record);
			
		}
		
		return result;
	}
	
	
	public void writeToFile(ArrayList<String> gameInfo,ArrayList<String> live){
		String s=gameInfo.get(2).split("/")[0];
		s=changeDate(s);
		try {
		File file=new File("14-15_MatchLive/"+season+"_"+s+"_"+visteam+"-"+hometeam+".txt");
		if(!file.exists())
			
				file.createNewFile();
			
		FileOutputStream out=new FileOutputStream(file);
		StringBuilder sb=new StringBuilder();
		for(String ss:live){
			sb.append(ss+"\r\n");
		}
		out.write(sb.toString().getBytes("utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//20150527   05-28
	public String changeDate(String s){
		//�ж����� 15������
		/*
		 * 
		 * */
		String mon=s.substring(4,6);
		String day=s.substring(6, 8);
		ArrayList<String> t=scoreweb.DayChange(mon, day);
		
		
		return t.get(0)+"-"+t.get(1);
	}
 	
	
	String eurl1="http://stats.nba.com/stats/playbyplayv2?EndPeriod=10&EndRange=55800&"
			+ "GameID=";
		String eurl2="&RangeType=2&Season=2014-15&SeasonType=Playoffs&StartPeriod=1&StartRange=0";
//[\d]{1,2}(,"[\d]+:[\d]+( PM)?"){2}(,("[-#"\w \(\)':\.]+"|null)){4}
	//ʾ��:�ȷֽ���,ʵ��ʱ�䣬ʣ����ӣ�����ֱ�����ݣ�*���Ͷ�ֱ�����ݣ��ȷ�/null
	String eInforex="[\\d]{1,2}(,\"[\\d]+:[\\d]+( PM)?\"){2}(,(\"[-#\"\\w \\(\\)':\\.]+\"|null)){4}";
//(,([\d]|null),([\d]+|null),("[\w -\.]+"|null),([\d]{10}|null)(,("[\w -\.]+"|null)){2},("[A-Z]{3}"|null)){3}
	//�Ƿ���һֱ��ȡ�����
	//PERSON1TYPE","PLAYER1_ID","PLAYER1_NAME","PLAYER1_TEAM_ID","PLAYER1_TEAM_CITY","PLAYER1_TEAM_NICKNAME","PLAYER1_TEAM_ABBREVIATION"
	//"PERIOD","WCTIMESTRING","PCTIMESTRING","HOMEDESCRIPTION","NEUTRALDESCRIPTION","VISITORDESCRIPTION","SCORE"
	public ArrayList<String> getELiveInfo(String gameID){
		ArrayList<String> result=new ArrayList<String>();
		String realurl=eurl1+gameID+eurl2;
		String dir=WebSec.getURLContent(realurl, "gbk");
		ArrayList<String> res=WebSec.getMatcherSubstrs(dir,eInforex);
		if(res.size()!=0){
			//��ʱ������ݷ�������
			result=res;
		}
		return result;
	}
	//ͬʱ���м�������
/*	public ArrayList<String> getEGameLiveING(String date,ArrayList<String> line){
		//ArrayList<ArrayList<String>> IdAndStatus=getGameStatus(date);
		ArrayList<String> res=new ArrayList<String>();
	
				//û����
		 res=getELiveInfo(line.get(0));
			
				
				
			
		
		return res;
	}*/
}
