package businesslogic;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import po.MatchPO;
import po.TeamPO;
import vo.MatchVO;
import vo.TeamVO;
import businesslogicservice.TeamBLService;
import dataservice.TeamDataService;

public class Team implements TeamBLService {
	private TeamDataService service = null;

	public Team() {
		try {
			// String host = "localhost";
			String host = GetServer.getServerHost();
			String url = "rmi://" + host + "/teamService";
			service = (TeamDataService) Naming.lookup(url);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 查找球队基本信息
	 * 
	 * @return 返回球队的ArrayList<TeamVO>
	 */
	public ArrayList<TeamVO> getTeamBaseInfo() {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		try {
			ArrayList<TeamPO> teams = service.getTeamBaseInfo();
			for (TeamPO po : teams) {
				TeamVO vo = poToVo(po);
				result.add(vo);
			}
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查找球队某个赛季的技术数据
	 * 
	 * @param season
	 *            赛季
	 */
	public ArrayList<TeamVO> getTeamSeasonInfo(String season) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		season = changeColumnCHToEN(season);
		try {
			ArrayList<TeamPO> teams = service.getTeamSeasonInfo(season);
			for (TeamPO po : teams) {
				TeamVO vo = poToVo(po);
				result.add(vo);
			}
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查找球队某个赛季的场均技术数据
	 * 
	 * @param season
	 *            赛季
	 */
	public ArrayList<TeamVO> getTeamAverageInfo(String season) {
		// TODO 自动生成的方法存根
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		season = changeColumnCHToEN(season);
		try {
			ArrayList<TeamPO> teams = service.getTeamAverageInfo(season);
			for (TeamPO po : teams) {
				TeamVO vo = poToVo(po);
				result.add(vo);
			}
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查找某个球队的基本信息
	 * 
	 * @param name
	 *            队名
	 */
	public TeamVO getTeamBaseInfo(String name) {
		// TODO 自动生成的方法存根
		TeamVO teamVO = null;
		try {
			name = changeTeamNameCHToEN(name);
			TeamPO po = service.getTeamBaseInfo(name);
			teamVO = poToVo(po);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return teamVO;
	}

	/**
	 * 查找某个球队某个赛季的技术数据
	 * 
	 * @param season
	 *            赛季
	 * @param name
	 *            球队名
	 */
	public TeamVO getTeamSeasonInfo(String season, String name) {
		// TODO 自动生成的方法存根
		TeamVO teamVO = null;
		season = changeColumnCHToEN(season);
		try {
			name = changeTeamNameCHToEN(name);
			TeamPO po = service.getTeamSeasonInfo(season, name);
			teamVO = poToVo(po);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return teamVO;
	}

	/**
	 * 查找某个球队某个赛季的场均技术数据
	 * 
	 * @param season
	 *            赛季
	 * @param name
	 *            球队名
	 */
	public TeamVO getTeamAverageInfo(String season, String name) {
		// TODO 自动生成的方法存根
		TeamVO teamVO = null;
		season = changeColumnCHToEN(season);
		try {
			name = changeTeamNameCHToEN(name);
			TeamPO po = service.getTeamAverageInfo(season, name);
			teamVO = poToVo(po);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return teamVO;
	}

	/**
	 * 根据赛季得到按某项技术属性排序的球队列表
	 * 
	 * @param season
	 *            赛季
	 * @param condition
	 *            技术属性
	 * @param order
	 *            排序顺序 asc升序 desc降序
	 * @return 球队列表
	 */
	public ArrayList<TeamVO> getOrderedTeamsBySeason(String season,
			String condition, String order) {
		// TODO 自动生成的方法存根
		season = changeColumnCHToEN(season);
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		try {
			ArrayList<TeamPO> teams = service.getOrderedTeamsBySeason(season,
					condition, order);
			for (TeamPO po : teams) {
				TeamVO vo = poToVo(po);
				result.add(vo);
			}
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据赛季得到按某项技术属性场均数据排序的球队列表
	 * 
	 * @param season
	 *            赛季
	 * @param condition
	 *            技术属性
	 * @param order
	 *            排序顺序 asc升序 desc降序
	 * @return 球队列表
	 */
	public ArrayList<TeamVO> getOrderedTeamsByAverage(String season,
			String condition, String order) {
		// TODO 自动生成的方法存根
		season = changeColumnCHToEN(season);
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		try {
			ArrayList<TeamPO> teams = service.getOrderedTeamsByAverage(season,
					condition, order);
			for (TeamPO po : teams) {
				TeamVO vo = poToVo(po);
				result.add(vo);
			}
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 得到球队的logo
	 * 
	 * @param name
	 *            球队缩写,允许是中文名
	 * @return 球队logo
	 */
	public ImageIcon getTeamImage(String name) {
		ImageIcon icon = null;
		try {
			name = changeTeamNameCHToEN(name);
			icon = service.getTeamImage(name);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return icon;
	}

	public static TeamVO poToVo(TeamPO po) {
		TeamVO teamVO = new TeamVO(po.getId(), po.getTeamName(),
				po.getAbLocation(), po.getLocation(), po.getConference(),
				po.getPartition(), po.getHomeCourt(), po.getSetUpTime(),
				po.getMatchesNum(), po.getShootHitNum(),
				po.getShootAttemptNum(), po.getThreeHitNum(),
				po.getThreeAttemptNum(), po.getFreeThrowHitNum(),
				po.getFreeThrowAttemptNum(), po.getOffenReboundNum(),
				po.getDefenReboundNum(), po.getReboundNum(), po.getAssistNum(),
				po.getStealNum(), po.getBlockNum(), po.getTurnOverNum(),
				po.getFoulNum(), po.getScore(), po.getShootHitRate(),
				po.getThreeHitRate(), po.getFreeThrowHitRate(),
				po.getWinRate(), po.getOffenRound(), po.getOffenEfficiency(),
				po.getDefenEfficiency(), po.getOffenReboundEfficiency(),
				po.getDefenReboundEfficiency(), po.getStealEfficiency(),
				po.getAssistRate());
		return teamVO;
	}

	/**
	 * 得到某支球队的最近五场比赛
	 * 
	 * @param teamName
	 *            球队缩写，允许是中文名
	 * @return 该球队的最近五场比赛列表
	 */
	public ArrayList<MatchVO> getRecentMatches(String teamName) {
		// TODO 自动生成的方法存根
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		try {
			teamName = changeTeamNameCHToEN(teamName);
			matches = service.getRecentMatches(teamName);
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

	/**
	 * 得到某支球队的比赛
	 * 
	 * @param teamName
	 *            球队缩写，允许是中文名
	 * @return 该球队的比赛列表
	 */
	public ArrayList<MatchVO> getMatches(String teamName) {
		// TODO 自动生成的方法存根
		ArrayList<MatchPO> matches = new ArrayList<MatchPO>();
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();
		try {
			teamName = changeTeamNameCHToEN(teamName);
			matches = service.getMatches(teamName);
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

	/**
	 * 根据某一技术属性得到某赛季的热点球队
	 * 
	 * @param season
	 *            赛季
	 * @param column
	 *            技术属性，允许是中文
	 * @return 热点球队的列表
	 */
	public ArrayList<TeamVO> getSeasonHotTeam(String season, String column) {
		// TODO 自动生成的方法存根
		ArrayList<TeamPO> teams = new ArrayList<TeamPO>();
		ArrayList<TeamVO> result = new ArrayList<TeamVO>();
		try {
			teams = service.getSeasonHotTeam(season, column);
			for (TeamPO po : teams) {
				TeamVO vo = poToVo(po);
				result.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	private String changeColumnCHToEN(String CH) {
		String EN = null;
		if (CH.equals("赛季")) {
			EN = "season";
		} else if (CH.equals("场均")) {
			EN = "average";
		} else {
			EN = CH;
		}
		return EN;
	}

	/**
	 * 将球队中文名转为相应的英文缩写
	 * 
	 * @param CH
	 *            球队中文名
	 * @return 英文缩写
	 */
	public static String changeTeamNameCHToEN(String CH) {
		String EN = "";
		if (CH.equals("太阳")) {
			EN = "PHX";
		} else if (CH.equals("马刺")) {
			EN = "SAS";
		} else if (CH.equals("国王")) {
			EN = "SAC";
		} else if (CH.equals("火箭")) {
			EN = "HOU";
		} else if (CH.equals("湖人")) {
			EN = "LAL";
		} else if (CH.equals("掘金")) {
			EN = "DEN";
		} else if (CH.equals("森林狼")) {
			EN = "MIN";
		} else if (CH.equals("小牛")) {
			EN = "DAL";
		} else if (CH.equals("快船")) {
			EN = "LAC";
		} else if (CH.equals("爵士")) {
			EN = "UTA";
		} else if (CH.equals("勇士")) {
			EN = "GSW";
		} else if (CH.equals("灰熊")) {
			EN = "MEM";
		} else if (CH.equals("雷霆")) {
			EN = "OKC";
		} else if (CH.equals("鹈鹕")) {
			EN = "NOP";
		} else if (CH.equals("开拓者")) {
			EN = "POR";
		} else if (CH.equals("凯尔特人")) {
			EN = "BOS";
		} else if (CH.equals("篮网")) {
			EN = "BKN";
		} else if (CH.equals("尼克斯")) {
			EN = "NYK";
		} else if (CH.equals("76人")) {
			EN = "PHI";
		} else if (CH.equals("猛龙")) {
			EN = "TOR";
		} else if (CH.equals("公牛")) {
			EN = "CHI";
		} else if (CH.equals("骑士")) {
			EN = "CLE";
		} else if (CH.equals("活塞")) {
			EN = "DET";
		} else if (CH.equals("步行者")) {
			EN = "IND";
		} else if (CH.equals("雄鹿")) {
			EN = "MIL";
		} else if (CH.equals("老鹰")) {
			EN = "ATL";
		} else if (CH.equals("黄蜂")) {
			EN = "CHA";
		} else if (CH.equals("热火")) {
			EN = "MIA";
		} else if (CH.equals("魔术")) {
			EN = "ORL";
		} else if (CH.equals("奇才")) {
			EN = "WAS";
		} else {
			EN = CH;
		}

		return EN;
	}

	/**
	 * 根据球队名称得到该球队的球员姓名列表
	 * 
	 * @param teamAbLocation
	 *            球队缩写，允许是中文名称
	 * @return 球员姓名列表
	 */
	public ArrayList<String> getPlayersByTeam(String teamAbLocation) {
		// TODO 自动生成的方法存根
		ArrayList<String> result = new ArrayList<String>();
		try {
			teamAbLocation = changeTeamNameCHToEN(teamAbLocation);
			result = service.getPlayersByTeam(teamAbLocation);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

}
