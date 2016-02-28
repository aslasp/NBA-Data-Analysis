package data;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;

import po.MatchPO;
import po.PlayerPO;
import po.RecordPO;
import SQLHelper.SqlManager;
import dataservice.PlayerDataService;

public class Player extends UnicastRemoteObject implements PlayerDataService {
	private static final long serialVersionUID = 1L;
	Connection con;
	String todaySeason;
	String todayDate;

	public Player() throws RemoteException {
		super();
		// TODO 自动生成的构造函数存根
	}

	/**
	 * 从数据库中获得球员列表
	 * 
	 * @return 球员最基本信息的列表
	 */
	public ArrayList<PlayerPO> getPlayerBaseInfo() throws RemoteException {
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			ResultSet resultSet = sql
					.executeQuery("select players.*,playermatchdataseason.owingTeam from players,playermatchdataseason where players.name=playermatchdataseason.playerName");

			while (resultSet.next()) {
				int id = resultSet.getInt("playerID");// 编号
				String name = resultSet.getString("name");// 球员名称
				int number = resultSet.getInt("number");// 球衣号码
				String position = resultSet.getString("position");// 位置
				String height = resultSet.getString("height");// 身高（英尺-英存）
				int weight = resultSet.getInt("weight");// 体重（英镑）
				String birth = resultSet.getString("birth");// （月 日，年）
				int age = resultSet.getInt("age");// 年龄
				int exp = resultSet.getInt("exp");// 球龄
				String school = resultSet.getString("school");// 毕业学校
				String owingTeam = resultSet.getString("owingTeam");// 所属球队

				PlayerPO playerPO = new PlayerPO(id, name, number, position,
						height, weight, birth, age, exp, school);
				playerPO.setTeamName(owingTeam);
				players.add(playerPO);
			}
			resultSet.close();
			con.close();
			sql.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return players;
	}

	/**
	 * 得到球员的某个赛季的技术统计数据
	 * 
	 * @param 赛季
	 * @return
	 */
	public ArrayList<PlayerPO> getPlayerSeasonInfo(String season)
			throws RemoteException {
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		PlayerPO player;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			String query = "select * from playermatchdataseason where season='"
					+ season + "'";
			ResultSet rs = sql.executeQuery(query);
			while (rs.next()) {
				player = new PlayerPO();
				player.setName(rs.getString("playerName"));
				player.setTeamName(rs.getString("owingTeam"));
				player.setPlayedGames(rs.getInt("playedGames"));
				player.setGameStartingNum(rs.getDouble("gameStartingNum"));
				player.setReboundNum(rs.getDouble("reboundNum"));
				player.setAssistNum(rs.getDouble("assistNum"));
				player.setPresentTime(rs.getDouble("presentTime"));
				player.setShootHitRate(rs.getDouble("shootHitRate"));
				player.setThreeHitRate(rs.getDouble("threeHitRate"));
				player.setFreeThrowHitRate(rs.getDouble("freeThrowHitRate"));
				player.setOffenNum(rs.getDouble("offenReboundNum"));
				player.setDefenNum(rs.getDouble("defenReboundNum"));
				player.setStealNum(rs.getDouble("stealNum"));
				player.setBlockNum(rs.getDouble("blockNum"));
				player.setFoulNum(rs.getDouble("foulNum"));
				player.setTurnOverNum(rs.getDouble("turnOverNum"));
				player.setScore(rs.getDouble("score"));
				player.setEfficiency(rs.getDouble("efficiency"));
				player.setRecentFiveMatchesScoreUpRate(rs
						.getDouble("recentFiveMatchesScoreUpRate"));
				player.setRecentFiveMatchesReboundUpRate(rs
						.getDouble("recentFiveMatchesReboundUpRate"));
				player.setRecentFiveMatchesAssistUpRate(rs
						.getDouble("recentFiveMatchesAssistUpRate"));
				player.setGmScEfficiencyValue(rs
						.getDouble("GmScEfficiencyValue"));
				player.setTrueHitRate(rs.getDouble("trueHitRate"));
				player.setShootEfficiency(rs.getDouble("shootEfficiency"));
				player.setReboundRate(rs.getDouble("reboundRate"));
				player.setOffenReboundRate(rs.getDouble("offenReboundRate"));
				player.setDefenReboundRate(rs.getDouble("defenReboundRate"));
				player.setAssistRate(rs.getDouble("assistRate"));
				player.setStealRate(rs.getDouble("stealRate"));
				player.setBlockRate(rs.getDouble("blockRate"));
				player.setTurnOverRate(rs.getDouble("turnOverRate"));
				player.setUsageRate(rs.getDouble("usageRate"));
				player.setScore_rebound_assist(rs
						.getDouble("score_rebound_assist"));
				player.setDoubleDoubleNum(rs.getDouble("doubleDoubleNum"));
				players.add(player);
			}

			rs.close();
			con.close();
			sql.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return players;
	}

	/**
	 * 查询某球员的基础信息
	 * 
	 * @param name
	 *            球员名称
	 * @return PlayerPO对象
	 */
	public PlayerPO getPlayerBaseInfo(String name) throws RemoteException {
		String playername = name.replace("'", "''");
		PlayerPO player = null;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			String query = "select players.*,playermatchdataseason.owingTeam from players,playermatchdataseason where name='"
					+ playername + "' and name=playerName";
			ResultSet resultSet = sql.executeQuery(query);
			resultSet.next();
			player = new PlayerPO();
			int id = resultSet.getInt("playerID");
			String playerName = resultSet.getString("name");
			int number = resultSet.getInt("number");
			String position = resultSet.getString("position");
			String height = resultSet.getString("height");
			int weight = resultSet.getInt("weight");
			String birth = resultSet.getString("birth");
			int age = resultSet.getInt("age");
			int exp = resultSet.getInt("exp");
			String school = resultSet.getString("school");
			String owingTeam = resultSet.getString("owingTeam");
			player = new PlayerPO(id, playerName, number, position, height,
					weight, birth, age, exp, school);
			player.setTeamName(owingTeam);
			resultSet.close();
			con.close();
			sql.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return player;
	}

