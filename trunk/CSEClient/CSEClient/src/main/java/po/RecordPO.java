package po;

import java.io.Serializable;

public class RecordPO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String team;// 球队
	private String playerName;// 球员名
	private String position;// 位置
	private String presentTime;// 在场时间
	private int shootHitNum;// 投篮命中数
	private int shootAttemptNum;// 投篮出手数
	private int threeHitNum;// 三分命中数
	private int threeAttemptNum;// 三分出手数
	private int freeThrowHitNum;// 罚球命中数
	private int freeThrowAttemptNum;// 罚球出手数
	private int offenReboundNum;// 进攻（前场）篮板数
	private int defenReboundNum;// 防守（后场）篮板数
	private int reboundNum;// 总篮板数
	private int assistNum;// 助攻数
	private int stealNum;// 抢断数
	private int blockNum;// 盖帽数
	private int turnOverNum;// 失误数
	private int foulNum;// 犯规数
	private int score;// 个人得分

	public RecordPO(String team, String playerName, String position,
			String presentTime, int shootHitNum, int shootAttemptNum,
			int threeHitNum, int threeAttemptNum, int freeThrowHitNum,
			int freeThrowAttemptNum, int offenReboundNum, int defenReboundNum,
			int reboundNum, int assistNum, int stealNum, int blockNum,
			int turnOverNum, int foulNum, int score) {
		super();
		this.team = team;
		this.playerName = playerName;
		this.position = position;
		this.presentTime = presentTime;
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
	}

	public String getTeam() {
		return team;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getPosition() {
		return position;
	}

	public String getPresentTime() {
		return presentTime;
	}

	public int getShootHitNum() {
		return shootHitNum;
	}

	public int getShootAttemptNum() {
		return shootAttemptNum;
	}

	public int getThreeHitNum() {
		return threeHitNum;
	}

	public int getThreeAttemptNum() {
		return threeAttemptNum;
	}

	public int getFreeThrowHitNum() {
		return freeThrowHitNum;
	}

	public int getFreeThrowAttemptNum() {
		return freeThrowAttemptNum;
	}

	public int getOffenReboundNum() {
		return offenReboundNum;
	}

	public int getDefenReboundNum() {
		return defenReboundNum;
	}

	public int getReboundNum() {
		return reboundNum;
	}

	public int getAssistNum() {
		return assistNum;
	}

	public int getStealNum() {
		return stealNum;
	}

	public int getBlockNum() {
		return blockNum;
	}

	public int getTurnOverNum() {
		return turnOverNum;
	}

	public int getFoulNum() {
		return foulNum;
	}

	public int getScore() {
		return score;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setPresentTime(String presentTime) {
		this.presentTime = presentTime;
	}

	public void setShootHitNum(int shootHitNum) {
		this.shootHitNum = shootHitNum;
	}

	public void setShootAttemptNum(int shootAttemptNum) {
		this.shootAttemptNum = shootAttemptNum;
	}

	public void setThreeHitNum(int threeHitNum) {
		this.threeHitNum = threeHitNum;
	}

	public void setThreeAttemptNum(int threeAttemptNum) {
		this.threeAttemptNum = threeAttemptNum;
	}

	public void setFreeThrowHitNum(int freeThrowHitNum) {
		this.freeThrowHitNum = freeThrowHitNum;
	}

	public void setFreeThrowAttemptNum(int freeThrowAttemptNum) {
		this.freeThrowAttemptNum = freeThrowAttemptNum;
	}

	public void setOffenReboundNum(int offenReboundNum) {
		this.offenReboundNum = offenReboundNum;
	}

	public void setDefenReboundNum(int defenReboundNum) {
		this.defenReboundNum = defenReboundNum;
	}

	public void setReboundNum(int reboundNum) {
		this.reboundNum = reboundNum;
	}

	public void setAssistNum(int assistNum) {
		this.assistNum = assistNum;
	}

	public void setStealNum(int stealNum) {
		this.stealNum = stealNum;
	}

	public void setBlockNum(int blockNum) {
		this.blockNum = blockNum;
	}

	public void setTurnOverNum(int turnOverNum) {
		this.turnOverNum = turnOverNum;
	}

	public void setFoulNum(int foulNum) {
		this.foulNum = foulNum;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
