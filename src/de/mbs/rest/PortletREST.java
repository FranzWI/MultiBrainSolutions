package de.mbs.rest;

import java.util.Vector;
import java.util.function.Consumer;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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
import de.mbs.filter.Admin;
import de.mbs.filter.User;
import de.mbs.handler.ServiceHandler;
import de.mbs.rest.utils.RESTHelper;

@Path("/portlet")
public class PortletREST {

	@GET
	@Path("/getAll")
	@User
	public Response getAll() {
		JSONArray array = new JSONArray();
		for (Portlet p : ServiceHandler.getDatabaseView().getPortletView()
				.getAll()) {
			JSONObject obj = new JSONObject();
			obj.put("id", p.getId());
			obj.put("name", p.getName());
			obj.put("description", p.getDescription());
			obj.put("path", p.getPath());
			obj.put("multiple", p.isMultiple());
			// Gruppe auslesen
			obj.put("groups", RESTHelper.groupsToJSONArray(p.getUsedByGroups()));
			obj.put("xs", p.getSizeXS());
			obj.put("sm", p.getSizeSM());
			obj.put("md", p.getSizeMD());
			obj.put("lg", p.getSizeLG());
			array.add(obj);
		}
		return Response.ok(array.toJSONString()).build();
	}

	@GET
	@Path("/get/{id}")
	@User
	public Response get(@PathParam("id") String id) {
		Portlet p = ServiceHandler.getDatabaseView().getPortletView().get(id);
		if (p != null) {
			JSONObject obj = new JSONObject();
			obj.put("id", p.getId());
			obj.put("name", p.getName());
			obj.put("description", p.getDescription());
			obj.put("path", p.getPath());
			obj.put("multiple", p.isMultiple());
			// Gruppe auslesen
			JSONArray groups = new JSONArray();
			for (String groupId : p.getUsedByGroups()) {
				Group g = ServiceHandler.getDatabaseView().getGroupView()
						.get(groupId);
				// TODO G == null
				if (g != null) {
					JSONObject group = new JSONObject();
					group.put("id", g.getId());
					group.put("name", g.getName());
					group.put("description", g.getDescription());
					groups.add(group);
				}
			}
			obj.put("groups", groups);
			obj.put("xs", p.getSizeXS());
			obj.put("sm", p.getSizeSM());
			obj.put("md", p.getSizeMD());
			obj.put("lg", p.getSizeLG());
			return Response.ok(obj.toString()).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("ID " + id + " ungültig").build();
		}
	}

	@POST
	@Path("/remove/{id}")
	@Admin
	public Response remove(@PathParam("id") String id) {
		if (ServiceHandler.getDatabaseView().getPortletView().remove(id)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("ID " + id + " ungültig").build();
		}
	}

	@POST
	@Path("/add/{json}")
	@Admin
	public Response add(@PathParam("json") String json) {
		try {
			JSONObject obj = RESTHelper.stringToJSONObject(json);
			final Portlet p = new Portlet(null);
			return this.editPortlet(obj, p, false);
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
	@Path("/edit/{json}")
	@Admin
	public Response edit(@PathParam("json") String json) {
		try {
			JSONObject obj = RESTHelper.stringToJSONObject(json);
			String id = obj.get("id") == null ? null : obj.get("id").toString();
			Portlet p = null;
			if (id != null
					&& (p = ServiceHandler.getDatabaseView().getPortletView()
							.get(id)) != null) {
				return this.editPortlet(obj, p, true);
			} else {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Portlet ID ungültig").build();
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

	private Response editPortlet(JSONObject obj, final Portlet p, boolean isEdit)
			throws ParseException, NumberFormatException {
		JSONParser parser = new JSONParser();
		for (Object oKey : obj.keySet()) {
			String key = oKey.toString().toLowerCase();
			String value = obj.get(key) == null ? "" : obj.get(key).toString();
			switch (key) {
			case "id":
				break;
			case "name":
				p.setName(value);
				break;
			case "description":
				p.setDescription(value);
				break;
			case "path":
				p.setPath(value);
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
				p.setUsedByGroups(newgroups);
				break;
			case "xs":
				p.setSizeXS(RESTHelper.getSize(Integer.parseInt((value
						.isEmpty() ? "0" : value))));
				break;
			case "sm":
				p.setSizeSM(RESTHelper.getSize(Integer.parseInt((value
						.isEmpty() ? "0" : value))));
				break;
			case "md":
				p.setSizeMD(RESTHelper.getSize(Integer.parseInt((value
						.isEmpty() ? "0" : value))));
				break;
			case "lg":
				p.setSizeLG(RESTHelper.getSize(Integer.parseInt((value
						.isEmpty() ? "0" : value))));
				break;
			case "multiple":
				p.setMultiple(Boolean.valueOf(value));
				break;
			default:
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity("JSON Objekt ungültig (Schlüssel " + key
								+ " unbekannt)").build();
			}
		}
		if (p.getUsedByGroups().size() > 0) {
			if (isEdit) {
				if (ServiceHandler.getDatabaseView().getPortletView().edit(p) != null) {
					// in den vom Nutzer genutzen Portlets die größe anpassen
					// falls gleich
					for (UserPortlet up : ServiceHandler.getDatabaseView()
							.getUserPortletView().getAll()) {
						if (up.getPortletId().equals(p.getId())
								&& up.getXs() == p.getSizeXS()
								&& up.getSm() == p.getSizeSM()
								&& up.getMd() == p.getSizeMD()
								&& up.getLg() == p.getSizeLG()) {
							up.setXs(p.getSizeXS());
							up.setSm(p.getSizeSM());
							up.setMd(p.getSizeMD());
							up.setLg(p.getSizeLG());
							ServiceHandler.getDatabaseView()
									.getUserPortletView().edit(up);
						}
					}
					return Response.ok().build();
				} else {
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("Fehler beim Ändern des Portlets").build();
				}
			} else {

				if (ServiceHandler.getDatabaseView().getPortletView().add(p) != null) {

					return Response.ok().build();
				} else {
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("Fehler beim Anlegen des Portlets").build();
				}
			}
		} else {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("Fehler es muss mindestens eine Gruppe übergeben werden")
					.build();
		}
	}

}
