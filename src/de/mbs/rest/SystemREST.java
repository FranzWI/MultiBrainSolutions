package de.mbs.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.json.simple.JSONObject;

import de.mbs.handler.ServiceHandler;

@Path("/system")
public class SystemREST {

	@GET
	@Path("/services")
	public String getServices() {
		JSONObject obj = new JSONObject();
		JSONObject db = new JSONObject();
		db.put("name", ServiceHandler.getDatabaseView().getServiceName());
		db.put("status", ServiceHandler.getDatabaseView().isRunning());
		obj.put("db", db);
		JSONObject mail = new JSONObject();
		mail.put("name", ServiceHandler.getMailView().getServiceName());
		mail.put("status", ServiceHandler.getMailView().isRunning());
		obj.put("mail", mail);
		return obj.toJSONString();
	}
	
}
