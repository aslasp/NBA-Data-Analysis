package dataForSql;

import data.LiveTxtData;
import data.MatchData;
import data.PlayerIdData;
import data.PlayerSalaryData;

/**
 * 初始化数据库的类
 * @author 严顺宽
 *
 */
public class Init {
	PlayerDataForSql playerDataForSql=new PlayerDataForSql();
	TeamDataForSql teamDataForSql=new TeamDataForSql(0);
	LiveTxtData liveTxtData=new LiveTxtData();
	PlayerIdData playerIdData=new PlayerIdData();
	PlayerSalaryData playerSalaryData=new PlayerSalaryData();
	MatchData matchData=new MatchData();
	
	public void run(){
		teamDataForSql.exportToSql();
		matchData.exportToSql();
		playerDataForSql.allInit();
		playerIdData.exportToSql();
		playerSalaryData.exportToSql();
		
		liveTxtData.exportToSql();
	}
	
	public static void main(String[] args) {
		Init init=new Init();
		init.run();
	}
}
