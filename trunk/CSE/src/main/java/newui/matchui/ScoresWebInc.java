package newui.matchui;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import newui.WebSec;

/*
 * author:jincui
 * des:get the scores data 
 * */
public class ScoresWebInc {
	/*step1: get the date ,get the set of game_id
	 *step2: visit the site by game_id
	 *step3: get the data
	 * */
	
	/*date website has one datafile
	 * scoreboard
	 *boxscore website has three datafile
	 *1.boxscoreSummary
	 *2.boxscoreTraditional
	 *3.CommonPlayerSeries  maybe useless
	 *separate them 
	 * */
	ArrayList<String> game_id_list;
	String fileName="";//�����ļ���  ͬһ���һ���ļ���  һ������һ��txt
	/**
	 * �ļ�������ʾ��13-14
	 * �ļ�����ʾ����_����_����  e.g13-14_01-01_CHA-LAC
	 * �ļ����ݸ�ʽ���ֺž�ΪӢ��
	 * ע��һ��ʮ�µ׵����һ���ܶ�������ʼ ��ǰ��ֻ��10�·�
	 * 
	 * ����;�������;�ȷ�;��������(Playoff Preseason Team);
	 * ��һ�ڱȷ�;�ڶ��ڱȷ�;����ڱȷ�;��n�ڱȷ�;
	 * ����1��д(mainly visit team)Ϊɶ����û�ֺ���
	 * ��Ա��;λ��;COMMENT��;MIN;FGM;FGA;FG_PCT;FG3M;FG3A;FG3_PCT;FTM;FTA;FT_PCT;
	 * OREB;DREB;REB;AST;STL;BLK;TO;PF;PTS;PLUS_MINUS
	 * ���ķ���
	 * ��Ա��;λ��;��;�ڳ�ʱ��;Ͷ��������;Ͷ��������;Ͷ��������;���������;��ֳ�����;���������;����������;���������;
	 * ����������;��������;����������;��������;����;������;��ñ��;ʧ����;������;�÷�;Ч��?;
	 * */
	String scoreUrl="http://stats.nba.com/stats/scoreboardV2?DayOffset=0&LeagueID=00&gameDate=";
	String gameDate="05%2F12%2F2015";
	
	String boxSumUrl="http://stats.nba.com/stats/boxscoresummaryv2?GameID=";
	String gameID="0041400215";
	//GameID��ǰn-1λΪSeriesID
	String seriesUrl="http://stats.nba.com/stats/commonplayoffseries?LeagueID=00&Season=2014-15&SeriesID=004140021";
	static String season="2014-15";
	String seriesID="004140021";
	
	String boxUrl="http://stats.nba.com/stats/boxscoretraditionalv2?EndPeriod=10&EndRange=28800&GameID=0041400215&RangeType=2&Season=2014-15&SeasonType=Playoffs&StartPeriod=1&StartRange=0";
	static String gameType="Playoffs";//Preseason   Regular+Season
	static String recordType="Playoff";
	//��ӦPlayoff Preseason Team
	String gameIDPattern="\"[\\d]{10}\""; //�����scoreboard  �����ظ�
	String linescorePattern="[A-Z]{3}\",(\"[\\w \\.]*\",){2}\"[\\d]*-[\\d]*\"(,(null|[\\d]*)){15}"; //�����Summary
	//e.gCLE","Cleveland","Cavaliers","3-2",25,29,26,26,0,0,0,0,0,0,0,0,0,0,106
	String typeIDPattern=gameIDPattern; //�������ظ�
	//�����traditional�ļ�  
	//��ȡ������Ա���
	
