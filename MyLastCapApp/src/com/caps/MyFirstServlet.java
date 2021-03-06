package com.caps;

import java.io.FileReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/show")
public class MyFirstServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		int regno;
		String fname,lname;
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		//get jdbc connection
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		FileReader fr=new FileReader("D:/dbprop/db.properties");
		Properties prop=new Properties();
		prop.load(fr);
		String dburl="jdbc:mysql://localhost:3306/caps50_db";
		try {
			con=DriverManager.getConnection(dburl,prop);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//issue query
		String query="SELECT * from person_info";
		try {
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next())
			{
				regno = rs.getInt(1);
				fname=rs.getString(2);
				lname=rs.getString(3);
				out.println("<h3>"+regno+". "+fname+" "+lname+"</h3>");
				out.println("<br>**************************</br>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		
		out.print("<h2>The time is: "+new Date()+"</h2>");
		
	}
}
