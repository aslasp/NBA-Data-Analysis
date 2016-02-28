package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import bl.DataSourse;
import bl.DirtyDataManager;
import bl.FileList;
import vo.MatchVO;
import vo.RecordVO;

public class NewMatch {
	private Map<String, MatchVO> matches = new HashMap<String, MatchVO>();
	private FileList fl;

	public NewMatch() {
		matches = getMatches();
	}

	/**
	 * 从文件中读取每场比赛信息
	 * 
	 * @param fileName
	 *            记录比赛信息的文件名
	 * @return 一个matchVO对象
	 */
	private MatchVO readFromMatchFile(String fileName) {
		MatchVO matchVO;
		String season;// 赛季
		String date = null;// 比赛日期
		String teams = null;// 对阵队伍
		String score = null;// 比分

		ArrayList<String> detailScores = new ArrayList<String>();// 各节比分
		ArrayList<RecordVO> records = new ArrayList<RecordVO>();// 球员比分数据记录

		String tp[] = fileName.split("matches");
		season = tp[1].substring(1, 6);

		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String temp = null;

			temp = br.readLine();
			String[] fisrtContent = temp.split(";");
			date = fisrtContent[0];
			teams = fisrtContent[1];
			score = fisrtContent[2];

			temp = br.readLine();
			String[] scoresData = temp.split(";");
			for (int i = 0; i < scoresData.length; i++) {
				detailScores.add(scoresData[i]);
			}

			String team = null;// 球队
			String playerName = null;// 球员名
			String VOsition = null;// 位置
			String presentTime = null;// 在场时间
			int shootHitNum = 0;// 投篮命中数
			int shootAttemptNum = 0;// 投篮出手数
			int threeHitNum = 0;// 三分命中数
			int threeAttemptNum = 0;// 三分出手数
			int freeThrowHitNum = 0;// 罚球命中数
			int freeThrowAttemptNum = 0;// 罚球出手数
			int offenReboundNum = 0;// 进攻（前场）篮板数
			int defenReboundNum = 0;// 防守（后场）篮板数
			int reboundNum = 0;// 总篮板数
			int assistNum = 0;// 助攻数
			int stealNum = 0;// 抢断数
			int blockNum = 0;// 盖帽数
			int turnOverNum = 0;// 失误数
			int foulNum = 0;// 犯规数
			int personScore = 0;// 个人得分

			boolean isComplete = false;
			temp = br.readLine();
			while (temp != null) {
				if (fileName.contains(temp)) {
					team = temp;
					isComplete = false;
				} else {
					String[] line = temp.split(";");
					playerName = line[0];
					VOsition = line[1];
					presentTime = DirtyDataManager.checkPresentTime(fileName,
							team, playerName, line[2]);// 在场时间
					shootHitNum = Integer.parseInt(line[3]);// 投篮命中数
					shootAttemptNum = DirtyDataManager.checkShootAndHitNum(
							fileName, Integer.parseInt(line[4]), shootHitNum);// 投篮出手数
					threeHitNum = Integer.parseInt(line[5]);// 三分命中数
					threeAttemptNum = DirtyDataManager.checkShootAndHitNum(
							fileName, Integer.parseInt(line[6]), threeHitNum);// 三分出手数
					freeThrowHitNum = Integer.parseInt(line[7]);// 罚球命中数
					freeThrowAttemptNum = DirtyDataManager.checkShootAndHitNum(
							fileName, Integer.parseInt(line[8]),
							freeThrowHitNum);// 罚球出手数
					offenReboundNum = Integer.parseInt(line[9]);// 进攻（前场）篮板数
					defenReboundNum = Integer.parseInt(line[10]);// 防守（后场）篮板数
					reboundNum = DirtyDataManager.checkReboundNum(fileName,
							offenReboundNum, defenReboundNum,
							Integer.parseInt(line[11]));// 总篮板数
					assistNum = Integer.parseInt(line[12]);// 助攻数
					stealNum = Integer.parseInt(line[13]);// 抢断数
					blockNum = Integer.parseInt(line[14]);// 盖帽数
					turnOverNum = Integer.parseInt(line[15]);// 失误数
					foulNum = Integer.parseInt(line[16]);// 犯规数
					personScore = DirtyDataManager.checkPersonScore(fileName,
							line[17], temp);// 个人得分

					isComplete = true;
				}
				if (isComplete) {
					RecordVO recordVO = new RecordVO(team, playerName,
							VOsition, presentTime, shootHitNum,
							shootAttemptNum, threeHitNum, threeAttemptNum,
							freeThrowHitNum, freeThrowAttemptNum,
							offenReboundNum, defenReboundNum, reboundNum,
							assistNum, stealNum, blockNum, turnOverNum,
							foulNum, personScore);
					records.add(recordVO);
					isComplete = false;
				}
				temp = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] temp = teams.split("-");
		String visitingTeam = temp[0];
		String homeTeam = temp[1];

		String[] s = score.split("-");
		int visitingScore = Integer.parseInt(s[0]);
		int homeScore = Integer.parseInt(s[1]);

		matchVO = new MatchVO(season, date, visitingTeam, homeTeam,
				visitingScore, homeScore, detailScores, records);
		return matchVO;
	}

