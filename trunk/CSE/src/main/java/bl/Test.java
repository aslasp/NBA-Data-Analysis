package bl;

import bl.team.Team;

//class a extends Thread {
//	
//	NewFinalTeam team = new NewFinalTeam();
//	public void run() {
//		while (true) {
//			System.out.println(team.getTeamAverageInfo().get(3).getAbLocation()
//					+ " " + team.getTeamAverageInfo().get(3).getScore());
//			try {
//				this.sleep(2000);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public void startThread() {
//		this.start();
//	}
//}

public class Test {

	public static void main(String[] args) {
		// a a = new a();
		// a.startThread();

		// Map<String, Map<String, Integer>> map = new HashMap<String,
		// Map<String, Integer>>();
		// Map<String, Integer> map1 = new HashMap<String, Integer>();
		// map1.put("a", 1);
		// Map<String, Integer> map2 = new HashMap<String, Integer>();
		// map2.put("c", 1);
		// map.put("aa", map1);
		// map.put("bb", map2);
		// map2.put("b", 2);
		//
		// Map<String, Integer> map3 = new HashMap<String, Integer>();
		// map3=map.get("aa");
		// map3.put("c", map2.get("c"));
		// System.out.println(map3.size());
		//
		int count = 0;
		// for (int i = 0; i < 100; i++) {
		// long t1 = System.currentTimeMillis();
		// FinalTeam newTeam = new FinalTeam();
		// newTeam.getTeamSeasonInfo("13-14");
		long t2 = System.currentTimeMillis();

		Team team = new Team();
		team.getOrderedTeamsBySeason("13-14", "score", "null", 5);
		long t3 = System.currentTimeMillis();
		// System.out.println(t2 - t1);
		System.out.println(t3 - t2);
		// if ((t2 - t1) >= (t3 - t2)) {
		// count++;
		// }
		// }
		// System.out.println("运行100次，有" + count + "次NewFinalTeam快");

		// new Thread() {
		// int count = 0;
		//
		// public void run() {
		// while (count < 100) {
		// System.out.println("get data");
		// Match match = new Match();
		// ArrayList<MatchVO> result = match.getMatchData("全部", "全部",
		// "全部", "全部");
		// System.out.println(result.size());
		// count++;
		// try {
		// this.sleep(5000);// 话说 能不能不睡
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }
		// }.start();

		// int equal = 0;
		// int count = 0;
		//
		// for (int i = 0; i < 100; i++) {
		// long t1 = System.currentTimeMillis();
		// NewNewPlayer newNewPlayer = new NewNewPlayer();
		// newNewPlayer.getPlayerAverageInfo();
		// long t2 = System.currentTimeMillis();
		// NewPlayer newPlayer = new NewPlayer(3);
		// newPlayer.getPlayerAverageInfo();
		// long t3 = System.currentTimeMillis();
		// if ((t3 - t2) > (t2 - t1)) {
		// count++;
		// }
		// if ((t3 - t2) == (t2 - t1)) {
		// equal++;
		// }
		// }
		// System.out.println("------------------------------");
		// System.out.println("newNewPlayer比newPlayer快"+count+"次");
		// System.out.println(equal+"次二者相等");
		// NewNewPlayer newNewPlayer = new NewNewPlayer();
		// newNewPlayer.getPlayerSeasonInfo("13-14","Andre Drummond");
		// newNewPlayer.getPlayerSeasonInfo("13-14", "Andre Drummond");
		// long t1 = System.currentTimeMillis();
		// newNewPlayer.getPlayerSeasonInfo("13-14", "Andre Drummond");
		// long t2 = System.currentTimeMillis();
		// newNewPlayer.getPlayerSeasonInfo("13-14","Andre Drummond");
		//
		// long t3 = System.currentTimeMillis();
		// if ((t3 - t2) < (t2 - t1)) {
		// count++;
		// }
		// if ((t3 - t2) == (t2 - t1)) {
		// equal++;
		// }
		// System.out.println(t2-t1+"ms");
		// System.out.println(t3-t2+"ms");
		//
		// }
		//
		// System.out.println("------------------------------");
		// System.out.println("1比2快"+count+"次");
		// System.out.println(equal+"次二者相等");
		// int equal = 0;
		// int count = 0;
		//
		// for (int i = 0; i < 100; i++) {
		// long t1 = System.currentTimeMillis();
		// NewNewPlayer newNewPlayer = new NewNewPlayer();
		// newNewPlayer.getPlayerAverageInfo();
		// long t2 = System.currentTimeMillis();
		// NewPlayer newPlayer = new NewPlayer(3);
		// newPlayer.getPlayerAverageInfo();
		// long t3 = System.currentTimeMillis();
		// if ((t3 - t2) > (t2 - t1)) {
		// count++;
		// }
		// if ((t3 - t2) == (t2 - t1)) {
		// equal++;
		// }
		// System.out.println("------------------------------");
		// System.out.println("newNewPlayer比newPlayer快"+count+"次");
		// System.out.println(equal+"次二者相等");
		// NewNewPlayer newNewPlayer = new NewNewPlayer();
		// newNewPlayer.getPlayerSeasonInfo("13-14","Andre Drummond");
		// newNewPlayer.getPlayerSeasonInfo("13-14", "Andre Drummond");
		// long t1 = System.currentTimeMillis();
		// newNewPlayer.getPlayerSeasonInfo("13-14", "Andre Drummond");
		// long t2 = System.currentTimeMillis();
		// newNewPlayer.getPlayerSeasonInfo("13-14","Andre Drummond");
		//
		// long t3 = System.currentTimeMillis();
		// if ((t3 - t2) < (t2 - t1)) {
		// count++;
		// }
		// if ((t3 - t2) == (t2 - t1)) {
		// equal++;
		// }
		// System.out.println(t2-t1+"ms");
		// System.out.println(t3-t2+"ms");
		//
		// }
		//
		// System.out.println("------------------------------");
		// System.out.println("1比2快"+count+"次");
		// System.out.println(equal+"次二者相等");
	}
}
