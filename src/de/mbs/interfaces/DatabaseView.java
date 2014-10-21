package de.mbs.interfaces;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import de.mbs.mail.Mail;

/**
 * 
 * @author mkuerbis
 * 
 * Sicht auf die Datenbank
 *
 */
public abstract class DatabaseView {
	
	protected MailView mailView;
	protected static LinkedList<Mail> mailQueue = new LinkedList<Mail>();
	protected static LinkedList<Mail> htmlMailQueue = new LinkedList<Mail>();
	
	public DatabaseView(){
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					Mail m = null;
					if(DatabaseView.this.mailView == null || !DatabaseView.this.mailView.isRunning()){
						//TOOD Mails in DB Aufnehmen
					}else{
						while((m = mailQueue.poll()) != null){
							DatabaseView.this.mailView.sendMail(m);
						}
						while((m = htmlMailQueue.poll()) != null){
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
		t.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(){

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.println("Unbehandelte Ausnahme (Mail Thread): ");
				e.printStackTrace();
			}
			
		});
		t.start();
	}
	
	public void setMailView(MailView mailView){
		this.mailView = mailView;
	}
	
	public void sendMail(Mail m){
		DatabaseView.mailQueue.add(m);
	}
	
	public void sendHtmlMail(Mail m){
		DatabaseView.htmlMailQueue.add(m);
	}
	
	public abstract String getServiceName();
	
	public abstract Vector<String> getDatabases();
	
	public abstract Map<String, Long> getDatabaseSize();
	
	public abstract long getEntryCount(String database);
	
	public abstract boolean isRunning();
	
	public abstract boolean exit();
	
}
