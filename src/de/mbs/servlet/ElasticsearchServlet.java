package de.mbs.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import de.mbs.elasticsearch.ElasticsearchContainer;

public class ElasticsearchServlet extends HttpServlet {

	private static final long serialVersionUID = 7913911436790432794L;

	@Override
	public void init() throws ServletException {
		System.out.println("Starte Elasticsearch verbindung");
		ElasticsearchContainer container = ElasticsearchContainer.initialise();
		//TODO Missverständlich, wird hier nicht geprüft
		System.out.println("Verbindung zu Elasticsearch hergestellt");
	}
	
	@Override
	public void destroy() {
		ElasticsearchContainer.initialise().getESClient().close();
	}
	
}
