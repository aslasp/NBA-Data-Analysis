package com.nba;

import java.io.PrintStream;
import java.util.ArrayList;

import test.data.PlayerHighInfo;
import test.data.PlayerHotInfo;
import test.data.PlayerKingInfo;
import test.data.PlayerNormalInfo;
import test.data.TeamHighInfo;
import test.data.TeamHotInfo;
import test.data.TeamNormalInfo;
import vo.PlayerVO;
import vo.TeamVO;
import bl.AgeEnum;
import bl.DataSourse;
import bl.player.NewPlayer;
import bl.team.NewTeam;

public class Console {

	private String[] player_sort = { "point", "rebound", "assist", "blockShot",
			"steal", "foul", "fault", "minute", "efficient", "shot", "three",
			"penalty", "doubleTwo", "realShot", "GmSc", "shotEfficient",
			"reboundEfficient", "offendReboundEfficient",
			"defendReboundEfficient", "assistEfficent", "stealEfficient",
			"blockShotEfficient", "faultEfficient", "frequency" };
	private String[] player_real_sort = { "score", "reboundNum", "assistNum",
			"blockNum", "stealNum", "foulNum", "turnOverNum", "presentTime",
			"efficiency", "shootHitRate", "threeHitRate", "freeThrowHitRate",
			"doubleDoubleNum", "trueHitRate", "GmScEfficiencyValue",
			"shootEfficiency", "reboundRate", "offenReboundRate",
			"defenReboundRate", "assistRate", "stealRate", "blockRate",
			"turnOverRate", "usageRate" };

	private String[] team_sort = { "point", "rebound", "assist", "blockShot",
			"steal", "foul", "fault", "shot", "three", "penalty",
			"defendRebound", "offendRebound", "offendRound", "offendEfficient",
			"defendEfficient", "offendReboundEfficient",
			"defendReboundEfficient", "stealEfficeint", "assistEfficient" };
	private String[] team_sort_real = { "score", "reboundNum", "assistNum",
			"blockNum", "stealNum", "foulNum", "turnOverNum", "shootHitRate",
			"threeHitRate", "freeThrowHitRate", "defenReboundNum",
			"offenReboundNum", "offenRound", "offenEfficiency",
			"defenEfficiency", "offenReboundEfficiency",
			"defenReboundEfficiency", "stealEfficiency", "assistRate" };

	private boolean isPTotal = false;
	private boolean isTTotal = false;
	private String playerhotField = "";
	private String teamhotField = "";
	private String playerkingField = "";
	private boolean isSeason = false;
	private int teamNum = 30;
	private int playerNum = 50;
	private boolean isPHigh = false;// 是否返回高阶数据
	private boolean isTHigh = false;
	private AgeEnum pAge = AgeEnum.ALL;
	private String pUnion = "All";
	private String pPosition = "All";
	// 为清晰命令 ，低阶高阶排序 存储分开
	private ArrayList<String> playerBaseSort = new ArrayList<String>();//
	private ArrayList<String> playerHighSort = new ArrayList<String>();
	private ArrayList<String> sortP = new ArrayList<String>();
	private ArrayList<String> sortHP = new ArrayList<String>();
	private ArrayList<String> playerFilter = new ArrayList<String>();// 优先级
																		// field.value
	private ArrayList<String> teamBaseSort = new ArrayList<String>();// aas.dsec
	private ArrayList<String> sortT = new ArrayList<String>();// 可以不声明
	private ArrayList<String> teamHighSort = new ArrayList<String>();
	private ArrayList<String> sortHT = new ArrayList<String>();

	NewPlayer player = new NewPlayer();
	NewTeam team = new NewTeam();
	private boolean defaultPSort = true, defaultTSort = true;

	public Console() {
		playerBaseSort.add("score");
		playerHighSort.add("trueHitRate");
		sortP.add("desc");
		sortHP.add("desc");
		teamBaseSort.add("score");
		teamHighSort.add("winRate");
		sortT.add("desc");
		sortHT.add("desc");
	}

