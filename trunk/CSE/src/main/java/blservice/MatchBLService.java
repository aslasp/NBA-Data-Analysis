package blservice;

import java.util.ArrayList;

import vo.MatchVO;

public interface MatchBLService {
	public ArrayList<MatchVO> getMatchData(String season, String date,
			String homeTeam, String visitingTeam);
}
