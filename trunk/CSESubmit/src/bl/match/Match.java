package bl.match;

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

import vo.MatchVO;
import vo.RecordVO;
import bl.DataSourse;
import bl.DirtyDataManager;
import bl.FileList;

public class Match {
	private Map<String, Map<String, MatchVO>> matches = new HashMap<String, Map<String, MatchVO>>();
	private FileList fl;

	public static void main(String[] args) {
		System.out.println("fdsaq");
		new Match();
		System.out.println("fdsa");
	}
	
	public Match() {
		matches = getMatches();
	}

	/**
	 * 浠庢枃浠朵腑璇诲彇姣忓満姣旇禌淇℃伅
	 * 
	 * @param fileName
	 *            璁板綍姣旇禌淇℃伅鐨勬枃浠跺悕
	 * @return 涓�涓猰atchVO瀵硅薄
	 */
	private MatchVO readFromMatchFile(String fileName) {
		MatchVO matchVO;
		String season;// 璧涘
		String date = null;// 姣旇禌鏃ユ湡
		String teams = null;// 瀵归樀闃熶紞
		String score = null;// 姣斿垎

		ArrayList<String> detailScores = new ArrayList<String>();// 鍚勮妭姣斿垎
		ArrayList<RecordVO> records = new ArrayList<RecordVO>();// 鐞冨憳姣斿垎鏁版嵁璁板綍

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

			String team = null;// 鐞冮槦
			String playerName = null;// 鐞冨憳鍚�
			String VOsition = null;// 浣嶇疆
			String presentTime = null;// 鍦ㄥ満鏃堕棿
			int shootHitNum = 0;// 鎶曠鍛戒腑鏁�
			int shootAttemptNum = 0;// 鎶曠鍑烘墜鏁�
			int threeHitNum = 0;// 涓夊垎鍛戒腑鏁�
			int threeAttemptNum = 0;// 涓夊垎鍑烘墜鏁�
			int freeThrowHitNum = 0;// 缃氱悆鍛戒腑鏁�
			int freeThrowAttemptNum = 0;// 缃氱悆鍑烘墜鏁�
			int offenReboundNum = 0;// 杩涙敾锛堝墠鍦猴級绡澘鏁�
			int defenReboundNum = 0;// 闃插畧锛堝悗鍦猴級绡澘鏁�
			int reboundNum = 0;// 鎬荤鏉挎暟
			int assistNum = 0;// 鍔╂敾鏁�
			int stealNum = 0;// 鎶㈡柇鏁�
			int blockNum = 0;// 鐩栧附鏁�
			int turnOverNum = 0;// 澶辫鏁�
			int foulNum = 0;// 鐘鏁�
			int personScore = 0;// 涓汉寰楀垎

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
							team, playerName, line[2]);// 鍦ㄥ満鏃堕棿
					shootHitNum = Integer.parseInt(line[3]);// 鎶曠鍛戒腑鏁�
					shootAttemptNum = DirtyDataManager.checkShootAndHitNum(
							fileName, Integer.parseInt(line[4]), shootHitNum);// 鎶曠鍑烘墜鏁�
					threeHitNum = Integer.parseInt(line[5]);// 涓夊垎鍛戒腑鏁�
					threeAttemptNum = DirtyDataManager.checkShootAndHitNum(
							fileName, Integer.parseInt(line[6]), threeHitNum);// 涓夊垎鍑烘墜鏁�
					freeThrowHitNum = Integer.parseInt(line[7]);// 缃氱悆鍛戒腑鏁�
					freeThrowAttemptNum = DirtyDataManager.checkShootAndHitNum(
							fileName, Integer.parseInt(line[8]),
							freeThrowHitNum);// 缃氱悆鍑烘墜鏁�
					offenReboundNum = Integer.parseInt(line[9]);// 杩涙敾锛堝墠鍦猴級绡澘鏁�
					defenReboundNum = Integer.parseInt(line[10]);// 闃插畧锛堝悗鍦猴級绡澘鏁�
					reboundNum = DirtyDataManager.checkReboundNum(fileName,
							offenReboundNum, defenReboundNum,
							Integer.parseInt(line[11]));// 鎬荤鏉挎暟
					assistNum = Integer.parseInt(line[12]);// 鍔╂敾鏁�
					stealNum = Integer.parseInt(line[13]);// 鎶㈡柇鏁�
					blockNum = Integer.parseInt(line[14]);// 鐩栧附鏁�
					turnOverNum = Integer.parseInt(line[15]);// 澶辫鏁�
					foulNum = Integer.parseInt(line[16]);// 鐘鏁�
					personScore = DirtyDataManager.checkPersonScore(fileName,
							line[17], temp);// 涓汉寰楀垎

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
	 * 寰楀埌鍏ㄩ儴鐨勬瘮璧涗俊鎭�
	 * 
	 * @return 姣旇禌鍒楄〃
	 */
	private Map<String, Map<String, MatchVO>> getMatches() {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		Map<String, Map<String, MatchVO>> matches = new HashMap<String, Map<String, MatchVO>>();
		try {
			// fl = new FileList(DataSourse.dataSourse + "/matches", this);
			fl = new FileList(DataSourse.dataSourse + "/matches", this);
			ArrayList<String> names = fl.getList();
			for (String name : names) {
//				DirtyDataManager.calculateMatchTime(name);
				String key = getKeyName(name);
				String season = getSeason(name);
				Map<String, MatchVO> test = matches.get(season);
				if (test == null) {
					Map<String, MatchVO> map = new HashMap<String, MatchVO>();
					map.put(key, readFromMatchFile(name));
					matches.put(season, map);
				} else {
					test.put(key, readFromMatchFile(name));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 鍒濆鍖栧悗 寮�鍚嚎绋嬶細
		 updateMatch um = new updateMatch();
		 um.startThread();

		return matches;
	}

	/**
	 * 寰楀埌鍏ㄩ儴姣旇禌鏁版嵁
	 * 
	 * @return 鎸夎禌瀛ｅ垎map锛岄噷闈㈠啀濂椾竴涓猰ap璁板綍杩欎釜璧涘鐨勬瘮璧�
	 */
	public Map<String, Map<String, MatchVO>> getAllMatches() {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		return matches;

	}

	/**
	 * 鑾峰緱鏌愬満姣旇禌鐨勪俊鎭紝鏆備笉鏀寔妯＄硦鏌ユ壘锛岄渶瑕佷笌鐣岄潰杩涜璁ㄨ鐮旂┒
	 * 
	 * @param season
	 *            璧涘
	 * @param date
	 *            鏃ユ湡
	 * @param homeTeam
	 *            涓婚槦
	 * @param visitingTeam
	 *            瀹㈤槦
	 * @return 姣旇禌鐨勫垪琛�
	 */
	public ArrayList<MatchVO> getMatchData(String season, String date,
			String homeTeam, String visitingTeam) {
		Map<String, Map<String, MatchVO>> mapResult = new HashMap<String, Map<String, MatchVO>>();
		ArrayList<MatchVO> result = new ArrayList<MatchVO>();

		if (season.equals("鍏ㄩ儴") && date.equals("鍏ㄩ儴") && homeTeam.equals("鍏ㄩ儴")
				&& visitingTeam.equals("鍏ㄩ儴")) {
			mapResult = matches;
		} else {
			Iterator<Entry<String, Map<String, MatchVO>>> iter = matches
					.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Map<String, MatchVO>> entry = (Map.Entry<String, Map<String, MatchVO>>) iter
						.next();
				String seasonKey = (String) entry.getKey();
				Map<String, MatchVO> temp = new HashMap<String, MatchVO>();

				Map<String, MatchVO> map = (Map<String, MatchVO>) entry
						.getValue();

				Iterator<Entry<String, MatchVO>> matchIterator = map.entrySet()
						.iterator();
				while (matchIterator.hasNext()) {
					Map.Entry<String, MatchVO> matchEntry = (Map.Entry<String, MatchVO>) matchIterator
							.next();

					MatchVO vo = (MatchVO) matchEntry.getValue();
					String currentSeason = vo.getSeason();
					String currentDate = vo.getDate();
					String currentHomeTeam = vo.getHomeTeam();
					String currentVisitingTeam = vo.getVisitingTeam();

					boolean checkSeason = true;
					boolean checkDate = true;
					boolean checkHomeTeam = true;
					boolean checkVisitingTeam = true;
					if (!season.equals("鍏ㄩ儴")) {
						checkSeason = currentSeason.equals(season);
					}
					if (!date.equals("鍏ㄩ儴")) {
						checkDate = currentDate.equals(date);
					}
					if (!homeTeam.equals("鍏ㄩ儴")) {
						checkHomeTeam = currentHomeTeam.equals(homeTeam);
					}
					if (!visitingTeam.equals("鍏ㄩ儴")) {
						checkVisitingTeam = currentVisitingTeam
								.equals(visitingTeam);
					}

					if (checkSeason && checkDate && checkHomeTeam
							&& checkVisitingTeam) {
						String key = (String) matchEntry.getKey();
						temp.put(key, vo);
					}
				}
				mapResult.put(seasonKey, temp);
			}
		}
		// change to ArrayList
		Iterator<Entry<String, Map<String, MatchVO>>> iter = mapResult
				.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Map<String, MatchVO>> entry = (Map.Entry<String, Map<String, MatchVO>>) iter
					.next();
			Map<String, MatchVO> map = (Map<String, MatchVO>) entry.getValue();

			Iterator<Entry<String, MatchVO>> matchIterator = map.entrySet()
					.iterator();
			while (matchIterator.hasNext()) {
				Map.Entry<String, MatchVO> matchEntry = (Map.Entry<String, MatchVO>) matchIterator
						.next();
				MatchVO vo = (MatchVO) matchEntry.getValue();
				result.add(vo);
			}
		}

		return result;
	}

	/**
	 * 鍚憁atch涓鍔犳瘮璧涗俊鎭�
	 * 
	 * @param newName
	 *            鍔ㄦ�佸鍔犵殑姣旇禌鐨勬枃浠跺悕
	 */
	public void add(ArrayList<String> newName) {
		for (String str : newName) {
			String key = getKeyName(str);
			String season = getSeason(str);
			Map<String, MatchVO> test = matches.get(season);
			if (test == null) {
				Map<String, MatchVO> map = new HashMap<String, MatchVO>();
				map.put(key, readFromMatchFile(str));
				matches.put(season, map);
			} else {
				test.put(key, readFromMatchFile(str));
			}
		}
	}

	private String getKeyName(String fileName) {
		String result = null;
		String[] tp = fileName.split("matches");
		result = tp[1].substring(1);
		return result;
	}

	private String getSeason(String fileName) {
		String result = null;
		String[] tp = fileName.split("_");
		result = tp[0].split("matches")[1].substring(1);
		return result;
	}

	// 鏀惧湪Filelist鎴朚atch閲屽潎鍙�
	class updateMatch extends Thread {
		boolean stop;

		public updateMatch() {
			stop = false;
		}

		public void run() {
			while (!stop) {
				fl.checkChange();
				// System.out.println("鎴戝湪寰堣鐪熺殑妫�鏌ュ憖锛屾腐鑽ｈ捀铔嬬硶鐪熺殑濂藉悆锛屽瓧鎵撻敊浜嗙伃");
				try {
					this.sleep(2000);// 璇濊 鑳戒笉鑳戒笉鐫�
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
