package de.mbs.rest;

import java.util.UUID;
import java.util.Vector;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.mbs.abstracts.db.objects.Group;
import de.mbs.abstracts.db.objects.Notification;
import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.objects.UserPortlet;
import de.mbs.abstracts.mail.MailView;
import de.mbs.abstracts.mail.definition.Mail;
import de.mbs.crypt.Crypt;
import de.mbs.filter.Admin;
import de.mbs.filter.User;
import de.mbs.handler.ServiceHandler;
import de.mbs.rest.utils.RESTHelper;

@Path("/user")
public class UserREST {

	@Context
	private HttpServletRequest webRequest;

	@POST
	@Path("/register/{userJSON}")
	public Response registerUser(@PathParam("userJSON") String userJSON) {
		try {
			JSONObject obj = RESTHelper.stringToJSONObject(userJSON);
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
					for(de.mbs.abstracts.db.objects.User user : ServiceHandler.getDatabaseView().getUserView().getAll()){
						if(u.getUsername().trim().equals(user.getUsername())){
							return Response.status(Status.BAD_REQUEST).entity("Nutzername bereits vergeben").build();
						}
					}
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
					return Response
							.status(Response.Status.BAD_REQUEST)
							.entity("JSON Objekt ungültig (Schlüssel " + key
									+ " unbekannt)").build();
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
						// TODO link hinterlegen
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
			UserPortlet up = new UserPortlet(null);
			up.setOwnerId(u.getId());
			up.setPortletId(p.getId());
			up.setXs(p.getSizeXS());
			up.setSm(p.getSizeSM());
			up.setMd(p.getSizeMD());
			up.setLg(p.getSizeLG());
			up.setOrder(ServiceHandler.getDatabaseView().getUserPortletView()
					.byOwner(u.getId()).size());
			if (ServiceHandler.getDatabaseView().getUserPortletView().add(up) != null)
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
		UserPortlet p = ServiceHandler.getDatabaseView().getUserPortletView()
				.get(portletid);
		if (p != null && u != null) {
			if (ServiceHandler.getDatabaseView().getUserPortletView()
					.remove(portletid))
				return Response.ok().build();
			else
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Fehler beim entfernen des Portlets").build();
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

		Vector<UserPortlet> portlets = new Vector<UserPortlet>();
		for (String portledid : portletids.split(",")) {
			UserPortlet p = ServiceHandler.getDatabaseView()
					.getUserPortletView().get(portledid);
			if (p == null) {

				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Portlet ID (" + portledid + ") fehlerhaft")
						.build();
			} else {
				portlets.add(p);
			}
		}

		if (ServiceHandler.getDatabaseView().getUserPortletView()
				.setPortlets(portlets, u.getId()))
			return Response.ok().build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Fehler beim speichern des Users").build();
	}

	@POST
	@Path("/setPortlets/settings/{id}/")
	@User
	public Response setPortletSettings(@PathParam("id") String id,
			@FormParam("setting") String settings, @FormParam("xs") int xs,
			@FormParam("sm") int sm, @FormParam("md") int md,
			@FormParam("lg") int lg) {
		de.mbs.abstracts.db.objects.User u = (de.mbs.abstracts.db.objects.User) webRequest
				.getAttribute("user");
		if (u == null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("User ID fehlerhaft").build();
		UserPortlet p = ServiceHandler.getDatabaseView().getUserPortletView()
				.get(id);
		if (p == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Portlet ID (" + id + ") fehlerhaft").build();
		} else {
			if (!p.getOwnerId().equals(u.getId())) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Sie sind nicht besizter des Portlets").build();
			}
			p.setSettings(settings);
			p.setXs(RESTHelper.getSize(xs));
			p.setSm(RESTHelper.getSize(sm));
			p.setMd(RESTHelper.getSize(md));
			p.setLg(RESTHelper.getSize(lg));
			if (ServiceHandler.getDatabaseView().getUserPortletView().edit(p) != null) {
				return Response.ok().build();
			}
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("Fehler beim Speichern").build();
	}

	@POST
	@Path("/add/{userJSON}")
	@Admin
	public Response add(@PathParam("userJSON") String json) {
		try {
			JSONObject obj = RESTHelper.stringToJSONObject(json);
			de.mbs.abstracts.db.objects.User user = new de.mbs.abstracts.db.objects.User(
					null);
			return this.editUser(obj, user, false);
		} catch (ParseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("JSON Objekt fehlerhaft").build();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("JSON Objekt fehlerhaft 2").build();
		}
	}

	@POST
	@Path("/edit/{userJSON}")
	@Admin
	public Response edit(@PathParam("userJSON") String json) {
		try {
			JSONObject obj = RESTHelper.stringToJSONObject(json);
			String id = obj.get("id") == null ? null : obj.get("id").toString();
			de.mbs.abstracts.db.objects.User user = null;
			if (id != null
					&& (user = ServiceHandler.getDatabaseView().getUserView()
							.get(id)) != null) {
				return this.editUser(obj, user, true);
			} else {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("User ID ungültig").build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("JSON Objekt fehlerhaft").build();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("JSON Objekt fehlerhaft 2").build();
		}
	}

	@GET
	@Path("/remove/{id}")
	@Admin
	public Response remove(@PathParam("id") String id) {
		if (ServiceHandler.getDatabaseView().getUserView().remove(id)) {
			return Response.ok().build();
		} else {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("ID " + id + " ungültig oder Fehler beim entfernen")
					.build();
		}
	}

	/**
	 * 
	 * @param obj
	 *            - der User als JSON Objekt
	 * @param user
	 *            - der Nutzer in den die DAten des JSONObjekts gespeichert
	 *            werden sollen
	 * @param isEdit
	 *            - true falls es ein bereits existierender Nutzer ist, false
	 *            fals nicht
	 * @return
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	private Response editUser(JSONObject obj,
			final de.mbs.abstracts.db.objects.User user, boolean isEdit)
			throws ParseException, NumberFormatException {
		JSONParser parser = new JSONParser();
		for (Object oKey : obj.keySet()) {
			String key = oKey.toString().toLowerCase();
			String value = obj.get(key) == null ? "" : obj.get(key).toString();
			switch (key) {
			case "id":
				break;
			case "firstname":
				user.setFirstname(value);
				break;
			case "lastname":
				user.setLastname(value);
				break;
			case "username":
				user.setUsername(value);
				break;
			case "email":
				user.setEmail(value);
				break;
			case "apikey":
				user.setApikey(value);
				break;
			case "active":
				user.setActive(Boolean.valueOf(value));
				break;
			case "groups":
				JSONArray array = (JSONArray) parser.parse(value);
				final Vector<String> newgroups = new Vector<String>();
				array.forEach(new Consumer() {

					@Override
					public void accept(Object t) {
						Group g = ServiceHandler.getDatabaseView()
								.getGroupView().get(t.toString());
						if (g != null) {
							newgroups.add(g.getId());
						}
					}
				});
				user.setMembership(newgroups);
				break;
			default:
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity("JSON Objekt ungültig (Schlüssel " + key
								+ " unbekannt)").build();
			}
		}
		if (user.getMembership().size() > 0) {
			// editieren ?
			if (isEdit) {
				if (ServiceHandler.getDatabaseView().getUserView().edit(user) != null) {
					return Response.ok().build();
				} else {
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("Fehler beim Ändern des Nutzers").build();
				}
			} else {
				// neu anlegen
				String pw = RESTHelper.randomPassword();
				user.setPw(pw);
				if (ServiceHandler.getDatabaseView().getUserView().add(user) != null) {
					// BEnachrichtigung für den Admin
					if (!user.isActive()) {
						Notification not = new Notification(null);
						not.setSubject(user.getFirstname() + " "
								+ user.getLastname() + " freischalten");
						not.setIcon("entypo-user-add");
						not.addGroup(ServiceHandler.getDatabaseView()
								.getGroupView().getAdminGroupId());
						// TODO link hinterlegen
						ServiceHandler.getDatabaseView().getNotificationView()
								.add(not);
					}
					// Email an den neuen Nutzer
					// TODO URL des Servers mitabfragen so das diese mit in die
					// mail kann
					Mail m = new Mail(
							user.getEmail(),
							"Registrierung am Multi Brain Cockpit",
							MailView.SENDER,
							"F&uuml;r Sie wurde ein Account angelegt<br/>"
									+ "Anmeldename: "
									+ user.getUsername()
									+ "<br/>"
									+ "Passwort: "
									+ pw
									+ "<br/><br/>"
									+ (user.isActive() ? ""
											: "Sie m&uuml;ssen aber noch Ihre aktivierung abwarten."));
					ServiceHandler.getDatabaseView().sendHtmlMail(m);
					return Response.ok().build();
				} else {
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("Fehler beim Anlegen des Nutzers").build();
				}
			}
		} else {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("Fehler es muss mindestens eine Gruppe übergeben werden")
					.build();
		}
	}

	@GET
	@Path("/get/{id}")
	@Admin
	public Response getUser(@PathParam("id") String id) {
		JSONObject obj = new JSONObject();
		de.mbs.abstracts.db.objects.User user = ServiceHandler
				.getDatabaseView().getUserView().get(id);
		if (user == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Unbekannte ID").build();
		}
		obj.put("id", user.getId());
		obj.put("username", user.getUsername());
		obj.put("firstname", user.getFirstname());
		obj.put("lastname", user.getLastname());
		obj.put("apikey", user.getApikey());
		obj.put("active", user.isActive());
		obj.put("email", user.getEmail());
		obj.put("groups", RESTHelper.groupsToJSONArray(user.getMembership()));
		return Response.ok(obj.toJSONString()).build();
	}

	@GET
	@Path("/getAllAdmin")
	@Admin
	public Response getAllAdmin() {
		JSONArray array = new JSONArray();
		for (de.mbs.abstracts.db.objects.User user : ServiceHandler
				.getDatabaseView().getUserView().getAll()) {
			JSONObject obj = new JSONObject();
			obj.put("id", user.getId());
			obj.put("username", user.getUsername());
			obj.put("firstname", user.getFirstname());
			obj.put("lastname", user.getLastname());
			obj.put("apikey", user.getApikey());
			obj.put("email", user.getEmail());
			obj.put("groups",
					RESTHelper.groupsToJSONArray(user.getMembership()));
			array.add(obj);
		}
		return Response.ok(array.toJSONString()).build();
	}

	@GET
	@Path("/getAll")
	@User
	public Response getAll() {
		JSONArray array = new JSONArray();
		for (de.mbs.abstracts.db.objects.User user : ServiceHandler
				.getDatabaseView().getUserView().getAll()) {
			JSONObject obj = new JSONObject();
			obj.put("id", user.getId());
			obj.put("username", user.getUsername());
			obj.put("firstname", user.getFirstname());
			obj.put("lastname", user.getLastname());
			obj.put("email", user.getEmail());
			array.add(obj);
		}
		return Response.ok(array.toJSONString()).build();
	}

	@GET
	@Path("/apikey")
	@Admin
	public Response generateApikey() {
		for (int i = 0; i < 50; i++) {
			String api = UUID.randomUUID().toString();
			if (ServiceHandler.getDatabaseView().getUserView()
					.getUserByApikey(api) == null) {
				return Response.ok(api).build();
			}
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("Fehler konnte keinen gültigen ApiKey erzeugen.")
				.build();
	}

	@GET
	@Path("/resetPassword/{id}")
	@Admin
	public Response resetPassword(@PathParam("id") String id) {
		de.mbs.abstracts.db.objects.User user = ServiceHandler
				.getDatabaseView().getUserView().get(id);
		if (user == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Unbekannte ID").build();
		}
		String password = RESTHelper.randomPassword();
		user.setPw(Crypt.getCryptedPassword(password));
		if (ServiceHandler.getDatabaseView().getUserView().edit(user) != null) {
			Mail mail = new Mail(user.getEmail(), "Passwort zurück gesetzt",
					MailView.SENDER, "Ihr neues Passwort lautet:\n" + password);
			ServiceHandler.getDatabaseView().sendMail(mail);
			return Response.ok().build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("Fehler beim speichern des Nutzers").build();
	}

}