	public void execute(PrintStream out, String[] args) {

		ArrayList<String> command = new ArrayList<String>();
		for (int i = 1; i < args.length; i++)
			command.add(args[i]);
		if (args[0].equals("-player")) {
			// 球队命令解析
			if (command.size() == 0) {
				ArrayList<PlayerVO> result = player.getOrderedPlayersByAverage(
						playerBaseSort, sortP, 50);
				for (PlayerVO vo : result) {
					// =====================================================
					PlayerNormalInfo playerNormalInfo = setPlayerNormalInfo(vo);
					out.println(playerNormalInfo.toString());
				}
				return;// 返回得分前50的球员的场均比赛数据 调用
			}
			int i = 0;
			if ((i = command.indexOf("-n")) >= 0)
				playerNum = Integer.parseInt(command.get(i + 1));
			else
				playerNum = 50;

			if ((i = command.indexOf("-hot")) >= 0) {
				playerhotField = command.get(i + 1);
				PlayerHotFieldChange();
				// 调用 player hot 方法
				ArrayList<PlayerVO> result = null;
				if (playerhotField.equals("score")) {
					result = player.getBestImprovedPlayer(
							"recentFiveMatchesScoreUpRate", playerNum);
				} else if (playerhotField.equals("rebound")) {
					result = player.getBestImprovedPlayer(
							"recentFiveMatchesReboundUpRate", playerNum);
				} else {
					result = player.getBestImprovedPlayer(
							"recentFiveMatchesAssistUpRate", playerNum);
				}

				for (PlayerVO vo : result) {
					// =====================================================
					PlayerHotInfo playerHotInfo = setplayerHotInfo(vo,
							playerhotField);
					out.println(playerHotInfo.toString());
				}
			} else if ((i = command.indexOf("-king")) >= 0) {
				playerkingField = command.get(i + 1);
				if (command.get(i + 2).equals("-season"))
					isSeason = true;
				else
					isSeason = false;
				PlayerKingFieldChange();
				// 调用返回数据王信息
				ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
				if (isSeason) {
					result = player.getSeasonHotPlayer("all", playerkingField,
							playerNum);
					for (PlayerVO vo : result) {
						// =====================================================
						PlayerKingInfo playerKingInfo = setPlayerKingInfo(vo,
								playerkingField);
						out.println(playerKingInfo.toString());
					}
				} else {
					result = player.getDayHotPlayer(playerkingField, playerNum);
					for (PlayerVO vo : result) {
						// =====================================================
						PlayerKingInfo playerKingInfo = setPlayerKingInfo(vo,
								playerkingField);
						out.println(playerKingInfo.toString());
					}
				}
			} else {
				// contain all
				if (command.indexOf("-high") >= 0) {
					isPHigh = true;
					// 返回高阶数据的 sort方法 其他返回均未基本数据
				} else
					isPHigh = false;

				playerBaseSort.clear();
				playerHighSort.clear();
				sortP.clear();
				sortHP.clear();// 冗余 为减少if else 消耗一点时间0.几毫秒左右
				if ((i = command.indexOf("-sort")) >= 0) {
					defaultPSort = false;
					String[] t = command.get(i + 1).split(",");
					for (String s : t) {
						String[] p = s.split("\\.");
						playerBaseSort.add(p[0]);
						sortP.add(p[1]);
					}
				} else {
					defaultTSort = true;
					playerBaseSort.add("score");
					playerHighSort.add("trueHitRate");
					sortP.add("desc");
					sortHP.add("desc");
				}

				if (command.indexOf("-total") >= 0)
					// 返回的是总和数据
					isPTotal = true;
				else
					isPTotal = false;
				PlayerSortFieldChange();
				if ((!defaultPSort) && isPHigh) {
					playerHighSort = playerBaseSort;
					sortHP = sortP;
				}
				if ((i = command.indexOf("-filter")) > 0) {
					playerFilter.clear();
					String[] t = command.get(i + 1).split(",");
					for (String s : t)
						playerFilter.add(s);
					PalyerFilterChange();
					// 调用sort+filter方法
					ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
					if (isPTotal) {
						if (isPHigh == true) {
							result = player.selectPlayersBySeason("all",
									pPosition, pUnion, pAge, playerHighSort,
									sortHP, playerNum);
						} else {
							result = player.selectPlayersBySeason("all",
									pPosition, pUnion, pAge, playerBaseSort,
									sortP, playerNum);
						}
						for (PlayerVO vo : result) {
							// =====================================================
							PlayerNormalInfo playerNormalInfo = setPlayerNormalInfo(vo);
							out.println(playerNormalInfo.toString());
						}
					} else {
						if (isPHigh == true) {
							result = player.selectPlayersByAverage(pPosition,
									pUnion, pAge, playerHighSort, sortHP,
									playerNum);
						} else {
							result = player.selectPlayersByAverage(pPosition,
									pUnion, pAge, playerBaseSort, sortP,
									playerNum);
						}
						for (PlayerVO vo : result) {
							// =====================================================
							PlayerNormalInfo playerNormalInfo = setPlayerNormalInfo(vo);
							out.println(playerNormalInfo.toString());
						}
					}
					clearFilter();
				} else {
					// 调用sort方法
					if (isPTotal) {
						ArrayList<PlayerVO> result;
						if (isPHigh == true) {
							// season命令中未给出
							result = player.getOrderedPlayersBySeason("13-14",
									playerHighSort, sortHP, playerNum);
							// result = player.selectPlayersBySeason("all",
							// pPosition,
							// pUnion, pAge,
							// playerSort.get(1),sortP.get(1),playerNum);
						} else {
							result = player.getOrderedPlayersBySeason("13-14",
									playerBaseSort, sortP, playerNum);
							// result = player.selectPlayersBySeason("all",
							// pPosition,
							// pUnion, pAge,
							// playerSort.get(0),sortP.get(0),playerNum);
						}

						for (PlayerVO vo : result) {
							// =====================================================
							if (isPHigh) {
								PlayerHighInfo playerHighInfo = setPlayerHighInfo(vo);
								out.println(playerHighInfo.toString());
							} else {
								PlayerNormalInfo playerNormalInfo = setPlayerNormalInfo(vo);
								out.println(playerNormalInfo.toString());
							}
						}
					} else {
						ArrayList<PlayerVO> result;
						if (isPHigh == true) {
							result = player.getOrderedPlayersByAverage(
									playerHighSort, sortHP, playerNum);

						} else {
							result = player.getOrderedPlayersByAverage(
									playerBaseSort, sortP, playerNum);

						}

						for (PlayerVO vo : result) {
							// =====================================================
							if (isPHigh) {
								PlayerHighInfo playerHighInfo = setPlayerHighInfo(vo);
								out.println(playerHighInfo.toString());
							} else {
								PlayerNormalInfo playerNormalInfo = setPlayerNormalInfo(vo);
								out.println(playerNormalInfo.toString());
							}
						}
					}
				}
			}
		} else if (args[0].equals("-team")) {
			// 球员命令解析
			if (command.size() == 0) {
				ArrayList<TeamVO> result = team.getOrderedTeamsByAverage(
						teamBaseSort, sortT, 30);
				for (TeamVO vo : result) {
					// ===================================================
					TeamNormalInfo teamNormalInfo = setTeamNormalInfo(vo);
					out.println(teamNormalInfo.toString());
				}
				return;// 返回得分前30的球队的场均比赛数据
			}
			int i = 0;
			if ((i = command.indexOf("-n")) >= 0)
				teamNum = Integer.parseInt(command.get(i + 1));
			else
				teamNum = 30;

			if ((i = command.indexOf("-hot")) >= 0) {
				teamhotField = command.get(i + 1);
				TeamHotFieldChange();
				// 调用 team hot 方法
				ArrayList<TeamVO> result = team.getSeasonHotTeam("all",
						teamhotField, teamNum);
				for (TeamVO vo : result) {
					// ===================================================
					TeamHotInfo teamHotInfo = setTeamHotInfo(vo, teamhotField);
					out.println(teamHotInfo.toString());
				}
			} else {
				// contain all
				teamBaseSort.clear();
				sortT.clear();
				teamHighSort.clear();
				sortHT.clear();
				if ((i = command.indexOf("-sort")) >= 0) {
					defaultTSort = false;
					String[] t = command.get(i + 1).split(",");
					for (String s : t) {
						String[] p = s.split("\\.");
						teamBaseSort.add(p[0]);
						sortT.add(p[1]);
					}

				} else {
					defaultTSort = true;
					teamBaseSort.add("score");
					teamHighSort.add("winRate");
					sortT.add("desc");
					sortHT.add("desc");
				}
				if (command.indexOf("-high") >= 0) {
					isTHigh = true;
					// 返回高阶数据的 sort方法 其他返回均未基本数据
				} else
					isTHigh = false;

				if (command.indexOf("-total") >= 0)
					// 返回的是总和数据
					isTTotal = true;
				else
					isTTotal = false;
				TeamSortFieldChange();
				if ((!defaultTSort) && isTHigh) {
					sortHT = sortT;
					teamHighSort = teamBaseSort;
				}
				// 调用 sort方法
				ArrayList<TeamVO> result = new ArrayList<TeamVO>();
				if (isTTotal) {
					if (isTHigh)
						result = team.getOrderedTeamsBySeason("13-14",
								teamHighSort, sortHT, teamNum);
					else
						result = team.getOrderedTeamsBySeason("13-14",
								teamBaseSort, sortT, teamNum);
					for (TeamVO vo : result) {
						// ===================================================
						if (isTHigh) {
							TeamHighInfo teamHighInfo = setTeamHighInfo(vo);
							out.println(teamHighInfo.toString());
						} else {
							TeamNormalInfo teamNormalInfo = setTeamNormalInfo(vo);
							out.println(teamNormalInfo.toString());
						}
					}
				} else {
					if (isTHigh)
						result = team.getOrderedTeamsByAverage(teamHighSort,
								sortHT, teamNum);
					else
						result = team.getOrderedTeamsByAverage(teamBaseSort,
								sortT, teamNum);
					for (TeamVO vo : result) {
						// ===================================================
						if (isTHigh) {
							TeamHighInfo teamHighInfo = setTeamHighInfo(vo);
							out.println(teamHighInfo.toString());
						} else {
							TeamNormalInfo teamNormalInfo = setTeamNormalInfo(vo);
							out.println(teamNormalInfo.toString());
						}
					}
				}
			}
		} else {
			// 设置数据源
			setData(args[1]);
		}

	}

