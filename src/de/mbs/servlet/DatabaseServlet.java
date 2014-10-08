package de.mbs.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import de.mbs.elasticsearch.ElasticsearchView;
import de.mbs.handler.ServiceHandler;
import de.mbs.interfaces.DatabaseView;

public class DatabaseServlet extends HttpServlet {

	private static final long serialVersionUID = 7913911436790432794L;

	private static DatabaseView view;
	
	@Override
	public void init() throws ServletException {
		System.out.println("Starte Datenbankverbindung");
		//FIXME hier einfach die klasse tauschen ^^ wenn eine andere Datenbank angehangen werden soll
		view = new ElasticsearchView();
		ServiceHandler.setDatabaseView(view);
		//TODO Missverständlich, wird hier nicht geprüft
		System.out.println("Verbindung zur Datenbank hergestellt");
	}
	
	@Override
	public void destroy() {
		view.exit();
	}
	
}
