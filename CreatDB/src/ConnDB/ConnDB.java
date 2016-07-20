package ConnDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnDB {
	
	private static String URL = "jdbc:mysql://localhost:3306/search3?characterEncoding=utf8";
	private static String problem = "problem";
	private static String knowledge = "knowledge";
	private static String report = "report";
	private static String knowledge_report = "knowledge_report";
	private static String problem_report = "problem_report";
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultset = null;
	
	public ConnDB(String username, String password){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(URL, username, password);
			statement = connection.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void Creat(String SQL){
		try{
			statement.executeUpdate(SQL);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void Insert(String SQL)
	{ 
		try{
			statement.executeUpdate(SQL);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ResultSet SearchKidFromKname(String kname){
		
		String SQL = "select kid from knowledge where kcname = " + "\'" + kname + "\'";
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = statement.executeQuery(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	public void Close() {
		try {
			if (resultset != null) {
				resultset.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
