package de.mbs.handler;

import de.mbs.abstracts.db.DatabaseView;
import de.mbs.abstracts.mail.MailView;
import de.mbs.db.java.JavaView;

public class ServiceHandler {

	private static DatabaseView dbview;
	private static MailView mailview;
	
	public static void setDatabaseView(DatabaseView dbview){
		ServiceHandler.dbview = dbview;
	}
	
	public static DatabaseView getDatabaseView(){
		if(dbview == null){
			dbview = new JavaView();
		}
		return dbview;
	}

	public static MailView getMailView() {
		return mailview;
	}

	public static void setMailView(MailView mailview) {
		ServiceHandler.mailview = mailview;
	}
	
}
