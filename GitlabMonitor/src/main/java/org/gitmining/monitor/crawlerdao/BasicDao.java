package org.gitmining.monitor.crawlerdao;

import java.sql.Connection;
import java.sql.DriverManager;

public class BasicDao {
	Connection conn;
	public BasicDao(){
		String driver = "com.mysql.jdbc.Driver"; 
		String url = "jdbc:mysql://localhost:3333/glmonitor?useUnicode=true&characterEncoding=utf-8";    
		String user = "root";
		String password = "";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
