package bl;

import java.util.ArrayList;

import vo.LiveMatchDetailVO;
import blService.LiveTxtBLService;
import data.LiveTxtData;
import dataservice.LiveTxtDataService;

public class LiveTxt implements LiveTxtBLService {
	public LiveTxtDataService liveTxtData;

	public LiveTxt() {
		liveTxtData = new LiveTxtData();
	}

	public static void main(String[] args){
		long start = System.currentTimeMillis();
		LiveTxt liveTxt = new LiveTxt();
		System.out.println(liveTxt.getLiveTxt("14-15", "04-04", "DET-CHI").size());
		long end1 = System.currentTimeMillis();
		System.out.println("运行时间：" + (end1 - start) + "毫秒");// 应该是end - start
//		System.out.println(liveTxt.getPlayerName(76002));
//		long end2 = System.currentTimeMillis();
//		System.out.println("运行时间：" + (end2 - end1) + "毫秒");// 应该是end - start
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(liveTxt.getPlayerName(51));
//		long end3 = System.currentTimeMillis();
//		System.out.println("运行时间：" + (end3 - end2-3000) + "毫秒");// 应该是end - start
	}
	
	public ArrayList<LiveMatchDetailVO> getLiveTxt(String season, String date,
			String teams) {
		// TODO Auto-generated method stub
		return liveTxtData.getLiveTxt(season, date, teams);
	}
	
	public void addToSql(String s){
		liveTxtData.addToSql(s);
	}

}
