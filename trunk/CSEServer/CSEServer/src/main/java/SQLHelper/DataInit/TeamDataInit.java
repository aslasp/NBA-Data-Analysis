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

import SQLHelper.SqlManager;
import po.TeamPO;

/**
 * 
 * 从文件中读取数据，用于将球队的基本数据存储到数据库中 表名：teams
 *
 */
public class TeamDataInit {
	public ArrayList<String[]> readFromFile(String fileName) {

		ArrayList<String[]> result = new ArrayList<String[]>();

		String[] content;
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				content = new String[7];
				if (temp.contains("│")) {
					String[] it = temp.split("│");
					String[] first = it[0].split("║");
					content[0] = first[1].trim();
					content[1] = it[1].trim();
					content[2] = it[2].trim();
					content[3] = it[3].trim();
					content[4] = it[4].trim();
					content[5] = it[5].trim();
					String[] last = it[6].split("║");
					content[6] = last[0].trim();
					result.add(content);
				}
				temp = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<TeamPO> getTeamListFromFile() {
		String name;
		String location;
		String abLocation;
		String conference;
		String partition;
		String homeCourt;
		int setUpTime;

		ArrayList<TeamPO> teams = new ArrayList<TeamPO>();
		TeamPO team;
		ArrayList<String[]> result = readFromFile("src/data/teams/teams");
		for (String[] content : result) {
			name = content[0];
			abLocation = content[1];
			location = content[2];
			conference = content[3];
			partition = content[4];
			homeCourt = content[5];
			setUpTime = Integer.parseInt(content[6]);

			team = new TeamPO(0, name, abLocation, location, conference,
					partition, homeCourt, setUpTime);
			teams.add(team);
		}
		return teams;
	}

	public void exportToSql() {
		ArrayList<TeamPO> teams = getTeamListFromFile();
		try {
			Connection con = SqlManager.getConnection();
			con.setAutoCommit(false);
			Statement sql = con.createStatement();
			sql.execute("drop table if exists teams");
			sql.execute("create table teams(teamID int not null auto_increment,teamName varchar(40) not null default 'null',"
					+ "abLocation varchar(20) not null default 'null',location varchar(20) not null default 'null',"
					+ "conference varchar(20) not null default 'null',partition varchar(20) not null default 'null',"
					+ "homeCourt varchar(40) not null default 'null',setUpTime int not null default 0,primary key(teamID));");
			int count = 1;
			sql.close();
			PreparedStatement statement = con.prepareStatement("INSERT INTO teams VALUES(?, ?,?,?,?,?,?,?)"); 
			for (TeamPO team : teams) {
				statement.setInt(1, count++);
				statement.setString(2, team.getTeamName());
				statement.setString(3, team.getAbLocation());
				statement.setString(4, team.getLocation());
				statement.setString(5, team.getConference());
				statement.setString(6, team.getPartition());
				statement.setString(7, team.getHomeCourt());
				statement.setInt(8, team.getSetUpTime());
				statement.addBatch();
			/*	sql.execute("insert teams values(" + (count++) + ",'"
						+ team.getTeamName() + "','" + team.getAbLocation()
						+ "','" + team.getLocation() + "','"
						+ team.getConference() + "','" + team.getPartition()
						+ "','" + team.getHomeCourt() + "',"
						+ team.getSetUpTime() + ")");
			*/

				System.out.println(count);
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
		TeamDataInit teamDataReader = new TeamDataInit();
		teamDataReader.exportToSql();
	}
}
