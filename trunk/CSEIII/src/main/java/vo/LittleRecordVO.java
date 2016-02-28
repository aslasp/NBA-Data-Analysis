package vo;

public class LittleRecordVO {
  private String season;
  private String date;
  private int score;
  private int reboundNum;
  private int assistNum;
  
  public LittleRecordVO(String season,String date,int score,int reboundNum,int assistNum){
	  this.season=season;
	  this.date=date;
	  this.score=score;
	  this.reboundNum=reboundNum;
	  this.assistNum=assistNum;
  }
  
  public String getSeason(){
	  return season;
  }
  
  public String getDate(){
	  return date;
  }
 
  public int getScore(){
	  return score;
  }
  
  public int getReboundNum(){
	  return reboundNum;
  }
  
  public int getAssistNum(){
	  return assistNum;
  }
}
