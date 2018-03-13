import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class UserValidation {
	public String validateUser(String usern,String userp){
		String utype = null;
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
			String dbUrl = "jdbc:mysql://localhost:3306/caps_asset";
			con = DriverManager.getConnection(dbUrl,prop);
			
			//3. Issue the SQL query via connection
			String query = "SELECT * from User_Master where username=? and userpassword=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, usern);
			pstmt.setString(2, userp);
			ResultSet res=pstmt.executeQuery();
			if(res.next())
			{
				int userid=res.getInt("userid");
				utype=res.getString("usertype");
				System.out.println("User "+userid+" logged in successfully as "+utype);
			}
			else
				System.out.println("Please enter correct username/password");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
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
		return utype;
	}
	public void addAsset(Asset aid)
	{
		int assetid=aid.getAssetid();
		String assetname=aid.getAssetname();
		String assetdes=aid.getAssetdes();
		int quantity=aid.getQuantity();
		String status=aid.getStatus();
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
			String dbUrl = "jdbc:mysql://localhost:3306/caps_asset";
			con = DriverManager.getConnection(dbUrl,prop);
			
			//3. Issue the SQL query via connection
			String query = "INSERT into asset values(?,?,?,?,?)";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, assetid);
			pstmt.setString(2, assetname);
			pstmt.setString(3, assetdes);
			pstmt.setInt(4, quantity);
			pstmt.setString(5, status);
			int count=pstmt.executeUpdate();
			if(count>0)
			{
				System.out.println("Asset details inserted succesfully");
			}
			else
				System.out.println("Asset details couldnot be inserted");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
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