	public void PlayerHotFieldChange() {
		if (!playerhotField.equals("score"))
			playerhotField += "Num";
	}

	public void TeamHotFieldChange() {
		for (int i = 1; i < 12; i++)
			if (teamhotField.equals(team_sort[i])) {
				teamhotField = team_sort_real[i];
				break;
			}
	}

	public void PlayerKingFieldChange() {
		if (!playerkingField.equals("score"))
			playerkingField += "Num";
	}

	public void PlayerSortFieldChange() {
		for (int i = 0; i < playerBaseSort.size(); i++) {
			String s = playerBaseSort.get(i);
			for (int j = 0; j < player_sort.length; j++)
				if (s.equals(player_sort[j])) {
					playerBaseSort.set(i, player_real_sort[j]);
					break;
				}
		}
	}

	public void TeamSortFieldChange() {
		for (int i = 0; i < teamBaseSort.size(); i++) {
			String s = teamBaseSort.get(i);
			for (int j = 0; j < team_sort.length; j++)
				if (s.equals(team_sort[j])) {
					teamBaseSort.set(i, team_sort_real[j]);
					break;
				}
		}
	}

	public void PalyerFilterChange() {
		String age;
		for (String s : playerFilter) {
			if (s.contains("position"))
				pPosition = s.split("\\.")[1];
			else if (s.contains("league")) {
				pUnion = s.split("\\.")[1];
				if (pUnion.equals("West")) {
					pUnion = "W";
				} else {
					pUnion = "E";
				}
			} else {
				age = s.split("\\.")[1];
				if (age.contains("<=22"))
					pAge = AgeEnum.LE22;
				else if (age.contains("<=25"))
					pAge = AgeEnum.M22_LE25;
				else if (age.contains("<=30"))
					pAge = AgeEnum.M25_LE30;
				else if (age.contains(">30"))
					pAge = AgeEnum.M30;
				else
					pAge = AgeEnum.ALL;

			}
		}

	}

