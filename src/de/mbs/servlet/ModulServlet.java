package de.mbs.servlet;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.options.GetPluginOption;
import de.mbs.modules.DataContainer;
import de.mbs.modules.ModulContainer;
import de.mbs.modules.ModulHelper;
import de.mbs.modules.interfaces.Modul;

public class ModulServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2154771014025553642L;

	private ModulContainer modules;

	private void readJar(File file) {
		PluginManager pm = PluginManagerFactory.createPluginManager();
		pm.addPluginsFrom(file.toURI());
		Modul mod = pm.getPlugin(Modul.class, new GetPluginOption[0]);
		modules.addModul(mod, file);
	}

	@Override
	public void init() throws ServletException {
		System.out.println("initialisiere Module");
		this.modules = ModulContainer.initialise();
		ModulHelper.setSerlvetContextPath(this.getServletContext().getRealPath("/"));
		String path = this.getServletContext().getRealPath("/")
				+ "WEB-INF/lib/modules";
		File modulesDir = new File(path);
		for (File fileEntry : modulesDir.listFiles()) {
			if (!fileEntry.isDirectory() && fileEntry.getName().endsWith("jar")) {
				this.readJar(fileEntry);
			}
		}
		System.out.println(this.modules.getModules().size()
				+ " Module gefunden");
		for (Modul mod : this.modules.getModules()) {
			System.out.println("Modul:\t" + mod.getModulName()
					+ " installiert: " + mod.isInstalled() + " läuft: "
					+ mod.isRunning());
			if (mod.isInstalled()) {
				System.out.println("Modul " + mod.getModulName()
						+ " startup wird ausgeführt.");
				mod.startup();
				System.out.println("Modul " + mod.getModulName()
						+ " startup wurde ausgeführt.");
			}
		}
	}

	@Override
	public void destroy() {
		for (Modul mod : this.modules.getModules()) {
			if (mod.isInstalled()) {
				System.out.println("Modul " + mod.getModulName()
						+ " shutdown wird ausgeführt.");
				mod.shutdown();
				System.out.println("Modul " + mod.getModulName()
						+ " shutdown wurde ausgeführt.");
			}
		}
	}
}
