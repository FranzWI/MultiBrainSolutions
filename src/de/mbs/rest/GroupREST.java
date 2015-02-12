package de.mbs.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import de.mbs.abstracts.db.objects.Group;
import de.mbs.filter.Admin;
import de.mbs.filter.User;
import de.mbs.handler.ServiceHandler;
import de.mbs.rest.utils.RESTHelper;

@Path("/group")
public class GroupREST {

	@GET
	@Path("/getAll")
	@User
	public String getServices() {
		JSONArray obj = new JSONArray();
		for (Group g : ServiceHandler.getDatabaseView().getGroupView().getAll()) {
			JSONObject group = new JSONObject();
			group.put("id", g.getId());
			group.put("name", g.getName());
			group.put("description", g.getDescription());
			obj.add(group);
		}
		return obj.toJSONString();
	}

	@GET
	@Path("/get/{id}")
	@User
	public String getGroup(@PathParam("id") String id) {
		Group group = ServiceHandler.getDatabaseView().getGroupView().get(id);
		JSONObject obj = new JSONObject();
		if (group != null) {
			obj.put("id", group.getId());
			obj.put("name", group.getName());
			obj.put("description", group.getDescription());
		} else {
			obj.put("error", "ID ungültig");
		}
		return obj.toJSONString();
	}

	@POST
	@Path("/add/{groupJSON}")
	@Admin
	public Response addGroup(@PathParam("groupJSON") String groupJSON) {
		try {
			JSONObject obj = RESTHelper.stringToJSONObject(groupJSON);
			Group group = new Group(null);
			group.setName(obj.get("name").toString());
			group.setDescription(obj.get("description").toString());
			if (ServiceHandler.getDatabaseView().getGroupView().add(group) == null) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Fehler beim speichern der Gruppe").build();
			} else {
				return Response.ok().build();
			}
		} catch (NullPointerException ex){
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Datenfehlerhaft").build();
		} catch (ParseException pe) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("JSON Objekt fehlerhaft").build();
		}
	}

	@POST
	@Path("/edit/{groupJSON}")
	@Admin
	public Response editGroup(@PathParam("groupJSON") String groupJSON) {
		// TODO Admin und Usergruppe dürfen nicht geändert werden!!
		try {
			JSONObject obj = RESTHelper.stringToJSONObject(groupJSON);
			Group group = ServiceHandler.getDatabaseView().getGroupView()
					.get(obj.get("id").toString());
			if (group != null) {
				group.setName(obj.get("name").toString());
				group.setDescription(obj.get("description").toString());
				if (ServiceHandler.getDatabaseView().getGroupView().edit(group) == null) {
					return Response
							.status(Response.Status.INTERNAL_SERVER_ERROR)
							.entity("Fehler beim speichern der Gruppe").build();
				} else {
					return Response.ok().build();
				}
			} else {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Gruppe unbekannt").build();
			}
		} catch (ParseException pe) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("JSON Objekt fehlerhaft").build();
		}
	}

	@POST
	@Path("/remove/{groupId}")
	@Admin
	public Response removeGroup(@PathParam("groupId") String groupId) {
		// TODO Admin und Usergruppe dürfen nicht gelöscht werden!!
		if (ServiceHandler.getDatabaseView().getGroupView().remove(groupId)) {
			return Response.ok().build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("Fehler beim entfernen der Gruppe").build();
	}

}