	public void clearFilter() {
		pPosition = pUnion = "All";
		pAge = AgeEnum.ALL;
	}

	/**
	 * 设置数据源地址
	 * 
	 * @param address
	 */
	private void setData(String address) {
		DataSourse.dataSourse = address;
	}

	private PlayerNormalInfo setPlayerNormalInfo(PlayerVO vo) {
		PlayerNormalInfo playerNormalInfo = new PlayerNormalInfo();
		playerNormalInfo.setAge(vo.getAge());
		playerNormalInfo.setAssist(vo.getAssistNum());
		playerNormalInfo.setBlockShot(vo.getBlockNum());
		playerNormalInfo.setDefend(vo.getDefenReboundNum());
		playerNormalInfo.setEfficiency(vo.getEfficiency());
		playerNormalInfo.setFault(vo.getTurnOverNum());
		playerNormalInfo.setFoul(vo.getFoulNum());
		playerNormalInfo.setMinute(vo.getPresentTime() / (double) 60);
		playerNormalInfo.setName(vo.getName());
		playerNormalInfo.setNumOfGame(vo.getPlayedGames());
		playerNormalInfo.setOffend(vo.getOffenReboundNum());
		playerNormalInfo.setPenalty(vo.getFreeThrowHitRate());
		playerNormalInfo.setPoint(vo.getScore());
		playerNormalInfo.setRebound(vo.getReboundNum());
		playerNormalInfo.setShot(vo.getShootHitRate());
		playerNormalInfo.setStart(vo.getGameStartingNum());
		playerNormalInfo.setSteal(vo.getStealNum());
		playerNormalInfo.setTeamName(vo.getOwingTeam());
		playerNormalInfo.setThree(vo.getThreeHitRate());

		return playerNormalInfo;
	}

