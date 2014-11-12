package de.mbs.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import de.mbs.handler.ServiceHandler;

public abstract class PrivilegesFilter implements ContainerRequestFilter{

	@Context
	protected HttpServletRequest webRequest;

	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {
		HttpSession session = webRequest.getSession(false);
		String apikey = webRequest.getParameter("apikey");
		if (session != null || apikey != null) {
			if (apikey != null) {
				this.checkApiKey(arg0, apikey);
			} else if (session != null) {
				this.checkSession(arg0, session);
			} else {
				this.denieAccess(arg0);
			}
		} else {
			this.denieAccess(arg0);
		}
	}

	private void checkSession(ContainerRequestContext arg0, HttpSession session) {
		String userID = session.getAttribute("user").toString();
		if(userID != null){
			de.mbs.abstracts.db.objects.User u = ServiceHandler.getDatabaseView().getUserView().get(userID);
			if(u != null){
				this.webRequest.setAttribute("user", u);
				this.check(arg0, u);
			}
		}else{
			this.denieAccess(arg0);
		}
	}

	private void checkApiKey(ContainerRequestContext arg0, String apikey) {
		de.mbs.abstracts.db.objects.User u = ServiceHandler.getDatabaseView().getUserView().getUserByApikey(apikey);
		if(u != null){
			this.webRequest.setAttribute("user", u);
			this.check(arg0, u);
		}
		else
			this.denieAccess(arg0);
	}

	/*
	 * soll aufgerufen werden wenn der API KEy oder die Session ungültig ist
	 */
	private void denieAccess(ContainerRequestContext arg0) {
		arg0.abortWith(Response.status(Response.Status.BAD_REQUEST)
				.entity("APIKEY oder Session nicht valide").build());
	}
	
	/*
	 * soll aufgerufen werden wenn die Rechte nicht ausreichend sind
	 */
	protected void deniePrivAccess(ContainerRequestContext arg0) {
		arg0.abortWith(Response.status(Response.Status.BAD_REQUEST)
				.entity("keine ausreichenden Rechte").build());
	}

	public abstract void check(ContainerRequestContext arg0, de.mbs.abstracts.db.objects.User u);
	
}
