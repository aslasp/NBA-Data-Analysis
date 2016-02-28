package businesslogic;

import java.io.File;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import po.MatchPO;
import po.PlayerPO;
import vo.MatchVO;
import vo.PlayerVO;
import businesslogicservice.PlayerBLService;
import dataservice.PlayerDataService;

public class Player implements PlayerBLService {
	private PlayerDataService service;

	public Player() {
		try {
			String host = GetServer.getServerHost();
			String url = "rmi://" + host + "/playerService";
			service = (PlayerDataService) Naming.lookup(url);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 得到球员信息
	 * 
	 * @return 返回ArrayList<PlayerVO>
	 */
	public ArrayList<PlayerVO> getPlayerBaseInfo() {
		// TODO 自动生成的方法存根
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			ArrayList<PlayerPO> data = service.getPlayerBaseInfo();

			for (PlayerPO po : data) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}

		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<PlayerVO> getPlayerSeasonInfo(String season) {
		// TODO 自动生成的方法存根
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			ArrayList<PlayerPO> data = service.getPlayerSeasonInfo(season);

			for (PlayerPO po : data) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}

		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<PlayerVO> getPlayerAverageInfo(String season) {
		// TODO 自动生成的方法存根
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			ArrayList<PlayerPO> data = service.getPlayerAverageInfo(season);

			for (PlayerPO po : data) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}

		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	public PlayerVO getPlayerBaseInfo(String name) {
		// TODO 自动生成的方法存根
		PlayerVO result = null;
		try {
			PlayerPO po = service.getPlayerBaseInfo(name);
			result = poTovo(po);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	public PlayerVO getPlayerSeasonInfo(String season, String name) {
		// TODO 自动生成的方法存根
		PlayerVO result = null;
		try {
			PlayerPO po = service.getPlayerSeasonInfo(season, name);
			result = poTovo(po);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	public PlayerVO getPlayerAverageInfo(String season, String name) {
		// TODO 自动生成的方法存根
		PlayerVO result = null;
		try {
			PlayerPO po = service.getPlayerAverageInfo(season, name);
			result = poTovo(po);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<PlayerVO> getOrderedPlayersBySeason(String season,
			String condition, String order,int num) throws RemoteException {
		// TODO 自动生成的方法存根
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			ArrayList<PlayerPO> data = service.getOrderedPlayersBySeason(
					season, condition, order,num);

			for (PlayerPO po : data) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}

		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<PlayerVO> getOrderedPlayersByAverage(String season,
			String condition, String order,int num) {
		// TODO 自动生成的方法存根
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			ArrayList<PlayerPO> data = service.getOrderedPlayersByAverage(
					season, condition, order,num);

			for (PlayerPO po : data) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}

		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<PlayerVO> selectPlayersBySeason(String season,
			String position, String union, String column,int num) {
		// TODO 自动生成的方法存根
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			position = changePositionCHToEN(position);
			union = changeUnionCHToEN(union);
			column = changeColumnCHToEN(column);
			ArrayList<PlayerPO> data = service.selectPlayersBySeason(season,
					position, union, column,num);

			for (PlayerPO po : data) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}

		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<PlayerVO> selectPlayersByAverage(String season,
			String position, String union, String column,int num) {
		// TODO 自动生成的方法存根
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			position = changePositionCHToEN(position);
			union = changeUnionCHToEN(union);
			column = changeColumnCHToEN(column);
			ArrayList<PlayerPO> data = service.selectPlayersByAverage(season,
					position, union, column,num);

			for (PlayerPO po : data) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}

		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获得球员的全身照路径
	 * 
	 * @param name球员名
	 * @return
	 * @throws RemoteException
	 */
	public String getActionPhotoPath(String name) {
		return "src/data/players/action/" + name + ".png";
	}

	/**
	 * 获得球员的大头照路径
	 * 
	 * @param name球员名
	 * @return
	 * @throws RemoteException
	 */
	public String getPortraitPhotoPath(String name) {
		return "src/data/players/portrait/" + name + ".png";
	}

	/**
	 * 获得球员的大头照路径
	 * 
	 * @param name球员名
	 * @return
	 */
	public ImageIcon getPlayerPortraitImage(String name) {
		String address = getPortraitPhotoPath(name);
		File file = new File(address);
		if (!file.exists()) {
			address = "src/data/players/lose.jpg";
		}
		ImageIcon imageIcon = new ImageIcon(address);
		return imageIcon;
	}

	/**
	 * 获得球员的全身照
	 * 
	 * @param name球员名
	 * @return
	 */
	public ImageIcon getPlayerActionImage(String name) {
		String address = getActionPhotoPath(name);
		File file = new File(address);
		if (!file.exists()) {
			address = "src/data/players/lose.jpg";
		}
		ImageIcon imageIcon = new ImageIcon(address);
		return imageIcon;
	}

	

	// public ImageIcon getPlayerPortraitImage(String name) {
	// ImageIcon icon = null;
	// try {
	// icon = service.getPlayerPortraitImage(name);
	// } catch (RemoteException e) {
	// // TODO 自动生成的 catch 块
	// e.printStackTrace();
	// }
	// return icon;
	// }
	//
	// public ImageIcon getPlayerActionImage(String name) {
	// ImageIcon icon = null;
	// try {
	// icon = service.getPlayerActionImage(name);
	// } catch (RemoteException e) {
	// // TODO 自动生成的 catch 块
	// e.printStackTrace();
	// }
	// return icon;
	// }

	public static PlayerVO poTovo(PlayerPO po) {
		int exp = po.getExp();
		String expString = "";
		if (exp > 0) {
			expString = String.valueOf(exp);
		} else if (exp == 0) {
			expString = "R";
		}
		PlayerVO vo = new PlayerVO(po.getId(), po.getName(), po.getNumber(),
				po.getPosition(), po.getHeight(), po.getWeight(),
				po.getBirth(), po.getAge(), expString, po.getSchool(),
				po.getTeamName(), po.getPlayedGames(), po.getGameStartingNum(),
				po.getReboundNum(), po.getAssistNum(), changeSecondToMinute(po.getPresentTime()),
				po.getShootHitRate(), po.getThreeHitRate(),
				po.getFreeThrowHitRate(), po.getOffenNum(), po.getDefenNum(),
				po.getStealNum(), po.getBlockNum(), po.getTurnOverNum(),
				po.getFoulNum(), po.getScore(), po.getEfficiency(),
				po.getRecentFiveMatchesScoreUpRate(),
				po.getRecentFiveMatchesReboundUpRate(),
				po.getGmScEfficiencyValue(), po.getTrueHitRate(),
				po.getShootEfficiency(), po.getReboundRate(),
				po.getOffenReboundRate(), po.getDefenReboundRate(),
				po.getAssistRate(), po.getStealRate(), po.getBlockRate(),
				po.getTurnOverRate(), po.getUsageRate(),
				po.getScore_rebound_assist(), po.getDoubleDoubleNum());
		return vo;
	}

	/**
	 * 将table的列的中文名转换为对应的英文名，便于数据库查找
	 * 
	 * @param CH
	 *            table列的中文名
	 * @return 对应的英文名
	 */
	private String changeColumnCHToEN(String CH) {
		String EN = null;
		if (CH.equals("得分")) {
			EN = "score";
		} else if (CH.equals("篮板")) {
			EN = "reboundNum";
		} else if (CH.equals("助攻")) {
			EN = "assistNum";
		} else if (CH.equals("得分/篮板/助攻(1:1:1)")) {
			EN = "score_rebound_assist";
		} else if (CH.equals("盖帽")) {
			EN = "blockNum";
		} else if (CH.equals("抢断")) {
			EN = "stealNum";
		} else if (CH.equals("犯规")) {
			EN = "foulNum";
		} else if (CH.equals("失误")) {
			EN = "turnOverNum";
		} else if (CH.equals("分钟")) {
			EN = "presentTime";
		} else if (CH.equals("效率")) {
			EN = "efficiency";
		} else if (CH.equals("投篮")) {
			EN = "shootHitRate";
		} else if (CH.equals("三分")) {
			EN = "threeHitRate";
		} else if (CH.equals("罚球")) {
			EN = "freeThrowHitRate";
		} else if (CH.equals("两双")) {
			EN = "doubleDoubleNum";
		} else if (CH.equals("赛季")) {
			EN = "season";
		} else if (CH.equals("场均")) {
			EN = "average";
		} else {
			EN = CH;
		}
		return EN;
	}

	/**
	 * 将球员位置中文转为英文便于数据库查找
	 * 
	 * @param CH
	 *            位置的中文名
	 * @return 对应的英文
	 */
	private String changePositionCHToEN(String CH) {
		String EN = null;
		if (CH.equals("全部")) {
			EN = "all";
		} else if (CH.equals("前锋")) {
			EN = "F";
		} else if (CH.equals("中锋")) {
			EN = "C";
		} else if (CH.equals("后卫")) {
			EN = "G";
		} else {
			EN = CH;
		}
		return EN;
	}

	/**
	 * 将球员联盟中文转为英文便于数据库查找
	 * 
	 * @param CH
	 *            球员联盟
	 * @return 对应的英文
	 */
	private String changeUnionCHToEN(String CH) {

		// "全部", "西部球队", "西北分区", "太平洋分区", "西南分区", "东部球队",
		// "大西洋分区", "中央分区", "东南分区"
		String EN = null;
		if (CH.equals("全部")) {
			EN = "all";
		} else if (CH.equals("西部球队")) {
			EN = "W";
		} else if (CH.equals("西北分区")) {
			EN = "Northwest";
		} else if (CH.equals("太平洋分区")) {
			EN = "Pacific";
		} else if (CH.equals("西南分区")) {
			EN = "Southwest";
		} else if (CH.equals("东部球队")) {
			EN = "E";
		} else if (CH.equals("大西洋分区")) {
			EN = "Atlantic";
		} else if (CH.equals("中央分区")) {
			EN = "Central";
		} else if (CH.equals("东南分区")) {
			EN = "Southeast";
		} else  {
			EN = CH;
		}
		return EN;
	}
	
	/**
	 * 将 以秒为单位的在场时间 改为 以分钟为单位的在场时间
	 * 
	 * @param second 秒
	 * @return 对应的分钟
	 */
	private static String changeSecondToMinute(double second){
		int kansecond=(int)second;
		String result="";
		int minute=kansecond/60;
		int thesecond=kansecond%60;
		result=minute+":"+thesecond;
		return result;
	}

	public ArrayList<PlayerVO> getDayHotPlayer(String column)
			throws RemoteException {
		// TODO 自动生成的方法存根
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			players = service.getDayHotPlayer(column);
			for (PlayerPO po : players) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<PlayerVO> getSeasonHotPlayer(String season, String column) {
		// TODO 自动生成的方法存根
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			players = service.getSeasonHotPlayer(season, column);
			for (PlayerPO po : players) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<PlayerVO> getBestImprovedPlayer(String column) {
		// TODO 自动生成的方法存根
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			players = service.getBestImprovedPlayer(column);
			for (PlayerPO po : players) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<PlayerVO> getPlayersByInitialName(char character) {
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		ArrayList<PlayerVO> result = new ArrayList<PlayerVO>();
		try {
			players = service.getPlayersByInitialName(character);
			for (PlayerPO po : players) {
				PlayerVO vo = poTovo(po);
				result.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<MatchVO> getRecentMatches(String playerName)
			throws RemoteException {
		// TODO 自动生成的方法存根
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		try {
			matches = service.getRecentMatches(playerName);
			for (MatchPO po : matches) {
				MatchVO vo = Match.poToVo(po);
				result.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<MatchVO> getTodayMatches(String playerName) {
		// TODO 自动生成的方法存根
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		try {
			matches = service.getTodayMatches(playerName);
			for (MatchPO po : matches) {
				MatchVO vo = Match.poToVo(po);
				result.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<MatchVO> getMatches(String playerName) {
		// TODO 自动生成的方法存根
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		try {
			matches = service.getMatches(playerName);
			for (MatchPO po : matches) {
				MatchVO vo = Match.poToVo(po);
				result.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// /**
	// * 将球员的位置由英文转为中文
	// * @param position
	// * @return
	// */
	// private String changePositionToCH(String position){
	// String CH="";
	// if (position.equals("F")) {
	// CH = "前锋";
	// } else if (position.equals("C")) {
	// CH = "中锋";
	// } else if (position.equals("G")) {
	// CH = "后卫";
	// } else {
	// CH = "";
	// }
	// return CH;
	// }
}