	// @倩倩
	private PlayerHighInfo setPlayerHighInfo(PlayerVO vo) {
		PlayerHighInfo playerHighInfo = new PlayerHighInfo();
		playerHighInfo.setAssistEfficient(vo.getAssistRate());
		playerHighInfo.setBlockShotEfficient(vo.getBlockRate());
		playerHighInfo.setDefendReboundEfficient(vo.getDefenReboundRate());
		playerHighInfo.setFaultEfficient(vo.getTurnOverRate());
		playerHighInfo.setFrequency(vo.getUsageRate());
		playerHighInfo.setGmSc(vo.getGmScEfficiencyValue());
		// ===============球员联盟是个什么鬼@倩倩
		playerHighInfo.setLeague(vo.getLeague());
		playerHighInfo.setName(vo.getName());
		playerHighInfo.setOffendReboundEfficient(vo.getOffenReboundRate());
		playerHighInfo.setPosition(vo.getPosition());
		playerHighInfo.setRealShot(vo.getTrueHitRate());
		playerHighInfo.setReboundEfficient(vo.getReboundRate());
		playerHighInfo.setShotEfficient(vo.getShootHitEfficiency());
		playerHighInfo.setStealEfficient(vo.getStealRate());
		playerHighInfo.setTeamName(vo.getOwingTeam());
		return playerHighInfo;
	}

	private PlayerHotInfo setplayerHotInfo(PlayerVO vo, String field) {
		PlayerHotInfo playerHotInfo = new PlayerHotInfo();
		playerHotInfo.setField(field);
		playerHotInfo.setName(vo.getName());
		playerHotInfo.setPosition(vo.getPosition());
		playerHotInfo.setTeamName(vo.getOwingTeam());
		double upgradeRate = 0;
		double value = 0;
		if (field.equals("score")) {
			upgradeRate = vo.getRecentFiveMatchesScoreUpRate();
			value = vo.getScore();
		} else if (field.equals("reboundNum")) {
			upgradeRate = vo.getRecentFiveMatchesReboundUpRate();
			value = vo.getReboundNum();
		} else if (field.equals("assistNum")) {
			upgradeRate = vo.getRecentFiveMatchesAssistUpRate();
			value = vo.getAssistNum();
		}
		playerHotInfo.setUpgradeRate(upgradeRate);
		playerHotInfo.setValue(value);
		return playerHotInfo;
	}

