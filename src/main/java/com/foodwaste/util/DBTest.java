package com.foodwaste.util;

import java.sql.*;


public class DBTest {
	public static void main(String[]args) {
		try {
			Connection con=DBConnection.getConnection();
			System.out.println("DB successfully connected");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
