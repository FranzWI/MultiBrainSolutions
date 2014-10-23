package de.mbs.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import de.mbs.abstracts.mail.MailView;
import de.mbs.db.elasticsearch.ElasticsearchView;
import de.mbs.handler.ServiceHandler;
import de.mbs.mail.sendgrid.SendGridView;

public class ServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 7913911436790432794L;
	
	@Override
	public void init() throws ServletException {
		System.out.println("Starte Datenbankverbindung");
		ServiceHandler.setDatabaseView(new ElasticsearchView());
		MailView mailView = new SendGridView();
		ServiceHandler.getDatabaseView().setMailView(mailView);
		ServiceHandler.setMailView(mailView);
		System.out.println("Verbindung zur Datenbank hergestellt");
	}
	
	@Override
	public void destroy() {
		ServiceHandler.getDatabaseView().exit();
	}
	
}