	String playerPattern="[A-Z]{3}\",\"[\\w. ]*\",[\\d]*,\"[\\w. ]*\",\"[A-Z]?\",\"\",((\"[\\d]*:[\\d]*\")|[\\d]*)(,-?[\\d]*(\\.[\\d]{3})?){19}";
	boolean isChange=false;
	String total="[A-Z]{3}\",\"[\\w .]*\",\"[\\d]*:[\\d]*\"(,-?[\\d]*(\\.[\\d]*)?){19}";
	public ScoresWebInc(){
		game_id_list=new ArrayList<String>();
	}
	public static void main(String[] args) {
		ScoresWebInc scoreSec=new ScoresWebInc();
		//�����ж�  �ܱ�4 14-15
		//10��30�� ��ʼ������  4��20�˿�ʼ������ 2012��Ϊ����
		//ע��15�� 5��6����δ��ȥ
		String[] month={"10","11","12","01","02","03","04","05","06"};
		season="2010-11";
		//26 16
		for(int i=3;i<month.length;i++){
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
				if(m==10&&j<26){
					gameType="Preseason";
					recordType="Preseason";
				}else if((m==4&&j>=16)||m==5||m==6){
					gameType="Playoffs";
					recordType="Playoff";
				}else{
					gameType="Regular+Season";
					recordType="Team";
				}
				String day=j+"";
				if(day.length()==1)
					day="0"+day;
				String year="2010";
				if(m<10)
					year="2011";
					
				 scoreSec.setDate(day,month[i],year);
			}
			
			
		
		}
		
	}
	
	public  void setDate(String day,String month,String year){
		//����12-13��������ʾ��
		//��ǰ-����-����  10��-10~4-4~6  �����ޱ���  Preseaon Team���·��ж�  Playoff��CommonSeries�ļ��ж� �б�Ҫ�� û�а�  
		//�������·��ж�
		File file=new File("10-11");
		if(!file.exists()){
			file.mkdirs();
		}
		//05%2F12%2F2015
		getGameID(month+"%2F"+day+"%2F"+year);
		int n=game_id_list.size();
		for(String s:game_id_list){
			System.out.println(s);
			ArrayList<String> sumList=getScoreSummary(s);
			if(sumList.size()!=0){
			String[] vist=sumList.get(0).replace("\"", "").split(",");
			String[] homt=sumList.get(1).replace("\"", "").split(",");
			String vsteam=vist[0]+"-"+homt[0];
			String vsscore=vist[18]+"-"+homt[18];
			//���ж�
			String type=recordType;
			ArrayList<String> descore=new ArrayList<String>();
			for(int i=4;i<18;i++){
				if(!(vist[i].equals("0")||vist[i].equals("null"))){
					String scor=vist[i]+"-"+homt[i];
					descore.add(scor);
				}
			}
			
			//ArrayList<ArrayList<String>> box=getBoxScoreData(gameType,season, s);
			ArrayList<String> vistPInfo=new ArrayList<String>();
			ArrayList<String> homePInfo=new ArrayList<String>();
		
			ArrayList<String> pInfo=getBoxScoreData(gameType,season, s);
			
		//	ArrayList<String> tInfo=box.get(1);
			//�����Ա���
		
			
			for(int j=0;j<pInfo.size();j++){
				String pp=pInfo.get(j);
				int index=pp.indexOf(",");
				index=pp.indexOf(",",index+1);
				//if(pp.contains(":")){
					index=pp.indexOf(",",index+1);
				//}
				pp=pp.substring(index+2);
				pp=pp.replace("\"","");
				//if(pp.contains(":"))
				pp=pp.replace(",,", ",");
				if(pInfo.get(j).contains(homt[0]))
					homePInfo.add(pp);
				else
					vistPInfo.add(pp);
			}
			//��������
			/*
			for(int i=0;i<tInfo.size();i++){
				String t=tInfo.get(i);
				if(t.contains(vist[0])){
					int index=
				}
			}*/
			
		
			ArrayList<String> dd=DayChange(month,day);
			String date=dd.get(0)+"-"+dd.get(1);
			String season="10-11";
			writeToFile(season,date,vsteam,vsscore,type,descore,vistPInfo,homePInfo);
			}
		}
	}
	
	public ArrayList<String> DayChange(String month,String day){
		ArrayList<String> date=new ArrayList<String>();
		int m=Integer.parseInt(month);
		int d=Integer.parseInt(day);
		if(m>=8){
			if(m%2==0){
				if(d==31){
					//change month and day
					day="01";
					month=MonthUpChange(month);
				}else{
					//add day
					day=DayUpChange(day);
				}
			}else{
				if(d==30){
					day="01";
					month=MonthUpChange(month);
				}else{
					day=DayUpChange(day);
				}
			}
				
		}else{
			if(m==2){
				if(d==28){
					//2
					day="01";
					month=MonthUpChange(month);
				}else{
					day=DayUpChange(day);
				}
			}else
				if(m%2!=0){
					if(d==31){
						day="01";
						month=MonthUpChange(month);
					}else{
						day=DayUpChange(day);
					}
					
				}else{
					if(d==30){
						day="01";
						month=MonthUpChange(month);
					}
					else{day=DayUpChange(day);}
				}
		}
		date.add(month);date.add(day);
		return date;
	}
	
	
	public String MonthUpChange(String tt){
		if(tt.equals("12"))
			return "01";
		else{
			String lt=(Integer.parseInt(tt)+1)+"";
			if(lt.length()==1)
				return "0"+lt;
			return lt;
		}
		
	}
	
	public String DayUpChange(String d){
		String ld=(Integer.parseInt(d)+1)+"";
		if(ld.length()==1)
			ld="0"+ld;
		return ld;
	}
	
	
	public String getType(String day,String month,String year){
		String type="";
		
		
		
		
		return type;
		
	}
	
	
	public void writeToFile(String season,String date,String vteam,String vscore,String type,
			ArrayList<String> detail,ArrayList<String> vInfo,ArrayList<String> hInfo){
		
		String s=date+","+vteam+","+vscore+","+type+",";
		String score="";
		for(String ss:detail)
			score=score+ss+",";
		String[] team=vteam.split("-");//�Ͷ�-����
		StringBuffer sb=new StringBuffer();
		sb.append(s+"\r\n");
		sb.append(score+"\r\n");
		sb.append(team[0]+"\r\n");
		for(String str:vInfo){
			sb.append(str+"\r\n");
		}
		sb.append(team[1]+"\r\n");
		for(String str:hInfo){
			sb.append(str+"\r\n");
		}
		try {
			String tip="";
			if(vInfo.size()==0&&hInfo.size()==0){
				tip="_la";
			}else if(vInfo.size()==0){
				tip="_lv";
			}else if(hInfo.size()==0){
				tip="_lh";
			}
			File file=new File(season+"/"+season+"_"+date+"_"+vteam+tip+".txt");
			if(!file.exists())
				try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			FileOutputStream out=new FileOutputStream(file);
			out.write(sb.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	public void getGameID(String date){
		game_id_list.clear();
		String surl=scoreUrl+date;
		String dir=WebSec.getURLContent(surl, "gbk");
		
		ArrayList<String> slist=WebSec.getMatcherSubstrs(dir, gameIDPattern);
		if(slist.size()!=0){
			String ss=slist.get(0);
			game_id_list.add(ss.replace("\"", ""));
			for(int i=1;i<slist.size();i++){
				String s=slist.get(i);
				if(!s.equals(ss)){
					game_id_list.add(s.replace("\"",""));
				}else
					break;
			}
		}
		

	}
	//��ʱ�洢��txt�� �����ṩֱ�ӷ��ض���ķ���
	public  ArrayList<String>  getBoxScoreData(String type,String season,String gameID){
		//String boxUrl="http://stats.nba.com/stats/boxscoretraditionalv2?EndPeriod=10&EndRange=28800&"
		//		+ "GameID=0041400215&RangeType=2&Season="
		//		+ "2014-15&SeasonType="
		//		+ "Playoffs&StartPeriod=1&StartRange=0";
		String boxUrl1="http://stats.nba.com/stats/boxscoretraditionalv2?EndPeriod=10&EndRange=28800&GameID=";
		String boxUrl2="&RangeType=2&Season=";
		String boxUrl3="&SeasonType=";
		String boxUrl4="&StartPeriod=1&StartRange=0";
		String url=boxUrl1+gameID+boxUrl2+season+boxUrl3+type+boxUrl4;
		String dir=WebSec.getURLContent(url, "gbk");
		ArrayList<String> ply=WebSec.getMatcherSubstrs(dir,playerPattern);
	//	ArrayList<String> tol=WebSec.getMatcherSubstrs(dir, total);
		//for(String s:ply)
		//	System.out.println(s);
	//	for(String s:tol)
		//	System.out.println(s);
	//	ArrayList<ArrayList<String>> pt=new ArrayList<ArrayList<String>>();
	//	pt.add(ply);pt.add(tol);
		return ply;
		
	}
	
	public ArrayList<String> getScoreSummary(String GameID){
		String sumurl=boxSumUrl+GameID;
		
		String dir=WebSec.getURLContent(sumurl,"gbk");
		ArrayList<String> list=WebSec.getMatcherSubstrs(dir,linescorePattern);
		if(list.size()!=0){
		String homevist=",[\\d]{4,10}";//������ ���Ͷ�
		String hvname=",[\\d]{4,10},\"[A-Z]{3}\"";
		ArrayList<String> teamid=WebSec.getMatcherSubstrs(dir,homevist);
		ArrayList<String> IdAndName=WebSec.getMatcherSubstrs(dir, hvname);
		
		String s=IdAndName.get(1);
		if(IdAndName.get(0).contains(teamid.get(0))){
			s=IdAndName.get(0);
		}
		
		String regex="[A-Z]{3}";
		Pattern pt=Pattern.compile(regex);
		Matcher m=pt.matcher(s);
		m.find();
		String homeTeam=m.group();
		//�Ͷ���ǰ
		if(list.get(0).contains(homeTeam)){
			String temp=list.get(0);
			list.set(0, list.get(1));
			list.set(1, temp);
		}
		
		}
		return list;
	}
	
	
	
	

}