	/**
	 * 得到全部的比赛信息
	 * 
	 * @return 比赛列表
	 */
	private Map<String, MatchVO> getMatches() {
		// TODO 自动生成的方法存根
		Map<String, MatchVO> matches = new HashMap<String, MatchVO>();
		try {
//			fl = new FileList(DataSourse.dataSourse + "/matches", this);
			fl = new FileList(DataSourse.dataSourse + "/matches");
			ArrayList<String> names = fl.getList();
			for (String name : names) {
				String key = getKeyName(name);
				matches.put(key, readFromMatchFile(name));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		// 初始化后 开启线程：
//		updateMatch um = new updateMatch();
//		um.startThread();

		return matches;
	}

	/**
	 * 获得某场比赛的信息，暂不支持模糊查找，需要与界面进行讨论研究
	 * 
	 * @param season
	 *            赛季
	 * @param date
	 *            日期
	 * @param homeTeam
	 *            主队
	 * @param visitingTeam
	 *            客队
	 * @return 比赛的列表
	 */
	public Map<String, MatchVO> getMatchData(String season, String date,
			String homeTeam, String visitingTeam) {
		// TODO 自动生成的方法存根
		Map<String, MatchVO> result = new HashMap<String, MatchVO>();

		if (season.equals("全部") && date.equals("全部") && homeTeam.equals("全部")
				&& visitingTeam.equals("全部")) {
			result = matches;
			return result;
		} else {
			Iterator<Entry<String, MatchVO>> iter = matches.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Map.Entry<String, MatchVO> entry = (Map.Entry<String, MatchVO>) iter
						.next();
				MatchVO vo = (MatchVO) entry.getValue();

				String currentSeason = vo.getSeason();
				String currentDate = vo.getDate();
				String currentHomeTeam = vo.getHomeTeam();
				String currentVisitingTeam = vo.getVisitingTeam();

				boolean checkSeason = true;
				boolean checkDate = true;
				boolean checkHomeTeam = true;
				boolean checkVisitingTeam = true;
				if (!season.equals("全部")) {
					checkSeason = currentSeason.equals(season);
				}
				if (!date.equals("全部")) {
					checkDate = currentDate.equals(date);
				}
				if (!homeTeam.equals("全部")) {
					checkHomeTeam = currentHomeTeam.equals(homeTeam);
				}
				if (!visitingTeam.equals("全部")) {
					checkVisitingTeam = currentVisitingTeam
							.equals(visitingTeam);
				}

				if (checkSeason && checkDate && checkHomeTeam
						&& checkVisitingTeam) {
					String key = (String) entry.getKey();
					result.put(key, vo);
				}

			}
		}
		return result;
	}

	/**
	 * 向match中增加比赛信息
	 * 
	 * @param newName
	 *            动态增加的比赛的文件名
	 */
	public void add(ArrayList<String> newName) {
		for (String str : newName) {
			matches.put(getKeyName(str), readFromMatchFile(str));
		}
	}

	private String getKeyName(String fileName) {
		String result = null;
		String[] tp = fileName.split("matches");
		result = tp[1].substring(1);
		return result;
	}

	// 放在Filelist或Match里均可
	class updateMatch extends Thread {
		boolean stop;

		public updateMatch() {
			stop = false;
		}

		public void run() {
			while (!stop) {
				fl.checkChange();
//				System.out.println("我在很认真的检查呀，港荣蒸蛋糕真的好吃，字打错了灭");
				try {
					this.sleep(2000);// 话说 能不能不睡
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public void startThread() {
			this.start();
		}

		public void stopThead() {
			this.stop = true;
		}
	}
}
