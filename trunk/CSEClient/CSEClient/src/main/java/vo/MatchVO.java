package vo;

import java.util.ArrayList;

public class MatchVO {
	private String season;// 赛季
	private String date;// 时间
	private String visitingTeam;// 对阵队伍
	private String homeTeam;
	private ArrayList<String> detailScores;// 各节比分
	private ArrayList<RecordVO> records;// 球员比分数据记录
	private int matchTime;//比赛总时间,以秒为单位
	
	private int visitingShootHitNum = 0; // 投篮命中数
	private int visitingShootAttemptNum = 0; // 投篮出手次数
	private int visitingThreeHitNum = 0; // 三分命中数
	private int visitingThreeAttemptNum = 0; // 三分出手数
	private int visitingFreeThrowHitNum = 0; // 罚球命中数
	private int visitingFreeThrowAttemptNum = 0; // 罚球出手数
	private int visitingOffenReboundNum = 0; // 进攻篮板数
	private int visitingDefenReboundNum = 0; // 防守篮板数
	private int visitingAssistNum = 0;// 助攻数
	private int visitingStealNum = 0;// 抢断数
	private int visitingBlockNum = 0;// 盖帽数
	private int visitingTurnOverNum = 0;// 失误数
	private int visitingFoulNum = 0;// 犯规数
	private int visitingScore = 0;// 得分

	private int homeShootHitNum = 0; // 投篮命中数
	private int homeShootAttemptNum = 0; // 投篮出手次数
	private int homeThreeHitNum = 0; // 三分命中数
	private int homeThreeAttemptNum = 0; // 三分出手数
	private int homeFreeThrowHitNum = 0; // 罚球命中数
	private int homeFreeThrowAttemptNum = 0; // 罚球出手数
	private int homeOffenReboundNum = 0; // 进攻篮板数
	private int homeDefenReboundNum = 0; // 防守篮板数
	private int homeAssistNum = 0;// 助攻数
	private int homeStealNum = 0;// 抢断数
	private int homeBlockNum = 0;// 盖帽数
	private int homeTurnOverNum = 0;// 失误数
	private int homeFoulNum = 0;// 犯规数
	private int homeScore = 0;// 得分

	public MatchVO(String season, String date, String visingTeam,
			String homeTeam,int partNum){
		this.season = season;
		this.date = date;
		this.visitingTeam = visingTeam;
		this.homeTeam = homeTeam;
		if(partNum==4){
			matchTime= 48*60;
		}
		else{
			matchTime= (48+(partNum-4)*5)*60;
		}
	}
	
	public MatchVO(String season, String date, String visitingTeam,
			String homeTeam, int visitingScore, int homeScore,
			ArrayList<String> detailScores, ArrayList<RecordVO> records) {
		super();
		this.season = season;
		this.date = date;
		this.visitingTeam = visitingTeam;
		this.homeTeam = homeTeam;
		this.visitingScore = visitingScore;
		this.homeScore = homeScore;
		this.detailScores = detailScores;
		this.records = records;
	}

	public String getSeason() {
		return season;
	}

	public String getDate() {
		return date;
	}

