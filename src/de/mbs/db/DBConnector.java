package de.mbs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	
	private final static String username ="";
	private final static String password = "";
		
	public static Connection getCon()
	{
		Connection connection = null;

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
			closeConnection(connection);
		}
		return connection;
	}
	
	public static boolean closeConnection(Connection connection)
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
