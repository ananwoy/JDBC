package com.caps.jdbc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import com.mysql.jdbc.Driver;

public class UpdatePassword {
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter reg no to change password");
		int regno=sc.nextInt();
		System.out.println("enter old password");
		String opass=sc.next();
		System.out.println("enter new password");
		String pass=sc.next();
		FileReader fr=null;
		try {
			fr = new FileReader("D:/dbprop/db.properties");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(fr);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			//2. get the DB connection via Driver
			String dbUrl = "jdbc:mysql://localhost:3306/caps50_db";
			con = DriverManager.getConnection(dbUrl,prop);
			
			//3. Issue the SQL query via connection
			String query = "UPDATE person_otherinfo SET password=? where regno=? and password=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, pass);
			pstmt.setInt(2, regno);
			pstmt.setString(3, opass);
			int count=pstmt.executeUpdate();
			if(count>0)
				System.out.println("password changed succesfully");
			else
				System.out.println("Please enter correct regno/password");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			sc.close();
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}