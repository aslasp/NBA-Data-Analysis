package vo;

public class TeamVO {
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
	private double assistEfficiency; // 助攻率

	public TeamVO(int id, String teamName, String abLocation, String location,
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

	public TeamVO(int id, String teamName, String abLocation, String location,
			String conference, String partition, String homeCourt,
			int setUpTime, int matchesNum, double shootHitNum,
			double shootAttemptNum, double threeHitNum, double threeAttemptNum,
			double freeThrowHitNum, double freeThrowAttemptNum,
			double offenReboundNum, double defenReboundNum, double reboundNum,
			double assistNum, double stealNum, double blockNum,
			double turnOverNum, double foulNum, double score,
			double shootHitRate, double threeHitRate, double freeThrowHitRate,
			double winRate, double offenRound, double offenEfficiency,
			double defenEfficiency, double offenReboundEfficiency,
			double defenReboundEfficiency, double stealEfficiency,
			double assistEfficiency) {
		super();
		this.id = id;
		this.teamName = teamName;
		this.abLocation = abLocation;
		this.location = location;
		this.conference = conference;
		this.partition = partition;
		this.homeCourt = homeCourt;
		this.setUpTime = setUpTime;
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
		this.assistEfficiency = assistEfficiency;
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

	public double getAssistEfficiency() {
		return assistEfficiency;
	}

}
