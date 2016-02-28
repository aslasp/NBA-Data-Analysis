package bl;

import data.PlayerIdData;
import dataservice.PlayerIdDataService;
import blService.PlayerIdBLService;

public class PlayerID implements PlayerIdBLService{
    PlayerIdDataService playerIDData=new PlayerIdData();
	public String getPlayerName(int playerID) {
		//return " ";
		return playerIDData.getPlayerName(playerID);
	}
	
	public void openSql() {
		playerIDData.openSql();
	}

	public void closeSql() {
		playerIDData.closeSql();
	}

	public static void main(String[] args){
		long start = System.currentTimeMillis();
		PlayerID playerDataReader = new PlayerID();
		System.out.println(playerDataReader.getPlayerName(76001));
		long end1 = System.currentTimeMillis();
		System.out.println("运行时间：" + (end1 - start) + "毫秒");// 应该是end - start
		System.out.println(playerDataReader.getPlayerName(76002));
		long end2 = System.currentTimeMillis();
		System.out.println("运行时间：" + (end2 - end1) + "毫秒");// 应该是end - start
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(playerDataReader.getPlayerName(51));
		long end3 = System.currentTimeMillis();
		System.out.println("运行时间：" + (end3 - end2-3000) + "毫秒");// 应该是end - start
	}

}
