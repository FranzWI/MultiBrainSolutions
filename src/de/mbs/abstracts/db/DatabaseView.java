package de.mbs.abstracts.db;

import groovy.xml.MarkupBuilder;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import de.mbs.abstracts.db.views.GroupView;
import de.mbs.abstracts.db.views.MessageView;
import de.mbs.abstracts.db.views.NotificationView;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.abstracts.db.views.SettingsView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.abstracts.db.views.definition.SearchableView;
import de.mbs.abstracts.mail.MailView;
import de.mbs.abstracts.mail.definition.Mail;
import de.mbs.modules.ModulContainer;

/**
 * 
 * @author mkuerbis
 * 
 *         Sicht auf die Datenbank
 *
 */
public abstract class DatabaseView {

	protected MailView mailView;
	protected static LinkedList<Mail> mailQueue = new LinkedList<Mail>();
	protected static LinkedList<Mail> htmlMailQueue = new LinkedList<Mail>();
	protected static Vector<SearchableView> searchable = new Vector<SearchableView>();
	
	public DatabaseView() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Mail m = null;
					if (DatabaseView.this.mailView == null
							|| !DatabaseView.this.mailView.isRunning()) {
						// TODO Mails in DB Aufnehmen
						// da sie scheinbar aktuell nich versendet werden können
						// später versenden
					} else {
						while ((m = mailQueue.poll()) != null) {
							DatabaseView.this.mailView.sendMail(m);
						}
						while ((m = htmlMailQueue.poll()) != null) {
							DatabaseView.this.mailView.sendHtmlMail(m);
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});
		t.setName("Multi Brain Mail Thread");
		t.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.println("Unbehandelte Ausnahme (Mail Thread): ");
				e.printStackTrace();
			}

		});
		t.setDaemon(true);
		t.start();
	}

	public void addSearchableView(SearchableView view){
		this.searchable.add(view);
	}
	
	public Vector<SearchableView> getSearchable(){
		return this.searchable;
	}
	
	public abstract MarkupBuilder search(String search, MarkupBuilder builder);
	
	public void setMailView(MailView mailView) {
		this.mailView = mailView;
	}

	public void sendMail(Mail m) {
		DatabaseView.mailQueue.add(m);
	}

	public void sendHtmlMail(Mail m) {
		DatabaseView.htmlMailQueue.add(m);
	}

	public abstract String getServiceName();

	public abstract Vector<String> getDatabases();

	public abstract Map<String, Long> getDatabaseSize();

	public abstract long getEntryCount(String database);

	public abstract boolean isRunning();

	public abstract boolean exit();

	public abstract UserView getUserView();

	public abstract PortletView getPortletView();

	public abstract GroupView getGroupView();
	
	public abstract MessageView getMessageView();
	
	public abstract SettingsView getSettingsView();
	
	public abstract NotificationView getNotificationView();
	
	public ModulContainer getModulContainer(){
		return ModulContainer.initialise();
	}

}
