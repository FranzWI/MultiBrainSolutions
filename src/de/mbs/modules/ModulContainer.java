package de.mbs.modules;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import de.mbs.modules.interfaces.Modul;

public class ModulContainer {

	private Vector<Modul> modules = null;
	private static ModulContainer container;
	private Map<String, String> modulMap = null;
	
	private ModulContainer() {
		this.modules = new Vector<Modul>();
		this.modulMap = new TreeMap<String, String>();
	}

	public static ModulContainer initialise() {
		if (container == null) {
			container = new ModulContainer();
		}
		return container;
	}

	public void addModul(Modul mod, File jarFile) {
		this.modules.add(mod);
		this.modulMap.put(mod.getModulName()+mod.getVersion(), jarFile.getAbsolutePath());
	}

	public Vector<Modul> getModules() {
		return this.modules;
	}
	
	public String getJarFileToModul(Modul mod){
		return this.modulMap.get(mod.getModulName()+mod.getVersion());
	}

}
