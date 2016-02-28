package businesslogic;

import java.rmi.Naming;
import java.util.ArrayList;

import po.MatchPO;
import po.RecordPO;
import vo.MatchVO;
import vo.RecordVO;
import businesslogicservice.MatchBLService;
import dataservice.MatchDataService;

public class Match implements MatchBLService {
	private MatchDataService service;

	public Match() {
		try {
			String host = GetServer.getServerHost();
			String url = "rmi://" + host + "/matchService";
			service = (MatchDataService) Naming.lookup(url);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public ArrayList<MatchVO> getMatchData(String season, String date,
			String homeTeam, String visitingTeam) {
		// TODO 自动生成的方法存根
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		try {
			matches = service
					.getMatchData(season, date, homeTeam, visitingTeam);
			for (MatchPO po : matches) {
				MatchVO vo = poToVo(po);
				result.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public static MatchVO poToVo(MatchPO po) {
		ArrayList<RecordVO> records = new ArrayList<RecordVO>();
		for (RecordPO recordPO : po.getRecords()) {
			RecordVO recordVO = recordPoToVO(recordPO);
			records.add(recordVO);
		}

		MatchVO matchVO = new MatchVO(po.getMatchID(), po.getSeason(),
				po.getDate(), po.getVisingTeam(), po.getHomeTeam(),
				po.getVisitingScore(), po.getHomeScore(), po.getDetailScores(),
				records);
		return matchVO;
	}

	public static RecordVO recordPoToVO(RecordPO po) {
		RecordVO recordVO = new RecordVO(po.getTeam(), po.getPlayerName(),
				po.getPosition(), po.getPresentTime(), po.getShootHitNum(),
				po.getShootAttemptNum(), po.getThreeHitNum(),
				po.getThreeAttemptNum(), po.getFreeThrowHitNum(),
				po.getFreeThrowAttemptNum(), po.getOffenReboundNum(),
				po.getDefenReboundNum(), po.getReboundNum(), po.getAssistNum(),
				po.getStealNum(), po.getBlockNum(), po.getTurnOverNum(),
				po.getFoulNum(), po.getScore());
		return recordVO;

	}
}
