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

import SQLHelper.SqlManager;
import dataservice.PlayerIdDataService;

public class PlayerIdData implements PlayerIdDataService{
	
	ArrayList<PlayerMatch> players=new ArrayList<PlayerMatch>();
	Connection con;
	
	public void openSql(){
		try {
			con=SqlManager.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeSql(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
	private void readInfoFromIDFile() {
		int id;
		String name;
		PlayerMatch pm;
		try {
			File file = new File("src/data/players/playerId.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				String[] nit=temp.split(",");
				id=Integer.parseInt(nit[0]);
				name=nit[1];
				pm=new PlayerMatch(id, name);
				players.add(pm);
				temp = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void exportToSql() {
		try {
			readInfoFromIDFile();
			Connection con = SqlManager.getConnection();
			con.setAutoCommit(false);
			Statement sql = con.createStatement();
			sql.execute("drop table if exists playersid");
			sql.execute("create table playersid(no int not null auto_increment,playerID int not null default 0,"
					+ "name varchar(40) not null default 'null',primary key(no));");
			PreparedStatement statement = con
					.prepareStatement("INSERT INTO playersid VALUES(?, ?,?)");
			int count = 1;
			
			for(PlayerMatch player:players) {
					
				statement.setInt(1, count++);
				statement.setInt(2, player.getID());
				statement.setString(3, player.getName());				
				statement.addBatch();
				System.out.println(count - 1);
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


	public String getPlayerName(int PlayerID) {
		
		String name="";
		try {
							
			Statement sql = con.createStatement();
			
			String query = "select * from playersid where playerID="+PlayerID+" limit 1";
			ResultSet resultSet = sql.executeQuery(query);
			
			resultSet.next();
			
			if(resultSet.getRow()==0)
				return "";
			
			name = resultSet.getString("name");
			
			resultSet.close();
			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(PlayerID);
			e.printStackTrace();
		}
		return name;
	}

	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		PlayerIdData playerDataReader = new PlayerIdData();
		playerDataReader.openSql();
		playerDataReader.exportToSql();
		System.out.println(playerDataReader.getPlayerName(76001));
		long end1 = System.currentTimeMillis();
		System.out.println("运行时间：" + (end1 - start) + "毫秒");// 应该是end - start
		System.out.println(playerDataReader.getPlayerName(76002));
		long end2 = System.currentTimeMillis();
		System.out.println("运行时间：" + (end2 - end1) + "毫秒");// 应该是end - start
		System.out.println(playerDataReader.getPlayerName(51));
		long end3 = System.currentTimeMillis();
		System.out.println("运行时间：" + (end3 - end2-3000) + "毫秒");// 应该是end - start
		playerDataReader.closeSql();
	}
	
	class PlayerMatch{
		int id;
		String name;
		
		public PlayerMatch(int id,String name){
			this.id=id;
			this.name=name;
		}
		
		public int getID(){
			return id;
		}
		
		public String getName(){
			return name;
		}
				
	}
	
}
