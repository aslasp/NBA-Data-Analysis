package po;

import java.io.Serializable;

public class PlayerPO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;// 编号
	private String name;// 球员名称
	private int number;// 球衣号码
	private String position;// 位置
	private String height;// 身高（英尺-英存）
	private int weight;// 体重（英镑）
	private String birth;// （月 日，年）
	private int age;// 年龄
	private int exp;// 球龄
	private String school;// 毕业学校

	private String teamName;// 所属球队
	private int playedGames;// 参赛场数
	private double gameStartingNum;// 先发场数
	private double reboundNum;// 篮板数
	private double assistNum;// 助攻数
	private double presentTime;// 在场时间
	private double shootHitRate;// 投篮命中率
	private double threeHitRate;// 三分命中率
	private double freeThrowHitRate;// 罚球命中率
	private double offenNum;// 进攻数
	private double defenNum;// 防守数
	private double stealNum;// 抢断数
	private double blockNum;// 盖帽数
	private double turnOverNum;// 失误数
	private double foulNum;// 犯规数
	private double score;// 得分
	private double efficiency;// 效率
	private double recentFiveMatchesScoreUpRate;// 近五场得分提升率
	private double recentFiveMatchesReboundUpRate;// 近五场篮板提升率
	private double recentFiveMatchesAssistUpRate;// 近五场助攻提升率
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

	public PlayerPO() {
		this(0, null, 0, null, null, 0, null, 0, 0, null);
	}

	public PlayerPO(int id, String name, int number, String position,
			String height, int weight, String birth, int age, int exp,
			String school) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.position = position;
		this.height = height;
		this.weight = weight;
		this.birth = birth;
		this.age = age;
		this.exp = exp;
		this.school = school;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
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

	public String getTeamName() {
		return teamName;
	}

	public int getPlayedGames() {
		return playedGames;
	}

	public double getGameStartingNum() {
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

	public double getShootHitRate() {
		return shootHitRate;
	}

	public double getThreeHitRate() {
		return threeHitRate;
	}

	public double getFreeThrowHitRate() {
		return freeThrowHitRate;
	}

	public double getOffenNum() {
		return offenNum;
	}

	public double getDefenNum() {
		return defenNum;
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

	public double getRecentFiveMatchesScoreUpRate() {
		return recentFiveMatchesScoreUpRate;
	}

	public double getRecentFiveMatchesReboundUpRate() {
		return recentFiveMatchesReboundUpRate;
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

	public double getShootEfficiency() {
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

	public double getScore_rebound_assist() {
		return score_rebound_assist;
	}

	public double getDoubleDoubleNum() {
		return doubleDoubleNum;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(int number) {
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

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
	}

	public void setGameStartingNum(double gameStartingNum) {
		this.gameStartingNum = gameStartingNum;
	}

	public void setReboundNum(double reboundNum) {
		this.reboundNum = reboundNum;
	}

	public void setAssistNum(double assistNum) {
		this.assistNum = assistNum;
	}

	public void setPresentTime(double presentTime) {
		this.presentTime = presentTime;
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

	public void setOffenNum(double offenNum) {
		this.offenNum = offenNum;
	}

	public void setDefenNum(double defenNum) {
		this.defenNum = defenNum;
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

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
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
		GmScEfficiencyValue = gmScEfficiencyValue;
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

}
