package de.mbs.modules.interfaces;

import java.io.File;

import net.xeoh.plugins.base.Plugin;

public interface Modul extends Plugin{

	/**
	 * @return den Namen der im Menü angezeigt wird
	 */
	public String getMenuName();

	/**
	 * Name des Moduls
	 * @return
	 */
	public String getModulName();
	
	/**
	 * @return Beschreibung des Modules
	 */
	public String getDescription();

	/**
	 * Initalisiert das Modul
	 * 
	 * @param path - Ordner des Wurzelverzeichnisses
	 * @param jarfile  - File in dem die Datenstehen
	 * @return true falls erfolgreich, false falls nicht
	 */
	public boolean install(File path, File jarfile);
	
	/**
	 * entfernt aller vorher installierten dateien
	 * 
	 * @return 
	 */
	public boolean remove(File path);

	/**
	 * @return true falls breits initialisiert sind, false falls noch nichts
	 *         initialisiert ist (DB...)
	 */
	public boolean isInstalled();

	/**
	 * @return true falls das Modul funktioniert, false falls nicht
	 */
	public boolean isRunning();
	
	/**
	 * @return Fehlermeldung warum das Modul nicht funktioniert
	 */
	public String getError();
	
	/**
	 * @param path - Ordner des Wurzelverzeichnisses
	 * @return Liefert den Pfad zum Frontend bsp.: 'WEB-INF/includes/modules/modul.groovy', falls kein Frontend null
	 */
	public String getFrontendFile();
	
	/**
	 * 
	 * @return den Pfad für den Configoberfläche des Administrators, falls keine Config null
	 */
	public String getConfigFile();
	
	/**
	 * 
	 * @return Pfade für die Portlets falls keine dann null
	 */
	public String[] getPortletFiles();
	
	/**
	 * Methode die beim Starten des Management Cockpits ausgeführt wird (Tomcat start)
	 * @return true falls erfolgreich, false falls nicht
	 */
	public boolean startup();
	
	/**
	 *  Methode die beim Herunterfahren des Management Cockpits ausgeführt wird (Tomcat stop, undeploy)
	 * @return true falls erfolgreich, false falls nicht
	 */
	public boolean shutdown();
	
	
}