	/**
	 * 根据赛季和球员名称返回球员这个赛季的总的技术数据
	 * 
	 * @param season
	 * @param name
	 * @return
	 */
	public PlayerPO getPlayerSeasonInfo(String season, String name)
			throws RemoteException {
		String playername = name.replace("'", "''");
		PlayerPO player = null;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			String query = "select * from playermatchdataseason where name='"
					+ playername + "' and season='" + season + "'";
			ResultSet rs = sql.executeQuery(query);
			rs.next();
			player = new PlayerPO();
			player.setName(rs.getString("playerName"));
			player.setTeamName(rs.getString("owingTeam"));
			player.setPlayedGames(rs.getInt("playedGames"));
			player.setGameStartingNum(rs.getDouble("gameStartingNum"));
			player.setReboundNum(rs.getDouble("reboundNum"));
			player.setAssistNum(rs.getDouble("assistNum"));
			player.setPresentTime(rs.getDouble("presentTime"));
			player.setShootHitRate(rs.getDouble("shootHitRate"));
			player.setThreeHitRate(rs.getDouble("threeHitRate"));
			player.setFreeThrowHitRate(rs.getDouble("freeThrowHitRate"));
			player.setOffenNum(rs.getDouble("offenReboundNum"));
			player.setDefenNum(rs.getDouble("defenReboundNum"));
			player.setStealNum(rs.getDouble("stealNum"));
			player.setBlockNum(rs.getDouble("blockNum"));
			player.setFoulNum(rs.getDouble("foulNum"));
			player.setTurnOverNum(rs.getDouble("turnOverNum"));
			player.setScore(rs.getDouble("score"));
			player.setEfficiency(rs.getDouble("efficiency"));
			player.setRecentFiveMatchesScoreUpRate(rs
					.getDouble("recentFiveMatchesScoreUpRate"));
			player.setRecentFiveMatchesReboundUpRate(rs
					.getDouble("recentFiveMatchesReboundUpRate"));
			player.setRecentFiveMatchesAssistUpRate(rs
					.getDouble("recentFiveMatchesAssistUpRate"));
			player.setGmScEfficiencyValue(rs.getDouble("GmScEfficiencyValue"));
			player.setTrueHitRate(rs.getDouble("trueHitRate"));
			player.setShootEfficiency(rs.getDouble("shootEfficiency"));
			player.setReboundRate(rs.getDouble("reboundRate"));
			player.setOffenReboundRate(rs.getDouble("offenReboundRate"));
			player.setDefenReboundRate(rs.getDouble("defenReboundRate"));
			player.setAssistRate(rs.getDouble("assistRate"));
			player.setStealRate(rs.getDouble("stealRate"));
			player.setBlockRate(rs.getDouble("blockRate"));
			player.setTurnOverRate(rs.getDouble("turnOverRate"));
			player.setUsageRate(rs.getDouble("usageRate"));
			player.setScore_rebound_assist(rs.getDouble("score_rebound_assist"));
			player.setDoubleDoubleNum(rs.getDouble("doubleDoubleNum"));
			rs.close();
			con.close();
			sql.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return player;
	}

	/**
	 * 得到球员的某个赛季的技术统计数据
	 * 
	 * @param 赛季
	 * @return
	 */
	public ArrayList<PlayerPO> getPlayerAverageInfo(String season)
			throws RemoteException {
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		PlayerPO player;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			String query = "select * from playermatchdataaverage where season='"
					+ season + "'";
			ResultSet rs = sql.executeQuery(query);
			while (rs.next()) {
				player = new PlayerPO();
				player.setName(rs.getString("playerName"));
				player.setTeamName(rs.getString("owingTeam"));
				player.setPlayedGames(rs.getInt("playedGames"));
				player.setGameStartingNum(rs.getDouble("gameStartingNum"));
				player.setReboundNum(rs.getDouble("reboundNum"));
				player.setAssistNum(rs.getDouble("assistNum"));
				player.setPresentTime(rs.getDouble("presentTime"));
				player.setShootHitRate(rs.getDouble("shootHitRate"));
				player.setThreeHitRate(rs.getDouble("threeHitRate"));
				player.setFreeThrowHitRate(rs.getDouble("freeThrowHitRate"));
				player.setOffenNum(rs.getDouble("offenReboundNum"));
				player.setDefenNum(rs.getDouble("defenReboundNum"));
				player.setStealNum(rs.getDouble("stealNum"));
				player.setBlockNum(rs.getDouble("blockNum"));
				player.setFoulNum(rs.getDouble("foulNum"));
				player.setTurnOverNum(rs.getDouble("turnOverNum"));
				player.setScore(rs.getDouble("score"));
				player.setEfficiency(rs.getDouble("efficiency"));
				player.setRecentFiveMatchesScoreUpRate(rs
						.getDouble("recentFiveMatchesScoreUpRate"));
				player.setRecentFiveMatchesReboundUpRate(rs
						.getDouble("recentFiveMatchesReboundUpRate"));
				player.setRecentFiveMatchesAssistUpRate(rs
						.getDouble("recentFiveMatchesAssistUpRate"));
				player.setGmScEfficiencyValue(rs
						.getDouble("GmScEfficiencyValue"));
				player.setTrueHitRate(rs.getDouble("trueHitRate"));
				player.setShootEfficiency(rs.getDouble("shootEfficiency"));
				player.setReboundRate(rs.getDouble("reboundRate"));
				player.setOffenReboundRate(rs.getDouble("offenReboundRate"));
				player.setDefenReboundRate(rs.getDouble("defenReboundRate"));
				player.setAssistRate(rs.getDouble("assistRate"));
				player.setStealRate(rs.getDouble("stealRate"));
				player.setBlockRate(rs.getDouble("blockRate"));
				player.setTurnOverRate(rs.getDouble("turnOverRate"));
				player.setUsageRate(rs.getDouble("usageRate"));
				player.setScore_rebound_assist(rs
						.getDouble("score_rebound_assist"));
				player.setDoubleDoubleNum(rs.getDouble("doubleDoubleNum"));
				players.add(player);
			}

			rs.close();
			con.close();
			sql.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return players;
	}

