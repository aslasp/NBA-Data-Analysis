package SQLHelper.DataInit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import SQLHelper.FileList;
import SQLHelper.SqlManager;
import po.PlayerPO;

/**
 * 
 * 从文件中读取数据，用于将球员基本数据存储到数据库中 表名：players
 * 
 *
 */
public class PlayerDataInit {
	public PlayerPO readFromFile(String fileName) {
		PlayerPO player;
		String mname;
		int mnumber;
		String mposition;
		String mheight;
		int mweight;
		String mbirth;
		int mage;
		int mexp;
		String mschool;
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

		mname = content[0];
		mnumber = DirtyDataManager.checkNum(fileName, content[1]);
		mposition = content[2];
		mheight = content[3];
		mweight = Integer.parseInt(content[4]);
		mbirth = content[5];
		mage = Integer.parseInt(content[6]);
		mexp = DirtyDataManager.checkExp(fileName, content[7]);
		mschool = content[8];
		player = new PlayerPO(0, mname, mnumber, mposition, mheight, mweight,
				mbirth, mage, mexp, mschool);
		return player;
	}

	public ArrayList<PlayerPO> getPlayerListFromFile() {
		ArrayList<PlayerPO> players = new ArrayList<PlayerPO>();
		try {
			FileList fl = new FileList("src/data/players/info");
			ArrayList<String> names = fl.getList();
			for (String name : names) {
				players.add(readFromFile(name));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return players;
	}

	public void exportToSql() {
		ArrayList<PlayerPO> players = getPlayerListFromFile();
		try {
			Connection con = SqlManager.getConnection();
			con.setAutoCommit(false);
			Statement sql = con.createStatement();
			sql.execute("drop table if exists players");
			sql.execute("create table players(playerID int not null auto_increment,name varchar(40) not null default 'null',"
					+ "number int not null default 0,position varchar(20) not null default 'null',"
					+ "height varchar(20) not null default 'null',weight int not null default 0,"
					+ "birth varchar(20) not null default 'null',age int not null default 0,exp int not null default 0,"
					+ "school varchar(40)not null default 'null',primary key(playerID));");
			sql.close();
			PreparedStatement statement = con.prepareStatement("INSERT INTO players VALUES(?, ?,?,?,?,?,?,?,?,?)");   
			int count = 1;
			for (PlayerPO player : players) {
				statement.setInt(1, count++);
				statement.setString(2, player.getName());
				statement.setInt(3, player.getNumber());
				statement.setString(4, player.getPosition());
				statement.setString(5, player.getHeight());
				statement.setInt(6, player.getWeight());
				statement.setString(7, player.getBirth());
				statement.setInt(8, player.getAge());
				statement.setInt(9, player.getExp());
				statement.setString(10, player.getSchool());
				statement.addBatch();
			/*	sql.execute("insert players values(" + (count++) + ",'"
						+ player.getName() + "'," + player.getNumber() + ",'"
						+ player.getPosition() + "','" + player.getHeight()
						+ "'," + player.getWeight() + ",'" + player.getBirth()
						+ "'," + player.getAge() + "," + player.getExp() + ",'"
						+ player.getSchool() + "')");
			*/
				System.out.println(count-1);
			}

			statement.executeBatch();
			con.commit();
			statement.close();
			con.close();
		} catch (java.lang.ClassNotFoundException e) {
			System.err.println("ClassNotFoundException:" + e.getMessage());
		} catch (SQLException ex) {
			System.err.println("SQLException:" + ex.getMessage());
		}

	}

	public static void main(String[] args) {
		PlayerDataInit playerDataReader = new PlayerDataInit();
		playerDataReader.exportToSql();
	}
}
