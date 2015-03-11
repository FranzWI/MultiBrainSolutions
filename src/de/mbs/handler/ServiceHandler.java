package de.mbs.handler;

import de.mbs.abstracts.db.DatabaseView;
import de.mbs.abstracts.mail.MailView;
import de.mbs.db.elasticsearch.ElasticsearchView;
import de.mbs.db.java.JavaView;
import de.mbs.mail.sendgrid.SendGridView;

public class ServiceHandler {

	private static DatabaseView dbview;
	private static MailView mailview;
	
	public static void setDatabaseView(DatabaseView dbview){
		ServiceHandler.dbview = dbview;
	}
	
	public static DatabaseView getDatabaseView(){
		if(dbview == null){
			dbview = new ElasticsearchView();
		}
		return dbview;
	}

	public static MailView getMailView() {
		if(mailview == null){
			//mailview = new SendGridView();
		}
		return mailview;
	}

	public static void setMailView(MailView mailview) {
		ServiceHandler.mailview = mailview;
	}
	
}
