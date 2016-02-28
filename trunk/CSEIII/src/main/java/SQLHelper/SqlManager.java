package SQLHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlManager {
	public static Connection getConnection() throws SQLException,
			java.lang.ClassNotFoundException {
		// String url = "jdbc:mysql://localhost:3306/nba";
		// try {
		// Class.forName("com.mysql.jdbc.Driver");
		// } catch (Exception e) {
		// // TODO: handle exception
		// System.out.println("无法加载jdbc");
		// }
		//
		// String userName = "root";
		// String password = "12345a";
		//
		// Connection c = DriverManager.getConnection(url,userName,password);
	
		String driverName = "com.mysql.jdbc.Driver";
		String userName = "root";
		String userPasswd = "12345a";
		String dbName = "nba";
		String url = "jdbc:mysql://localhost/" + dbName + "?user=" + userName
				+ "&password=" + userPasswd
				+ "&useUnicode=true&characterEncoding=UTF-8";

		try {
			Class.forName(driverName).newInstance();
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Connection c= (Connection) DriverManager.getConnection(url);

		return c;
	}

}
