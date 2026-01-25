package com.foodwaste.util;

import java.sql.*;

public class DBConnection {
	
	private static final String URL="jdbc:mysql://localhost:3306/FoodDB?useSSL=false&serverTimezone=UTC";
	private static final String USER="root";
	private static final String PASSWORD="Sachin@2005";
	
	public static Connection getConnection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

}
