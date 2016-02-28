package data;

import java.util.ArrayList;
import java.util.HashMap;

import newui.MyUIDataFormater;
import vo.PlayerVO;

public class PlayerStatistic {
	private ArrayList<Double> X = new ArrayList<Double>();
	private ArrayList<Double> Y = new ArrayList<Double>();

	public static void main(String[] args) {
		PlayerStatistic playerStatistic = new PlayerStatistic();
		System.out.println("在main方法中调用，"
				+ playerStatistic.start().get("foul").get(1));
		playerStatistic.getVariance();
	}

	public ArrayList<Double> testX() {
		PlayerData player = new PlayerData();
		PlayerSalaryData ps = new PlayerSalaryData();
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> y = new ArrayList<Double>();
		ArrayList<PlayerVO> players = player.getPlayerAverageInfo("13-14",
				"Playoff");
		for (int i = 0; i < players.size(); i++) {
			PlayerVO vo = players.get(i);
			double salary = (double) ps.getSalary("14-15", vo.getName());
			if (salary == 0) {
				continue;
			}
			y.add(salary);
			x.add(vo.getGmScEfficiencyValue());
		}
		return x;
	}

	public ArrayList<Double> testY() {
		PlayerData player = new PlayerData();
		PlayerSalaryData ps = new PlayerSalaryData();
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> y = new ArrayList<Double>();
		ArrayList<PlayerVO> players = player.getPlayerAverageInfo("13-14",
				"Playoff");
		for (int i = 0; i < players.size(); i++) {
			PlayerVO vo = players.get(i);
			double salary = (double) ps.getSalary("14-15", vo.getName());
			if (salary == 0) {
				continue;
			}
			y.add(salary / 1000000);
			x.add(vo.getGmScEfficiencyValue());
		}
		return y;
	}

	/**
	 * @return hashMap中key值是 "matchNum","score", "rebound", "assist",
	 *         "efficiency", "GmScEfficiencyValue",
	 *         "score_rebound_assist","steal","foul","block"
	 *         hashMap中value是一个Arraylist，第一个元素是F值，第二个元素是拟合度，第三个元素是回归系数 
	 */
	public HashMap<String, ArrayList<String>> start() {
		HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		PlayerData player = new PlayerData();
		PlayerSalaryData ps = new PlayerSalaryData();
		ArrayList<PlayerVO> players = player.getPlayerAverageInfo("13-14",
				"Playoff");
		String[] type = { "matchNum", "score", "rebound", "assist",
				"efficiency", "GmScEfficiencyValue", "score_rebound_assist",
				"steal", "foul" ,"block"};
		for (int index = 0; index < type.length; index++) {
			String flag = type[index];
			for (int i = 0; i < players.size(); i++) {
				PlayerVO vo = players.get(i);
				double salary = (double) ps.getSalary("14-15", vo.getName());
				if (salary == 0) {
					continue;
				}
				Y.add(salary / 1000000);
				initX(flag, vo);
			}
			ArrayList<String> temp = lineAnalysis();
			result.put(flag, temp);
			X = new ArrayList<Double>();
			Y = new ArrayList<Double>();
		}

		return result;
	}
	
	/**
	 * 预估球员下一赛季的薪水
	 * @param a 回归截距
	 * @param b 回归系数
	 * @param x 上一赛季的场均效率、GmSc效率值或者得分_篮板_助攻（1：1：1）
	 * @return 线性回归预估的下一赛季的薪水
	 */
	public double getEstimatedSalary(double a,double b,double x){
		return calculateY(a, b, x);
	}

	/**
	 * 得到方差汇总表
	 * 
	 * @return 方差汇总表的各项数据
	 */
	public ArrayList<String> getVariance() {
		PlayerData player = new PlayerData();
		PlayerSalaryData ps = new PlayerSalaryData();
		ArrayList<PlayerVO> players = player.getPlayerAverageInfo("13-14",
				"Playoff");
		for (int i = 0; i < players.size(); i++) {
			PlayerVO vo = players.get(i);
			double salary = (double) ps.getSalary("14-15", vo.getName());
			if (salary == 0) {
				continue;
			}
			Y.add(salary / 1000000);
			initX("score_rebound_assist", vo);
		}

		ArrayList<String> result = new ArrayList<String>();
		double xAvg = calculateAvg(getX());
		double yAvg = calculateAvg(getY());
		double b = calculateL(getX(), getY(), xAvg, yAvg)
				/ (double) calculateL(getX(), getX(), xAvg, xAvg);
		double a = yAvg - b * xAvg;
		double Sr = calculateSr(yAvg, a, b);
		double Se = calculateSe(a, b);
		double Vr = Sr / 1;
		double Ve = Se / (double) X.size();
		double F = calculateF(Vr, Ve);

		result.add("回归");
		result.add("随机");
		result.add("总和");

		result.add(MyUIDataFormater.formatTo3(Sr));
		result.add("1");
		result.add(MyUIDataFormater.formatTo3(Vr));
		result.add(MyUIDataFormater.formatTo3(F));
		result.add("3.889");
		result.add("**");

		result.add(MyUIDataFormater.formatTo3(Se));
		result.add((getX().size() - 2) + "");
		result.add(MyUIDataFormater.formatTo3(Ve));
		// result.add("");
		result.add("6.763");
		// result.add("");

		result.add(MyUIDataFormater.formatTo3(Sr + Se));
		result.add("199");
		return result;
	}

