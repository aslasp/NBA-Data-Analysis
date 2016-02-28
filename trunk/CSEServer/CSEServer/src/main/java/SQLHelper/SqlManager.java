package SQLHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlManager {
	public static Connection getConnection() throws SQLException,java.lang.ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/test";
        try {
        	Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("无法加载jdbc");
		}
        
        String userName = "root";
        String password = "12345a";
        
        Connection c = DriverManager.getConnection(url,userName,password);
        return c;
  }
	 
}
