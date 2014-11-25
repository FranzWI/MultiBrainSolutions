package de.mbs.modules.interfaces;

import java.util.Vector;

import net.xeoh.plugins.base.Plugin;
import de.mbs.modules.DataContainer;

public interface Modul extends Plugin{

	/**
	 * @return den Namen der im Men� angezeigt wird
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
	 * @return true falls erfolgreich, false falls nicht
	 */
	public boolean install(DataContainer container);
	
	/**
	 * entfernt aller vorher installierten dateien
	 * 
	 * @return 
	 */
	public boolean remove(DataContainer container);

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
	 * @return den Pfad f�r den Configoberfl�che des Administrators, falls keine Config null
	 */
	public String getConfigFile();
	
	/**
	 * Methode die beim Starten des Management Cockpits ausgef�hrt wird (Tomcat start)
	 * @return true falls erfolgreich, false falls nicht
	 */
	public boolean startup();
	
	/**
	 *  Methode die beim Herunterfahren des Management Cockpits ausgef�hrt wird (Tomcat stop, undeploy)
	 * @return true falls erfolgreich, false falls nicht
	 */
	public boolean shutdown();
	
	/**
	 * liefert die aktuelle Version
	 * @return
	 */
	public String getVersion();
	
	/**
	 * liefert den namen der Css-Klasse des Entypo Icons
	 * @return
	 */
	public String getMenuIcon();
	
	/**
	 * gibt an ob nach der Installation des Moduls der Tomcat neugestartet werden muss
	 * @return
	 */
	public boolean requiresRestart();
	
	
	/**
	 * liste der Javascript dateien die verwendet werden sollen
	 * 
	 * @return
	 */
	public Vector<String> getJavascripts();
	
}
