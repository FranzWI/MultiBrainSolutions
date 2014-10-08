package de.mbs.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import de.mbs.handler.ServiceHandler;
import de.mbs.mail.sendgrid.SendGridView;

public class MailServlet extends HttpServlet {

	private static final long serialVersionUID = 568638160556721224L;

	@Override
	public void init() throws ServletException {
		System.out.println("Mail wird initialisiert");
		ServiceHandler.setMailView(new SendGridView());
	}
	
	@Override
	public void destroy() {
		System.out.println("Mail wird geschlossen");
	}
	
}
