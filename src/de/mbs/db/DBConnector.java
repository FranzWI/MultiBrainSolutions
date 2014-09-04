package de.mbs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
		
	static Connection connection;

	public DBConnector(String username, String password) 
	{
		if(connection == null)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost/feedback?user=" +
							 username + "&password=" + password);
			} 
			catch (ClassNotFoundException cnfErr) 
			{
				cnfErr.printStackTrace();
			} 
			catch (SQLException sqlErr) 
			{
				sqlErr.printStackTrace();
			}
			finally
			{
				closeConnection();
			}
		}
		
	}
	
	public static Connection getDBConnection()
	{
		return connection;
	}
	
	public static boolean closeConnection()
	{
		if(connection!=null)
			try 
			{
				connection.close();
				return true;
			} 
			catch (SQLException sqlErr) 
			{
				sqlErr.printStackTrace();
			}
		return false;
	}

}