	/**
	 * 根据赛季和球员名称返回球员这个赛季的技术数据
	 * 
	 * @param season
	 * @param name
	 * @return
	 */
	public PlayerPO getPlayerAverageInfo(String season, String name)
			throws RemoteException {
		String playername = name.replace("'", "''");
		PlayerPO player = null;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			String query = "select * from playermatchdataaverage where name='"
					+ playername + "' and season='" + season + "'";
			ResultSet rs = sql.executeQuery(query);
			rs.next();
			player = new PlayerPO();
			player.setName(rs.getString("playerName"));
			player.setTeamName(rs.getString("owingTeam"));
			player.setPlayedGames(rs.getInt("playedGames"));
			player.setGameStartingNum(rs.getDouble("gameStartingNum"));
			player.setReboundNum(rs.getDouble("reboundNum"));
			player.setAssistNum(rs.getDouble("assistNum"));
			player.setPresentTime(rs.getDouble("presentTime"));
			player.setShootHitRate(rs.getDouble("shootHitRate"));
			player.setThreeHitRate(rs.getDouble("threeHitRate"));
			player.setFreeThrowHitRate(rs.getDouble("freeThrowHitRate"));
			player.setOffenNum(rs.getDouble("offenReboundNum"));
			player.setDefenNum(rs.getDouble("defenReboundNum"));
			player.setStealNum(rs.getDouble("stealNum"));
			player.setBlockNum(rs.getDouble("blockNum"));
			player.setFoulNum(rs.getDouble("foulNum"));
			player.setTurnOverNum(rs.getDouble("turnOverNum"));
			player.setScore(rs.getDouble("score"));
			player.setEfficiency(rs.getDouble("efficiency"));
			player.setRecentFiveMatchesScoreUpRate(rs
					.getDouble("recentFiveMatchesScoreUpRate"));
			player.setRecentFiveMatchesReboundUpRate(rs
					.getDouble("recentFiveMatchesReboundUpRate"));
			player.setRecentFiveMatchesAssistUpRate(rs
					.getDouble("recentFiveMatchesAssistUpRate"));
			player.setGmScEfficiencyValue(rs.getDouble("GmScEfficiencyValue"));
			player.setTrueHitRate(rs.getDouble("trueHitRate"));
			player.setShootEfficiency(rs.getDouble("shootEfficiency"));
			player.setReboundRate(rs.getDouble("reboundRate"));
			player.setOffenReboundRate(rs.getDouble("offenReboundRate"));
			player.setDefenReboundRate(rs.getDouble("defenReboundRate"));
			player.setAssistRate(rs.getDouble("assistRate"));
			player.setStealRate(rs.getDouble("stealRate"));
			player.setBlockRate(rs.getDouble("blockRate"));
			player.setTurnOverRate(rs.getDouble("turnOverRate"));
			player.setUsageRate(rs.getDouble("usageRate"));
			player.setScore_rebound_assist(rs.getDouble("score_rebound_assist"));
			player.setDoubleDoubleNum(rs.getDouble("doubleDoubleNum"));
			rs.close();
			con.close();
			sql.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return player;
	}

	/**
	 * 根据某一项技术分析项，将球员按某个赛季的该项数据进行升降序排序
	 * 
	 * @param season
	 *            赛季
	 * @param condition
	 *            排序条件，即某一项技术分析项，如：篮板率
	 * @param order
	 *            升序还是降序 asc 表示升序 , desc表示降序 , 未明确写明排序方式时默认是升序
	 * @param num
	 *            筛选出前多少名球员
	 * 
	 * @return 按照所给条件排好序的球队列表
	 */
	public ArrayList<PlayerPO> getOrderedPlayersBySeason(String season,
			String condition, String order, int num) throws RemoteException {
		ArrayList<PlayerPO> result = new ArrayList<PlayerPO>();
		result = getOrderedPlayers("playermatchdataseason", season, condition,
				order, num);
		return result;
	}

	/**
	 * 根据某一项技术分析项，将球员按某个赛季的该项数据进行升降序排序
	 * 
	 * @param season
	 *            赛季
	 * @param condition
	 *            排序条件，即某一项技术分析项，如：篮板率
	 * @param order
	 *            升序还是降序 asc 表示升序 , desc表示降序 , 未明确写明排序方式时默认是升序
	 * 
	 * @return 按照所给条件排好序的球员列表
	 */
	public ArrayList<PlayerPO> getOrderedPlayersByAverage(String season,
			String condition, String order, int num) throws RemoteException {
		ArrayList<PlayerPO> result = new ArrayList<PlayerPO>();
		result = getOrderedPlayers("playermatchdataaverage", season, condition,
				order, num);
		return result;
	}

	private ArrayList<PlayerPO> getOrderedPlayers(String table, String season,
			String condition, String order, int num) {
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		PlayerPO player;
		// 未明确写明排序方式时默认是升序
		if (order == null || order.equals("null")) {
			order = "asc";
		}
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			String query = "select * from " + table + " where season='"
					+ season + "' order by " + condition + " " + order
					+ " limit " + num;
			ResultSet rs = sql.executeQuery(query);
			while (rs.next()) {
				player = new PlayerPO();
				player.setName(rs.getString("playerName"));
				player.setTeamName(rs.getString("owingTeam"));
				player.setPlayedGames(rs.getInt("playedGames"));
				player.setGameStartingNum(rs.getDouble("gameStartingNum"));
				player.setReboundNum(rs.getDouble("reboundNum"));
				player.setAssistNum(rs.getDouble("assistNum"));
				player.setPresentTime(rs.getDouble("presentTime"));
				player.setShootHitRate(rs.getDouble("shootHitRate"));
				player.setThreeHitRate(rs.getDouble("threeHitRate"));
				player.setFreeThrowHitRate(rs.getDouble("freeThrowHitRate"));
				player.setOffenNum(rs.getDouble("offenReboundNum"));
				player.setDefenNum(rs.getDouble("defenReboundNum"));
				player.setStealNum(rs.getDouble("stealNum"));
				player.setBlockNum(rs.getDouble("blockNum"));
				player.setFoulNum(rs.getDouble("foulNum"));
				player.setTurnOverNum(rs.getDouble("turnOverNum"));
				player.setScore(rs.getDouble("score"));
				player.setEfficiency(rs.getDouble("efficiency"));
				player.setRecentFiveMatchesScoreUpRate(rs
						.getDouble("recentFiveMatchesScoreUpRate"));
				player.setRecentFiveMatchesReboundUpRate(rs
						.getDouble("recentFiveMatchesReboundUpRate"));
				player.setRecentFiveMatchesAssistUpRate(rs
						.getDouble("recentFiveMatchesAssistUpRate"));
				player.setGmScEfficiencyValue(rs
						.getDouble("GmScEfficiencyValue"));
				player.setTrueHitRate(rs.getDouble("trueHitRate"));
				player.setShootEfficiency(rs.getDouble("shootEfficiency"));
				player.setReboundRate(rs.getDouble("reboundRate"));
				player.setOffenReboundRate(rs.getDouble("offenReboundRate"));
				player.setDefenReboundRate(rs.getDouble("defenReboundRate"));
				player.setAssistRate(rs.getDouble("assistRate"));
				player.setStealRate(rs.getDouble("stealRate"));
				player.setBlockRate(rs.getDouble("blockRate"));
				player.setTurnOverRate(rs.getDouble("turnOverRate"));
				player.setUsageRate(rs.getDouble("usageRate"));
				player.setScore_rebound_assist(rs
						.getDouble("score_rebound_assist"));
				player.setDoubleDoubleNum(rs.getDouble("doubleDoubleNum"));
				players.add(player);
			}
			rs.close();
			con.close();
			sql.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return players;
	}

