package bl.team;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import vo.TeamVO;

public class SequenceOfTeamMap implements Comparator<Map.Entry<String, TeamVO>> {
	private String column;
	private String order;

	public SequenceOfTeamMap(String column, String order) {
		this.column = column;
		this.order = order;
	}

	public int compare(Entry<String, TeamVO> t1, Entry<String, TeamVO> t2) {
		// TODO 自动生成的方法存根
		int result = 0;

		if (column.equals("teamName")) {
			result = t1.getValue().getTeamName()
					.compareTo(t1.getValue().getTeamName());
		} else if (column.equals("matchesNum")) {
			result = compareInt(t1.getValue().getMatchesNum(), t2.getValue()
					.getMatchesNum());
		} else if (column.equals("shootHitNum")) {
			result = compareDouble(t1.getValue().getShootHitNum(), t2
					.getValue().getShootHitNum());
		} else if (column.equals("shootAttemptNum")) {
			result = compareDouble(t1.getValue().getShootAttemptNum(), t2
					.getValue().getShootAttemptNum());
		} else if (column.equals("shootHitRate")) {
			result = compareDouble(t1.getValue().getShootHitRate(), t2
					.getValue().getShootHitRate());
		} else if (column.equals("threeHitNum")) {
			result = compareDouble(t1.getValue().getThreeHitNum(), t2
					.getValue().getThreeHitNum());
		} else if (column.equals("threeAttemptNum")) {
			result = compareDouble(t1.getValue().getThreeAttemptNum(), t2
					.getValue().getThreeAttemptNum());
		} else if (column.equals("threeHitRate")) {
			result = compareDouble(t1.getValue().getThreeHitRate(), t2
					.getValue().getThreeHitRate());
		} else if (column.equals("freeThrowHitNum")) {
			result = compareDouble(t1.getValue().getFreeThrowHitNum(), t2
					.getValue().getFreeThrowHitNum());
		} else if (column.equals("freeThrowAttemptNum")) {
			result = compareDouble(t1.getValue().getFreeThrowAttemptNum(), t2
					.getValue().getFreeThrowAttemptNum());
		} else if (column.equals("freeThrowHitRate")) {
			result = compareDouble(t1.getValue().getFreeThrowHitRate(), t2
					.getValue().getFreeThrowHitRate());
		} else if (column.equals("offenReboundNum")) {
			result = compareDouble(t1.getValue().getOffenReboundNum(), t2
					.getValue().getOffenReboundNum());
		} else if (column.equals("defenReboundNum")) {
			result = compareDouble(t1.getValue().getDefenReboundNum(), t2
					.getValue().getDefenReboundNum());
		} else if (column.equals("reboundNum")) {
			result = compareDouble(t1.getValue().getReboundNum(), t2.getValue()
					.getReboundNum());
		} else if (column.equals("offenReboundEfficiency")) {
			result = compareDouble(t1.getValue().getOffenReboundEfficiency(),
					t2.getValue().getOffenReboundEfficiency());
		} else if (column.equals("defenReboundEfficiency")) {
			result = compareDouble(t1.getValue().getDefenReboundEfficiency(),
					t2.getValue().getDefenReboundEfficiency());
		} else if (column.equals("offenRound")) {
			result = compareDouble(t1.getValue().getOffenRound(), t2.getValue()
					.getOffenRound());
		} else if (column.equals("offenEfficiency")) {
			result = compareDouble(t1.getValue().getOffenEfficiency(), t2
					.getValue().getOffenEfficiency());
		} else if (column.equals("defenEfficiency")) {
			result = compareDouble(t1.getValue().getDefenEfficiency(), t2
					.getValue().getDefenEfficiency());
		} else if (column.equals("assistNum")) {
			result = compareDouble(t1.getValue().getAssistNum(), t2.getValue()
					.getAssistNum());
		} else if (column.equals("assistRate")) {
			result = compareDouble(t1.getValue().getAssistRate(), t2.getValue()
					.getAssistRate());
		} else if (column.equals("stealNum")) {
			result = compareDouble(t1.getValue().getStealNum(), t2.getValue()
					.getStealNum());
		} else if (column.equals("stealEfficiency")) {
			result = compareDouble(t1.getValue().getStealEfficiency(), t2
					.getValue().getStealEfficiency());
		} else if (column.equals("blockNum")) {
			result = compareDouble(t1.getValue().getBlockNum(), t2.getValue()
					.getBlockNum());
		} else if (column.equals("turnOverNum")) {
			result = compareDouble(t1.getValue().getTurnOverNum(), t2
					.getValue().getTurnOverNum());
		} else if (column.equals("foulNum")) {
			result = compareDouble(t1.getValue().getFoulNum(), t2.getValue()
					.getFoulNum());
		} else if (column.equals("score")) {
			result = compareDouble(t1.getValue().getScore(), t2.getValue()
					.getScore());
		} else if (column.equals("winRate")) {
			result = compareDouble(t1.getValue().getWinRate(), t2.getValue()
					.getWinRate());
		}
		
//		if (column.equals("球队名称")) {
//			result = t1.getValue().getTeamName()
//					.compareTo(t1.getValue().getTeamName());
//		} else if (column.equals("比赛场数")) {
//			result = compareInt(t1.getValue().getMatchesNum(), t2.getValue()
//					.getMatchesNum());
//		} else if (column.equals("投篮命中数")) {
//			result = compareDouble(t1.getValue().getShootHitNum(), t2
//					.getValue().getShootHitNum());
//		} else if (column.equals("投篮出手次数")) {
//			result = compareDouble(t1.getValue().getShootAttemptNum(), t2
//					.getValue().getShootAttemptNum());
//		} else if (column.equals("投篮命中率")) {
//			result = compareDouble(t1.getValue().getShootHitRate(), t2
//					.getValue().getShootHitRate());
//		} else if (column.equals("三分命中数")) {
//			result = compareDouble(t1.getValue().getThreeHitNum(), t2
//					.getValue().getThreeHitNum());
//		} else if (column.equals("三分出手数")) {
//			result = compareDouble(t1.getValue().getThreeAttemptNum(), t2
//					.getValue().getThreeAttemptNum());
//		} else if (column.equals("三分命中率")) {
//			result = compareDouble(t1.getValue().getThreeHitRate(), t2
//					.getValue().getThreeHitRate());
//		} else if (column.equals("罚球命中数")) {
//			result = compareDouble(t1.getValue().getFreeThrowHitNum(), t2
//					.getValue().getFreeThrowHitNum());
//		} else if (column.equals("罚球出手数")) {
//			result = compareDouble(t1.getValue().getFreeThrowAttemptNum(), t2
//					.getValue().getFreeThrowAttemptNum());
//		} else if (column.equals("罚球命中率")) {
//			result = compareDouble(t1.getValue().getFreeThrowHitRate(), t2
//					.getValue().getFreeThrowHitRate());
//		} else if (column.equals("进攻篮板数")) {
//			result = compareDouble(t1.getValue().getOffenReboundNum(), t2
//					.getValue().getOffenReboundNum());
//		} else if (column.equals("防守篮板数")) {
//			result = compareDouble(t1.getValue().getDefenReboundNum(), t2
//					.getValue().getDefenReboundNum());
//		} else if (column.equals("篮板数")) {
//			result = compareDouble(t1.getValue().getReboundNum(), t2.getValue()
//					.getReboundNum());
//		} else if (column.equals("进攻篮板效率")) {
//			result = compareDouble(t1.getValue().getOffenReboundEfficiency(),
//					t2.getValue().getOffenReboundEfficiency());
//		} else if (column.equals("防守篮板效率")) {
//			result = compareDouble(t1.getValue().getDefenReboundEfficiency(),
//					t2.getValue().getDefenReboundEfficiency());
//		} else if (column.equals("进攻回合")) {
//			result = compareDouble(t1.getValue().getOffenRound(), t2.getValue()
//					.getOffenRound());
//		} else if (column.equals("进攻效率")) {
//			result = compareDouble(t1.getValue().getOffenEfficiency(), t2
//					.getValue().getOffenEfficiency());
//		} else if (column.equals("防守效率")) {
//			result = compareDouble(t1.getValue().getDefenEfficiency(), t2
//					.getValue().getDefenEfficiency());
//		} else if (column.equals("助攻数")) {
//			result = compareDouble(t1.getValue().getAssistNum(), t2.getValue()
//					.getAssistNum());
//		} else if (column.equals("助攻率")) {
//			result = compareDouble(t1.getValue().getAssistRate(), t2.getValue()
//					.getAssistRate());
//		} else if (column.equals("抢断数")) {
//			result = compareDouble(t1.getValue().getStealNum(), t2.getValue()
//					.getStealNum());
//		} else if (column.equals("抢断效率")) {
//			result = compareDouble(t1.getValue().getStealEfficiency(), t2
//					.getValue().getStealEfficiency());
//		} else if (column.equals("盖帽数")) {
//			result = compareDouble(t1.getValue().getBlockNum(), t2.getValue()
//					.getBlockNum());
//		} else if (column.equals("失误数")) {
//			result = compareDouble(t1.getValue().getTurnOverNum(), t2
//					.getValue().getTurnOverNum());
//		} else if (column.equals("犯规数")) {
//			result = compareDouble(t1.getValue().getFoulNum(), t2.getValue()
//					.getFoulNum());
//		} else if (column.equals("比赛得分")) {
//			result = compareDouble(t1.getValue().getScore(), t2.getValue()
//					.getScore());
//		} else if (column.equals("胜率")) {
//			result = compareDouble(t1.getValue().getWinRate(), t2.getValue()
//					.getWinRate());
//		}
		if (order.equals("desc")) {
			// 降序
			return (-result);
		} else {
			// 升序(默认)
			return result;
		}
	}

	private int compareInt(int a, int b) {
		return Integer.compare(a, b);
	}

	private int compareDouble(Double a, Double b) {
		return Double.compare(a, b);
	}

}
