package BookSystem;

import java.sql.*;

//to connect with the sql database id26346745;

public class ServerDatabase {
		Statement stmt = null;
		Connection conn = null;
		
		public ServerDatabase(){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/id26346745?user=fit5183a1");
				stmt = conn.createStatement();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
			
		
		public void closeConn()
		{
			try{
				if(stmt != null) {
					stmt.close();
					stmt = null;
				}
				if(conn != null) {
					conn.close();
					conn = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}
	

