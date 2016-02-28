package bl;

import java.util.Comparator;

import vo.TeamVO;

public class SequenceOfTeam implements Comparator<TeamVO> {
	private String column;
	private String order;

	public SequenceOfTeam(String column, String order) {
		this.column = column;
		this.order = order;
	}

	public int compare(TeamVO t1, TeamVO t2) {
		// TODO 自动生成的方法存根
		int result = 0;

		if (column.equals("teamName")) {
			result = t1.getTeamName().compareTo(t1.getTeamName());
		} else if (column.equals("matchesNum")) {
			result = compareInt(t1.getMatchesNum(), t2.getMatchesNum());
		} else if (column.equals("shootHitNum")) {
			result = compareDouble(t1.getShootHitNum(), t2.getShootHitNum());
		} else if (column.equals("shootAttemptNum")) {
			result = compareDouble(t1.getShootAttemptNum(),
					t2.getShootAttemptNum());
		} else if (column.equals("shootHitRate")) {
			result = compareDouble(t1.getShootHitRate(), t2.getShootHitRate());
		} else if (column.equals("threeHitNum")) {
			result = compareDouble(t1.getThreeHitNum(), t2.getThreeHitNum());
		} else if (column.equals("threeAttemptNum")) {
			result = compareDouble(t1.getThreeAttemptNum(),
					t2.getThreeAttemptNum());
		} else if (column.equals("threeHitRate")) {
			result = compareDouble(t1.getThreeHitRate(), t2.getThreeHitRate());
		} else if (column.equals("freeThrowHitNum")) {
			result = compareDouble(t1.getFreeThrowHitNum(),
					t2.getFreeThrowHitNum());
		} else if (column.equals("freeThrowAttemptNum")) {
			result = compareDouble(t1.getFreeThrowAttemptNum(),
					t2.getFreeThrowAttemptNum());
		} else if (column.equals("freeThrowHitRate")) {
			result = compareDouble(t1.getFreeThrowHitRate(),
					t2.getFreeThrowHitRate());
		} else if (column.equals("offenReboundNum")) {
			result = compareDouble(t1.getOffenReboundNum(),
					t2.getOffenReboundNum());
		} else if (column.equals("defenReboundNum")) {
			result = compareDouble(t1.getDefenReboundNum(),
					t2.getDefenReboundNum());
		} else if (column.equals("reboundNum")) {
			result = compareDouble(t1.getReboundNum(), t2.getReboundNum());
		} else if (column.equals("offenReboundEfficiency")) {
			result = compareDouble(t1.getOffenReboundEfficiency(),
					t2.getOffenReboundEfficiency());
		} else if (column.equals("defenReboundEfficiency")) {
			result = compareDouble(t1.getDefenReboundEfficiency(),
					t2.getDefenReboundEfficiency());
		} else if (column.equals("offenRound")) {
			result = compareDouble(t1.getOffenRound(), t2.getOffenRound());
		} else if (column.equals("offenEfficiency")) {
			result = compareDouble(t1.getOffenEfficiency(),
					t2.getOffenEfficiency());
		} else if (column.equals("defenEfficiency")) {
			result = compareDouble(t1.getDefenEfficiency(),
					t2.getDefenEfficiency());
		} else if (column.equals("assistNum")) {
			result = compareDouble(t1.getAssistNum(), t2.getAssistNum());
		} else if (column.equals("assistRate")) {
			result = compareDouble(t1.getAssistRate(),
					t2.getAssistRate());
		} else if (column.equals("stealNum")) {
			result = compareDouble(t1.getStealNum(), t2.getStealNum());
		} else if (column.equals("stealEfficiency")) {
			result = compareDouble(t1.getStealEfficiency(),
					t2.getStealEfficiency());
		} else if (column.equals("blockNum")) {
			result = compareDouble(t1.getBlockNum(), t2.getBlockNum());
		} else if (column.equals("turnOverNum")) {
			result = compareDouble(t1.getTurnOverNum(), t2.getTurnOverNum());
		} else if (column.equals("foulNum")) {
			result = compareDouble(t1.getFoulNum(), t2.getFoulNum());
		} else if (column.equals("score")) {
			result = compareDouble(t1.getScore(), t2.getScore());
		} else if (column.equals("winRate")) {
			result = compareDouble(t1.getWinRate(), t2.getWinRate());
		}
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