	private PlayerKingInfo setPlayerKingInfo(PlayerVO vo, String field) {
		PlayerKingInfo playerKingInfo = new PlayerKingInfo();
		playerKingInfo.setField(field);
		playerKingInfo.setName(vo.getName());
		playerKingInfo.setPosition(vo.getPosition());
		playerKingInfo.setTeamName(vo.getOwingTeam());
		double value = 0;
		if (field.equals("score")) {
			value = vo.getScore();
		} else if (field.equals("reboundNum")) {
			value = vo.getReboundNum();
		} else if (field.equals("assistNum")) {
			value = vo.getAssistNum();
		}
		playerKingInfo.setValue(value);
		return playerKingInfo;
	}

	private TeamNormalInfo setTeamNormalInfo(TeamVO vo) {
		TeamNormalInfo teamNormalInfo = new TeamNormalInfo();
		teamNormalInfo.setAssist(vo.getAssistNum());
		teamNormalInfo.setBlockShot(vo.getBlockNum());
		teamNormalInfo.setDefendRebound(vo.getDefenReboundNum());
		teamNormalInfo.setFault(vo.getTurnOverNum());
		teamNormalInfo.setFoul(vo.getFoulNum());
		teamNormalInfo.setNumOfGame(vo.getMatchesNum());
		teamNormalInfo.setOffendRebound(vo.getOffenReboundNum());
		teamNormalInfo.setPenalty(vo.getFreeThrowHitRate());
		teamNormalInfo.setPoint(vo.getScore());
		teamNormalInfo.setRebound(vo.getReboundNum());
		teamNormalInfo.setShot(vo.getShootHitRate());
		teamNormalInfo.setSteal(vo.getStealNum());
		teamNormalInfo.setTeamName(vo.getAbLocation());
		teamNormalInfo.setThree(vo.getThreeHitRate());
		return teamNormalInfo;
	}

	private TeamHighInfo setTeamHighInfo(TeamVO vo) {
		TeamHighInfo teamHighInfo = new TeamHighInfo();
		teamHighInfo.setAssistEfficient(vo.getAssistRate());
		teamHighInfo.setDefendEfficient(vo.getDefenEfficiency());
		teamHighInfo.setDefendReboundEfficient(vo.getDefenReboundEfficiency());
		teamHighInfo.setOffendEfficient(vo.getOffenEfficiency());
		teamHighInfo.setOffendReboundEfficient(vo.getOffenReboundEfficiency());
		teamHighInfo.setOffendRound(vo.getOffenRound());
		teamHighInfo.setStealEfficient(vo.getStealEfficiency());
		teamHighInfo.setTeamName(vo.getAbLocation());
		teamHighInfo.setWinRate(vo.getWinRate());
		return teamHighInfo;
	}

	private TeamHotInfo setTeamHotInfo(TeamVO vo, String hotField) {
		TeamHotInfo teamHotInfo = new TeamHotInfo();
		teamHotInfo.setLeague(vo.getConference());
		teamHotInfo.setTeamName(vo.getAbLocation());
		teamHotInfo.setField(hotField);
		double value = 0;

		if (hotField.equals("score")) {
			value = vo.getScore();
		} else if (hotField.equals("reboundNum")) {
			value = vo.getReboundNum();
		} else if (hotField.equals("assistNum")) {
			value = vo.getAssistNum();
		} else if (hotField.equals("blockNum")) {
			value = vo.getBlockNum();
		} else if (hotField.equals("stealNum")) {
			value = vo.getStealNum();
		} else if (hotField.equals("foulNum")) {
			value = vo.getFoulNum();
		} else if (hotField.equals("turnOverNum")) {
			value = vo.getTurnOverNum();
		} else if (hotField.equals("shootHitRate")) {
			value = vo.getShootHitRate();
		} else if (hotField.equals("threeHitRate")) {
			value = vo.getThreeHitRate();
		} else if (hotField.equals("freeThrowHitRate")) {
			value = vo.getFreeThrowHitRate();
		} else if (hotField.equals("defenReboundNum")) {
			value = vo.getDefenReboundNum();
		} else if (hotField.equals("offendReboundNum")) {
			value = vo.getOffenReboundNum();
		}
		teamHotInfo.setValue(value);

		return teamHotInfo;
	}

}