	public ArrayList<String> lineAnalysis() {
		ArrayList<String> result = new ArrayList<String>();
		double xAvg = calculateAvg(getX());
		double yAvg = calculateAvg(getY());
		double b = calculateL(getX(), getY(), xAvg, yAvg)
				/ (double) calculateL(getX(), getX(), xAvg, xAvg);
		double a = yAvg - b * xAvg;
		double Sr = calculateSr(yAvg, a, b);
		double Se = calculateSe(a, b);
		double Vr = Sr / 1;
		double Ve = Se / (double) X.size();
		double F = calculateF(Vr, Ve);
		double r2 = Sr / (Sr + Se);
		// double Sy = calculateSy(Se, X.size());
		// System.out.println("样本容量：" + X.size());
		// System.out.println("回归偏差平方和：" + Sr);
		// System.out.println("剩余偏差平方和：" + Se);
		// System.out.println("方差：" + Vr);
		// System.out.println("方差：" + Ve);
		// System.out.println("a:"+a);
		// System.out.println("b:"+b);
		// System.out.println("F值：" + F);
		// System.out.println("拟合度：" + r2);
		// System.out.println("标准残差：" + Sy);
		result.add(MyUIDataFormater.formatTo4(F));
		result.add(MyUIDataFormater.formatTo4(r2));
		result.add(MyUIDataFormater.formatTo3(b));
		return result;
	}

	public void initX(String flag, PlayerVO vo) {
		if (flag.equals("score")) {
			X.add(vo.getScore());
		} else if (flag.equals("rebound")) {
			X.add(vo.getReboundNum());
		} else if (flag.equals("assist")) {
			X.add(vo.getAssistNum());
		} else if (flag.equals("efficiency")) {
			X.add(vo.getEfficiency());
		} else if (flag.equals("GmScEfficiencyValue")) {
			X.add(vo.getGmScEfficiencyValue());
		} else if (flag.equals("score_rebound_assist")) {
			X.add(vo.getScore_rebound_assist());
		} else if (flag.equals("matchNum")) {
			X.add((double) vo.getPlayedGames());
		} else if (flag.equals("steal")) {
			X.add(vo.getStealNum());
		} else if (flag.equals("foul")) {
			X.add(vo.getFoulNum());
		}else if(flag.equals("block")){
			X.add(vo.getBlockNum());
		}

	}

	public double calculateF(double Vr, double Ve) {
		return Vr / Ve;
	}

	public double calculateA(double xAvg, double yAvg, double b) {
		return yAvg - b * xAvg;
	}

	public double calculateB(double lxx, double lxy) {
		return lxy / lxx;
	}

	public double calculateAvg(ArrayList<Double> data) {
		int sum = 0;
		double avg = 0;
		for (int i = 0; i < data.size(); i++) {
			sum += data.get(i);
		}
		avg = sum / (double) data.size();
		return avg;
	}

	public double calculateL(ArrayList<Double> X, ArrayList<Double> Y,
			double xAvg, double yAvg) {
		double result = 0;
		for (int i = 0; i < X.size(); i++) {
			result += (X.get(i) - xAvg) * (Y.get(i) - yAvg);
		}
		return result;
	}

	public static double calculateY(double a, double b, double x) {
		return (a + b * x);
	}

	public double calculateSe(double a, double b) {
		double result = 0;
		for (int i = 0; i < Y.size(); i++) {
			result += (Y.get(i) - calculateY(a, b, X.get(i)))
					* (Y.get(i) - calculateY(a, b, X.get(i)));
		}
		return result;
	}

	public double calculateSr(double yAvg, double a, double b) {
		double result = 0;
		for (int i = 0; i < X.size(); i++) {
			result += (calculateY(a, b, X.get(i)) - yAvg)
					* (calculateY(a, b, X.get(i)) - yAvg);
		}
		return result;
	}

	public double calculateSy(double Se, int n) {
		return Math.sqrt(Se / (double) (n - 2));
	}

	public ArrayList<Double> getX() {
		return X;
	}

	public ArrayList<Double> getY() {
		return Y;
	}

	public void setX(ArrayList<Double> x) {
		X = x;
	}

	public void setY(ArrayList<Double> y) {
		Y = y;
	}
}
