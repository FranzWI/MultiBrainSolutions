package de.mbs.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.simple.JSONObject;

import de.mbs.handler.ServiceHandler;

@Path("/system")
public class SystemREST {

	@GET
	@Path("/services/{service}")
	public String getServices(@PathParam("service") String service) {
		JSONObject obj = new JSONObject();
		
		switch(service){
		case "db":
			obj.put("name", ServiceHandler.getDatabaseView().getServiceName());
			obj.put("status", ServiceHandler.getDatabaseView().isRunning());
			break;
		case "mail":
			obj.put("name", ServiceHandler.getMailView().getServiceName());
			obj.put("status", ServiceHandler.getMailView().isRunning());
			break;
		default:
			obj.put("error","Unbekannter Dienst");
		}
		return obj.toJSONString();
	}
	
}
