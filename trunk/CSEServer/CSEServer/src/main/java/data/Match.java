package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import po.MatchPO;
import po.RecordPO;
import SQLHelper.SqlManager;
import dataservice.MatchDataService;

public class Match extends UnicastRemoteObject implements MatchDataService {
	private static final long serialVersionUID = 1L;

	Connection con;

	public Match() throws RemoteException {
		super();
		// TODO 自动生成的构造函数存根
	}

	public static void main(String[] args) {
		Match match;
		try {
			match = new Match();
			ArrayList<MatchPO> result = match.getMatchData("all", "all","all",
					"all");
			System.out.println(result.size());
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		System.out.println("success!");
	}

	public ArrayList<MatchPO> getMatchData(String season, String date,
			String homeTeam, String visitingTeam) throws RemoteException {

		String query = "select * from matches where";
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();

		int flag = 0;

		if (!season.equals("all")) {
			if (flag == 1)
				query = query + " and season like '%" + season + "%'";
			else {
				query = query + " season like '%" + season + "%'";
				flag = 1;
			}
		}

		if (!date.equals("all")) {
			if (flag == 1)
				query = query + " and date like '%" + date + "%'";
			else {
				query = query + " date like '%" + date + "%'";
				flag = 1;
			}
		}

		if (!homeTeam.equals("all")) {
			if (flag == 1)
				query = query + " and homeTeam like '%" + homeTeam + "%'";
			else {
				query = query + " homeTeam like '%" + homeTeam + "%'";
				flag = 1;
			}
		}

		if (!visitingTeam.equals("all")) {
			if (flag == 1)
				query = query + " and visitingTeam like '%" + visitingTeam
						+ "%'";
			else {
				query = query + " visitingTeam like '%" + visitingTeam + "%'";
				flag = 1;
			}
		}

		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			ResultSet rs = sql.executeQuery(query);
			int matchID = 0;
			String myseason = "";
			String mydate = "";
			String myvisingTeam = "";
			String myhomeTeam = "";
			int visitingScore = 0;
			int homeScore = 0;
			ArrayList<String> detailScores;
			ArrayList<RecordPO> records;
			MatchPO matchPO;

			while (rs.next()) {
				matchID = rs.getInt("matchID");
				myseason = rs.getString("season");
				mydate = rs.getString("date");
				myvisingTeam = rs.getString("visitingTeam");
				myhomeTeam = rs.getString("homeTeam");
				visitingScore = rs.getInt("visitingScore");
				homeScore = rs.getInt("homeScore");
				Statement sql2 = con.createStatement();
				ResultSet rs1 = sql2
						.executeQuery("select score from detailscores where matchID="
								+ matchID);
				detailScores = new ArrayList<String>();
				while (rs1.next()) {
					detailScores.add(rs1.getString("score"));
				}
				rs1.close();
				sql2.close();
				Statement sql3 = con.createStatement();
				ResultSet rs2 = sql3
						.executeQuery("select * from records where matchID="
								+ matchID);
				records = new ArrayList<RecordPO>();
				RecordPO po;
				while (rs2.next()) {
					po = new RecordPO(rs2.getString("team"),
							rs2.getString("playerName"),
							rs2.getString("position"),
							rs2.getString("presentTime"),
							rs2.getInt("shootHitNum"),
							rs2.getInt("shootAttemptNum"),
							rs2.getInt("threeHitNum"),
							rs2.getInt("threeAttemptNum"),
							rs2.getInt("freeThrowHitNum"),
							rs2.getInt("freeThrowAttemptNum"),
							rs2.getInt("offenReboundNum"),
							rs2.getInt("defenReboundNum"),
							rs2.getInt("reboundNum"), rs2.getInt("assistNum"),
							rs2.getInt("stealNum"), rs2.getInt("blockNum"),
							rs2.getInt("turnOverNum"), rs2.getInt("foulNum"),
							rs2.getInt("score"));
					records.add(po);
				}
				rs2.close();
				sql3.close();
				matchPO = new MatchPO(matchID, myseason, mydate, myvisingTeam,
						myhomeTeam, visitingScore, homeScore, detailScores,
						records);
				matches.add(matchPO);
			}

			rs.close();
			sql.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return matches;
	}
}
