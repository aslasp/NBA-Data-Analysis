package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;


public class NewPlayer {
	
	Map<String,PlayerVO> players = new HashMap<String,PlayerVO>(32);
	ArrayList<TeamVO> teams;
	Map<Integer,MatchVO> matches=new HashMap<Integer,MatchVO>(1024);
//	Map<String, ArrayList<MatchVO>> allSeasonMatches=new HashMap<String, ArrayList<MatchVO>>();
	
	/**
	 * 
	 * @param flag    
	 * flag=1   看球员的基本信息
	 * flag=2   看球员的赛季信息
	 * flag=3   看球员的场均信息
	 */
	public NewPlayer(int flag){

		if(flag==0){
			baseInfoInit();
		}
		else if(flag==2){
			
		}
		
		else{
			long start = System.currentTimeMillis();
			baseInfoInit();
		    allMatchInfoInit();
		    long end = System.currentTimeMillis();
		    System.out.println("运行时间：" + (end - start) + "毫秒");//应该是end - start
			
		}
	}
	
	public NewPlayer(String season){
		seasonMatchInfoInit(season);
	}
	
	private void baseInfoInit(){
	
		try{					
			FileList fl = new FileList("src/data/players/info");
			ArrayList<String> names = fl.getList();
	      for(String name:names){
	    	  readBaseInfoFromFile(name);
	      }
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	private PlayerVO readBaseInfoFromFile(String fileName) {
		PlayerVO player;
		String name;
		String number;
		String position;
		String height;
		int weight;
		String birth;
		int age;
		int exp;
		String school;
		String[] content = new String[9];
		try {
			File file = new File(fileName);
			if (!file.exists()) {

				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			int i = 0;
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				// System.out.println(temp);
				if (temp.contains("│")) {
					String[] it = temp.split("│");
					String[] nit = it[1].split("║");
					content[i++] = nit[0].trim();
				}
				temp = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		name = content[0];
		number = content[1];
		position = content[2];
		height = content[3];
		weight = Integer.parseInt(content[4]);
		birth = content[5];
		age = Integer.parseInt(content[6]);
		exp = DirtyDataManager.checkExp(content[7]);
		school = content[8];
		player = new PlayerVO(name, number, position, height, weight,birth, age, exp, school);
		players.put(name,player);
		return player;
	}
	
	private void seasonMatchInfoInit(String season){

		try{					
			FileList fl = new FileList("src/data/matches");
			ArrayList<String> names = fl.getList();
			int matchCount=0;
	      for(String name:names){
	    	  String[] buffer=name.split("_");
	    	  if(buffer[0].equals(season)){
	    	     readMatchInfoFromFile(name,matchCount);
	    	     matchCount++;
	    	  }
	      }
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void allMatchInfoInit(){

		try{					
			FileList fl = new FileList("src/data/matches");
			ArrayList<String> names = fl.getList();
			int matchCount=0;
	      for(String name:names){
	    	  readMatchInfoFromFile(name,matchCount);
	    	  matchCount++;
	      }
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readMatchInfoFromFile(String fileName,int matchCount){
		String season;// 赛季
		String date = null;// 比赛日期
		String teams = null;// 对阵队伍
//		ArrayList<RecordVO> records = new ArrayList<RecordVO>();// 球员比分数据记录

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
			
			String[] teamstemp = teams.split("-");
			String visitingTeam = teamstemp[0];
			String homeTeam = teamstemp[1];
	

			temp = br.readLine();
			String[] scoresData = temp.split(";");
			int partNum=scoresData.length;
			MatchVO thisMatch=new MatchVO(season, date, visitingTeam, homeTeam,partNum);	
			

			String team = null;// 球队
			String playerName = null;// 球员名
			String position = null;// 位置
			int presentTime = 0;// 在场时间
			String presentTimeString=null;
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

			temp = br.readLine();
			while (temp != null) {
				if (fileName.contains(temp)) {
					team = temp;
				} else {
					String[] line = temp.split(";");
					playerName = line[0];
					position = line[1];
					presentTimeString = DirtyDataManager.checkPresentTime(fileName,
							team, playerName, line[2]);// 在场时间
					presentTime=convertMinuteToSecond(presentTimeString);
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
				    PlayerVO thisPlayer=players.get(playerName);
				    LittleRecordVO littleRecordVO=new LittleRecordVO(season, date, personScore, reboundNum, assistNum);
				    if(thisPlayer==null){
				    	temp = br.readLine();
				    	continue;
				    }
				    else{
				    	thisPlayer.addPlayedGames();
				    	if(!position.equals(""))
				    		thisPlayer.addGameStartingNum();
				    	if(thisPlayer.getMostRecentMatch()==null){
				    		thisPlayer.setOwingTeam(team);
				    	    thisPlayer.setMostRecentMatch(season+"_"+date);
				    	}
				    	else{
				    		if(thisPlayer.getMostRecentMatch().compareTo(season+"_"+date)<0){
				    	       thisPlayer.setOwingTeam(team);
				    	       thisPlayer.setMostRecentMatch(season+"_"+date);
				    	    }
				    	}
	                    thisPlayer.addPresentTime(presentTime);
	                    thisPlayer.addShootHitNum(shootHitNum);
						thisPlayer.addShootAttemptNum(shootAttemptNum);
						thisPlayer.addThreeHitNum(threeHitNum);
						thisPlayer.addThreeAttemptNum(threeAttemptNum);
						thisPlayer.addFreeThrowHitNum(freeThrowHitNum);
						thisPlayer.addFreeThrowAttemptNum(freeThrowAttemptNum);
						thisPlayer.addOffenReboundNum(offenReboundNum);
						thisPlayer.addDefenReboundNum(defenReboundNum);
						thisPlayer.addReboundNum(reboundNum);
						thisPlayer.addAssistNum(assistNum);
						thisPlayer.addStealNum(stealNum);
						thisPlayer.addBlockNum(blockNum);
						thisPlayer.addTurnOverNum(turnOverNum);
						thisPlayer.addFoulNum(foulNum);
						thisPlayer.addScore(personScore);
						
				    }
				if(team.equals(visitingTeam))
				    thisPlayer.addMatchesID(matchCount,true);
				else
					thisPlayer.addMatchesID(matchCount,false);
				
				thisPlayer.addFiveRecentRecords(littleRecordVO);
				
				//计算两双
				int tempDouble=0;
				if (personScore >= 10)
					tempDouble++;
				if (reboundNum >= 10)
					tempDouble++;
				if (assistNum >= 10)
					tempDouble++;
				if (stealNum >= 10)
					tempDouble++;
				if (blockNum >= 10)
					tempDouble++;
				if (tempDouble >= 2)
					thisPlayer.addDoubleDoubleNum();
				
				if(team.equals(homeTeam)){
					thisMatch.addHomeShootHitNum(shootHitNum);
					thisMatch.addHomeShootAttemptNum(shootAttemptNum);
					thisMatch.addHomeThreeHitNum(threeHitNum);
					thisMatch.addHomeThreeAttemptNum(threeAttemptNum);
					thisMatch.addHomeFreeThrowHitNum(freeThrowHitNum);
					thisMatch.addHomeFreeThrowAttemptNum(freeThrowAttemptNum);
					thisMatch.addHomeOffenReboundNum(offenReboundNum);
					thisMatch.addHomeDefenReboundNum(defenReboundNum); 
					thisMatch.addHomeAssistNum(assistNum);
					thisMatch.addHomeStealNum(stealNum);
					thisMatch.addHomeBlockNum(blockNum);
					thisMatch.addHomeTurnOverNum(turnOverNum);
					thisMatch.addHomeFoulNum(foulNum);
				}
				else{
					thisMatch.addVisitingShootHitNum(shootHitNum);
					thisMatch.addVisitingShootAttemptNum(shootAttemptNum);
					thisMatch.addVisitingThreeHitNum(threeHitNum);
					thisMatch.addVisitingThreeAttemptNum(threeAttemptNum);
					thisMatch.addVisitingFreeThrowHitNum(freeThrowHitNum);
					thisMatch.addVisitingFreeThrowAttemptNum(freeThrowAttemptNum);
					thisMatch.addVisitingOffenReboundNum(offenReboundNum);
					thisMatch.addVisitingDefenReboundNum(defenReboundNum); 
					thisMatch.addVisitingAssistNum(assistNum);
					thisMatch.addVisitingStealNum(stealNum);
					thisMatch.addVisitingBlockNum(blockNum);
					thisMatch.addVisitingTurnOverNum(turnOverNum);
					thisMatch.addVisitingFoulNum(foulNum);
				}
					
			  }
			
//				RecordVO recordVO = new RecordVO(team, playerName,
//						position, presentTimeString, shootHitNum,
//						shootAttemptNum, threeHitNum, threeAttemptNum,
//						freeThrowHitNum, freeThrowAttemptNum,
//						offenReboundNum, defenReboundNum, reboundNum,
//						assistNum, stealNum, blockNum, turnOverNum,
//						foulNum, personScore);
//				records.add(recordVO);

				temp = br.readLine();
			}
			
			matches.put(matchCount, thisMatch);	
			
//			MatchVO theMatch=new MatchVO(season, date, visitingTeam, homeTeam, partNum, records);
//            ArrayList<MatchVO> theSeasonMatches=allSeasonMatches.get(season);
//            if(theSeasonMatches==null){
//            	theSeasonMatches=new ArrayList<MatchVO>();
//            	theSeasonMatches.add(theMatch);
//            }
//            else{
//            	theSeasonMatches.add(theMatch);
//            }
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
	

	public ArrayList<PlayerVO> getPlayerSeasonInfo(String season) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getPlayerAverageInfo() {
		ArrayList<PlayerVO> result=new ArrayList<PlayerVO>();
		Iterator iter = players.entrySet().iterator();
		while (iter.hasNext()) {
		   Map.Entry entry = (Map.Entry) iter.next();
	       PlayerVO val = (PlayerVO)entry.getValue();
	       val=getPlayerAverageInfo(val.getName());
		   result.add(val);
		}
		Collections.sort(result, new SequenceOfPlayer());
		return result;
	}


	public PlayerVO getPlayerSeasonInfo(String season, String name) {
		PlayerVO player=new PlayerVO();
		String owingTeam="";
		int playedGames=0;
		int gameStartingNum=0;
		double shootHitRate=0;
		double threeHitRate=0;
		double freeThrowHitRate=0;
		double efficiency=0;
		double recentFiveMatchesScoreUpRate=0;
		double recentFiveMatchesReboundUpRate=0;
		double recentFiveMatchesAssistUpRate=0;
		double GmScEfficiencyValue=0;
		double trueHitRate=0;
		double shootEfficiency=0;
		double reboundRate=0;
		double offenReboundRate=0;
		double defenReboundRate=0;
		double assistRate=0;
		double stealRate=0;
		double blockRate=0;
		double turnOverRate=0;
		double usageRate=0;
		double score_rebound_assist=0;

		int allMatchTime=0;
		int teamReboundNum=0;
		int dsReboundNum=0;
		int teamOffenReboundNum=0;
		int dsOffenReboundNum=0;
		int teamDefenReboundNum=0;
		int dsDefenReboundNum=0;
		int teamShootHitNum=0;
		int dsTwoAttemptNum=0;
		int teamShootAttemptNum=0;
		int teamFreeThrowAttemptNum=0;
		int teamTurnOverNum=0;
		double dsOffenRoundNum=0;
				
		PlayerVO playerSeason=players.get(name);
		
		gameStartingNum=playerSeason.getGameStartingNum();
		owingTeam=playerSeason.getOwingTeam();
		double allreboundNum=playerSeason.getReboundNum();
		double allassistNum=playerSeason.getAssistNum();
		double allpresentTime=playerSeason.getPresentTime();
		double allshootHitNum=playerSeason.getShootHitNum();
		double allshootAttemptNum=playerSeason.getShootAttemptNum();
		double allthreeHitNum=playerSeason.getThreeHitNum();
		double allthreeAttemptNum=playerSeason.getThreeAttemptNum();
		double allfreeThrowHitNum=playerSeason.getFreeThrowHitNum();
		double allfreeThrowAttemptNum=playerSeason.getFreeThrowAttemptNum();
		double alloffenReboundNum=playerSeason.getOffenReboundNum();
		double alldefenReboundNum=playerSeason.getDefenReboundNum();
		double allstealNum=playerSeason.getStealNum();
		double allblockNum=playerSeason.getBlockNum();
		double allfoulNum=playerSeason.getFoulNum();
		double allturnOverNum=playerSeason.getTurnOverNum();
		double allscore=playerSeason.getScore();
		double alldoubleDoubleNum=playerSeason.getDoubleDoubleNum();
		
		
	    playedGames=playerSeason.getPlayedGames();
	    
		if(playedGames==0)
			return new PlayerVO(name, owingTeam, "",playedGames, gameStartingNum, 0, 0, 0, 0, 0, shootHitRate, 0, 0, threeHitRate, 0, 0, freeThrowHitRate, 0, 0, 0, 0, 0, 0, 0, efficiency, recentFiveMatchesScoreUpRate, recentFiveMatchesReboundUpRate, recentFiveMatchesAssistUpRate, GmScEfficiencyValue, trueHitRate, shootEfficiency, reboundRate, offenReboundRate, defenReboundRate, assistRate, stealRate, blockRate, turnOverRate, usageRate, score_rebound_assist, 0);
		else{			
			efficiency = (allscore + allreboundNum
					+ allassistNum + allstealNum + allblockNum)
					- (allshootAttemptNum - allshootHitNum)
					- (allfreeThrowAttemptNum - allfreeThrowHitNum)
					- allturnOverNum;
			
			ArrayList<Integer> matchIDs=playerSeason.getMatchesID();
			Map<Integer,Boolean> isVisitingTeam=playerSeason.getIsVisitingTeam();
			if(matchIDs.size()<5){
				recentFiveMatchesScoreUpRate=0;
			    recentFiveMatchesReboundUpRate=0;
			    recentFiveMatchesAssistUpRate=0;
			}
			else{
				LittleRecordVO[] fiveRecordVOs=playerSeason.getFiveRecentRecords();
			
			    double recentFiveScore=0;
			    double recentReboundNum=0;
			    double recentAssistNum=0;
			    for(int i=0;i<5;i++){
				    LittleRecordVO vo=fiveRecordVOs[i];
				    recentFiveScore+=vo.getScore();
				    recentReboundNum+=vo.getReboundNum();
				    recentAssistNum+=vo.getAssistNum();
			    }
			    double beforeRecentFiveScore=allscore-recentFiveScore;
			    double beforeRecentReboundNum=allreboundNum-recentReboundNum;
			    double beforeRecentAssistNum=allassistNum-recentAssistNum;
			    recentFiveMatchesScoreUpRate=((recentFiveScore/5)-beforeRecentFiveScore/(playedGames-5))/(beforeRecentFiveScore/(playedGames-5));
			    recentFiveMatchesReboundUpRate=((recentReboundNum/5)-beforeRecentReboundNum/(playedGames-5))/(beforeRecentReboundNum/(playedGames-5));
			    recentFiveMatchesAssistUpRate=((recentAssistNum/5)-beforeRecentAssistNum/(playedGames-5))/(beforeRecentAssistNum/(playedGames-5));
			}
			
			GmScEfficiencyValue = allscore
					+ 0.4
					* allshootHitNum
					- 0.7
					* allshootAttemptNum
					- 0.4
					* (allfreeThrowAttemptNum - allfreeThrowHitNum)
					+ 0.7 * alloffenReboundNum + 0.3
					* alldefenReboundNum + allstealNum + 0.7
					* allassistNum + 0.7 * allblockNum - 0.4
					* allfoulNum - allturnOverNum;
			trueHitRate= (double) allscore
					/ (2 * (allshootAttemptNum + 0.44 * allfreeThrowAttemptNum));
			shootEfficiency = (double) (allshootHitNum + 0.5 * allthreeHitNum)
					/ allshootAttemptNum;
			for(Integer id:matchIDs){
				MatchVO match=matches.get(id);
				allMatchTime+=match.getMatchTime();
				if(isVisitingTeam.get(id)==true){
				    teamOffenReboundNum+=match.getVisitingOffenReboundNum();
				    dsOffenReboundNum+=match.getHomeOffenReboundNum();
				    teamDefenReboundNum+=match.getVisitingDefenReboundNum();
				    dsDefenReboundNum+=match.getHomeDefenReboundNum();
				    teamShootHitNum+=match.getVisitingShootHitNum();
				    dsTwoAttemptNum+=match.getHomeShootAttemptNum()-match.getHomeThreeAttemptNum();
				    teamShootAttemptNum+=match.getVisitingShootAttemptNum();
				    teamFreeThrowAttemptNum+=match.getVisitingFreeThrowAttemptNum();
				    teamTurnOverNum+=match.getVisitingTurnOverNum();
				    dsOffenRoundNum+=match.getHomeShootAttemptNum()+0.4*match.getHomeFreeThrowAttemptNum()
				    		-1.07
				    		*((double)match.getHomeOffenReboundNum()/(match.getHomeOffenReboundNum()+match.getVisitingDefenReboundNum())*(match.getHomeShootAttemptNum()-match.getHomeShootHitNum()))+1.07*match.getHomeTurnOverNum();
//				    int v89=match.getHomeShootAttemptNum();
//					int v24=match.getHomeFreeThrowAttemptNum();
//					 int v17=match.getHomeOffenReboundNum();
//					 int v34=match.getVisitingDefenReboundNum();
//					 int v37=match.getHomeShootHitNum();
//					 int v8=match.getHomeTurnOverNum();
//					 double v88_6133=v89+0.4*v24
//						    	-1.07
//						    	*((double)v17/(v17+v34)*(v89-v37))+1.07*v8;
//					 System.out.println("-"+v88_6133);
				}
				else{
					 teamOffenReboundNum+=match.getHomeOffenReboundNum();
					 dsOffenReboundNum+=match.getVisitingOffenReboundNum();
					 teamDefenReboundNum+=match.getHomeDefenReboundNum();
					 dsDefenReboundNum+=match.getVisitingDefenReboundNum();
					 teamShootHitNum+=match.getHomeShootHitNum();
					 dsTwoAttemptNum+=match.getVisitingShootAttemptNum()-match.getVisitingThreeAttemptNum();
					 teamShootAttemptNum+=match.getHomeShootAttemptNum();
					 teamFreeThrowAttemptNum+=match.getHomeFreeThrowAttemptNum();
					 teamTurnOverNum+=match.getHomeTurnOverNum();
					 dsOffenRoundNum+=match.getVisitingShootAttemptNum()+0.4*match.getVisitingFreeThrowAttemptNum()
					    	-1.07
					    	*((double)match.getVisitingOffenReboundNum()/(match.getVisitingOffenReboundNum()+match.getHomeDefenReboundNum())*(match.getVisitingShootAttemptNum()-match.getVisitingShootHitNum()))+1.07*match.getVisitingTurnOverNum();
					 
				}
			}
			 teamReboundNum=teamOffenReboundNum+teamDefenReboundNum;
			 dsReboundNum=dsOffenReboundNum+dsDefenReboundNum;

			reboundRate = allreboundNum * allMatchTime
					/ (double)allpresentTime
					/ (teamReboundNum + dsReboundNum);
			offenReboundRate = alloffenReboundNum * allMatchTime
					/ (double)allpresentTime
					/ (teamOffenReboundNum + dsOffenReboundNum);
			defenReboundRate = alldefenReboundNum * allMatchTime
					/ (double)allpresentTime
					/ (teamDefenReboundNum + dsDefenReboundNum);
			assistRate =(double) allassistNum
					/( allpresentTime / allMatchTime
							* teamShootHitNum - allshootHitNum);
			stealRate = (double)allassistNum* allMatchTime
					/ allpresentTime
					/ dsOffenRoundNum;
			blockRate = allblockNum * allMatchTime
					/(double)allpresentTime
					/ dsTwoAttemptNum;
			turnOverRate = (double)allturnOverNum
					/ (allshootAttemptNum- allthreeAttemptNum
							+ 0.4 * allfreeThrowAttemptNum+ allturnOverNum);
			usageRate = (allshootAttemptNum + 0.44
					* allfreeThrowAttemptNum + allturnOverNum)
					* allMatchTime
					/ (double)allpresentTime
					/ (teamShootAttemptNum + 0.44
							* teamFreeThrowAttemptNum + teamTurnOverNum);
			score_rebound_assist=(double)(allscore+allreboundNum+allassistNum)/3;
	
		}
		
		player.setName(name);
		player.setOwingTeam(owingTeam);
		player.setPlayedGames(playedGames);
		player.setGameStartingNum(gameStartingNum);
		player.setReboundNum(allreboundNum);
		player.setAssistNum(allassistNum);
		player.setPresentTime(allpresentTime);
		player.setShootHitRate(shootHitRate);
		player.setThreeHitRate(threeHitRate);
		player.setFreeThrowHitRate(freeThrowHitRate);
		player.setOffenReboundNum(alloffenReboundNum);
		player.setDefenReboundNum(alldefenReboundNum);
		player.setStealNum(allstealNum);
		player.setBlockNum(allblockNum);
		player.setFoulNum(allfoulNum);
		player.setTurnOverNum(allturnOverNum);
		player.setScore(allscore);
		player.setEfficiency(efficiency);
		player.setRecentFiveMatchesScoreUpRate(recentFiveMatchesScoreUpRate);
		player.setRecentFiveMatchesReboundUpRate(recentFiveMatchesReboundUpRate);
		player.setRecentFiveMatchesAssistUpRate(recentFiveMatchesAssistUpRate);
		player.setGmScEfficiencyValue(GmScEfficiencyValue);
		player.setTrueHitRate(trueHitRate);
		player.setShootEfficiency(shootEfficiency);
		player.setReboundRate(reboundRate);
		player.setOffenReboundRate(offenReboundRate);
		player.setDefenReboundRate(defenReboundRate);
		player.setAssistRate(assistRate);
		player.setStealRate(stealRate);
		player.setBlockRate(blockRate);
		player.setTurnOverRate(turnOverRate);
		player.setUsageRate(usageRate);
		player.setScore_rebound_assist(score_rebound_assist);
		player.setDoubleDoubleNum(alldoubleDoubleNum);
		return player;
	}

	public PlayerVO getPlayerAverageInfo(String name) {
		PlayerVO player=new PlayerVO();
		String owingTeam="";
		int playedGames=0;
		int gameStartingNum=0;
		double reboundNum=0;
		double assistNum=0;
		double presentTime=0;
		double shootHitNum=0;
		double shootAttemptNum=0;
		double shootHitRate=0;
		double threeHitNum=0;
		double threeAttemptNum=0;
		double threeHitRate=0;
		double freeThrowHitNum=0;
		double freeThrowAttemptNum=0;
		double freeThrowHitRate=0;
		double offenReboundNum=0;
		double defenReboundNum=0;
		double stealNum=0;
		double blockNum=0;
		double foulNum=0;
		double turnOverNum=0;
		double score=0;
		double efficiency=0;
		double recentFiveMatchesScoreUpRate=0;
		double recentFiveMatchesReboundUpRate=0;
		double recentFiveMatchesAssistUpRate=0;
		double GmScEfficiencyValue=0;
		double trueHitRate=0;
		double shootEfficiency=0;
		double reboundRate=0;
		double offenReboundRate=0;
		double defenReboundRate=0;
		double assistRate=0;
		double stealRate=0;
		double blockRate=0;
		double turnOverRate=0;
		double usageRate=0;
		double score_rebound_assist=0;
		double doubleDoubleNum=0;

		int allMatchTime=0;
		int teamReboundNum=0;
		int dsReboundNum=0;
		int teamOffenReboundNum=0;
		int dsOffenReboundNum=0;
		int teamDefenReboundNum=0;
		int dsDefenReboundNum=0;
		int teamShootHitNum=0;
		int dsTwoAttemptNum=0;
		int teamShootAttemptNum=0;
		int teamFreeThrowAttemptNum=0;
		int teamTurnOverNum=0;
		double dsOffenRoundNum=0;
				
		PlayerVO playerSeason=players.get(name);
		
		
		gameStartingNum=playerSeason.getGameStartingNum();
		owingTeam=playerSeason.getOwingTeam();
		double allreboundNum=playerSeason.getReboundNum();
		double allassistNum=playerSeason.getAssistNum();
		double allpresentTime=playerSeason.getPresentTime();
		double allshootHitNum=playerSeason.getShootHitNum();
		double allshootAttemptNum=playerSeason.getShootAttemptNum();
		double allthreeHitNum=playerSeason.getThreeHitNum();
		double allthreeAttemptNum=playerSeason.getThreeAttemptNum();
		double allfreeThrowHitNum=playerSeason.getFreeThrowHitNum();
		double allfreeThrowAttemptNum=playerSeason.getFreeThrowAttemptNum();
		double alloffenReboundNum=playerSeason.getOffenReboundNum();
		double alldefenReboundNum=playerSeason.getDefenReboundNum();
		double allstealNum=playerSeason.getStealNum();
		double allblockNum=playerSeason.getBlockNum();
		double allfoulNum=playerSeason.getFoulNum();
		double allturnOverNum=playerSeason.getTurnOverNum();
		double allscore=playerSeason.getScore();
		double alldoubleDoubleNum=playerSeason.getDoubleDoubleNum();
		
		
	    playedGames=playerSeason.getPlayedGames();
	    
		if(playedGames==0)
			return new PlayerVO(name, owingTeam, "",playedGames, gameStartingNum, reboundNum, assistNum, 0, shootHitNum, shootAttemptNum, shootHitRate, threeHitNum, threeAttemptNum, threeHitRate, freeThrowHitNum, freeThrowAttemptNum, freeThrowHitRate, offenReboundNum, defenReboundNum, stealNum, blockNum, turnOverNum, foulNum, score, efficiency, recentFiveMatchesScoreUpRate, recentFiveMatchesReboundUpRate, recentFiveMatchesAssistUpRate, GmScEfficiencyValue, trueHitRate, shootEfficiency, reboundRate, offenReboundRate, defenReboundRate, assistRate, stealRate, blockRate, turnOverRate, usageRate, score_rebound_assist, doubleDoubleNum);
		else{			
			reboundNum=allreboundNum/playedGames;
			assistNum=allassistNum/playedGames;
			presentTime=allpresentTime/playedGames;
			shootHitNum=allshootHitNum/playedGames;
			shootAttemptNum=allshootAttemptNum/playedGames;
			shootHitRate=allshootHitNum/allshootAttemptNum;
			threeHitNum=allthreeHitNum/playedGames;
			threeAttemptNum=allthreeAttemptNum/playedGames;
			threeHitRate=allthreeHitNum/allthreeAttemptNum;
			freeThrowHitNum=allfreeThrowHitNum/playedGames;
			freeThrowAttemptNum=allfreeThrowAttemptNum/playedGames;
			freeThrowHitRate=allfreeThrowHitNum/allfreeThrowAttemptNum;
			offenReboundNum=alloffenReboundNum/playedGames;
			defenReboundNum=alldefenReboundNum/playedGames;
			stealNum=allstealNum/playedGames;
			blockNum=allblockNum/playedGames;
			foulNum=allfoulNum/playedGames;
			turnOverNum=allturnOverNum/playedGames;
			score=allscore/playedGames;
			efficiency = (allscore + allreboundNum
					+ allassistNum + allstealNum + allblockNum)
					- (allshootAttemptNum - allshootHitNum)
					- (allfreeThrowAttemptNum - allfreeThrowHitNum)
					- allturnOverNum;
			
			ArrayList<Integer> matchIDs=playerSeason.getMatchesID();
			Map<Integer,Boolean> isVisitingTeam=playerSeason.getIsVisitingTeam();
			if(matchIDs.size()<5){
				recentFiveMatchesScoreUpRate=0;
			    recentFiveMatchesReboundUpRate=0;
			    recentFiveMatchesAssistUpRate=0;
			}
			else{
				LittleRecordVO[] fiveRecordVOs=playerSeason.getFiveRecentRecords();
			
			    double recentFiveScore=0;
			    double recentReboundNum=0;
			    double recentAssistNum=0;
			    for(int i=0;i<5;i++){
				    LittleRecordVO vo=fiveRecordVOs[i];
				    recentFiveScore+=vo.getScore();
				    recentReboundNum+=vo.getReboundNum();
				    recentAssistNum+=vo.getAssistNum();
			    }
			    double beforeRecentFiveScore=allscore-recentFiveScore;
			    double beforeRecentReboundNum=allreboundNum-recentReboundNum;
			    double beforeRecentAssistNum=allassistNum-recentAssistNum;
			    recentFiveMatchesScoreUpRate=((recentFiveScore/5)-beforeRecentFiveScore/(playedGames-5))/(beforeRecentFiveScore/(playedGames-5));
			    recentFiveMatchesReboundUpRate=((recentReboundNum/5)-beforeRecentReboundNum/(playedGames-5))/(beforeRecentReboundNum/(playedGames-5));
			    recentFiveMatchesAssistUpRate=((recentAssistNum/5)-beforeRecentAssistNum/(playedGames-5))/(beforeRecentAssistNum/(playedGames-5));
			}
			
			GmScEfficiencyValue = allscore
					+ 0.4
					* allshootHitNum
					- 0.7
					* allshootAttemptNum
					- 0.4
					* (allfreeThrowAttemptNum - allfreeThrowHitNum)
					+ 0.7 * alloffenReboundNum + 0.3
					* alldefenReboundNum + allstealNum + 0.7
					* allassistNum + 0.7 * allblockNum - 0.4
					* allfoulNum - allturnOverNum;
			trueHitRate= (double) allscore
					/ (2 * (allshootAttemptNum + 0.44 * allfreeThrowAttemptNum));
			shootEfficiency = (double) (allshootHitNum + 0.5 * allthreeHitNum)
					/ allshootAttemptNum;
			for(Integer id:matchIDs){
				MatchVO match=matches.get(id);
				allMatchTime+=match.getMatchTime();
				if(isVisitingTeam.get(id)==true){
				    teamOffenReboundNum+=match.getVisitingOffenReboundNum();
				    dsOffenReboundNum+=match.getHomeOffenReboundNum();
				    teamDefenReboundNum+=match.getVisitingDefenReboundNum();
				    dsDefenReboundNum+=match.getHomeDefenReboundNum();
				    teamShootHitNum+=match.getVisitingShootHitNum();
				    dsTwoAttemptNum+=match.getHomeShootAttemptNum()-match.getHomeThreeAttemptNum();
				    teamShootAttemptNum+=match.getVisitingShootAttemptNum();
				    teamFreeThrowAttemptNum+=match.getVisitingFreeThrowAttemptNum();
				    teamTurnOverNum+=match.getVisitingTurnOverNum();
				    dsOffenRoundNum+=match.getHomeShootAttemptNum()+0.4*match.getHomeFreeThrowAttemptNum()
				    		-1.07
				    		*((double)match.getHomeOffenReboundNum()/(match.getHomeOffenReboundNum()+match.getVisitingDefenReboundNum())*(match.getHomeShootAttemptNum()-match.getHomeShootHitNum()))+1.07*match.getHomeTurnOverNum();
//				    int v89=match.getHomeShootAttemptNum();
//					int v24=match.getHomeFreeThrowAttemptNum();
//					 int v17=match.getHomeOffenReboundNum();
//					 int v34=match.getVisitingDefenReboundNum();
//					 int v37=match.getHomeShootHitNum();
//					 int v8=match.getHomeTurnOverNum();
//					 double v88_6133=v89+0.4*v24
//						    	-1.07
//						    	*((double)v17/(v17+v34)*(v89-v37))+1.07*v8;
//					 System.out.println("-"+v88_6133);
				}
				else{
					 teamOffenReboundNum+=match.getHomeOffenReboundNum();
					 dsOffenReboundNum+=match.getVisitingOffenReboundNum();
					 teamDefenReboundNum+=match.getHomeDefenReboundNum();
					 dsDefenReboundNum+=match.getVisitingDefenReboundNum();
					 teamShootHitNum+=match.getHomeShootHitNum();
					 dsTwoAttemptNum+=match.getVisitingShootAttemptNum()-match.getVisitingThreeAttemptNum();
					 teamShootAttemptNum+=match.getHomeShootAttemptNum();
					 teamFreeThrowAttemptNum+=match.getHomeFreeThrowAttemptNum();
					 teamTurnOverNum+=match.getHomeTurnOverNum();
//					 int v89=match.getVisitingShootAttemptNum();
//					 int v24=match.getVisitingFreeThrowAttemptNum();
//					 int v17=match.getVisitingOffenReboundNum();
//					 int v34=match.getHomeDefenReboundNum();
//					 int v37=match.getVisitingShootHitNum();
//					 int v8=match.getVisitingTurnOverNum();
//					 double v88_6133=v89+0.4*v24
//						    	-1.07
//						    	*((double)v17/(v17+v34)*(v89-v37))+1.07*v8;
					 dsOffenRoundNum+=match.getVisitingShootAttemptNum()+0.4*match.getVisitingFreeThrowAttemptNum()
					    	-1.07
					    	*((double)match.getVisitingOffenReboundNum()/(match.getVisitingOffenReboundNum()+match.getHomeDefenReboundNum())*(match.getVisitingShootAttemptNum()-match.getVisitingShootHitNum()))+1.07*match.getVisitingTurnOverNum();
			//		 System.out.println("-"+v88_6133);
				}
			}
			 teamReboundNum=teamOffenReboundNum+teamDefenReboundNum;
			 dsReboundNum=dsOffenReboundNum+dsDefenReboundNum;

			reboundRate = allreboundNum * allMatchTime
					/ allpresentTime
					/ (teamReboundNum + dsReboundNum);
			offenReboundRate = alloffenReboundNum * allMatchTime
					/ allpresentTime
					/ (teamOffenReboundNum + dsOffenReboundNum);
			defenReboundRate = alldefenReboundNum * allMatchTime
					/ allpresentTime
					/ (teamDefenReboundNum + dsDefenReboundNum);
			assistRate = allassistNum
					/( allpresentTime / allMatchTime
							* teamShootHitNum - allshootHitNum);
			stealRate = (double)allassistNum* allMatchTime
					/ allpresentTime
					/ dsOffenRoundNum;
			blockRate = allblockNum * allMatchTime
					/allpresentTime
					/ dsTwoAttemptNum;
			turnOverRate = allturnOverNum
					/ (allshootAttemptNum- allthreeAttemptNum
							+ 0.4 * allfreeThrowAttemptNum+ allturnOverNum);
			usageRate = (allshootAttemptNum + 0.44
					* allfreeThrowAttemptNum + allturnOverNum)
					* allMatchTime
					/ allpresentTime
					/ (teamShootAttemptNum + 0.44
							* teamFreeThrowAttemptNum + teamTurnOverNum);
			score_rebound_assist=(allscore+allreboundNum+allassistNum)/3/playedGames;
			doubleDoubleNum=alldoubleDoubleNum/playedGames;		
		}
		
		player.setName(name);
		player.setOwingTeam(owingTeam);
		player.setPlayedGames(playedGames);
		player.setGameStartingNum(gameStartingNum);
		player.setReboundNum(reboundNum);
		player.setAssistNum(assistNum);
		player.setPresentTime(presentTime);
		player.setShootHitRate(shootHitRate);
		player.setThreeHitRate(threeHitRate);
		player.setFreeThrowHitRate(freeThrowHitRate);
		player.setOffenReboundNum(offenReboundNum);
		player.setDefenReboundNum(defenReboundNum);
		player.setStealNum(stealNum);
		player.setBlockNum(blockNum);
		player.setFoulNum(foulNum);
		player.setTurnOverNum(turnOverNum);
		player.setScore(score);
		player.setEfficiency(efficiency);
		player.setRecentFiveMatchesScoreUpRate(recentFiveMatchesScoreUpRate);
		player.setRecentFiveMatchesReboundUpRate(recentFiveMatchesReboundUpRate);
		player.setRecentFiveMatchesAssistUpRate(recentFiveMatchesAssistUpRate);
		player.setGmScEfficiencyValue(GmScEfficiencyValue);
		player.setTrueHitRate(trueHitRate);
		player.setShootEfficiency(shootEfficiency);
		player.setReboundRate(reboundRate);
		player.setOffenReboundRate(offenReboundRate);
		player.setDefenReboundRate(defenReboundRate);
		player.setAssistRate(assistRate);
		player.setStealRate(stealRate);
		player.setBlockRate(blockRate);
		player.setTurnOverRate(turnOverRate);
		player.setUsageRate(usageRate);
		player.setScore_rebound_assist(score_rebound_assist);
		player.setDoubleDoubleNum(doubleDoubleNum);
		return player;
	}
	
	
	public ArrayList<PlayerVO> getOrderedPlayersBySeason(String season,
			String condition, String order, int num) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getOrderedPlayersByAverage(
			String condition, String order, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> selectPlayersBySeason(String season,
			String position, String union, String column, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> selectPlayersByAverage(
			String position, String union, String column, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public ImageIcon getPlayerPortraitImage(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public ImageIcon getPlayerActionImage(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getDayHotPlayer(String column) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getSeasonHotPlayer(String season, String column) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getBestImprovedPlayer(String column) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PlayerVO> getPlayersByInitialName(char character) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MatchVO> getRecentMatches(String playerName){
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MatchVO> getTodayMatches(String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MatchVO> getMatches(String playerName) {
		// TODO Auto-generated method stub
		return null;
	}
		
	public ArrayList<String> getPlayersByTeam(String teamAbLocation) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private int convertMinuteToSecond(String s){
		String[] temp = s.split(":");
		int minute = Integer.parseInt(temp[0]);
		int second = Integer.parseInt(temp[1]);
		return minute * 60 + second;
	}
  
	public static void main(String[] args){
	   long start = System.currentTimeMillis();
	   NewPlayer player=new NewPlayer(3);
	   PlayerVO vo=player.getPlayerAverageInfo("Al Horford");
	   System.out.println(vo.getOwingTeam());
	   System.out.println(vo.getPlayedGames());
	   System.out.println(vo.getGameStartingNum());
	   System.out.println(vo.getReboundNum()*vo.getPlayedGames());
	   System.out.println(vo.getAssistNum()*vo.getPlayedGames());
	   System.out.println(vo.getPresentTime()*vo.getPlayedGames());
	   System.out.println(vo.getShootHitRate());
	   System.out.println(vo.getThreeHitRate());
	   System.out.println(vo.getFreeThrowHitRate());
	   System.out.println(vo.getOffenReboundNum()*vo.getPlayedGames());
	   System.out.println(vo.getDefenReboundNum()*vo.getPlayedGames());
	   System.out.println(vo.getStealNum()*vo.getPlayedGames());
	   System.out.println(vo.getBlockNum()*vo.getPlayedGames());
	   System.out.println(vo.getFoulNum()*vo.getPlayedGames());
	   System.out.println(vo.getTurnOverNum()*vo.getPlayedGames());
	   System.out.println(vo.getScore()*vo.getPlayedGames());
	   System.out.println(vo.getEfficiency());
	   System.out.println(vo.getRecentFiveMatchesScoreUpRate());
	   System.out.println(vo.getRecentFiveMatchesReboundUpRate());
	   System.out.println(vo.getRecentFiveMatchesAssistUpRate());
	   System.out.println(vo.getGmScEfficiencyValue());
	   System.out.println(vo.getTrueHitRate());
	   System.out.println(vo.getShootHitEfficiency());
	   System.out.println(vo.getReboundRate());
	   System.out.println(vo.getOffenReboundRate());
	   System.out.println(vo.getDefenReboundRate());
	   System.out.println(vo.getAssistRate());
	   System.out.println(vo.getStealRate());
	   System.out.println(vo.getBlockRate());
	   System.out.println(vo.getTurnOverRate());
	   System.out.println(vo.getUsageRate());
	   System.out.println(vo.getScore_rebound_assist()*vo.getPlayedGames());
	   System.out.println(vo.getDoubleDoubleNum()*vo.getPlayedGames());
//	   PlayerVO vo2=player.getPlayerAverageInfo("Aaron Brooks");
//	   PlayerVO vo3=player.getPlayerAverageInfo("Adonis Thomas");
//	   PlayerVO vo4=player.getPlayerAverageInfo("Aaron Gray");
//	   PlayerVO vo5=player.getPlayerAverageInfo("Al Harrington");
//	   PlayerVO vo6=player.getPlayerAverageInfo("Al Jefferson");
	   player.getPlayerAverageInfo();
	   long end = System.currentTimeMillis();
       System.out.println("运行时间：" + (end - start) + "毫秒");//应该是end - start
   }

//  newnewplayer的:	
//	public ArrayList<PlayerVO> getPlayerAverageInfo() {
//	ArrayList<PlayerVO> result=new ArrayList<PlayerVO>();
//    int matchCount=0;
//	
//	for (MatchVO match:allSeasonMatches) {    
//
//       String season=match.getSeason();
//       String date=match.getDate();
//       String homeTeam=match.getHomeTeam();
//       String visitingTeam=match.getVisitingTeam();
//       
//	   MatchVO thisMatch=new MatchVO(season, date, visitingTeam, homeTeam);
//	   thisMatch.setMatchTime(match.getMatchTime());
//	   
//       String team = null;// 球队
//		String playerName = null;// 球员名
//		String position = null;// 位置
//		int presentTime = 0;// 在场时间
//		int shootHitNum = 0;// 投篮命中数
//		int shootAttemptNum = 0;// 投篮出手数
//		int threeHitNum = 0;// 三分命中数
//		int threeAttemptNum = 0;// 三分出手数
//		int freeThrowHitNum = 0;// 罚球命中数
//		int freeThrowAttemptNum = 0;// 罚球出手数
//		int offenReboundNum = 0;// 进攻（前场）篮板数
//		int defenReboundNum = 0;// 防守（后场）篮板数
//		int reboundNum = 0;// 总篮板数
//		int assistNum = 0;// 助攻数
//		int stealNum = 0;// 抢断数
//		int blockNum = 0;// 盖帽数
//		int turnOverNum = 0;// 失误数
//		int foulNum = 0;// 犯规数
//		int personScore = 0;// 个人得分
//
//		for(RecordVO record:match.getRecords()){
//			    team=record.getTeam();
//				playerName = record.getPlayerName();
//				position = record.getPosition();
//				presentTime=convertMinuteToSecond(record.getPresentTime());
//				shootHitNum = record.getShootHitNum();// 投篮命中数
//				shootAttemptNum = record.getShootAttemptNum();// 投篮出手数
//				threeHitNum = record.getThreeHitNum();// 三分命中数
//				threeAttemptNum = record.getThreeAttemptNum();// 三分出手数
//				freeThrowHitNum = record.getFreeThrowHitNum();// 罚球命中数
//				freeThrowAttemptNum = record.getFreeThrowAttemptNum();// 罚球出手数
//				offenReboundNum = record.getOffenReboundNum();// 进攻（前场）篮板数
//				defenReboundNum = record.getDefenReboundNum();// 防守（后场）篮板数
//				reboundNum = record.getReboundNum();// 总篮板数
//				assistNum = record.getAssistNum();// 助攻数
//				stealNum = record.getStealNum();// 抢断数
//				blockNum = record.getBlockNum();// 盖帽数
//				turnOverNum = record.getTurnOverNum();// 失误数
//				foulNum = record.getFoulNum();// 犯规数
//				personScore = record.getScore();// 个人得分
//			    PlayerVO thisPlayer=players.get(playerName);
//			    if(thisPlayer==null)
//			    	continue;
//			    LittleRecordVO littleRecordVO=new LittleRecordVO(season, date, personScore, reboundNum, assistNum);
//
//			    thisPlayer.addPlayedGames();
//			    	if(!position.equals(""))
//			    		thisPlayer.addGameStartingNum();
//			    	
//			    	if(thisPlayer.getMostRecentMatch()==null){
//			    		thisPlayer.setOwingTeam(team);
//			    		thisPlayer.setMostRecentMatch(season+"_"+date);
//			    	}
//			    	else{
//			    		if(thisPlayer.getMostRecentMatch().compareTo(season+"_"+date)<0){
//			    		   thisPlayer.setOwingTeam(team);
//			    	       thisPlayer.setMostRecentMatch(season+"_"+date);
//			    		}
//			    	}
//			    	
//                    thisPlayer.addPresentTime(presentTime);
//                    thisPlayer.addShootHitNum(shootHitNum);
//					thisPlayer.addShootAttemptNum(shootAttemptNum);
//					thisPlayer.addThreeHitNum(threeHitNum);
//					thisPlayer.addThreeAttemptNum(threeAttemptNum);
//					thisPlayer.addFreeThrowHitNum(freeThrowHitNum);
//					thisPlayer.addFreeThrowAttemptNum(freeThrowAttemptNum);
//					thisPlayer.addOffenReboundNum(offenReboundNum);
//					thisPlayer.addDefenReboundNum(defenReboundNum);
//					thisPlayer.addReboundNum(reboundNum);
//					thisPlayer.addAssistNum(assistNum);
//					thisPlayer.addStealNum(stealNum);
//					thisPlayer.addBlockNum(blockNum);
//					thisPlayer.addTurnOverNum(turnOverNum);
//					thisPlayer.addFoulNum(foulNum);
//					thisPlayer.addScore(personScore);
//					
//		
//			if(team.equals(visitingTeam))
//			    thisPlayer.addMatchesID(matchCount,true);
//			else
//				thisPlayer.addMatchesID(matchCount,false);
//			
//			thisPlayer.addFiveRecentRecords(littleRecordVO);
//			
//			//计算两双
//			int tempDouble=0;
//			if (personScore >= 10)
//				tempDouble++;
//			if (reboundNum >= 10)
//				tempDouble++;
//			if (assistNum >= 10)
//				tempDouble++;
//			if (stealNum >= 10)
//				tempDouble++;
//			if (blockNum >= 10)
//				tempDouble++;
//			if (tempDouble >= 2)
//				thisPlayer.addDoubleDoubleNum();
//			
//			
//			if(team.equals(homeTeam)){
//				thisMatch.addHomeShootHitNum(shootHitNum);
//				thisMatch.addHomeShootAttemptNum(shootAttemptNum);
//				thisMatch.addHomeThreeHitNum(threeHitNum);
//				thisMatch.addHomeThreeAttemptNum(threeAttemptNum);
//				thisMatch.addHomeFreeThrowHitNum(freeThrowHitNum);
//				thisMatch.addHomeFreeThrowAttemptNum(freeThrowAttemptNum);
//				thisMatch.addHomeOffenReboundNum(offenReboundNum);
//				thisMatch.addHomeDefenReboundNum(defenReboundNum); 
//				thisMatch.addHomeAssistNum(assistNum);
//				thisMatch.addHomeStealNum(stealNum);
//				thisMatch.addHomeBlockNum(blockNum);
//				thisMatch.addHomeTurnOverNum(turnOverNum);
//				thisMatch.addHomeFoulNum(foulNum);
//			}
//			else{
//				thisMatch.addVisitingShootHitNum(shootHitNum);
//				thisMatch.addVisitingShootAttemptNum(shootAttemptNum);
//				thisMatch.addVisitingThreeHitNum(threeHitNum);
//				thisMatch.addVisitingThreeAttemptNum(threeAttemptNum);
//				thisMatch.addVisitingFreeThrowHitNum(freeThrowHitNum);
//				thisMatch.addVisitingFreeThrowAttemptNum(freeThrowAttemptNum);
//				thisMatch.addVisitingOffenReboundNum(offenReboundNum);
//				thisMatch.addVisitingDefenReboundNum(defenReboundNum); 
//				thisMatch.addVisitingAssistNum(assistNum);
//				thisMatch.addVisitingStealNum(stealNum);
//				thisMatch.addVisitingBlockNum(blockNum);
//				thisMatch.addVisitingTurnOverNum(turnOverNum);
//				thisMatch.addVisitingFoulNum(foulNum);
//	        }
//			
//		}
//		matches.put(matchCount, thisMatch);
//		matchCount++;		
//	}
//	
//	
//	Iterator iter = players.entrySet().iterator();
//	while (iter.hasNext()) {
//	   Map.Entry entry = (Map.Entry) iter.next();
//       PlayerVO playerSeason = (PlayerVO)entry.getValue();
//
//
//	PlayerVO player=new PlayerVO();
//	String name="";
//	String owingTeam="";
//	int playedGames=0;
//	int gameStartingNum=0;
//	double reboundNum=0;
//	double assistNum=0;
//	double presentTime=0;
//	double shootHitNum=0;
//	double shootAttemptNum=0;
//	double shootHitRate=0;
//	double threeHitNum=0;
//	double threeAttemptNum=0;
//	double threeHitRate=0;
//	double freeThrowHitNum=0;
//	double freeThrowAttemptNum=0;
//	double freeThrowHitRate=0;
//	double offenReboundNum=0;
//	double defenReboundNum=0;
//	double stealNum=0;
//	double blockNum=0;
//	double foulNum=0;
//	double turnOverNum=0;
//	double score=0;
//	double efficiency=0;
//	double recentFiveMatchesScoreUpRate=0;
//	double recentFiveMatchesReboundUpRate=0;
//	double recentFiveMatchesAssistUpRate=0;
//	double GmScEfficiencyValue=0;
//	double trueHitRate=0;
//	double shootEfficiency=0;
//	double reboundRate=0;
//	double offenReboundRate=0;
//	double defenReboundRate=0;
//	double assistRate=0;
//	double stealRate=0;
//	double blockRate=0;
//	double turnOverRate=0;
//	double usageRate=0;
//	double score_rebound_assist=0;
//	double doubleDoubleNum=0;
//
//	int allMatchTime=0;
//	int teamReboundNum=0;
//	int dsReboundNum=0;
//	int teamOffenReboundNum=0;
//	int dsOffenReboundNum=0;
//	int teamDefenReboundNum=0;
//	int dsDefenReboundNum=0;
//	int teamShootHitNum=0;
//	int dsTwoAttemptNum=0;
//	int teamShootAttemptNum=0;
//	int teamFreeThrowAttemptNum=0;
//	int teamTurnOverNum=0;
//	double dsOffenRoundNum=0;
//			
//
//	name=playerSeason.getName();
//	
//	
//    playedGames=playerSeason.getPlayedGames();
//    
//	if(playedGames==0)
//		result.add(new PlayerVO(name, owingTeam, playedGames, gameStartingNum, reboundNum, assistNum, 0, shootHitNum, shootAttemptNum, shootHitRate, threeHitNum, threeAttemptNum, threeHitRate, freeThrowHitNum, freeThrowAttemptNum, freeThrowHitRate, offenReboundNum, defenReboundNum, stealNum, blockNum, turnOverNum, foulNum, score, efficiency, recentFiveMatchesScoreUpRate, recentFiveMatchesReboundUpRate, recentFiveMatchesAssistUpRate, GmScEfficiencyValue, trueHitRate, shootEfficiency, reboundRate, offenReboundRate, defenReboundRate, assistRate, stealRate, blockRate, turnOverRate, usageRate, score_rebound_assist, doubleDoubleNum));
//	else{
//		
//		gameStartingNum=playerSeason.getGameStartingNum();
//		owingTeam=playerSeason.getOwingTeam();
//		double allreboundNum=playerSeason.getReboundNum();
//		double allassistNum=playerSeason.getAssistNum();
//		double allpresentTime=playerSeason.getPresentTime();
//		double allshootHitNum=playerSeason.getShootHitNum();
//		double allshootAttemptNum=playerSeason.getShootAttemptNum();
//		double allthreeHitNum=playerSeason.getThreeHitNum();
//		double allthreeAttemptNum=playerSeason.getThreeAttemptNum();
//		double allfreeThrowHitNum=playerSeason.getFreeThrowHitNum();
//		double allfreeThrowAttemptNum=playerSeason.getFreeThrowAttemptNum();
//		double alloffenReboundNum=playerSeason.getOffenReboundNum();
//		double alldefenReboundNum=playerSeason.getDefenReboundNum();
//		double allstealNum=playerSeason.getStealNum();
//		double allblockNum=playerSeason.getBlockNum();
//		double allfoulNum=playerSeason.getFoulNum();
//		double allturnOverNum=playerSeason.getTurnOverNum();
//		double allscore=playerSeason.getScore();
//		double alldoubleDoubleNum=playerSeason.getDoubleDoubleNum();
//		
//		reboundNum=allreboundNum/playedGames;
//		assistNum=allassistNum/playedGames;
//		presentTime=allpresentTime/playedGames;
//		shootHitNum=allshootHitNum/playedGames;
//		shootAttemptNum=allshootAttemptNum/playedGames;
//		shootHitRate=allshootHitNum/allshootAttemptNum;
//		threeHitNum=allthreeHitNum/playedGames;
//		threeAttemptNum=allthreeAttemptNum/playedGames;
//		threeHitRate=allthreeHitNum/allthreeAttemptNum;
//		freeThrowHitNum=allfreeThrowHitNum/playedGames;
//		freeThrowAttemptNum=allfreeThrowAttemptNum/playedGames;
//		freeThrowHitRate=allfreeThrowHitNum/allfreeThrowAttemptNum;
//		offenReboundNum=alloffenReboundNum/playedGames;
//		defenReboundNum=alldefenReboundNum/playedGames;
//		stealNum=allstealNum/playedGames;
//		blockNum=allblockNum/playedGames;
//		foulNum=allfoulNum/playedGames;
//		turnOverNum=allturnOverNum/playedGames;
//		score=allscore/playedGames;
//		efficiency = (allscore + allreboundNum
//				+ allassistNum + allstealNum + allblockNum)
//				- (allshootAttemptNum - allshootHitNum)
//				- (allfreeThrowAttemptNum - allfreeThrowHitNum)
//				- allturnOverNum;
//		
//		ArrayList<Integer> matchIDs=playerSeason.getMatchesID();
//		Map<Integer,Boolean> isVisitingTeam=playerSeason.getIsVisitingTeam();
//		if(matchIDs.size()<5){
//			recentFiveMatchesScoreUpRate=0;
//		    recentFiveMatchesReboundUpRate=0;
//		    recentFiveMatchesAssistUpRate=0;
//		}
//		else{
//			LittleRecordVO[] fiveRecordVOs=playerSeason.getFiveRecentRecords();
//		
//		    double recentFiveScore=0;
//		    double recentReboundNum=0;
//		    double recentAssistNum=0;
//		    for(int i=0;i<5;i++){
//			    LittleRecordVO vo=fiveRecordVOs[i];
//			    recentFiveScore+=vo.getScore();
//			    recentReboundNum+=vo.getReboundNum();
//			    recentAssistNum+=vo.getAssistNum();
//		    }
//		    double beforeRecentFiveScore=allscore-recentFiveScore;
//		    double beforeRecentReboundNum=allreboundNum-recentReboundNum;
//		    double beforeRecentAssistNum=allassistNum-recentAssistNum;
//		    recentFiveMatchesScoreUpRate=((recentFiveScore/5)-beforeRecentFiveScore/(playedGames-5))/(beforeRecentFiveScore/(playedGames-5));
//		    recentFiveMatchesReboundUpRate=((recentReboundNum/5)-beforeRecentReboundNum/(playedGames-5))/(beforeRecentReboundNum/(playedGames-5));
//		    recentFiveMatchesAssistUpRate=((recentAssistNum/5)-beforeRecentAssistNum/(playedGames-5))/(beforeRecentAssistNum/(playedGames-5));
//		}
//		
//		GmScEfficiencyValue = allscore
//				+ 0.4
//				* allshootHitNum
//				- 0.7
//				* allshootAttemptNum
//				- 0.4
//				* (allfreeThrowAttemptNum - allfreeThrowHitNum)
//				+ 0.7 * alloffenReboundNum + 0.3
//				* alldefenReboundNum + allstealNum + 0.7
//				* allassistNum + 0.7 * allblockNum - 0.4
//				* allfoulNum - allturnOverNum;
//		trueHitRate= (double) allscore
//				/ (2 * (allshootAttemptNum + 0.44 * allfreeThrowAttemptNum));
//		shootEfficiency = (double) (allshootHitNum + 0.5 * allthreeHitNum)
//				/ allshootAttemptNum;
//		for(Integer id:matchIDs){
//			MatchVO match=matches.get(id);
//			allMatchTime+=match.getMatchTime();
//			if(isVisitingTeam.get(id)==true){
//			    teamOffenReboundNum+=match.getVisitingOffenReboundNum();
//			    dsOffenReboundNum+=match.getHomeOffenReboundNum();
//			    teamDefenReboundNum+=match.getVisitingDefenReboundNum();
//			    dsDefenReboundNum+=match.getHomeDefenReboundNum();
//			    teamShootHitNum+=match.getVisitingShootHitNum();
//			    dsTwoAttemptNum+=match.getHomeShootAttemptNum()-match.getHomeThreeAttemptNum();
//			    teamShootAttemptNum+=match.getVisitingShootAttemptNum();
//			    teamFreeThrowAttemptNum+=match.getVisitingFreeThrowAttemptNum();
//			    teamTurnOverNum+=match.getVisitingTurnOverNum();
//			    dsOffenRoundNum+=match.getHomeShootAttemptNum()+0.4*match.getHomeFreeThrowAttemptNum()
//			    		-1.07
//			    		*((double)match.getHomeOffenReboundNum()/(match.getHomeOffenReboundNum()+match.getVisitingDefenReboundNum())*(match.getHomeShootAttemptNum()-match.getHomeShootHitNum()))+1.07*match.getHomeTurnOverNum();
//			}
//			else{
//				 teamOffenReboundNum+=match.getHomeOffenReboundNum();
//				 dsOffenReboundNum+=match.getVisitingOffenReboundNum();
//				 teamDefenReboundNum+=match.getHomeDefenReboundNum();
//				 dsDefenReboundNum+=match.getVisitingDefenReboundNum();
//				 teamShootHitNum+=match.getHomeShootHitNum();
//				 dsTwoAttemptNum+=match.getVisitingShootAttemptNum()-match.getVisitingThreeAttemptNum();
//				 teamShootAttemptNum+=match.getHomeShootAttemptNum();
//				 teamFreeThrowAttemptNum+=match.getHomeFreeThrowAttemptNum();
//				 teamTurnOverNum+=match.getHomeTurnOverNum();
//				 dsOffenRoundNum+=match.getVisitingShootAttemptNum()+0.4*match.getVisitingFreeThrowAttemptNum()
//				    	-1.07
//				    	*((double)match.getVisitingOffenReboundNum()/(match.getVisitingOffenReboundNum()+match.getHomeDefenReboundNum())*(match.getVisitingShootAttemptNum()-match.getVisitingShootHitNum()))+1.07*match.getVisitingTurnOverNum();
//
//			}
//		}
//		 teamReboundNum=teamOffenReboundNum+teamDefenReboundNum;
//		 dsReboundNum=dsOffenReboundNum+dsDefenReboundNum;
//
//		reboundRate = allreboundNum * allMatchTime
//				/ allpresentTime
//				/ (teamReboundNum + dsReboundNum);
//		offenReboundRate = alloffenReboundNum * allMatchTime
//				/ allpresentTime
//				/ (teamOffenReboundNum + dsOffenReboundNum);
//		defenReboundRate = alldefenReboundNum * allMatchTime
//				/ allpresentTime
//				/ (teamDefenReboundNum + dsDefenReboundNum);
//		assistRate = allassistNum
//				/( allpresentTime / allMatchTime
//						* teamShootHitNum - allshootHitNum);
//		stealRate = (double)allassistNum* allMatchTime
//				/ allpresentTime
//				/ dsOffenRoundNum;
//		blockRate = allblockNum * allMatchTime
//				/allpresentTime
//				/ dsTwoAttemptNum;
//		turnOverRate = allturnOverNum
//				/ (allshootAttemptNum- allthreeAttemptNum
//						+ 0.4 * allfreeThrowAttemptNum+ allturnOverNum);
//		usageRate = (allshootAttemptNum + 0.44
//				* allfreeThrowAttemptNum + allturnOverNum)
//				* allMatchTime
//				/ allpresentTime
//				/ (teamShootAttemptNum + 0.44
//						* teamFreeThrowAttemptNum + teamTurnOverNum);
//		score_rebound_assist=(allscore+allreboundNum+allassistNum)/3/playedGames;
//		doubleDoubleNum=alldoubleDoubleNum/playedGames;	
//		
//		player.setName(name);
//		player.setOwingTeam(owingTeam);
//		player.setPlayedGames(playedGames);
//		player.setGameStartingNum(gameStartingNum);
//		player.setReboundNum(reboundNum);
//		player.setAssistNum(assistNum);
//		player.setPresentTime(presentTime);
//		player.setShootHitRate(shootHitRate);
//		player.setThreeHitRate(threeHitRate);
//		player.setFreeThrowHitRate(freeThrowHitRate);
//		player.setOffenReboundNum(offenReboundNum);
//		player.setDefenReboundNum(defenReboundNum);
//		player.setStealNum(stealNum);
//		player.setBlockNum(blockNum);
//		player.setFoulNum(foulNum);
//		player.setTurnOverNum(turnOverNum);
//		player.setScore(score);
//		player.setEfficiency(efficiency);
//		player.setRecentFiveMatchesScoreUpRate(recentFiveMatchesScoreUpRate);
//		player.setRecentFiveMatchesReboundUpRate(recentFiveMatchesReboundUpRate);
//		player.setRecentFiveMatchesAssistUpRate(recentFiveMatchesAssistUpRate);
//		player.setGmScEfficiencyValue(GmScEfficiencyValue);
//		player.setTrueHitRate(trueHitRate);
//		player.setShootEfficiency(shootEfficiency);
//		player.setReboundRate(reboundRate);
//		player.setOffenReboundRate(offenReboundRate);
//		player.setDefenReboundRate(defenReboundRate);
//		player.setAssistRate(assistRate);
//		player.setStealRate(stealRate);
//		player.setBlockRate(blockRate);
//		player.setTurnOverRate(turnOverRate);
//		player.setUsageRate(usageRate);
//		player.setScore_rebound_assist(score_rebound_assist);
//		player.setDoubleDoubleNum(doubleDoubleNum);
//		result.add(player);
//		
//		playerSeason.setShootHitRate(shootHitRate);
//		playerSeason.setThreeHitRate(threeHitRate);
//		playerSeason.setFreeThrowHitRate(freeThrowHitRate);
//		playerSeason.setEfficiency(efficiency);
//		playerSeason.setRecentFiveMatchesScoreUpRate(recentFiveMatchesScoreUpRate);
//		playerSeason.setRecentFiveMatchesReboundUpRate(recentFiveMatchesReboundUpRate);
//		playerSeason.setRecentFiveMatchesAssistUpRate(recentFiveMatchesAssistUpRate);
//		playerSeason.setGmScEfficiencyValue(GmScEfficiencyValue);
//		playerSeason.setTrueHitRate(trueHitRate);
//		playerSeason.setShootEfficiency(shootEfficiency);
//		playerSeason.setReboundRate(reboundRate);
//		playerSeason.setOffenReboundRate(offenReboundRate);
//		playerSeason.setDefenReboundRate(defenReboundRate);
//		playerSeason.setAssistRate(assistRate);
//		playerSeason.setStealRate(stealRate);
//		playerSeason.setBlockRate(blockRate);
//		playerSeason.setTurnOverRate(turnOverRate);
//		playerSeason.setUsageRate(usageRate);
//		playerSeason.setScore_rebound_assist((allscore+allreboundNum+allassistNum)/3);
//	 }
//	
//	
//	}
//	Collections.sort(result, new SequenceOfPlayer());
//	return result;
//}
	
}

