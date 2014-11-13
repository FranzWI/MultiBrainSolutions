package de.mbs.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import de.mbs.filter.User;

@Path("/notifications")
public class NotificationREST {

	@Context
	private HttpServletRequest webRequest;
	
	@POST
	@Path("/remove")
	@User
	public Response removeAll(){
		//TODO morgen
		return Response.ok().build();
	}
	
	@POST
	@Path("/remove/{notId}")
	@User
	public Response remove(@PathParam("notId") String notId){
		//TODO morgen
		return Response.ok().build();
	}
	
}
