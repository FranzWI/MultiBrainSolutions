package de.mbs.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import de.mbs.db.elasticsearch.ElasticsearchView;
import de.mbs.handler.ServiceHandler;
import de.mbs.interfaces.DatabaseView;
import de.mbs.interfaces.MailView;
import de.mbs.mail.sendgrid.SendGridView;

public class ServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 7913911436790432794L;
	
	@Override
	public void init() throws ServletException {
		System.out.println("Starte Datenbankverbindung");
		ServiceHandler.setDatabaseView(new ElasticsearchView());
		System.out.println("Verbindung zur Datenbank hergestellt");
		System.out.println("Starte Mailhandler");
		ServiceHandler.setMailView(new SendGridView());
		System.out.println("Mailhandler gestartet");
	}
	
	@Override
	public void destroy() {
		ServiceHandler.getDatabaseView().exit();
		
	}
	
}
