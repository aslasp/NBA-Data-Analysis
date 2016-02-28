package po;

import java.io.Serializable;

public class TeamPO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id; // 编号
	private String teamName; // 球队全名
	private String abLocation; // 所在地(缩写)
	private String location; // 所在地
	private String conference; // 赛区
	private String partition; // 分区
	private String homeCourt; // 主场
	private int setUpTime; // 建立时间

	private int matchesNum; // 比赛场数
	private double shootHitNum; // 投篮命中数
	private double shootAttemptNum; // 投篮出手次数
	private double threeHitNum; // 三分命中数
	private double threeAttemptNum; // 三分出手数
	private double freeThrowHitNum; // 罚球命中数
	private double freeThrowAttemptNum; // 罚球出手数
	private double offenReboundNum; // 进攻篮板数
	private double defenReboundNum; // 防守篮板数
	private double reboundNum;// 篮板数
	private double assistNum;// 助攻数
	private double stealNum;// 抢断数
	private double blockNum;// 盖帽数
	private double turnOverNum;// 失误数
	private double foulNum;// 犯规数
	private double score;// 比赛得分
	private double shootHitRate;// 投篮命中率
	private double threeHitRate;// 三分命中率
	private double freeThrowHitRate;// 罚球命中率
	private double winRate; // 胜率
	private double offenRound; // 进攻回合
	private double offenEfficiency; // 进攻效率
	private double defenEfficiency; // 防守效率
	private double offenReboundEfficiency; // 进攻篮板效率
	private double defenReboundEfficiency; // 防守篮板效率
	private double stealEfficiency; // 抢断效率
	private double assistRate; // 助攻率

	public TeamPO(int id, String teamName, String abLocation, String location,
			String conference, String partition, String homeCourt, int setUpTime) {
		super();
		this.id = id;
		this.teamName = teamName;
		this.location = location;
		this.abLocation = abLocation;
		this.conference = conference;
		this.partition = partition;
		this.homeCourt = homeCourt;
		this.setUpTime = setUpTime;
	}

	public TeamPO(String abLocation, int matchesNum, double shootHitNum,
			double shootAttemptNum, double threeHitNum, double threeAttemptNum,
			double freeThrowHitNum, double freeThrowAttemptNum,
			double offenReboundNum, double defenReboundNum, double reboundNum,
			double assistNum, double stealNum, double blockNum,
			double turnOverNum, double foulNum, double score,
			double shootHitRate, double threeHitRate, double freeThrowHitRate,
			double winRate, double offenRound, double offenEfficiency,
			double defenEfficiency, double offenReboundEfficiency,
			double defenReboundEfficiency, double stealEfficiency,
			double assistRate) {
		super();
		this.abLocation = abLocation;
		this.matchesNum = matchesNum;
		this.shootHitNum = shootHitNum;
		this.shootAttemptNum = shootAttemptNum;
		this.threeHitNum = threeHitNum;
		this.threeAttemptNum = threeAttemptNum;
		this.freeThrowHitNum = freeThrowHitNum;
		this.freeThrowAttemptNum = freeThrowAttemptNum;
		this.offenReboundNum = offenReboundNum;
		this.defenReboundNum = defenReboundNum;
		this.reboundNum = reboundNum;
		this.assistNum = assistNum;
		this.stealNum = stealNum;
		this.blockNum = blockNum;
		this.turnOverNum = turnOverNum;
		this.foulNum = foulNum;
		this.score = score;
		this.shootHitRate = shootHitRate;
		this.threeHitRate = threeHitRate;
		this.freeThrowHitRate = freeThrowHitRate;
		this.winRate = winRate;
		this.offenRound = offenRound;
		this.offenEfficiency = offenEfficiency;
		this.defenEfficiency = defenEfficiency;
		this.offenReboundEfficiency = offenReboundEfficiency;
		this.defenReboundEfficiency = defenReboundEfficiency;
		this.stealEfficiency = stealEfficiency;
		this.assistRate = assistRate;
	}

	public int getId() {
		return id;
	}

	public String getTeamName() {
		return teamName;
	}

	public String getAbLocation() {
		return abLocation;
	}

	public String getLocation() {
		return location;
	}

	public String getConference() {
		return conference;
	}

	public String getPartition() {
		return partition;
	}

	public String getHomeCourt() {
		return homeCourt;
	}

	public int getSetUpTime() {
		return setUpTime;
	}

	public int getMatchesNum() {
		return matchesNum;
	}

	public double getShootHitNum() {
		return shootHitNum;
	}

	public double getShootAttemptNum() {
		return shootAttemptNum;
	}

	public double getThreeHitNum() {
		return threeHitNum;
	}

	public double getThreeAttemptNum() {
		return threeAttemptNum;
	}

	public double getFreeThrowHitNum() {
		return freeThrowHitNum;
	}

	public double getFreeThrowAttemptNum() {
		return freeThrowAttemptNum;
	}

	public double getOffenReboundNum() {
		return offenReboundNum;
	}

	public double getDefenReboundNum() {
		return defenReboundNum;
	}

	public double getReboundNum() {
		return reboundNum;
	}

	public double getAssistNum() {
		return assistNum;
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

	public double getShootHitRate() {
		return shootHitRate;
	}

	public double getThreeHitRate() {
		return threeHitRate;
	}

	public double getFreeThrowHitRate() {
		return freeThrowHitRate;
	}

	public double getWinRate() {
		return winRate;
	}

	public double getOffenRound() {
		return offenRound;
	}

	public double getOffenEfficiency() {
		return offenEfficiency;
	}

	public double getDefenEfficiency() {
		return defenEfficiency;
	}

	public double getOffenReboundEfficiency() {
		return offenReboundEfficiency;
	}

	public double getDefenReboundEfficiency() {
		return defenReboundEfficiency;
	}

	public double getStealEfficiency() {
		return stealEfficiency;
	}

	public double getAssistRate() {
		return assistRate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setAbLocation(String abLocation) {
		this.abLocation = abLocation;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setConference(String conference) {
		this.conference = conference;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public void setHomeCourt(String homeCourt) {
		this.homeCourt = homeCourt;
	}

	public void setSetUpTime(int setUpTime) {
		this.setUpTime = setUpTime;
	}

	public void setMatchesNum(int matchesNum) {
		this.matchesNum = matchesNum;
	}

	public void setShootHitNum(double shootHitNum) {
		this.shootHitNum = shootHitNum;
	}

	public void setShootAttemptNum(double shootAttemptNum) {
		this.shootAttemptNum = shootAttemptNum;
	}

	public void setThreeHitNum(double threeHitNum) {
		this.threeHitNum = threeHitNum;
	}

	public void setThreeAttemptNum(double threeAttemptNum) {
		this.threeAttemptNum = threeAttemptNum;
	}

	public void setFreeThrowHitNum(double freeThrowHitNum) {
		this.freeThrowHitNum = freeThrowHitNum;
	}

	public void setFreeThrowAttemptNum(double freeThrowAttemptNum) {
		this.freeThrowAttemptNum = freeThrowAttemptNum;
	}

	public void setOffenReboundNum(double offenReboundNum) {
		this.offenReboundNum = offenReboundNum;
	}

	public void setDefenReboundNum(double defenReboundNum) {
		this.defenReboundNum = defenReboundNum;
	}

	public void setReboundNum(double reboundNum) {
		this.reboundNum = reboundNum;
	}

	public void setAssistNum(double assistNum) {
		this.assistNum = assistNum;
	}

	public void setStealNum(double stealNum) {
		this.stealNum = stealNum;
	}

	public void setBlockNum(double blockNum) {
		this.blockNum = blockNum;
	}

	public void setTurnOverNum(double turnOverNum) {
		this.turnOverNum = turnOverNum;
	}

	public void setFoulNum(double foulNum) {
		this.foulNum = foulNum;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void setShootHitRate(double shootHitRate) {
		this.shootHitRate = shootHitRate;
	}

	public void setThreeHitRate(double threeHitRate) {
		this.threeHitRate = threeHitRate;
	}

	public void setFreeThrowHitRate(double freeThrowHitRate) {
		this.freeThrowHitRate = freeThrowHitRate;
	}

	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}

	public void setOffenRound(double offenRound) {
		this.offenRound = offenRound;
	}

	public void setOffenEfficiency(double offenEfficiency) {
		this.offenEfficiency = offenEfficiency;
	}

	public void setDefenEfficiency(double defenEfficiency) {
		this.defenEfficiency = defenEfficiency;
	}

	public void setOffenReboundEfficiency(double offenReboundEfficiency) {
		this.offenReboundEfficiency = offenReboundEfficiency;
	}

	public void setDefenReboundEfficiency(double defenReboundEfficiency) {
		this.defenReboundEfficiency = defenReboundEfficiency;
	}

	public void setStealEfficiency(double stealEfficiency) {
		this.stealEfficiency = stealEfficiency;
	}

	public void setAssistRate(double assistRate) {
		this.assistRate = assistRate;
	}

}