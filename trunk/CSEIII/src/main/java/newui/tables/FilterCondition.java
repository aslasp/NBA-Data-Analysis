package newui.tables;

public class FilterCondition {
	private String position;
	private String union;
	private String sort;//排序依据
	private String season;
	private String allOrAve;
	
	public FilterCondition(String position, String union, String sort,
			String season, String allOrAve) {
		super();
		this.position = position;
		this.union = union;
		this.sort = sort;
		this.season = season;
		this.allOrAve = allOrAve;
	}
	public String getPosition() {
		return position;
	}
	public String getUnion() {
		return union;
	}
	public String getSort() {
		return sort;
	}
	public String getSeason() {
		return season;
	}
	public String getAllOrAve() {
		return allOrAve;
	}
	

}
