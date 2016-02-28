package vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerVO {
	private String name;// 球员名称
	private String number;// 球衣号码
	private String position;// 位置
	private String height;// 身高（英尺-英存）
	private int weight;// 体重（英镑）
	private String birth;// （月 日，年）
	private int age;// 年龄
	private int exp;// 球龄
	private String school;// 毕业学校

	private String owingTeam;// 所属球队
	private String league;//所属联盟
	private String mostRecentMatch;//最近一场比赛的赛季_日期
	private int playedGames;// 参赛场数
	private int gameStartingNum;// 先发场数
	private double reboundNum;// 篮板数
	private double assistNum;// 助攻数
	private double presentTime;// 在场时间
	private double shootHitNum;//投篮命中数
	private double shootAttemptNum;//投篮出手数
	private double shootHitRate;// 投篮命中率
	private double threeHitNum;//三分命中数
	private double threeAttemptNum;//三分出手数
	private double threeHitRate;// 三分命中率
	private double freeThrowHitNum;//罚球命中数
	private double freeThrowAttemptNum;//罚球出手数
	private double freeThrowHitRate;// 罚球命中率
	private double offenReboundNum;// 进攻数
	private double defenReboundNum;// 防守数
	private double stealNum;// 抢断数
	private double blockNum;// 盖帽数
	private double turnOverNum;// 失误数
	private double foulNum;// 犯规数
	private double score;// 得分
	private double efficiency;// 效率
	private double recentFiveMatchesScoreUpRate;// 近五场得分提升率
	private double recentFiveMatchesReboundUpRate;// 近五场篮板提升率
	private double recentFiveMatchesAssistUpRate;//近五场助攻提升率
	private double GmScEfficiencyValue;// GmSc效率值
	private double trueHitRate;// 真实命中率
	private double shootEfficiency;// 投篮效率
	private double reboundRate;// 篮板率
	private double offenReboundRate;// 进攻篮板率
	private double defenReboundRate;// 防守篮板率
	private double assistRate;// 助攻率
	private double stealRate;// 抢断率
	private double blockRate;// 盖帽率
	private double turnOverRate;// 失误率
	private double usageRate;// 使用率
	private double score_rebound_assist;// 得分/篮板/助攻
	private double doubleDoubleNum;// 两双
	private ArrayList<Integer> matchesID=new ArrayList<Integer>();//所参加的比赛ID
	private Map<Integer,Boolean> isVisitingTeam=new HashMap<Integer,Boolean>(64);//记录球员在上面的比赛ID中所属队伍是否是客队。如果是，是true;不是，是false;
    private LittleRecordVO[] fiveRecentRecords=new LittleRecordVO[5];//最近五场比赛记录
//    private boolean isCalculate;
	

	
	public PlayerVO(){
		this(null, null, null, null, 0, null, 0, 0, null);
	}
	
	public PlayerVO( String name, String number, String position,
			String height, int weight, String birth, int age, int exp,
			String school) {
		this.name = name;
		this.number = number;
		this.position = position;
		this.height = height;
		this.weight = weight;
		this.birth = birth;
		this.age = age;
		this.exp = exp;
		this.school = school;
		this.owingTeam = "";
		this.league="";
		this.playedGames = 0;
		this.gameStartingNum = 0;
		this.reboundNum = 0;
		this.assistNum = 0;
		this.presentTime = 0;
		this.shootHitNum=0;
		this.shootAttemptNum=0;
		this.shootHitRate = 0;
		this.threeHitNum=0;
		this.threeAttemptNum=0;
		this.threeHitRate = 0;
		this.freeThrowHitNum=0;
		this.freeThrowAttemptNum=0;
		this.freeThrowHitRate =0;
		this.offenReboundNum =0;
		this.defenReboundNum = 0;
		this.stealNum = 0;
		this.blockNum = 0;
		this.turnOverNum = 0;
		this.foulNum = 0;
		this.score = 0;
		this.efficiency = 0;
		this.recentFiveMatchesReboundUpRate = 0;
		this.recentFiveMatchesScoreUpRate = 0;
		this.recentFiveMatchesAssistUpRate=0;
		GmScEfficiencyValue = 0;
		this.trueHitRate = 0;
		this.shootEfficiency = 0;
		this.reboundRate = 0;
		this.offenReboundRate = 0;
		this.defenReboundRate = 0;
		this.assistRate = 0;
		this.stealRate = 0;
		this.blockRate = 0;
		this.turnOverRate = 0;
		this.usageRate = 0;
		this.score_rebound_assist = 0;
		this.doubleDoubleNum = 0;
		
	}
	
	public PlayerVO(String name, String owingTeam,String league, int playedGames,
			int gameStartingNum, double reboundNum, double assistNum,
			double presentTime, double shootHitNum,double shootAttemptNum,double shootHitRate,
			double threeHitNum,double threeAttemptNum,double threeHitRate,
			double freeThrowHitNum,double freeThrowAttemptNum,
			double freeThrowHitRate, double offenReboundNum, double defenReboundNum,
			double stealNum, double blockNum, double turnOverNum,
			double foulNum, double score, double efficiency,
			double recentFiveMatchesScoreUpRate,
			double recentFiveMatchesReboundUpRate,double recentFiveMatchesAssistUpRate, double gmScEfficiencyValue,
			double trueHitRate, double shootEfficiency, double reboundRate,
			double offenReboundRate, double defenReboundRate,
			double assistRate, double stealRate, double blockRate,
			double turnOverRate, double usageRate, double score_rebound_assist,
			double doubleDoubleNum) {
		this.name=name;
		this.owingTeam = owingTeam;
		this.league=league;
		this.playedGames = playedGames;
		this.gameStartingNum = gameStartingNum;
		this.reboundNum = reboundNum;
		this.assistNum = assistNum;
		this.presentTime = presentTime;
		this.shootHitNum=shootHitNum;
		this.shootAttemptNum=shootAttemptNum;
		this.shootHitRate = shootHitRate;
		this.threeHitNum=threeHitNum;
		this.threeAttemptNum=threeAttemptNum;
		this.threeHitRate = threeHitRate;
		this.freeThrowHitNum=freeThrowHitNum;
		this.freeThrowAttemptNum=freeThrowAttemptNum;
		this.freeThrowHitRate = freeThrowHitRate;
		this.offenReboundNum = offenReboundNum;
		this.defenReboundNum = defenReboundNum;
		this.stealNum = stealNum;
		this.blockNum = blockNum;
		this.turnOverNum = turnOverNum;
		this.foulNum = foulNum;
		this.score = score;
		this.efficiency = efficiency;
		this.recentFiveMatchesReboundUpRate = recentFiveMatchesReboundUpRate;
		this.recentFiveMatchesScoreUpRate = recentFiveMatchesScoreUpRate;
		this.recentFiveMatchesAssistUpRate=recentFiveMatchesAssistUpRate;
		GmScEfficiencyValue = gmScEfficiencyValue;
		this.trueHitRate = trueHitRate;
		this.shootEfficiency = shootEfficiency;
		this.reboundRate = reboundRate;
		this.offenReboundRate = offenReboundRate;
		this.defenReboundRate = defenReboundRate;
		this.assistRate = assistRate;
		this.stealRate = stealRate;
		this.blockRate = blockRate;
		this.turnOverRate = turnOverRate;
		this.usageRate = usageRate;
		this.score_rebound_assist = score_rebound_assist;
		this.doubleDoubleNum = doubleDoubleNum;
	}

	public PlayerVO(String name, String number, String position,
			String height, int weight, String birth, int age, int exp,
			String school, String owingTeam, String league,int playedGames,
			int gameStartingNum, double reboundNum, double assistNum,
			double presentTime, double shootHitNum,double shootAttemptNum,double shootHitRate,
			double threeHitNum,double threeAttemptNum,double threeHitRate,
			double freeThrowHitNum,double freeThrowAttemptNum,
			double freeThrowHitRate, double offenReboundNum, double defenReboundNum,
			double stealNum, double blockNum, double turnOverNum,
			double foulNum, double score, double efficiency,
			double recentFiveMatchesScoreUpRate,
			double recentFiveMatchesReboundUpRate,double recentFiveMatchesAssistUpRate, double gmScEfficiencyValue,
			double trueHitRate, double shootEfficiency, double reboundRate,
			double offenReboundRate, double defenReboundRate,
			double assistRate, double stealRate, double blockRate,
			double turnOverRate, double usageRate, double score_rebound_assist,
			double doubleDoubleNum) {
		super();
		this.name = name;
		this.number = number;
		this.position = position;
		this.height = height;
		this.weight = weight;
		this.birth = birth;
		this.age = age;
		this.exp = exp;
		this.school = school;
		this.owingTeam = owingTeam;
		this.league=league;
		this.playedGames = playedGames;
		this.gameStartingNum = gameStartingNum;
		this.reboundNum = reboundNum;
		this.assistNum = assistNum;
		this.presentTime = presentTime;
		this.shootHitNum=shootHitNum;
		this.shootAttemptNum=shootAttemptNum;
		this.shootHitRate = shootHitRate;
		this.threeHitNum=threeHitNum;
		this.threeAttemptNum=threeAttemptNum;
		this.threeHitRate = threeHitRate;
		this.freeThrowHitNum=freeThrowHitNum;
		this.freeThrowAttemptNum=freeThrowAttemptNum;
		this.freeThrowHitRate = freeThrowHitRate;
		this.offenReboundNum = offenReboundNum;
		this.defenReboundNum = defenReboundNum;
		this.stealNum = stealNum;
		this.blockNum = blockNum;
		this.turnOverNum = turnOverNum;
		this.foulNum = foulNum;
		this.score = score;
		this.efficiency = efficiency;
		this.recentFiveMatchesReboundUpRate = recentFiveMatchesReboundUpRate;
		this.recentFiveMatchesScoreUpRate = recentFiveMatchesScoreUpRate;
		this.recentFiveMatchesAssistUpRate=recentFiveMatchesAssistUpRate;
		GmScEfficiencyValue = gmScEfficiencyValue;
		this.trueHitRate = trueHitRate;
		this.shootEfficiency = shootEfficiency;
		this.reboundRate = reboundRate;
		this.offenReboundRate = offenReboundRate;
		this.defenReboundRate = defenReboundRate;
		this.assistRate = assistRate;
		this.stealRate = stealRate;
		this.blockRate = blockRate;
		this.turnOverRate = turnOverRate;
		this.usageRate = usageRate;
		this.score_rebound_assist = score_rebound_assist;
		this.doubleDoubleNum = doubleDoubleNum;
	}


	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public String getPosition() {
		return position;
	}

	public String getHeight() {
		return height;
	}

	public int getWeight() {
		return weight;
	}

	public String getBirth() {
		return birth;
	}

	public int getAge() {
		return age;
	}

	public int getExp() {
		return exp;
	}

	public String getSchool() {
		return school;
	}

	public String getOwingTeam() {
		return owingTeam;
	}
	
	public String getLeague(){
		return league;
	}
	
	public String getMostRecentMatch(){
		return mostRecentMatch;
	}

	public int getPlayedGames() {
		return playedGames;
	}

	public int getGameStartingNum() {
		return gameStartingNum;
	}

	public double getReboundNum() {
		return reboundNum;
	}

	public double getAssistNum() {
		return assistNum;
	}

	public double getPresentTime() {
		return presentTime;
	}

	public double getShootHitNum(){
		return shootHitNum;
	}
	
	public double getShootAttemptNum(){
		return shootAttemptNum;
	}
	
	public double getShootHitRate() {
		return shootHitRate;
	}
	
	public double getThreeHitNum(){
		return threeHitNum;
	}
	
	public double getThreeAttemptNum(){
		return threeAttemptNum;
	}

	public double getThreeHitRate() {
		return threeHitRate;
	}
	
	public double getFreeThrowHitNum(){
		return freeThrowHitNum;
	}
	
	public double getFreeThrowAttemptNum(){
		return freeThrowAttemptNum;
	}

	public double getFreeThrowHitRate() {
		return freeThrowHitRate;
	}

	public double getOffenReboundNum() {
		return offenReboundNum;
	}

	public double getDefenReboundNum() {
		return defenReboundNum;
	}

	public double getStealNum() {
		return stealNum;
	}

	public double getBlockNum() {
		return blockNum;
	}

	public double getTurnOverNum() {
		return turnOverNum;
	}

	public double getFoulNum() {
		return foulNum;
	}

	public double getScore() {
		return score;
	}

	public double getEfficiency() {
		return efficiency;
	}

	public double getRecentFiveMatchesReboundUpRate() {
		return recentFiveMatchesReboundUpRate;
	}

	public double getRecentFiveMatchesScoreUpRate() {
		return recentFiveMatchesScoreUpRate;
	}
	
	public double getRecentFiveMatchesAssistUpRate() {
		return recentFiveMatchesAssistUpRate;
	}

	public double getGmScEfficiencyValue() {
		return GmScEfficiencyValue;
	}

	public double getTrueHitRate() {
		return trueHitRate;
	}

	public double getShootHitEfficiency() {
		return shootEfficiency;
	}

	public double getReboundRate() {
		return reboundRate;
	}

	public double getOffenReboundRate() {
		return offenReboundRate;
	}

	public double getDefenReboundRate() {
		return defenReboundRate;
	}

	public double getAssistRate() {
		return assistRate;
	}

	public double getStealRate() {
		return stealRate;
	}

	public double getBlockRate() {
		return blockRate;
	}

	public double getTurnOverRate() {
		return turnOverRate;
	}

	public double getUsageRate() {
		return usageRate;
	}

	public double getDoubleDoubleNum() {
		return doubleDoubleNum;
	}

	public double getScore_rebound_assist() {
		return score_rebound_assist;
	}
	
	public ArrayList<Integer> getMatchesID(){
		return matchesID;
	}
	
	public Map<Integer,Boolean> getIsVisitingTeam(){
		return isVisitingTeam;
	}
	
	public LittleRecordVO[] getFiveRecentRecords(){
		return fiveRecentRecords;
	}
	
//	public boolean getIsCalculate(){
//		return isCalculate;
//	}
	
	
	
	
	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setOwingTeam(String owingTeam) {
		this.owingTeam = owingTeam;
	}
	
	public void setLeague(String league){
		this.league=league;
	}
	
	public void setMostRecentMatch(String mostRecentMatch){
		this.mostRecentMatch=mostRecentMatch;
	}

	public void setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
	}
	
	public void addPlayedGames() {
		this.playedGames++;
	}
	

	public void setGameStartingNum(int gameStartingNum) {
		this.gameStartingNum = gameStartingNum;
	}
	
	public void addGameStartingNum() {
		this.gameStartingNum++;
	}

	public void setReboundNum(double reboundNum) {
		this.reboundNum = reboundNum;
	}
	
	public void addReboundNum(int reboundNum) {
		this.reboundNum += reboundNum;
	}

	public void setAssistNum(double assistNum) {
		this.assistNum = assistNum;
	}
	
	public void addAssistNum(int assistNum) {
		this.assistNum += assistNum;
	}

	public void setPresentTime(double presentTime) {
		this.presentTime = presentTime;
	}
	
	public void addPresentTime(int presentTime) {
		this.presentTime += presentTime;
	}

	public void setShootHitNum(double shootHitNum){
		this.shootHitNum=shootHitNum;
	}
	
	public void addShootHitNum(int shootHitNum){
		this.shootHitNum+=shootHitNum;
	}
	
	public void setShootAttemptNum(double shootAttemptNum){
		this.shootAttemptNum=shootAttemptNum;
	}
	
	public void addShootAttemptNum(int shootAttemptNum){
		this.shootAttemptNum+=shootAttemptNum;
	}
	
	public void setShootHitRate(double shootHitRate) {
		this.shootHitRate = shootHitRate;
	}
	
	public void addShootHitRate(int shootHitRate) {
		this.shootHitRate += shootHitRate;
	}
	
	public void setThreeHitNum(double threeHitNum){
		this.threeHitNum=threeHitNum;
	}
	
	public void addThreeHitNum(int threeHitNum){
		this.threeHitNum+=threeHitNum;
	}
	
	public void setThreeAttemptNum(double threeAttemptNum){
		this.threeAttemptNum=threeAttemptNum;
	}
	
	public void addThreeAttemptNum(int threeAttemptNum){
		this.threeAttemptNum+=threeAttemptNum;
	}

	public void setThreeHitRate(double threeHitRate) {
		this.threeHitRate = threeHitRate;
	}

	public void setFreeThrowHitNum(double freeThrowHitNum){
		this.freeThrowHitNum=freeThrowHitNum;
	}
	
	public void addFreeThrowHitNum(int freeThrowHitNum){
		this.freeThrowHitNum+=freeThrowHitNum;
	}
	
	public void setFreeThrowAttemptNum(double freeThrowAttemptNum){
		this.freeThrowAttemptNum=freeThrowAttemptNum;
	}
	
	public void addFreeThrowAttemptNum(int freeThrowAttemptNum){
		this.freeThrowAttemptNum+=freeThrowAttemptNum;
	}
	
	public void setFreeThrowHitRate(double freeThrowHitRate) {
		this.freeThrowHitRate = freeThrowHitRate;
	}

	public void setOffenReboundNum(double offenReboundNum) {
		this.offenReboundNum = offenReboundNum;
	}
	
	public void addOffenReboundNum(int offenReboundNum) {
		this.offenReboundNum += offenReboundNum;
	}

	public void setDefenReboundNum(double defenReboundNum) {
		this.defenReboundNum = defenReboundNum;
	}
	
	public void addDefenReboundNum(int defenReboundNum) {
		this.defenReboundNum += defenReboundNum;
	}

	public void setStealNum(double stealNum) {
		this.stealNum = stealNum;
	}
	
	public void addStealNum(int stealNum) {
		this.stealNum += stealNum;
	}

	public void setBlockNum(double blockNum) {
		this.blockNum = blockNum;
	}
	
	public void addBlockNum(int blockNum) {
		this.blockNum += blockNum;
	}

	public void setTurnOverNum(double turnOverNum) {
		this.turnOverNum = turnOverNum;
	}
	
	public void addTurnOverNum(int turnOverNum) {
		this.turnOverNum += turnOverNum;
	}

	public void setFoulNum(double foulNum) {
		this.foulNum = foulNum;
	}
	
	public void addFoulNum(int foulNum) {
		this.foulNum += foulNum;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}
	
	public void addEfficiency(int efficiency){
		this.efficiency+=efficiency;
	}

	public void setRecentFiveMatchesScoreUpRate(
			double recentFiveMatchesScoreUpRate) {
		this.recentFiveMatchesScoreUpRate = recentFiveMatchesScoreUpRate;
	}

	public void setRecentFiveMatchesReboundUpRate(
			double recentFiveMatchesReboundUpRate) {
		this.recentFiveMatchesReboundUpRate = recentFiveMatchesReboundUpRate;
	}

	public void setRecentFiveMatchesAssistUpRate(
			double recentFiveMatchesAssistUpRate) {
		this.recentFiveMatchesAssistUpRate = recentFiveMatchesAssistUpRate;
	}

	public void setGmScEfficiencyValue(double gmScEfficiencyValue) {
		this.GmScEfficiencyValue = gmScEfficiencyValue;
	}
	
	public void addGmScEfficiencyValue(double gmscEfficiencyValue){
		this.GmScEfficiencyValue+=gmscEfficiencyValue;
	}

	public void setTrueHitRate(double trueHitRate) {
		this.trueHitRate = trueHitRate;
	}

	public void setShootEfficiency(double shootEfficiency) {
		this.shootEfficiency = shootEfficiency;
	}

	public void setReboundRate(double reboundRate) {
		this.reboundRate = reboundRate;
	}

	public void setOffenReboundRate(double offenReboundRate) {
		this.offenReboundRate = offenReboundRate;
	}

	public void setDefenReboundRate(double defenReboundRate) {
		this.defenReboundRate = defenReboundRate;
	}

	public void setAssistRate(double assistRate) {
		this.assistRate = assistRate;
	}

	public void setStealRate(double stealRate) {
		this.stealRate = stealRate;
	}

	public void setBlockRate(double blockRate) {
		this.blockRate = blockRate;
	}

	public void setTurnOverRate(double turnOverRate) {
		this.turnOverRate = turnOverRate;
	}

	public void setUsageRate(double usageRate) {
		this.usageRate = usageRate;
	}

	public void setScore_rebound_assist(double score_rebound_assist) {
		this.score_rebound_assist = score_rebound_assist;
	}

	public void setDoubleDoubleNum(double doubleDoubleNum) {
		this.doubleDoubleNum = doubleDoubleNum;
	}
	
