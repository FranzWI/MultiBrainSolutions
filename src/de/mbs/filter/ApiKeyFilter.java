package de.mbs.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Admin
public class ApiKeyFilter implements ContainerRequestFilter {

	@Context
	HttpServletRequest webRequest;

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
		// TODO session okey anhand des angemeldeten Nutzers prüfen ob
		// rechte vorhanden sind
		System.out.println("Session da");
	}

	private void checkApiKey(ContainerRequestContext arg0, String apikey) {
		// TODO anhand des Api keys prüfen ob entsprechende Rechte
		// vorhanden sind
		System.out.println("APIKEY da: " + apikey);
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
	 * soll aufgerufen werden wenn der Nutzer kein Admin ist 
	 */
	private void noAdmin(ContainerRequestContext arg0){
		arg0.abortWith(Response.status(Response.Status.BAD_REQUEST)
				.entity("Sie sind kein Admin").build());
	}

}
