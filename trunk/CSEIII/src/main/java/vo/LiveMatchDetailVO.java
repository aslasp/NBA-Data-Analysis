package vo;

/**
 * 比赛直播的VO
 * 
 * @author Administrator
 *
 */
public class LiveMatchDetailVO {
	private String season;// 赛季
	private String date;// 日期
	private String teams;// 对阵队伍 客队-主队
	private int part;// 比赛节数
	private int partNum;// 比赛总节数
	private String content;// 直播的详细内容

	public LiveMatchDetailVO(String season, String date, String teams,
			int part, int partNum, String content) {
		super();
		this.season = season;
		this.date = date;
		this.teams = teams;
		this.part = part;
		this.partNum = partNum;
		this.content = content;
	}

	public String getSeason() {
		return season;
	}

	public String getDate() {
		return date;
	}

	public String getTeams() {
		return teams;
	}

	public int getPart() {
		return part;
	}

	public int getPartNum() {
		return partNum;
	}

	public String getContent() {
		return content;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTeams(String teams) {
		this.teams = teams;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public void setPartNum(int partNum) {
		this.partNum = partNum;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
