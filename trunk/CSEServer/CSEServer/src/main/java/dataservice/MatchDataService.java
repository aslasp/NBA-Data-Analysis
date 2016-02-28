package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.MatchPO;

public interface MatchDataService {

	public ArrayList<MatchPO> getMatchData(String season, String date,
			String homeTeam, String visitingTeam) throws RemoteException;

}