	public String getVisitingTeam() {
		return visitingTeam;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public int getMatchTime(){
		return matchTime;
	}
	
	public int getVisitingScore() {
		return visitingScore;
	}
	
	public void addVisitingScore(int visitingScore){
		this.visitingScore+=visitingScore;
	}

	public int getHomeScore() {
		return homeScore;
	}
	
	public void addHomeScore(int homeScore){
		this.homeScore+=homeScore;
	}

	public ArrayList<String> getDetailScores() {
		return detailScores;
	}

	public ArrayList<RecordVO> getRecords() {
		return records;
	}
	
	public int getVisitingShootHitNum(){
		return visitingShootHitNum;
	}
	
	public int getVisitingShootAttemptNum(){
		return visitingShootAttemptNum;
	}
	
	public int getVisitingThreeHitNum(){
		return visitingThreeHitNum;
	}
	
	public int getVisitingThreeAttemptNum(){
		return visitingThreeAttemptNum;
	}
	
	public int getVisitingFreeThrowHitNum(){
		return visitingFreeThrowHitNum;
	}
	
	public int getVisitingFreeThrowAttemptNum(){
		return visitingFreeThrowAttemptNum;
	}
	
	public int getVisitingOffenReboundNum(){
		return visitingOffenReboundNum;
	}
	
	public int getVisitingDefenReboundNum(){
		return visitingDefenReboundNum;
	}
	
	public int getVisitingAssistNum(){
		return visitingAssistNum;
	}
	
	public int getVisitingStealNum(){
		return visitingStealNum;
	}
	
	public int getVisitingBlockNum(){
		return visitingBlockNum;
	}
	
	public int getVisitingTurnOverNum(){
		return visitingTurnOverNum;
	}
	
	public int getVisitingFoulNum(){
		return visitingFoulNum;
	}
	


	
	public void addVisitingShootHitNum(int visitingShootHitNum){
		this.visitingShootHitNum+=visitingShootHitNum;
	}
	
	public void addVisitingShootAttemptNum(int visitingShootAttemptNum){
		this.visitingShootAttemptNum+=visitingShootAttemptNum;
	}
	
	public void addVisitingThreeHitNum(int visitingThreeHitNum){
		this.visitingThreeHitNum+=visitingThreeHitNum;
	}
	
	public void addVisitingThreeAttemptNum(int visitingThreeAttemptNum){
		this.visitingThreeAttemptNum+=visitingThreeAttemptNum;
	}
	
	public void addVisitingFreeThrowHitNum(int visitingFreeThrowHitNum){
		this.visitingFreeThrowHitNum+=visitingFreeThrowHitNum;
	}
	
	public void addVisitingFreeThrowAttemptNum(int visitingFreeThrowAttemptNum){
		this.visitingFreeThrowAttemptNum+=visitingFreeThrowAttemptNum;
	}
	
	public void addVisitingOffenReboundNum(int visitingOffenReboundNum){
		this.visitingOffenReboundNum+=visitingOffenReboundNum;
	}
	
	public void addVisitingDefenReboundNum(int visitingDefenReboundNum){
		this.visitingDefenReboundNum+=visitingDefenReboundNum;
	}
	
	public void addVisitingAssistNum(int visitingAssistNum){
		this.visitingAssistNum+=visitingAssistNum;
	}
	
	public void addVisitingStealNum(int visitingStealNum){
		this.visitingStealNum+=visitingStealNum;
	}
	
	public void addVisitingBlockNum(int visitingBlockNum){
		this.visitingBlockNum+=visitingBlockNum;
	}
	
	public void addVisitingTurnOverNum(int visitingTurnOverNum){
		this.visitingTurnOverNum+=visitingTurnOverNum;
	}
	
	public void addVisitingFoulNum(int visitingFoulNum){
		this.visitingFoulNum+=visitingFoulNum;
	}
	
	
	
	
	
	
	public int getHomeShootHitNum(){
		return homeShootHitNum;
	}
	
	public int getHomeShootAttemptNum(){
		return homeShootAttemptNum;
	}
	
	public int getHomeThreeHitNum(){
		return homeThreeHitNum;
	}
	
	public int getHomeThreeAttemptNum(){
		return homeThreeAttemptNum;
	}
	
	public int getHomeFreeThrowHitNum(){
		return homeFreeThrowHitNum;
	}
	
	public int getHomeFreeThrowAttemptNum(){
		return homeFreeThrowAttemptNum;
	}
	
	public int getHomeOffenReboundNum(){
		return homeOffenReboundNum;
	}
	
	public int getHomeDefenReboundNum(){
		return homeDefenReboundNum;
	}
	
	public int getHomeAssistNum(){
		return homeAssistNum;
	}
	
	public int getHomeStealNum(){
		return homeStealNum;
	}
	
	public int getHomeBlockNum(){
		return homeBlockNum;
	}
	
	public int getHomeTurnOverNum(){
		return homeTurnOverNum;
	}
	
	public int getHomeFoulNum(){
		return homeFoulNum;
	}
	
	
	public void addHomeShootHitNum(int homeShootHitNum){
		this.homeShootHitNum+=homeShootHitNum;
	}
	
	public void addHomeShootAttemptNum(int homeShootAttemptNum){
		this.homeShootAttemptNum+=homeShootAttemptNum;
	}
	
	public void addHomeThreeHitNum(int homeThreeHitNum){
		this.homeThreeHitNum+=homeThreeHitNum;
	}
	
	public void addHomeThreeAttemptNum(int homeThreeAttemptNum){
		this.homeThreeAttemptNum+=homeThreeAttemptNum;
	}
	
	public void addHomeFreeThrowHitNum(int homeFreeThrowHitNum){
		this.homeFreeThrowHitNum+=homeFreeThrowHitNum;
	}
	
	public void addHomeFreeThrowAttemptNum(int homeFreeThrowAttemptNum){
		this.homeFreeThrowAttemptNum+=homeFreeThrowAttemptNum;
	}
	
	public void addHomeOffenReboundNum(int homeOffenReboundNum){
		this.homeOffenReboundNum+=homeOffenReboundNum;
	}
	
	public void addHomeDefenReboundNum(int homeDefenReboundNum){
		this.homeDefenReboundNum+=homeDefenReboundNum;
	}
	
	public void addHomeAssistNum(int homeAssistNum){
		this.homeAssistNum+=homeAssistNum;
	}
	
	public void addHomeStealNum(int homeStealNum){
		this.homeStealNum+=homeStealNum;
	}
	
	public void addHomeBlockNum(int homeBlockNum){
		this.homeBlockNum+=homeBlockNum;
	}
	
	public void addHomeTurnOverNum(int homeTurnOverNum){
		this.homeTurnOverNum+=homeTurnOverNum;
	}
	
	public void addHomeFoulNum(int homeFoulNum){
		this.homeFoulNum+=homeFoulNum;
	}
	
	
}