	/**
	 * 对某个赛季中的球员信息，根据某一项属性里的值进行筛选
	 * 
	 * @param 赛季
	 * @param 哪一项属性
	 *            ，如"name"
	 * @param 筛选属性的输入值
	 * @return 符合条件的球员列表
	 */
	public ArrayList<PlayerPO> selectPlayersBySeason(String season,
			String position, String union, String column,int num)
			throws RemoteException {
		ArrayList<PlayerPO> players = selectPlayers("playermatchdataseason",
				season, position, union, column,num);
		return players;
	}

	/**
	 * 对某个赛季中的球员信息，根据某一项属性里的值进行筛选
	 * 
	 * @param 赛季
	 * @param 哪一项属性
	 *            ，如"name"
	 * @param 筛选属性的输入值
	 * @return 符合条件的球员列表
	 */
	public ArrayList<PlayerPO> selectPlayersByAverage(String season,
			String position, String union, String column,int num)
			throws RemoteException {
		ArrayList<PlayerPO> players = selectPlayers("playermatchdataaverage",
				season, position, union, column,num);
		return players;
	}

	// F是前锋 C是中锋 G是后卫
	private ArrayList<PlayerPO> selectPlayers(String table, String season,
			String position, String union, String column,int num) {
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		PlayerPO player;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			String query;
			if (position.equals("all") && !union.equals("all")) {
				query = "select *" + " from " + table + " where season='"
						+ season + "' order by " + table + "." + column
						+ " desc limit "+num;
				ResultSet rs = sql.executeQuery(query);
				while (rs.next()) {
					if (isUnionRight(rs.getString("owingTeam"), union)) {
						player = new PlayerPO();
						player.setName(rs.getString("playerName"));
						player.setTeamName(rs.getString("owingTeam"));
						player.setPlayedGames(rs.getInt("playedGames"));
						player.setGameStartingNum(rs
								.getDouble("gameStartingNum"));
						player.setReboundNum(rs.getDouble("reboundNum"));
						player.setAssistNum(rs.getDouble("assistNum"));
						player.setPresentTime(rs.getDouble("presentTime"));
						player.setShootHitRate(rs.getDouble("shootHitRate"));
						player.setThreeHitRate(rs.getDouble("threeHitRate"));
						player.setFreeThrowHitRate(rs
								.getDouble("freeThrowHitRate"));
						player.setOffenNum(rs.getDouble("offenReboundNum"));
						player.setDefenNum(rs.getDouble("defenReboundNum"));
						player.setStealNum(rs.getDouble("stealNum"));
						player.setBlockNum(rs.getDouble("blockNum"));
						player.setFoulNum(rs.getDouble("foulNum"));
						player.setTurnOverNum(rs.getDouble("turnOverNum"));
						player.setScore(rs.getDouble("score"));
						player.setEfficiency(rs.getDouble("efficiency"));
						player.setRecentFiveMatchesScoreUpRate(rs
								.getDouble("recentFiveMatchesScoreUpRate"));
						player.setRecentFiveMatchesReboundUpRate(rs
								.getDouble("recentFiveMatchesReboundUpRate"));
						player.setRecentFiveMatchesAssistUpRate(rs
								.getDouble("recentFiveMatchesAssistUpRate"));
						player.setGmScEfficiencyValue(rs
								.getDouble("GmScEfficiencyValue"));
						player.setTrueHitRate(rs.getDouble("trueHitRate"));
						player.setShootEfficiency(rs
								.getDouble("shootEfficiency"));
						player.setReboundRate(rs.getDouble("reboundRate"));
						player.setOffenReboundRate(rs
								.getDouble("offenReboundRate"));
						player.setDefenReboundRate(rs
								.getDouble("defenReboundRate"));
						player.setAssistRate(rs.getDouble("assistRate"));
						player.setStealRate(rs.getDouble("stealRate"));
						player.setBlockRate(rs.getDouble("blockRate"));
						player.setTurnOverRate(rs.getDouble("turnOverRate"));
						player.setUsageRate(rs.getDouble("usageRate"));
						player.setScore_rebound_assist(rs
								.getDouble("score_rebound_assist"));
						player.setDoubleDoubleNum(rs
								.getDouble("doubleDoubleNum"));
						players.add(player);
					}

				}
				rs.close();
			} else if (union.equals("all") && !position.equals("all")) {
				query = "select players.name,players.position," + table + ".*"
						+ " from players," + table + " where players.name="
						+ table + ".playerName and players.position='"
						+ position + "' and season='" + season + "' order by "
						+ table + "." + column + " desc limit "+num;
				ResultSet rs = sql.executeQuery(query);
				while (rs.next()) {
					player = new PlayerPO();
					player.setPosition(rs.getString("position"));
					player.setName(rs.getString("playerName"));
					player.setTeamName(rs.getString("owingTeam"));
					player.setPlayedGames(rs.getInt("playedGames"));
					player.setGameStartingNum(rs.getDouble("gameStartingNum"));
					player.setReboundNum(rs.getDouble("reboundNum"));
					player.setAssistNum(rs.getDouble("assistNum"));
					player.setPresentTime(rs.getDouble("presentTime"));
					player.setShootHitRate(rs.getDouble("shootHitRate"));
					player.setThreeHitRate(rs.getDouble("threeHitRate"));
					player.setFreeThrowHitRate(rs.getDouble("freeThrowHitRate"));
					player.setOffenNum(rs.getDouble("offenReboundNum"));
					player.setDefenNum(rs.getDouble("defenReboundNum"));
					player.setStealNum(rs.getDouble("stealNum"));
					player.setBlockNum(rs.getDouble("blockNum"));
					player.setFoulNum(rs.getDouble("foulNum"));
					player.setTurnOverNum(rs.getDouble("turnOverNum"));
					player.setScore(rs.getDouble("score"));
					player.setEfficiency(rs.getDouble("efficiency"));
					player.setRecentFiveMatchesScoreUpRate(rs
							.getDouble("recentFiveMatchesScoreUpRate"));
					player.setRecentFiveMatchesReboundUpRate(rs
							.getDouble("recentFiveMatchesReboundUpRate"));
					player.setRecentFiveMatchesAssistUpRate(rs
							.getDouble("recentFiveMatchesAssistUpRate"));
					player.setGmScEfficiencyValue(rs
							.getDouble("GmScEfficiencyValue"));
					player.setTrueHitRate(rs.getDouble("trueHitRate"));
					player.setShootEfficiency(rs.getDouble("shootEfficiency"));
					player.setReboundRate(rs.getDouble("reboundRate"));
					player.setOffenReboundRate(rs.getDouble("offenReboundRate"));
					player.setDefenReboundRate(rs.getDouble("defenReboundRate"));
					player.setAssistRate(rs.getDouble("assistRate"));
					player.setStealRate(rs.getDouble("stealRate"));
					player.setBlockRate(rs.getDouble("blockRate"));
					player.setTurnOverRate(rs.getDouble("turnOverRate"));
					player.setUsageRate(rs.getDouble("usageRate"));
					player.setScore_rebound_assist(rs
							.getDouble("score_rebound_assist"));
					player.setDoubleDoubleNum(rs.getDouble("doubleDoubleNum"));
					players.add(player);
				}
				rs.close();
			} else if (!position.equals("all") && !union.equals("all")) {
				query = "select players.name,players.position," + table + ".*"
						+ " from players," + table + " where players.name="
						+ table + ".playerName and players.position='"
						+ position + "' and season='" + season + "' order by "
						+ table + "." + column + " desc limit "+num;
				ResultSet rs = sql.executeQuery(query);
				while (rs.next()) {
					if (isUnionRight(rs.getString("owingTeam"), union)) {
						player = new PlayerPO();
						player.setPosition(rs.getString("position"));
						player.setName(rs.getString("playerName"));
						player.setTeamName(rs.getString("owingTeam"));
						player.setPlayedGames(rs.getInt("playedGames"));
						player.setGameStartingNum(rs
								.getDouble("gameStartingNum"));
						player.setReboundNum(rs.getDouble("reboundNum"));
						player.setAssistNum(rs.getDouble("assistNum"));
						player.setPresentTime(rs.getDouble("presentTime"));
						player.setShootHitRate(rs.getDouble("shootHitRate"));
						player.setThreeHitRate(rs.getDouble("threeHitRate"));
						player.setFreeThrowHitRate(rs
								.getDouble("freeThrowHitRate"));
						player.setOffenNum(rs.getDouble("offenReboundNum"));
						player.setDefenNum(rs.getDouble("defenReboundNum"));
						player.setStealNum(rs.getDouble("stealNum"));
						player.setBlockNum(rs.getDouble("blockNum"));
						player.setFoulNum(rs.getDouble("foulNum"));
						player.setTurnOverNum(rs.getDouble("turnOverNum"));
						player.setScore(rs.getDouble("score"));
						player.setEfficiency(rs.getDouble("efficiency"));
						player.setRecentFiveMatchesScoreUpRate(rs
								.getDouble("recentFiveMatchesScoreUpRate"));
						player.setRecentFiveMatchesReboundUpRate(rs
								.getDouble("recentFiveMatchesReboundUpRate"));
						player.setRecentFiveMatchesAssistUpRate(rs
								.getDouble("recentFiveMatchesAssistUpRate"));
						player.setGmScEfficiencyValue(rs
								.getDouble("GmScEfficiencyValue"));
						player.setTrueHitRate(rs.getDouble("trueHitRate"));
						player.setShootEfficiency(rs
								.getDouble("shootEfficiency"));
						player.setReboundRate(rs.getDouble("reboundRate"));
						player.setOffenReboundRate(rs
								.getDouble("offenReboundRate"));
						player.setDefenReboundRate(rs
								.getDouble("defenReboundRate"));
						player.setAssistRate(rs.getDouble("assistRate"));
						player.setStealRate(rs.getDouble("stealRate"));
						player.setBlockRate(rs.getDouble("blockRate"));
						player.setTurnOverRate(rs.getDouble("turnOverRate"));
						player.setUsageRate(rs.getDouble("usageRate"));
						player.setScore_rebound_assist(rs
								.getDouble("score_rebound_assist"));
						player.setDoubleDoubleNum(rs
								.getDouble("doubleDoubleNum"));
						players.add(player);
					}

				}
				rs.close();
			} else {
				query = "select players.name," + table + ".*"
						+ " from players," + table + " where players.name="
						+ table + ".playerName and season='" + season
						+ "' order by " + table + "." + column + " desc limit "+num;
				ResultSet rs = sql.executeQuery(query);
				while (rs.next()) {
					player = new PlayerPO();
					player.setName(rs.getString("playerName"));
					player.setTeamName(rs.getString("owingTeam"));
					player.setPlayedGames(rs.getInt("playedGames"));
					player.setGameStartingNum(rs.getDouble("gameStartingNum"));
					player.setReboundNum(rs.getDouble("reboundNum"));
					player.setAssistNum(rs.getDouble("assistNum"));
					player.setPresentTime(rs.getDouble("presentTime"));
					player.setShootHitRate(rs.getDouble("shootHitRate"));
					player.setThreeHitRate(rs.getDouble("threeHitRate"));
					player.setFreeThrowHitRate(rs.getDouble("freeThrowHitRate"));
					player.setOffenNum(rs.getDouble("offenReboundNum"));
					player.setDefenNum(rs.getDouble("defenReboundNum"));
					player.setStealNum(rs.getDouble("stealNum"));
					player.setBlockNum(rs.getDouble("blockNum"));
					player.setFoulNum(rs.getDouble("foulNum"));
					player.setTurnOverNum(rs.getDouble("turnOverNum"));
					player.setScore(rs.getDouble("score"));
					player.setEfficiency(rs.getDouble("efficiency"));
					player.setRecentFiveMatchesScoreUpRate(rs
							.getDouble("recentFiveMatchesScoreUpRate"));
					player.setRecentFiveMatchesReboundUpRate(rs
							.getDouble("recentFiveMatchesReboundUpRate"));
					player.setRecentFiveMatchesAssistUpRate(rs
							.getDouble("recentFiveMatchesAssistUpRate"));
					player.setGmScEfficiencyValue(rs
							.getDouble("GmScEfficiencyValue"));
					player.setTrueHitRate(rs.getDouble("trueHitRate"));
					player.setShootEfficiency(rs.getDouble("shootEfficiency"));
					player.setReboundRate(rs.getDouble("reboundRate"));
					player.setOffenReboundRate(rs.getDouble("offenReboundRate"));
					player.setDefenReboundRate(rs.getDouble("defenReboundRate"));
					player.setAssistRate(rs.getDouble("assistRate"));
					player.setStealRate(rs.getDouble("stealRate"));
					player.setBlockRate(rs.getDouble("blockRate"));
					player.setTurnOverRate(rs.getDouble("turnOverRate"));
					player.setUsageRate(rs.getDouble("usageRate"));
					player.setScore_rebound_assist(rs
							.getDouble("score_rebound_assist"));
					player.setDoubleDoubleNum(rs.getDouble("doubleDoubleNum"));
					players.add(player);
				}
				rs.close();
			}

			con.close();
			sql.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return players;
	}

