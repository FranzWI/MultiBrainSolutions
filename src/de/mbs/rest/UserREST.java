package de.mbs.rest;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.filter.User;
import de.mbs.handler.ServiceHandler;

@Path("/user")
public class UserREST {

	@Context
	private HttpServletRequest webRequest;

	@POST
	@Path("/addPortlet/{portletId}")
	@User
	public Response addPortlet(@PathParam("portletId") String portletid) {
		de.mbs.abstracts.db.objects.User u = (de.mbs.abstracts.db.objects.User) webRequest
				.getAttribute("user");
		Portlet p = ServiceHandler.getDatabaseView().getPortletView()
				.get(portletid);
		if (p != null && u != null) {
			u.addPortlet(portletid);
			if (ServiceHandler.getDatabaseView().getUserView().edit(u) != null)
				return Response.ok().build();
			else
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Fehler beim speichern des Users").build();
		}
		return Response.status(Response.Status.BAD_REQUEST)
				.entity("User oder Portlet ID fehlerhaft").build();
	}

	@POST
	@Path("/removePortlet/{portletId}")
	@User
	public Response removePortlet(@PathParam("portletId") String portletid) {
		de.mbs.abstracts.db.objects.User u = (de.mbs.abstracts.db.objects.User) webRequest
				.getAttribute("user");
		Portlet p = ServiceHandler.getDatabaseView().getPortletView()
				.get(portletid);
		if (p != null && u != null) {
			u.removePortlet(portletid);
			if (ServiceHandler.getDatabaseView().getUserView().edit(u) != null)
				return Response.ok().build();
			else
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Fehler beim speichern des Users").build();
		}
		return Response.status(Response.Status.BAD_REQUEST)
				.entity("User oder Portlet ID fehlerhaft").build();
	}

	@POST
	@Path("/setPortlets/{portletIds}")
	@User
	public Response setPortlet(
			@PathParam("portletIds") String portletids) {
		de.mbs.abstracts.db.objects.User u = (de.mbs.abstracts.db.objects.User) webRequest
				.getAttribute("user");
		if (u == null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("User ID fehlerhaft").build();

		Vector<String> portletIDs = new Vector<String>();
		for (String portledid : portletids.split(",")) {
			Portlet p = ServiceHandler.getDatabaseView().getPortletView()
					.get(portledid);
			if (p == null) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Portlet ID (" + portledid + ") fehlerhaft")
						.build();
			} else {
				portletIDs.add(p.getId());
			}
		}
		u.setPortlets(portletIDs);
		if (ServiceHandler.getDatabaseView().getUserView().edit(u) != null)
			return Response.ok().build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Fehler beim speichern des Users").build();
	}
}
