package de.mbs.handler;

import de.mbs.interfaces.DatabaseView;
import de.mbs.interfaces.MailView;

public class ServiceHandler {

	private static DatabaseView dbview;
	
	public static void setDatabaseView(DatabaseView dbview){
		ServiceHandler.dbview = dbview;
	}
	
	public static DatabaseView getDatabaseView(){
		return dbview;
	}
	
}
