package de.mbs.handler;

import de.mbs.interfaces.DatabaseView;
import de.mbs.interfaces.MailView;

public class ServiceHandler {

	private static DatabaseView dbview;
	private static MailView mailview;
	
	public static void setDatabaseView(DatabaseView dbview){
		ServiceHandler.dbview = dbview;
	}
	
	public static DatabaseView getDatabaseView(){
		return dbview;
	}

	public static MailView getMailView() {
		return mailview;
	}

	public static void setMailView(MailView mailview) {
		ServiceHandler.mailview = mailview;
	}
	
}
