package de.mbs.rest;

import java.util.UUID;
import java.util.Vector;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.mbs.abstracts.db.objects.Group;
import de.mbs.abstracts.db.objects.Notification;
import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.mail.MailView;
import de.mbs.abstracts.mail.definition.Mail;
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

	@POST
	@Path("/add/{userJSON}")
	@Admin
	public Response add(@PathParam("userJSON") String json) {
		JSONParser parser = new JSONParser();
		json = json.replaceAll("\\(", "{").replaceAll("\\)", "}");
		try {
			JSONObject obj = (JSONObject) parser.parse(json);
			de.mbs.abstracts.db.objects.User user = new de.mbs.abstracts.db.objects.User(null);
			return this.editUser(obj, user,false);
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
		JSONParser parser = new JSONParser();
		json = json.replaceAll("\\(", "{").replaceAll("\\)", "}");
		try {
			JSONObject obj = (JSONObject) parser.parse(json);
			String id = obj.get("id") == null ? null : obj.get("id").toString();
			de.mbs.abstracts.db.objects.User p = null;
			if (id != null
					&& (p = ServiceHandler.getDatabaseView().getUserView()
							.get(id)) != null) {
				return this.editUser(obj, p, true);
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

	@POST
	@Path("/remove/{id}")
	@Admin
	public Response remove(@PathParam("id") String id) {
		if (ServiceHandler.getDatabaseView().getUserView().remove(id)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("ID " + id + " ungültig").build();
		}
	}

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
				user.setActive(Boolean.getBoolean(value));
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
				if(user.getPw() != null && !user.getPw().isEmpty()){
					if (ServiceHandler.getDatabaseView().getUserView().add(user) != null) {
						return Response.ok().build();
					} else {
						return Response.status(Response.Status.BAD_REQUEST)
								.entity("Fehler beim Anlegen des Nutzers").build();
					}
				}else{
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("Es muss ein Passwort angegeben werden").build();
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
		//TODO fertig machen
		return Response.ok().build();
	}
	
	@GET
	@Path("/getAllAdmin")
	@Admin
	public Response getAllAdmin(){
		JSONArray array = new JSONArray();
		for(de.mbs.abstracts.db.objects.User user : ServiceHandler.getDatabaseView().getUserView().getAll()){
			JSONObject obj = new JSONObject();
			obj.put("id", user.getId());
			obj.put("username", user.getUsername());
			obj.put("firstname", user.getFirstname());
			obj.put("lastname", user.getLastname());
			obj.put("apikey", user.getApikey());
			obj.put("email",user.getEmail());
			obj.put("groups", RESTHelper.groupsToJSONArray(user.getMembership()));
			array.add(obj);
		}
		return Response.ok(array.toJSONString()).build();
	}
	
	@GET
	@Path("/getAll")
	@User
	public Response getAll(){
		JSONArray array = new JSONArray();
		for(de.mbs.abstracts.db.objects.User user : ServiceHandler.getDatabaseView().getUserView().getAll()){
			JSONObject obj = new JSONObject();
			obj.put("id", user.getId());
			obj.put("username", user.getUsername());
			obj.put("firstname", user.getFirstname());
			obj.put("lastname", user.getLastname());
			obj.put("email",user.getEmail());
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

}
