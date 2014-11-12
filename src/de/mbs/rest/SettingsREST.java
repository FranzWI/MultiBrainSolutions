package de.mbs.rest;

import java.util.Properties;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.mbs.abstracts.db.objects.Settings;
import de.mbs.filter.Admin;
import de.mbs.handler.ServiceHandler;

@Path("/settings")
public class SettingsREST {

	@POST
	@Path("/setProperties/{target}/{jsonData}")
	@Admin
	public Response addPortlet(@PathParam("target") String target,
			@PathParam("jsonData") String jsonData) {
		Properties p = null;
		Settings settings = ServiceHandler.getDatabaseView().getSettingsView()
				.getAll().get(0);
		switch (target) {
		case "db":
			p = settings.getDbProperties();
			break;
		case "mail":
			p = settings.getMailProperties();
			break;
		case "proxy":
			p = settings.getProxyProperties();
			break;
		default:
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Unbekanntes Ziel").build();
		}
		JSONParser parser = new JSONParser();
		jsonData = jsonData.replaceAll("\\(", "{").replaceAll("\\)", "}");
		try {
			JSONObject obj = (JSONObject) parser.parse(jsonData);
			for (Object oKey : obj.keySet()) {
				String key = oKey.toString();
				p.setProperty(key, obj.get(key).toString());
			}
			switch (target) {
			case "db":
				settings.setDbProperties(p);
				break;
			case "mail":
				settings.setMailProperties(p);
				break;
			case "proxy":
				settings.setProxyProperties(p);
				break;
			}
			if (ServiceHandler.getDatabaseView().getSettingsView()
					.edit(settings) == null) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Fehler beim speichern der Einstellungen")
						.build();
			} else {
				return Response.ok().build();
			}
		} catch (ParseException pe) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("JSON Objekt fehlerhaft").build();
		}
	}

}
