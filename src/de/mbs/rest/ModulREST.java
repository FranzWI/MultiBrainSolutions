package de.mbs.rest;

import java.io.File;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.simple.JSONObject;

import de.mbs.modules.DataContainer;
import de.mbs.modules.ModulContainer;
import de.mbs.modules.interfaces.Modul;

@Path("/modules")
public class ModulREST {

	@POST
	@Path("/install/{modulname}/{version}")
	public String install(@PathParam("modulname") String modulname,
			@PathParam("version") String version) {
		JSONObject obj = new JSONObject();
		ModulContainer modules = ModulContainer.initialise();
		boolean hit = false, success = false;
		String error = null;
		for (Modul mod : modules.getModules()) {
			if (mod.getModulName().equals(modulname)
					&& mod.getVersion().equals(version)) {
				hit = true;
				if (mod.isInstalled()) {
					obj.put("error", "Modul " + modulname + " in Version "
							+ version + " bereits installiert.");
				} else {
					String jarPath = modules.getJarFileToModul(mod);
					if (jarPath == null) {
						obj.put("error", "Modul " + modulname + " in Version "
								+ version + " konnte Jar Datei nicht finden");
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
			obj.put("error", "Modul " + modulname + " in Version " + version
					+ " wurde nicht gefunden");
		} else {
			if (success) {
				obj.put("status", "ok");
			} else {
				obj.put("error",
						"Modul "
								+ modulname
								+ " in Version "
								+ version
								+ " konnte nicht installiert werden ein Fehler ist aufgetretten.\n"
								+ error);
			}
		}
		return obj.toString();
	}
}
