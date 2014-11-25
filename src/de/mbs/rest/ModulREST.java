package de.mbs.rest;

import java.io.File;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;

import de.mbs.filter.Admin;
import de.mbs.filter.User;
import de.mbs.modules.DataContainer;
import de.mbs.modules.ModulContainer;
import de.mbs.modules.interfaces.Modul;

@Path("/modules")
public class ModulREST {

	@POST
	@Path("/install/{modulname}/{version}")
	@Admin
	public Response install(@PathParam("modulname") String modulname,
			@PathParam("version") String version) {
		ModulContainer modules = ModulContainer.initialise();
		boolean hit = false, success = false;
		String error = null;
		for (Modul mod : modules.getModules()) {
			if (mod.getModulName().equals(modulname)
					&& mod.getVersion().equals(version)) {
				hit = true;
				if (mod.isInstalled()) {
					return Response
							.status(Response.Status.BAD_REQUEST)
							.entity("Modul " + modulname + " in Version "
									+ version + " bereits installiert.")
							.build();
				} else {
					String jarPath = modules.getJarFileToModul(mod);
					if (jarPath == null) {
						return Response
								.status(Response.Status.BAD_REQUEST)
								.entity("Modul " + modulname + " in Version "
										+ version
										+ " konnte Jar Datei nicht finden")
								.build();
					} else {
						DataContainer cont = new DataContainer(
								new File(jarPath));
						success = mod.install(cont);
						error = mod.getError();
					}
				}
				break;
			}
		}
		if (!hit) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("Modul " + modulname + " in Version " + version
							+ " wurde nicht gefunden").build();
		} else {
			if (success) {
				return Response.ok().build();
			} else {
				return Response
						.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Modul "
								+ modulname
								+ " in Version "
								+ version
								+ " konnte nicht installiert werden ein Fehler ist aufgetretten.\n"
								+ error).build();
			}
		}
	}
}