//	public void setIsCalculate(boolean isCalculate){
//		this.isCalculate=isCalculate;
//	}
	
	public void addDoubleDoubleNum(){
		this.doubleDoubleNum++;
	}

	public void addMatchesID(int i,boolean isVisitingTeam){
		this.matchesID.add(i);
		this.isVisitingTeam.put(i,isVisitingTeam);
	}
	
	public int fiveRecentRecordsSort(){
		boolean change = true;
		int length=0;
		for(int i=0;i<5;i++){
			if(fiveRecentRecords[i]!=null)
				length++;
		}
		
		for (int i=length-1; i>0 && change == true; i--)
		{
			change = false;
			for (int j=0; j<i; j++)
				if (compare(fiveRecentRecords[j], fiveRecentRecords[j+1])>0 )
				{
					LittleRecordVO temp = fiveRecentRecords[j];
					fiveRecentRecords[j] = fiveRecentRecords[j+1];
					fiveRecentRecords[j+1] = temp;
					change = true;
				}
		}
		
		return length;
	}
	
	public int compare(LittleRecordVO v1,LittleRecordVO v2){
		if(v1.getSeason().compareTo(v2.getSeason())<0)
			return -1;
		else if(v1.getSeason().compareTo(v2.getSeason())>0)
			return 1;
		else{
			String month1=v1.getDate().split("-")[0];
			String month2=v2.getDate().split("-")[0];
			int type1=0;
			int type2=0; //type=0代表在6月前，=1代表在6月后
			if(month1.compareTo("06")>0){
				type1=1;
			}
			if(month2.compareTo("06")>0){
				type2=1;
			}
			
			if(type1==0&&type2==1){
				return 1;
			}
			
			if(type1==1&&type2==0){
				return-1;
			}
			
			//下面是2个都是6月前或6月后的情况
		     	if(v1.getDate().compareTo(v2.getDate())<0)
				   return -1;
			    else if(v1.getDate().compareTo(v2.getDate())>0)
				   return 1;
			    else 
				   return 0;
		     	
		}
	}
	
	
	public boolean isMoreRecent(String season,String date){
		int length=fiveRecentRecordsSort();
		if(length<5)
			return true;
		else{
			LittleRecordVO vo=fiveRecentRecords[0];
			LittleRecordVO v1=new LittleRecordVO(season, date, 0, 0, 0);
			if(compare(vo,v1)==-1)
				return true;
			else
				return false;
		}
	}
	
	public void addFiveRecentRecords(LittleRecordVO vo){
		int length=fiveRecentRecordsSort();
		if(length<5){
			fiveRecentRecords[length]=vo;
		}
		else{
			if(isMoreRecent(vo.getSeason(), vo.getDate())==true){
				fiveRecentRecords[0]=vo;
				return;
			}
			else{
				return;
			}
		}
	}
}
