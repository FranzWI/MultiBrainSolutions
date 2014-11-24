package de.mbs.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.mbs.abstracts.db.objects.Notification;
import de.mbs.filter.User;
import de.mbs.handler.ServiceHandler;

@Path("/notifications")
public class NotificationREST {

	@Context
	private HttpServletRequest webRequest;

	/**
	 * liefert alle Notificationen eines Nutzers
	 * 
	 * @return
	 */
	@GET
	@Path("/getAll")
	@User
	public Response getAll() {
		de.mbs.abstracts.db.objects.User u = (de.mbs.abstracts.db.objects.User) webRequest
				.getAttribute("user");
		if (u == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Nutzer ungültig").build();
		}
		JSONArray array = new JSONArray();
		for (Notification n : ServiceHandler.getDatabaseView()
				.getNotificationView().getNotificationsForUser(u.getId())) {
			JSONObject obj = new JSONObject();
			obj.put("id", n.getId());
			obj.put("icon", n.getIcon());
			obj.put("link", n.getLink());
			obj.put("release", n.getReleaseTimestamp());
			array.add(obj);
		}
		return Response.ok(array.toJSONString()).build();
	}
	
	/**
	 * entfernt alle Benachrichtigungen eines Nutzers
	 * 
	 * @return
	 */
	@GET
	@Path("/removeAll")
	@User
	public Response removeAll() {
		de.mbs.abstracts.db.objects.User u = (de.mbs.abstracts.db.objects.User) webRequest
				.getAttribute("user");
		if (u == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Nutzer ungültig").build();
		}
		for (Notification n : ServiceHandler.getDatabaseView()
				.getNotificationView().getNotificationsForUser(u.getId())) {
			if (!ServiceHandler.getDatabaseView().getNotificationView()
					.remove(n.getId())) {
				return Response
						.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Fehler beim entfernen der Notification ID: "
								+ n.getId()).build();
			}
		}
		return Response.ok().build();
	}

	@GET
	@Path("/remove/{id}")
	@User
	public Response remove(@PathParam("id") String notId) {
		de.mbs.abstracts.db.objects.User u = (de.mbs.abstracts.db.objects.User) webRequest
				.getAttribute("user");
		if (u == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Nutzer ungültig").build();
		}
		if (ServiceHandler.getDatabaseView().getNotificationView()
				.getNotificationsForUser(u.getId()).contains(notId)) {
			if (ServiceHandler.getDatabaseView().getNotificationView()
					.remove(notId)) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Fehler beim entfernen der Notification")
						.build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Sie dürfen nur Ihre Notfications ändern.").build();
		}
	}

}