	/**
	 * 获得球员的全身照路径
	 * 
	 * @param name球员名
	 * @return
	 * @throws RemoteException
	 */
	public String getActionPhotoPath(String name) throws RemoteException {
		return "src/data/players/action/" + name + ".png";
	}

	/**
	 * 获得球员的大头照路径
	 * 
	 * @param name球员名
	 * @return
	 * @throws RemoteException
	 */
	public String getPortraitPhotoPath(String name) throws RemoteException {
		return "src/data/players/portrait/" + name + ".png";
	}

	/**
	 * 获得球员的全身照
	 * 
	 * @param name球员名
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getPlayerActionImage(String name) throws RemoteException {
		String address = getActionPhotoPath(name);
		File file = new File(address);
		if (!file.exists()) {
			address = "src/data/players/lose.jpg";
		}
		ImageIcon imageIcon = new ImageIcon(address);
		return imageIcon;
	}

	/**
	 * 获得球员的大头照路径
	 * 
	 * @param name球员名
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getPlayerPortraitImage(String name) throws RemoteException {
		String address = getPortraitPhotoPath(name);
		File file = new File(address);
		if (!file.exists()) {
			address = "src/data/players/lose.jpg";
		}
		ImageIcon imageIcon = new ImageIcon(address);
		return imageIcon;
	}

	public static void main(String[] args) {
		// Connection con;
		// try {
		// String table = "playermatchdataaverage";
		// String position = "G";
		// String column = "playedGames";
		// con = SqlManager.getConnection();
		// Statement sql = con.createStatement();
		// String query = "select players.name,players.position," + table
		// + ".*" + " from players," + table + " where players.name="
		// + table + ".playerName and players.position like '%"
		// + position + "%' order by " + table + "." + column
		// + " desc";
		// ResultSet rs = sql.executeQuery(query);
		// rs.next();
		// System.out.println(rs.getString("reboundNum"));
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		try {
			Player p = new Player();
			// ArrayList<PlayerPO> pl= p.selectPlayersBySeason("13-14",
			// "G", "E", "playedGames");
			// for(PlayerPO pp:pl){
			// System.out.println(pp.getName()+" "+pp.getPosition());
			// }
			// ArrayList<MatchPO> matches=p.getMatches("Aaron Brooks");
			// System.out.println(matches.get(0).getDate());
			// ArrayList<PlayerPO> players=p.getSeasonHotPlayer("13-14",
			// "score");
			// ArrayList<PlayerPO> players=p.getPlayersByInitialName('A');
			// System.out.println(players.get(0).getName());
			ArrayList<PlayerPO> players = p.selectPlayersByAverage("13-14",
					"all", "all", "assistNum",50);
			System.out.println(players.get(0).getAssistNum());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean isUnionRight(String owingTeam, String union) {
		String[] theTeams = owingTeam.split("  ");
		for (int i = 0; i < theTeams.length; i++) {
			String team = theTeams[i];
			if (team.equals(""))
				return false;
			try {
				con = SqlManager.getConnection();
				Statement sql = con.createStatement();
				String query = "select conference,partition from teams where abLocation='"
						+ team + "'";
				ResultSet rs = sql.executeQuery(query);
				if (rs == null)
					continue;
				rs.next();
				if (union.equals(rs.getString("conference"))
						|| union.equals(rs.getString("partition")))
					return true;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 获得该球员最近5场比赛的数据
	 */
	public ArrayList<MatchPO> getRecentMatches(String playerName)
			throws RemoteException {
		playerName = playerName.replace("'", "''");
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		MatchPO matchPO = null;
		RecordPO recordPO;
		ArrayList<String> detailScores;
		ArrayList<RecordPO> records;
		int matchID = 0;
		String season = "";
		String date = "";
		String visingTeam = "";
		String homeTeam = "";
		int visitingScore = 0;
		int homeScore = 0;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			ResultSet rs = sql
					.executeQuery("select * from records where playerName='"
							+ playerName
							+ "' order by season desc,date desc limit 5");
			while (rs.next()) {
				recordPO = new RecordPO(rs.getString("team"),
						rs.getString("playerName"), rs.getString("position"),
						rs.getString("presentTime"), rs.getInt("shootHitNum"),
						rs.getInt("shootAttemptNum"), rs.getInt("threeHitNum"),
						rs.getInt("threeAttemptNum"),
						rs.getInt("freeThrowHitNum"),
						rs.getInt("freeThrowAttemptNum"),
						rs.getInt("offenReboundNum"),
						rs.getInt("defenReboundNum"), rs.getInt("reboundNum"),
						rs.getInt("assistNum"), rs.getInt("stealNum"),
						rs.getInt("blockNum"), rs.getInt("turnOverNum"),
						rs.getInt("foulNum"), rs.getInt("score"));
				records = new ArrayList<RecordPO>();
				records.add(recordPO);
				matchID = rs.getInt("matchID");
				season = rs.getString("season");
				date = rs.getString("date");
				Statement sql2 = con.createStatement();
				ResultSet rs2 = sql2
						.executeQuery("select * from matches where matchID="
								+ matchID + " limit 1");
				rs2.next();
				visingTeam = rs2.getString("visitingTeam");
				homeTeam = rs2.getString("homeTeam");
				visitingScore = rs2.getInt("visitingScore");
				homeScore = rs2.getInt("homeScore");
				rs2.close();
				sql2.close();
				Statement sql3 = con.createStatement();
				ResultSet rs3 = sql3
						.executeQuery("select * from detailscores where matchID="
								+ matchID);
				detailScores = new ArrayList<String>();
				while (rs3.next()) {
					detailScores.add(rs3.getString("score"));
				}
				rs3.close();
				sql3.close();
				matchPO = new MatchPO(matchID, season, date, visingTeam,
						homeTeam, visitingScore, homeScore, detailScores,
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

	/**
	 * 获得该球员所有比赛的数据
	 */
	public ArrayList<MatchPO> getMatches(String playerName)
			throws RemoteException {
		playerName = playerName.replace("'", "''");
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		MatchPO matchPO = null;
		RecordPO recordPO;
		ArrayList<String> detailScores;
		ArrayList<RecordPO> records;
		int matchID = 0;
		String season = "";
		String date = "";
		String visingTeam = "";
		String homeTeam = "";
		int visitingScore = 0;
		int homeScore = 0;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			ResultSet rs = sql
					.executeQuery("select * from records where playerName='"
							+ playerName + "' order by season desc,date desc");
			while (rs.next()) {
				recordPO = new RecordPO(rs.getString("team"),
						rs.getString("playerName"), rs.getString("position"),
						rs.getString("presentTime"), rs.getInt("shootHitNum"),
						rs.getInt("shootAttemptNum"), rs.getInt("threeHitNum"),
						rs.getInt("threeAttemptNum"),
						rs.getInt("freeThrowHitNum"),
						rs.getInt("freeThrowAttemptNum"),
						rs.getInt("offenReboundNum"),
						rs.getInt("defenReboundNum"), rs.getInt("reboundNum"),
						rs.getInt("assistNum"), rs.getInt("stealNum"),
						rs.getInt("blockNum"), rs.getInt("turnOverNum"),
						rs.getInt("foulNum"), rs.getInt("score"));
				records = new ArrayList<RecordPO>();
				records.add(recordPO);
				matchID = rs.getInt("matchID");
				season = rs.getString("season");
				date = rs.getString("date");
				Statement sql2 = con.createStatement();
				ResultSet rs2 = sql2
						.executeQuery("select * from matches where matchID="
								+ matchID + " limit 1");
				rs2.next();
				visingTeam = rs2.getString("visitingTeam");
				homeTeam = rs2.getString("homeTeam");
				visitingScore = rs2.getInt("visitingScore");
				homeScore = rs2.getInt("homeScore");
				rs2.close();
				sql2.close();
				Statement sql3 = con.createStatement();
				ResultSet rs3 = sql3
						.executeQuery("select * from detailscores where matchID="
								+ matchID);
				detailScores = new ArrayList<String>();
				while (rs3.next()) {
					detailScores.add(rs3.getString("score"));
				}
				rs3.close();
				sql3.close();
				matchPO = new MatchPO(matchID, season, date, visingTeam,
						homeTeam, visitingScore, homeScore, detailScores,
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

	/**
	 * 获得该球员今日比赛的数据
	 */
	public ArrayList<MatchPO> getTodayMatches(String playerName)
			throws RemoteException {
		playerName = playerName.replace("'", "''");
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		MatchPO matchPO = null;
		RecordPO recordPO;
		ArrayList<String> detailScores;
		ArrayList<RecordPO> records;
		int matchID = 0;
		String season = "";
		String date = "";
		String visingTeam = "";
		String homeTeam = "";
		int visitingScore = 0;
		int homeScore = 0;
		getTodaySeason();
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			ResultSet rs = sql
					.executeQuery("select * from records where playerName='"
							+ playerName + "' and season='" + todaySeason
							+ "' and date='" + todayDate + "'");
			while (rs.next()) {
				recordPO = new RecordPO(rs.getString("team"),
						rs.getString("playerName"), rs.getString("position"),
						rs.getString("presentTime"), rs.getInt("shootHitNum"),
						rs.getInt("shootAttemptNum"), rs.getInt("threeHitNum"),
						rs.getInt("threeAttemptNum"),
						rs.getInt("freeThrowHitNum"),
						rs.getInt("freeThrowAttemptNum"),
						rs.getInt("offenReboundNum"),
						rs.getInt("defenReboundNum"), rs.getInt("reboundNum"),
						rs.getInt("assistNum"), rs.getInt("stealNum"),
						rs.getInt("blockNum"), rs.getInt("turnOverNum"),
						rs.getInt("foulNum"), rs.getInt("score"));
				records = new ArrayList<RecordPO>();
				records.add(recordPO);
				matchID = rs.getInt("matchID");
				season = rs.getString("season");
				date = rs.getString("date");
				Statement sql2 = con.createStatement();
				ResultSet rs2 = sql2
						.executeQuery("select * from matches where matchID="
								+ matchID + " limit 1");
				rs2.next();
				visingTeam = rs2.getString("visitingTeam");
				homeTeam = rs2.getString("homeTeam");
				visitingScore = rs2.getInt("visitingScore");
				homeScore = rs2.getInt("homeScore");
				rs2.close();
				sql2.close();
				Statement sql3 = con.createStatement();
				ResultSet rs3 = sql3
						.executeQuery("select * from detailscores where matchID="
								+ matchID);
				detailScores = new ArrayList<String>();
				while (rs3.next()) {
					detailScores.add(rs3.getString("score"));
				}
				rs3.close();
				sql3.close();
				matchPO = new MatchPO(matchID, season, date, visingTeam,
						homeTeam, visitingScore, homeScore, detailScores,
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

	/**
	 * 获得该球员某赛季比赛的数据
	 */
	public ArrayList<MatchPO> getSeasonMatches(String playerName, String season)
			throws RemoteException {
		playerName = playerName.replace("'", "''");
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		MatchPO matchPO = null;
		RecordPO recordPO;
		ArrayList<String> detailScores;
		ArrayList<RecordPO> records;
		int matchID = 0;
		String date = "";
		String visingTeam = "";
		String homeTeam = "";
		int visitingScore = 0;
		int homeScore = 0;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			ResultSet rs = sql
					.executeQuery("select * from records where playerName='"
							+ playerName + "' and season='" + season + "'");
			while (rs.next()) {
				recordPO = new RecordPO(rs.getString("team"),
						rs.getString("playerName"), rs.getString("position"),
						rs.getString("presentTime"), rs.getInt("shootHitNum"),
						rs.getInt("shootAttemptNum"), rs.getInt("threeHitNum"),
						rs.getInt("threeAttemptNum"),
						rs.getInt("freeThrowHitNum"),
						rs.getInt("freeThrowAttemptNum"),
						rs.getInt("offenReboundNum"),
						rs.getInt("defenReboundNum"), rs.getInt("reboundNum"),
						rs.getInt("assistNum"), rs.getInt("stealNum"),
						rs.getInt("blockNum"), rs.getInt("turnOverNum"),
						rs.getInt("foulNum"), rs.getInt("score"));
				records = new ArrayList<RecordPO>();
				records.add(recordPO);
				matchID = rs.getInt("matchID");
				season = rs.getString("season");
				date = rs.getString("date");
				Statement sql2 = con.createStatement();
				ResultSet rs2 = sql2
						.executeQuery("select * from matches where matchID="
								+ matchID + " limit 1");
				rs2.next();
				visingTeam = rs2.getString("visitingTeam");
				homeTeam = rs2.getString("homeTeam");
				visitingScore = rs2.getInt("visitingScore");
				homeScore = rs2.getInt("homeScore");
				rs2.close();
				sql2.close();
				Statement sql3 = con.createStatement();
				ResultSet rs3 = sql3
						.executeQuery("select * from detailscores where matchID="
								+ matchID);
				detailScores = new ArrayList<String>();
				while (rs3.next()) {
					detailScores.add(rs3.getString("score"));
				}
				rs3.close();
				sql3.close();
				matchPO = new MatchPO(matchID, season, date, visingTeam,
						homeTeam, visitingScore, homeScore, detailScores,
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

	/**
	 * 获得当天热点球员
	 */
	public ArrayList<PlayerPO> getDayHotPlayer(String column)
			throws RemoteException {

		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		getTodaySeason();
		PlayerPO playerPO;
		String name;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			ResultSet rs = sql.executeQuery("select playerName," + column
					+ " from records where season='" + todaySeason
					+ "' and date='" + todayDate + "' order by " + column
					+ " desc limit 5");
			while (rs.next()) {
				name = rs.getString("playerName");
				playerPO = getPlayerBaseInfo(name);
				players.add(playerPO);
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

		return players;
	}

	/**
	 * 获得赛季热点球员
	 */
	public ArrayList<PlayerPO> getSeasonHotPlayer(String season, String column)
			throws RemoteException {
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		PlayerPO playerPO;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			String query = "";
			if (column.equals("score") || column.equals("reboundNum")
					|| column.equals("assistNum") || column.equals("blockNum")
					|| column.equals("stealNum")) {
				query = "select * from playerMatchDataAverage where season='"
						+ season + "' order by " + column + " desc limit 5";
			} else {
				query = "select * from playerMatchDataSeason where season='"
						+ season + "' order by " + column + " desc limit 5";
			}
			ResultSet rs = sql.executeQuery(query);
			while (rs.next()) {
				String playName = rs.getString("playerName");
				playerPO = getPlayerBaseInfo(playName);
				players.add(playerPO);
			}
			rs.close();
			sql.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return players;
	}

	/**
	 * 获得进步最快球员
	 */
	public ArrayList<PlayerPO> getBestImprovedPlayer(String column)
			throws RemoteException {
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		PlayerPO playerPO;
		if (column.equals("score")) {
			column = "recentFiveMatchesScoreUpRate";
		} else if (column.equals("reboundNum")) {
			column = "recentFiveMatchesReboundUpRate";
		} else {
			column = "recentFiveMatchesAssistUpRate";
		}
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			String query = "select playerName from playerMatchDataSeason order by "
					+ column + " desc limit 5";

			ResultSet rs = sql.executeQuery(query);
			while (rs.next()) {
				String playName = rs.getString("playerName");
				playerPO = getPlayerBaseInfo(playName);
				players.add(playerPO);
			}
			rs.close();
			sql.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return players;
	}

	/**
	 * 获得以某个首字母开头的球员
	 */
	public ArrayList<PlayerPO> getPlayersByInitialName(char character)
			throws RemoteException {
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		PlayerPO playerPO;
		try {
			con = SqlManager.getConnection();
			Statement sql = con.createStatement();
			ResultSet resultSet = sql
					.executeQuery("Select * from players where name like '"
							+ character + "%' ");
			while (resultSet.next()) {
				int id = resultSet.getInt("playerID");// 编号
				String name = resultSet.getString("name");// 球员名称
				int number = resultSet.getInt("number");// 球衣号码
				String position = resultSet.getString("position");// 位置
				String height = resultSet.getString("height");// 身高（英尺-英存）
				int weight = resultSet.getInt("weight");// 体重（英镑）
				String birth = resultSet.getString("birth");// （月 日，年）
				int age = resultSet.getInt("age");// 年龄
				int exp = resultSet.getInt("exp");// 球龄
				String school = resultSet.getString("school");// 毕业学校

				playerPO = new PlayerPO(id, name, number, position, height,
						weight, birth, age, exp, school);
				players.add(playerPO);
			}
			resultSet.close();
			sql.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return players;
	}

	private String getTodaySeason() {
		String result;
		int year;
		int month;
		Calendar cal = Calendar.getInstance();
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("MM-dd");
		todayDate = f.format(cal.getTime());
		year = cal.get(Calendar.YEAR);
		year -= 2000;
		month = cal.get(Calendar.MONTH);
		if (month <= 6) {
			result = (year - 1) + "-" + year;
		} else {
			result = year + "-" + (year + 1);
		}
		todaySeason = result;

		return result;
	}

}
