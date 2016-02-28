package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vo.LiveMatchDetailVO;
import SQLHelper.FileList;
import SQLHelper.SqlManager;
import dataservice.LiveTxtDataService;

public class LiveTxtData implements LiveTxtDataService {
	private Connection connection = null;
	private Statement sql = null;
	private ResultSet resultSet = null;
	private int partNum = -1;// 比赛总节数 ！！！注意最后导入数据库时导的总节数不是从vo中拿的，而是使用此变量
	static int count=0;

	public static void main(String[] args) {
		LiveTxtData liveTxtData = new LiveTxtData();
		liveTxtData.exportToSql();
//		System.out.println(liveTxtData.getLiveTxt("14-15", "01-01", "CHA-HOU").size());

	}

	public ArrayList<LiveMatchDetailVO> getLiveTxt(String season, String date,
			String teams) {
		// TODO 自动生成的方法存根
		ArrayList<LiveMatchDetailVO> result = new ArrayList<LiveMatchDetailVO>();
		try {
			connection = SqlManager.getConnection();
			sql = connection.createStatement();
			String query = "select * from matchLive where season ='" + season
					+ "' and date ='" + date + "' and teams ='" + teams + "'";
			resultSet = sql.executeQuery(query);
			while (resultSet.next()) {
				int partNum = resultSet.getInt("partNum");
				int part = resultSet.getInt("part");
				String content = resultSet.getString("content");
				LiveMatchDetailVO vo = new LiveMatchDetailVO(season, date,
						teams, part, partNum, content);
				result.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeMySql();
		}

		return result;
	}

	/**
	 * 从直播数据文件中创建对象的列表，一个文件名的每条记录存储一个对象
	 * 
	 * @param fileName
	 *            直播数据的文件名
	 * @return 除了总节数不对的VO对象列表！！！注意最后导入数据库时导的总节数不是从vo中拿的，而是使用partNum这个全局变量
	 */
	private ArrayList<LiveMatchDetailVO> readFromFiles(String fileName) {
		ArrayList<LiveMatchDetailVO> liveList = new ArrayList<LiveMatchDetailVO>();
		String season = null;// 赛季
		String date = null;// 日期
		String teams = null;// 对阵队伍 客队-主队
		int part = -1;// 比赛节数
		String content = null;// 直播的详细内容

		String title = fileName.split("matchLive")[1].substring(1);
		String[] detailTitle = title.split("_");

		season = detailTitle[0];
		date = detailTitle[1];
		teams = detailTitle[2].replace(".txt", "");

		try {
			boolean isPart = true;
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				if (isPart) {
					part = Integer.parseInt(temp);
					isPart = false;
					temp = br.readLine();
					continue;
				}
				content = temp;
				temp = br.readLine();
				try {
					Integer.parseInt(temp);
					isPart = true;
				} catch (Exception e) {
					// TODO: handle exception
				}
				LiveMatchDetailVO liveMatchDetailVO = new LiveMatchDetailVO(
						season, date, teams, part, partNum, content);
				liveList.add(liveMatchDetailVO);
			}

			partNum = part;
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return liveList;
	}

	/**
	 * 得到比赛直播的文件名
	 * 
	 * @return 比赛直播的文件名列表
	 */
	private ArrayList<String> getMatchLiveFiles() {
		ArrayList<String> files = new ArrayList<String>();
		try {
			FileList fl = new FileList("src/data/matchLive");
			files = fl.getList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return files;
	}

	public void addToSql(String s){
		try {
			connection = SqlManager.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preStatement = connection
					.prepareStatement("INSERT INTO matchLive VALUES(?,?,?,?,?,?,?)");
			preStatement.setString(count, s);
			connection.commit();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			closeMySql();
		}
	}
	
	public void exportToSql() {
		ArrayList<String> files = new ArrayList<String>();
		files = getMatchLiveFiles();
		// create table matchLive
		try {
			connection = SqlManager.getConnection();

			sql = connection.createStatement();
			sql.execute("drop table if exists matchLive");

			sql.execute("create table matchLive(matchLiveID int not null auto_increment,"
					+ "season varchar(20) not null default 'null',"
					+ "date varchar(20) not null default 'null',"
					+ "teams varchar(20) not null default 'null',"
					+ "partNum int not null default 0,"
					+ "part int not null default 0,"
					+ "content varchar(100) not null default 'null',"
					+ "primary key(matchLiveID));");
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeMySql();
		}

		// export data
		int countID = 1;
		try {
			connection = SqlManager.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preStatement = connection
					.prepareStatement("INSERT INTO matchLive VALUES(?,?,?,?,?,?,?)");
			for (String fileName : files) {
				ArrayList<LiveMatchDetailVO> liveList = new ArrayList<LiveMatchDetailVO>();
				liveList = readFromFiles(fileName);
				for (LiveMatchDetailVO vo : liveList) {

					preStatement.setInt(1, countID++);
					preStatement.setString(2, vo.getSeason());
					preStatement.setString(3, vo.getDate());
					preStatement.setString(4, vo.getTeams());
					preStatement.setInt(5, partNum);// !!!
					preStatement.setInt(6, vo.getPart());
					preStatement.setString(7, vo.getContent());

					preStatement.addBatch();
					System.out.println(countID);
				}
			}
			preStatement.executeBatch();
			connection.commit();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			closeMySql();
		}

	}

	private void closeMySql() {
		try {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
			if (sql != null) {
				sql.close();
				sql = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
