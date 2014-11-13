package de.mbs.rest;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.mbs.abstracts.db.objects.Notification;
import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.mail.MailView;
import de.mbs.abstracts.mail.definition.Mail;
import de.mbs.filter.User;
import de.mbs.handler.ServiceHandler;

@Path("/user")
public class UserREST {

	@Context
	private HttpServletRequest webRequest;

	@POST
	@Path("/register/{userJSON}")
	public Response registerUser(@PathParam("userJSON") String userJSON) {

		JSONParser parser = new JSONParser();
		userJSON = userJSON.replaceAll("\\(", "{").replaceAll("\\)", "}");
		try {
			JSONObject obj = (JSONObject) parser.parse(userJSON);
			de.mbs.abstracts.db.objects.User u = new de.mbs.abstracts.db.objects.User(
					null);
			u.setActive(false);
			String pw = "", rpw = "";
			for (Object oKey : obj.keySet()) {
				String key = oKey.toString();
				switch (key) {
				case "firstname":
					u.setFirstname(obj.get(key).toString());
					break;
				case "lastname":
					u.setLastname(obj.get(key).toString());
					break;
				case "username":
					u.setUsername(obj.get(key).toString());
					break;
				case "mail":
					u.setEmail(obj.get(key).toString());
					break;
				case "pw":
					pw = obj.get(key).toString();
					break;
				case "rpw":
					rpw = obj.get(key).toString();
					break;
				default:
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("JSON Objekt ungültig (Schlüssel "+key+" unbekannt)").build();
				}
			}
			if (pw.equals(rpw)) {
				if (u.getUsername() != null && !u.getUsername().isEmpty()) {
					u.setPw(pw);
					if (ServiceHandler.getDatabaseView().getUserView().add(u) != null) {
						// BEnachrichtigung für den Admin
						Notification not = new Notification(null);
						not.setSubject(u.getFirstname() + " " + u.getLastname()
								+ " freischalten");
						not.setIcon("entypo-user-add");
						not.addGroup(ServiceHandler.getDatabaseView()
								.getGroupView().getAdminGroupId());
						//TODO link hinterlegen
						ServiceHandler.getDatabaseView().getNotificationView()
								.add(not);
						// Email an den neuen Nutzer
						Mail m = new Mail(u.getEmail(),
								"Registrierung am Multi Brain Cockpit",
								MailView.SENDER,
								"Sie haben sich erfolgreich angemeldet");
						ServiceHandler.getDatabaseView().sendMail(m);
						return Response.ok().build();
					} else {
						return Response
								.status(Response.Status.INTERNAL_SERVER_ERROR)
								.entity("Fehler beim anlegen des Nutzers")
								.build();
					}
				} else {
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("Nutzername ungültig").build();
				}
			} else {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Passwörter nicht identisch").build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("JSON Objekt fehlerhaft").build();
		}
	}

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
	public Response setPortlet(@PathParam("portletIds") String portletids) {
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
